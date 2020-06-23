package com.ts.junction.Controller;

import com.ts.junction.Model.PlanDetailModel;
import com.ts.junction.tableClasses.PlanDetails;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author DELL
 */
public class PlanDetailIdController extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           PlanDetailModel planDetailModel = new PlanDetailModel();
        ServletContext ctx = getServletContext();
        planDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        planDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        planDetailModel.setDb_userName(ctx.getInitParameter("db_userName"));
        planDetailModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        planDetailModel.setConnection();
         String task = request.getParameter("task");
         
         
          try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getOnHr")) {
                    list = planDetailModel.getOnHr(q);
                }
               if (JQstring.equals("getOnMin")) {
                   list = planDetailModel.getOnMin(q, request.getParameter("action2"));
                }
               if (JQstring.equals("getOffHr")) {
                   list = planDetailModel.getOffHr(q);
                }
              if (JQstring.equals("getOffMin")) {
                   list = planDetailModel.getOffMin(q, request.getParameter("action2"));
              }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
               planDetailModel.closeConnection();
                return;
            }
         
          }catch (Exception e) {
            System.out.println("\n Error --PlanDetsilIdController get JQuery Parameters Part-" + e);
        }
         
         
//            int ontime_hr = 0;
//        int ontime_min = 0;
//          int offtime_hr = 0;
//          int offtime_min =0;
//            // List<PlanDetails> list1 = new ArrayList<>();
//            
//            
//              try {
//            ontime_hr = Integer.parseInt(request.getParameter("ontime_hr").trim());
//            if(ontime_hr==0){
//            ontime_hr=0;
//            }
//              ontime_min = Integer.parseInt(request.getParameter("ontime_min").trim());
//            
//               offtime_hr = Integer.parseInt(request.getParameter("offtime_hr").trim());
//            
//                offtime_min = Integer.parseInt(request.getParameter("offtime_min").trim());
//            
//        } catch (Exception e) {
//            System.out.println("Exception while searching in controller" + e);
//        }
        
            // List<PlanDetails> list1 = new ArrayList<>();
            String ontime_hr="";
            String  ontime_min="";
            String  offtime_hr="";
            String offtime_min="";
              try {
            ontime_hr = request.getParameter("ontime_hr").trim();
            if(ontime_hr==""){
            ontime_hr="";
            }
              ontime_min = request.getParameter("ontime_min").trim();
             if(ontime_min==""){
            ontime_min="";
            }
             offtime_hr = request.getParameter("offtime_hr").trim();
             if(offtime_hr==""){
            offtime_hr="";
            }
               offtime_min = request.getParameter("offtime_min").trim();
             if(offtime_min==""){
            offtime_min="";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
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
             
         int lowerLimit=0, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
           noOfRowsInTable = planDetailModel.getNoOfRows();
         
       
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
   
         
        list1 = planDetailModel.showDataPhase(lowerLimit, noOfRowsToDisplay,searchCommandName, ontime_hr,ontime_min,offtime_hr,offtime_min);

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
        
//        request.setAttribute("ontime_hr", ontime_hr);
//        request.setAttribute("ontime_min", ontime_min);
//        request.setAttribute("offtime_hr", offtime_hr);
//        request.setAttribute("offtime_min", offtime_min);
        request.setAttribute("IDGenerator", new xyz());
       request.getRequestDispatcher("/plan_detail_id").forward(request, response);
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