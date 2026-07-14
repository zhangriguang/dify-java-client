package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 人工介入表单超时事件
 * <p>
 * 表单在过期时间前未被提交时触发，工作流按超时兜底分支（若配置）继续执行。
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HumanInputFormTimeoutEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private HumanInputFormTimeoutData data;

    /**
     * 人工介入表单超时事件数据
     */
    @Data
    @NoArgsConstructor
    public static class HumanInputFormTimeoutData {

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
         * 表单过期时间（Unix 秒级时间戳）
         */
        @JsonProperty("expiration_time")
        private Long expirationTime;
    }
}
