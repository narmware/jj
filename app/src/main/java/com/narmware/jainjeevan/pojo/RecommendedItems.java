package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class RecommendedItems {

    String title,image,id;

    public RecommendedItems(String title, String image, String id) {
        this.title = title;
        this.image = image;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
