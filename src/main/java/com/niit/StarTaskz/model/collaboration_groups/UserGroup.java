package com.niit.StarTaskz.model.collaboration_groups;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "collaboration-groups")
public class UserGroup {
    @Id
    private String id;
    private String creator;
    private String groupName;
    private List<String> members;
    private List<Message> messages;
}
