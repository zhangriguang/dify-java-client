# Dify Java Client

[![Maven Central](https://img.shields.io/maven-central/v/io.github.imfangs/dify-java-client.svg)](https://search.maven.org/search?q=g:io.github.imfangs%20AND%20a:dify-java-client)
[![License](https://img.shields.io/github/license/imfangs/dify-java-client)](https://github.com/imfangs/dify-java-client/blob/main/LICENSE)

English | [简体中文](README.md)

Dify Java Client is a Java client library for interacting with the [Dify](https://dify.ai) platform. It provides complete support for Dify Application APIs and Knowledge Base APIs, enabling Java developers to easily integrate Dify's generative AI capabilities into their applications.

## What is Dify?

[Dify](https://dify.ai) is an open-source Large Language Model (LLM) application development platform. It combines the concepts of Backend as a Service and LLMOps, allowing developers to quickly build production-ready generative AI applications. Dify covers the core technology stack needed to build native generative AI applications, including:

- RAG Pipeline: Securely build data channels between private data and large language models, providing highly reliable indexing and retrieval tools
- Prompt IDE: Thoughtfully designed, user-friendly prompt development tools for prompt engineers
- LLM Agent: Customized agents that autonomously call a series of tools to complete complex tasks
- Workflow: Orchestrate AI workflows for more stable and controllable outputs
- Knowledge Base Management: Create and manage knowledge bases, supporting multiple data sources and retrieval methods

## Features

Dify Java Client supports the following features:

### Application API Calls

- **Text Generation Applications**: Call text generation applications via `DifyCompletionClient`
- **Chat Assistants**: Call conversational applications via `DifyChatClient`
- **Agent**: Support for Agent mode in conversational applications
- **Chatflow**: Call workflow-orchestrated conversational applications via `DifyChatflowClient`
- **Workflow**: Call workflow applications via `DifyWorkflowClient`

### Knowledge Base API Calls

- Create and manage knowledge bases
- Upload and manage documents
- Manage document segments
- Retrieve knowledge base content

## Installation

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

## Quick Start

### Creating a Client

```java
import io.github.imfangs.dify.client.DifyClient;
import io.github.imfangs.dify.client.DifyClientFactory;

// Create a complete Dify client
DifyClient client = DifyClientFactory.createClient("https://api.dify.ai", "your-api-key");

// Create specific types of clients
DifyChatClient chatClient = DifyClientFactory.createChatClient("https://api.dify.ai", "your-api-key");
DifyCompletionClient completionClient = DifyClientFactory.createCompletionClient("https://api.dify.ai", "your-api-key");
DifyChatflowClient chatflowClient = DifyClientFactory.createChatWorkflowClient("https://api.dify.ai", "your-api-key");
DifyWorkflowClient workflowClient = DifyClientFactory.createWorkflowClient("https://api.dify.ai", "your-api-key");
DifyDatasetsClient datasetsClient = DifyClientFactory.createDatasetsClient("https://api.dify.ai", "your-api-key");
```

### Creating a Client with Configuration

```java
import io.github.imfangs.dify.client.DifyClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.model.DifyConfig;

// Create configuration
DifyConfig config = new DifyConfig();
config.setBaseUrl("https://api.dify.ai");
config.setApiKey("your-api-key");
config.setConnectTimeout(30);
config.setReadTimeout(30);
config.setWriteTimeout(30);

// Create client with configuration
DifyClient client = DifyClientFactory.createClient(config);
```

### Chat Application Example

```java
import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.callback.ChatStreamCallback;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.ChatMessageResponse;

// Create chat client
DifyChatClient chatClient = DifyClientFactory.createChatClient("https://api.dify.ai", "your-api-key");

// Create chat message
ChatMessage message = new ChatMessage();
message.setQuery("Hello, please introduce yourself");
message.setUser("user-123");

// Blocking mode call
ChatMessageResponse response = chatClient.sendChatMessage(message);
System.out.println("Reply: " + response.getAnswer());

// Streaming mode call
chatClient.sendChatMessageStream(message, new ChatStreamCallback() {
    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Stream reply completed");
    }
});
```

### Text Generation Application Example

```java
import io.github.imfangs.dify.client.DifyCompletionClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.callback.CompletionStreamCallback;
import io.github.imfangs.dify.client.model.completion.CompletionRequest;
import io.github.imfangs.dify.client.model.completion.CompletionResponse;

// Create text generation client
DifyCompletionClient completionClient = DifyClientFactory.createCompletionClient("https://api.dify.ai", "your-api-key");

// Create text generation request
CompletionRequest request = new CompletionRequest();
request.getInputs().put("text", "Write a short article about artificial intelligence");
request.setUser("user-123");

// Blocking mode call
CompletionResponse response = completionClient.sendCompletionMessage(request);
System.out.println("Generated text: " + response.getText());

// Streaming mode call
completionClient.sendCompletionMessageStream(request, new CompletionStreamCallback() {
    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Stream generation completed");
    }
});
```

### Knowledge Base Operations Example

```java
import io.github.imfangs.dify.client.DifyDatasetsClient;
import io.github.imfangs.dify.client.DifyClientFactory;
import io.github.imfangs.dify.client.model.datasets.CreateDatasetRequest;
import io.github.imfangs.dify.client.model.datasets.DatasetResponse;
import io.github.imfangs.dify.client.model.datasets.DatasetListResponse;

// Create knowledge base client
DifyDatasetsClient datasetsClient = DifyClientFactory.createDatasetsClient("https://api.dify.ai", "your-api-key");

// Create knowledge base
CreateDatasetRequest createRequest = new CreateDatasetRequest();
createRequest.setName("Test Knowledge Base");
createRequest.setDescription("This is a test knowledge base");
DatasetResponse dataset = datasetsClient.createDataset(createRequest);
System.out.println("Created knowledge base ID: " + dataset.getId());

// Get knowledge base list
DatasetListResponse datasetList = datasetsClient.getDatasets(1, 10);
System.out.println("Total knowledge bases: " + datasetList.getTotal());
datasetList.getData().forEach(ds -> {
    System.out.println("Knowledge base name: " + ds.getName());
});
```

## More Examples

For more usage examples, please refer to the [example code](https://github.com/imfangs/dify-java-client/tree/main/src/test/java/io/github/imfangs/dify/client).

## Contributing

Contributions of code, issue reports, or improvement suggestions are welcome. Please participate in project development through GitHub Issues or Pull Requests.

## License

This project is licensed under the [Apache License 2.0](LICENSE).

## Related Links

- [Dify Website](https://dify.ai)
- [Dify Documentation](https://docs.dify.ai)
- [Dify GitHub](https://github.com/langgenius/dify)
