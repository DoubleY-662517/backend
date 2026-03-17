package com.homeworkai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppCreateDTO {
    
    @NotBlank(message = "应用名称不能为空")
    private String name;
    
    private String description;
    
    private String schemaJson;
}
