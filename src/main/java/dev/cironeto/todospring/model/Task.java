package dev.cironeto.todospring.model;

import jakarta.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Enumerated
    private TaskPriority priority;
    private boolean taskDone;


    public Task() {
    }

    public Task(Long id, String description, TaskPriority priority, boolean taskDone) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.taskDone = taskDone;
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

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskDone(boolean taskDone) {
        this.taskDone = taskDone;
    }
}
