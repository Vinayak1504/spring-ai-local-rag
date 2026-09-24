package org.example.controller;

import org.springframework.ai.chat.client.ChatClient;
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
    private final VectorStore vectorStore;
    public RAGController(ChatClient.Builder chatClientBuilder,VectorStore vectorStore){
        chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }
    @GetMapping
    public String ask(@RequestParam String question){
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder().query(question).topK(3).build()
        );
        String context = documents.stream()
                .map(Document::getText)
                .reduce("",(a,b)->a+"/n"+b);
        return chatClient.prompt()
                .system("""
                        You are a Java tutor.

                        Answer the user's question using the provided context.
                        If the answer is not present in the context,
                        clearly say that the information is not available
                        in the provided context.

                        Context:
                        """ + context)
                .user(question)
                .call().content();
    }
}
