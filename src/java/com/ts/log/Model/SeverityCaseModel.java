/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Model;

import com.ts.junction.Model.JunctionModel;
import com.ts.log.tableClasses.SeverityCase;
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
public class SeverityCaseModel {
    
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
    
    public List<SeverityCase> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<SeverityCase> list = new ArrayList<SeverityCase>();
        try {
            String query = "SELECT s.severity_case_id, s.severity_level_id, s.severity_case, l.severity_number,s.remark,s.revision_no,s.send_data,s.recieved_data"
                    + " from severity_level AS l join severity_case As s on s.severity_level_id = l.severity_level_id"
                    + " WHERE s.active='Y' "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                SeverityCase severity = new SeverityCase();
                severity.setSeverity_level_id(rset.getInt("severity_level_id"));
                severity.setSeverity_case_id(rset.getInt("severity_case_id"));
                severity.setSeverity_level(rset.getInt("severity_number"));
                severity.setSeverity_case(rset.getString("severity_case"));
                severity.setRevision_no(rset.getInt("revision_no"));
                severity.setRemark(rset.getString("remark"));
                severity.setSent_data(rset.getString("send_data"));
                severity.setReceived_data(rset.getString("recieved_data"));
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
            ResultSet rset = connection.prepareStatement("select count(*) from severity_case WHERE active= 'Y'").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("SeverityCaseModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
    
    public List<String> getLevel(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT s.severity_number"
                + " FROM severity_level AS s where s.active='Y'";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("severity_number");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    public int getLevelId(int severity_number) { 
         int levelId = 0;
        try {
            ResultSet rset = connection.prepareStatement("select severity_level_id from severity_level WHERE active= 'Y' and severity_number="+severity_number).executeQuery();
            rset.next();
            levelId = Integer.parseInt(rset.getString(1));
            System.out.println(levelId);
        } catch (Exception e) {
            System.out.println("SeverityLevelModel getNoOfRows() Error: " + e);
        }
        return levelId;
    }
    
    public boolean insertRecord(SeverityCase severityCase) {
        String Query = "INSERT INTO severity_case (severity_case_id,severity_level_id, severity_case, remark, send_data, recieved_data) "
                + " VALUES(?, ?, ?, ?, ?, ?) ";
        int severity_level_id = getLevelId(severityCase.getSeverity_level());
        String query = " SELECT MAX(severity_case_id) AS max_id FROM severity_case ";
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
            System.out.println("SeverityCaseModel insertRecord()getting max error" + e);
        }

        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(Query);
            pstmt.setInt(1, (junction_id + 1));
            pstmt.setInt(2, severity_level_id);
            pstmt.setString(3, severityCase.getSeverity_case());
            pstmt.setString(4, severityCase.getRemark());
            pstmt.setString(5, severityCase.getSent_data());
            pstmt.setString(6, severityCase.getReceived_data());
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
    
    public boolean updateRecord(SeverityCase severityCase) {
        String Query = "INSERT INTO severity_case (severity_case_id,severity_level_id, severity_case, remark,revision_no, send_data, recieved_data) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?) ";

        String update_query = " Update severity_case set active='N' where severity_case_id = ?";
        int junction_id = 0;
        int count = 0, rowsAffected = 0;
        PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean autoCommit = true;
        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(update_query);
            pstmt.setInt(1, severityCase.getSeverity_case_id());
            rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                 pstmt.close();
                    pstmt = connection.prepareStatement(Query);
                    pstmt.setInt(1, (junction_id + 1));
                    pstmt.setInt(2, severityCase.getSeverity_level_id());
                    pstmt.setString(3, severityCase.getSeverity_case());
                    pstmt.setString(4, severityCase.getRemark());
                    pstmt.setInt(5, severityCase.getRevision_no()+1);
                    pstmt.setString(6, severityCase.getSent_data());
                    pstmt.setString(7, severityCase.getReceived_data());
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
    
    public int deleteRecord(int id) {
        String junctionQuery = "UPDATE severity_case SET active = 'N' WHERE severity_case_id= " + id + " AND active = 'Y'";
       
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            rowsAffected = connection.prepareStatement(junctionQuery).executeUpdate();
            if (rowsAffected > 0) {                
                    connection.commit();                
            } else {
                    connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error:Delete:JunctionModel-- " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JunctionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
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
