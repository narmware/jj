package com.narmware.jainjeevan.pojo;

public class UpdateProfile {
    String cust_id,profile_city,profile_state,profile_pincode,profile_address;

    public UpdateProfile(String cust_id,String profile_city, String profile_state, String profile_pincode, String profile_address) {
        this.profile_city = profile_city;
        this.profile_state = profile_state;
        this.profile_pincode = profile_pincode;
        this.profile_address = profile_address;
        this.cust_id=cust_id;
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
