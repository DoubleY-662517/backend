package com.homeworkai.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppVO {
    
    private String id;
    private String name;
    private String description;
    private String status;
    private String version;
    private String schemaJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
