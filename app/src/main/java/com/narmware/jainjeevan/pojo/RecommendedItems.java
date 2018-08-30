package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 12/08/18.
 */

public class RecommendedItems {

    String dharmshala_id,name,manager,mobile,minamount,address,IMG,latitude,longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getDharmshala_id() {
        return dharmshala_id;
    }

    public void setDharmshala_id(String dharmshala_id) {
        this.dharmshala_id = dharmshala_id;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMinamount() {
        return minamount;
    }

    public void setMinamount(String minamount) {
        this.minamount = minamount;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
