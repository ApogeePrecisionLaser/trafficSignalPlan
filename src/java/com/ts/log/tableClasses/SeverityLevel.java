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
public class SeverityLevel {
    int severity_level_id;
    int severity_number;
    String remark;
    int revision_no;

    public int getSeverity_level_id() {
        return severity_level_id;
    }

    public void setSeverity_level_id(int severity_level_id) {
        this.severity_level_id = severity_level_id;
    }

    public int getSeverity_number() {
        return severity_number;
    }

    public void setSeverity_number(int severity_number) {
        this.severity_number = severity_number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }
    
    
    
}
