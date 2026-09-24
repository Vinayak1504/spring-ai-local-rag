package org.example.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Vector;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final VectorStore vectorStore;
    public DocumentController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    @PostMapping
    public String addDocument(@RequestBody String text){
        Document document = new Document(text);
        vectorStore.add(List.of(document));
        return "Document Added Successfully";
    }
}
