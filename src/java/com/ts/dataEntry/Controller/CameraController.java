 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.dataEntry.Controller;

import com.ts.dataEntry.Model.CameraModel;
import com.ts.dataEntry.tableClasses.Camera;
import com.ts.util.xyz;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        CameraModel cameraModel = new CameraModel();
        cameraModel.setDriverClass(ctx.getInitParameter("driverClass"));
        cameraModel.setConnectionString(ctx.getInitParameter("connectionString"));
        cameraModel.setDb_userName(ctx.getInitParameter("db_userName"));
        cameraModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        cameraModel.setConnection();
         response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        Map<String, String> map = new HashMap<String, String>();
       String task = request.getParameter("task");
String s=null;
       if (task == null) {
           task = "";
       }
       
       String searchCamIp = request.getParameter("searchCamIp");
          String searchcammake = request.getParameter("searchcammake");
           String searchjunction = request.getParameter("searchjunction");
          String searchcamType = request.getParameter("searchCamtype");
        if(searchCamIp==null){
        searchCamIp="";
        }
         if(searchcammake==null){
        searchcammake="";
        } 
          if(searchjunction==null){
        searchjunction="";
        }
         if(searchcamType==null){
        searchcamType="";
        } 
         
         if (task.equals("SearchAllRecords")) {
            searchCamIp = "";
            searchcammake="";
            searchjunction="";
            searchcamType="";
        }
        String junctionId = request.getParameter("junction_id1");
        String side_no1 = request.getParameter("side_no_details");
//        int camera_id;
//        camera_id = Integer.parseInt(request.getParameter("camera_id"));
        int junction_id = junctionId != null ? Integer.parseInt(junctionId) : 0;
//      String junctionName = cameraModel.getJunctionName(junction_id);
        String junctionName ="";
         if (task.equals("GetCordinates1")) {
            
            String longi1 = request.getParameter("longitude");
            String latti1 = request.getParameter("latitude");
            if (longi1 == null || longi1.equals("undefined")) {
                longi1 = "0";
            }
            if (latti1 == null || latti1.equals("undefined")) {
                latti1 = "0";
            }
            request.setAttribute("longi", longi1);
            request.setAttribute("latti", latti1);
         
            System.out.println(latti1 + "," + longi1);
            request.getRequestDispatcher("getCordinate1").forward(request, response);
            return;
        }
        
         
         
         
        try {
             String q = request.getParameter("q"); 
            String JQstring = request.getParameter("action1");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if (JQstring.equals("getCameraMake")) {
                    list = cameraModel.getCameraMake();
                }  if (JQstring.equals("getCameraType")) {
                    list = cameraModel.getCameraType();
                }
                if (JQstring.equals("getsearchJunctionName")) {
                    list = cameraModel.getsearchJunctionName(q);
                } if (JQstring.equals("getCameraModel")) {
                    String cam=request.getParameter("action2");
                    list = cameraModel.getCameraModel(cam);
                }
                  if (JQstring.equals("getsearchCamIp")) {
                    list = cameraModel.getsearchCamIp(q);
                }
                  if (JQstring.equals("getsearchCamMake")) {
                    list = cameraModel.getsearchCamMake(q);
                }
                   if (JQstring.equals("getsearchJun")) {
                    list = cameraModel.getsearchJun(q);
                }
                    if (JQstring.equals("getsearchCamType")) {
                    list = cameraModel.getsearchCamType(q);
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
      /////////////////////
      
              List image_name_list = new ArrayList();

            String destination_path = "";
            String imagePath = null;
            Map<String, String> map_image = new HashMap<String, String>();

            //junction1 = slaveinfoModel.getjunctionName(jId);
            List<String> imageNameList = new ArrayList<String>();

            boolean isCreated = true;
            List items = null;
            Iterator itr = null;
            Iterator itr1 = null;
            List<File> list2 = new ArrayList<File>();
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(8 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(new File(""));
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                String auto_no = "";
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field

                        map.put(item.getFieldName(), item.getString("UTF-8"));

                    }
                }

                itr1 = items.iterator();
                int count=0;
                while (itr1.hasNext()) {
                   
                    FileItem item = (FileItem) itr1.next();
                    if (!item.isFormField()) {
                         count++;
                        //System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {

                            String image_name = item.getName();

                       
                            if (!(image_name).equals(null)) {
                                imageNameList.add(image_name);// add all image name to list
                                image_name = (image_name).substring(0, (image_name).length());
                                int index = (image_name).indexOf('.');
                                System.out.println(index);
                                String ext = (image_name).substring(index, (image_name).length());
                                System.out.println(image_name);
                                map.put(item.getFieldName(), item.getName());
                               // destination_path = documentModel.getRepositoryPath();
                                // imagePath = destination_path + "/trafficSignalPlan/images/camera/" + junctionName + "/" + side_name1;

                                imagePath = "C:\\trafficSignalPlan\\images\\camera";
                                
                                int lid=cameraModel.getLastId();
                                lid=lid+1;
                                  //long l=   System.currentTimeMillis();
                                  Timestamp stamp = new Timestamp(System.currentTimeMillis());
                                 s= stamp.toString();
                                  // s = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                                      image_name="C_"+lid+"_"+s.replace(" ", "_").replace(":", "_").replace("-", "_")+"_"+count+".jpg";
                              //  image_name=map.get("junction_name").concat(map.get("camera_ip").concat("_"+count+".jpg"));
                                File file = new File(imagePath);
                                if (!image_name.isEmpty()) {
                                    file = new File(imagePath);
                                    if (!file.exists()) {
                                        isCreated = file.mkdirs();
                                    }
                                    item.write(new File(imagePath + "\\" + image_name));
                                    image_name_list.add(image_name);
                                    list2.add(new File(imagePath + "\\" + image_name));
                                }

                            }

                        }
                    }//else end
                }
               
                itr = null;
                itr = items.iterator();
            } catch (Exception e) {
                System.out.println("Error is :" + e);
            }
            
      
      
      
      
      /////////////////////////////
         task=map.get("task");
         if(task==null){
         task="";
         }
        int side_no2 = side_no1 != null ? Integer.parseInt(side_no1) : 0;
        if (task.equals("Delete")) {
            cameraModel.deleteRecord(Integer.parseInt(map.get("camera_id")));  // Pretty sure that camera_id will be available.
        } else if (task.equals("Save")) {
         
            //////////////////

            //////////////
         int camera_id;
            try {
                // state_id may or may NOT be available i.e. it can be update or new record.
                camera_id = Integer.parseInt(map.get("camera_id"));
            } catch (Exception e) {
                camera_id = 0;
            }
//           junction_id = Integer.parseInt(request.getParameter("junction_id"));
            junctionName = map.get("junction_name");
            side_no2 = Integer.parseInt(map.get("side_no"));
            Camera camera = new Camera();
            camera.setCamera_id(camera_id);
            camera.setCamera_ip(map.get("camera_ip"));
            camera.setCamera_make(map.get("camera_make"));
            camera.setCamera_model(map.get("camera_mod"));
            camera.setCamera_ip(map.get("camera_ip"));
            camera.setCamera_type(map.get("camera_type"));
            camera.setJunction_id(Integer.parseInt(map.get("junction_id")));
            camera.setJunction_name(map.get("junction_name"));
            camera.setSide_no(side_no2);
            camera.setRemark(map.get("remark"));
         camera.setLatitude(map.get("latitude"));
         camera.setLongitude(map.get("longitude"));
camera.setCamerafacing(map.get("camera_facing"));
camera.setLane_no(map.get("lane_no"));
camera.setCreated_at(s);
camera.setImage_folder(imagePath);

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
         String requester = request.getParameter("requester");
           if (requester != null && requester.equals("PRINT")) {
               
              String searchcamType1 = request.getParameter("scamtype");  
             String searchCamIp1 = request.getParameter("scamip");  
             String searchcammake1 = request.getParameter("scammake");  
              String searchjunction1 = request.getParameter("sjun");  
               if(searchcamType1==null){
                    searchcamType1="";
                 }
                if(searchCamIp1==null){
                    searchCamIp1="";
                 }
                 if(searchcammake1==null){
                    searchcammake1="";
                 }
                  if(searchjunction1==null){
                    searchjunction1="";
                 }
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               List  listAll=cameraModel.showDataReport(searchCamIp1,searchcammake1,searchcamType1,searchjunction1);
                jrxmlFilePath = ctx.getRealPath("/Report/camerapdf.jrxml");
                byte[] reportInbytes = cameraModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                cameraModel.closeConnection();
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                 String searchcamType1 = request.getParameter("scamtype");  
             String searchCamIp1 = request.getParameter("scamip");  
             String searchcammake1 = request.getParameter("scammake");  
              String searchjunction1 = request.getParameter("sjun"); 
              if(searchcamType1==null){
                    searchcamType1="";
                 }
                if(searchCamIp1==null){
                    searchCamIp1="";
                 }
                 if(searchcammake1==null){
                    searchcammake1="";
                 }
                  if(searchjunction1==null){
                    searchjunction1="";
                 }
              
                String jrxmlFilePath;
                List listAll = null;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=Orginisation_Report.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("Report/camerapdf.jrxml");
                   listAll=cameraModel.showDataReport(searchCamIp1,searchcammake1,searchcamType1,searchjunction1);
                ByteArrayOutputStream reportInbytes = cameraModel.generateOrginisationXlsRecordList(jrxmlFilePath, listAll);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        noOfRowsInTable = cameraModel.getNoOfRows(searchCamIp,searchcammake,searchcamType,searchjunction);                  // get the number of records (rows) in the table.
        if (buttonAction.equals("Next")){
            searchCamIp = request.getParameter("manname");
              searchcammake = request.getParameter("pname");
                 searchjunction = request.getParameter("Jname");
              searchcamType = request.getParameter("camType");
             noOfRowsInTable = cameraModel.getNoOfRows(searchCamIp,searchcammake,searchcamType,searchjunction); 

        } // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            searchCamIp = request.getParameter("manname");
              searchcammake = request.getParameter("pname");
                 searchjunction = request.getParameter("Jname");
              searchcamType = request.getParameter("camType");
            noOfRowsInTable = cameraModel.getNoOfRows(searchCamIp,searchcammake,searchcamType,searchjunction); 
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            searchCamIp = request.getParameter("manname");
              searchcammake = request.getParameter("pname");
            searchjunction = request.getParameter("Jname");
              searchcamType = request.getParameter("camType");
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            searchCamIp = request.getParameter("manname");
              searchcammake = request.getParameter("pname");
               searchjunction = request.getParameter("Jname");
              searchcamType = request.getParameter("camType");
          noOfRowsInTable = cameraModel.getNoOfRows(searchCamIp,searchcammake,searchcamType,searchjunction); 
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals("Save") || task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        // Logic to show data in the table.
        List<Camera> cameraList = cameraModel.showData(lowerLimit, noOfRowsToDisplay,searchCamIp,searchcammake,searchcamType,searchjunction);
//                List<Camera> cameraList = cameraModel.showData(lowerLimit, noOfRowsToDisplay, junction_id, side_no2);
//     System.out.println(camera_id);
        lowerLimit = lowerLimit + cameraList.size();
        noOfRowsTraversed = cameraList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("cameraList", cameraList);
        request.setAttribute("junction_id", junction_id);
//         request.setAttribute("camera_id", camera_id);
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
          request.setAttribute("manname", searchCamIp);
          request.setAttribute("pname", searchcammake);
           request.setAttribute("Jname", searchjunction);
          request.setAttribute("camType", searchcamType);
        request.setAttribute("searchCamIp", searchCamIp);
            request.setAttribute("searchJun", searchjunction);
        
        request.setAttribute("searchCamtype", searchcamType);
        request.setAttribute("searchcammake", searchcammake);
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("message", cameraModel.getMessage());
        request.setAttribute("msgBgColor", cameraModel.getMsgBgColor());
        request.getRequestDispatcher("camera_view").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}
