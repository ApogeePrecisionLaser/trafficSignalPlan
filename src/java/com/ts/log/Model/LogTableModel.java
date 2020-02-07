/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Model;

import com.ts.log.tableClasses.LogTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class LogTableModel {
    
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
    
    public List<LogTable> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<LogTable> list = new ArrayList<LogTable>();
        try {
            String query = "SELECT j.junction_id,side.side_detail_id,j.junction_name,side.side_name,l.log_table_id, s.send_data, s.recieved_data, l.sms_sent_status, l.date_time, l.revision_no, l.active,s.severity_case_id, s.severity_case"
                    + " from log_table AS l "
                    + "join severity_case As s on s.severity_case_id = l.case_id "
                    + "join side_detail As side on side.side_detail_id = l.side_detail_id "
                    + "join junction as j on j.junction_id = side.junction_id "
                    + " WHERE s.active='Y' "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//            
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                LogTable logTable = new LogTable();
                logTable.setJunction_name(rset.getString("junction_name"));
                logTable.setJunction_id(rset.getInt("junction_id"));
                logTable.setSide_name(rset.getString("side_name"));
                logTable.setSide_detail_id(rset.getInt("side_detail_id"));
                logTable.setLog_table_id(rset.getInt("log_table_id"));
                logTable.setSend_data(rset.getString("send_data"));
                logTable.setR_data(rset.getString("recieved_data"));
                logTable.setSms_sent_status(rset.getString("sms_sent_status"));
                logTable.setDate_time(rset.getString("date_time"));
                logTable.setSeverity_case(rset.getString("severity_case"));
                list.add(logTable);
            }
//
        } catch (Exception e) {
            System.out.println("Error:SeverityLevelModel-showData--- " + e);
        }
        return list;
    }
    
    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from log_table WHERE active= 'Y'").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("SeverityCaseModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
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
