package io.github.imfangs.dify.client;

import io.github.imfangs.dify.client.config.DifyTestConfig;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.DifyConfig;
import io.github.imfangs.dify.client.model.common.SimpleResponse;
import io.github.imfangs.dify.client.model.datasets.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Dify 知识库客户端测试类
 * 注意：运行测试前，请确保已经正确配置了 dify-test-config.properties 文件
 */
public class DifyDatasetsClientTest {
    private static final String BASE_URL = DifyTestConfig.getBaseUrl();
    private static final String API_KEY = DifyTestConfig.getDatasetsApiKey();
    private static final String USER_ID = "test-user-" + System.currentTimeMillis();

    private DifyDatasetsClient datasetsClient;
    private String testDatasetId;
    private String testDocumentId;

    @BeforeEach
    public void setUp() {
        // 创建客户端
        datasetsClient = DifyClientFactory.createDatasetsClient(BASE_URL, API_KEY);

        // 创建测试知识库
        try {
            CreateDatasetRequest createRequest = CreateDatasetRequest.builder()
                    .name("测试知识库-" + System.currentTimeMillis())
                    .description("这是一个测试知识库")
                    .indexingTechnique("high_quality")
                    .permission("only_me")
                    .provider("vendor")
                    .build();

            DatasetResponse response = datasetsClient.createDataset(createRequest);
            testDatasetId = response.getId();
            System.out.println("创建测试知识库成功，ID: " + testDatasetId);
        } catch (Exception e) {
            System.err.println("创建测试知识库失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        // 清理测试数据
        if (testDatasetId != null) {
            try {
                datasetsClient.deleteDataset(testDatasetId);
                System.out.println("删除测试知识库成功，ID: " + testDatasetId);
            } catch (Exception e) {
                System.err.println("删除测试知识库失败: " + e.getMessage());
            }
        }
    }

    /**
     * 测试创建知识库
     */
    @Test
    public void testCreateDataset() throws IOException, DifyApiException {
        // 创建知识库请求
        CreateDatasetRequest request = CreateDatasetRequest.builder()
                .name("测试知识库-" + System.currentTimeMillis())
                .description("这是一个通过测试创建的知识库")
                .indexingTechnique("high_quality")
                .permission("only_me")
                .provider("vendor")
                .build();

        // 发送请求
        DatasetResponse response = datasetsClient.createDataset(request);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getDescription(), response.getDescription());

        // 清理测试数据
        datasetsClient.deleteDataset(response.getId());
    }

    /**
     * 测试获取知识库列表
     */
    @Test
    public void testGetDatasets() throws IOException, DifyApiException {
        // 获取知识库列表
        DatasetListResponse response = datasetsClient.getDatasets(1, 10);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getData());
        assertNotNull(response.getTotal());

        // 打印知识库列表
        System.out.println("知识库总数: " + response.getTotal());
        response.getData().forEach(dataset -> {
            System.out.println("知识库ID: " + dataset.getId() + ", 名称: " + dataset.getName());
        });
    }

    /**
     * 测试通过文本创建文档
     */
    @Test
    public void testCreateDocumentByText() throws IOException, DifyApiException {
        // 跳过测试如果没有测试知识库
        if (testDatasetId == null) {
            System.out.println("跳过测试，因为没有测试知识库");
            return;
        }

        RetrievalModel retrievalModel = new RetrievalModel();
        retrievalModel.setSearchMethod("hybrid_search");
        retrievalModel.setRerankingEnable(false);
        retrievalModel.setTopK(2);
        retrievalModel.setScoreThresholdEnabled(false);

        // 创建文档请求
        CreateDocumentByTextRequest request = CreateDocumentByTextRequest.builder()
                .name("测试文档-" + System.currentTimeMillis())
                .text("这是一个测试文档的内容。\n这是第二行内容。\n这是第三行内容。")
                .indexingTechnique("economy")
                .docForm("text_model")
                // 1.1.3 invalid_param (400) - Must not be null! 【doc_language】
                .docLanguage("Chinese")
                // 1.1.3 invalid_param (400) - Must not be null! 【retrieval_model】
                .retrievalModel(retrievalModel)
                // 没有这里的设置，会500报错，服务器内部错误
                .processRule(ProcessRule.builder().mode("automatic").build())
                .build();

        // 发送请求
        DocumentResponse response = datasetsClient.createDocumentByText(testDatasetId, request);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getDocument());
        assertNotNull(response.getDocument().getId());
        assertEquals(request.getName(), response.getDocument().getName());

        // 保存文档ID用于后续测试
        testDocumentId = response.getDocument().getId();
        System.out.println("创建测试文档成功，ID: " + testDocumentId);
    }

    /**
     * 测试获取文档列表
     */
    @Test
    public void testGetDocuments() throws IOException, DifyApiException {
        // 跳过测试如果没有测试知识库
        if (testDatasetId == null) {
            System.out.println("跳过测试，因为没有测试知识库");
            return;
        }

        // 获取文档列表
        DocumentListResponse response = datasetsClient.getDocuments(testDatasetId, null, 1, 10);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getData());
        assertNotNull(response.getTotal());

        // 打印文档列表
        System.out.println("文档总数: " + response.getTotal());
        response.getData().forEach(document -> {
            System.out.println("文档ID: " + document.getId() + ", 名称: " + document.getName());
        });
    }

    /**
     * 测试删除文档
     */
    @Test
    public void testDeleteDocument() throws IOException, DifyApiException {
        // 跳过测试如果没有测试知识库或文档
        if (testDatasetId == null || testDocumentId == null) {
            System.out.println("跳过测试，因为没有测试知识库或文档");
            return;
        }

        // 删除文档
        SimpleResponse response = datasetsClient.deleteDocument(testDatasetId, testDocumentId);

        // 验证响应
        assertNotNull(response);
        assertEquals("success", response.getResult());

        // 清除文档ID
        testDocumentId = null;
    }

    /**
     * 测试检索知识库
     */
    @Test
    public void testRetrieveDataset() throws IOException, DifyApiException {
        // 跳过测试如果没有测试知识库
        if (testDatasetId == null) {
            System.out.println("跳过测试，因为没有测试知识库");
            return;
        }

        // 先创建一个文档
        if (testDocumentId == null) {

            RetrievalModel retrievalModel = new RetrievalModel();
            retrievalModel.setSearchMethod("hybrid_search");
            retrievalModel.setRerankingEnable(false);
            retrievalModel.setTopK(2);
            retrievalModel.setScoreThresholdEnabled(false);

            CreateDocumentByTextRequest createRequest = CreateDocumentByTextRequest.builder()
                    .name("检索测试文档-" + System.currentTimeMillis())
                    .text("人工智能（Artificial Intelligence，简称AI）是计算机科学的一个分支，它企图了解智能的实质，并生产出一种新的能以人类智能相似的方式做出反应的智能机器。人工智能是对人的意识、思维的信息过程的模拟。人工智能不是人的智能，但能像人那样思考、也可能超过人的智能。")
                    .indexingTechnique("high_quality")
                    .docForm("text_model")
                    // 1.1.3 invalid_param (400) - Must not be null! 【doc_language】
                    .docLanguage("Chinese")
                    // 1.1.3 invalid_param (400) - Must not be null! 【retrieval_model】
                    .retrievalModel(retrievalModel)
                    // 没有这里的设置，会500报错，服务器内部错误
                    .processRule(ProcessRule.builder().mode("automatic").build())
                    .build();

            DocumentResponse docResponse = datasetsClient.createDocumentByText(testDatasetId, createRequest);
            testDocumentId = docResponse.getDocument().getId();

            // 等待索引完成
            try {
                System.out.println("等待文档索引完成...");
                Thread.sleep(5000); // 等待5秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 创建检索请求
        RetrievalModel retrievalModel = new RetrievalModel();
        retrievalModel.setTopK(3);
        retrievalModel.setScoreThreshold(0.5f);

        RetrieveRequest request = RetrieveRequest.builder()
                .query("什么是人工智能")
                .retrievalModel(retrievalModel)
                .build();

        // 发送请求
        RetrieveResponse response = datasetsClient.retrieveDataset(testDatasetId, request);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getQuery());
        assertNotNull(response.getRecords());

        // 打印检索结果
        System.out.println("检索查询: " + response.getQuery().getContent());
        System.out.println("检索结果数量: " + response.getRecords().size());
        response.getRecords().forEach(record -> {
            System.out.println("分数: " + record.getScore());
            System.out.println("内容: " + record.getSegment().getContent());
            System.out.println("文档: " + record.getSegment().getDocument().getName());
        });
    }

    /**
     * 测试自定义配置
     */
    @Test
    public void testCustomConfig() throws IOException, DifyApiException {
        // 创建自定义配置
        DifyConfig config = DifyConfig.builder()
                .baseUrl(BASE_URL)
                .apiKey(API_KEY)
                .connectTimeout(5000)
                .readTimeout(60000)
                .writeTimeout(30000)
                .build();

        // 使用自定义配置创建客户端
        DifyDatasetsClient customClient = DifyClientFactory.createDatasetsClient(config);

        // 获取知识库列表
        DatasetListResponse response = customClient.getDatasets(1, 5);

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getData());
    }
}
