package com.niit.StarTaskz.service;

import com.niit.StarTaskz.model.collaboration_workspace.groups.Message;
import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import com.niit.StarTaskz.repository.CollaborationWorkspaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GroupService {
    @Autowired
    CollaborationWorkspaceRepo workspaceRepo;

    //  group creation
    public UserGroup createGroup(String workSpaceId,UserGroup group){
        WorkSpace userWorkspace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        group.setId(UUID.randomUUID().toString());
        group.setWorkSpace(workSpaceId);
        group.setMembers(new ArrayList<String>(List.of(userWorkspace.getCreator())));
        group.setMessages(new ArrayList<Message>());
        userWorkspace.setGroup(group);
        workspaceRepo.save(userWorkspace);
        return userWorkspace.getGroup();


    }

    // delete group
    public String deleteGroup(String workSpaceId, String creatorId){
        WorkSpace workSpace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        if(workSpace.getCreator().equals(creatorId)){
        workSpace.setGroup(null);
        workspaceRepo.save(workSpace);
        return "group deleted!";
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"you are not the creator");
        }
    }

    // get group
    public UserGroup getGroup(String workspaceId){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        return workSpace.getGroup();
    }

    public UserGroup updateGroupName(String workspaceId,String groupName){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        workSpace.getGroup().setGroupName(groupName);
        workspaceRepo.save(workSpace);
        return workSpace.getGroup();
    }

    public UserGroup addMembersToGroup(String workspaceId,String memberId){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = workSpace.getGroup();
        group.getMembers().add(memberId);
        workspaceRepo.save(workSpace);
        return workSpace.getGroup();
    }

    public UserGroup removeMemberFromGroup(String workspaceId, String member){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = workSpace.getGroup();
        group.getMembers().remove(member);
        workspaceRepo.save(workSpace);
        return workSpace.getGroup();
    }


    public String sendMessage(String workspaceId,String senderId, Message message){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = workSpace.getGroup();
        List<Message> groupMessages = group.getMessages();
        message.setId(UUID.randomUUID().toString());
        message.setSenderId(senderId);
        message.setReadBy(new ArrayList<>(List.of(senderId)));
        message.setMessageDateTime(LocalDateTime.now());
        groupMessages.add(message);
        group.setMessages(groupMessages);
        workspaceRepo.save(workSpace);
        return "message sent";
    }
}
