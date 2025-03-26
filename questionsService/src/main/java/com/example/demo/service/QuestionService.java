package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Create or Update a Question
    public Question saveQuestion(Question question) {
        if( questionRepository.save(question)!= null) {
        	return questionRepository.save(question);
        }
        return null;
        
    }

    // Get all Questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Get a single Question by ID
    public Optional<Question> getQuestionById(String id) {
        return questionRepository.findById(id);
    }

    // Delete a Question by ID
    public void deleteQuestion(String id) {
        questionRepository.deleteById(id);
    }
}
