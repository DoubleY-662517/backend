package com.homeworkai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "app_template")
public class AppTemplate {
    
    @Id
    private String id;
    
    private String name;
    
    private String description;
    
    @Indexed
    private String category;
    
    @Field("preview_image")
    private String previewImage;
    
    @Field("schema_json")
    private String schemaJson;
    
    @Indexed
    @Field("is_public")
    private Integer isPublic;
    
    @Field("use_count")
    private Integer useCount;
    
    @Field("created_at")
    private LocalDateTime createdAt;
}
