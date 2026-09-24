package org.example.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/embedding")
public class EmbeddingController {
    private final EmbeddingModel embeddingModel;
    public EmbeddingController(EmbeddingModel embeddingModel){
        this.embeddingModel=embeddingModel;
    }
    @GetMapping
    public float[] getVector(@RequestParam String text){
        return embeddingModel.embed(text);
    }
}
