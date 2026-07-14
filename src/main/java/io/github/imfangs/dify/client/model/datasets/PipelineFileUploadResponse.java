package io.github.imfangs.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 知识库 Pipeline 文件上传响应
 * 对应 POST /datasets/pipeline/file-upload
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PipelineFileUploadResponse {

    /**
     * 上传文件 ID，作为 pipeline/run 时 datasource_info_list 中的 reference 使用
     */
    private String id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小（字节）
     */
    private Long size;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * MIME 类型
     */
    @JsonProperty("mime_type")
    private String mimeType;

    /**
     * 创建者（账号 ID）
     */
    @JsonProperty("created_by")
    private String createdBy;

    /**
     * 创建时间（ISO 8601 字符串）
     */
    @JsonProperty("created_at")
    private String createdAt;
}
