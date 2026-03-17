package com.homeworkai.service.impl;

import com.homeworkai.dto.AppCreateDTO;
import com.homeworkai.dto.AppUpdateDTO;
import com.homeworkai.entity.AppApplication;
import com.homeworkai.entity.AppVersion;
import com.homeworkai.exception.BusinessException;
import com.homeworkai.repository.AppApplicationRepository;
import com.homeworkai.repository.AppVersionRepository;
import com.homeworkai.service.ApplicationService;
import com.homeworkai.vo.AppVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    
    private final AppApplicationRepository appApplicationRepository;
    private final AppVersionRepository appVersionRepository;
    
    @Override
    public Page<AppVO> list(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        
        Page<AppApplication> result;
        if (userId != null && !userId.isEmpty()) {
            result = appApplicationRepository.findByUserIdAndDeletedIsNull(userId, pageable);
        } else {
            result = appApplicationRepository.findByDeletedIsNull(pageable);
        }
        
        return result.map(this::toAppVO);
    }
    
    @Override
    public AppVO getById(String id) {
        AppApplication app = appApplicationRepository.findById(id)
            .orElseThrow(() -> new BusinessException("应用不存在"));
        return toAppVO(app);
    }
    
    @Override
    public AppVO create(String userId, AppCreateDTO dto) {
        AppApplication app = new AppApplication();
        app.setName(dto.getName());
        app.setDescription(dto.getDescription());
        app.setSchemaJson(dto.getSchemaJson());
        app.setUserId(userId);
        app.setStatus("draft");
        app.setVersion("1.0.0");
        app.setDeleted(0);
        app.setCreatedAt(LocalDateTime.now());
        app.setUpdatedAt(LocalDateTime.now());
        
        appApplicationRepository.save(app);
        
        return toAppVO(app);
    }
    
    @Override
    public AppVO update(String id, AppUpdateDTO dto) {
        AppApplication app = appApplicationRepository.findById(id)
            .orElseThrow(() -> new BusinessException("应用不存在"));
        
        if (dto.getName() != null) {
            app.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            app.setDescription(dto.getDescription());
        }
        if (dto.getSchemaJson() != null) {
            app.setSchemaJson(dto.getSchemaJson());
        }
        if (dto.getStatus() != null) {
            app.setStatus(dto.getStatus());
        }
        app.setUpdatedAt(LocalDateTime.now());
        
        appApplicationRepository.save(app);
        
        return toAppVO(app);
    }
    
    @Override
    public void delete(String id) {
        AppApplication app = appApplicationRepository.findById(id)
            .orElseThrow(() -> new BusinessException("应用不存在"));
        app.setDeleted(1);
        app.setUpdatedAt(LocalDateTime.now());
        appApplicationRepository.save(app);
    }
    
    @Override
    public void saveVersion(String id, String changeLog) {
        AppApplication app = appApplicationRepository.findById(id)
            .orElseThrow(() -> new BusinessException("应用不存在"));
        
        AppVersion version = new AppVersion();
        version.setAppId(id);
        version.setVersion(app.getVersion());
        version.setSchemaJson(app.getSchemaJson());
        version.setChangeLog(changeLog);
        version.setCreatedAt(LocalDateTime.now());
        
        appVersionRepository.save(version);
    }
    
    private AppVO toAppVO(AppApplication app) {
        AppVO vo = new AppVO();
        BeanUtils.copyProperties(app, vo);
        return vo;
    }
}
