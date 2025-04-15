package com.sureCare.CustomerService.util;

import org.springframework.http.MediaType;

public class DharmaCareUtils {
	
	   public final static String SURE_CARE_CONSUMES=MediaType.APPLICATION_JSON_VALUE;
	   public final static String SURE_CARE_PRODUCES=MediaType.APPLICATION_JSON_VALUE;
	   
	   public final static String CUSTOMER="/customers";
	   public final static String CUSTOMER_SIGN_UP_OR_SIGN_IN="/sign-in-or-sign-up";
	   public final static String CUSTOMER_VERIFY_OTP="/verify-otp";
	   public final static String CUSTOMER_RESEND_OTP="/resend-otp";
	   public final static String CUSTOMER_BASIC_DETAILS="/basic-details";
	   public final static String CUSTOMER_SAVE_ADDRESS="/save-address";

}
