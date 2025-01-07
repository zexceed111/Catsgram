package ru.yandex.practicum.catsgram.exception;

public class ConditionsNotMetException extends RuntimeException {
    public ConditionsNotMetException (String massage){
        super(massage);
    }

    public ConditionsNotMetException(String massage, Throwable cause){
        super(massage, cause);
    }
}
