package org.example.controller;

import org.example.dto.ConceptResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AIController {
    private final ChatClient chatClient;
    public AIController(ChatClient.Builder chatClientBuilder,ChatMemory chatMemory){
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient = chatClientBuilder.defaultAdvisors(memoryAdvisor).build();
    }
    @GetMapping("/ask")
    public ConceptResponse ask(@RequestParam String conversationId, @RequestParam String question){
        return chatClient.prompt()
                .system(""" 
                    You are a Java teacher. 

                    Explain Java concepts in simple language. 

                    Give a small example whenever possible. 

                    """)
                .user("""
                    Explain the following Java concept.

                    Return the answer with these fields:
                    concept
                    definition
                    example
                    interviewTip

                    Concept:
                    %s
                    """.formatted(question))
                .advisors(advisors-> advisors.param(ChatMemory.CONVERSATION_ID,conversationId))
                .call()
                .entity(ConceptResponse.class);
    }
}
