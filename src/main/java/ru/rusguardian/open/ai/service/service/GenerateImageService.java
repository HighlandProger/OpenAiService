package ru.rusguardian.open.ai.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.rusguardian.open.ai.service.exception.OpenAiRequestException;
import ru.rusguardian.open.ai.service.util.WebExceptionMessageUtil;
import ru.rusguardian.open.ai.service.service.dto.image.OpenAiTextToImageRequestDto;
import ru.rusguardian.open.ai.service.service.dto.image.OpenAiTextToImageResponseDto;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GenerateImageService {

    private static final String OPEN_AI_GENERATION_URL = "https://api.openai.com/v1/images/generations";

    private final WebClient openAIImageWebClient;

    public GenerateImageService(@Qualifier("openAIImageWebClient") WebClient openAIImageWebClient) {
        this.openAIImageWebClient = openAIImageWebClient;
    }

    @Async
    public CompletableFuture<String> generateImage(OpenAiTextToImageRequestDto dto) {

        return openAIImageWebClient.post()
                .uri(OPEN_AI_GENERATION_URL)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(OpenAiTextToImageResponseDto.class)
                .toFuture()
                .thenApply(response -> response.getData().get(0).getUrl())
                .exceptionally(e -> {
                    String errorMessage = WebExceptionMessageUtil.getErrorMessage(e);
                    log.error(errorMessage);
                    throw new OpenAiRequestException(errorMessage, e);
                });
    }
}
