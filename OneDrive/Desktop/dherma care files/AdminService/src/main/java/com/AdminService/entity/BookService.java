package com.AdminService.entity;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.AdminService.dto.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "BookService")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookService {
	
	@Id
	@Field("_id")
	private ObjectId appointmentId;
   
	private String patientName;
	private String relationShip;
	private long patientNumber;
	private String gender;
	private String emailId;
	private String age;
	private long customerNumber;
	private Address address;

    private String categoryName;
	private List<ServicesAdded> servicesAdded;
	private double totalPrice; 
	private double totalDiscountAmount;
	private double totalDiscountedAmount;
	private double totalTax;
	private double payAmount;
    private String bookedAt;

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
 
    public AddressDTO getAddressDto() { return new AddressDTO( address.getHouseNo(), address.getStreet(), address.getCity(), address.getState(), address.getPostalCode(), address.getCountry(), address.getApartment(), address.getDirection(), address.getLatitude(), address.getLongitude() ); }
}
