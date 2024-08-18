package com.llama2_whisper.controller;

import com.llama2_whisper.service.ChatService;
import com.llama2_whisper.service.DiaryService;
import com.llama2_whisper.service.ProblemTransformationService;
import com.llama2_whisper.service.BasicSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class AIController {

    private static final Logger logger = Logger.getLogger(AIController.class.getName());

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private ProblemTransformationService problemTransformationService;

    @Autowired
    private BasicSearchService basicSearchService;

    @Autowired
    private ChatService chatService;

    // 일기 분석 API
    @PostMapping("/api/diary/analyze")
    public Map<String, String> analyzeDiary(@RequestBody Map<String, String> requestBody) {
        String diaryEntry = requestBody.get("diaryEntry");
        logger.info("Received diary entry: " + diaryEntry);

        String aiResponse = diaryService.generateResult(diaryEntry);

        Map<String, String> response = new HashMap<>();
        response.put("Response", aiResponse);

        logger.info("Generated AI response: " + response);
        return response;
    }

    // 검색 API - 문제 변형
    @PostMapping("/api/search/problemTransformation")
    public Map<String, String> searchProblemTransformation(@RequestBody Map<String, String> requestBody) {
        String searchQuery = requestBody.get("searchQuery");
        logger.info("Received search query for problem transformation: " + searchQuery);

        String aiResponse = problemTransformationService.generateResponse(searchQuery);

        Map<String, String> response = new HashMap<>();
        response.put("Response", aiResponse);

        logger.info("Generated AI response: " + response);
        return response;
    }

    // 검색 API - 일반 서치
    @PostMapping("/api/search/basic")
    public Map<String, String> searchBasic(@RequestBody Map<String, String> requestBody) {
        String searchQuery = requestBody.get("searchQuery");
        logger.info("Received basic search query: " + searchQuery);

        String aiResponse = basicSearchService.generateResponse(searchQuery);

        Map<String, String> response = new HashMap<>();
        response.put("Response", aiResponse);

        logger.info("Generated AI response: " + response);
        return response;
    }

    // Chat API
    @PostMapping("/api/chat")
    public Map<String, String> chatWithAI(@RequestBody Map<String, String> requestBody) {
        String userMessage = requestBody.get("message");
        logger.info("Received message: " + userMessage);

        String aiResponse = chatService.generateChatResponse(userMessage);

        Map<String, String> response = new HashMap<>();
        response.put("Response", aiResponse);

        logger.info("Generated AI response: " + response);
        return response;
    }
}
