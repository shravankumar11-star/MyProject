package com.AdminService.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	private String houseNo;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String apartment; // Optional field
    private String direction; // Optional field
    private Double latitude; // Optional field
    private Double longitude; // Optional field

}