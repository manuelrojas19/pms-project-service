package com.manuelr.pms.projectservice.resources.rest;

import com.manuelr.pms.projectservice.annotations.ProjectServiceImpl;
import com.manuelr.pms.projectservice.dto.ProjectDto;
import com.manuelr.pms.projectservice.mapper.ProjectMapper;
import com.manuelr.pms.projectservice.service.ProjectService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.Status;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

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
public class ProjectResource {

    @Inject
    @ProjectServiceImpl
    ProjectService projectService;

    @Inject
    ProjectMapper projectMapper;

    @GET
    @Path("/projects")
    public Multi<ProjectDto> findAll() {
        return projectService.findAll().map(projectMapper::toDto);
    }

    @GET
    @Path("/managers/{managerId}/projects")
    public Multi<ProjectDto> findAllByManager(@RestPath String managerId) {
        return projectService.findAllByManager(managerId).map(projectMapper::toDto);
    }

    @GET
    @Path("/projects/{id}")
    public Uni<ProjectDto> findById(@RestPath String id) {
        return projectService.findById(id).map(projectMapper::toDto);
    }

    @POST
    @Path("/projects")
    public Uni<RestResponse<ProjectDto>> save(@Valid ProjectDto project) {
        return projectService.save(projectMapper.toEntity(project)).map(projectMapper::toDto)
                .map(p -> ResponseBuilder.create(Status.CREATED, p)
                        .location(URI.create("/api/v1/projects/" + p.getId())).build());
    }

    @PUT
    @Path("/projects/{id}")
    public Uni<RestResponse<ProjectDto>> update(@RestPath String id, @Valid ProjectDto project) {
        return projectService.update(id, projectMapper.toEntity(project)).map(projectMapper::toDto)
                .map(p -> ResponseBuilder.create(Status.ACCEPTED, p)
                        .location(URI.create("/api/v1/projects/" + p.getId())).build());
    }

    @DELETE
    @Path("/projects/{id}")
    public Uni<RestResponse<?>> delete(@RestPath String id) {
        return projectService.delete(id).replaceWith(() -> ResponseBuilder
                .noContent().build());
    }

    @DELETE
    @Path("/projects")
    public Uni<RestResponse<?>> deleteAll() {
        return projectService.deleteAll().replaceWith(() -> ResponseBuilder
                .noContent().build());
    }

}
