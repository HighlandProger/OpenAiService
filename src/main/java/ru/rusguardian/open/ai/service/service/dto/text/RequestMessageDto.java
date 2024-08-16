package ru.rusguardian.open.ai.service.service.dto.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessageDto {
    private List<ContentDto> content;
    private String role;

    public RequestMessageDto(CompletionDto completionDto) {
        this.content = List.of(new ContentDto(ContentDtoType.TEXT.getValue(), completionDto.getMessage(), null));
        this.role = completionDto.getRole();
    }

    public RequestMessageDto(String textContent, String role) {
        this.content = List.of(new ContentDto(ContentDtoType.TEXT.getValue(), textContent, null));
        this.role = role;
    }
}
