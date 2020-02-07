/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.CameraModel;
import com.ts.dataEntry.Model.PositionModel;
import com.ts.dataEntry.tableClasses.Camera;
import com.ts.dataEntry.tableClasses.Position;
import com.ts.util.xyz;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author DELL
 */
public class CameraController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        CameraModel cameraModel = new CameraModel();
        cameraModel.setDriverClass(ctx.getInitParameter("driverClass"));
        cameraModel.setConnectionString(ctx.getInitParameter("connectionString"));
        cameraModel.setDb_userName(ctx.getInitParameter("db_userName"));
        cameraModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        cameraModel.setConnection();
        Map<String, String> map = new HashMap<String, String>();
       String task = request.getParameter("task");

       if (task == null) {
           task = "";
       }
        String junctionId = request.getParameter("junction_id1");
        String side_no1 = request.getParameter("side_no_details");
        int junction_id = junctionId != null ? Integer.parseInt(junctionId) : 0;
        String junctionName = cameraModel.getJunctionName(junction_id);

        try {
            String JQstring = request.getParameter("action1");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if (JQstring.equals("getCameraMake")) {
                    list = cameraModel.getCameraMake();
                } else if (JQstring.equals("getCameraType")) {
                    list = cameraModel.getCameraType();
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                cameraModel.closeConnection();
                out.flush();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --CameraController get JQuery Parameters Part-" + e);
        }
      
         
        int side_no2 = side_no1 != null ? Integer.parseInt(side_no1) : 0;
        if (task.equals("Delete")) {
            cameraModel.deleteRecord(Integer.parseInt(request.getParameter("camera_id")));  // Pretty sure that state_id will be available.
        } else if (task.equals("Save")) {
            //////////////////

            //////////////
            int camera_id = 0;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                camera_id = Integer.parseInt(request.getParameter("camera_id"));
            } catch (Exception e) {
                camera_id = 0;
            }
            junction_id = Integer.parseInt(request.getParameter("junction_id"));
            junctionName = request.getParameter("junction_name");
            side_no2 = Integer.parseInt(request.getParameter("side_no"));
            Camera camera = new Camera();
            camera.setCamera_id(camera_id);
            camera.setCamera_ip(request.getParameter("camera_ip"));
            camera.setCamera_make(request.getParameter("camera_make"));
            camera.setCamera_ip(request.getParameter("camera_ip"));
            camera.setCamera_type(request.getParameter("camera_type"));
            camera.setJunction_id(junction_id);
            camera.setJunction_name(junctionName);
            camera.setSide_no(side_no2);
            camera.setRemark(request.getParameter("remark"));
         

//            String folder = "ab";
//            for (int i = 1; i < no_of_col; i++) {
//                image_folder = destination_path + "//" + folder;
//                camera.setImage_folder(image_folder);
//                camera.setImage_name(image_name);
//            }
            if (camera_id == 0) {
                // if state_id was not provided, that means insert new record.
                cameraModel.insertRecord(camera);
            } else {
                // update existing record.
                cameraModel.updateRecord(camera);
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
        noOfRowsInTable = cameraModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
        List<Camera> cameraList = cameraModel.showData(lowerLimit, noOfRowsToDisplay, junction_id, side_no2);
        lowerLimit = lowerLimit + cameraList.size();
        noOfRowsTraversed = cameraList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("cameraList", cameraList);
        request.setAttribute("junction_id", junction_id);
        request.setAttribute("junction_name", junctionName);
        request.setAttribute("side_no", side_no2);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", cameraModel.getMessage());
        request.setAttribute("msgBgColor", cameraModel.getMsgBgColor());
        request.getRequestDispatcher("camera_view").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}
