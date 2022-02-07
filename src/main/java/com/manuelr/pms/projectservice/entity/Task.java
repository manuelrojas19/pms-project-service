package com.manuelr.pms.projectservice.entity;

import com.manuelr.pms.projectservice.enums.TaskStatus;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "tasks")
public class Task implements Serializable {
    @BsonId
    private ObjectId id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 250)
    private String description;

    @JsonbTransient
    private ObjectId projectId;

//    private List<Employee> participants;

    @JsonbTransient
    private ObjectId createdBy;

    @FutureOrPresent
    private LocalDate beginDate;

    @Future
    private LocalDate endDate;

    @NotNull
    private TaskStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
