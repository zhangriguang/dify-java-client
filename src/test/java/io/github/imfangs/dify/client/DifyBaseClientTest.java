package io.github.imfangs.dify.client;

import io.github.imfangs.dify.client.config.DifyTestConfig;
import io.github.imfangs.dify.client.model.DifyConfig;
import io.github.imfangs.dify.client.model.chat.AppInfoResponse;
import io.github.imfangs.dify.client.model.chat.AppMetaResponse;
import io.github.imfangs.dify.client.model.chat.AppParametersResponse;
import io.github.imfangs.dify.client.model.file.FileUploadRequest;
import io.github.imfangs.dify.client.model.file.FileUploadResponse;
import okhttp3.MediaType;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Dify 基础客户端测试类
 * 注意：运行测试前，请确保已经正确配置了 dify-test-config.properties 文件
 */
public class DifyBaseClientTest {
    private static final String BASE_URL = DifyTestConfig.getBaseUrl();
    private static final String API_KEY = DifyTestConfig.getChatApiKey();
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
            if (!file.exists()) {
                System.out.println("文件不存在，跳过测试");
                return;
            }
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
            if (!file.exists()) {
                System.out.println("文件不存在，跳过测试");
                return;
            }
            FileUploadRequest request = FileUploadRequest.builder()
                    .user(USER_ID)
                    // 不传默认为application/octet-stream
                    .mediaType(MediaType.parse("text/plain"))
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
                    // 不传默认为application/octet-stream
                    .mediaType(MediaType.parse("text/plain"))
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
