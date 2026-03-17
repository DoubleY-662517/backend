package com.homeworkai.controller;

import org.springframework.data.domain.Page;
import com.homeworkai.common.Result;
import com.homeworkai.dto.AppCreateDTO;
import com.homeworkai.dto.AppUpdateDTO;
import com.homeworkai.service.ApplicationService;
import com.homeworkai.service.UserService;
import com.homeworkai.vo.AppVO;
import com.homeworkai.vo.UserVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class ApplicationController {
    
    private final ApplicationService applicationService;
    private final UserService userService;
    
    @GetMapping("/list")
    public Result<Page<AppVO>> list(
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(applicationService.list(userId, current, size));
    }
    
    @GetMapping("/{id}")
    public Result<AppVO> getById(@PathVariable String id) {
        return Result.success(applicationService.getById(id));
    }
    
    @PostMapping("/create")
    public Result<AppVO> create(@Valid @RequestBody AppCreateDTO dto) {
        UserVO user = userService.getCurrentUser();
        return Result.success(applicationService.create(user.getId(), dto));
    }
    
    @PutMapping("/{id}")
    public Result<AppVO> update(@PathVariable String id, @RequestBody AppUpdateDTO dto) {
        return Result.success(applicationService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        applicationService.delete(id);
        return Result.success();
    }
    
    @PostMapping("/{id}/version")
    public Result<Void> saveVersion(@PathVariable String id, @RequestParam String changeLog) {
        applicationService.saveVersion(id, changeLog);
        return Result.success();
    }
}
