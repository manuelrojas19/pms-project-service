package com.manuelr.pms.projectservice.service.impl;

import com.manuelr.pms.projectservice.annotations.ProjectServiceImpl;
import com.manuelr.pms.projectservice.entity.Task;
import com.manuelr.pms.projectservice.exception.BadRequestException;
import com.manuelr.pms.projectservice.exception.NotFoundException;
import com.manuelr.pms.projectservice.repository.TaskRepository;
import com.manuelr.pms.projectservice.service.ProjectService;
import com.manuelr.pms.projectservice.service.TaskService;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;

@ApplicationScoped
@com.manuelr.pms.projectservice.annotations.TaskServiceImpl
public class TaskServiceImpl implements TaskService {
    public static final String NOT_FOUND_ERROR_MSG = "Task was not found";

    @Inject
    @ProjectServiceImpl
    ProjectService projectService;

    @Inject
    TaskRepository taskRepository;

    @Override
    public Uni<Task> findById(String id) {
        return taskRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));
    }

    @Override
    public Multi<Task> findAll() {
        return taskRepository.streamAll(Sort.by("id"));
    }

    @Override
    public Multi<Task> findAllByProject(String projectId) {
        return projectService.findById(projectId)
                .onFailure().transform(throwable -> new BadRequestException(throwable.getMessage()))
                .onItem().transformToMulti(project -> taskRepository.findAllByProject(project.getId()));
    }


    @Override
    public Uni<Task> save(Task task) {
        return projectService.findById(task.getProjectId().toString())
                .onFailure().transform(throwable -> new BadRequestException(throwable.getMessage()))
                .chain(project -> {
                    task.setProjectId(project.getId());
                    return taskRepository.persist(task)
                            .call(t -> {
                                project.getTasks().add(t);
                                return projectService.save(project);
                            });
                });
    }

    @Override
    public Multi<Task> deleteAllByProject(String projectId) {
        return taskRepository.findAllByProject(new ObjectId(projectId))
                .onItem().call(t -> taskRepository.delete(t));
    }

    @Override
    public Uni<Task> update(String id, Task task) {
        return taskRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException(NOT_FOUND_ERROR_MSG))
                .map(t -> {
                    t.setName(task.getName());
                    t.setDescription(task.getDescription());
                    t.setBeginDate(task.getBeginDate());
                    t.setEndDate(task.getEndDate());
                    return t;
                }).chain(t -> taskRepository.update(t));
    }

    @Override
    public Uni<Void> delete(String id) {
        return taskRepository.findById(new ObjectId(id))
                .onItem().ifNull().failWith(() -> new NotFoundException(NOT_FOUND_ERROR_MSG))
                .call(task -> projectService.findById(task.getProjectId().toString())
                        .call(project -> {
                            project.getTasks().remove(task);
                            return projectService.save(project);
                        }))
                .chain(task -> taskRepository.delete(task));
    }

    @Override
    public Uni<Long> deleteAll() {
        return projectService.findAll().call(project -> {
            project.setTasks(Collections.emptyList());
            return projectService.save(project);
        }).collect().asList().chain(t -> taskRepository.deleteAll());
    }
}
