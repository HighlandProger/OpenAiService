package ru.rusguardian.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.rusguardian.service.dto.speech.OpenAiCreateSpeechRequestDto;
import ru.rusguardian.service.exception.OpenAiRequestException;
import ru.rusguardian.util.FileUtils;
import ru.rusguardian.util.WebExceptionMessageUtil;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class GetSpeechService {

    private static final String CREATE_SPEECH_URL = "https://api.openai.com/v1/audio/speech";

    private final WebClient webClient;

    public GetSpeechService(@Qualifier("openAITextWebClient") WebClient webClient) {
        this.webClient = webClient;
    }


    @Async
    public CompletableFuture<File> getSpeechFromText(OpenAiCreateSpeechRequestDto dto) {
        String fileFormat = dto.getResponseFormat() == null ? "mp3" : dto.getResponseFormat();

        return webClient.post()
                .uri(CREATE_SPEECH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve() // Используем retrieve вместо exchangeToFlux для упрощения
                .bodyToFlux(DataBuffer.class)
                .collectList()
                .flatMap(dataBuffers -> {
                    Path path = FileUtils.getTempFile(fileFormat).toPath();
                    // Выполняем запись данных в файл асинхронно
                    return writeDataToFileAsync(path, dataBuffers);
                })
                .map(Path::toFile)
                .toFuture()
                .exceptionally(e -> {
                    String errorMessage = WebExceptionMessageUtil.getErrorMessage(e);
                    log.error(errorMessage);
                    throw new OpenAiRequestException(errorMessage, e);
                });
    }


    private Mono<Path> writeDataToFileAsync(Path path, List<DataBuffer> dataBuffers) {
        return Mono.fromCallable(() -> {
            try (FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
                for (DataBuffer dataBuffer : dataBuffers) {
                    channel.write(dataBuffer.asByteBuffer());
                    DataBufferUtils.release(dataBuffer);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return path;
        }).subscribeOn(Schedulers.boundedElastic()); // Используем другой планировщик для асинхронной записи в файл
    }

}
