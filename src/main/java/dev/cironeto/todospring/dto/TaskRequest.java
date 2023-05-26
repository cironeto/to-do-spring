package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequest {

    @NotBlank(message = "Field DESCRIPTION cannot be null")
    @Schema(description = "The description of the task", example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
    private String description;
    @NotNull(message = "Field PRIORITY cannot be null")
    @Schema(description = "The priority of the task", example = "1")
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
