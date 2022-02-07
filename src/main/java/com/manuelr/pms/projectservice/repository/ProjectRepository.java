package com.manuelr.pms.projectservice.repository;

import com.manuelr.pms.projectservice.entity.Project;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectRepository implements ReactivePanacheMongoRepository<Project> {

    public Multi<Project> findAllByManager(ObjectId managerId) {
        return stream("managerId", managerId);
    }
}
