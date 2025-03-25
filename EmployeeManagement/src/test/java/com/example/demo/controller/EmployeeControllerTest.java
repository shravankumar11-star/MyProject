package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeServiceTest;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mockMvc ;
	
	@MockBean
	private EmployeeServiceTest employeeServiceTest;
	Employee employeeOne ;
	Employee employeeTwo;
	
	
	
	
	
	
}
