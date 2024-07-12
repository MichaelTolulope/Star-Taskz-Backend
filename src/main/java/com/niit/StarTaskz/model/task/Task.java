package com.niit.StarTaskz.model.task;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Task {
    private String id;
    private String taskName;
    private List<TaskSteps> steps;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Status status;
}

