 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Model;

import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.JunctionPlanMap;
import com.ts.junction.tableClasses.PhaseData;
import com.ts.junction.tableClasses.PlanDetails;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author Shruti
 */
public class JunctionModel extends HttpServlet {

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

    public Map<Integer, Junction> getJunctionList() {
        Map<Integer, Junction> junctionList = new HashMap<Integer, Junction>();
        try {
            String query = "SELECT junction_id,program_version_no, junction_name, address1, address2, city_name, controller_model, no_of_sides, amber_time, "
                    + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                    + " side2_name, side3_name, side4_name, side5_name,file_no "
                    + " from junction AS j, city AS c "
                    + " WHERE c.city_id=j.city_id AND j.final_revision='VALID'";

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Junction junction = new Junction();
                int junction_id = rset.getInt("junction_id");
                int program_version_no = rset.getInt("program_version_no");
                junction.setJunction_id(junction_id);
                junction.setProgram_version_no(program_version_no);
                junction.setJunction_name(rset.getString("junction_name"));
                junction.setAddress1(rset.getString("address1"));
                junction.setAddress2(rset.getString("address2"));
                junction.setState_name(getStateName());
                junction.setCity_name(rset.getString("city_name"));
                junction.setController_model(rset.getString("controller_model"));
                junction.setNo_of_sides(rset.getInt("no_of_sides"));
                junction.setAmber_time(rset.getInt("amber_time"));
                junction.setFlash_rate(rset.getInt("flash_rate"));
                junction.setNo_of_plans(rset.getInt("no_of_plans"));
                junction.setMobile_no(rset.getString("mobile_no"));
                junction.setSim_no(rset.getString("mobile_no"));
                junction.setImei_no(rset.getString("imei_no"));
                junction.setInstant_green_time(rset.getInt("instant_green_time"));
                String pedestrian = rset.getString("pedestrian");
                junction.setPedestrian(pedestrian.equals("Y") ? "YES" : "NO");
                junction.setPedestrian_time(rset.getInt("pedestrian_time"));
                junction.setSide1_name(rset.getString("side1_name"));
                junction.setSide2_name(rset.getString("side2_name"));
                junction.setSide3_name(rset.getString("side3_name"));
                junction.setSide4_name(rset.getString("side4_name"));
                junction.setSide5_name(rset.getString("side5_name"));
                junction.setFile_no(rset.getInt("file_no"));
                junctionList.put(junction_id, junction);
            }
        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
        return junctionList;
    }

    public int getJunctionID(String IMEINo) {
//        System.out.println(IMEINo);
        int junction_id = 0;
        String queryJunctionID = " SELECT * FROM junction WHERE imei_number = 'ABC' AND final_revision='VALID' ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            //pstmt.setString(1, IMEINo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("junction_id");
                System.out.println("junction_id---" + junction_id);
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("ClientResponder: registerModem() Error" + e);
        }
        return junction_id;
    }

    public List<String> getCityName(String q, String state_name) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT c.city_name "
                + " FROM city AS c, state AS s "
                + " WHERE c.state_id=s.state_id AND state_name= ? "
                + " ORDER BY city_name ";
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, state_name);
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

    public List<String> getStateName(String q) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT state_name "
                + " FROM state "
                + " ORDER BY state_name ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_name = rset.getString("state_name");
                if (state_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_name);
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

    public int deleteRecord(int junction_id) {
        String junctionQuery = "UPDATE junction SET final_revision = 'EXPIRED' WHERE junction_id= " + junction_id + " AND final_revision = 'VALID'";
        String planQuery = "UPDATE plan_info SET final_revision = 'EXPIRED' WHERE junction_id= " + junction_id + " AND final_revision = 'VALID'";
        int rowsAffected = 0;
        try {
            connection.setAutoCommit(false);
            rowsAffected = connection.prepareStatement(junctionQuery).executeUpdate();
            if (rowsAffected > 0) {
                rowsAffected = 0;
                rowsAffected = connection.prepareStatement(planQuery).executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        } catch (Exception e) {
            System.out.println("Error:Delete:JunctionModel-- " + e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(JunctionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from junction WHERE final_revision= 'VALID' ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("JunctionModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public int getCityID(String city_name) {
        int city_id = 0;
        String query = "SELECT city_id "
                + " FROM city WHERE city_name= ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                city_id = rset.getInt("city_id");
            }
        } catch (Exception ex) {
            System.out.println("JunctionModel getCityID() Error: " + ex);
        }
        return city_id;
    }

    public String getStateName() {
        String query = "SELECT state_name "
                + " FROM junction as j, city as c, district as d, state as s "
                + " WHERE j.city_id = c.city_id and "
                + " c.district_id = d.district_id and "
                + " d.state_id = s.state_id GROUP BY state_name ";
        String state_name = null;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                state_name = rset.getString("state_name");
            }
        } catch (Exception e) {
            System.out.println("Error:Delete:JunctionModel-- " + e);
        }
        return state_name;
    }

    public boolean insertRecord(Junction junction) {
        String junctionQuery = "INSERT INTO junction (junction_id, junction_name, address1, address2, city_id, controller_model, no_of_sides, amber_time, "
                + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                + " side2_name, side3_name, side4_name, side5_name, program_version_no, transferred_status, file_no, remark, created_by) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        String slave_insert_query = "INSERT into slave_info(junction_id, program_version_no, side_no, side_revision_no, side_name) "
                + " VALUES(?, ?, ?, ?, ?) ";

        String query = " SELECT MAX(junction_id) AS max_id FROM junction ";
        int junction_id = 0;
        int count = 0, rowsAffected = 0;
        PreparedStatement pstmt = null;
        boolean errorOccured = false;
        boolean autoCommit = true;
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                junction_id = rset.getInt("max_id");
            }
        } catch (Exception e) {
            System.out.println("Junction Model insertRecord() error" + e);
        }

        try {
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(junctionQuery);
            pstmt.setInt(1, (junction_id + 1));
            pstmt.setString(2, junction.getJunction_name());
            pstmt.setString(3, junction.getAddress1());
            pstmt.setString(4, junction.getAddress2());
            pstmt.setInt(5, getCityID(junction.getCity_name()));
            pstmt.setString(6, junction.getController_model());
            pstmt.setInt(7, junction.getNo_of_sides());
            pstmt.setInt(8, junction.getAmber_time());
            pstmt.setInt(9, junction.getFlash_rate());
            pstmt.setInt(10, junction.getNo_of_plans());
            pstmt.setString(11, junction.getMobile_no());
            pstmt.setString(12, junction.getSim_no());
            pstmt.setString(13, junction.getImei_no());
            pstmt.setInt(14, junction.getInstant_green_time());
            pstmt.setString(15, junction.getPedestrian());
            pstmt.setInt(16, junction.getPedestrian_time());
            pstmt.setString(17, junction.getSide1_name());
            pstmt.setString(18, junction.getSide2_name());
            pstmt.setString(19, junction.getSide3_name());
            pstmt.setString(20, junction.getSide4_name());
            pstmt.setString(21, junction.getSide5_name());
            pstmt.setInt(22, 1);
            pstmt.setString(23, "NO");
            pstmt.setInt(24, junction.getFile_no());
            pstmt.setString(25, junction.getRemark());
            pstmt.setInt(26, 1);
            rowsAffected = pstmt.executeUpdate();
            for (int i = 0; i < junction.getNo_of_sides() && rowsAffected > 0; i++) {
                rowsAffected = 0;
                pstmt.close();
                pstmt = connection.prepareStatement(slave_insert_query);
                pstmt.setInt(1, (junction_id + 1));
                pstmt.setInt(2, 1);
                pstmt.setInt(3, i + 1);
                pstmt.setInt(4, 1);
                pstmt.setString(5, i == 0 ? junction.getSide1_name()
                        : i == 1 ? junction.getSide2_name()
                                : i == 2 ? junction.getSide3_name()
                                        : i == 3 ? junction.getSide4_name()
                                                : i == 4 ? junction.getSide5_name() : "");
                rowsAffected = pstmt.executeUpdate();
            }
            if (rowsAffected > 0) {
                // Finally commit the connection.
                connection.commit();
                message = "Record saved successfully.";
                msgBgColor = COLOR_OK;
            } else {
                throw new SQLException("Record updated in plan_info.");
            }

        } catch (SQLException sqlEx) {
            errorOccured = true;
            message = "Could NOT save data , some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("JUnctionModel insert() Error: " + message + " Cause: " + sqlEx.getMessage());
        } finally {
            try {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            } catch (SQLException ex) {
                Logger.getLogger(JunctionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return !errorOccured;
    }

    public int updateRecord(Junction junction) {
        String query = null;
        PreparedStatement pstmt = null;
        int program_version_no = getFinalProgramVersionNo(junction.getJunction_id());
        query = "INSERT INTO junction (junction_id, junction_name, address1, address2, city_id, controller_model, no_of_sides, amber_time, "
                + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                + " side2_name, side3_name, side4_name, side5_name, program_version_no, transferred_status, file_no, remark, created_by) "
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        String updateStatus = "UPDATE junction SET final_revision='EXPIRED' WHERE junction_id = ? AND program_version_no = ? ";

        String update_plan_query = "UPDATE plan_info SET program_version_no = ? WHERE junction_id= ? AND program_version_no = ? AND final_revision='VALID' ";

        String delete_plan_query = "UPDATE slave_info SET final_revision = 'EXPIRED' WHERE junction_id= ? AND program_version_no = ? AND final_revision='VALID' ";

        String update_slave_query = "UPDATE slave_info SET program_version_no = ? WHERE junction_id= ? AND program_version_no = ? AND final_revision='VALID' ";

        String slave_insert_query = "INSERT into slave_info(junction_id, program_version_no, side_no, side_revision_no, side_name) "
                + " VALUES(?, ?, ?, ?, ?) ";

        String update_slave_query1 = "UPDATE slave_info SET final_revision = 'EXPIRED' WHERE junction_id= ? AND program_version_no = ? AND final_revision='VALID' ";

        int rowsAffected = 0;
        int sideRevisionNo = 0;
        int prevNoOfSides = getNoOfSides(junction.getJunction_id(), program_version_no);
        int prevNoOfPlans = getNoOfPlans(junction.getJunction_id(), program_version_no);
        try {
            boolean autoCommit = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, (junction.getJunction_id()));
                pstmt.setString(2, junction.getJunction_name());
                pstmt.setString(3, junction.getAddress1());
                pstmt.setString(4, junction.getAddress2());
                pstmt.setInt(5, getCityID(junction.getCity_name()));
                pstmt.setString(6, junction.getController_model());
                pstmt.setInt(7, junction.getNo_of_sides());
                pstmt.setInt(8, junction.getAmber_time());
                pstmt.setInt(9, junction.getFlash_rate());
                pstmt.setInt(10, junction.getNo_of_plans());
                pstmt.setString(11, junction.getMobile_no());
                pstmt.setString(12, junction.getSim_no());
                pstmt.setString(13, junction.getImei_no());
                pstmt.setInt(14, junction.getInstant_green_time());
                pstmt.setString(15, junction.getPedestrian());
                pstmt.setInt(16, junction.getPedestrian_time());
                pstmt.setString(17, junction.getSide1_name());
                pstmt.setString(18, junction.getSide2_name());
                pstmt.setString(19, junction.getSide3_name());
                pstmt.setString(20, junction.getSide4_name());
                pstmt.setString(21, junction.getSide5_name());
                pstmt.setInt(22, program_version_no + 1);
                pstmt.setString(23, "NO");
                pstmt.setInt(24, junction.getFile_no());
                pstmt.setString(25, junction.getRemark());
                pstmt.setInt(26, 1);
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    pstmt.close();
                    rowsAffected = 0;
                    pstmt = connection.prepareStatement(updateStatus);
                    pstmt.setInt(1, junction.getJunction_id());
                    pstmt.setInt(2, program_version_no);
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        if (prevNoOfPlans != junction.getNo_of_plans() && junction.getNo_of_plans() != 0) {
                            rowsAffected = 0;
                            pstmt.close();
                            pstmt = connection.prepareStatement(delete_plan_query);
                            pstmt.setInt(1, junction.getJunction_id());
                            pstmt.setInt(2, program_version_no);
                            rowsAffected = pstmt.executeUpdate();
                        } else if (prevNoOfPlans != 0) {
                            rowsAffected = 0;
                            pstmt.close();
                            pstmt = connection.prepareStatement(update_plan_query);
                            pstmt.setInt(1, program_version_no + 1);
                            pstmt.setInt(2, junction.getJunction_id());
                            pstmt.setInt(3, program_version_no);
                            rowsAffected = pstmt.executeUpdate();
                        }
                        if (rowsAffected > 0) {
                            pstmt.close();
                            if (prevNoOfSides != junction.getNo_of_sides()) {
                                rowsAffected = 0;
                                sideRevisionNo = getFinalSideRevisionNo(junction.getJunction_id(), program_version_no);
                                pstmt = connection.prepareStatement(update_slave_query1);
                                pstmt.setInt(1, junction.getJunction_id());
                                pstmt.setInt(2, program_version_no);
                                rowsAffected = pstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    for (int i = 0; i < junction.getNo_of_sides() && rowsAffected > 0; i++) {
                                        rowsAffected = 0;
                                        pstmt.close();
                                        pstmt = connection.prepareStatement(slave_insert_query);
                                        pstmt.setInt(1, junction.getJunction_id());
                                        pstmt.setInt(2, program_version_no + 1);
                                        pstmt.setInt(3, i + 1);
                                        pstmt.setInt(4, sideRevisionNo + 1);
                                        pstmt.setString(5, i == 0 ? junction.getSide1_name()
                                                : i == 1 ? junction.getSide2_name()
                                                        : i == 2 ? junction.getSide3_name()
                                                                : i == 3 ? junction.getSide4_name()
                                                                        : i == 4 ? junction.getSide5_name() : "");
                                        rowsAffected = pstmt.executeUpdate();
                                    }
                                } else {
                                    throw new SQLException("all pervious slaves records are not updateded");
                                }
                            } else {
                                pstmt = connection.prepareStatement(update_slave_query);
                                pstmt.setInt(1, program_version_no + 1);
                                pstmt.setInt(2, junction.getJunction_id());
                                pstmt.setInt(3, program_version_no);
                                //rowsAffected =
                                pstmt.executeUpdate();
                            }
                        } else {
                            throw new SQLException("Record updated in plan_info.");
                        }

                    } else {
                        throw new SQLException("Record NOT updated in junction.");
                    }
                } else {
                    throw new SQLException("Record NOT saved in junction.");
                }
            } catch (SQLException sqlEx) {
                connection.rollback();
                message = "Could NOT save data , some error.";
                msgBgColor = COLOR_ERROR;
                System.out.println("JUnctionModel updateRecord() Error: " + message + " Cause: " + sqlEx.getMessage());
            } finally {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            }
        } catch (Exception e) {
            System.out.println("JUnctionModel updateRecord() Error: " + e);
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

    public List<Junction> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<Junction> list = new ArrayList<Junction>();
        try {
            String query = "SELECT junction_id, junction_name, address1, address2, city_name, controller_model, no_of_sides, amber_time, "
                    + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                    + " side2_name, side3_name, side4_name, side5_name,file_no, program_version_no,remark "
                    + " from junction AS j, city AS c "
                    + " WHERE c.city_id=j.city_id AND j.final_revision='VALID' "
                    + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
            System.out.println(lowerLimit + "," + noOfRowsToDisplay);
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Junction junction = new Junction();
                junction.setJunction_id(rset.getInt("junction_id"));
                junction.setJunction_name(rset.getString("junction_name"));
                junction.setAddress1(rset.getString("address1"));
                junction.setAddress2(rset.getString("address2"));
                junction.setState_name(getStateName());
                junction.setCity_name(rset.getString("city_name"));
                junction.setController_model(rset.getString("controller_model"));
                junction.setNo_of_sides(rset.getInt("no_of_sides"));
                junction.setAmber_time(rset.getInt("amber_time"));
                junction.setFlash_rate(rset.getInt("flash_rate"));
                junction.setNo_of_plans(rset.getInt("no_of_plans"));
                junction.setMobile_no(rset.getString("mobile_no"));
                junction.setSim_no(rset.getString("mobile_no"));
                junction.setImei_no(rset.getString("imei_no"));
                junction.setInstant_green_time(rset.getInt("instant_green_time"));
                String pedestrian = rset.getString("pedestrian");
                junction.setPedestrian(pedestrian.equals("Y") ? "YES" : "NO");
                junction.setPedestrian_time(rset.getInt("pedestrian_time"));
                junction.setSide1_name(rset.getString("side1_name"));
                junction.setSide2_name(rset.getString("side2_name"));
                junction.setSide3_name(rset.getString("side3_name"));
                junction.setSide4_name(rset.getString("side4_name"));
                junction.setSide5_name(rset.getString("side5_name"));
                junction.setFile_no(rset.getInt("file_no"));
                junction.setProgram_version_no(rset.getInt("program_version_no"));
                junction.setRemark(rset.getString("remark"));
                list.add(junction);
            }

        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
        return list;
    }
    public List<Junction> showData() {
        List<Junction> list = new ArrayList<Junction>();
        try {
            String query = "SELECT junction_id, junction_name, address1, address2, city_name, controller_model, no_of_sides, amber_time, "
                    + " flash_rate, no_of_plans, mobile_no, sim_no, imei_no, instant_green_time, pedestrian, pedestrian_time, side1_name, "
                    + " side2_name, side3_name, side4_name, side5_name,file_no, program_version_no,remark "
                    + " from junction AS j, city AS c "
                    + " WHERE c.city_id=j.city_id AND j.final_revision='VALID' ";
                     
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                Junction junction = new Junction();
                junction.setJunction_id(rset.getInt("junction_id"));
                junction.setJunction_name(rset.getString("junction_name"));
                junction.setAddress1(rset.getString("address1"));
                junction.setAddress2(rset.getString("address2"));
                junction.setState_name(getStateName());
                junction.setCity_name(rset.getString("city_name"));
                junction.setController_model(rset.getString("controller_model"));
                junction.setNo_of_sides(rset.getInt("no_of_sides"));
                junction.setAmber_time(rset.getInt("amber_time"));
                junction.setFlash_rate(rset.getInt("flash_rate"));
                junction.setNo_of_plans(rset.getInt("no_of_plans"));
                junction.setMobile_no(rset.getString("mobile_no"));
                junction.setSim_no(rset.getString("mobile_no"));
                junction.setImei_no(rset.getString("imei_no"));
                junction.setInstant_green_time(rset.getInt("instant_green_time"));
                String pedestrian = rset.getString("pedestrian");
                junction.setPedestrian(pedestrian.equals("Y") ? "YES" : "NO");
                junction.setPedestrian_time(rset.getInt("pedestrian_time"));
                junction.setSide1_name(rset.getString("side1_name"));
                junction.setSide2_name(rset.getString("side2_name"));
                junction.setSide3_name(rset.getString("side3_name"));
                junction.setSide4_name(rset.getString("side4_name"));
                junction.setSide5_name(rset.getString("side5_name"));
                junction.setFile_no(rset.getInt("file_no"));
                junction.setProgram_version_no(rset.getInt("program_version_no"));
                junction.setRemark(rset.getString("remark"));
                list.add(junction);
            }

        } catch (Exception e) {
            System.out.println("Error:junctionModel-showData--- " + e);
        }
        return list;
    }

    public List<JunctionPlanMap> showDataPlanMap(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected) {
        List<JunctionPlanMap> list = new ArrayList<JunctionPlanMap>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query2 = "SELECT jp.junction_plan_map_id, jp.order_no, dd.from_date, dd.to_date, d.day, "
                + "j.junction_name, p.on_time_hour,p.on_time_min,p.off_time_hour,p.off_time_min,p.plan_no,jp.junction_id "
                + "FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                + "left join day_detail d on jp.day_id = d.day_detail_id "
                + "inner join junction j on jp.junction_id = j.junction_id "
                + "inner join plan_details p on jp.plan_id = p.plan_id "
                + "where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and j.junction_id = " + junction_id_selected
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JunctionPlanMap bean = new JunctionPlanMap();
                bean.setJunction_plan_map_id(rset.getInt(1));
                bean.setOrder_no(rset.getInt(2));
                bean.setFrom_date(rset.getString(3));
                bean.setTo_date(rset.getString(4));
                bean.setDay(rset.getString(5));
                bean.setJunction_name(rset.getString(6));
                bean.setOn_time_hr(rset.getInt(7));
                bean.setOn_time_min(rset.getInt(8));
                bean.setOff_time_hr(rset.getInt(9));
                bean.setOff_time_min(rset.getInt(10));
                bean.setPlan_no(rset.getInt(11));
                bean.setJunction_id(rset.getInt(12));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getTotalPlans(int jid, String filter,String Fromdate,String todate,String day,int junction_id_selected) {
        int noOfRows = 0;
         
        String query = "";
        if (filter.equalsIgnoreCase("date")) {
            query = "SELECT  count(*) FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id left join day_detail d on jp.day_id = d.day_detail_id inner join junction j on jp.junction_id = j.junction_id inner join plan_details p on jp.plan_id = p.plan_id where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and jp.date_id!='' and dd.from_date='"+Fromdate+"' and dd.to_date='"+todate+"' group by dd.from_date;";
        } else if (filter.equalsIgnoreCase("day")) {
            query = "SELECT count(*) FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id left join day_detail d on jp.day_id = d.day_detail_id inner join junction j on jp.junction_id = j.junction_id inner join plan_details p on jp.plan_id = p.plan_id where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and jp.day_id!='' and d.day='"+day+"' and jp.junction_id='"+junction_id_selected+"'";

        } else {
            query = "SELECT count(*) FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id left join day_detail d on jp.day_id = d.day_detail_id inner join junction j on jp.junction_id = j.junction_id inner join plan_details p on jp.plan_id = p.plan_id where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and date_id IS null and day_id is null and j.junction_id = '"+day+"' group by p.on_time_hour";

        }
        try {
           
          PreparedStatement pstmt1 = (PreparedStatement)connection.prepareStatement(query);
           ResultSet rset1 = pstmt1.executeQuery();
            while (rset1.next()) {
                noOfRows = Integer.parseInt(rset1.getString(1));
            }
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("JunctionModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<JunctionPlanMap> showDataPlanMapbyfilter(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected, String filter) {
        List<JunctionPlanMap> list = new ArrayList<JunctionPlanMap>();
        JunctionModel jm = new JunctionModel();
        int totalplans = 0;
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query2 = "";
        if (filter.equalsIgnoreCase("date")) {

            query2 = "SELECT  distinct jp.junction_plan_map_id, jp.order_no, dd.from_date, dd.to_date, d.day, "
                    + "j.junction_name, p.on_time_hour,p.on_time_min,p.off_time_hour,p.off_time_min,p.plan_no,jp.junction_id "
                    + "FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                    + "left join day_detail d on jp.day_id = d.day_detail_id "
                    + "inner join junction j on jp.junction_id = j.junction_id "
                    + "inner join plan_details p on jp.plan_id = p.plan_id "
                    + "where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and jp.date_id!='' and j.junction_id = '" + junction_id_selected + "' group by dd.from_date "
                    + addQuery;
        } else if (filter.equalsIgnoreCase("day")) {
            // totalplans=jm.getTotalPlans(junction_id_selected,filter);
            query2 = "SELECT jp.junction_plan_map_id, jp.order_no, dd.from_date, dd.to_date, d.day, "
                    + "j.junction_name, p.on_time_hour,p.on_time_min,p.off_time_hour,p.off_time_min,p.plan_no,jp.junction_id "
                    + "FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                    + "left join day_detail d on jp.day_id = d.day_detail_id "
                    + "inner join junction j on jp.junction_id = j.junction_id "
                    + "inner join plan_details p on jp.plan_id = p.plan_id "
                    + "where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and day_id!='' and j.junction_id =" + junction_id_selected
                    + addQuery;

        } else {

            // totalplans=jm.getTotalPlans(junction_id_selected,filter);
            query2 = "SELECT distinct  jp.junction_plan_map_id, jp.order_no, dd.from_date, dd.to_date,"
                    + " d.day, j.junction_name, p.on_time_hour,p.on_time_min,p.off_time_hour,p.off_time_min,p.plan_no,jp.junction_id "
                    + "FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                    + "left join day_detail d on jp.day_id = d.day_detail_id inner join junction j on jp.junction_id = j.junction_id "
                    + "inner join plan_details p on jp.plan_id = p.plan_id where j.final_revision = 'VALID' and jp.active='Y' "
                    + "and p.active = 'Y' and date_id IS null and day_id is null and j.junction_id = '" + junction_id_selected + "' group by p.on_time_hour"
                    + addQuery;
        }

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                JunctionPlanMap bean = new JunctionPlanMap();
                bean.setJunction_plan_map_id(rset.getInt("junction_plan_map_id"));
                bean.setOrder_no(rset.getInt(2));
                bean.setFrom_date(rset.getString("from_date"));
                bean.setTo_date(rset.getString(4));
                bean.setDay(rset.getString(5));
                bean.setJunction_name(rset.getString(6));
                bean.setOn_time_hr(rset.getInt(7));
                bean.setOn_time_min(rset.getInt(8));
                bean.setOff_time_hr(rset.getInt(9));
                bean.setOff_time_min(rset.getInt(10));
            
                String fromdate="";
                String todate="";
                String day="";
                
                
                
               //fromdate= bean.getFrom_date().toString();
                
                
             //  todate= bean.getTo_date().toString();
               
                
                
                
                if (filter.equalsIgnoreCase("date")) {
                     fromdate= bean.getFrom_date().toString();
                      todate= bean.getTo_date().toString();
                    totalplans = getTotalPlans(junction_id_selected, filter,fromdate,todate,day,junction_id_selected);
                } else if (filter.equalsIgnoreCase("day")) {
                    day=bean.getDay().toString();
                    totalplans = getTotalPlans(junction_id_selected, filter,fromdate,todate,day,junction_id_selected);
                }else{
                 totalplans =getTotalPlans(junction_id_selected, filter,fromdate,todate,day,junction_id_selected);
                }
                 bean.setPlan_no(totalplans);
                 bean.setPlan_id(rset.getInt(11));
                bean.setJunction_id(rset.getInt(12));
                
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public String getPlans(int junction_id_selected1) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = "SELECT plan_id "
                + " FROM junction_plan_map where active='Y' and junction_id=" + junction_id_selected1
                + " ORDER BY order_no ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String plan_id = rset.getString("plan_id");

                list.add(plan_id);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        String plansString = "";
        String plansString1 = "";
        for (int i = 0; i < list.size(); i++) {
            plansString = list.get(i);
            plansString1 = plansString1 + "," + plansString;
        }
        String plansString2 = plansString1.substring(1);
        return plansString2;
    }

    public List<PlanDetails> showDataPlans(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected1, int plan_no) {

        List<PlanDetails> list = new ArrayList<PlanDetails>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query2 = "select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, "
                + "off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, "
                + "side4_green_time, side5_green_time, side1_amber_time, side2_amber_time, side3_amber_time, "
                + "side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s,junction_plan_map pm"
                + " where s.active='Y'"
                + " and pm.junction_plan_map_id='" + junction_id_selected1 + "' ORDER BY plan_no"
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PlanDetails bean = new PlanDetails();
                bean.setPlan_id(rset.getInt(1));
                bean.setPlan_no(rset.getInt(2));
                bean.setOn_time_hour(rset.getInt(3));
                bean.setOn_time_min(rset.getInt(4));
                bean.setOff_time_hour(rset.getInt(5));
                bean.setOff_time_min(rset.getInt(6));
                bean.setMode(rset.getString(7));
                bean.setSide1_green_time(rset.getInt(8));
                bean.setSide2_green_time(rset.getInt(9));
                bean.setSide3_green_time(rset.getInt(10));
                bean.setSide4_green_time(rset.getInt(11));
                bean.setSide5_green_time(rset.getInt(12));
                bean.setSide1_amber_time(rset.getInt(13));
                bean.setSide2_amber_time(rset.getInt(14));
                bean.setSide3_amber_time(rset.getInt(15));
                bean.setSide4_amber_time(rset.getInt(16));
                bean.setSide5_amber_time(rset.getInt(17));
                bean.setTransferred_status(rset.getString(18));
                bean.setRemark(rset.getString(19));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
     public String getTotalPhase(String city_name) {
        String city_id = "";
        String query = " Select count(*) from phase_detail pd,phase_map pm ,junction_plan_map jpm "
                + "where pm.phase_id=pd.phase_info_id and \n" +
"                pm.junction_plan_map_id=jpm.junction_plan_map_id and pm.active='Y' "
                + "and jpm.active='Y' and pd.active='Y' and jpm.plan_id='"+city_name+"'";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
          //  pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            int count=0;
            while (rset.next()) {
                
              city_id =rset.getString(1);
            }
             
        } catch (Exception ex) {
            System.out.println("JunctionModel getCityID() Error: " + ex);
        }
        return city_id;
    }
     public List<PlanDetails> showDataPlansdetails(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected1, String fromdate,String todate) {
List<String> list1 = new ArrayList<String>();
        List<PlanDetails> list = new ArrayList<PlanDetails>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
 String totalphase=null;
        String query2 = "SELECT p.plan_id "
                + "FROM junction_plan_map jp "
                + "left join date_detail dd on jp.date_id = dd.date_detail_id "
                + "left join day_detail d on jp.day_id = d.day_detail_id "
                + "inner join junction j on jp.junction_id = j.junction_id "
                + "inner join plan_details p on jp.plan_id = p.plan_id "
                + "where j.final_revision = 'VALID' and jp.active='Y' and p.active = 'Y' and "
                + "jp.date_id!='' and dd.from_date='"+fromdate+"' and dd.to_date='"+todate+"' and jp.junction_id='"+junction_id_selected1+"'"
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            String joined="";
            int count=0;
            while (rset.next()) {
                String plan_id = rset.getString("plan_id");
             
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(plan_id);
                    count++;
                
            }
            
            
             joined = String.join(",", list1);
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, side1_amber_time, "
                    + "side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s "
                    + "where s.plan_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery(); 
           int count1=0;
           while (rset1.next()) {
                
                 PlanDetails bean = new PlanDetails();
                 
                 totalphase=getTotalPhase(list1.get(count1));
                 count1++;
                bean.setPlan_id(rset1.getInt(1));
                bean.setPlan_no(rset1.getInt(2));
                bean.setOn_time_hour(rset1.getInt(3));
                bean.setOn_time_min(rset1.getInt(4));
                bean.setOff_time_hour(rset1.getInt(5));
                bean.setOff_time_min(rset1.getInt(6));
                bean.setMode(rset1.getString(7));
                bean.setSide1_green_time(rset1.getInt(8));
                bean.setSide2_green_time(rset1.getInt(9));
                bean.setSide3_green_time(rset1.getInt(10));
                bean.setSide4_green_time(rset1.getInt(11));
                bean.setSide5_green_time(rset1.getInt(12));
                bean.setSide1_amber_time(rset1.getInt(13));
                bean.setSide2_amber_time(rset1.getInt(14));
                bean.setSide3_amber_time(rset1.getInt(15));
                bean.setSide4_amber_time(rset1.getInt(16));
                bean.setSide5_amber_time(rset1.getInt(17));
                bean.setTransferred_status(rset1.getString(18));
                bean.setRemark(rset1.getString(19));
                bean.setTotalphase(totalphase);
                list.add(bean);
             
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
     
        public List<PlanDetails> showDataPlansdetailsday(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected1, String day) {
        List<String> list1 = new ArrayList<String>();
        List<PlanDetails> list = new ArrayList<PlanDetails>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
String totalphase=""; 
        String query2 = "SELECT jp.junction_plan_map_id,p.plan_id "
                + "FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                + "left join day_detail d on jp.day_id = d.day_detail_id "
                + "inner join junction j on jp.junction_id = j.junction_id "
                + "inner join plan_details p on jp.plan_id = p.plan_id "
                + "where j.final_revision = 'VALID' and jp.active='Y' "
                + "and p.active = 'Y' and jp.day_id!='' and d.day='"+day+"' and jp.junction_id='"+junction_id_selected1+"'"
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            String joined="";
            int count=0;
            while (rset.next()) {
                String plan_id = rset.getString("plan_id");
            // totalphase=getTotalPhase(plan_id);
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(plan_id);
                    count++;
                
            }
            
            
             joined = String.join(",", list1);
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, side1_amber_time, "
                    + "side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s "
                    + "where s.plan_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery(); 
             int count1=0; 
            while (rset1.next()) {
                 PlanDetails bean = new PlanDetails();
                 totalphase=getTotalPhase(list1.get(count1));
                 count1++;
                bean.setPlan_id(rset1.getInt(1));
                bean.setPlan_no(rset1.getInt(2));
                bean.setOn_time_hour(rset1.getInt(3));
                bean.setOn_time_min(rset1.getInt(4));
                bean.setOff_time_hour(rset1.getInt(5));
                bean.setOff_time_min(rset1.getInt(6));
                bean.setMode(rset1.getString(7));
                bean.setSide1_green_time(rset1.getInt(8));
                bean.setSide2_green_time(rset1.getInt(9));
                bean.setSide3_green_time(rset1.getInt(10));
                bean.setSide4_green_time(rset1.getInt(11));
                bean.setSide5_green_time(rset1.getInt(12));
                bean.setSide1_amber_time(rset1.getInt(13));
                bean.setSide2_amber_time(rset1.getInt(14));
                bean.setSide3_amber_time(rset1.getInt(15));
                bean.setSide4_amber_time(rset1.getInt(16));
                bean.setSide5_amber_time(rset1.getInt(17));
                bean.setTransferred_status(rset1.getString(18));
                bean.setRemark(rset1.getString(19));
                 bean.setTotalphase(totalphase);
                list.add(bean);
             
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
      public List<PlanDetails> showDataPlansdetailsnormal(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected1,int plan_id) {
        List<String> list1 = new ArrayList<String>();
        List<PlanDetails> list = new ArrayList<PlanDetails>();
        String totalphase="";
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query2 = "SELECT jp.junction_plan_map_id,p.plan_id "
                + "FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
                + "left join day_detail d on jp.day_id = d.day_detail_id "
                + "inner join junction j on jp.junction_id = j.junction_id "
                + "inner join plan_details p on jp.plan_id = p.plan_id "
                + "where j.final_revision = 'VALID' and jp.active='Y' "
                + "and p.active = 'Y' and jp.day_id is null and jp.date_id is null and jp.junction_id='"+junction_id_selected1+"' and p.plan_id='"+plan_id+"'"
                + addQuery;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            String joined="";
            int count=0;
            while (rset.next()) {
                String plan_ids = rset.getString("plan_id");
                totalphase=getTotalPhase(plan_ids);
               // if (device_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list1.add(plan_ids);
                    count++;
                
            }
            
            
             joined = String.join(",", list1);
            PreparedStatement pstmt1 = (PreparedStatement) connection.prepareStatement("select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, side4_green_time, side5_green_time, side1_amber_time, "
                    + "side2_amber_time, side3_amber_time, side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s "
                    + "where s.plan_id IN ("+joined+")");
            ResultSet rset1 = pstmt1.executeQuery(); 
            while (rset1.next()) {
                 PlanDetails bean = new PlanDetails();
                bean.setPlan_id(rset1.getInt(1));
                bean.setPlan_no(rset1.getInt(2));
                bean.setOn_time_hour(rset1.getInt(3));
                bean.setOn_time_min(rset1.getInt(4));
                bean.setOff_time_hour(rset1.getInt(5));
                bean.setOff_time_min(rset1.getInt(6));
                bean.setMode(rset1.getString(7));
                bean.setSide1_green_time(rset1.getInt(8));
                bean.setSide2_green_time(rset1.getInt(9));
                bean.setSide3_green_time(rset1.getInt(10));
                bean.setSide4_green_time(rset1.getInt(11));
                bean.setSide5_green_time(rset1.getInt(12));
                bean.setSide1_amber_time(rset1.getInt(13));
                bean.setSide2_amber_time(rset1.getInt(14));
                bean.setSide3_amber_time(rset1.getInt(15));
                bean.setSide4_amber_time(rset1.getInt(16));
                bean.setSide5_amber_time(rset1.getInt(17));
                bean.setTransferred_status(rset1.getString(18));
                bean.setRemark(rset1.getString(19));
                bean.setTotalphase(totalphase);
                list.add(bean);
             
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
    
    

// public List<PlanDetails> showDataPlansbyfilter(int lowerLimit, int noOfRowsToDisplay, int junction_id_selected1,int plan_no,String filter) {
//         
//        List<PlanDetails> list = new ArrayList<PlanDetails>();
//         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
//          if(lowerLimit == -1)
//            addQuery = "";
//      String query2=""; 
//if(filter.equalsIgnoreCase("date")){ 
//     query2="select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, "
//               + "off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, "
//               + "side4_green_time, side5_green_time, side1_amber_time, side2_amber_time, side3_amber_time, "
//               + "side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s,junction_plan_map pm"
//               + " where s.active='Y'"
//               + " and pm.junction_plan_map_id='"+junction_id_selected1+"' ORDER BY plan_no and "
//                     + addQuery;
//
//}else if(filter.equalsIgnoreCase("day")){
//    
// query2="select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, "
//               + "off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, "
//               + "side4_green_time, side5_green_time, side1_amber_time, side2_amber_time, side3_amber_time, "
//               + "side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s,junction_plan_map pm"
//               + " where s.active='Y'"
//               + " and pm.junction_plan_map_id='"+junction_id_selected1+"' ORDER BY plan_no and "
//                     + addQuery;
//
//} else{
// query2="select s.plan_id, plan_no, on_time_hour, on_time_min, off_time_hour, "
//               + "off_time_min, mode, side1_green_time, side2_green_time, side3_green_time, "
//               + "side4_green_time, side5_green_time, side1_amber_time, side2_amber_time, side3_amber_time, "
//               + "side4_amber_time, side5_amber_time, transferred_status, s.remark from plan_details s,junction_plan_map pm"
//               + " where s.active='Y'"
//               + " and pm.junction_plan_map_id='"+junction_id_selected1+"' ORDER BY plan_no and "
//                     + addQuery;
//}
//       
//        try {
//            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
//            ResultSet rset = pstmt.executeQuery();
//            while (rset.next()) {
//                PlanDetails bean = new PlanDetails();
//                bean.setPlan_id(rset.getInt(1));
//                bean.setPlan_no(rset.getInt(2));
//                bean.setOn_time_hour(rset.getInt(3));
//                bean.setOn_time_min(rset.getInt(4));
//                bean.setOff_time_hour(rset.getInt(5));
//                bean.setOff_time_min(rset.getInt(6));
//                bean.setMode(rset.getString(7));
//                bean.setSide1_green_time(rset.getInt(8));
//                bean.setSide2_green_time(rset.getInt(9));
//                bean.setSide3_green_time(rset.getInt(10));
//                bean.setSide4_green_time(rset.getInt(11));
//                bean.setSide5_green_time(rset.getInt(12));
//                bean.setSide1_amber_time(rset.getInt(13));
//                bean.setSide2_amber_time(rset.getInt(14));
//                bean.setSide3_amber_time(rset.getInt(15));
//                bean.setSide4_amber_time(rset.getInt(16));
//                bean.setSide5_amber_time(rset.getInt(17));
//                bean.setTransferred_status(rset.getString(18));
//                bean.setRemark(rset.getString(19));
//                list.add(bean);
//            }
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        }
//        return list;
//    }
    public String getPhase(int junction_plan_map_id_selected1) {
        List<String> list = new ArrayList<String>();
        PreparedStatement pstmt;
        String query = " SELECT phase_id  FROM phase_map "
                + " where active='Y' and junction_plan_map_id=" + junction_plan_map_id_selected1
                + " ORDER BY order_no ";
        try {
            pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;

            while (rset.next()) {    // move cursor from BOR to valid record.
                String phase_id = rset.getString("phase_id");

                list.add(phase_id);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        String phaseString = "";
        String phaseString1 = "";
        for (int i = 0; i < list.size(); i++) {
            phaseString = list.get(i);
            phaseString1 = phaseString1 + "," + phaseString;
        }
        String phaseString2 = phaseString1.substring(1);
        return phaseString2;
    }
public List<PhaseData> showDataPhaseDetails(int lowerLimit, int noOfRowsToDisplay, int junction_plan_map_id_selected, int plan_no) {
        List<PhaseData> list = new ArrayList<PhaseData>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        //  String phaseString2=getPhase(junction_plan_map_id_selected);

//       String query2="SELECT jp.junction_plan_map_id, jp.order_no, dd.from_date, dd.to_date, d.day, "
//                    + "j.junction_name, p.on_time_hour,p.on_time_min,p.off_time_hour,p.off_time_min,p.plan_no " 
//                    +"FROM junction_plan_map jp left join date_detail dd on jp.date_id = dd.date_detail_id "
//                    +"left join day_detail d on jp.day_id = d.day_detail_id " 
//                    +"inner join junction j on jp.junction_id = j.junction_id " 
//                    +"inner join plan_details p on jp.plan_id = p.plan_id "
//                    +"where j.final_revision = 'VALID' and p.active = 'Y'  "
//                    +addQuery;
        String query2 = " SELECT pds.phase_info_id,jpm.order_no,dd.from_date,dd.to_date,dy.day,"
                + "j.junction_name,pd.on_time_hour,pd.on_time_min,pd.off_time_hour,pd.off_time_min,pd.plan_no,"
                + "pds.side13,pds.side24,pds.phase_no,pds.padestrian_info,pds.remark,dy.day_name"
                + " from junction j inner join junction_plan_map jpm on j.junction_id=jpm.junction_id "
                + " left join date_detail dd on jpm.date_id=dd.date_detail_id left join day_detail dy on jpm.day_id=dy.day_detail_id"
                + " left join plan_details pd on jpm.plan_id=pd.plan_id left join phase_map pm on jpm.junction_plan_map_id=pm.junction_plan_map_id"
                + " left join phase_detail pds on pm.phase_id=pds.phase_info_id where j.final_revision = 'VALID' and pd.active = 'Y' and "
                + " jpm.junction_plan_map_id='" + junction_plan_map_id_selected + "' and pd.plan_no='" + plan_no + "'"
                + addQuery;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PhaseData bean = new PhaseData();
//                bean.setJunction_plan_map_id(rset.getInt(1));
                bean.setPhase_info_id(rset.getInt(1));
                bean.setOrder_no(rset.getInt(2));
                bean.setFrom_date(rset.getString(3));
                bean.setTo_date(rset.getString(4));
                bean.setDay(rset.getString(5));
                bean.setJunction_name(rset.getString(6));
                bean.setOn_time_hr(rset.getInt(7));
                bean.setOn_time_min(rset.getInt(8));
                bean.setOff_time_hr(rset.getInt(9));
                bean.setOff_time_min(rset.getInt(10));
                bean.setPlan_no(rset.getInt(11));

//                bean.setOrder_no(rset.getInt(2));
//                bean.setJunction_name(rset.getString(3));
                bean.setSide13(rset.getInt(12));
                bean.setSide24(rset.getInt(13));
                bean.setPhase_no(rset.getInt(14));
                bean.setPadestrian_info(rset.getString(15));
                bean.setRemark(rset.getString(16));

                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
 public String decToBinaryAndSplitFirst(String n) 
    { 
        //perform decToBinary
//        int[] binaryNum = decToBinary(n);
//        int len = binaryNum.length;
//        String binary = "";
//        for (int i = 0; i < binaryNum.length; i++) {
//            binary = binary + binaryNum[i];            
//        }
         //String binary = n;
      int a=n.length();
      String remain="";
      String str="";
if(a<=7){
    a=8-a;
    
      for(int k=0;k<a;k++){
          
          str = str.concat("0");
      
      }
      n=str.concat(n);
}

        String sideFirst = n.substring(0, 4);
        return sideFirst;
    }
    
    public String decToBinaryAndSplitLater(String n) 
    { 
        //perform decToBinary
//        int[] binaryNum = decToBinary(n);
//        int len = binaryNum.length;
//        String binary = "";
//        for (int i = 0; i < binaryNum.length; i++) {
//            binary = binary + binaryNum[i];            
//        }
        // String binary = Integer.toBinaryString(n);
      int a=n.length();
       String str="";
if(a<=7){
    a=8-a;
    
      for(int k=0;k<a;k++){
          
          str = str.concat("0");
      
      }
      n=str.concat(n);
}
        String sideFirst = n.substring(4, 8);
        return sideFirst;
    }
    
    public List<PhaseData> showDataPhaseNew(int junction_plan_map_id_selected, int plan_no) {
        List<PhaseData> list = new ArrayList<PhaseData>();
         
        
         
        String query2 = "Select pd.phase_info_id,j.junction_name,pd.side13,pd.side24,pd.phase_no,pd.remark,j.no_of_sides  from phase_detail pd,phase_map pm ,junction_plan_map jpm ,junction j\n" +
"                where pm.phase_id=pd.phase_info_id and jpm.junction_id=j.junction_id and\n" +
"                pm.junction_plan_map_id=jpm.junction_plan_map_id and pm.active='Y' and jpm.active='Y' and pd.active='Y' and j.final_revision='valid' and jpm.plan_id='"+plan_no+"'"
                ;

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query2);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                PhaseData bean = new PhaseData();
//                bean.setJunction_plan_map_id(rset.getInt(1));
                bean.setPhase_info_id(rset.getInt(1));
               
                bean.setJunction_name(rset.getString(2));
               bean.setSide13(rset.getInt(3));
                bean.setSide24(rset.getInt(4));
                bean.setPhase_no(rset.getInt(5));
                 bean.setRemark(rset.getString(6));
                 
                 bean.setNo_of_sides(rset.getString(7));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public boolean checkImei(String imei_no) {
        boolean result = false;
        String query1 = " SELECT count(*) AS c FROM junction WHERE imei_no = '" + imei_no + "' AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("c") == 0 ? false : true;
            }
        } catch (Exception e) {
            System.out.println("Junction Model checkImei() error" + e);
        }
        return result;
    }

    public int getFinalProgramVersionNo(int junction_id) {
        int program_version_no = 0;
        String query1 = " SELECT program_version_no FROM junction WHERE junction_id = ? AND final_revision='VALID'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, junction_id);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                program_version_no = rset.getInt("program_version_no");
            }

        } catch (Exception e) {
            System.out.println("Junction Model getFinalProgramVersionNo() error" + e);
        }
        return program_version_no;
    }

    public int getFinalPlanRevisionNo(int junction_id, int programVersionNo) {
        int getFinalPlanRevisionNo = 0;
        String query1 = " SELECT plan_revision_no FROM plan_info WHERE junction_id = ? AND program_version_no = ?  AND final_revision='VALID' LIMIT 1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, programVersionNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                getFinalPlanRevisionNo = rset.getInt("plan_revision_no");
            }

        } catch (Exception e) {
            System.out.println("Junction Model getFinalPlanRevisionNo() error" + e);
        }
        return getFinalPlanRevisionNo;
    }

    public int getNoOfPlans(int junction_id, int program_version_no) {
        int noOfplans = 0;
        try {
            ResultSet rset = connection.prepareStatement("select no_of_plans from junction WHERE junction_id = " + junction_id + " AND program_version_no = " + program_version_no).executeQuery();
            rset.next();
            noOfplans = Integer.parseInt(rset.getString(1));
            System.out.println(noOfplans);
        } catch (Exception e) {
            System.out.println("JunctionModel getNoOfPlans() Error: " + e);
        }
        return noOfplans;
    }

    public int getFinalSideRevisionNo(int junction_id, int programVersionNo) {
        int side_revision_no = 0;
        String query1 = " SELECT side_revision_no FROM slave_info WHERE junction_id = ? AND program_version_no = ? AND final_revision='VALID' LIMIT 1";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, programVersionNo);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                side_revision_no = rset.getInt("side_revision_no");
            }

        } catch (Exception e) {
            System.out.println("Junction Model getFinalSideRevisionNo() error" + e);
        }
        return side_revision_no;
    }

    public int getNoOfSides(int junction_id, int program_version_no) {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select no_of_sides from junction WHERE junction_id = " + junction_id + " AND program_version_no = " + program_version_no).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println(noOfRows);
        } catch (Exception e) {
            System.out.println("JunctionModel getNoOfSides() Error: " + e);
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
