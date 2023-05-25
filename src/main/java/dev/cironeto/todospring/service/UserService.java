package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.UserRequest;
import dev.cironeto.todospring.dto.UserResponse;
import dev.cironeto.todospring.exception.BadRequestException;
import dev.cironeto.todospring.exception.DataIntegrityException;
import dev.cironeto.todospring.model.Role;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER);

        User userSaved;
        try {
            userSaved = userRepository.save(user);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return new UserResponse(userSaved);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserResponse(user);
    }

    @Override
    public UserResponse update(UserRequest userRequest, Long id) {
        User user = userRepository.getReferenceById(id);
        user.setId(id);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User userUpdated;
        try {
            userUpdated = userRepository.save(user);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return new UserResponse(userUpdated);
    }

    @Override
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e){
            throw new DataIntegrityException(e.getMessage());
        }
    }
}
