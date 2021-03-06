/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ts.dataEntry.Model;


import com.ts.dataEntry.tableClasses.City;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Shruti
 */
public class CityModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("CityModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchstate, String searchdistrict) {
        int noOfRows = 0;
          String query = "SELECT count(*) "
                + "FROM city AS c, district AS d, state AS s "
                + "WHERE c.district_id = d.district_id AND d.state_id = s.state_id and "
                     + " IF('" + searchstate + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+searchstate+"') and "
                 + " IF('" + searchdistrict + "' = '',  d.district_name LIKE '%%', d.district_name ='"+searchdistrict+"')  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("CityModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<City> showData(int lowerLimit, int noOfRowsToDisplay,String searchstate, String searchdistrict) {
        List<City> list = new ArrayList<City>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT c.city_id, c.city_name, d.district_name, s.state_name, c.pin_code, c.std_code "
                + "FROM city AS c, district AS d, state AS s "
                + "WHERE c.district_id = d.district_id AND d.state_id = s.state_id and "
                     + " IF('" + searchstate + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+searchstate+"') and "
                 + " IF('" + searchdistrict + "' = '',  d.district_name LIKE '%%', d.district_name ='"+searchdistrict+"')  "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                City city = new City();
                city.setCity_id(rset.getInt("city_id"));
                city.setCity_name(rset.getString("city_name"));
                city.setDistrict_name(rset.getString("district_name"));
                city.setState_name(rset.getString("state_name"));
                city.setPin_code(rset.getInt("pin_code"));
                city.setStd_code(rset.getString("std_code"));
                list.add(city);
            }
        } catch (Exception e) {
            System.out.println("CityModel showData() Error: " + e);
        }
        return list;
    }
     
       public  ByteArrayOutputStream generateOrginisationXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("OrginisationTypeStatusModel generateOrgnisitionXlsRecordList() JRException: " + e);
                }
                return bytArray;
            }
     public byte[] generateSiteList(String jrxmlFilePath,List listAll) {
        byte[] reportInbytes = null;        
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in  generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    
     public List<City> showDataReport(String searchstate, String searchdistrict) {
        List<City> list = new ArrayList<City>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT c.city_id, c.city_name, d.district_name, s.state_name, c.pin_code, c.std_code "
                + "FROM city AS c, district AS d, state AS s "
                + "WHERE c.district_id = d.district_id AND d.state_id = s.state_id and "
                     + " IF('" + searchstate + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+searchstate+"') and "
                 + " IF('" + searchdistrict + "' = '',  d.district_name LIKE '%%', d.district_name ='"+searchdistrict+"')  ";
              
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                City city = new City();
                city.setCity_id(rset.getInt("city_id"));
                city.setCity_name(rset.getString("city_name"));
                city.setDistrict_name(rset.getString("district_name"));
                city.setState_name(rset.getString("state_name"));
                city.setPin_code(rset.getInt("pin_code"));
                city.setStd_code(rset.getString("std_code"));
                list.add(city);
            }
        } catch (Exception e) {
            System.out.println("CityModel showData() Error: " + e);
        }
        return list;
    }
    
    
     
public List<String> getState() {
        String query = "SELECT state_name from state WHERE active = 'Y'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("state_name"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
public List<String> getDistrict(String state) {
    
    int st_id=getStateId(state);
        String query = "SELECT district_name from district WHERE active = 'Y' and state_id='"+st_id+"'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("district_name"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
    public int insertRecord(City city) {
        String query = "INSERT INTO city(city_name, district_id, state_id, pin_code, std_code) "
                + "VALUES(?, ?, ?, ?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city.getCity_name());
            pstmt.setInt(2, city.getDistrict_id());
            pstmt.setInt(3, city.getState_id());
            pstmt.setInt(4, city.getPin_code());
            pstmt.setString(5, city.getStd_code());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("CityModel insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(City city) {
        String query = "UPDATE city SET city_name = ?, district_id = ?, state_id = ?, pin_code = ?, std_code = ? WHERE city_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city.getCity_name());
            pstmt.setInt(2, city.getDistrict_id());
            pstmt.setInt(3, city.getState_id());
            pstmt.setInt(4, city.getPin_code());
            pstmt.setString(5, city.getStd_code());
            pstmt.setInt(6, city.getCity_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("CityModel updateRecord() Error: " + e);
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

    public int deleteRecord(int city_id) {
        String query = "DELETE FROM city WHERE city_id = " + city_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("CityModel deleteRecord() Error: " + e);
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

    public int getStateId(String stateName) {
        int state_id = -1;
        String query = "SELECT state_id FROM state WHERE state_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, stateName);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            state_id = rset.getInt("state_id");
        } catch (Exception e) {
            System.out.println("CityModel getStateId() Error: " + e);
        }
        return state_id;
    }

    public int getDistrictId(String districtName) {
        int district_id = -1;
        String query = "SELECT district_id FROM district WHERE district_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, districtName);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            district_id = rset.getInt("district_id");
        } catch (Exception e) {
            System.out.println("CityModel getDistrictId() Error: " + e);
        }
        return district_id;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPasswrod = db_userPasswrod;
    }
}


