package ru.rusguardian.service.dto.embeddings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiEmbeddingResponseDto {

    private String object;
    private List<Data> data;
    private String model;
    private Usage usage;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String object;
        private Long index;
        private double[] embedding;
    }

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("total_tokens")
        private int totalTokens;
    }
}
