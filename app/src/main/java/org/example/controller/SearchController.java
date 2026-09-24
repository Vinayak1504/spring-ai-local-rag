package org.example.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final VectorStore vectorStore;
    public SearchController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    @GetMapping
    public List<Document> search(@RequestParam String query){
        return vectorStore.similaritySearch(
                SearchRequest.builder().query(query).topK(3).build()
        );
    }
}
