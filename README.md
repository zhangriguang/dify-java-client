# Dify Java Client

[![Maven Central](https://img.shields.io/maven-central/v/io.github.imfangs/dify-java-client.svg)](https://search.maven.org/search?q=g:io.github.imfangs%20AND%20a:dify-java-client)
[![License](https://img.shields.io/github/license/imfangs/dify-java-client)](https://github.com/imfangs/dify-java-client/blob/main/LICENSE)

[English](README_EN.md) | 简体中文

Dify Java Client 是一个用于与 [Dify](https://dify.ai) 平台进行交互的 Java 客户端库。它提供了对 Dify 应用 API 和知识库 API 的完整支持，让 Java 开发者能够轻松地将 Dify 的生成式 AI 能力集成到自己的应用中。

## 什么是 Dify？

[Dify](https://dify.ai) 是一个开源的大语言模型(LLM)应用开发平台。它融合了后端即服务（Backend as Service）和 LLMOps 的理念，使开发者可以快速搭建生产级的生成式 AI 应用。Dify 涵盖了构建生成式 AI 原生应用所需的核心技术栈，包括：

- RAG Pipeline：安全构建私有数据与大型语言模型之间的数据通道，并提供高可靠的索引和检索工具
- Prompt IDE：为提示词工程师精心设计，友好易用的提示词开发工具
- LLM Agent：定制化 Agent，自主调用系列工具完成复杂任务
- Workflow：编排 AI 工作流，使其输出更稳定可控
- 知识库管理：创建和管理知识库，支持多种数据源和检索方式

## 功能特性

Dify Java Client 支持以下功能：

### 应用 API 调用

- **文本生成应用**：通过 `DifyCompletionClient` 调用文本生成型应用
- **聊天助手**：通过 `DifyChatClient` 调用对话型应用
- **Agent**：支持 Agent 模式的对话应用调用
- **对话流（Chatflow）**：通过 `DifyChatflowClient` 调用工作流编排对话型应用
- **工作流（Workflow）**：通过 `DifyWorkflowClient` 调用工作流应用

### 知识库 API 调用

- 创建和管理知识库
- 上传和管理文档
- 管理文档分段
- 检索知识库内容

## 安装

### Maven

```xml
<dependency>
    <groupId>io.github.imfangs</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.imfangs:dify-java-client:1.0.0'
```

## 快速开始

### 创建客户端

```java
import io.github.imfangs.dify.client.DifyClient;
import io.github.imfangs.dify.client.DifyClientFactory;

// 创建完整的 Dify 客户端
DifyClient client = DifyClientFactory.createClient("https://api.dify.ai", "your-api-key");

// 创建特定类型的客户端
DifyChatClient chatClient = DifyClientFactory.createChatClient("https://api.dify.ai", "your-api-key");
DifyCompletionClient completionClient = DifyClientFactory.createCompletionClient("https://api.dify.ai", "your-api-key");
DifyChatflowClient chatflowClient = DifyClientFactory.createChatWorkflowClient("https://api.dify.ai", "your-api-key");
DifyWorkflowClient workflowClient = DifyClientFactory.createWorkflowClient("https://api.dify.ai", "your-api-key");
DifyDatasetsClient datasetsClient = DifyClientFactory.createDatasetsClient("https://api.dify.ai", "your-api-key");
```

### 使用配置创建客户端

```java
import io.github.imfangs.dify.client.DifyClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.model.DifyConfig;

// 创建配置
DifyConfig config = new DifyConfig();
config.setBaseUrl("https://api.dify.ai");
config.setApiKey("your-api-key");
config.setConnectTimeout(30);
config.setReadTimeout(30);
config.setWriteTimeout(30);

// 使用配置创建客户端
DifyClient client = DifyClientFactory.createClient(config);
```

### 聊天应用示例

```java
import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.callback.ChatStreamCallback;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.ChatMessageResponse;

// 创建聊天客户端
DifyChatClient chatClient = DifyClientFactory.createChatClient("https://api.dify.ai", "your-api-key");

// 创建聊天消息
ChatMessage message = new ChatMessage();
message.setQuery("你好，请介绍一下自己");
message.setUser("user-123");

// 阻塞模式调用
ChatMessageResponse response = chatClient.sendChatMessage(message);
System.out.println("回复: " + response.getAnswer());

// 流式模式调用
chatClient.sendChatMessageStream(message, new ChatStreamCallback() {
    @Override
    public void onMessage(String message) {
        System.out.println("收到消息: " + message);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("发生错误: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("流式回复完成");
    }
});
```

### 文本生成应用示例

```java
import io.github.imfangs.dify.client.DifyCompletionClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.callback.CompletionStreamCallback;
import io.github.imfangs.dify.client.model.completion.CompletionRequest;
import io.github.imfangs.dify.client.model.completion.CompletionResponse;

// 创建文本生成客户端
DifyCompletionClient completionClient = DifyClientFactory.createCompletionClient("https://api.dify.ai", "your-api-key");

// 创建文本生成请求
CompletionRequest request = new CompletionRequest();
request.getInputs().put("text", "写一篇关于人工智能的短文");
request.setUser("user-123");

// 阻塞模式调用
CompletionResponse response = completionClient.sendCompletionMessage(request);
System.out.println("生成文本: " + response.getText());

// 流式模式调用
completionClient.sendCompletionMessageStream(request, new CompletionStreamCallback() {
    @Override
    public void onMessage(String message) {
        System.out.println("收到消息: " + message);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("发生错误: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("流式生成完成");
    }
});
```

### 知识库操作示例

```java
import io.github.imfangs.dify.client.DifyDatasetsClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.model.datasets.CreateDatasetRequest;
import io.github.imfangs.dify.client.model.datasets.DatasetResponse;
import io.github.imfangs.dify.client.model.datasets.DatasetListResponse;

// 创建知识库客户端
DifyDatasetsClient datasetsClient = DifyClientFactory.createDatasetsClient("https://api.dify.ai", "your-api-key");

// 创建知识库
CreateDatasetRequest createRequest = new CreateDatasetRequest();
createRequest.setName("测试知识库");
createRequest.setDescription("这是一个测试知识库");
DatasetResponse dataset = datasetsClient.createDataset(createRequest);
System.out.println("创建的知识库ID: " + dataset.getId());

// 获取知识库列表
DatasetListResponse datasetList = datasetsClient.getDatasets(1, 10);
System.out.println("知识库总数: " + datasetList.getTotal());
datasetList.getData().forEach(ds -> {
    System.out.println("知识库名称: " + ds.getName());
});
```

## 更多示例

更多使用示例请参考 [示例代码](https://github.com/imfangs/dify-java-client/tree/main/src/test/java/io/github/imfangs/dify/client)。

## 贡献

欢迎贡献代码、报告问题或提出改进建议。请通过 GitHub Issues 或 Pull Requests 参与项目开发。

## 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。

## 相关链接

- [Dify 官网](https://dify.ai)
- [Dify 文档](https://docs.dify.ai)
- [Dify GitHub](https://github.com/langgenius/dify)
