package com.todoapp.entity.task;

import com.todoapp.entity.task.enums.Priority;
import com.todoapp.entity.task.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
    @PastOrPresent(message = "Deadline must be in the future")
    private LocalDate deadline;

    @NotNull(message = "Status cannot be null")
    private Status status;
}