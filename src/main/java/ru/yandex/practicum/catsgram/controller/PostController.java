package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam String sort,
                                    @RequestParam Long page,
                                    @RequestParam Long size) {
        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new ParameterNotValidException(sort, "Некорректный тип сортировки.");
        }
        if (size <= 0) {
            throw new ParameterNotValidException(size.toString(),
                    "Некорректный размер выборки. Размер должен быть больше нуля.");
        }

        if (page < 0) {
            throw new ParameterNotValidException(page.toString(),
                    "Некорректное место старта. Место должно быть не меньше нуля.");
        }

        return postService.findAll(page, size, sort);

    }

    @GetMapping(value = "/posts/{postId}")
    @ResponseBody
    public Post findPostById(@PathVariable Long postId) {
        return postService.findById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }

}


