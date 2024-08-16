package ru.rusguardian.open.ai.service.service.dto.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletionDto {

    private String message;
    private String role;

}
