/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.tableClasses;

import java.util.List;

/**
 *
 * @author DELL
 */
public class PlanDetails {
    private int plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min;
    private int side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time;
    private int side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time,junction_plan_map_id;
    private String mode, transferred_status,  remark,totalphase;
   //int ontime_hr,int ontime_min,int offtime_hr,int ontime_min
    private List<Integer> plan_id1,plan_no1, on_time_hour1, on_time_min1, off_time_hour1, off_time_min1,side1_amber_time1, side2_amber_time1, side3_amber_time1, side4_amber_time1, side5_amber_time1;
    private List<String> mode1,transferred_status1,  remark1;

    public List<Integer> getPlan_id1() {
        return plan_id1;
    }

    public void setPlan_id1(List<Integer> plan_id1) {
        this.plan_id1 = plan_id1;
    }

    public List<Integer> getPlan_no1() {
        return plan_no1;
    }

    public void setPlan_no1(List<Integer> plan_no1) {
        this.plan_no1 = plan_no1;
    }

    public List<Integer> getOn_time_hour1() {
        return on_time_hour1;
    }

    public void setOn_time_hour1(List<Integer> on_time_hour1) {
        this.on_time_hour1 = on_time_hour1;
    }

    public List<Integer> getOn_time_min1() {
        return on_time_min1;
    }

    public void setOn_time_min1(List<Integer> on_time_min1) {
        this.on_time_min1 = on_time_min1;
    }

    public List<Integer> getOff_time_hour1() {
        return off_time_hour1;
    }

    public void setOff_time_hour1(List<Integer> off_time_hour1) {
        this.off_time_hour1 = off_time_hour1;
    }

    public List<Integer> getOff_time_min1() {
        return off_time_min1;
    }

    public void setOff_time_min1(List<Integer> off_time_min1) {
        this.off_time_min1 = off_time_min1;
    }

    public List<Integer> getSide1_amber_time1() {
        return side1_amber_time1;
    }

    public void setSide1_amber_time1(List<Integer> side1_amber_time1) {
        this.side1_amber_time1 = side1_amber_time1;
    }

    public List<Integer> getSide2_amber_time1() {
        return side2_amber_time1;
    }

    public void setSide2_amber_time1(List<Integer> side2_amber_time1) {
        this.side2_amber_time1 = side2_amber_time1;
    }

    public List<Integer> getSide3_amber_time1() {
        return side3_amber_time1;
    }

    public void setSide3_amber_time1(List<Integer> side3_amber_time1) {
        this.side3_amber_time1 = side3_amber_time1;
    }

    public List<Integer> getSide4_amber_time1() {
        return side4_amber_time1;
    }

    public void setSide4_amber_time1(List<Integer> side4_amber_time1) {
        this.side4_amber_time1 = side4_amber_time1;
    }

    public List<Integer> getSide5_amber_time1() {
        return side5_amber_time1;
    }

    public void setSide5_amber_time1(List<Integer> side5_amber_time1) {
        this.side5_amber_time1 = side5_amber_time1;
    }

    public List<String> getMode1() {
        return mode1;
    }

    public void setMode1(List<String> mode1) {
        this.mode1 = mode1;
    }

    public List<String> getTransferred_status1() {
        return transferred_status1;
    }

    public void setTransferred_status1(List<String> transferred_status1) {
        this.transferred_status1 = transferred_status1;
    }

    public List<String> getRemark1() {
        return remark1;
    }

    public void setRemark1(List<String> remark1) {
        this.remark1 = remark1;
    }
    
    //

    public int getJunction_plan_map_id() {
        return junction_plan_map_id;
    }

    public void setJunction_plan_map_id(int junction_plan_map_id) {
        this.junction_plan_map_id = junction_plan_map_id;
    }

    public String getTotalphase() {
        return totalphase;
    }

    public void setTotalphase(String totalphase) {
        this.totalphase = totalphase;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getPlan_no() {
        return plan_no;
    }

    public void setPlan_no(int plan_no) {
        this.plan_no = plan_no;
    }

    public int getOn_time_hour() {
        return on_time_hour;
    }

    public void setOn_time_hour(int on_time_hour) {
        this.on_time_hour = on_time_hour;
    }

    public int getOn_time_min() {
        return on_time_min;
    }

    public void setOn_time_min(int on_time_min) {
        this.on_time_min = on_time_min;
    }

    public int getOff_time_hour() {
        return off_time_hour;
    }

    public void setOff_time_hour(int off_time_hour) {
        this.off_time_hour = off_time_hour;
    }

    public int getOff_time_min() {
        return off_time_min;
    }

    public void setOff_time_min(int off_time_min) {
        this.off_time_min = off_time_min;
    }

    public int getSide1_green_time() {
        return side1_green_time;
    }

    public void setSide1_green_time(int side1_green_time) {
        this.side1_green_time = side1_green_time;
    }

    public int getSide2_green_time() {
        return side2_green_time;
    }

    public void setSide2_green_time(int side2_green_time) {
        this.side2_green_time = side2_green_time;
    }

    public int getSide3_green_time() {
        return side3_green_time;
    }

    public void setSide3_green_time(int side3_green_time) {
        this.side3_green_time = side3_green_time;
    }

    public int getSide4_green_time() {
        return side4_green_time;
    }

    public void setSide4_green_time(int side4_green_time) {
        this.side4_green_time = side4_green_time;
    }

    public int getSide5_green_time() {
        return side5_green_time;
    }

    public void setSide5_green_time(int side5_green_time) {
        this.side5_green_time = side5_green_time;
    }

    public int getSide1_amber_time() {
        return side1_amber_time;
    }

    public void setSide1_amber_time(int side1_amber_time) {
        this.side1_amber_time = side1_amber_time;
    }

    public int getSide2_amber_time() {
        return side2_amber_time;
    }

    public void setSide2_amber_time(int side2_amber_time) {
        this.side2_amber_time = side2_amber_time;
    }

    public int getSide3_amber_time() {
        return side3_amber_time;
    }

    public void setSide3_amber_time(int side3_amber_time) {
        this.side3_amber_time = side3_amber_time;
    }

    public int getSide4_amber_time() {
        return side4_amber_time;
    }

    public void setSide4_amber_time(int side4_amber_time) {
        this.side4_amber_time = side4_amber_time;
    }

    public int getSide5_amber_time() {
        return side5_amber_time;
    }

    public void setSide5_amber_time(int side5_amber_time) {
        this.side5_amber_time = side5_amber_time;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTransferred_status() {
        return transferred_status;
    }

    public void setTransferred_status(String transferred_status) {
        this.transferred_status = transferred_status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
