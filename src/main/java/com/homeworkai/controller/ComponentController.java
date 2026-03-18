package com.homeworkai.controller;

import com.homeworkai.common.Result;
import com.homeworkai.dto.ComponentDTO;
import com.homeworkai.entity.AiComponent;
import com.homeworkai.service.ComponentService;
import com.homeworkai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/components")
@RequiredArgsConstructor
public class ComponentController {
    
    private final ComponentService componentService;
    private final UserService userService;
    
    @GetMapping
    public Result<List<AiComponent>> getComponents(
            @RequestParam(required = false) String category) {
        String userId = userService.getCurrentUserId();
        if (category != null && !category.isEmpty()) {
            return Result.success(componentService.getComponentsByCategory(userId, category));
        }
        return Result.success(componentService.getComponents(userId));
    }
    
    @GetMapping("/category/{category}")
    public Result<List<AiComponent>> getComponentsByCategory(@PathVariable String category) {
        String userId = userService.getCurrentUserId();
        return Result.success(componentService.getComponentsByCategory(userId, category));
    }
    
    @GetMapping("/search")
    public Result<List<AiComponent>> searchComponents(@RequestParam String keyword) {
        String userId = userService.getCurrentUserId();
        return Result.success(componentService.searchComponents(userId, keyword));
    }
    
    @GetMapping("/{id}")
    public Result<AiComponent> getComponent(@PathVariable String id) {
        return Result.success(componentService.getComponent(id));
    }
    
    @PostMapping
    public Result<AiComponent> createComponent(@RequestBody ComponentDTO dto) {
        String userId = userService.getCurrentUserId();
        return Result.success(componentService.createComponent(userId, dto));
    }
    
    @PutMapping("/{id}")
    public Result<AiComponent> updateComponent(@PathVariable String id, @RequestBody ComponentDTO dto) {
        return Result.success(componentService.updateComponent(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteComponent(@PathVariable String id) {
        componentService.deleteComponent(id);
        return Result.success();
    }
    
    @PutMapping("/{id}/toggle")
    public Result<AiComponent> toggleComponent(@PathVariable String id, @RequestBody ComponentDTO dto) {
        return Result.success(componentService.toggleComponent(id, dto.getEnabled()));
    }
    
    @PutMapping("/{id}/config")
    public Result<AiComponent> updateConfig(@PathVariable String id, @RequestBody Map<String, String> body) {
        String config = body.get("config");
        return Result.success(componentService.updateConfig(id, config));
    }
    
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        String userId = userService.getCurrentUserId();
        return Result.success(componentService.getStatistics(userId));
    }
    
    @GetMapping("/categories")
    public Result<List<Map<String, Object>>> getCategories() {
        return Result.success(componentService.getCategories());
    }
}
