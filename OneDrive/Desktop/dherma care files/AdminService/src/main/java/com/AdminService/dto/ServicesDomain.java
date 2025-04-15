package com.AdminService.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServicesDomain {

	private String serviceId;

	private String serviceName;
	
	private  String categoryName;
	
	private String categoryId;

	private String description;
    
	private double pricing;
	
	private String includes;
	
	private String readyPeriod;
	
	private String viewDescription;
	
	private  String preparation;
	
	private String status;
    
    private String  serviceImage;
    
    private String  viewImage;
    
	private byte discount;

	private byte tax;

	private String minTime;

	private double discountCost;

	private double discountedCost;

	private double taxAmount;

	private double finalCost;
}
