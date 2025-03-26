package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions") // Specifies MongoDB collection

public class Question {

    @Id
    private String id;
    private String title;
    private String description;
    private String category;

    // Constructors
    public Question() {}

    public Question(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}

