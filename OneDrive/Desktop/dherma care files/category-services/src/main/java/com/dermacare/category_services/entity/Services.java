package com.dermacare.category_services.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

	private String categoryName;

	private ObjectId categoryId;

	private String description;

	private String viewDescription;

	private String status;

	private byte[] serviceImage;

	private byte[] viewImage;

	private String minTime;

	private List<Map<String, List<String>>> descriptionQA;

	private double price;

	private byte discountPercentage;

	private byte taxPercentage;

	private byte platformFeePercentage;

	private double discountAmount;

	private double taxAmount;

	private double platformFee;

	private double discountedCost; // price - discount Amount

	private double clinicPay; // Price - platformFee

	private double finalCost; // taxAmount + discounedCost

}
