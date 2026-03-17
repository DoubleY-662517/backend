package com.homeworkai.repository;

import com.homeworkai.entity.AppApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppApplicationRepository extends MongoRepository<AppApplication, String> {
    
    Page<AppApplication> findByUserId(String userId, Pageable pageable);
    
    Page<AppApplication> findByDeletedIsNull(Pageable pageable);
    
    Page<AppApplication> findByUserIdAndDeletedIsNull(String userId, Pageable pageable);
    
    List<AppApplication> findByStatus(String status);
}
