package io.github.imfangs.dify.client.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 建议问题响应
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestedQuestionsResponse {
    /**
     * 结果
     */
    private String result;

    /**
     * 建议问题列表
     */
    private List<String> data;
}
