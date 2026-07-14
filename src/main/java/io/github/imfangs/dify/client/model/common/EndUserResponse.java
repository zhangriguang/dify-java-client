package io.github.imfangs.dify.client.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 终端用户（End User）响应
 * 对应后端 EndUserDetail
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndUserResponse {

    /**
     * 终端用户 ID
     */
    private String id;

    /**
     * 租户 ID
     */
    @JsonProperty("tenant_id")
    private String tenantId;

    /**
     * 应用 ID
     */
    @JsonProperty("app_id")
    private String appId;

    /**
     * 终端用户类型
     */
    private String type;

    /**
     * 外部用户 ID（调用方传入的 user 标识）
     */
    @JsonProperty("external_user_id")
    private String externalUserId;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 是否为匿名用户
     */
    @JsonProperty("is_anonymous")
    private Boolean isAnonymous;

    /**
     * 会话 ID
     */
    @JsonProperty("session_id")
    private String sessionId;

    /**
     * 创建时间（ISO 8601 字符串）
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 更新时间（ISO 8601 字符串）
     */
    @JsonProperty("updated_at")
    private String updatedAt;
}
