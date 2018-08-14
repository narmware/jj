package com.narmware.jainjeevan.pojo;

public class MenuItemResponse {
    String response;
    MenuItem[] data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public MenuItem[] getData() {
        return data;
    }

    public void setData(MenuItem[] data) {
        this.data = data;
    }
}
