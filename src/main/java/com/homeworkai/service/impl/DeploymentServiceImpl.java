package com.homeworkai.service.impl;

import com.homeworkai.entity.AppApplication;
import com.homeworkai.entity.AppDeployment;
import com.homeworkai.exception.BusinessException;
import com.homeworkai.repository.AppApplicationRepository;
import com.homeworkai.repository.AppDeploymentRepository;
import com.homeworkai.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeploymentServiceImpl implements DeploymentService {
    
    private final AppApplicationRepository appApplicationRepository;
    private final AppDeploymentRepository appDeploymentRepository;
    
    @Override
    public String deploy(String appId) {
        AppApplication app = appApplicationRepository.findById(appId)
            .orElseThrow(() -> new BusinessException("应用不存在"));
        
        AppDeployment deployment = new AppDeployment();
        deployment.setAppId(appId);
        deployment.setVersion(app.getVersion());
        deployment.setStatus("pending");
        deployment.setCreatedAt(LocalDateTime.now());
        
        appDeploymentRepository.save(deployment);
        
        String deployUrl = generateDeployUrl(deployment.getId());
        deployment.setDeployUrl(deployUrl);
        deployment.setStatus("success");
        appDeploymentRepository.save(deployment);
        
        app.setStatus("published");
        app.setUpdatedAt(LocalDateTime.now());
        appApplicationRepository.save(app);
        
        log.info("应用部署成功: appId={}, deployUrl={}", appId, deployUrl);
        
        return deployUrl;
    }
    
    @Override
    public String getStatus(String deploymentId) {
        AppDeployment deployment = appDeploymentRepository.findById(deploymentId)
            .orElseThrow(() -> new BusinessException("部署记录不存在"));
        return deployment.getStatus();
    }
    
    private String generateDeployUrl(String deploymentId) {
        return "/deploy/" + UUID.randomUUID().toString().substring(0, 8);
    }
}
