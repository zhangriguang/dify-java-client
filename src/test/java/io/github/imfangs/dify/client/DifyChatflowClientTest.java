package io.github.imfangs.dify.client;

import io.github.imfangs.dify.client.callback.ChatflowStreamCallback;
import io.github.imfangs.dify.client.config.DifyTestConfig;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.event.*;
import io.github.imfangs.dify.client.model.chat.AppInfoResponse;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.ChatMessageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 工作流编排对话型应用客户端测试类
 * 注意：运行测试前，请确保已经正确配置了 dify-test-config.properties 文件
 */
public class DifyChatflowClientTest {
    private static final String BASE_URL = DifyTestConfig.getBaseUrl();
    private static final String API_KEY = DifyTestConfig.getChatApiKey();
    private static final String USER_ID = "test-user-" + System.currentTimeMillis();

    private DifyChatflowClient chatWorkflowClient;

    @BeforeEach
    public void setUp() {
        chatWorkflowClient = DifyClientFactory.createChatWorkflowClient(BASE_URL, API_KEY);
    }

    @AfterEach
    public void tearDown() {
        chatWorkflowClient.close();
    }

    /**
     * 测试发送对话消息（阻塞模式）
     */
    @Test
    public void testSendChatMessage() throws Exception {
        // 创建聊天消息
        ChatMessage message = ChatMessage.builder()
                .query("你好，请介绍一下自己")
                .user(USER_ID)
                .responseMode(ResponseMode.BLOCKING)
                .build();

        // 发送消息并获取响应
        ChatMessageResponse response = chatWorkflowClient.sendChatMessage(message);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getMessageId());
        assertNotNull(response.getAnswer());
        System.out.println("回复: " + response.getAnswer());
    }

    /**
     * 测试发送对话消息（流式模式）
     */
    @Test
    public void testSendChatMessageStream() throws Exception {
        // 创建聊天消息
        ChatMessage message = ChatMessage.builder()
                .query("请给我讲一个简短的故事")
                .user(USER_ID)
                .responseMode(ResponseMode.STREAMING)
                .build();

        // 用于等待异步回调完成
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder responseBuilder = new StringBuilder();

        // 发送流式消息
        chatWorkflowClient.sendChatMessageStream(message, new ChatflowStreamCallback() {
            @Override
            public void onMessage(MessageEvent event) {
                System.out.println("收到消息片段: " + event.getAnswer());
                responseBuilder.append(event.getAnswer());
            }

            @Override
            public void onMessageEnd(MessageEndEvent event) {
                System.out.println("消息结束，完整消息ID: " + event.getMessageId());
                latch.countDown();
            }

            @Override
            public void onMessageFile(MessageFileEvent event) {
                System.out.println("收到文件: " + event);
            }

            @Override
            public void onTTSMessage(TtsMessageEvent event) {
                System.out.println("收到TTS消息: " + event);
            }

            @Override
            public void onTTSMessageEnd(TtsMessageEndEvent event) {
                System.out.println("TTS消息结束: " + event);
            }

            @Override
            public void onMessageReplace(MessageReplaceEvent event) {
                System.out.println("消息替换: " + event);
            }

            @Override
            public void onAgentMessage(AgentMessageEvent event) {
                System.out.println("Agent消息: " + event);
            }

            @Override
            public void onAgentThought(AgentThoughtEvent event) {
                System.out.println("Agent思考: " + event);
            }

            @Override
            public void onWorkflowStarted(WorkflowStartedEvent event) {
                System.out.println("工作流开始: " + event);
            }

            @Override
            public void onNodeStarted(NodeStartedEvent event) {
                System.out.println("节点开始: " + event);
            }

            @Override
            public void onNodeFinished(NodeFinishedEvent event) {
                System.out.println("节点完成: " + event);
            }

            @Override
            public void onWorkflowFinished(WorkflowFinishedEvent event) {
                System.out.println("工作流完成: " + event);
            }

            @Override
            public void onError(ErrorEvent event) {
                System.err.println("错误: " + event.getMessage());
                latch.countDown();
            }

            @Override
            public void onException(Throwable throwable) {
                System.err.println("异常: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onPing(PingEvent event) {
                System.out.println("心跳: " + event);
            }
        });

        // 等待流式响应完成
        boolean completed = latch.await(30, TimeUnit.SECONDS);
        assertTrue(completed, "流式响应超时");

        // 验证响应
        assertFalse(responseBuilder.toString().isEmpty(), "响应不应为空");
        System.out.println("完整响应: " + responseBuilder.toString());
    }

    /**
     * 测试获取应用信息
     */
    @Test
    public void testGetAppInfo() throws Exception {
        AppInfoResponse appInfo = chatWorkflowClient.getAppInfo();

        // 验证响应
        assertNotNull(appInfo);
        assertNotNull(appInfo.getName(), "应用名称不应为空");
        System.out.println("应用名称: " + appInfo.getName());
        System.out.println("应用描述: " + appInfo.getDescription());
    }
}
