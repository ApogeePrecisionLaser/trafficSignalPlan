/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.dataEntry.tableClasses.Pole_Type;
import com.ts.junction.tableClasses.DayDetail;
import com.ts.junction.tableClasses.JunctionPlanMap;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
public class DayDetailModel {

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

    public int getNoOfRows(String searchitem,String searchday) {
        int noOfRows = 0;
         String query = "SELECT count(*) "
                + "FROM day_detail as d, junction as j where d.junction_id=j.junction_id and j.final_revision='VALID' and d.active='Y'and "
             + " IF('" + searchitem + "' = '',j.junction_name LIKE '%%',  j.junction_name ='"+searchitem+"') and "
                   + " IF('" + searchday + "' = '',d.day LIKE '%%',  d.day ='"+searchday+"') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("DayDetailModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
 public List<String> getState() {
        String query = "SELECT junction_name from junction WHERE final_revision = 'VALID' ";
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
 public List<String> getDay() {
        String query = "SELECT day from day_detail WHERE active = 'Y' ";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("day"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
       public List<DayDetail> showDataReport(String searchitem,String searchday) {
        List<DayDetail> list = new ArrayList<DayDetail>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT d.day_detail_id,d.day_name,d.day,j.remark,j.junction_name "
                + "FROM day_detail as d, junction as j where d.junction_id=j.junction_id and j.final_revision='VALID' and d.active='Y'and "
             + " IF('" + searchitem + "' = '',j.junction_name LIKE '%%',  j.junction_name ='"+searchitem+"') and "
                  + " IF('" + searchday + "' = '',d.day LIKE '%%',  d.day ='"+searchday+"') "
           ;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DayDetail dateDetail = new DayDetail();
                dateDetail.setDay_detail_id(rset.getInt("day_detail_id"));

                dateDetail.setDay_name(rset.getString("day_name"));
                dateDetail.setDay(rset.getString("day"));
                dateDetail.setJunction_name(rset.getString("junction_name"));
                dateDetail.setRemark(rset.getString("remark"));
                list.add(dateDetail);
            }
        } catch (Exception e) {
            System.out.println("DayDetailModel showData() Error: " + e);
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
    public int getJunctionID(String junction_name) {
        int junction_id = 0;
        String query = "SELECT junction_id "
                + " FROM junction WHERE junction_name= ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, junction_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("junction_id");
            }
        } catch (Exception ex) {
            System.out.println("JunctionModel getCityID() Error: " + ex);
        }
        return junction_id;
    }

    public List<DayDetail> showData(int lowerLimit, int noOfRowsToDisplay,String searchitem,String searchday) {
        List<DayDetail> list = new ArrayList<DayDetail>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT d.day_detail_id,d.day_name,d.day,j.remark,j.junction_name "
                + "FROM day_detail as d, junction as j where d.junction_id=j.junction_id and j.final_revision='VALID' and d.active='Y'and "
             + " IF('" + searchitem + "' = '',j.junction_name LIKE '%%',  j.junction_name ='"+searchitem+"') and "
                + " IF('" + searchday + "' = '',d.day LIKE '%%',  d.day ='"+searchday+"') "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                DayDetail dateDetail = new DayDetail();
                dateDetail.setDay_detail_id(rset.getInt("day_detail_id"));

                dateDetail.setDay_name(rset.getString("day_name"));
                dateDetail.setDay(rset.getString("day"));
                dateDetail.setJunction_name(rset.getString("junction_name"));
                dateDetail.setRemark(rset.getString("remark"));
                list.add(dateDetail);
            }
        } catch (Exception e) {
            System.out.println("DayDetailModel showData() Error: " + e);
        }
        return list;
    }

    public List<String> getJunctionName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select junction_name from junction where active='Y' "
                + " group by junction_name order by id desc ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String junction_name = rset.getString("junction_name");
                if (junction_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(junction_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Manufacturer Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside CommandModel - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
    }

    public int insertRecord(DayDetail dayDetail) {
        String query = "INSERT INTO day_detail(day_name, day, junction_id) "
                + "VALUES(?, ?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, dayDetail.getDay_name());
            pstmt.setString(2, dayDetail.getDay());
            pstmt.setInt(3, dayDetail.getJunction_id());
           
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("DayDEtailModel insertRecord() Error: " + e);
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
            pstmt = connection.prepareStatement(" UPDATE day_detail SET active = 'N' where day_detail_id = " + id);
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

    

    public boolean updateRecord(DayDetail dayDetail) {
        String insert_query = null;
        PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0;
        try {
            boolean autoCommit = connection.getAutoCommit();
            if (!updatePreviousRecord(dayDetail.getDay_detail_id())) {
                errorOccured = true;
                message = "Day Not updated";
                msgBgColor = COLOR_ERROR;
            } else {

                int revision_no = getRevisionNo(dayDetail.getDay_detail_id()) + 1;

                insert_query = "INSERT into day_detail(day_detail_id,day_name, day, junction_id,revision_no ) "
                        + " VALUES(?, ?, ?, ?, ?) ";
                try {
                    connection.setAutoCommit(false);

                   pstmt = connection.prepareStatement(insert_query);
                    pstmt.setInt(1, dayDetail.getDay_detail_id());
                   pstmt.setString(2, dayDetail.getDay_name());
                    pstmt.setString(3, dayDetail.getDay());
                    pstmt.setInt(4, dayDetail.getJunction_id());
                     
                    pstmt.setInt(5, revision_no);
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

    public int getRevisionNo(int day_id) {
        int revision_no = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT revision_no FROM day_detail where day_detail_id=" + day_id + " AND active='Y'");
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            revision_no = Integer.parseInt(rset.getString(1));
            System.out.println(revision_no);
        } catch (Exception e) {
            System.out.println("PlanInfoModel getNoOfPlans() Error: " + e);
        }
        return revision_no;
    }

    public int deleteRecord(int day_detail_id) {
        String query = "UPDATE day_detail Set active = 'N' WHERE day_detail_id = " + day_detail_id;
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

    public List<String> getSearchJunctionName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select junction_name"
                + " from junction as j "
                + " where j.final_revision='VALID'"
                + " group by junction_name order by junction_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String junction_name = rset.getString("junction_name");
                if (junction_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(junction_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such junction_name Name exists.......");
            }
        } catch (Exception e) {
            System.out.println(" ERROR inside Model - " + e);
            message = "Something going wrong";
            //messageBGColor = "red";
        }
        return list;
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
