package com.homeworkai.service.impl;

import com.homeworkai.dto.ComponentDTO;
import com.homeworkai.entity.AiComponent;
import com.homeworkai.exception.BusinessException;
import com.homeworkai.repository.AiComponentRepository;
import com.homeworkai.service.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {
    
    private final AiComponentRepository componentRepository;
    
    @Override
    public List<AiComponent> getComponents(String userId) {
        return componentRepository.findByUserIdAndDeletedIsNull(userId);
    }
    
    @Override
    public List<AiComponent> getComponentsByCategory(String userId, String category) {
        if (category == null || category.isEmpty() || "all".equals(category)) {
            return getComponents(userId);
        }
        return componentRepository.findByUserIdAndCategoryAndDeletedIsNull(userId, category);
    }
    
    @Override
    public List<AiComponent> searchComponents(String userId, String keyword) {
        return componentRepository.searchByUserId(userId, keyword);
    }
    
    @Override
    public AiComponent getComponent(String id) {
        return componentRepository.findById(id)
            .orElseThrow(() -> new BusinessException("组件不存在"));
    }
    
    @Override
    public AiComponent createComponent(String userId, ComponentDTO dto) {
        AiComponent component = new AiComponent();
        component.setUserId(userId);
        component.setName(dto.getName());
        component.setDescription(dto.getDescription());
        component.setIcon(dto.getIcon());
        component.setCategory(dto.getCategory());
        component.setGradient(dto.getGradient());
        component.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : false);
        component.setEndpoint(dto.getEndpoint());
        component.setConfig(dto.getConfig());
        component.setCreatedAt(LocalDateTime.now());
        component.setUpdatedAt(LocalDateTime.now());
        return componentRepository.save(component);
    }
    
    @Override
    public AiComponent updateComponent(String id, ComponentDTO dto) {
        AiComponent component = getComponent(id);
        
        if (dto.getName() != null) {
            component.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            component.setDescription(dto.getDescription());
        }
        if (dto.getIcon() != null) {
            component.setIcon(dto.getIcon());
        }
        if (dto.getCategory() != null) {
            component.setCategory(dto.getCategory());
        }
        if (dto.getGradient() != null) {
            component.setGradient(dto.getGradient());
        }
        if (dto.getEnabled() != null) {
            component.setEnabled(dto.getEnabled());
        }
        if (dto.getEndpoint() != null) {
            component.setEndpoint(dto.getEndpoint());
        }
        if (dto.getConfig() != null) {
            component.setConfig(dto.getConfig());
        }
        
        component.setUpdatedAt(LocalDateTime.now());
        return componentRepository.save(component);
    }
    
    @Override
    public void deleteComponent(String id) {
        AiComponent component = getComponent(id);
        component.setDeleted(1);
        component.setUpdatedAt(LocalDateTime.now());
        componentRepository.save(component);
    }
    
    @Override
    public AiComponent toggleComponent(String id, Boolean enabled) {
        AiComponent component = getComponent(id);
        component.setEnabled(enabled);
        component.setUpdatedAt(LocalDateTime.now());
        return componentRepository.save(component);
    }
    
    @Override
    public AiComponent updateConfig(String id, String config) {
        AiComponent component = getComponent(id);
        component.setConfig(config);
        component.setUpdatedAt(LocalDateTime.now());
        return componentRepository.save(component);
    }
    
    @Override
    public Map<String, Object> getStatistics(String userId) {
        Map<String, Object> stats = new HashMap<>();
        long total = componentRepository.countByUserIdAndDeletedIsNull(userId);
        long enabled = componentRepository.countByUserIdAndEnabledAndDeletedIsNull(userId, true);
        long disabled = componentRepository.countByUserIdAndEnabledAndDeletedIsNull(userId, false);
        
        stats.put("total", total);
        stats.put("enabled", enabled);
        stats.put("disabled", disabled);
        return stats;
    }
    
    @Override
    public List<Map<String, Object>> getCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        
        Map<String, Object> input = new HashMap<>();
        input.put("id", "input");
        input.put("name", "输入类");
        input.put("icon", "description");
        categories.add(input);
        
        Map<String, Object> chat = new HashMap<>();
        chat.put("id", "chat");
        chat.put("name", "对话类");
        chat.put("icon", "forum");
        categories.add(chat);
        
        Map<String, Object> generate = new HashMap<>();
        generate.put("id", "generate");
        generate.put("name", "生成类");
        generate.put("icon", "palette");
        categories.add(generate);
        
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("id", "analysis");
        analysis.put("name", "分析类");
        analysis.put("icon", "monitoring");
        categories.add(analysis);
        
        return categories;
    }
}
