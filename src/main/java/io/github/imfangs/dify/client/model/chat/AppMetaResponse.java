package io.github.imfangs.dify.client.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/**
 * 应用元数据响应
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppMetaResponse {
    /**
     * 工具图标
     */
    private Map<String, Object> toolIcons;
}
