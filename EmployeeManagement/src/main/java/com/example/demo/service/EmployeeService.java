package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EployeeInterface;

@Service
public class EmployeeService {
	@Autowired
	private EployeeInterface eployeeInterface;
public boolean storeEmployeeData(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		employee.setMobileNumber(employeeDTO.getMobileNumber());
		employee.setAge(employeeDTO.getAge());
		employee.setDob(employeeDTO.getDob());
		employee.setEid(employeeDTO.getEid());
		employee.setName(employeeDTO.getName());
		Employee employeeObj = eployeeInterface.save(employee);
		if(employeeObj==null) {
			return false;
		}
		return true;
		
	}
	
	
public EmployeeDTO getEmployeeDetails(String mobileNumber) {
		
		Employee employee = eployeeInterface.findByMobileNumber(mobileNumber);
		EmployeeDTO employeedto = new EmployeeDTO();
		employeedto.setAge( employee.getAge());
		employeedto.setDob( employee.getDob());
		employeedto.setEid( employee.getEid());
		employeedto.setMobileNumber( employee.getMobileNumber());
		employeedto.setName( employee.getName());
		return employeedto;
	
		}
	


public boolean updateEmployeeData(EmployeeDTO employeeDTO, String mobileNumber) {
	Employee employee = eployeeInterface.findByMobileNumber(mobileNumber);
	employee.setMobileNumber(employeeDTO.getMobileNumber());
	employee.setAge(employeeDTO.getAge());
	employee.setDob(employeeDTO.getDob());
	employee.setEid(employeeDTO.getEid());
	employee.setName(employeeDTO.getName());
	Employee employeeObj = eployeeInterface.save(employee);
	if(employeeObj==null) {
		return false;
	}
	return true;
	
}


public boolean deleteEmployee(String mobileNumber) {
	Employee employee = eployeeInterface.findByMobileNumber(mobileNumber);
	if(employee==null) {
		
		return false;
	}
	eployeeInterface.delete(employee);
	return true;
	
}

}
  

