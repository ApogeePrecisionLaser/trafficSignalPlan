/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ts.dataEntry.Model;

import com.ts.dataEntry.tableClasses.District;
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
public class DistrictModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("DistrictModel setConnection() Error: " + e);
        }
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

    public int getNoOfRows(String State,String District) {
        int noOfRows = 0;
           String query = "SELECT count(*) "
                + "FROM district d, state s "
                + "WHERE d.state_id = s.state_id and s.active='Y' and d.active='Y' and "
                 + " IF('" + State + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+State+"') and "
                 + " IF('" + District + "' = '',  d.district_name LIKE '%%', d.district_name ='"+District+"') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("DistrictModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<District> showData(int lowerLimit, int noOfRowsToDisplay,String State,String District) {
        List<District> list = new ArrayList<District>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT d.district_id, d.district_name, s.state_name "
                + "FROM district d, state s "
                + "WHERE d.state_id = s.state_id and s.active='Y' and d.active='Y' and "
                 + " IF('" + State + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+State+"') and "
                 + " IF('" + District + "' = '',  d.district_name LIKE '%%', d.district_name ='"+District+"') "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                District state = new District();
                state.setDistrict_id(rset.getInt("district_id"));
                state.setDistrict_name(rset.getString("district_name"));
                state.setState_name(rset.getString("state_name"));
                list.add(state);
            }
        } catch (Exception e) {
            System.out.println("DistrictModel showData() Error: " + e);
        }
        return list;
    }
    public List<District> showDataReport(String State,String District) {
        List<District> list = new ArrayList<District>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT d.district_id, d.district_name, s.state_name "
                + "FROM district d, state s "
                + "WHERE d.state_id = s.state_id and s.active='Y' and d.active='Y' and "
                 + " IF('" + State + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+State+"') and "
                 + " IF('" + District + "' = '',  d.district_name LIKE '%%', d.district_name ='"+District+"') "
             ;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                District state = new District();
                state.setDistrict_id(rset.getInt("district_id"));
                state.setDistrict_name(rset.getString("district_name"));
                state.setState_name(rset.getString("state_name"));
                list.add(state);
            }
        } catch (Exception e) {
            System.out.println("DistrictModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(District district) {
        String query = "INSERT INTO district(district_name, state_id) VALUES(?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, district.getDistrict_name());
            pstmt.setInt(2, district.getState_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("DistrictModel insertRecord() Error: " + e);
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
    public int updateRecord(District district) {
        String query = "UPDATE district SET district_name = ? WHERE district_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, district.getDistrict_name());
            pstmt.setInt(2, district.getDistrict_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("DistrictModel updateRecord() Error: " + e);
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

    public int deleteRecord(int district_id) {
        String query = "DELETE FROM district WHERE district_id = " + district_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("DistrictModel deleteRecord() Error: " + e);
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
            System.out.println("DistrictModel getStateId() Error: " + e);
        }
        return state_id;
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
        this.db_userPassword = db_userPasswrod;
    }
}

