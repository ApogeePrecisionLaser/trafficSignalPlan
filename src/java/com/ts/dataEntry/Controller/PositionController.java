/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.PositionModel;
import com.ts.dataEntry.Model.StateModel;
import com.ts.dataEntry.tableClasses.Position;
import com.ts.dataEntry.tableClasses.State;
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
public class PositionController extends HttpServlet {

   @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        
        PositionModel positionModel = new PositionModel();
        positionModel.setDriverClass(ctx.getInitParameter("driverClass"));
        positionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        positionModel.setDb_userName(ctx.getInitParameter("db_userName"));
        positionModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        positionModel.setConnection();
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("Delete")) {
            positionModel.deleteRecord(Integer.parseInt(request.getParameter("position_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            int position_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                position_id = Integer.parseInt(request.getParameter("position_id"));
            } catch (Exception e) {
                position_id = 0;
            }
            Position position = new Position();
            position.setPosition_id(position_id);
            position.setPosition(request.getParameter("position").trim());
            if (position_id == 0) {
                // if state_id was not provided, that means insert new record.
               positionModel.insertRecord(position);
            } else {
                // update existing record.
                positionModel.updateRecord(position);
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
        noOfRowsInTable = positionModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
        List<Position> positionList = positionModel.showData(lowerLimit, noOfRowsToDisplay);
        lowerLimit = lowerLimit + positionList.size();
        noOfRowsTraversed = positionList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("positionList", positionList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", positionModel.getMessage());
        request.setAttribute("msgBgColor", positionModel.getMsgBgColor());
        request.getRequestDispatcher("position_view").forward(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}


