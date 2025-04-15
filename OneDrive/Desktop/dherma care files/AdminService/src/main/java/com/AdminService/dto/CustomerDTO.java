package com.AdminService.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
 
    
    @NotBlank(message = "Full name is required and cannot start or end with spaces.")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Full name must contain only letters and spaces.")
    private String fullName;

    @NotBlank(message = "Mobile number is required and cannot start or end with spaces.")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be a 10-digit number.")
    private String mobileNumber; // Changed to String to preserve leading zeros

    @NotBlank(message = "Gender is required and cannot start or end with spaces.")
    @Pattern(regexp = "^(male|female|other)$", message = "Gender must be either 'male', 'female', or 'other'.")
    private String gender;

//    @NotBlank(message = "Blood group is required and cannot start or end with spaces.")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood group must be in the format A+, A-, B+, B-, AB+, AB-, O+, or O-.")
    private String bloodGroup;

    @Min(value = 1, message = "Age must be a positive number.")
    @Max(value = 120, message = "Age must be less than or equal to 120.")
    private byte age;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email is required and cannot start or end with spaces.")
    private String emailId;

    private String status;
    private String remark;
    
    @NotEmpty(message = "At least one address is required.")
    @Valid
    private List<AddressDTO> addresses;

    

    private boolean registrationCompleted;
    
    public void trimFields() {
        this.fullName = this.fullName != null ? this.fullName.trim() : null;
        this.mobileNumber = this.mobileNumber != null ? this.mobileNumber.trim() : null;
        this.gender = this.gender != null ? this.gender.trim() : null;
        this.bloodGroup = this.bloodGroup != null ? this.bloodGroup.trim() : null;
        this.emailId = this.emailId != null ? this.emailId.trim() : null;

        if (addresses != null) {
            for (AddressDTO address : addresses) {
                address.trimFields();  // Assuming AddressDTO also has a similar method
            }
        }
    }
}