package com.ts.general.Controller;

import com.ts.general.Model.Signal;
import com.ts.junction.tableClasses.BaldeobagInfo;
import com.ts.junction.tableClasses.BandariyaTirahaInfo;
import com.ts.junction.tableClasses.BloomChowkInfo;
import com.ts.junction.tableClasses.DamohNakaInfo;
import com.ts.junction.tableClasses.DeendayalChowkInfo;
import com.ts.junction.tableClasses.GohalPurInfo;
import com.ts.junction.tableClasses.HighCourtInfo;
import com.ts.junction.tableClasses.History;
import com.ts.junction.tableClasses.KatangaInfo;
import com.ts.junction.tableClasses.MadanMahalInfo;
import com.ts.junction.tableClasses.PlanInfo;
import com.ts.junction.tableClasses.RanitalInfo;
import com.ts.junction.tableClasses.TeenPatti_Info;
import com.ts.junction.tableClasses.YatayatThanaInfo;
import com.ts.tcpServer.ClientResponder;
import com.ts.tcpServer.ClientResponderModel;
import com.ts.webservice.ClientResponderWS1;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class TS_StatusShowerController1 extends HttpServlet {

    private PlanInfo planInfoList;
    private TeenPatti_Info teenPattiInfoList;
    private RanitalInfo ranitalInfoList;
    private DamohNakaInfo damohNakaInfoList;
    private YatayatThanaInfo yatayatThanaInfoList;
    private KatangaInfo katangaInfoList;
    private BaldeobagInfo baldeobagInfoList;
    private DeendayalChowkInfo deendayalChowkInfoList;
    private HighCourtInfo highCourtInfoList;
    private BloomChowkInfo bloomChowkInfoList;
    private MadanMahalInfo madanMahalInfoList;
    private GohalPurInfo gohalPurInfoList;
    private ClientResponder clientResponder;
    private BandariyaTirahaInfo bandariyaTirahaInfoList;

    @Override
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ServletContext ctx = getServletContext();
            // for testing
            ClientResponderModel clientResponderModel = new ClientResponderModel();
            ServletContext ctx1 = getServletContext();
            clientResponderModel.setDriverClass(ctx1.getInitParameter("driverClass"));
            clientResponderModel.setConnectionString(ctx1.getInitParameter("connectionString"));
            clientResponderModel.setDb_userName(ctx1.getInitParameter("db_userName"));
            clientResponderModel.setDb_userPasswrod(ctx1.getInitParameter("db_userPassword"));
            clientResponderModel.setConnection();
            // end of testing
            String task = request.getParameter("task");
            int a = 0;
            //int current_junction_id1 = Integer.parseInt(request.getParameter("selected_junction_id"));
            String abc = (String) request.getAttribute("selected_junction_id");
            String map_junction_id = request.getParameter("selected_junction_id");
            if (task == null) {
                task = "";
            }
            if (abc == null) {
                abc = "";
            } else {
                a = Integer.parseInt(abc);
            }
            if (map_junction_id == null) {
                map_junction_id = "";
            } else {
                a = Integer.parseInt(map_junction_id);
            }
            if (task.equals("jQueryRequest")) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                String time = null;
                Signal signal = (Signal) ctx.getAttribute("signal");
                if (signal != null) {
                    synchronized (signal) {
                        time = signal.getCurrentTime();
                    }
                } else {
                    System.out.println("signal object was not found");
                }

                out.print(time);
                out.flush();
                return;
            }
            int i = 1;
            if (task.equals("getLatestStatus")) {
                PrintWriter out = response.getWriter();
                JSONObject jsonObj = new JSONObject();
                int current_junction_id = Integer.parseInt(request.getParameter("current_junction_id"));
                //ClientResponderWS client = new ClientResponderWS1();
//                if(i == 1){
//                    ClientResponderWS1.main("abc");
//                    i++;
//                }

                Map<String, PlanInfo> mp = new HashMap();
//                mp.put("planInfolist", (PlanInfo) ctx.getAttribute("planInfolist"));
//                mp.put("teenPattiInfoList", (PlanInfo) ctx.getAttribute("teenPattiInfoList"));
//                mp.put("ranitalInfoList", (PlanInfo) ctx.getAttribute("ranitalInfoList"));
//                mp.put("damohNakaInfoList", (PlanInfo) ctx.getAttribute("damohNakaInfoList"));
//                mp.put("yatayatThanaInfoList", (PlanInfo) ctx.getAttribute("yatayatThanaInfoList"));
//                mp.put("katangaInfoList", (PlanInfo) ctx.getAttribute("katangaInfoList"));
//                mp.put("baldeobagInfoList", (PlanInfo) ctx.getAttribute("baldeobagInfoList"));
//                mp.put("deendayalChowkInfoList", (PlanInfo) ctx.getAttribute("deendayalChowkInfoList"));
//                mp.put("highCourtInfoList", (PlanInfo) ctx.getAttribute("highCourtInfoList"));
//                mp.put("bloomChowkInfoList", (PlanInfo) ctx.getAttribute("bloomChowkInfoList"));
//                mp.put("madanMahalInfoList", (PlanInfo) ctx.getAttribute("madanMahalInfoList"));
                this.planInfoList = ((PlanInfo) ctx.getAttribute("planInfolist"));
//ClientResponderWS.getPlanInfoRefreshList();
                this.teenPattiInfoList = ((TeenPatti_Info) ctx.getAttribute("teenPattiInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.ranitalInfoList = ((RanitalInfo) ctx.getAttribute("ranitalInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.damohNakaInfoList = ((DamohNakaInfo) ctx.getAttribute("damohNakaInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.yatayatThanaInfoList = ((YatayatThanaInfo) ctx.getAttribute("yatayatThanaInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.katangaInfoList = ((KatangaInfo) ctx.getAttribute("katangaInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.baldeobagInfoList = ((BaldeobagInfo) ctx.getAttribute("baldeobagInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.deendayalChowkInfoList = ((DeendayalChowkInfo) ctx.getAttribute("deendayalChowkInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.highCourtInfoList = ((HighCourtInfo) ctx.getAttribute("highCourtInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.bloomChowkInfoList = ((BloomChowkInfo) ctx.getAttribute("bloomChowkInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                this.madanMahalInfoList = ((MadanMahalInfo) ctx.getAttribute("madanMahalInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
this.bandariyaTirahaInfoList = ((BandariyaTirahaInfo) ctx.getAttribute("bandariyaTirahaInfoList"));//ClientResponderWS.getPlanInfoRefreshList();
                //int junction_id1 = this.ranitalInfoList.getJunction_id();
//                if(current_junction_id == 13){
//                this.planInfoList = null;
//                BeanUtils.copyProperties(planInfoList, teenPattiInfoList);
//                }
//                if(current_junction_id == 11){
//                this.planInfoList = null;
//                BeanUtils.copyProperties(planInfoList, ranitalInfoList);
//                }

                //     for teen patti
                if (current_junction_id == 13) {

                    if (mp.containsKey("teenPattiInfoList")) {

                        if (mp.get("teenPattiInfoList") != null) {

                            PlanInfo pi = mp.get("teenPattiInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {

                                int functionNo = pi.getFunction_no();
                                int junction_id = pi.getJunction_id();
                                int program_version_no = pi.getProgram_version_no();
                                int fileNo = pi.getFileNo();
                                int activity = pi.getActivity();
                                int side_no = pi.getSide_no();
                                int plan_no = pi.getPlan_no();
                                String mode = pi.getMode();
                                String junctionName = pi.getJunction_name();
                                String sideName = pi.getSideName();

                                String onTime = pi.getOnTime();
                                String offTime = pi.getOffTime();

                                String side1Name = pi.getSide1Name();
                                String side2Name = pi.getSide2Name();
                                String side3Name = pi.getSide3Name();
                                String side4Name = pi.getSide4Name();
                                String side5Name = pi.getSide5Name();

                                int side1Time = pi.getSide1_time();
                                int side2Time = pi.getSide2_time();
                                int side3Time = pi.getSide3_time();
                                int side4Time = pi.getSide4_time();
                                int side1LeftStatus = pi.getSide1_left_status();
                                int side1RightStatus = pi.getSide1_right_status();
                                int side1UpStatus = pi.getSide1_up_status();
                                int side1DownStatus = pi.getSide1_down_status();
                                int side2LeftStatus = pi.getSide2_left_status();
                                int side2RightStatus = pi.getSide2_right_status();
                                int side2UpStatus = pi.getSide2_up_status();
                                int side2DownStatus = pi.getSide2_down_status();
                                int side3LeftStatus = pi.getSide3_left_status();
                                int side3RightStatus = pi.getSide3_right_status();
                                int side3UpStatus = pi.getSide3_up_status();
                                int side3DownStatus = pi.getSide3_down_status();
                                int side4LeftStatus = pi.getSide4_left_status();
                                int side4RightStatus = pi.getSide4_right_status();
                                int side4UpStatus = pi.getSide4_up_status();
                                int side4DownStatus = pi.getSide4_down_status();

                                int juncHr = pi.getJuncHr();
                                int juncMin = pi.getJuncMin();
                                int juncDat = pi.getJuncDate();
                                int juncMonth = pi.getJuncMonth();
                                int juncYear = pi.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;

                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();

                            }

                        } else {

                            out.println("");

                        }
                    }
                }

                // end for teen patti
                // start for damohnaka
                if (current_junction_id == 2) {
                    if (mp.containsKey("damohNakaInfoList")) {

                        if (mp.get("damohNakaInfoList") != null) {

                            PlanInfo pid = mp.get("damohNakaInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {
                                int functionNo = pid.getFunction_no();
                                int junction_id = pid.getJunction_id();
                                int program_version_no = pid.getProgram_version_no();
                                int fileNo = pid.getFileNo();
                                int activity = pid.getActivity();
                                int side_no = pid.getSide_no();
                                int plan_no = pid.getPlan_no();
                                String mode = pid.getMode();
                                String junctionName = pid.getJunction_name();
                                String sideName = pid.getSideName();

                                String onTime = pid.getOnTime();
                                String offTime = pid.getOffTime();

                                String side1Name = pid.getSide1Name();
                                String side2Name = pid.getSide2Name();
                                String side3Name = pid.getSide3Name();
                                String side4Name = pid.getSide4Name();
                                String side5Name = pid.getSide5Name();

                                int side1Time = pid.getSide1_time();
                                int side2Time = pid.getSide2_time();
                                int side3Time = pid.getSide3_time();
                                int side4Time = pid.getSide4_time();
                                int side1LeftStatus = pid.getSide1_left_status();
                                int side1RightStatus = pid.getSide1_right_status();
                                int side1UpStatus = pid.getSide1_up_status();
                                int side1DownStatus = pid.getSide1_down_status();
                                int side2LeftStatus = pid.getSide2_left_status();
                                int side2RightStatus = pid.getSide2_right_status();
                                int side2UpStatus = pid.getSide2_up_status();
                                int side2DownStatus = pid.getSide2_down_status();
                                int side3LeftStatus = pid.getSide3_left_status();
                                int side3RightStatus = pid.getSide3_right_status();
                                int side3UpStatus = pid.getSide3_up_status();
                                int side3DownStatus = pid.getSide3_down_status();
                                int side4LeftStatus = pid.getSide4_left_status();
                                int side4RightStatus = pid.getSide4_right_status();
                                int side4UpStatus = pid.getSide4_up_status();
                                int side4DownStatus = pid.getSide4_down_status();

                                int juncHr = pid.getJuncHr();
                                int juncMin = pid.getJuncMin();
                                int juncDat = pid.getJuncDate();
                                int juncMonth = pid.getJuncMonth();
                                int juncYear = pid.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;

                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        }
                    }
                }

                // end for damonaka
                // start for ranital signal
                if (current_junction_id == 11) {

                    if (mp.containsKey("ranitalInfoList")) {

                        if (mp.get("ranitalInfoList") != null) {

                            PlanInfo pir = mp.get("ranitalInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {

                                int functionNo = pir.getFunction_no();
                                int junction_id = pir.getJunction_id();
                                int program_version_no = pir.getProgram_version_no();
                                int fileNo = pir.getFileNo();
                                int activity = pir.getActivity();
                                int side_no = pir.getSide_no();
                                int plan_no = pir.getPlan_no();
                                String mode = pir.getMode();
                                String junctionName = pir.getJunction_name();
                                String sideName = pir.getSideName();

                                String onTime = pir.getOnTime();
                                String offTime = pir.getOffTime();

                                String side1Name = pir.getSide1Name();
                                String side2Name = pir.getSide2Name();
                                String side3Name = pir.getSide3Name();
                                String side4Name = pir.getSide4Name();
                                String side5Name = pir.getSide5Name();

                                int side1Time = pir.getSide1_time();
                                int side2Time = pir.getSide2_time();
                                int side3Time = pir.getSide3_time();
                                int side4Time = pir.getSide4_time();
                                int side1LeftStatus = pir.getSide1_left_status();
                                int side1RightStatus = pir.getSide1_right_status();
                                int side1UpStatus = pir.getSide1_up_status();
                                int side1DownStatus = pir.getSide1_down_status();
                                int side2LeftStatus = pir.getSide2_left_status();
                                int side2RightStatus = pir.getSide2_right_status();
                                int side2UpStatus = pir.getSide2_up_status();
                                int side2DownStatus = pir.getSide2_down_status();
                                int side3LeftStatus = pir.getSide3_left_status();
                                int side3RightStatus = pir.getSide3_right_status();
                                int side3UpStatus = pir.getSide3_up_status();
                                int side3DownStatus = pir.getSide3_down_status();
                                int side4LeftStatus = pir.getSide4_left_status();
                                int side4RightStatus = pir.getSide4_right_status();
                                int side4UpStatus = pir.getSide4_up_status();
                                int side4DownStatus = pir.getSide4_down_status();

                                int juncHr = pir.getJuncHr();
                                int juncMin = pir.getJuncMin();
                                int juncDat = pir.getJuncDate();
                                int juncMonth = pir.getJuncMonth();
                                int juncYear = pir.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        } else {
                            out.println("");
                            out.flush();
                        }
                    }
                }

                // end of ranital
                // starrt for yatayat
                if (current_junction_id == 1) {
                   // if (mp.containsKey("yatayatThanaInfoList")) {

//                        if (mp.get("yatayatThanaInfoList") != null) {
                           if (this.yatayatThanaInfoList != null) {
                         //   PlanInfo piy = mp.get("yatayatThanaInfoList");

                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {
//                                int functionNo = piy.getFunction_no();
//                                int junction_id = piy.getJunction_id();
//                                int program_version_no = piy.getProgram_version_no();
//                                int fileNo = piy.getFileNo();
//                                int activity = piy.getActivity();
//                                int side_no = piy.getSide_no();
//                                int plan_no = piy.getPlan_no();
//                                String mode = piy.getMode();
//                                String junctionName = piy.getJunction_name();
//                                String sideName = piy.getSideName();
//
//                                String onTime = piy.getOnTime();
//                                String offTime = piy.getOffTime();
//
//                                String side1Name = piy.getSide1Name();
//                                String side2Name = piy.getSide2Name();
//                                String side3Name = piy.getSide3Name();
//                                String side4Name = piy.getSide4Name();
//                                String side5Name = piy.getSide5Name();
//
//                                int side1Time = piy.getSide1_time();
//                                int side2Time = piy.getSide2_time();
//                                int side3Time = piy.getSide3_time();
//                                int side4Time = piy.getSide4_time();
//                                int side1LeftStatus = piy.getSide1_left_status();
//                                int side1RightStatus = piy.getSide1_right_status();
//                                int side1UpStatus = piy.getSide1_up_status();
//                                int side1DownStatus = piy.getSide1_down_status();
//                                int side2LeftStatus = piy.getSide2_left_status();
//                                int side2RightStatus = piy.getSide2_right_status();
//                                int side2UpStatus = piy.getSide2_up_status();
//                                int side2DownStatus = piy.getSide2_down_status();
//                                int side3LeftStatus = piy.getSide3_left_status();
//                                int side3RightStatus = piy.getSide3_right_status();
//                                int side3UpStatus = piy.getSide3_up_status();
//                                int side3DownStatus = piy.getSide3_down_status();
//                                int side4LeftStatus = piy.getSide4_left_status();
//                                int side4RightStatus = piy.getSide4_right_status();
//                                int side4UpStatus = piy.getSide4_up_status();
//                                int side4DownStatus = piy.getSide4_down_status();
//
//                                int juncHr = piy.getJuncHr();
//                                int juncMin = piy.getJuncMin();
//                                int juncDat = piy.getJuncDate();
//                                int juncMonth = piy.getJuncMonth();
//                                int juncYear = piy.getJuncYear();
                               
                                int functionNo = this.yatayatThanaInfoList.getFunction_no();
                    int junction_id = this.yatayatThanaInfoList.getJunction_id();
                    int program_version_no = this.yatayatThanaInfoList.getProgram_version_no();
                    int fileNo = this.yatayatThanaInfoList.getFileNo();
                    int activity = this.yatayatThanaInfoList.getActivity();
                    int side_no = this.yatayatThanaInfoList.getSide_no();
                    int plan_no = this.yatayatThanaInfoList.getPlan_no();
                    String mode = this.yatayatThanaInfoList.getMode();
                    String junctionName = this.yatayatThanaInfoList.getJunction_name();
                    String sideName = this.yatayatThanaInfoList.getSideName();

                    String onTime = this.yatayatThanaInfoList.getOnTime();
                    String offTime = this.yatayatThanaInfoList.getOffTime();

                    String side1Name = this.yatayatThanaInfoList.getSide1Name();
                    String side2Name = this.yatayatThanaInfoList.getSide2Name();
                    String side3Name = this.yatayatThanaInfoList.getSide3Name();
                    String side4Name = this.yatayatThanaInfoList.getSide4Name();
                    String side5Name = this.yatayatThanaInfoList.getSide5Name();

                    int side1Time = this.yatayatThanaInfoList.getSide1_time();
                    int side2Time = this.yatayatThanaInfoList.getSide2_time();
                    int side3Time = this.yatayatThanaInfoList.getSide3_time();
                    int side4Time = this.yatayatThanaInfoList.getSide4_time();
                    int side1LeftStatus = this.yatayatThanaInfoList.getSide1_left_status();
                    int side1RightStatus = this.yatayatThanaInfoList.getSide1_right_status();
                    int side1UpStatus = this.yatayatThanaInfoList.getSide1_up_status();
                    int side1DownStatus = this.yatayatThanaInfoList.getSide1_down_status();
                    int side2LeftStatus = this.yatayatThanaInfoList.getSide2_left_status();
                    int side2RightStatus = this.yatayatThanaInfoList.getSide2_right_status();
                    int side2UpStatus = this.yatayatThanaInfoList.getSide2_up_status();
                    int side2DownStatus = this.yatayatThanaInfoList.getSide2_down_status();
                    int side3LeftStatus = this.yatayatThanaInfoList.getSide3_left_status();
                    int side3RightStatus = this.yatayatThanaInfoList.getSide3_right_status();
                    int side3UpStatus = this.yatayatThanaInfoList.getSide3_up_status();
                    int side3DownStatus = this.yatayatThanaInfoList.getSide3_down_status();
                    int side4LeftStatus = this.yatayatThanaInfoList.getSide4_left_status();
                    int side4RightStatus = this.yatayatThanaInfoList.getSide4_right_status();
                    int side4UpStatus = this.yatayatThanaInfoList.getSide4_up_status();
                    int side4DownStatus = this.yatayatThanaInfoList.getSide4_down_status();

                    int juncHr = this.yatayatThanaInfoList.getJuncHr();
                    int juncMin = this.yatayatThanaInfoList.getJuncMin();
                    int juncDat = this.yatayatThanaInfoList.getJuncDate();
                    int juncMonth = this.yatayatThanaInfoList.getJuncMonth();
                    int juncYear = this.yatayatThanaInfoList.getJuncYear();


                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        }
               //     }
                }
                // end for yatayat
                // start for katanga

                if (current_junction_id == 4) {
                    if (mp.containsKey("katangaInfoList")) {

                        if (mp.get("katangaInfoList") != null) {

                            PlanInfo pik = mp.get("katangaInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {

                                int functionNo = pik.getFunction_no();
                                int junction_id = pik.getJunction_id();
                                int program_version_no = pik.getProgram_version_no();
                                int fileNo = pik.getFileNo();
                                int activity = pik.getActivity();
                                int side_no = pik.getSide_no();
                                int plan_no = pik.getPlan_no();
                                String mode = pik.getMode();
                                String junctionName = pik.getJunction_name();
                                String sideName = pik.getSideName();

                                String onTime = pik.getOnTime();
                                String offTime = pik.getOffTime();

                                String side1Name = pik.getSide1Name();
                                String side2Name = pik.getSide2Name();
                                String side3Name = pik.getSide3Name();
                                String side4Name = pik.getSide4Name();
                                String side5Name = pik.getSide5Name();

                                int side1Time = pik.getSide1_time();
                                int side2Time = pik.getSide2_time();
                                int side3Time = pik.getSide3_time();
                                int side4Time = pik.getSide4_time();
                                int side1LeftStatus = pik.getSide1_left_status();
                                int side1RightStatus = pik.getSide1_right_status();
                                int side1UpStatus = pik.getSide1_up_status();
                                int side1DownStatus = pik.getSide1_down_status();
                                int side2LeftStatus = pik.getSide2_left_status();
                                int side2RightStatus = pik.getSide2_right_status();
                                int side2UpStatus = pik.getSide2_up_status();
                                int side2DownStatus = pik.getSide2_down_status();
                                int side3LeftStatus = pik.getSide3_left_status();
                                int side3RightStatus = pik.getSide3_right_status();
                                int side3UpStatus = pik.getSide3_up_status();
                                int side3DownStatus = pik.getSide3_down_status();
                                int side4LeftStatus = pik.getSide4_left_status();
                                int side4RightStatus = pik.getSide4_right_status();
                                int side4UpStatus = pik.getSide4_up_status();
                                int side4DownStatus = pik.getSide4_down_status();

                                int juncHr = pik.getJuncHr();
                                int juncMin = pik.getJuncMin();
                                int juncDat = pik.getJuncDate();
                                int juncMonth = pik.getJuncMonth();
                                int juncYear = pik.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        } else {
                            out.println("");
                            out.flush();
                        }
                    }

                }

                // end for katanga
                // start for baldeobag
                if (current_junction_id == 5) {

                    if (mp.containsKey("baldeobagInfoList")) {

                        if (mp.get("baldeobagInfoList") != null) {

                            PlanInfo pib = mp.get("baldeobagInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {
                                int functionNo = pib.getFunction_no();
                                int junction_id = pib.getJunction_id();
                                int program_version_no = pib.getProgram_version_no();
                                int fileNo = pib.getFileNo();
                                int activity = pib.getActivity();
                                int side_no = pib.getSide_no();
                                int plan_no = pib.getPlan_no();
                                String mode = pib.getMode();
                                String junctionName = pib.getJunction_name();
                                String sideName = pib.getSideName();

                                String onTime = pib.getOnTime();
                                String offTime = pib.getOffTime();

                                String side1Name = pib.getSide1Name();
                                String side2Name = pib.getSide2Name();
                                String side3Name = pib.getSide3Name();
                                String side4Name = pib.getSide4Name();
                                String side5Name = pib.getSide5Name();

                                int side1Time = pib.getSide1_time();
                                int side2Time = pib.getSide2_time();
                                int side3Time = pib.getSide3_time();
                                int side4Time = pib.getSide4_time();
                                int side1LeftStatus = pib.getSide1_left_status();
                                int side1RightStatus = pib.getSide1_right_status();
                                int side1UpStatus = pib.getSide1_up_status();
                                int side1DownStatus = pib.getSide1_down_status();
                                int side2LeftStatus = pib.getSide2_left_status();
                                int side2RightStatus = pib.getSide2_right_status();
                                int side2UpStatus = pib.getSide2_up_status();
                                int side2DownStatus = pib.getSide2_down_status();
                                int side3LeftStatus = pib.getSide3_left_status();
                                int side3RightStatus = pib.getSide3_right_status();
                                int side3UpStatus = pib.getSide3_up_status();
                                int side3DownStatus = pib.getSide3_down_status();
                                int side4LeftStatus = pib.getSide4_left_status();
                                int side4RightStatus = pib.getSide4_right_status();
                                int side4UpStatus = pib.getSide4_up_status();
                                int side4DownStatus = pib.getSide4_down_status();

                                int juncHr = pib.getJuncHr();
                                int juncMin = pib.getJuncMin();
                                int juncDat = pib.getJuncDate();
                                int juncMonth = pib.getJuncMonth();
                                int juncYear = pib.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        } else {
                            out.println("");
                            out.flush();
                        }
                    }

                }

                // end for baldeobag
                // start for deendayal
                if (current_junction_id == 6) {
                    if (mp.containsKey("deendayalChowkInfoList")) {

                        if (mp.get("deendayalChowkInfoList") != null) {

                            PlanInfo pid = mp.get("deendayalChowkInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {

                                int functionNo = pid.getFunction_no();
                                int junction_id = pid.getJunction_id();
                                int program_version_no = pid.getProgram_version_no();
                                int fileNo = pid.getFileNo();
                                int activity = pid.getActivity();
                                int side_no = pid.getSide_no();
                                int plan_no = pid.getPlan_no();
                                String mode = pid.getMode();
                                String junctionName = pid.getJunction_name();
                                String sideName = pid.getSideName();

                                String onTime = pid.getOnTime();
                                String offTime = pid.getOffTime();

                                String side1Name = pid.getSide1Name();
                                String side2Name = pid.getSide2Name();
                                String side3Name = pid.getSide3Name();
                                String side4Name = pid.getSide4Name();
                                String side5Name = pid.getSide5Name();

                                int side1Time = pid.getSide1_time();
                                int side2Time = pid.getSide2_time();
                                int side3Time = pid.getSide3_time();
                                int side4Time = pid.getSide4_time();
                                int side1LeftStatus = pid.getSide1_left_status();
                                int side1RightStatus = pid.getSide1_right_status();
                                int side1UpStatus = pid.getSide1_up_status();
                                int side1DownStatus = pid.getSide1_down_status();
                                int side2LeftStatus = pid.getSide2_left_status();
                                int side2RightStatus = pid.getSide2_right_status();
                                int side2UpStatus = pid.getSide2_up_status();
                                int side2DownStatus = pid.getSide2_down_status();
                                int side3LeftStatus = pid.getSide3_left_status();
                                int side3RightStatus = pid.getSide3_right_status();
                                int side3UpStatus = pid.getSide3_up_status();
                                int side3DownStatus = pid.getSide3_down_status();
                                int side4LeftStatus = pid.getSide4_left_status();
                                int side4RightStatus = pid.getSide4_right_status();
                                int side4UpStatus = pid.getSide4_up_status();
                                int side4DownStatus = pid.getSide4_down_status();

                                int juncHr = pid.getJuncHr();
                                int juncMin = pid.getJuncMin();
                                int juncDat = pid.getJuncDate();
                                int juncMonth = pid.getJuncMonth();
                                int juncYear = pid.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        } else {
                            out.println("");
                            out.flush();
                        }
                    }
                }

                // end for deendayal
                // start for high court
                if (current_junction_id == 7) {
                    if (mp.containsKey("highCourtInfoList")) {

                        if (mp.get("highCourtInfoList") != null) {

                            PlanInfo pih = mp.get("highCourtInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {

                                int functionNo = pih.getFunction_no();
                                int junction_id = pih.getJunction_id();
                                int program_version_no = pih.getProgram_version_no();
                                int fileNo = pih.getFileNo();
                                int activity = pih.getActivity();
                                int side_no = pih.getSide_no();
                                int plan_no = pih.getPlan_no();
                                String mode = pih.getMode();
                                String junctionName = pih.getJunction_name();
                                String sideName = pih.getSideName();

                                String onTime = pih.getOnTime();
                                String offTime = pih.getOffTime();

                                String side1Name = pih.getSide1Name();
                                String side2Name = pih.getSide2Name();
                                String side3Name = pih.getSide3Name();
                                String side4Name = pih.getSide4Name();
                                String side5Name = pih.getSide5Name();

                                int side1Time = pih.getSide1_time();
                                int side2Time = pih.getSide2_time();
                                int side3Time = pih.getSide3_time();
                                int side4Time = pih.getSide4_time();
                                int side1LeftStatus = pih.getSide1_left_status();
                                int side1RightStatus = pih.getSide1_right_status();
                                int side1UpStatus = pih.getSide1_up_status();
                                int side1DownStatus = pih.getSide1_down_status();
                                int side2LeftStatus = pih.getSide2_left_status();
                                int side2RightStatus = pih.getSide2_right_status();
                                int side2UpStatus = pih.getSide2_up_status();
                                int side2DownStatus = pih.getSide2_down_status();
                                int side3LeftStatus = pih.getSide3_left_status();
                                int side3RightStatus = pih.getSide3_right_status();
                                int side3UpStatus = pih.getSide3_up_status();
                                int side3DownStatus = pih.getSide3_down_status();
                                int side4LeftStatus = pih.getSide4_left_status();
                                int side4RightStatus = pih.getSide4_right_status();
                                int side4UpStatus = pih.getSide4_up_status();
                                int side4DownStatus = pih.getSide4_down_status();

                                int juncHr = pih.getJuncHr();
                                int juncMin = pih.getJuncMin();
                                int juncDat = pih.getJuncDate();
                                int juncMonth = pih.getJuncMonth();
                                int juncYear = pih.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        } else {
                            out.println("");
                        }
                    }
                }
                // end for high court
                // strat for bloom chowk

                if (current_junction_id == 8) {

                    if (mp.containsKey("bloomChowkInfoList")) {

                        if (mp.get("bloomChowkInfoList") != null) {

                            PlanInfo pib = mp.get("bloomChowkInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {
                                int functionNo = pib.getFunction_no();
                                int junction_id = pib.getJunction_id();
                                int program_version_no = pib.getProgram_version_no();
                                int fileNo = pib.getFileNo();
                                int activity = pib.getActivity();
                                int side_no = pib.getSide_no();
                                int plan_no = pib.getPlan_no();
                                String mode = pib.getMode();
                                String junctionName = pib.getJunction_name();
                                String sideName = pib.getSideName();

                                String onTime = pib.getOnTime();
                                String offTime = pib.getOffTime();

                                String side1Name = pib.getSide1Name();
                                String side2Name = pib.getSide2Name();
                                String side3Name = pib.getSide3Name();
                                String side4Name = pib.getSide4Name();
                                String side5Name = pib.getSide5Name();

                                int side1Time = pib.getSide1_time();
                                int side2Time = pib.getSide2_time();
                                int side3Time = pib.getSide3_time();
                                int side4Time = pib.getSide4_time();
                                int side1LeftStatus = pib.getSide1_left_status();
                                int side1RightStatus = pib.getSide1_right_status();
                                int side1UpStatus = pib.getSide1_up_status();
                                int side1DownStatus = pib.getSide1_down_status();
                                int side2LeftStatus = pib.getSide2_left_status();
                                int side2RightStatus = pib.getSide2_right_status();
                                int side2UpStatus = pib.getSide2_up_status();
                                int side2DownStatus = pib.getSide2_down_status();
                                int side3LeftStatus = pib.getSide3_left_status();
                                int side3RightStatus = pib.getSide3_right_status();
                                int side3UpStatus = pib.getSide3_up_status();
                                int side3DownStatus = pib.getSide3_down_status();
                                int side4LeftStatus = pib.getSide4_left_status();
                                int side4RightStatus = pib.getSide4_right_status();
                                int side4UpStatus = pib.getSide4_up_status();
                                int side4DownStatus = pib.getSide4_down_status();

                                int juncHr = pib.getJuncHr();
                                int juncMin = pib.getJuncMin();
                                int juncDat = pib.getJuncDate();
                                int juncMonth = pib.getJuncMonth();
                                int juncYear = pib.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        }
                    } else {
                        out.println("");
                        out.flush();
                    }
                }
                // end for bloom chowk
                // start for madan mahal

                if (current_junction_id == 12) {

                    if (mp.containsKey("madanMahalInfoList")) {

                        if (mp.get("madanMahalInfoList") != null) {

                            PlanInfo pim = mp.get("madanMahalInfoList");
                            boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                            if ((responseFromModemForRefresh == true)) {
                                int functionNo = pim.getFunction_no();
                                int junction_id = pim.getJunction_id();
                                int program_version_no = pim.getProgram_version_no();
                                int fileNo = pim.getFileNo();
                                int activity = pim.getActivity();
                                int side_no = pim.getSide_no();
                                int plan_no = pim.getPlan_no();
                                String mode = pim.getMode();
                                String junctionName = pim.getJunction_name();
                                String sideName = pim.getSideName();

                                String onTime = pim.getOnTime();
                                String offTime = pim.getOffTime();

                                String side1Name = pim.getSide1Name();
                                String side2Name = pim.getSide2Name();
                                String side3Name = pim.getSide3Name();
                                String side4Name = pim.getSide4Name();
                                String side5Name = pim.getSide5Name();

                                int side1Time = pim.getSide1_time();
                                int side2Time = pim.getSide2_time();
                                int side3Time = pim.getSide3_time();
                                int side4Time = pim.getSide4_time();
                                int side1LeftStatus = pim.getSide1_left_status();
                                int side1RightStatus = pim.getSide1_right_status();
                                int side1UpStatus = pim.getSide1_up_status();
                                int side1DownStatus = pim.getSide1_down_status();
                                int side2LeftStatus = pim.getSide2_left_status();
                                int side2RightStatus = pim.getSide2_right_status();
                                int side2UpStatus = pim.getSide2_up_status();
                                int side2DownStatus = pim.getSide2_down_status();
                                int side3LeftStatus = pim.getSide3_left_status();
                                int side3RightStatus = pim.getSide3_right_status();
                                int side3UpStatus = pim.getSide3_up_status();
                                int side3DownStatus = pim.getSide3_down_status();
                                int side4LeftStatus = pim.getSide4_left_status();
                                int side4RightStatus = pim.getSide4_right_status();
                                int side4UpStatus = pim.getSide4_up_status();
                                int side4DownStatus = pim.getSide4_down_status();

                                int juncHr = pim.getJuncHr();
                                int juncMin = pim.getJuncMin();
                                int juncDat = pim.getJuncDate();
                                int juncMonth = pim.getJuncMonth();
                                int juncYear = pim.getJuncYear();

                                String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no + "#$" + "fileNo=" + fileNo
                                        + "#$" + "functionNo=" + functionNo + "#$" + "activity=" + activity
                                        + "#$" + "side_no=" + side_no + "#$" + "plan_no=" + plan_no
                                        + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTime=" + onTime + "#$" + "offTime=" + offTime
                                        + "#$" + "mode=" + mode + "#$" + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name
                                        + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time
                                        + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus
                                        + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus
                                        + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus
                                        + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus
                                        + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus
                                        + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus
                                        + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDat=" + juncDat + "#$" + "juncMonth=" + juncMonth
                                        + "#$" + "juncYear=" + juncYear;
                                jsonObj.put("junction_id", junction_id + "");
                                jsonObj.put("program_version_no", program_version_no + "");
                                jsonObj.put("fileNo", fileNo + "");
                                jsonObj.put("functionNo", functionNo + "");
                                jsonObj.put("activity", activity + "");
                                jsonObj.put("side_no", side_no + "");
                                jsonObj.put("plan_no", plan_no + "");
                                jsonObj.put("junctionName", junctionName);
                                jsonObj.put("sideName", sideName);
                                jsonObj.put("onTime", onTime);
                                jsonObj.put("offTime", offTime);
                                jsonObj.put("mode", mode);
                                jsonObj.put("side1Name", side1Name);
                                jsonObj.put("side2Name", side2Name);
                                jsonObj.put("side3Name", side3Name);
                                jsonObj.put("side4Name", side4Name);
                                jsonObj.put("side5Name", side5Name);
                                jsonObj.put("side1Time", side1Time + "");
                                jsonObj.put("side2Time", side2Time + "");
                                jsonObj.put("side3Time", side3Time + "");
                                jsonObj.put("side4Time", side4Time + "");
                                jsonObj.put("side1LeftStatus", side1LeftStatus + "");
                                jsonObj.put("side1RightStatus", side1RightStatus + "");
                                jsonObj.put("side1UpStatus", side1UpStatus + "");
                                jsonObj.put("side1DownStatus", side1DownStatus + "");
                                jsonObj.put("side2LeftStatus", side2LeftStatus + "");
                                jsonObj.put("side2RightStatus", side2RightStatus + "");
                                jsonObj.put("side2UpStatus", side2UpStatus + "");
                                jsonObj.put("side2DownStatus", side2DownStatus + "");
                                jsonObj.put("side3LeftStatus", side3LeftStatus + "");
                                jsonObj.put("side3RightStatus", side3RightStatus + "");
                                jsonObj.put("side3UpStatus", side3UpStatus + "");
                                jsonObj.put("side3DownStatus", side3DownStatus + "");
                                jsonObj.put("side4LeftStatus", side4LeftStatus + "");
                                jsonObj.put("side4RightStatus", side4RightStatus + "");
                                jsonObj.put("side4UpStatus", side4UpStatus + "");
                                jsonObj.put("side4DownStatus", side4DownStatus + "");
                                jsonObj.put("juncHr", juncHr + "");
                                jsonObj.put("juncMin", juncMin + "");
                                jsonObj.put("juncDat", juncDat + "");
                                jsonObj.put("juncMonth", juncMonth + "");
                                jsonObj.put("juncYear", juncYear + "");
                                out.println(jsonObj);
                                out.flush();
                            }
                        }
                    }
                }

                // end for madanmahal
            }
            //  for testing

            if (task.equals("getLatestMapSignal")) {
                this.planInfoList = ((PlanInfo) ctx.getAttribute("planInfolist"));
                if (this.planInfoList != null) {
                    int functionNo = this.planInfoList.getFunction_no();
                    int junction_id = this.planInfoList.getJunction_id();
                    int program_version_no = this.planInfoList.getProgram_version_no();
                    int fileNo = this.planInfoList.getFileNo();
                    int activity = this.planInfoList.getActivity();
                    int side_no = this.planInfoList.getSide_no();
                    int plan_no = this.planInfoList.getPlan_no();
                    String mode = this.planInfoList.getMode();
                    String junctionName = this.planInfoList.getJunction_name();
                    String sideName = this.planInfoList.getSideName();

                    String onTime = this.planInfoList.getOnTime();
                    String offTime = this.planInfoList.getOffTime();

                    String side1Name = this.planInfoList.getSide1Name();
                    String side2Name = this.planInfoList.getSide2Name();
                    String side3Name = this.planInfoList.getSide3Name();
                    String side4Name = this.planInfoList.getSide4Name();
                    String side5Name = this.planInfoList.getSide5Name();

                    int side1Time = this.planInfoList.getSide1_time();
                    int side2Time = this.planInfoList.getSide2_time();
                    int side3Time = this.planInfoList.getSide3_time();
                    int side4Time = this.planInfoList.getSide4_time();
                    int side1LeftStatus = this.planInfoList.getSide1_left_status();
                    int side1RightStatus = this.planInfoList.getSide1_right_status();
                    int side1UpStatus = this.planInfoList.getSide1_up_status();
                    int side1DownStatus = this.planInfoList.getSide1_down_status();
                    int side2LeftStatus = this.planInfoList.getSide2_left_status();
                    int side2RightStatus = this.planInfoList.getSide2_right_status();
                    int side2UpStatus = this.planInfoList.getSide2_up_status();
                    int side2DownStatus = this.planInfoList.getSide2_down_status();
                    int side3LeftStatus = this.planInfoList.getSide3_left_status();
                    int side3RightStatus = this.planInfoList.getSide3_right_status();
                    int side3UpStatus = this.planInfoList.getSide3_up_status();
                    int side3DownStatus = this.planInfoList.getSide3_down_status();
                    int side4LeftStatus = this.planInfoList.getSide4_left_status();
                    int side4RightStatus = this.planInfoList.getSide4_right_status();
                    int side4UpStatus = this.planInfoList.getSide4_up_status();
                    int side4DownStatus = this.planInfoList.getSide4_down_status();

                    int juncHr = this.planInfoList.getJuncHr();
                    int juncMin = this.planInfoList.getJuncMin();
                    int juncDat = this.planInfoList.getJuncDate();
                    int juncMonth = this.planInfoList.getJuncMonth();
                    int juncYear = this.planInfoList.getJuncYear();

                    //  String map_image= clientResponderModel.getMapImage(junction_id);
                    request.setAttribute("juncHr", juncHr);
                    //  request.setAttribute("map_image", map_image);
                    request.setAttribute("juncMin", juncMin);
                    request.setAttribute("juncDat", juncDat);
                    request.setAttribute("juncMonth", juncMonth);
                    request.setAttribute("juncYear", juncYear);

                    request.setAttribute("functionNo", Integer.valueOf(functionNo));
                    request.setAttribute("junctionId", Integer.valueOf(junction_id));
                    request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                    request.setAttribute("fileNo", Integer.valueOf(fileNo));
                    request.setAttribute("junctionName", junctionName);
                    request.setAttribute("activity", activity);
                    request.setAttribute("sideNo", Integer.valueOf(side_no));
                    request.setAttribute("sideName", sideName);
                    request.setAttribute("side1Name", side1Name);
                    request.setAttribute("side2Name", side2Name);
                    request.setAttribute("side3Name", side3Name);
                    request.setAttribute("side4Name", side4Name);
                    request.setAttribute("side5Name", side5Name);

                    request.setAttribute("onTime", onTime);
                    request.setAttribute("offTime", offTime);
                    request.setAttribute("plan_no", plan_no);
                    request.setAttribute("mode", mode);

                    request.setAttribute("side1Time", Integer.valueOf(side1Time));
                    request.setAttribute("side2Time", Integer.valueOf(side2Time));
                    request.setAttribute("side3Time", Integer.valueOf(side3Time));
                    request.setAttribute("side4Time", Integer.valueOf(side4Time));
                    request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                    request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                    request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                    request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                    request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                    request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                    request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                    request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                    request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                    request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                    request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                    request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                    request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                    request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                    request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                    request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                    request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));

                    request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                    return;
                } else {
                    String jSON_format = "Oops... No junction is active right now.";

                    request.setAttribute("message", jSON_format);
                    request.getRequestDispatcher("errorView").forward(request, response);
                    return;
                }

            }

            if (task.equals("showAllStatus")) {
                List<History> List = clientResponderModel.showDataBean();
                int cordinateLength = List.size();
                request.setAttribute("cordinateLength", cordinateLength);
                request.setAttribute("CoordinatesList", List);
                request.getRequestDispatcher("/view/general/allStatusMapWindow.jsp").forward(request, response);
                return;
            } // end of testing
            else {
              //  this.planInfoList = ((PlanInfo) ctx.getAttribute("planInfolist"));
                Map<String, PlanInfo> mp = new HashMap();
//                mp.put("planInfolist", (PlanInfo) ctx.getAttribute("planInfolist"));
//                mp.put("teenPattiInfoList", (PlanInfo) ctx.getAttribute("teenPattiInfoList"));
//                mp.put("ranitalInfoList", (PlanInfo) ctx.getAttribute("ranitalInfoList"));
//                mp.put("damohNakaInfoList", (PlanInfo) ctx.getAttribute("damohNakaInfoList"));
//                mp.put("yatayatThanaInfoList", (yatayatThanaInfoList) ctx.getAttribute("yatayatThanaInfoList"));
//                mp.put("katangaInfoList", (PlanInfo) ctx.getAttribute("katangaInfoList"));
//                mp.put("baldeobagInfoList", (PlanInfo) ctx.getAttribute("baldeobagInfoList"));
//                mp.put("deendayalChowkInfoList", (PlanInfo) ctx.getAttribute("deendayalChowkInfoList"));
//                mp.put("highCourtInfoList", (PlanInfo) ctx.getAttribute("highCourtInfoList"));
//                mp.put("bloomChowkInfoList", (PlanInfo) ctx.getAttribute("bloomChowkInfoList"));
//                mp.put("madanMahalInfoList", (PlanInfo) ctx.getAttribute("madanMahalInfoList"));
//                
                
                this.planInfoList = ((PlanInfo) ctx.getAttribute("planInfolist"));
                this.teenPattiInfoList = ((TeenPatti_Info) ctx.getAttribute("teenPattiInfoList"));
                this.ranitalInfoList = ((RanitalInfo) ctx.getAttribute("ranitalInfoList"));
                this.damohNakaInfoList = ((DamohNakaInfo) ctx.getAttribute("damohNakaInfoList"));
                this.yatayatThanaInfoList = ((YatayatThanaInfo) ctx.getAttribute("yatayatThanaInfoList"));
                this.katangaInfoList = ((KatangaInfo) ctx.getAttribute("katangaInfoList"));
                this.baldeobagInfoList = ((BaldeobagInfo) ctx.getAttribute("baldeobagInfoList"));
                this.deendayalChowkInfoList = ((DeendayalChowkInfo) ctx.getAttribute("deendayalChowkInfoList"));
                this.highCourtInfoList = ((HighCourtInfo) ctx.getAttribute("highCourtInfoList"));
                this.bloomChowkInfoList = ((BloomChowkInfo) ctx.getAttribute("bloomChowkInfoList"));
                this.madanMahalInfoList = ((MadanMahalInfo) ctx.getAttribute("madanMahalInfoList"));
                this.gohalPurInfoList = ((GohalPurInfo) ctx.getAttribute("gohalPurInfoList"));
                this.bandariyaTirahaInfoList = ((BandariyaTirahaInfo) ctx.getAttribute("bandariyaTirahaInfoList"));
                //int junction_id1 = this.planInfoList.getJunction_id();
                if (a == 11) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("planInfolist")) {

                        if (mp.get("planInfolist") != null) {

                            PlanInfo pir = mp.get("ranitalInfoList");

                            int functionNo = pir.getFunction_no();
                            int junction_id = pir.getJunction_id();
                            int program_version_no = pir.getProgram_version_no();
                            int fileNo = pir.getFileNo();
                            int activity = pir.getActivity();
                            int side_no = pir.getSide_no();
                            int plan_no = pir.getPlan_no();
                            String mode = pir.getMode();
                            String junctionName = pir.getJunction_name();
                            String sideName = pir.getSideName();

                            String onTime = pir.getOnTime();
                            String offTime = pir.getOffTime();

                            String side1Name = pir.getSide1Name();
                            String side2Name = pir.getSide2Name();
                            String side3Name = pir.getSide3Name();
                            String side4Name = pir.getSide4Name();
                            String side5Name = pir.getSide5Name();

                            int side1Time = pir.getSide1_time();
                            int side2Time = pir.getSide2_time();
                            int side3Time = pir.getSide3_time();
                            int side4Time = pir.getSide4_time();
                            int side1LeftStatus = pir.getSide1_left_status();
                            int side1RightStatus = pir.getSide1_right_status();
                            int side1UpStatus = pir.getSide1_up_status();
                            int side1DownStatus = pir.getSide1_down_status();
                            int side2LeftStatus = pir.getSide2_left_status();
                            int side2RightStatus = pir.getSide2_right_status();
                            int side2UpStatus = pir.getSide2_up_status();
                            int side2DownStatus = pir.getSide2_down_status();
                            int side3LeftStatus = pir.getSide3_left_status();
                            int side3RightStatus = pir.getSide3_right_status();
                            int side3UpStatus = pir.getSide3_up_status();
                            int side3DownStatus = pir.getSide3_down_status();
                            int side4LeftStatus = pir.getSide4_left_status();
                            int side4RightStatus = pir.getSide4_right_status();
                            int side4UpStatus = pir.getSide4_up_status();
                            int side4DownStatus = pir.getSide4_down_status();

                            int juncHr = pir.getJuncHr();
                            int juncMin = pir.getJuncMin();
                            int juncDat = pir.getJuncDate();
                            int juncMonth = pir.getJuncMonth();
                            int juncYear = pir.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));

                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }
                if (a == 2) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("damohNakaInfoList")) {

                        if (mp.get("damohNakaInfoList") != null) {

                            PlanInfo pid = mp.get("damohNakaInfoList");
                            int functionNo = pid.getFunction_no();
                            int junction_id = pid.getJunction_id();
                            int program_version_no = pid.getProgram_version_no();
                            int fileNo = pid.getFileNo();
                            int activity = pid.getActivity();
                            int side_no = pid.getSide_no();
                            int plan_no = pid.getPlan_no();
                            String mode = pid.getMode();
                            String junctionName = pid.getJunction_name();
                            String sideName = pid.getSideName();

                            String onTime = pid.getOnTime();
                            String offTime = pid.getOffTime();

                            String side1Name = pid.getSide1Name();
                            String side2Name = pid.getSide2Name();
                            String side3Name = pid.getSide3Name();
                            String side4Name = pid.getSide4Name();
                            String side5Name = pid.getSide5Name();

                            int side1Time = pid.getSide1_time();
                            int side2Time = pid.getSide2_time();
                            int side3Time = pid.getSide3_time();
                            int side4Time = pid.getSide4_time();
                            int side1LeftStatus = pid.getSide1_left_status();
                            int side1RightStatus = pid.getSide1_right_status();
                            int side1UpStatus = pid.getSide1_up_status();
                            int side1DownStatus = pid.getSide1_down_status();
                            int side2LeftStatus = pid.getSide2_left_status();
                            int side2RightStatus = pid.getSide2_right_status();
                            int side2UpStatus = pid.getSide2_up_status();
                            int side2DownStatus = pid.getSide2_down_status();
                            int side3LeftStatus = pid.getSide3_left_status();
                            int side3RightStatus = pid.getSide3_right_status();
                            int side3UpStatus = pid.getSide3_up_status();
                            int side3DownStatus = pid.getSide3_down_status();
                            int side4LeftStatus = pid.getSide4_left_status();
                            int side4RightStatus = pid.getSide4_right_status();
                            int side4UpStatus = pid.getSide4_up_status();
                            int side4DownStatus = pid.getSide4_down_status();

                            int juncHr = pid.getJuncHr();
                            int juncMin = pid.getJuncMin();
                            int juncDat = pid.getJuncDate();
                            int juncMonth = pid.getJuncMonth();
                            int juncYear = pid.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }

                if (a == 13) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("teenPattiInfoList")) {

                        if (mp.get("teenPattiInfoList") != null) {

                            PlanInfo pit = mp.get("teenPattiInfoList");
                            int functionNo = pit.getFunction_no();
                            int junction_id = pit.getJunction_id();
                            int program_version_no = pit.getProgram_version_no();
                            int fileNo = pit.getFileNo();
                            int activity = pit.getActivity();
                            int side_no = pit.getSide_no();
                            int plan_no = pit.getPlan_no();
                            String mode = pit.getMode();
                            String junctionName = pit.getJunction_name();
                            String sideName = pit.getSideName();

                            String onTime = pit.getOnTime();
                            String offTime = pit.getOffTime();

                            String side1Name = pit.getSide1Name();
                            String side2Name = pit.getSide2Name();
                            String side3Name = pit.getSide3Name();
                            String side4Name = pit.getSide4Name();
                            String side5Name = pit.getSide5Name();

                            int side1Time = pit.getSide1_time();
                            int side2Time = pit.getSide2_time();
                            int side3Time = pit.getSide3_time();
                            int side4Time = pit.getSide4_time();
                            int side1LeftStatus = pit.getSide1_left_status();
                            int side1RightStatus = pit.getSide1_right_status();
                            int side1UpStatus = pit.getSide1_up_status();
                            int side1DownStatus = pit.getSide1_down_status();
                            int side2LeftStatus = pit.getSide2_left_status();
                            int side2RightStatus = pit.getSide2_right_status();
                            int side2UpStatus = pit.getSide2_up_status();
                            int side2DownStatus = pit.getSide2_down_status();
                            int side3LeftStatus = pit.getSide3_left_status();
                            int side3RightStatus = pit.getSide3_right_status();
                            int side3UpStatus = pit.getSide3_up_status();
                            int side3DownStatus = pit.getSide3_down_status();
                            int side4LeftStatus = pit.getSide4_left_status();
                            int side4RightStatus = pit.getSide4_right_status();
                            int side4UpStatus = pit.getSide4_up_status();
                            int side4DownStatus = pit.getSide4_down_status();

                            int juncHr = pit.getJuncHr();
                            int juncMin = pit.getJuncMin();
                            int juncDat = pit.getJuncDate();
                            int juncMonth = pit.getJuncMonth();
                            int juncYear = pit.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }

                if (a == 1) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                  //  if (mp.containsKey("yatayatThanaInfoList")) {

//                        if (mp.get("yatayatThanaInfoList") != null) {
                             if (this.yatayatThanaInfoList != null) {
//                            PlanInfo piy = mp.get("yatayatThanaInfoList");
//                            int functionNo = piy.getFunction_no();
//                            int junction_id = piy.getJunction_id();
//                            int program_version_no = piy.getProgram_version_no();
//                            int fileNo = piy.getFileNo();
//                            int activity = piy.getActivity();
//                            int side_no = piy.getSide_no();
//                            int plan_no = piy.getPlan_no();
//                            String mode = piy.getMode();
//                            String junctionName = piy.getJunction_name();
//                            String sideName = piy.getSideName();
//
//                            String onTime = piy.getOnTime();
//                            String offTime = piy.getOffTime();
//
//                            String side1Name = piy.getSide1Name();
//                            String side2Name = piy.getSide2Name();
//                            String side3Name = piy.getSide3Name();
//                            String side4Name = piy.getSide4Name();
//                            String side5Name = piy.getSide5Name();
//
//                            int side1Time = piy.getSide1_time();
//                            int side2Time = piy.getSide2_time();
//                            int side3Time = piy.getSide3_time();
//                            int side4Time = piy.getSide4_time();
//                            int side1LeftStatus = piy.getSide1_left_status();
//                            int side1RightStatus = piy.getSide1_right_status();
//                            int side1UpStatus = piy.getSide1_up_status();
//                            int side1DownStatus = piy.getSide1_down_status();
//                            int side2LeftStatus = piy.getSide2_left_status();
//                            int side2RightStatus = piy.getSide2_right_status();
//                            int side2UpStatus = piy.getSide2_up_status();
//                            int side2DownStatus = piy.getSide2_down_status();
//                            int side3LeftStatus = piy.getSide3_left_status();
//                            int side3RightStatus = piy.getSide3_right_status();
//                            int side3UpStatus = piy.getSide3_up_status();
//                            int side3DownStatus = piy.getSide3_down_status();
//                            int side4LeftStatus = piy.getSide4_left_status();
//                            int side4RightStatus = piy.getSide4_right_status();
//                            int side4UpStatus = piy.getSide4_up_status();
//                            int side4DownStatus = piy.getSide4_down_status();
//
//                            int juncHr = piy.getJuncHr();
//                            int juncMin = piy.getJuncMin();
//                            int juncDat = piy.getJuncDate();
//                            int juncMonth = piy.getJuncMonth();
//                            int juncYear = piy.getJuncYear();

                            int functionNo = this.yatayatThanaInfoList.getFunction_no();
                    int junction_id = this.yatayatThanaInfoList.getJunction_id();
                    int program_version_no = this.yatayatThanaInfoList.getProgram_version_no();
                    int fileNo = this.yatayatThanaInfoList.getFileNo();
                    int activity = this.yatayatThanaInfoList.getActivity();
                    int side_no = this.yatayatThanaInfoList.getSide_no();
                    int plan_no = this.yatayatThanaInfoList.getPlan_no();
                    String mode = this.yatayatThanaInfoList.getMode();
                    String junctionName = this.yatayatThanaInfoList.getJunction_name();
                    String sideName = this.yatayatThanaInfoList.getSideName();

                    String onTime = this.yatayatThanaInfoList.getOnTime();
                    String offTime = this.yatayatThanaInfoList.getOffTime();

                    String side1Name = this.yatayatThanaInfoList.getSide1Name();
                    String side2Name = this.yatayatThanaInfoList.getSide2Name();
                    String side3Name = this.yatayatThanaInfoList.getSide3Name();
                    String side4Name = this.yatayatThanaInfoList.getSide4Name();
                    String side5Name = this.yatayatThanaInfoList.getSide5Name();

                    int side1Time = this.yatayatThanaInfoList.getSide1_time();
                    int side2Time = this.yatayatThanaInfoList.getSide2_time();
                    int side3Time = this.yatayatThanaInfoList.getSide3_time();
                    int side4Time = this.yatayatThanaInfoList.getSide4_time();
                    int side1LeftStatus = this.yatayatThanaInfoList.getSide1_left_status();
                    int side1RightStatus = this.yatayatThanaInfoList.getSide1_right_status();
                    int side1UpStatus = this.yatayatThanaInfoList.getSide1_up_status();
                    int side1DownStatus = this.yatayatThanaInfoList.getSide1_down_status();
                    int side2LeftStatus = this.yatayatThanaInfoList.getSide2_left_status();
                    int side2RightStatus = this.yatayatThanaInfoList.getSide2_right_status();
                    int side2UpStatus = this.yatayatThanaInfoList.getSide2_up_status();
                    int side2DownStatus = this.yatayatThanaInfoList.getSide2_down_status();
                    int side3LeftStatus = this.yatayatThanaInfoList.getSide3_left_status();
                    int side3RightStatus = this.yatayatThanaInfoList.getSide3_right_status();
                    int side3UpStatus = this.yatayatThanaInfoList.getSide3_up_status();
                    int side3DownStatus = this.yatayatThanaInfoList.getSide3_down_status();
                    int side4LeftStatus = this.yatayatThanaInfoList.getSide4_left_status();
                    int side4RightStatus = this.yatayatThanaInfoList.getSide4_right_status();
                    int side4UpStatus = this.yatayatThanaInfoList.getSide4_up_status();
                    int side4DownStatus = this.yatayatThanaInfoList.getSide4_down_status();

                    int juncHr = this.yatayatThanaInfoList.getJuncHr();
                    int juncMin = this.yatayatThanaInfoList.getJuncMin();
                    int juncDat = this.yatayatThanaInfoList.getJuncDate();
                    int juncMonth = this.yatayatThanaInfoList.getJuncMonth();
                    int juncYear = this.yatayatThanaInfoList.getJuncYear();


                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                 //   }
                }
                if (a == 4) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("katangaInfoList")) {

                        if (mp.get("katangaInfoList") != null) {

                            PlanInfo pik = mp.get("katangaInfoList");
                            int functionNo = pik.getFunction_no();
                            int junction_id = pik.getJunction_id();
                            int program_version_no = pik.getProgram_version_no();
                            int fileNo = pik.getFileNo();
                            int activity = pik.getActivity();
                            int side_no = pik.getSide_no();
                            int plan_no = pik.getPlan_no();
                            String mode = pik.getMode();
                            String junctionName = pik.getJunction_name();
                            String sideName = pik.getSideName();

                            String onTime = pik.getOnTime();
                            String offTime = pik.getOffTime();

                            String side1Name = pik.getSide1Name();
                            String side2Name = pik.getSide2Name();
                            String side3Name = pik.getSide3Name();
                            String side4Name = pik.getSide4Name();
                            String side5Name = pik.getSide5Name();

                            int side1Time = pik.getSide1_time();
                            int side2Time = pik.getSide2_time();
                            int side3Time = pik.getSide3_time();
                            int side4Time = pik.getSide4_time();
                            int side1LeftStatus = pik.getSide1_left_status();
                            int side1RightStatus = pik.getSide1_right_status();
                            int side1UpStatus = pik.getSide1_up_status();
                            int side1DownStatus = pik.getSide1_down_status();
                            int side2LeftStatus = pik.getSide2_left_status();
                            int side2RightStatus = pik.getSide2_right_status();
                            int side2UpStatus = pik.getSide2_up_status();
                            int side2DownStatus = pik.getSide2_down_status();
                            int side3LeftStatus = pik.getSide3_left_status();
                            int side3RightStatus = pik.getSide3_right_status();
                            int side3UpStatus = pik.getSide3_up_status();
                            int side3DownStatus = pik.getSide3_down_status();
                            int side4LeftStatus = pik.getSide4_left_status();
                            int side4RightStatus = pik.getSide4_right_status();
                            int side4UpStatus = pik.getSide4_up_status();
                            int side4DownStatus = pik.getSide4_down_status();

                            int juncHr = pik.getJuncHr();
                            int juncMin = pik.getJuncMin();
                            int juncDat = pik.getJuncDate();
                            int juncMonth = pik.getJuncMonth();
                            int juncYear = pik.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }
                if (a == 5) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("baldeobagInfoList")) {

                        if (mp.get("baldeobagInfoList") != null) {

                            PlanInfo pib = mp.get("baldeobagInfoList");
                            int functionNo = pib.getFunction_no();
                            int junction_id = pib.getJunction_id();
                            int program_version_no = pib.getProgram_version_no();
                            int fileNo = pib.getFileNo();
                            int activity = pib.getActivity();
                            int side_no = pib.getSide_no();
                            int plan_no = pib.getPlan_no();
                            String mode = pib.getMode();
                            String junctionName = pib.getJunction_name();
                            String sideName = pib.getSideName();

                            String onTime = pib.getOnTime();
                            String offTime = pib.getOffTime();

                            String side1Name = pib.getSide1Name();
                            String side2Name = pib.getSide2Name();
                            String side3Name = pib.getSide3Name();
                            String side4Name = pib.getSide4Name();
                            String side5Name = pib.getSide5Name();

                            int side1Time = pib.getSide1_time();
                            int side2Time = pib.getSide2_time();
                            int side3Time = pib.getSide3_time();
                            int side4Time = pib.getSide4_time();
                            int side1LeftStatus = pib.getSide1_left_status();
                            int side1RightStatus = pib.getSide1_right_status();
                            int side1UpStatus = pib.getSide1_up_status();
                            int side1DownStatus = pib.getSide1_down_status();
                            int side2LeftStatus = pib.getSide2_left_status();
                            int side2RightStatus = pib.getSide2_right_status();
                            int side2UpStatus = pib.getSide2_up_status();
                            int side2DownStatus = pib.getSide2_down_status();
                            int side3LeftStatus = pib.getSide3_left_status();
                            int side3RightStatus = pib.getSide3_right_status();
                            int side3UpStatus = pib.getSide3_up_status();
                            int side3DownStatus = pib.getSide3_down_status();
                            int side4LeftStatus = pib.getSide4_left_status();
                            int side4RightStatus = pib.getSide4_right_status();
                            int side4UpStatus = pib.getSide4_up_status();
                            int side4DownStatus = pib.getSide4_down_status();

                            int juncHr = pib.getJuncHr();
                            int juncMin = pib.getJuncMin();
                            int juncDat = pib.getJuncDate();
                            int juncMonth = pib.getJuncMonth();
                            int juncYear = pib.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }
                if (a == 6) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("deendayalChowkInfoList")) {

                        if (mp.get("deendayalChowkInfoList") != null) {

                            PlanInfo pid = mp.get("deendayalChowkInfoList");
                            int functionNo = pid.getFunction_no();
                            int junction_id = pid.getJunction_id();
                            int program_version_no = pid.getProgram_version_no();
                            int fileNo = pid.getFileNo();
                            int activity = pid.getActivity();
                            int side_no = pid.getSide_no();
                            int plan_no = pid.getPlan_no();
                            String mode = pid.getMode();
                            String junctionName = pid.getJunction_name();
                            String sideName = pid.getSideName();

                            String onTime = pid.getOnTime();
                            String offTime = pid.getOffTime();

                            String side1Name = pid.getSide1Name();
                            String side2Name = pid.getSide2Name();
                            String side3Name = pid.getSide3Name();
                            String side4Name = pid.getSide4Name();
                            String side5Name = pid.getSide5Name();

                            int side1Time = pid.getSide1_time();
                            int side2Time = pid.getSide2_time();
                            int side3Time = pid.getSide3_time();
                            int side4Time = pid.getSide4_time();
                            int side1LeftStatus = pid.getSide1_left_status();
                            int side1RightStatus = pid.getSide1_right_status();
                            int side1UpStatus = pid.getSide1_up_status();
                            int side1DownStatus = pid.getSide1_down_status();
                            int side2LeftStatus = pid.getSide2_left_status();
                            int side2RightStatus = pid.getSide2_right_status();
                            int side2UpStatus = pid.getSide2_up_status();
                            int side2DownStatus = pid.getSide2_down_status();
                            int side3LeftStatus = pid.getSide3_left_status();
                            int side3RightStatus = pid.getSide3_right_status();
                            int side3UpStatus = pid.getSide3_up_status();
                            int side3DownStatus = pid.getSide3_down_status();
                            int side4LeftStatus = pid.getSide4_left_status();
                            int side4RightStatus = pid.getSide4_right_status();
                            int side4UpStatus = pid.getSide4_up_status();
                            int side4DownStatus = pid.getSide4_down_status();

                            int juncHr = pid.getJuncHr();
                            int juncMin = pid.getJuncMin();
                            int juncDat = pid.getJuncDate();
                            int juncMonth = pid.getJuncMonth();
                            int juncYear = pid.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }
                if (a == 7) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("highCourtInfoList")) {

                        if (mp.get("highCourtInfoList") != null) {

                            PlanInfo pih = mp.get("highCourtInfoList");
                            int functionNo = pih.getFunction_no();
                            int junction_id = pih.getJunction_id();
                            int program_version_no = pih.getProgram_version_no();
                            int fileNo = pih.getFileNo();
                            int activity = pih.getActivity();
                            int side_no = pih.getSide_no();
                            int plan_no = pih.getPlan_no();
                            String mode = pih.getMode();
                            String junctionName = pih.getJunction_name();
                            String sideName = pih.getSideName();

                            String onTime = pih.getOnTime();
                            String offTime = pih.getOffTime();

                            String side1Name = pih.getSide1Name();
                            String side2Name = pih.getSide2Name();
                            String side3Name = pih.getSide3Name();
                            String side4Name = pih.getSide4Name();
                            String side5Name = pih.getSide5Name();

                            int side1Time = pih.getSide1_time();
                            int side2Time = pih.getSide2_time();
                            int side3Time = pih.getSide3_time();
                            int side4Time = pih.getSide4_time();
                            int side1LeftStatus = pih.getSide1_left_status();
                            int side1RightStatus = pih.getSide1_right_status();
                            int side1UpStatus = pih.getSide1_up_status();
                            int side1DownStatus = pih.getSide1_down_status();
                            int side2LeftStatus = pih.getSide2_left_status();
                            int side2RightStatus = pih.getSide2_right_status();
                            int side2UpStatus = pih.getSide2_up_status();
                            int side2DownStatus = pih.getSide2_down_status();
                            int side3LeftStatus = pih.getSide3_left_status();
                            int side3RightStatus = pih.getSide3_right_status();
                            int side3UpStatus = pih.getSide3_up_status();
                            int side3DownStatus = pih.getSide3_down_status();
                            int side4LeftStatus = pih.getSide4_left_status();
                            int side4RightStatus = pih.getSide4_right_status();
                            int side4UpStatus = pih.getSide4_up_status();
                            int side4DownStatus = pih.getSide4_down_status();

                            int juncHr = pih.getJuncHr();
                            int juncMin = pih.getJuncMin();
                            int juncDat = pih.getJuncDate();
                            int juncMonth = pih.getJuncMonth();
                            int juncYear = pih.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }
                if (a == 8) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("bloomChowkInfoList")) {

                        if (mp.get("bloomChowkInfoList") != null) {

                            PlanInfo pib = mp.get("bloomChowkInfoList");
                            int functionNo = pib.getFunction_no();
                            int junction_id = pib.getJunction_id();
                            int program_version_no = pib.getProgram_version_no();
                            int fileNo = pib.getFileNo();
                            int activity = pib.getActivity();
                            int side_no = pib.getSide_no();
                            int plan_no = pib.getPlan_no();
                            String mode = pib.getMode();
                            String junctionName = pib.getJunction_name();
                            String sideName = pib.getSideName();

                            String onTime = pib.getOnTime();
                            String offTime = pib.getOffTime();

                            String side1Name = pib.getSide1Name();
                            String side2Name = pib.getSide2Name();
                            String side3Name = pib.getSide3Name();
                            String side4Name = pib.getSide4Name();
                            String side5Name = pib.getSide5Name();

                            int side1Time = pib.getSide1_time();
                            int side2Time = pib.getSide2_time();
                            int side3Time = pib.getSide3_time();
                            int side4Time = pib.getSide4_time();
                            int side1LeftStatus = pib.getSide1_left_status();
                            int side1RightStatus = pib.getSide1_right_status();
                            int side1UpStatus = pib.getSide1_up_status();
                            int side1DownStatus = pib.getSide1_down_status();
                            int side2LeftStatus = pib.getSide2_left_status();
                            int side2RightStatus = pib.getSide2_right_status();
                            int side2UpStatus = pib.getSide2_up_status();
                            int side2DownStatus = pib.getSide2_down_status();
                            int side3LeftStatus = pib.getSide3_left_status();
                            int side3RightStatus = pib.getSide3_right_status();
                            int side3UpStatus = pib.getSide3_up_status();
                            int side3DownStatus = pib.getSide3_down_status();
                            int side4LeftStatus = pib.getSide4_left_status();
                            int side4RightStatus = pib.getSide4_right_status();
                            int side4UpStatus = pib.getSide4_up_status();
                            int side4DownStatus = pib.getSide4_down_status();

                            int juncHr = pib.getJuncHr();
                            int juncMin = pib.getJuncMin();
                            int juncDat = pib.getJuncDate();
                            int juncMonth = pib.getJuncMonth();
                            int juncYear = pib.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    }
                }
                if (a == 12) {
                    //BeanUtils.copyProperties(planInfoList, ranitalInfoList);
                    if (mp.containsKey("madanMahalInfoList")) {

                        if (mp.get("madanMahalInfoList") != null) {

                            PlanInfo pim = mp.get("madanMahalInfoList");
                            int functionNo = pim.getFunction_no();
                            int junction_id = pim.getJunction_id();
                            int program_version_no = pim.getProgram_version_no();
                            int fileNo = pim.getFileNo();
                            int activity = pim.getActivity();
                            int side_no = pim.getSide_no();
                            int plan_no = pim.getPlan_no();
                            String mode = pim.getMode();
                            String junctionName = pim.getJunction_name();
                            String sideName = pim.getSideName();

                            String onTime = pim.getOnTime();
                            String offTime = pim.getOffTime();

                            String side1Name = pim.getSide1Name();
                            String side2Name = pim.getSide2Name();
                            String side3Name = pim.getSide3Name();
                            String side4Name = pim.getSide4Name();
                            String side5Name = pim.getSide5Name();

                            int side1Time = pim.getSide1_time();
                            int side2Time = pim.getSide2_time();
                            int side3Time = pim.getSide3_time();
                            int side4Time = pim.getSide4_time();
                            int side1LeftStatus = pim.getSide1_left_status();
                            int side1RightStatus = pim.getSide1_right_status();
                            int side1UpStatus = pim.getSide1_up_status();
                            int side1DownStatus = pim.getSide1_down_status();
                            int side2LeftStatus = pim.getSide2_left_status();
                            int side2RightStatus = pim.getSide2_right_status();
                            int side2UpStatus = pim.getSide2_up_status();
                            int side2DownStatus = pim.getSide2_down_status();
                            int side3LeftStatus = pim.getSide3_left_status();
                            int side3RightStatus = pim.getSide3_right_status();
                            int side3UpStatus = pim.getSide3_up_status();
                            int side3DownStatus = pim.getSide3_down_status();
                            int side4LeftStatus = pim.getSide4_left_status();
                            int side4RightStatus = pim.getSide4_right_status();
                            int side4UpStatus = pim.getSide4_up_status();
                            int side4DownStatus = pim.getSide4_down_status();

                            int juncHr = pim.getJuncHr();
                            int juncMin = pim.getJuncMin();
                            int juncDat = pim.getJuncDate();
                            int juncMonth = pim.getJuncMonth();
                            int juncYear = pim.getJuncYear();

                            request.setAttribute("juncHr", juncHr);
                            request.setAttribute("juncMin", juncMin);
                            request.setAttribute("juncDat", juncDat);
                            request.setAttribute("juncMonth", juncMonth);
                            request.setAttribute("juncYear", juncYear);

                            request.setAttribute("functionNo", Integer.valueOf(functionNo));
                            request.setAttribute("junctionId", Integer.valueOf(junction_id));
                            request.setAttribute("program_version_no", Integer.valueOf(program_version_no));
                            request.setAttribute("fileNo", Integer.valueOf(fileNo));
                            request.setAttribute("junctionName", junctionName);
                            request.setAttribute("activity", activity);
                            request.setAttribute("sideNo", Integer.valueOf(side_no));
                            request.setAttribute("sideName", sideName);
                            request.setAttribute("side1Name", side1Name);
                            request.setAttribute("side2Name", side2Name);
                            request.setAttribute("side3Name", side3Name);
                            request.setAttribute("side4Name", side4Name);
                            request.setAttribute("side5Name", side5Name);

                            request.setAttribute("onTime", onTime);
                            request.setAttribute("offTime", offTime);
                            request.setAttribute("plan_no", plan_no);
                            request.setAttribute("mode", mode);

                            request.setAttribute("side1Time", Integer.valueOf(side1Time));
                            request.setAttribute("side2Time", Integer.valueOf(side2Time));
                            request.setAttribute("side3Time", Integer.valueOf(side3Time));
                            request.setAttribute("side4Time", Integer.valueOf(side4Time));
                            request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                            request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                            request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                            request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                            request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                            request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                            request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                            request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                            request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                            request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                            request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                            request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                            request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                            request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                            request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                            if (map_junction_id != "") {
                                request.getRequestDispatcher("/view/general/showMapOnTrafficSignal.jsp").forward(request, response);
                            } else {
                                request.getRequestDispatcher("ts_statusShower_view").forward(request, response);
                            }
                            return;
                        } else {
                            String jSON_format = "Oops... No junction is active right now.";

                            request.setAttribute("message", jSON_format);
                            request.getRequestDispatcher("errorView").forward(request, response);
                            return;
                        }
                    } else {
                        String jSON_format = "Oops... No junction is active right now.";

                        request.setAttribute("message", jSON_format);
                        request.getRequestDispatcher("errorView").forward(request, response);
                        return;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("TS_StatusShowerController- doPostError :" + e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public String getCurrentTime() {
        DateFormat df = DateFormat.getTimeInstance(2);
        return df.format(new Date());
    }

    public ClientResponder getClientResponder() {
        return this.clientResponder;
    }

    public void setClientResponder(ClientResponder clientResponder) {
        this.clientResponder = clientResponder;
    }
}
