/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.History;
import com.ts.junction.tableClasses.SlaveInfo;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Shruti
 */
public class SlaveInfoModel extends HttpServlet {

    private static Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    String image_uploaded_for_column = null, uploaded_table = null, destination_path;
    private List<File> list;

    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = (Connection) DriverManager.getConnection(connectionString, db_userName, db_userPassword);
        } catch (Exception e) {
            System.out.println("Image setConnection() Error: " + e);
        }
    }

    public boolean validateSlaves(List<SlaveInfo> planInfoList) {
        return true;
    }

    public int getNoOfSlaves(int junction_id, int program_version_no) {
        int noOfSides = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT no_of_sides FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfSides = rset.getInt(1);
            System.out.println(noOfSides);
        } catch (Exception e) {
            System.out.println("SlaveInfoModel getNoOfSlaves() Error: " + e);
        }
        return noOfSides;
    }

    ///////////////////////upload image part start
    public String getRepositoryPath() {
        String destination_path = "";
        String query = " SELECT destination_path FROM image_destination where image_destination_id = 1";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                destination_path = rs.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return destination_path;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdirs();
        }
        return result;
    }

    public String getdestinationPath(int general_image_details_id, String image_name) {
        String query;
        String destination = "";
        query = " SELECT id.destination_path from image_destination AS id , general_image_details as g"
                + " WHERE id.image_destination_id=g.image_destination_id AND g.general_image_details_id= '" + general_image_details_id + "' and "
                + "g.image_name='" + image_name + "'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                destination = rset.getString("destination_path");
            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return destination;
    }

    //////////////upload image part end
    public int writeImage(String imageName, List<File> file) {
        boolean isCreated = false;
        System.out.println("Start to write payment images in Repository");
        try {
            File srcfile = null;
            //      String dayOfMonthFolder = createAppropriateDirectories(destination_path);
            //File folder = new File(dayOfMonthFolder);
            boolean isuploaded = false;
            Iterator<File> fileItr = file.iterator();
            //  int number_of_file = folder.list().length;
            while (fileItr.hasNext()) {
//                Object image = fileItr.next();
//                tempSource = image.toString();
                //  number_of_file++;
                srcfile = fileItr.next();
                // String ext = srcfile.getName().replace(".", "%#");
                //ext = ext.split("%#")[1];
                String image = srcfile.toString();
                int index = image.indexOf('.');
                System.out.println(index);
                String ext = image.substring(index, image.length());
                // String image_name = imageName + "_" + number_of_file + ext;
                // System.out.println("" + image_name);
                // String forlder_path = dayOfMonthFolder + "/" + imageName + "_" + number_of_file;
                getRepositoryPath();
                File file1 = new File(destination_path);

                if (!file1.exists()) {
                    isCreated = file1.mkdirs();
                }
                File desfile = new File(destination_path + "/" + imageName);
                isuploaded = srcfile.renameTo(desfile);

                if (isuploaded) {
                    message = "Image Uploaded Successfully.";
                    msgBgColor = COLOR_OK;

                }

            }
            File deleteFile = new File(getRepositoryPath());
            deleteDirectory(deleteFile);
        } catch (Exception ex) {
            System.out.println("File write error" + ex);
            message = "Cannot upload the image, some error.";
            msgBgColor = COLOR_ERROR;
            PreparedStatement pstmt;
            int rowsAffected = 0, id;
            String query, query1;
            query = "UPDATE general_image_details"
                    + "SET general_image_details_id = NULL  "
                    + " where image_name = ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query);
                pstmt.setString(1, imageName);
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: GeneralImageDetailsModel-record cannot updated when image is not uploaded successfully-" + e);
            }
            query1 = "DELETE from general_image_details WHERE general_image_details_id= ? ";
            try {
                pstmt = (PreparedStatement) connection.prepareStatement(query1);
                pstmt.setInt(1, getgeneral_image_details_id(imageName));
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: ReadMailModel-record cannot deleted when image is not uploaded successfully-" + e);
            }
        }
        return message.equals("Image Uploaded Successfully.") ? 1 : 0;
    }

    public int getgeneral_image_details_id(String image_name) {
        String query;
        int key_person_id = 0;
        query = "select general_image_details_id from general_image_details where image_name='" + image_name + "' and active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {

                key_person_id = rset.getInt("general_image_details_id");

            }

        } catch (Exception ex) {
            System.out.println("Error: UploadBillImageModel-getimage_destination_id-" + ex);
        }
        return key_person_id;
    }

    public List<String> getPoleType() {
        int noOfSides = 0;
        PreparedStatement pstmt;
        List<String> set = new ArrayList<String>();
        try {
            pstmt = connection.prepareStatement(" SELECT distinct pole_type FROM pole_type WHERE active = 'Y' ");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                set.add(rset.getString("pole_type"));
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel getPoleType() Error: " + e);
        }
        return set;
    }
    
    public JSONObject showDataBean4()
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();      
       // String query = " select position_data1,position_data2 from position where active='Y' order by position_id desc LIMIT 2";
        String query = " SELECT distinct pole_type FROM pole_type WHERE active = 'Y' ";
                   
        try
        {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next())
            {
                JSONObject json1 = new JSONObject();
                String pole_type1 = rset.getString("pole_type");                               
                json1.put("pole_type1", pole_type1);                
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        json.put("data", jsonArray);
        json.put("cordinateLength", jsonArray.size());
        return json;
    }
    public JSONObject showDataBean5()
    {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();      
       // String query = " select position_data1,position_data2 from position where active='Y' order by position_id desc LIMIT 2";
        String query = " SELECT distinct position FROM position WHERE active = 'Y' ";
                   
        try
        {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next())
            {
                JSONObject json1 = new JSONObject();
                String position1 = rset.getString("position");                               
                json1.put("position1", position1);                
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        System.out.println("json array size -"+jsonArray);
        json.put("data", jsonArray);
        json.put("cordinateLength", jsonArray.size());
        return json;
    }
    //------------------------------------------------------
     public List<History> showDataBean6() {
        List<History> list = new ArrayList<History>();
        String revision="VALID";
        int rev = 3;
        String query = "select junction_id,junction_name,no_of_sides,no_of_plans,latitude,longitude,path from junction where final_revision='VALID'";
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
    //-------------------------------------
         
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
    public List<String> getSideName(int junction_id, int side_no) {
        PreparedStatement pstmt;
         List<String> set = new ArrayList<String>();
        try {
            pstmt = connection.prepareStatement("SELECT side"+side_no+"_name FROM junction WHERE junction_id="+junction_id+" AND final_revision='VALID'");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                set.add(rset.getString("side"+side_no+"_name"));
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel getSideName() Error: " + e);
        }
        return set;
    }

    public List<String> getPosition() {
        int noOfSides = 0;
        PreparedStatement pstmt;
        List<String> set = new ArrayList<String>();
        try {
            pstmt = connection.prepareStatement(" SELECT distinct position FROM position WHERE active = 'Y' ");

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                set.add(rset.getString("position"));
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel getPosition() Error: " + e);
        }
        return set;
    }

    public int getLastId() {
        int noOfRows = 0;
        int camera_id = 0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT side_detail_id FROM side_detail order by side_detail_id desc limit 1").executeQuery();
            rset.next();

            noOfRows = rset.getInt("side_detail_id");
        } catch (Exception e) {
            System.out.println("SlaveInfoModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public boolean insertRecord(SlaveInfo slaveInfoList) throws SQLException {
        String insert_query = null, update_query = null;
        insert_query = "INSERT into side_detail(junction_id, side_no, revision_no, side_name, "
                + "pole_type_id, position_id, primary_h_aspect_no, primary_v_aspect_no, secondary_h_aspect_no, secondary_v_aspect_no, "
                + "image_path, vehicle_detection, count_down, no_of_lane,rlvd,anpr,pa_system,image_folder,side_detail_id,longitude,lattitude,active)"
                + " VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,'Y') ";

        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        boolean errorOccured = false;
        boolean isUpdated = false;
        int rowsAffected = 0,rowsAffected1 = 0,rowsAffected2 = 0;
        int junction_id = 0, program_version_no = 0;
        int last_id = getLastId();

        boolean ifExsist = checkIfExsist(slaveInfoList.getJunction_id(), slaveInfoList.getSide_no());
        if (!ifExsist) {

            String imageQuery = "INSERT INTO general_image_details (image_name, image_destination_id) "
                    + " VALUES(?, ?)";
            String update_slave_query = "UPDATE side_detail SET final_revision = 'EXPIRED' WHERE junction_id= ? AND  final_revision='VALID' ";

            try {
                boolean autoCommit = connection.getAutoCommit();
                try {
                    connection.setAutoCommit(false);
                    //     for (SlaveInfo slaveInfo : slaveInfoList) {
                    rowsAffected = 0;
                    String pole_type = slaveInfoList.getPole_type();
                    int pole_type_id = getPoleTypeId(pole_type);
                    String position = slaveInfoList.getPosition();
                    int position_id = getPositionId(position);
                    junction_id = slaveInfoList.getJunction_id();
                    program_version_no = slaveInfoList.getProgram_version_no();

                    // if this is first entry of plan_no, there won't be any record to update expire.
                    boolean firstflag = checkSlaveEntry(junction_id, slaveInfoList.getSide_no());
                    if (!firstflag && !isUpdated) {
                        pstmt = connection.prepareStatement(update_slave_query);
                        pstmt.setInt(1, slaveInfoList.getJunction_id());
//                        pstmt.setInt(2, slaveInfoList.getProgram_version_no());
                        rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            isUpdated = true;
                            rowsAffected = 0;
                            pstmt.close();
                        } else {
                            throw new SQLException("previous record is not updated");
                        }
                    }
                   //---------------------------------------------------------------------------------------------------------------------
                    pstmt = connection.prepareStatement(insert_query);
                    pstmt.setInt(1, slaveInfoList.getJunction_id());
                    pstmt.setInt(2, slaveInfoList.getSide_no());
                    pstmt.setInt(3, slaveInfoList.getRevision_no());
                    pstmt.setString(4, slaveInfoList.getSideName());
                    pstmt.setInt(5, pole_type_id);
                    pstmt.setInt(6, position_id);
                    pstmt.setInt(7, slaveInfoList.getPrimary_h_aspect_no());
                    pstmt.setInt(8, slaveInfoList.getPrimary_v_aspect_no());
                    pstmt.setInt(9, slaveInfoList.getSecondary_h_aspect_no());
                    pstmt.setInt(10, slaveInfoList.getSecondary_v_aspect_no());
                    pstmt.setString(11, slaveInfoList.getImage_path());
                    pstmt.setString(12, slaveInfoList.getVehicle_detection());
                    pstmt.setString(13, slaveInfoList.getCount_down());
                    pstmt.setInt(14, slaveInfoList.getNo_of_lane());
                    pstmt.setString(15, slaveInfoList.getRlpd());
                    pstmt.setInt(16, slaveInfoList.getAnpr());
                    pstmt.setString(17, slaveInfoList.getPa_system());
                    pstmt.setString(18, slaveInfoList.getImage_folder());
                    pstmt.setInt(19, last_id + 1);
                    pstmt.setDouble(20, slaveInfoList.getLongitude());
                    pstmt.setDouble(21, slaveInfoList.getLatitude());
                    rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Finally commit the connection.
                        connection.commit();
                        message = "Data has been saved successfully.";
                        msgBgColor = COLOR_OK;
                    } else {
                        throw new SQLException("All records were NOT saved.");
                    }

                } catch (SQLException sqlEx) {
                    errorOccured = true;
                    connection.rollback();
                    message = "Could NOT save all data , some error.";
                    msgBgColor = COLOR_ERROR;
                    System.out.println("SlaveInfoModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
                } finally {
                    pstmt.close();
                    connection.setAutoCommit(autoCommit);
                }
            } catch (Exception e) {
                System.out.println("SlaveInfoModel insert() Error: " + e);
            }
            // return !errorOccured;
        } else {
            boolean autoCommit = connection.getAutoCommit();
            update_query = "UPDATE side_detail SET revision_no = 0 , active = 'N' WHERE junction_id= ?  AND revision_no = 0 AND side_no = ? ";
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(update_query);
                pstmt.setInt(1, slaveInfoList.getJunction_id());
                pstmt.setInt(2, slaveInfoList.getSide_no());
                rowsAffected1 = pstmt.executeUpdate();
                if(rowsAffected1 > 0) {
                    String pole_type = slaveInfoList.getPole_type();
                    int pole_id = getPoleTypeId(pole_type);
                    String position = slaveInfoList.getPosition();
                    int position_id = getPositionId(position);
                    pstmt1 = connection.prepareStatement(insert_query);
                    pstmt1.setInt(1, slaveInfoList.getJunction_id());
                    pstmt1.setInt(2, slaveInfoList.getSide_no());
                    pstmt1.setInt(3, slaveInfoList.getRevision_no()+1);
                    pstmt1.setString(4, slaveInfoList.getSideName());
                    pstmt1.setInt(5, pole_id);
                    pstmt1.setInt(6, position_id);
                    pstmt1.setInt(7, slaveInfoList.getPrimary_h_aspect_no());
                    pstmt1.setInt(8, slaveInfoList.getPrimary_v_aspect_no());
                    pstmt1.setInt(9, slaveInfoList.getSecondary_h_aspect_no());
                    pstmt1.setInt(10, slaveInfoList.getSecondary_v_aspect_no());
                    pstmt1.setString(11, slaveInfoList.getImage_path());
                    pstmt1.setString(12, slaveInfoList.getVehicle_detection());
                    pstmt1.setString(13, slaveInfoList.getCount_down());
                    pstmt1.setInt(14, slaveInfoList.getNo_of_lane());
                    pstmt1.setString(15, slaveInfoList.getRlpd());
                    pstmt1.setInt(16, slaveInfoList.getAnpr());
                    pstmt1.setString(17, slaveInfoList.getPa_system());
                    pstmt1.setString(18, slaveInfoList.getImage_folder());
                    pstmt1.setInt(19, slaveInfoList.getSide_detail_id());
                    pstmt1.setDouble(20, slaveInfoList.getLongitude());
                    pstmt1.setDouble(21, slaveInfoList.getLatitude());
                    rowsAffected2 = pstmt1.executeUpdate();
                    if(rowsAffected2 == 0){
                        connection.commit();
                        message = "Data has been updated successfully.";
                        msgBgColor = COLOR_OK;
                    }
                }
                    
            } catch (SQLException sqlEx) {
                errorOccured = true;
                connection.rollback();
                message = "Could NOT save all data , some error.";
                msgBgColor = COLOR_ERROR;
                System.out.println("SlaveInfoModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
            }finally {
                    pstmt.close();
                    connection.setAutoCommit(autoCommit);
                }
        }

        return false;

    }

    public boolean checkIfExsist(int junction_id, int program_version_no) {
        int count = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT count(*) AS count FROM side_detail WHERE junction_id= ? AND side_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            count = rset.getInt("count");
        } catch (Exception ex) {

        }
        return count > 0;
    }

    public int getPoleTypeId(String poleType) {
        int pole_type_id = 0;
        String query = " SELECT pole_type_id FROM pole_type "
                + " WHERE pole_type= '" + poleType + "' AND active = 'Y' ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                pole_type_id = rset.getInt("pole_type_id");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getPoleTypeId() error" + e);
        }
        return pole_type_id;
    }

    public boolean checkSideNoExsist(int side_no, int junction_id) {
        String Query = "SELECT count(*) as total FROM side_detail "
                + " WHERE side_no= " + side_no + " AND junction_id = " + junction_id;
        int count = 0;
        boolean exsist = false;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(Query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                count = rset.getInt("total");
            }

            if (count > 0) {
                exsist = true;
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:checkSideNoExsist() error" + e);
        }
        return exsist;
    }

    public int getPositionId(String position) {
        int position_id = 0;
        String query = " SELECT position_id FROM position "
                + " WHERE position= '" + position + "' AND active = 'Y' ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                position_id = rset.getInt("position_id");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getPositionId() error" + e);
        }
        return position_id;
    }

    public String getjunctionName(int junction_id) {
        String junction_name = null;
        String query = " SELECT junction_name FROM junction "
                + " WHERE junction_id= ? AND final_revision = 'VALID' ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_name = rset.getString("junction_name");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getjunctionName() error" + e);
        }
        return junction_name;
    }

    public String getSideName(int junction_id, int program_version_no, int side_no) {
        String junction_name = null;
        String query = " SELECT side" + side_no + "_name FROM junction "
                + " WHERE junction_id= ? AND program_version_no = ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_name = rset.getString("side" + side_no + "_name");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getSideName() error " + e);
        }
        return junction_name;
    }
  public String getSideNameImage(int junction_id, int side_no) {
        String junction_name = null;
        String query = " SELECT side" + side_no + "_name FROM junction "
                + " WHERE junction_id="+junction_id+" AND side_no ="+side_no;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
          
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_name = rset.getString("side" + side_no + "_name");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getSideName() error " + e);
        }
        return junction_name;
    }
    public boolean checkSlaveEntry(int junction_id,  int side_no) {
        int no = 0;
        String query1 = " SELECT count(*) AS c FROM side_detail WHERE junction_id = ?  AND side_no= ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, junction_id);
//            pstmt.setInt(2, program_version_no);
            pstmt.setInt(2, side_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                no = rset.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("SlaveInfoModel getFinalPlanRevisionNo() error" + e);
        }
        return no > 0 ? false : true;
        
    }
        public int deleteRecord(int side_detail_id) {
        String query = "DELETE FROM side_detail WHERE side_detail_id = "+side_detail_id ;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("SlaveInfoModel deleteRecord() Error: " + e);
        }
        if (rowsAffected >=0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<SlaveInfo> showData(int junction_id, int program_version_no) {
        List<SlaveInfo> list = new ArrayList<SlaveInfo>();
        String query = " SELECT j.junction_name, p.junction_id, "
                + "p.side_no, p.revision_no, p.side_name, p.pole_type_id, "
                + "p.position_id, p.primary_h_aspect_no, p.primary_v_aspect_no, "
                + "p.secondary_h_aspect_no, p.secondary_v_aspect_no, p.image_path, "
                + "p.vehicle_detection, p.count_down, p.no_of_lane, p.image_folder, "
                + "p.rlvd,p.anpr,p.pa_system,p.side_detail_id,p.lattitude,p.longitude"
                + " FROM side_detail AS p, junction AS j "
                + " WHERE p.junction_id=j.junction_id "
                + " AND j.junction_id= ? AND j.program_version_no = ? AND p.active='Y' "
                + " AND j.final_revision='VALID' ORDER BY p.side_no";
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                SlaveInfo slaveinfo = new SlaveInfo();
                String poleType = getPoleTypeById(rset.getInt("pole_type_id"));
                String position = getPositionById(rset.getInt("position_id"));
                slaveinfo.setSide_detail_id(rset.getInt("side_detail_id"));
                slaveinfo.setJunction_id(rset.getInt("junction_id"));
                slaveinfo.setSide_no(rset.getInt("side_no"));
                slaveinfo.setRevision_no(rset.getInt("revision_no"));
                slaveinfo.setSideName(rset.getString("side_name"));
                slaveinfo.setCount_down(rset.getString("count_down"));
                slaveinfo.setNo_of_lane(rset.getInt("no_of_lane"));
                slaveinfo.setVehicle_detection(rset.getString("vehicle_detection"));
                slaveinfo.setImage_folder(rset.getString("image_folder"));
                slaveinfo.setSecondary_v_aspect_no(rset.getInt("secondary_v_aspect_no"));
                slaveinfo.setSecondary_h_aspect_no(rset.getInt("secondary_h_aspect_no"));
                slaveinfo.setPrimary_v_aspect_no(rset.getInt("primary_v_aspect_no"));
                slaveinfo.setPrimary_h_aspect_no(rset.getInt("primary_h_aspect_no"));
                slaveinfo.setPosition(position);
                slaveinfo.setPole_type(poleType);
                slaveinfo.setRlpd(rset.getString("rlvd"));
                slaveinfo.setAnpr(rset.getInt("anpr"));
                slaveinfo.setPa_system(rset.getString("pa_system"));
                slaveinfo.setJunction_name(rset.getString("junction_name"));
                slaveinfo.setSide_no(rset.getInt("side_no"));
                slaveinfo.setRevision_no(rset.getInt("revision_no"));
                slaveinfo.setSideName(rset.getString("side_name"));
                slaveinfo.setLatitude(rset.getDouble("lattitude"));
                slaveinfo.setLongitude(rset.getDouble("longitude"));
                list.add(slaveinfo);
            }
        } catch (Exception e) {
            System.out.println("Error:SlaveInfoModel-showData--- " + e);
        }
        return list;
    }

    public String getPoleTypeById(int poleType_id) {
        String pole_type = null;
        String query = " SELECT pole_type FROM pole_type "
                + " WHERE pole_type_id= '" + poleType_id + "' AND active = 'Y' ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                pole_type = rset.getString("pole_type");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getPoleTypeId() error" + e);
        }
        return pole_type;
    }

    public String getPositionById(int position_id) {
        String position = null;
        String query = " SELECT position FROM position "
                + " WHERE position_id= '" + position_id + "' AND active = 'Y' ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                position = rset.getString("position");
            }
        } catch (Exception e) {
            System.out.println("SlaveInfoModel:getPoleTypeId() error" + e);
        }
        return position;
    }
    public static String getImageDestinationPath(int imageid1) {
        String image_folder = "";

        //String query = " SELECT image_folder  FROM  side_detail where side_detail_id=" + imageid1;
        String query = " SELECT  destination_path  FROM image_destination";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
               // image_folder = rset.getString("image_folder");
                  image_folder = rset.getString("destination_path");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
        return image_folder;
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
