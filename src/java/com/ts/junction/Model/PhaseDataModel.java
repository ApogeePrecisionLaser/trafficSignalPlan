/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.JunctionPlanMap;
import com.ts.junction.tableClasses.PhaseData;
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
public class PhaseDataModel {
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
    
    public List<String> getOnOffTime(String q, String getsearchJunctionName ,String day, String date) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        int junction_id=0;
        junction_id=getJunctionId(getsearchJunctionName);
        if(date!=""){
        String date1[]= date.split("//");
        String from_date=date1[0];
        String to_date=date1[1];
            String query = "SELECT distinct on_time_hour, on_time_min, off_time_hour, off_time_min "
                + " FROM plan_details pd,junction_plan_map jpm,date_detail dd,junction j WHERE dd.date_detail_id=jpm.date_id "
                    + " and pd.plan_id=jpm.plan_id and j.junction_id=jpm.junction_id and dd.from_date='"+from_date+"' and dd.to_date='"+to_date+"' and j.junction_id='"+junction_id+"' "
                    + " and jpm.active = 'Y' and  dd.active='Y' and pd.active='Y' and j.final_revision='VALID'"
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
        }else if(day.equals("")){
             String query = "SELECT distinct on_time_hour, on_time_min, off_time_hour, off_time_min "
                + " FROM plan_details pd,junction_plan_map jpm,day_detail dd,junction j WHERE dd.day_detail_id=jpm.day_id "
                    + " and pd.plan_id=jpm.plan_id and j.junction_id=jpm.junction_id and dd.day='"+day+"' and j.junction_id='"+junction_id+"' "
                    + " and jpm.active = 'Y' and  dd.active='Y' and pd.active='Y' and j.final_revision='VALID'"
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
        
        } else {
//            String query = "SELECT distinct on_time_hour, on_time_min, off_time_hour, off_time_min "
//                + " FROM plan_details pd,junction_plan_map jpm,junction j WHERE  j.junction_id='"+junction_id+"'"
//                    + " and pd.plan_id=jpm.plan_id and j.junction_id=jpm.junction_id "
//                    + " and jpm.active = 'Y' and  pd.active='Y' and j.final_revision='VALID'"
//                + " ORDER BY plan_no ";
 String query = "SELECT on_time_hour, on_time_min, off_time_hour, off_time_min "
                + " FROM plan_details pd,junction_plan_map jpm,junction j WHERE  j.junction_id='"+junction_id+"'"
                    + " and pd.plan_id=jpm.plan_id and j.junction_id=jpm.junction_id "
                    + " and jpm.active = 'Y' and  pd.active='Y' and j.final_revision='VALID'"
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
        }
        return list;
    }
    
    public List<String> getDateTime(String q, String searchJanctionName) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT distinct from_date, to_date  FROM junction j,date_detail dd,junction_plan_map jpm,phase_map pm "
                + " Where j.junction_name='"+ searchJanctionName + "' and j.junction_id=jpm.junction_id and dd.date_detail_id=jpm.date_id "
                + " and jpm.junction_plan_map_id=pm.junction_plan_map_id  and jpm.active = 'Y' and dd.active = 'Y' and pm.active = 'Y'";
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
        public List<String> getDay(String q, String searchJanctionName, String date) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String date1[]= date.split("//");
        String fromdate=date1[0];
        String endDate=date1[1];
        String query = "SELECT distinct day FROM junction j,day_detail dd,junction_plan_map jpm,phase_map pm,date_detail ded "
                + " Where j.junction_name='"+ searchJanctionName + "'  and ded.from_date='"+fromdate+"' and ded.to_date='"+endDate+"' "
                + " and j.junction_id=jpm.junction_id and dd.day_detail_id=jpm.day_id and ded.date_detail_id=jpm.date_id "
                + " and jpm.junction_plan_map_id=pm.junction_plan_map_id  and jpm.active = 'Y' and dd.active = 'Y' and pm.active = 'Y' and ded.active='Y' ";
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
        public List<String> getsearchJunctionName(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT DISTINCT junction_name "
                + " FROM junction";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String junction_name = rset.getString("junction_name");               
                if (junction_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(junction_name);
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
    
    public int getMaxPhaseMapId() {
        int plan_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT Max(phase_map_id) FROM phase_map where active = 'Y' ");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_id = Integer.parseInt(rset.getString(1));
            System.out.println(plan_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return plan_id;
    }
    
    public int getMaxPhaseId() {
        int plan_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT Max(phase_info_id) as phase_info_id FROM phase_detail ");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_id = rset.getInt("phase_info_id");
            System.out.println(plan_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return plan_id;
    }
    
    public boolean insertRecord(PhaseData phaseData) {
        String insert_query = null;
        String insert_phase_query = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int max_phase_id = 0;
        int rowsAffected = 0;
        int program_version_no = 0;
            
            int plan_id = getPlanId(phaseData.getOn_time_hr(),phaseData.getOn_time_min(),phaseData.getOff_time_hr(),phaseData.getOff_time_min());
            int junction_id = getJunctionId(phaseData.getJunction_name());
            int date_id = getDateId(phaseData.getFrom_date(),phaseData.getTo_date());
            int day_id = getDayId(phaseData.getDay());
            int junction_plan_map_id = getJunctionPlanMapId(junction_id,plan_id,date_id, day_id);
            int phase_id = getPhaseId(phaseData.getSide13(), phaseData.getSide24());
            
            
            if(phase_id > 0) {
                
                insert_query = "INSERT into phase_map(phase_map_id,junction_plan_map_id, phase_id, order_no, remark) "
                    + " VALUES(?, ?, ?, ?, ?) "; 
            }  else {
                insert_phase_query = "INSERT into phase_detail(phase_info_id, phase_no,side13,side24) Values(?,?,?,?)";
                insert_query = "INSERT into phase_map(phase_map_id,junction_plan_map_id, phase_id, order_no, remark) "
                    + " VALUES(?, ?, ?, ?, ?) "; 
            }
            try {
                boolean autoCommit = connection.getAutoCommit();
                try {
                    connection.setAutoCommit(false);                
                        if(phase_id > 0) {
                            Junction junc = getJunctionDetail(junction_id);
                            updateRecordOfJunction(junc);
                            program_version_no = getProgramVersionNo(junction_id);
                            int phase_map_id = getMaxPhaseMapId()+1;
                            pstmt = connection.prepareStatement(insert_query);
                            pstmt.setInt(1, phase_map_id);
                            pstmt.setInt(2, junction_plan_map_id);
                            pstmt.setInt(3, phase_id);
                            pstmt.setInt(4, phaseData.getPhase_no() + 1);                        
                            pstmt.setInt(5, program_version_no);
                            rowsAffected = pstmt.executeUpdate();
                        } else {
                            max_phase_id = getMaxPhaseId()+1;
                            pstmt = connection.prepareStatement(insert_phase_query);
                            pstmt.setInt(1, max_phase_id);
                            pstmt.setInt(2, phaseData.getPhase_no());
                            pstmt.setInt(3, phaseData.getSide13());
                            pstmt.setInt(4, phaseData.getSide24());  
                            rowsAffected = pstmt.executeUpdate();
                            if(rowsAffected > 0) {
                                Junction junc = getJunctionDetail(junction_id);
                                updateRecordOfJunction(junc);
                                program_version_no = getProgramVersionNo(junction_id);
                                int phase_map_id = getMaxPhaseMapId()+1;
                                pstmt1 = connection.prepareStatement(insert_query);
                                pstmt1.setInt(1, phase_map_id);
                                pstmt1.setInt(2, junction_plan_map_id);
                                pstmt1.setInt(3, max_phase_id);
                                pstmt1.setInt(4, phaseData.getPhase_no() + 1);                        
                                pstmt1.setInt(5, program_version_no);
                                rowsAffected = pstmt1.executeUpdate();                                
                            }
                        }    
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
 public int getSidesOfJunction(String junction_name_select) {
        int no_of_sides = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT no_of_sides FROM junction where junction_name = '"+junction_name_select+ "'"
                    + " and final_revision ='VALID'  Order by no_of_sides");
//            pstmt.setInt(1, no_of_sides);
            
            ResultSet rset = pstmt.executeQuery();
            rset.next();
           no_of_sides= Integer.parseInt(rset.getString(1));
            System.out.println(no_of_sides);
        } catch (Exception e) {
            System.out.println("PlanDataModel getPlanId() Error: " + e);
        }
        return no_of_sides;
    }
   public String getModeName(String on_off_time_select) {
        String mode_name= "";
        PreparedStatement pstmt;
        String time[]=on_off_time_select.split("-");
        String on_time[]=time[0].split(":");
        String on_time_hour=on_time[0];
        String on_time_min=on_time[1];
        String off_time[]=time[1].split(":");
        String off_time_hour=off_time[0];
        String off_time_min=off_time[1];
        
        try {
            pstmt = connection.prepareStatement(" SELECT mode FROM plan_details where on_time_hour = '"+on_time_hour+ "' and on_time_min='" +on_time_min+"' "
                    + "and off_time_hour='"+off_time_hour+"' and off_time_min='"+off_time_min+"'"
                    + " and active='Y'  Order by plan_no");
//            pstmt.setInt(1, no_of_sides);
            
            ResultSet rset = pstmt.executeQuery();
            rset.next();
           mode_name= rset.getString(1);
            System.out.println(mode_name);
        } catch (Exception e) {
            System.out.println("PlanDataModel getPlanId() Error: " + e);
        }
        return mode_name;
    }
//  public int getModeOfJunction(String on_off_time_select) {
//        int no_of_sides = 0;
//        PreparedStatement pstmt;
//        String mode_name=getModeName(on_off_time_select);
//        try {
//            pstmt = connection.prepareStatement(" SELECT no_of_sides FROM junction where junction_name = '"+on_off_time_select+ "'"
//                    + " and final_revision ='VALID'  Order by no_of_sides");
////            pstmt.setInt(1, no_of_sides);
//            
//            ResultSet rset = pstmt.executeQuery();
//            rset.next();
//           no_of_sides= Integer.parseInt(rset.getString(1));
//            System.out.println(no_of_sides);
//        } catch (Exception e) {
//            System.out.println("PlanDataModel getPlanId() Error: " + e);
//        }
//        return no_of_sides;
//    }
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
    
    private int getPhaseId(int side13, int side24) {
        int phase_id = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT phase_info_id FROM phase_detail where side13 = ? "
                    + " and side24 = ? and active ='Y' ");
            pstmt.setInt(1, side13);
            pstmt.setInt(2, side24);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            phase_id = Integer.parseInt(rset.getString(1));
            System.out.println(phase_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return phase_id ;
    }
    
    
    private int getJunctionPlanMapId(int junction_id, int plan_id, int date_id, int day_id) {
        int junction_plan_map_id = 0;
        PreparedStatement pstmt;
        try {
            if(date_id == 0 && day_id == 0) {
                pstmt = connection.prepareStatement(" SELECT junction_plan_map_id FROM junction_plan_map where "
                    + " plan_id = ? and junction_id = ? and active = 'Y'");
                pstmt.setInt(1, plan_id);
                pstmt.setInt(2, junction_id);
                ResultSet rset = pstmt.executeQuery();
                while(rset.next()) {
                    junction_plan_map_id = rset.getInt(1);
                }
            }
            else if(date_id == 0) {
                pstmt = connection.prepareStatement(" SELECT junction_plan_map_id FROM junction_plan_map where "
                    + "day_id = ? and plan_id = ? and junction_id = ? and active = 'Y'");
                pstmt.setInt(1, day_id);
                pstmt.setInt(2, plan_id);
                pstmt.setInt(3, junction_id);
                ResultSet rset = pstmt.executeQuery();
                while(rset.next()) {
                    junction_plan_map_id = rset.getInt(1);
                }
            } else if(day_id == 0) {
                pstmt = connection.prepareStatement(" SELECT junction_plan_map_id FROM junction_plan_map where "
                    + "date_id = ? and plan_id = ? and junction_id = ? and active = 'Y'");
                pstmt.setInt(1, date_id);
                pstmt.setInt(2, plan_id);
                pstmt.setInt(3, junction_id);
                ResultSet rset = pstmt.executeQuery();
                while(rset.next()) {
                    junction_plan_map_id = rset.getInt(1);
                }
            } 
            
            
            
            System.out.println(day_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return junction_plan_map_id ;
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
    
    public Junction getJunctionDetail(int junction_id) {
        Junction junction = new Junction();
        try {
            String query = "SELECT junction_id, junction_name, address1, address2, city_name, controller_model, no_of_sides, amber_time, "
                    + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                    + " side2_name, side3_name, side4_name, side5_name,file_no, program_version_no,remark "
                    + " from junction AS j, city AS c "
                    + " WHERE c.city_id=j.city_id AND j.final_revision='VALID' and j.junction_id = " +junction_id;
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                
                junction.setJunction_id(rset.getInt("junction_id"));
                junction.setJunction_name(rset.getString("junction_name"));
                junction.setAddress1(rset.getString("address1"));
                junction.setAddress2(rset.getString("address2"));
                junction.setState_name(getStateName());
                junction.setCity_name(rset.getString("city_name"));
                junction.setController_model(rset.getString("controller_model"));
                junction.setNo_of_sides(rset.getInt("no_of_sides"));
                junction.setAmber_time(rset.getInt("amber_time"));
                junction.setFlash_rate(rset.getInt("flash_rate"));
                junction.setNo_of_plans(rset.getInt("no_of_plans"));
                junction.setMobile_no(rset.getString("mobile_no"));
                junction.setSim_no(rset.getString("mobile_no"));
                junction.setImei_no(rset.getString("imei_no"));
                junction.setInstant_green_time(rset.getInt("instant_green_time"));
                String pedestrian = rset.getString("pedestrian");
                junction.setPedestrian(pedestrian.equals("Y") ? "YES" : "NO");
                junction.setPedestrian_time(rset.getInt("pedestrian_time"));
                junction.setSide1_name(rset.getString("side1_name"));
                junction.setSide2_name(rset.getString("side2_name"));
                junction.setSide3_name(rset.getString("side3_name"));
                junction.setSide4_name(rset.getString("side4_name"));
                junction.setSide5_name(rset.getString("side5_name"));
                junction.setFile_no(rset.getInt("file_no"));
                junction.setProgram_version_no(rset.getInt("program_version_no"));
                junction.setRemark(rset.getString("remark"));
                
            }

        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
        return junction;
    }
    
    public int getCityID(String city_name) {
        int city_id = 0;
        String query = "SELECT city_id "
                + " FROM city WHERE city_name= ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                city_id = rset.getInt("city_id");
            }
        } catch (Exception ex) {
            System.out.println("JunctionModel getCityID() Error: " + ex);
        }
        return city_id;
    }
    
    public int updateRecordOfJunction(Junction junction) {
        String query = null;
        PreparedStatement pstmt = null;
        int program_version_no = getProgramVersionNo(junction.getJunction_id());
        query = "INSERT INTO junction (junction_id, junction_name, address1, address2, city_id, controller_model, no_of_sides, amber_time, "
                + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                + " side2_name, side3_name, side4_name, side5_name, program_version_no, transferred_status, file_no, remark, created_by) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        String updateStatus = "UPDATE junction SET final_revision='EXPIRED' WHERE junction_id = ? AND program_version_no = ? ";
        int rowsAffected = 0;
     
        try {
            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, (junction.getJunction_id()));
                pstmt.setString(2, junction.getJunction_name());
                pstmt.setString(3, junction.getAddress1());
                pstmt.setString(4, junction.getAddress2());
                pstmt.setInt(5, getCityID(junction.getCity_name()));
                pstmt.setString(6, junction.getController_model());
                pstmt.setInt(7, junction.getNo_of_sides());
                pstmt.setInt(8, junction.getAmber_time());
                pstmt.setInt(9, junction.getFlash_rate());
                pstmt.setInt(10, junction.getNo_of_plans());
                pstmt.setString(11, junction.getMobile_no());
                pstmt.setString(12, junction.getSim_no());
                pstmt.setString(13, junction.getImei_no());
                pstmt.setInt(14, junction.getInstant_green_time());
                pstmt.setString(15, junction.getPedestrian().equals("NO")?"N":"Y");
                pstmt.setInt(16, junction.getPedestrian_time());
                pstmt.setString(17, junction.getSide1_name());
                pstmt.setString(18, junction.getSide2_name());
                pstmt.setString(19, junction.getSide3_name());
                pstmt.setString(20, junction.getSide4_name());
                pstmt.setString(21, junction.getSide5_name());
                pstmt.setInt(22, program_version_no + 1);
                pstmt.setString(23, "NO");
                pstmt.setInt(24, junction.getFile_no());
                pstmt.setString(25, junction.getRemark());
                pstmt.setInt(26, 1);
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(updateStatus);
                    pstmt.setInt(1, junction.getJunction_id());
                    pstmt.setInt(2, program_version_no);
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        

                    } else {
                        throw new SQLException("Record NOT updated in junction.");
                    }
                } else {
                    throw new SQLException("Record NOT saved in junction.");
                }
            } catch (SQLException sqlEx) {
                connection.rollback();
                message = "Could NOT save data , some error.";
                msgBgColor = COLOR_ERROR;
                System.out.println("JUnctionModel updateRecord() Error: " + message + " Cause: " + sqlEx.getMessage());
            } finally {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            }
        } catch (Exception e) {
            System.out.println("JUnctionModel updateRecord() Error: " + e);
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
    
    private int getProgramVersionNo(int junction_id) {
        int program_version_no = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT program_version_no FROM junction where junction_id = ? "
                    + "and final_revision ='VALID'  Order by junction_id");
            pstmt.setInt(1, junction_id);
            
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            program_version_no = rset.getInt(1);
            System.out.println(junction_id);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getPlanId() Error: " + e);
        }
        return program_version_no;
    }
    
    public String getStateName() {
        String query = "SELECT state_name "
                + " FROM junction as j, city as c, district as d, state as s "
                + " WHERE j.city_id = c.city_id and "
                + " c.district_id = d.district_id and "
                + " d.state_id = s.state_id GROUP BY state_name ";
        String state_name = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                state_name = rset.getString("state_name");
            }
        } catch (Exception e) {
            System.out.println("Error:Delete:JunctionModel-- " + e);
        }
        return state_name;
    }
    
}
