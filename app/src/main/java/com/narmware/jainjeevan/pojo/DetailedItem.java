package com.narmware.jainjeevan.pojo;

public class DetailedItem {

    String item;
    String facility_id;
    String img;

    public DetailedItem(String item, String img) {
        this.item = item;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(String facility_id) {
        this.facility_id = facility_id;
    }
}
