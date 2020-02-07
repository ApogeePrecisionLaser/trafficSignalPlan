/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.JunctionPlanMap;
import com.ts.junction.tableClasses.PlanDetails;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author DELL
 */
public class JunctionPlanMapModelOLD {
    
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
    
    public List<String> getOnOffTime(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT on_time_hour, on_time_min, off_time_hour, off_time_min "
                + " FROM plan_details WHERE active = 'Y' "
                + " ORDER BY plan_no ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String on_time_hour = rset.getString("on_time_hour");
                String on_time_min = rset.getString("on_time_min");
                String off_time_hour = rset.getString("off_time_hour");
                String off_time_min = rset.getString("off_time_min");
                if (on_time_hour.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(on_time_hour+":"+on_time_min+"-"+off_time_hour+":"+off_time_min);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such state_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    public List<String> getDateTime(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT from_date, to_date "
                + " FROM date_detail Where active = 'Y' ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String from_date = rset.getString("from_date");
                String to_date = rset.getString("to_date");                
                if (from_date.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(from_date+"//"+to_date);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such state_name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    public int getMaxJunctionPlanId() {
        int plan_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT Max(junction_plan_map_id) FROM junction_plan_map ");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_id = Integer.parseInt(rset.getString(1));
            System.out.println(plan_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return plan_id;
    }
    
    public boolean insertRecord(JunctionPlanMap junctionPlanMap) {
        String insert_query = null;
         PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0;
        
            int junction_plan_map_id = getMaxJunctionPlanId()+1;
            int plan_id = getPlanId(junctionPlanMap.getOn_time_hr(),junctionPlanMap.getOn_time_min(),junctionPlanMap.getOff_time_hr(),junctionPlanMap.getOff_time_min());
            int junction_id = getJunctionId(junctionPlanMap.getJunction_name());
            int date_id = getDateId(junctionPlanMap.getFrom_date(),junctionPlanMap.getTo_date());
            int day_id = getDayId(junctionPlanMap.getDay());
            if(date_id > 0) {
                insert_query = "INSERT into junction_plan_map(junction_plan_map_id, plan_id, junction_id, date_id, order_no) "
                    + " VALUES(?, ?, ?, ?, ?) "; 
            } else if(day_id > 0) {
                insert_query = "INSERT into junction_plan_map(junction_plan_map_id, plan_id, junction_id, day_id, order_no) "
                    + " VALUES(?, ?, ?, ?, ?) "; 
            } else {
                insert_query = "INSERT into junction_plan_map(junction_plan_map_id, plan_id, junction_id, order_no) "
                    + " VALUES(?, ?, ?, ?) "; 
            }
            try {
                boolean autoCommit = connection.getAutoCommit();
                try {
                    connection.setAutoCommit(false);                

                        pstmt = connection.prepareStatement(insert_query);
                        pstmt.setInt(1, junction_plan_map_id);
                        pstmt.setInt(2, plan_id);
                        pstmt.setInt(3, junction_id);
                        if(date_id > 0) {
                            pstmt.setInt(4, date_id);
                            pstmt.setInt(5, junctionPlanMap.getOrder_no());
                        } else if(day_id > 0) {
                            pstmt.setInt(4, day_id);
                            pstmt.setInt(5, junctionPlanMap.getOrder_no());
                        } else {
                            pstmt.setInt(4, junctionPlanMap.getOrder_no());
                        }
                        
                       
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
                    System.out.println("JunctionPlanMapModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
                } finally {
                    pstmt.close();
                    connection.setAutoCommit(autoCommit);
                }
            } catch (Exception e) {
                System.out.println("JunctionPlanMapModel insert() Error: " + e);
            }
            
            return !errorOccured;
        }
        
       

    private int getPlanId(int on_time_hr, int on_time_min, int off_time_hr, int off_time_min) {
        int plan_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT plan_id FROM plan_details where on_time_hour = ? "
                    + "and on_time_min = ? and off_time_hour = ? and off_time_min = ? and active='Y' Order by plan_id");
            pstmt.setInt(1, on_time_hr);
            pstmt.setInt(2, on_time_min);
            pstmt.setInt(3, off_time_hr);
            pstmt.setInt(4, off_time_min);
            
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_id = Integer.parseInt(rset.getString(1));
            System.out.println(plan_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return plan_id;
    }

    private int getJunctionId(String junction_name) {
        int junction_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT junction_id FROM junction where junction_name = ? "
                    + "and final_revision ='VALID'  Order by junction_id");
            pstmt.setString(1, junction_name);
            
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            junction_id = Integer.parseInt(rset.getString(1));
            System.out.println(junction_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return junction_id;
    }

    private int getDateId(String from_date, String to_date) {
        int date_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT date_detail_id FROM date_detail where from_date = ? "
                    + "and to_date = ? and active ='Y' Order by date_detail_id");
            pstmt.setString(1, from_date);
            pstmt.setString(2, to_date);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            date_id = Integer.parseInt(rset.getString(1));
            System.out.println(date_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return date_id;
    }

    private int getDayId(String day) {
        int day_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT day_detail_id FROM day_detail where day = ? "
                    + " and active ='Y' Order by day_detail_id");
            pstmt.setString(1, day);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            day_id = Integer.parseInt(rset.getString(1));
            System.out.println(day_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return day_id ;
    }
    
    public List<JunctionPlanMap> showData(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName) {
        List<JunctionPlanMap> list = new ArrayList<JunctionPlanMap>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";

       String query2="SELECT jp.junction_plan_map_id, jp.order_no, dd.from_date, dd.to_date, d.day, "
                    + "j.junction_name, p.on_time_hour,p.on_time_min,p.off_time_hour,p.off_time_min,p.plan_no " 
                    +"FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                    +"left join day_detail d on jp.day_id = d.day_detail_id " 
                    +"inner join junction j on jp.junction_id = j.junction_id " 
                    +"inner join plan_details p on jp.plan_id = p.plan_id "
                    +"where j.final_revision = 'VALID' and p.active = 'Y'  "
                    +addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JunctionPlanMap bean = new JunctionPlanMap();
                bean.setJunction_plan_map_id(rset.getInt(1));
                bean.setOrder_no(rset.getInt(2));
                bean.setFrom_date(rset.getString(3));
                bean.setTo_date(rset.getString(4));
                bean.setDay(rset.getString(5));
                bean.setJunction_name(rset.getString(6));
                bean.setOn_time_hr(rset.getInt(7)); 
                bean.setOn_time_min(rset.getInt(8));
                bean.setOff_time_hr(rset.getInt(9));
                bean.setOff_time_min(rset.getInt(10));
                bean.setPlan_no(rset.getInt(11));
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
            pstmt = connection.prepareStatement(" SELECT revision_no FROM junction_plan_map where junction_plan_map_id="+plan_id+" AND active='Y'");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            revision_no = Integer.parseInt(rset.getString(1));
            System.out.println(revision_no);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return revision_no;
    }
    
    public boolean updateRecord(JunctionPlanMap junctionPlanMap) {
        String insert_query = null;
         PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0;
        try {
            boolean autoCommit = connection.getAutoCommit();
            if(!updatePreviousRecord(junctionPlanMap.getJunction_plan_map_id())) {
                errorOccured = true;
                message = "Plan Not updated";
                msgBgColor = COLOR_ERROR;
            } else {
                int plan_id = getPlanId(junctionPlanMap.getOn_time_hr(), junctionPlanMap.getOn_time_min(), junctionPlanMap.getOff_time_hr(), junctionPlanMap.getOff_time_min());
                int junction_id = getJunctionId(junctionPlanMap.getJunction_name());
                int date_id = getDateId(junctionPlanMap.getFrom_date(),junctionPlanMap.getTo_date());
                int day_id = getDayId(junctionPlanMap.getDay());
                int revision_no = getRevisionNo(junctionPlanMap.getJunction_plan_map_id())+1;
                if(date_id > 0) {
                insert_query = "INSERT into junction_plan_map(junction_plan_map_id, plan_id, junction_id, date_id, order_no,revision_no) "
                    + " VALUES(?, ?, ?, ?, ?, ?) "; 
                } else if(day_id > 0) {
                    insert_query = "INSERT into junction_plan_map(junction_plan_map_id, plan_id, junction_id, day_id, order_no,revision_no) "
                        + " VALUES(?, ?, ?, ?, ?, ?) "; 
                } else {
                    insert_query = "INSERT into junction_plan_map(junction_plan_map_id, plan_id, junction_id, order_no,revision_no ) "
                        + " VALUES(?, ?, ?, ?, ?) "; 
                }    

                    try {
                        connection.setAutoCommit(false);                

                            pstmt = connection.prepareStatement(insert_query);
                        pstmt.setInt(1, junctionPlanMap.getJunction_plan_map_id());
                        pstmt.setInt(2, plan_id);
                        pstmt.setInt(3, junction_id);
                        if(date_id > 0) {
                            pstmt.setInt(4, date_id);
                            pstmt.setInt(5, junctionPlanMap.getOrder_no());
                            pstmt.setInt(6, revision_no);
                        } else if(day_id > 0) {
                            pstmt.setInt(4, day_id);
                            pstmt.setInt(5, junctionPlanMap.getOrder_no());
                            pstmt.setInt(6, revision_no);
                        } else {
                            pstmt.setInt(4, junctionPlanMap.getOrder_no());
                            pstmt.setInt(5, revision_no);
                        }
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
    
    public boolean updatePreviousRecord(int id) throws SQLException {
        PreparedStatement pstmt;
        int rowAffected = 0;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" UPDATE junction_plan_map SET active = 'N' where junction_plan_map_id = "+id);
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
}
