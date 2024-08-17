package com.todoapp.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.Task;
import com.todoapp.entity.task.enums.Priority;
import com.todoapp.entity.task.enums.Status;
import com.todoapp.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Nested
    class WhenTasksExist {

        @Test
        void shouldReturnTasks() throws Exception {
            final List<Task> tasks = List.of(new Task(1L, "Title", "Description", Priority.HIGH, LocalDate.now(), Status.PENDING));
            when(taskService.getAllTasks()).thenReturn(tasks);

            mockMvc.perform(get("/api/tasks"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].title").value("Title"));
        }

        @Test
        void shouldReturnTask() throws Exception {
            final Task task = new Task(1L, "Title", "Description", Priority.HIGH, LocalDate.now(), Status.PENDING);
            when(taskService.getTaskById(1L)).thenReturn(task);

            mockMvc.perform(get("/api/tasks/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("Title"));
        }
    }

    @Nested
    class WhenCreatingOrUpdatingTask {

        @Test
        void shouldCreateTask() throws Exception {
            final TaskRequest taskRequest = new TaskRequest("Title", "Description", Priority.HIGH, LocalDate.now(), Status.PENDING);
            final Task task = new Task(1L, "Title", "Description", Priority.HIGH, LocalDate.now(), Status.PENDING);
            when(taskService.createTask(taskRequest)).thenReturn(task);

            mockMvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(taskRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("Title"));
        }

        @Test
        void shouldUpdateTask() throws Exception {
            final TaskRequest taskRequest = new TaskRequest("Updated Title", "Updated Description", Priority.MEDIUM, LocalDate.now(), Status.COMPLETED);
            final Task updatedTask = new Task(1L, "Updated Title", "Updated Description", Priority.MEDIUM, LocalDate.now(), Status.COMPLETED);
            when(taskService.updateTask(1L, taskRequest)).thenReturn(updatedTask);

            mockMvc.perform(put("/api/tasks/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(taskRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("Updated Title"));
        }
    }

    @Test
    void shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(1L);
    }
}