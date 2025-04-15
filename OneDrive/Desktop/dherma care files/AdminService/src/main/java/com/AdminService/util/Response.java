package com.AdminService.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	
		private boolean sucess;
		private Object data;
		private String message;
		private int status;
	}

