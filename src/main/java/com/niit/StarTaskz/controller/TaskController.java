package com.niit.StarTaskz.controller;

import com.niit.StarTaskz.model.dto_classes.StatusDTO;
import com.niit.StarTaskz.model.dto_classes.task.DateTimeDTO;
import com.niit.StarTaskz.model.dto_classes.task.TaskCategoryDTO;
import com.niit.StarTaskz.model.dto_classes.task.TaskNameDTO;
import com.niit.StarTaskz.model.task.Status;
import com.niit.StarTaskz.model.task.Task;
import com.niit.StarTaskz.model.task.TaskSteps;
import com.niit.StarTaskz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("star-taskz/api/task")
public class TaskController {

    @Autowired
    UserService userService;

    // endpoint to get all tasks for user - Tested & Trusted
    @GetMapping("/all/{userId}")
    protected ResponseEntity<List<Task>> getAllTasks(@PathVariable String userId){
        return new ResponseEntity<>(userService.getAllTasks(userId), HttpStatus.OK);
    }

    // endpoint to get single task - Tested and Trusted
    @GetMapping("/single-task/{userId}/{taskId}")
    protected ResponseEntity<Task> getSingleTask(@PathVariable String userId,@PathVariable String taskId){
        return new ResponseEntity<>(userService.getSingleTask(userId,taskId), HttpStatus.OK);
    }

    // endpoint to created task - Tested and Trusted
    @PostMapping("/add/{userId}")
    protected ResponseEntity<Task> addTask(@PathVariable String userId,@RequestBody Task task){
        task.setCreatedAt(LocalDateTime.now());
        task.setId(UUID.randomUUID().toString());
        task.setSteps(new ArrayList<>());
        task.setStatus(Status.pending);
        return new ResponseEntity<>(userService.addTask(userId,task),HttpStatus.OK);
    }

    // endpoint to update task - Tested & Trusted
    @PutMapping("/update-status/{userId}/{taskId}")
    protected ResponseEntity<Task> updateStatus(@PathVariable String userId, @PathVariable String taskId, @RequestBody StatusDTO statusEntity){
        Status status = statusEntity.getStatus();
        return new ResponseEntity<>(userService.updateStatus(userId,taskId,status),HttpStatus.OK);
    }

// endpoint to update task startTime - Tested & Trusted
    @PutMapping("/update-taskDateTime/{userId}/{taskId}")
    protected ResponseEntity<Task> updateTaskDateTime(@PathVariable String userId, @PathVariable String taskId,@RequestBody DateTimeDTO dateTimeEntity){
        LocalDateTime dateTime = dateTimeEntity.getStartedAt();
        return new ResponseEntity<>(userService.updateStartTime(userId, taskId, dateTime),HttpStatus.OK);
    }

    // endpoint to delete a task- Tested & Trusted
    @DeleteMapping("/delete-task/{userId}/{taskId}")
    protected ResponseEntity<List<Task>> deleteTask(@PathVariable String userId, @PathVariable String taskId){
       return new ResponseEntity<>(userService.deleteTask(userId,taskId), HttpStatus.OK);
    }


 //------------------------------ SUB TASKS
// endpoint for creating a sub-task - Tested & Trusted
    @PostMapping("/add-subTasks/{userId}/{taskId}")
    protected ResponseEntity<Task> addSubTask(@PathVariable String userId, @PathVariable String taskId, @RequestBody TaskSteps subTask){
        subTask.setId(UUID.randomUUID().toString());
        subTask.setStatus(Status.pending);
        return new ResponseEntity<>(userService.addSubTasks(userId,taskId,subTask),HttpStatus.OK);
    }

    // endpoint to update subTask status - Tested &  Trusted
    @PutMapping("/update-subTaskStatus/{userId}/{taskId}/{subTaskId}")
    protected ResponseEntity<Task> updateSubTask(@PathVariable String userId, @PathVariable String taskId, @PathVariable String subTaskId, @RequestBody StatusDTO statusEntity){
        Status status = statusEntity.getStatus();
        return new ResponseEntity<>(userService.updateTaskStepStatus(userId,taskId,subTaskId,status),HttpStatus.OK);
    }

    // endpoint to update taskName -Tested & Trusted
    @PutMapping("/update-taskName/{userId}/{taskId}")
    protected ResponseEntity<Task> updateTaskName(@PathVariable String userId, @PathVariable String taskId, @RequestBody TaskNameDTO taskNameEntity){
        String taskName = taskNameEntity.getTaskName();
        return new ResponseEntity<>(userService.updateTaskName(userId,taskId,taskName), HttpStatus.OK);
    }
    @PutMapping("/update-taskCategory/{userId}/{taskId}")
    protected ResponseEntity<Task> updateTaskCategory(@PathVariable String userId, @PathVariable String taskId, @RequestBody TaskCategoryDTO taskCategoryEntity){
        String taskName = taskCategoryEntity.getTaskCategory();
        return new ResponseEntity<>(userService.updateTaskCategory(userId,taskId,taskName), HttpStatus.OK);
    }

 // endpoint to delete a subTask - Tested & Trusted
    @DeleteMapping("/delete-subTask/{userId}/{taskId}/{subTaskId}")
    protected ResponseEntity<Task> deleteSubTask(@PathVariable String userId, @PathVariable String taskId, @PathVariable String subTaskId){
        return new ResponseEntity<>(
                userService.deleteTaskStep(userId,taskId,subTaskId),HttpStatus.OK
        );
    }

}
