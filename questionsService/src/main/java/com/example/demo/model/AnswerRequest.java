package com.example.demo.model;

public class AnswerRequest {
    private String id;
    private String response;

    // Constructors
    public AnswerRequest() {}

    public AnswerRequest(String id, String response) {
        this.id = id;
        this.response = response;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}
