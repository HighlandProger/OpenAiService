package ru.rusguardian.open.ai.service.service.dto.embeddings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiEmbeddingRequestDto {

    private String input;
    private String model;
}
