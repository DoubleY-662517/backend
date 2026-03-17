package com.homeworkai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "app_component")
public class AppComponent {
    
    @Id
    private String id;
    
    private String name;
    
    @Indexed
    private String type;
    
    @Indexed
    private String category;
    
    private String icon;
    
    @Field("props_json")
    private String propsJson;
    
    @Field("is_system")
    private Integer isSystem;
    
    @Field("created_at")
    private LocalDateTime createdAt;
}
