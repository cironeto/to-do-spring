package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.TaskRequest;
import dev.cironeto.todospring.dto.TaskResponse;
import dev.cironeto.todospring.exception.UserNotAllowedException;
import dev.cironeto.todospring.model.Task;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.TaskRepository;
import dev.cironeto.todospring.repository.UserRepository;
import dev.cironeto.todospring.validation.BeanValidator;
import jakarta.persistence.EntityNotFoundException;
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
        BeanValidator.validate(taskRequest);

        var task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setTaskCompleted(false);

        String userEmail = getUserEmailFromContext();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("User not found"));
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    @Override
    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return new TaskResponse(task);
    }

    @Override
    public TaskResponse update(TaskRequest taskRequest, Long id) {
        BeanValidator.validate(taskRequest);

        Task task = taskRepository.getReferenceById(id);
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());

        String userEmail = getUserEmailFromContext();
        if(!userEmail.equals(task.getUser().getEmail())){
            throw new UserNotAllowedException();
        }

        Task taskSaved = taskRepository.save(task);
        return new TaskResponse(taskSaved);
    }

    @Override
    public void delete(Long id) {
        Task task = taskRepository.getReferenceById(id);
        String userEmail = getUserEmailFromContext();
        if(!userEmail.equals(task.getUser().getEmail())){
            throw new UserNotAllowedException();
        }

        taskRepository.deleteById(id);
    }

    public void completeTask(Long id){
        Task task = taskRepository.getReferenceById(id);
        String userEmail = getUserEmailFromContext();
        if(!userEmail.equals(task.getUser().getEmail())){
            throw new UserNotAllowedException();
        }

        task.setTaskCompleted(true);
        taskRepository.save(task);
    }

    public List<TaskResponse> getUncompletedTasks(Integer priority){
        if (priority == -1) {
            priority = null;
        }
        String email = getUserEmailFromContext();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Task> uncompletedTasks = taskRepository.findUncompletedTasks(user.getId(), priority);
        return uncompletedTasks
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    private String getUserEmailFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
