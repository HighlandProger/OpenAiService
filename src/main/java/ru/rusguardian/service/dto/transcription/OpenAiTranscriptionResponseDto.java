package ru.rusguardian.service.dto.transcription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OpenAiTranscriptionResponseDto {
    private String text;
}
