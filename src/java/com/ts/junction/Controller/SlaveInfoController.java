/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import com.ts.junction.Model.SlaveInfoModel;
import com.ts.junction.tableClasses.SlaveInfo;
import com.ts.util.xyz;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Shruti
 */
public class SlaveInfoController extends HttpServlet {

    static int jId;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SlaveInfoModel slaveinfoModel = new SlaveInfoModel();
        ServletContext ctx = getServletContext();
        slaveinfoModel.setDriverClass(ctx.getInitParameter("driverClass"));
        slaveinfoModel.setConnectionString(ctx.getInitParameter("connectionString"));
        slaveinfoModel.setDb_userName(ctx.getInitParameter("db_userName"));
        slaveinfoModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        slaveinfoModel.setConnection();
        Map<String, String> map = new HashMap<String, String>();

        int no_of_col = 1;
        String image_folder = "";
        String junctionName = "";
        String side1 = "";
        String task1 = request.getParameter("task");
        String task2 = request.getParameter("task2");
        if (task1 == null) {
            task1 = "";
        }

        List<File> list = new ArrayList<File>();
        if ("poleType".equals(task1)) {
            List<String> pole_type_list = slaveinfoModel.getPoleType();
            PrintWriter out = response.getWriter();
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("pole_type", "testing");
            } catch (JSONException ex) {
                System.out.println("SlaveInfoController poleType() Error: " + ex);
            }
            out.println(pole_type_list);
            out.flush();
        }

        if (task1.equals("getSideName")) {
            PrintWriter out1 = response.getWriter();
            int junction_id = Integer.parseInt(request.getParameter("junctionID"));
            jId = junction_id;
            int side_no = Integer.parseInt(request.getParameter("side_no"));
            List<String> side_name_list = slaveinfoModel.getSideName(junction_id, side_no);
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("side_name", side_name_list.get(0));
            } catch (JSONException ex) {
                System.out.println("SlaveInfoController poleType() Error: " + ex);
            }
            response.setContentType("application/json");
            out1.println(jsonObj.toString());
            return;

        }

        if ("position".equals(task1)) {
            List<String> position_list = slaveinfoModel.getPosition();
            PrintWriter out = response.getWriter();
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("position", position_list);
            } catch (JSONException ex) {
                System.out.println("SlaveInfoController position() Error: " + ex);
            }
            out.println(position_list);
            out.flush();
        }

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
            while (itr1.hasNext()) {
                FileItem item = (FileItem) itr1.next();
                if (!item.isFormField()) {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                    if (item.getName() == null || item.getName().isEmpty()) {
                        map.put(item.getFieldName(), "");
                    } else {

                        String image_name = item.getName();

                        junctionName = slaveinfoModel.getjunctionName(Integer.parseInt(map.get("junction_id")));

                        String side_seq = item.getFieldName().trim().substring(item.getFieldName().length() - 1);
                        int leng = item.getFieldName().length();

                        String side_name1 = "";
                        side_name1 = map.get("side_name" + side_seq);
//                    String  side_name1 = slaveinfoModel.getSideNameImage(map.get("junction_id"),side1);

                        if (!(image_name).equals(null)) {
                            imageNameList.add(image_name);// add all image name to list
                            image_name = (image_name).substring(0, (image_name).length());
                            int index = (image_name).indexOf('.');
                            System.out.println(index);
                            String ext = (image_name).substring(index, (image_name).length());
                            System.out.println(image_name);
                            map.put(item.getFieldName(), item.getName());
                            destination_path = slaveinfoModel.getRepositoryPath();
                            imagePath = destination_path + "/trafficSignalPlan/images/camera/" + junctionName + "/" + side_name1;

                            //imagePath = date[0] + "-" + date[1] + "-" + date[2].split("/")[0] + "_" + "tp_" + traffic_police_id + fileExt;
                            // imagePath = imagePath + date[2].split("\\")[0] + "\\" + date[1] + "\\" + date[0];
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
        String task = map.get("task");
        if (task == null) {
            task = "";
        }

        int imageid1 = 0;
        String side_name1 = "";
        try {
            if (task2 == null) {
                task2 = "";
            }
            if (task1.equals("View_Image")) {

                imageid1 = Integer.parseInt(request.getParameter("imageid"));
                side_name1 = request.getParameter("sideName");
                junctionName = slaveinfoModel.getjunctionName(Integer.parseInt(request.getParameter("junction_id")));
                List imageList = new ArrayList();
                String destinationPath = "";
//                       int general_image_details_id=Integer.parseInt(request.getParameter("general_image_details_id"));                       
                String destination = slaveinfoModel.getImageDestinationPath(imageid1);
//                         String task2=request.getParameter("task1");

                destination = destination + "/trafficSignalPlan/images/camera/" + junctionName + "/" + side_name1;

                File folder = new File(destination);
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
//                      
                        String name1 = listOfFiles[i].getName();
                        /////////////////////////////////////filterImageName//////////////////////////////////////////////                                                       
                        SlaveInfo fd = new SlaveInfo();
                        fd.setImage_name(name1);
//                                   fd.setTask(task2);
                        imageList.add(fd);
                    }
                }
                int image_list_length = imageList.size();
                request.setAttribute("imageList", imageList);
                request.setAttribute("imageid1", imageid1);
                request.setAttribute("side_name1", side_name1);
                request.setAttribute("junctionName", junctionName);
                request.setAttribute("image_list_lenght", image_list_length);
                request.getRequestDispatcher("multipleImage").forward(request, response);
                return;
            } else if (task2.equals("getImage") || task2.equals("getImageThumb")) {

//                //String image_name2 = image_name1[0];
                imageid1 = Integer.parseInt(request.getParameter("imageid1").trim());
                String image_name1 = request.getParameter("image_name").trim();
                String side_name2 = request.getParameter("side_name1").trim();
                String junctionName2 = request.getParameter("junctionName").trim();
                String destinationPath = slaveinfoModel.getImageDestinationPath(imageid1);
                destinationPath = destinationPath + "/trafficSignalPlan/images/camera/" + junctionName2 + "/" + side_name2 + "\\" + image_name1;
                File f = new File(destinationPath);
                FileInputStream fis = null;
                if (!f.exists()) {
                    destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
                    f = new File(destinationPath);
                }
                fis = new FileInputStream(f);
                if (destinationPath.contains("pdf")) {
                    response.setContentType("pdf");
                } else {
                    response.setContentType("image/jpeg");
                }

                //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                // BufferedImage bi=ImageIO.read(f);
                response.setContentLength(fis.available());
                ServletOutputStream os = response.getOutputStream();
                BufferedOutputStream out = new BufferedOutputStream(os);
                int ch = 0;
                while ((ch = bis.read()) != -1) {
                    out.write(ch);
                }
                bis.close();
                fis.close();
                out.close();
                os.close();
                response.flushBuffer();
                return;

            }
        } catch (IOException | NumberFormatException | ServletException e) {
            System.out.println("exception in image view part" + e);
        }
        if (task.equals("GetCordinates1")) {
            String count = request.getParameter("count");
            String longi1 = request.getParameter("longitude" + count);
            String latti1 = request.getParameter("latitude" + count);
            if (longi1 == null || longi1.equals("undefined")) {
                longi1 = "0";
            }
            if (latti1 == null || latti1.equals("undefined")) {
                latti1 = "0";
            }
            request.setAttribute("longi", longi1);
            request.setAttribute("latti", latti1);
            request.setAttribute("count", count);
            System.out.println(latti1 + "," + longi1);
            request.getRequestDispatcher("getCordinate1").forward(request, response);
            return;
        }

        String junction_idS = request.getParameter("junction_id122");
        String no_of_sidesS = request.getParameter("no_of_sides122");
        String program_version_noS = request.getParameter("program_version_no122");
        String junction_idS1 = map.get("junction_id");
        String no_of_sidesS1 = map.get("no_of_sides");
        String program_version_noS1 = map.get("program_version_no");
        int junction_id = (junction_idS != null) ? Integer.parseInt(junction_idS.trim()) : (junction_idS1 != null) ? Integer.parseInt(junction_idS1.trim()) : 0;
        int no_of_sides = (no_of_sidesS != null) ? Integer.parseInt(no_of_sidesS.trim()) : (no_of_sidesS1 != null) ? Integer.parseInt(no_of_sidesS1.trim()) : 0;
        int program_version_no = (program_version_noS != null) ? Integer.parseInt(program_version_noS.trim()) : (program_version_noS1 != null) ? Integer.parseInt(program_version_noS1.trim()) : 0;
        jId = junction_id;
        if (task.equals("SAVE") || task.equals("Save AS New")) {
            // List<SlaveInfo> slaveInfoList = new ArrayList<SlaveInfo>();
            SlaveInfo slave_info = new SlaveInfo();
            int no_of_cols = Integer.parseInt(map.get("s_no_count"));
            for (int i = 1; i <= no_of_cols; i++) {

                try {
                    int side_no = Integer.parseInt(map.get("side_no" + i));
                    String side_name = map.get("side_name" + i).trim();
                    int side_revision_no = (map.get("side_revision_no" + i) == null ? 0 : Integer.parseInt(map.get("side_revision_no" + i)));

                    String slave_id = map.get("slave_id" + i) == null ? " " : map.get("slave_id" + i);

                    int id = 0;
                    try {
                        id = Integer.parseInt(map.get("junction_id").trim());
                    } catch (Exception ex) {
                        id = 0;
                    }
                    int prog_version = 0;
                    try {
                        prog_version = Integer.parseInt(map.get("program_version_no").trim());
                    } catch (Exception ex) {
                        prog_version = 0;
                    }
                    if (task1.equals("Save AS New")) {
                        id = 0;
                    }
//                    if(slaveinfoModel.checkSideNoExsist(side_no, junction_id)) {
//                        continue;
//                    } else {
                    slave_info.setJunction_id(id);
                    slave_info.setProgram_version_no(prog_version);
                    slave_info.setSide_no(side_no);
                    slave_info.setRevision_no(side_revision_no);
                    slave_info.setSideName(slaveinfoModel.getSideName(junction_id, program_version_no, side_no));

                    slave_info.setCount_down(map.get("count_down" + i));
                    slave_info.setNo_of_lane(Integer.parseInt(map.get("no_of_lane" + i)));
                    slave_info.setVehicle_detection(map.get("vehicle_detection" + i));
                    image_folder = destination_path;
//                    image_folder = imagePath;

                    slave_info.setImage_folder(image_folder);
                    slave_info.setSecondary_v_aspect_no(Integer.parseInt(map.get("secondary_v_aspect_no" + i)));
                    slave_info.setSecondary_h_aspect_no(Integer.parseInt(map.get("secondary_h_aspect_no" + i)));
                    slave_info.setPrimary_v_aspect_no(Integer.parseInt(map.get("primary_v_aspect_no" + i)));
                    slave_info.setPrimary_h_aspect_no(Integer.parseInt(map.get("primary_h_aspect_no" + i)));
                    slave_info.setPosition(map.get("position" + i));
                    slave_info.setPole_type(map.get("pole_type" + i));
                    slave_info.setVehicle_detection(map.get("vehicle_detection" + i));
                    slave_info.setCount_down(map.get("count_down" + i));
                    slave_info.setNo_of_lane(Integer.parseInt(map.get("no_of_lane" + i)));
                    slave_info.setRlpd(map.get("rlpd" + i));
                    slave_info.setAnpr(Integer.parseInt(map.get("anpr" + i)));
                    slave_info.setPa_system(map.get("pa_system" + i));
                    slave_info.setLatitude(Double.parseDouble(map.get("latitude" + i)));
                    slave_info.setLongitude(Double.parseDouble(map.get("longitude" + i)));
                    //slaveInfoList.add(slave_info);
                    slaveinfoModel.insertRecord(slave_info);
//                    }

                } catch (Exception ex) {
                    System.out.println("SlaveInfo has less record" + ex);
                }
                // plan_info.setIs_edited((request.getParameter("edited" + i)==null ? 1 : Integer.parseInt(request.getParameter("edited" + i))));

            }

        }

        //junction_id = Integer.parseInt(request.getParameter("junction_id").trim());
        List<SlaveInfo> list1 = slaveinfoModel.showData(junction_id, program_version_no);

        request.setAttribute("no_of_sides", no_of_sides);
        request.setAttribute("junction_id", junction_id);
        request.setAttribute("program_version_no", program_version_no);
        request.setAttribute("slaveinfo", list1);
        request.setAttribute("slave_info_name", slaveinfoModel.getjunctionName(junction_id));
        request.setAttribute("message", slaveinfoModel.getMessage());
        request.setAttribute("msgBgColor", slaveinfoModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.getRequestDispatcher("/slave_info_view").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
