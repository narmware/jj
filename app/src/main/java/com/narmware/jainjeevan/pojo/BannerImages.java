package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 10/05/18.
 */

public class BannerImages {
    String banner_title,service_image;

    public BannerImages(String banner_title, String service_image) {
        this.banner_title = banner_title;
        this.service_image = service_image;
    }

    public String getBanner_title() {
        return banner_title;
    }

    public void setBanner_title(String banner_title) {
        this.banner_title = banner_title;
    }

    public String getService_image() {
        return service_image;
    }

    public void setService_image(String service_image) {
        this.service_image = service_image;
    }
}
