package com.narmware.jainjeevan.pojo;

import java.util.ArrayList;

public class AddDharamshala {
    String name,contact_person,email,mobile,phone,city,pincode,address,state,option;
    ArrayList<String> dharamshala_facilities;
    ArrayList<String> bhojan_facilities;

    public AddDharamshala(String name, String contact_person, String email, String mobile, String phone, String city, String pincode, String address,String state) {
        this.name = name;
        this.contact_person = contact_person;
        this.email = email;
        this.mobile = mobile;
        this.phone = phone;
        this.city = city;
        this.pincode = pincode;
        this.address = address;
        this.state=state;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getDharamshala_facilities() {
        return dharamshala_facilities;
    }

    public void setDharamshala_facilities(ArrayList<String> dharamshala_facilities) {
        this.dharamshala_facilities = dharamshala_facilities;
    }

    public ArrayList<String> getBhojan_facilities() {
        return bhojan_facilities;
    }

    public void setBhojan_facilities(ArrayList<String> bhojan_facilities) {
        this.bhojan_facilities = bhojan_facilities;
    }
}
