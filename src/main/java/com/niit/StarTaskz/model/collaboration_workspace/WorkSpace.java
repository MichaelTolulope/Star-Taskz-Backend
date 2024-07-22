package com.niit.StarTaskz.model.collaboration_workspace;

import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("collaboration-workspace")
public class WorkSpace {
    private String id;
    private String workSpaceTitle;
    private String workspaceImageUrl;
    private String workSpaceDescription;
    private String creator;
    private List<String> teamMembers;
    private List<UserGroup> groups;
    private List<CollaboratingTask> tasks;
    private LocalDateTime createdAt;
}
