package ru.yandex.practicum.catsgram.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode(of = {"email"})
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Instant registrationDate;

    public User(long id, String username, String email, String password, Instant registrationDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }
}
