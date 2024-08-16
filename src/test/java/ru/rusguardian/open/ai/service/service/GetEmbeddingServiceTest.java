package ru.rusguardian.open.ai.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.rusguardian.open.ai.service.config.ApplicationConfiguration;

@SpringBootTest(classes = {ApplicationConfiguration.class, GetEmbeddingService.class})
class GetEmbeddingServiceTest {

    @Autowired
    private GetEmbeddingService getEmbeddingService;

    @org.junit.jupiter.api.Test
    void getEmbedding() {
    }
}