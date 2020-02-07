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
         int lowerLimit=0, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
         List<PlanDetails> list1 = new ArrayList<>();
        list1 = planDetailModel.showData(lowerLimit, noOfRowsToDisplay,searchCommandName);
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
