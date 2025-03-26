package com.example.quizService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "quizzes") // Specifies MongoDB collection
public class Quiz {

    @Id
    private String id;
    private String title;
    private String description;
    private List<String> questionIds; // List of Question IDs

    // Constructors
    public Quiz() {}

    public Quiz(String title, String description, List<String> questionIds) {
        this.title = title;
        this.description = description;
        this.questionIds = questionIds;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getQuestionIds() { return questionIds; }
    public void setQuestionIds(List<String> questionIds) { this.questionIds = questionIds; }
}
