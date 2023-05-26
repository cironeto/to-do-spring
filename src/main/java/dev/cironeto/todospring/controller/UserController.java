package dev.cironeto.todospring.controller;

import dev.cironeto.todospring.dto.UserRequest;
import dev.cironeto.todospring.dto.UserResponse;
import dev.cironeto.todospring.service.UserService;
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
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", tags = {"User Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful request to create the user"),
            @ApiResponse(responseCode = "400", description = "Validation error on mandatory fields or email conflict", content = @Content)
    })
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.create(userRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponse);
    }
    
    @GetMapping(value = "/all")
    @Operation(summary = "List all users", tags = {"User Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to list all users"),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content)
    })
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a user by the ID", tags = {"User Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to get a user by the ID"),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    public ResponseEntity<UserResponse> findById(@PathVariable("id") @Parameter(name = "id", description = "User id", example = "1") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a user", tags = {"User Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request to update a user by the ID"),
            @ApiResponse(responseCode = "400", description = "Validation on mandatory fields or email conflict", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest, @PathVariable Long id){
        return ResponseEntity.ok(userService.update(userRequest, id));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a user", tags = {"User Endpoints"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful request to delete a user by the ID"),
            @ApiResponse(responseCode = "400", description = "User related to a task that exists", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied to the resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "User ID not found", content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
