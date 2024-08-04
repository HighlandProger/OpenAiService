package ru.rusguardian.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Getter
@Slf4j
public enum AIModel {

    GPT_4_OMNI("gpt-4o-2024-05-13"),
    GPT_4_MINI("gpt-4o-mini-2024-07-18"),
    GPT_3_5_TURBO("gpt-3.5-turbo-0125"),
    ADA_V2_EMBEDDINGS("ada-v2-embedding"),
    DALL_E_3("dall-e-3"),
    WHISPER("whisper-1"),
    TTS("tts-1"),
    TEXT_EMBEDDING_3_SMALL("text-embedding-3-small"),
    TEXT_EMBEDDING_3_LARGE("text-embedding-3-large"),
    TEXT_EMBEDDING_ADA_002("text-embedding-ada-002");

    private final String modelName;

    private static final Map<String, AIModel> MODEL_NAME_MAP = new HashMap<>();

    static {
        for (AIModel m : AIModel.values()) {
            if (m.getModelName() != null) MODEL_NAME_MAP.put(m.getModelName(), m);
        }
    }

}
