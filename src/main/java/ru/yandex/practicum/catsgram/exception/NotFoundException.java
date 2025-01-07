package ru.yandex.practicum.catsgram.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String massage) {
        super(massage);
    }

    public NotFoundException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
