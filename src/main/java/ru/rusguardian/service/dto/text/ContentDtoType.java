package ru.rusguardian.service.dto.text;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentDtoType {
    TEXT("text"),
    IMAGE_URL("image_url");

    private final String value;
}
