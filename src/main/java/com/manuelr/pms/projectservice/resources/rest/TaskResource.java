package com.manuelr.pms.projectservice.resources.rest;

import com.manuelr.pms.projectservice.annotations.TaskServiceImpl;
import com.manuelr.pms.projectservice.dto.TaskDto;
import com.manuelr.pms.projectservice.mapper.TaskMapper;
import com.manuelr.pms.projectservice.service.TaskService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.jboss.resteasy.reactive.RestResponse.Status;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;

@ApplicationScoped
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    @TaskServiceImpl
    TaskService taskService;

    @Inject
    TaskMapper taskMapper;


    @GET
    @Path("/tasks")
    public Multi<TaskDto> findAll() {
        return taskService.findAll().map(taskMapper::toDto);
    }

    @GET
    @Path("/tasks/{id}")
    public Uni<TaskDto> findById(@RestPath String id) {
        return taskService.findById(id).map(taskMapper::toDto);
    }

    @GET
    @Path("/projects/{projectId}/tasks")
    public Multi<TaskDto> findAllByProject(@RestPath String projectId) {
        return taskService.findAllByProject(projectId).map(taskMapper::toDto);
    }

    @POST
    @Path("/tasks")
    public Uni<RestResponse<TaskDto>> save(@Valid TaskDto taskDto) {
        return taskService.save(taskMapper.toEntity(taskDto)).map(taskMapper::toDto)
                .map(t -> ResponseBuilder.create(Status.CREATED, t)
                        .location(URI.create("/api/v1/tasks/" + t.getId())).build());
    }

    @PUT
    @Path("/tasks/{id}")
    public Uni<RestResponse<TaskDto>> update(@RestPath String id, @Valid TaskDto taskDto) {
        return taskService.update(id, taskMapper.toEntity(taskDto)).map(taskMapper::toDto)
                .map(p -> ResponseBuilder.create(Status.ACCEPTED, p)
                        .location(URI.create("/api/v1/tasks/" + p.getId())).build());
    }

    @DELETE
    @Path("/tasks/{id}")
    public Uni<RestResponse<?>> delete(@RestPath String id) {
        return taskService.delete(id).replaceWith(() -> ResponseBuilder
                .noContent().build());
    }

    @DELETE
    @Path("/tasks")
    public Uni<RestResponse<?>> deleteAll() {
        return taskService.deleteAll().replaceWith(() -> ResponseBuilder
                .noContent().build());
    }

}

