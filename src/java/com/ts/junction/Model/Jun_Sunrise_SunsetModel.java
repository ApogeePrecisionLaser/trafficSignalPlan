/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import com.ts.junction.tableClasses.Jun_Sunrise_Sunset;
import com.ts.junction.tableClasses.NewClass;
import com.ts.util.Longitude_LatitudeCalculator;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Shruti
 */
public class Jun_Sunrise_SunsetModel extends HttpServlet {

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

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("Image setConnection() Error: " + e);
        }
    }

    public List<Jun_Sunrise_Sunset> showData(int lowerLimit, int noOfRowsToDisplay, String searchcity,String searchdate,String searchsunrisehr,String searchsunrisemin,String searchsunsethr,String searchsunsetmin) {
        List<Jun_Sunrise_Sunset> list = new ArrayList<Jun_Sunrise_Sunset>();
        PreparedStatement pstmt=null;
        try {
           
                String query = " SELECT city_name, sunrise_hr, sunrise_min, sunset_hr, sunset_min, date "
                        + " FROM jn_rise_set_time AS jrs, city AS c "
                        + " WHERE c.city_id=jrs.city_id and"
                    + " IF('" + searchcity + "' = '', c.city_name LIKE '%%',c.city_name ='" + searchcity + "') and " 
                    + " IF('" + searchdate + "' = '', jrs.date LIKE '%%',jrs.date ='" + searchdate + "') and " 
                    + " IF('" + searchsunrisehr + "' = '', jrs.sunrise_hr LIKE '%%',jrs.sunrise_hr ='" + searchsunrisehr + "') and " 
                    + " IF('" + searchsunrisemin + "' = '', jrs.sunrise_min LIKE '%%',jrs.sunrise_min ='" + searchsunrisemin + "') and " 
                    + " IF('" + searchsunsethr + "' = '', jrs.sunset_hr LIKE '%%',jrs.sunset_hr ='" + searchsunsethr + "') and " 
                  + " IF('" + searchsunsetmin + "' = '', jrs.sunset_min LIKE '%%',jrs.sunset_min ='"+searchsunsetmin+"') ";
                    
               
//                pstmt.setString(1, city_name);
//                pstmt.setInt(2, year);
//                pstmt.setInt(3, month);
                //pstmt.setString(4, date);
          
          ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Jun_Sunrise_Sunset junRiseSet = new Jun_Sunrise_Sunset();
                junRiseSet.setCity_name(rset.getString("city_name"));
                junRiseSet.setSunrise_time_hrs(rset.getInt("sunrise_hr"));
                junRiseSet.setSunrise_time_min(rset.getInt("sunrise_min"));
                junRiseSet.setSunset_time_hrs(rset.getInt("sunset_hr"));
                junRiseSet.setSunset_time_min(rset.getInt("sunset_min"));
                junRiseSet.setDate(convertDateToString(rset.getString("date")));
                list.add(junRiseSet);
            }
        } catch (Exception e) {
            System.out.println("Error:JunctionSunriseSunsetModel-showData--- " + e);
        }
        return list;
    }

    public int deleteRecord(String date) {
        String query = "DELETE FROM jn_rise_set_time WHERE date= ? ";
        int rowsAffected = 0;
        PreparedStatement pstmt;
        try {
            Date setDate = setDate(date);
            pstmt = connection.prepareStatement(query);
            pstmt.setDate(1, setDate);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:Delete:JunctionSunriseSunsetModel-- " + e);
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

    public int getCityId(String cityName) {
        int cityId = 0;
        String query = " SELECT city_id FROM city WHERE city_name = ? ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cityName);
            rset = pstmt.executeQuery();
            while (rset.next()) {
                cityId = rset.getInt("city_id");
            }
        } catch (Exception e) {
            System.out.println("Error: JunctionSunriseSunsetModel-getCityId-" + e);
        }
        return cityId;
    }

    public boolean IsDateExists(String date, int city_id) {
        boolean isExists = false;
        String query;
        Date dateFromRset;
        Date setDate = null;
        query = " SELECT date FROM jn_rise_set_time WHERE city_id = ? ";
        PreparedStatement pstmt;
        Set<Date> dateSet = new HashSet<Date>();
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, city_id);
            setDate = setDate(date);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                dateFromRset = rset.getDate("date");
                dateSet.add(dateFromRset);
            }
        } catch (SQLException ex) {
            System.out.println("Jun_sunrise_sunset : isDtaeExists error" + ex);
        }
        if (dateSet.contains(setDate)) {
            isExists = true;
        }
        return isExists;
    }

    public String generateDate(int day, int month, int year) {
        String date = "";
        date = day + "-" + month + "-" + year;
        return date;
    }

    public int insertRecord(Jun_Sunrise_Sunset junRiseSet) {
        String query;
       
         
                int rowsAffected = 0;
      
            query = "INSERT INTO jn_rise_set_time (city_id, sunrise_hr, sunrise_min, sunset_hr, sunset_min, date) "
                    + " VALUES(?, ?, ?, ?, ?, ?) ";
//        
                try {
                    PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
                      
                    pstmt.setInt(1, getCityId(junRiseSet.getCity_name()));
                    pstmt.setInt(2, junRiseSet.getSunrise_time_hrs());
                    pstmt.setInt(3, junRiseSet.getSunrise_time_min());
                    pstmt.setInt(4, junRiseSet.getSunset_time_hrs());
                    pstmt.setInt(5, junRiseSet.getSunset_time_min());
                    
                    pstmt.setDate(6, junRiseSet.getSqldate());
                            rowsAffected = pstmt.executeUpdate();

                } catch (Exception e) {
                    System.out.println("Error: JunctionSunriseSunsetModel-insertRecord-" + e);
                }
           
     
        if (rowsAffected > 0) {
            message = "Record inserted successfully.";
            msgBgColor = COLOR_OK;
            System.out.println("record saved successfully");
        } else {
            message = "Cannot insert the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("record cannot saved successfully");
        }
        return rowsAffected;
    }

    public String convertDateToString(String date) {
        String finalDate = null;
        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[2] + "-" + str1[1] + "-" + str1[0]; // Format: mm/dd/yy
        finalDate = strD;
        return finalDate;
    }

    public java.sql.Date setDate(String date) {
        java.sql.Date finalDate = null;

        String strD = date;
        String[] str1 = strD.split("-");
        strD = str1[1] + "/" + str1[0] + "/" + str1[2]; // Format: mm/dd/yy
        try {
            finalDate = new java.sql.Date(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("en", "US")).parse(strD).getTime());
        } catch (Exception ex) {
            System.out.println("junction sunrise sunset : setdate error" + ex);
        }
        return finalDate;
    }
  public List<String> getCity() {
        String query = "SELECT city_name from city WHERE active = 'Y'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("city_name"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
  public List<String> getSunriseHr() {
        String query = "SELECT sunrise_hr from jn_rise_set_time";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("sunrise_hr"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
  public List<String> getSunriseMin() {
        String query = "SELECT sunrise_min from jn_rise_set_time";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("sunrise_min"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
  public List<String> getSunsetHr() {
        String query = "SELECT sunset_hr from jn_rise_set_time";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("sunset_hr"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
  public List<String> getSunsetMin() {
        String query = "SELECT sunset_min from jn_rise_set_time";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("sunset_min"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
  public List<String> getSearchDate() {
        String query = "SELECT date from jn_rise_set_time";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("date"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT city_name "
                + " FROM city AS c "
                + " ORDER BY city_name ";
        try {
            pstmt = connection.prepareStatement(query);
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

    public String showSunriseSunset() {
        List<NewClass> list = new ArrayList<NewClass>();
        String responseStr = "{";
        String res = "";
        try {
            String query = "SELECT sunrise_hr, sunrise_min, sunset_hr, sunset_min "
                    + " FROM jn_rise_set_time AS jrs, city AS c "
                    + " WHERE c.city_id=jrs.city_id AND substring_index(substring(date, 6), '-', 1)=12 AND jrs.city_id=1";
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                NewClass junRiseSet = new NewClass();
                junRiseSet.setSunrise_time_hrs(rset.getInt("sunrise_hr"));
                junRiseSet.setSunrise_time_min(rset.getInt("sunrise_min"));
                junRiseSet.setSunset_time_hrs(rset.getInt("sunset_hr"));
                junRiseSet.setSunset_time_min(rset.getInt("sunset_min"));
                list.add(junRiseSet);
                String value = "";
                int j = 0;
                value = value + "{";
                value = value + junRiseSet.getSunrise_time_hrs() + ",";
                value = value + junRiseSet.getSunrise_time_min() + ",";
                value = value + junRiseSet.getSunset_time_hrs() + ",";
                value = value + junRiseSet.getSunset_time_min();
                responseStr = value + "}" + ",";
                res = res + responseStr;
            }
            System.out.println(res);
        } catch (Exception e) {
            System.out.println("Error:JunctionSunriseSunsetModel-showData--- " + e);
        }
        return responseStr;
    }

    public int getNoOfRows( String searchcity,String searchdate,String searchsunrisehr,String searchsunrisemin,String searchsunsethr,String searchsunsetmin) {
        int noOfRows = 0;
        PreparedStatement pstmt;
        try {
             String query = " SELECT count(*)"
                        + " FROM jn_rise_set_time AS jrs, city AS c "
                        + " WHERE c.city_id=jrs.city_id and"
                    + " IF('" + searchcity + "' = '', c.city_name LIKE '%%',c.city_name ='" + searchcity + "') and " 
                    + " IF('" + searchdate + "' = '', jrs.date LIKE '%%',jrs.date ='" + searchdate + "') and " 
                    + " IF('" + searchsunrisehr + "' = '', jrs.sunrise_hr LIKE '%%',jrs.sunrise_hr ='" + searchsunrisehr + "') and " 
                    + " IF('" + searchsunrisemin + "' = '', jrs.sunrise_min LIKE '%%',jrs.sunrise_min ='" + searchsunrisemin + "') and " 
                    + " IF('" + searchsunsethr + "' = '', jrs.sunset_hr LIKE '%%',jrs.sunset_hr ='" + searchsunsethr + "') and " 
                  + " IF('" + searchsunsetmin + "' = '', jrs.sunset_min LIKE '%%',jrs.sunset_min ='"+searchsunsetmin+"') ";
                    
               
//                pstmt.setString(1, city_name);
//                pstmt.setInt(2, year);
//                pstmt.setInt(3, month);
                //pstmt.setString(4, date);
          
          ResultSet rset = connection.prepareStatement(query).executeQuery();
                        rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("JunctionSunriseSunsetModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public int updateRecord(Jun_Sunrise_Sunset junRiseSet) {
        String query = null;
        Longitude_LatitudeCalculator llCalculator = new Longitude_LatitudeCalculator();
        String city_name = junRiseSet.getCity_name();
        String date = junRiseSet.getDate();
        String[] ll = llCalculator.findLatitudeLongitude(city_name);
        String latitude = ll[0];
        String longitude = ll[1];
        Date setDate = setDate(date);
        System.out.println("sqltDate---" + setDate);
        Location location = new Location(latitude, longitude);
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, city_name);
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(setDate);
        System.out.println("time---" + myCal.getTime());
        String sunrise = calculator.getOfficialSunriseForDate(myCal);
        String sunset = calculator.getOfficialSunsetForDate(myCal);
        String[] str1 = sunrise.split(":");
        int sunrise_hr = Integer.parseInt(str1[0]) + 5;
        int sunrise_min = Integer.parseInt(str1[1]) + 30;
        int sunrise_min_difference;
        if (sunrise_min > 60) {
            sunrise_min_difference = sunrise_min - 60;
            sunrise_hr = sunrise_hr + 1;
            sunrise_min = sunrise_min_difference;
        }
        String[] str2 = sunset.split(":");
        int sunset_hr = Integer.parseInt(str2[0]) + 5;
        int sunset_min = Integer.parseInt(str2[1]) + 30;
        int sunset_min_difference;
        if (sunset_min > 60) {
            sunset_min_difference = sunset_min - 60;
            sunset_hr = sunset_hr + 1;
            sunset_min = sunset_min_difference;
        }
        System.out.println("officialSunrise" + sunrise + "---------officialSunset" + sunset);
        query = "UPDATE jn_rise_set_time "
                + "SET city_id =?, sunrise_hr =?, sunrise_min = ?, sunset_hr = ?, sunset_min = ? "
                + " WHERE date = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1, getCityId(junRiseSet.getCity_name()));
            pstmt.setInt(2, sunrise_hr);
            pstmt.setInt(3, sunrise_min);
            pstmt.setInt(4, sunset_hr);
            pstmt.setInt(5, sunset_min);
            pstmt.setDate(6, setDate);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("JunctionSunriseSunsetModel updateRecord() Error: " + e);
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
}
