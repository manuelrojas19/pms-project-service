package com.manuelr.pms.projectservice.service;

import com.manuelr.pms.projectservice.entity.Project;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ProjectService {
    Multi<Project> findAll();

    Multi<Project> findAllByManager(String managerId);

    Uni<Project> findById(String id);

    Uni<Project> save(Project project);

    Uni<Project> update(String id, Project project);

    Uni<Void> delete(String id);
}
