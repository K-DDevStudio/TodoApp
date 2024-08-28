package com.todoapp.repository;

import com.todoapp.entity.Task;
import com.todoapp.entity.enums.Priority;
import com.todoapp.entity.enums.Status;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static com.todoapp.test_utils.TestTaskDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Nested
    class SaveAndFindTests {

        @Test
        void shouldSaveAndFindTaskById() {
            final Task task = createDefaultTask();
            final Task savedTask = taskRepository.save(task);

            final Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

            assertThat(foundTask).isPresent();
            assertThat(foundTask.get().getTitle()).isEqualTo("Default Task Name");
            assertThat(foundTask.get().getDescription()).isEqualTo("Default Description");
            assertThat(foundTask.get().getPriority()).isEqualTo(Priority.MEDIUM);
            assertThat(foundTask.get().getStatus()).isEqualTo(Status.PENDING);
        }
    }

    @Nested
    class FindAllTests {

        @Test
        void shouldFindAllTasks() {
            taskRepository.saveAll(createDefaultTaskList(2));

            final List<Task> tasks = taskRepository.findAll();

            assertThat(tasks).hasSize(2);
            assertThat(tasks).extracting(Task::getTitle).containsExactlyInAnyOrder("Task1", "Task2");
        }
    }

    @Nested
    class DeleteTests {

        @Test
        void shouldDeleteTaskById() {
            final Task task = createDefaultTask();
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
            final Task task = createDefaultTask();
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