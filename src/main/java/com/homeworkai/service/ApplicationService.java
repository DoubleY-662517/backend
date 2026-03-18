package com.homeworkai.service;

import org.springframework.data.domain.Page;
import com.homeworkai.dto.AppCreateDTO;
import com.homeworkai.dto.AppUpdateDTO;
import com.homeworkai.vo.AppVO;

import java.util.Map;

public interface ApplicationService {
    
    Page<AppVO> list(String userId, int page, int size);
    
    Page<AppVO> listByStatus(String userId, String status, int page, int size);
    
    Page<AppVO> search(String userId, String keyword, int page, int size);
    
    AppVO getById(String id);
    
    AppVO create(String userId, AppCreateDTO dto);
    
    AppVO update(String id, AppUpdateDTO dto);
    
    void delete(String id);
    
    void saveVersion(String id, String changeLog);
    
    Map<String, Object> getStatistics(String userId);
}
