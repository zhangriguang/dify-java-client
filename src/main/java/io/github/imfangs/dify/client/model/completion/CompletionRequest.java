package io.github.imfangs.dify.client.model.completion;

import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.model.file.FileInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 文本生成请求
 */
@Data
@Builder
public class CompletionRequest {
    /**
     * 输入参数，允许传入 App 定义的各变量值
     */
    private Map<String, Object> inputs;

    /**
     * 响应模式
     */
    private ResponseMode responseMode;

    /**
     * 用户标识
     */
    private String user;

    /**
     * 文件列表
     */
    private List<FileInfo> files;
}
