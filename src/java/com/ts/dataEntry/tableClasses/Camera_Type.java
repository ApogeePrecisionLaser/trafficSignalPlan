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
public class Camera_Type {
    int camera_type_id;
    String camera_type;
     String remark;
    String active;
    int revision_no;

    public int getCamera_type_id() {
        return camera_type_id;
    }

    public String getCamera_type() {
        return camera_type;
    }

    public String getRemark() {
        return remark;
    }

    public String getActive() {
        return active;
    }

    public int getRevision_no() {
        return revision_no;
    }

    public void setCamera_type_id(int camera_type_id) {
        this.camera_type_id = camera_type_id;
    }

    public void setCamera_type(String camera_type) {
        this.camera_type = camera_type;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }
    
    
}
