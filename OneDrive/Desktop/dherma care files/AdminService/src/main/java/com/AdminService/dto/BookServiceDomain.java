package com.AdminService.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookServiceDomain {

    private String appointmentId;
    private String patientName;
    private String relationShip = "self";
    private String patientNumber;
    private String gender;
    private String emailId;
    private String age;
    private String customerNumber;
    private AddressDTO addressDto;
    private String categoryName;
    private List<ServicesAddedDomain> servicesAdded;
    private double totalPrice; 
    private double totalDiscountAmount;
    private double totalDiscountedAmount;
    private double totalTax;
    private double payAmount;
    private double amountToProvider;
  
    private String bookedAt; // Add this field
    
    private String bookedDate; // Add this field
    private String bookedTime; // Add this field
    
    // Constructor to convert BookService to BookServiceDomain
    public BookServiceDomain(com.AdminService.entity.BookService bookService) {
        this.appointmentId = bookService.getAppointmentId().toString();
        this.patientName = bookService.getPatientName();
        this.relationShip = bookService.getRelationShip();
        this.patientNumber = String.valueOf(bookService.getPatientNumber());
        this.gender = bookService.getGender();
        this.emailId = bookService.getEmailId();
        this.age = bookService.getAge();
        this.customerNumber = String.valueOf(bookService.getCustomerNumber());
        this.addressDto = new AddressDTO(bookService.getAddress());
//        this.status = bookService.getStatus();
        this.categoryName  = bookService.getCategoryName();
        this.servicesAdded = bookService.getServicesAdded().stream()
                             .map(ServicesAddedDomain::new)
                             .collect(Collectors.toList());
        this.totalPrice = bookService.getTotalPrice();
        this.totalDiscountAmount = bookService.getTotalDiscountAmount();
        this.totalDiscountedAmount= bookService.getTotalDiscountedAmount();
        this.totalTax = bookService.getTotalTax();
        this.payAmount = bookService.getPayAmount();
        this.bookedAt = bookService.getBookedAt(); // Set bookedAt field
        this.amountToProvider = (int) (bookService.getPayAmount() * 0.20);


    }
}
