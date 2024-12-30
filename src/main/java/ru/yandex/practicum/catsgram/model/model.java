package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import java.time.Instant;

@Data
class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Instant registrationDate;
}
@Data
class Post {
    private Long id;
    private long authorId;
    private String description;
    private Instant postDate;
}
@Data
class Image {
    private Long id;
    private long postId;
    private String originalFileName;
    private String filePath;
}
