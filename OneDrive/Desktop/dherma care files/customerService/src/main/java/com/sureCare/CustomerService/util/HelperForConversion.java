package com.sureCare.CustomerService.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.sureCare.CustomerService.dto.AddressDTO;
import com.sureCare.CustomerService.dto.CustomerDTO;
import com.sureCare.CustomerService.entity.Address;
import com.sureCare.CustomerService.entity.Customer;

@Component
public class HelperForConversion {

	public static CustomerDTO convertToDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDto = new CustomerDTO();

        // Map basic fields
        customerDto.setFullName(customer.getFullName());
        customerDto.setMobileNumber(String.valueOf(customer.getMobileNumber()));  // Assuming mobileNumber is long, converting to String
        customerDto.setGender(customer.getGender());
        customerDto.setBloodGroup(customer.getBloodGroup());
        customerDto.setAge(customer.getAge());
        customerDto.setEmailId(customer.getEmailId());
        customerDto.setStatus(customer.getStatus());
        customerDto.setRemark(customer.getRemark());
        customerDto.setRegistrationCompleted(customer.isRegistrationCompleted());

        // Map address list
        if (customer.getAddresses() != null) {
            List<AddressDTO> addressDTOList = new ArrayList<>();
            for (Address address : customer.getAddresses()) {
                AddressDTO addressDTO = convertToAddressDTO(address);
                addressDTOList.add(addressDTO);
            }
            customerDto.setAddresses(addressDTOList);
        }

        return customerDto;
    }
	
	
	private static AddressDTO convertToAddressDTO(Address address) {
	    AddressDTO addressDTO = new AddressDTO();
	    // Map all fields from Address to AddressDTO
	    addressDTO.setHouseNo(address.getHouseNo());
	    addressDTO.setStreet(address.getStreet());
	    addressDTO.setCity(address.getCity());
	    addressDTO.setState(address.getState());
	    addressDTO.setPostalCode(address.getPostalCode());
	    addressDTO.setCountry(address.getCountry());
	    addressDTO.setApartment(address.getApartment());
	    addressDTO.setDirection(address.getDirection());
	    addressDTO.setLatitude(address.getLatitude());
	    addressDTO.setLongitude(address.getLongitude());
	    return addressDTO;
	}
	
	
}


