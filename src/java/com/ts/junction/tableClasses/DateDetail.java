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
public class DateDetail {
    
    int date_detail_id;
    String from_date, to_date, name, remark;

    public int getDate_detail_id() {
        return date_detail_id;
    }

    public void setDate_detail_id(int date_detail_id) {
        this.date_detail_id = date_detail_id;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
}
