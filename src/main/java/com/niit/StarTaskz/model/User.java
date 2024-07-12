package com.niit.StarTaskz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niit.StarTaskz.model.task.Task;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@Document("user")
public class User {
    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;

//    @JsonIgnore
    private String password;

    private Date dateOfBirth;
    private String role;
    private List<Task> userTasks;
    private List<String> groups;



}
