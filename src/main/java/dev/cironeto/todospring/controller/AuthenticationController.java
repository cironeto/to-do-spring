package dev.cironeto.todospring.controller;

import dev.cironeto.todospring.dto.AuthenticationDto;
import dev.cironeto.todospring.dto.TokenDto;
import dev.cironeto.todospring.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authenticate(@RequestBody AuthenticationDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
