package com.AdminService.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDTO {

	 private String hospitalId; // MongoDB uses String as the default ID type
        private String name;
        private String address;
        private String city;
        private String contactNumber;
        private int hospitalOverallRating;
        private String hospitalRegistrations;
        private String openingTime;
	    private String closingTime;
        private String hospitalLogo;
        private String emailAddress;
        private String website;
        private String licenseNumber;
        private String issuingAuthority;
        private List<String> hospitalCategory;
        private List<String> hospitalServices;
        private List<String> hospitalDocuments;
 
	   
	    
}