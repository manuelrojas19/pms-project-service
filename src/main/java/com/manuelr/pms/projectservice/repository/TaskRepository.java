package com.manuelr.pms.projectservice.repository;

import com.manuelr.pms.projectservice.entity.Task;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskRepository implements ReactivePanacheMongoRepository<Task> {

    public Multi<Task> findAllByProject(ObjectId projectId) {
        return stream("projectId", projectId);
    }
}
