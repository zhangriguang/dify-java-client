package io.github.imfangs.dify.client;

import io.github.imfangs.dify.client.model.DifyConfig;
import io.github.imfangs.dify.client.model.chat.AppInfoResponse;
import io.github.imfangs.dify.client.model.chat.AppMetaResponse;
import io.github.imfangs.dify.client.model.chat.AppParametersResponse;
import io.github.imfangs.dify.client.model.file.FileUploadRequest;
import io.github.imfangs.dify.client.model.file.FileUploadResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Dify 基础客户端测试类
 * 注意：这只是示例代码，实际运行需要替换为有效的 API 密钥和 URL
 */
public class DifyBaseClientTest {
    // 替换为实际的 API 密钥和 URL
    private static final String API_KEY = "your-api-key";
    private static final String BASE_URL = "https://api.dify.ai/v1";
    private static final String USER_ID = "test-user-" + System.currentTimeMillis();

    /**
     * 测试获取应用信息
     */
    @Test
    public void testGetAppInfo() throws Exception {
        try (DifyClient client = DifyClientFactory.createClient(BASE_URL, API_KEY)) {
            AppInfoResponse appInfo = client.getAppInfo();
            System.out.println("应用信息: " + appInfo);
        }
    }

    /**
     * 测试获取应用参数
     */
    @Test
    public void testGetAppParameters() throws Exception {
        try (DifyClient client = DifyClientFactory.createClient(BASE_URL, API_KEY)) {
            AppParametersResponse parameters = client.getAppParameters();
            System.out.println("应用参数: " + parameters);
        }
    }

    /**
     * 测试获取应用元数据
     */
    @Test
    public void testGetAppMeta() throws Exception {
        try (DifyClient client = DifyClientFactory.createClient(BASE_URL, API_KEY)) {
            AppMetaResponse meta = client.getAppMeta();
            System.out.println("应用元数据: " + meta);
        }
    }

    /**
     * 测试文件上传 - 使用File对象
     */
    @Test
    public void testUploadFile() throws Exception {
        try (DifyClient client = DifyClientFactory.createClient(BASE_URL, API_KEY)) {
            File file = new File("path/to/your/file.txt");
            FileUploadResponse response = client.uploadFile(file, USER_ID);
            System.out.println("文件上传响应: " + response);
        }
    }

    /**
     * 测试文件上传 - 使用FileUploadRequest和File对象
     */
    @Test
    public void testUploadFileWithRequest() throws Exception {
        try (DifyClient client = DifyClientFactory.createClient(BASE_URL, API_KEY)) {
            File file = new File("path/to/your/file.txt");
            FileUploadRequest request = FileUploadRequest.builder()
                    .user(USER_ID)
                    .build();

            FileUploadResponse response = client.uploadFile(request, file);
            System.out.println("文件上传响应: " + response);
        }
    }

    /**
     * 测试文件上传 - 使用InputStream
     */
    @Test
    public void testUploadFileWithInputStream() throws Exception {
        try (DifyClient client = DifyClientFactory.createClient(BASE_URL, API_KEY)) {
            String content = "这是测试文件内容";
            InputStream inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

            FileUploadRequest request = FileUploadRequest.builder()
                    .user(USER_ID)
                    .build();

            FileUploadResponse response = client.uploadFile(request, inputStream, "test-file.txt");
            System.out.println("文件上传响应: " + response);
        }
    }

    /**
     * 测试自定义配置
     */
    @Test
    public void testCustomConfig() throws Exception {
        DifyConfig config = DifyConfig.builder()
                .baseUrl(BASE_URL)
                .apiKey(API_KEY)
                .connectTimeout(5000)
                .readTimeout(60000)
                .writeTimeout(30000)
                .build();

        try (DifyClient client = DifyClientFactory.createClient(config)) {
            AppInfoResponse appInfo = client.getAppInfo();
            System.out.println("应用信息: " + appInfo);
        }
    }
}
