package com.example.otp.dto;

import com.example.otp.model.Supplier;  // Assuming Supplier is a model class

public class SupplierDTO {
    private Long id;
    private String email;
    private String phone;
    private String address;
    private String businessType;
    private String gstNumber;

    // Constructor that takes a Supplier object
    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getId();
        this.email = supplier.getEmail();
        this.phone = supplier.getPhone();
        this.address = supplier.getAddress();
        this.businessType = supplier.getBusinessType();
        this.gstNumber = supplier.getGstNumber();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }
}
