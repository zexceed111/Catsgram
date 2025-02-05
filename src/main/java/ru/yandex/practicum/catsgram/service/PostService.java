package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.time.Instant;
import java.util.*;


@Service
public class PostService {
    public final Map<Long, Post> posts = new HashMap<>();
    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public Collection<Post> findAll(Long from, Long size, String sort) {
        if (sort.equals("asc")) {
            return posts.keySet()
                    .stream()
                    .map(posts::get)
                    .filter(k -> k.getId() > from)
                    .sorted(Comparator.comparing(Post::getPostDate))
                    .limit(size)
                    .toList();
        } else if (sort.equals("desc")) {
            return posts.keySet()
                    .stream()
                    .map(posts::get)
                    .sorted(Comparator.comparing(Post::getPostDate).reversed())
                    .limit(size)
                    .toList();
        } else {
            return null;
        }
    }

    public Post create(Post post) {
        if (post.getDescription() == null || post.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        // Проверка на существование автора поста
        Long authorId = post.getAuthorId();
        if (userService.findUserById(authorId).isEmpty()) {
            throw new ConditionsNotMetException("Автор с id = " + authorId + " не найден");
        }

        post.setId(getNextId());
        post.setPostDate(Instant.now());
        posts.put(post.getId(), post);
        return post;
    }

    public Post findById(Long postId) {
        return Optional.ofNullable(posts.get(postId))
                .orElseThrow(() -> new ConditionsNotMetException(String.format("Пост № %d не найден", postId)));
    }

    public Post update (Post newPost){
        if (newPost.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (posts.containsKey(newPost.getId())){
            Post oldPost = posts.get(newPost.getId());
            if (newPost.getDescription() == null || newPost.getDescription().isBlank()){
                throw new ConditionsNotMetException("Описание не может быть пустым");
            }
            oldPost.setDescription(newPost.getDescription());
            return oldPost;
        }
        throw new NotFoundException("Пост с id = \" + newPost.getId() + \" не найден");
    }


    private long getNextId() {
        long currentMaxId = posts.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
