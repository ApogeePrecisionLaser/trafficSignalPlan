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
import java.util.ArrayList;
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
        if (buttonAction.equals("Next")) // lowerLimit already has value such that it shows forward records, so do nothing here.
            {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }
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
