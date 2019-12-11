package com.ts.general.Controller;

import com.ts.general.Model.Signal;
import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.PlanInfo;
import com.ts.tcpServer.ClientResponder;
import com.ts.tcpServer.ClientResponderModel;
import com.ts.webservice.ClientResponderWS1;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TS_StatusUpdaterController extends HttpServlet {

    private ServletContext ctx;
    private boolean responseFromModemForClearance;
    private boolean responseFromModemForActivity;
    DateFormat dateFormat;
    private Calendar cal;
    String currentTime;
    private String lastVisitedTime;

    public void init()
            throws ServletException {
        super.init();
        Signal signal = new Signal();
        getServletContext().setAttribute("signal", signal);
        System.out.println("\n\nSC_HC: " + getServletContext().hashCode() + " signal obj set.  ObjHC: " + signal.hashCode() + "   ObjAddr: " + signal);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();

        PrintWriter out = response.getWriter();
        ctx = getServletContext();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.cal = Calendar.getInstance();
        this.currentTime = this.dateFormat.format(this.cal.getTime());
        this.lastVisitedTime = this.currentTime;
        ctx = getServletContext();
        Junction jun = (Junction) ctx.getAttribute("junction");
        if (jun == null) {
            jun = new Junction();
        }
        ClientResponder clientResponder = jun.getClientResponder();
        ClientResponderWS1 clientResponderws = jun.getClientResponderws();
        if (clientResponder == null) {
            clientResponder = new ClientResponder();
            jun.setClientResponder(clientResponder);
        }
        if (clientResponderws == null) {
            clientResponderws = new ClientResponderWS1();
            jun.setClientResponderws(clientResponderws);
        }

        response.setContentType("text/html");

        String task = request.getParameter("task");
        task = task != null ? task.trim() : "";
        String hours = request.getParameter("h");
        String minutes = request.getParameter("m");
        String seconds = request.getParameter("s");
        String currentTime = hours + ":" + minutes + ":" + seconds;
        PlanInfo planInfo = (PlanInfo) ctx.getAttribute("planInfoRefreshList");

        Signal signal = (Signal) ctx.getAttribute("signal");
        synchronized (signal) {
            signal.setCurrentTime(currentTime);
            System.out.println("\n\nTime " + signal.getCurrentTime() + " updated successfully." + " ObjHC: " + signal.hashCode() + "   ObjAddr: " + signal + "ServletContext Hashcode: " + ctx.hashCode());
        }
        if (task.equals("Change To Green")) {
            int sideNo1 = 0;
            clientResponderws.setRequestForClearance(true);
            clientResponderws.setRequestForActivity(false);
            String junctionName = request.getParameter("junctionName");
            int junctionID = Integer.parseInt(request.getParameter("junctionId"));
            int program_version_no = Integer.parseInt(request.getParameter("program_version_no"));
            int fileNo = Integer.parseInt(request.getParameter("fileNo"));
            String radioBtnValue = request.getParameter("radioBtnValue");
            if (radioBtnValue == null || radioBtnValue.isEmpty() || radioBtnValue.equals("1")) {
                sideNo1 = 1;
            } else if (radioBtnValue.equals("2")) {
                sideNo1 = 2;
            } else if (radioBtnValue.equals("3")) {
                sideNo1 = 3;
            } else if (radioBtnValue.equals("4")) {
                sideNo1 = 4;
            }
            //String res = "126 126 126 126 " + junctionID + " " + program_version_no+ " " + fileNo + " "+  "8" + " " +  "2" + " " + sideNo1 + " " +  "1" + " " +  "1" + " " + "125" + " " + "125";

            //  if (clientResponder.sendResponse(res)) {
            clientResponderws.setClearanceSide(sideNo1);
            ctx.setAttribute("requestForActivity"+junctionID, false);
            ctx.setAttribute("requestForClearance"+junctionID, true);
            ctx.setAttribute("clearanceSide"+junctionID, sideNo1);

            System.out.println("status updater RequestForClearance: " + clientResponderws.isRequestForClearance());
            if (planInfo != null);
            planInfo.setResponseFromModemForClearance(false);
            clientResponderws.setPlanInfoRefreshList(planInfo);
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.cal = Calendar.getInstance();
            currentTime = this.dateFormat.format(this.cal.getTime());
            String currentVisitedTime = currentTime;
            int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
            int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
            int calculatedDifference = calculateCurrentTime - calculateLastTime;
            this.responseFromModemForClearance = jun.isResponseFromModemForClearance();
            out.print("success");
            out.close();
            return;
            /*if (!this.responseFromModemForClearance) {
                while (calculatedDifference < 20) {
                    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    this.cal = Calendar.getInstance();
                    currentTime = this.dateFormat.format(this.cal.getTime());
                    currentVisitedTime = currentTime;
                    calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                    calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                    calculatedDifference = calculateCurrentTime - calculateLastTime;
                    this.responseFromModemForClearance = jun.isResponseFromModemForClearance();
                    if (this.responseFromModemForClearance == true) {
                        break;
                    }
                }
            }

//            if (this.responseFromModemForClearance == true) {
//                System.out.println("responseFromModemForClearance status is----true");
//                request.getRequestDispatcher("ts_statusShowerCont").forward(request, response);
//                return;
//            }

            String jSON_format = "Timeout... Modem didn't responds relative to changing <b>" + junctionName + "</b> signal status.";

            request.setAttribute("message", jSON_format);
            request.getRequestDispatcher("errorView").forward(request, response);
            return;
            //}

//            System.out.println("Unable to send the request for clearance function");
//
//            String jSON_format = "Unable to send the request for clearance function corresponding to <b>" + junctionName;
//            request.setAttribute("message", jSON_format);
//            request.getRequestDispatcher("errorView").forward(request, response);
//
//            return;*/
        }


        if (task.equals("changeActivity")) {
            String activity = request.getParameter("activity_no");
            int activitySide = Integer.parseInt(request.getParameter("activitySide"));
            int junctionID = Integer.parseInt(request.getParameter("junctionId"));
            System.out.println("Server request for activity(from staus updater): " + activity + " " + activitySide);
            int activity_no = activity == null || activity.isEmpty() ? 1 : Integer.parseInt(activity.trim());
            clientResponder.setRequestForActivity(true);
            clientResponder.setRequestForClearance(false);
            clientResponder.setActivityNo(activity_no);
            clientResponder.setActivitySide(activitySide);
            clientResponderws.setRequestForActivity(true);
            clientResponderws.setRequestForClearance(false);
            clientResponderws.setActivityNo(activity_no);
            clientResponderws.setActivitySide(activitySide);
            ctx.setAttribute("requestForActivity"+junctionID, true);
            ctx.setAttribute("requestForClearance"+junctionID, false);
            ctx.setAttribute("activityNo"+junctionID, activity_no);
            ctx.setAttribute("activitySide"+junctionID, activitySide);
//             if (true) {ctx.setAttribute("activityNo"+junctionID, activity_no);
            ctx.setAttribute("activitySide"+junctionID, activitySide);
//                request.setAttribute("val", clientResponder.isRequestForActivity());
//                request.getRequestDispatcher("ts_statusShowerCont").forward(request, response);
//                return;
//            }
            jun.setResponseFromModemForActivity(false);
            String junctionName = request.getParameter("junctionName");
            //int junctionID = Integer.parseInt(request.getParameter("junctionId"));
            int program_version_no = Integer.parseInt(request.getParameter("program_version_no"));
            int fileNo = Integer.parseInt(request.getParameter("fileNo"));
//            String res = "126 126 126 126 " + junctionID + " " + program_version_no + " " + fileNo + " " + "8" + " "  + activity + " " +  "1" + " " +  "1" + " " +  "1" + " " + "125" + " " + "125";
            // System.out.println("Change activity request - - " + res);
            //  if (clientResponder.sendResponse(res)) {

            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.cal = Calendar.getInstance();
            currentTime = this.dateFormat.format(this.cal.getTime());
            String currentVisitedTime = currentTime;
            int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
            int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
            int calculatedDifference = calculateCurrentTime - calculateLastTime;
            this.responseFromModemForActivity = jun.isResponseFromModemForActivity();
            /*if (!this.responseFromModemForActivity) {
                while (calculatedDifference < 20) {
                    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    this.cal = Calendar.getInstance();
                    currentTime = this.dateFormat.format(this.cal.getTime());
                    currentVisitedTime = currentTime;
                    calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                    calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                    calculatedDifference = calculateCurrentTime - calculateLastTime;
                    this.responseFromModemForActivity = jun.isResponseFromModemForActivity();
                    if (this.responseFromModemForActivity == true) {
                        break;
                    }
                }
            }*/

            if (true) {
                //request.getRequestDispatcher("ts_statusShowerCont").forward(request, response);
                out.print("success");
                out.close();
                return;
            }
            if (this.responseFromModemForActivity == false) {
                String jSON_format = "Timeout... Modem didn't responds relative to changing <b>" + junctionName + "</b> activity. ";
                request.setAttribute("message", jSON_format);
                request.getRequestDispatcher("errorView").forward(request, response);
                return;
            }
            System.out.println("Unable to send the request for change activity");
            String jSON_format = "Unable to send the request to <b>" + junctionName;
            request.setAttribute("message", jSON_format);
            request.getRequestDispatcher("errorView").forward(request, response);

            return;
        }
    }

    public int calculateTimeInSeconds(String Time) {
        String[] currTime = Time.split(" ");
        String strTime = currTime[1];

        String[] currStr = strTime.split(":");
        int Hr = Integer.parseInt(currStr[0]);
        int Min = Integer.parseInt(currStr[1]);
        int Sec = Integer.parseInt(currStr[2]);
        int calculatedTime = Hr * 60 * 60 + Min * 60 + Sec;

        return calculatedTime;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public ServletContext getCtx() {
        return this.ctx;
    }

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }
}
