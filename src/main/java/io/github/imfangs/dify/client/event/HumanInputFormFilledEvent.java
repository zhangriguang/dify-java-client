package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 人工介入表单已提交事件
 * <p>
 * 接收人提交表单后触发，工作流将从暂停处继续执行。
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HumanInputFormFilledEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private HumanInputFormFilledData data;

    /**
     * 人工介入表单提交事件数据
     */
    @Data
    @NoArgsConstructor
    public static class HumanInputFormFilledData {

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
         * 使用提交内容渲染后的表单正文（表单原始 {@code form_content} 是未填写模板）
         */
        @JsonProperty("rendered_content")
        private String renderedContent;

        /**
         * 用户点击的操作 ID
         */
        @JsonProperty("action_id")
        private String actionId;

        /**
         * 用户点击的操作显示文本
         */
        @JsonProperty("action_text")
        private String actionText;

        /**
         * 用户提交的表单数据
         */
        @JsonProperty("submitted_data")
        private Map<String, Object> submittedData;
    }
}
