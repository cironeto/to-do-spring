package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.TaskRequest;
import dev.cironeto.todospring.dto.TaskResponse;
import dev.cironeto.todospring.dto.UserResponse;
import dev.cironeto.todospring.exception.EntityNotFound;
import dev.cironeto.todospring.exception.UserNotAllowedException;
import dev.cironeto.todospring.model.Task;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.TaskRepository;
import dev.cironeto.todospring.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements ApplicationCrudService<TaskRequest, TaskResponse> {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse create(TaskRequest taskRequest) {
        var task = new Task();
        task.setDescription(taskRequest.description());
        task.setPriority(taskRequest.priority());
        task.setTaskCompleted(false);

        String userEmail = getUserEmailFromContext();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFound("User not found"));
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(
                savedTask.getId(),
                savedTask.getDescription(),
                savedTask.getPriority(),
                savedTask.isTaskCompleted(),
                new UserResponse(task.getUser().getId(), task.getUser().getName(), task.getUser().getEmail())
        );
    }

    @Override
    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getDescription(),
                        task.getPriority(),
                        task.isTaskCompleted(),
                        new UserResponse(task.getUser().getId(), task.getUser().getName(), task.getUser().getEmail())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFound("User not found"));
        return new TaskResponse(
                task.getId(),
                task.getDescription(),
                task.getPriority(),
                task.isTaskCompleted(),
                new UserResponse(task.getUser().getId(), task.getUser().getName(), task.getUser().getEmail())
        );
    }

    @Override
    public TaskResponse update(TaskRequest taskRequest, Long id) {
        Task task = taskRepository.getReferenceById(id);
        task.setDescription(taskRequest.description());
        task.setPriority(taskRequest.priority());

        String userEmail = getUserEmailFromContext();
        if(!userEmail.equals(task.getUser().getEmail())){
            throw new UserNotAllowedException("User not allowed to update the task");
        }

        Task taskSaved = taskRepository.save(task);
        return new TaskResponse(
                taskSaved.getId(),
                taskSaved.getDescription(),
                taskSaved.getPriority(),
                taskSaved.isTaskCompleted(),
                new UserResponse(task.getUser().getId(), task.getUser().getName(), task.getUser().getEmail())
        );
    }

    @Override
    public void delete(Long id) {
        Task task = taskRepository.getReferenceById(id);
        String userEmail = getUserEmailFromContext();
        if(!userEmail.equals(task.getUser().getEmail())){
            throw new UserNotAllowedException("User not allowed to delete the task");
        }

        taskRepository.deleteById(id);
    }

    public void completeTask(Long id){
        Task task = taskRepository.getReferenceById(id);
        String userEmail = getUserEmailFromContext();
        if(!userEmail.equals(task.getUser().getEmail())){
            throw new UserNotAllowedException("User not allowed to complete the task");
        }
        task.setTaskCompleted(true);
        taskRepository.save(task);
    }

    public List<TaskResponse> getUncompletedTasks(Integer priority){
        if (priority == -1) {
            priority = null;
        }
        String email = getUserEmailFromContext();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFound("User not found"));

        List<Task> uncompletedTasks = taskRepository.findUncompletedTasks(user.getId(), priority);
        return uncompletedTasks
                .stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getDescription(),
                        task.getPriority(),
                        task.isTaskCompleted(),
                        new UserResponse(task.getUser().getId(), task.getUser().getName(), task.getUser().getEmail())))
                .collect(Collectors.toList());
    }

    private String getUserEmailFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
