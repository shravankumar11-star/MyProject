package com.example.quizService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.quizService.dto.QuestionWrapper;
import com.example.quizService.model.Quiz;
import com.example.quizService.service.QuizService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService  quizService;

    // Create a new quiz
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    // Get all quizzes
    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    // Get a quiz by ID
    @GetMapping("/{id}")
    public Optional<Quiz> getQuizById(@PathVariable String id) {
        return quizService.getQuizById(id);
    }

    // Update a quiz
    @PutMapping("/{id}")
    public Quiz updateQuiz(@PathVariable String id, @RequestBody Quiz quiz) {
        quiz.setId(id); // Ensure ID remains the same
        return quizService.saveQuiz(quiz);
    }

    // Delete a quiz
    @DeleteMapping("/{id}")
    public String deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return "Quiz deleted successfully!";
    }
    
    @GetMapping("/quens/{id}")
    public com.example.quizService.dto.Question getQuestionsById(@PathVariable String id) {
    	return quizService.getQuestionsById(id).get();
    	
    }
}
