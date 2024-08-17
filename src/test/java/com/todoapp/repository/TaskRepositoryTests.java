package com.todoapp.repository;

import com.todoapp.entity.task.Task;
import com.todoapp.entity.task.enums.Priority;
import com.todoapp.entity.task.enums.Status;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    private Task createTask(String title, String description, Priority priority, Status status) {
        final Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setDeadline(LocalDate.now());
        task.setStatus(status);
        return task;
    }

    @Nested
    class SaveAndFindTests {

        @Test
        void shouldSaveAndFindTaskById() {
            final Task task = createTask("Test Task", "Test Description", Priority.MEDIUM, Status.COMPLETED);

            final Task savedTask = taskRepository.save(task);
            final Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

            assertThat(foundTask).isPresent();
            assertThat(foundTask.get().getTitle()).isEqualTo("Test Task");
            assertThat(foundTask.get().getDescription()).isEqualTo("Test Description");
            assertThat(foundTask.get().getPriority()).isEqualTo(Priority.MEDIUM);
            assertThat(foundTask.get().getStatus()).isEqualTo(Status.COMPLETED);
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldFindAllTasks() {
            final Task task1 = createTask("Task 1", "Description 1", Priority.LOW, Status.PENDING);
            final Task task2 = createTask("Task 2", "Description 2", Priority.HIGH, Status.IN_PROGRESS);

            taskRepository.save(task1);
            taskRepository.save(task2);

            final List<Task> tasks = taskRepository.findAll();

            assertThat(tasks).hasSize(2);
            assertThat(tasks).extracting(Task::getTitle).containsExactlyInAnyOrder("Task 1", "Task 2");
            assertThat(tasks).extracting(Task::getStatus).containsExactlyInAnyOrder(Status.PENDING, Status.IN_PROGRESS);
        }
    }

    @Nested
    class DeleteTests {

        @Test
        void shouldDeleteTaskById() {
            final Task task = createTask("Task to delete", "Description", Priority.HIGH, Status.PENDING);

            final Task savedTask = taskRepository.save(task);

            taskRepository.deleteById(savedTask.getId());
            final Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

            assertThat(foundTask).isNotPresent();
        }
    }

    @Nested
    class UpdateTests {

        @Test
        void shouldUpdateTask() {
            final Task task = createTask("Original Title", "Original Description", Priority.MEDIUM, Status.PENDING);

            final Task savedTask = taskRepository.save(task);

            savedTask.setTitle("Updated Title");
            savedTask.setDescription("Updated Description");
            taskRepository.save(savedTask);

            final Optional<Task> updatedTask = taskRepository.findById(savedTask.getId());

            assertThat(updatedTask).isPresent();
            assertThat(updatedTask.get().getTitle()).isEqualTo("Updated Title");
            assertThat(updatedTask.get().getDescription()).isEqualTo("Updated Description");
        }
    }
}