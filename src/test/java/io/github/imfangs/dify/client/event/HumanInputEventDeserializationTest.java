package io.github.imfangs.dify.client.event;

import io.github.imfangs.dify.client.util.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 验证 Dify 1.14.2+ 引入的人工介入 (Human Input) 相关事件反序列化
 */
public class HumanInputEventDeserializationTest {

    @Test
    public void shouldDeserializeHumanInputRequiredEvent() {
        String json = "{"
                + "\"event\":\"human_input_required\","
                + "\"task_id\":\"t1\","
                + "\"workflow_run_id\":\"wr1\","
                + "\"data\":{"
                + "  \"form_id\":\"f1\","
                + "  \"form_token\":\"tok_abc\","
                + "  \"node_id\":\"approval_node\","
                + "  \"node_title\":\"Approval\","
                + "  \"form_content\":\"Please review.\","
                + "  \"inputs\":[{\"type\":\"paragraph\",\"output_variable_name\":\"comment\",\"default\":null}],"
                + "  \"actions\":[{\"id\":\"approve\",\"title\":\"Approve\",\"button_style\":\"primary\"}],"
                + "  \"display_in_ui\":false,"
                + "  \"resolved_default_values\":{\"comment\":\"\"},"
                + "  \"expiration_time\":1705494029"
                + "}"
                + "}";

        HumanInputRequiredEvent event = JsonUtils.fromJson(json, HumanInputRequiredEvent.class);

        assertNotNull(event);
        assertEquals("wr1", event.getWorkflowRunId());
        assertNotNull(event.getData());
        assertEquals("f1", event.getData().getFormId());
        assertEquals("tok_abc", event.getData().getFormToken());
        assertEquals("approval_node", event.getData().getNodeId());
        assertEquals(Long.valueOf(1705494029L), event.getData().getExpirationTime());
        assertEquals(1, event.getData().getInputs().size());
        assertEquals(1, event.getData().getActions().size());
    }

    @Test
    public void shouldDeserializeWorkflowPausedEvent() {
        String json = "{"
                + "\"event\":\"workflow_paused\","
                + "\"task_id\":\"t1\","
                + "\"workflow_run_id\":\"wr1\","
                + "\"data\":{"
                + "  \"workflow_run_id\":\"wr1\","
                + "  \"paused_nodes\":[\"approval_node\"],"
                + "  \"outputs\":{},"
                + "  \"reasons\":[{\"TYPE\":\"human_input_required\",\"form_id\":\"f1\"}],"
                + "  \"status\":\"paused\","
                + "  \"created_at\":1705407629,"
                + "  \"elapsed_time\":0.5,"
                + "  \"total_tokens\":0,"
                + "  \"total_steps\":1"
                + "}"
                + "}";

        WorkflowPausedEvent event = JsonUtils.fromJson(json, WorkflowPausedEvent.class);

        assertNotNull(event);
        assertEquals("wr1", event.getWorkflowRunId());
        assertNotNull(event.getData());
        assertEquals("paused", event.getData().getStatus());
        assertEquals(1, event.getData().getPausedNodes().size());
        assertEquals("approval_node", event.getData().getPausedNodes().get(0));
        assertEquals(1, event.getData().getReasons().size());
        assertEquals("human_input_required", event.getData().getReasons().get(0).get("TYPE"));
    }

    @Test
    public void shouldDeserializeHumanInputFormFilledEvent() {
        String json = "{"
                + "\"event\":\"human_input_form_filled\","
                + "\"task_id\":\"t1\","
                + "\"workflow_run_id\":\"wr1\","
                + "\"data\":{"
                + "  \"node_id\":\"approval_node\","
                + "  \"node_title\":\"Approval\","
                + "  \"rendered_content\":\"Approved by reviewer.\","
                + "  \"action_id\":\"approve\","
                + "  \"action_text\":\"Approve\","
                + "  \"submitted_data\":{\"comment\":\"looks good\"}"
                + "}"
                + "}";

        HumanInputFormFilledEvent event = JsonUtils.fromJson(json, HumanInputFormFilledEvent.class);

        assertNotNull(event);
        assertEquals("wr1", event.getWorkflowRunId());
        assertNotNull(event.getData());
        assertEquals("approve", event.getData().getActionId());
        assertEquals("Approve", event.getData().getActionText());
        assertEquals("Approved by reviewer.", event.getData().getRenderedContent());
        assertEquals("looks good", event.getData().getSubmittedData().get("comment"));
    }

    @Test
    public void shouldDeserializeHumanInputFormTimeoutEvent() {
        String json = "{"
                + "\"event\":\"human_input_form_timeout\","
                + "\"task_id\":\"t1\","
                + "\"workflow_run_id\":\"wr1\","
                + "\"data\":{"
                + "  \"node_id\":\"approval_node\","
                + "  \"node_title\":\"Approval\","
                + "  \"expiration_time\":1705494029"
                + "}"
                + "}";

        HumanInputFormTimeoutEvent event = JsonUtils.fromJson(json, HumanInputFormTimeoutEvent.class);

        assertNotNull(event);
        assertEquals("wr1", event.getWorkflowRunId());
        assertNotNull(event.getData());
        assertEquals("approval_node", event.getData().getNodeId());
        assertEquals(Long.valueOf(1705494029L), event.getData().getExpirationTime());
    }
}
