package io.github.imfangs.dify.client.model.datasets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 检索模式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalModel {
    /**
     * 检索方法
     * keyword_search 关键字检索
     * hybrid_search 混合检索
     * semantic_search 语义检索
     * full_text_search 全文检索
     */
    private String searchMethod;

    /**
     * 是否开启rerank (非必填，如果检索模式为 semantic_search 模式或者 hybrid_search 则传值)
     */
    private Boolean rerankingEnable;

    /**
     * 混合检索
     * weighted_score 权重设置
     * reranking_model Rerank 模型
     */
    private String rerankingMode;

    /**
     * Rerank 模型配置
     */
    private RerankingModel rerankingModel;

    /**
     * 召回条数
     */
    private Integer topK;

    /**
     * 是否开启召回分数限制
     */
    private Boolean scoreThresholdEnabled;

    /**
     * 召回分数限制
     */
    private Float scoreThreshold;

    /**
     * Rerank 模型配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RerankingModel {
        /**
         * Rerank 模型的提供商
         */
        private String rerankingProviderName;

        /**
         * Rerank 模型的名称
         */
        private String rerankingModelName;
    }
}
