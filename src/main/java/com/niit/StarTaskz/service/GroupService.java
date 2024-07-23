package com.niit.StarTaskz.service;

import com.cloudinary.utils.ObjectUtils;
import com.niit.StarTaskz.configurations.CloudinaryConfig;
import com.niit.StarTaskz.model.collaboration_workspace.groups.Message;
import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import com.niit.StarTaskz.model.user.User;
import com.niit.StarTaskz.repository.CollaborationWorkspaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
public class GroupService {
    @Autowired
    CollaborationWorkspaceRepo workspaceRepo;


    @Autowired
    CloudinaryConfig cloudinaryConfig;

    @Autowired
    CollaborationWorkspaceService workspaceService;

    @Autowired
    UserService userService;




    public String uploadGroupImage(String workspaceId,String groupId, MultipartFile file) throws IOException {
        WorkSpace workSpace = workspaceService.getSingleWorkSpace(workspaceId);
        List<UserGroup> groups = workSpace.getGroups();
        for(UserGroup group : groups) {
            if (group.getId().equals(groupId)) {
                Map uploadResult = cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                group.setGroupImageUrl(uploadResult.get("url").toString());
                workspaceRepo.save(workSpace);
                return group.getGroupImageUrl();
            }

        }
            return null;
    }

    //  group creation
    public UserGroup createGroup(String workSpaceId,UserGroup group,String creatorId){
        WorkSpace userWorkspace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NO_CONTENT,"workspace not found!"));
        if(userWorkspace.getCreator().equals(creatorId)) {
            group.setId(UUID.randomUUID().toString());
            group.setWorkSpace(workSpaceId);
            group.setMembers(new ArrayList<>(List.of(userWorkspace.getCreator())));
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
    public List<UserGroup> deleteGroup(String workSpaceId, String creatorId, String groupId){
        WorkSpace workSpace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        if(workSpace.getCreator().equals(creatorId)){
            UserGroup group = getGroup(workSpaceId,groupId);
        workSpace.getGroups().remove(group);
        WorkSpace updatedWorkspace = workspaceRepo.save(workSpace);
        return updatedWorkspace.getGroups();
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
        List<UserGroup> groups = workSpace.getGroups();
        for(UserGroup group : groups){
            if (group.getId().equals(groupId)) {
                group.setGroupName(groupName);
                workspaceRepo.save(workSpace);
                return group;

            }
        }
        return null;

    }
    public UserGroup updateGroupDescription(String workspaceId,String groupId,String groupDescription){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        List<UserGroup> groups = workSpace.getGroups();
        for(UserGroup group : groups){
            if (group.getId().equals(groupId)) {
                group.setGroupDescription(groupDescription);
                workspaceRepo.save(workSpace);
                return group;

            }
        }
        return null;

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


    public Message sendMessage(String workspaceId,String groupId,String senderId, Message message){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        List<UserGroup> groups = workSpace.getGroups();
        User sender = userService.getOneUser(senderId);
        for(UserGroup group : groups){
            if(group.getId().equals(groupId)){
                List<Message> groupMessages = group.getMessages();
                message.setId(UUID.randomUUID().toString());
                message.setSenderId(senderId);
                message.setSenderImage(sender.getProfileImageUrl());
                message.setSenderName(sender.getFirstName());
                message.setReadBy(new ArrayList<>(List.of(senderId)));
                message.setMessageDateTime(LocalDateTime.now());
                groupMessages.add(message);
                group.setMessages(groupMessages);
                workspaceRepo.save(workSpace);
            }

        }

        return message;
    }
}
