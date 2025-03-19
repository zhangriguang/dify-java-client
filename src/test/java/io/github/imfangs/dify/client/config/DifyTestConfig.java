package io.github.imfangs.dify.client.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Dify测试配置加载器
 * 用于加载测试所需的API密钥和基础URL配置
 */
public class DifyTestConfig {
    private static final Properties properties = new Properties();
    private static boolean initialized = false;

    /**
     * 初始化配置
     * 从 dify-test-config.properties 文件中加载配置
     */
    private static synchronized void init() {
        if (initialized) {
            return;
        }

        try (InputStream input = DifyTestConfig.class.getClassLoader()
                .getResourceAsStream("dify-test-config.properties")) {
            if (input == null) {
                throw new RuntimeException("无法找到 dify-test-config.properties 文件。请复制 dify-test-config.template.properties 到 dify-test-config.properties 并填写你的API密钥。");
            }
            properties.load(input);
            initialized = true;
        } catch (IOException e) {
            throw new RuntimeException("加载 dify-test-config.properties 文件失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取基础URL
     */
    public static String getBaseUrl() {
        init();
        return properties.getProperty("dify.base.url");
    }

    /**
     * 获取聊天应用的API密钥
     */
    public static String getChatApiKey() {
        init();
        return properties.getProperty("dify.chat.api.key");
    }

    /**
     * 获取文本生成应用的API密钥
     */
    public static String getCompletionApiKey() {
        init();
        return properties.getProperty("dify.completion.api.key");
    }

    /**
     * 获取工作流应用的API密钥
     */
    public static String getWorkflowApiKey() {
        init();
        return properties.getProperty("dify.workflow.api.key");
    }

    /**
     * 获取工作流编排对话应用的API密钥
     */
    public static String getChatflowApiKey() {
        init();
        return properties.getProperty("dify.chatflow.api.key");
    }

    /**
     * 获取知识库应用的API密钥
     */
    public static String getDatasetsApiKey() {
        init();
        return properties.getProperty("dify.datasets.api.key");
    }
} 