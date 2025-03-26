package com.example.quizService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quizService.feign.Feign;

import com.example.quizService.model.Quiz;
import com.example.quizService.repository.QuizRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private Feign feign;

    // Create or Update a Quiz
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    // Get all Quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Get a single Quiz by ID
    public Optional<Quiz> getQuizById(String id) {
        return quizRepository.findById(id);
    }

    // Delete a Quiz by ID
    public void deleteQuiz(String id) {
    	feign. deleteQuestion(id);
    }
    
    public Optional<com.example.quizService.dto.Question> getQuestionsById(String id){
    	return feign.getQuestionById(id);
    }
}

