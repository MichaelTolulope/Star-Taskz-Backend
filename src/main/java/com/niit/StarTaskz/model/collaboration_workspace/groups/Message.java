package com.niit.StarTaskz.model.collaboration_workspace.groups;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@JsonSerialize(using = MessageSerializer.class)
@Data
public class Message {
    private String id;
    private String senderId;
    private LocalDateTime messageDateTime;
    private String messageContent;
    private List<String> readBy;
}
