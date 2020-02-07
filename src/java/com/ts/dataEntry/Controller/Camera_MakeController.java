/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.Camera_MakeModel;
import com.ts.dataEntry.Model.PositionModel;
import com.ts.dataEntry.tableClasses.Camera_Make;
import com.ts.dataEntry.tableClasses.Position;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Camera_MakeController extends HttpServlet{
    
  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        
        Camera_MakeModel camera_makeModel = new Camera_MakeModel();
        camera_makeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        camera_makeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        camera_makeModel.setDb_userName(ctx.getInitParameter("db_userName"));
       camera_makeModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
      camera_makeModel.setConnection();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("Delete")) {
            camera_makeModel.deleteRecord(Integer.parseInt(request.getParameter("camera_make_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            int camera_make_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                camera_make_id = Integer.parseInt(request.getParameter("camera_make_id"));
            } catch (Exception e) {
                camera_make_id = 0;
            }
            Camera_Make camera_make = new Camera_Make();
            camera_make.setCamera_make_id(camera_make_id);
            camera_make.setCamera_make(request.getParameter("camera_make"));
             camera_make.setRemark(request.getParameter("remark"));
            if (camera_make_id == 0) {
                // if state_id was not provided, that means insert new record.
                camera_makeModel.insertRecord(camera_make);
            } else {
                // update existing record.
                camera_makeModel.updateRecord(camera_make);
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
        noOfRowsInTable = camera_makeModel.getNoOfRows();                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<Camera_Make> camera_makeList = camera_makeModel.showData(lowerLimit, noOfRowsToDisplay);
        lowerLimit = lowerLimit + camera_makeList.size();
        noOfRowsTraversed = camera_makeList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("camera_makeList", camera_makeList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", camera_makeModel.getMessage());
        request.setAttribute("msgBgColor", camera_makeModel.getMsgBgColor());
        request.getRequestDispatcher("camera_make_view").forward(request, response);
    }


  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    
}



