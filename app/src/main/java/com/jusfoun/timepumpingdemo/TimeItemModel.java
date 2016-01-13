package com.jusfoun.timepumpingdemo;

import java.io.Serializable;
import java.util.List;

/**
 * Author  wangchenchen
 * CreateDate 2016/1/11.
 * Email wcc@jusfoun.com
 * Description
 */
public class TimeItemModel implements Serializable {

    private String year;
    private List<TimeItemValueModel> value;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<TimeItemValueModel> getValue() {
        return value;
    }

    public void setValue(List<TimeItemValueModel> value) {
        this.value = value;
    }
}
