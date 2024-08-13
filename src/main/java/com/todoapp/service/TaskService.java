package com.todoapp.service;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.Task;
import com.todoapp.mapper.TaskMapper;
import com.todoapp.repository.TaskRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with id " + id));
    }

    public Task createTask(final TaskRequest taskCreateRequest) {
        final var task = taskMapper.toEntity(taskCreateRequest);
        return taskRepository.save(task);
    }

    public Task updateTask(final Long id,
                           final TaskRequest updatedTask) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found with id " + id);
        }
        final var task = taskMapper.toEntity(updatedTask);
        task.setId(id);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}