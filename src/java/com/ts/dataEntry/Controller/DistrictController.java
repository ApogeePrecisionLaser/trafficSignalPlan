/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.DistrictModel;
import com.ts.dataEntry.tableClasses.District;
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
public class DistrictController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay =  5, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        DistrictModel districtModel = new DistrictModel();
        districtModel.setDriverClass(ctx.getInitParameter("driverClass"));
        districtModel.setConnectionString(ctx.getInitParameter("connectionString"));
        districtModel.setDb_userName(ctx.getInitParameter("db_userName"));
        districtModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        districtModel.setConnection();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        
        
           String searchstate=request.getParameter("searchstate");
         String searchdistrict=request.getParameter("searchdistrict");
        if (searchstate == null) {
            searchstate = "";
        }
         
       
        if (searchdistrict == null) {
            searchdistrict = "";
        }
         String JQstring = request.getParameter("action1");
         
          if (JQstring != null) {
                PrintWriter out = response.getWriter();
            List<String> list = null;
        if (JQstring.equals("getState")) {
                   list = districtModel.getState();
                }
         if (JQstring.equals("getDistrict")) {
              String st=request.getParameter("action2");
                    list = districtModel.getDistrict(st);
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
         searchdistrict="";
        }
         
         
            String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
                 List listAll = null;
                String searchstate1=request.getParameter("searchstate");
                String searchdistrict1=request.getParameter("searchdistrict");
                if(searchstate1==null){
                    searchstate1="";
                 }
                  if(searchdistrict1==null){
                    searchdistrict1="";
                 }
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               listAll=districtModel.showDataReport(searchstate1,searchdistrict1);
                jrxmlFilePath = ctx.getRealPath("/Report/district.jrxml");
                byte[] reportInbytes = districtModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
               
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                 String searchstate1=request.getParameter("searchstate");
                String searchdistrict1=request.getParameter("searchdistrict");
                if(searchstate1==null){
                    searchstate1="";
                 }
                  if(searchdistrict1==null){
                    searchdistrict1="";
                 }
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/district.jrxml");
                listAll=districtModel.showDataReport(searchstate1,searchdistrict1);
                ByteArrayOutputStream reportInbytes = districtModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            // Pretty sure that district_id will be available.
            districtModel.deleteRecord(Integer.parseInt(request.getParameter("district_id")));
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int district_id;
            try {
                // district_id may or may NOT be available i.e. it can be update or new record.
                district_id = Integer.parseInt(request.getParameter("district_id"));
            } catch (Exception e) {
                district_id = 0;
            }
            if (task.equals("Save AS New")) {
                district_id = 0;
            }
            District district = new District();
            district.setDistrict_id(district_id);
            district.setDistrict_name(request.getParameter("district_name").trim());
            district.setState_id(districtModel.getStateId(request.getParameter("state_name").trim()));
            if (district_id == 0) {
                // if district_id was not provided, that means insert new record.
                districtModel.insertRecord(district);
            } else {
                // update existing record.
                districtModel.updateRecord(district);
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
        noOfRowsInTable = districtModel.getNoOfRows(searchstate,searchdistrict);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchstate = request.getParameter("manname");
            searchdistrict = request.getParameter("pname");
              
         noOfRowsInTable = districtModel.getNoOfRows(searchstate,searchdistrict);      
        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
             searchstate = request.getParameter("manname");
            searchdistrict = request.getParameter("pname");
              
         noOfRowsInTable = districtModel.getNoOfRows(searchstate,searchdistrict);   
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
             searchstate = request.getParameter("manname");
            searchdistrict = request.getParameter("pname");
            
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
             searchstate = request.getParameter("manname");
            searchdistrict = request.getParameter("pname");
              
         noOfRowsInTable = districtModel.getNoOfRows(searchstate,searchdistrict);   
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<District> districtList = districtModel.showData(lowerLimit, noOfRowsToDisplay,searchstate,searchdistrict);
        lowerLimit = lowerLimit + districtList.size();
        noOfRowsTraversed = districtList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("districtList", districtList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("manname", searchstate);
          request.setAttribute("pname", searchdistrict);
        request.setAttribute("searchstate", searchstate);
        request.setAttribute("searchdistrict", searchdistrict);
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", districtModel.getMessage());
        request.setAttribute("msgBgColor", districtModel.getMsgBgColor());
        request.getRequestDispatcher("district_view").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
