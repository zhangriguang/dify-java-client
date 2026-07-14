package io.github.imfangs.dify.client.model.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Human Input 表单响应
 * 对应 GET /form/human_input/{form_token} 返回体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HumanInputFormResponse {

    /**
     * 表单内容（渲染后的 markdown/HTML 文本）
     */
    @JsonProperty("form_content")
    private String formContent;

    /**
     * 表单输入项定义（每一项包含 variable、label、type、required 等）
     */
    private List<Map<String, Object>> inputs;

    /**
     * 默认值（key 为输入项变量名，value 已序列化为字符串）
     */
    @JsonProperty("resolved_default_values")
    private Map<String, String> resolvedDefaultValues;

    /**
     * 用户可选的操作按钮列表（每一项含 id、label、style 等）
     * 提交时通过 action 字段回传所选按钮的 id
     */
    @JsonProperty("user_actions")
    private List<Map<String, Object>> userActions;

    /**
     * 过期时间（Unix 秒），null 表示不过期
     */
    @JsonProperty("expiration_time")
    private Long expirationTime;
}
