package com.sureCare.CustomerService.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sureCare.CustomerService.dto.AddressDTO;
import com.sureCare.CustomerService.dto.ConsultationDTO;
import com.sureCare.CustomerService.dto.CustomerDTO;
import com.sureCare.CustomerService.entity.Address;
import com.sureCare.CustomerService.entity.ConsultationEntity;
import com.sureCare.CustomerService.entity.Customer;
import com.sureCare.CustomerService.repository.ConsultationRep;
import com.sureCare.CustomerService.repository.CustomerRepository;
import com.sureCare.CustomerService.util.HelperForConversion;
import com.sureCare.CustomerService.util.OtpUtil;
import com.sureCare.CustomerService.util.ResponseDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Validator validator;
    
    @Autowired
    HelperForConversion conversion;
    
    
    @Autowired
	private ConsultationRep consultationRep;
    
    
//    @Value("${fast2sms.api.key}")
//    private String fast2smsApiKey;
//
//    private Map<String, Instant> otpLastSentTime = new ConcurrentHashMap<>();
//    private Map<String, String> generatedOtps = new HashMap<>();
//    private Map<String, CustomerDTO> temporaryCustomers = new HashMap<>();
//    private static final int OTP_COOLDOWN_SECONDS = 60;
//
//    @Override
//    public ResponseDTO signInOrSignUp(String fullName, String mobileNumber, HttpSession session) {
//        ResponseDTO response = new ResponseDTO();
//
//
//        // Capitalize the first letter of the full name and make the rest lowercase
//        fullName = capitalizeFullName(fullName);
//
//        // Create and trim the CustomerDTO
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setFullName(fullName);
//        customerDTO.setMobileNumber(mobileNumber);
//        customerDTO.trimFields();
//
//        // Validate input fields
//        if (!isValidFullName(customerDTO.getFullName())) {
//            return createErrorResponse(response, "Full name is required and must contain only letters and spaces.");
//        }
//        if (!isValidMobileNumber(customerDTO.getMobileNumber())) {
//            return createErrorResponse(response, "A valid mobile number is required (10 digits only).");
//        }
//
//        // Convert mobileNumber from String to long
//        long mobileNumberLong = Long.parseLong(customerDTO.getMobileNumber());
//
//        Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(mobileNumberLong);
//        if (existingCustomerOpt.isPresent()) {
//            return handleExistingCustomer(existingCustomerOpt.get(), mobileNumberLong, session, response);
//        } else {
//            response.setMessage(requestOtp(customerDTO.getMobileNumber(), customerDTO.getFullName(), session).getMessage() +
//                    " You need to complete your registration. Please verify your OTP.");
//            response.setRegistrationCompleted(false);
//            response.setStatus(HttpStatus.OK.value());
//            response.setSuccess(true);
//        }
//
//        return response;
//    }
//    
//
// // Helper method to capitalize the full name
//    private String capitalizeFullName(String fullName) {
//        if (fullName == null || fullName.isEmpty()) {
//            return fullName;
//        }
//
//        // Split the full name into words
//        String[] words = fullName.split(" ");
//        
//        // Capitalize the first letter of each word and make the rest lowercase
//        StringBuilder capitalizedFullName = new StringBuilder();
//        for (String word : words) {
//            if (!word.isEmpty()) {
//                capitalizedFullName.append(word.substring(0, 1).toUpperCase())
//                                   .append(word.substring(1).toLowerCase())
//                                   .append(" ");
//            }
//        }
//
//        // Trim the trailing space
//        return capitalizedFullName.toString().trim();
//    }
//
//
//    private ResponseDTO handleExistingCustomer(Customer existingCustomer, long mobileNumber, HttpSession session, ResponseDTO response) {
//        response.setMessage(requestOtp(String.valueOf(mobileNumber), existingCustomer.getFullName(), session).getMessage() +
//                (existingCustomer.isRegistrationCompleted() ?
//                        " You are already registered. Please verify your OTP to log in." :
//                        " You have an incomplete registration. Please verify your OTP to continue."));
//        response.setRegistrationCompleted(existingCustomer.isRegistrationCompleted());
//        response.setStatus(HttpStatus.OK.value());
//        response.setSuccess(existingCustomer.isRegistrationCompleted()); // Reflect registration status
//        
//        return response;
//    }
//    
//    @Override
//    public ResponseDTO requestOtp(String mobileNumber, String fullName, HttpSession session) {
//        ResponseDTO response = new ResponseDTO();
//
//        // Validate mobile number
//        if (!isValidMobileNumber(mobileNumber)) {
//            return createErrorResponse(response, "A valid mobile number is required.");
//        }
//
//        Instant now = Instant.now();
//        Instant lastSentTime = otpLastSentTime.get(mobileNumber);
//
//        // Check OTP cooldown
//        if (lastSentTime != null && now.isBefore(lastSentTime.plusSeconds(OTP_COOLDOWN_SECONDS))) {
//            long secondsLeft = OTP_COOLDOWN_SECONDS - (now.getEpochSecond() - lastSentTime.getEpochSecond());
//            return createErrorResponse(response, "Please wait " + secondsLeft + " seconds before requesting a new OTP.");
//        }
//
//        // Generate and send OTP
//        String otp = OtpUtil.generateOtp();
//        boolean sentSuccessfully = sendOtpToMobile(mobileNumber, otp);
//        if (!sentSuccessfully) {
//            return createErrorResponse(response, "Failed to send OTP due to provider restrictions. Please wait and try again.");
//        }
//
//        generatedOtps.put(mobileNumber, otp);
//        otpLastSentTime.put(mobileNumber, now);
//
//        // Store temporary customer data
//        CustomerDTO tempCustomer = new CustomerDTO();
//        tempCustomer.setFullName(fullName);
//        tempCustomer.setMobileNumber(mobileNumber);
//        temporaryCustomers.put(mobileNumber, tempCustomer);
//
//        session.setAttribute("mobileNumber", mobileNumber);
//        response.setMessage("OTP sent successfully. Please verify your OTP.");
//        response.setRegistrationCompleted(false);
//        response.setStatus(HttpStatus.OK.value());
//        response.setSuccess(true); // Registration not yet complete
//        
//        return response;
//    }
//
//    @Override
//    public ResponseDTO verifyOtp(String enteredOtp, String mobileNumber, HttpSession session) {
//        ResponseDTO response = new ResponseDTO();
//
//        // Validate mobile number
//        if (!isValidMobileNumber(mobileNumber)) {
//            return createErrorResponse(response, "A valid mobile number is required.");
//        }
//
//        String generatedOtp = generatedOtps.get(mobileNumber);
//        Instant otpSentTime = otpLastSentTime.get(mobileNumber);
//
//        if (generatedOtp == null || otpSentTime == null) {
//            return createErrorResponse(response, "No OTP found for this mobile number.");
//        }
//
//        Instant now = Instant.now();
//        if (now.isAfter(otpSentTime.plusSeconds(OTP_COOLDOWN_SECONDS))) {
//            generatedOtps.remove(mobileNumber);
//            otpLastSentTime.remove(mobileNumber);
//            return createErrorResponse(response, "OTP has expired. Please request a new OTP.");
//        }
//
//        if (enteredOtp.equals(generatedOtp)) {
//            generatedOtps.remove(mobileNumber);
//            otpLastSentTime.remove(mobileNumber);
//            handleOtpSuccess(mobileNumber, response);
//        } else {
//            return createErrorResponse(response, "Invalid OTP.");
//        }
//
//        return response;
//    }
//
//    private void handleOtpSuccess(String mobileNumber, ResponseDTO response) {
//        Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(Long.parseLong(mobileNumber));
//        if (existingCustomerOpt.isPresent()) {
//            Customer existingCustomer = existingCustomerOpt.get();
//            response.setMessage("OTP verified successfully. " +
//                    (existingCustomer.isRegistrationCompleted() ?
//                            "You are now logged in." :
//                            "You can now complete the remaining registration details."));
//            response.setRegistrationCompleted(existingCustomer.isRegistrationCompleted());
//            response.setStatus(HttpStatus.OK.value());
//        } else {
//            CustomerDTO tempCustomer = temporaryCustomers.get(mobileNumber);
//            if (tempCustomer != null) {
//                Customer customerEntity = new Customer();
//                customerEntity.setFullName(tempCustomer.getFullName());
//                customerEntity.setMobileNumber(Long.parseLong(tempCustomer.getMobileNumber()));
//                customerEntity.setRegistrationCompleted(false);
//                customerRepository.save(customerEntity);
//                temporaryCustomers.remove(mobileNumber);
//                response.setMessage("OTP verification successful. You can now complete the remaining registration details.");
//                response.setRegistrationCompleted(false);
//                response.setStatus(HttpStatus.OK.value());
//                response.setSuccess(true); 
//            } else {
//                response.setMessage("No registration in progress for this mobile number.");
//                response.setRegistrationCompleted(false);
//                response.setStatus(HttpStatus.BAD_REQUEST.value());
//            }
//        }
//    }
//
//    @Override
//    public ResponseDTO resendOtp(String mobileNumber, HttpSession session) {
//        ResponseDTO response = new ResponseDTO();
//
//        // Validate mobile number
//        if (!isValidMobileNumber(mobileNumber)) {
//            return createErrorResponse(response, "A valid mobile number is required.");
//        }
//
//        CustomerDTO tempCustomer = temporaryCustomers.get(mobileNumber);
//        if (tempCustomer == null) {
//            return createErrorResponse(response, "No registration in progress for this mobile number.");
//        }
//
//        String otpResponseMessage = requestOtp(mobileNumber, tempCustomer.getFullName(), session).getMessage();
//        response.setMessage(otpResponseMessage);
//        response.setRegistrationCompleted(false);
//        response.setStatus(HttpStatus.OK.value());
//        response.setSuccess(true); 
//        return response;
//    }
//
//    
//    private boolean sendOtpToMobile(String mobileNumber, String otp) {
//        String url = "https://www.fast2sms.com/dev/bulkV2";
//        String message = "Your OTP is: " + otp;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("authorization", fast2smsApiKey);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("route", "v3");
//        body.put("sender_id", "TXTIND");
//        body.put("message", message);
//        body.put("language", "english");
//        body.put("flash", 0);
//        body.put("numbers", String.valueOf(mobileNumber));
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//        RestTemplate restTemplate = new RestTemplate();
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                System.out.println("OTP sent successfully to mobile number: " + mobileNumber);
//                return true;
//            } else {
//                System.out.println("Failed to send OTP. Response: " + response.getBody());
//                return response.getBody().contains("Spamming detected");
//            }
//        } catch (Exception e) {
//            System.out.println("Error sending OTP: " + e.getMessage());
//            return false;
//        }
//    }
//

    @Override
    public ResponseDTO basicDetails(String fullName, String mobileNumber, String gender, String bloodGroup, byte age,
                                     String emailId, List<AddressDTO> addressDTOs, String status, String remark) {
    	
        ResponseDTO response = new ResponseDTO();

        // Validate mobile number
        if (!isValidMobileNumber(mobileNumber)) {
            return createErrorResponse(response, "A valid mobile number is required.");
        }

        // Create and trim CustomerDTO fields
        CustomerDTO customerDTO = new CustomerDTO();
        
        customerDTO.setFullName(fullName);
        customerDTO.setMobileNumber(mobileNumber);
        customerDTO.setGender(gender);
        customerDTO.setBloodGroup(bloodGroup);
        customerDTO.setAge(age);
        customerDTO.setEmailId(emailId);
        customerDTO.setStatus(status);
        customerDTO.setRemark(remark);
        customerDTO.setAddresses(addressDTOs);
        customerDTO.trimFields();

        // Validate other fields
        if (!isValidGender(customerDTO.getGender())) {
            return createErrorResponse(response, "Gender is required and must be either 'male' or 'female'.");
        }

        if (!isValidBloodGroup(customerDTO.getBloodGroup())) {
            return createErrorResponse(response, "Blood group is required.");
        }

        if (customerDTO.getAge() <= 0) {
            return createErrorResponse(response, "Valid age is required.");
        }

        if (!isValidEmail(customerDTO.getEmailId())) {
            return createErrorResponse(response, "A valid email ID is required.");
        }

        //Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(Long.parseLong(mobileNumber));
        //if (existingCustomerOpt.isEmpty()) {
           // return createErrorResponse(response, "No registration in progress for this mobile number.");
        //}

        Customer existingCustomer = new Customer();
        existingCustomer.setFullName(fullName);
        existingCustomer.setMobileNumber(Long.valueOf(mobileNumber));
        existingCustomer.setGender(customerDTO.getGender());
        existingCustomer.setBloodGroup(customerDTO.getBloodGroup());
        existingCustomer.setAge(customerDTO.getAge());
        existingCustomer.setEmailId(customerDTO.getEmailId());
        existingCustomer.setStatus(customerDTO.getStatus());
        existingCustomer.setRemark(customerDTO.getRemark());
        existingCustomer.setReferCode(generateAlphaNumericCode());
        // Handle null addressDTOs
        List<Address> addresses = (customerDTO.getAddresses() != null) ? convertToAddresses(customerDTO.getAddresses()) : new ArrayList<>();
        existingCustomer.setAddresses(addresses);

        // Check registration completion status
        boolean registrationCompleted = !addresses.isEmpty();
        existingCustomer.setRegistrationCompleted(registrationCompleted);
        customerRepository.save(existingCustomer);

        // Set appropriate response message based on registration status
        response.setMessage(registrationCompleted
                ? "Registration completed successfully for " + existingCustomer.getFullName() + "."
                : "Basic details completed successfully for " + existingCustomer.getFullName() + ".");

        response.setRegistrationCompleted(registrationCompleted);
        response.setStatus(HttpStatus.OK.value());
        response.setSuccess(true); 
        return response;
    }

    @Override
    public ResponseDTO saveAddress(String mobileNumber, @Valid AddressDTO addressDTO) {
        ResponseDTO response = new ResponseDTO();

        // Validate mobile number
        if (!isValidMobileNumber(mobileNumber)) {
            return createErrorResponse(response, "A valid mobile number is required.");
        }

        Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(Long.parseLong(mobileNumber));
        if (existingCustomerOpt.isEmpty()) {
            return createErrorResponse(response, "Customer not found.");
        }

        // Validate the addressDTO
        Set<ConstraintViolation<AddressDTO>> violations = validator.validate(addressDTO);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            return createErrorResponse(response, errorMessage);
        }

        // Trim the address fields
        addressDTO.trimFields();
        Customer existingCustomer = existingCustomerOpt.get();
       // addressDTO.setId(existingCustomer.getId());
       // AddressDTO addressDTO = new AddressDTO();
        Address address = mapToAddressEntity(addressDTO);

        if (existingCustomer.getAddresses() == null) {
            existingCustomer.setAddresses(new ArrayList<>());
        }

        existingCustomer.getAddresses().add(address);
        customerRepository.save(existingCustomer);

        if (!existingCustomer.isRegistrationCompleted()) {
            existingCustomer.setRegistrationCompleted(true);
            customerRepository.save(existingCustomer);
            response.setMessage("Address saved and registration completed successfully.");
            response.setRegistrationCompleted(true);
        } else {
            response.setMessage("Address saved successfully.");
        }

        response.setStatus(HttpStatus.OK.value());
        response.setSuccess(true); 
        return response;
    }

    private List<Address> convertToAddresses(List<AddressDTO> addressDTOs) {
        return addressDTOs.stream()
                .map(this::mapToAddressEntity)
                .collect(Collectors.toList());
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches("^\\d{10}$");
    }

    private boolean isValidFullName(String fullName) {
        return fullName != null && !fullName.trim().isEmpty() && fullName.matches("^[A-Za-z\\s]+$");
    }

    private boolean isValidEmail(String emailId) {
        if (emailId == null) return false;
        return emailId.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidGender(String gender) {
        return gender != null && !gender.isEmpty() && (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"));
    }

    private boolean isValidBloodGroup(String bloodGroup) {
        return bloodGroup != null && !bloodGroup.isEmpty();
    }

    private ResponseDTO createErrorResponse(ResponseDTO response, String message) {
        response.setMessage(message);
        response.setRegistrationCompleted(false);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return response;
    }

    private Address mapToAddressEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setHouseNo(addressDTO.getHouseNo());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCountry(addressDTO.getCountry());
        address.setApartment(addressDTO.getApartment());
        address.setDirection(addressDTO.getDirection());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());
        

        return address;
    }
    

    
    @Override
	public List<CustomerDTO> getAllCustomerDetails() {
		List<Customer> customers = customerRepository.findAll();
		List<AddressDTO> addressDto = new ArrayList<>();
		List<CustomerDTO> customerDTOsList = new ArrayList<>();
		if( customers != null) {
		for(Customer customer : customers ) {
			if(customer.getAddresses() != null) {
			for(Address address :customer.getAddresses()) {
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setApartment( address.getApartment());
			addressDTO.setCity( address.getCity());
			addressDTO.setCountry( address.getCountry());
			addressDTO.setDirection( address.getDirection());
			addressDTO.setHouseNo( address.getHouseNo());
			addressDTO.setLatitude( address.getLatitude());
			addressDTO.setLongitude( address.getLongitude());
			addressDTO.setPostalCode( address.getPostalCode());
			addressDTO.setState( address.getState());
			addressDTO.setStreet( address.getStreet());
			addressDto.add(addressDTO);
		}
			}
			
		}
		for(Customer customer :customers ) {
			    CustomerDTO customerDTO = new CustomerDTO(
			        customer.getFullName(),                           // Full name
			        String.valueOf(customer.getMobileNumber()),
			        customer.getGender(),                             // Gender
			        customer.getBloodGroup(),                         // Blood group
			        (byte)customer.getAge(),                                // Age
			        customer.getEmailId(),                            // Email ID
			        customer.getStatus(),                             // Status
			        customer.getRemark(),
			        null,
			        addressDto,
			        false
			    );

			    customerDTOsList.add(customerDTO);
			}
		}else {
			return null;
		}
		return customerDTOsList;
		}
	
	@Override
	public CustomerDTO getCustomerDetailsByMobileNumber(long mobileNumber) {

	    // Retrieve customer from repository
	    Optional<Customer> customerOptional = customerRepository.findByMobileNumber(mobileNumber);

	    // Check if customer is present, return null or handle accordingly if not found
	    if (customerOptional.isEmpty()) {
	        System.out.println("Customer not found for mobile number: " + mobileNumber);
	        return null; // Or throw a custom exception based on your requirement
	    }

	    Customer customerObject = customerOptional.get();

	    // Process addresses into AddressDTO list with null-safe handling
	    List<AddressDTO> addressDTOList = Optional.ofNullable(customerObject.getAddresses())
	            .orElse(Collections.emptyList()) // Use empty list if addresses are null
	            .stream()
	            .map(address -> {
	                if (address == null) {
	                    System.out.println("Address is null.");
	                    return null; // Handle null address objects
	                }
	                // Create a new AddressDTO object
	                AddressDTO addressDTO = new AddressDTO();
	                addressDTO.setApartment(Optional.ofNullable(address.getApartment()).orElse(null));
	                addressDTO.setCity(Optional.ofNullable(address.getCity()).orElse(null));
	                addressDTO.setCountry(Optional.ofNullable(address.getCountry()).orElse(null));
	                addressDTO.setDirection(Optional.ofNullable(address.getDirection()).orElse(null));
	                addressDTO.setHouseNo(Optional.ofNullable(address.getHouseNo()).orElse(null));
	                addressDTO.setLatitude(address.getLatitude()); // Assuming latitude is a primitive; it won't be null
	                addressDTO.setLongitude(address.getLongitude()); // Assuming longitude is a primitive; it won't be null
	                addressDTO.setPostalCode(Optional.ofNullable(address.getPostalCode()).orElse(null));
	                addressDTO.setState(Optional.ofNullable(address.getState()).orElse(null));
	                addressDTO.setStreet(Optional.ofNullable(address.getStreet()).orElse(null));
	                return addressDTO;
	            })
	            .collect(Collectors.toList());

	    // Create and return CustomerDTO with null-safe handling
	    CustomerDTO customerDTO = new CustomerDTO(
	            Optional.ofNullable(customerObject.getFullName()).orElse(null),           // Full name
	            String.valueOf(customerObject.getMobileNumber()),                       // Mobile number
	            Optional.ofNullable(customerObject.getGender()).orElse(null),            // Gender
	            Optional.ofNullable(customerObject.getBloodGroup()).orElse(null),        // Blood group
	            (byte) Optional.ofNullable(customerObject.getAge()).orElse(null),           // Age
	            Optional.ofNullable(customerObject.getEmailId()).orElse(null),           // Email ID
	            Optional.ofNullable(customerObject.getStatus()).orElse(null),            // Status
	            Optional.ofNullable(customerObject.getRemark()).orElse(null),
	            null,
	           addressDTOList, false
	    );

	    return customerDTO;
	}


	@Override
	public CustomerDTO getCustomerDetailsByEmail(String email) {

	    // Retrieve customer from repository
	    Optional<Customer> customerOptional = customerRepository.findByEmailId(email);

	    // Check if customer is present, return null or handle accordingly if not found
	    if (customerOptional.isEmpty()) {
	        System.out.println("Customer not found for email: " + email);
	        return null; // Or throw a custom exception if preferred
	    }

	    Customer customerObject = customerOptional.get();

	    // Process addresses into AddressDTO list with null-safe handling
	    List<AddressDTO> addressDTOList = Optional.ofNullable(customerObject.getAddresses())
	            .orElse(Collections.emptyList()) // Use empty list if addresses are null
	            .stream()
	            .map(address -> {
	                if (address == null) {
	                    System.out.println("Address is null.");
	                    return null; // Handle null address objects
	                }
	                // Create a new AddressDTO object
	                AddressDTO addressDTO = new AddressDTO();
	                addressDTO.setApartment(Optional.ofNullable(address.getApartment()).orElse(null));
	                addressDTO.setCity(Optional.ofNullable(address.getCity()).orElse(null));
	                addressDTO.setCountry(Optional.ofNullable(address.getCountry()).orElse(null));
	                addressDTO.setDirection(Optional.ofNullable(address.getDirection()).orElse(null));
	                addressDTO.setHouseNo(Optional.ofNullable(address.getHouseNo()).orElse(null));
	                addressDTO.setLatitude(address.getLatitude()); // Assuming latitude is a primitive; it won't be null
	                addressDTO.setLongitude(address.getLongitude()); // Assuming longitude is a primitive; it won't be null
	                addressDTO.setPostalCode(Optional.ofNullable(address.getPostalCode()).orElse(null));
	                addressDTO.setState(Optional.ofNullable(address.getState()).orElse(null));
	                addressDTO.setStreet(Optional.ofNullable(address.getStreet()).orElse(null));
	                return addressDTO;
	            })
	            .collect(Collectors.toList());

	    // Create and return CustomerDTO with null-safe handling
	    CustomerDTO customerDTO = new CustomerDTO(
	            Optional.ofNullable(customerObject.getFullName()).orElse(null),           // Full name
	            String.valueOf(customerObject.getMobileNumber()),                      // Mobile number
	            Optional.ofNullable(customerObject.getGender()).orElse(null),            // Gender
	            Optional.ofNullable(customerObject.getBloodGroup()).orElse(null),        // Blood group
	            (byte) Optional.ofNullable(customerObject.getAge()).orElse(null),           // Age
	            Optional.ofNullable(customerObject.getEmailId()).orElse(null),           // Email ID
	            Optional.ofNullable(customerObject.getStatus()).orElse(null),            // Status
	            Optional.ofNullable(customerObject.getRemark()).orElse(null),
	            null,
	            addressDTOList, false
	    );

	    return customerDTO;
	}

	@Override
	public List<CustomerDTO> getCustomerByfullName(String fullName) {
	    // Retrieve customers by full name
	    List<Customer> customers = customerRepository.findByfullName(fullName);
	    List<CustomerDTO> customerDTOs = new ArrayList<>();

	    // Process each customer
	    for (Customer customerObject : customers) {
	        // Process addresses into AddressDTO list with null-safe handling
	        List<AddressDTO> addressDTOList = Optional.ofNullable(customerObject.getAddresses())
	                .orElse(Collections.emptyList()) // Use empty list if addresses are null
	                .stream()
	                .map(address -> {
	                    if (address == null) {
	                        System.out.println("Address is null.");
	                        return null; // Handle null address objects
	                    }
	                    // Create a new AddressDTO object
	                    AddressDTO addressDTO = new AddressDTO();
	                    addressDTO.setApartment(Optional.ofNullable(address.getApartment()).orElse(null));
	                    addressDTO.setCity(Optional.ofNullable(address.getCity()).orElse(null));
	                    addressDTO.setCountry(Optional.ofNullable(address.getCountry()).orElse(null));
	                    addressDTO.setDirection(Optional.ofNullable(address.getDirection()).orElse(null));
	                    addressDTO.setHouseNo(Optional.ofNullable(address.getHouseNo()).orElse(null));
	                    addressDTO.setLatitude(address.getLatitude()); // Assuming latitude is a primitive
	                    addressDTO.setLongitude(address.getLongitude()); // Assuming longitude is a primitive
	                    addressDTO.setPostalCode(Optional.ofNullable(address.getPostalCode()).orElse(null));
	                    addressDTO.setState(Optional.ofNullable(address.getState()).orElse(null));
	                    addressDTO.setStreet(Optional.ofNullable(address.getStreet()).orElse(null));
	                    return addressDTO;
	                })
	                .collect(Collectors.toList());

	        // Create CustomerDTO with null-safe handling
	        CustomerDTO customerDTO = new CustomerDTO(
	                Optional.ofNullable(customerObject.getFullName()).orElse(null),           // Full name
	                String.valueOf(customerObject.getMobileNumber()),                        // Mobile number
	                Optional.ofNullable(customerObject.getGender()).orElse(null),            // Gender
	                Optional.ofNullable(customerObject.getBloodGroup()).orElse(null),        // Blood group
	                (byte) Optional.ofNullable(customerObject.getAge()).orElse(null),           // Age
	                Optional.ofNullable(customerObject.getEmailId()).orElse(null),           // Email ID
	                Optional.ofNullable(customerObject.getStatus()).orElse(null),            // Status
	                Optional.ofNullable(customerObject.getRemark()).orElse(null),null,  
	                addressDTOList, false
	        );

	        customerDTOs.add(customerDTO);
	    }

	    return customerDTOs;
	}

	

@Override
public CustomerDTO getBasicCustomerDetails(String mobileNumber) {
        // Convert mobileNumber from String to long if needed
        long mobileNumberLong;
        try {
            mobileNumberLong = Long.parseLong(mobileNumber);
        } catch (NumberFormatException e) {
            System.err.println("Invalid mobile number format: " + mobileNumber);
            return null; // or handle the error as needed
        }

        Optional<Customer> customerOpt = customerRepository.findByMobileNumber(mobileNumberLong);
        Customer customerObject = customerOpt.get();
        List<AddressDTO> addressDTOList = customerObject.getAddresses().stream()
        	    .map(address -> {
        	        // Create a new AddressDTO object
        	        AddressDTO addressDTO = new AddressDTO();
        	        addressDTO.setApartment(address.getApartment());
        	        addressDTO.setCity(address.getCity());
        	        addressDTO.setCountry(address.getCountry());
        	        addressDTO.setDirection(address.getDirection());
        	        addressDTO.setHouseNo(address.getHouseNo());
        	        addressDTO.setLatitude(address.getLatitude());
        	        addressDTO.setLongitude(address.getLongitude());
        	        addressDTO.setPostalCode(address.getPostalCode());
        	        addressDTO.setState(address.getState());
        	        addressDTO.setStreet(address.getStreet());
        	        return addressDTO;
        	    })
        	    .collect(Collectors.toList());
        		
        		       CustomerDTO  customerDTO = new CustomerDTO(
        		        customerObject.getFullName(),           // Full name
        		        String.valueOf(customerObject.getMobileNumber()), // Mobile number (converted to String)
        		        customerObject.getGender(),             // Gender
        		        customerObject.getBloodGroup(),         // Blood group
        		        (byte) customerObject.getAge(),         // Age
        		        customerObject.getEmailId(),            // Email ID
        		        customerObject.getStatus(),             // Status
        		        customerObject.getRemark(), null,
        		        addressDTOList,false
        		    );

        		  return  customerDTO;
        		}


@Override
public List<AddressDTO> getCustomerAddresses(String mobileNumber) {
    // Validate and parse the mobile number
    long mobileNumberLong;
    try {
        mobileNumberLong = Long.parseLong(mobileNumber);
    } catch (NumberFormatException e) {
        System.err.println("Invalid mobile number format: " + mobileNumber);
        throw new IllegalArgumentException("Invalid mobile number format: " + mobileNumber); // Throw an exception with a clear message
    }

    Optional<Customer> customerOpt = customerRepository.findByMobileNumber(mobileNumberLong);

    if (!customerOpt.isPresent()) {
        return null;
    }
    Customer customerObject = customerOpt.get();
    List<AddressDTO> addressDTOList = customerObject.getAddresses().stream()
    	    .map(address -> {
    	        // Create a new AddressDTO object
    	        AddressDTO addressDTO = new AddressDTO();
    	        addressDTO.setApartment(address.getApartment());
    	        addressDTO.setCity(address.getCity());
    	        addressDTO.setCountry(address.getCountry());
    	        addressDTO.setDirection(address.getDirection());
    	        addressDTO.setHouseNo(address.getHouseNo());
    	        addressDTO.setLatitude(address.getLatitude());
    	        addressDTO.setLongitude(address.getLongitude());
    	        addressDTO.setPostalCode(address.getPostalCode());
    	        addressDTO.setState(address.getState());
    	        addressDTO.setStreet(address.getStreet());
    	        return addressDTO;
    	    })
    	    .collect(Collectors.toList());
    return addressDTOList;
}

	
@Override
public ResponseDTO updateAddressByIndex(String mobileNumber, int index, List<AddressDTO> addressDTOs) {
    ResponseDTO response = new ResponseDTO();

    // Validate mobile number
    if (!isValidMobileNumber(mobileNumber)) {
        return createErrorResponse(response, "A valid mobile number is required.");
    }

    // Find the customer by mobile number
    Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(Long.parseLong(mobileNumber));
    if (existingCustomerOpt.isEmpty()) {
        return createErrorResponse(response, "Customer not found.");
    }

    Customer existingCustomer = existingCustomerOpt.get();

    // Find the address by index in the addresses list
    List<Address> addressList = existingCustomer.getAddresses();
    if (index < 0 || index >= addressList.size()) {
        // If the index is out of bounds, return an error
        return createErrorResponse(response, "Invalid address index.");
    }

    // Get the address at the specified index
    Address existingAddress = addressList.get(index);

    // Validate and update each address in the list (assuming you want to update multiple addresses)
    for (AddressDTO addressDTO : addressDTOs) {
        // Validate the addressDTO fields
        String validationErrorMessage = validateAddressDTO(addressDTO);
        if (validationErrorMessage != null) {
            return createErrorResponse(response, validationErrorMessage);
        }

        // Trim all fields in the DTO to avoid leading/trailing spaces
        addressDTO.trimFields();

        // Map the DTO to the existing address entity
        mapToExistingAddressEntity(existingAddress, addressDTO);
    }

    // Save the updated customer entity with modified addresses
    customerRepository.save(existingCustomer);

    // Prepare the success response
    response.setMessage("Address updated successfully.");
    response.setStatus(200);
    response.setSuccess(true);
    return response;
}

// Helper method to validate the fields in the AddressDTO
private String validateAddressDTO(AddressDTO addressDTO) {
    if (addressDTO.getHouseNo() == null || addressDTO.getHouseNo().trim().isEmpty()) {
        return "House number is required.";
    }
    if (addressDTO.getStreet() == null || addressDTO.getStreet().trim().isEmpty()) {
        return "Street is required.";
    }
    if (addressDTO.getCity() == null || addressDTO.getCity().trim().isEmpty()) {
        return "City is required.";
    }
    if (addressDTO.getState() == null || addressDTO.getState().trim().isEmpty()) {
        return "State is required.";
    }
    if (addressDTO.getPostalCode() == null || addressDTO.getPostalCode().trim().isEmpty()) {
        return "Postal code is required.";
    }
    if (addressDTO.getCountry() == null || addressDTO.getCountry().trim().isEmpty()) {
        return "Country is required.";
    }
    if (addressDTO.getPostalCode() != null && !addressDTO.getPostalCode().matches("\\d+")) {
        return "Postal code must be numeric.";
    }
    return null;
}

// Helper method to map AddressDTO fields to an existing Address entity
private void mapToExistingAddressEntity(Address existingAddress, AddressDTO addressDTO) {
    existingAddress.setStreet(addressDTO.getStreet());
    existingAddress.setHouseNo(addressDTO.getHouseNo());
    existingAddress.setCity(addressDTO.getCity());
    existingAddress.setState(addressDTO.getState());
    existingAddress.setPostalCode(addressDTO.getPostalCode());
    existingAddress.setCountry(addressDTO.getCountry());
    existingAddress.setApartment(addressDTO.getApartment());
    existingAddress.setDirection(addressDTO.getDirection());
    existingAddress.setLatitude(addressDTO.getLatitude());
    existingAddress.setLongitude(addressDTO.getLongitude());
}
	
@Override	
public ResponseDTO updateCustomerDetails(String mobileNumber, CustomerDTO customerDTO) {
	    ResponseDTO response = new ResponseDTO();

	    // Validate mobile number
	    if (!isValidMobileNumber(mobileNumber)) {
	        return createErrorResponse(response, "A valid mobile number is required.");
	    }

	    // Trim the customerDTO fields before processing
	    customerDTO.trimFields();

	    // Validate gender
	    if (!isValidGender(customerDTO.getGender())) {
	        return createErrorResponse(response, "Gender is required and must be either 'male' or 'female'.");
	    }

	    // Validate blood group
	    if (!isValidBloodGroup(customerDTO.getBloodGroup())) {
	        return createErrorResponse(response, "Blood group is required.");
	    }

	    // Validate age
	    if (customerDTO.getAge() <= 0) {
	        return createErrorResponse(response, "Valid age is required.");
	    }

	    // Validate email ID
	    if (!isValidEmail(customerDTO.getEmailId())) {
	        return createErrorResponse(response, "A valid email ID is required.");
	    }

	    // Check if the customer exists based on mobile number
	    Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(Long.parseLong(mobileNumber));
	    if (existingCustomerOpt.isEmpty()) {
	        return createErrorResponse(response, "No registration in progress for this mobile number.");
	    }

	    Customer existingCustomer = existingCustomerOpt.get();

	    // Update basic customer details
	    existingCustomer.setFullName(customerDTO.getFullName());
	    existingCustomer.setGender(customerDTO.getGender());
	    existingCustomer.setBloodGroup(customerDTO.getBloodGroup());
	    existingCustomer.setAge(customerDTO.getAge());
	    existingCustomer.setEmailId(customerDTO.getEmailId());
	    existingCustomer.setStatus(customerDTO.getStatus());
        existingCustomer.setRemark(customerDTO.getRemark());
	    // Check registration completion based on the presence of addresses (optional)
	    boolean registrationCompleted = !existingCustomer.getAddresses().isEmpty();
	    existingCustomer.setRegistrationCompleted(registrationCompleted);

	    // Save the updated customer details
	    customerRepository.save(existingCustomer);

	    // Set appropriate response message
	    response.setMessage("Basic details updated successfully");

	    response.setRegistrationCompleted(registrationCompleted);
	    response.setStatus(200);
	    response.setSuccess(true);

	    return response;
	}


@Override
public boolean deleteBasicProfile(String mobileNumber) {
				Long mobileNumberLong = Long.parseLong(mobileNumber);
			    Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(mobileNumberLong);

			    if (!existingCustomerOpt.isPresent()) {
			        return false;
			    }

			    Customer existingCustomer = existingCustomerOpt.get();
			    customerRepository.delete(existingCustomer);
			    return true;
			}
			
	@Override			
	public ResponseDTO deleteAddressByIndex(String mobileNumber, int index) {
	    ResponseDTO response = new ResponseDTO();

	    // Validate mobile number
	    if (!isValidMobileNumber(mobileNumber)) {
	        return createErrorResponse(response, "A valid mobile number is required.");
	    }

	    // Find the customer by mobile number
	    Optional<Customer> existingCustomerOpt = customerRepository.findByMobileNumber(Long.parseLong(mobileNumber));
	    if (existingCustomerOpt.isEmpty()) {
	        return createErrorResponse(response, "Customer not found.");
	    }

	    Customer existingCustomer = existingCustomerOpt.get();

	    // Get the list of addresses
	    List<Address> addresses = existingCustomer.getAddresses();
	    if (addresses == null || addresses.isEmpty()) {
	        return createErrorResponse(response, "No addresses available to delete.");
	    }

	    // Validate the index
	    if (index < 0 || index >= addresses.size()) {
	        return createErrorResponse(response, "Invalid address index.");
	    }

	    // Remove the address at the specified index
	    addresses.remove(index);
	    existingCustomer.setAddresses(addresses);  // Update the customer with the modified address list
	    customerRepository.save(existingCustomer);  // Save the customer to the repository

	    // Return success response
	    response.setMessage("Address deleted successfully.");
	    response.setStatus(200);
	    response.setSuccess(true);

	    return response;
	}

	
	//method for generate refer code.
	
	public static String generateAlphaNumericCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

	
	//consultation
	
	 public ConsultationDTO saveConsultation(ConsultationDTO dto) {
		 ConsultationEntity consultation = new  ConsultationEntity();
		 try{
	     consultation.setConsultationType(dto.getConsultationType());
		 consultation.setConsultationId(dto.getConsultationId());
		 ConsultationEntity consultationEntity = consultationRep.save( consultation);
		 if(consultationEntity != null) {
	     ConsultationDTO dtoObj = new  ConsultationDTO();
		 dtoObj.setConsultationType(consultationEntity.getConsultationType());
		 dtoObj.setConsultationId(consultationEntity.getConsultationId());
		 return dtoObj;
	    }else {
	    	return null;
	    }}catch(Exception e) {
	    	return null;
	    }
	 }
	 
	    public List<ConsultationDTO> getAllConsultations() {
	    	
	    	List<ConsultationDTO> dto = new ArrayList<>();
	    	try {
	        List<ConsultationEntity> list = consultationRep.findAll();
	        if(list != null) {
	        	dto = list.stream().map(n->{ConsultationDTO consultationDTO = new ConsultationDTO();
	        			consultationDTO.setConsultationType(n.getConsultationType());
	        			consultationDTO.setConsultationId(n.getConsultationId());
	        			return consultationDTO;
	        	}).collect(Collectors.toList());
	        
	        return dto;
	        }else {
	        	return null;
	        }}catch(Exception e) {
	        	return null;
	        }
	    }
	
}