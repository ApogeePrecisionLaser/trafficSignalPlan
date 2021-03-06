/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import com.ts.junction.Model.DateDetailModel;
import com.ts.junction.tableClasses.DateDetail;
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
public class DateDetailsController extends HttpServlet {

    @Override

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        DateDetailModel dateDetailModel = new DateDetailModel();
        dateDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        dateDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        dateDetailModel.setDb_userName(ctx.getInitParameter("db_userName"));
        dateDetailModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        dateDetailModel.setConnection();
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
                   list = dateDetailModel.getState();
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
               listAll=dateDetailModel.showDataReport(searchstate1);
                jrxmlFilePath = ctx.getRealPath("/Report/date.jrxml");
                byte[] reportInbytes = dateDetailModel.generateSiteList(jrxmlFilePath,listAll);
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
                jrxmlFilePath = ctx.getRealPath("Report/date.jrxml");
                listAll=dateDetailModel.showDataReport(searchstate1);
                ByteArrayOutputStream reportInbytes = dateDetailModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            // Pretty sure that city_id will be available.
            dateDetailModel.deleteRecord(Integer.parseInt(request.getParameter("date_detail_id")));
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int date_detail_id;
            try {
                // date_detail_id may or may NOT be available i.e. it can be update or new record.
                date_detail_id = Integer.parseInt(request.getParameter("date_detail_id"));
            } catch (Exception e) {
                date_detail_id = 0;
            }
            if (task.equals("Save AS New")) {
                date_detail_id = 0;
            }
            DateDetail dateDetail = new DateDetail();
            dateDetail.setDate_detail_id(date_detail_id);
            dateDetail.setName(request.getParameter("name").trim());
            dateDetail.setFrom_date(request.getParameter("from_date").trim());
            dateDetail.setTo_date(request.getParameter("to_date").trim());
            dateDetail.setRemark(request.getParameter("remark").trim());

            if (date_detail_id == 0) {
                // if city_id was not provided, that means insert new record.
                dateDetailModel.insertRecord(dateDetail);
            } else {
                // update existing record.
                dateDetailModel.updateRecord(dateDetail);
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
        noOfRowsInTable = dateDetailModel.getNoOfRows(searchstate);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchstate= request.getParameter("manname");
    noOfRowsInTable = dateDetailModel.getNoOfRows(searchstate);   
        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
             searchstate= request.getParameter("manname");
    noOfRowsInTable = dateDetailModel.getNoOfRows(searchstate);  
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchstate= request.getParameter("manname");
    //noOfRowsInTable = dateDetailModel.getNoOfRows(searchstate);  
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchstate= request.getParameter("manname");
    noOfRowsInTable = dateDetailModel.getNoOfRows(searchstate);  
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<DateDetail> dateDetailList = dateDetailModel.showData(lowerLimit, noOfRowsToDisplay,searchstate);
        lowerLimit = lowerLimit + dateDetailList.size();
        noOfRowsTraversed = dateDetailList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("dateDetailList", dateDetailList);

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
        request.setAttribute("message", dateDetailModel.getMessage());
        request.setAttribute("msgBgColor", dateDetailModel.getMsgBgColor());
        request.getRequestDispatcher("/date_detail").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
