package com.AdminService.entity;

import java.util.List;
import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesAdded {
    private ObjectId serviceId;
    private String notificationId;
    private long providerMobileNumber; 
    private String status;
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
    private List<com.AdminService.dto.DayStatus> daysStatus; // New field for storing day status
    private double latitude; // New field for latitude
    private double longitude; // New field for longitude
   
    
}


