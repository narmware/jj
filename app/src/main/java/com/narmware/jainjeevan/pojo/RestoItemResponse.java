package com.narmware.jainjeevan.pojo;

public class RestoItemResponse {

    String response;
    RestoItems[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public RestoItems[] getData() {
        return data;
    }

    public void setData(RestoItems[] data) {
        this.data = data;
    }
}
