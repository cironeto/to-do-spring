package dev.cironeto.todospring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank(message = "Field NAME cannot be null")
    private String name;
    @NotBlank(message = "Field EMAIL cannot be null")
    @Email(message = "EMAIL format not valid")
    private String email;
    @NotBlank(message = "Field PASSWORD cannot be null")
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
