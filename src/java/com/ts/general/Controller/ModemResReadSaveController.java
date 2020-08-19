package com.ts.general.Controller;

import com.ts.junction.tableClasses.Junction;
import com.ts.tcpServer.ClientResponder;
import com.ts.tcpServer.ClientResponderModel;
import com.ts.webservice.TrafficSignalWebServices;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

public class ModemResReadSaveController extends HttpServlet {

    private Junction junction;
    private ServletContext ctx;
    private boolean responseFromModemForRefresh;
    DateFormat dateFormat;
    private Date date;
    private Calendar cal;
    String currentTime;
    private String lastVisitedTime;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setIntHeader("Refresh", 50);
        ClientResponder clientResponder = null;
        try {
            this.ctx = getServletContext();
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.cal = Calendar.getInstance();
            this.currentTime = this.dateFormat.format(this.cal.getTime());
            ClientResponderModel clientResponderModel = new ClientResponderModel();
            clientResponderModel.setDriverClass(this.ctx.getInitParameter("driverClass"));
            clientResponderModel.setConnectionString(this.ctx.getInitParameter("connectionString"));
            clientResponderModel.setDb_userName(this.ctx.getInitParameter("db_userName"));
            clientResponderModel.setDb_userPasswrod(this.ctx.getInitParameter("db_userPassword"));
            clientResponderModel.setConnection();
            this.lastVisitedTime = this.currentTime;
            Junction jun = (Junction) this.ctx.getAttribute("junction");
            //int live_junction_id = jun.getJunction_id();
            if (jun != null) {
                clientResponder = jun.getClientResponder();

                response.setContentType("text/html");

                String task = request.getParameter("task");
                task = task != null ? task.trim() : "";

                synchronized (this) {
                    if (task.equals("readClientResponse")) {
                        out.println(getServletContext().getAttribute("clientResponse"));
                        return;
                    }
                    if (task.equals("setClientResponse")) {
                        String message = request.getParameter("message").trim();
                        getServletContext().setAttribute("clientResponse", message);
                        return;
                    }
                }
                String junctionName = "";
                String res = "";
                if (task.equals("viewJunctionCurrentStatus")) {
                    boolean check = false;
                    System.out.println("hrr");
                    try {
                        int junctionID = Integer.parseInt(request.getParameter("junctionId"));
                        int program_version_no = Integer.parseInt(request.getParameter("program_version_no"));
                        System.out.println("junctionID -- " + junctionID+"       program_version_no---- " + program_version_no);
                        List junctionID1 = clientResponderModel.getJunctionID();
                        check = junctionID1.contains(Integer.valueOf(junctionID));
                        if (!check) {
                            if(!clientResponderModel.isValidJunction(junctionID,program_version_no)){
                                request.getRequestDispatcher("loggedInJunctionCont").forward(request, response);
                                return;
                            }
                        }
                        int fileNoFromDB = clientResponderModel.getFileNo(junctionID, program_version_no);
                        junctionName = request.getParameter("junctionName");
                        //res = "126 126 126 126 " + junctionID + " "+ program_version_no + " " + fileNoFromDB + " " +"8" + " " +"125" + " " + "125";

                        //System.out.println("refresh request in ModemResReadSaveController ***** ..." + res);
                        //if (clientResponder.sendResponse(res)) {
                        if (true) {                          
                            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            this.cal = Calendar.getInstance();
                            this.currentTime = this.dateFormat.format(this.cal.getTime());
                            String currentVisitedTime = this.currentTime;
                            int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                            int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                            int calculatedDifference = calculateCurrentTime - calculateLastTime;
                            this.responseFromModemForRefresh = jun.isResponseFromModemForActivity();
                            if (!this.responseFromModemForRefresh) { 
                                while (calculatedDifference < 20) {
                                    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    this.cal = Calendar.getInstance();
                                    this.currentTime = this.dateFormat.format(this.cal.getTime());
                                    currentVisitedTime = this.currentTime;
                                    calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                                    calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                                    calculatedDifference = calculateCurrentTime - calculateLastTime;
                                    this.responseFromModemForRefresh = jun.isResponseFromModemForActivity();
                                    if (this.responseFromModemForRefresh == true) {
                                        break;
                                    }
                                }
                            }                             
                            if (this.responseFromModemForRefresh == true) {
                                System.out.println("responseFromModemForRefresh status is----true");
                                //if(live_junction_id == junctionID){
                                String junction_id1 = Integer.toString(junctionID);
                                request.setAttribute("selected_junction_id", junction_id1);
                                //Session["username"] = junctionID;
                                request.getRequestDispatcher("ts_statusShowerCont").forward(request, response);
                                return;
                            }
                           // }
                              

                            String jSON_format = "Timeout... Modem didn't responds relative to viewing <b>" + junctionName + "</b> signal status.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                       }
                       // }
                        System.out.println("Unable to send the request for refresh function");

                        String jSON_format = "Unable to send the request for refresh function corresponding to <b>" + junctionName;
                        request.setAttribute("message", jSON_format);
                        request.getRequestDispatcher("errorView").forward(request, response);

                        return;
                    } catch (Exception ex) {
                        Logger.getLogger(ClientResponder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   if (task.equals("viewFireCurrentStatus")) {
                    
                    int junctionID =0;
                     if (TrafficSignalWebServices.mapDatanew != null) {
               
                    String data = TrafficSignalWebServices.mapDatanew;
                    junctionID = Integer.parseInt(data);
                     
             
            }
//if(request.getParameter("mapDatanew")!=null){
//    junctionID=Integer.parseInt(request.getParameter("mapDatanew"));
//
//}
                    boolean check = false;
                    System.out.println("hrr");
                    try { 
                      int program_version_no=0;
                        
                         if(junctionID==0){
                       String jSON_format = "No Fire Vechicle is Running";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                       
                        }else
                            // junctionID = Integer.parseInt(request.getParameter("junctionId"));
                         
                       // program_version_no =43;
                        System.out.println("junctionID -- " + junctionID);
                        List junctionID1 = clientResponderModel.getJunctionID();
                        check = junctionID1.contains(Integer.valueOf(junctionID));
                        if (!check) {
                            if(!clientResponderModel.isValidJunction11(junctionID)){
                                request.getRequestDispatcher("loggedInJunctionCont").forward(request, response);
                                return;
                            }
                        }
                       // int fileNoFromDB = clientResponderModel.getFileNo(junctionID, program_version_no);
                      //  junctionName = request.getParameter("junctionName");
                        //res = "126 126 126 126 " + junctionID + " "+ program_version_no + " " + fileNoFromDB + " " +"8" + " " +"125" + " " + "125";

                        //System.out.println("refresh request in ModemResReadSaveController ***** ..." + res);
                        //if (clientResponder.sendResponse(res)) {
                        if (true) {                          
                            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            this.cal = Calendar.getInstance();
                            this.currentTime = this.dateFormat.format(this.cal.getTime());
                            String currentVisitedTime = this.currentTime;
                            int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                            int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                            int calculatedDifference = calculateCurrentTime - calculateLastTime;
                            this.responseFromModemForRefresh = jun.isResponseFromModemForActivity();
                            if (!this.responseFromModemForRefresh) {
                                while (calculatedDifference < 20) {
                                    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    this.cal = Calendar.getInstance();
                                    this.currentTime = this.dateFormat.format(this.cal.getTime());
                                    currentVisitedTime = this.currentTime;
                                    calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                                    calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                                    calculatedDifference = calculateCurrentTime - calculateLastTime;
                                    this.responseFromModemForRefresh = jun.isResponseFromModemForActivity();
                                    if (this.responseFromModemForRefresh == true) {
                                        break;
                                    }
                                }
                            }                             
                            if (this.responseFromModemForRefresh == true) {
                                System.out.println("responseFromModemForRefresh status is----true");
                                //if(live_junction_id == junctionID){
                                String junction_id1 = Integer.toString(junctionID);
                                request.setAttribute("selected_junction_id", junction_id1);
                                //Session["username"] = junctionID;
                                request.getRequestDispatcher("ts_statusShowerCont").forward(request, response);
                                return;
                            }
                           // }
                              

                            String jSON_format = "Timeout... Modem didn't responds relative to viewing <b>" + junctionName + "</b> signal status.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                       }
                       // }
                        System.out.println("Unable to send the request for refresh function");

                        String jSON_format = "Unable to send the request for refresh function corresponding to <b>" + junctionName;
                        request.setAttribute("message", jSON_format);
                        request.getRequestDispatcher("errorView").forward(request, response);

                        return;
                    } catch (Exception ex) {
                        Logger.getLogger(ClientResponder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    String jSON_format = "Oops... Some error occured with selected junction.";

                    request.setAttribute("message", jSON_format);
                    request.getRequestDispatcher("errorView").forward(request, response);
                    return;
                }
            }else {
               
                String jSON_format = "Oops... Some error occured with selected junction.";

                request.setAttribute("message", jSON_format);
                request.getRequestDispatcher("errorView").forward(request, response);
                return;
            }

        } catch (Exception e) {
            System.out.println("ModemResReadSaveController Error - " + e);
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

    public void setJunction(Junction junction) {
        this.junction = junction;
    }

    public void setResponseFromModemForRefresh(boolean responseFromModemForRefresh) {
        this.responseFromModemForRefresh = responseFromModemForRefresh;
    }

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
        System.out.println("context object in Modem read save cont" + this.ctx);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
