package com.bootcoding.content_question_generator.service;

import com.bootcoding.content_question_generator.dto.QuestionRequest;
import com.bootcoding.content_question_generator.model.QuestionDocument;
import com.bootcoding.content_question_generator.repository.QuestionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class QuestionGeneratorService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final QuestionRepository questionRepository;

    @Value("${GEMINI_KEY}")
    private String geminiApiKey;

    @Autowired
    public QuestionGeneratorService(WebClient.Builder webClientBuilder,
                                    ObjectMapper objectMapper,
                                    QuestionRepository questionRepository) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
        this.questionRepository = questionRepository;
    }

    public String generateQuestions(QuestionRequest request) {
        String userPrompt = request.getContent();

        // Request body for Gemini API
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", userPrompt)
                        })
                }
        );

        try {
            // Call Gemini API (updated to use gemini-2.0-flash)
            String response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("generativelanguage.googleapis.com")
                            .path("/v1beta/models/gemini-2.0-flash:generateContent") // ✅ Updated model
                            .queryParam("key", geminiApiKey)
                            .build())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Extract generated text
            String generatedContent = extractGeneratedContent(response);

            // Save to MongoDB
            QuestionDocument document = new QuestionDocument(userPrompt, generatedContent);
            questionRepository.save(document);

            // Save to local markdown file (optional)
            saveAsMarkdown(generatedContent);

            return generatedContent;

        } catch (Exception e) {
            return "Error calling Gemini API: " + e.getMessage();
        }
    }

    private String extractGeneratedContent(String response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Error parsing Gemini API response: " + e.getMessage();
        }
    }

    private void saveAsMarkdown(String content) {
        try {
            Path folderPath = Paths.get("/home/kumar/IdeaProjects/content-question-generator/src/main/java/com/bootcoding/content_question_generator/quesions/");
            Files.createDirectories(folderPath);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH:mm"));
            String filename = "generated_question_" + timestamp + ".md";
            Path filePath = folderPath.resolve(filename);

            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                writer.write(content);
            }

            System.out.println("Saved generated question to: " + filePath);

        } catch (IOException e) {
            System.err.println("Error saving markdown file: " + e.getMessage());
        }
    }
}
