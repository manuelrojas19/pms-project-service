package com.manuelr.pms.projectservice.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@MongoEntity(collection = "projects")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project {

    @BsonId
    private ObjectId id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 250)
    private String description;


    @NotNull
    @FutureOrPresent
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate beginDate;

    @NotNull
    @Future
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate endDate;

    private List<Task> tasks = new ArrayList<>();

//    @NotNull
//    @JsonbTransient
//    private ObjectId managerId;

//    private List<Employee> participants = new ArrayList<>();

}
