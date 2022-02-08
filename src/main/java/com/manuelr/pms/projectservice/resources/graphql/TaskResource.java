package com.manuelr.pms.projectservice.resources.graphql;

import com.manuelr.pms.projectservice.annotations.TaskServiceImpl;
import com.manuelr.pms.projectservice.dto.TaskDto;
import com.manuelr.pms.projectservice.mapper.TaskMapper;
import com.manuelr.pms.projectservice.service.TaskService;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import java.util.List;

@GraphQLApi
public class TaskResource {

    @Inject
    @TaskServiceImpl
    TaskService taskService;

    @Inject
    TaskMapper taskMapper;

    @Query("allTasks")
    @Description("Get all Projects")
    public Uni<List<TaskDto>> findAll() {
        return taskService.findAll()
                .map(taskMapper::toDto).collect().asList();
    }

    @Query("task")
    @Description("Get a Task")
    public Uni<TaskDto> find(@Name("id") String id) {
        return taskService.findById(id).map(taskMapper::toDto);
    }

}
