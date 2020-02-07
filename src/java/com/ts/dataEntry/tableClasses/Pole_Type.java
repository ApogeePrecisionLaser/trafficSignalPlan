/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.tableClasses;

/**
 *
 * @author DELL
 */
public class Pole_Type {
    
int pole_type_id;
    String pole_type;
    String active;
    int revision_no;

    public int getPole_type_id() {
        return pole_type_id;
    }

    public void setPole_type_id(int pole_type_id) {
        this.pole_type_id = pole_type_id;
    }

    public String getPole_type() {
        return pole_type;
    }

    public void setPole_type(String pole_type) {
        this.pole_type = pole_type;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

 
    
}
