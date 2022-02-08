package com.manuelr.pms.projectservice.service;

import com.manuelr.pms.projectservice.entity.Task;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface TaskService {
    Multi<Task> findAll();

    Multi<Task> findAllByProject(String projectId);

    Uni<Task> findById(String id);

    Uni<Task> save(Task task);

    Uni<Task> update(String id, Task task);

    Uni<Void> delete(String id);

    Multi<Task> deleteAllByProject(String id);

    Uni<Long> deleteAll();
}
