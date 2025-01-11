package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Long, User> users = new HashMap<>();
    private Long idCounter = 1L;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = users.values().stream().collect(Collectors.toList());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        for (User existringUSer : users.values()) {
            if (existringUSer.getEmail().equals(user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }
        user.setId((idCounter++));
        users.put(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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
