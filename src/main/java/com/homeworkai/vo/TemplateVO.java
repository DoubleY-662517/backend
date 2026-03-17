package com.homeworkai.vo;

import lombok.Data;

@Data
public class TemplateVO {
    
    private String id;
    private String name;
    private String description;
    private String category;
    private String previewImage;
    private String schemaJson;
    private Integer useCount;
}
