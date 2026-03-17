package com.homeworkai.service.impl;

import com.homeworkai.entity.AppTemplate;
import com.homeworkai.repository.AppTemplateRepository;
import com.homeworkai.service.TemplateService;
import com.homeworkai.vo.TemplateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    
    private final AppTemplateRepository appTemplateRepository;
    
    @Override
    public Page<TemplateVO> list(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "useCount"));
        
        Page<AppTemplate> result;
        if (category != null && !category.isEmpty()) {
            result = appTemplateRepository.findByIsPublicAndCategory(1, category, pageable);
        } else {
            result = appTemplateRepository.findByIsPublic(1, pageable);
        }
        
        return result.map(this::toTemplateVO);
    }
    
    @Override
    public TemplateVO getById(String id) {
        return appTemplateRepository.findById(id)
            .map(this::toTemplateVO)
            .orElse(null);
    }
    
    @Override
    public void incrementUseCount(String id) {
        appTemplateRepository.findById(id).ifPresent(template -> {
            template.setUseCount(template.getUseCount() + 1);
            appTemplateRepository.save(template);
        });
    }
    
    private TemplateVO toTemplateVO(AppTemplate template) {
        TemplateVO vo = new TemplateVO();
        BeanUtils.copyProperties(template, vo);
        return vo;
    }
}
