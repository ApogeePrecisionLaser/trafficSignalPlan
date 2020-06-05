/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.PlanModeModel;
import com.ts.dataEntry.tableClasses.PlanMode;
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
 * @author Shruti
 */
public class PlanModeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        
        PlanModeModel planModeModel = new PlanModeModel();
        planModeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        planModeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        planModeModel.setDb_userName(ctx.getInitParameter("db_userName"));
        planModeModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        planModeModel.setConnection();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
          
           String searchmod=request.getParameter("searchstate");
         
        if (searchmod == null) {
            searchmod = "";
        }
         
       
        
         String JQstring = request.getParameter("action1");
         
          if (JQstring != null) {
                PrintWriter out = response.getWriter();
            List<String> list = null;
        if (JQstring.equals("getState")) {
                   list = planModeModel.getState();
                }
         
         Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                
                return;
          }
         if (task.equals("SearchAllRecords")) {
         searchmod="";
        
        }
         
         
            String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
                 List listAll = null;
                String searchmod1=request.getParameter("searchstate");
               
                if(searchmod1==null){
                    searchmod1="";
                 }
                 
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=planModeModel.showDataReport(searchmod1);
                jrxmlFilePath = ctx.getRealPath("/Report/planmode.jrxml");
                byte[] reportInbytes = planModeModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
               
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
               String searchstate1=request.getParameter("searchstate");
               
                if(searchstate1==null){
                    searchstate1="";
                 }
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/planmode.jrxml");
                listAll=planModeModel.showDataReport(searchstate1);
                ByteArrayOutputStream reportInbytes = planModeModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            planModeModel.deleteRecord(Integer.parseInt(request.getParameter("plan_mode_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            int plan_mode_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                plan_mode_id = Integer.parseInt(request.getParameter("plan_mode_id"));
            } catch (Exception e) {
                plan_mode_id = 0;
            }
            PlanMode planMode = new PlanMode();
            planMode.setPlan_mode_id(plan_mode_id);
            planMode.setPlan_mode_name(request.getParameter("plan_mode_name").trim());
            if (plan_mode_id == 0) {
                // if state_id was not provided, that means insert new record.
                planModeModel.insertRecord(planMode);
            } else {
                // update existing record.
                planModeModel.updateRecord(planMode);
            }
        }

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
        noOfRowsInTable = planModeModel.getNoOfRows(searchmod);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchmod = request.getParameter("manname");
    noOfRowsInTable = planModeModel.getNoOfRows(searchmod);
        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
              searchmod = request.getParameter("manname");
    noOfRowsInTable = planModeModel.getNoOfRows(searchmod);
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
              searchmod = request.getParameter("manname");
   // noOfRowsInTable = planModeModel.getNoOfRows(searchmod);
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
              searchmod = request.getParameter("manname");
    noOfRowsInTable = planModeModel.getNoOfRows(searchmod);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<PlanMode> planModeList = planModeModel.showData(lowerLimit, noOfRowsToDisplay,searchmod);
        lowerLimit = lowerLimit + planModeList.size();
        noOfRowsTraversed = planModeList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("planModeList", planModeList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         request.setAttribute("manname", searchmod);
         
        request.setAttribute("searchstate", searchmod);
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", planModeModel.getMessage());
        request.setAttribute("msgBgColor", planModeModel.getMsgBgColor());
        request.getRequestDispatcher("plan_mode_view").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

