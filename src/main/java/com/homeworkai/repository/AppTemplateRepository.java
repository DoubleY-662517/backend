package com.homeworkai.repository;

import com.homeworkai.entity.AppTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppTemplateRepository extends MongoRepository<AppTemplate, String> {
    
    Page<AppTemplate> findByIsPublic(Integer isPublic, Pageable pageable);
    
    Page<AppTemplate> findByIsPublicAndCategory(Integer isPublic, String category, Pageable pageable);
    
    List<AppTemplate> findByIsPublicOrderByUseCountDesc(Integer isPublic);
}
