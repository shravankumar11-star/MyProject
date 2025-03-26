package com.example.quizService.circuitBreaker;

import java.util.Optional;

import com.example.quizService.dto.Question;

public class QuestionServiceFallback {
	
	public Optional<Question> genericFallback(String id, Exception e) {
        System.out.println("Fallback executed for ID: " + id + " due to: " + e.getMessage());
        return Optional.empty(); // Returning an empty optional as a default fallback
    }

}
