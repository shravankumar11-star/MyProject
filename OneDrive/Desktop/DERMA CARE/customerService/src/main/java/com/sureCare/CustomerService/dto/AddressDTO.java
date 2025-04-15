package com.sureCare.CustomerService.dto;


import com.sureCare.CustomerService.entity.Address;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "House number is required and cannot start or end with spaces.")
    private String houseNo;

    @NotBlank(message = "Street is required and cannot start or end with spaces.")
    private String street;

    @NotBlank(message = "City is required and cannot start or end with spaces.")
    private String city;

    @NotBlank(message = "State is required and cannot start or end with spaces.")
    private String state;

    @NotBlank(message = "Postal code is required and cannot start or end with spaces.")
    private String postalCode;

    @NotBlank(message = "Country is required and cannot start or end with spaces.")
    private String country;

    private String apartment;
    private String direction;
    private Double latitude;
    private Double longitude;

    // Constructor to convert Address to AddressDTO
    public AddressDTO(Address address) {
        this.houseNo = address.getHouseNo();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.postalCode = address.getPostalCode();
        this.country = address.getCountry();
        this.apartment = address.getApartment();
        this.direction = address.getDirection();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
    }
    
    public void trimFields() {
        this.houseNo = this.houseNo != null ? this.houseNo.trim() : null;
        this.street = this.street != null ? this.street.trim() : null;
        this.city = this.city != null ? this.city.trim() : null;
        this.state = this.state != null ? this.state.trim() : null;
        this.postalCode = this.postalCode != null ? this.postalCode.trim() : null;
        this.country = this.country != null ? this.country.trim() : null;
        this.apartment = this.apartment != null ? this.apartment.trim() : null;
        this.direction = this.direction != null ? this.direction.trim() : null;
    }
}
