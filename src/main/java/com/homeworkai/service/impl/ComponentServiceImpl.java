package com.homeworkai.service.impl;

import com.homeworkai.entity.AppComponent;
import com.homeworkai.repository.AppComponentRepository;
import com.homeworkai.service.ComponentService;
import com.homeworkai.vo.ComponentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {
    
    private final AppComponentRepository appComponentRepository;
    
    @Override
    public List<ComponentVO> listAll() {
        List<AppComponent> components = appComponentRepository.findAllByOrderByCategoryAsc();
        return components.stream().map(this::toComponentVO).collect(Collectors.toList());
    }
    
    @Override
    public List<ComponentVO> listByCategory(String category) {
        List<AppComponent> components = appComponentRepository.findByCategoryOrderByNameAsc(category);
        return components.stream().map(this::toComponentVO).collect(Collectors.toList());
    }
    
    @Override
    public Map<String, List<ComponentVO>> listGroupByCategory() {
        List<AppComponent> components = appComponentRepository.findAll();
        return components.stream()
            .map(this::toComponentVO)
            .collect(Collectors.groupingBy(ComponentVO::getCategory));
    }
    
    private ComponentVO toComponentVO(AppComponent component) {
        ComponentVO vo = new ComponentVO();
        BeanUtils.copyProperties(component, vo);
        return vo;
    }
}
