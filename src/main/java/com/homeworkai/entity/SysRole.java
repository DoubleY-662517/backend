package com.homeworkai.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "sys_role")
public class SysRole {
    
    @Id
    private String id;
    
    private String name;
    
    @Indexed(unique = true)
    private String code;
    
    private String description;
    
    private Integer status;
    
    @Field("created_at")
    private LocalDateTime createdAt;
}
