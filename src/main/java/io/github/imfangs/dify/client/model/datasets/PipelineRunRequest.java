package io.github.imfangs.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 运行完整知识库 Pipeline 的请求体
 * 对应 POST /datasets/{dataset_id}/pipeline/run
 * 支持 streaming（SSE）和 blocking（JSON）两种响应模式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PipelineRunRequest {

    /**
     * Pipeline 输入变量（在工作流中定义），无变量时传 {}
     */
    private Map<String, Object> inputs;

    /**
     * 数据源类型（local_file / online_document / website_crawl / online_drive）
     */
    @JsonProperty("datasource_type")
    private String datasourceType;

    /**
     * 需要处理的数据源列表。每项结构取决于 datasource_type：
     * - local_file: { reference: <upload_file_id>, name?: string }
     * - online_document: { workspace_id, page: {page_id, type, page_name?}, credential_id? }
     * - website_crawl: { url, title? }
     * - online_drive: { id, type: "file"|"folder", bucket?, name? }
     */
    @JsonProperty("datasource_info_list")
    private List<Map<String, Object>> datasourceInfoList;

    /**
     * 起始数据源节点 ID
     */
    @JsonProperty("start_node_id")
    private String startNodeId;

    /**
     * 是否运行已发布版本
     */
    @JsonProperty("is_published")
    private Boolean isPublished;

    /**
     * 响应模式：streaming 或 blocking
     */
    @JsonProperty("response_mode")
    private String responseMode;
}
