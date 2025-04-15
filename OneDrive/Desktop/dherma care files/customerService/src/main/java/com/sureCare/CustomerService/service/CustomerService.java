package com.sureCare.CustomerService.service;

import java.util.List;

import com.sureCare.CustomerService.dto.AddressDTO;
import com.sureCare.CustomerService.dto.ConsultationDTO;
import com.sureCare.CustomerService.dto.CustomerDTO;
import com.sureCare.CustomerService.util.ResponseDTO;

import jakarta.servlet.http.HttpSession;


public interface CustomerService {

//	public ResponseDTO signInOrSignUp(String fullName, String mobileNumber, HttpSession session);
//	
//	public ResponseDTO requestOtp(String mobileNumber, String fullName, HttpSession session);
//	
//	 public ResponseDTO verifyOtp(String enteredOtp, String mobileNumber, HttpSession session);
//	 
//	 public ResponseDTO resendOtp(String mobileNumber, HttpSession session);

   ResponseDTO saveAddress(String mobileNumber, AddressDTO addressDTO);

    public ResponseDTO basicDetails(String fullName, String mobileNumber, String gender, String bloodGroup, byte age,
            String emailId, List<AddressDTO> addressDTOs, String status, String remark);
    
    public List<CustomerDTO> getAllCustomerDetails();

    public CustomerDTO getCustomerDetailsByMobileNumber(long mobileNumber);

    public CustomerDTO getCustomerDetailsByEmail(String email);

    public List<CustomerDTO> getCustomerByfullName(String fullName);
    	
    public CustomerDTO getBasicCustomerDetails(String mobileNumber);

    public List<AddressDTO> getCustomerAddresses(String mobileNumber);

    	
    public ResponseDTO updateAddressByIndex(String mobileNumber, int index, List<AddressDTO> addressDTOs);

    public ResponseDTO updateCustomerDetails(String mobileNumber, CustomerDTO customerDTO);

       
    public boolean deleteBasicProfile(String mobileNumber);
    	
    public ResponseDTO deleteAddressByIndex(String mobileNumber, int index);
    
    public ConsultationDTO saveConsultation(ConsultationDTO dto) ;
    
    public List<ConsultationDTO> getAllConsultations();
	

}
