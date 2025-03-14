package io.github.imfangs.dify.client.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 简单响应
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleResponse {
    /**
     * 结果
     */
    private String result;
}
