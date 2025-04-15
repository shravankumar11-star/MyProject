package com.AdminService.entity;

import java.io.Serializable;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Document(collection = "services")
@NoArgsConstructor
@AllArgsConstructor
public class Services implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId serviceId;

	private String serviceName;
	
	private  String categoryName;
	
	private ObjectId categoryId;

	private String description;
    
	private double pricing;
	
	private String includes;
	
	private String readyPeriod;
	
	private String viewDescription;
	
	private  String preparation;
	
	private String status;
	
  
    
    private byte[]  serviceImage;
    
    private byte[]  viewImage;
    
    private String minTime;

	private byte discount;  
	
	private byte tax;

	private double discountCost; 

	private double  discountedCost;
	
	private  double taxAmount;
	
	private double finalCost;

}
