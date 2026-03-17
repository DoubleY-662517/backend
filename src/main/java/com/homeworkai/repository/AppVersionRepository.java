package com.homeworkai.repository;

import com.homeworkai.entity.AppVersion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppVersionRepository extends MongoRepository<AppVersion, String> {
    
    List<AppVersion> findByAppId(String appId);
    
    Optional<AppVersion> findByAppIdAndVersion(String appId, String version);
    
    List<AppVersion> findByAppIdOrderByCreatedAtDesc(String appId);
}
