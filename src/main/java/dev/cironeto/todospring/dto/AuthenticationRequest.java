package dev.cironeto.todospring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {

    @NotBlank(message = "Field EMAIL cannot be null")
    @Schema(description = "The email of the user", example = "user@mail.com")
    private String email;
    @NotBlank(message = "Field PASSWORD cannot be null")
    @Schema(description = "The password of the user", example = "123")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
