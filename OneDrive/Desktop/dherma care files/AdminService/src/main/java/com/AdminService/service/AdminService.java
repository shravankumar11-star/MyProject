package com.AdminService.service;

import com.AdminService.dto.AdminHelper;
import com.AdminService.dto.ClinicDTO;
import com.AdminService.util.Response;


public interface AdminService {

//ADMIN
	
public void adminRegister(AdminHelper helperAdmin);
	
public AdminHelper adminLogin(String userName,String password);
	

//Create a new Clinic
Response createClinic(ClinicDTO clinic);

// Get a Clinic by its ID
Response getClinicById(String clinicId);

// Update an existing Clinic by its ID
Response updateClinic(String clinicId, ClinicDTO clinic);

// Delete a Clinic by its ID
Response deleteClinic(String clinicId);


	 
}