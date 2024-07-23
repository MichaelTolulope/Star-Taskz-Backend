package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.collaboration_workspace.groups.Message;
import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import com.niit.StarTaskz.model.dto_classes.collaboration_workspace.GroupDescriptionDTO;
import com.niit.StarTaskz.model.dto_classes.collaboration_workspace.GroupNameDTO;
import com.niit.StarTaskz.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("star-taskz/api/group")
@RestController
public class UserGroupController {
    @Autowired
    GroupService groupService;


    // endpoint to get single group created by user
    @GetMapping("/get-group/{workspaceId}/{groupId}")
    public ResponseEntity<UserGroup> getSingleGroup(@PathVariable String workspaceId, @PathVariable String groupId){
        return new ResponseEntity<>(groupService.getGroup(workspaceId,groupId),HttpStatus.OK);
    }

    // endpoint to create a group -
    @PostMapping("/create-group/{workSpaceId}/{creatorId}")
    protected ResponseEntity<UserGroup> createGroup(@PathVariable String workSpaceId, @RequestBody UserGroup group, @PathVariable String creatorId){

        return new ResponseEntity<>(groupService.createGroup(workSpaceId,group,creatorId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/" +
            "{workSpaceId}/{creatorId}/{groupId}/")
    protected ResponseEntity<List<UserGroup>> deleteGroup(@PathVariable String workSpaceId ,@PathVariable String creatorId, @PathVariable String groupId){
        return new ResponseEntity<>(groupService.deleteGroup(workSpaceId,creatorId,groupId),HttpStatus.OK);

    }

    @PutMapping("/add-member/{workspaceId}/{groupId}/{memberId}")
    protected ResponseEntity<UserGroup> addMember(@PathVariable String workspaceId,@PathVariable String groupId, @PathVariable String memberId){
        return new ResponseEntity<>(groupService.addMembersToGroup(workspaceId,groupId, memberId),HttpStatus.OK);
    }

    @PutMapping("/remove-member/{workspaceId}/{groupId}/{memberId}")
    protected ResponseEntity<UserGroup> removeMember(@PathVariable String workspaceId, @PathVariable String groupId, @PathVariable String memberId){
        return new ResponseEntity<>(groupService.removeMemberFromGroup(workspaceId,groupId,memberId),HttpStatus.OK);
    }

    @PutMapping("/update-groupName/{workspaceId}/{groupId}")
    protected ResponseEntity<UserGroup> updateGroupName(@PathVariable String workspaceId,@PathVariable String groupId, @RequestBody GroupNameDTO groupNameDto){
        String groupName = groupNameDto.getGroupName();
        return new ResponseEntity<>(groupService.updateGroupName(workspaceId,groupId,groupName),HttpStatus.OK);
    }
    @PutMapping("/update-groupDescription/{workspaceId}/{groupId}")
    protected ResponseEntity<UserGroup> updateGroupDescription(@PathVariable String workspaceId, @PathVariable String groupId, @RequestBody GroupDescriptionDTO groupDescriptionDTO){
        String groupDescription = groupDescriptionDTO.getGroupDescription();
        return new ResponseEntity<>(groupService.updateGroupDescription(workspaceId,groupId,groupDescription),HttpStatus.OK);
    }

    @PostMapping("/upload-groupImage/{workspaceId}/{groupId}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @PathVariable String workspaceId, @PathVariable String groupId) {
        try {
            String imageUrl = groupService.uploadGroupImage(workspaceId,groupId,file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed");
        }
    }

    // ----------------------------- Messaging------------------------

    @PostMapping("/send-groupMessage/{workspaceId}/{groupId}/{senderId}")
    protected ResponseEntity<Message> sendMessage(@PathVariable String senderId,@PathVariable String groupId, @PathVariable String workspaceId, @RequestBody Message message){
        return new ResponseEntity<>(groupService.sendMessage(workspaceId,groupId,senderId,message),HttpStatus.OK);
    }

}
