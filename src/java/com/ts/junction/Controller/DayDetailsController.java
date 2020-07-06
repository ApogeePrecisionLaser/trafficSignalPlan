/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import com.ts.junction.Model.DayDetailModel;
import com.ts.junction.tableClasses.DayDetail;
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
public class DayDetailsController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        DayDetailModel dayDetailModel = new DayDetailModel();
         dayDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
         dayDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
         dayDetailModel.setDb_userName(ctx.getInitParameter("db_userName"));
         dayDetailModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
         dayDetailModel.setConnection();
         int junction_id=0;
         int day_detail_id;
      
         String junction_name = request.getParameter("junction_name");
         
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        
          String searchstate=request.getParameter("searchstate");
          String searchday=request.getParameter("searchday");
         
        if (searchstate == null) {
            searchstate = "";
        }
        if (searchday == null) {
            searchday = "";
        }
         
  try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if(JQstring.equals("getJunctionName")) {
                    list = dayDetailModel.getSearchJunctionName(q);
                }
               if (JQstring.equals("getState")) {
                   list = dayDetailModel.getState();
                }
               if (JQstring.equals("getDay")) {
                   list = dayDetailModel.getDay();
                }


                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                dayDetailModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
  
   if (task.equals("SearchAllRecords")) {
         searchstate="";
           searchday = "";
        }
         
         
            String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
                 List listAll = null;
                String searchstate1=request.getParameter("searchstate");
                String searchday1=request.getParameter("searchday");
               
                if(searchstate1==null){
                    searchstate1="";
                 }
                if(searchday1==null){
                    searchday1="";
                 }
                 
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=dayDetailModel.showDataReport(searchstate1,searchday1);
                jrxmlFilePath = ctx.getRealPath("/Report/day.jrxml");
                byte[] reportInbytes = dayDetailModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
               
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                String searchstate1=request.getParameter("searchstate");
                String searchday1=request.getParameter("searchday");
               
                if(searchstate1==null){
                    searchstate1="";
                 }
                if(searchday1==null){
                    searchday1="";
                 }
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/day.jrxml");
                listAll=dayDetailModel.showDataReport(searchstate1,searchday1);
                ByteArrayOutputStream reportInbytes = dayDetailModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            // Pretty sure that id will be available.
            try{
              day_detail_id=Integer.parseInt(request.getParameter("day_detail_id"));
             dayDetailModel.deleteRecord(day_detail_id);
             
            }catch(Exception e)
            {
                System.out.println("Exception "+e);
            }
        } else if (task.equals("Save") || task.equals("Save AS New")) {
         
            try {
                // date_detail_id may or may NOT be available i.e. it can be update or new record.
                day_detail_id = Integer.parseInt(request.getParameter("day_detail_id"));
            } catch (Exception e) {
                day_detail_id = 0;
            }
            if (task.equals("Save AS New")) {
                day_detail_id = 0;
            }
            junction_id = dayDetailModel.getJunctionID(junction_name);
            DayDetail dayDetail = new DayDetail();
            dayDetail.setDay_detail_id(day_detail_id);
            dayDetail.setDay_name(request.getParameter("day_name").trim());
            dayDetail.setDay(request.getParameter("day").trim());
            //dayDetail.setJunction_id(junction_id);
            // dayDetail.setJunction_name(request.getParameter("junction_name").trim());
            
           dayDetail.setRemark(request.getParameter("remark").trim());

            if (day_detail_id == 0) {
                // if day_detail_id was not provided, that means insert new record.
                dayDetailModel.insertRecord(dayDetail);
            } else {
                // update existing record.
                dayDetailModel.updateRecord(dayDetail);
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
        noOfRowsInTable = dayDetailModel.getNoOfRows(searchstate,searchday);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchstate= request.getParameter("manname");
               searchday= request.getParameter("pname");
     noOfRowsInTable = dayDetailModel.getNoOfRows(searchstate,searchday);           
        }  // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
             searchstate= request.getParameter("manname");
                searchday= request.getParameter("pname");
     noOfRowsInTable = dayDetailModel.getNoOfRows(searchstate,searchday);       
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchstate= request.getParameter("manname");
             searchday= request.getParameter("pname");
    //noOfRowsInTable = dayDetailModel.getNoOfRows(searchstate); 
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchstate= request.getParameter("manname");
                searchday= request.getParameter("pname");
     noOfRowsInTable = dayDetailModel.getNoOfRows(searchstate,searchday);       
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<DayDetail> dayDetailList = dayDetailModel.showData(lowerLimit, noOfRowsToDisplay,searchstate,searchday);
        lowerLimit = lowerLimit + dayDetailList.size();
        noOfRowsTraversed = dayDetailList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("dayDetailList", dayDetailList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
                request.setAttribute("searchstate", searchstate);
                request.setAttribute("searchday", searchday);
                request.setAttribute("manname", searchstate);
                request.setAttribute("pname", searchday);
              
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", dayDetailModel.getMessage());
        request.setAttribute("msgBgColor", dayDetailModel.getMsgBgColor());
         request.setAttribute("junction_name", request.getParameter("junction_name"));
          request.getRequestDispatcher("/day_details").forward(request, response);
        }
    
   public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
