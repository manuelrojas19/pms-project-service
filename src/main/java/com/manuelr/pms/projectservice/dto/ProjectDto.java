package com.manuelr.pms.projectservice.dto;

import com.manuelr.pms.projectservice.entity.Employee;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@JsonbPropertyOrder({"id", "name", "description", "managerId", "beginDate", "endDate"})
public class ProjectDto {
    @Null
    private String id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 250)
    private String description;

    @NotBlank
    private String managerId;

    @Null
    private List<TaskDto> tasks;

    @Null
    private List<Employee> participants;

    @NotNull
    @FutureOrPresent
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate beginDate;

    @NotNull
    @Future
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate endDate;

}
