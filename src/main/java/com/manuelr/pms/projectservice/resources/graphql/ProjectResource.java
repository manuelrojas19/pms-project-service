package com.manuelr.pms.projectservice.resources.graphql;

import com.manuelr.pms.projectservice.annotations.ProjectServiceImpl;
import com.manuelr.pms.projectservice.dto.ProjectDto;
import com.manuelr.pms.projectservice.mapper.ProjectMapper;
import com.manuelr.pms.projectservice.service.ProjectService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.GraphQLApi;
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


@GraphQLApi
@ApplicationScoped
public class ProjectResource {

    @Inject
    @ProjectServiceImpl
    ProjectService projectService;

    @Inject
    ProjectMapper projectMapper;

    // TODO: Code GraphQL API

}
