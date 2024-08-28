package com.todoapp.dto;

import com.todoapp.entity.enums.Priority;
import com.todoapp.entity.enums.Status;
import java.time.LocalDate;

public record TaskRequest(
        String title,
        String description,
        Priority priority,
        LocalDate deadline,
        Status status
) {}