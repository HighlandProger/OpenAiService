package ru.rusguardian.service.dto.text;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenAiTextRequestDto {

    //TODO minor add all types by documentation
    private List<RequestMessageDto> messages;
    private String model;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private double temperature;
    private String user;

}
