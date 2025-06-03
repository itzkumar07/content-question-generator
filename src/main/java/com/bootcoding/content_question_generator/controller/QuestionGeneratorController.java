package com.bootcoding.content_question_generator.controller;

import com.bootcoding.content_question_generator.dto.QuestionRequest;
import com.bootcoding.content_question_generator.service.QuestionGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin
@RequiredArgsConstructor
public class QuestionGeneratorController {

    private final QuestionGeneratorService questionGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateQuestions(@RequestBody QuestionRequest request) {
        String response = questionGeneratorService.generateQuestions(request);
        return ResponseEntity.ok(response);
    }
}
