/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.log.Controller;

import com.ts.junction.tableClasses.Junction;
import com.ts.log.Model.SeverityCaseModel;
import com.ts.log.tableClasses.SeverityCase;
import com.ts.log.tableClasses.SeverityLevel;
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
public class SeverityCaseController extends HttpServlet {

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
        SeverityCaseModel severityCaseModel = new SeverityCaseModel();
        ServletContext ctx = getServletContext();
        severityCaseModel.setDriverClass(ctx.getInitParameter("driverClass"));
        severityCaseModel.setConnectionString(ctx.getInitParameter("connectionString"));
        severityCaseModel.setDb_userName(ctx.getInitParameter("db_userName"));
        severityCaseModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        severityCaseModel.setConnection();
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 4, noOfRowsInTable;
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        
               String searchstate=request.getParameter("searchstate");
               String searchcase=request.getParameter("searchcase");
         
         
        if (searchstate == null) {
            searchstate = "";
        }
        if (searchcase == null) {
            searchcase = "";
        }
         
        try {
            //----- This is only for Vendor key Person JQuery
            String jqstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (jqstring != null) {
                List<String> list = null;
                if (jqstring.equals("getLevel")) {
                    list = severityCaseModel.getLevel(q);
                } 
                 if (jqstring.equals("getState")) {
                   list = severityCaseModel.getState();
                }
                 if (jqstring.equals("getCase")) {
                   list = severityCaseModel.getCase();
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
         if (task.equals("SearchAllRecords")) {
         searchstate="";
        searchcase="";
        }
         
           String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
                 List listAll = null;
                String searchstate1=request.getParameter("searchstate");
               
                if(searchstate1==null){
                    searchstate1="";
                 }
                String searchcase1=request.getParameter("searchcase");
               
                if(searchcase1==null){
                    searchcase1="";
                 }
                 
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=severityCaseModel.showDataReport(searchstate1,searchcase1);
                jrxmlFilePath = ctx.getRealPath("/Report/seviritycase.jrxml");
                byte[] reportInbytes = severityCaseModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
               
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
               String searchstate1=request.getParameter("searchstate");
               String searchcase1=request.getParameter("searchcase");
               
                if(searchcase1==null){
                    searchcase1="";
                 }
                 
                if(searchstate1==null){
                    searchstate1="";
                 }
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/seviritycase.jrxml");
                listAll=severityCaseModel.showDataReport(searchstate1,searchcase1);
                ByteArrayOutputStream reportInbytes = severityCaseModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Save") || task.equals("Save AS New")) {
            SeverityCase severityCase = new SeverityCase();
            String severity_level = request.getParameter("severity_number");
            int id = 0;
            try {
                id =  Integer.parseInt(request.getParameter("severity_case_id"));
            } catch (Exception ex) {
                id = 0;
//                System.out.println("ad_asso_site_detail_id error: " + ex);
            }
            if (task.equals("Save AS New")) {
                id = 0;
            }
            //severityCase.setSeverity_level_id(Integer.parseInt(request.getParameter("severity_level_id")));
            severityCase.setSeverity_case(request.getParameter("case"));
            severityCase.setSeverity_case_id(id);
            severityCase.setSeverity_level(Integer.parseInt(severity_level));
            severityCase.setSent_data(request.getParameter("send_data"));
            severityCase.setReceived_data(request.getParameter("recieved_data"));
            severityCase.setRemark(request.getParameter("remark"));
            if (id == 0) {
               
                    severityCaseModel.insertRecord(severityCase);
                
            } else {
                // update existing record.
                severityCase.setSeverity_level_id(Integer.parseInt(request.getParameter("severity_level_id")));
                severityCase.setRevision_no(Integer.parseInt(request.getParameter("revision_no")));
                severityCaseModel.updateRecord(severityCase);
            }
        } 
        if(task.equals("Delete")) {
            int id =  Integer.parseInt(request.getParameter("severity_case_id"));
            severityCaseModel.deleteRecord(id);
        }
        
        
        noOfRowsInTable = severityCaseModel.getNoOfRows(searchstate,searchcase); // get the number of records (rows) in the table.

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
     noOfRowsInTable = severityCaseModel.getNoOfRows(searchstate,searchcase); // get the number of records (rows) in the table.

        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
             searchstate= request.getParameter("manname");
    noOfRowsInTable = severityCaseModel.getNoOfRows(searchstate,searchcase); // get the number of records (rows) in the table.

            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchstate= request.getParameter("manname");
    // noOfRowsInTable = severityLevelModel.getNoOfRows(searchstate);
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchstate= request.getParameter("manname");
     noOfRowsInTable = severityCaseModel.getNoOfRows(searchstate,searchcase); // get the number of records (rows) in the table.

            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        List<SeverityCase> list1 = severityCaseModel.showData(lowerLimit, noOfRowsToDisplay,searchstate,searchcase);
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
         request.setAttribute("pname", searchcase);
        request.setAttribute("searchstate", searchstate);
        request.setAttribute("searchcase", searchcase);
        request.setAttribute("severity_case", list1);
        request.setAttribute("message", severityCaseModel.getMessage());
        request.setAttribute("msgBgColor", severityCaseModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.getRequestDispatcher("/severity_case_view").forward(request, response);
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
