package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.TaskRequest;
import dev.cironeto.todospring.dto.TaskResponse;
import dev.cironeto.todospring.dto.UserResponse;
import dev.cironeto.todospring.exception.EntityNotFound;
import dev.cironeto.todospring.model.Task;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ApplicationCrudService<TaskRequest, TaskResponse> {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse create(TaskRequest taskRequest) {
        var task = new Task();
        task.setDescription(taskRequest.description());
        task.setPriority(taskRequest.priority());
        task.setTaskDone(false);

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask.getId(), savedTask.getDescription(), savedTask.getPriority(), savedTask.isTaskDone());
    }

    @Override
    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponse(task.getId(), task.getDescription(), task.getPriority(), task.isTaskDone()))
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFound("User not found"));
        return new TaskResponse(task.getId(), task.getDescription(), task.getPriority(), task.isTaskDone());
    }

    @Override
    public TaskResponse update(TaskRequest taskRequest, Long id) {
        Task task = taskRepository.getReferenceById(id);
        task.setDescription(taskRequest.description());
        task.setPriority(taskRequest.priority());
        task.setTaskDone(task.isTaskDone());

        Task taskSaved = taskRepository.save(task);
        return new TaskResponse(taskSaved.getId(), taskSaved.getDescription(), taskSaved.getPriority(), taskSaved.isTaskDone());
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
