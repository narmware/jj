package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 16/08/18.
 */

public class Facility {

    String facility_id,facility_name,img;
    boolean isSelected;

    public Facility(String facility_id, String facility_name, String img,boolean isSelected) {
        this.facility_id = facility_id;
        this.facility_name = facility_name;
        this.img = img;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(String facility_id) {
        this.facility_id = facility_id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
