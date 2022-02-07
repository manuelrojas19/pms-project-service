package com.manuelr.pms.projectservice.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.json.bind.annotation.JsonbTransient;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "managers")
public class Manager {
    @BsonId
    private ObjectId id;

    @JsonbTransient
    List<Project> projects = new ArrayList<>();

//    @JsonbTransient
//    List<Employee> subordinates = new ArrayList<>();

}
