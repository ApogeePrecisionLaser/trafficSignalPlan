/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.Pole_TypeModel;
import com.ts.dataEntry.Model.PositionModel;
import com.ts.dataEntry.tableClasses.Pole_Type;

import com.ts.dataEntry.tableClasses.Position;
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
public class Pole_TypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        
        Pole_TypeModel pole_typeModel = new Pole_TypeModel();
        pole_typeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        pole_typeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        pole_typeModel.setDb_userName(ctx.getInitParameter("db_userName"));
        pole_typeModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
      pole_typeModel.setConnection();
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
                   list = pole_typeModel.getState();
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
         
         
            String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
                 List listAll = null;
                String searchstate1=request.getParameter("searchstate");
               
                if(searchstate1==null){
                    searchstate1="";
                 }
                 
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=pole_typeModel.showDataReport(searchstate1);
                jrxmlFilePath = ctx.getRealPath("/Report/poletype.jrxml");
                byte[] reportInbytes = pole_typeModel.generateSiteList(jrxmlFilePath,listAll);
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
                jrxmlFilePath = ctx.getRealPath("Report/poletype.jrxml");
                listAll=pole_typeModel.showDataReport(searchstate1);
                ByteArrayOutputStream reportInbytes = pole_typeModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            pole_typeModel.deleteRecord(Integer.parseInt(request.getParameter("pole_type_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            int pole_type_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                pole_type_id = Integer.parseInt(request.getParameter("pole_type_id"));
            } catch (Exception e) {
                pole_type_id = 0;
            }
            Pole_Type pole_type = new Pole_Type();
            pole_type.setPole_type_id(pole_type_id);
            pole_type.setPole_type(request.getParameter("pole_type"));
            if (pole_type_id == 0) {
                // if state_id was not provided, that means insert new record.
                pole_typeModel.insertRecord(pole_type);
            } else {
                // update existing record.
                pole_typeModel.updateRecord(pole_type);
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
        noOfRowsInTable = pole_typeModel.getNoOfRows(searchstate);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchstate= request.getParameter("manname");
    noOfRowsInTable = pole_typeModel.getNoOfRows(searchstate);   
        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
             searchstate= request.getParameter("manname");
    noOfRowsInTable = pole_typeModel.getNoOfRows(searchstate);  
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchstate= request.getParameter("manname");
    //noOfRowsInTable = pole_typeModel.getNoOfRows(searchstate);  
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchstate= request.getParameter("manname");
    noOfRowsInTable = pole_typeModel.getNoOfRows(searchstate);  
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<Pole_Type> pole_typeList = pole_typeModel.showData(lowerLimit, noOfRowsToDisplay,searchstate);
        lowerLimit = lowerLimit + pole_typeList.size();
        noOfRowsTraversed = pole_typeList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("pole_typeList", pole_typeList);

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
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", pole_typeModel.getMessage());
        request.setAttribute("msgBgColor", pole_typeModel.getMsgBgColor());
        request.getRequestDispatcher("pole_type_view").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}


