package io.github.imfangs.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.imfangs.dify.client.model.common.Metadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 消息结束事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageEndEvent extends BaseMessageEvent {

    /**
     * 元数据
     */
    @JsonProperty("metadata")
    private Metadata metadata;
}
