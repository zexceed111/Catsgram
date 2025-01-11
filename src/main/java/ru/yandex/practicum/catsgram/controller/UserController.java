package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = users.values().stream().collect(Collectors.toList());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    private long getNextId() {
        long currentMaxId = users.keySet().stream().mapToLong(id -> id).max().orElse(0);
        return ++currentMaxId;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        for (User existringUSer : users.values()) {
            if (existringUSer.getEmail().equals(user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        if (!users.containsKey(updatedUser.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User existingUser = users.get(updatedUser.getId());

        if (updatedUser.getEmail() != null || !updatedUser.getEmail().isEmpty()) {
            for (User user : users.values()) {
                if (!user.getId().equals(updatedUser.getId()) && user.getEmail().equals(updatedUser.getEmail())) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
            }
        }
        existingUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

}
