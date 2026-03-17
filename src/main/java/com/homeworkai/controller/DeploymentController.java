package com.homeworkai.controller;

import com.homeworkai.common.Result;
import com.homeworkai.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deploy")
@RequiredArgsConstructor
public class DeploymentController {
    
    private final DeploymentService deploymentService;
    
    @PostMapping("/{appId}")
    public Result<String> deploy(@PathVariable String appId) {
        return Result.success("部署成功", deploymentService.deploy(appId));
    }
    
    @GetMapping("/status/{deploymentId}")
    public Result<String> getStatus(@PathVariable String deploymentId) {
        return Result.success(deploymentService.getStatus(deploymentId));
    }
}
