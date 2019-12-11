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
public class SeverityCase {
    
    int severity_case_id;
    String severity_case;
    String sent_data;
    String received_data;
    int severity_level_id;
    int severity_level;
    int revision_no;
    String remark;

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

    public int getSeverity_level_id() {
        return severity_level_id;
    }

    public void setSeverity_level_id(int severity_level_id) {
        this.severity_level_id = severity_level_id;
    }

    public int getSeverity_level() {
        return severity_level;
    }

    public void setSeverity_level(int severity_level) {
        this.severity_level = severity_level;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSent_data() {
        return sent_data;
    }

    public void setSent_data(String sent_data) {
        this.sent_data = sent_data;
    }

    public String getReceived_data() {
        return received_data;
    }

    public void setReceived_data(String received_data) {
        this.received_data = received_data;
    }

   
    
    
    
    
}
