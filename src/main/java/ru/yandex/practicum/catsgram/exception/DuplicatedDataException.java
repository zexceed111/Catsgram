package ru.yandex.practicum.catsgram.exception;

public class DuplicatedDataException extends RuntimeException {
    public DuplicatedDataException(String message) {
        super(message);
    }

    public DuplicatedDataException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
