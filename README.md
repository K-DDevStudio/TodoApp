# TodoApp: Note Management Application

## Overview

TodoApp is a Java-based note management application designed to help users create, update, and manage their notes efficiently. The application is built using a layered architecture, with separate packages for controllers, DTOs, entities, enums, repositories, exceptions, and mappers. The primary focus of TodoApp is to allow users to manage their tasks and events effectively, tracking their status and priority.

## Project Structure

The project is organized into the following packages:

1. **controller**
    - `EventController`: Manages HTTP requests related to `Event` entities. It provides endpoints to create, update, delete, and retrieve events.
    - `TaskController`: Handles HTTP requests related to `Task` entities. It includes endpoints for creating, updating, deleting, and retrieving tasks.

2. **dto**
    - `TaskRequest`: A Data Transfer Object (DTO) used to encapsulate the data required to create or update a `Task`. This class typically includes fields such as `name`, `description`, `priority`, and `status`.

3. **entity**
    - `Event`: Represents an event entity with attributes like `id`, `name`, `date`, and `location`. This class maps to the database table for storing event data.
    - `Task`: Represents a task entity with attributes such as `id`, `name`, `description`, `priority`, and `status`. This class maps to the database table for storing task data.

4. **enums**
    - `Priority`: Enum class defining the possible priorities for a `Task` (e.g., `LOW`, `MEDIUM`, `HIGH`).
    - `Status`: Enum class defining the possible statuses for a `Task` (e.g., `PENDING`, `IN_PROGRESS`, `COMPLETED`).

5. **repository**
    - `EventRepository`: Provides CRUD operations for the `Event` entity, interacting with the database to perform these operations.
    - `TaskRepository`: Provides CRUD operations for the `Task` entity, interacting with the database to perform these operations.

6. **exception**
    - `TaskNotFoundException`: A custom exception thrown when a task is not found in the database. This exception is typically used in the service layer to handle scenarios where a requested task does not exist.

7. **mapper**
    - `TaskMapper`: Responsible for mapping between `Task` entities and `TaskRequest` DTOs. This mapper facilitates the conversion of data between the different layers of the application.

8. **mapper_event_interface**
    - `EventMapper`: An interface that defines the mapping logic for `Event` entities, potentially using an implementation class or third-party libraries like MapStruct for automatic mapping.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven or Gradle (depending on the build system used)

### Building the Project

To build the project, navigate to the project directory and use the following command:

```bash
mvn clean install
