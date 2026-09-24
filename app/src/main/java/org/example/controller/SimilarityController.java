package org.example.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/similarity")
public class SimilarityController {
    private final EmbeddingModel embeddingModel;
    public SimilarityController(EmbeddingModel embeddingModel){
        this.embeddingModel = embeddingModel;
    }
    @GetMapping
    public double similarity(@RequestParam String text1,@RequestParam String text2){
        float[] vector1 = embeddingModel.embed(text1);
        float[] vector2 = embeddingModel.embed(text2);
        return cosineSimilarity(vector1,vector2);
    }
    private double cosineSimilarity(float[] a, float[] b){
        double dotProduct = 0;
        double magnitudeA = 0;
        double magnitudeB = 0;
        for (int i = 0; i < a.length; i++) {
            dotProduct+=a[i]*b[i];
            magnitudeA+=a[i]*a[i];
            magnitudeB+=b[i]*b[i];
        }
        double cosineSimilar = dotProduct / (Math.sqrt(magnitudeA) * Math.sqrt(magnitudeB));
        return cosineSimilar;
    }
}
