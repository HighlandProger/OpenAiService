package ru.rusguardian.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String value;
}
