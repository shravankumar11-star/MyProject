package com.example.quizService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.quizService.model.Quiz;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {
    // Custom query methods if needed
}
