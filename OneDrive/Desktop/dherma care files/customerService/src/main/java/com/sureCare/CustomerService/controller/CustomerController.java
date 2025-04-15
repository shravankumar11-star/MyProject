package com.sureCare.CustomerService.controller;

import static  com.sureCare.CustomerService.util.DharmaCareUtils.CUSTOMER_BASIC_DETAILS;
import static  com.sureCare.CustomerService.util.DharmaCareUtils.CUSTOMER_RESEND_OTP;
import static com.sureCare.CustomerService.util.DharmaCareUtils.CUSTOMER_SAVE_ADDRESS;
import static  com.sureCare.CustomerService.util.DharmaCareUtils.CUSTOMER_SIGN_UP_OR_SIGN_IN;
import static  com.sureCare.CustomerService.util.DharmaCareUtils.CUSTOMER_VERIFY_OTP;
import static  com.sureCare.CustomerService.util.DharmaCareUtils.SURE_CARE_CONSUMES;
import static  com.sureCare.CustomerService.util.DharmaCareUtils.SURE_CARE_PRODUCES;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sureCare.CustomerService.dto.AddressDTO;
import com.sureCare.CustomerService.dto.ConsultationDTO;
import com.sureCare.CustomerService.dto.CustomerDTO;
import com.sureCare.CustomerService.service.CustomerService;
import com.sureCare.CustomerService.service.CustomerServiceImpl;
import com.sureCare.CustomerService.util.OtpUtil;
import com.sureCare.CustomerService.util.ResponseDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerServiceImpl serviceImpl;

	
	/**
	 * @param SignIn  or SignUp of Customer By taking UserName and MobileNumber
	 * @param Expires session up to 1 minute
	 * @return OTP for Customer
	 */
//	@PostMapping(value = CUSTOMER_SIGN_UP_OR_SIGN_IN, consumes = SURE_CARE_CONSUMES, produces = SURE_CARE_PRODUCES)
//	public ResponseEntity<ResponseDTO> registerOrLogin(@RequestBody CustomerDTO customerDTO, HttpSession session) {
//		ResponseDTO response = customerService.signInOrSignUp(customerDTO.getFullName().trim(),
//				customerDTO.getMobileNumber().trim(), session);
//		return ResponseEntity.status(response.getStatus()).body(response);
//	}
//
//	/**
//	 * @param Verify the OTp
//	 * @return Success Message after Verify
//	 */
//	@PostMapping(value = CUSTOMER_VERIFY_OTP, consumes = SURE_CARE_CONSUMES)
//	public ResponseEntity<ResponseDTO> verifyOtp(@RequestBody Map<String, String> otpData, HttpSession session) {
//		String enteredOtp = otpData.get("enteredOtp").trim();
//		String mobileNumber = otpData.get("mobileNumber").trim();
//
//		ResponseDTO response = customerService.verifyOtp(enteredOtp, mobileNumber, session);
//		return ResponseEntity.status(response.getStatus()).body(response);
//	}
//
//	/**
//	 * @param Resends OTP to customer
//	 * @param session
//	 * @return OTP to Customer
//	 */
//	@PostMapping(value = CUSTOMER_RESEND_OTP, consumes = SURE_CARE_CONSUMES)
//	public ResponseEntity<ResponseDTO> resendOtp(@RequestBody Map<String, String> requestData, HttpSession session) {
//		String mobileNumber = requestData.get("mobileNumber").trim();
//		ResponseDTO response = customerService.resendOtp(mobileNumber, session);
//		return ResponseEntity.status(response.getStatus()).body(response);
//	}

	
	
	@PostMapping(value = CUSTOMER_BASIC_DETAILS, consumes = SURE_CARE_CONSUMES)
	public ResponseEntity<ResponseDTO> completeBasicDetailsRegistration(@RequestBody CustomerDTO customerDTO) {
		ResponseDTO response = customerService.basicDetails(customerDTO.getFullName().trim(),
				customerDTO.getMobileNumber().trim(), customerDTO.getGender().trim(),
				customerDTO.getBloodGroup().trim(), customerDTO.getAge(), customerDTO.getEmailId().trim(),
				customerDTO.getAddresses(), customerDTO.getStatus(), customerDTO.getRemark());
		return ResponseEntity.status(response.getStatus()).body(response);
	}

    /**
     * @param Customer Address
     * @param mobileNumber
     * @return Success Message.
     */
    @PostMapping(value = CUSTOMER_SAVE_ADDRESS,consumes = SURE_CARE_CONSUMES)
    public ResponseEntity<ResponseDTO> saveAddress(@RequestBody AddressDTO addressDTO, @RequestParam String mobileNumber) {
        ResponseDTO response = customerService.saveAddress(mobileNumber.trim(), addressDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    
    
  //CUSTOMER GET MAPPINGS

  	@GetMapping("/getCustomerDetails")
  	private ResponseEntity<?> getCustomerDetails() {
  		List<CustomerDTO> customerDTO = serviceImpl.getAllCustomerDetails();
  		if(customerDTO != null) {
  		return new ResponseEntity<>(customerDTO, HttpStatus.OK);
  	}else {
  		return new ResponseEntity<>("object is not found", HttpStatus.OK);
  	}
  	}
  	/**
  	 * 
  	 * @param Single Input takes UserName or MobileNumber or EmailId
  	 * 
  	 * @return A Customer Based on the ADMIN requirement
  	 */
  	@GetMapping("/getCustomerByInput/{input}")
  	private ResponseEntity<?> getCustomerByUsernameMobileEmail(@PathVariable String input) {
  		if (input == null || input.equals("") || input.equals(" ")) {
  			return new ResponseEntity<String>("Please Enter a valid input ", HttpStatus.BAD_REQUEST);
  		} else {
  			if (OtpUtil.isMobileNumber(input)) {
  				Long mobilenumber;
  				try {
  					mobilenumber = Long.parseLong(input);
  				} catch (Exception e) {
  					return new ResponseEntity<String>("Please Enter a MobileNumber in Range ", HttpStatus.BAD_REQUEST);
  				}
  				CustomerDTO optCustomer = serviceImpl.getCustomerDetailsByMobileNumber(mobilenumber);
  				if (optCustomer != null) {
  					return new ResponseEntity<CustomerDTO>(optCustomer, HttpStatus.OK);
  				} else {
  					return new ResponseEntity<String>("Customer Data not found", HttpStatus.OK);
  				}
  			} else {
  				if (OtpUtil.isEmail(input)) {
  					CustomerDTO optCustomer = serviceImpl.getCustomerDetailsByEmail(input);
  					if (optCustomer != null) {
  					
  						return new ResponseEntity<CustomerDTO>(optCustomer, HttpStatus.OK);
  					} else {
  						return new ResponseEntity<String>("Customer Data not found Please enter a valid email",
  								HttpStatus.OK);
  					}
  				} else {
  					List<CustomerDTO> listCustomers = serviceImpl.getCustomerByfullName(input);
  					if (listCustomers.isEmpty()) {
  						return new ResponseEntity<String>("Customers Data Not found", HttpStatus.OK);
  					} else {
  						return new ResponseEntity<List<CustomerDTO>>(listCustomers, HttpStatus.OK);
  					}
  				}
  			}
  		}
  	}

  	/**
  	 * 
  	 * @param Single Input takes UserName or MobileNumber or EmailId
  	 * 
  	 * @return A Provider Based on the ADMIN requirement
  	 */

  	@GetMapping("/getBasicCustomerDetails/{mobileNumber}")
  	public ResponseEntity<?> getBasicCustomerDetails(@PathVariable String mobileNumber) {
  		CustomerDTO customer = serviceImpl.getBasicCustomerDetails(mobileNumber);
  		if (customer == null) {
  			return ResponseEntity.status(200).body("Customer not found with mobile number: " + mobileNumber);
  		}
  		return ResponseEntity.ok(customer); // Return the Customer entity (could be a DTO in a real case)
  	}

  	@GetMapping("/getCustomerAddresses/{mobileNumber}")
  	public ResponseEntity<?> getCustomerAddresses(@PathVariable String mobileNumber) {
  		try {
  			// Get the list of addresses for the customer
  			List<AddressDTO> addresses = serviceImpl.getCustomerAddresses(mobileNumber);

  			// If no addresses are found, return 404
  			if (addresses.isEmpty()) {
  				return ResponseEntity.status(HttpStatus.NOT_FOUND)
  						.body("No addresses found for mobile number: " + mobileNumber);
  			}

  			// If addresses are found, return them with a 200 OK status
  			return ResponseEntity.ok(addresses);

  		} catch (IllegalArgumentException | NoSuchElementException e) {
  			// Handle invalid mobile number or customer not found
  			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  		} catch (Exception e) {
  			// Catch any other unexpected exceptions and return a 500 Internal Server Error
  			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
  					.body("An unexpected error occurred: " + e.getMessage());
  		}
  	}

  //CUSTOMER PUT MAPPINGS

  	@PutMapping("/updateCustomerBasicDetails/{mobileNumber}")
  	public ResponseEntity<ResponseDTO> updateCustomerDetails(@RequestBody CustomerDTO customerDTO,
  			@PathVariable String mobileNumber) {
  		ResponseDTO response = serviceImpl.updateCustomerDetails(mobileNumber.trim(), customerDTO);
  		return ResponseEntity.status(response.getStatus()).body(response);
  	}

  	@PutMapping("/updateAddresses/{mobileNumber}/{index}")
      public ResponseDTO updateAddress(
              @PathVariable int index, 
              @PathVariable String mobileNumber,
              @RequestBody List<AddressDTO> addressDTOs) {

          // Call the service to update the address
          return serviceImpl.updateAddressByIndex( mobileNumber, index, addressDTOs);
      }

  //CUSTOMER DELETE MAPPINGS

  	@DeleteMapping("/deleteCustomerBasicProfile/{mobileNumber}")
  	public ResponseEntity<String> deleteBasicProfile(
  			@PathVariable @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") // Validate mobile
  																										// number
  			String mobileNumber) {

  		boolean success = serviceImpl.deleteBasicProfile(mobileNumber);
  		HttpStatus status = success ? HttpStatus.OK : HttpStatus.NOT_FOUND;
  		String message = success ? "Basic profile deleted successfully." : "Customer not found.";
  		return new ResponseEntity<>(message, status);
  	}

  	@DeleteMapping("/deleteAddresses/{mobileNumber}/{index}")
  	public ResponseEntity<ResponseDTO> deleteAddress(
  			@PathVariable @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") // Validate mobile
  																										// number
  			String mobileNumber, @PathVariable int index) {

  		ResponseDTO responseDTO = serviceImpl.deleteAddressByIndex(mobileNumber, index);
  		
  			return ResponseEntity.ok(responseDTO);
  		}

  	
  	//consultation
  	
  	 // POST: Create a new consultation
    @PostMapping("/createConsultation")
    public ResponseEntity<ConsultationDTO> createConsultation(@RequestBody ConsultationDTO entity) {
        ConsultationDTO saved = customerService.saveConsultation(entity);
        if(saved != null) {
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }else {
    	return new ResponseEntity<>(saved,HttpStatus.NOT_FOUND );
    }}
   

    // GET: Retrieve all consultations
    @GetMapping("/getAllConsultations")
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {
        List<ConsultationDTO> consultations = customerService.getAllConsultations();
        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }
    }