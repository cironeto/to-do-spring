package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequest {

    @NotBlank(message = "Field DESCRIPTION cannot be null")
    private String description;
    @NotNull(message = "Field PRIORITY cannot be null")
    private TaskPriority priority;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
}
