package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.UserRequest;
import dev.cironeto.todospring.dto.UserResponse;
import dev.cironeto.todospring.exception.DataIntegrityException;
import dev.cironeto.todospring.model.Role;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void create_ValidUserRequest_ReturnsUserResponse() {
        UserRequest userRequest = createValidUserRequest();
        User savedUser = createUserToBeSaved(userRequest);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");

        UserResponse response = userService.create(userRequest);

        assertNotNull(response);
        assertEquals(savedUser.getId(), response.getId());
        assertEquals(savedUser.getName(), response.getName());
        assertEquals(savedUser.getEmail(), response.getEmail());
    }

    @Test
    void findAll_NoUsers_ReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponse> responses = userService.findAll();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void findById_ExistingId_ReturnsUserResponse() {
        User user = createUser();

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        UserResponse response = userService.findById(user.getId());

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void findById_NonExistingId_ThrowsEntityNotFoundException() {
        Long nonExistingId = 100L;

        when(userRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(nonExistingId));
    }

    @Test
    void update_ValidUserRequestAndExistingId_ReturnsUpdatedUserResponse() {
        Long userId = 1L;
        UserRequest userRequest = createValidUserRequest();

        User existingUser = createUser();

        User updatedUser = new User();
        updatedUser.setId(existingUser.getId());
        updatedUser.setName(userRequest.getName());
        updatedUser.setEmail(userRequest.getEmail());
        updatedUser.setPassword("newPassword");
        updatedUser.setRole(Role.USER);

        when(userRepository.getReferenceById(userId)).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedNewPassword");

        UserResponse response = userService.update(userRequest, userId);

        assertNotNull(response);
        assertEquals(updatedUser.getId(), response.getId());
        assertEquals(updatedUser.getName(), response.getName());
        assertEquals(updatedUser.getEmail(), response.getEmail());
    }

    @Test
    void delete_ExistingId_DeletesUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        userService.delete(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void delete_NonExistingId_ThrowsEntityNotFoundException() {
        Long nonExistingId = 100L;

        when(userRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(nonExistingId));
    }

    private UserRequest createValidUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("João");
        userRequest.setEmail("joao@mail.com");
        userRequest.setPassword("123");
        return userRequest;
    }


    private static User createUserToBeSaved(UserRequest userRequest) {
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName(userRequest.getName());
        savedUser.setEmail(userRequest.getEmail());
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(Role.USER);
        return savedUser;
    }

    private static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("João");
        user.setEmail("joao@mail.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);
        return user;
    }
}