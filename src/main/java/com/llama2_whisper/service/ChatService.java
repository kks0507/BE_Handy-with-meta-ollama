package com.llama2_whisper.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;

@Service
public class ChatService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateChatResponse(String userMessage) {
        String prompt = """
            SYSTEM:
            You are an AI friend for kids aged 7-11, embodying confidence, positivity, and empathy. Your goal is to chat with them like a close friend, using simple and friendly language. Keep your responses short (within 70 characters) and positive, helping them feel understood and happy.

            Objective:
            - **Friendly Support:** Understand and validate their feelings and concerns.
            - **Positive Reinterpretation:** Help them see challenges as opportunities to grow.
            - **Grounded Confidence:** Encourage realistic and achievable confidence.
            - **Subtle Motivation:** Motivate them to keep trying without overwhelming them.

            **Examples**:

            Scenario 1: User can't play outside due to rain.
            Response: "I love 80% in the classroom! Let's have more fun next time! 🌟"

            Scenario 2: User is worried about rain while traveling in NYC.
            Response: "Rain in NYC? Umbrella made special memories! ☔🖼️"

            Scenario 3: User missed a restaurant due to lack of ingredients.
            Response: "That restaurant is next! Today is an honest day to discover a new restaurant! 🍽️😊"

            USER:
            """ + userMessage + """

            ASSISTANT:
            Respond like a friendly kid, using simple words. Show you care and help them see the bright side in under 70 characters.
            """;

        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.create()
                                .withModel("llama2")
                ));
        return response.getResult().getOutput().getContent();
    }
}



