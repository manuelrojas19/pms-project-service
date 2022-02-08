package com.manuelr.pms.projectservice.resources.graphql;

import com.manuelr.pms.projectservice.annotations.ProjectServiceImpl;
import com.manuelr.pms.projectservice.dto.ProjectDto;
import com.manuelr.pms.projectservice.mapper.ProjectMapper;
import com.manuelr.pms.projectservice.service.ProjectService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@GraphQLApi
@ApplicationScoped
public class ProjectResource {

    @Inject
    @ProjectServiceImpl
    ProjectService projectService;

    @Inject
    ProjectMapper projectMapper;

    @Query("allProjects")
    @Description("Get all Projects")
    public Uni<List<ProjectDto>> findAll() {
        return projectService.findAll()
                .map(projectMapper::toDto).collect().asList();
    }

    @Query("project")
    @Description("Get a Project")
    public Uni<ProjectDto> find(@Name("id") String id) {
        return projectService.findById(id).map(projectMapper::toDto);
    }

}
