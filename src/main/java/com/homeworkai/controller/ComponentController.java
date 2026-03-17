package com.homeworkai.controller;

import com.homeworkai.common.Result;
import com.homeworkai.service.ComponentService;
import com.homeworkai.vo.ComponentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/component")
@RequiredArgsConstructor
public class ComponentController {
    
    private final ComponentService componentService;
    
    @GetMapping("/list")
    public Result<List<ComponentVO>> listAll() {
        return Result.success(componentService.listAll());
    }
    
    @GetMapping("/category/{category}")
    public Result<List<ComponentVO>> listByCategory(@PathVariable String category) {
        return Result.success(componentService.listByCategory(category));
    }
    
    @GetMapping("/group")
    public Result<Map<String, List<ComponentVO>>> listGroupByCategory() {
        return Result.success(componentService.listGroupByCategory());
    }
}
