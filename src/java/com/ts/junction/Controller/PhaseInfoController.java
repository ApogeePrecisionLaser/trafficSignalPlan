/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ts.junction.Controller;

import com.ts.junction.Model.PhaseInfoModel;
import com.ts.junction.Model.PlanInfoModel;
import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.PhaseInfo;
import com.ts.junction.tableClasses.PlanInfo;
import com.ts.util.xyz;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author apogee
 */
public class PhaseInfoController extends HttpServlet {

    int junctionId = 0;
    int junctionId1 = 0;

    private static int[][][] phaseInfoArray = new int[20][4][2];
    private static List<PhaseInfo> phaseInfoList = new ArrayList<PhaseInfo>();
    private static int[] flag= new int[20];
    private static int previousPlan = 0;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PhaseInfoModel phaseInfoModel = new PhaseInfoModel();
        PlanInfoModel planinfoModel = new PlanInfoModel();
        ServletContext ctx = getServletContext();
        phaseInfoModel.setDriverClass(ctx.getInitParameter("driverClass"));
        phaseInfoModel.setConnectionString(ctx.getInitParameter("connectionString"));
        phaseInfoModel.setDb_userName(ctx.getInitParameter("db_userName"));
        phaseInfoModel.setDb_userPasswrod(ctx.getInitParameter("db_userPassword"));
        phaseInfoModel.setConnection();
        String task = request.getParameter("task");
        String requester = request.getParameter("requester");
        List<PhaseInfo> list1 = new ArrayList();
        String loopCounter = "";
        String junctionName = "";

        if (request.getParameter("junction_id2") != null && request.getParameter("junction_name212") != null) {
            junctionId = Integer.parseInt(request.getParameter("junction_id2").trim());
            junctionName = request.getParameter("junction_name212").trim();
        } else if (request.getParameter("loopCounter") != null) {
            loopCounter = request.getParameter("loopCounter").trim();
            junctionId = Integer.parseInt(request.getParameter("junction_id" + loopCounter).trim());
            junctionName = request.getParameter("junction_name" + loopCounter).trim();
        }

        //System.out.println(request.getParameter("junction_name1"));
        try {
            //----- This is only for Vendor key Person JQuery
            String jqstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (jqstring != null) {
                Set<String> list = null;
                if (jqstring.equals("getJunctionName")) {
                    list = phaseInfoModel.getJunctionName(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    if (data.equals("Disable")) {
                        out.print(data);
                    } else {
                        out.println(data);
                    }
                }
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
        }

        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 4, noOfRowsInTable;
        if (task == null) {
            task = "";
        }

        if (task.equals("Delete")) {
            // Pretty sure that junction_id will be available.
            //int phase_info_id = Integer.parseInt(request.getParameter("phase_info_id").trim());
            String plan_no1 = request.getParameter("plan_no");
            int plan_no = Integer.parseInt(request.getParameter("plan_no").trim());
            int junction_id = Integer.parseInt(request.getParameter("junction_id").trim());
            phaseInfoModel.deleteRecord(plan_no, junction_id);
        }
        
        int no_of_sides = phaseInfoModel.getNoOfSides(junctionId, phaseInfoModel.getValidPorogramVersionNo(junctionId));

        if (task.equals("Save And Continue")) {
            System.out.println(request.getParameter("side_no"));
            System.out.println(request.getParameter("phase_no"));
            int plan_no = Integer.parseInt(request.getParameter("plan_no"));
            junctionName = request.getParameter("junction_name");
            if (previousPlan == 0) {
                previousPlan = Integer.parseInt(request.getParameter("plan_no"));
                int operation = Integer.parseInt(request.getParameter("opn"));
                PhaseInfo phaseInfo;
                if (operation == 4 || operation == 5) {
                    phaseInfo = saveBlinkerData(request, operation);
                } else {
                    phaseInfo = saveData(request,no_of_sides);
                }
                phaseInfoList.add(phaseInfo);
            } else if (previousPlan != plan_no) {
                previousPlan = Integer.parseInt(request.getParameter("plan_no"));
                phaseInfoArray = new int[20][4][2];
                phaseInfoList = new ArrayList<PhaseInfo>();
                int operation = Integer.parseInt(request.getParameter("opn"));
                PhaseInfo phaseInfo;
                if (operation == 4 || operation == 5) {
                    phaseInfo = saveBlinkerData(request, operation);
                } else {
                    phaseInfo = saveData(request,no_of_sides);
                }
                phaseInfoList.add(phaseInfo);
            } else {
                previousPlan = Integer.parseInt(request.getParameter("plan_no"));
                int operation = Integer.parseInt(request.getParameter("opn"));
                PhaseInfo phaseInfo;
                if (operation == 4 || operation == 5) {
                    phaseInfo = saveBlinkerData(request, operation);
                } else {
                    phaseInfo = saveData(request,no_of_sides);
                }
                phaseInfoList.add(phaseInfo);
            }

            // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }

        if (task.equals("Save And Submit")) {
            System.out.println(request.getParameter("side_no"));
            System.out.println(request.getParameter("phase_no"));
            int phase_no = Integer.parseInt(request.getParameter("phase_no"));
            int phase_time = Integer.parseInt(request.getParameter("phase_time"));
            int plan_no = Integer.parseInt(request.getParameter("plan_no"));
            junctionName = request.getParameter("junction_name");
            if (previousPlan == 0) {
                previousPlan = Integer.parseInt(request.getParameter("plan_no"));
                int operation = Integer.parseInt(request.getParameter("opn"));
                PhaseInfo phaseInfo = new PhaseInfo();
                if (operation == 4 || operation == 5) {
                    phaseInfo = saveBlinkerData(request, operation);
                } else {
                    phaseInfo = saveData(request,no_of_sides);
                }
                phaseInfoList.add(phaseInfo);
                int j = 0;
                for (PhaseInfo phaseInfo1 : phaseInfoList) {
                    PhaseInfo info = new PhaseInfo();
                    info.setPlanNo(phaseInfo1.getPlanNo());
                    info.setPhaseNo(phaseInfo1.getPhaseNo());
                    info.setPhaseTime(phaseInfo1.getPhaseTime());
                    info.setJunction_name(phaseInfo1.getJunction_name());
                    info.setJunction_id(phaseInfo1.getJunction_id());
                    info.setSide13(phaseInfo1.getSide13());
                    info.setSide24(phaseInfo1.getSide24());
                    info.setSide5(phaseInfo1.getSide5());
                    info.setGreen1(phaseInfoArray[j][0][1]);
                    info.setGreen2(phaseInfoArray[j][1][1]);
                    info.setGreen3(phaseInfoArray[j][2][1]);
                    if(no_of_sides==4) {
                        info.setGreen4(phaseInfoArray[j][3][1]);
                    }                    
                    info.setGPIO(phaseInfo1.getGPIO());
                    info.setLeft_green(phaseInfo1.getLeft_green());
                    phaseInfoModel.insertRecord(info);
                    j++;
                }

            } else if (previousPlan != plan_no) {
                previousPlan = Integer.parseInt(request.getParameter("plan_no"));
                int operation = Integer.parseInt(request.getParameter("opn"));
                PhaseInfo phaseInfo;
                if (operation == 4 || operation == 5) {
                    phaseInfo = saveBlinkerData(request, operation);
                } else {
                    phaseInfo = saveData(request,no_of_sides);
                }
                phaseInfoList.add(phaseInfo);
                int j = 0;
                for (PhaseInfo phaseInfo1 : phaseInfoList) {
                    PhaseInfo info = new PhaseInfo();
                    info.setPlanNo(phaseInfo1.getPlanNo());
                    info.setPhaseNo(phaseInfo1.getPhaseNo());
                    info.setPhaseTime(phaseInfo1.getPhaseTime());
                    info.setJunction_name(phaseInfo1.getJunction_name());
                    info.setJunction_id(phaseInfo1.getJunction_id());
                    info.setSide13(phaseInfo1.getSide13());
                    info.setSide24(phaseInfo1.getSide24());
                    info.setSide5(phaseInfo1.getSide5());
                    info.setGreen1(phaseInfoArray[j][0][1]);
                    info.setGreen2(phaseInfoArray[j][1][1]);
                    info.setGreen3(phaseInfoArray[j][2][1]);
                    if(no_of_sides==4) {
                        info.setGreen4(phaseInfoArray[j][3][1]);
                    }   
                    info.setGPIO(phaseInfo1.getGPIO());
                    info.setLeft_green(phaseInfo1.getLeft_green());
                    phaseInfoModel.insertRecord(info);
                    j++;
                }
                phaseInfoArray = new int[20][4][2];
                phaseInfoList = new ArrayList<PhaseInfo>();
                PhaseInfo phaseInfo1 = saveData(request,no_of_sides);
                phaseInfoList.add(phaseInfo1);
            } else {
                previousPlan = Integer.parseInt(request.getParameter("plan_no"));
                int operation = Integer.parseInt(request.getParameter("opn"));
                PhaseInfo phaseInfo = new PhaseInfo();
                if (operation == 4 || operation == 5) {
                    phaseInfo = saveBlinkerData(request, operation);
                } else {
                    phaseInfo = saveData(request,no_of_sides);
                }
                phaseInfoList.add(phaseInfo);
                int j = 0;
                for (PhaseInfo phaseInfo1 : phaseInfoList) {
                    PhaseInfo info = new PhaseInfo();
                    info.setPlanNo(phaseInfo1.getPlanNo());
                    info.setPhaseNo(phaseInfo1.getPhaseNo());
                    info.setPhaseTime(phaseInfo1.getPhaseTime());
                    info.setJunction_name(phaseInfo1.getJunction_name());
                    info.setJunction_id(phaseInfo1.getJunction_id());
                    info.setSide13(phaseInfo1.getSide13());
                    info.setSide24(phaseInfo1.getSide24());
                    info.setSide5(phaseInfo1.getSide5());
                    info.setGreen1(phaseInfoArray[j][0][1]);
                    info.setGreen2(phaseInfoArray[j][1][1]);
                    info.setGreen3(phaseInfoArray[j][2][1]);
                    if(no_of_sides==4) {
                        info.setGreen4(phaseInfoArray[j][3][1]);
                    }    
                    info.setGPIO(phaseInfo1.getGPIO());
                    info.setLeft_green(phaseInfo1.getLeft_green());
                    phaseInfoModel.insertRecord(info);
                    j++;
                }

            }
            phaseInfoList = null;
        }

//        if (task.equals("Save")) {
//
//            PhaseInfo pi = saveData(request);
//            phaseInfoModel.updateRecord(pi);
//
//        }
        if (junctionId1 == 0) {
            noOfRowsInTable = phaseInfoModel.getNoOfRows(junctionId);
            //list1 = phaseInfoModel.showData(lowerLimit, noOfRowsToDisplay, junctionId);
        } else {
            noOfRowsInTable = phaseInfoModel.getNoOfRows(junctionId1);
            //list1 = phaseInfoModel.showData(lowerLimit, noOfRowsToDisplay, junctionId1);
        }
        // get the number of records (rows) in the table.

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

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        }
        if (task.equals("ajaxCall")) {
            PrintWriter out = response.getWriter();
            JSONObject jsonObj = new JSONObject();

            String operation = request.getParameter("operation");
            int side_no = Integer.parseInt(request.getParameter("side_no"));
            String green_type = request.getParameter("green_type");
            int pairing = Integer.parseInt(request.getParameter("pairing"));
            int plan_no = Integer.parseInt(request.getParameter("plan_no"));

            PlanInfo planList;
            if (junctionId1 == 0) {
                int valid_program_version_no = phaseInfoModel.getValidPorogramVersionNo(junctionId);
                planList = phaseInfoModel.showPlanData(junctionId, valid_program_version_no, plan_no);
            } else {
                int valid_program_version_no = phaseInfoModel.getValidPorogramVersionNo(junctionId1);
                planList = phaseInfoModel.showPlanData(junctionId1, valid_program_version_no, plan_no);
            }

            try {
                if (operation.equals("green")) {
                    switch (side_no) {
                        case 1:
                            jsonObj.put("phase_time", planList.getSide1_green_time());
                            break;
                        case 2:
                            jsonObj.put("phase_time", planList.getSide2_green_time());
                            break;
                        case 3:
                            jsonObj.put("phase_time", planList.getSide3_green_time());
                            break;
                        case 4:
                            jsonObj.put("phase_time", planList.getSide4_green_time());
                            break;
                        case 5:
                            jsonObj.put("phase_time", planList.getSide5_green_time());
                            break;
                        default:
                            break;
                    }
                } else if (operation.equals("amber")) {
                    switch (side_no) {
                        case 1:
                            jsonObj.put("phase_time", planList.getSide1_amber_time());
                            break;
                        case 2:
                            jsonObj.put("phase_time", planList.getSide2_amber_time());
                            break;
                        case 3:
                            jsonObj.put("phase_time", planList.getSide3_amber_time());
                            break;
                        case 4:
                            jsonObj.put("phase_time", planList.getSide4_amber_time());
                            break;
                        case 5:
                            jsonObj.put("phase_time", planList.getSide5_amber_time());
                            break;
                        default:
                            jsonObj.put("phase_time", planList.getSide1_amber_time());
                            break;
                    }
                } else {
                    jsonObj.put("phase_time", planList.getPedestrian_time());
                }
                out.println(jsonObj);
                out.flush();

            } catch (JSONException ex) {
                Logger.getLogger(PhaseInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (junctionId1 == 0) {
            list1 = phaseInfoModel.showData(lowerLimit, noOfRowsToDisplay, junctionId);
            lowerLimit = lowerLimit + list1.size();
            noOfRowsTraversed = list1.size();
        } else {
            list1 = phaseInfoModel.showData(lowerLimit, noOfRowsToDisplay, junctionId1);
            lowerLimit = lowerLimit + list1.size();
            noOfRowsTraversed = list1.size();
        }

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        request.setAttribute("junction", list1);
        request.setAttribute("junctionName", junctionName);
        request.setAttribute("junctionId", junctionId);
        request.setAttribute("message", phaseInfoModel.getMessage());
        request.setAttribute("msgBgColor", phaseInfoModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new xyz());
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.getRequestDispatcher("/pITest").forward(request, response);

    }

    private static String binaryToHex(String binary) {
        int decimalValue = 0;
        int length = binary.length() - 1;
        for (int i = 0; i < binary.length(); i++) {
            decimalValue += Integer.parseInt(binary.charAt(i) + "") * Math.pow(2, length);
            length--;
        }
        return decimalToHex(decimalValue);
    }

    private static String decimalToHex(int decimal) {
        String hex = "";
        while (decimal != 0) {
            int hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return hex;
    }

    private static char toHexChar(int hexValue) {
        if (hexValue <= 9 && hexValue >= 0) {
            return (char) (hexValue + '0');
        } else {
            return (char) (hexValue - 10 + 'A');
        }
    }
    
    private static int binaryToDecimal(int n) 
    { 
        int num = n; 
        int dec_value = 0; 
  
        // Initializing base 
        // value to 1, i.e 2^0 
        int base = 1; 
  
        int temp = num; 
        while (temp > 0) { 
            int last_digit = temp % 10; 
            temp = temp / 10; 
  
            dec_value += last_digit * base; 
  
            base = base * 2; 
        } 
  
        return dec_value; 
    } 

    private PhaseInfo saveBlinkerData(HttpServletRequest request, int operation) {
        PhaseInfo phaseInfo = new PhaseInfo();
        int plan_no1 = Integer.parseInt(request.getParameter("plan_no"));
        int phase_no = Integer.parseInt(request.getParameter("phase_no"));
        int phase_time = Integer.parseInt(request.getParameter("phase_time"));
        String side_one_three = "00000000";
        String side_two_four = "00000000";
        String side_five = "0000";
        int side_one_three1 = 0;
        int side_two_four1 = 0;
        int side_five1 = 0;
        phaseInfoArray[phase_no - 1][0][1] = phase_time;
        phaseInfoArray[phase_no - 1][1][1] = phase_time;
        phaseInfoArray[phase_no - 1][2][1] = phase_time;
        phaseInfoArray[phase_no - 1][3][1] = phase_time;
        phaseInfoArray[phase_no - 1][0][0] = 2;
        phaseInfoArray[phase_no - 1][1][0] = 2;
        phaseInfoArray[phase_no - 1][2][0] = 2;
        phaseInfoArray[phase_no - 1][3][0] = 2;
        if (operation == 4) {
            side_one_three = "01000100";
            side_two_four = "01000100";
            side_five = "0100";
            side_one_three1 = Integer.parseInt(side_one_three,2);
            side_two_four1 = Integer.parseInt(side_two_four,2);
            side_five1 = Integer.parseInt(side_five,2);
        }
        int left_green = 1;
        int gpio = 1;
        int id = 0;
        phaseInfo.setPhaseInfoId(id);
        phaseInfo.setJunction_id(junctionId1);
        phaseInfo.setJunction_name(request.getParameter("junction_name"));
        phaseInfo.setGPIO(gpio);
        phaseInfo.setGreen1(phase_time);
        phaseInfo.setGreen2(phase_time);
        phaseInfo.setGreen3(phase_time);
        phaseInfo.setGreen4(phase_time);
        phaseInfo.setGreen5(phase_time);
        phaseInfo.setLeft_green(left_green);
        //pi.setPadestrian_info(padestrian_info);
        phaseInfo.setPhaseNo(phase_no);
        phaseInfo.setPhaseTime(phase_time);
        phaseInfo.setPlanNo(plan_no1);
        phaseInfo.setSide13(side_one_three1);
        phaseInfo.setSide24(side_two_four1);
        phaseInfo.setSide5(side_five1);
        phaseInfo.setRemark(request.getParameter("remark"));
        return phaseInfo;
    }

    private PhaseInfo saveData(HttpServletRequest request,int no_of_sides) {
        PhaseInfo pi = new PhaseInfo();
        String side_one_Three = "10001000";
        String side_two_Four = "10001000";
        int green1 = 0;
        int green2 = 0;
        int green3 = 0;
        int green4 = 0;
        int green5 = 0;

        String side_one = "0000", side_two = "0000", side_three = "0000", side_four = "0000", side_Five = "0000";
        junctionId1 = Integer.parseInt(request.getParameter("junction_id"));
        String junction_name = request.getParameter("junction_name");
        String junctionName = request.getParameter("junction_name");
        int plan_no = Integer.parseInt(request.getParameter("plan_no"));
        int phase_no = Integer.parseInt(request.getParameter("phase_no"));
        int phase_time = Integer.parseInt(request.getParameter("phase_time"));
        int operation = Integer.parseInt(request.getParameter("opn"));
        int side_no = 0;
        if(operation != 3) {
            side_no = Integer.parseInt(request.getParameter("side_no"));
        }
        
        String green_type = request.getParameter("green");
        //int pair = Integer.parseInt(request.getParameter("pair"));
        PhaseInfo phaseInfo = new PhaseInfo();
        String side1A = request.getParameter("side1A");
        
        if(no_of_sides == 3) {
            phaseInfoArray[phase_no - 1][0][1] = phase_time;
            phaseInfoArray[phase_no - 1][1][1] = phase_time;
            phaseInfoArray[phase_no - 1][2][1] = phase_time;
        } else {
            phaseInfoArray[phase_no - 1][0][1] = phase_time;
            phaseInfoArray[phase_no - 1][1][1] = phase_time;
            phaseInfoArray[phase_no - 1][2][1] = phase_time;
            phaseInfoArray[phase_no - 1][3][1] = phase_time;
        }
        

        if (request.getParameter("side1R") == null || request.getParameter("side3R") == null) {
            if (request.getParameter("side1A") != null) {
                side_one = "0100";
                phaseInfoArray[phase_no - 1][0][0] = 2;
            } else if (request.getParameter("side1G1") != null && request.getParameter("side1G2") != null) {
                side_one = "0011";
                phaseInfoArray[phase_no - 1][0][0] = 5;
            } else if (request.getParameter("side1G1") != null && request.getParameter("side1G2") == null) {
                side_one = "0010";
                phaseInfoArray[phase_no - 1][0][0] = 3;
            } else if (request.getParameter("side1G2") != null && request.getParameter("side1G1") == null) {
                side_one = "0001";
                phaseInfoArray[phase_no - 1][0][0] = 4;
            } else if (request.getParameter("side1R") != null) {
                side_one = "1000";
                phaseInfoArray[phase_no - 1][0][0] = 1;
            }

            if (request.getParameter("side3A") != null) {
                side_three = "0100";
                phaseInfoArray[phase_no - 1][2][0] = 2;
            } else if (request.getParameter("side3G1") != null && request.getParameter("side3G2") != null) {
                side_three = "0011";
                phaseInfoArray[phase_no - 1][2][0] = 5;
            } else if (request.getParameter("side3G1") != null && request.getParameter("side3G2") == null) {
                side_three = "0010";
                phaseInfoArray[phase_no - 1][2][0] = 3;
            } else if (request.getParameter("side3G2") != null && request.getParameter("side3G1") == null) {
                side_three = "0001";
                phaseInfoArray[phase_no - 1][2][0] = 4;
            } else if (request.getParameter("side3R") != null) {
                side_three = "1000";
                phaseInfoArray[phase_no - 1][2][0] = 1;
            }

            side_one_Three = side_one + side_three;

        } else if (request.getParameter("side1R") != null && request.getParameter("side3R") != null) {
            side_one_Three = "10001000";
            phaseInfoArray[phase_no - 1][0][0] = 1;
            phaseInfoArray[phase_no - 1][2][0] = 1;
        }

        if (request.getParameter("side2R") == null || request.getParameter("side4R") == null) {
            if (request.getParameter("side2A") != null) {
                side_two = "0100";
                phaseInfoArray[phase_no - 1][1][0] = 2;
            } else if (request.getParameter("side2G1") != null && request.getParameter("side2G2") != null) {
                side_two = "0011";
                phaseInfoArray[phase_no - 1][1][0] = 5;
            } else if (request.getParameter("side2G1") != null && request.getParameter("side2G2") == null) {
                side_two = "0010";
                phaseInfoArray[phase_no - 1][1][0] = 3;
            } else if (request.getParameter("side2G2") != null && request.getParameter("side2G1") == null) {
                side_two = "0001";
                phaseInfoArray[phase_no - 1][1][0] = 4;
            } else if (request.getParameter("side2R") != null) {
                 side_two = "1000";
                phaseInfoArray[phase_no - 1][1][0] = 1;
            }
            
            if(no_of_sides == 4) {
                if (request.getParameter("side4A") != null) {
                    side_four = "0100";
                    phaseInfoArray[phase_no - 1][3][0] = 2;
                } else if (request.getParameter("side4G1") != null && request.getParameter("side4G2") != null) {
                    side_four = "0011";
                    phaseInfoArray[phase_no - 1][3][0] = 5;
                } else if (request.getParameter("side4G1") != null && request.getParameter("side4G2") == null) {
                    side_four = "0010";
                    phaseInfoArray[phase_no - 1][3][0] = 3;
                } else if (request.getParameter("side4G2") != null && request.getParameter("side4G1") == null) {
                    side_four = "0001";
                    phaseInfoArray[phase_no - 1][3][0] = 4;
                } else if (request.getParameter("side4R") != null) {
                    side_four = "1000";
                    phaseInfoArray[phase_no - 1][3][0] = 1;
                }
            } else {
                side_four = "0000";
            }
            

            side_two_Four = side_two + side_four;

        } else if (request.getParameter("side2R") != null && request.getParameter("side4R") != null) {
            if(no_of_sides == 4) {
                side_two_Four = "10001000";
            } else {
                side_two_Four = "10000000";
            }            
            phaseInfoArray[phase_no - 1][1][0] = 1;
            phaseInfoArray[phase_no - 1][3][0] = 1;
        }

        if (request.getParameter("side5R") == null || request.getParameter("side4R") == null) {
            if (request.getParameter("side5A") != null) {
                side_Five = "0100";
            } else if (request.getParameter("side5G1") != null && request.getParameter("side5G2") != null) {
                side_Five = "0011";
            } else if (request.getParameter("side5G1") != null && request.getParameter("side5G2") == null) {
                side_Five = "0010";
            } else if (request.getParameter("side5G2") != null && request.getParameter("side5G1") == null) {
                side_Five = "0001";
            }
        }

        int side_one_three = Integer.parseInt(side_one_Three,2);
        int side_two_four = Integer.parseInt(side_two_Four,2);
        int side_five = Integer.parseInt(side_Five,2);
        int left_green = Integer.parseInt(request.getParameter("left_green"));
        int gpio = Integer.parseInt(request.getParameter("gpio"));
//            int padestrian_info = Integer.parseInt(request.getParameter("padestrian_info"));
//            int phase_info_id = Integer.parseInt(request.getParameter("phase_info_id"));
//            int junction_id = Integer.parseInt(request.getParameter("junction_id"));
        String remark = request.getParameter("remark");
        int id = 0;
        int junction_id = 0;
        try {
            id = Integer.parseInt(request.getParameter("phase_info_id").trim());
            junction_id = Integer.parseInt(request.getParameter("junction_id").trim());
        } catch (NumberFormatException ex) {
            id = 0;
//                System.out.println("ad_asso_site_detail_id error: " + ex);
        }

        green1 = green3 = green2 = green4 = green5 = phase_time;
        
        if (request.getParameter("task").equals("Save And Continue") || request.getParameter("task").equals("Save And Submit")) {
            if (phase_no > 1) {
                for (int i = phase_no; i >= (phase_no - 1); --i) {
                    for (int j = 0; j < no_of_sides; j++) {                    
                        if (phaseInfoArray[i][j][0] == 2 && phaseInfoArray[i - 1][j][0] == 3) {
                            System.out.println("No Change");
                        } else if (phaseInfoArray[i][j][0] == 2 && phaseInfoArray[i - 1][j][0] == 5) {
                            System.out.println("No Change");
                        } else if (phaseInfoArray[i][j][0] == 1 && phaseInfoArray[i - 1][j][0] == 2) {
                            if(phaseInfoArray[0][j][0] == 1) {
                                 phaseInfoArray[i][j][1] = phaseInfoArray[0][j][1] + phase_time;
                                 flag[j] = 1;
                            }                           
                        } else if (phaseInfoArray[i][j][0] == 3 && phaseInfoArray[i - 1][j][0] == 1) {
                            System.out.println("No Change");
                        } else if (phaseInfoArray[i][j][0] == 5 && phaseInfoArray[i - 1][j][0] == 1) {
                            System.out.println("No Change");
                        } else if (phaseInfoArray[i][j][0] == 1 && phaseInfoArray[i - 1][j][0] == 1) {
                            int m = i;
                            if(flag[j] == 1) {
                                phaseInfoArray[m][j][1] = phaseInfoArray[m][j][1] + phaseInfoArray[0][j][1];                                        
                            } 
                            while (m > 0) {
                                if (phaseInfoArray[m][j][0] == 1 && phaseInfoArray[m - 1][j][0] == 1) {                                    
                                    phaseInfoArray[m - 1][j][1] = phaseInfoArray[m - 1][j][1] + phase_time;                                    
                                }else {
                                    break;
                                }
                                --m;
                            }
                        }
                    }
                }
            }
        }

        pi.setPhaseInfoId(id);
        pi.setJunction_id(junctionId1);
        pi.setJunction_name(junction_name);
        pi.setGPIO(gpio);
        pi.setGreen1(phase_time);
        pi.setGreen2(phase_time);
        pi.setGreen3(phase_time);
        pi.setGreen4(phase_time);
        pi.setGreen5(phase_time);
        pi.setLeft_green(left_green);
        //pi.setPadestrian_info(padestrian_info);
        pi.setPhaseNo(phase_no);
        pi.setPhaseTime(phase_time);
        pi.setPlanNo(plan_no);
        pi.setSide13(side_one_three);
        pi.setSide24(side_two_four);
        pi.setSide5(side_five);
        pi.setRemark(remark);

        return pi;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
