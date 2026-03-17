package com.homeworkai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "app_version")
public class AppVersion {
    
    @Id
    private String id;
    
    @Indexed
    @Field("app_id")
    private String appId;
    
    @Indexed
    private String version;
    
    @Field("schema_json")
    private String schemaJson;
    
    @Field("change_log")
    private String changeLog;
    
    @Field("created_at")
    private LocalDateTime createdAt;
}
