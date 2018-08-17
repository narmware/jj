package com.narmware.jainjeevan.pojo;

import java.util.ArrayList;

/**
 * Created by rohitsavant on 16/08/18.
 */

public class SendFilters {
    String city_id;
    ArrayList<String> facilities;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(ArrayList<String> facilities) {
        this.facilities = facilities;
    }
}
