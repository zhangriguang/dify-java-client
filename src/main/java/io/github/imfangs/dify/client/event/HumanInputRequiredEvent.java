package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 人工介入表单等待事件
 * <p>
 * 工作流运行到人工介入节点时触发，随后紧跟 {@code workflow_paused} 事件流结束。
 * 使用 {@code form_token} 通过 Human Input API 获取或提交表单以恢复运行。
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HumanInputRequiredEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private HumanInputRequiredData data;

    /**
     * 人工介入表单事件数据
     */
    @Data
    @NoArgsConstructor
    public static class HumanInputRequiredData {

        /**
         * 表单 ID
         */
        @JsonProperty("form_id")
        private String formId;

        /**
         * 节点 ID
         */
        @JsonProperty("node_id")
        private String nodeId;

        /**
         * 节点标题
         */
        @JsonProperty("node_title")
        private String nodeTitle;

        /**
         * 表单说明内容（未填写模板）
         */
        @JsonProperty("form_content")
        private String formContent;

        /**
         * 表单输入项配置
         */
        @JsonProperty("inputs")
        private List<Map<String, Object>> inputs;

        /**
         * 表单操作按钮配置
         */
        @JsonProperty("actions")
        private List<Map<String, Object>> actions;

        /**
         * 是否在 UI 中展示
         */
        @JsonProperty("display_in_ui")
        private Boolean displayInUi;

        /**
         * 表单访问令牌，用于获取/提交表单
         */
        @JsonProperty("form_token")
        private String formToken;

        /**
         * 审批人渠道列表
         */
        @JsonProperty("approval_channels")
        private List<String> approvalChannels;

        /**
         * 输入项默认值（已解析变量后的结果）
         */
        @JsonProperty("resolved_default_values")
        private Map<String, Object> resolvedDefaultValues;

        /**
         * 表单过期时间（Unix 秒级时间戳）
         */
        @JsonProperty("expiration_time")
        private Long expirationTime;
    }
}
