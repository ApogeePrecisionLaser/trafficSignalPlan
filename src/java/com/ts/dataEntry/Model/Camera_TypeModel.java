/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Model;
import com.ts.dataEntry.tableClasses.Camera_Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Camera_TypeModel {
    
private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("Camera_TypeModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT COUNT(*) FROM camera_type ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Camera_TypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
  

    
    public int getLastId() {
        int noOfRows = 0;
        int camera_type_id=0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT camera_type_id FROM camera_type order by camera_type_id desc limit 1").executeQuery();
            rset.next();
              
            noOfRows = rset.getInt("camera_type_id");
        } catch (Exception e) {
            System.out.println("Camera_TypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<Camera_Type> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<Camera_Type> list = new ArrayList<Camera_Type>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT * FROM camera_type as c where c.active='Y' ORDER BY camera_type_id  "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Camera_Type camera_type = new Camera_Type();
                camera_type.setCamera_type_id(rset.getInt("camera_type_id"));
                camera_type.setCamera_type(rset.getString("camera_type"));
                  camera_type.setRemark(rset.getString("remark"));
                list.add( camera_type);
            }
        } catch (Exception e) {
            System.out.println("Camera_TypeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(Camera_Type camera_type) {
        String query = "INSERT INTO camera_type (camera_type,remark) VALUES(?,?) ";
      
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1,last_id+1 );
            pstmt.setString(1, camera_type.getCamera_type());
            pstmt.setString(2, camera_type.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Camera_TypeModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(Camera_Type camera_type) {
        String query = "UPDATE camera_type SET camera_type = ?, remark = ? WHERE camera_type_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, camera_type.getCamera_type());
             pstmt.setString(2, camera_type.getRemark());
            pstmt.setInt(3, camera_type.getCamera_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Camera_TypeModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecord(int camera_type_id) {
        String query = "UPDATE camera_type SET ACTIVE='N' WHERE camera_type_id =  " + camera_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Camera_TypeModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPasswrod = db_userPasswrod;
    }

 

   
}



