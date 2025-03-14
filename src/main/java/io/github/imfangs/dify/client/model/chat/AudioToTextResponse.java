package io.github.imfangs.dify.client.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 语音转文字响应
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioToTextResponse {
    /**
     * 文本内容
     */
    private String text;
}
