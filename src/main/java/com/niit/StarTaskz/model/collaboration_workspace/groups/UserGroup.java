package com.niit.StarTaskz.model.collaboration_workspace.groups;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class UserGroup {
    @Id
    private String id;
    private String workSpace;
    private String groupName;
    private List<String> members;
    private List<Message> messages;
}
