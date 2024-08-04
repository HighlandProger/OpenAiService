package ru.rusguardian.exception;

public class FileReadException extends RuntimeException {

    public FileReadException(String fileName, Throwable e) {
        super(String.format("Exception occurred during reading file: \"%s\"", fileName), e);
    }
}
