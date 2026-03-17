package com.homeworkai.controller;

import org.springframework.data.domain.Page;
import com.homeworkai.common.Result;
import com.homeworkai.service.TemplateService;
import com.homeworkai.vo.TemplateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {
    
    private final TemplateService templateService;
    
    @GetMapping("/list")
    public Result<Page<TemplateVO>> list(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(templateService.list(category, current, size));
    }
    
    @GetMapping("/{id}")
    public Result<TemplateVO> getById(@PathVariable String id) {
        templateService.incrementUseCount(id);
        return Result.success(templateService.getById(id));
    }
}
