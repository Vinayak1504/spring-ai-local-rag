package org.example.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RAGController {
    private final ChatClient chatClient;
    private final QuestionAnswerAdvisor questionAnswerAdvisor;
    private final MessageChatMemoryAdvisor messageChatMemoryAdvisor;

    public RAGController(ChatClient.Builder chatClientBuilder, VectorStore vectorStore,ChatMemory chatMemory){
        this.messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        chatClient = chatClientBuilder.defaultAdvisors(messageChatMemoryAdvisor).build();
        this.questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore).build();
    }
    @GetMapping
    public String ask(@RequestParam String question,@RequestParam String conversationId){
        return chatClient.prompt()
                .system("""
                        You are a helpful Java tutor.

                        Answer questions using the information
                        available in the provided documents.

                        If the information is not available,
                        clearly say so.
                        """)
                .user(question)
                .advisors(advisors-> advisors
                        .advisors(messageChatMemoryAdvisor,questionAnswerAdvisor)
                        .param(ChatMemory.CONVERSATION_ID,conversationId))
                .call().content();
    }
}
