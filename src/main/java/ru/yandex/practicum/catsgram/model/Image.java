package ru.yandex.practicum.catsgram.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Image {
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
