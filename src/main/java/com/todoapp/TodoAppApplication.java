package com.todoapp;

import entities.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class TodoAppApplication {

    public static void main(String[] args) {

        Event event = Event.builder()
                        .id(1L)
                        .title("A")
                        .description("B")
                        .startDateTime(LocalDateTime.of(2024, 9, 12, 9, 0))
                        .endDateTime(LocalDateTime.of(2024, 9, 12, 17, 0))
                        .build();

        System.out.println(event);

        SpringApplication.run(TodoAppApplication.class, args);
    }

}
