package ru.rusguardian.open.ai.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.rusguardian.open.ai.service.service.dto.transcription.OpenAiTranscriptionRequestDto;
import ru.rusguardian.open.ai.service.service.dto.transcription.OpenAiTranscriptionResponseDto;
import ru.rusguardian.open.ai.service.util.FileUtils;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GetTranscriptionService {

    private static final String TRANSCRIPTIONS_URL = "https://api.openai.com/v1/audio/transcriptions";

    private final WebClient webClient;

    public GetTranscriptionService(@Qualifier("openAIWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Async
    public CompletableFuture<String> getTextFromSpeech(OpenAiTranscriptionRequestDto requestBody) {

        return webClient.post()
                .uri(TRANSCRIPTIONS_URL)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(getObjectMultiValueMap(requestBody)))
                .retrieve()
                .bodyToMono(OpenAiTranscriptionResponseDto.class)
                .map(OpenAiTranscriptionResponseDto::getText)
                .toFuture();
    }

    private MultiValueMap<String, Object> getObjectMultiValueMap(OpenAiTranscriptionRequestDto requestBody) {
        MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();
        multipartBody.add("file", new ByteArrayResource(FileUtils.getBytes(requestBody.getFile())) {
            @Override
            public String getFilename() {
                return "audio.webm";
            }
        });
        multipartBody.add("model", requestBody.getModel());
        if (requestBody.getLanguage() != null) {
            multipartBody.add("language", requestBody.getLanguage());
        }
        if (requestBody.getPrompt() != null) {
            multipartBody.add("prompt", requestBody.getPrompt());
        }
        if (requestBody.getResponseFormat() != null) {
            multipartBody.add("response_format", requestBody.getResponseFormat());
        }
        multipartBody.add("temperature", requestBody.getTemperature());
        return multipartBody;
    }

}
