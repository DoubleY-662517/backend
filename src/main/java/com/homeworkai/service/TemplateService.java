package com.homeworkai.service;

import org.springframework.data.domain.Page;
import com.homeworkai.vo.TemplateVO;

public interface TemplateService {
    
    Page<TemplateVO> list(String category, int page, int size);
    
    TemplateVO getById(String id);
    
    void incrementUseCount(String id);
}
