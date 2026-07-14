## Changes

- Add Service API for **Knowledge Pipeline (RAG Pipeline)** — 4 new endpoints on `DifyDatasetsClient`
  - `List<DatasourcePluginResponse> listPipelineDatasourcePlugins(String datasetId, Boolean isPublished)` — `GET /datasets/{id}/pipeline/datasource-plugins`
  - `void runPipelineDatasourceNodeStream(...)` — `POST /datasets/{id}/pipeline/datasource/nodes/{node_id}/run`, SSE stream of node execution events
  - `Map<String,Object> runPipeline(String datasetId, PipelineRunRequest request)` — `POST /datasets/{id}/pipeline/run` in **blocking** mode
  - `void runPipelineStream(String datasetId, PipelineRunRequest request, WorkflowStreamCallback callback)` — same route in **streaming** mode
  - `PipelineFileUploadResponse uploadPipelineFile(File file)` / `uploadPipelineFile(InputStream, String, String)` — `POST /datasets/pipeline/file-upload`
- New models: `DatasourcePluginResponse` (with nested `CredentialInfo`), `DatasourceNodeRunRequest`, `PipelineRunRequest`, `PipelineFileUploadResponse`
- **Internal refactor**: promoted streaming (SSE) helpers (`executeStreamRequest`, `executeGetStreamRequest`, `processStreamLine`, `LineProcessor`, `EventProcessor`) from `DefaultDifyClient` to `AbstractDifyClient`. Non-breaking; enables `DefaultDifyDatasetsClient` to consume SSE streams and simplifies future dataset streaming endpoints

## Installation

```xml
<dependency>
    <groupId>io.github.imfangs</groupId>
    <artifactId>dify-java-client</artifactId>
    <version>1.6.0</version>
</dependency>
```
