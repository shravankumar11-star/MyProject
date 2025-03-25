package com.example.BookManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.BookManagement.dto.BookDto;
import com.example.BookManagement.entity.BookEntity;
import com.example.BookManagement.repository.RepositoryLayer;

import lombok.AllArgsConstructor;


@Service
public class Serviceclass {
    @Autowired
	private RepositoryLayer repository;
	
	public BookDto storeData(BookDto bookDto) {
		
		BookEntity bookEntity = new BookEntity();
		bookEntity.setBookName(bookDto.getBookName());
		bookEntity.setAuthorName(bookDto.getAuthorName());
		BookEntity c = repository.save(bookEntity);
		BookDto bookDtos = new BookDto();
		bookDtos.setAuthorName(c.getAuthorName());
		bookDtos.setBookName(c.getBookName());
		return  bookDtos;
		
	}
	
	public BookDto getData(String id) {
		
		Optional<BookEntity> c = repository.findById(id);
		
		BookEntity obj = c.get();
		
		 BookDto v = new  BookDto();
		 v.setAuthorName(obj.getAuthorName());
		 v.setBookName(obj.getBookName());
		 return v;
		
	}
	
	public BookDto putdata(BookDto bookDtos,  String id) {
		Optional<BookEntity> c = repository.findById(id);
		BookEntity obj = c.get();
		obj.setAuthorName(bookDtos.getAuthorName());
		obj.setBookName( bookDtos.getBookName());
		BookEntity object = repository.save(obj);
		 BookDto v = new  BookDto();
		 v.setAuthorName(object.getAuthorName());
		 v.setBookName(object.getBookName());
		 return v;
		
		
	}
	
	public boolean deleteData(String id) {
		
		Optional<BookEntity> c = repository.findById(id);
		BookEntity obj = c.get();
		if(obj != null) {
			repository.deleteById(id);
			return true;
		}
		else {
			return false;
		}
		
	}

}
