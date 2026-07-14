package io.github.imfangs.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 知识库 Pipeline 中已配置的数据源插件节点
 * 对应 GET /datasets/{dataset_id}/pipeline/datasource-plugins 列表元素
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatasourcePluginResponse {

    /**
     * 数据源节点 ID（可用作 run datasource node 的 node_id，或 pipeline/run 的 start_node_id）
     */
    @JsonProperty("node_id")
    private String nodeId;

    /**
     * 插件 ID
     */
    @JsonProperty("plugin_id")
    private String pluginId;

    /**
     * 提供方名称
     */
    @JsonProperty("provider_name")
    private String providerName;

    /**
     * 数据源类型（local_file / online_document / website_crawl / online_drive）
     */
    @JsonProperty("datasource_type")
    private String datasourceType;

    /**
     * 节点标题
     */
    private String title;

    /**
     * 该节点期望的用户输入变量描述
     */
    @JsonProperty("user_input_variables")
    private List<Map<String, Object>> userInputVariables;

    /**
     * 可用凭证列表
     */
    private List<CredentialInfo> credentials;

    /**
     * 数据源凭证摘要信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CredentialInfo {
        private String id;
        private String name;
        private String type;
        @JsonProperty("is_default")
        private Boolean isDefault;
    }
}
