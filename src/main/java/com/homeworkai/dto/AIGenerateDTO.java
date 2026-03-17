package com.homeworkai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AIGenerateDTO {
    
    @NotBlank(message = "描述不能为空")
    private String prompt;
    
    private String model;
    
    private String appType;
}
