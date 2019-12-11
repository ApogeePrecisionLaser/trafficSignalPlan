/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.PlanDetails;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class PlanDetailModel {
    
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

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("Image setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (Exception ex) {
            System.out.println("ERROR: in closeConnection in TrafficSignalWebServiceModel : " + ex);
        }
    }
    
    public boolean insertRecord(PlanDetails planDetails) {
        String insert_query = null;
         PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0;
        if(checkPlanNo(planDetails.getPlan_no())) {
            errorOccured = true;
            message = "Plan No already exsists";
            msgBgColor = COLOR_ERROR;
        } else {
            int plan_id = getMaxPlanId()+1;
            insert_query = "INSERT into plan_details(plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, "
                    + " side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, "
                    + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status,"
                    + " remark,plan_revision_no) "
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";    
            try {
                boolean autoCommit = connection.getAutoCommit();
                try {
                    connection.setAutoCommit(false);                

                        pstmt = connection.prepareStatement(insert_query);
                        pstmt.setInt(1, plan_id);
                        pstmt.setInt(2, planDetails.getPlan_no());
                        pstmt.setInt(3, planDetails.getOn_time_hour());
                        pstmt.setInt(4, planDetails.getOn_time_min());
                        pstmt.setInt(5, planDetails.getOff_time_hour());
                        pstmt.setInt(6, planDetails.getOff_time_min());
                        pstmt.setString(7, planDetails.getMode());
                        pstmt.setInt(8, planDetails.getSide1_green_time());
                        pstmt.setInt(9, planDetails.getSide2_green_time());
                        pstmt.setInt(10, planDetails.getSide3_green_time());
                        pstmt.setInt(11, planDetails.getSide4_green_time());
                        pstmt.setInt(12, planDetails.getSide5_green_time());
                        pstmt.setInt(13, planDetails.getSide1_amber_time());
                        pstmt.setInt(14, planDetails.getSide2_amber_time());
                        pstmt.setInt(15, planDetails.getSide3_amber_time());
                        pstmt.setInt(16, planDetails.getSide4_amber_time());
                        pstmt.setInt(17, planDetails.getSide5_amber_time());
                        pstmt.setString(18, planDetails.getTransferred_status());
                        pstmt.setString(19, planDetails.getRemark());
                        pstmt.setInt(20, 0);
                        rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // Finally commit the connection.
                        connection.commit();
                        message = "Data has been saved successfully.";
                        msgBgColor = COLOR_OK;
                    } else {
                        throw new SQLException("All records were NOT saved.");
                    }


                } catch (SQLException sqlEx) {
                    errorOccured = true;
                    connection.rollback();
                    message = "Could NOT save all data , some error.";
                    msgBgColor = COLOR_ERROR;
                    System.out.println("PlanInfoModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
                } finally {
                    pstmt.close();
                    connection.setAutoCommit(autoCommit);
                }
            } catch (Exception e) {
                System.out.println("PlanInfoModel insert() Error: " + e);
            }
        }
        
        return !errorOccured;
    }
    
    public boolean checkPlanNo(int plan_no){
        boolean plan_no_check = false;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(*) FROM plan_details where plan_no = "+plan_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_no_check = Integer.parseInt(rset.getString(1)) > 0;
            System.out.println(plan_no_check);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return plan_no_check;
    }
    
    public boolean updatePreviousRecord(int plan_id) throws SQLException {
        PreparedStatement pstmt;
        int rowAffected = 0;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" UPDATE plan_details SET active = 'N' where plan_id = "+plan_id);
            rowAffected = pstmt.executeUpdate();   
            if (rowAffected > 0) {
                // Finally commit the connection.
                connection.commit();
            } else {
                throw new SQLException("All records were NOT saved.");
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return rowAffected > 0;
    }
    
    public int getMaxPlanId() {
        int plan_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT Max(plan_id) FROM plan_details ");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_id = Integer.parseInt(rset.getString(1));
            System.out.println(plan_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return plan_id;
    }
    
    public int getNoOfRows() {

      String query1="select count(*) "
                  +" from plan_details p "
                  +" where p.active='Y' ";
                  

        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query1);
           
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows SelectionModel" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }
    
     public List<PlanDetails> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
        List<PlanDetails> list = new ArrayList<PlanDetails>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";

       String query2="select plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min,"
               + " mode, side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time,"
               + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time,"
               + " transferred_status, remark"
                     +" from plan_details s"
                     +" where s.active='Y' ORDER BY plan_no "
                     + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PlanDetails bean = new PlanDetails();
                bean.setPlan_id(rset.getInt(1));
                bean.setPlan_no(rset.getInt(2));
                bean.setOn_time_hour(rset.getInt(3));
                bean.setOn_time_min(rset.getInt(4));
                bean.setOff_time_hour(rset.getInt(5));
                bean.setOff_time_min(rset.getInt(6));
                bean.setMode(rset.getString(7));
                bean.setSide1_green_time(rset.getInt(8));
                bean.setSide2_green_time(rset.getInt(9));
                bean.setSide3_green_time(rset.getInt(10));
                bean.setSide4_green_time(rset.getInt(11));
                bean.setSide5_green_time(rset.getInt(12));
                bean.setSide1_amber_time(rset.getInt(13));
                bean.setSide2_amber_time(rset.getInt(14));
                bean.setSide3_amber_time(rset.getInt(15));
                bean.setSide4_amber_time(rset.getInt(16));
                bean.setSide5_amber_time(rset.getInt(17));
                bean.setTransferred_status(rset.getString(18));
                bean.setRemark(rset.getString(19));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
     
    public int getRevisionNo(int plan_id) {
        int revision_no = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT plan_revision_no FROM plan_details where plan_id="+plan_id+" AND active='Y'");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            revision_no = Integer.parseInt(rset.getString(1));
            System.out.println(revision_no);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return revision_no;
    }
     
     public boolean updateRecord(PlanDetails planDetails) {
        String insert_query = null;
         PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0;
        try {
            boolean autoCommit = connection.getAutoCommit();
            if(!updatePreviousRecord(planDetails.getPlan_id())) {
                errorOccured = true;
                message = "Plan Not updated";
                msgBgColor = COLOR_ERROR;
            } else {
                int plan_revision_no = getRevisionNo(planDetails.getPlan_id())+1;
                insert_query = "INSERT into plan_details(plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, "
                        + " side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, "
                        + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status,"
                        + " remark,plan_revision_no) "
                        + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";    

                    try {
                        connection.setAutoCommit(false);                

                            pstmt = connection.prepareStatement(insert_query);
                            pstmt.setInt(1, planDetails.getPlan_id());
                            pstmt.setInt(2, planDetails.getPlan_no());
                            pstmt.setInt(3, planDetails.getOn_time_hour());
                            pstmt.setInt(4, planDetails.getOn_time_min());
                            pstmt.setInt(5, planDetails.getOff_time_hour());
                            pstmt.setInt(6, planDetails.getOff_time_min());
                            pstmt.setString(7, planDetails.getMode());
                            pstmt.setInt(8, planDetails.getSide1_green_time());
                            pstmt.setInt(9, planDetails.getSide2_green_time());
                            pstmt.setInt(10, planDetails.getSide3_green_time());
                            pstmt.setInt(11, planDetails.getSide4_green_time());
                            pstmt.setInt(12, planDetails.getSide5_green_time());
                            pstmt.setInt(13, planDetails.getSide1_amber_time());
                            pstmt.setInt(14, planDetails.getSide2_amber_time());
                            pstmt.setInt(15, planDetails.getSide3_amber_time());
                            pstmt.setInt(16, planDetails.getSide4_amber_time());
                            pstmt.setInt(17, planDetails.getSide5_amber_time());
                            pstmt.setString(18, planDetails.getTransferred_status());
                            pstmt.setString(19, planDetails.getRemark());
                            pstmt.setInt(20, plan_revision_no);
                            rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            // Finally commit the connection.
                            connection.commit();
                            message = "Data has been updated successfully.";
                            msgBgColor = COLOR_OK;
                        } else {
                            throw new SQLException("All records were NOT saved.");
                        }


                    } catch (SQLException sqlEx) {
                        errorOccured = true;
                        connection.rollback();
                        message = "Could NOT save all data , some error.";
                        msgBgColor = COLOR_ERROR;
                        System.out.println("PlanInfoModel updateRecord() Error: " + message + " Cause: " + sqlEx.getMessage());
                    } finally {
                        pstmt.close();
                        connection.setAutoCommit(autoCommit);
                    }
            }
         } catch (Exception e) {
                System.out.println("PlanInfoModel updateRecord() Error: " + e);
        }
        
        return !errorOccured;
    }
     
     public int getMaxPlanNo() {
         int plan_no = 0;
         PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT Max(plan_no) FROM plan_details where active = 'Y' ");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_no = Integer.parseInt(rset.getString(1));
            System.out.println(plan_no);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getMaxPlanNo() Error: " + e);
        }
        return plan_no;
     }
     
     public void deleteRecord(int plan_id) throws SQLException {
        PreparedStatement pstmt;
        int rowAffected = 0;
        try {           
            pstmt = connection.prepareStatement(" UPDATE plan_details SET active = 'N' where plan_id = "+plan_id);
            rowAffected = pstmt.executeUpdate();  
            if(rowAffected>0) {
                message = "Data has been updated successfully.";
                msgBgColor = COLOR_OK;
            }
        } catch (SQLException e) {
                message = "Could NOT delete data , some error.";
                msgBgColor = COLOR_OK;
            System.out.println("PlanInfoModel deleteRecord() Error: " + e);
        }
    }
    
}
