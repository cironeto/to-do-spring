package dev.cironeto.todospring.service;

import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void findAll() {
        List<User> all = userRepository.findAll();
        all.forEach(user -> {
            System.out.println(passwordEncoder.matches("123", user.getPassword()));
        });
    }
}