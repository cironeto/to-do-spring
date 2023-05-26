package dev.cironeto.todospring.service;

import dev.cironeto.todospring.dto.TaskRequest;
import dev.cironeto.todospring.dto.TaskResponse;
import dev.cironeto.todospring.exception.UserNotAllowedException;
import dev.cironeto.todospring.model.Task;
import dev.cironeto.todospring.model.TaskPriority;
import dev.cironeto.todospring.model.User;
import dev.cironeto.todospring.repository.TaskRepository;
import dev.cironeto.todospring.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, userRepository);
    }

    @Test
    void create_ValidTaskRequest_ReturnsTaskResponse() {
        User user = new User();
        user.setId(1L);
        user.setEmail("joao@mail.com");

        TaskRequest taskRequest = createValidTaskRequest();
        Task savedTask = createTaskFromRequest(taskRequest, user);

        when(userRepository.findByEmail(anyString())).thenReturn(java.util.Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        mockSecurityContextHolder(user.getEmail());

        TaskResponse response = taskService.create(taskRequest);

        assertNotNull(response);
        assertEquals(savedTask.getId(), response.getId());
        assertEquals(savedTask.getDescription(), response.getDescription());
        assertEquals(savedTask.getPriority(), response.getPriority());
        assertEquals(savedTask.isTaskCompleted(), response.isTaskCompleted());
        assertEquals(savedTask.getUser(), user);
    }



    @Test
    void findAll_NoTasks_ReturnsEmptyList() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskResponse> responses = taskService.findAll();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void findById_ExistingId_ReturnsTaskResponse() {
        Task task = createTask();

        User user = new User();
        user.setEmail("joao@mail.com");
        task.setUser(user);

        when(taskRepository.findById(task.getId())).thenReturn(java.util.Optional.of(task));

        TaskResponse response = taskService.findById(task.getId());

        assertNotNull(response);
        assertEquals(task.getId(), response.getId());
        assertEquals(task.getDescription(), response.getDescription());
        assertEquals(task.getPriority(), response.getPriority());
        assertEquals(task.isTaskCompleted(), response.isTaskCompleted());
        assertEquals(user.getId(), response.getUserId());
    }

    @Test
    void findById_NonExistingId_ThrowsEntityNotFoundException() {
        Long nonExistingId = 100L;

        when(taskRepository.findById(nonExistingId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.findById(nonExistingId));
    }

    @Test
    void update_ValidTaskRequestAndExistingIdAndUserAllowed_ReturnsUpdatedTaskResponse() {
        Long taskId = 1L;
        TaskRequest taskRequest = createValidTaskRequest();

        Task existingTask = createTask();
        User user = new User();
        user.setEmail("joao@mail.com");
        existingTask.setUser(user);

        Task updatedTask = createTaskFromRequest(taskRequest, user);

        when(taskRepository.getReferenceById(taskId)).thenReturn(existingTask);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        mockSecurityContextHolder(user.getEmail());

        TaskResponse response = taskService.update(taskRequest, taskId);

        assertNotNull(response);
        assertEquals(taskId, response.getId());
        assertEquals(updatedTask.getDescription(), response.getDescription());
        assertEquals(updatedTask.getPriority(), response.getPriority());
        assertEquals(updatedTask.isTaskCompleted(), response.isTaskCompleted());
        assertEquals(user.getId(), response.getUserId());
    }

    @Test
    void update_ValidTaskRequestAndExistingIdAndUserNotAllowed_ThrowsUserNotAllowedException() {
        Long taskId = 1L;
        TaskRequest taskRequest = createValidTaskRequest();

        Task existingTask = createTask();

        User user = new User();
        user.setEmail("joao@mail.com");
        existingTask.setUser(user);

        when(taskRepository.getReferenceById(taskId)).thenReturn(existingTask);
        mockSecurityContextHolder("maria@mail.com");

        assertThrows(UserNotAllowedException.class, () -> taskService.update(taskRequest, taskId));
    }

    @Test
    void delete_ExistingIdAndUserAllowed_DeletesTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setEmail("joao@mail.com");
        task.setUser(user);

        when(taskRepository.getReferenceById(taskId)).thenReturn(task);
        mockSecurityContextHolder(user.getEmail());

        taskService.delete(taskId);

        verify(taskRepository, times(1)).getReferenceById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void delete_ExistingIdAndUserNotAllowed_ThrowsUserNotAllowedException() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setEmail("joao@mail.com");
        task.setUser(user);

        when(taskRepository.getReferenceById(taskId)).thenReturn(task);
        mockSecurityContextHolder("maria@mail.com");

        assertThrows(UserNotAllowedException.class, () -> taskService.delete(taskId));
    }

    @Test
    void completeTask_ExistingIdAndUserAllowed_CompletesTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setEmail("joao@mail.com");
        task.setUser(user);

        when(taskRepository.getReferenceById(taskId)).thenReturn(task);
        mockSecurityContextHolder(user.getEmail());

        taskService.completeTask(taskId);

        assertTrue(task.isTaskCompleted());
    }

    @Test
    void completeTask_ExistingIdAndUserNotAllowed_ThrowsUserNotAllowedException() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setEmail("joao@mail.com");
        task.setUser(user);

        when(taskRepository.getReferenceById(taskId)).thenReturn(task);
        mockSecurityContextHolder("maria@mail.com");

        assertThrows(UserNotAllowedException.class, () -> taskService.completeTask(taskId));
        assertFalse(task.isTaskCompleted());
    }

    private void mockSecurityContextHolder(String userEmail) {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(userEmail);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    private static TaskRequest createValidTaskRequest() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setDescription("Task description");
        taskRequest.setPriority(TaskPriority.MEDIUM_PRIORITY);
        return taskRequest;
    }


    private static Task createTaskFromRequest(TaskRequest taskRequest, User user) {
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setDescription(taskRequest.getDescription());
        savedTask.setPriority(taskRequest.getPriority());
        savedTask.setTaskCompleted(false);
        savedTask.setUser(user);
        return savedTask;
    }

    private static Task createTask() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Task description");
        task.setPriority(TaskPriority.MEDIUM_PRIORITY);
        task.setTaskCompleted(false);
        return task;
    }
}
