/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.tableClasses;

/**
 *
 * @author DELL
 */
public class LogTable {
    
    int log_table_id;
    String send_data;
    String recieved_data;
    int severity_case_id;
    String severity_case;
    String sms_sent_status;
    int key_person_id;
    String date_time;
    int side_detail_id;
    String side_name;
    int junction_id;
    String junction_name;
    String remark;

    public int getLog_table_id() {
        return log_table_id;
    }

    public void setLog_table_id(int log_table_id) {
        this.log_table_id = log_table_id;
    }

    public String getSend_data() {
        return send_data;
    }

    public void setSend_data(String send_data) {
        this.send_data = send_data;
    }

    public String getRecieve_data() {
        return recieved_data;
    }

    public void setRecieve_data(String recieve_data) {
        this.recieved_data = recieve_data;
    }

    public int getSeverity_case_id() {
        return severity_case_id;
    }

    public void setSeverity_case_id(int severity_case_id) {
        this.severity_case_id = severity_case_id;
    }

    public String getSeverity_case() {
        return severity_case;
    }

    public void setSeverity_case(String severity_case) {
        this.severity_case = severity_case;
    }

    public String getSms_sent_status() {
        return sms_sent_status;
    }

    public void setSms_sent_status(String sms_sent_status) {
        this.sms_sent_status = sms_sent_status;
    }

    public int getKey_person_id() {
        return key_person_id;
    }

    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getSide_detail_id() {
        return side_detail_id;
    }

    public void setSide_detail_id(int side_detail_id) {
        this.side_detail_id = side_detail_id;
    }

    public String getSide_name() {
        return side_name;
    }

    public void setSide_name(String side_name) {
        this.side_name = side_name;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    public String getJunction_name() {
        return junction_name;
    }

    public void setJunction_name(String junction_name) {
        this.junction_name = junction_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
}
