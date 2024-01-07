package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.*;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    void saveUser(User user);

    List<User> getAllUsers();

    User showUserById(int id);

    void updateUserById(int id, User updateUser);

    void deleteUserById(int id);
}
