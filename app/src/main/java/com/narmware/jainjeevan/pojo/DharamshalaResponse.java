package com.narmware.jainjeevan.pojo;

public class DharamshalaResponse {

    String response;
    DharamshalaItem[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public DharamshalaItem[] getData() {
        return data;
    }

    public void setData(DharamshalaItem[] data) {
        this.data = data;
    }
}
