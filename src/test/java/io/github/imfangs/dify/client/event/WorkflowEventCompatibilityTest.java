package io.github.imfangs.dify.client.event;

import io.github.imfangs.dify.client.util.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 纯单元测试：验证 Chatflow 工作流事件在不同版本字段结构下的兼容性
 */
public class WorkflowEventCompatibilityTest {

    @Test
    public void shouldDeserializeRootWorkflowIdIfProvided() {
        String json = "{"
                + "\"event\":\"workflow_started\","
                + "\"task_id\":\"t1\","
                + "\"workflow_run_id\":\"wr1\","
                + "\"workflow_id\":\"w_root\","
                + "\"conversation_id\":\"c1\","
                + "\"created_at\":1700000000,"
                + "\"data\":{"
                + "  \"id\":\"wr1\","
                + "  \"workflow_id\":\"w_data\","
                + "  \"sequence_number\":1,"
                + "  \"created_at\":1700000001"
                + "}"
                + "}";

        WorkflowStartedEvent event = JsonUtils.fromJson(json, WorkflowStartedEvent.class);

        assertNotNull(event);
        assertEquals("wr1", event.getWorkflowRunId());
        assertEquals("w_root", event.getWorkflowId(), "应能从事件根节点读取 workflow_id");
        assertNotNull(event.getData());
        assertEquals("w_data", event.getData().getWorkflowId(), "应能从 data.workflow_id 读取 workflow_id");
    }
}

