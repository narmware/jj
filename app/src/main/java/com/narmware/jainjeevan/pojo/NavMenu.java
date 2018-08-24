package com.narmware.jainjeevan.pojo;

public class NavMenu {
    String nav_title;
    int nav_img;

    public NavMenu(String nav_title, int nav_img) {
        this.nav_title = nav_title;
        this.nav_img = nav_img;
    }

    public String getNav_title() {
        return nav_title;
    }

    public void setNav_title(String nav_title) {
        this.nav_title = nav_title;
    }

    public int getNav_img() {
        return nav_img;
    }

    public void setNav_img(int nav_img) {
        this.nav_img = nav_img;
    }
}
