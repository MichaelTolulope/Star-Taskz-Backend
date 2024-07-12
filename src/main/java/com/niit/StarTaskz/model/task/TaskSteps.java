package com.niit.StarTaskz.model.task;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
@JsonSerialize(using = TaskStepsSerializer.class)
@Data
public class TaskSteps {
    String id;
    String stepDescription;
    Status status;
}
