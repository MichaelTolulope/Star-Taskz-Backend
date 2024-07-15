package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.collaboration_workspace.CollaboratingTask;
import com.niit.StarTaskz.service.CollaboratingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/star-taskz/api/collaborating-task")
public class CollaboratingTaskController {

    @Autowired
    CollaboratingTaskService taskService;

    @PostMapping("/create-task{workspaceId}/{creatorId}")
    protected ResponseEntity<List<CollaboratingTask>> ceateCollabTask(@PathVariable String workspaceId,@PathVariable String creatorId,@RequestBody CollaboratingTask task){
       return new ResponseEntity<>(taskService.createTask(workspaceId,creatorId, task), HttpStatus.CREATED);
    }

}
