package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Create a new question
    @PostMapping("/add")
    public Question createQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    // Get all questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    // Get a question by ID
    @GetMapping("/quens/{id}")
    public Optional<Question> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id);
    }

    // Update a question
    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable String id, @RequestBody Question question) {
        question.setId(id); // Ensure ID remains the same
        return questionService.saveQuestion(question);
    }

    // Delete a question
    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
        return "Question deleted successfully!";
    }
    
    
}
