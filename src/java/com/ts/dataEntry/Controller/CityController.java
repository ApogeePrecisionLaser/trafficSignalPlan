/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.CityModel;
import com.ts.dataEntry.tableClasses.City;
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
public class CityController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        CityModel cityModel = new CityModel();
        cityModel.setDriverClass(ctx.getInitParameter("driverClass"));
        cityModel.setConnectionString(ctx.getInitParameter("connectionString"));
        cityModel.setDb_userName(ctx.getInitParameter("db_userName"));
        cityModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        cityModel.setConnection();
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
                    list = cityModel.getState();
                }
         if (JQstring.equals("getDistrict")) {
              String st=request.getParameter("action2");
                    list = cityModel.getDistrict(st);
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
               listAll=cityModel.showDataReport(searchstate,searchdistrict);
                jrxmlFilePath = ctx.getRealPath("/Report/city.jrxml");
                byte[] reportInbytes = cityModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
               
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                String searchCammodel1=request.getParameter("searchCammodel");
                String searchCammake1=request.getParameter("searchCammake");
                 if(searchCammodel1==null){
                    searchCammodel1="";
                 }
                  if(searchCammake1==null){
                    searchCammake1="";
                 }
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/city.jrxml");
                listAll=cityModel.showDataReport(searchstate,searchdistrict);
                ByteArrayOutputStream reportInbytes = cityModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            // Pretty sure that city_id will be available.
            cityModel.deleteRecord(Integer.parseInt(request.getParameter("city_id")));
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int city_id;
            try {
                // city_id may or may NOT be available i.e. it can be update or new record.
                city_id = Integer.parseInt(request.getParameter("city_id"));
            } catch (Exception e) {
                city_id = 0;
            }
            if (task.equals("Save AS New")) {
                city_id = 0;
            }
            City city = new City();
            city.setCity_id(city_id);
            city.setCity_name(request.getParameter("city_name").trim());
            city.setDistrict_id(cityModel.getDistrictId(request.getParameter("district_name").trim()));
            city.setState_id(cityModel.getStateId(request.getParameter("state_name").trim()));
            city.setPin_code(Integer.parseInt(request.getParameter("pin_code").trim()));
            city.setStd_code(request.getParameter("std_code").trim());
            if (city_id == 0) {
                // if city_id was not provided, that means insert new record.
                cityModel.insertRecord(city);
            } else {
                // update existing record.
                cityModel.updateRecord(city);
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
        noOfRowsInTable = cityModel.getNoOfRows(searchstate,searchdistrict);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchstate = request.getParameter("manname");
            searchdistrict = request.getParameter("pname");
              
           noOfRowsInTable = cityModel.getNoOfRows(searchstate,searchdistrict);
        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
             searchstate = request.getParameter("manname");
            searchdistrict = request.getParameter("pname");
              
           noOfRowsInTable = cityModel.getNoOfRows(searchstate,searchdistrict);
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
              
           noOfRowsInTable = cityModel.getNoOfRows(searchstate,searchdistrict);
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<City> cityList = cityModel.showData(lowerLimit, noOfRowsToDisplay,searchstate,searchdistrict);
        lowerLimit = lowerLimit + cityList.size();
        noOfRowsTraversed = cityList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("cityList", cityList);

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
        request.setAttribute("message", cityModel.getMessage());
        request.setAttribute("msgBgColor", cityModel.getMsgBgColor());
        request.getRequestDispatcher("city_view").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

