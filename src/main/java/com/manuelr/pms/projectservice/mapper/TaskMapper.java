package com.manuelr.pms.projectservice.mapper;

import com.manuelr.pms.projectservice.dto.TaskDto;
import com.manuelr.pms.projectservice.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {DataMapper.class}, componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {
    Task toEntity(TaskDto dto);
    TaskDto toDto(Task task);
}
