package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.TaskPriority;

public record TaskResponse(Long id, String description, TaskPriority priority, boolean taskDone) {
}
