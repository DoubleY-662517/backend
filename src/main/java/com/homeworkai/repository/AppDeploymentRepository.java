package com.homeworkai.repository;

import com.homeworkai.entity.AppDeployment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppDeploymentRepository extends MongoRepository<AppDeployment, String> {
    
    List<AppDeployment> findByAppId(String appId);
    
    List<AppDeployment> findByStatus(String status);
    
    Optional<AppDeployment> findFirstByAppIdOrderByCreatedAtDesc(String appId);
}
