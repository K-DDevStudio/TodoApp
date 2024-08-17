package com.todoapp;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class TodoAppApplicationTests {

    @Nested
    class AppStartTest {

        @Test
        void shouldLoadContext() {
            String[] args = {};

            SpringApplicationBuilder builder = new SpringApplicationBuilder(TodoAppApplication.class);
            ServletWebServerApplicationContext context = (ServletWebServerApplicationContext) builder.run(args);

            assertThat(context).isNotNull();
            assertThat(context.getBean(TodoAppApplication.class)).isNotNull();
            context.close();
        }

        @Test
        void shouldNotThrowAnyException() {
            String[] args = {};

            TodoAppApplication.main(args);
        }
    }
}