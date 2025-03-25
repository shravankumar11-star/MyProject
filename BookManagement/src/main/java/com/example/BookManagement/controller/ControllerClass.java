package com.example.BookManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookManagement.dto.BookDto;
import com.example.BookManagement.service.Serviceclass;

@RestController
@RequestMapping("/BookService")
public class ControllerClass {
	@Autowired
	public Serviceclass obj;
	
	@PostMapping("/store")
	public BookDto storeData(@RequestBody BookDto bookDto) {
		BookDto b = obj.storeData(bookDto);
		return b;
	}
	
	@GetMapping("/getdata/{id}")
	
	public BookDto getBookData(@PathVariable("id") String id) {
		 BookDto  bookDto  = obj.getData(id);
		return bookDto;
	}
	
	@PutMapping("/modifydata/{id}")
	
	public BookDto putData(@RequestBody BookDto bookDtos ,@PathVariable("id") String id) {
		
		 BookDto  bookDto  = obj.putdata(bookDtos, id);
		 return bookDto;
	}
	
	@DeleteMapping("/deleteData/{id}")
	
	public String deleteData(@PathVariable("id") String id) {
		boolean a = obj.deleteData(id);
		if(a == true) {
			return "object deleted successfully";
			
		}
		else {
			return "object not found";
		}
	}

}
