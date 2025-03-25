package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
@PostMapping("/storeEmployeeData")
public ResponseEntity<String> storeEmployeeData(@RequestBody EmployeeDTO employeeDTO){
		boolean result = employeeService.storeEmployeeData(employeeDTO);
		 ResponseEntity<String> response = result ?  ResponseEntity.status(200)
				 .body("details stored successfully"):ResponseEntity.status(400)
				 .body("object not found");
		 return response;
}		
@GetMapping("/getEmployeeData/{mobileNumber}")	
public ResponseEntity<EmployeeDTO> getEmployeeData(@PathVariable("mobileNumber") String mobileNumber){
	EmployeeDTO employeeDTO = employeeService.getEmployeeDetails(mobileNumber);
				 ResponseEntity<EmployeeDTO> response = employeeDTO!=null ?  ResponseEntity.status(200)
						 .body(employeeDTO):ResponseEntity.status(400)
						 .body(null);
				 return response;

}

@PutMapping("/updateEmployeeData/{mobileNumber}")
public ResponseEntity<String> updateEmployeeData(@RequestBody EmployeeDTO employeeDTO,
		@PathVariable("mobileNumber") String MobileNumber){
	boolean result = employeeService.updateEmployeeData(employeeDTO,MobileNumber);
	 ResponseEntity<String> response = result ?  ResponseEntity.status(200)
			 .body("details updated successfully"):ResponseEntity.status(400)
			 .body("object not found");
	 return response;
}		

@DeleteMapping("/deleteEmployee/{mobileNumber}")
public ResponseEntity<String> deleteEmployeeData(@PathVariable("mobileNumber") String mobileNumber){
	boolean result = employeeService.deleteEmployee(mobileNumber);
	 ResponseEntity<String> response = result ?  ResponseEntity.status(200)
			 .body("details deleted successfully"):ResponseEntity.status(400)
			 .body("object not found");
	 return response;
}	
}
