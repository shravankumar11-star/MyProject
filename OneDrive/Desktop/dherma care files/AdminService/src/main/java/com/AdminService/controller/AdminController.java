package com.AdminService.controller;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.AdminService.dto.AdminHelper;
import com.AdminService.dto.CategoryDto;
import com.AdminService.dto.ClinicCredentialsDTO;
import com.AdminService.dto.ClinicDTO;
import com.AdminService.dto.UpdateClinicCredentials;
import com.AdminService.service.AdminServiceImpl;
import com.AdminService.util.OtpUtil;
import com.AdminService.util.Response;
import com.AdminService.util.ResponseStructure;

import ch.qos.logback.core.util.StringUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminServiceImpl serviceImpl;

	/**
	 * @param Takes ADMIN DETAILS
	 * @Operation Saves in Database
	 * @return Account Created
	 */
	@PostMapping("/adminRegister")
	private ResponseEntity<?> adminRegister(@RequestBody AdminHelper helperAdmin) {
		
	    String username = helperAdmin.getUserName();
	    String password = helperAdmin.getPassword();
	    String mobileNumber = helperAdmin.getMobileNumber();

	    if (StringUtils.isBlank(username)) {
	        return new ResponseEntity<>("Please Enter User Name", HttpStatus.BAD_REQUEST);
	    } else if (StringUtils.isBlank(password)) {
	        return new ResponseEntity<>("Please Enter Password", HttpStatus.BAD_REQUEST);
	    } else if (StringUtils.isBlank(mobileNumber)) {
	        return new ResponseEntity<>("Please Enter Mobile Number", HttpStatus.BAD_REQUEST);
	    }

	    // Mobile number validation
	    if (OtpUtil.isMobileNumber(mobileNumber)) {
	        try {
	            Long.parseLong(mobileNumber);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Invalid Mobile Number", HttpStatus.BAD_REQUEST);
	        }
	    } else {
	        return new ResponseEntity<>("Please Enter Valid Mobile Number", HttpStatus.BAD_REQUEST);
	    }

	    
	    // Username and password validation
	    if (!isValidUsername(username)) {
	        return new ResponseEntity<>("Invalid Username: Must start with a capital letter, contain at least one special character and number", HttpStatus.BAD_REQUEST);
	    }

	    if (!isValidPassword(password)) {
	        return new ResponseEntity<>("Invalid Password: Must start with a capital letter, contain at least one special character and number.", HttpStatus.BAD_REQUEST);
	    }

	    serviceImpl.adminRegister(helperAdmin);
	  return new ResponseEntity<>("Admin details are saved", HttpStatus.CREATED);
	
	}
	

	/// check valid userName
	public static boolean isValidUsername(String username) {
       
        // Check first character is uppercase
        if (!Character.isUpperCase(username.charAt(0))) {
            return false;
        }

        // Check for at least one digit
        boolean hasDigit = username.matches(".*\\d.*");

        // Check for at least one special character
        boolean hasSpecialChar = username.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/].*");

        return hasDigit && hasSpecialChar;
    }

	
	// check valid password
	
	public static boolean isValidPassword(String username) {
        
        // Check first character is uppercase
        if (!Character.isUpperCase(username.charAt(0))) {
            return false;
        }

        // Check for at least one digit
        boolean hasDigit = username.matches(".*\\d.*");

        // Check for at least one special character
        boolean hasSpecialChar = username.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/].*");

        return hasDigit && hasSpecialChar;
    }

	/**
	 * @param Takes User Name and Password as Arguments
	 * 
	 *              Verify the Details of ADMIN
	 * 
	 * @return Success and Invalid Details of ADMIN
	 */
	@PostMapping("/adminLogin")
	private ResponseEntity<?> adminLogin(@RequestBody AdminHelper helperAdmin) {

		if (StringUtil.isNullOrEmpty(helperAdmin.getUserName()) || StringUtils.isBlank(helperAdmin.getUserName())) {
			return new ResponseEntity<String>("Please Enter UserName", HttpStatus.BAD_REQUEST);
		} else if (StringUtil.isNullOrEmpty(helperAdmin.getUserName()) || StringUtils.isBlank(helperAdmin.getPassword())) {
			return new ResponseEntity<String>("Please Enter Password", HttpStatus.BAD_REQUEST);
		} else {
			try {
			AdminHelper adminDb = serviceImpl.adminLogin(helperAdmin.getUserName(), helperAdmin.getPassword());
			if (adminDb != null) {
				return new ResponseEntity<String>("Login Sucess", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Data Not Found", HttpStatus.OK);
			}}catch(Exception e) {
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
	}

	/**
	 * @return List of Provider Details
	 * 
	 * @param Does not take any parameters
	 */

///CLINIC CRUD
	
	// Create Clinic
    @PostMapping("/createClinic")
    public Response createClinic(@RequestBody ClinicDTO clinic) {
        return serviceImpl.createClinic(clinic);
    }

    // Get Clinic by ID
    @GetMapping("/getClinicById/{clinicId}")
    public Response getClinicById(@PathVariable String clinicId) {
        return serviceImpl.getClinicById(clinicId);
    }

    // Update Clinic
    @PutMapping("/updateClinic/{clinicId}")
    public Response updateClinic(@PathVariable String clinicId, @RequestBody ClinicDTO clinic) {
        return serviceImpl.updateClinic(clinicId, clinic);
    }

    // Delete Clinic
    @DeleteMapping("/deleteClinic/{clinicId}")
    public Response deleteClinic(@PathVariable String clinicId) {
        return serviceImpl.deleteClinic(clinicId);
    }
   
    
   /// CLINIC CREDENTIALS
 
    // Get clinic credentials by hospitalId
    @GetMapping("/getClinicCredentials/{userName}")
    public Response getClinicCredentials(@PathVariable String userName) {
        return serviceImpl.getClinicCredentials(userName);
    }
    // Update clinic credentials
    @PutMapping("/updateClinicCredentials/{userName}")
    public Response updateClinicCredentials(@RequestBody UpdateClinicCredentials updatedCredentials
    		,@PathVariable String userName) {
        return serviceImpl.updateClinicCredentials(updatedCredentials,userName);
    }
    // Delete clinic credentials
    @DeleteMapping("/deleteClinicCredentials/{userName}")
    public Response deleteClinicCredentials(@PathVariable String userName) {
        return serviceImpl.deleteClinicCredentials(userName);
    }
    
    // clinic admin login
    
    @PostMapping("/login")
    public  ResponseEntity<?> login( @RequestBody ClinicCredentialsDTO credentials) {
    Response res =  serviceImpl.login(credentials);
    return ResponseEntity.status(200).body(res);
}

    
   
    /// Category Management
   
    @PostMapping("/addCategory")
	public ResponseEntity<?> addNewCategory(@RequestBody CategoryDto dto) {
		return serviceImpl.addNewCategory(dto);
    }
    
    @GetMapping("/getCategories")
	public ResponseEntity<?> getAllCategory() {
    	return serviceImpl.getAllCategory();
    
    }
    
    @DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<?> deleteCategoryById(
			@PathVariable(value = "categoryId") String categoryId){
    	return serviceImpl.deleteCategoryById(categoryId); 
    }
    	
@PutMapping("updateCategory/{categoryId}")
public ResponseEntity<?> updateCategory(@PathVariable ObjectId categoryId,
		@RequestBody CategoryDto updatedCategory){
	return serviceImpl.updateCategory(categoryId, updatedCategory);
}
}


