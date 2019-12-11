/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Model;

import com.ts.log.tableClasses.SeverityLevel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class SeverityLevelModel {
    
    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    String image_uploaded_for_column = null, uploaded_table = null, destination_path;
    
    public List<SeverityLevel> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<SeverityLevel> list = new ArrayList<SeverityLevel>();
        try {
            String query = "SELECT severity_level_id, severity_number,remark,revision_no"
                    + " from severity_level AS s"
                    + " WHERE s.active='Y' "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                SeverityLevel severity = new SeverityLevel();
                severity.setSeverity_level_id(rset.getInt("severity_level_id"));
                severity.setSeverity_number(Integer.parseInt(rset.getString("severity_number")));
                severity.setRevision_no(rset.getInt("revision_no"));
                severity.setRemark(rset.getString("remark"));
                list.add(severity);
            }

        } catch (Exception e) {
            System.out.println("Error:SeverityLevelModel-showData--- " + e);
        }
        return list;
    }
    
    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from severity_level WHERE active= 'Y'").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("SeverityLevelModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
    
    public boolean insertRecord(SeverityLevel severityLevel) {
        String Query = "INSERT INTO severity_level (severity_level_id, severity_number, remark) "
                + " VALUES(?, ?, ?) ";

        String query = " SELECT MAX(severity_level_id) AS max_id FROM severity_level ";
        int junction_id = 0;
        int count = 0, rowsAffected = 0;
        PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean autoCommit = true;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("max_id");
            }
        } catch (Exception e) {
            System.out.println("SeverityLevelModel insertRecord()getting max error" + e);
        }

        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(Query);
            pstmt.setInt(1, (junction_id + 1));
            pstmt.setString(2, String.valueOf(severityLevel.getSeverity_number()));
            pstmt.setString(3, severityLevel.getRemark());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                // Finally commit the connection.
                connection.commit();
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
            } else {
                throw new SQLException("Record updated in plan_info.");
            }

        } catch (SQLException sqlEx) {
            errorOccured = true;
            message = "Could NOT save data , some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("JUnctionModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
        } finally {
            try {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.getLogger(SeverityLevel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return !errorOccured;
    }
    
    public boolean updateRecord(SeverityLevel severityLevel) {
        String Query = "INSERT INTO severity_level (severity_level_id, severity_number, revision_no,remark) "
                + " VALUES(?, ?, ?, ?) ";

        String update_query = " Update severity_level set active='N' where severity_level_id = ?";
        int junction_id = 0;
        int count = 0, rowsAffected = 0;
        PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean autoCommit = true;
        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(update_query);
            pstmt.setInt(1, severityLevel.getSeverity_level_id());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                 pstmt.close();
                    pstmt = connection.prepareStatement(Query);
                    pstmt.setInt(1, (junction_id + 1));
                    pstmt.setString(2, String.valueOf(severityLevel.getSeverity_number()));
                    pstmt.setString(4, severityLevel.getRemark());
                    pstmt.setInt(3, count);
                    rowsAffected = pstmt.executeUpdate();
                // Finally commit the connection.
                connection.commit();
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
            } else {
                throw new SQLException("Record updated in plan_info.");
            }

        } catch (SQLException sqlEx) {
            errorOccured = true;
            message = "Could NOT save data , some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("JUnctionModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
        } finally {
            try {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.getLogger(SeverityLevel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return !errorOccured;
    }

    
    
    public void setDriverClass(String driverclass) {
        this.driverClass = driverclass;
    }

    public void setConnectionString(String connectionstring) {
        this.connectionString = connectionstring;
    }

    public void setDb_userName(String username) {
        this.db_userName = username;
    }

    public void setDb_userPasswrod(String pass) {
        this.db_userPassword = pass;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("Image setConnection() Error: " + e);
        }
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception ex) {
            System.out.println("ERROR: on closeConnection() in ClientResponderModel : " + ex);
        }
    }
}
