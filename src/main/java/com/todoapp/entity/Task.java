package com.todoapp.entity;

import com.todoapp.entity.enums.Priority;
import com.todoapp.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    @Size(max = 20, message = "Title cannot be longer than 20 characters")
    @Column(length = 20)
    private String title;

    private String description;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    @NotNull(message = "Deadline cannot be null")
    @Future(message = "Deadline must be in the future")
    private LocalDate deadline;

    @NotNull(message = "Status cannot be null")
    private Status status;
}