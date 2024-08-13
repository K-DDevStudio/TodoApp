package com.todoapp.service;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.Task;
import com.todoapp.mapper.TaskMapper;
import com.todoapp.repository.TaskRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Validator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;
    private final Validator validator;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with id " + id));
    }

    public Task createTask(@Valid final TaskRequest taskCreateRequest) {
        validateTaskRequest(taskCreateRequest);
        final var task = taskMapper.toEntity(taskCreateRequest);
        return taskRepository.save(task);
    }

    public Task updateTask(final Long id,
                           @Valid final TaskRequest updatedTask) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found with id " + id);
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
        var violations = validator.validate(taskRequest);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
        }
    }
}