package org.example.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {
    private final VectorStore vectorStore;
    public KnowledgeController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    @PostMapping
    public String addKnowledge(@RequestBody String text){
        Document document = new Document(text);
        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(100)
                .withMinChunkSizeChars(50)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(100)
                .withKeepSeparator(true)
                .build();
        List<Document> chunks = tokenTextSplitter.apply(List.of(document));
        vectorStore.add(chunks);
        StringBuilder result = new StringBuilder();

        result.append("Total chunks: ")
                .append(chunks.size())
                .append("\n\n");

        for (int i = 0; i < chunks.size(); i++) {

            result.append("========== CHUNK ")
                    .append(i + 1)
                    .append(" ==========\n");

            result.append(chunks.get(i).getText())
                    .append("\n\n");
        }

        return result.toString();
//        return "Knowledge Added Successfully"+chunks.size();
    }
}
