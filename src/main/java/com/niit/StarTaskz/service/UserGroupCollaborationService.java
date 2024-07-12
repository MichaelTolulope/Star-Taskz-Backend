package com.niit.StarTaskz.service;

import com.niit.StarTaskz.model.User;
import com.niit.StarTaskz.model.collaboration_groups.Message;
import com.niit.StarTaskz.model.collaboration_groups.UserGroup;
import com.niit.StarTaskz.repository.UserGroupsRepo;
import com.niit.StarTaskz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserGroupCollaborationService {

    @Autowired
    UserGroupsRepo userGroupsRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    public UserGroup createGroup(UserGroup group){
        User user = userRepo.findById(group.getCreator()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found!"));
         user.getGroups().add(group.getId());
         userRepo.save(user);
        return userGroupsRepo.save(group);
    }

    public List<UserGroup> getAllGroup(String creator){
        List<UserGroup> group = userGroupsRepo.findAll();
        List<UserGroup> groupForUser = new ArrayList<>();
        for(UserGroup userGroup : group){
            if(userGroup.getCreator().equals(creator)) {
                groupForUser.add(userGroup);
                return groupForUser;
            }
        }
        return null;
    }

    public UserGroup getSingleGroup(String groupId){
        return userGroupsRepo.findById(groupId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"group not found!"));

    }
    public String deleteGroup( String groupId){
        UserGroup group = userGroupsRepo.findById(groupId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"group not found!"));
        userGroupsRepo.deleteById(groupId);
        User user = userService.getOneUser(group.getCreator());
        List<String> groups = user.getGroups();
        groups.remove(groupId);
        userRepo.save(user);

        return "group deleted!";
    }

    public String sendMessage(String groupId, Message message){
        UserGroup group = getSingleGroup(groupId);
        List<Message> groupMessages = group.getMessages();
        groupMessages.add(message);
        group.setMessages(groupMessages);
        userGroupsRepo.save(group);
        return "message sent";
    }
}
