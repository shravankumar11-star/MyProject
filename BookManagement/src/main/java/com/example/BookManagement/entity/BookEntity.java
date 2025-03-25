package com.example.BookManagement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data///for getters and setters
@Document(collection = "BookManagement")

public class BookEntity {
	@Id
	String id;
	String bookName;
	String authorName;
	
}
