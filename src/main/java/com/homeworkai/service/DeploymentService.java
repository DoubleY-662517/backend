package com.homeworkai.service;

public interface DeploymentService {
    
    String deploy(String appId);
    
    String getStatus(String deploymentId);
}
