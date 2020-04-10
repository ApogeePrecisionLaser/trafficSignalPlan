/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Model;

import com.ts.dataEntry.tableClasses.Pole_Type;

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
public class Pole_TypeModel {
    
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
            System.out.println("Pole_TypeModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT COUNT(*) FROM pole_type ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Pole_TypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<Pole_Type> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<Pole_Type> list = new ArrayList<Pole_Type>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT * FROM pole_type as pt where pt.active='Y' ORDER BY pole_type LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Pole_Type pole_type = new Pole_Type();
                pole_type.setPole_type_id(rset.getInt("pole_type_id"));
                pole_type.setPole_type(rset.getString("pole_type"));
                list.add(pole_type);
            }
        } catch (Exception e) {
            System.out.println("Pole_TypeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(Pole_Type pole_type) {
        String query = "INSERT INTO pole_type (pole_type) VALUES(?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_type.getPole_type());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Pole_TypeModel insertRecord() Error: " + e);
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

    public int updateRecord(Pole_Type pole_type) {
        String query = "UPDATE pole_type SET pole_type = ? WHERE pole_type_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pole_type.getPole_type());
            pstmt.setInt(2, pole_type.getPole_type_id());
//            pstmt.setString(2, position.getPosition());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Pole_TypeModel updateRecord() Error: " + e);
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

    public int deleteRecord(int pole_type_id) {
        String query = "UPDATE pole_type SET ACTIVE='N' WHERE pole_type_id = " + pole_type_id;
// String query = "delete pole_type_id WHERE pole_type_id = " + pole_type_id +"and active='Y'";
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Pole_TypeModel deleteRecord() Error: " + e);
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


