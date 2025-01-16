package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public List<User> getUsers() {
        List<User> userList = users.values().stream().collect(Collectors.toList());
        return userList;
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public User addUser(User user) {
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

    public User updateUser(User updatedUser) {
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
        return updatedUser;
    }

    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        return users.get(email);
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
