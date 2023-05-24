package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.TaskPriority;

public record TaskRequest(String description, TaskPriority priority) {
}
