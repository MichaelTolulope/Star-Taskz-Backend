package com.niit.StarTaskz.service;

import com.cloudinary.utils.ObjectUtils;
import com.niit.StarTaskz.configurations.CloudinaryConfig;
import com.niit.StarTaskz.model.user.User;
import com.niit.StarTaskz.model.task.Status;
import com.niit.StarTaskz.model.task.Task;
import com.niit.StarTaskz.model.task.TaskSteps;
import com.niit.StarTaskz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CloudinaryConfig cloudinaryConfig;




    public String uploadProfilePic(String userId, MultipartFile file) throws IOException {
        User user = getOneUser(userId);
        Map uploadResult =cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        user.setProfileImageUrl(uploadResult.get("url").toString());
        userRepo.save(user);
        return user.getProfileImageUrl();
    }

    public User registerUser(User user) {
        user.setRole("user");
        user.setUserTasks(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setWorkSpaces(new ArrayList<>());
        return userRepo.save(user);
    }


    public User loginUser(String email, String password){
        User user = userRepo.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user with email not found!"));
        String encodedPassword = user.getPassword();
        if(passwordEncoder.matches(password,encodedPassword)){
            return user;
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials");
        }
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getOneUser(String id) {
        if (userRepo.findById(id).isPresent())
            return userRepo.findById(id).get();
        return null;
    }

    public User getOneUserByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found!"));
    }

    // update email
    public User updateEmail(String userId, String email) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        ;
        user.setEmail(email);
        return userRepo.save(user);
    }

    // update names
    public User updateNames(String userId, String firstName, String lastName) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!"));
        ;
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userRepo.save(user);
    }

    public User updatePassword(String userId, String password) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                ,"User not found!"));
        user.setPassword(password);
        userRepo.save(user);
        return user;
    }

    public User updateDateOfBirth(String userId, Date dateOfBirth) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!"));
        user.setDateOfBirth(dateOfBirth);
        return userRepo.save(user);
    }

    //--------------------------- TASKS -----------------------------

    // get all task for user
    public List<Task> getAllTasks(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        return user.getUserTasks();
    }

    // get one task
    public Task getSingleTask(String userId, String taskId){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found!"));
        for(Task task : user.getUserTasks()){
            if(task.getId().equals(taskId))
                return task;
        }
        return null;
    }

    // add a task
    public Task addTask(String userId, Task task) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));
        List<Task> userTasks = user.getUserTasks();
        userTasks.add(task);
        user.setUserTasks(userTasks);
        User updatedUser = userRepo.save(user);
        for (Task task1 : updatedUser.getUserTasks()) {
            if (task1.getTaskName().equals(task.getTaskName())) {
                return task;
            }
        }
        return null;
    }

    //update taskName
    public Task updateTaskName(String userId, String taskId, String taskName) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        Task specificTask;
        for (Task task : taskList) {
            if (task.getId().equals(taskId)) {
                specificTask = task;
                specificTask.setTaskName(taskName);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return specificTask;
            }
        }
        return null;
    }

    //update task status
    public Task updateStatus(String userId, String taskId, Status status) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        Task specificTask;
        for (Task task : taskList) {
            if (task.getId().equals(taskId)) {
                specificTask = task;
                if(status == Status.completed){
                    specificTask.setEndedAt(LocalDateTime.now());
                }
                specificTask.setStatus(status);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return specificTask;
            }
        }
        return null;
    }

    // update start time
    public Task updateStartTime(String userId, String taskId, LocalDateTime dateTime) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        Task specificTask;
        for (Task task : taskList) {
            if (task.getId().equals(taskId)) {
                specificTask = task;
                specificTask.setStartedAt(dateTime);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return specificTask;
            }
        }
        return null;
    }

    public List<Task> deleteTask(String userId, String taskId){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        taskList.removeIf(task -> task.getId().equals(taskId));
        user.setUserTasks(taskList);
        return userRepo.save(user).getUserTasks();
    }
// --------------------- Task Steps


    // add subtasks
    public Task addSubTasks(String userId, String taskId, TaskSteps subTask){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not Found!"));
        List<Task> taskList = user.getUserTasks();
        List<TaskSteps> subTasksList;
        for (Task task: taskList) {
            if (task.getId().equals(taskId)) {
                subTasksList = task.getSteps();
                subTasksList.add(subTask);
                task.setSteps(subTasksList);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return task;
            }
        }
        return null;
    }

    // update subtask status
    public Task updateTaskStepStatus(String userId, String taskId, String stepId, Status status) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        Task specificTask;
        for (Task task : taskList) {
            if (task.getId().equals(taskId)) {
                specificTask = task;
                List<TaskSteps> subTasks = specificTask.getSteps();
                for (TaskSteps step : subTasks)
                    if(step.getId().equals(stepId))
                        step.setStatus(status);
                specificTask.setSteps(subTasks);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return specificTask;
            }
        }
        return null;
    }

    // delete subTask
    public Task deleteTaskStep(String userId, String taskId, String stepId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));
        List<Task> taskList = user.getUserTasks();
        Task specificTask;
        for (Task task : taskList) {
            if (task.getId().equals(taskId)) {
                specificTask = task;
                List<TaskSteps> subTasks = specificTask.getSteps();
                subTasks.removeIf(step -> step.getId().equals(stepId));
                specificTask.setSteps(subTasks);
                user.setUserTasks(taskList);
                userRepo.save(user);
                return specificTask;
            }
        }
        return null;
    }

}
