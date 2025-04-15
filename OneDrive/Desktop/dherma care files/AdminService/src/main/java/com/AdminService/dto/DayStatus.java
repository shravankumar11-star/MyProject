package com.AdminService.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayStatus {
    private String date;
    private boolean status;

    // Constructor to initialize with false status
    public DayStatus(String date) {
        this.date = date;
        this.status = false; // Initial status set to false
    }
}
