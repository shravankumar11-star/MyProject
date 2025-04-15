package com.AdminService.dto;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClinicCredentials {

	private String password;
	private String newPassword;
	private String confirmPassword;
	
}
