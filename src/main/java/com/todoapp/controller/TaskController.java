package com.todoapp.controller;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.Task;
import com.todoapp.exception.TaskNotFoundException;
import com.todoapp.service.TaskService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        final var tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable final Long id) {
        try{
            final var task = taskService.getTaskById(id);
            return ResponseEntity.ok(task);
        } catch (TaskNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody final TaskRequest taskRequest) {
        try {
            final var task = taskService.createTask(taskRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(task);
        } catch (ConstraintViolationException ex) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable final Long id,
                                           @RequestBody final TaskRequest taskRequest) {
        try {
            final var updatedTask = taskService.updateTask(id, taskRequest);
            return ResponseEntity.ok(updatedTask);
        } catch (ConstraintViolationException ex) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable final Long id) {
        try{
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (TaskNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}