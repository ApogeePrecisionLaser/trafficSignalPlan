/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.PlanDetails;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    
      public int checkplan(PlanDetails planDetails){
        boolean plan_check = false;
        List<PlanDetails> list = new ArrayList<PlanDetails>();
       int rowAffected=0;
        int generatedKey = 0;

       String query2="select plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min,"
               + " mode, side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time,"
               + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time,"
               + " transferred_status, remark"
                     +" from plan_details s"
                     +" where s.active='Y' and s.plan_no="+planDetails.getPlan_no()+" "
               + " and s.on_time_hour="+planDetails.getOn_time_hour()+" and "
              +" s.on_time_min="+planDetails.getOn_time_min()+" and s.off_time_hour="+planDetails.getOff_time_hour()+ " and "
              +" s.off_time_min="+planDetails.getOff_time_min()+" and s.mode='"+planDetails.getMode()+ "' and "
              +" s.side1_green_time="+planDetails.getSide1_green_time()+" and s.side2_green_time="+planDetails.getSide2_green_time()+ " and "
         + "  s.side3_green_time="+planDetails.getSide3_green_time()+" and "
         + "  s.side4_green_time="+planDetails.getSide4_green_time()+" and "
         + "  s.side5_green_time="+planDetails.getSide5_green_time()+" and "
         + "  s.side1_amber_time="+planDetails.getSide1_amber_time()+" and "
          + "  s.side2_amber_time="+planDetails.getSide2_amber_time()+" and "
         + "  s.side3_amber_time="+planDetails.getSide3_amber_time()+" and "
         + "  s.side4_amber_time="+planDetails.getSide4_amber_time()+" and "
         + "  s.side5_amber_time="+planDetails.getSide5_amber_time()+" and "         
    + "  s.transferred_status='"+planDetails.getTransferred_status()+"' and "
         + "  s.remark='"+planDetails.getRemark()+"' "            
               + "  ORDER BY plan_no ";
                   
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
//             if (rowAffected != 0) {
//            rset = pstmt.getGeneratedKeys();
//               
//                if (rset.next()) {
//                    generatedKey = rset.getInt(1);
//                }
//             }
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
                generatedKey=bean.getPlan_id();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
      // check if the list is empty or not 
        // after adding an element 
        plan_check = list.isEmpty(); 
        if (plan_check == true)
        {
            System.out.println("The List is empty"); 
        }
        else
        {
             System.out.println(generatedKey);
            System.out.println("The List is not empty");
        }
        return generatedKey;
    }
      
      //////////check update according to the fields
          public  List<PlanDetails> checkplanOneSearch(String on_off_time){
        boolean plan_check = false;
        List<PlanDetails> list = new ArrayList<PlanDetails>();
    String check[]=on_off_time.split(",");
     int on_time_hr=0;
     int on_time_min=0;
     int off_time_hr=0;
     int off_time_min=0;
      on_time_hr=Integer.parseInt(check[0].trim());
     on_time_min=Integer.parseInt(check[1].trim());
     off_time_hr=Integer.parseInt(check[2].trim());
     off_time_min=Integer.parseInt(check[3].trim());
       int rowAffected=0;
        int generatedKey = 0;

//       String query2="select * from plan_details where on_time_hour='"+on_time_hr+"'"           
//               + "  ORDER BY plan_no ";
   String query2="select * from plan_details where on_time_hour='"+on_time_hr+"' and on_time_min='"+on_time_min+"' and "
           + " off_time_hour='"+off_time_hr+"' and off_time_min='"+off_time_min+"'"            
               + "  ORDER BY plan_no ";
                   
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
//             if (rowAffected != 0) {
//            rset = pstmt.getGeneratedKeys();
//               
//                if (rset.next()) {
//                    generatedKey = rset.getInt(1);
//                }
//             }
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
               
//                list1.add(rset.getInt(1));
//                list2.add(rset.getInt(2));
//                list3.add(rset.getInt(3));
//                list4.add(rset.getInt(4));
//                list5.add(rset.getInt(5));
//                list6.add(rset.getInt(6));
//                list7.add(rset.getString(7));
//                list8.add(rset.getInt(8));
//                list9.add(rset.getInt(9));
//                list10.add(rset.getInt(10));
//                list11.add(rset.getInt(11));
//                list12.add(rset.getInt(12));
//                list13.add(rset.getInt(13));
//                list14.add(rset.getInt(14));
//                list15.add(rset.getInt(15));
//                list16.add(rset.getInt(16));
//                list17.add(rset.getInt(17));
//                 list18.add(rset.getString(18));
//                list19.add(rset.getString(19));
             
                
//                listHash.put("plan_id", list1);
//               listHash.put("plan_no", list2);
//               listHash.put("on_time_hour", list3);
//               listHash.put("on_time_min", list4);
//               listHash.put("off_time_hour", list5);
//               listHash.put("off_time_min", list6);
//               listHash.put("mode", list7);
//               listHash.put("side1_green_time", list8);
//               listHash.put("side2_green_time",list9);
//               listHash.put("side3_green_time", list10);
//                listHash.put("side4_green_time", list11);
//                 listHash.put("side5_green_time", list12);
//                  listHash.put("side1_amber_time", list13);
//                   listHash.put("side2_amber_time", list14);
//                    listHash.put("side3_amber_time", list15);
//                     listHash.put("side4_amber_time", list16);
//                      listHash.put("side5_amber_time", list17);
//                       listHash.put("transferred_status", list18);
//                        listHash.put("remark", list19);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
      // check if the list is empty or not 
        // after adding an element 
        plan_check = list.isEmpty(); 
        if (plan_check == true)
        {
            System.out.println("The List is empty"); 
        }
        else
        {
             System.out.println(generatedKey);
            System.out.println("The List is not empty");
        }
        return list;
    }
      ///end
     
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
    
    public boolean mapNewPlanId(PlanDetails planDetails,int plan_update_id) throws SQLException {
        PreparedStatement pstmt;
        int rowAffected = 0;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" UPDATE junction_plan_map SET plan_id = "+plan_update_id+" "
                    + " where junction_plan_map_id = " +planDetails.getJunction_plan_map_id());
            rowAffected = pstmt.executeUpdate();   
            if (rowAffected > 0) {
                // Finally commit the connection.
                connection.commit();
            } else {
                throw new SQLException("All records were NOT plan id not Update in junction plan map.");
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return rowAffected > 0;
    }
    
   
        public boolean insertRecordMapNewPlanId(PlanDetails planDetails,int planCheck) {
      
         String insert_query = null;
         PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int generatedKey=0;
        int rowsAffected = 0;
        int plan_no=0;
         int plan_id =0;
        if(checkPlanNo(planDetails.getPlan_no())) {
          plan_no= getMaxPlanNo();
         plan_no=plan_no+1;
          plan_id = getMaxPlanId()+1;
            insert_query = "INSERT into plan_details(plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, "
                    + " side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, "
                    + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status,"
                    + " remark,plan_revision_no) "
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";    
            try {
                boolean autoCommit = connection.getAutoCommit();
                try {
                    connection.setAutoCommit(false);                

                        pstmt = connection.prepareStatement(insert_query,Statement.RETURN_GENERATED_KEYS);
                        pstmt.setInt(1, plan_id);
                        pstmt.setInt(2, plan_no);
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
                          if (rowsAffected != 0) {
           ResultSet rset = pstmt.getGeneratedKeys();
               
                if (rset.next()) {
                    generatedKey = rset.getInt(1);
                }
                if(generatedKey>0)
                {
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(" UPDATE junction_plan_map SET plan_id = "+generatedKey+" "
                    + " where junction_plan_map_id = " +planDetails.getJunction_plan_map_id());
                     rowsAffected = pstmt.executeUpdate();
                }
             }
                          
                    if (rowsAffected > 0) {
                        // Finally commit the connection.
                        connection.commit();
                        message = "Data has been update successfully.";
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
        
            
        } else {
            plan_id = getMaxPlanId()+1;
            insert_query = "INSERT into plan_details(plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, "
                    + " side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, "
                    + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status,"
                    + " remark,plan_revision_no) "
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";    
            try {
                boolean autoCommit = connection.getAutoCommit();
                try {
                    connection.setAutoCommit(false);                

                        pstmt = connection.prepareStatement(insert_query,Statement.RETURN_GENERATED_KEYS);
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
                          if (rowsAffected != 0) {
           ResultSet rset = pstmt.getGeneratedKeys();
               
                if (rset.next()) {
                    generatedKey = rset.getInt(1);
                }
                if(generatedKey>0)
                {
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(" UPDATE junction_plan_map SET plan_id = "+generatedKey+" "
                    + " where junction_plan_map_id = " +planDetails.getJunction_plan_map_id());
                     rowsAffected = pstmt.executeUpdate();
                }
             }
                          
                    if (rowsAffected > 0) {
                        // Finally commit the connection.
                        connection.commit();
                        message = "Data has been update successfully.";
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
    
    
    
    
    
       public List<String> getOffMin(String q, String OffHr) {
        List<String> list = new ArrayList<String>();
    String query = "select distinct off_time_min from plan_details where active='y' and off_time_hour=? order by off_time_min desc";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, OffHr);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String type = rset.getString("off_time_min");
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such off_time_min Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside Phase Data Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getOffHr(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct off_time_hour from plan_details where active='y' order by off_time_hour desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("off_time_hour");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such OffTime Hr exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside phaseDataModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }
    
    
    
     public List<String> getOnMin(String q, String OnHr) {
        List<String> list = new ArrayList<String>();
    String query = "select distinct on_time_min from plan_details where active='y' and on_time_hour=? order by on_time_min desc";
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, OnHr);
            ResultSet rset = pstmt.executeQuery();

            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String type = rset.getString("on_time_min");
                if (type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such on_time_min Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside Phase Data Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public List<String> getOnHr(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct on_time_hour from plan_details where active='y' order by on_time_hour desc";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("on_time_hour");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such OnTime Hr exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside phaseDataModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
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
                     +" where s.active='Y' and"
              
               + "  ORDER BY plan_no "
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
     
     
      public List<PlanDetails> showDataPhase(int lowerLimit, int noOfRowsToDisplay,String searchManufacturerName,String ontime_hr,String ontime_min,String offtime_hr,String offtime_min) {
        List<PlanDetails> list = new ArrayList<PlanDetails>();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";

            
       String query2="select plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min,"
               + " mode, side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time,"
               + " side1_amber_time, side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time,"
               + " transferred_status, remark"
                     +" from plan_details s"
                     +" where s.active='Y' "
              + " and IF('" + ontime_hr + "' = '', on_time_hour LIKE '%%',on_time_hour =?) "
                + " and IF('" + ontime_min + "' = '', on_time_min LIKE '%%',on_time_min =?) "
                + " and IF('" + offtime_hr + "'='',off_time_hour LIKE '%%',off_time_hour=?)"
                + " and IF('" + offtime_min + "'='',off_time_min LIKE '%%',off_time_min=?)"
               + "  ORDER BY plan_no "
                     + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
              pstmt.setString(1, ontime_hr);
            pstmt.setString(2, ontime_min);
            pstmt.setString(3, offtime_hr);
            pstmt.setString(4, offtime_min);
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
