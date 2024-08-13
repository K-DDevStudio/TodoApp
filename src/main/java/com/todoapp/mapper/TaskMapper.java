package com.todoapp.mapper;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskRequest toDto(Task task);

    Task toEntity(TaskRequest taskDTO);
}