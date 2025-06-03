package com.bootcoding.content_question_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "generated_questions")
public class QuestionDocument {

    // Getters and Setters
    @Id
    private String id;
    @Setter
    private String inputPrompt;
    @Setter
    private String generatedContent;
    @Setter
    private LocalDateTime createdAt;

    public QuestionDocument() {}

    public QuestionDocument(String inputPrompt, String generatedContent) {
        this.inputPrompt = inputPrompt;
        this.generatedContent = generatedContent;
        this.createdAt = LocalDateTime.now();
    }

}
