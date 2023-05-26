package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.AuthenticationRequest;
import dev.cironeto.todospring.dto.TokenResponse;
import dev.cironeto.todospring.repository.UserRepository;
import dev.cironeto.todospring.validation.BeanValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse authenticate(AuthenticationRequest request) {
        BeanValidator.validate(request);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new TokenResponse(jwtToken);
    }
}
