/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Controller;

import com.ts.log.Model.LogTableModel;
import com.ts.log.tableClasses.LogTable;
import com.ts.util.xyz;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class LogTableController extends HttpServlet {
    
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
        LogTableModel logTableModel = new LogTableModel();
        ServletContext ctx = getServletContext();
        logTableModel.setDriverClass(ctx.getInitParameter("driverClass"));
        logTableModel.setConnectionString(ctx.getInitParameter("connectionString"));
        logTableModel.setDb_userName(ctx.getInitParameter("db_userName"));
        logTableModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        logTableModel.setConnection();
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
         String searchjunction=request.getParameter("searchjunction");
       String searchside=request.getParameter("searchside");
       String searchdate=request.getParameter("searchdate");
        if (searchjunction == null) {
            searchjunction = "";
        }
         
       
        if (searchside == null) {
            searchside = "";
        }
        if (searchdate == null) {
            searchdate = "";
        }
        if (task.equals("SearchAllRecords")) {
         searchjunction="";
         searchside="";
         searchdate="";
        }
          String JQstring = request.getParameter("action1");
         
          if (JQstring != null) {
                PrintWriter out = response.getWriter();
            List<String> list = null;
        if (JQstring.equals("getJunctionName")) {
                    list = logTableModel.getState();
                }
         if (JQstring.equals("getSideName")) {
              String st=request.getParameter("action2");
                    list = logTableModel.getDistrict(st);
                }
         if (JQstring.equals("getdate")) {
            //  String st=request.getParameter("action2");
                    list = logTableModel.getDateTime();
                }
         Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                
                return;
          }
          
          
           String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
                 List listAll = null;
                  searchjunction=request.getParameter("searchjunction");
                 searchside=request.getParameter("searchside");
                 searchdate=request.getParameter("searchdate");
                if(searchjunction==null){
                    searchjunction="";
                 }
                  if(searchside==null){
                    searchside="";
                 }
                  if(searchdate==null){
                    searchdate="";
                 }
                String jrxmlFilePath;
                    response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=logTableModel.showDataReport(searchjunction,searchside,searchdate);
                jrxmlFilePath = ctx.getRealPath("/Report/logtablepdf.jrxml");
                byte[] reportInbytes = logTableModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
               
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                
                searchjunction=request.getParameter("searchjunction");
                 searchside=request.getParameter("searchside");
                 searchdate=request.getParameter("searchdate");
                if(searchjunction==null){
                    searchjunction="";
                 }
                  if(searchside==null){
                    searchside="";
                 }
                  if(searchdate==null){
                    searchdate="";
                 }
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/city.jrxml");
                listAll=logTableModel.showDataReport(searchjunction,searchside,searchdate);
                ByteArrayOutputStream reportInbytes = logTableModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        noOfRowsInTable = logTableModel.getNoOfRows(searchjunction,searchside,searchdate); // get the number of records (rows) in the table.

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
            searchjunction = request.getParameter("manname");
            searchside = request.getParameter("pname");
            searchdate = request.getParameter("dname");
              
           noOfRowsInTable = logTableModel.getNoOfRows(searchjunction,searchside,searchdate);
        }  // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
               searchjunction = request.getParameter("manname");
            searchside = request.getParameter("pname");
            searchdate = request.getParameter("dname");
              
           noOfRowsInTable = logTableModel.getNoOfRows(searchjunction,searchside,searchdate);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
               searchjunction = request.getParameter("manname");
            searchside = request.getParameter("pname");
            searchdate = request.getParameter("dname");
              
          // noOfRowsInTable = logTableModel.getNoOfRows(searchjunction,searchside,searchdate);
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
               searchjunction = request.getParameter("manname");
            searchside = request.getParameter("pname");
            searchdate = request.getParameter("dname");
              
           noOfRowsInTable = logTableModel.getNoOfRows(searchjunction,searchside,searchdate);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        List<LogTable> list1 = logTableModel.showData(lowerLimit, noOfRowsToDisplay,searchjunction,searchside,searchdate);
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
          request.setAttribute("manname", searchjunction);
          request.setAttribute("pname", searchside);
            request.setAttribute("dname", searchdate);
          request.setAttribute("searchjunction", searchjunction);
          request.setAttribute("searchside", searchside);
            request.setAttribute("searchdate", searchdate);
          
        request.setAttribute("log_table", list1);
        request.setAttribute("message", logTableModel.getMessage());
        request.setAttribute("msgBgColor", logTableModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.getRequestDispatcher("/log_table_view").forward(request, response);
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
