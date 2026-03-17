package com.homeworkai.repository;

import com.homeworkai.entity.AppComponent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppComponentRepository extends MongoRepository<AppComponent, String> {
    
    List<AppComponent> findByCategory(String category);
    
    List<AppComponent> findByCategoryOrderByNameAsc(String category);
    
    List<AppComponent> findByIsSystem(Integer isSystem);
    
    List<AppComponent> findAllByOrderByCategoryAsc();
}
