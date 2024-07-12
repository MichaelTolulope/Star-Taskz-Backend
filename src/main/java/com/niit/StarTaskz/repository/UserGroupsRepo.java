package com.niit.StarTaskz.repository;

import com.niit.StarTaskz.model.collaboration_groups.UserGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserGroupsRepo extends MongoRepository<UserGroup,String> {
    public Optional<UserGroup> findByCreator(String creator);
}
