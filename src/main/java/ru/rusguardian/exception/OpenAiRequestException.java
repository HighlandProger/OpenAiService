package ru.rusguardian.exception;

public class OpenAiRequestException extends RuntimeException {
    public OpenAiRequestException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

}
