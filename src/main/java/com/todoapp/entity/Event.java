package com.todoapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Generates getters for all fields, a useful toString method, and equals and hashCode methods.
// Also, it generates setters for all non-final fields.
@Data
//Generates a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
//Generates a no-arguments constructor.
@NoArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event {

    @Id
    private Long id;

    private String title;

    private String description;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}