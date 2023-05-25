package dev.cironeto.todospring.dto;

import dev.cironeto.todospring.model.Task;
import dev.cironeto.todospring.model.TaskPriority;

public class TaskResponse {
    private Long id;
    private String description;
    private TaskPriority priority;
    private boolean taskCompleted;
    Long userId;

    public TaskResponse() {
    }

    public TaskResponse(Task entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.priority = entity.getPriority();
        this.taskCompleted = entity.isTaskCompleted();
        this.userId = entity.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
