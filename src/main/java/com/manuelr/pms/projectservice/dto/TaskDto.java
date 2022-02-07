package com.manuelr.pms.projectservice.dto;

import com.manuelr.pms.projectservice.enums.TaskStatus;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data

@JsonbPropertyOrder({"id", "name", "description", "projectId", "status", "beginDate", "endDate"})
public class TaskDto {

    @Null
    private String id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 250)
    private String description;

    @NotBlank
    private String projectId;

    @Null
    private TaskStatus status;

    @NotNull
    @FutureOrPresent
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate beginDate;

    @NotNull
    @Future
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate endDate;

}
