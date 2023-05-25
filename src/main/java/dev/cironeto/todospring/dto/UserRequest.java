package dev.cironeto.todospring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank(message = "Field NAME cannot be null")
    @Schema(description = "The name of the user", example = "Maria Fernanda")
    private String name;
    @NotBlank(message = "Field EMAIL cannot be null")
    @Email(message = "EMAIL format not valid")
    @Schema(description = "The email of the user", example = "fernanda@gmail.com")
    private String email;
    @NotBlank(message = "Field PASSWORD cannot be null")
    @Schema(description = "The password of the user", example = "Fer*123")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
