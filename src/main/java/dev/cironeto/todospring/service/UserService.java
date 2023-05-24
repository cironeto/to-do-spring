package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.UserDto;
import dev.cironeto.todospring.model.Role;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ApplicationCrud<UserDto>{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(UserDto userDto) {
        var user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(Role.USER);
        User userSaved = userRepository.save(user);
        return new UserDto(userSaved.getName(), userSaved.getEmail(), null);
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public void delete(UserDto userDto) {

    }
}
