package ru.yandex.practicum.catsgram.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode(of = {"email"})
class User {
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

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
class Post {
    private Long id;
    private long authorId;
    private String description;
    private Instant postDate;

    public Post(Long id, long authorId, String description, Instant postDate) {
        this.id = id;
        this.authorId = authorId;
        this.description = description;
        this.postDate = postDate;
    }
}

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
class Image {
    private Long id;
    private long postId;
    private String originalFileName;
    private String filePath;

    public Image(Long id, long postId, String originalFileName, String filePath) {
        this.id = id;
        this.postId = postId;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
    }
}
