package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 工作流事件的基类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseWorkflowEvent extends BaseEvent {

    /**
     * 工作流执行ID
     */
    @JsonProperty("workflow_run_id")
    private String workflowRunId;

    /**
     * 工作流 ID（兼容部分版本在事件根节点返回 workflow_id 的情况）
     *
     * <p>标准事件结构中，workflow_id 通常位于 data.workflow_id；但为适配不同版本的返回结构，
     * 这里在根节点也增加一份映射。</p>
     */
    @JsonProperty("workflow_id")
    private String workflowId;

    /**
     * 会话ID（对话型应用特有）
     */
    @JsonProperty("conversation_id")
    private String conversationId;
}
