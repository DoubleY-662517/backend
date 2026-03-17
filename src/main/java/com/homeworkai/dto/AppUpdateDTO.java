package com.homeworkai.dto;

import lombok.Data;

@Data
public class AppUpdateDTO {
    
    private String name;
    
    private String description;
    
    private String schemaJson;
    
    private String status;
}
