package com.todoapp.test_utils;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.Task;
import com.todoapp.entity.enums.Priority;
import com.todoapp.entity.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestTaskDataFactory {

    public static Task createTask(Long id, String name, String description, Priority priority, LocalDate dueDate, Status status) {
        return new Task(id, name, description, priority, dueDate, status);
    }

    public static TaskRequest createTaskRequest(String name, String description, Priority priority, LocalDate dueDate, Status status) {
        return new TaskRequest(name, description, priority, dueDate, status);
    }

    public static Task createDefaultTask() {
        return createTask(null, "Default Task Name", "Default Description", Priority.MEDIUM, LocalDate.now().plusDays(1), Status.PENDING);
    }

    public static TaskRequest createDefaultTaskRequest() {
        return createTaskRequest("Default Task Name", "Default Description", Priority.MEDIUM, LocalDate.now().plusDays(1), Status.PENDING);
    }

    public static List<Task> createDefaultTaskList(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> createTask((long) (i + 1), "Task" + (i + 1), "Desc" + (i + 1), Priority.MEDIUM, LocalDate.now().plusDays(1), Status.PENDING))
                .collect(Collectors.toList());
    }
}