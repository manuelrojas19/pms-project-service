package com.manuelr.pms.projectservice.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "projects")
public class Project {
    @BsonId
    private ObjectId id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    @JsonbTransient
    private ObjectId managerId;

//    private List<Employee> participants = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();

    @FutureOrPresent
    private LocalDate beginDate;

    @Future
    private LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
