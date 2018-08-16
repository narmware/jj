package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 16/08/18.
 */

public class FilterResponse {

    String response;
    City[] city;
    Facility[] facility;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public City[] getCity() {
        return city;
    }

    public void setCity(City[] city) {
        this.city = city;
    }

    public Facility[] getFacility() {
        return facility;
    }

    public void setFacility(Facility[] facility) {
        this.facility = facility;
    }
}
