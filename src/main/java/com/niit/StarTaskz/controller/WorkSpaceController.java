package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import com.niit.StarTaskz.service.CollaborationWorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("star-taskz/api/workSpace")
public class WorkSpaceController {

    @Autowired
    CollaborationWorkspaceService workspaceService;


    @GetMapping("/all/{userId}")
    protected ResponseEntity<List<WorkSpace>> getAllWorkspace(@PathVariable String userId){
       return new ResponseEntity<>( workspaceService.getAllWorkSpace(userId), HttpStatus.FOUND);
    }

    @GetMapping("/single-workspace/{workSpaceId}")
    public ResponseEntity<WorkSpace> getOnWorkSpace(@PathVariable String workSpaceId){
        return new ResponseEntity<>(workspaceService.getSingleWorkSpace(workSpaceId),HttpStatus.FOUND);
    }

    @PostMapping("/create-workSpace/{userId}")
    public ResponseEntity<WorkSpace> createWorkSpace(@PathVariable String userId, @RequestBody WorkSpace workSpace){
        return  new ResponseEntity<>(workspaceService.createWorkspace(workSpace,userId),HttpStatus.CREATED);
    }

    @PostMapping("/upload-workspaceImage/{workspaceId}")
    public ResponseEntity<WorkSpace> uploadImage(@RequestParam("file") MultipartFile file,
                                              @PathVariable String workspaceId) {
        try {
            WorkSpace workSpace = workspaceService.uploadWorkspacePic(workspaceId,file);
            return ResponseEntity.ok(workSpace);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/update-Title/{workSpaceId}")
    public ResponseEntity<WorkSpace> updateWorkspaceTitle(@PathVariable String workSpaceId){
        return new ResponseEntity<>(workspaceService.updateWorkSpaceTitle(workSpaceId,workSpaceId),HttpStatus.OK);
    }

    // delete workspace Tested & Trusted
    @DeleteMapping("/delete/{workSpaceId}")
    public ResponseEntity<String> deleteWorkSpace(@PathVariable String workSpaceId){
        return new ResponseEntity<>(workspaceService.deleteWorkSpace(workSpaceId),HttpStatus.OK);
    }
// remove Members Tested & Trusted
    @PutMapping("/remove-member/{workspaceId}/{userId}")
    protected ResponseEntity<WorkSpace> removeMember(@PathVariable String workspaceId, @PathVariable String userId){
        return new ResponseEntity<>(workspaceService.removeMember(workspaceId,userId),HttpStatus.OK);
    }
//  add members Tested & Trusted
    @PutMapping("/add-member/{workspaceId}/{userId}")
    protected ResponseEntity<WorkSpace> addMember(@PathVariable String workspaceId, @PathVariable String userId){
        return new ResponseEntity<>(workspaceService.addMember(workspaceId,userId),HttpStatus.OK);
    }
}
