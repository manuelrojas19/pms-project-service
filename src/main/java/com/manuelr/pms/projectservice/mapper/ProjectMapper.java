package com.manuelr.pms.projectservice.mapper;

import com.manuelr.pms.projectservice.dto.ProjectDto;
import com.manuelr.pms.projectservice.entity.Project;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {DataMapper.class}, componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);
    ProjectDto toDto(Project project);
}
