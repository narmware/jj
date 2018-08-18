package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 10/05/18.
 */

public class BannerImages {
    String banner_title,banner_image,banner_type;

    public BannerImages(String banner_title, String banner_image, String banner_type) {
        this.banner_title = banner_title;
        this.banner_image = banner_image;
        this.banner_type = banner_type;
    }

    public String getBanner_title() {
        return banner_title;
    }

    public void setBanner_title(String banner_title) {
        this.banner_title = banner_title;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }
}
