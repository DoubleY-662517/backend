package com.homeworkai.service;

import com.homeworkai.dto.AIGenerateDTO;
import java.util.Map;

public interface AIService {

    /**
     * 生成应用Schema配置
     */
    Map<String, Object> generateApp(AIGenerateDTO dto);

    /**
     * 生成代码建议
     */
    String generateSuggestion(String context, String code);

    /**
     * 进行对话
     */
    String chat(String message, String history);
}
