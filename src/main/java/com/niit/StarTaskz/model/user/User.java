package com.niit.StarTaskz.model.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@JsonSerialize(using = UserSerializer.class)
public class User {
    @Id
    private String id;
    private String profileImageUrl;
    private String email;
    private String firstName;
    private String lastName;
    private String accountType;
    private String jobTitle;

//    @JsonIgnore
    private String password;

    private Date dateOfBirth;
    private String role;
    private List<Task> userTasks;
    private List<String> workSpaces;



}
