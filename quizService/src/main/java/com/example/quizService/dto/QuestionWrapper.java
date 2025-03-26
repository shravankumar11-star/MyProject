package com.example.quizService.dto;

import java.util.List;

public class QuestionWrapper {
    private String id;
    private String title;
    private String category;
    private List<String> options;

    // Constructors
    public QuestionWrapper() {}

    public QuestionWrapper(String id, String title, String category, List<String> options) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.options = options;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}

