package org.example.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingest")
public class PdfController {
    private final VectorStore vectorStore;
    public PdfController(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }
    @PostMapping
    public String readPdf()
    {
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader("classpath:/pdf/java.pdf");
        List<Document> documents = pagePdfDocumentReader.read();
        TokenTextSplitter tokenTextSplitter = TokenTextSplitter.builder()
                .withChunkSize(300)
                .withMinChunkSizeChars(50)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(1000)
                .withKeepSeparator(true)
                .build();
        List<Document> chunks = tokenTextSplitter.apply(documents);
        vectorStore.add(chunks);

        return "PDF ingested successfully. "
                + "Pages: " + documents.size()
                + ", Chunks: " + chunks.size();
    }

}
