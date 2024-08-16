package ru.rusguardian.open.ai.service.config;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class ApplicationConfiguration {

    @Bean(name = "openAIWebClient")
    public WebClient openAIWebClient(@Value("${open-api.secret-key}") String apiKey) {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)  // Таймаут на подключение
                        .responseTimeout(Duration.ofSeconds(35))  // Таймаут на получение ответа
                ))
                .build();
    }

    @Bean(name = "openAIImageWebClient")
    public WebClient openAIImageWebClient(@Value("${open-api.secret-key}") String apiKey) {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)  // Таймаут на подключение
                        .responseTimeout(Duration.ofSeconds(60))  // Таймаут на получение ответа
                ))
                .build();
    }
}
