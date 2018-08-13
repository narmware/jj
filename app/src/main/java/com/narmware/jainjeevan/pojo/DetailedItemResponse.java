package com.narmware.jainjeevan.pojo;

public class DetailedItemResponse {
    String response;
    DetailedItem[] rules;
    DetailedItem[] bhojanshala;
    DetailedItem[] facility;
    DetailedItem[] room;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public DetailedItem[] getRules() {
        return rules;
    }

    public void setRules(DetailedItem[] rules) {
        this.rules = rules;
    }

    public DetailedItem[] getBhojanshala() {
        return bhojanshala;
    }

    public void setBhojanshala(DetailedItem[] bhojanshala) {
        this.bhojanshala = bhojanshala;
    }

    public DetailedItem[] getFacility() {
        return facility;
    }

    public void setFacility(DetailedItem[] facility) {
        this.facility = facility;
    }

    public DetailedItem[] getRoom() {
        return room;
    }

    public void setRoom(DetailedItem[] room) {
        this.room = room;
    }
}
