/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Model;

import com.ts.dataEntry.tableClasses.City;
import com.ts.log.tableClasses.LogTable;
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
    
    public List<LogTable> showData(int lowerLimit, int noOfRowsToDisplay,String junctionname,String sidename,String datetime) {
        List<LogTable> list = new ArrayList<LogTable>();
        try {
            String query = "SELECT j.junction_id,side.side_detail_id,j.junction_name,side.side_name,l.log_table_id, s.send_data, s.recieved_data, l.sms_sent_status, l.date_time, l.revision_no, l.active,s.severity_case_id, s.severity_case,l.side_aspect_name"
                    + " from log_table AS l "
                    + " inner join severity_case As s on s.severity_case_id = l.case_id "
                    + "inner join side_detail As side on side.side_detail_id = l.side_detail_id "
                    + "inner join junction as j on j.junction_id = side.junction_id "
                    + " WHERE s.active='Y' and j.final_revision='valid' and side.active='y' and "
                    + " IF('" + junctionname + "' = '',   j.junction_name LIKE '%%',  j.junction_name='"+junctionname+"') and "
                    + " IF('" + sidename + "' = '',   side.side_name LIKE '%%',  side.side_name ='"+sidename+"') order by l.log_table_id desc  "
                 //   + " IF('" + datetime + "' = '',   l.date_time LIKE '%%',  l.date_time ='"+datetime+"')  "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//              + " IF('" + searchstate + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+searchstate+"') and "
               //  + " IF('" + searchdistrict + "' = '',  d.district_name LIKE '%%', d.district_name ='"+searchdistrict+"')  "
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
                logTable.setSide_aspect_name(rset.getString("side_aspect_name"));
                list.add(logTable);
            }
//
        } catch (Exception e) {
            System.out.println("Error:SeverityLevelModel-showData--- " + e);
        }
        return list;
    }
    public List<LogTable> showDataReport(String junctionname,String sidename,String datetime) {
        List<LogTable> list = new ArrayList<LogTable>();
        try {
            String query = "SELECT j.junction_id,side.side_detail_id,j.junction_name,side.side_name,l.log_table_id, s.send_data, s.recieved_data, l.sms_sent_status, l.date_time, l.revision_no, l.active,s.severity_case_id, s.severity_case"
                    + " from log_table AS l "
                    + "join severity_case As s on s.severity_case_id = l.case_id "
                    + "join side_detail As side on side.side_detail_id = l.side_detail_id "
                    + "join junction as j on j.junction_id = side.junction_id "
                    + " WHERE s.active='Y' and j.final_revision='valid' and side.active='y' and "
                    + " IF('" + junctionname + "' = '',   j.junction_name LIKE '%%',  j.junction_name='"+junctionname+"') and "
                    + " IF('" + sidename + "' = '',   side.side_name LIKE '%%',  side.side_name ='"+sidename+"') and "
                    + " IF('" + datetime + "' = '',   l.date_time LIKE '%%',  l.date_time ='"+datetime+"') ";
                  
//              + " IF('" + searchstate + "' = '',   s.state_name LIKE '%%',  s.state_name ='"+searchstate+"') and "
               //  + " IF('" + searchdistrict + "' = '',  d.district_name LIKE '%%', d.district_name ='"+searchdistrict+"')  "
            
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
    public List<String> getState() {
        String query = "SELECT junction_name from junction WHERE final_revision = 'VALID'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("junction_name"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
    
    public List<String> getDateTime() {
        String query = "SELECT date_time FROM log_table WHERE active='Y' ";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("date_time"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
    
      public int getStateId(String stateName) {
        int state_id = -1;
        String query = "SELECT junction_id FROM junction WHERE junction_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, stateName);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            state_id = rset.getInt("junction_id");
        } catch (Exception e) {
            System.out.println("CityModel getStateId() Error: " + e);
        }
        return state_id;
    }
    
         
    public List<String> getDistrict(String state) {
    
    int st_id=getStateId(state);
        String query = "SELECT distinct side_name from side_detail WHERE active = 'Y' and junction_id='"+st_id+"'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("side_name"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
      
    public int getNoOfRows(String junctionname,String sidename,String datetime) {
         String query = "SELECT count(*)"
                    + " from log_table AS l "
                    + "join severity_case As s on s.severity_case_id = l.case_id "
                    + "join side_detail As side on side.side_detail_id = l.side_detail_id "
                    + "join junction as j on j.junction_id = side.junction_id "
                    + " WHERE s.active='Y' and j.final_revision='valid' and side.active='y' and "
                    + " IF('" + junctionname + "' = '',   j.junction_name LIKE '%%',  j.junction_name='"+junctionname+"') and "
                    + " IF('" + sidename + "' = '',   side.side_name LIKE '%%',  side.side_name ='"+sidename+"')  ";
                   // + " IF('" + datetime + "' = '',   l.date_time LIKE '%%',  l.date_time ='"+datetime+"')  ";
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
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
