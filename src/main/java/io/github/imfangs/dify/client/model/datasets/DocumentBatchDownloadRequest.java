package io.github.imfangs.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量下载文档为 ZIP 的请求体
 * 对应 POST /datasets/{dataset_id}/documents/download-zip
 * 每次最多 100 个文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentBatchDownloadRequest {

    /**
     * 要打包下载的文档 ID 列表（1-100）
     */
    @JsonProperty("document_ids")
    private List<String> documentIds;
}
