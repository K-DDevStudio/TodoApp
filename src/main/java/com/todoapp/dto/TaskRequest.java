package com.todoapp.dto;

import com.todoapp.entity.task.enums.Priority;
import com.todoapp.entity.task.enums.Status;
import java.time.LocalDate;

public record TaskRequest(
        String title,
        String description,
        Priority priority,
        LocalDate deadline,
        Status status
) {}