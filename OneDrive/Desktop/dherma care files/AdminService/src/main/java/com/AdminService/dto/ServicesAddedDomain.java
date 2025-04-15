package com.AdminService.dto;


import com.AdminService.entity.ServicesAdded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesAddedDomain {
	private String status = "pending";
    private String serviceId;
    private String serviceName;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private int numberOfDays;
    private String numberOfHours;
    private double price;
    private byte discount;
    private double discountAmount;
    private double discountedCost;
    private byte tax;
    private double taxAmount;
    private double finalCost;
    private String startPin; // New field for start pin
    private String endPin; // New field for end pin
    private double latitude; // New field for latitude
    private double longitude; // New field for longitude
   
    
    // Constructor to convert ServicesAdded to ServicesAddedDomain
    public ServicesAddedDomain(ServicesAdded servicesAdded) {
    	this.status=servicesAdded.getStatus();
        this.serviceId = servicesAdded.getServiceId().toString(); // Convert ObjectId to String
        this.serviceName = servicesAdded.getServiceName();
        this.startDate = servicesAdded.getStartDate();
        this.endDate = servicesAdded.getEndDate();
        this.startTime = servicesAdded.getStartTime();
        this.endTime = servicesAdded.getEndTime();
        this.numberOfDays = servicesAdded.getNumberOfDays();
        this.numberOfHours = servicesAdded.getNumberOfHours();
        this.price = servicesAdded.getPrice();
        this.discount = servicesAdded.getDiscount();
        this.discountAmount = servicesAdded.getDiscountAmount();
        this.discountedCost = servicesAdded.getDiscountedCost();
        this.tax = servicesAdded.getTax();
        this.taxAmount = servicesAdded.getTaxAmount();
        this.finalCost = servicesAdded.getFinalCost();
        this.startPin = servicesAdded.getStartPin();
        this.endPin = servicesAdded.getEndPin();
        this.latitude=servicesAdded.getLatitude();
        this.longitude=servicesAdded.getLongitude();
    }
}
