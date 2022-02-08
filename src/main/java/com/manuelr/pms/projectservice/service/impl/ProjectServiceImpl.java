package com.manuelr.pms.projectservice.service.impl;

import com.manuelr.pms.projectservice.annotations.TaskServiceImpl;
import com.manuelr.pms.projectservice.entity.Project;
import com.manuelr.pms.projectservice.entity.Task;
import com.manuelr.pms.projectservice.exception.NotFoundException;
import com.manuelr.pms.projectservice.repository.ProjectRepository;
import com.manuelr.pms.projectservice.service.ProjectService;
import com.manuelr.pms.projectservice.service.TaskService;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@com.manuelr.pms.projectservice.annotations.ProjectServiceImpl
public class ProjectServiceImpl implements ProjectService {
    public static final String NOT_FOUND_ERROR_MSG = "Project was not found";

    @Inject
    @TaskServiceImpl
    TaskService taskService;

    @Inject
    ProjectRepository projectRepository;

    @Override
    public Multi<Project> findAll() {
        return projectRepository.streamAll(Sort.by("name"));
    }


    @Override
    public Multi<Project> findAllByManager(String managerId) {
        return projectRepository.findAllByManager(new ObjectId(managerId));
    }


    public Uni<Project> findById(String id) {
        return projectRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));
    }

    public Uni<Project> save(Project project) {
        return projectRepository.persistOrUpdate(project);
    }

    public Uni<Project> update(String id, Project project) {
        return projectRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException(NOT_FOUND_ERROR_MSG))
                .map(p -> {
                    p.setName(project.getName());
                    p.setDescription(project.getDescription());
                    p.setBeginDate(project.getBeginDate());
                    p.setEndDate(project.getEndDate());
                    return p;
                }).chain(p -> projectRepository.update(p));
    }

    public Uni<Void> delete(String id) {
        Uni<Project> projectUni = projectRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));

        Multi<Task> tasksMulti = taskService.findAllByProject(id);

        return projectUni.call(project -> tasksMulti.onItem()
                        .call(task -> taskService.delete(task.getId().toString())).collect().asList())
                .chain(project -> projectRepository.delete(project));
    }

    public Uni<Long> deleteAll() {
        return taskService.deleteAll().chain(projectRepository::deleteAll);
    }
}
