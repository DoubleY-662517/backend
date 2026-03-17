package com.homeworkai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "app_application")
public class AppApplication {
    
    @Id
    private String id;
    
    private String name;
    
    private String description;
    
    @Indexed
    @Field("user_id")
    private String userId;
    
    @Field("schema_json")
    private String schemaJson;
    
    @Indexed
    private String status;
    
    private String version;
    
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @Field("updated_at")
    private LocalDateTime updatedAt;
    
    private Integer deleted;
}
