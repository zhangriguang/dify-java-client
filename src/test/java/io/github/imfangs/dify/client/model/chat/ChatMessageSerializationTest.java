package io.github.imfangs.dify.client.model.chat;

import io.github.imfangs.dify.client.util.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 纯单元测试：验证 ChatMessage 的 JSON 序列化行为
 */
public class ChatMessageSerializationTest {

    @Test
    public void shouldSerializeWorkflowIdAsWorkflowIdSnakeCaseWhenProvided() {
        ChatMessage message = ChatMessage.builder()
                .query("hi")
                .user("u1")
                .workflowId("w1")
                .build();

        String json = JsonUtils.toJson(message);

        assertTrue(json.contains("\"workflow_id\":\"w1\""), "应序列化为 workflow_id");
        assertFalse(json.contains("workflowId"), "不应出现驼峰字段名 workflowId");
    }

    @Test
    public void shouldNotSerializeWorkflowIdWhenNull() {
        ChatMessage message = ChatMessage.builder()
                .query("hi")
                .user("u1")
                .build();

        String json = JsonUtils.toJson(message);

        assertFalse(json.contains("workflow_id"), "workflowId 未设置时不应出现在请求体中");
    }
}

