package io.github.imfangs.dify.client.model.workflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Human Input 表单提交请求体
 * 对应 POST /form/human_input/{form_token}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HumanInputFormSubmitRequest {

    /**
     * 表单填写内容，key 为输出变量名。
     * - 段落/下拉输入：String
     * - 单文件输入：Map（含 transfer_method / upload_file_id 或 url / type 等）
     * - 多文件输入：List of Map
     */
    private Map<String, Object> inputs;

    /**
     * 用户选择的操作按钮 id，必须匹配表单 user_actions 中某一项的 id
     */
    private String action;

    /**
     * 终端用户标识
     */
    private String user;
}
