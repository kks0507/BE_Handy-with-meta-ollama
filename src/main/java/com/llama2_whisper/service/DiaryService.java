package com.llama2_whisper.service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;

@Service
public class DiaryService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResult(String diaryEntry) {
        String prompt = """
            SYSTEM:
            You are an AI that turns diary entries into short fantasy stories where the diary writer becomes a hero named Handy.

            Objective:
            Create a 250-character fantasy story based on the diary entry. Ensure Handy's journey directly reflects the writer's emotions and events from their day, making them feel heroic and accomplished.

            USER:
            """ + diaryEntry + """

            ASSISTANT:
            Transform the diary into a 250-character story where Handy is the protagonist. The story should mirror the day's challenges and emotions, portraying Handy as a hero who overcomes them.
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




