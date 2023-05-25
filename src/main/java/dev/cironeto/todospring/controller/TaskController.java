package dev.cironeto.todospring.controller;

import dev.cironeto.todospring.dto.TaskRequest;
import dev.cironeto.todospring.dto.TaskResponse;
import dev.cironeto.todospring.service.TaskService;
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
    public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest taskRequest){
        TaskResponse taskResponse = taskService.create(taskRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(taskResponse.id()).toUri();
        return ResponseEntity.created(uri).body(taskResponse);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TaskResponse>> findAll(){
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskResponse> update(@RequestBody TaskRequest taskRequest, @PathVariable Long id){
        return ResponseEntity.ok(taskService.update(taskRequest, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable Long id){
        taskService.completeTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "uncompleted")
    public ResponseEntity<List<TaskResponse>> getUncompletedTasks(@RequestParam(value = "priority", defaultValue = "-1") Integer priority) {
        List<TaskResponse> uncompletedTasks = taskService.getUncompletedTasks(priority);
        return ResponseEntity.ok(uncompletedTasks);
    }
}
