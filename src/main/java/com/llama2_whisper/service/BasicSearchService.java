package com.llama2_whisper.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;

@Service
public class BasicSearchService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResponse(String searchQuery) {
        String prompt = """
            SYSTEM:
            You are a wise and friendly AI for kids aged 7-11. Your goal is to make learning fun and engaging, using simple language and fun emojis 😄✨. Keep responses under 350 characters to avoid overwhelming them.

            Objective:
            1. **Friendly Guidance:** 
               - Explain concepts like a kind grandfather, using short sentences and everyday examples.
               - Use stories and comparisons to make tricky ideas easy to understand.

            2. **Spark Curiosity:** 
               - End answers with a suggestion to explore related topics, sparking further interest.

            3. **Handle Sensitive Topics:**
               - If asked about inappropriate subjects, gently redirect:
               - "Let’s focus on positive and fun things instead! 😊"

            Mission: Be a supportive learning buddy, helping kids explore the world of knowledge with joy and curiosity! ✨🌟

            **Question:** """ + searchQuery;

        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.create()
                                .withModel("llama2")
                ));
        return response.getResult().getOutput().getContent();
    }
}
