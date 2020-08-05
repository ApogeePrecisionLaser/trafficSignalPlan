/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.tcpServer;

import com.ts.junction.tableClasses.History;
import com.ts.junction.tableClasses.SlaveInfo;
import com.ts.log.tableClasses.SeverityCase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import java.util.*;
/**
 *
 * @author Shruti
 */
public class ClientResponderModel extends HttpServlet {
    

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("ClientResponderModel setConnection() Error: " + e);
        }
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    public void closeConnection() {
        try{
            this.connection.close();
        }catch(Exception ex){
            System.out.println("ERROR: on closeConnection() in ClientResponderModel : " + ex);
        }
    }

    public List<Integer> getPlanTiming(int planNo, int junctionID) {
        List<Integer> planTime = new ArrayList<Integer>();
        String query = "SELECT on_time_hour, on_time_min, off_time_hour, off_time_min FROM plan_info WHERE junction_id= ? AND plan_no= ? AND final_revision='VALID'";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, planNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                int on_time_hour = rset.getInt("on_time_hour");
                int on_time_min = rset.getInt("on_time_min");
                int off_time_hour = rset.getInt("off_time_hour");
                int off_time_min = rset.getInt("off_time_min");
                planTime.add(on_time_hour);
                planTime.add(on_time_min);
                planTime.add(off_time_hour);
                planTime.add(off_time_min);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
        }
        return planTime;
    }
    
    public int getPhaseNumber(int planNo, int junctionID, int program_version_no) {
        int phaseNo = 0;
        String query = "SELECT count(pi.phase_no) AS PHASE_NO FROM plan_info as pli left join phase_info as pi ON pli.junction_id = pi.junction_id AND pli.plan_no = pi.plan_no where pli.junction_id = ? AND pli.plan_no = ? AND pli.final_revision = 'VALID' AND active = 'Y';";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, planNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                phaseNo = rset.getInt("PHASE_NO");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
        }
        return phaseNo;
    }

    public int getNoOfSides(int junction_id, int program_version_no) {
        int noOfSides = 0;

        String query = "SELECT no_of_sides FROM junction WHERE junction_id= ? AND program_version_no= ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                noOfSides = rset.getInt("no_of_sides");
                System.out.println(noOfSides);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return noOfSides;
    }

    public int getCityName(int junctionID) {
        int cityID = 0;
        String query = " SELECT c.city_id FROM city AS c, junction As j WHERE j.city_id=c.city_id AND junction_id= ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                cityID = rset.getInt("c.city_id");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getCityName() Error: " + e);
        }
        return cityID;
    }

    public List<Integer> getCitySunriseSunset(int month, int cityID) {
        List<Integer> risesetList = new ArrayList<Integer>();
        Integer sunriseHrs = 0, sunriseMin = 0, sunsetHrs = 0, sunsetMin = 0;
        String query = " SELECT sunrise_hr, sunrise_min, sunset_hr, sunset_min FROM jn_rise_set_time "
                + " WHERE SUBSTRING(date, 6, 2) = ? AND city_id = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, month);
            pstmt.setInt(2, cityID);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                sunriseHrs = rset.getInt("sunrise_hr");
                sunriseMin = rset.getInt("sunrise_min");
                sunsetHrs = rset.getInt("sunset_hr");
                sunsetMin = rset.getInt("sunset_min");
                risesetList.add(sunriseHrs);
                risesetList.add(sunriseMin);
                risesetList.add(sunsetHrs);
                risesetList.add(sunsetMin);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getCitySunriseSunset() Error: " + e);
        }
        return risesetList;
    }

    public String getJunctionName(int junctionID, int program_version_no) {
        String query = " SELECT junction_name FROM junction WHERE junction_id= ? AND program_version_no = ?";
        String junction_name = "";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_name = rset.getString("junction_name");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getJunctionName() Error: " + e);
        }
        return junction_name;
    }

    public String getSideName(int sideNo, int junctionID, int program_version_no) {
        String query = "SELECT side" + sideNo + "_name FROM junction WHERE junction_id = ? AND program_version_no= ? ";
        String sideNames = "";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                sideNames = rset.getString("side" + sideNo + "_name");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getSideName() Error: " + e);
        }
        return sideNames;
    }
    
    public int getSideId(int sideNo, int junctionID, int program_version_no) {
        String query = "SELECT side_detail_id FROM side_detail WHERE junction_id = ? AND side_no= ? ";
        int side_id = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, sideNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                side_id = rset.getInt("side_detail_id");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getSideName() Error: " + e);
        }
        return side_id;
    }

    public int getPlanGreenTime(int sideNo, int planNo, int junctionID, int program_version_no) {
        String query = "SELECT side" + sideNo + "_green_time FROM plan_info WHERE junction_id= ? AND program_version_no = ? AND plan_no= ? AND final_revision='VALID' ";
        int plan_green_time = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            pstmt.setInt(3, planNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                plan_green_time = rset.getInt("side" + sideNo + "_green_time");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanGreenTime() Error: " + e);
        }
        return plan_green_time;
    }

    public String getPlanMode(int planNo, int junctionID, int program_version_no) {
        String query = "SELECT mode FROM plan_info WHERE junction_id= ? AND program_version_no = ? AND plan_no= ? AND final_revision='VALID'";
        String signal_mode = null;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            pstmt.setInt(3, planNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                signal_mode = rset.getString("mode");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanMode() Error: " + e);
        }
        return signal_mode;
    }

    public List<Integer> getPlanNo(int junction_id) {
        List<Integer> planNo = new ArrayList<Integer>();

        String query = "SELECT plan_no FROM plan_info WHERE junction_id= ? AND final_revision='VALID' ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                int planNum = rset.getInt("plan_no");
                System.out.println(planNum);
                planNo.add(planNum);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanNo() Error: " + e);
        }
        return planNo;
    }

    public int getPlanAmberTime(int sideNo, int planNo, int junctionID, int program_version_no) {
        String query = "SELECT side" + sideNo + "_amber_time FROM plan_info WHERE junction_id= ? AND program_version_no = ? AND plan_no= ? AND final_revision='VALID'";
        int plan_amber_time = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            pstmt.setInt(3, planNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                plan_amber_time = rset.getInt("side" + sideNo + "_amber_time");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanAmberTime() Error: " + e);
        }
        return plan_amber_time;
    }

    public int updateFileNo(int junctionID, int file_no, int program_version_no) {
        String query = null;
        PreparedStatement pstmt = null;
        query = "UPDATE junction SET file_no= ? WHERE junction_id = ? AND program_version_no = ? ";
        int rowsAffected = 0;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, file_no);
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, program_version_no);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException sqlEx) {
            System.out.println("ClientResponderModel updateFileNo() Error: " + sqlEx.getMessage());
        }
        return rowsAffected;
    }

    public boolean insertRecord(History history) {
        int rowsReturned = 0;
        if (checkJunctionHistory(history.getIp_address())) {
            updateRecord(history);
        }
        String junctionQuery = " INSERT INTO log_history(ip_address, port, login_timestamp_date, login_timestamp_time, status, junction_id, program_version_no) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement pstmt=null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());
        boolean errorOccured = false;
        try {
            pstmt = connection.prepareStatement(junctionQuery);
            pstmt.setString(1, history.getIp_address());
            pstmt.setInt(2, history.getPort());
            pstmt.setDate(3, sqltDate);
            pstmt.setTime(4, sqltTime);
            pstmt.setString(5, history.isStatus() == false ? "Y" : "N");
            pstmt.setInt(6, history.getJunction_id());
            pstmt.setInt(7, history.getProgram_version_no());
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0) {
                System.out.println("ClientResponderModel insertRecord() Record inserted successfully ");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel insertRecord() Error: " + e);
        }
        return !errorOccured;
    }

    public boolean updateRecord(History history) {
        String junctionQuery = " UPDATE log_history SET status= ?, logout_timestamp_date= ?, "
                + " logout_timestamp_time= ? WHERE ip_address= ?  AND logout_timestamp_date is null AND logout_timestamp_time is null  ";
        PreparedStatement pstmt = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        System.out.println(junctionQuery);
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());

        boolean errorOccured = false;
        int rowsReturned = 0;
        try {
            pstmt = connection.prepareStatement(junctionQuery);
            boolean status = history.isStatus();
            pstmt.setString(1, "N");
            pstmt.setDate(2, sqltDate);
            pstmt.setTime(3, sqltTime);
            pstmt.setString(4, history.getIp_address());
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0) {
                System.out.println("ClientResponderModel update() Record updated successfully ");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel update() Error: " + e);
        }
        return !errorOccured;
    }

    public boolean checkJunctionHistory(String ip) {
        String query = " SELECT Count(*) FROM log_history WHERE ip_address= ? AND logout_timestamp_date is null AND logout_timestamp_time is null  ";

        int rowsReturned = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ip);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                rowsReturned = rset.getInt(1);
            }
            rset.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel checkJunctionHistory() Error: " + e);
        }
        return rowsReturned > 0 ? true : false;
    }

    public boolean updateErrorStateOfLoggedInJunctions() {
        String junctionQuery = " UPDATE log_history SET status= ?, logout_timestamp_date= ?, logout_timestamp_time= ?, error_state= ? "
                + " WHERE status = 'Y' AND logout_timestamp_date is null AND logout_timestamp_time is null  ";
        PreparedStatement pstmt = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        System.out.println(junctionQuery);
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            Logger.getLogger(ClientResponderModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());

        boolean errorOccured = false;
        int rowsReturned = 0;
        try {
            pstmt = connection.prepareStatement(junctionQuery);
            pstmt.setString(1, "N");
            pstmt.setDate(2, sqltDate);
            pstmt.setTime(3, sqltTime);
            pstmt.setString(4, "Y");
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0) {
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Error state of all last logged in junctions updated successfully @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel updateErrorStateOfLoggedInJunctions() Error: " + e);
        }
        return !errorOccured;
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from log_history WHERE logout_timestamp_time is null ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public int getNoOfRowsInShowAll(String ipAddress, int port) {
        int noOfRows = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement("select count(*) from log_history WHERE ip_address= ? AND port= ? ");
            pstmt.setString(1, ipAddress);
            pstmt.setInt(2, port);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfRowsInShowAll() Error: " + e);
        }
        return noOfRows;
    }

    public List<History> showDetails(String ipAddress, int port, int lowerLimit, int noOfRowsToDisplay) {
        List<History> list = new ArrayList<History>();
        try {
            String query = " SELECT ip_address, port, login_timestamp_date, login_timestamp_time, status, logout_timestamp_date, "
                    + " logout_timestamp_time , status FROM  log_history WHERE ip_address= ? AND port= ? ORDER BY login_timestamp_date DESC, "
                    + " login_timestamp_time DESC LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
            PreparedStatement pstmt;
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ipAddress);
            pstmt.setInt(2, port);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History log_history = new History();
                log_history.setIp_address(rset.getString("ip_address"));
                log_history.setPort(rset.getInt("port"));
                log_history.setLogin_timestamp_date(rset.getString("login_timestamp_date"));
                log_history.setLogin_timestamp_time(rset.getString("login_timestamp_time"));
                log_history.setStatus(rset.getString("status").equals("Y") ? true : false);
                String logout_timestamp_date = rset.getString("logout_timestamp_date");
                String logout_timestamp_time = rset.getString("logout_timestamp_time");
                if (logout_timestamp_date == null) {
                    logout_timestamp_date = "";
                }
                if (logout_timestamp_time == null) {
                    logout_timestamp_time = "";
                }
                log_history.setLogout_timestamp_date(logout_timestamp_date);
                log_history.setLogout_timestamp_time(logout_timestamp_time);
                list.add(log_history);
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-showDetails--- " + e);
        }
        return list;
    }

    public List<History> showLoggedInJunctionDetails() {
        List<History> list = new ArrayList<History>();
        try {
//            String query = " SELECT distinct j.junction_id, junction_name, city_name, ip_address, port, j.program_version_no, j.file_no, "
//                    + " IF(synchronization_status is null or synchronization_status = '' ,'N',synchronization_status) AS synchronization_status ,"
//                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(application_hr,':',application_min,' ',application_date,'-',application_month,'-',application_year)) AS application_last_time, "
//                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(junction_hr,':',junction_min,' ',junction_date,'-',junction_month,'-',junction_year)) AS junction_last_time"
//                    + " FROM  junction AS j LEFT JOIN time_synchronization_detail AS tsd"
//                    + "  ON j.junction_id = tsd.junction_id AND tsd.final_revision='VALID' AND j.program_version_no=tsd.program_version_no "
//                    + ", log_history AS lh, city AS c  "
//                    + " WHERE j.junction_id=lh.junction_id AND j.city_id=c.city_id AND logout_timestamp_time is null AND j.final_revision='VALID' "
//                    + "ORDER BY login_timestamp_date DESC, login_timestamp_time DESC ";
             //multiple rows show solution query
              String query = " SELECT max(j.timestamp), j.junction_id, junction_name, city_name, ip_address, port, j.program_version_no, j.file_no, "
                    + " IF(synchronization_status is null or synchronization_status = '' ,'N',synchronization_status) AS synchronization_status ,"
                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(application_hr,':',application_min,' ',application_date,'-',application_month,'-',application_year)) AS application_last_time, "
                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(junction_hr,':',junction_min,' ',junction_date,'-',junction_month,'-',junction_year)) AS junction_last_time"
                    + " FROM  junction AS j LEFT JOIN time_synchronization_detail AS tsd"
                    + "  ON j.junction_id = tsd.junction_id AND tsd.final_revision='VALID' AND j.program_version_no=tsd.program_version_no "
                    + ", log_history AS lh, city AS c  "
                    + " WHERE j.junction_id=lh.junction_id AND j.city_id=c.city_id AND logout_timestamp_time is null AND j.final_revision='VALID' "
                    + " group by j.junction_id ORDER BY login_timestamp_date DESC, login_timestamp_time DESC ";
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                History log_history = new History();
                log_history.setJunction_id(rset.getInt("junction_id"));
                log_history.setProgram_version_no(rset.getInt("program_version_no"));
                log_history.setFileNo(rset.getInt("file_no"));
                log_history.setJunction_name(rset.getString("junction_name"));
                log_history.setCity_name(rset.getString("city_name"));
                log_history.setIp_address(rset.getString("ip_address"));
                log_history.setPort(rset.getInt("port"));
                log_history.setApplication_last_time_set(rset.getString("application_last_time"));
                log_history.setJunction_last_time_set(rset.getString("junction_last_time"));
                log_history.setTime_synchronization_status(rset.getString("synchronization_status"));
                list.add(log_history);
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-showLoggedInJunctionDetails--- " + e);
        }
        return list;
    }
    
    
    
    public List<History> showDataBean() {
        List<History> list = new ArrayList<History>();
        String revision="VALID";
        int rev = 3;
        String query = " select junction_id,junction_name,no_of_sides,no_of_plans,latitude,longitude,path from junction where final_revision='VALID'";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History bean = new History();
                bean.setLatitude(rset.getString("latitude"));
                bean.setLongitude(rset.getString("longitude"));
                bean.setJunction_name(rset.getString("junction_name"));
                bean.setNo_of_sides(rset.getInt("no_of_sides"));
                bean.setNo_of_plans(rset.getInt("no_of_plans"));
                int junction_id=rset.getInt("junction_id");
                bean.setJunction_id(junction_id);
                String active= getactiveJunction(junction_id);
                if(active.equals("No")){
                active = "NO";
                bean.setActive(active);
                }else{
                active="YES";    
                bean.setActive(active);
                }
               // bean.setActive(getactiveJunction(junction_id));
                bean.setPath(rset.getString("path"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
    
    public List<History> showDataBean1() {
        List<History> list = new ArrayList<History>();
        String revision="VALID";
        int rev = 3;
        String query = " select latitude,longitude from junction_side_coordinates where junction_id=13";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History bean = new History();
                bean.setLatitude(rset.getString("latitude"));
                bean.setLongitude(rset.getString("longitude"));
                //bean.setPath(rset.getString("path"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
    
    public List<History> showDataBean2(int side1,int side2,int side3,int side4,String side1Name,String side2Name,String side3Name,String side4Name) {
        List<History> list = new ArrayList<History>();
        String revision="VALID";
        String red_image="red_light.png";
        int rev = 3;
        String query = " select latitude,longitude,side_name,img_name from junction_side_coordinates where junction_id=13";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History bean = new History();
                bean.setLatitude(rset.getString("latitude"));
                bean.setLongitude(rset.getString("longitude"));
                String side_name=rset.getString("side_name");
                if(side_name.equals(side1Name) && side1==1)
                {
                bean.setImage(red_image);    
                }
//                else{
//                bean.setImage(rset.getString("img_name"));
//                }
                else if(side_name.equals(side2Name) && side2==1)
                {
                bean.setImage(red_image);    
                }
//                else{
//                bean.setImage(rset.getString("img_name"));
//                }
                else if(side_name.equals(side3Name) && side3==1)
                {
                bean.setImage(red_image);    
                }
//                else{
//                bean.setImage(rset.getString("img_name"));
//                }
               else if(side_name.equals(side4Name) && side4==1)
                {
                bean.setImage(red_image);    
                }else{
                bean.setImage(rset.getString("img_name"));
                }
                               
             
                //bean.setPath(rset.getString("path"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
    
    public String showDataBeanForJunction(String latitude,String longitude) {
        List<History> list = new ArrayList<History>();
        String junction_list="";
        int rev = 3;
        String query = " select junction_id,junction_name,no_of_sides,no_of_plans from junction where final_revision='VALID' and latitude="+latitude+" and longitude="+longitude;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History bean = new History();           
                String junction_name = rset.getString("junction_name");
                String   no_of_sides = String.valueOf(rset.getInt("no_of_sides"));
                String no_of_plans = String.valueOf(rset.getInt("no_of_plans"));
                String junction_id = String.valueOf(rset.getInt("junction_id"));               
                junction_list = junction_name+","+no_of_sides+","+no_of_plans+","+junction_id;
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return junction_list;
    }
    
    public List<History> showDataBeanForSingleJunction(int junction_id) {
        List<History> list = new ArrayList<History>();
        String revision="VALID";
        int rev = 3;
        String query = " select side1_latlon as ColValue from junction where junction_id ="+junction_id+" and final_revision='VALID'"+
                        " union all select side2_latlon as ColValue from junction where junction_id ="+junction_id+" and final_revision='VALID'" +
                        " union all select side3_latlon as ColValue from junction where junction_id ="+junction_id+" and final_revision='VALID'" +
                        " union all select side4_latlon as ColValue from junction where junction_id ="+junction_id+" and final_revision='VALID'";
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                History bean = new History();
                String latlon = rset.getString("ColValue");
                String latlonArray[] = latlon.split(",");
                bean.setLatitude(latlonArray[0]);
                bean.setLongitude(latlonArray[1]);            
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }

    public List<Integer> getJunctionID() {
        int junction_id = 0;
        List<Integer> list = new ArrayList<Integer>();
        String query = "SELECT j.junction_id FROM  junction AS j, log_history AS lh, city AS c "
                + " WHERE j.junction_id=lh.junction_id AND j.city_id=c.city_id AND logout_timestamp_time is null "
                + " ORDER BY login_timestamp_date DESC, login_timestamp_time DESC ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("j.junction_id");
                list.add(junction_id);
            }
        } catch (Exception e) {
            System.out.println("Error: clientResponderModel getJunctionID " + e);
        }
        return list;
    }

    public boolean checkJunctionId(int junctionID) {
        boolean result = false;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT COUNT(*) FROM junction WHERE junction_id= ? AND final_revision='VALID'");
            pstmt.setInt(1, junctionID);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            result = rset.getInt(1) > 0 ? true : false;
            System.out.println(result);
        } catch (Exception e) {
            result = false;
            System.out.println("ClientResponderModel checkProgramVersionNo() Error: " + e);
        }
        return result;
    }

    public List<History> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<History> list = new ArrayList<History>();
        try {
            String query = " SELECT ip_address, port, login_timestamp_date, login_timestamp_time, status, logout_timestamp_date, "
                    + " logout_timestamp_time FROM  log_history WHERE logout_timestamp_time is null ORDER BY login_timestamp_date DESC, "
                    + " login_timestamp_time DESC LIMIT " + lowerLimit + "," + noOfRowsToDisplay;
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                History log_history = new History();
                log_history.setIp_address(rset.getString("ip_address"));
                log_history.setPort(rset.getInt("port"));
                log_history.setLogin_timestamp_date(rset.getString("login_timestamp_date"));
                log_history.setLogin_timestamp_time(rset.getString("login_timestamp_time"));
                log_history.setStatus(rset.getString("status").equals("Y") ? true : false);
                list.add(log_history);
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-showData--- " + e);
        }
        return list;
    }

    public boolean checkJunctionLastSynchronisation(String ipAddress, String port, int junctionID, int program_version_no, boolean testRequest) {
        boolean result = false;
        try {
            String query1 = " SELECT COUNT(*) FROM log_history "
                    + " WHERE CONCAT_WS(' ',logout_timestamp_date,logout_timestamp_time) BETWEEN DATE_SUB(now(),INTERVAL 4 Hour) AND now() "
                    + " AND ip_address='" + ipAddress + "' AND status='N' AND junction_id= " + junctionID + " AND program_version_no = " + program_version_no
                    + " AND IF(" + testRequest + "=1, error_state LIKE '%%' , error_state = 'Y') ";

            ResultSet rset1 = connection.prepareStatement(query1).executeQuery();
            if (rset1.next()) {
                result = rset1.getInt(1) > 0 ? true : false;
            }

        } catch (Exception e) {
            System.out.println("Error:clientResponderModel-checkJunctionLastSynchronisation--- " + e);
        }
        return result;
    }

    public boolean checkJunctionIfLive(String ipAddress, String port, int junctionID, int program_version_no) {
        boolean result = false;
        try {
            String query = " SELECT COUNT(*) FROM log_history WHERE ip_address='" + ipAddress + "' AND status='Y' AND junction_id= " + junctionID + " AND program_version_no = " + program_version_no;

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                result = rset.getInt(1) > 0 ? true : false;
            }
        } catch (Exception e) {
           System.out.println("Error:clientResponderModel-checkJunctionIfLive--- " + e);
        }
        return result;
    }

    public int getNoOfPlans(int junction_id, int program_version_no) {
        int noOfPlans = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT COUNT(*) FROM plan_info WHERE junction_id= ? AND program_version_no = ? AND final_revision='VALID'");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfPlans = Integer.parseInt(rset.getString(1));
            System.out.println(noOfPlans);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return noOfPlans;
    }
    
    public int getNoOfDateSlot(int junction_id, int program_version_no) {
        int noOfPlans = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT COUNT(Distinct date_id) FROM junction_plan_map WHERE junction_id= ? AND date_id IS NOT NULL AND active='Y'");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfPlans = Integer.parseInt(rset.getString(1));
            System.out.println(noOfPlans);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return noOfPlans;
    }
    
    public int getNoOfDaySlot(int junction_id, int program_version_no) {
        int noOfPlans = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT COUNT(Distinct day_id) FROM junction_plan_map WHERE junction_id= ? AND day_id IS NOT NULL AND active='Y'");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfPlans = Integer.parseInt(rset.getString(1));
            System.out.println(noOfPlans);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return noOfPlans;
    }
    

    public List<String> getDateDetail(int junction_id, int order_no) {
        List<String> dateDetail = new ArrayList<>();
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT d.from_date,d.to_date,d.date_detail_id FROM junction_plan_map jp,date_detail d "
                    + "WHERE jp.date_id = d.date_detail_id AND jp.junction_id= ? AND jp.date_id IS NOT NULL "
                    + "AND jp.order_no = ? AND jp.active='Y'");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, order_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            dateDetail.add(rset.getString(1));
            dateDetail.add(rset.getString(2));
            dateDetail.add(String.valueOf(rset.getInt(3)));
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return dateDetail;
    }
    
    public String getToDate(int junction_id, int order_no) {
        String fromDate = "";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT d.to_date FROM junction_plan_map jp,date_detail d "
                    + "WHERE jp.date_id = d.date_detail_id AND jp.junction_id= ? AND jp.date_id IS NOT NULL "
                    + "AND jp.order_no = ? AND jp.active='Y'");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, order_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            fromDate = rset.getString(1);
            System.out.println(fromDate);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return fromDate;
    }
    
    public String getDay(int junction_id, int order_no, int day_id) {
        String day = "";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT d.day FROM junction_plan_map jp,day_detail d "
                    + "WHERE jp.day_id = d.day_detail_id AND jp.junction_id= ? AND jp.day_id IS NOT NULL "
                    + "AND jp.active='Y' AND d.active='Y' AND jp.day_id = ?");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, day_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            day = rset.getString(1);
            System.out.println(day);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return day;
    }
    
    public int getDayId(int junction_id, int order_no) {
        int day = 0;
        int i = 1;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT distinct jp.day_id FROM junction_plan_map jp,day_detail d "
                    + "WHERE jp.day_id = d.day_detail_id AND jp.junction_id= ? AND jp.day_id IS NOT NULL "
                    + "AND jp.active='Y' AND d.active='Y' " );
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            int j = 1;
            while(rset.next()){
                if(order_no == j) {
                    day = rset.getInt(1);
                    break;
                } else {
                    j++;
                }
            }
                    
               
            
            System.out.println(day);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return day;
    }
    
    public String getWeekDay(int junction_id) {
        String day = "";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT d.day FROM junction_plan_map jp "
                    + "WHERE jp.junction_id= ? AND jp.day_id IS NULL AND jp.date_id IS NULL "
                    + " AND jp.active='Y' ");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            day = rset.getString(1);
            System.out.println(day);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return day;
    }
    
    public int getNoOfPlanInDate(int junction_id, int order_no, int date_id) {
        int planNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(plan_id) FROM junction_plan_map jp,date_detail d "
                    + "WHERE jp.date_id = d.date_detail_id AND jp.junction_id= ? AND jp.date_id IS NOT NULL "
                    + "AND jp.active='Y' AND jp.date_id=?");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, date_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            planNo = Integer.parseInt(rset.getString(1));
            System.out.println(planNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planNo;
    }
    
    public Map<String,String> getPlanDetail(int junction_id, int plan_no) {
        Map<String,String> planDetail = new HashMap<>();
        PreparedStatement pstmt;
        int i = 1;
        try {
            pstmt = connection.prepareStatement(" SELECT p.plan_id, p.plan_no, p.on_time_hour, p.on_time_min, "
                    + "p.off_time_hour, p.off_time_min, p.mode, p.side1_green_time, p.side2_green_time, "
                    + "p.side3_green_time, p.side4_green_time, p.side5_green_time, p.side1_amber_time, "
                    + "p.side2_amber_time, p.side3_amber_time, p.side4_amber_time, p.side5_amber_time "
                    + "FROM junction_plan_map jp,plan_details p "
                    + "WHERE jp.plan_id = p.plan_id AND jp.junction_id= ? AND jp.active='Y' "
                    + "AND p.active='Y' group by p.plan_id");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            
            while(rset.next()){
                if(i == plan_no) {
                    planDetail.put("plan_id", rset.getString(1));
                    planDetail.put("plan_no", rset.getString(2));
                    planDetail.put("on_time_hour", rset.getString(3));
                    planDetail.put("on_time_min", rset.getString(4));
                    planDetail.put("off_time_hour", rset.getString(5));
                    planDetail.put("off_time_min", rset.getString(6));
                    planDetail.put("mode", rset.getString(7));
                    planDetail.put("side1_green_time", rset.getString(8));
                    planDetail.put("side2_green_time", rset.getString(9));
                    planDetail.put("side3_green_time", rset.getString(10));
                    planDetail.put("side4_green_time", rset.getString(11));
                    planDetail.put("side5_green_time", rset.getString(12));
                    planDetail.put("side1_amber_time", rset.getString(13));
                    planDetail.put("side2_amber_time", rset.getString(14));
                    planDetail.put("side3_amber_time", rset.getString(15));
                    planDetail.put("side4_amber_time", rset.getString(16));
                    planDetail.put("side5_amber_time", rset.getString(17));
                    break;
                } 
                    i++;               
            }
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planDetail;
    }
    
    public Map<String,Integer> getJunctionPlanMapDate(int junction_id, int date_id, int plan_no) {
        Map<String,Integer> planDetail = new HashMap<>();
        PreparedStatement pstmt;
        int i = 1;
        try {
            pstmt = connection.prepareStatement(" SELECT plan_id, order_no, junction_plan_map_id "
                    + "FROM junction_plan_map jp "
                    + "WHERE jp.junction_id= ? and date_id = ? AND jp.active='Y' "
                    + "AND jp.active='Y'");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, date_id);
            ResultSet rset = pstmt.executeQuery();
            
            while(rset.next()){
                if(i == plan_no) {
                    planDetail.put("plan_id", rset.getInt(1));
                    planDetail.put("order_no", rset.getInt(2));
                    planDetail.put("map_id", rset.getInt(3));
                    break;
                } 
                    i++;               
            }
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planDetail;
    }
    
    public Map<String,Integer> getJunctionPlanMapDay(int junction_id, int day_id, int plan_no) {
        Map<String,Integer> planDetail = new HashMap<>();
        PreparedStatement pstmt;
        int i = 1;
        try {
            if(day_id > 0) {
                pstmt = connection.prepareStatement(" SELECT plan_id, order_no, junction_plan_map_id "
                        + "FROM junction_plan_map jp "
                        + "WHERE jp.junction_id= ? and day_id = ? AND jp.active='Y' "
                        + "AND jp.active='Y'");
                pstmt.setInt(1, junction_id);
                pstmt.setInt(2, day_id);
                ResultSet rset = pstmt.executeQuery();

                while(rset.next()){
                    if(i == plan_no) {
                        planDetail.put("plan_id", rset.getInt(1));
                        planDetail.put("order_no", rset.getInt(2));
                        planDetail.put("map_id", rset.getInt(3));
                        break;
                    } 
                        i++;               
                }
            } else {
                pstmt = connection.prepareStatement(" SELECT plan_id, order_no, junction_plan_map_id "
                        + "FROM junction_plan_map jp "
                        + "WHERE jp.junction_id= ? and day_id IS NULL and date_id IS NULL AND jp.active='Y' "
                        + "AND jp.active='Y'");
                pstmt.setInt(1, junction_id);
                ResultSet rset = pstmt.executeQuery();

                while(rset.next()){
                    if(i == plan_no) {
                        planDetail.put("plan_id", rset.getInt(1));
                        planDetail.put("order_no", rset.getInt(2));
                        planDetail.put("map_id", rset.getInt(3));
                        break;
                    } 
                        i++;               
                }
            }
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planDetail;
    }
    
    public Map<String,Integer> getPhaseMapDay(int junction_id, int map_id, int phase_no) {
        Map<String,Integer> phaseMapDetail = new HashMap<>();
        PreparedStatement pstmt;
        int i = 1;
        try {
           
                 pstmt = connection.prepareStatement(" SELECT pm.phase_map_id, pm. phase_id, pm.order_no "
                        + "FROM junction_plan_map jp, phase_map pm "
                        + "WHERE jp.junction_id= ? AND jp.junction_plan_map_id = ? AND pm.active='Y' "
                        + "AND jp.active='Y' AND jp.junction_plan_map_id = pm.junction_plan_map_id");
                pstmt.setInt(1, junction_id);
                pstmt.setInt(2, map_id);
                ResultSet rset = pstmt.executeQuery();

                while(rset.next()){
                    if(i == phase_no) {
                        phaseMapDetail.put("phase_map_id", rset.getInt(1));
                        phaseMapDetail.put("phase_id", rset.getInt(2));
                        phaseMapDetail.put("order_no", rset.getInt(3));
                        break;
                    } 
                        i++;               
                }
            
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return phaseMapDetail;
    }
    
     public List<SlaveInfo> getsidedata(int junction_id, int map_id, int phase_no) {
         List<SlaveInfo> li=new ArrayList<>();
        PreparedStatement pstmt;
        int i = 1;
        try {
           
                 pstmt = connection.prepareStatement("SELECT s.pole_type_id,s.primary_h_aspect_no,s.primary_v_aspect_no,s.secondary_h_aspect_no,s.secondary_v_aspect_no,s.side_no " +
"FROM traffic_signal_plan1.side_detail s where s.junction_id=? and s.active='Y'");
                pstmt.setInt(1, junction_id);
              //  pstmt.setInt(2, map_id);
                ResultSet rset = pstmt.executeQuery();
SlaveInfo si=new SlaveInfo();
                while(rset.next()){
                     si.setPole_type_id(rset.getInt("pole_type_id"));
                      si.setPrimary_h_aspect_no(rset.getInt("primary_h_aspect_no"));
                      si.setPrimary_v_aspect_no(rset.getInt("primary_v_aspect_no"));
                      si.setSecondary_h_aspect_no(rset.getInt("secondary_h_aspect_no"));
                      si.setSecondary_v_aspect_no(rset.getInt("secondary_v_aspect_no"));
                      li.add(si);
                }
            
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return li;
    }
    
     public List<SlaveInfo> getsidedatalist(int junction_id) {
         List<SlaveInfo> li=new ArrayList<>();
        PreparedStatement pstmt;
        int i = 1;
        try {
           
                 pstmt = connection.prepareStatement("SELECT s.pole_type_id,s.primary_h_aspect_no,s.primary_v_aspect_no,s.secondary_h_aspect_no,s.secondary_v_aspect_no,s.side_no,s.side_name " +
"FROM traffic_signal_plan1.side_detail s where s.junction_id=? and s.active='Y'");
                pstmt.setInt(1, junction_id);
              //  pstmt.setInt(2, map_id);
                ResultSet rset = pstmt.executeQuery();
SlaveInfo si=new SlaveInfo();
                while(rset.next()){
                     si.setPole_type_id(rset.getInt("pole_type_id"));
                      si.setPrimary_h_aspect_no(rset.getInt("primary_h_aspect_no"));
                      si.setPrimary_v_aspect_no(rset.getInt("primary_v_aspect_no"));
                      si.setSecondary_h_aspect_no(rset.getInt("secondary_h_aspect_no"));
                      si.setSecondary_v_aspect_no(rset.getInt("secondary_v_aspect_no"));
                      si.setSideName(rset.getString("s.side_name"));
                      
                      li.add(si);
                }
            
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return li;
    }
    
     
       public List<SeverityCase> getseveritydatalist(String side) {
         List<SeverityCase> li=new ArrayList<>();
         String query="SELECT  severity_case, remark, send_data, recieved_data FROM severity_case"
                 + " where  (remark='low' or remark='middle') and active='y' and send_data=?";
        PreparedStatement pstmt;
        int i = 1;
        try {
           
                 pstmt = connection.prepareStatement(query);
                pstmt.setString(1, side);
             
                ResultSet rset = pstmt.executeQuery();
SeverityCase si=new SeverityCase();
                while(rset.next()){
                    si.setSeverity_case(rset.getString(1));
                    si.setRemark(rset.getString(2));
                     si.setSent_data(rset.getString(3));
                      si.setReceived_data(rset.getString(4));
                      li.add(si);
                }
            
            
            
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return li;
    }
    
    
    
    
    public int getNoOfPlanInDay(int junction_id, int order_no, int day_id) {
        int planNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(plan_id) FROM junction_plan_map jp,day_detail d "
                    + "WHERE jp.day_id = d.day_detail_id AND jp.junction_id= ? AND jp.day_id IS NOT NULL "
                    + "AND jp.active='Y' AND d.active='Y' AND jp.day_id = ?");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, day_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            planNo = Integer.parseInt(rset.getString(1));
            System.out.println(planNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planNo;
    }
    
    public int getWeekDayNoOfPlanInDay(int junction_id) {
        int planNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(plan_id) FROM junction_plan_map jp "
                    + "WHERE jp.junction_id= ? AND jp.day_id IS NULL AND jp.date_id IS NULL "
                    + "AND jp.active='Y'");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            planNo = Integer.parseInt(rset.getString(1));
            System.out.println(planNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planNo;
    }
    
    
    public int getTotalNoOfPlan(int junction_id, int programVersionNoFromDB) {
        int planNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(distinct plan_id) FROM junction_plan_map jp "
                    + "WHERE jp.junction_id= ? "
                    + "AND jp.active='Y' ");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            planNo = Integer.parseInt(rset.getString(1));
            System.out.println(planNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planNo;
    }
    
    public int getTotalNoOfPhase(int junction_id, int programVersionNoFromDB) {
        int planNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(distinct phase_id) FROM junction_plan_map jp , phase_map pm "
                    + "WHERE jp.junction_plan_map_id = pm.junction_plan_map_id and jp.junction_id= ? "
                    + "AND jp.active='Y'  AND pm.active='Y' ");
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            planNo = Integer.parseInt(rset.getString(1));
            System.out.println(planNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getNoOfPlans() Error: " + e);
        }
        return planNo;
    }
    
    public int getFileNo(int junction_id, int program_version_no) {
        int fileNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT file_no FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            fileNo = rset.getInt("file_no");
            //System.out.println(fileNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getFIleNo() Error: " + e);
        }
        return fileNo;
    }

    public int getJunctionID(String imeiNo) {
//        System.out.println(IMEINo);
        int junction_id = 0;
        String queryJunctionID = " SELECT junction_id FROM junction WHERE imei_no = ? ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, imeiNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("junction_id");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getJunctionID() Error" + e);
        }
        return junction_id;
    }

    public int getSideNo(int junction_id, int program_version_no, String slave_id) {
//        System.out.println(slave_id);
        int side_no = 0;
        String queryJunctionID = " SELECT side_no FROM slave_info WHERE slave_id = ? AND junction_id= ? AND program_version_no = ? AND final_revision='VALID' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, slave_id);
            pstmt.setInt(2, junction_id);
            pstmt.setInt(3, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                side_no = rset.getInt("side_no");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getSideNo() Error" + e);
        }
        return side_no;
    }

    public int getProgramVersionNo(String imeiNo, int junctionID) {
        int program_Version_no = 0;
        String queryJunctionID = " SELECT program_version_no FROM junction WHERE imei_no = ? AND junction_id = ? AND final_revision='VALID' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, imeiNo);
            pstmt.setInt(2, junctionID);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                program_Version_no = rset.getInt("program_version_no");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getProgramVersionNo() Error" + e);
        }
        return program_Version_no;
    }

    public int getPedestrianTime(int junctionID, int program_version_no) {
        int pedestrian_time = 0;
        String queryJunctionID = " SELECT pedestrian_time AS pedestrian_time  FROM junction WHERE junction_id = ? AND program_version_no = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                pedestrian_time = rset.getInt("pedestrian_time");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel: getPedestrianTime() Error" + e);
        }
        return pedestrian_time;
    }

    public boolean isPedestrian(int junctionID, int program_version_no) {
        boolean pedestrian = false;
        String queryJunctionID = " SELECT pedestrian FROM junction WHERE junction_id = ? AND program_version_no = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                pedestrian = rset.getString("pedestrian").equals("Y") ? true : false;
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel: isPedestrian() Error" + e);
        }
        return pedestrian;
    }

    public boolean isValidJunction(int junctionID, int program_version_no) {
        boolean result = false;
        String queryJunctionID = " SELECT count(*) AS c FROM junction WHERE junction_id = ? AND program_version_no = ? AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("c") > 0 ? true : false;
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel: isValidJunction() Error" + e);
        }
        return result;
    }
    
     public boolean isValidJunction11(int junctionID) {
        boolean result = false;
        String queryJunctionID = " SELECT count(*) AS c FROM junction WHERE junction_id = ? AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
           // pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("c") > 0 ? true : false;
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponderModel: isValidJunction() Error" + e);
        }
        return result;
    }

    public String getPlanOnTime(int junction_id, int program_version_no, int plan_id) {
        String onTime = "";
        String query = "SELECT "
                + "CONCAT(p.on_time_hour,':',p.on_time_min) AS onTime FROM junction_plan_map jp ,plan_details p "
                + "WHERE jp.plan_id = p.plan_id AND p.active = 'Y' AND jp.junction_id = " + junction_id
                + " AND jp.active='Y' AND p.plan_id= " + plan_id ;
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                onTime = rset.getString("onTime");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanOnTime() Error: " + e);
        }
        return onTime;
    }
    
    public String getMapImage(int junction_id) {
        String onTime = "";
        String query = "SELECT "
                + "map_image  FROM plan_info "
                + "WHERE map_status='Y' AND junction_id = " + junction_id;
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                onTime = rset.getString("map_image");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanOnTime() Error: " + e);
        }
        return onTime;
    }
    
    public String getActiveRequest(int junction_id) {
        String onTime = "";
        String query = "SELECT "
                + "active  FROM device_detail "
                + "WHERE RequestMode='M' and active = 'Y' AND junction_id = " + junction_id;
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                onTime = rset.getString("active");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanOnTime() Error: " + e);
        }
        return onTime;
    }
    
    public String getactiveJunction(int junction_id) {
        String onTime = "No";
//        String query = "SELECT "
//                + "junction_id  FROM time_synchronization_detail "
//                + "WHERE junction_id = " + junction_id;
         String query = " SELECT j.junction_id, junction_name, city_name, ip_address, port, j.program_version_no, j.file_no, "
                    + " IF(synchronization_status is null or synchronization_status = '' ,'N',synchronization_status) AS synchronization_status ,"
                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(application_hr,':',application_min,' ',application_date,'-',application_month,'-',application_year)) AS application_last_time, "
                    + " IF(synchronization_status is null or synchronization_status = '','Not Set',CONCAT(junction_hr,':',junction_min,' ',junction_date,'-',junction_month,'-',junction_year)) AS junction_last_time"
                    + " FROM  junction AS j LEFT JOIN time_synchronization_detail AS tsd"
                    + "  ON j.junction_id = tsd.junction_id AND tsd.final_revision='VALID' AND j.program_version_no=tsd.program_version_no "
                    + ", log_history AS lh, city AS c  "
                    + " WHERE j.junction_id=lh.junction_id AND j.city_id=c.city_id AND logout_timestamp_time is null AND j.final_revision='VALID' and j.junction_id =" + junction_id
                    + " ORDER BY login_timestamp_date DESC, login_timestamp_time DESC ";

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                String junct=Integer.toString(rset.getInt("junction_id"));
                if(junct!=" "){
                onTime="Yes";
                }else{
                onTime="No";
                }
                //onTime = Integer.toString(rset.getInt("junction_id"));
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanOnTime() Error: " + e);
        }
        return onTime;
    }

    public String getPlanOffTime(int junction_id, int program_version_no, int plan_no) {
        String onTime = "";
        String query = "SELECT "
                + "CONCAT(off_time_hour,':',off_time_min) AS offTime FROM junction_plan_map jp ,plan_details p "
                + "WHERE jp.plan_id = p.plan_id AND p.active = 'Y' AND jp.active='Y' "
                + "AND junction_id = " + junction_id 
                + "  AND plan_no= " + plan_no;
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                onTime = rset.getString("offTime");
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanOffTime() Error: " + e);
        }
        return onTime;
    }

    public int getProgramVersionNo(int junctionID) {
        int program_Version_no = 0;
        String queryJunctionID = " SELECT program_version_no FROM junction WHERE junction_id = ? AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                program_Version_no = rset.getInt("program_version_no");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: getProgramVersionNo() Error" + e);
        }
        return program_Version_no;
    }

    public boolean updatePlanTransferredStatus(int junctionId, int program_version_no, int planNo) {
        boolean isTransferred = false;
        String queryJunctionID = " UPDATE plan_info SET transferred_status = 'YES' WHERE junction_id = ?  AND program_version_no= ? AND plan_no = ? AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionId);
            pstmt.setInt(2, program_version_no);
            pstmt.setInt(3, planNo);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                isTransferred = true;
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: updatePlanTransferredStatus() Error" + e);
        }
        return isTransferred;
    }

    public boolean updateSlaveTransferredStatus(int junctionId, int program_version_no, int side_no) {
        boolean isTransferred = false;
        String queryJunctionID = " UPDATE slave_info SET transferred_status = 'YES' WHERE junction_id = ?  AND program_version_no= ? AND side_no = ? AND final_revision = 'VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionId);
            pstmt.setInt(2, program_version_no);
            pstmt.setInt(3, side_no);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                isTransferred = true;
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: updateSlaveTransferredStatus() Error" + e);
        }
        return isTransferred;
    }

    public boolean updateJunctionTransferredStatus(int junctionId, int program_version_no, String status) {
        boolean isTransferred = false;
        String queryJunctionID = " UPDATE junction SET transferred_status = ? WHERE junction_id = ? AND program_version_no= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setString(1, status);
            pstmt.setInt(2, junctionId);
            pstmt.setInt(3, program_version_no);
            int executeUpdate = pstmt.executeUpdate();
            if (executeUpdate > 0) {
                isTransferred = true;
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: updateJunctionTransferredStatus() Error" + e);
        }
        return isTransferred;
    }

    public int synchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        int rowAffected = 0;
        String activity = "";
        String lastTimeSynchronizationStatus = getLastTimeSynchronizationStatus(junctionID, program_version_no);
        String currentTimeSynchronizationStatus = getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);

        if (lastTimeSynchronizationStatus.equals(currentTimeSynchronizationStatus)) {
            rowAffected = updateSynchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);
            activity = "updated";
        } else {
            rowAffected = insertSynchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear, currentTimeSynchronizationStatus);
            activity = "inserted";
        }
        if (rowAffected > 0) {
            System.out.println("Synchronized time data " + activity + " successfully.");
        }
        return rowAffected;
    }

    public String getCurrentTimeSynchronizationStatus(int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        String currentTimeSynchronizationStatus = "N";
        // increase time  (juncMin=appMin)    Now   juncMin <= appMin && appMin<juncMin+2
//        if(juncMin>=59){
//            juncMin=00;
//        }else{
//        juncMin=juncMin+2;
//        }
            if (juncHr == appHr && juncMin <= appMin && juncMin<=appMin && juncDat == appDat && juncMonth == appMonth && juncYear == appYear) {
            currentTimeSynchronizationStatus = "Y";
            System.out.println("Hello!!!!!!!!!!!!!!!!!!!!!!!"+juncMin+"                    sssssssssssssssss"+appMin);
        } else {
            currentTimeSynchronizationStatus = "N";
              System.out.println("Hello!!!!!!!!!!!!!!!!!!!!!!!"+juncMin+"                    sssssssssssssssss"+appMin);
        }
        return currentTimeSynchronizationStatus;
    }

    public String getLastTimeSynchronizationStatus(int junctionID, int program_version_no) {
        String status = "No Record";
        int final_rev_no = getLastTimeSynchronizationId(junctionID, program_version_no);
        if (final_rev_no != -1) {
            String query = " SELECT synchronization_status FROM time_synchronization_detail "
                    + " WHERE time_synchronization_detail_id = ? "
                    + " AND junction_id = ? AND program_version_no = ? ";
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, final_rev_no);
                pstmt.setInt(2, junctionID);
                pstmt.setInt(3, program_version_no);
                ResultSet rset = pstmt.executeQuery();
                while (rset.next()) {
                    status = rset.getString("synchronization_status");
                }
//            connection.close();
            } catch (Exception e) {
                System.out.println("ClientResponderModel: getLastTimeSynchronizationStatus() Error" + e);
            }
        }
        return status;
    }

    public int updateSynchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        int rowsReturned = 0;
        String insertQuery = " UPDATE time_synchronization_detail SET  junction_hr = ?, application_hr = ?, junction_min = ?, application_min = ?, "
                + " junction_date = ?, application_date = ?, junction_month = ?, application_month = ?, junction_year = ?, application_year = ? WHERE "
                + " junction_id = ? AND time_synchronization_detail_id = ? AND program_version_no = ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, juncHr);
            pstmt.setInt(2, appHr);
            pstmt.setInt(3, juncMin);
            pstmt.setInt(4, appMin);
            pstmt.setInt(5, juncDat);
            pstmt.setInt(6, appDat);
            pstmt.setInt(7, juncMonth);
            pstmt.setInt(8, appMonth);
            pstmt.setInt(9, juncYear);
            pstmt.setInt(10, appYear);
            pstmt.setInt(11, junctionID);
            pstmt.setInt(12, getLastTimeSynchronizationId(junctionID, program_version_no));
            pstmt.setInt(13, program_version_no);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ClientResponderModel updateSynchronizeTimeData() Error: " + e);
        }
        return rowsReturned;
    }

    public int insertSynchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear, String currentTimeSynchronizationStatus) {
        int rowsReturned = 0;
        String insertQuery = " INSERT INTO time_synchronization_detail(junction_id, time_synchronization_detail_id, junction_hr, application_hr, junction_min, application_min, "
                + " junction_date, application_date, junction_month, application_month, junction_year, application_year, synchronization_status, remark, program_version_no) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        PreparedStatement pstmt;
        try {
            int final_rev_no = getLastTimeSynchronizationId(junctionID, program_version_no);
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, (final_rev_no + 1));
            pstmt.setInt(3, juncHr);
            pstmt.setInt(4, appHr);
            pstmt.setInt(5, juncMin);
            pstmt.setInt(6, appMin);
            pstmt.setInt(7, juncDat);
            pstmt.setInt(8, appDat);
            pstmt.setInt(9, juncMonth);
            pstmt.setInt(10, appMonth);
            pstmt.setInt(11, juncYear);
            pstmt.setInt(12, appYear);
            pstmt.setString(13, currentTimeSynchronizationStatus);
            pstmt.setString(14, "");
            pstmt.setInt(15, program_version_no);
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0 && final_rev_no != -1) {
                rowsReturned = 0;
                rowsReturned = updateSynchronizeTimeFinalRevision(junctionID, program_version_no, final_rev_no);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel insertSynchronizeTimeData() Error: " + e);
        }
        return rowsReturned;
    }

    public int updateSynchronizeTimeFinalRevision(int junctionID, int program_version_no, int time_synchronization_detail_id) {
        String updateQuery = "UPDATE time_synchronization_detail SET  final_revision = ? WHERE  junction_id = ? AND time_synchronization_detail_id = ? AND program_version_no = ? ";
        int rowsReturned = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, "EXPIRED");
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, time_synchronization_detail_id);
            pstmt.setInt(4, program_version_no);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ClientResponderModel updateSynchronizeTimeFinalRevision() Error: " + e);
        }
        return rowsReturned;
    }

    public int getLastTimeSynchronizationId(int junctionID, int program_version_no) {
        int id = 0;
        String query = " SELECT COUNT(*) AS c,time_synchronization_detail_id AS id FROM time_synchronization_detail WHERE final_revision='VALID' AND junction_id = ? AND program_version_no = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("c") != 0 ? rset.getInt("id") : -1;
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel: getLastTimeSynchronizationId() Error" + e);
        }
        return id;
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

    public Map<String,String> getPhaseDetail(int junctionID, int phaseNo) {
        /*String query = "SELECT distinct pd.phase_info_id,pd.phase_no,pd.phase_time, pd.green1,pd.green2,pd.green3,pd.green4,pd.green5, "
                       +"pd.side13, pd.side24, pd.side5,pd.left_green,pd.padestrian_info,pd.GPIO,pm.junction_plan_map_id "
                       +"FROM phase_map pm, phase_detail pd, junction_plan_map jp,plan_details p "
                       +"WHERE pm.junction_plan_map_id = jp.junction_plan_map_id and pm.phase_id = pd.phase_info_id and jp.plan_id = p.plan_id "
                       +"and pm.active = 'Y' and jp.active='Y' and pd.active = 'Y' and p.active='Y' and jp.junction_id = ? order by phase_info_id;";
        */
         String query = "SELECT distinct pd.phase_info_id,pd.phase_no,pd.phase_time, pd.green1,pd.green2,pd.green3,pd.green4,pd.green5, "
                       +"pd.side13, pd.side24, pd.side5,pd.left_green,pd.padestrian_info,pd.GPIO,pm.junction_plan_map_id "
                       +"FROM phase_map pm, phase_detail pd, junction_plan_map jp, plan_details p "
                       +"WHERE pm.junction_plan_map_id = jp.junction_plan_map_id and pm.phase_id = pd.phase_info_id and jp.plan_id = p.plan_id "
                       +"and pm.active = 'Y' and jp.active='Y' and pd.active = 'Y' and jp.junction_id = ? group by phase_info_id;";
        
        int phaseTime = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        int i = 1;
        Map<String,String> planDetails = new HashMap<>();
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                if(i ==  phaseNo) {
                    planDetails.put("phase_info_id", rset.getString(1));
                    planDetails.put("phase_no", rset.getString(2));
                    planDetails.put("phase_time", rset.getString(3));
                    planDetails.put("green1", rset.getString(4));
                    planDetails.put("green2", rset.getString(5));
                    planDetails.put("green3", rset.getString(6));
                    planDetails.put("green4", rset.getString(7));
                    planDetails.put("green5", rset.getString(8));
                    planDetails.put("side13", rset.getString(9));
                    planDetails.put("side24", rset.getString(10));
                    planDetails.put("side5", rset.getString(11));
                    planDetails.put("left_green", rset.getString(12));
                    planDetails.put("padestrian_info", rset.getString(13));
                    planDetails.put("GPIO", rset.getString(14));
                    planDetails.put("map_id", rset.getString(15));
                    break;
                }
                i++;
                
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPhaseTime() Error: " + e);
        }
        return planDetails;
    }
    
    public int getPhaseNumberNew(int junctionID, int plan_id) {
        int phaseNo = 0;
        String query = "Select count(distinct phase_id) from junction_plan_map jp, phase_map pm " +
                        "where jp.junction_plan_map_id = pm.junction_plan_map_id and jp.active='Y' and pm.active='Y' and jp.plan_id = ? and jp.junction_id = ?";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, plan_id);
            pstmt.setInt(2, junctionID);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                phaseNo = rset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
        }
        return phaseNo;
    }
    
    public int getPhaseNoMapDate(int junctionID, int date_id, int plan_id) {
        int phaseNo = 0;
        String query = "Select count(distinct phase_id) from junction_plan_map jp, phase_map pm " +
                        "where jp.junction_plan_map_id = pm.junction_plan_map_id and jp.active='Y' and pm.active='Y' and jp.plan_id = ? and jp.junction_id = ? and jp.date_id = ?";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, plan_id);
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, date_id);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                phaseNo = rset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
        }
        return phaseNo;
    }
    
    public int getPhaseNoMapDay(int junctionID, int day_id, int plan_id,int jpm_id) {
        int phaseNo = 0;
        if(day_id > 0) {
            String query = "Select count(distinct phase_id) from junction_plan_map jp, phase_map pm " +
                            "where jp.junction_plan_map_id = pm.junction_plan_map_id and jp.active='Y' and pm.active='Y' and jp.plan_id = ? and jp.junction_id = ? and jp.junction_plan_map_id = '"+jpm_id+"' and jp.day_id = ?";
            PreparedStatement pstmt;
            ResultSet rset;
            try {
                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, plan_id);
                pstmt.setInt(2, junctionID);
                pstmt.setInt(3, day_id);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    phaseNo = rset.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
            }
        } else {
            String query = "Select count(distinct phase_id) from junction_plan_map jp, phase_map pm " +
                            "where jp.junction_plan_map_id = pm.junction_plan_map_id and jp.active='Y' and pm.active='Y' and jp.plan_id = ? and jp.junction_id = ?  and jp.junction_plan_map_id = '"+jpm_id+"' and day_id IS NULL and date_id IS NULL";
            PreparedStatement pstmt;
            ResultSet rset;
            try {
                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, plan_id);
                pstmt.setInt(2, junctionID);
                rset = pstmt.executeQuery();
                while (rset.next()) {
                    phaseNo = rset.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
            }
        }
        return phaseNo;
    }
    

    public Map<String,String> getPhaseDetailCheck(int junctionID, int planNo, int plan_id, int phaseNo) {
        String query = "SELECT pd.phase_info_id,pd.phase_no,pd.phase_time, pd.green1,pd.green2,pd.green3,pd.green4,pd.green5, "
                       +"pd.side13, pd.side24, pd.side5,pd.left_green,pd.padestrian_info,pd.GPIO "
                       +"FROM phase_map pm, phase_detail pd, junction_plan_map jp,plan_details p "
                       +"WHERE pm.junction_plan_map_id = jp.junction_plan_map_id and pm.phase_id = pd.phase_info_id and jp.plan_id = p.plan_id"
                       +"and pm.active = 'Y' and jp.active='Y' and pd.active = 'Y' and p.active='Y' and jp.junction_id = ? and jp.plan_id = ? and p.plan_no = ? and pd.phase_no = ?";
        int phaseTime = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        int i = 1;
        Map<String,String> planDetails = new HashMap<>();
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, plan_id);
            pstmt.setInt(3, planNo);
            pstmt.setInt(4, phaseNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                if(i ==  phaseNo) {
                    planDetails.put("phase_info_id", rset.getString(1));
                    planDetails.put("phase_no", rset.getString(2));
                    planDetails.put("phase_time", rset.getString(3));
                    planDetails.put("green1", rset.getString(4));
                    planDetails.put("green2", rset.getString(5));
                    planDetails.put("green3", rset.getString(6));
                    planDetails.put("green4", rset.getString(7));
                    planDetails.put("green5", rset.getString(8));
                    planDetails.put("side13", rset.getString(9));
                    planDetails.put("side24", rset.getString(10));
                    planDetails.put("side5", rset.getString(11));
                    planDetails.put("left_green", rset.getString(12));
                    planDetails.put("padestrian_info", rset.getString(13));
                    planDetails.put("GPIO", rset.getString(13));
                }
                i++;
                
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPhaseTime() Error: " + e);
        }
        return planDetails;
    }
    public List<Integer> getGreenTiming(int junctionID, int planNo, int phaseNo) {
        List<Integer> greenTime = new ArrayList<Integer>();
        String query = "SELECT green1,green2, green3,green4,green5 FROM phase_info where junction_id = ? and plan_no = ? and phase_no = ? and active = 'Y';";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, planNo);
            pstmt.setInt(3, phaseNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                int green1 = rset.getInt("green1");
                int green2 = rset.getInt("green2");
                int green3 = rset.getInt("green3");
                int green4 = rset.getInt("green4");
                int green5 = rset.getInt("green5");
                greenTime.add(green1);
                greenTime.add(green2);
                greenTime.add(green3);
                greenTime.add(green4);
                greenTime.add(green5);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanTiming() Error: " + e);
        }
        return greenTime;
    }

    public List<Integer> getSideValue(int junctionID, int planNo, int phaseNo, int map_id) {
        List<Integer> sideValue = new ArrayList<Integer>();
        String query = "Select jpm.junction_plan_map_id,pm.phase_id,side13,side24, side5,pm.order_no "
                + "FROM phase_detail pd,junction_plan_map jpm, phase_map pm " 
                + "Where pd.phase_info_id=pm.phase_id AND jpm.junction_plan_map_id = pm.junction_plan_map_id " 
                + "AND junction_id = "+junctionID+" AND plan_id = "+planNo+" AND pd.phase_info_id= "+phaseNo+" AND pd.active='Y' AND pm.active='Y' "
                + "AND jpm.active='Y' AND jpm.junction_plan_map_id = "+map_id;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                int side13 = rset.getInt("side13");
                int side24 = rset.getInt("side24");
                int side5 = rset.getInt("side5");
                sideValue.add(side13);
                sideValue.add(side24);
                sideValue.add(side5);
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel sideValue Error: " + e);
        }
        return sideValue;
    }

    public int getLeftGreen(int junctionID, int planNo, int phaseNo) {
        String query = "SELECT left_green FROM phase_info where junction_id = ? and plan_no = ? and phase_no = ? and active = 'Y';";
        
        int leftGreen = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, planNo);
            pstmt.setInt(3, phaseNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                leftGreen = Integer.parseInt(rset.getString("left_green"));
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanMode() Error: " + e);
        }
        return leftGreen;
    }

    public int getPadestrianTime(int junctionID, int planNo, int phaseNo) {
        String query = "SELECT padestrian_info FROM phase_info where junction_id = ? and plan_no = ? and phase_no = ? and active = 'Y';";
        
        int padestrian_info = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, planNo);
            pstmt.setInt(3, phaseNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                padestrian_info = Integer.parseInt(rset.getString("padestrian_info"));
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPlanMode() Error: " + e);
        }
        return padestrian_info;
    }

    public int getGPIO(int junctionID, int planNo, int phaseNo) {
        String query = "SELECT GPIO FROM phase_info where junction_id = ? and plan_no = ? and phase_no = ? and active = 'Y';";
        
        int GPIO = 0;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, planNo);
            pstmt.setInt(3, phaseNo);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                GPIO = Integer.parseInt(rset.getString("GPIO"));
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel GPIO() Error: " + e);
        }
        return GPIO;
    }
    
    public Boolean sideColorMatch(int junctionID, int planNo, int phaseNo, int unsignedSide13, int unsignedSide24, int unsignedSide5,int map_id) {
        int side13 = 0;
        int side24 = 0;
        int side5 = 0;
        Boolean match = false;
        String query = "Select jpm.junction_plan_map_id,pm.phase_id,side13,side24, side5,pm.order_no "
                + "FROM phase_detail pd,junction_plan_map jpm, phase_map pm " 
                + "Where pd.phase_info_id=pm.phase_id AND jpm.junction_plan_map_id = pm.junction_plan_map_id " 
                + "AND junction_id = "+junctionID+" AND plan_id = "+planNo+" AND pd.phase_info_id= "+phaseNo+" AND pd.active='Y' AND pm.active='Y' "
                + "AND jpm.active='Y' AND jpm.junction_plan_map_id = "+map_id;
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                side13 = rset.getInt("side13");
                side24 = rset.getInt("side24");
                side5 = rset.getInt("side5");                
            }
            if(side13 == unsignedSide13) {
                if(side24 == unsignedSide24) {
                    if(side5 == unsignedSide5) {
                        match = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ClientResponderModel sideColorMatch Error: " + e);
        }
        return match;
    }
     
    public int[] decToBinary(int n) 
    { 
        // array to store binary number 
        int[] binaryNum = new int[100]; 
        int[] binaryNum1 = new int[100];
        // counter for binary array 
        int i = 0; 
        while (n > 0)  
        { 
            // storing remainder in binary array 
            binaryNum[i] = n % 2; 
            n = n / 2; 
            i++; 
        } 
   
        // printing binary array in reverse order 
        int k = 0;
        for (int j = i - 1; j >= 0; j--) {
            binaryNum1[k++] = binaryNum[j]; 
        }
        return binaryNum1;
    } 
    
    public String decToBinaryAndSplitFirst(int n) 
    { 
        //perform decToBinary
//        int[] binaryNum = decToBinary(n);
//        int len = binaryNum.length;
//        String binary = "";
//        for (int i = 0; i < binaryNum.length; i++) {
//            binary = binary + binaryNum[i];            
//        }
         String binary = Integer.toBinaryString(n);
      int a=binary.length();
      String remain="";
      String str="";
if(a<=7){
    a=8-a;
    
      for(int k=0;k<a;k++){
          
          str = str.concat("0");
      
      }
      binary=str.concat(binary);
}

        String sideFirst = binary.substring(0, 4);
        return sideFirst;
    }
    
    public String decToBinaryAndSplitLater(int n) 
    { 
        //perform decToBinary
//        int[] binaryNum = decToBinary(n);
//        int len = binaryNum.length;
//        String binary = "";
//        for (int i = 0; i < binaryNum.length; i++) {
//            binary = binary + binaryNum[i];            
//        }
         String binary = Integer.toBinaryString(n);
      int a=binary.length();
       String str="";
if(a<=7){
    a=8-a;
    
      for(int k=0;k<a;k++){
          
          str = str.concat("0");
      
      }
      binary=str.concat(binary);
}
        String sideFirst = binary.substring(4, 8);
        return sideFirst;
    }
    
    public int getPoleType(int junction_id, int program_version_no, int side_no) {
        int poleType = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" Select pt.pole_type AS pole_type "
                    + "from side_detail sl inner join pole_type pt on sl.pole_type_id = pt.pole_type_id " 
                    + "where junction_id = ? and sl.active = 'Y' and pt.active = 'Y' and side_no = ?");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, side_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            String poleTypeS = rset.getString("pole_type");
            if(poleTypeS.equals("Standard")) {
                poleType = 1;
            } else if(poleTypeS.equals("Cantilever T")) {
                poleType =2;
            } else if(poleTypeS.equals("Cantilever L")) {
                poleType = 3;
            }
            //System.out.println(fileNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPoleType() Error: " + e);
        }
        return poleType;
    }
    //--------
     public int getPoleType1(int junction_id, int programVersionNoFromDB, int side_no) {
        int poleType = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" Select pt.pole_type AS pole_type "
                    + "from side_detail sl inner join pole_type pt on sl.pole_type_id = pt.pole_type_id " 
                    + "where junction_id = ? and sl.active = 'Y' and pt.active = 'Y' and side_no = ?");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, side_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            String poleTypeS = rset.getString("pole_type");
            if(poleTypeS.equals("Standard")) {
                poleType = 1;
            } else if(poleTypeS.equals("Cantilever T")){
                poleType =2;
            } else if(poleTypeS.equals("Cantilever L")) {
                poleType = 3;
            }
            //System.out.println(fileNo);
        } catch (Exception e) {
            System.out.println("ClientResponderModel getPoleType() Error: " + e);
        }
        return poleType;
    }
    
    //-----
    
    public Map<String,Object> matchColor(String sendData, String recievedData) { 
        String query = "SELECT severity_case, severity_case_id,remark "
                + "FROM severity_case "
                + " where send_data = ? and recieved_data = ? and severity_case.active = 'Y'";
        PreparedStatement pstmt;
        ResultSet rset;
        int rowsReturned = 0;
        Map<String,Object> mapObject = new HashMap<>();
        String severity_case = "";
        int severity_case_id = 0;
         try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, sendData);
            pstmt.setString(2, recievedData);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                mapObject.put("severity_case",rset.getString("severity_case"));
                mapObject.put("severity_case_id",rset.getInt("severity_case_id"));
                  mapObject.put("remark",rset.getString("remark"));
            }
            if(severity_case_id > 0) {                
                System.out.println("ClientResponderModel matchColor() Record inserted successfully in Log Table");
            }
        } catch (SQLException e) {
            System.out.println("ClientResponderModel matchColor Error: " + e);
        }
        return mapObject;
    }
    
    public int insertIntoLogTable(String severity_case,int severity_case_id, int side_detail_id) {
        PreparedStatement pstmt;
        ResultSet rset;
        int rowsReturned = 0;
        if(severity_case_id > 0) {
                String insertQyery = "INSERT INTO log_table(case_id, date_time, remark, side_detail_id) "
                + " VALUES (?, ?, ?, ?) ";
                try {
//                    Date and time
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    pstmt = connection.prepareStatement(insertQyery);
                    pstmt.setInt(1, severity_case_id);
                    pstmt.setString(2, formatter.format(date));
                    pstmt.setString(3, severity_case);
                    pstmt.setInt(4, side_detail_id);
                    rowsReturned = pstmt.executeUpdate();
                    if (rowsReturned > 0) {
                        System.out.println("ClientResponderModel matchColor() Record inserted successfully in Log Table");
                    }
                } catch (SQLException e) {
                    System.out.println("ClientResponderModel matchColor Error: " + e);
                }
            }
        return rowsReturned;
    }
    
    
    

    
}
