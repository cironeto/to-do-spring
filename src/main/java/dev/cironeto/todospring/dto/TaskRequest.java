package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.TaskPriority;

public class TaskRequest {

    private String description;
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
