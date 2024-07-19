package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.collaboration_workspace.groups.Message;
import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import com.niit.StarTaskz.model.dto_classes.collaboration_workspace.GroupNameDTO;
import com.niit.StarTaskz.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("star-taskz/api/group")
@RestController
public class UserGroupController {
    @Autowired
    GroupService groupService;


    // endpoint to get single group created by user
    @GetMapping("/get-group/{workspaceId}/{groupId}")
    public ResponseEntity<UserGroup> getSingleGroup(@PathVariable String workspaceId, @PathVariable String groupId){
        return new ResponseEntity<>(groupService.getGroup(workspaceId,groupId),HttpStatus.FOUND);
    }

    // endpoint to create a group -
    @PostMapping("/create-group/{workSpaceId}/{creatorId}")
    protected ResponseEntity<UserGroup> createGroup(@PathVariable String workSpaceId, @RequestBody UserGroup group, @PathVariable String creatorId){

        return new ResponseEntity<>(groupService.createGroup(workSpaceId,group,creatorId),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/" +
            "{workSpaceId}/{groupId}/")
    protected ResponseEntity<String> deleteGroup(@PathVariable String workSpaceId ,@PathVariable String groupId){
        return new ResponseEntity<>(groupService.deleteGroup(workSpaceId,groupId),HttpStatus.OK);

    }

    @PutMapping("/add-member/{workspaceId}/{groupId}/{memberId}")
    protected ResponseEntity<UserGroup> addMember(@PathVariable String workspaceId,@PathVariable String groupId, @PathVariable String memberId){
        return new ResponseEntity<>(groupService.addMembersToGroup(workspaceId,groupId, memberId),HttpStatus.OK);
    }

    @PutMapping("/remove-member/{workspaceId}/{groupId}/{memberId}")
    protected ResponseEntity<UserGroup> removeMember(@PathVariable String workspaceId, @PathVariable String groupId, @PathVariable String memberId){
        return new ResponseEntity<>(groupService.removeMemberFromGroup(workspaceId,groupId,memberId),HttpStatus.OK);
    }

    @PutMapping("/update-groupName/{workspaceId}/{groupName}")
    protected ResponseEntity<UserGroup> updateGroupName(@PathVariable String workspaceId,@PathVariable String groupId, GroupNameDTO groupNameDto){
        String groupName = groupNameDto.getGroupName();
        return new ResponseEntity<>(groupService.updateGroupName(workspaceId,groupId,groupName),HttpStatus.OK);
    }

    // ----------------------------- Messaging------------------------

    @PostMapping("/send-groupMessage/{workspaceId}/{groupId}/{senderId}/")
    protected ResponseEntity<String> sendMessage(
            @PathVariable String workspaceId,
            @PathVariable String groupId,
            @PathVariable String senderId,
            @RequestBody Message message){
        return new ResponseEntity<>(groupService.sendMessage(workspaceId,groupId,senderId,message),HttpStatus.OK);
    }

}
