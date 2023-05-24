package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.UserRequest;
import dev.cironeto.todospring.dto.UserResponse;
import dev.cironeto.todospring.exception.EntityNotFound;
import dev.cironeto.todospring.model.Role;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements ApplicationCrudService<UserRequest, UserResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        var user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(Role.USER);
        User userSaved = userRepository.save(user);
        return new UserResponse(user.getId(), userSaved.getName(), userSaved.getEmail());
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFound("User not found"));
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public UserResponse update(UserRequest userRequest, Long id) {
        User user = userRepository.getReferenceById(id);
        user.setId(id);
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        User userUpdated = userRepository.save(user);
        return new UserResponse(userUpdated.getId(), userUpdated.getName(), userUpdated.getEmail());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
