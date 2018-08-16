package com.narmware.jainjeevan.pojo;

/**
 * Created by rohitsavant on 16/08/18.
 */

public class Filter {

    String filter_id,filter_name;

    public Filter(String filter_id, String filter_name) {
        this.filter_id = filter_id;
        this.filter_name = filter_name;
    }

    public String getFilter_id() {
        return filter_id;
    }

    public void setFilter_id(String filter_id) {
        this.filter_id = filter_id;
    }

    public String getFilter_name() {
        return filter_name;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }
}
