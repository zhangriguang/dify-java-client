package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * reasoning_chunk 事件（Chatflow）
 * 当 LLM 节点使用 reasoning_format=separated 时，思考（chain-of-thought）内容以此事件的增量形式并行流出，
 * 与 message 事件的正文分离。以 data.is_final=true 标记思考流结束（此时 data.reasoning 可能为空）。
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReasoningChunkEvent extends BaseMessageEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private ReasoningChunkData data;

    /**
     * reasoning_chunk 事件的 data 载荷
     */
    @Data
    @NoArgsConstructor
    public static class ReasoningChunkData {

        /**
         * 消息 ID（与外层 message_id 相同）
         */
        @JsonProperty("message_id")
        private String messageId;

        /**
         * 本次思考内容增量（可能为空字符串，尤其是 is_final=true 时）
         */
        @JsonProperty("reasoning")
        private String reasoning;

        /**
         * 触发本次事件的 LLM 节点 ID
         */
        @JsonProperty("node_id")
        private String nodeId;

        /**
         * 是否为思考流的最后一条（true 时通常 reasoning 为空，仅作结束标记）
         */
        @JsonProperty("is_final")
        private Boolean isFinal;
    }
}
