package ru.rusguardian.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.rusguardian.service.dto.text.OpenAiTextRequestDto;
import ru.rusguardian.service.dto.text.OpenAiTextResponseDto;
import ru.rusguardian.service.exception.OpenAiRequestException;
import ru.rusguardian.util.WebExceptionMessageUtil;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GetCompletionService {

    private static final String OPEN_AI_COMPLETIONS_URL = "https://api.openai.com/v1/chat/completions";

    private final WebClient openAITextWebClient;

    public GetCompletionService(@Qualifier("openAITextWebClient") WebClient openAITextWebClient) {
        this.openAITextWebClient = openAITextWebClient;
    }

    @Async
    public CompletableFuture<OpenAiTextResponseDto> getCompletion(OpenAiTextRequestDto dto) {

        return openAITextWebClient.post()
                .uri(OPEN_AI_COMPLETIONS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(OpenAiTextResponseDto.class)
                .toFuture()
                .exceptionally(e -> {
                    String errorMessage = WebExceptionMessageUtil.getErrorMessage(e);
                    log.error(errorMessage);
                    throw new OpenAiRequestException(errorMessage, e);
                });
    }
}
