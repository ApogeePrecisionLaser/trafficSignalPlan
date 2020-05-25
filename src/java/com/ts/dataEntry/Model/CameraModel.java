 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Model;
import com.ts.dataEntry.tableClasses.Camera;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author DELL
 */
public class CameraModel {
    
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
            System.out.println("CameraModel setConnection() Error: " + e);
        }
    }
      public String getRepositoryPath(){
  String destination_path = "";
          String query = "SELECT destination_path FROM image_destination";

     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next())
             destination_path = rs.getString("destination_path");
     }catch(Exception ex){
         System.out.println("ERROR: " + ex);
     }
     return destination_path;
 }
    
   public String getdateTime(int id){
  String created_at = "";
          String query = "SELECT created_at FROM camera where camera_id='"+id+"'";

     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next())
             created_at = rs.getString("created_at");
     }catch(Exception ex){
         System.out.println("ERROR: " + ex);
     }
     return created_at;
 }
       public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdirs();
        }
        return result;
    }
    

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT COUNT(*) FROM camera where active='y' ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("CameraModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
    
    public int getLastId() {
        int noOfRows = 0;
        int camera_id=0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT camera_id FROM camera order by camera_id desc limit 1").executeQuery();
            rset.next();
              
            noOfRows = rset.getInt("camera_id");
        } catch (Exception e) {
            System.out.println("CameraModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

//    public List<Camera> showData(int lowerLimit, int noOfRowsToDisplay, int junction_id, int side_no) {
           public List<Camera> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<Camera> list = new ArrayList<Camera>();
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        String query = "SELECT * FROM camera c,camera_type ct,side_detail sd ORDER BY c.camera_id LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//        String query = "SELECT c.*, j.junction_name, sd.side_no, sd.side_name, sd.junction_id, cm.camera_make, ct.camera_type FROM camera c,camera_type ct,side_detail sd, camera_make cm, junction j " +
//                       "where c.camera_type_id = ct.camera_type_id and c.camera_make_id = cm.camera_make_id and " +
//                       "c.side_detail_id = sd.side_detail_id and sd.junction_id = j.junction_id and j.final_revision = 'VALID' and sd.junction_id = "+junction_id+" and sd.side_no = "+side_no +
//                       " ORDER BY c.camera_id LIMIT "+lowerLimit+", "+noOfRowsToDisplay;
        String query ="select c.camera_id,c.camera_ip,ct.camera_type,cm.camera_make,j.junction_name,sd.side_no,c.remark,c.latitude,c.longitude,c.camera_facing,c.lane_no,cm.model_no from junction as j inner join side_detail as  sd on j.junction_id=sd.junction_id " +
                        " right join camera as c on  c.side_detail_id=sd.side_detail_id " +
                        " right join camera_type as ct on ct.camera_type_id=c.camera_type_id right join camera_make as cm on cm.camera_make_id=c.camera_make_id "+
                     " where j.final_revision = 'VALID' and c.active='Y' and sd.active='Y' "
                + " LIMIT "+lowerLimit+", "+noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Camera camera = new Camera();
               camera.setCamera_id(rset.getInt("camera_id"));
//               camera.setCamera_id(camera_id);
                camera.setCamera_ip(rset.getString("camera_ip"));
                camera.setCamera_make(rset.getString("camera_make"));
                camera.setCamera_type(rset.getString("camera_type"));
//                camera.setJunction_id(junction_id);
                camera.setJunction_name(rset.getString("junction_name"));
                camera.setSide_no(rset.getInt("side_no"));
                  camera.setRemark(rset.getString("remark"));
                  camera.setLatitude(rset.getString("latitude"));
                  camera.setLongitude(rset.getString("longitude"));
                  camera.setCamerafacing(rset.getString("camera_facing"));
                  camera.setLane_no(rset.getString("lane_no"));
                  camera.setCamera_model(rset.getString("model_no"));
                list.add( camera);
            }
        } catch (Exception e) {
            System.out.println("CameraModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(Camera camera) {
        int j_id=getjunctionId(camera.getJunction_name());
       int sideDetailId = getSideDetailId(camera.getSide_no(),j_id);
    int id=0;
        int cameraMakeId = getCamerMakeId(camera.getCamera_make(),camera.getCamera_model());
        int cameraTypeId = getCamerTypeId(camera.getCamera_type());
        String query = "INSERT INTO camera (camera_ip,camera_type_id,camera_make_id,side_detail_id,remark,latitude,longitude,camera_facing,image_folder_path,created_at,lane_no) VALUES(?,?,?,?,?,?,?,?,?,?,?) ";
//        int last_id = getLastId();
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setInt(1,last_id+1 );
            pstmt.setString(1,camera.getCamera_ip());
            pstmt.setInt(2, cameraTypeId);
            pstmt.setInt(3, cameraMakeId);
            pstmt.setInt(4,sideDetailId );
            pstmt.setString(5,camera.getRemark());
            pstmt.setString(6, camera.getLatitude());
            pstmt.setString(7, camera.getLongitude());
            pstmt.setString(8, camera.getCamerafacing());
            pstmt.setString(9, camera.getImage_folder());
            pstmt.setString(10,camera.getCreated_at());
            pstmt.setString(11, camera.getLane_no());
            rowsAffected = pstmt.executeUpdate();
             if (rowsAffected > 0) {
                String query1 = "select last_insert_id()";
                pstmt = (PreparedStatement) connection.prepareStatement(query1);
                ResultSet rset = pstmt.executeQuery();
                while (rset.next()) {
                    id = rset.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("Camera_Model insertRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return id;
    }
      public int getSideDetailId( int side_no,int jid) {
//        String query = "SELECT side_detail_id FROM side_detail where junction_id = "+junction_id+" and side_no="+side_no;
        String query ="SELECT sd.side_detail_id FROM side_detail as sd inner join junction as j on sd.junction_id=j.junction_id where  "+
          "sd.active='Y'  and sd.side_no= '"+side_no+"' and sd.junction_id='"+jid+"'";
        int id = 0;
        try{
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            id = rset.getInt("side_detail_id");
        } catch (Exception e) {
            System.out.println("Camera_Model getSideDetailId() Error: " + e);
        }
        return id;
     }
     
     public int getjunctionId( String jname) {
//        String query = "SELECT side_detail_id FROM side_detail where junction_id = "+junction_id+" and side_no="+side_no;
        String query ="SELECT junction_id from junction where junction_name='"+jname+"'";
        int id = 0;
        try{
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            id = rset.getInt("junction_id");
        } catch (Exception e) {
            System.out.println("Camera_Model getSideDetailId() Error: " + e);
        }
        return id;
     }
     
     public int getCamerMakeId(String camera_make,String model) {
        String query = "SELECT camera_make_id FROM camera_make where camera_make = '"+camera_make+"' and model_no='"+model+"'";
        int id = 0;
        try{
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            id = rset.getInt("camera_make_id");
        } catch (Exception e) {
            System.out.println("Camera_Model getCamerMakeId() Error: " + e);
        }
        return id;
     }
     
     public int getCamerTypeId(String camera_type) {
        String query = "SELECT camera_type_id FROM camera_type where camera_type = '"+camera_type+"'";
        int id = 0;
        try{
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            id = rset.getInt("camera_type_id");
        } catch (Exception e) {
            System.out.println("Camera_Model getCamerTypeId() Error: " + e);
        }
        return id;
     }
 public int updateRecord(Camera camera) {
        int rowAffected = 0;
        int id=0;
         int a=0;
        boolean status = false;
  int j_id=getjunctionId(camera.getJunction_name());
       int sideDetailId = getSideDetailId(camera.getSide_no(),j_id);
       int cameraMakeId = getCamerMakeId(camera.getCamera_make(),camera.getCamera_model());
        int cameraTypeId = getCamerTypeId(camera.getCamera_type());
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query1 = " select max(revision_no) revision_no from camera where camera_id='" + camera.getCamera_id() + "' and active='Y'  ORDER BY revision_no DESC;";
//        String query2 = " UPDATE device_command_map SET active=? WHERE device_command_id = ? AND revision_no = ? ";
//        String query3 = " insert into device_command_map(device_command_id,device_id,command_id,operation_id,remark,revision_no,active,order_no,delay) VALUES (?,?,?,?,?,?,?,?,?) ";
//
        String query2 = "update camera SET active=? where  revision_no=? and camera_id=? ";
       String query3 = "INSERT INTO camera (camera_ip,camera_type_id,camera_make_id,side_detail_id,remark,latitude,longitude,camera_facing,image_folder_path,revision_no,created_at,LANE_NO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

        int updateRowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            ps = (PreparedStatement) connection.prepareStatement(query1);
            rs = ps.executeQuery();
            if (rs.next()) {
                ps = (PreparedStatement) connection.prepareStatement(query2);
                ps.setString(1, "N");

                ps.setInt(2, rs.getInt("revision_no"));
                ps.setInt(3, camera.getCamera_id());
                updateRowsAffected = ps.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("revision_no") + 1;
  ps = connection.prepareStatement(query3);
//            pstmt.setInt(1,last_id+1 );
            ps.setString(1,camera.getCamera_ip());
            ps.setInt(2, cameraTypeId);
            ps.setInt(3, cameraMakeId);
            ps.setInt(4,sideDetailId );
            ps.setString(5,camera.getRemark());
            ps.setString(6, camera.getLatitude());
            ps.setString(7, camera.getLongitude());
            ps.setString(8, camera.getCamerafacing());
            ps.setString(9, camera.getImage_folder());
            ps.setInt(10, rev);
            ps.setString(11,camera.getCreated_at());
              ps.setString(12, camera.getLane_no());
       
                   a = ps.executeUpdate();
                    if (a > 0) {
                        
                        connection.commit();
                        status = true;
                    } else {
                        connection.rollback();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(" Error: " + e);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                //  Logger.getLogger(DeviceOperationCommandModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (a>0) {
            message = "Record updated successfully......";
   msgBgColor = "yellow";
            System.out.println("Inserted");
        } else {
            message = "Record Not updated Some Error!";
            
            msgBgColor = "red";
        
        }

        return id;

    }
//    public int updateRecord(Camera camera) {
//        String query = "UPDATE camera SET camera_ip = ? , remark = ?,lattitude=?,longitude=?,camera_facing=? WHERE camera_id = ? ";
//        int rowsAffected = 0;
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//             pstmt.setString(1, camera.getCamera_ip());
//             pstmt.setString(2, camera.getRemark());
//             pstmt.setInt(3, camera.getCamera_id());
//             pstmt.setString(4, camera.getLatitude());
//            pstmt.setString(5, camera.getLongitude());
//           pstmt.setString(6, camera.getCamerafacing());
//            rowsAffected = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("CameraModel updateRecord() Error: " + e);
//        }
//        if (rowsAffected > 0) {
//            message = "Record updated successfully.";
//            msgBgColor = COLOR_OK;
//        } else {
//            message = "Cannot update the record, some error.";
//            msgBgColor = COLOR_ERROR;
//        }
//        return rowsAffected;
//    }
//    
//   public String getJunctionName(int junction_id) {
//        String query = "SELECT junction_name from junction WHERE final_revision = 'VALID'";
//       String junctionName = "";
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            ResultSet rset = pstmt.executeQuery();
//            rset.next();
//       }catch (Exception e) {
//           System.out.println("CameraModel getJunctionName() Error: " + e);
//        }
//        return junctionName;
//   }
// 
    
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
    
    
    //-----------------
    
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
    public List<String> getCameraModel(String cam_make) {
        String query = "SELECT c.model_no from camera_make c WHERE c.active = 'Y' and c.camera_make='"+cam_make+"'";
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
    
    public List<String> getCameraType() {
        String query = "SELECT camera_type from camera_type WHERE active = 'Y'";
        List<String> cameraMakeList = new ArrayList();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while(rset.next()){
                cameraMakeList.add(rset.getString("camera_type"));
            }
            
        }catch (Exception e) {
            System.out.println("CameraModel getJunctionName() Error: " + e);
        }
        return cameraMakeList;
    }

    public int deleteRecord(int camera_id) {
        String query = "UPDATE camera SET ACTIVE='N' WHERE camera_id =" + camera_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("CameraModel deleteRecord() Error: " + e);
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
    
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection CameraModel:" + e);
        }
    }

 }



