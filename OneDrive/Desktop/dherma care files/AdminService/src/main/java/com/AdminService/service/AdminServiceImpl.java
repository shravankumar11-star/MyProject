package com.AdminService.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.AdminService.dto.AdminHelper;
import com.AdminService.dto.CategoryDto;
import com.AdminService.dto.ClinicCredentialsDTO;
import com.AdminService.dto.ClinicDTO;
import com.AdminService.dto.UpdateClinicCredentials;
import com.AdminService.entity.Admin;
import com.AdminService.entity.Clinic;
import com.AdminService.entity.ClinicCredentials;
import com.AdminService.feign.CategoryFeign;
import com.AdminService.repository.AdminRepository;
import com.AdminService.repository.ClinicCredentialsRepository;
import com.AdminService.repository.ClinicRep;
import com.AdminService.util.Response;
import com.AdminService.util.ResponseStructure;

import feign.FeignException;



@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ClinicRep clinicRep;
	
	@Autowired
	private ClinicCredentialsRepository clinicCredentialsRepository;
	
	@Autowired
	private CategoryFeign categoryFeign;
	
	
	@Override
	public void adminRegister(AdminHelper helperAdmin) {
	
		  Admin entityAdmin = new Admin();
		        entityAdmin.setUserName(helperAdmin.getUserName());
		        entityAdmin.setPassword(helperAdmin.getPassword());
		        entityAdmin.setMobileNumber(Long.parseLong(helperAdmin.getMobileNumber()));
		        adminRepository.save(entityAdmin);
	}
	
	@Override
	public AdminHelper adminLogin(String userName, String password) {
		
		Optional<Admin> admin = adminRepository.findByUsernameAndPassword(userName, password);
		if(admin.isPresent()) {
			
			Admin adminObj = admin.get();
			AdminHelper  adminHelper = new AdminHelper(
					String.valueOf(adminObj.getMobileNumber()),  // Pass mobileNumber
					adminObj.getUserName(),     // Pass userName
					adminObj.getPassword()      // Pass password
				);
			return adminHelper;
			}
		else {
			return null;
		}
		
	}
	
	//CLINIC CRUD
	
	// Create Clinic
    public Response createClinic(ClinicDTO clinic) {
        Response response = new Response();
        try {
        	
        	Clinic savedClinic = new Clinic();
        	savedClinic.setAddress(clinic.getAddress());
        	savedClinic.setCity(clinic.getCity());
        	List<byte[]> docs = new ArrayList<>();
        	for(String document : clinic.getHospitalDocuments()) {
        		byte[] doc = Base64.getDecoder().decode(document);
        	docs.add(doc);
        	}
        	savedClinic.setHospitalDocuments(docs);	
        	savedClinic.setHospitalId(generateHospitalId());
        	savedClinic.setHospitalLogo(Base64.getDecoder().decode(clinic.getHospitalLogo()));
        	savedClinic.setHospitalOverallRating(clinic.getHospitalOverallRating());
        	savedClinic.setClosingTime(clinic.getClosingTime());
        	savedClinic.setClosingTime(clinic.getClosingTime());
        	savedClinic.setContactNumber(clinic.getContactNumber());
        	savedClinic.setName(clinic.getName());
        	savedClinic.setOpeningTime(clinic.getOpeningTime());
        	savedClinic.setHospitalRegistrations(clinic.getHospitalRegistrations());
        	savedClinic.setHospitalCategory(clinic.getHospitalCategory());
        	savedClinic.setHospitalServices(clinic.getHospitalServices());
        	savedClinic.setEmailAddress(clinic.getEmailAddress());
        	savedClinic.setWebsite(clinic.getWebsite());
        	savedClinic.setLicenseNumber(clinic.getLicenseNumber());
        	savedClinic.setIssuingAuthority(clinic.getIssuingAuthority());
        	Clinic clnc = clinicRep.save(savedClinic);
        
        	if(clnc != null) {
            response.setMessage("Clinic created successfully");
            response.setSucess(true);
            response.setStatus(200);
            ClinicCredentials clinicCredentials = new ClinicCredentials();
            clinicCredentials.setUserName(clnc.getHospitalId());
            clinicCredentials.setPassword(generatePassword(9));
            clinicCredentialsRepository.save(clinicCredentials);
            }
        	
        } catch (Exception e) {
            response.setMessage("Error occurred while creating the clinic: " + e.getMessage());
            response.setSucess(false);
            response.setStatus(500); // HTTP Status for Internal Server Error
        }
        return response;
    }

    // Read Clinic (Get by ID)
    public Response getClinicById(String clinicId) {
    	 Response response = new Response();
    	try {
        Clinic clinic = clinicRep.findByHospitalId(clinicId);
        if (clinic !=  null) {
        ClinicDTO clnc = new  ClinicDTO();
        clnc.setAddress(clinic.getAddress());
        clnc.setCity(clinic.getCity());
        List<String> docs = new ArrayList<>();
        for(byte[] document : clinic.getHospitalDocuments()) {
    		String doc = Base64.getEncoder().encodeToString(document);
    	docs.add(doc);	
    	}
        clnc.setHospitalDocuments(docs);
        clnc.setHospitalId(clinic.getHospitalId());
        clnc.setHospitalLogo(Base64.getEncoder().encodeToString(clinic.getHospitalLogo()));
        clnc.setHospitalOverallRating(clinic.getHospitalOverallRating());
        clnc.setHospitalRegistrations(clinic.getHospitalRegistrations());
        clnc.setEmailAddress(clinic.getEmailAddress());
        clnc.setWebsite(clinic.getWebsite());
        clnc.setLicenseNumber(clinic.getLicenseNumber());
        clnc.setIssuingAuthority(clinic.getIssuingAuthority());
        clnc.setClosingTime(clinic.getClosingTime());
        clnc.setContactNumber(clinic.getContactNumber());
        clnc.setName(clinic.getName());
        clnc.setOpeningTime(clinic.getOpeningTime());
        clnc.setEmailAddress(clinic.getEmailAddress());
        clnc.setEmailAddress(clinic.getEmailAddress());
        clnc.setLicenseNumber(clinic.getLicenseNumber());
        clnc.setIssuingAuthority(clinic.getIssuingAuthority());
        clnc.setHospitalCategory(clinic.getHospitalCategory());
        clnc.setHospitalServices(clinic.getHospitalServices());
        
            response.setMessage("Clinic fetched successfully");
            response.setSucess(true);
            response.setStatus(200); // HTTP Status for OK
            response.setData(clnc);
        } else {
            response.setMessage("Clinic not found");
            response.setSucess(false);
            response.setStatus(404);
            return response;// HTTP Status for Not Found
        }}catch(Exception e) {
        	 response.setMessage("Exception occured");
             response.setSucess(false);
             response.setStatus(400);
        }
        return response;
    }

    // Update Clinic
    public Response updateClinic(String clinicId, ClinicDTO clinic) {
        Response response = new Response();
        try {
            Clinic savedClinic = clinicRep.findByHospitalId(clinicId);
            if (savedClinic != null) {

                // Preserve the original Mongo _id
               // String existingId = savedClinic.getId();

                savedClinic.setAddress(clinic.getAddress());
                savedClinic.setCity(clinic.getCity());

                List<byte[]> docs = new ArrayList<>();
                for (String document : clinic.getHospitalDocuments()) {
                    docs.add(Base64.getDecoder().decode(document));
                }
                savedClinic.setHospitalDocuments(docs);

                savedClinic.setHospitalLogo(Base64.getDecoder().decode(clinic.getHospitalLogo()));
                savedClinic.setHospitalOverallRating(clinic.getHospitalOverallRating());
                savedClinic.setClosingTime(clinic.getClosingTime());
                savedClinic.setContactNumber(clinic.getContactNumber());
                savedClinic.setName(clinic.getName());
                savedClinic.setOpeningTime(clinic.getOpeningTime());
                savedClinic.setHospitalRegistrations(clinic.getHospitalRegistrations());
                savedClinic.setHospitalCategory(clinic.getHospitalCategory());
                savedClinic.setHospitalServices(clinic.getHospitalServices());
                savedClinic.setEmailAddress(clinic.getEmailAddress());
                savedClinic.setWebsite(clinic.getWebsite());
                savedClinic.setLicenseNumber(clinic.getLicenseNumber());
                savedClinic.setIssuingAuthority(clinic.getIssuingAuthority());

                // 💥 Set ID again before saving
              //  savedClinic.setId(existingId);

                Clinic updated = clinicRep.save(savedClinic);
                if (updated != null) {
                    response.setMessage("Clinic updated successfully");
                    response.setSucess(true);
                    response.setStatus(200);
                }

            } else {
                response.setMessage("Clinic not found for update");
                response.setSucess(false);
                response.setStatus(404);
            }
        } catch (Exception e) {
            response.setMessage("Error occurred while updating the clinic: " + e.getMessage());
            response.setSucess(false);
            response.setStatus(500);
        }
        return response;
    }

    // Delete Clinic
    public Response deleteClinic(String clinicId) {
        Response responseDTO = new Response();
        try {
            Clinic clinic = clinicRep.findByHospitalId(clinicId);
            if (clinic != null) {
                clinicRep.deleteByHospitalId(clinicId);
                responseDTO.setMessage("Clinic deleted successfully");
                responseDTO.setSucess(true);
                responseDTO.setStatus(200); // HTTP Status for OK
            } else {
                responseDTO.setMessage("Clinic not found for deletion");
                responseDTO.setSucess(false);
                responseDTO.setStatus(404); // HTTP Status for Not Found
            }
        } catch (Exception e) {
            responseDTO.setMessage("Error occurred while deleting the clinic: " + e.getMessage());
            responseDTO.setSucess(false);
            responseDTO.setStatus(500); // HTTP Status for Internal Server Error
        }
        return responseDTO;
    }
    
    
    //generatePassword
    public static String generatePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4.");
        }

        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$&_";

        Random random = new Random();

        // First character - must be uppercase
        char firstChar = upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length()));

        // Ensure at least one special character and one digit
        char specialChar = specialChars.charAt(random.nextInt(specialChars.length()));
        char digit = digits.charAt(random.nextInt(digits.length()));

        // Remaining characters pool
        String allChars = upperCaseLetters + lowerCaseLetters + digits + specialChars;
        StringBuilder remaining = new StringBuilder();

        for (int i = 0; i < length - 3; i++) {
            remaining.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Build the password and shuffle to randomize the positions (except first char)
        List<Character> passwordChars = new ArrayList<>();
        for (char c : remaining.toString().toCharArray()) {
            passwordChars.add(c);
        }

        // Add guaranteed special and digit
        passwordChars.add(specialChar);
        passwordChars.add(digit);

        // Shuffle rest except first character
        Collections.shuffle(passwordChars);
        StringBuilder password = new StringBuilder();
        password.append(firstChar);
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
    
    // generateHospitalId
    public String generateHospitalId() {
        // Fetch the last clinic document ordered by HospitalId in descending order
        Clinic lastClinic = clinicRep.findFirstByOrderByHospitalIdDesc();
        
        // If no clinics exist in the database, return the first hospital ID "H_1"
        if (lastClinic == null) {
            return "H_1";
        }

        // Get the last HospitalId from the last clinic record
        String lastHospitalId = lastClinic.getHospitalId();
        
        // Define the regex pattern to match "H_" followed by a number (e.g., H_123)
        Pattern pattern = Pattern.compile("H_(\\d+)");
        Matcher matcher = pattern.matcher(lastHospitalId);

        // If the last ID is in the expected format (H_1, H_2, etc.)
        if (matcher.matches()) {
            // Extract the numeric part from the last hospital ID
            String numberPart = matcher.group(1);
            
            // Increment the numeric part by 1 to generate the next ID
            int nextNumber = Integer.parseInt(numberPart) + 1;
            
            // Return the new HospitalId in the format "H_{nextNumber}"
            return "H_" + nextNumber;
        } else {
            // If no valid format is found, return "H_1" (indicating the first hospital)
            return "H_1";
        }
    }
	
/// CLINIC CREDENTIALS CRUD
    
    // Get clinic credentials by hospitalId
    public Response getClinicCredentials(String userName) {
        Response response = new Response();
        try {
            ClinicCredentials clinicCredentials = clinicCredentialsRepository.findByUserName(userName);
            if (clinicCredentials != null) {
            	ClinicCredentialsDTO clinicCredentialsDTO = new ClinicCredentialsDTO();
            	clinicCredentialsDTO.setUserName(clinicCredentials.getUserName());
            	clinicCredentialsDTO.setPassword(clinicCredentials.getPassword());
                response.setSucess(true);
                response.setData(clinicCredentialsDTO );
                response.setMessage("Clinic credentials found.");
                response.setStatus(200); // HTTP status for OK
            } else {
                response.setSucess(false);
                response.setMessage("Clinic credentials not found.");
                response.setStatus(404); // HTTP status for Not Found
            }
        } catch (Exception e) {
            response.setSucess(false);
            response.setMessage("Error retrieving clinic credentials: " + e.getMessage());
            response.setStatus(500); // Internal server error
        }
        return response;
    }

    // Update clinic credentials by hospitalId
    public Response updateClinicCredentials(UpdateClinicCredentials credentials,String userName) {
        Response response = new Response();
        try {
           ClinicCredentials existingCredentials = clinicCredentialsRepository.
           findByUserNameAndPassword(userName,credentials.getPassword());
            if (existingCredentials != null) {
            if( credentials.getNewPassword().equalsIgnoreCase(credentials.getConfirmPassword())) {
               existingCredentials.setPassword(credentials.getNewPassword());
            	ClinicCredentials c = clinicCredentialsRepository.save(existingCredentials);
            	if(c != null) {
            	response.setSucess(true);
                response.setData(null);
                response.setMessage("Clinic credentials updated successfully.");
                response.setStatus(200);
                return response;
            } else {
                response.setSucess(false);
                response.setMessage("Clinic credentials not saved.");
                response.setStatus(400); 
                return response;// HTTP status for Not Found
            }}else {
            	 response.setSucess(false);
                 response.setMessage("NewPassword and ConfirmPassword not match.");
                 response.setStatus(400);
            	return response;
            }}else {
            	response.setSucess(false);
                response.setMessage("Invalid Credentials.");
                response.setStatus(400);
           	return response;
            }}
           catch (Exception e) {
            response.setSucess(false);
            response.setMessage("Error updating clinic credentials: " + e.getMessage());
            response.setStatus(500); // Internal server error
        return response;}
    }

    // Delete clinic credentials by hospitalId
    public Response deleteClinicCredentials(String userName ) {
        Response response = new Response();
        try {
            ClinicCredentials clinicCredentials = clinicCredentialsRepository.findByUserName(userName);
            if (clinicCredentials != null) {
                clinicCredentialsRepository.delete(clinicCredentials);
                response.setSucess(true);
                response.setMessage("Clinic credentials deleted successfully.");
                response.setStatus(200); // HTTP status for OK
            } else {
                response.setSucess(false);
                response.setMessage("Clinic credentials not found.");
                response.setStatus(404); // HTTP status for Not Found
            }
        } catch (Exception e) {
            response.setSucess(false);
            response.setMessage("Error deleting clinic credentials: " + e.getMessage());
            response.setStatus(500); // Internal server error
        }
        return response;
    }
    
    //login
    public Response login(ClinicCredentialsDTO credentials) {
    	Response response = new Response();
    	try {
    	String userName = credentials.getUserName();
    	String password = credentials.getPassword();
    	if(userName == null || userName.isBlank()) {
    		response.setSucess(false);
    		response.setMessage("username is required");
    		response.setStatus(401);	
    		return response;
    	}
    	if(password == null || password.isBlank()) {
    		response.setSucess(false);
    		response.setMessage(" password is required");
    		response.setStatus(401);	
    		return response;
    	}
    	ClinicCredentials clinicCredentials =  clinicCredentialsRepository.findByUserNameAndPassword(userName, password);
    	if(clinicCredentials != null) {
    		response.setSucess(true);
    		response.setMessage("login successful");
    		response.setStatus(200);	
    		}
    	else {
    		response.setSucess(false);
    		response.setMessage("invalid username or password");
    		response.setStatus(401);	
    	}
    	return response;
    	}
    	catch(Exception e){
    		response.setSucess(false);
    		response.setMessage("login unsuccessful exception occurs");
    		response.setStatus(401);
    		return response;
    	}
    }

	
    // Category Management
    
    public ResponseEntity<?> addNewCategory(CategoryDto dto){
    	try {
    	return categoryFeign.addNewCategory(dto);
    	}
    	catch(FeignException e){
    	return ResponseEntity.status(e.status()).body(e.getMessage());
    	}
    }
     
	public ResponseEntity<?> getAllCategory() {
		try {
     return categoryFeign.getAllCategory();
     }catch(FeignException e){
     	return ResponseEntity.status(e.status()).body(e.getMessage());
     	}
	}
	
	public ResponseEntity<?> deleteCategoryById(
			 String categoryId) {
		try {
		return categoryFeign.deleteCategoryById(categoryId);
	}catch(FeignException e){
     	return ResponseEntity.status(e.status()).body(e.getMessage());
     	}
	}
	
	public ResponseEntity<?> updateCategory(ObjectId categoryId,
			 CategoryDto updatedCategory){
		try {
		return categoryFeign.updateCategory(categoryId, updatedCategory);
	}catch(FeignException e){
     	return ResponseEntity.status(e.status()).body(e.getMessage());
     	}
	}
}
	
