# Spring AI Local RAG

A local Retrieval-Augmented Generation (RAG) application built with Spring Boot and Spring AI.

This project demonstrates how to build a local AI application that can read PDF documents, split them into chunks, generate embeddings, store them in a vector store, retrieve relevant information, and use a local LLM to answer questions.

## Features

- Local AI chat using Ollama
- Qwen 0.5B as the chat model
- Nomic Embed Text for embeddings
- Spring AI `ChatClient`
- Structured AI output
- Conversation memory
- Text embeddings
- Cosine similarity search
- Spring AI Vector Store
- Semantic search
- Document chunking
- PDF document reading
- PDF ingestion
- Retrieval-Augmented Generation (RAG)
- Question answering using information retrieved from documents

## Technologies

- Java
- Spring Boot
- Spring AI
- Gradle
- Ollama
- Qwen 0.5B
- Nomic Embed Text
- SimpleVectorStore
- REST API

## Requirements

Before running the project, install:

- Java
- Gradle or use the included Gradle Wrapper
- Ollama

Then download the required Ollama models:

```bash
ollama pull qwen:0.5b
ollama pull nomic-embed-text
```

Make sure Ollama is running locally.

## Configuration

The application uses Ollama locally.

Example `application.properties`:

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=qwen:0.5b
spring.ai.ollama.embedding.options.model=nomic-embed-text
```

No paid cloud AI API is required for the current version.

## Running the Application

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/spring-ai-local-rag.git
```

Move into the project:

```bash
cd spring-ai-local-rag
```

Run the application using Gradle:

```bash
./gradlew bootRun
```

On Windows:

```bash
gradlew.bat bootRun
```

The application will start on:

```text
http://localhost:8080
```

## PDF RAG

Place a text-based PDF in:

```text
src/main/resources/pdf/
```

For example:

```text
src/main/resources/pdf/java.pdf
```

Then ingest the PDF using:

```http
POST /pdf/ingest
```

The application:

```text
PDF
 ↓
PDF Reader
 ↓
Document
 ↓
Chunking
 ↓
Nomic Embeddings
 ↓
Vector Store
```

## Semantic Search

The project provides semantic document search.

Example:

```http
GET /search?query=How does Java store data using keys?
```

The application converts the query into an embedding and searches for semantically similar document chunks.

## RAG Question Answering

Ask questions using:

```http
GET /rag?question=What is inheritance in Java?
```

The application:

1. Converts the question into an embedding.
2. Searches the Vector Store.
3. Retrieves relevant document chunks.
4. Adds the retrieved information to the prompt.
5. Sends the context and question to Qwen.
6. Returns the generated answer.

## Conversation Memory

The project also demonstrates conversation memory using Spring AI.

This allows follow-up questions to use information from previous messages in the same conversation.

## Learning Progress

This project was developed incrementally while learning Spring AI.

```text
Basic Chat
    ↓
System/User Messages
    ↓
Structured Output
    ↓
Conversation Memory
    ↓
Embeddings
    ↓
Cosine Similarity
    ↓
Vector Store
    ↓
Semantic Search
    ↓
Document Chunking
    ↓
PDF Reading
    ↓
PDF Ingestion
    ↓
RAG
```

## Current Limitations

The current version is primarily a learning and demonstration project.

- SimpleVectorStore is an in-memory vector store.
- Vector data is lost when the application restarts.
- The current PDF workflow uses a locally configured PDF.
- Authentication is not implemented.
- The application is not intended as a production deployment.

## Future Improvements

Planned improvements include:

- PDF upload through REST API
- Multiple PDF support
- Persistent vector database
- Metadata-based filtering
- Improved chunking strategies
- Conversational PDF assistant
- Better error handling
- REST API documentation
- Docker support

## Author

**Siddhi Vinayak**

This project was created as a hands-on learning project to understand Spring AI, embeddings, vector stores, and Retrieval-Augmented Generation.
