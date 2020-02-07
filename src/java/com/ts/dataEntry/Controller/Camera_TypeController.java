/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.Camera_TypeModel;
import com.ts.dataEntry.Model.PositionModel;
import com.ts.dataEntry.tableClasses.Camera_Type;

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
public class Camera_TypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        
        Camera_TypeModel camera_typeModel = new Camera_TypeModel();
        camera_typeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        camera_typeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        camera_typeModel.setDb_userName(ctx.getInitParameter("db_userName"));
       camera_typeModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
      camera_typeModel.setConnection();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("Delete")) {
            camera_typeModel.deleteRecord(Integer.parseInt(request.getParameter("camera_type_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            int camera_type_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                camera_type_id = Integer.parseInt(request.getParameter("camera_type_id"));
            } catch (Exception e) {
                camera_type_id = 0;
            }
            Camera_Type camera_type = new Camera_Type();
            camera_type.setCamera_type_id(camera_type_id);
            camera_type.setCamera_type(request.getParameter("camera_type"));
             camera_type.setRemark(request.getParameter("remark"));
            if (camera_type_id == 0) {
                // if state_id was not provided, that means insert new record.
                camera_typeModel.insertRecord(camera_type);
            } else {
                // update existing record.
                camera_typeModel.updateRecord(camera_type);
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
        noOfRowsInTable = camera_typeModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
        List<Camera_Type> camera_typeList = camera_typeModel.showData(lowerLimit, noOfRowsToDisplay);
        lowerLimit = lowerLimit + camera_typeList.size();
        noOfRowsTraversed = camera_typeList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("camera_typeList", camera_typeList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", camera_typeModel.getMessage());
        request.setAttribute("msgBgColor", camera_typeModel.getMsgBgColor());
        request.getRequestDispatcher("camera_type_view").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}


