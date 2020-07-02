/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;
import com.ts.junction.Model.PlanDetailModel;
import com.ts.junction.tableClasses.PlanDetails;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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

/**
 *
 * @author DELL
 */
public class PlanDetailsController extends HttpServlet {

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
        
        PlanDetailModel planDetailModel = new PlanDetailModel();
        ServletContext ctx = getServletContext();
        planDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        planDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        planDetailModel.setDb_userName(ctx.getInitParameter("db_userName"));
        planDetailModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        planDetailModel.setConnection();
        String task = request.getParameter("task");
        String requester = request.getParameter("requester");
        if(task == null) {
            task = "";
        }
        if (task.equals("Save") || task.equals("Save AS New")) {
         
        
                PlanDetails planDetails = new PlanDetails();
                int plan_detail_id;
                try {
                    plan_detail_id = Integer.parseInt(request.getParameter("plan_id").trim());
                } catch (Exception e) {
                    plan_detail_id = 0;
                }
                if (task.equals("Save AS New")) {
                    plan_detail_id = 0;
                }
                               
                planDetails.setPlan_id(plan_detail_id);
                planDetails.setPlan_no(Integer.parseInt(request.getParameter("plan_no").trim()));
                planDetails.setOn_time_hour(Integer.parseInt(request.getParameter("on_time_hour").trim()));
                planDetails.setOn_time_min(Integer.parseInt(request.getParameter("on_time_min").trim()));
                planDetails.setOff_time_hour(Integer.parseInt(request.getParameter("off_time_hour").trim()));
                planDetails.setOff_time_min(Integer.parseInt(request.getParameter("off_time_min").trim()));
                planDetails.setMode(request.getParameter("mode").trim());
                planDetails.setSide1_green_time(Integer.parseInt(request.getParameter("side1_green_time").trim()));
                planDetails.setSide2_green_time(Integer.parseInt(request.getParameter("side2_green_time").trim()));
                planDetails.setSide3_green_time(Integer.parseInt(request.getParameter("side3_green_time").trim()));
                planDetails.setSide4_green_time(Integer.parseInt(request.getParameter("side4_green_time").trim()));
                planDetails.setSide5_green_time(Integer.parseInt(request.getParameter("side5_green_time").trim()));
                planDetails.setSide1_amber_time(Integer.parseInt(request.getParameter("side1_amber_time").trim()));
                planDetails.setSide2_amber_time(Integer.parseInt(request.getParameter("side2_amber_time").trim()));
                planDetails.setSide3_amber_time(Integer.parseInt(request.getParameter("side3_amber_time").trim()));
                planDetails.setSide4_amber_time(Integer.parseInt(request.getParameter("side4_amber_time").trim()));
                planDetails.setSide5_amber_time(Integer.parseInt(request.getParameter("side5_amber_time").trim()));
                planDetails.setTransferred_status(request.getParameter("transferred_status").trim());
                planDetails.setRemark(request.getParameter("remark").trim());
                
                if (plan_detail_id == 0) {
                // validation was successful so now insert record.
                planDetailModel.insertRecord(planDetails);
            } else {
                // Get the error message regarding validate plans.
                planDetailModel.updateRecord(planDetails);
            }
            
        }    
        
        if(task.equals("getPlanNo")) {
            try{
                PrintWriter out = response.getWriter();
                JSONObject jsonObj = new JSONObject();
                int planNo = planDetailModel.getMaxPlanNo();
                jsonObj.put("plan_no", planNo);
                out.println(jsonObj);
                out.flush();

            } catch (JSONException ex) {
                Logger.getLogger(PhaseInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if(task.equals("Delete")) {
            int plan_detail_id=Integer.parseInt(request.getParameter("plan_id").trim());
            try {
                planDetailModel.deleteRecord(plan_detail_id);
            } catch (SQLException e) {
                //printStackTrace();
            }
  
            
              
        }
        
        String searchCommandName = "";
        List<PlanDetails> list1 = new ArrayList<>();

        searchCommandName = request.getParameter("searchParameterName");

        try {
            if (searchCommandName == null) {
                searchCommandName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        noOfRowsInTable = planDetailModel.getNoOfRows();
     
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
        
        list1 = planDetailModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandName);
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
        request.setAttribute("no_of_plans", list1.size());
        request.setAttribute("plandetails", list1);
        request.setAttribute("message", planDetailModel.getMessage());
        request.setAttribute("msgBgColor", planDetailModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
         request.getRequestDispatcher("/plan_details").forward(request, response); 
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
