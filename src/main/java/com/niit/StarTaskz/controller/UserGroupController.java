package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.collaboration_groups.Message;
import com.niit.StarTaskz.model.collaboration_groups.UserGroup;
import com.niit.StarTaskz.repository.UserGroupsRepo;
import com.niit.StarTaskz.service.UserGroupCollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("star-taskz/api/group")
@RestController
public class UserGroupController {
    @Autowired
    UserGroupCollaborationService groupService;

    // endpoint to get groups created by user - Tested & Trusted
    @GetMapping("/user-groups/{creatorId}")
    public ResponseEntity<List<UserGroup>> getAllGroupsForUser(@PathVariable String creatorId){
      return new ResponseEntity<>(groupService.getAllGroup(creatorId), HttpStatus.FOUND);
    }

    // endpoint to get single group created by user -Tested & Trusted
    @GetMapping("/single-group/{groupId}")
    public ResponseEntity<UserGroup> getSingleGroup(@PathVariable String groupId){
        return new ResponseEntity<>(groupService.getSingleGroup(groupId),HttpStatus.FOUND);
    }

    // endpoint to create a group - Tested & Trusted
    @PostMapping("/create-group/{creatorId}")
    protected ResponseEntity<UserGroup> createGroup(@PathVariable String creatorId, @RequestBody UserGroup group){
        group.setId(UUID.randomUUID().toString());
        group.setCreator(creatorId);
        group.setMembers(new ArrayList<String>(List.of(creatorId)));
        group.setMessages(new ArrayList<Message>());
        return new ResponseEntity<>(groupService.createGroup(group),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/" +
            "{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable String groupId){
        return new ResponseEntity<>(groupService.deleteGroup(groupId),HttpStatus.OK);

    }

    // ----------------------------- Messaging------------------------

    @PostMapping("/send-groupMessage/{senderId}/{groupId}")
    protected ResponseEntity<String> sendMessage(@PathVariable String senderId, @PathVariable String groupId, @RequestBody Message message){
        message.setId(UUID.randomUUID().toString());
        message.setSenderId(senderId);
        message.setReadBy(new ArrayList<>(List.of(senderId)));
        message.setMessageDateTime(LocalDateTime.now());
        return new ResponseEntity<>(groupService.sendMessage(groupId,message),HttpStatus.OK);
    }

}
