package io.github.imfangs.dify.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * Dify客户端配置
 */
@Data
@Builder
public class DifyConfig {
    /**
     * API基础URL
     */
    private String baseUrl;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 连接超时时间（毫秒）
     */
    @Builder.Default
    private int connectTimeout = 5000;

    /**
     * 读取超时时间（毫秒）
     */
    @Builder.Default
    private int readTimeout = 60000;

    /**
     * 写入超时时间（毫秒）
     */
    @Builder.Default
    private int writeTimeout = 30000;
}
