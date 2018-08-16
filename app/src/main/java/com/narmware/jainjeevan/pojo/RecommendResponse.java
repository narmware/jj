package com.narmware.jainjeevan.pojo;

public class RecommendResponse {
    String response;
    RecommendedItems[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public RecommendedItems[] getData() {
        return data;
    }

    public void setData(RecommendedItems[] data) {
        this.data = data;
    }
}
