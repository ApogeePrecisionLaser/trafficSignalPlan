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
public class Camera {
    int camera_id;
    String camera_ip;
    String camera_make;
    String camera_type;
    String junction_name;
    int junction_id;
    int side_no;
     String remark;
    String active;
    int revision_no;
    
    private String image_folder;
    private String image_name;

    public String getImage_folder() {
        return image_folder;
    }

    public void setImage_folder(String image_folder) {
        this.image_folder = image_folder;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
    

    public int getCamera_id() {
        return camera_id;
    }

    public String getCamera_ip() {
        return camera_ip;
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

    public void setCamera_id(int camera_id) {
        this.camera_id = camera_id;
    }

    public void setCamera_ip(String camera_ip) {
        this.camera_ip = camera_ip;
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

    public String getCamera_make() {
        return camera_make;
    }

    public void setCamera_make(String camera_make) {
        this.camera_make = camera_make;
    }

    public String getCamera_type() {
        return camera_type;
    }

    public void setCamera_type(String camera_type) {
        this.camera_type = camera_type;
    }

    public int getSide_no() {
        return side_no;
    }

    public void setSide_no(int side_no) {
        this.side_no = side_no;
    }

    public String getJunction_name() {
        return junction_name;
    }

    public void setJunction_name(String junction_name) {
        this.junction_name = junction_name;
    }

    public int getJunction_id() {
        return junction_id;
    }

    public void setJunction_id(int junction_id) {
        this.junction_id = junction_id;
    }

    

     
    
}
