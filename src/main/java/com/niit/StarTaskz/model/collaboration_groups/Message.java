package com.niit.StarTaskz.model.collaboration_groups;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Message {
    private String id;
    private String senderId;
    private LocalDateTime messageDateTime;
    private String messageContent;
    private List<String> readBy;
}
