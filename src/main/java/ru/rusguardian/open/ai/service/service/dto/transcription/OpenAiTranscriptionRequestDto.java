package ru.rusguardian.open.ai.service.service.dto.transcription;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OpenAiTranscriptionRequestDto {

    private File file;
    private String model;
    private String language;
    private String prompt;
    @JsonProperty("response_format")
    private String responseFormat;
    private double temperature;
}
