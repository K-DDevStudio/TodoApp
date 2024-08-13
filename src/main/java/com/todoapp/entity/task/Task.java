package com.todoapp.entity.task;

import com.todoapp.entity.task.enums.Priority;
import com.todoapp.entity.task.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String description;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    @NotNull(message = "Deadline cannot be null")
    @PastOrPresent(message = "Deadline must be in the future")
    private LocalDate deadline;

    @NotNull(message = "Status cannot be null")
    private Status status;
}
