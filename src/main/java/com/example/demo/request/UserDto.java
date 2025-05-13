package com.example.demo.request;

import java.util.List;

public class UserDto {
	
    private String firstName;
    private String lastName;
    private String userEmail;
    private Long mobileNumber;
    private List<UserAddressDto> addresses;

    public UserDto() {}

    public UserDto(String firstName, String lastName, String userEmail, Long mobileNumber, List<UserAddressDto> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.mobileNumber = mobileNumber;
        this.addresses = addresses;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<UserAddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<UserAddressDto> addresses) {
        this.addresses = addresses;
    }

}
