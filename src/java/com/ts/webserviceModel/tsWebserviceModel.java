/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.webserviceModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author DELL
 */
public class tsWebserviceModel {
     private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    public JSONArray getJunctionRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT junction_id,junction_name,address1,address2,city_id,controller_model,no_of_sides,amber_time,flash_rate,no_of_plans,"
                + "mobile_no,sim_no,imei_no,instant_green_time,pedestrian, pedestrian_time,side1_name,side2_name,side3_name,side4_name,side5_name,"
                + "program_version_no,transferred_status,final_revision, file_no,remark,timestamp,created_by,latitude,longitude,path,"
                + "bluetooth_address,monitor_status FROM junction where final_revision='valid'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("junction_id",rset.getInt("junction_id"));
                 obj.put("junction_name",rset.getString("junction_name"));
                 obj.put("address1",rset.getString("address1"));
                 obj.put("address2",rset.getString("address2"));
                 obj.put("city_id",rset.getInt("city_id"));
                 obj.put("controller_model",rset.getString("controller_model"));
                 obj.put("no_of_sides",rset.getInt("no_of_sides"));
                 obj.put("amber_time",rset.getInt("amber_time"));
                 obj.put("flash_rate",rset.getInt("flash_rate"));
                 obj.put("no_of_plans",rset.getInt("no_of_plans"));
                 obj.put("mobile_no",rset.getString("mobile_no"));
                 obj.put("sim_no",rset.getString("sim_no"));
                 obj.put("imei_no",rset.getString("imei_no"));
                 obj.put("instant_green_time",rset.getInt("instant_green_time"));
                 obj.put("pedestrian",rset.getString("pedestrian"));
                 obj.put("pedestrian_time",rset.getInt("pedestrian_time"));
                 obj.put("side1_name",rset.getString("side1_name"));
                 obj.put("side2_name",rset.getString("side2_name"));
                 obj.put("side3_name",rset.getString("side3_name"));
                 obj.put("side4_name",rset.getString("side4_name"));
                 obj.put("side5_name",rset.getString("side5_name"));
                 obj.put("program_version_no",rset.getInt("program_version_no"));
                 obj.put("transferred_status",rset.getString("transferred_status"));
                 obj.put("final_revision",rset.getString("final_revision"));
                 obj.put("file_no",rset.getInt("file_no"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("timestamp",rset.getString("timestamp"));
                 obj.put("created_by",rset.getInt("created_by"));
                 obj.put("latitude",rset.getString("latitude"));
                 obj.put("longitude",rset.getString("longitude"));   
                 obj.put("path",rset.getString("path"));
                 obj.put("bluetooth_address",rset.getString("bluetooth_address"));
                 obj.put("monitor_status",rset.getString("monitor_status"));
                   
                   
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of ts: " + e);
        }
        return rowData;
    }
    public JSONArray getDayDetailsRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT day_detail_id,junction_id,day_name,day,created_at,created_by,revision_no,active FROM day_detail where active='y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("day_detail_id",rset.getInt("day_detail_id"));
                 obj.put("junction_id",rset.getInt("junction_id"));
                 obj.put("day_name",rset.getString("day_name"));
                  obj.put("day",rset.getString("day"));
                   obj.put("created_at",rset.getString("created_at"));
                    obj.put("created_by",rset.getString("created_by"));
                     obj.put("revision_no",rset.getInt("revision_no"));
                      obj.put("active",rset.getString("active"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }

    public JSONArray getDateDetailsRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT date_detail_id,from_date,to_date,name,created_at,remark,created_by,revision_no,active FROM  date_detail where active='y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                obj.put("date_detail_id",rset.getInt("date_detail_id"));
                 obj.put("from_date",rset.getString("from_date"));
                  obj.put("to_date",rset.getString("to_date"));
                   obj.put("name",rset.getString("name"));
                    obj.put("created_at",rset.getString("created_at"));
                     obj.put("remark",rset.getString("remark"));
                      obj.put("created_by",rset.getString("created_by"));
                     obj.put("revision_no",rset.getInt("revision_no"));
                      obj.put("active",rset.getString("active"));
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getPlanDetailsRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT plan_id,plan_no,on_time_hour,on_time_min,off_time_hour,off_time_min,"
                + "mode,side1_green_time,side2_green_time,side3_green_time ,side4_green_time,"
                + "side5_green_time,side1_amber_time,side2_amber_time,side3_amber_time,"
                + "side4_amber_time,side5_amber_time,transferred_status, plan_revision_no,"
                + "timestamp,remark,created_by,active FROM plan_details where active='y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("plan_id",rset.getInt("plan_id"));
                  obj.put("plan_no",rset.getInt("plan_no"));
                   obj.put("on_time_hour",rset.getInt("on_time_hour"));
                    obj.put("on_time_min",rset.getInt("on_time_min"));
                     obj.put("off_time_hour",rset.getInt("off_time_hour"));
                      obj.put("off_time_min",rset.getInt("off_time_min"));
                  obj.put("mode",rset.getString("mode"));
                  obj.put("side1_green_time",rset.getInt("side1_green_time"));
                  obj.put("side2_green_time",rset.getInt("side2_green_time"));
                  obj.put("side3_green_time",rset.getInt("side3_green_time"));
                  obj.put("side4_green_time",rset.getInt("side4_green_time"));
                  obj.put("side5_green_time",rset.getInt("side5_green_time"));
                  obj.put("side1_amber_time",rset.getInt("side1_amber_time"));
                  obj.put("side2_amber_time",rset.getInt("side2_amber_time"));
                  obj.put("side3_amber_time",rset.getInt("side3_amber_time"));
                  obj.put("side4_amber_time",rset.getInt("side4_amber_time"));
                 obj.put("side5_amber_time",rset.getInt("side5_amber_time"));
                 obj.put("transferred_status",rset.getString("transferred_status"));
                   obj.put("plan_revision_no",rset.getInt("plan_revision_no"));
                   
                    obj.put("timestamp",rset.getString("timestamp"));
                     obj.put("remark",rset.getString("remark"));
                      obj.put("created_by",rset.getInt("created_by"));
                       obj.put("active",rset.getString("active"));

                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public JSONArray getJunctionPlanMapRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT junction_plan_map_id,plan_id,"
                + "junction_id,created_at,remark,created_by,"
                + "revision_no,active,date_id,day_id,order_no "
                + "FROM junction_plan_map where active='y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("junction_plan_map_id",rset.getInt("junction_plan_map_id")); 
                 obj.put("plan_id",rset.getInt("plan_id"));
                 obj.put("junction_id",rset.getInt("junction_id"));
                 obj.put("created_at",rset.getString("created_at"));
                 obj.put("remark",rset.getString("remark"));
                 obj.put("created_by",rset.getString("created_by"));
                 obj.put("revision_no",rset.getInt("revision_no"));
                 obj.put("active",rset.getString("active"));
                 obj.put("date_id",rset.getInt("date_id"));
                 obj.put("day_id",rset.getInt("day_id"));
                 obj.put("order_no",rset.getInt("order_no"));
                
                 
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    
    
    public JSONArray getPhaseDetailsRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT  phase_info_id,revision_no,phase_no,phase_time,"
                + "green1,green2,green3,green4,green5,side13,side24,side5,"
                + "left_green,padestrian_info, GPIO,active,timestamp,created_by,remark,junction_plan_id "
                + "FROM phase_detail where active='y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("phase_info_id",rset.getInt("phase_info_id"));
                  obj.put("revision_no",rset.getInt("revision_no"));
                   obj.put("phase_no",rset.getInt("phase_no"));
                    obj.put("phase_time",rset.getInt("phase_time"));
                     obj.put("green1",rset.getInt("green1"));
                      obj.put("green2",rset.getInt("green2"));
                       obj.put("green3",rset.getInt("green3"));
                        obj.put("green4",rset.getInt("green4"));
                         obj.put("green5",rset.getInt("green5"));
                          obj.put("side13",rset.getInt("side13"));
                           obj.put("side24",rset.getInt("side24"));
                            obj.put("side5",rset.getInt("side5"));
                             obj.put("left_green",rset.getInt("left_green"));
                               obj.put("padestrian_info",rset.getString("padestrian_info"));
                     obj.put("GPIO",rset.getInt("GPIO"));
                   obj.put("active",rset.getString("active"));
                    obj.put("timestamp",rset.getString("timestamp"));
                     obj.put("created_by",rset.getString("created_by"));
                      obj.put("remark",rset.getString("remark"));
                   // obj.put("junction_plan_id",rset.getInt("junction_plan_id"));
                  
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
   
    public JSONArray getPhaseMapRecords()
        {
        JSONArray rowData = new JSONArray();
        String query = null;
        query = "SELECT phase_map_id,revision_no,junction_plan_map_id,phase_id,active,timestamp,created_by,remark FROM phase_map where active='y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JSONObject obj = new JSONObject();
                 obj.put("phase_map_id",rset.getInt("phase_map_id")); 
                 obj.put("revision_no",rset.getInt("revision_no"));
                 obj.put("junction_plan_map_id",rset.getInt("junction_plan_map_id"));
                 obj.put("phase_id",rset.getInt("phase_id"));
                 obj.put("active",rset.getString("active"));
                 obj.put("timestamp",rset.getString("timestamp"));
                 obj.put("created_by",rset.getString("created_by"));
                 obj.put("remark",rset.getString("remark"));
                
                 
                 rowData.add(obj);
           }
        } catch (Exception e) {
            System.out.println("Error inside show data of survey: " + e);
        }
        return rowData;
    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            System.out.println("hii inside setConnection() method");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/traffic_signal_plan1", "jpss_2", "jpss_1277");
        } catch (Exception e) {
            System.out.println("BLEWebServicesModel setConnection() Error: " + e);
        }
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPassword() {
        return db_userPassword;
    }

    public void setDb_userPassword(String db_userPassword) {
        this.db_userPassword = db_userPassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }





}
