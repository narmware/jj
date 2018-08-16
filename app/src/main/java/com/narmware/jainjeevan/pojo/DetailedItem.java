package com.narmware.jainjeevan.pojo;

public class DetailedItem {

    String item;
    int img;

    public DetailedItem(String item, int img) {
        this.item = item;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
