package com.niit.StarTaskz.model.collaboration_workspace;

import com.niit.StarTaskz.model.task.Task;
import lombok.Data;

@Data
public class CollaboratingTask extends Task {
    private ProgressStatus progressStatus;
    private String assignedTo;
}
