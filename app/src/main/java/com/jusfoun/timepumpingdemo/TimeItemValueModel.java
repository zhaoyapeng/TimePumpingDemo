package com.jusfoun.timepumpingdemo;

import java.io.Serializable;

/**
 * Author  wangchenchen
 * CreateDate 2016/1/11.
 * Email wcc@jusfoun.com
 * Description
 */
public class TimeItemValueModel implements Serializable {
    private String type;
    private String image;
    private String month;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
