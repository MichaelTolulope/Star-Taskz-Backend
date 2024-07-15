package com.niit.StarTaskz.service;

import com.niit.StarTaskz.model.collaboration_workspace.CollaboratingTask;
import com.niit.StarTaskz.model.collaboration_workspace.ProgressStatus;
import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import com.niit.StarTaskz.repository.CollaborationWorkspaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CollaboratingTaskService {
    @Autowired
    CollaborationWorkspaceService workspaceService;



    @Autowired
    CollaborationWorkspaceRepo workspaceRepo;

    public List<CollaboratingTask> createTask(String workspaceId,String creatorId, CollaboratingTask task){
        WorkSpace workspace = workspaceService.getSingleWorkSpace(workspaceId);
        if(workspace.getCreator().equals(creatorId)) {
            task.setProgressStatus(ProgressStatus.toDo);
            workspace.getTasks().add(task);
            return workspaceRepo.save(workspace).getTasks();
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "only admin can create tasks");
        }

    }
}
