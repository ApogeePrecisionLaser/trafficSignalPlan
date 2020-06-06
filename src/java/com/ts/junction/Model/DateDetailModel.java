/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.dataEntry.tableClasses.Pole_Type;
import com.ts.junction.tableClasses.DateDetail;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class DateDetailModel {
    
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
    
    public int getNoOfRows(String searchitem) {
        int noOfRows = 0;
          String query = "SELECT Count(*) "
                + "FROM date_detail WHERE active='Y' and "
                   + " IF('" + searchitem + "' = '',name LIKE '%%',  name ='"+searchitem+"') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("DateDetailModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<DateDetail> showData(int lowerLimit, int noOfRowsToDisplay,String searchitem) {
        List<DateDetail> list = new ArrayList<DateDetail>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT date_detail_id,name,from_date,to_date,remark "
                + "FROM date_detail WHERE active='Y' and "
                   + " IF('" + searchitem + "' = '',name LIKE '%%',  name ='"+searchitem+"') "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DateDetail dateDetail = new DateDetail();
                dateDetail.setDate_detail_id(rset.getInt("date_detail_id"));
                dateDetail.setName(rset.getString("name"));
                dateDetail.setFrom_date(rset.getString("from_date"));
               dateDetail.setTo_date(rset.getString("to_date"));
               dateDetail.setRemark(rset.getString("remark"));
                list.add(dateDetail);
            }
        } catch (Exception e) {
            System.out.println("DateDetailModel showData() Error: " + e);
        }
        return list;
    }
    public List<DateDetail> showDataReport(String searchitem) {
        List<DateDetail> list = new ArrayList<DateDetail>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT date_detail_id,name,from_date,to_date,remark "
                + "FROM date_detail WHERE active='Y' and "
                   + " IF('" + searchitem + "' = '',name LIKE '%%',  name ='"+searchitem+"') "
                ;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DateDetail dateDetail = new DateDetail();
                dateDetail.setDate_detail_id(rset.getInt("date_detail_id"));
                dateDetail.setName(rset.getString("name"));
                dateDetail.setFrom_date(rset.getString("from_date"));
               dateDetail.setTo_date(rset.getString("to_date"));
               dateDetail.setRemark(rset.getString("remark"));
                list.add(dateDetail);
            }
        } catch (Exception e) {
            System.out.println("DateDetailModel showData() Error: " + e);
        }
        return list;
    }
public List<String> getState() {
        String query = "SELECT name from date_detail WHERE active = 'Y'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("name"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
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
    public int insertRecord(DateDetail dateDetail) {
        String query = "INSERT INTO date_detail(name, from_date, to_date, remark) "
                + "VALUES(?, ?, ?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, dateDetail.getName());
        pstmt.setString(2, dateDetail.getFrom_date());
        pstmt.setString(3, dateDetail.getTo_date());
        pstmt.setString(4, dateDetail.getRemark());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("DateDEtailModel insertRecord() Error: " + e);
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

    
    
     public boolean updatePreviousRecord(int id) throws SQLException {
        PreparedStatement pstmt;
        int rowAffected = 0;
        try {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" UPDATE date_detail SET active = 'N' where date_detail_id = " + id);
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

    public int getRevisionNo(int date_id) {
        int revision_no = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT revision_no FROM date_detail where date_detail_id=" + date_id + " AND active='Y'");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            revision_no = Integer.parseInt(rset.getString(1));
            System.out.println(revision_no);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return revision_no;
    }

    public boolean updateRecord(DateDetail dateDetail) {
        String insert_query = null;
        PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0;
        try {
            boolean autoCommit = connection.getAutoCommit();
            if (!updatePreviousRecord(dateDetail.getDate_detail_id())) {
                errorOccured = true;
                message = "Day Not updated";
                msgBgColor = COLOR_ERROR;
            } else {

                int revision_no = getRevisionNo(dateDetail.getDate_detail_id()) + 1;

                insert_query = "INSERT into date_detail(date_detail_id,from_date,to_date,name, remark,revision_no ) "
                        + " VALUES(?, ?, ?, ?, ?, ?) ";
                try {
                    connection.setAutoCommit(false);

                   pstmt = connection.prepareStatement(insert_query);
                    pstmt.setInt(1, dateDetail.getDate_detail_id());
                   pstmt.setString(2, dateDetail.getFrom_date());
                    pstmt.setString(3, dateDetail.getTo_date());
                    pstmt.setString(4, dateDetail.getName());
                    pstmt.setString(5, dateDetail.getRemark());
                    pstmt.setInt(6, revision_no);
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
                    System.out.println("DayDetailModel updateRecord() Error: " + message + " Cause: " + sqlEx.getMessage());
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

    public int deleteRecord(int date_detail_id) {
        String query = "DELETE FROM date_detail WHERE date_detail_id = " + date_detail_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("DateDetailModel deleteRecord() Error: " + e);
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
}
