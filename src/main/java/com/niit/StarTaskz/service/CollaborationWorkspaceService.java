package com.niit.StarTaskz.service;

import com.cloudinary.utils.ObjectUtils;
import com.niit.StarTaskz.configurations.CloudinaryConfig;
import com.niit.StarTaskz.model.user.User;
import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import com.niit.StarTaskz.repository.CollaborationWorkspaceRepo;
import com.niit.StarTaskz.repository.UserRepo;
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

@Service
public class CollaborationWorkspaceService {

    @Autowired
    CollaborationWorkspaceRepo workspaceRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

    @Autowired
    UserService userService;




    public String uploadWorkspaceImage(String workspaceId, MultipartFile file) throws IOException {
        WorkSpace workSpace = getSingleWorkSpace(workspaceId);
        Map uploadResult =cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        workSpace.setWorkspaceImageUrl(uploadResult.get("url").toString());
        workspaceRepo.save(workSpace);
        return workSpace.getWorkspaceImageUrl();
    }


    // creating a work-space
    public WorkSpace createWorkspace(WorkSpace workSpace, String creatorId){
        User user = userRepo.findById(creatorId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found!"));
         user.getWorkSpaces().add(workSpace.getId());
         userRepo.save(user);
         workSpace.setCreator(user.getId());
         workSpace.setTeamMembers(new ArrayList<>(List.of(user.getId())));
        workSpace.setCreatedAt(LocalDateTime.now());
        workSpace.setTasks(new ArrayList<>());
        workSpace.setGroups(new ArrayList<>());
        return workspaceRepo.save(workSpace);
    }


    // list all work-space
    public List<WorkSpace> getAllWorkSpace(String creator){
        return workspaceRepo.findByCreator(creator).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found"));
    }


    public WorkSpace getSingleWorkSpace(String workSpaceId){
        return workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"group not found!"));

    }
    public String deleteWorkSpace( String workSpace){
        WorkSpace workSpaceToDel = workspaceRepo.findById(workSpace).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"workspace not found!"));
        workspaceRepo.deleteById(workSpaceToDel.getId());
        User user = userRepo.findById(workSpaceToDel.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found!"));
        user.getWorkSpaces().remove(workSpaceToDel.getId());
        userRepo.save(user);
        return "work deleted!";
    }

    public WorkSpace updateWorkSpaceTitle(String workSpaceTitle, String workSpaceId){
       WorkSpace workSpace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"work space not found!"));
       workSpace.setWorkSpaceTitle(workSpaceTitle);
       return workspaceRepo.save(workSpace);

    }
    public WorkSpace updateWorkSpaceDescription(String workSpaceDescription, String workSpaceId){
       WorkSpace workSpace = workspaceRepo.findById(workSpaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"work space not found!"));
       workSpace.setWorkSpaceDescription(workSpaceDescription);
       return workspaceRepo.save(workSpace);

    }

    public WorkSpace removeMember(String workspaceId, String memberId){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"work space not found!"));
        workSpace.getTeamMembers().remove(memberId);
        User member = userService.getOneUser(memberId);
        member.getInvitedWorkSpaces().remove(workspaceId);
        return workspaceRepo.save(workSpace);
    }
    public WorkSpace addMember(String workspaceId, String memberId){
        WorkSpace workSpace = workspaceRepo.findById(workspaceId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"work space not found!"));
        workSpace.getTeamMembers().add(memberId);
        User member = userService.getOneUser(memberId);
        member.getInvitedWorkSpaces().add(workspaceId);
        return workspaceRepo.save(workSpace);

    }

}
