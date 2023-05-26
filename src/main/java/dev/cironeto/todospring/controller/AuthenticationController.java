package dev.cironeto.todospring.controller;

import dev.cironeto.todospring.dto.AuthenticationRequest;
import dev.cironeto.todospring.dto.TokenResponse;
import dev.cironeto.todospring.service.AuthenticationService;
import dev.cironeto.todospring.validation.BeanValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Authenticate the user with JWT", tags = {"Auth Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication"),
            @ApiResponse(responseCode = "400", description = "Validation on mandatory fields", content = @Content),
            @ApiResponse(responseCode = "403", description = "Invalid credentials", content = @Content)
    })
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
