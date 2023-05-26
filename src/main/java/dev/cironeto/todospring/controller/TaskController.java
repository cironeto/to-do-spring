package dev.cironeto.todospring.controller;

import dev.cironeto.todospring.dto.TaskRequest;
import dev.cironeto.todospring.dto.TaskResponse;
import dev.cironeto.todospring.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a new task", tags = {"Task Endpoints"}, description = "Task Priority params: 0 = LOW_PRIORITY / 1 = MEDIUM_PRIORITY / 2 = HIGH_PRIORITY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful request to create the task"),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation error on mandatory fields", content = @Content)
    })
    public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest taskRequest){
        TaskResponse taskResponse = taskService.create(taskRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(taskResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(taskResponse);
    }

    @GetMapping(value = "/all")
    @Operation(summary = "List all tasks", tags = {"Task Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to list all tasks"),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content)
    })
    public ResponseEntity<List<TaskResponse>> findAll(){
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "List a task by ID", tags = {"Task Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to get a task by the ID"),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task ID not found", content = @Content)
    })
    public ResponseEntity<TaskResponse> findById(@PathVariable("id") @Parameter(name = "id", description = "Task id", example = "1") Long id){
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a task", tags = {"Task Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to update a task by the ID"),
            @ApiResponse(responseCode = "400", description = "Validation on mandatory fields", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task ID not found", content = @Content)
    })
    public ResponseEntity<TaskResponse> update(@RequestBody TaskRequest taskRequest, @PathVariable Long id){
        return ResponseEntity.ok(taskService.update(taskRequest, id));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a task", tags = {"Task Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful request to delete a task by the ID"),
            @ApiResponse(responseCode = "400", description = "User not allowed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task ID not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/complete-task")
    @Operation(summary = "Complete a task", tags = {"Task Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful request to complete the task"),
            @ApiResponse(responseCode = "400", description = "User not allowed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task ID not found", content = @Content)
    })
    public ResponseEntity<Void> completeTask(@PathVariable Long id){
        taskService.completeTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "uncompleted")
    @Operation(summary = "List the uncompleted tasks of the logged in user", description = "May be filter by the priority as URL parameter (-1 = no filter / 0 = LOW_PRIORITY / 1 = MEDIUM_PRIORITY / 2 = HIGH_PRIORITY)", tags = {"Task Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to list the uncompleted tasks"),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content)
    })
    public ResponseEntity<List<TaskResponse>> getUncompletedTasks(@RequestParam(value = "priority", defaultValue = "-1") Integer priority) {
        List<TaskResponse> uncompletedTasks = taskService.getUncompletedTasks(priority);
        return ResponseEntity.ok(uncompletedTasks);
    }
}
