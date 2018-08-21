package com.narmware.jainjeevan.pojo;

public class AddVendor {
    String business_name,product_name,contact_person,email,contact_no,city,pincode,detailes_address;

    public AddVendor(String business_name, String product_name, String contact_person, String email, String contact_no, String city, String pincode, String detailes_address) {
        this.business_name = business_name;
        this.product_name = product_name;
        this.contact_person = contact_person;
        this.email = email;
        this.contact_no = contact_no;
        this.city = city;
        this.pincode = pincode;
        this.detailes_address = detailes_address;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDetailes_address() {
        return detailes_address;
    }

    public void setDetailes_address(String detailes_address) {
        this.detailes_address = detailes_address;
    }
}
