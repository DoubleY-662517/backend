package com.homeworkai.service;

import com.homeworkai.vo.ComponentVO;
import java.util.List;
import java.util.Map;

public interface ComponentService {
    
    List<ComponentVO> listAll();
    
    List<ComponentVO> listByCategory(String category);
    
    Map<String, List<ComponentVO>> listGroupByCategory();
}
