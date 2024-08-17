package com.todoapp.service;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.Task;
import com.todoapp.mapper.TaskMapper;
import com.todoapp.repository.TaskRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final Validator validator;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id " + id));
    }

    public Task createTask(@Valid final TaskRequest taskCreateRequest) {
        validateTaskRequest(taskCreateRequest);
        final var task = taskMapper.toEntity(taskCreateRequest);
        return taskRepository.save(task);
    }

    public Task updateTask(final Long id,
                           @Valid final TaskRequest updatedTask) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id " + id);
        }
        validateTaskRequest(updatedTask);
        final var task = taskMapper.toEntity(updatedTask);
        task.setId(id);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private void validateTaskRequest(TaskRequest taskRequest) {
        Set<ConstraintViolation<TaskRequest>> violations = validator.validate(taskRequest);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }
}