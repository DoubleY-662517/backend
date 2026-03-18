package com.homeworkai.repository;

import com.homeworkai.entity.AppApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppApplicationRepository extends MongoRepository<AppApplication, String> {
    
    Page<AppApplication> findByUserId(String userId, Pageable pageable);
    
    Page<AppApplication> findByDeletedIsNull(Pageable pageable);
    
    Page<AppApplication> findByUserIdAndDeletedIsNull(String userId, Pageable pageable);
    
    List<AppApplication> findByStatus(String status);
    
    Page<AppApplication> findByUserIdAndStatusAndDeletedIsNull(String userId, String status, Pageable pageable);
    
    @Query("{ 'user_id': ?0, 'deleted': null, 'name': { $regex: ?1, $options: 'i' } }")
    Page<AppApplication> findByUserIdAndNameLike(String userId, String name, Pageable pageable);
    
    @Query("{ 'user_id': ?0, 'deleted': null, $or: [ { 'name': { $regex: ?1, $options: 'i' } }, { 'description': { $regex: ?1, $options: 'i' } } ] }")
    Page<AppApplication> searchByUserId(String userId, String keyword, Pageable pageable);
    
    long countByUserIdAndDeletedIsNull(String userId);
    
    long countByUserIdAndStatusAndDeletedIsNull(String userId, String status);
}
