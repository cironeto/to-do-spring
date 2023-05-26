package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.User;

public class UserResponse {

    private Long id;
    private String name;
    private String email;

    public UserResponse() {
    }

    public UserResponse(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
