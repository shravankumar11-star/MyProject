package com.example.demo.dto;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeDTO {
	
	private String mobileNumber;

	private String name;
	
	private String age;
	
	private String dob;
	
	private String eid;
	
	}