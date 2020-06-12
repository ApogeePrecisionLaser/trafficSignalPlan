 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import com.ts.junction.Model.JunctionModel;
import com.ts.junction.Model.JunctionPlanMapModel;
import com.ts.junction.Model.junctionupdatemodel;
import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.JunctionPlanMap;
import com.ts.junction.tableClasses.PhaseData;
import com.ts.junction.tableClasses.PhaseInfo;
import com.ts.junction.tableClasses.PlanDetails;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author Shruti
 */
public class JunctionDetailsUpdate extends HttpServlet {

    public static JSONArray junarray = new JSONArray();
    //public static JSONObject junobj=new JSONObject();
    public static JSONArray junplanmaparray = new JSONArray();
    public static JSONArray plandetails = new JSONArray();
    //public static JSONObject junobj=new JSONObject();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        junctionupdatemodel jum = new junctionupdatemodel();
        JunctionModel junctionModel = new JunctionModel();
        JunctionPlanMapModel junctionPlanMapModel = new JunctionPlanMapModel();
        ServletContext ctx = getServletContext();
        junctionModel.setDriverClass(ctx.getInitParameter("driverClass"));
        junctionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        junctionModel.setDb_userName(ctx.getInitParameter("db_userName"));
        junctionModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        junctionModel.setConnection();

        String task = request.getParameter("task");
        String requester = request.getParameter("requester");
        List<JunctionPlanMap> list2 = new ArrayList<>();
        List<PlanDetails> list3 = new ArrayList<>();
        List<PhaseData> list4 = new ArrayList<>();
        List<JunctionPlanMap> listdata = null;
        try {
            //----- This is only for Vendor key Person JQuery
            String jqstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (jqstring != null) {
                List<String> list = null;
                if (jqstring.equals("getCityName")) {
                    list = junctionModel.getCityName(q, request.getParameter("action2"));
                } else if (jqstring.equals("getStateName")) {
                    list = junctionModel.getStateName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    if (data.equals("Disable")) {
                        out.print(data);
                    } else {
                        out.println(data);
                    }
                }
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
        }

        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 4, noOfRowsInTable;
        if (task == null) {
            task = "";
        }

        if (task.equals("Delete")) {
            // Pretty sure that junction_id will be available.
            int junction_id = Integer.parseInt(request.getParameter("junction_id").trim());
            junctionModel.deleteRecord(junction_id);
        }

        if (task.equals("Save") || task.equals("Save AS New")) {
            String side_1_name, side_3_name = null, side_4_name = null, side_5_name = null, side_2_name;
            Junction junction = new Junction();
            String junction_name = request.getParameter("junction_name");
            String address_1 = request.getParameter("address_1");
            String address_2 = request.getParameter("address_2");
            String state_name = request.getParameter("state_name");
            String city_name = request.getParameter("city_name");
            String controller_model = request.getParameter("controller_model");
            int no_of_sides = Integer.parseInt(request.getParameter("no_of_sides"));
            int amber_time = Integer.parseInt(request.getParameter("amber_time"));
            int flash_rate = Integer.parseInt(request.getParameter("flash_rate"));
            int no_of_plans = Integer.parseInt(request.getParameter("no_of_plans"));
            String mobile_no = request.getParameter("mobile_no");
            String sim_no = request.getParameter("sim_no");
            String imei_no = request.getParameter("imei_no");
            int instant_green_time = Integer.parseInt(request.getParameter("instant_green_time"));
            String pedestrian = request.getParameter("pedestrian");
            junction.setPedestrian(pedestrian.equals("YES") ? "Y" : "N");
            junction.setPedestrian_time(Integer.parseInt(request.getParameter("pedestrian_time")));
            junction.setFile_no(Integer.parseInt(request.getParameter("file_no")));
            junction.setRemark(request.getParameter("remark"));

            side_1_name = request.getParameter("side_1_name");
            side_2_name = request.getParameter("side_2_name");
            switch (no_of_sides) {
                case 5:
                    side_5_name = request.getParameter("side_5_name");
                case 4:
                    side_4_name = request.getParameter("side_4_name");
                case 3:
                    side_3_name = request.getParameter("side_3_name");
            }

            int id = 0;
            try {
                id = Integer.parseInt(request.getParameter("junction_id").trim());
            } catch (Exception ex) {
                id = 0;
//                System.out.println("ad_asso_site_detail_id error: " + ex);
            }
            if (task.equals("Save AS New")) {
                id = 0;
            }
            junction.setJunction_id(id);
            junction.setJunction_name(junction_name);
            junction.setAddress1(address_1);
            junction.setAddress2(address_2);
            junction.setState_name(state_name);
            junction.setCity_name(city_name);
            junction.setController_model(controller_model);
            junction.setNo_of_sides(no_of_sides);
            junction.setAmber_time(amber_time);
            junction.setFlash_rate(flash_rate);
            junction.setNo_of_plans(no_of_plans);
            junction.setMobile_no(mobile_no);
            junction.setSim_no(sim_no);
            junction.setImei_no(imei_no);
            junction.setInstant_green_time(instant_green_time);
            junction.setSide1_name(side_1_name);
            junction.setSide2_name(side_2_name);
            junction.setSide3_name(side_3_name);
            junction.setSide4_name(side_4_name);
            junction.setSide5_name(side_5_name);
            if (id == 0) {
                if (!junctionModel.checkImei(imei_no)) {
                    junctionModel.insertRecord(junction);
                } else {
                    junctionModel.setMessage("IMEI is already exists");
                    junctionModel.setMsgBgColor("orange");
                }
            } else {
                // update existing record.
                junctionModel.updateRecord(junction);
            }
        }

        noOfRowsInTable = junctionModel.getNoOfRows();                  // get the number of records (rows) in the table.

        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }

        List<Junction> list1 = junctionModel.showData(lowerLimit, noOfRowsToDisplay);
        int junction_id_selected = 0;
        if (task.equals("SelectedJunctionPlans")) {

            junction_id_selected = Integer.parseInt(request.getParameter("junction_id_selected").trim());

            request.setAttribute("j_id", junction_id_selected);
            if (junction_id_selected != 0) {

                try {

                    // list2 = junctionModel.showDataPlanMap(lowerLimit, noOfRowsToDisplay, junction_id_selected);
//                            JSONObject gson = new JSONObject();
//                     gson.put("list3",list2);
                    // return;
                } catch (Exception e) {
                    System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
                }

            } else {
                System.out.println("junction selected id is 0");
            }
        }

        int junction_id_selected1 = 0;
        // int  junction_plan_map_id = Integer.parseInt(request.getParameter("junction_plan_map_id").trim());
        if (task.equals("SelectedJunctionPhase")) {
            // junction_id_selected = Integer.parseInt(request.getParameter("j_id").trim());
            //   list2 = junctionModel.showDataPlanMap(lowerLimit, noOfRowsToDisplay, junction_id_selected);
            int junction_plan_map_id_selected = 0;
            junction_plan_map_id_selected = Integer.parseInt(request.getParameter("junction_plan_map_id_selected").trim());
            int plan_no = Integer.parseInt(request.getParameter("plan_no").trim());
            junction_id_selected1 = Integer.parseInt(request.getParameter("junction_id").trim());

            if (junction_id_selected1 != 0) {
                try {
                    list2 = junctionModel.showDataPlanMap(lowerLimit, noOfRowsToDisplay, junction_id_selected1);
                    list3 = junctionModel.showDataPlans(lowerLimit, noOfRowsToDisplay, junction_id_selected1, plan_no);
                    list4 = junctionModel.showDataPhaseDetails(lowerLimit, noOfRowsToDisplay, junction_plan_map_id_selected, plan_no);

//                            JSONObject gson = new JSONObject();
//                     gson.put("list3",list2);
                    // return;
                } catch (Exception e) {
                    System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
                }

            } else {
                System.out.println("junction selected id is 0");
            }
        }
        if (task.equalsIgnoreCase("plandetailsnormal")) {
            JSONArray jsonarr1 = new JSONArray();
              
                JSONObject jobj1 = new JSONObject();
            try {
                String day = request.getParameter("day");
             //   String todate = request.getParameter("to_date");
                int j_id11 = Integer.parseInt(request.getParameter("junction_id"));
                 int p_id11 = Integer.parseInt(request.getParameter("plan_id"));
                

                list3 = junctionModel.showDataPlansdetailsnormal(lowerLimit, noOfRowsToDisplay, j_id11,p_id11);

               // jobj.put("size_1", list3.size());
                for (int i = 0; i < list3.size(); i++) {

                    jsonarr1.add(list3.get(i).getPlan_no());
                    jsonarr1.add(list3.get(i).getOn_time_hour());
                    jsonarr1.add(list3.get(i).getOn_time_min());
                    jsonarr1.add(list3.get(i).getOff_time_hour());
                    jsonarr1.add(list3.get(i).getOff_time_min());
                    jsonarr1.add(list3.get(i).getMode());
                    jsonarr1.add(list3.get(i).getSide1_green_time());
                    jsonarr1.add(list3.get(i).getSide2_green_time());
                    jsonarr1.add(list3.get(i).getSide3_green_time());
                    jsonarr1.add(list3.get(i).getSide4_green_time());
                    jsonarr1.add(list3.get(i).getSide5_green_time());
                    jsonarr1.add(list3.get(i).getSide1_amber_time());
                    jsonarr1.add(list3.get(i).getSide2_amber_time());
                    jsonarr1.add(list3.get(i).getSide3_amber_time());
                    jsonarr1.add(list3.get(i).getSide4_amber_time());
                    jsonarr1.add(list3.get(i).getSide5_amber_time());
                    jsonarr1.add(list3.get(i).getTransferred_status());
                    jsonarr1.add(list3.get(i).getRemark());

                 jobj1.put("p_id", list3.get(i).getPlan_id());
                    // jobj1.put("j_id", list2.get(i).getJunction_id());
                }
                System.out.println("json array --" + jsonarr1);

                jobj1.put("data", jsonarr1);

                PrintWriter out1 = response.getWriter();
                out1.print(jobj1.toString());
                return;
                 
            } catch (JSONException ex) {
                Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
                
                
                
          if (task.equalsIgnoreCase("plandetailsday")) {
            JSONArray jsonarr1 = new JSONArray();
              
                JSONObject jobj1 = new JSONObject();
            try {
                String day = request.getParameter("day");
             //   String todate = request.getParameter("to_date");
                int j_id11 = Integer.parseInt(request.getParameter("junction_id"));
                

                list3 = junctionModel.showDataPlansdetailsday(lowerLimit, noOfRowsToDisplay, j_id11,day);

               // jobj.put("size_1", list3.size());
                for (int i = 0; i < list3.size(); i++) {

                    jsonarr1.add(list3.get(i).getPlan_no());
                    jsonarr1.add(list3.get(i).getOn_time_hour());
                    jsonarr1.add(list3.get(i).getOn_time_min());
                    jsonarr1.add(list3.get(i).getOff_time_hour());
                    jsonarr1.add(list3.get(i).getOff_time_min());
                    jsonarr1.add(list3.get(i).getMode());
                    jsonarr1.add(list3.get(i).getSide1_green_time());
                    jsonarr1.add(list3.get(i).getSide2_green_time());
                    jsonarr1.add(list3.get(i).getSide3_green_time());
                    jsonarr1.add(list3.get(i).getSide4_green_time());
                    jsonarr1.add(list3.get(i).getSide5_green_time());
                    jsonarr1.add(list3.get(i).getSide1_amber_time());
                    jsonarr1.add(list3.get(i).getSide2_amber_time());
                    jsonarr1.add(list3.get(i).getSide3_amber_time());
                    jsonarr1.add(list3.get(i).getSide4_amber_time());
                    jsonarr1.add(list3.get(i).getSide5_amber_time());
                    jsonarr1.add(list3.get(i).getTransferred_status());
                    jsonarr1.add(list3.get(i).getRemark());

                 jobj1.put("jpm", list3.get(i).getMode());
                    jobj1.put("p_id", list3.get(i).getPlan_id());
                }
                System.out.println("json array --" + jsonarr1);

                jobj1.put("data", jsonarr1);

                PrintWriter out1 = response.getWriter();
                out1.print(jobj1.toString());
                return;
                 
            } catch (JSONException ex) {
                Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        
        if (task.equalsIgnoreCase("plandetails")) {
            JSONArray jsonarr1 = new JSONArray();
              
                JSONObject jobj1 = new JSONObject();
            try {
                String fromdate = request.getParameter("from_date");
                String todate = request.getParameter("to_date");
                int j_id11 = Integer.parseInt(request.getParameter("junction_id"));
                

                list3 = junctionModel.showDataPlansdetails(lowerLimit, noOfRowsToDisplay, j_id11, fromdate, todate);

               // jobj.put("size_1", list3.size());
                for (int i = 0; i < list3.size(); i++) {

                    jsonarr1.add(list3.get(i).getPlan_no());
                    jsonarr1.add(list3.get(i).getOn_time_hour());
                    jsonarr1.add(list3.get(i).getOn_time_min());
                    jsonarr1.add(list3.get(i).getOff_time_hour());
                    jsonarr1.add(list3.get(i).getOff_time_min());
                    jsonarr1.add(list3.get(i).getMode());
                    jsonarr1.add(list3.get(i).getSide1_green_time());
                    jsonarr1.add(list3.get(i).getSide2_green_time());
                    jsonarr1.add(list3.get(i).getSide3_green_time());
                    jsonarr1.add(list3.get(i).getSide4_green_time());
                    jsonarr1.add(list3.get(i).getSide5_green_time());
                    jsonarr1.add(list3.get(i).getSide1_amber_time());
                    jsonarr1.add(list3.get(i).getSide2_amber_time());
                    jsonarr1.add(list3.get(i).getSide3_amber_time());
                    jsonarr1.add(list3.get(i).getSide4_amber_time());
                    jsonarr1.add(list3.get(i).getSide5_amber_time());
                    jsonarr1.add(list3.get(i).getTransferred_status());
                    jsonarr1.add(list3.get(i).getRemark());

                 jobj1.put("jpm", list3.get(i).getMode());
                    // jobj1.put("j_id", list2.get(i).getJunction_id());
                }
                System.out.println("json array --" + jsonarr1);

                jobj1.put("data", jsonarr1);

                PrintWriter out1 = response.getWriter();
                out1.print(jobj1.toString());
                return;
                 
            } catch (JSONException ex) {
                Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (task.equals("testing")) {
            JSONArray jsonarr = new JSONArray();
            JSONArray jsonarr1 = new JSONArray();
            JSONObject jobj = new JSONObject();
            JSONObject jobj1 = new JSONObject();
            int plan_no = 1;

            junction_id_selected1 = Integer.parseInt(request.getParameter("junction_id").trim());
            String filter = request.getParameter("filterdata");
            if (junction_id_selected1 != 0) {
                try {
                    list2 = junctionModel.showDataPlanMapbyfilter(lowerLimit, 6, junction_id_selected1, filter);

                    if (filter.equalsIgnoreCase("date")) {
                        System.out.println("lsit 2 size -" + list2.size());
                        jobj.put("size_1", list2.size());
                        for (int i = 0; i < list2.size(); i++) {
                            jsonarr1.add(list2.get(i).getFrom_date());
                            jsonarr1.add(list2.get(i).getTo_date());
                            jsonarr1.add(list2.get(i).getPlan_no());

                            // jsonarr1.add("");
                            jobj1.put("jpm", list2.get(i).getJunction_plan_map_id());
                            jobj1.put("j_id", list2.get(i).getJunction_id());
                        }
                        System.out.println("json array --" + jsonarr1);

                        jobj1.put("data", jsonarr1);

                        PrintWriter out1 = response.getWriter();
                        out1.print(jobj1.toString());
                        return;

                    } else if (filter.equalsIgnoreCase("day")) {

                        System.out.println("lsit 2 size -" + list2.size());
                        jobj.put("size_1", list2.size());
                        for (int i = 0; i < list2.size(); i++) {
                            jsonarr1.add(list2.get(i).getDay());
                            // jsonarr1.add(list2.get(i).getTo_date());
                            jsonarr1.add(list2.get(i).getPlan_no());

                        }
                        System.out.println("json array --" + jsonarr1);

                        jobj1.put("data", jsonarr1);
                        PrintWriter out1 = response.getWriter();
                        out1.print(jobj1.toString());
                        return;

                    } else {

                        System.out.println("lsit 2 size -" + list2.size());
                        jobj.put("size_1", list2.size());
                        for (int i = 0; i < list2.size(); i++) {
                            jsonarr1.add(list2.get(i).getOn_time_hr() + ":" + list2.get(i).getOn_time_min() + "to" + list2.get(i).getOff_time_hr() + ":" + list2.get(i).getOff_time_min());
                            //  jsonarr1.add(list2.get(i).getOn_time_min());
                            // jsonarr1.add(list2.get(i).getOff_time_hr());
                            // jsonarr1.add(list2.get(i).getOff_time_min());
                           
                             jsonarr1.add(list2.get(i).getPlan_id());
                             //jobj1.put("p_id", list2.get(i).getPlan_id());
                            

                        }
                        System.out.println("json array --" + jsonarr1);

                        jobj1.put("data", jsonarr1);
                        PrintWriter out1 = response.getWriter();
                        out1.print(jobj1.toString());
                        return;

                    }

                    //list3 = junctionModel.showDataPlansbyfilter(lowerLimit, noOfRowsToDisplay,junction_id_selected1,plan_no,filter);
                    // request.setAttribute("SelectedJunctionPlans123", list2);
//                            JSONObject gson = new JSONObject();
//                     gson.put("list3",list2);
                    // return;
                } catch (Exception e) {
                    System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
                }

            } else {
                System.out.println("junction selected id is 0");
            }
        }

        if (task.equals("junctionsave")) {

            try {
                JSONObject jsonobj = new JSONObject();
                String side_1_name, side_3_name = null, side_4_name = null, side_5_name = null, side_2_name;
                Junction junction = new Junction();
                String junction_name = request.getParameter("junction_name");
                jsonobj.put("junction_name", junction_name);
                String address_1 = request.getParameter("address_1");
                jsonobj.put("address_1", address_1);
                String address_2 = request.getParameter("address_2");
                jsonobj.put("address_2", address_2);
                String state_name = request.getParameter("state_name");
                jsonobj.put("state_name", state_name);
                String city_name = request.getParameter("city_name");
                jsonobj.put("city_name", city_name);
                String controller_model = request.getParameter("controller_model");
                jsonobj.put("controller_model", controller_model);
                int no_of_sides = Integer.parseInt(request.getParameter("no_of_sides"));
                jsonobj.put("no_of_sides", no_of_sides);
                int amber_time = Integer.parseInt(request.getParameter("amber_time"));
                jsonobj.put("amber_time", amber_time);
                int flash_rate = Integer.parseInt(request.getParameter("flash_rate"));
                jsonobj.put("flash_rate", flash_rate);
                int no_of_plans = Integer.parseInt(request.getParameter("no_of_plans"));
                jsonobj.put("no_of_plans", no_of_plans);
                String mobile_no = request.getParameter("mobile_no");
                jsonobj.put("mobile_no", mobile_no);
                String sim_no = request.getParameter("sim_no");
                jsonobj.put("sim_no", sim_no);
                String imei_no = request.getParameter("imei_no");
                jsonobj.put("imei_no", imei_no);
                int instant_green_time = Integer.parseInt(request.getParameter("instant_green_time"));
                jsonobj.put("instant_green_time", instant_green_time);
                String pedestrian = request.getParameter("pedestrian");
                // jsonobj.put("pedestrian",pedestrian );
                junction.setPedestrian(pedestrian.equals("YES") ? "Y" : "N");
                jsonobj.put("pedestrian", junction.getPedestrian());
                junction.setPedestrian_time(Integer.parseInt(request.getParameter("pedestrian_time")));
                jsonobj.put("pedestrian_time", junction.getPedestrian_time());
                junction.setFile_no(Integer.parseInt(request.getParameter("file_no")));
                jsonobj.put("file_no", junction.getFile_no());
                junction.setRemark(request.getParameter("remark"));
                jsonobj.put("remark", junction.getRemark());
                side_1_name = request.getParameter("side_1_name");
                jsonobj.put("side_1_name", side_1_name);
                side_2_name = request.getParameter("side_2_name");
                jsonobj.put("side_2_name", side_2_name);
                switch (no_of_sides) {
                    case 5:
                        side_5_name = request.getParameter("side_5_name");
                        jsonobj.put("side_5_name", side_5_name);
                    case 4:
                        side_4_name = request.getParameter("side_4_name");
                        jsonobj.put("side_4_name", side_4_name);
                    case 3:
                        side_3_name = request.getParameter("side_3_name");
                        jsonobj.put("side_3_name", side_3_name);
                }
                this.junarray.add(jsonobj);
                System.out.println("hh");
                //this.junobj.put("jundata11",jsonobj );
                //PrintWriter out12 = response.getWriter();
                //out12.print(junobj.toString());
                //return;
            } catch (JSONException ex) {
                Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (task.equals("junctionplanmap")) {
            try {
                JSONObject jsonobj = new JSONObject();
                JunctionPlanMap junctionPlanMap = new JunctionPlanMap();
                int junction_plan_map_id;
                try {
                    junction_plan_map_id = Integer.parseInt(request.getParameter("junction_plan_map_id1").trim());
                    String j = request.getParameter("jmpid");
                } catch (Exception e) {
                    junction_plan_map_id = 0;
                }
                if (task.equals("Save AS New")) {
                    junction_plan_map_id = 0;
                }

                String time = request.getParameter("start_time").trim();
                String on_time = time.split("-")[0];
                String off_time = time.split("-")[1];
                String junction_id = request.getParameter("junction_id");
                String junction_name = request.getParameter("junction_name1");
                // int ju = Integer.parseInt(request.getParameter("junction_plan_map_id1"));

                junctionPlanMap.setJunction_plan_map_id(junction_plan_map_id);
                jsonobj.put("junction_plan_map_id", junctionPlanMap.getJunction_plan_map_id());
                junctionPlanMap.setJunction_name(junction_name);
                jsonobj.put("junction_name", junctionPlanMap.getJunction_name());
                junctionPlanMap.setOn_time_hr(Integer.parseInt(on_time.split(":")[0]));
                jsonobj.put("On_time_hr", junctionPlanMap.getOn_time_hr());
                junctionPlanMap.setOn_time_min(Integer.parseInt(on_time.split(":")[1]));
                jsonobj.put("On_time_min", junctionPlanMap.getOn_time_min());
                junctionPlanMap.setOff_time_hr(Integer.parseInt(off_time.split(":")[0]));
                jsonobj.put("Off_time_hr", junctionPlanMap.getOff_time_hr());
                junctionPlanMap.setOff_time_min(Integer.parseInt(off_time.split(":")[1]));
                jsonobj.put("Off_time_min", junctionPlanMap.getOff_time_min());
                junctionPlanMap.setOrder_no(Integer.parseInt(request.getParameter("order_no").trim()));
                jsonobj.put("Order_no", junctionPlanMap.getOrder_no());
                String date = request.getParameter("date");
                String day = request.getParameter("day");
                if (request.getParameter("date") != null && !request.getParameter("date").equals("//") && !request.getParameter("date").equals("")) {
                    String date_detail = request.getParameter("date").trim();
                    //  jsonobj.put("date_detail",date_detail);
                    String from_date = date_detail.split("//")[0];
                    //  jsonobj.put("from_date",from_date);
                    String to_date = date_detail.split("//")[1];
                    //  jsonobj.put("to_date",to_date);
                    junctionPlanMap.setFrom_date(from_date);
                    jsonobj.put("from_date", junctionPlanMap.getFrom_date());
                    junctionPlanMap.setTo_date(to_date);
                    jsonobj.put("to_date", junctionPlanMap.getTo_date());
                }
                if (request.getParameter("day") != null && !request.getParameter("day").equals("")) {
                    String day_detail = request.getParameter("day").trim();
                    junctionPlanMap.setDay(day_detail);
                    jsonobj.put("to_date", junctionPlanMap.getDay());
                }

                junplanmaparray.add(jsonobj);
                System.out.println("okk");
            } catch (JSONException ex) {
                Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (task.equals("plandet")) {

            try {

                JSONObject jsonobj = new JSONObject();

                PlanDetails planDetails = new PlanDetails();
                int plan_detail_id;
                try {
                    plan_detail_id = Integer.parseInt(request.getParameter("plan_no").trim());
                } catch (Exception e) {
                    plan_detail_id = 0;
                }

                planDetails.setPlan_id(plan_detail_id);
                jsonobj.put("plan_id", planDetails.getPlan_id());
                planDetails.setPlan_no(Integer.parseInt(request.getParameter("plan_no").trim()));
                jsonobj.put("plan_no", planDetails.getPlan_no());
                planDetails.setOn_time_hour(Integer.parseInt(request.getParameter("on_time_hour").trim()));
                jsonobj.put("on_time_hour", planDetails.getOn_time_hour());
                planDetails.setOn_time_min(Integer.parseInt(request.getParameter("on_time_min").trim()));
                jsonobj.put("on_time_min", planDetails.getOn_time_min());
                planDetails.setOff_time_hour(Integer.parseInt(request.getParameter("off_time_hour").trim()));
                jsonobj.put("off_time_hour", planDetails.getOff_time_hour());
                planDetails.setOff_time_min(Integer.parseInt(request.getParameter("off_time_min").trim()));
                jsonobj.put("off_time_min", planDetails.getOff_time_min());
                planDetails.setMode(request.getParameter("mode").trim());
                jsonobj.put("mode", planDetails.getMode());
                planDetails.setSide1_green_time(Integer.parseInt(request.getParameter("side1_green_time").trim()));
                jsonobj.put("side1_green_time", planDetails.getSide1_green_time());
                planDetails.setSide2_green_time(Integer.parseInt(request.getParameter("side2_green_time").trim()));
                jsonobj.put("side2_green_time", planDetails.getSide2_green_time());

                planDetails.setSide3_green_time(Integer.parseInt(request.getParameter("side3_green_time").trim()));
                jsonobj.put("side3_green_time", planDetails.getSide3_green_time());
                planDetails.setSide4_green_time(Integer.parseInt(request.getParameter("side4_green_time").trim()));
                jsonobj.put("side4_green_time", planDetails.getSide4_green_time());
                planDetails.setSide5_green_time(Integer.parseInt(request.getParameter("side5_green_time").trim()));
                jsonobj.put("side5_green_time", planDetails.getSide5_green_time());
                planDetails.setSide1_amber_time(Integer.parseInt(request.getParameter("side1_amber_time").trim()));
                jsonobj.put("side1_amber_time", planDetails.getSide1_amber_time());
                planDetails.setSide2_amber_time(Integer.parseInt(request.getParameter("side2_amber_time").trim()));
                jsonobj.put("side2_amber_time", planDetails.getSide2_amber_time());
                planDetails.setSide3_amber_time(Integer.parseInt(request.getParameter("side3_amber_time").trim()));
                jsonobj.put("side3_amber_time", planDetails.getSide3_amber_time());
                planDetails.setSide4_amber_time(Integer.parseInt(request.getParameter("side4_amber_time").trim()));
                jsonobj.put("side4_amber_time", planDetails.getSide4_amber_time());
                planDetails.setSide5_amber_time(Integer.parseInt(request.getParameter("side5_amber_time").trim()));
                jsonobj.put("side5_amber_time", planDetails.getSide5_amber_time());
                planDetails.setTransferred_status(request.getParameter("transferred_status").trim());
                jsonobj.put("transferred_status", planDetails.getTransferred_status());
                planDetails.setRemark(request.getParameter("remark").trim());
                jsonobj.put("remark", planDetails.getRemark());

                plandetails.add(jsonobj);

            } catch (JSONException ex) {
                Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //Final Form Submission
        if (task.equals("Final")) {

            JunctionPlanMap junctionPlanMap = new JunctionPlanMap();
            PlanDetails planDetails = new PlanDetails();
            Junction junction = new Junction();

            if (junarray.size() > 0) {
                int count = 0;
                for (int i = 0; i < junarray.size(); i++) {
                    try {
                        JSONObject object = (JSONObject) junarray.get(i);

                        //  junction.setJunction_id(Integer.parseInt(object.get("plan_id").toString()));
                        junction.setJunction_name(object.get("junction_name").toString());
                        junction.setAddress1(object.get("address_1").toString());
                        junction.setAddress2(object.get("address_2").toString());
                        junction.setState_name(object.get("state_name").toString());
                        junction.setCity_name(object.get("city_name").toString());
                        junction.setController_model(object.get("controller_model").toString());
                        junction.setNo_of_sides(Integer.parseInt(object.get("no_of_sides").toString()));
                        junction.setAmber_time(Integer.parseInt(object.get("amber_time").toString()));
                        junction.setFlash_rate(Integer.parseInt(object.get("flash_rate").toString()));
                        junction.setNo_of_plans(Integer.parseInt(object.get("no_of_plans").toString()));
                        junction.setMobile_no(object.get("mobile_no").toString());
                        junction.setSim_no(object.get("sim_no").toString());
                        junction.setImei_no(object.get("imei_no").toString());
                        junction.setInstant_green_time(Integer.parseInt(object.get("instant_green_time").toString()));
                        junction.setSide1_name(object.get("side_1_name").toString());
                        junction.setSide2_name(object.get("side_2_name").toString());
                        junction.setSide3_name(object.get("side_3_name").toString());
                        junction.setSide4_name(object.get("side_4_name").toString());
                        junction.setSide5_name(object.get("side_5_name").toString());
                        jum.insertjunctionRecord(junction);
                        count++;
                    } catch (JSONException ex) {
                        Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            junarray = null;
            if (junplanmaparray.size() > 0) {
                for (int j = 0; j < junplanmaparray.size() - 1; j++) {
                    try {
                        JSONObject object = (JSONObject) junplanmaparray.get(j);

                        junctionPlanMap.setJunction_plan_map_id(Integer.parseInt(object.get("junction_plan_map_id").toString()));
                        junctionPlanMap.setJunction_name(object.get("junction_name").toString());
                        junctionPlanMap.setOn_time_hr(Integer.parseInt(object.get("On_time_hr").toString()));
                        junctionPlanMap.setOn_time_min(Integer.parseInt(object.get("On_time_min").toString()));
                        junctionPlanMap.setOff_time_hr(Integer.parseInt(object.get("Off_time_hr").toString()));
                        junctionPlanMap.setOff_time_min(Integer.parseInt(object.get("Off_time_min").toString()));
                        junctionPlanMap.setOrder_no(Integer.parseInt(object.get("order_no").toString()));
                        String date = request.getParameter("date");
                        String day = request.getParameter("day");
                        if (object.get("from_date").toString() != null && !object.get("from_date").toString().equals("//") && !object.get("from_date").toString().equals("")) {
                            String date_detail = object.get("from_date").toString().trim();
                            String from_date = date_detail.split("//")[0];
                            String to_date = date_detail.split("//")[1];
                            junctionPlanMap.setFrom_date(from_date);
                            junctionPlanMap.setTo_date(to_date);
                        }
                        if (object.get("to_date").toString() != null && !object.get("to_date").toString().equals("")) {
                            String day_detail = object.get("to_date").toString().trim();
                            junctionPlanMap.setDay(day_detail);
                        }

                        // if (junction_plan_map_id == 0) {
                        //     int selected_plan_id =0;
                        //     selected_plan_id=Integer.parseInt(request.getParameter("selected_plan_id"));
                        // validation was successful so now insert record.
                        // junctionPlanMapModel.insertRecord(junctionPlanMap,selected_plan_id);
                    } catch (JSONException ex) {
                        Logger.getLogger(JunctionDetailsUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (plandetails.size() > 0) {
                for (int k = 0; k < plandetails.size() - 1; k++) {

                }

            }

        }

        //  SelectedJunctionPhase
        lowerLimit = lowerLimit + list1.size();
        noOfRowsTraversed = list1.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("junction_id", junction_id_selected1);

        System.out.println("datatata size  --" + list2.size());
        request.setAttribute("junction", list1);
        request.setAttribute("SelectedJunctionPlans123", list2);
        request.setAttribute("plandetailsSelected", list3);
        request.setAttribute("phasedetailsSelected", list4);
        request.setAttribute("message", junctionModel.getMessage());
        request.setAttribute("msgBgColor", junctionModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("IDGenerator1", new xyz());
        request.setAttribute("IDGenerator2", new xyz());
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.getRequestDispatcher("view/junction/Junction_detail_update.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
