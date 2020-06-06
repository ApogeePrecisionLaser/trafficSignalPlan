/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Controller;

import com.ts.junction.tableClasses.Junction;
import com.ts.log.Model.SeverityLevelModel;
import com.ts.log.tableClasses.SeverityLevel;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class SeverityLevelController extends HttpServlet {

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
        SeverityLevelModel severityLevelModel = new SeverityLevelModel();
        ServletContext ctx = getServletContext();
        severityLevelModel.setDriverClass(ctx.getInitParameter("driverClass"));
        severityLevelModel.setConnectionString(ctx.getInitParameter("connectionString"));
        severityLevelModel.setDb_userName(ctx.getInitParameter("db_userName"));
        severityLevelModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        severityLevelModel.setConnection();
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 4, noOfRowsInTable;
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
               String searchstate=request.getParameter("searchstate");
         
        if (searchstate == null) {
            searchstate = "";
        }
         
       
        
         String JQstring = request.getParameter("action1");
         
          if (JQstring != null) {
                PrintWriter out = response.getWriter();
            List<String> list = null;
        if (JQstring.equals("getState")) {
                   list = severityLevelModel.getState();
                }
         
         Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                
                return;
          }
         if (task.equals("SearchAllRecords")) {
         searchstate="";
        
        }
         
        if (task.equals("Save") || task.equals("Save AS New")) {
            SeverityLevel severityLevel = new SeverityLevel();
            String severity_level = request.getParameter("severity_number");
            int id = 0;
            try {
                id =  Integer.parseInt(request.getParameter("severity_level_id"));
            } catch (Exception ex) {
                id = 0;
//                System.out.println("ad_asso_site_detail_id error: " + ex);
            }
            if (task.equals("Save AS New")) {
                id = 0;
            }
            severityLevel.setSeverity_level_id(id);
            severityLevel.setSeverity_number(Integer.parseInt(severity_level));
            severityLevel.setRemark(request.getParameter("remark"));
            if (id == 0) {
               
                    severityLevelModel.insertRecord(severityLevel);
                
            } else {
                // update existing record.
                severityLevelModel.updateRecord(severityLevel);
            }
        }
         if (task.equals("Delete")) {
            severityLevelModel.deleteRecord(Integer.parseInt(request.getParameter("severity_level_id")));  // Pretty sure that camera_id will be available.
        }
        
        
        noOfRowsInTable = severityLevelModel.getNoOfRows(searchstate); // get the number of records (rows) in the table.

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
        if (buttonAction.equals("Next")){
            searchstate= request.getParameter("manname");
     noOfRowsInTable = severityLevelModel.getNoOfRows(searchstate);
        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            searchstate= request.getParameter("manname");
     noOfRowsInTable = severityLevelModel.getNoOfRows(searchstate);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            searchstate= request.getParameter("manname");
      
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            searchstate= request.getParameter("manname");
     noOfRowsInTable = severityLevelModel.getNoOfRows(searchstate);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        List<SeverityLevel> list1 = severityLevelModel.showData(lowerLimit, noOfRowsToDisplay,searchstate);
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
         request.setAttribute("manname", searchstate);
         
        request.setAttribute("searchstate", searchstate);
        request.setAttribute("severity_level", list1);
        request.setAttribute("message", severityLevelModel.getMessage());
        request.setAttribute("msgBgColor", severityLevelModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.getRequestDispatcher("/severity_level_view").forward(request, response);
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
