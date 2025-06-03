package com.bootcoding.content_question_generator.repository;

import com.bootcoding.content_question_generator.model.QuestionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<QuestionDocument, String> {
}
