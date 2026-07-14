package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 工作流暂停事件
 * <p>
 * 工作流因人工介入等原因暂停时触发，流在此事件后结束；运行恢复后通过独立的 stream 继续。
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkflowPausedEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private WorkflowPausedData data;

    /**
     * 工作流暂停事件数据
     */
    @Data
    @NoArgsConstructor
    public static class WorkflowPausedData {

        /**
         * 工作流运行 ID
         */
        @JsonProperty("workflow_run_id")
        private String workflowRunId;

        /**
         * 被暂停的节点 ID 列表
         */
        @JsonProperty("paused_nodes")
        private List<String> pausedNodes;

        /**
         * 已产出的中间 outputs
         */
        @JsonProperty("outputs")
        private Map<String, Object> outputs;

        /**
         * 暂停原因列表（每个 reason 通过 {@code TYPE} 字段区分类型，例如 {@code human_input_required}）
         */
        @JsonProperty("reasons")
        private List<Map<String, Object>> reasons;

        /**
         * 状态（例如 {@code paused}）
         */
        @JsonProperty("status")
        private String status;

        /**
         * 创建时间（Unix 秒级时间戳）
         */
        @JsonProperty("created_at")
        private Long createdAt;

        /**
         * 已消耗时间（秒）
         */
        @JsonProperty("elapsed_time")
        private Double elapsedTime;

        /**
         * 已消耗 Token 数
         */
        @JsonProperty("total_tokens")
        private Integer totalTokens;

        /**
         * 已执行步骤数
         */
        @JsonProperty("total_steps")
        private Integer totalSteps;
    }
}
