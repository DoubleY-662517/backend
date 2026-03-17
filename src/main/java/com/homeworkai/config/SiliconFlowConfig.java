package com.homeworkai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "siliconflow")
public class SiliconFlowConfig {
    
    private String apiKey;
    private String baseUrl = "https://api.siliconflow.cn/v1";
    private String defaultModel = "Qwen/Qwen2.5-72B-Instruct";
}
