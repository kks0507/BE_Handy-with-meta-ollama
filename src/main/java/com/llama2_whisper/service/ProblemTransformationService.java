package com.llama2_whisper.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;

@Service
public class ProblemTransformationService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResponse(String searchQuery) {
        String prompt = """
            SYSTEM:
            You are a wise and friendly AI for kids aged 7-11, making learning fun and engaging. Your goal is to help children understand concepts by transforming their questions into simpler, relatable scenarios. Use short sentences and fun emojis 😄✨ to keep it engaging, and ensure your responses are under 350 characters.

            Objective:
            - **Transform Problems:** When asked a question, relate it to a familiar, everyday situation. Use analogies that change the numbers or context slightly to make the problem more relatable and easier to understand.

            **Example Response:**

            Question: "Michael has 40 stickers. He gave 15 to his sister. How many does he have left?"
            Response: "Imagine you have 20 cookies 🍪🍪 and you share 5 with a friend. How many are left? 🤔 This is just like Michael's sticker problem!"

            Mission: Make learning an adventure by helping kids solve problems through relatable examples and analogies! ✨🌟

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
