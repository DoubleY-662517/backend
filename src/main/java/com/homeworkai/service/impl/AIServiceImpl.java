package com.homeworkai.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeworkai.config.SiliconFlowConfig;
import com.homeworkai.dto.AIGenerateDTO;
import com.homeworkai.service.AIService;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
    
    private final SiliconFlowConfig siliconFlowConfig;
    private ChatLanguageModel chatModel;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        chatModel = OpenAiChatModel.builder()
            .baseUrl(siliconFlowConfig.getBaseUrl())
            .apiKey(siliconFlowConfig.getApiKey())
            .modelName(siliconFlowConfig.getDefaultModel())
            .timeout(Duration.ofSeconds(60))
            .build();
    }
    
    @Override
    public Map<String, Object> generateApp(AIGenerateDTO dto) {
        String prompt = buildAppGenerationPrompt(dto.getPrompt(), dto.getAppType());
        String response = chatModel.generate(prompt);
        return parseSchemaResponse(response);
    }
    
    @Override
    public String generateSuggestion(String context, String code) {
        String prompt = buildSuggestionPrompt(context, code);
        return chatModel.generate(prompt);
    }
    
    @Override
    public String chat(String message, String history) {
        String prompt = buildChatPrompt(message, history);
        return chatModel.generate(prompt);
    }
    
    private String buildAppGenerationPrompt(String userPrompt, String appType) {
        return String.format("""
            你是一个零代码应用开发平台的AI助手。请根据用户的需求生成应用Schema配置。
            
            用户需求：%s
            应用类型：%s
            
            请生成符合以下JSON格式的Schema配置：
            {
              "pages": [
                {
                  "id": "page_xxx",
                  "name": "页面名称",
                  "path": "/",
                  "components": []
                }
              ],
              "globalStyle": {
                "primaryColor": "#409EFF"
              }
            }
            
            组件类型包括：ElButton, ElInput, ElForm, ElTable, ElCard, ElContainer, ElRow, ElCol等。
            
            请只返回JSON格式的Schema，不要包含其他说明文字。
            """, userPrompt, appType != null ? appType : "通用应用");
    }
    
    private String buildSuggestionPrompt(String context, String code) {
        return String.format("""
            你是一个代码助手。请根据上下文提供代码建议。
            
            上下文：%s
            当前代码：%s
            
            请提供改进建议或下一步操作建议。
            """, context, code);
    }
    
    private String buildChatPrompt(String message, String history) {
        return String.format("""
            历史对话：%s
            
            当前问题：%s
            
            请回答用户的问题。
            """, history != null ? history : "无", message);
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseSchemaResponse(String response) {
        if (response == null || response.isEmpty()) {
            return Collections.emptyMap();
        }
        
        try {
            String jsonStr = response;
            if (response.contains("```json")) {
                jsonStr = response.substring(response.indexOf("```json") + 7, response.lastIndexOf("```"));
            } else if (response.contains("```")) {
                jsonStr = response.substring(response.indexOf("```") + 3, response.lastIndexOf("```"));
            }
            
            return objectMapper.readValue(jsonStr.trim(), Map.class);
        } catch (Exception e) {
            log.error("解析Schema响应失败: {}", e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("rawResponse", response);
            return result;
        }
    }
}
