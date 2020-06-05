/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.StateModel;
import com.ts.dataEntry.tableClasses.State;
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
public class StateController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        
        StateModel stateModel = new StateModel();
        stateModel.setDriverClass(ctx.getInitParameter("driverClass"));
        stateModel.setConnectionString(ctx.getInitParameter("connectionString"));
        stateModel.setDb_userName(ctx.getInitParameter("db_userName"));
        stateModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        stateModel.setConnection();
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
                   list = stateModel.getState();
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
               listAll=stateModel.showDataReport(searchstate1);
                jrxmlFilePath = ctx.getRealPath("/Report/state.jrxml");
                byte[] reportInbytes = stateModel.generateSiteList(jrxmlFilePath,listAll);
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
                jrxmlFilePath = ctx.getRealPath("Report/state.jrxml");
                listAll=stateModel.showDataReport(searchstate1);
                ByteArrayOutputStream reportInbytes = stateModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {
            stateModel.deleteRecord(Integer.parseInt(request.getParameter("state_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            int state_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                state_id = Integer.parseInt(request.getParameter("state_id"));
            } catch (Exception e) {
                state_id = 0;
            }
            State state = new State();
            state.setState_id(state_id);
            state.setState_name(request.getParameter("state_name").trim());
            if (state_id == 0) {
                // if state_id was not provided, that means insert new record.
                stateModel.insertRecord(state);
            } else {
                // update existing record.
                stateModel.updateRecord(state);
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
        noOfRowsInTable = stateModel.getNoOfRows(searchstate);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchstate = request.getParameter("manname");
  noOfRowsInTable = stateModel.getNoOfRows(searchstate);  
        }  // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
              searchstate = request.getParameter("manname");
  noOfRowsInTable = stateModel.getNoOfRows(searchstate);  
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
              searchstate = request.getParameter("manname");
  // = stateModel.getNoOfRows(searchstate);  
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
              searchstate = request.getParameter("manname");
  noOfRowsInTable = stateModel.getNoOfRows(searchstate);  
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<State> stateList = stateModel.showData(lowerLimit, noOfRowsToDisplay,searchstate);
        lowerLimit = lowerLimit + stateList.size();
        noOfRowsTraversed = stateList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("stateList", stateList);

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
        request.setAttribute("message", stateModel.getMessage());
        request.setAttribute("msgBgColor", stateModel.getMsgBgColor());
        request.getRequestDispatcher("state_view").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}

