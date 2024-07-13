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
    @GetMapping("/get-group/{workspaceId}")
    public ResponseEntity<UserGroup> getSingleGroup(@PathVariable String workspaceId){
        return new ResponseEntity<>(groupService.getGroup(workspaceId),HttpStatus.FOUND);
    }

    // endpoint to create a group -
    @PostMapping("/create-group/{workSpaceId}")
    protected ResponseEntity<UserGroup> createGroup(@PathVariable String workSpaceId, @RequestBody UserGroup group){

        return new ResponseEntity<>(groupService.createGroup(workSpaceId,group),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/" +
            "{workSpaceId}{creatorId}/")
    protected ResponseEntity<String> deleteGroup(@PathVariable String workSpaceId ,@PathVariable String creatorId){
        return new ResponseEntity<>(groupService.deleteGroup(workSpaceId,creatorId),HttpStatus.OK);

    }

    @PutMapping("/add-member/{workspaceId}/{memberId}")
    protected ResponseEntity<UserGroup> addMember(@PathVariable String workspaceId, @PathVariable String memberId){
        return new ResponseEntity<>(groupService.addMembersToGroup(workspaceId, memberId),HttpStatus.OK);
    }

    @PutMapping("/remove-member/{workspaceId}/{memberId}")
    protected ResponseEntity<UserGroup> removeMember(@PathVariable String workspaceId, @PathVariable String memberId){
        return new ResponseEntity<>(groupService.removeMemberFromGroup(workspaceId,memberId),HttpStatus.OK);
    }

    @PutMapping("/update-groupName{workspaceId}")
    protected ResponseEntity<UserGroup> updateGroupName(@PathVariable String workspaceId, GroupNameDTO groupNameDto){
        String groupName = groupNameDto.getGroupName();
        return new ResponseEntity<>(groupService.updateGroupName(workspaceId,groupName),HttpStatus.OK);
    }

    // ----------------------------- Messaging------------------------

    @PostMapping("/send-groupMessage/{senderId}/{workspaceId}")
    protected ResponseEntity<String> sendMessage(@PathVariable String senderId, @PathVariable String workspaceId, @RequestBody Message message){
        return new ResponseEntity<>(groupService.sendMessage(workspaceId,senderId,message),HttpStatus.OK);
    }

}
