package com.niit.StarTaskz.service;

import com.niit.StarTaskz.model.collaboration_workspace.groups.Message;
import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import com.niit.StarTaskz.model.user.User;
import com.niit.StarTaskz.repository.CollaborationWorkspaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class GroupService {
    @Autowired
    CollaborationWorkspaceRepo workspaceRepo;

    //  group creation
    public UserGroup createGroup(String workSpaceId,UserGroup group,String creatorId){
        WorkSpace userWorkspace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NO_CONTENT,"workspace not found!"));
        if(userWorkspace.getCreator().equals(creatorId)) {
            group.setId(UUID.randomUUID().toString());
            group.setWorkSpace(workSpaceId);
            group.setMembers(new ArrayList<>(List.of(userWorkspace.getId())));
            group.setMessages(new ArrayList<Message>());
            group.setCreatedAt(LocalDateTime.now());
            userWorkspace.getGroups().add(group);
            workspaceRepo.save(userWorkspace);
            for (UserGroup createdGroup : userWorkspace.getGroups()) {
                if (createdGroup.getId().equals(group.getId())) {
                    return createdGroup;
                }

            }
        }
        return null;


    }

    // delete group
    public String deleteGroup(String workSpaceId, String creatorId){
        WorkSpace workSpace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        if(workSpace.getCreator().equals(creatorId)){
        workSpace.setGroups(new ArrayList<>());
        workspaceRepo.save(workSpace);
        return "group deleted!";
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"you are not the creator");
        }
    }

    // get group
    public UserGroup getGroup(String workspaceId, String groupId){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        List<UserGroup> groups = workSpace.getGroups();
        for (UserGroup group : groups){
            if(group.getId().equals(groupId)){
                return group;
            }
        }
        return null;
    }

    public UserGroup updateGroupName(String workspaceId,String groupId,String groupName){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = getGroup(workspaceId,groupId);
        group.setGroupName(groupName);
        workspaceRepo.save(workSpace);
        return group;
    }

    public UserGroup addMembersToGroup(String workspaceId,String groupId,String memberId){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = getGroup(workspaceId,groupId);
        group.getMembers().add(memberId);
        workspaceRepo.save(workSpace);
        return group;
    }

    public UserGroup removeMemberFromGroup(String workspaceId,String groupId, String member){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = getGroup(workspaceId,groupId);
        group.getMembers().remove(member);
        workspaceRepo.save(workSpace);
        return group;
    }


    public String sendMessage(String workspaceId,String groupId,String senderId, Message message){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        UserGroup group = getGroup(workspaceId,groupId);
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