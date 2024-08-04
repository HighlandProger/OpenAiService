package ru.rusguardian.service.dto.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OpenAiErrorResponseDto {

    private ErrorDto error;

    @NoArgsConstructor
    @Data
    public static class ErrorDto {
        private String message;
        private String type;
        private String param;
        private String code;
    }
}
