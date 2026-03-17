package com.homeworkai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "app_deployment")
public class AppDeployment {
    
    @Id
    private String id;
    
    @Indexed
    @Field("app_id")
    private String appId;
    
    private String version;
    
    @Field("deploy_url")
    private String deployUrl;
    
    @Indexed
    private String status;
    
    @Field("created_at")
    private LocalDateTime createdAt;
}
