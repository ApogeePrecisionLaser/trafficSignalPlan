/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.PhaseInfo;
import com.ts.junction.tableClasses.PlanInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author apogee
 */
public class PhaseInfoModel {
    
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
    
    public Map<Integer, Junction> getJunctionList() {
        Map<Integer, Junction> junctionList = new HashMap<Integer, Junction>();
        try {
            String query = "SELECT junction_id,program_version_no, junction_name, address1, address2, city_name, controller_model, no_of_sides, amber_time, "
                    + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                    + " side2_name, side3_name, side4_name, side5_name,file_no "
                    + " from junction AS j, city AS c "
                    + " WHERE c.city_id=j.city_id AND j.final_revision='VALID'";

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Junction junction = new Junction();
                int junction_id = rset.getInt("junction_id");
                int program_version_no = rset.getInt("program_version_no");
                junction.setJunction_id(junction_id);
                junction.setProgram_version_no(program_version_no);
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
                junctionList.put(junction_id, junction);
            }
        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
        return junctionList;
    }

    public int getJunctionID(String IMEINo) {
//        System.out.println(IMEINo);
        int junction_id = 0;
        String queryJunctionID = " SELECT * FROM junction WHERE imei_number = 'ABC' AND final_revision='VALID' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            //pstmt.setString(1, IMEINo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("junction_id");
                System.out.println("junction_id---" + junction_id);
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: registerModem() Error" + e);
        }
        return junction_id;
    }
    
    public int getJunctionID1(String junction_name) {
//        System.out.println(IMEINo);
        int junction_id = 0;
        String queryJunctionID = " SELECT * FROM junction WHERE junction_name = ? AND final_revision='VALID' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, junction_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("junction_id");
                System.out.println("junction_id---" + junction_id);
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: registerModem() Error" + e);
        }
        return junction_id;
    }

    public List<String> getCityName(String q, String state_name) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT c.city_name "
                + " FROM city AS c, state AS s "
                + " WHERE c.state_id=s.state_id AND state_name= ? "
                + " ORDER BY city_name ";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, state_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name = rset.getString("city_name");
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

    public Set<String> getJunctionName(String q) {
        Set<String> list = new HashSet<String>();
        PreparedStatement pstmt;
        String query = "SELECT junction_name "
                + " FROM junction "
                + " ORDER BY junction_name ";
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

    public int deleteRecord(int plan_info, int junctionId) {
        String junctionQuery = "UPDATE phase_info SET active = 'N' WHERE plan_no = " + plan_info + " AND junction_id = "+junctionId+" AND active = 'Y'";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            rowsAffected = connection.prepareStatement(junctionQuery).executeUpdate();
            if (rowsAffected > 0) {
                    connection.commit();
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

    public int getNoOfRows( int junctionId) {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from phase_info where junction_id ="+junctionId).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (SQLException e) {
            System.out.println("JunctionModel getNoOfRows() Error: " + e);
        } catch (NumberFormatException e) {
            System.out.println("JunctionModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
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

    public boolean insertRecord(PhaseInfo phaseInfo) {
        String phaseQuery = "INSERT INTO phase_info (phase_info_id,junction_id,plan_no,revision_no,phase_no,phase_time,green1,green2,green3,green4,green5,side13,side24,side5,left_green,padestrian_info,GPIO,active,remark)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
     int junction_id = getJunctionID1(phaseInfo.getJunction_name());
        String query = " SELECT MAX(phase_info_id) AS max_id FROM phase_info ";
        
        //String query1 = "SELECT junction_id FROM traffic_signals.junction where junction_name = ? order by program_version_no Desc Limit 1";
        
        int phase_info_id = 0;
        //int junction_id = 0;
        int count = 0, rowsAffected = 0;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        boolean errorOccured = false;
        boolean autoCommit = true;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                phase_info_id = rset.getInt("max_id");
            }            
        } catch (Exception e) {
            System.out.println("Junction Model insertRecord() error" + e);
        }

        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(phaseQuery);
            pstmt.setInt(1, (phase_info_id + 1));
            pstmt.setInt(2, junction_id);
            pstmt.setInt(3, phaseInfo.getPlanNo());
            pstmt.setInt(4, 0);
            pstmt.setInt(5, phaseInfo.getPhaseNo());
            pstmt.setInt(6, phaseInfo.getPhaseTime());
            pstmt.setInt(7, phaseInfo.getGreen1());
            pstmt.setInt(8, phaseInfo.getGreen2());
            pstmt.setInt(9, phaseInfo.getGreen3());
            pstmt.setInt(10, phaseInfo.getGreen4());
            pstmt.setInt(11, phaseInfo.getGreen5());
            pstmt.setInt(12, phaseInfo.getSide13());
            pstmt.setInt(13, phaseInfo.getSide24());
            pstmt.setInt(14, phaseInfo.getSide5());
            pstmt.setInt(15, phaseInfo.getLeft_green());
            pstmt.setInt(16, phaseInfo.getPadestrian_info());
            pstmt.setInt(17, phaseInfo.getGPIO());
            pstmt.setString(18, "Y");
            pstmt.setString(19, phaseInfo.getRemark());
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
                Logger.getLogger(JunctionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return !errorOccured;
    }

    public int updateRecord(PhaseInfo pi) {
        String query = null;
        PreparedStatement pstmt = null;
        int revision_no = getFinalProgramVersionNo(pi.getPhaseInfoId());
        String phaseQuery = "INSERT INTO phase_info (phase_info_id,junction_id,plan_no,revision_no,phase_no,phase_time,green1,green2,green3,green4,green5,side13,side24,side5,left_green,padestrian_info,GPIO,active,remark)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        
        String updateStatus = "UPDATE phase_info SET active='N' WHERE phase_info_id = ? AND revision_no = ? ";
        int junction_id = 0;
        if(pi.getJunction_id() == 0) {
            junction_id = getJunctionID1(pi.getJunction_name());
        } else {
            junction_id = pi.getJunction_id();
        }
        int rowsAffected = 0;
        try {
            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(phaseQuery);
              
            pstmt.setInt(1, pi.getPhaseInfoId());
            
            pstmt.setInt(2, junction_id);
            pstmt.setInt(3, pi.getPlanNo());
            pstmt.setInt(4, revision_no +1);
            pstmt.setInt(5, pi.getPhaseNo());
            pstmt.setInt(6, pi.getPhaseTime());
            pstmt.setInt(7, pi.getGreen1());
            pstmt.setInt(8, pi.getGreen2());
            pstmt.setInt(9, pi.getGreen3());
            pstmt.setInt(10, pi.getGreen4());
            pstmt.setInt(11, pi.getGreen5());
            pstmt.setInt(12, pi.getSide13());
            pstmt.setInt(13, pi.getSide24());
            pstmt.setInt(14, pi.getSide5());
            pstmt.setInt(15, pi.getLeft_green());
            pstmt.setInt(16, pi.getPadestrian_info());
            pstmt.setInt(17, pi.getGPIO());
            pstmt.setString(18, "Y");
            pstmt.setString(19, pi.getRemark());
            rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(updateStatus);
                    pstmt.setInt(1, pi.getPhaseInfoId());
                    pstmt.setInt(2, revision_no);
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        connection.commit();
                        message = "Record Updated successfully.";
                        msgBgColor = COLOR_OK;
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

    public List<PhaseInfo> showData(int lowerLimit, int noOfRowsToDisplay, int junctionId) {
        List<PhaseInfo> list = new ArrayList<PhaseInfo>();
        try {
            String query = "SELECT * from phase_info AS p, junction AS j "
                    + " WHERE p.junction_id = j.junction_id AND j.final_revision='VALID' AND p.junction_id = "+ junctionId
                    + " AND p.active = 'Y' "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PhaseInfo pi = new PhaseInfo();
                pi.setPhaseInfoId(rset.getInt("phase_info_id"));
                pi.setJunction_id(rset.getInt("junction_id"));
                pi.setJunction_name(rset.getString("junction_name"));
                pi.setGPIO(rset.getInt("GPIO"));
                pi.setGreen1(rset.getInt("green1"));
                pi.setGreen2(rset.getInt("green2"));
                pi.setGreen3(rset.getInt("green3"));
                pi.setGreen4(rset.getInt("green4"));
                pi.setGreen5(rset.getInt("green5"));
                pi.setLeft_green(rset.getInt("left_green"));
                pi.setPadestrian_info(rset.getInt("padestrian_info"));
                pi.setPhaseNo(rset.getInt("phase_no"));
                pi.setPhaseTime(rset.getInt("phase_time"));
                pi.setPlanNo(rset.getInt("plan_no"));
                pi.setSide13(rset.getInt("side13"));
                pi.setSide24(rset.getInt("side24"));
                pi.setSide5(rset.getInt("side5"));
                pi.setRemark(rset.getString("remark"));
                list.add(pi);
            }

        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
        return list;
    }

    public boolean checkImei(String imei_no) {
        boolean result = false;
        String query1 = " SELECT count(*) AS c FROM junction WHERE imei_no = '" + imei_no + "' AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("c") == 0 ? false : true;
            }
        } catch (Exception e) {
            System.out.println("Junction Model checkImei() error" + e);
        }
        return result;
    }

    public int getFinalProgramVersionNo(int phase_info_id) {
        int revision_no = 0;
        String query1 = " SELECT revision_no FROM phase_info WHERE phase_info_id = ? AND active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, phase_info_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                revision_no = rset.getInt("revision_no");
            }

        } catch (Exception e) {
            System.out.println("Junction Model getFinalProgramVersionNo() error" + e);
        }
        return revision_no;
    }

    public int getFinalPlanRevisionNo(int junction_id, int programVersionNo) {
        int getFinalPlanRevisionNo = 0;
        String query1 = " SELECT plan_revision_no FROM plan_info WHERE junction_id = ? AND program_version_no = ?  AND final_revision='VALID' LIMIT 1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, programVersionNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                getFinalPlanRevisionNo = rset.getInt("plan_revision_no");
            }

        } catch (Exception e) {
            System.out.println("Junction Model getFinalPlanRevisionNo() error" + e);
        }
        return getFinalPlanRevisionNo;
    }

    public int getNoOfPlans(int junction_id, int program_version_no) {
        int noOfplans = 0;
        try {
            ResultSet rset = connection.prepareStatement("select no_of_plans from junction WHERE junction_id = " + junction_id + " AND program_version_no = " + program_version_no).executeQuery();
            rset.next();
            noOfplans = Integer.parseInt(rset.getString(1));
            System.out.println(noOfplans);
        } catch (Exception e) {
            System.out.println("JunctionModel getNoOfPlans() Error: " + e);
        }
        return noOfplans;
    }

    public int getFinalSideRevisionNo(int junction_id, int programVersionNo) {
        int side_revision_no = 0;
        String query1 = " SELECT side_revision_no FROM slave_info WHERE junction_id = ? AND program_version_no = ? AND final_revision='VALID' LIMIT 1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, programVersionNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                side_revision_no = rset.getInt("side_revision_no");
            }

        } catch (Exception e) {
            System.out.println("Junction Model getFinalSideRevisionNo() error" + e);
        }
        return side_revision_no;
    }

    public int getNoOfSides(int junction_id, int program_version_no) {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select no_of_sides from junction WHERE junction_id = " + junction_id + " AND program_version_no = " + program_version_no).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("JunctionModel getNoOfSides() Error: " + e);
        }
        return noOfRows;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public int getValidPorogramVersionNo(int junction_id) {
        int plan_revision_no = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("SELECT program_version_no FROM junction WHERE junction_id= ?  AND final_revision = 'VALID' LIMIT 1");
            pstmt.setInt(1, junction_id);
            //pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            plan_revision_no = Integer.parseInt(rset.getString(1));
            System.out.println(plan_revision_no);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getValidPorogramVersionNo() Error: " + e);
        }
        return plan_revision_no;
    }
    
    
    public PlanInfo showPlanData(int junction_id, int program_version_no, int planNo) {
        PlanInfo planinfo = new PlanInfo();
        String query = " SELECT junction_name, plan_no, plan_revision_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, side1_green_time, "
                + " side2_green_time, side3_green_time, side4_green_time, side5_green_time, side1_amber_time, side2_amber_time, "
                + " side3_amber_time, side4_amber_time, side5_amber_time,pedestrian_time "
                + " FROM plan_info AS p, junction AS j "
                + " WHERE p.junction_id=j.junction_id "
                + " AND p.program_version_no= j.program_version_no"
                + " AND j.junction_id= ? AND j.program_version_no = ? AND p.final_revision='VALID'"
                + " AND p.plan_no = ?";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            pstmt.setInt(3, planNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                planinfo.setJunction_name(rset.getString("junction_name"));
                planinfo.setPlan_no(rset.getInt("plan_no"));
                planinfo.setPlan_revision_no(rset.getInt("plan_revision_no"));
                planinfo.setOn_time_hour(rset.getInt("on_time_hour"));
                planinfo.setOn_time_min(rset.getInt("on_time_min"));
                planinfo.setOff_time_hour(rset.getInt("off_time_hour"));
                planinfo.setOff_time_min(rset.getInt("off_time_min"));
                String mode = rset.getString("mode");
                planinfo.setMode(mode);
                planinfo.setSide1_green_time(rset.getInt("side1_green_time"));
                planinfo.setSide2_green_time(rset.getInt("side2_green_time"));
                planinfo.setSide3_green_time(rset.getInt("side3_green_time"));
                planinfo.setSide4_green_time(rset.getInt("side4_green_time"));
                planinfo.setSide5_green_time(rset.getInt("side5_green_time"));
                planinfo.setSide1_amber_time(rset.getInt("side1_amber_time"));
                planinfo.setSide2_amber_time(rset.getInt("side2_amber_time"));
                planinfo.setSide3_amber_time(rset.getInt("side3_amber_time"));
                planinfo.setSide4_amber_time(rset.getInt("side4_amber_time"));
                planinfo.setSide5_amber_time(rset.getInt("side5_amber_time"));
                planinfo.setPedestrian_time(rset.getInt("pedestrian_time"));
            }
        } catch (Exception e) {
            System.out.println("Error:PlanInfoModel-showData--- " + e);
        }
        return planinfo;
    }
    
    public PhaseInfo showPhaseData(int junctionId, int plan_no, int phase_no) {
        PhaseInfo phaseInfo = new PhaseInfo();
        try {
            String query = "SELECT * from phase_info AS p, junction AS j "
                    + " WHERE p.junction_id = j.junction_id AND j.final_revision='VALID' AND p.junction_id = "+ junctionId
                    + " AND p.active = 'Y' AND p.plan_no = " + plan_no + " AND p.phase_no = "+ phase_no;
            
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                phaseInfo.setPhaseInfoId(rset.getInt("phase_info_id"));
                phaseInfo.setJunction_id(rset.getInt("junction_id"));
                phaseInfo.setJunction_name(rset.getString("junction_name"));
                phaseInfo.setGPIO(rset.getInt("GPIO"));
                phaseInfo.setGreen1(rset.getInt("green1"));
                phaseInfo.setGreen2(rset.getInt("green2"));
                phaseInfo.setGreen3(rset.getInt("green3"));
                phaseInfo.setGreen4(rset.getInt("green4"));
                phaseInfo.setGreen5(rset.getInt("green5"));
                phaseInfo.setLeft_green(rset.getInt("left_green"));
                phaseInfo.setPadestrian_info(rset.getInt("padestrian_info"));
                phaseInfo.setPhaseNo(rset.getInt("phase_no"));
                phaseInfo.setPhaseTime(rset.getInt("phase_time"));
                phaseInfo.setPlanNo(rset.getInt("plan_no"));
                phaseInfo.setSide13(rset.getInt("side13"));
                phaseInfo.setSide24(rset.getInt("side24"));
                phaseInfo.setSide5(rset.getInt("side5"));
                phaseInfo.setRemark(rset.getString("remark"));
            }

        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
            return phaseInfo;
    }
    
    public int updatePreviousRecord(PhaseInfo pi) {
        String query = null;
        PreparedStatement pstmt = null;
        int revision_no = getFinalProgramVersionNo(pi.getPhaseInfoId());
        
        String updateStatus = "UPDATE phase_info SET green1 = "+pi.getGreen1()+", green2 = "+pi.getGreen2()+", "
                + " green3 = "+pi.getGreen3()+", green4 = "+pi.getGreen4()+", "
                + " green5 = "+pi.getGreen5()+" WHERE phase_info_id = ? AND revision_no = ? ";
        int junction_id = 0;
        if(pi.getJunction_id() == 0) {
            junction_id = getJunctionID1(pi.getJunction_name());
        } else {
            junction_id = pi.getJunction_id();
        }
        int rowsAffected = 0;
        try {
            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(updateStatus);
                    pstmt.setInt(1, pi.getPhaseInfoId());
                    pstmt.setInt(2, revision_no);
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        connection.commit();
                    } else {
                        throw new SQLException("Record NOT updated in junction.");
                    }
            } catch (SQLException sqlEx) {
                connection.rollback();
                System.out.println("JUnctionModel updateRecord() Error: " + message + " Cause: " + sqlEx.getMessage());
            } finally {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            }
        } catch (Exception e) {
            System.out.println("JUnctionModel updateRecord() Error: " + e);
        }
        return rowsAffected;
    }
    
   
    
}
