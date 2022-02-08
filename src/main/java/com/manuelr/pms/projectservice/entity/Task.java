package com.manuelr.pms.projectservice.entity;

import com.manuelr.pms.projectservice.enums.TaskStatus;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.*;
import java.time.LocalDate;

@MongoEntity(collection = "tasks")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @BsonId
    ObjectId id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 250)
    private String description;

    @JsonbTransient
    private ObjectId projectId;

    @NotNull
    @FutureOrPresent
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate beginDate;

    @NotNull
    @Future
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate endDate;

    @NotNull
    private TaskStatus status = TaskStatus.UNINITIATED;

//    @JsonbTransient
//    private ObjectId createdBy;

//    private List<Employee> participants;

}
