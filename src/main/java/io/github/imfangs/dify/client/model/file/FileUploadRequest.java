package io.github.imfangs.dify.client.model.file;

import lombok.Builder;
import lombok.Data;

/**
 * 文件上传请求
 */
@Data
@Builder
public class FileUploadRequest {
    /**
     * 用户标识
     */
    private String user;
}
