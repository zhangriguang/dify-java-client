package io.github.imfangs.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单文档签名下载 URL 响应
 * 对应 GET /datasets/{dataset_id}/documents/{document_id}/download
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDownloadUrlResponse {

    /**
     * 签名下载 URL（限时有效）
     */
    private String url;
}
