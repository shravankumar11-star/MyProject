package com.example.BookManagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.BookManagement.entity.BookEntity;

@Repository
public interface RepositoryLayer extends MongoRepository<BookEntity, String> {
	
	public BookEntity findByauthorName(String author);

}
