/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import com.ts.junction.Model.JunctionPlanMapModel;
import com.ts.junction.tableClasses.JunctionPlanMap;
import com.ts.junction.tableClasses.PlanDetails;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class JunctionPlanMapController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        JunctionPlanMapModel junctionPlanMapModel = new JunctionPlanMapModel();
        ServletContext ctx = getServletContext();
        junctionPlanMapModel.setDriverClass(ctx.getInitParameter("driverClass"));
        junctionPlanMapModel.setConnectionString(ctx.getInitParameter("connectionString"));
        junctionPlanMapModel.setDb_userName(ctx.getInitParameter("db_userName"));
        junctionPlanMapModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        junctionPlanMapModel.setConnection();
        String task = request.getParameter("task");
        try {
            //----- This is only for Vendor key Person JQuery
            String jqstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (jqstring != null) {
                List<String> list = null;
                if (jqstring.equals("getOnOffTime")) {
                    list = junctionPlanMapModel.getOnOffTime(q);
                }
                
                if (jqstring.equals("getDate")) {
                    list = junctionPlanMapModel.getDateTime(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
        }
        
        if(task == null) {
            task = "";
        }
        if (task.equals("Save") || task.equals("Save AS New")) {         
        
                JunctionPlanMap junctionPlanMap = new JunctionPlanMap();
                int junction_plan_map_id;
                try {
                    junction_plan_map_id = Integer.parseInt(request.getParameter("junction_plan_map_id").trim());
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
                String junction_name = request.getParameter("junction_name");
                
                junctionPlanMap.setJunction_plan_map_id(junction_plan_map_id);
                junctionPlanMap.setJunction_name(junction_name);
                junctionPlanMap.setOn_time_hr(Integer.parseInt(on_time.split(":")[0]));
                junctionPlanMap.setOn_time_min(Integer.parseInt(on_time.split(":")[1]));
                junctionPlanMap.setOff_time_hr(Integer.parseInt(off_time.split(":")[0]));
                junctionPlanMap.setOff_time_min(Integer.parseInt(off_time.split(":")[1]));
                junctionPlanMap.setOrder_no(Integer.parseInt(request.getParameter("order_no").trim()));
                if(request.getParameter("date") != null && !request.getParameter("date").equals("")) {
                    String date_detail = request.getParameter("date").trim();
                    String from_date = date_detail.split("//")[0];
                    String to_date = date_detail.split("//")[1];
                    junctionPlanMap.setFrom_date(from_date);
                    junctionPlanMap.setTo_date(to_date);
                }
                if(request.getParameter("day") != null && !request.getParameter("day").equals("")) {
                    String day_detail = request.getParameter("day").trim();
                    junctionPlanMap.setDay(day_detail);
                }
                
                if (junction_plan_map_id == 0) {
                // validation was successful so now insert record.
                junctionPlanMapModel.insertRecord(junctionPlanMap);
            } else {
                // Get the error message regarding validate plans.
                junctionPlanMapModel.updateRecord(junctionPlanMap);
            }
            
        }  
        
        
        
      
        
//        if(task.equals("Delete")) {
//            int plan_detail_id=Integer.parseInt(request.getParameter("plan_id").trim());
//            try {
//                planDetailModel.deleteRecord(plan_detail_id);
//            } catch (SQLException e) {
//                printStackTrace();
//            }
//  
//            
//              
//        }

        String searchCommandName = "";
        List<JunctionPlanMap> list1 = new ArrayList<>();

        searchCommandName = request.getParameter("searchParameterName");

        try {
            if (searchCommandName == null) {
                searchCommandName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable=0;
        //noOfRowsInTable = junctionPlanMapModel.getNoOfRows();
     
        // get the number of records (rows) in the table.

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
        
        list1 = junctionPlanMapModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandName);
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
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        
        request.setAttribute("junctionPlanMapList", list1);
        
        request.setAttribute("junction_id", request.getParameter("junction_id"));
        request.setAttribute("no_of_sides", request.getParameter("no_of_sides"));
        request.setAttribute("junction_name", request.getParameter("junction_name"));
        request.setAttribute("message", junctionPlanMapModel.getMessage());
        request.setAttribute("msgBgColor", junctionPlanMapModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.getRequestDispatcher("/junction_plan_map").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
