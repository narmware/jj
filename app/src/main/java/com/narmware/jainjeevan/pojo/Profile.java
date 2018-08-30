package com.narmware.jainjeevan.pojo;

public class Profile {
    String cust_id,profile_city,profile_state,profile_pincode,profile_address,profile_dob,profile_img;

    public Profile(String cust_id, String profile_city, String profile_state, String profile_pincode, String profile_address, String profile_dob) {
        this.profile_city = profile_city;
        this.profile_state = profile_state;
        this.profile_pincode = profile_pincode;
        this.profile_address = profile_address;
        this.cust_id=cust_id;
        this.profile_dob = profile_dob;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getProfile_dob() {
        return profile_dob;
    }

    public void setProfile_dob(String profile_dob) {
        this.profile_dob = profile_dob;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getProfile_city() {
        return profile_city;
    }

    public void setProfile_city(String profile_city) {
        this.profile_city = profile_city;
    }

    public String getProfile_state() {
        return profile_state;
    }

    public void setProfile_state(String profile_state) {
        this.profile_state = profile_state;
    }

    public String getProfile_pincode() {
        return profile_pincode;
    }

    public void setProfile_pincode(String profile_pincode) {
        this.profile_pincode = profile_pincode;
    }

    public String getProfile_address() {
        return profile_address;
    }

    public void setProfile_address(String profile_address) {
        this.profile_address = profile_address;
    }
}
