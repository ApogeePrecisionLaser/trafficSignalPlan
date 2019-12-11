/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.tableClasses;

/**
 *
 * @author DELL
 */
public class DayDetail {
    
    private int day_detail_id, junction_id;
    private String day_name, day, remark,junction_name;

    public int getDay_detail_id() {
        return day_detail_id;
    }

    public void setDay_detail_id(int day_detail_id) {
        this.day_detail_id = day_detail_id;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJunction_name() {
        return junction_name;
    }

    public void setJunction_name(String junction_name) {
        this.junction_name = junction_name;
    }

   
    
    
    
    
}
