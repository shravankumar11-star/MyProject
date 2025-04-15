package com.sureCare.CustomerService.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private ObjectId id; // MongoDB ObjectId 
    private long  mobileNumber; // 10 digits
    private String fullName; // required 
    private String gender; // required
    private String bloodGroup; 
    private byte age; // required 
    @Indexed(unique = true)
    private String emailId;
    private String status;
    private String remark;
    private String referCode;
    private List<Address> addresses; // Use List<AddressDTO> if DTOs are preferred
    private boolean registrationCompleted;
}