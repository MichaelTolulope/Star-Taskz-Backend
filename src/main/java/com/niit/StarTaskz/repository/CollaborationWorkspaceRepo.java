package com.niit.StarTaskz.repository;

import com.niit.StarTaskz.model.collaboration_workspace.groups.UserGroup;
import com.niit.StarTaskz.model.collaboration_workspace.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CollaborationWorkspaceRepo extends MongoRepository<WorkSpace,String> {
    public Optional<List<WorkSpace>> findByCreator(String creator);
}
