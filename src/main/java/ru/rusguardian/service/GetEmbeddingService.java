package ru.rusguardian.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.rusguardian.exception.OpenAiRequestException;
import ru.rusguardian.service.dto.embeddings.OpenAiEmbeddingRequestDto;
import ru.rusguardian.service.dto.embeddings.OpenAiEmbeddingResponseDto;
import ru.rusguardian.util.WebExceptionMessageUtil;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GetEmbeddingService {

    private static final String OPEN_AI_EMBEDDINGS_URL = "https://api.openai.com/v1/embeddings";
    private final WebClient openAiWebClient;

    public GetEmbeddingService(@Qualifier("openAIWebClient") WebClient openAiWebClient) {
        this.openAiWebClient = openAiWebClient;
    }

    @Async
    public CompletableFuture<OpenAiEmbeddingResponseDto> getEmbedding(OpenAiEmbeddingRequestDto dto) {

        return openAiWebClient.post()
                .uri(OPEN_AI_EMBEDDINGS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(OpenAiEmbeddingResponseDto.class)
                .toFuture()
                .exceptionally(e -> {
                    String errorMessage = WebExceptionMessageUtil.getErrorMessage(e);
                    log.error(errorMessage);
                    throw new OpenAiRequestException(errorMessage, e);
                });
    }
}
