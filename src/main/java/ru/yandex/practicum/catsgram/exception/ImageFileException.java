package ru.yandex.practicum.catsgram.exception;

public class ImageFileException extends RuntimeException {
    public ImageFileException(String massage) {
        super(massage);
    }

    public ImageFileException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
