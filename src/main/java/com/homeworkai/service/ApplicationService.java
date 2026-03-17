package com.homeworkai.service;

import org.springframework.data.domain.Page;
import com.homeworkai.dto.AppCreateDTO;
import com.homeworkai.dto.AppUpdateDTO;
import com.homeworkai.vo.AppVO;

public interface ApplicationService {
    
    Page<AppVO> list(String userId, int page, int size);
    
    AppVO getById(String id);
    
    AppVO create(String userId, AppCreateDTO dto);
    
    AppVO update(String id, AppUpdateDTO dto);
    
    void delete(String id);
    
    void saveVersion(String id, String changeLog);
}
