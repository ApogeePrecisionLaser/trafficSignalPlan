/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Model;
import com.ts.dataEntry.tableClasses.Camera_Make;
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
public class Camera_MakeModel {
    
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
            System.out.println("Camera_MakeModel setConnection() Error: " + e);
        }
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

    public int getNoOfRows(String searchCammodel,String searchCammake) {
        int noOfRows = 0;
         String query="SELECT COUNT(*) FROM camera_make where active='Y' and "
                 + " IF('" + searchCammodel + "' = '',  model_no LIKE '%%', model_no ='"+searchCammodel+"') and "
                 + " IF('" + searchCammake + "' = '',  camera_make LIKE '%%', camera_make ='"+searchCammake+"')  ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Camera_MakeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
    
    public int getLastId() {
        int noOfRows = 0;
        int camera_make_id=0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT camera_make_id FROM camera_make order by camera_make_id desc limit 1").executeQuery();
            rset.next();
              
            noOfRows = rset.getInt("camera_make_id");
        } catch (Exception e) {
            System.out.println("Camera_MakeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
public List<String> getCameraMake() {
        String query = "SELECT camera_make from camera_make WHERE active = 'Y'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("camera_make"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }
public List<String> getCameraModel() {
        String query = "SELECT model_no from camera_make WHERE active = 'Y'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("model_no"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }


    public List<Camera_Make> showData(int lowerLimit, int noOfRowsToDisplay,String searchCammodel,String searchCammake) {
        List<Camera_Make> list = new ArrayList<Camera_Make>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT * FROM camera_make as c where c.active='Y' and "
                 + " IF('" + searchCammodel + "' = '',  model_no LIKE '%%', model_no ='"+searchCammodel+"') and "
                 + " IF('" + searchCammake + "' = '',  camera_make LIKE '%%', camera_make ='"+searchCammake+"')  "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Camera_Make camera_make = new Camera_Make();
                camera_make.setCamera_make_id(rset.getInt("camera_make_id"));
                camera_make.setCamera_make(rset.getString("camera_make"));
                  camera_make.setRemark(rset.getString("remark"));
                  camera_make.setCamera_model(rset.getString("model_no"));
                list.add( camera_make);
            }
        } catch (Exception e) {
            System.out.println("Camera_MakeModel showData() Error: " + e);
        }
        return list;
    }
    public List<Camera_Make> showData1(String searchCammodel,String searchCammake) {
        List<Camera_Make> list = new ArrayList<Camera_Make>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT * FROM camera_make as c where c.active='Y' and "
                 + " IF('" + searchCammodel + "' = '',  model_no LIKE '%%', model_no ='"+searchCammodel+"') and "
                 + " IF('" + searchCammake + "' = '',  camera_make LIKE '%%', camera_make ='"+searchCammake+"')  ";
              
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Camera_Make camera_make = new Camera_Make();
                camera_make.setCamera_make_id(rset.getInt("camera_make_id"));
                camera_make.setCamera_make(rset.getString("camera_make"));
                  camera_make.setRemark(rset.getString("remark"));
                  camera_make.setCamera_model(rset.getString("model_no"));
                list.add( camera_make);
            }
        } catch (Exception e) {
            System.out.println("Camera_MakeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(Camera_Make camera_make) {
        String query = "INSERT INTO camera_make (camera_make,remark,model_no) VALUES(?,?,?) ";
//        int last_id = getLastId();
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1,last_id+1 );
            pstmt.setString(1, camera_make.getCamera_make());
            pstmt.setString(2, camera_make.getRemark());
            pstmt.setString(3,camera_make.getCamera_model());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Camera_MakeModel insertRecord() Error: " + e);
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

    public int updateRecord(Camera_Make camera_make) {
        String query = "UPDATE camera_make SET camera_make = ? , remark = ?, model_no=? WHERE camera_make_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, camera_make.getCamera_make());
             pstmt.setString(2, camera_make.getRemark());
           
            pstmt.setString(3, camera_make.getCamera_model()); 
            pstmt.setInt(4, camera_make.getCamera_make_id());
            
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Camera_MakeModel updateRecord() Error: " + e);
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

    public int deleteRecord(int camera_make_id) {
        String query = "UPDATE camera_make SET ACTIVE='N' WHERE camera_make_id = " + camera_make_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Camera_MakeModel deleteRecord() Error: " + e);
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



