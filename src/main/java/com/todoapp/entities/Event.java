package com.todoapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

@Data
//Generates getters for all fields, a useful toString method, and equals and hashCode methods.
// Also, it generates setters for all non-final fields.
@AllArgsConstructor
//Generates a constructor with 1 parameter for each field in your class.
@NoArgsConstructor
//Generates a no-arguments constructor.
@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 40, message = "Title cannot be longer than 40 characters")
    private String title;

    @Column(nullable = false)
    private String description;

    @FutureOrPresent(message = "End date and time cannot be in the past")
    private LocalDateTime startDateTime;

    @Future(message = "End date and time cannot be in the past or present")
    private LocalDateTime endDateTime;


}
