package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("EmployeeData")
public class Employee {
@Id
    private String id;

    private String mobileNumber;
	private String name;
	
	private String age;
	
	private String dob;
	
	private String eid;
	
	}
