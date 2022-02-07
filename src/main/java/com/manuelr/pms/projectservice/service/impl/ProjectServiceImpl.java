package com.manuelr.pms.projectservice.service.impl;

import com.manuelr.pms.projectservice.entity.Project;
import com.manuelr.pms.projectservice.exception.NotFoundException;
import com.manuelr.pms.projectservice.repository.ProjectRepository;
import com.manuelr.pms.projectservice.service.ProjectService;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@com.manuelr.pms.projectservice.annotations.ProjectServiceImpl
public class ProjectServiceImpl implements ProjectService {

    @Inject
    ProjectRepository projectRepository;

    public Multi<Project> findAll() {
        return projectRepository.streamAll(Sort.by("name"));
    }

    @Override
    public Multi<Project> findAllByManager(String managerId) {
        return projectRepository.findAllByManager(new ObjectId(managerId));
    }


    public Uni<Project> findById(String id) {
        return projectRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException("Project was not found"));
    }

    public Uni<Project> save(Project project) {
        return projectRepository.persistOrUpdate(project);
    }

    public Uni<Project> update(String id, Project project) {
        return projectRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException("Project was not found"))
                .map(p -> {
                    p.setName(project.getName());
                    p.setDescription(project.getDescription());
                    p.setBeginDate(project.getBeginDate());
                    p.setEndDate(project.getEndDate());
                    return p;
                }).chain(p -> projectRepository.update(p));
    }

    public Uni<Void> delete(String id) {
        return projectRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException("Project was not found"))
                .chain(p -> projectRepository.delete(p));
    }
}
