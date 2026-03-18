package com.homeworkai.service;

import com.homeworkai.entity.AiComponent;
import com.homeworkai.dto.ComponentDTO;

import java.util.List;
import java.util.Map;

public interface ComponentService {
    
    List<AiComponent> getComponents(String userId);
    
    List<AiComponent> getComponentsByCategory(String userId, String category);
    
    List<AiComponent> searchComponents(String userId, String keyword);
    
    AiComponent getComponent(String id);
    
    AiComponent createComponent(String userId, ComponentDTO dto);
    
    AiComponent updateComponent(String id, ComponentDTO dto);
    
    void deleteComponent(String id);
    
    AiComponent toggleComponent(String id, Boolean enabled);
    
    AiComponent updateConfig(String id, String config);
    
    Map<String, Object> getStatistics(String userId);
    
    List<Map<String, Object>> getCategories();
}
