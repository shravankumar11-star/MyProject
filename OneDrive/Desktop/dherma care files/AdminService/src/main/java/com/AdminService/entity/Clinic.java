package com.AdminService.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clinics") // MongoDB collection

public class Clinic {
@Id
	private String id;
	private String hospitalId;
	private String name;
	private String address;
	private String city;
	private String contactNumber;
	private int hospitalOverallRating;
	private String hospitalRegistrations;
	private String openingTime;
	private String closingTime;
	private byte[] hospitalLogo;
	 private String emailAddress;
     private String website;
     private String licenseNumber;
     private String issuingAuthority;
     private List<String> hospitalCategory;
     private List<String> hospitalServices;
	private List<byte[]>  hospitalDocuments;
	
	}

