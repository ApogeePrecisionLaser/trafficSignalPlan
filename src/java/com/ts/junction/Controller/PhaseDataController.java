/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import com.ts.junction.Model.JunctionPlanMapModel;
import com.ts.junction.Model.PhaseDataModel;
import com.ts.junction.tableClasses.JunctionPlanMap;
import com.ts.junction.tableClasses.PhaseData;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author DELL
 */
public class PhaseDataController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PhaseDataModel phaseDataModel = new PhaseDataModel();
        ServletContext ctx = getServletContext();
        phaseDataModel.setDriverClass(ctx.getInitParameter("driverClass"));
        phaseDataModel.setConnectionString(ctx.getInitParameter("connectionString"));
        phaseDataModel.setDb_userName(ctx.getInitParameter("db_userName"));
        phaseDataModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        phaseDataModel.setConnection();
        String task = request.getParameter("task");
        Map<String, String> map = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        int side_no = 0;
        int selected_plan_id=0;
              int selected_plan_id1=0;
        String mode_name = "";
        int no_of_phase = 0;
        try {
            //----- This is only for Vendor key Person JQuery
            String jqstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (jqstring != null) {
                List<String> list = null;
//                if (jqstring.equals("getOnOffTime")) {
//                    list = phaseDataModel.getOnOffTime(q, request.getParameter("action2"), request.getParameter("action3"), request.getParameter("action4"));
//                }

                if (jqstring.equals("getDate")) {
                    list = phaseDataModel.getDateTime(q, request.getParameter("action2"));

                }
                if (jqstring.equals("getsearchJunctionName")) {
                    list = phaseDataModel.getsearchJunctionName(q);
                }
                if (jqstring.equals("getDay")) {
                    list = phaseDataModel.getDayTime(q, request.getParameter("action2"));

                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
        }
        String junction_name_select = request.getParameter("searchJunctionName");
        String on_off_time_select = request.getParameter("start_time");
        String day_select = request.getParameter("day");
        String date_select = request.getParameter("date");
        if (task == null) {
            task = "";
        }
        if (task.equals("OK")) {
              
                    selected_plan_id=Integer.parseInt(request.getParameter("selected_plan_id"));
            side_no = phaseDataModel.getSidesOfJunction(junction_name_select);
            mode_name = phaseDataModel.getModeName(on_off_time_select,selected_plan_id);
            if (mode_name.equals("Blinker")) {
                no_of_phase = 2;
            } else if (mode_name.equals("Signal")) {
                if (side_no == 3) {
                    no_of_phase = 7;

                } else {
                    no_of_phase = 9;
                }

            }
        }
        int no_of_phase1 = 0;
        int side_no1 = 0;
        String check = "";
        String date1 = "";
        String day1 = "";
        String on_off_time_select1 = "";
        String junction_name = "";
        if (task.equals("submit")) {
            no_of_phase1 = Integer.parseInt(request.getParameter("rows"));
            side_no1 = Integer.parseInt(request.getParameter("columns"));
            date1 = request.getParameter("date_select");
            day1 = request.getParameter("day_select");
            on_off_time_select1 = request.getParameter("on_off_time_select");
            junction_name = request.getParameter("junction_name");
         
                  selected_plan_id1=Integer.parseInt(request.getParameter("selected_plan_id1"));
            String select_color = "";
            for (int i = 0; i < no_of_phase1; i++) {

                for (int j = 0; j < side_no1; j++) {
                    check = request.getParameter("hp" + i + j);
                    map.put("hp" + i + j, check);

                }

            }

            String side[][] = new String[no_of_phase1][(side_no1)];
            for (int i = 0; i < no_of_phase1; i++) {

                for (int j = 0; j < side_no1; j++) {

                    String side_value = map.get("hp" + i + j);
                    if (side_value != null) {
                        String side_color = side_value.substring(0, (side_value.length() - 2));
                        if (side_color.equals("red")) {
                            side[i][j] = "1000";
                        } else if (side_color.equals("amber")) {
                            side[i][j] = "0100";
                        } else if (side_color.equals("green1")) {
                            side[i][j] = "0010";
                        } else if (side_color.equals("green2")) {
                            side[i][j] = "0001";
                        } else if (side_color.equals("green12")) {
                            side[i][j] = "0011";
                        }
                    } else {
                        side[i][j] = "0000";
                    }
                 
                }

            }
            
          

            String side13[] = new String[no_of_phase1];
            String side24[] = new String[no_of_phase1];

            for (int i = 0; i < no_of_phase1; i++) {
                PhaseData phaseData = new PhaseData();
                int phase_info_id;
                try {
                    phase_info_id = Integer.parseInt(request.getParameter("phase_info_id").trim());
                } catch (Exception e) {
                    phase_info_id = 0;
                }
                String on_time = on_off_time_select1.split("-")[0];
                String off_time = on_off_time_select1.split("-")[1];
                if (date1 != "") {
                    phaseData.setTo_date(date1.split("//")[1]);
                    phaseData.setFrom_date(date1.split("//")[0]);
                }
                if (day1 != "") {
                    phaseData.setDay(day1);
                }

                phaseData.setJunction_name(junction_name);
                phaseData.setOff_time_hr(Integer.parseInt(off_time.split(":")[0]));
                phaseData.setOff_time_min(Integer.parseInt(off_time.split(":")[1]));
                phaseData.setOn_time_hr(Integer.parseInt(on_time.split(":")[0]));
                phaseData.setOn_time_min(Integer.parseInt(on_time.split(":")[1]));
                phaseData.setPhase_no(i);
                side13[i] = side[i][0] + side[i][2];
                phaseData.setSide13(Integer.parseInt(side13[i], 2));
                if (side_no1 == 3) {
                    side24[i] = side[i][1] + "0000";
                } else if (side_no1 == 4) {
                    side24[i] = side[i][1] + side[i][3];
                }
                phaseData.setSide24(Integer.parseInt(side24[i], 2));
                if (phase_info_id == 0) {
                    // validation was successful so now insert record.
                 
                    phaseDataModel.insertRecord(phaseData,selected_plan_id1);
                }
            }
        }

        if (task.equals(
                "Delete")) {
            int phase_info_id = Integer.parseInt(request.getParameter("phase_info_id").trim());
            try {
//                phaseDataModel.deleteRecord(phase_info_id);
            } catch (Exception e) {
                System.out.println("Exception" + e);
            }
        }
        String searchCommandName = "";
        List<PhaseData> list1 = new ArrayList<>();

        searchCommandName = request.getParameter("searchParameterName");

        try {
            if (searchCommandName == null) {
                searchCommandName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable = 0;
        //noOfRowsInTable = junctionPlanMapModel.getNoOfRows();

        // get the number of records (rows) in the table.
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction
                == null) {
            buttonAction = "none";
        }

        if (buttonAction.equals(
                "Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals(
                "Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals(
                "First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals(
                "Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        if (task.equals(
                "Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }

        list1 = phaseDataModel.showData(lowerLimit, noOfRowsToDisplay, searchCommandName);
        lowerLimit = lowerLimit + list1.size();
        noOfRowsTraversed = list1.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);

        request.setAttribute("junctionPlanMapPhaseList", list1);
 request.setAttribute("selected_plan_id1", selected_plan_id);
        request.setAttribute("junction_id", request.getParameter("junction_id"));
        request.setAttribute("no_of_sides", request.getParameter("no_of_sides"));
        request.setAttribute("junction_name", junction_name_select);
        request.setAttribute("day_select", day_select);
        request.setAttribute("date_select", date_select);
        request.setAttribute("on_off_time_select", on_off_time_select);
        request.setAttribute("message", phaseDataModel.getMessage());
        request.setAttribute("msgBgColor", phaseDataModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("side_no", side_no);
        request.setAttribute("no_of_phase", no_of_phase);
        request.getRequestDispatcher("/phase_data").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
