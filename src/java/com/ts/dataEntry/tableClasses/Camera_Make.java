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
public class Camera_Make {
    int camera_make_id;
    String camera_make;
     String camera_model;
     String remark;
    String active;
    int revision_no;

    public String getCamera_model() {
        return camera_model;
    }

    public void setCamera_model(String camera_model) {
        this.camera_model = camera_model;
    }

    public int getCamera_make_id() {
        return camera_make_id;
    }

    public String getCamera_make() {
        return camera_make;
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

    public void setCamera_make_id(int camera_make_id) {
        this.camera_make_id = camera_make_id;
    }

    public void setCamera_make(String camera_make) {
        this.camera_make = camera_make;
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
