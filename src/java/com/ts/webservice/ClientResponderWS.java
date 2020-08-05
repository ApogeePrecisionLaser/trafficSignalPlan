package com.ts.webservice;

import com.ts.general.Controller.ModemResReadSaveController;
import com.ts.general.Controller.TS_StatusShowerController;
import com.ts.general.Controller.TS_StatusUpdaterController;
import com.ts.junction.tableClasses.BaldeobagInfo;
import com.ts.junction.tableClasses.BandariyaTirahaInfo;
import com.ts.junction.tableClasses.BloomChowkInfo;
import com.ts.junction.tableClasses.DamohNakaInfo;
import com.ts.junction.tableClasses.DeendayalChowkInfo;
import com.ts.junction.tableClasses.GohalPurInfo;
import com.ts.junction.tableClasses.HighCourtInfo;
import com.ts.junction.tableClasses.History;
import com.ts.junction.tableClasses.Junction;
import com.ts.junction.tableClasses.KatangaInfo;
import com.ts.junction.tableClasses.LabourChowkInfo;
import com.ts.junction.tableClasses.MadanMahalInfo;
import com.ts.junction.tableClasses.PlanInfo;
import com.ts.junction.tableClasses.RanitalInfo;
import com.ts.junction.tableClasses.SlaveInfo;
import com.ts.junction.tableClasses.TeenPatti_Info;
import com.ts.junction.tableClasses.YatayatThanaInfo;
import com.ts.log.tableClasses.SeverityCase;
import com.ts.tcpServer.ClientResponderModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

public class ClientResponderWS extends HttpServlet
        implements Runnable {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;
    private BufferedReader reader;
    private OutputStreamWriter outputStreamWriter;
    private String driverClass;
    private PrintWriter out;
    private String connectionString;
    private String db_userName;
    private Map<String, ClientResponderWS> map;
    private String db_userPassword;
    private Map<Integer, Junction> junctionList;
    private String ipAddress;
    private String ipPort;
    private String ipLoginTimstamp;
    private Boolean ipStatus;
    ClientResponderModel clientResponderModel = new ClientResponderModel();
    TS_StatusShowerController tsStatusShowerCont = new TS_StatusShowerController();
    private Junction junction;
    private int totalNoOfSides;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext ctx;
    private ServerSocket serverSocket;
    private Async async;
    private Date date;
    private TS_StatusUpdaterController tsUpdaterCont;
    private Calendar cal;
    private static PlanInfo planInfoRefreshList = new PlanInfo();
    private static TeenPatti_Info teenPattiInfoRefreshList = new TeenPatti_Info();
    private static GohalPurInfo gohalPurInfoRefreshList = new GohalPurInfo();
    private static RanitalInfo ranitalInfoRefreshList = new RanitalInfo();
    private static DamohNakaInfo damohNakaInfoRefreshList = new DamohNakaInfo();
    private static KatangaInfo katangaInfoRefreshList = new KatangaInfo();
    private static YatayatThanaInfo yatayatThanaInfoRefreshList = new YatayatThanaInfo();
    private static BaldeobagInfo baldeobagInfoRefreshList = new BaldeobagInfo();
    private static DeendayalChowkInfo deendayalChowkInfoRefreshList = new DeendayalChowkInfo();
    private static HighCourtInfo highCourtInfoRefreshList = new HighCourtInfo();
    private static BloomChowkInfo bloomChowkInfoRefreshList = new BloomChowkInfo();
    private static MadanMahalInfo madanMahalInfoRefreshList = new MadanMahalInfo();
    private static BandariyaTirahaInfo bandariyaTirahaInfoRefreshList = new BandariyaTirahaInfo();
    private static LabourChowkInfo labourChowkInfoRefreshList = new LabourChowkInfo();
    // private static SlaveInfo sidelist;
    int[] arrdata;
    List<SlaveInfo> sidelistdata;
    List<SlaveInfo> sideinfolist = new ArrayList<>();
    HashMap<String, Integer> hmap = new HashMap();
    String currentTime;
    private String lastVisitedTime;
    DateFormat dateFormat;
    private boolean requestForClearance;
    private boolean requestForActivity;
    private boolean requestToUpdateProgramVersionNo;
    private boolean isJunctionLive;
    private int clearanceSide;
    private int activityNo;
    private int activitySide;
    private ModemResReadSaveController modemResReadSaveCont;
    private int noOfRequestReceived;
    public static byte[] arr1;
    public static byte[] arr13;
    public static byte[] arr11;
    public static byte[] arrkatanga;
    public static byte[] arryatayat;
    public static byte[] arrbaldeobag;
    public static byte[] arrbloomchowk;
    public static byte[] arrdeendayal;
    public static byte[] arrhighcourt;
    public static byte[] arrmadanmahal;
    public static byte[] arrgohalpur;
    public static byte[] arrbandariya;

    public ClientResponderWS(InputStream inputStream, OutputStream outputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.outputStreamWriter = new OutputStreamWriter(outputStream);
        this.out = new PrintWriter(outputStream, true);
        this.async = new Async();
        this.modemResReadSaveCont = new ModemResReadSaveController();
        this.tsUpdaterCont = new TS_StatusUpdaterController();
    }

    public ClientResponderWS() {
    }

    public void run() {
        this.clientResponderModel.setDriverClass(this.driverClass);
        this.clientResponderModel.setConnectionString(this.connectionString);
        this.clientResponderModel.setDb_userName(this.db_userName);
        this.clientResponderModel.setDb_userPasswrod(this.db_userPassword);
        this.clientResponderModel.setConnection();

        this.async.setClientSocket(this.clientSocket);
        this.async.setTcpServer(this.serverSocket);
        this.async.setDriverClass(this.driverClass);
        this.async.setConnectionString(this.connectionString);
        this.async.setDb_userName(this.db_userName);
        this.async.setDb_userPasswrod(this.db_userPassword);
        this.ctx.setAttribute("planInfoRefreshList", this.planInfoRefreshList);
        this.tsUpdaterCont.setCtx(this.ctx);

        System.out.println("Reading data from Client...");
        try {
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.cal = Calendar.getInstance();
            this.currentTime = this.dateFormat.format(this.cal.getTime());
            this.lastVisitedTime = this.currentTime;
            System.out.println("run:cal -lastVisitedTime--" + this.lastVisitedTime);
            this.junction.setLastVisitedTime(this.lastVisitedTime);
            //this.junction.setClientResponder(this);
            this.async.setJunction(this.junction);
            this.async.setPlanInfoRefreshList(this.planInfoRefreshList);
            this.async.setTeenPattiInfoRefreshList(this.teenPattiInfoRefreshList);
            this.async.setGohalPurInfoRefreshList(this.gohalPurInfoRefreshList);
            this.async.setRanitalInfoRefreshList(this.ranitalInfoRefreshList);
            this.async.setDamohNakaInfoRefreshList(this.damohNakaInfoRefreshList);
            this.async.setYatayatThanaInfoRefreshList(this.yatayatThanaInfoRefreshList);
            this.async.setKatangaInfoRefreshList(this.katangaInfoRefreshList);
            this.async.setBaldeobagInfoRefreshList(this.baldeobagInfoRefreshList);
            this.async.setDeendayalChowkInfoRefreshList(this.deendayalChowkInfoRefreshList);
            this.async.setHighCourtInfoRefreshList(this.highCourtInfoRefreshList);
            this.async.setBloomChowkInfoRefreshList(this.bloomChowkInfoRefreshList);
            this.async.setMadanMahalInfoRefreshList(this.madanMahalInfoRefreshList);
            this.async.setBandariyaTirahaInfoRefreshList(this.bandariyaTirahaInfoRefreshList);
            this.async.setLabourChowkInfoRefreshList(this.labourChowkInfoRefreshList);
            this.ctx.setAttribute("planInfoRefreshList", this.planInfoRefreshList);
            this.ctx.setAttribute("teenPattiInfoRefreshList", this.teenPattiInfoRefreshList);
            this.ctx.setAttribute("gohalPurInfoRefreshList", this.gohalPurInfoRefreshList);
            this.ctx.setAttribute("ranitalInfoRefreshList", this.ranitalInfoRefreshList);
            this.ctx.setAttribute("damohNakaInfoRefreshList", this.damohNakaInfoRefreshList);
            this.ctx.setAttribute("yatayatThanaInfoRefreshList", this.yatayatThanaInfoRefreshList);
            this.ctx.setAttribute("katangaInfoRefreshList", this.katangaInfoRefreshList);
            this.ctx.setAttribute("baldeobagInfoRefreshList", this.baldeobagInfoRefreshList);
            this.ctx.setAttribute("deendayalChowkInfoRefreshList", this.deendayalChowkInfoRefreshList);
            this.ctx.setAttribute("highCourtInfoRefreshList", this.highCourtInfoRefreshList);
            this.ctx.setAttribute("bloomChowkInfoRefreshList", this.bloomChowkInfoRefreshList);
            this.ctx.setAttribute("madanMahalInfoRefreshList", this.madanMahalInfoRefreshList);
            this.ctx.setAttribute("bandariyaTirahaInfoRefreshList", this.bandariyaTirahaInfoRefreshList);
            this.ctx.setAttribute("labourChowkInfoRefreshList", this.labourChowkInfoRefreshList);
            this.tsUpdaterCont.setCtx(this.ctx);
            this.ctx.setAttribute("junction", this.junction);
            this.modemResReadSaveCont.setCtx(this.ctx);
            System.out.println("clientSocket closed Status #######" + this.clientSocket.isClosed());
            System.out.println("client responder obj=" + this.junction.getClientResponder());

            while (readClientResponse(new byte[0]).isEmpty()) {
                try {
                    synchronized (this) {
                        wait(2000L);
                    }
                } catch (InterruptedException interruptedEx) {
                    System.out.println("ClientResponseReader run() Error: " + interruptedEx);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientResponderWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void setConnection(Connection con) {
//        this.clientResponderModel.setConnection(con);
//    }
    public void setConnection() {
        try {
            System.out.println("hii");
            Class.forName("com.mysql.jdbc.Driver");
            this.clientResponderModel.setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3306/traffic_signals", "jpss_2", "jpss_1277"));
        } catch (Exception e) {
            System.out.println("ReadMailModel setConnection() Error: " + e);
        }
    }

    public static void main(String... args) {
        //byte[] receivedBytes = {126,126,0,28,1,0,0,2,1,48,49,51,55,55,55,48,48,48,54,48,57,56,51,50,48,48,56,48,50,55,53,57,87,125,125};// function 1
        //byte[] receivedBytes = {126,126,0,9,1,16,0,2,9,0,0,0,0,0,-13,125,125};// function 9
        //byte[] receivedBytes = { 126, 126, 0, 4, 11, 1, 1, 3, -17, 125, 125};//{126,126,0,46,1,16,1,1,2,90,-49,90,-38,90,-55,0,0,0,23,0,0,0,0,0,1,0,1,0,3,0,3,0,0,7,-6,1,59,59,59,1,1,18,42,5,31,11,34,12,5,16,-111,125};// fun 2
        //byte[] receivedBytes = {126, 126, 1, 19, 32, 11, 4, 8, 35, 35, 35, 35, -125, -120, -103, 2, 3, 1, 14, 14, 15, 15, 15, 107, 125, 125};
        byte[] receivedBytes = {126, 126, 1, 19, 2, 10, 4, 8, 35, 35, 35, 35, -120, -125, -103, 2, 3, 1, 14, 14, 15, 15, 15, 107, 125, 125};
        ClientResponderWS obj = new ClientResponderWS();
        obj.setConnection();
        try {
            obj.setJunction(new Junction());
            obj.setAsync(new Async());
            String res = obj.JunctionRefreshFunction(receivedBytes, 4, false);
            obj.sendResponse(res);
            obj.closeConnection();
        } catch (Exception ex) {
            System.out.println("ERROR : in response " + ex);
        }
        //System.out.println(res);
    }

    public void closeConnection() {
        this.clientResponderModel.closeConnection();
    }

    public String readClientResponse(byte[] receivedBytes)
            throws IOException {
        boolean isConnectionClosed = false;
        String currentVisitedTime = null;
        String res = null;
        try {
            //byte[] bytes = new byte[1000];

            //int read = this.inputStream.read(bytes);
            byte[] bytes = receivedBytes;
            int read = bytes.length;
            //while (read > 1) {
            noOfRequestReceived++;
            System.out.println("number of bytes actualy read: " + read);
            System.out.println("received Request No: " + noOfRequestReceived);
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.cal = Calendar.getInstance();
            this.currentTime = this.dateFormat.format(this.cal.getTime());
            this.lastVisitedTime = this.currentTime;
            //System.out.println("clientSocket closed Status #######" + this.clientSocket.isClosed());
            System.out.println("cal -currentTime--" + this.currentTime);
            this.junction.setLastVisitedTime(this.currentTime);
            this.async.setJunction(this.junction);
            int firstStartDataPosition = 0;
            int minimumDatabytes = 6;
            int initialflag1 = 0;
            int endflag1 = 0;
            int initialflag2 = 0;
            int endflag2 = 0;
            try {
                for (int i = 0; i <= (bytes.length - minimumDatabytes) && (initialflag1 != 1 || initialflag2 != 1); i++) {
                    if (bytes[i] == 126 && (initialflag1 != 1 || initialflag2 != 1)) {
                        if (bytes[i + 1] == 126) {
                            firstStartDataPosition = i + 4;
                            initialflag2 = 1;
                        } else {
                            firstStartDataPosition = i + 3;
                            initialflag1 = 1;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("ClientResponseReader readClientResponse initial delimiter Exception: " + e);
            }
            if (initialflag1 == 1 || initialflag2 == 1) {
                byte dataSize[] = new byte[2];
                dataSize[0] = 0;//bytes[firstStartDataPosition - 2];
                dataSize[1] = bytes[firstStartDataPosition - 1];
                int receivedDataSize = new BigInteger(dataSize).intValue();
                int function_no = bytes[firstStartDataPosition + 3];

                if (matchDataLength(receivedDataSize, function_no)) {

                    if (bytes[firstStartDataPosition + receivedDataSize + 1] == 125) {

                        // if (validateDataByCrc(bytes, firstStartDataPosition, receivedDataSize)) {
                        boolean testRequestFromJunction = false;
                        int junctionID = (int) bytes[firstStartDataPosition];
                        int  program_version_no= (int) bytes[firstStartDataPosition + 1];
                        /*------19-6-20--start-----range of byte array -127to127*/
                        if(program_version_no <0){
                        program_version_no=256+program_version_no;
                        } 
                        //done because when program version exceeded 127 it gets value in negative and throws erroe
                        /*------19-6-20--end-----*/
                        int fileNo = (int) bytes[firstStartDataPosition + 2];
                        int functionNo = (int) bytes[firstStartDataPosition + 3];
                        currentVisitedTime = this.currentTime;
                        System.out.println("readClientResponse----currentVisitedTime--- " + currentVisitedTime + ", " + "lastVisitedTime--- " + this.lastVisitedTime);

                        int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                        int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                        int calculatedDifference = calculateCurrentTime - calculateLastTime;

                        this.junction.setLastVisitedTime(currentVisitedTime);
                        this.junction.setJunction_id(junctionID);
                        this.junction.setProgram_version_no(program_version_no);
                        this.junction.setFile_no(fileNo);
                        //this.junction.setClientResponder(this);
                        this.async.setJunction(this.junction);
                        this.async.setPlanInfoRefreshList(this.planInfoRefreshList);
                        this.ctx.setAttribute("junction", this.junction);
                        this.modemResReadSaveCont.setCtx(this.ctx);
                        History historyList = new History();
                        historyList.setJunction_id(junctionID);
                        historyList.setProgram_version_no(program_version_no);
                        historyList.setIp_address(ipAddress);
                        historyList.setPort(Integer.parseInt(ipPort));
                        historyList.setStatus(false);
                        if (!clientResponderModel.checkJunctionIfLive(ipAddress, ipPort, junctionID, program_version_no)) {
                            //System.out.println("Async :serverSocket.isClosed()------" + closedStatus);
                            this.clientResponderModel.insertRecord(historyList);
                            //j = 1;
                        }

                        if ((functionNo != 1 && functionNo != 11) ? clientResponderModel.checkJunctionId(junctionID) ? true : functionNo == 9 ? true : false : true) {

                            if (!clientResponderModel.checkJunctionIfLive(ipAddress, ipPort, junctionID, program_version_no)) {
                                if (functionNo != 4) {
                                    if (checkIfTestRequest(bytes, firstStartDataPosition)) {
                                        setRequestForActivity(false);
                                        setRequestForClearance(false);
                                        testRequestFromJunction = true;

                                    }
                                }
                                if (clientResponderModel.checkJunctionLastSynchronisation(ipAddress, ipPort, junctionID, program_version_no, testRequestFromJunction)) {
                                    confirmRegistration(junctionID, program_version_no, testRequestFromJunction);
                                }
                            } else {
                                isJunctionLive = true;
                            }
                            if (functionNo == 8 ? isJunctionLive : true) {
                                if (functionNo == 1) {
                                    if (this.clientResponderModel.isValidJunction(junctionID, program_version_no)) {

                                        res = doRegistration(bytes, firstStartDataPosition);
                                        try {
                                            // System.out.println("register status response" + res);
                                        } catch (Exception ex) {
                                            System.out.println("Error - " + ex);
                                        }
                                        sendResponse(res);
                                    } else {
                                        res = checkRegistration(bytes, firstStartDataPosition);
                                        // System.out.println("register modem response" + res);

                                        sendResponse(res);
                                    }

                                } else if (functionNo == 2) {
                                    res = slaveFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);

                                    sendResponse(res);
                                } else if (functionNo == 3) {

                                    res = JunctionSideNames(bytes, firstStartDataPosition);
                                    //System.out.println(res);

                                    sendJunctionResponse(res);
                                } else if (functionNo == 4) {
                                    res = planFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);

                                    sendResponse(res);
                                } else if (functionNo == 5) {
                                    res = SunriseSunsetDetails(bytes, firstStartDataPosition);
                                    //System.out.println(res);

                                    sendResponse(res);

                                } else if (functionNo == 8) {

                                    res = JunctionRefreshFunction(bytes, firstStartDataPosition, testRequestFromJunction);

                                    //System.out.println(res);
                                    //sendResponse(res);
                                } else if (functionNo == 9) {
                                    res = getJunctionTime(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 10) {
                                    res = phaseFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 11) {
                                    res = junctionFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 12) {
                                    res = DateFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 13) {
                                    res = DayFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 14) {
                                    res = planDetailFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 15) {
                                    res = junctionPlanMapDateFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 16) {
                                    res = junctionPlanMapDayFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 17) {
                                    res = phaseMapFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else if (functionNo == 18) {
                                    res = SideDataFunction(bytes, firstStartDataPosition);
                                    //System.out.println(res);
                                    sendResponse(res);

                                } else {
                                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Sent Error: Invalid function no  @@@@@@@@@@@@@@@@@@@@@@");
                                    sendErrorResponse(6);
                                }
                            } else {
                                System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Sent Error: Junction is not live  @@@@@@@@@@@@@@@@@@@@@@");
                                sendErrorResponse(10);
                            }
                        } else {
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Sent Error: junction_id not found  @@@@@@@@@@@@@@@@@@@@@@");
                            sendErrorResponse(5);
                        }
//                        }
//                        else {
//                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Sent Error: Crc not matched @@@@@@@@@@@@@@@@@@@@@@");
//                            sendErrorResponse(4);
//                        }
                    } else {
                        if (initialflag2 == 1) {
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Sent Error: Last delimiters not found  @@@@@@@@@@@@@@@@@@@@@@");
                            sendErrorResponse(3);
                        } else {
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Error: Delimiters not found  @@@@@@@@@@@@@@@@@@@@@@");
                            // sendResponse("126 126 126 126 16 2 4 8 0 0 0 0 125 125");
                        }
                    }
                } else {
                    if (initialflag2 == 1) {
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Sent Error: Data length not matched  @@@@@@@@@@@@@@@@@@@@@@");
                        sendErrorResponse(2);
                    } else {
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Error: corrupted data according to data length and single starting byte  @@@@@@@@@@@@@@@@@@@@@@");
                        // sendResponse("126 126 126 126 16 2 4 8 0 0 0 0 125 125");
                    }
                }
            } else {
                try {
                    for (int i = minimumDatabytes; i < (bytes.length) - 1 && endflag2 != 1; i++) {
                        if (bytes[i] == 125) {
                            if (bytes[i + 1] == 125) {
                                endflag2 = 1;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ClientResponseReader readClientResponse last delimiter Exception: " + e);
                }
                if (endflag2 == 1) {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Sent Error: Starting delimiters not found  @@@@@@@@@@@@@@@@@@@@@@");
                    sendErrorResponse(1);
                } else {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Delimiters not found  @@@@@@@@@@@@@@@@@@@@@@");
                    // sendResponse("126 126 126 126 16 2 4 8 0 0 0 0 125 125");
                }

            }
            //read = this.inputStream.read(bytes);
            //}
        } catch (IOException ioEx) {
            this.clientSocket.close();
            isConnectionClosed = true;
            System.out.println("ClientResponseReader readClientResponse() Error: " + ioEx);
        }
        //setConnectedIpList();
        return res;//!isConnectionClosed;
    }

    public boolean validateDataByCrc(byte[] bytes, int firstStartDataPosition, int receivedDataSize) {

        int calculatedCrc = 0;
        int receivedCrc = 0;
        int dSum = 0;
        try {
            for (int i = firstStartDataPosition; i < (firstStartDataPosition + receivedDataSize); i++) {
                int d = bytes[i];
                dSum += d;
            }
            calculatedCrc = (0xFF - (dSum & 0xFF)); // get lowest 8-bit and subtract lowest 8-bit from 255
            receivedCrc = bytes[firstStartDataPosition + receivedDataSize] & 0xFF;
            System.out.println("Receivd crc: " + receivedCrc + "  and calculatedCrc: " + calculatedCrc);
        } catch (Exception e) {
            System.out.println("Exception in ClientResponder validateData: " + e);
        }
        return (calculatedCrc == receivedCrc);
    }

    public boolean matchDataLength(int dataLength, int functionNo) {
        System.out.println("DataLength: " + dataLength);
        System.out.println("Function Number: " + functionNo);
        if (functionNo == 1) {
            return (dataLength == 19 || dataLength == 22 || dataLength == 31 || dataLength == 34);
        } else if (functionNo == 2) {
            return (dataLength == 12 || dataLength == 13);
        } else if (functionNo == 3) {
            System.out.println("DataLength: " + dataLength);
            System.out.println("Function Number: " + functionNo);
            return (dataLength == 4 || dataLength == 16);
        } else if (functionNo == 4) {
            return (dataLength == 5 || dataLength == 20 || dataLength == 24 || dataLength == 17 || dataLength == 32);
        } else if (functionNo == 5) {
            return (dataLength == 5);
        } else if (functionNo == 6) {
            return (dataLength == 5);
        } else if (functionNo == 7) {
            return (dataLength == 5);
        } else if (functionNo == 8) {
            return (dataLength == 19 || dataLength == 31 || dataLength == 56 || dataLength == 66);
        } else if (functionNo == 9) {
            return (dataLength == 9 || dataLength == 21);
        } else if (functionNo == 10) {
            return (dataLength == 18 || dataLength == 31);
        } else if (functionNo == 11) {
            return (dataLength == 31);
        } else if (functionNo == 12) {
            return (dataLength == 14);
        } else if (functionNo == 13) {
            return (dataLength == 14);
        } else if (functionNo == 14) {
            return (dataLength == 17);
        } else if (functionNo == 15) {
            return (dataLength == 18);
        } else if (functionNo == 16) {
            return (dataLength == 18);
        } else if (functionNo == 17) {
            return (dataLength == 18);
        } else if (functionNo == 18) {
            return (dataLength == 14);
        } else {
            return false;
        }
    }

    public boolean checkIfTestRequest(byte[] dataToProcess, int firstStartDataPosition) {
        boolean result = false;

        int side1Time = dataToProcess[firstStartDataPosition + 4];
        int side2Time = dataToProcess[firstStartDataPosition + 5];
        int side3Time = dataToProcess[firstStartDataPosition + 6];
        int side4Time = dataToProcess[firstStartDataPosition + 7];

        int unsignedSide13 = dataToProcess[firstStartDataPosition + 8];
        int unsignedSide24 = dataToProcess[firstStartDataPosition + 9];

        int unsignedMiscByte = dataToProcess[firstStartDataPosition + 10];

        if (side1Time == 1 && side2Time == 1 && side3Time == 1 && side4Time == 1
                && unsignedSide13 == 1 && unsignedSide24 == 1 && unsignedMiscByte == 1) {
            result = true;
        }

        return result;
    }

    public String checkRegistration(byte[] dataToProcess, int firstStartDataPosition) {
        String imeiNo = "";
        String response = "";
        String extraBytes = "";
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];

            for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 18); i++) {
                char ch = (char) dataToProcess[i];
                imeiNo = imeiNo + ch;
            }

            for (int i = (firstStartDataPosition + 19); i <= (firstStartDataPosition + 30); i++) {
                //char ch = (char) dataToProcess[i];
                if (extraBytes == "") {
                    extraBytes = extraBytes + dataToProcess[i];
                } else {
                    extraBytes = extraBytes + " " + dataToProcess[i];
                }

            }

            //int crc = dataToProcess[firstStartDataPosition + 19] & 0xFF;
            int crc = dataToProcess[firstStartDataPosition + 31] & 0xFF;
            //int firstLastDelimiter = dataToProcess[firstStartDataPosition + 20];
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 32];
            //int secLastDelimiter = dataToProcess[firstStartDataPosition + 21];
            int secLastDelimiter = firstLastDelimiter;

            int junctIDFromDB = this.clientResponderModel.getJunctionID(imeiNo);
            int programVersionNoFromDB = junctIDFromDB == 0 ? 0 : this.clientResponderModel.getProgramVersionNo(junctIDFromDB);

            int registrationStatus = junctIDFromDB == 0 ? 0 : this.clientResponderModel.updateJunctionTransferredStatus(junctionID, program_version_no, "NO") ? 0 : 0;

            int noOfPlans = this.clientResponderModel.getNoOfPlans(junctIDFromDB, programVersionNoFromDB);
            int noOfSides = this.clientResponderModel.getNoOfSides(junctIDFromDB, programVersionNoFromDB);
            int pedestrianTime = this.clientResponderModel.getPedestrianTime(junctIDFromDB, programVersionNoFromDB);
            int fileNoFromDB = this.clientResponderModel.getFileNo(junctIDFromDB, programVersionNoFromDB);

            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctIDFromDB, fileNo, programVersionNoFromDB);
            }
            fileNoFromDB = this.clientResponderModel.getFileNo(junctIDFromDB, programVersionNoFromDB);

            System.out.println("register modem request--- FirstStartDelimliter=" + firstStartDelimiter + " SecondStartDelimliter=" + secStartDelimiter + " dataSize1=" + dataSize1 + " dataSize2=" + dataSize2 + " junctionID=" + junctionID + " program_version_no=" + program_version_no + " file_no=" + fileNo + " functionNo=" + functionNo + " imeiNo= " + imeiNo + " crc=" + crc + " firstLastDelimiter= " + firstLastDelimiter + " secLastDelimiter= " + secLastDelimiter);
            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctIDFromDB + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + registrationStatus + " " + noOfSides + " " + noOfPlans + " " + pedestrianTime + " " + extraBytes + " " + firstLastDelimiter + " " + firstLastDelimiter;
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("ClientResponder checkRegistration exception: " + e);
        }
        return response;
    }

    public String doRegistration(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        String imeiNo = "";
        String extraByte = "";
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];
            for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 18); i++) {
                char ch = (char) dataToProcess[i];
                imeiNo = imeiNo + ch;
            }
            int noOfSides = dataToProcess[firstStartDataPosition + 19];
            int noOfPlans = dataToProcess[firstStartDataPosition + 20];
            int pedestrianTime = dataToProcess[firstStartDataPosition + 21];

            for (int i = (firstStartDataPosition + 22); i <= (firstStartDataPosition + 33); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
            int crc = dataToProcess[firstStartDataPosition + 34] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 35];
            int secLastDelimiter = firstLastDelimiter;

            int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(imeiNo, junctionID);
            int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);
            int noOfPlansFromDB = this.clientResponderModel.getNoOfPlans(junctionID, programVersionNoFromDB);
            int noOfSidesFromDB = this.clientResponderModel.getNoOfSides(junctionID, programVersionNoFromDB);
            int pedestrianTimeFromDB = this.clientResponderModel.getPedestrianTime(junctionID, programVersionNoFromDB);

            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctionID, fileNo, programVersionNoFromDB);
            }
            fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);
            Junction juncID = (Junction) this.junctionList.get(Integer.valueOf(junctionID));
            int registrationStatus = 0;
            if (noOfPlans == noOfPlansFromDB && noOfSides == noOfSidesFromDB && pedestrianTime == pedestrianTimeFromDB) {
                if (program_version_no != 127 && program_version_no != 0) {
                    if (program_version_no != programVersionNoFromDB) {
                        registrationStatus = 1;
                    } else {
                        if (!this.clientResponderModel.checkJunctionIfLive(ipAddress, ipPort, junctionID, program_version_no)) {
                            confirmRegistration(junctionID, program_version_no, false);
                        }
                        registrationStatus = 2;
                        juncID.setRegistration_status(true);

                    }
                }
            } else {
                registrationStatus = 0;
            }

            System.out.println("acknowledgement request--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + imeiNo + " " + noOfPlans + " " + noOfSides + " " + pedestrianTime + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);

            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + registrationStatus + " " + noOfSidesFromDB + " " + noOfPlansFromDB + " " + pedestrianTimeFromDB + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
        } catch (Exception e) {
            System.out.println("ClientResponder doRegistration exception: " + e);
        }

        //System.out.println("Response: " + response);
        return response;
    }

    private boolean confirmRegistration(int junctionID, int program_version_no, boolean testRequestFromJunction) {
        boolean result = false;
        try {
            boolean updateJunctionTransferredStatus = testRequestFromJunction ? true : this.clientResponderModel.updateJunctionTransferredStatus(junctionID, program_version_no, "YES");
            if (updateJunctionTransferredStatus == true) {
                setIsJunctionLive(true);
                //new Thread(this.async).start();
                async.setIpAddress(ipAddress);
                async.setIpPort(ipPort);
                async.setClientResponderModel(clientResponderModel);
                this.async.run();
                System.out.println("*********************ASYNC STARTED******************");
                result = true;
            }
        } catch (Exception e) {
            result = false;
            setIsJunctionLive(false);
            System.out.println("Error - clientResponder - " + e);
        }
        return result;
    }

    private String planFunction(byte[] dataToProcess, int firstStartDataPosition) {
        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int planNo = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);
        if ((dataToProcess[firstStartDataPosition + 18] == 125) && (dataToProcess[firstStartDataPosition + 4] != 127)) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            crc = dataToProcess[firstStartDataPosition + 17] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 18];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("plan function first request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + planNo + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            try {
                String strPlanMode = this.clientResponderModel.getPlanMode(planNo, junctionID, program_version_no);
                String planMode = "0";
                if (strPlanMode.equals("Blinker")) {
                    planMode = "1";
                } else if (strPlanMode.equals("Signal")) {
                    if (!this.clientResponderModel.isPedestrian(junctionID, program_version_no)) {
                        planMode = "2";
                    } else {
                        planMode = "3";
                    }
                }

                int phaseNo = this.clientResponderModel.getPhaseNumber(planNo, junctionID, program_version_no);
                List planTimings = this.clientResponderModel.getPlanTiming(planNo, junctionID);
                int onTimeHrFromDB = ((Integer) planTimings.get(0)).intValue();
                int onTimeMinFromDB = ((Integer) planTimings.get(1)).intValue();
                int offTimeHrFromDB = ((Integer) planTimings.get(2)).intValue();
                int offTimeMinFromDB = ((Integer) planTimings.get(3)).intValue();

                int noOfSides = this.clientResponderModel.getNoOfSides(junctionID, program_version_no);
                List sideGreenlist = new ArrayList();
                List sideAmberlist = new ArrayList();
                for (int i = 1; i <= 5; i++) {
                    int sideGreenTime = this.clientResponderModel.getPlanGreenTime(i, planNo, junctionID, program_version_no);
                    int sideAmberTime = this.clientResponderModel.getPlanAmberTime(i, planNo, junctionID, program_version_no);
                    sideGreenlist.add(Integer.valueOf(sideGreenTime));
                    sideAmberlist.add(Integer.valueOf(sideAmberTime));
                }

                for (int i = (firstStartDataPosition + 5); i <= (firstStartDataPosition + 16); i++) {
                    if (extraByte == "") {
                        extraByte = extraByte + dataToProcess[i];
                    } else {
                        extraByte = extraByte + " " + dataToProcess[i];
                    }
                }
                responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + onTimeHrFromDB + " " + onTimeMinFromDB + " " + offTimeHrFromDB + " " + offTimeMinFromDB;
                Iterator itr = sideGreenlist.iterator();
                while (itr.hasNext()) {
                    responseVal = responseVal + " " + (Integer) itr.next();
                }

                itr = null;
                itr = sideAmberlist.iterator();
                while (itr.hasNext()) {
                    responseVal = responseVal + " " + (Integer) itr.next();
                }
                responseVal = responseVal + " " + planMode + " " + phaseNo + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        } else if (dataToProcess[firstStartDataPosition + 18] != 125) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            int onTimeHR = dataToProcess[firstStartDataPosition + 5];
            int onTimeMin = dataToProcess[firstStartDataPosition + 6];
            int offTimeHr = dataToProcess[firstStartDataPosition + 7];
            int offTimeMin = dataToProcess[firstStartDataPosition + 8];

            int side1GT = dataToProcess[firstStartDataPosition + 9];
            int side2GT = dataToProcess[firstStartDataPosition + 10];
            int side3GT = dataToProcess[firstStartDataPosition + 11];
            int side4GT = dataToProcess[firstStartDataPosition + 12];
            int side5GT = dataToProcess[firstStartDataPosition + 13];

            int side1AT = dataToProcess[firstStartDataPosition + 14];
            int side2AT = dataToProcess[firstStartDataPosition + 15];
            int side3AT = dataToProcess[firstStartDataPosition + 16];
            int side4AT = dataToProcess[firstStartDataPosition + 17];
            int side5AT = dataToProcess[firstStartDataPosition + 18];

            int mode = dataToProcess[firstStartDataPosition + 19];
            int phaseNo = dataToProcess[firstStartDataPosition + 20];
            for (int i = (firstStartDataPosition + 21); i <= (firstStartDataPosition + 31); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
            crc = dataToProcess[firstStartDataPosition + 32] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 33];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 22];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("plan function first request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo
                    + " " + planNo + " " + onTimeHR + " " + onTimeMin + " " + offTimeHr + " " + offTimeMin
                    + " " + side1GT + " " + side2GT + " " + side3GT + " " + side4GT + " " + side5GT
                    + " " + side1AT + " " + side2AT + " " + side3AT + " " + side4AT + " " + side5AT + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            try {
                String strPlanMode = this.clientResponderModel.getPlanMode(planNo, junctionID, program_version_no);
                String planMode = "0";
                if (strPlanMode.equals("Blinker")) {
                    planMode = "1";
                } else if (strPlanMode.equals("Signal")) {
                    if (!this.clientResponderModel.isPedestrian(junctionID, program_version_no)) {
                        planMode = "2";
                    } else {
                        planMode = "3";
                    }
                }
                List planTimings = this.clientResponderModel.getPlanTiming(planNo, junctionID);
                int onTimeHrFromDB = ((Integer) planTimings.get(0)).intValue();
                int onTimeMinFromDB = ((Integer) planTimings.get(1)).intValue();
                int offTimeHrFromDB = ((Integer) planTimings.get(2)).intValue();
                int offTimeMinFromDB = ((Integer) planTimings.get(3)).intValue();
                int phaseNoR = this.clientResponderModel.getPhaseNumber(planNo, junctionID, program_version_no);
                int noOfSides = this.clientResponderModel.getNoOfSides(junctionID, program_version_no);
                List sideGreenlist = new ArrayList();
                List sideAmberlist = new ArrayList();
                for (int i = 1; i <= 5; i++) {
                    int sideGreenTime = this.clientResponderModel.getPlanGreenTime(i, planNo, junctionID, program_version_no);
                    int sideAmberTime = this.clientResponderModel.getPlanAmberTime(i, planNo, junctionID, program_version_no);
                    sideGreenlist.add(Integer.valueOf(sideGreenTime));
                    sideAmberlist.add(Integer.valueOf(sideAmberTime));
                }
                Iterator itr = sideGreenlist.iterator();
                String GT = "";
                while (itr.hasNext()) {
                    GT = GT + " " + (Integer) itr.next();
                }
                String[] GTsplit = GT.split(" ", GT.length());
                itr = null;
                itr = sideAmberlist.iterator();
                String AT = "";
                while (itr.hasNext()) {
                    AT = AT + " " + (Integer) itr.next();
                }
                int planStatus = 0;
                String[] ATsplit = AT.split(" ", AT.length());
                if ((onTimeHR == onTimeHrFromDB) && (onTimeMin == onTimeMinFromDB) && (offTimeHr == offTimeHrFromDB)
                        && (offTimeMin == offTimeMinFromDB) && (side1GT == Integer.parseInt(GTsplit[1]))
                        && (side2GT == Integer.parseInt(GTsplit[2])) && (side3GT == Integer.parseInt(GTsplit[3]))
                        && (side4GT == Integer.parseInt(GTsplit[4])) && (side5GT == Integer.parseInt(GTsplit[5]))
                        && (side1AT == Integer.parseInt(ATsplit[1])) && (side2AT == Integer.parseInt(ATsplit[2]))
                        && (side3AT == Integer.parseInt(ATsplit[3])) && (side4AT == Integer.parseInt(ATsplit[4]))
                        && (side5AT == Integer.parseInt(ATsplit[5])) && (mode == Integer.parseInt(planMode)) && (phaseNo == phaseNoR)) {
                    planStatus = 1;
                    boolean updatePlanTransferredStatus = this.clientResponderModel.updatePlanTransferredStatus(junctionID, program_version_no, planNo);
                    if (updatePlanTransferredStatus == true) {
                        responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + planStatus + " " + phaseNo + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
                    }
                } else {
                    responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + planStatus + " " + phaseNo + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
                }
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        }

//        Junction juncID = (Junction) this.junctionList.get(Integer.valueOf(junctionID));
//        boolean regisStatus = juncID.isRegistration_status();
        return responseVal;
    }

    public String getJunctionTime(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        String extraBytes = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int juncHr = dataToProcess[firstStartDataPosition + 4];
        int juncMin = dataToProcess[firstStartDataPosition + 5];
        int juncDat = dataToProcess[firstStartDataPosition + 6];
        int juncMonth = dataToProcess[firstStartDataPosition + 7];
        int juncYear = dataToProcess[firstStartDataPosition + 8];
        for (int i = (firstStartDataPosition + 9) + 1; i <= (firstStartDataPosition + 20); i++) {
            //char ch = (char) dataToProcess[i];
            if (extraBytes == "") {
                extraBytes = extraBytes + dataToProcess[i];
            } else {
                extraBytes = extraBytes + " " + dataToProcess[i];
            }

        }
        int crc = dataToProcess[firstStartDataPosition + 21] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 22];
        //int secLastDelimiter = dataToProcess[firstStartDataPosition + 11];
        int secLastDelimiter = firstLastDelimiter;
        int appHr = 0, appMin = 0, appDat = 0, appMonth = 0, appYear = 0;
        Calendar cald = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss EEEE");
        String dateTime = sd.format(cald.getTime());

        String appDate = dateTime.split(" ")[0];
        String appTime = dateTime.split(" ")[1];
        String appWeekDay = dateTime.split(" ")[2];
        System.out.println(appWeekDay);
        String time[] = appTime.split(":");
        String dte[] = appDate.split("-");

        appHr = Integer.parseInt(time[0]);
        appMin = Integer.parseInt(time[1]);

        appDat = Integer.parseInt(dte[2]);
        appMonth = Integer.parseInt(dte[1]);
        appYear = Integer.parseInt(dte[0].substring(2));
        int dayNo = 0;
        switch (appWeekDay) {
            case "Monday":
                dayNo = 1;
                break;
            case "Tuesday":
                dayNo = 2;
                break;
            case "WednesDay":
                dayNo = 3;
                break;
            case "Thursday":
                dayNo = 4;
                break;
            case "Friday":
                dayNo = 5;
                break;
            case "Saturday":
                dayNo = 6;
                break;
            case "Sunday":
                dayNo = 7;
                break;
        }

        /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/
        int monthNo = (juncMonth - appMonth) == Math.abs(1) ? juncMonth > appMonth ? appMonth : juncMonth : 0;

        if (((juncYear - appYear) == Math.abs(1)) && ((juncMonth - appMonth) == Math.abs(11)) && ((juncDat - appDat) == Math.abs(30)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of last month of a year
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
            juncMonth = appMonth;
            juncYear = appYear;
        } else if (((juncMonth - appMonth) == Math.abs(1)) && ((juncDat - appDat) == Math.abs(getNoOfDays(monthNo, appYear) - 1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of a month
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
            juncMonth = appMonth;
        } else if (((juncDat - appDat) == Math.abs(1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of a day
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
        } else if (((juncHr - appHr) == Math.abs(1)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of an hour
            juncHr = appHr;
            juncMin = appMin;
        } else if ((juncMin - appMin) == Math.abs(1)) { //a minute
            juncMin = appMin;
        }
        /*---------------------------- code endeded to manage one minute difference in junction time and in application time  ------------------------------------------*/

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB && fileNoFromDB != 0) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        this.clientResponderModel.synchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);
        String currentTimeSynchronizationStatus = this.clientResponderModel.getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);

        int currTimeSynchronizationStatus = currentTimeSynchronizationStatus.equals("Y") ? 1 : 0;

        System.out.println("junction time info request--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + juncHr + " " + juncMin + " " + juncDat + " " + juncMonth + " " + juncYear + " " + extraBytes + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);

        response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + appHr + " " + appMin + " " + appDat + " " + appMonth + " " + appYear + " " + currTimeSynchronizationStatus + " " + dayNo + " " + extraBytes + " " + firstLastDelimiter + " " + firstLastDelimiter;

        //System.out.println("Response of junction info:" + response);
        return response;
    }

    private String slaveFunction(byte[] dataToProcess, int firstStartDataPosition) {
        String responseVal = null;
        String slave_id = "";
        int db_side_no = 0;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 11); i++) {
            char ch = (char) dataToProcess[i];
            slave_id = slave_id + ch;
        }

        db_side_no = this.clientResponderModel.getSideNo(junctionID, program_version_no, slave_id);

        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);
        if ((dataToProcess[firstStartDataPosition + 13] == 125)) { //request from modem for slave detail
            crc = dataToProcess[firstStartDataPosition + 12];
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 13];
            secLastDelimiter = dataToProcess[firstStartDataPosition + 14];
            System.out.println("junction slave request--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + slave_id + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + slave_id + " " + db_side_no + " " + firstLastDelimiter + " " + firstLastDelimiter;

        } else if (dataToProcess[firstStartDataPosition + 14] == 125) { //slave detail confirmation request from modem
            int side_no = dataToProcess[firstStartDataPosition + 12];
            int status = 0;
            crc = dataToProcess[firstStartDataPosition + 13];
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 14];
            secLastDelimiter = dataToProcess[firstStartDataPosition + 15];
            System.out.println("junction slave request--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + slave_id + " " + side_no + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);

            if (side_no == db_side_no) {
                try {
                    boolean result = this.clientResponderModel.updateSlaveTransferredStatus(junctionID, program_version_no, side_no);
                    status = result ? 1 : 0;
                } catch (Exception e) {
                    System.out.println("Error - clientResponder - " + e);
                }
            }
            responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + slave_id + " " + db_side_no + " " + status + " " + firstLastDelimiter + " " + firstLastDelimiter;
        }
        return responseVal;
    }

//    
    /*String JunctionRefreshFunction(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
        String response = null;
        String extraBytes = "";
        PlanInfo planInfo = new PlanInfo();
        TeenPatti_Info teenPattiInfo = new TeenPatti_Info();
        DamohNakaInfo damohNakaInfo = new DamohNakaInfo();
        RanitalInfo ranitalInfo = new RanitalInfo();
        YatayatThanaInfo yatayatThanaInfo = new YatayatThanaInfo();
        KatangaInfo katangaInfo = new KatangaInfo();
        BaldeobagInfo baldeobagInfo = new BaldeobagInfo();
        DeendayalChowkInfo deendayalChowkInfo = new DeendayalChowkInfo();
        HighCourtInfo highCourtInfo = new HighCourtInfo();
        BloomChowkInfo bloomChowkInfo = new BloomChowkInfo();
        MadanMahalInfo madanMahalInfo = new MadanMahalInfo();

        GohalPurInfo gohalPurInfo = new GohalPurInfo();
        TrafficSignalWebServices tsws = new TrafficSignalWebServices();
        String[] side13 = null;
        String[] side24 = null;
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];
            String currentTimeSynchronizationStatus = "Y";

            int side1Time = dataToProcess[firstStartDataPosition + 4] & 0xFF;
            int side2Time = dataToProcess[firstStartDataPosition + 5] & 0xFF;
            int side3Time = dataToProcess[firstStartDataPosition + 6] & 0xFF;
            int side4Time = dataToProcess[firstStartDataPosition + 7] & 0xFF;
            int side5Time = dataToProcess[firstStartDataPosition + 8] & 0xFF;

            int unsignedSide13 = dataToProcess[firstStartDataPosition + 9] & 0xFF;
            int unsignedSide24 = dataToProcess[firstStartDataPosition + 10] & 0xFF;
            int unsignedSide5 = dataToProcess[firstStartDataPosition + 11] & 0xFF;

            int unsignedMiscByte = dataToProcess[firstStartDataPosition + 12] & 0xFF;

            int plan_no = dataToProcess[firstStartDataPosition + 13];
            int plan_mode = dataToProcess[firstStartDataPosition + 14];
            int activity = dataToProcess[firstStartDataPosition + 15];
            int activity1 = 1;
            int activity2 = 1;
            int activity3 = 1;
            int receivedActivity = activity;
            // String currentTimeSynchronizationStatus="Y";
            // data at firstStartDataPosition + 13/14/15 are default 1, when activity byte value is 2(clearance) then only firstStartDataPosition + 13 byte value will be used as clearance side.

            String onTime = this.clientResponderModel.getPlanOnTime(junctionID, program_version_no, plan_no);
            String offTime = this.clientResponderModel.getPlanOffTime(junctionID, program_version_no, plan_no);

            int juncHr = dataToProcess[firstStartDataPosition + 16];
            int juncMin = dataToProcess[firstStartDataPosition + 17];
            int juncDat = dataToProcess[firstStartDataPosition + 18];
            int juncMonth = dataToProcess[firstStartDataPosition + 19];
            int juncYear = dataToProcess[firstStartDataPosition + 20];
            int phase_no = dataToProcess[firstStartDataPosition + 21];

            int primary_side_1 = dataToProcess[firstStartDataPosition + 22];// & 0xFF;
            int secondary_side_1 = dataToProcess[firstStartDataPosition + 23];// & 0xFF;
            int hunderedth_place_side_1 = dataToProcess[firstStartDataPosition + 24];// & 0xFF;
            int tenth_place_side_1 = dataToProcess[firstStartDataPosition + 25];// & 0xFF;
            int once_place_side_1 = dataToProcess[firstStartDataPosition + 25];// & 0xFF;

            int primary_side_2 = dataToProcess[firstStartDataPosition + 27];// & 0xFF;
            int secondary_side_2 = dataToProcess[firstStartDataPosition + 28];// & 0xFF;
            int hunderedth_place_side_2 = dataToProcess[firstStartDataPosition + 29];// & 0xFF;
            int tenth_place_side_2 = dataToProcess[firstStartDataPosition + 30];// & 0xFF;
            int once_place_side_2 = dataToProcess[firstStartDataPosition + 31];// & 0xFF;

            int primary_side_3 = dataToProcess[firstStartDataPosition + 32];// & 0xFF;
            int secondary_side_3 = dataToProcess[firstStartDataPosition + 33];// & 0xFF;
            int hunderedth_place_side_3 = dataToProcess[firstStartDataPosition + 34];// & 0xFF;
            int tenth_place_side_3 = dataToProcess[firstStartDataPosition + 35];// & 0xFF;
            int once_place_side_3 = dataToProcess[firstStartDataPosition + 36];// & 0xFF;

            int primary_side_4 = dataToProcess[firstStartDataPosition + 37];// & 0xFF;
            int secondary_side_4 = dataToProcess[firstStartDataPosition + 38];// & 0xFF;
            int hunderedth_place_side_4 = dataToProcess[firstStartDataPosition + 39];// & 0xFF;
            int tenth_place_side_4 = dataToProcess[firstStartDataPosition + 40];// & 0xFF;
            int once_place_side_4 = dataToProcess[firstStartDataPosition + 41];// & 0xFF;

            int primary_side_5 = dataToProcess[firstStartDataPosition + 42];// & 0xFF;
            int secondary_side_5 = dataToProcess[firstStartDataPosition + 43];// & 0xFF;
            int hunderedth_place_side_5 = dataToProcess[firstStartDataPosition + 44];// & 0xFF;
            int tenth_place_side_5 = dataToProcess[firstStartDataPosition + 45];// & 0xFF;
            int once_place_side_5 = dataToProcess[firstStartDataPosition + 46];// & 0xFF;

            int powerStatus = dataToProcess[firstStartDataPosition + 47];
            int communicationStatus = dataToProcess[firstStartDataPosition + 48];

            for (int i = (firstStartDataPosition + 49); i <= (firstStartDataPosition + 55); i++) {
                //char ch = (char) dataToProcess[i];
                if (extraBytes == "") {
                    extraBytes = extraBytes + dataToProcess[i];
                } else {
                    extraBytes = extraBytes + " " + dataToProcess[i];
                }

            }
            int crc = dataToProcess[firstStartDataPosition + 56] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 57];
            int secLastDelimiter = firstLastDelimiter;

            boolean match = this.clientResponderModel.sideColorMatch(junctionID, plan_no, phase_no, unsignedSide13, unsignedSide24, unsignedSide5);
            if (match) {
                int unsignedSide13DB = 0;
                int unsignedSide24DB = 0;
                int unsignedSide5DB = 0;
                int primary1 = 0;
                int primary2 = 0;
                int primary3 = 0;
                int primary4 = 0;
                int primary5 = 0;
                int secondary1 = 0;
                int secondary2 = 0;
                int secondary3 = 0;
                int secondary4 = 0;
                int secondary5 = 0;
                List<Integer> sidevalue = this.clientResponderModel.getSideValue(junctionID, plan_no, phase_no);
                unsignedSide13DB = sidevalue.get(0);
                unsignedSide24DB = sidevalue.get(1);
                unsignedSide5DB = sidevalue.get(2);

                //side1
                int poleType = this.clientResponderModel.getPoleType(junctionID, program_version_no, 1);
                String side1 = this.clientResponderModel.decToBinaryAndSplitFirst(unsignedSide13DB);
                String side2 = this.clientResponderModel.decToBinaryAndSplitFirst(unsignedSide24DB);
                String side3 = this.clientResponderModel.decToBinaryAndSplitLater(unsignedSide13DB);
                String side4 = this.clientResponderModel.decToBinaryAndSplitLater(unsignedSide24DB);
                switch (poleType) {
                    case 1:
                        primary1 = Integer.getInteger(side1 + "0000", 2);
                        secondary1 = Integer.getInteger(side1 + "0000", 2);
                        primary2 = Integer.getInteger(side2 + "0000", 2);
                        secondary2 = Integer.getInteger(side2 + "0000", 2);
                        primary3 = Integer.getInteger(side3 + "0000", 2);
                        secondary3 = Integer.getInteger(side3 + "0000", 2);
                        primary4 = Integer.getInteger(side4 + "0000", 2);
                        secondary4 = Integer.getInteger(side4 + "0000", 2);

                        matchSignal(Integer.toBinaryString(primary_side_1), Integer.toBinaryString(primary_side_2), Integer.toBinaryString(primary_side_3), Integer.toBinaryString(primary_side_4),
                                Integer.toBinaryString(secondary_side_1), Integer.toBinaryString(secondary_side_2), Integer.toBinaryString(secondary_side_3), Integer.toBinaryString(secondary_side_4),
                                Integer.toBinaryString(primary1), Integer.toBinaryString(primary2), Integer.toBinaryString(primary3), Integer.toBinaryString(primary4),
                                Integer.toBinaryString(secondary1), Integer.toBinaryString(secondary2), Integer.toBinaryString(secondary3), Integer.toBinaryString(secondary4), junctionID);

                        break;
                    case 2:
                        primary1 = Integer.getInteger(side1 + side1, 2);
                        secondary1 = Integer.getInteger(side1 + side1, 2);
                        primary2 = Integer.getInteger(side2 + side2, 2);
                        secondary2 = Integer.getInteger(side2 + side2, 2);
                        primary3 = Integer.getInteger(side3 + side3, 2);
                        secondary3 = Integer.getInteger(side3 + side3, 2);
                        primary4 = Integer.getInteger(side4 + side4, 2);
                        secondary4 = Integer.getInteger(side4 + side4, 2);
                        break;
                    case 3:
                        primary1 = Integer.getInteger(side1 + side1, 2);
                        secondary1 = Integer.getInteger(side1 + "0000", 2);
                        primary2 = Integer.getInteger(side2 + side2, 2);
                        secondary2 = Integer.getInteger(side2 + "0000", 2);
                        primary3 = Integer.getInteger(side3 + side3, 2);
                        secondary3 = Integer.getInteger(side3 + "0000", 2);
                        primary4 = Integer.getInteger(side4 + side4, 2);
                        secondary4 = Integer.getInteger(side4 + "0000", 2);
                        break;
                    default:
                        break;
                }
                if (primary_side_1 == primary1) {
                    if (secondary_side_1 != secondary1) {
                        System.out.println("Secondary side 1 not matched");
                    }
                } else {
                    System.out.println("Primary side 1 not matched");
                }

                if (primary_side_2 == primary2) {
                    if (secondary_side_2 != secondary2) {
                        System.out.println("Secondary side 2 not matched");
                    }
                } else {
                    System.out.println("Primary side 2 not matched");
                }

                if (primary_side_3 == primary3) {
                    if (secondary_side_3 != secondary3) {
                        System.out.println("Secondary side 3 not matched");
                    }
                } else {
                    System.out.println("Primary side 3 not matched");
                }

                if (primary_side_4 == primary4) {
                    if (secondary_side_4 != secondary4) {
                        System.out.println("Secondary side 4 not matched");
                    }
                } else {
                    System.out.println("Primary side 4 not matched");
                }

            }

            int appHr = 0, appMin = 0, appDat = 0, appMonth = 0, appYear = 0;
            Calendar cald = Calendar.getInstance();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = sd.format(cald.getTime());

            String appDate = dateTime.split(" ")[0];
            String appTime = dateTime.split(" ")[1];

            String time[] = appTime.split(":");
            String dte[] = appDate.split("-");

            appHr = Integer.parseInt(time[0]);
            appMin = Integer.parseInt(time[1]);

            appDat = Integer.parseInt(dte[2]);
            appMonth = Integer.parseInt(dte[1]);
            appYear = Integer.parseInt(dte[0].substring(2));

            /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/
 /*int monthNo = (juncMonth - appMonth) == Math.abs(1) ? juncMonth > appMonth ? appMonth : juncMonth : 0;

            if (((juncYear - appYear) == Math.abs(1)) && ((juncMonth - appMonth) == Math.abs(11)) && ((juncDat - appDat) == Math.abs(30)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of last month of a year
                juncHr = appHr;
                juncMin = appMin;
                juncDat = appDat;
                juncMonth = appMonth;
                juncYear = appYear;
            } else if (((juncMonth - appMonth) == Math.abs(1)) && ((juncDat - appDat) == Math.abs(getNoOfDays(monthNo, appYear) - 1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of a month
                juncHr = appHr;
                juncMin = appMin;
                juncDat = appDat;
                juncMonth = appMonth;
            } else if (((juncDat - appDat) == Math.abs(1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of a day
                juncHr = appHr;
                juncMin = appMin;
                juncDat = appDat;
            } else if (((juncHr - appHr) == Math.abs(1)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of an hour
                juncHr = appHr;
                juncMin = appMin;
            } else if ((juncMin - appMin) == Math.abs(1)) { //a minute
                juncMin = appMin;
            }
            /*---------------------------- code endeded to manage one minute difference in junction time and in application time  ------------------------------------------*/
    //   String currentTimeSynchronizationStatus = this.clientResponderModel.getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);
    /*String side13Status = Integer.toBinaryString(unsignedSide13);
            String side24Status = Integer.toBinaryString(unsignedSide24);

            String miscByte = Integer.toBinaryString(unsignedMiscByte);

            int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
            }
            fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
            int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

            String junctionName = this.clientResponderModel.getJunctionName(junctionID, program_version_no);
            int noOfSides = this.clientResponderModel.getNoOfSides(junctionID, program_version_no);
            List sideNameList = new ArrayList(5);
            for (int j = 1; j <= noOfSides; j++) {
                String sideName = this.clientResponderModel.getSideName(j, junctionID, program_version_no);
                sideNameList.add(sideName);
            }

            System.out.println("refresh  request from modem--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2
                    + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + side1Time + " " + side2Time + " " + side3Time + " " + side4Time + " "
                    + unsignedSide13 + " " + unsignedSide24 + " " + unsignedMiscByte + " " + plan_no + " " + plan_mode + " " + activity + " "
                    + juncHr + " " + juncMin + " " + juncDat + " " + juncMonth + " " + juncYear + " "
                    + crc + " " + firstLastDelimiter + " " + secLastDelimiter);

            switch (side13Status.length()) {
                case 5:
                    side13Status = "000" + side13Status;
                    break;
                case 6:
                    side13Status = "00" + side13Status;
                    break;
                case 7:
                    side13Status = "0" + side13Status;
            }

            switch (side24Status.length()) {
                case 5:
                    side24Status = "000" + side24Status;
                    break;
                case 6:
                    side24Status = "00" + side24Status;
                    break;
                case 7:
                    side24Status = "0" + side24Status;
            }

            switch (miscByte.length()) {
                case 5:
                    miscByte = "000" + miscByte;
                    break;
                case 6:
                    miscByte = "00" + miscByte;
                    break;
                case 7:
                    miscByte = "0" + miscByte;
            }

            System.out.println("side13Status : " + side13Status.length() + " , " + side13Status);
            System.out.println("side24Status : " + side24Status.length() + " , " + side24Status);
            System.out.println("miscByte     : " + miscByte.length() + " , " + miscByte);

            side13 = side13Status.trim().split("");
            side24 = side24Status.trim().split("");

            if (!testRequestFromJunction) {

                planInfo.setFunction_no(functionNo);
                planInfo.setJunction_name(junctionName);
                if (noOfSides == 2) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                } else if (noOfSides == 3) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                    planInfo.setSide3Name((String) sideNameList.get(2));
                } else if (noOfSides == 4) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                    planInfo.setSide3Name((String) sideNameList.get(2));
                    planInfo.setSide4Name((String) sideNameList.get(3));
                } else if (noOfSides == 5) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                    planInfo.setSide3Name((String) sideNameList.get(2));
                    planInfo.setSide4Name((String) sideNameList.get(3));
                    planInfo.setSide5Name((String) sideNameList.get(4));
                }
                planInfo.setJunction_id(junctionID);
                planInfo.setProgram_version_no(program_version_no);
                planInfo.setFileNo(fileNo);
                planInfo.setPlan_no(plan_no);
                planInfo.setOnTime(onTime);
                planInfo.setOffTime(offTime);
                planInfo.setMode(plan_mode == 1 ? "Blinker" : plan_mode == 2 ? "Signal" : "Signal With Pedestrian");
                planInfo.setSide1_time(side1Time);
                planInfo.setSide2_time(side2Time);
                planInfo.setSide3_time(side3Time);
                planInfo.setSide4_time(side4Time);

                System.out.println("side13 size: " + side13.length);
                System.out.println("side24 size: " + side24.length);
                int beginIndex = 0;
                try {
                    if (side13.length == 9) {
                        beginIndex = 1;
                    }
                    planInfo.setSide1_left_status(Integer.parseInt(side13[beginIndex]));
                    planInfo.setSide1_right_status(Integer.parseInt(side13[beginIndex + 1]));
                    planInfo.setSide1_up_status(Integer.parseInt(side13[beginIndex + 2]));
                    planInfo.setSide1_down_status(Integer.parseInt(side13[beginIndex + 3]));
                    planInfo.setSide3_left_status(Integer.parseInt(side13[beginIndex + 4]));
                    planInfo.setSide3_right_status(Integer.parseInt(side13[beginIndex + 5]));
                    planInfo.setSide3_up_status(Integer.parseInt(side13[beginIndex + 6]));
                    planInfo.setSide3_down_status(Integer.parseInt(side13[beginIndex + 7]));

                    planInfo.setSide2_left_status(Integer.parseInt(side24[beginIndex]));
                    planInfo.setSide2_right_status(Integer.parseInt(side24[beginIndex + 1]));
                    planInfo.setSide2_up_status(Integer.parseInt(side24[beginIndex + 2]));
                    planInfo.setSide2_down_status(Integer.parseInt(side24[beginIndex + 3]));
                    planInfo.setSide4_left_status(Integer.parseInt(side24[beginIndex + 4]));
                    planInfo.setSide4_right_status(Integer.parseInt(side24[beginIndex + 5]));
                    planInfo.setSide4_up_status(Integer.parseInt(side24[beginIndex + 6]));
                    planInfo.setSide4_down_status(Integer.parseInt(side24[beginIndex + 7]));

                } catch (Exception e) {
                    System.out.println("Error in side1 2 3 4 status: " + e);
                }

                planInfo.setJuncHr(juncHr);
                planInfo.setJuncMin(juncMin);
                planInfo.setJuncDate(juncDat);
                planInfo.setJuncMonth(juncMonth);
                planInfo.setJuncYear(juncYear);

                try {
                    this.planInfoRefreshList.setResponseFromModemForRefresh(true);
                    this.ctx.setAttribute("planInfolist", this.planInfoRefreshList);
                    if (activity == 2) {
                        this.junction.setResponseFromModemForClearance(true);
                    } else {
                        this.junction.setResponseFromModemForActivity(true);
                    }
                    System.out.println("client responder RequestForClearance: " + isRequestForClearance());
                    System.out.println("client responder requestForActivity: " + isRequestForActivity());

                    this.ctx.setAttribute("junction", this.junction);
                    this.modemResReadSaveCont.setCtx(this.ctx);

                    this.requestForActivity = (Boolean) (ctx.getAttribute("requestForActivity" + junctionID) == null ? false : ctx.getAttribute("requestForActivity" + junctionID));
                    this.requestForClearance = (Boolean) (ctx.getAttribute("requestForClearance" + junctionID) == null ? false : ctx.getAttribute("requestForClearance" + junctionID));
                    this.activityNo = (Integer) (ctx.getAttribute("activityNo" + junctionID) == null ? 0 : ctx.getAttribute("activityNo" + junctionID));
                    this.activitySide = (Integer) (ctx.getAttribute("activitySide" + junctionID) == null ? 0 : ctx.getAttribute("activitySide" + junctionID));
                    this.clearanceSide = (Integer) (ctx.getAttribute("clearanceSide" + junctionID) == null ? 0 : ctx.getAttribute("clearanceSide" + junctionID));

                    activity = receivedActivity == 4 || receivedActivity == 6 ? 1 : this.requestForActivity ? this.activityNo : this.requestForClearance ? 2 : 1;
                    activity1 = receivedActivity == 4 || receivedActivity == 6 ? 1
                            : this.requestForActivity ? this.activityNo == 4 || this.activityNo == 6 ? this.activitySide : 1
                                    : this.requestForClearance ? this.clearanceSide : 1;

                    ctx.setAttribute("requestForActivity" + junctionID, false);
                    //           ctx.setAttribute("requestForClearance" + junctionID, false);
                    ctx.setAttribute("activityNo" + junctionID, 0);
                    ctx.setAttribute("activitySide" + junctionID, 0);
                    //             ctx.setAttribute("clearanceSide" + junctionID, 0);

                    planInfo.setActivity(activity);
                    planInfo.setSide_no(activity1);

                    if (receivedActivity == 4 || receivedActivity == 6) {
                        this.requestForActivity = false;
                    }

                    planInfo.setResponseFromModemForRefresh(true);

                    this.planInfoRefreshList = planInfo;
                    this.ctx.setAttribute("planInfolist", this.planInfoRefreshList);
                    if (junctionID == 2) {
                        BeanUtils.copyProperties(damohNakaInfo, planInfo);
                        damohNakaInfoRefreshList = damohNakaInfo;
                        this.ctx.setAttribute("damohNakaInfoList", this.damohNakaInfoRefreshList);
                    }
                    if (junctionID == 13) {
                        BeanUtils.copyProperties(teenPattiInfo, planInfo);
                        teenPattiInfoRefreshList = teenPattiInfo;
                        this.ctx.setAttribute("teenPattiInfoList", this.teenPattiInfoRefreshList);
                    }
                    if (junctionID == 14) {
                        BeanUtils.copyProperties(gohalPurInfo, planInfo);
                        gohalPurInfoRefreshList = gohalPurInfo;
                        this.ctx.setAttribute("gohalPurInfoList", this.gohalPurInfoRefreshList);
                    }

                    if (junctionID == 11) {
                        BeanUtils.copyProperties(ranitalInfo, planInfo);
                        ranitalInfoRefreshList = ranitalInfo;
                        this.ctx.setAttribute("ranitalInfoList", this.ranitalInfoRefreshList);
                    }
                    if (junctionID == 1) {
                        BeanUtils.copyProperties(yatayatThanaInfo, planInfo);
                        yatayatThanaInfoRefreshList = yatayatThanaInfo;
                        this.ctx.setAttribute("yatayatThanaInfoList", this.yatayatThanaInfoRefreshList);
                    }
                    if (junctionID == 4) {
                        BeanUtils.copyProperties(katangaInfo, planInfo);
                        katangaInfoRefreshList = katangaInfo;
                        this.ctx.setAttribute("katangaInfoList", this.katangaInfoRefreshList);
                    }
                    if (junctionID == 5) {
                        BeanUtils.copyProperties(baldeobagInfo, planInfo);
                        baldeobagInfoRefreshList = baldeobagInfo;
                        this.ctx.setAttribute("baldeobagInfoList", this.baldeobagInfoRefreshList);
                    }
                    if (junctionID == 6) {
                        BeanUtils.copyProperties(deendayalChowkInfo, planInfo);
                        deendayalChowkInfoRefreshList = deendayalChowkInfo;
                        this.ctx.setAttribute("deendayalChowkInfoList", this.deendayalChowkInfoRefreshList);
                    }
                    if (junctionID == 7) {
                        BeanUtils.copyProperties(highCourtInfo, planInfo);
                        highCourtInfoRefreshList = highCourtInfo;
                        this.ctx.setAttribute("highCourtInfoList", this.highCourtInfoRefreshList);
                    }
                    if (junctionID == 8) {
                        BeanUtils.copyProperties(bloomChowkInfo, planInfo);
                        bloomChowkInfoRefreshList = bloomChowkInfo;
                        this.ctx.setAttribute("bloomChowkInfoList", this.bloomChowkInfoRefreshList);
                    }
                    if (junctionID == 12) {
                        BeanUtils.copyProperties(madanMahalInfo, planInfo);
                        madanMahalInfoRefreshList = madanMahalInfo;
                        this.ctx.setAttribute("madanMahalInfoList", this.madanMahalInfoRefreshList);
                    }
                    this.ctx.setAttribute("junction", this.junction);
                    this.modemResReadSaveCont.setCtx(this.ctx);
                    //this.planInfoRefreshList = planInfo;
                } catch (Exception e) {
                    System.out.println("ClientResponder junctionRefresh PlanInfoListContext error :" + e);
                }
            }

            activity = program_version_no == programVersionNoFromDB ? fileNo == fileNoFromDB ? currentTimeSynchronizationStatus.equals("Y") ? activity : testRequestFromJunction ? 1 : 9 : 8 : 7;
            activity1 = activity == 7 || activity == 8 || activity == 9 ? 1 : activity1;

            String pureBytes = null;
            int junction_id = 0;
            // pureBytes = tsws.arr2;
            //String activeReq = clientResponderModel.getActiveRequest(junctionID);
            if (mapData() != null) {
                if (!mapData().equals("normal")) {
                    String data = mapData();
                    junction_id = Integer.parseInt(data.split("_")[0]);
                    if (junctionID == junction_id) {
                        activity = 2;
                        activity1 = Integer.parseInt(data.split("_")[1]);
                    }
                }

            }
//            

            System.out.println("activity bytes: " + activity + " " + activity1 + " " + activity2 + " " + activity3);
            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + activity + " " + activity1 + " " + activity2 + " " + activity3 + " " + firstLastDelimiter + " " + firstLastDelimiter;
            System.out.println("response ::: " + response);

        } catch (Exception e) {
            System.out.println("PlanInfoListContext error :" + e);
        }
        return response;
    }*/
    String JunctionRefreshFunction(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
        String response = null;
        String extraBytes = "";
        PlanInfo planInfo = new PlanInfo();
        TeenPatti_Info teenPattiInfo = new TeenPatti_Info();
        DamohNakaInfo damohNakaInfo = new DamohNakaInfo();
        RanitalInfo ranitalInfo = new RanitalInfo();
        YatayatThanaInfo yatayatThanaInfo = new YatayatThanaInfo();
        KatangaInfo katangaInfo = new KatangaInfo();
        BaldeobagInfo baldeobagInfo = new BaldeobagInfo();
        DeendayalChowkInfo deendayalChowkInfo = new DeendayalChowkInfo();
        HighCourtInfo highCourtInfo = new HighCourtInfo();
        BloomChowkInfo bloomChowkInfo = new BloomChowkInfo();
        MadanMahalInfo madanMahalInfo = new MadanMahalInfo();

        GohalPurInfo gohalPurInfo = new GohalPurInfo();
        LabourChowkInfo labourChowkInfo = new LabourChowkInfo();
        TrafficSignalWebServices tsws = new TrafficSignalWebServices();
        String[] side13 = null;
        String[] side24 = null;
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            /*------19-6-20--start-----range of byte array -127to127*/
                        if(program_version_no <0){
                        program_version_no=256+program_version_no;
                        } 
                        //done because when program version exceeded 127 it gets value in negative and throws erroe
                        /*------19-6-20--end-----*/
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];
            String currentTimeSynchronizationStatus = "Y";

            int side1Time = dataToProcess[firstStartDataPosition + 4] & 0xFF;
            int side2Time = dataToProcess[firstStartDataPosition + 5] & 0xFF;
            int side3Time = dataToProcess[firstStartDataPosition + 6] & 0xFF;
            int side4Time = dataToProcess[firstStartDataPosition + 7] & 0xFF;
            int side5Time = dataToProcess[firstStartDataPosition + 8] & 0xFF;

            int unsignedSide13Hex = dataToProcess[firstStartDataPosition + 9];//& 0xFF;
            int unsignedSide24Hex = dataToProcess[firstStartDataPosition + 10];// & 0xFF;
            int unsignedSide5Hex = dataToProcess[firstStartDataPosition + 11];// & 0xFF;
            int unsignedSide13 = dataToProcess[firstStartDataPosition + 9] & 0xFF;
            int unsignedSide24 = dataToProcess[firstStartDataPosition + 10] & 0xFF;
            int unsignedSide5 = dataToProcess[firstStartDataPosition + 11] & 0xFF;

            int unsignedMiscByte = dataToProcess[firstStartDataPosition + 12];// & 0xFF;

            int plan_id = dataToProcess[firstStartDataPosition + 13];
            int plan_mode = dataToProcess[firstStartDataPosition + 14];
            int activity = dataToProcess[firstStartDataPosition + 15];
            int activity1 = 1;
            int activity2 = 1;
            int activity3 = 1;
            int receivedActivity = activity;
            // String currentTimeSynchronizationStatus="Y";
            // data at firstStartDataPosition + 13/14/15 are default 1, when activity byte value is 2(clearance) then only firstStartDataPosition + 13 byte value will be used as clearance side.

            String onTime = this.clientResponderModel.getPlanOnTime(junctionID, program_version_no, plan_id);
            String offTime = this.clientResponderModel.getPlanOffTime(junctionID, program_version_no, plan_id);

            int juncHr = dataToProcess[firstStartDataPosition + 16];
            int juncMin = dataToProcess[firstStartDataPosition + 17];
            int juncDat = dataToProcess[firstStartDataPosition + 18];
            int juncMonth = dataToProcess[firstStartDataPosition + 19];
            int juncYear = dataToProcess[firstStartDataPosition + 20];
            int phase_id = dataToProcess[firstStartDataPosition + 21];

            int primary_horizontal_for_side1 = dataToProcess[firstStartDataPosition + 22] & 0xFF;
            int primary_Vertical_for_side1 = dataToProcess[firstStartDataPosition + 23] & 0xFF;
            int secondry_horizontal_for_side1 = dataToProcess[firstStartDataPosition + 24];// & 0xFF;
            int secondry_verticalfor_side1 = dataToProcess[firstStartDataPosition + 25];
            int hundred_place_side_1 = dataToProcess[firstStartDataPosition + 26];// & 0xFF;
            int tenth_place_side_1 = dataToProcess[firstStartDataPosition + 27];// & 0xFF;
            int once_place_side_1 = dataToProcess[firstStartDataPosition + 28];// & 0xFF;

            int primary_horizontal_for_side2 = dataToProcess[firstStartDataPosition + 29] & 0xFF;
            int primary_Vertical_for_side2 = dataToProcess[firstStartDataPosition + 30] & 0xFF;
            int secondry_horizontal_for_side2 = dataToProcess[firstStartDataPosition + 31];// & 0xFF;
            int secondry_verticalfor_side2 = dataToProcess[firstStartDataPosition + 32];
            int hundred_place_side_2 = dataToProcess[firstStartDataPosition + 33];// & 0xFF;
            int tenth_place_side_2 = dataToProcess[firstStartDataPosition + 34];// & 0xFF;
            int once_place_side_2 = dataToProcess[firstStartDataPosition + 35];// & 0xFF;

            int primary_horizontal_for_side3 = dataToProcess[firstStartDataPosition + 36] & 0xFF;
            int primary_Vertical_for_side3 = dataToProcess[firstStartDataPosition + 37] & 0xFF;
            int secondry_horizontal_for_side3 = dataToProcess[firstStartDataPosition + 38];// & 0xFF;
            int secondry_verticalfor_side3 = dataToProcess[firstStartDataPosition + 39];
            int hundred_place_side_3 = dataToProcess[firstStartDataPosition + 40];// & 0xFF;
            int tenth_place_side_3 = dataToProcess[firstStartDataPosition + 41];// & 0xFF;
            int once_place_side_3 = dataToProcess[firstStartDataPosition + 42];// & 0xFF;

            int primary_horizontal_for_side4 = dataToProcess[firstStartDataPosition + 43] & 0xFF;
            int primary_Vertical_for_side4 = dataToProcess[firstStartDataPosition + 44] & 0xFF;
            int secondry_horizontal_for_side4 = dataToProcess[firstStartDataPosition + 45];// & 0xFF;
            int secondry_verticalfor_side4 = dataToProcess[firstStartDataPosition + 46];
            System.out.println("secondry_verticalfor_side4"+secondry_verticalfor_side4);
            int hundred_place_side_4 = dataToProcess[firstStartDataPosition + 47];// & 0xFF;
            int tenth_place_side_4 = dataToProcess[firstStartDataPosition + 48];// & 0xFF;
            int once_place_side_4 = dataToProcess[firstStartDataPosition + 49];// & 0xFF;

            int primary_horizontal_for_side5 = dataToProcess[firstStartDataPosition + 50] & 0xFF;
            int primary_Vertical_for_side5 = dataToProcess[firstStartDataPosition + 51] & 0xFF;
            int secondry_horizontal_for_side5 = dataToProcess[firstStartDataPosition + 52];// & 0xFF;
            int secondry_verticalfor_side5 = dataToProcess[firstStartDataPosition + 53];
            int hundred_place_side_5 = dataToProcess[firstStartDataPosition + 54];// & 0xFF;
            int tenth_place_side_5 = dataToProcess[firstStartDataPosition + 55];// & 0xFF;
            int once_place_side_5 = dataToProcess[firstStartDataPosition + 56];// & 0xFF;

            int powerStatus = dataToProcess[firstStartDataPosition + 57];
            int communicationStatus = dataToProcess[firstStartDataPosition + 58];
            int map_id = dataToProcess[firstStartDataPosition + 59];

            for (int i = (firstStartDataPosition + 60); i <= (firstStartDataPosition + 65); i++) {
                //char ch = (char) dataToProcess[i];
                if (extraBytes == "") {
                    extraBytes = extraBytes + dataToProcess[i];
                } else {
                    extraBytes = extraBytes + " " + dataToProcess[i];
                }

            }
            int crc = dataToProcess[firstStartDataPosition + 66] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 67];
            int secLastDelimiter = firstLastDelimiter;
            int fault = 0;
            boolean match = this.clientResponderModel.sideColorMatch(junctionID, plan_id, phase_id, unsignedSide13, unsignedSide24, unsignedSide5, map_id);
            int unsignedSide13DB = 0;
            int unsignedSide24DB = 0;
            int unsignedSide5DB = 0;
            if (match) {

                String primary1 = "";
                String primary2 = "";
                String primary3 = "";
                String primary4 = "";
                String primary5 = "";
                String secondary1 = "";
                String secondary2 = "";
                String secondary3 = "";
                String secondary4 = "";
                String secondary5 = "";
                List<Integer> sidevalue = this.clientResponderModel.getSideValue(junctionID, plan_id, phase_id, map_id);
                unsignedSide13DB = sidevalue.get(0);
                unsignedSide24DB = sidevalue.get(1);
                unsignedSide5DB = sidevalue.get(2);

                //side1
                int poleType = this.clientResponderModel.getPoleType(junctionID, program_version_no, 1);

//Severity Case Work End       
            }

            int appHr = 0, appMin = 0, appDat = 0, appMonth = 0, appYear = 0;
            Calendar cald = Calendar.getInstance();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = sd.format(cald.getTime());

            String appDate = dateTime.split(" ")[0];
            String appTime = dateTime.split(" ")[1];

            String time[] = appTime.split(":");
            String dte[] = appDate.split("-");

            appHr = Integer.parseInt(time[0]);
            appMin = Integer.parseInt(time[1]);

            appDat = Integer.parseInt(dte[2]);
            appMonth = Integer.parseInt(dte[1]);
            appYear = Integer.parseInt(dte[0].substring(2));

            /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/
            int monthNo = (juncMonth - appMonth) == Math.abs(1) ? juncMonth > appMonth ? appMonth : juncMonth : 0;

            if (((juncYear - appYear) == Math.abs(1)) && ((juncMonth - appMonth) == Math.abs(11)) && ((juncDat - appDat) == Math.abs(30)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of last month of a year
                juncHr = appHr;
                juncMin = appMin;
                juncDat = appDat;
                juncMonth = appMonth;
                juncYear = appYear;
            } else if (((juncMonth - appMonth) == Math.abs(1)) && ((juncDat - appDat) == Math.abs(getNoOfDays(monthNo, appYear) - 1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of a month
                juncHr = appHr;
                juncMin = appMin;
                juncDat = appDat;
                juncMonth = appMonth;
            } else if (((juncDat - appDat) == Math.abs(1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of a day
                juncHr = appHr;
                juncMin = appMin;
                juncDat = appDat;
            } else if (((juncHr - appHr) == Math.abs(1)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of an hour
                juncHr = appHr;
                juncMin = appMin;
            } else if ((juncMin - appMin) == Math.abs(1)) { //a minute
                juncMin = appMin;
            }
            /*---------------------------- code endeded to manage one minute difference in junction time and in application time  ------------------------------------------*/

              currentTimeSynchronizationStatus = this.clientResponderModel.getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);
            String side13Status = Integer.toBinaryString(unsignedSide13);
            String side24Status = Integer.toBinaryString(unsignedSide24);

            String miscByte = Integer.toBinaryString(unsignedMiscByte);

            int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
            }
            fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
            int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

            String junctionName = this.clientResponderModel.getJunctionName(junctionID, program_version_no);
            int noOfSides = this.clientResponderModel.getNoOfSides(junctionID, programVersionNoFromDB);
            List sideNameList = new ArrayList(5);
            for (int j = 1; j <= noOfSides; j++) {
                String sideName = this.clientResponderModel.getSideName(j, junctionID, program_version_no);
                sideNameList.add(sideName);
            }

            System.out.println("refresh  request from modem--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2
                    + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + side1Time + " " + side2Time + " " + side3Time + " " + side4Time + " "
                    + unsignedSide13 + " " + unsignedSide24 + " " + unsignedMiscByte + " " + plan_id + " " + plan_mode + " " + activity + " "
                    + juncHr + " " + juncMin + " " + juncDat + " " + juncMonth + " " + juncYear + " "
                    + crc + " " + firstLastDelimiter + " " + secLastDelimiter);

            switch (side13Status.length()) {
                case 5:
                    side13Status = "000" + side13Status;
                    break;
                case 6:
                    side13Status = "00" + side13Status;
                    break;
                case 7:
                    side13Status = "0" + side13Status;
            }

            switch (side24Status.length()) {
                case 5:
                    side24Status = "000" + side24Status;
                    break;
                case 6:
                    side24Status = "00" + side24Status;
                    break;
                case 7:
                    side24Status = "0" + side24Status;
            }

            switch (miscByte.length()) {
                case 5:
                    miscByte = "000" + miscByte;
                    break;
                case 6:
                    miscByte = "00" + miscByte;
                    break;
                case 7:
                    miscByte = "0" + miscByte;
            }

            System.out.println("side13Status : " + side13Status.length() + " , " + side13Status);
            System.out.println("side24Status : " + side24Status.length() + " , " + side24Status);
            System.out.println("miscByte     : " + miscByte.length() + " , " + miscByte);

            side13 = side13Status.trim().split("");
            side24 = side24Status.trim().split("");

            if (!testRequestFromJunction) {

                planInfo.setFunction_no(functionNo);
                planInfo.setJunction_name(junctionName);
                if (noOfSides == 2) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                } else if (noOfSides == 3) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                    planInfo.setSide3Name((String) sideNameList.get(2));
                } else if (noOfSides == 4) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                    planInfo.setSide3Name((String) sideNameList.get(2));
                    planInfo.setSide4Name((String) sideNameList.get(3));
                } else if (noOfSides == 5) {
                    planInfo.setSide1Name((String) sideNameList.get(0));
                    planInfo.setSide2Name((String) sideNameList.get(1));
                    planInfo.setSide3Name((String) sideNameList.get(2));
                    planInfo.setSide4Name((String) sideNameList.get(3));
                    planInfo.setSide5Name((String) sideNameList.get(4));
                }
                planInfo.setJunction_id(junctionID);
                planInfo.setProgram_version_no(program_version_no);
                planInfo.setFileNo(fileNo);
                planInfo.setPlan_no(plan_id);
                planInfo.setOnTime(onTime);
                planInfo.setOffTime(offTime);
                planInfo.setMode(plan_mode == 1 ? "Blinker" : plan_mode == 2 ? "Signal" : "Signal With Pedestrian");
                planInfo.setSide1_time(side1Time);
                planInfo.setSide2_time(side2Time);
                planInfo.setSide3_time(side3Time);
                planInfo.setSide4_time(side4Time);

                System.out.println("side13 size: " + side13.length);
                System.out.println("side24 size: " + side24.length);
                int beginIndex = 0;
                try {
                    if (side13.length == 9) {
                        beginIndex = 1;
                    }
                    planInfo.setSide1_left_status(Integer.parseInt(side13[beginIndex]));
                    planInfo.setSide1_right_status(Integer.parseInt(side13[beginIndex + 1]));
                    planInfo.setSide1_up_status(Integer.parseInt(side13[beginIndex + 2]));
                    planInfo.setSide1_down_status(Integer.parseInt(side13[beginIndex + 3]));
                    planInfo.setSide3_left_status(Integer.parseInt(side13[beginIndex + 4]));
                    planInfo.setSide3_right_status(Integer.parseInt(side13[beginIndex + 5]));
                    planInfo.setSide3_up_status(Integer.parseInt(side13[beginIndex + 6]));
                    planInfo.setSide3_down_status(Integer.parseInt(side13[beginIndex + 7]));

                    planInfo.setSide2_left_status(Integer.parseInt(side24[beginIndex]));
                    planInfo.setSide2_right_status(Integer.parseInt(side24[beginIndex + 1]));
                    planInfo.setSide2_up_status(Integer.parseInt(side24[beginIndex + 2]));
                    planInfo.setSide2_down_status(Integer.parseInt(side24[beginIndex + 3]));
                    planInfo.setSide4_left_status(Integer.parseInt(side24[beginIndex + 4]));
                    planInfo.setSide4_right_status(Integer.parseInt(side24[beginIndex + 5]));
                    planInfo.setSide4_up_status(Integer.parseInt(side24[beginIndex + 6]));
                    planInfo.setSide4_down_status(Integer.parseInt(side24[beginIndex + 7]));

                } catch (Exception e) {
                    System.out.println("Error in side1 2 3 4 status: " + e);
                }

                planInfo.setJuncHr(juncHr);
                planInfo.setJuncMin(juncMin);
                planInfo.setJuncDate(juncDat);
                planInfo.setJuncMonth(juncMonth);
                planInfo.setJuncYear(juncYear);

                try {
                    this.planInfoRefreshList.setResponseFromModemForRefresh(true);
                    this.ctx.setAttribute("planInfolist", this.planInfoRefreshList);
                    if (activity == 2) {
                        this.junction.setResponseFromModemForClearance(true);
                    } else {
                        this.junction.setResponseFromModemForActivity(true);
                    }
                    System.out.println("client responder RequestForClearance: " + isRequestForClearance());
                    System.out.println("client responder requestForActivity: " + isRequestForActivity());

                    this.ctx.setAttribute("junction", this.junction);
                    this.modemResReadSaveCont.setCtx(this.ctx);

                    this.requestForActivity = (Boolean) (ctx.getAttribute("requestForActivity" + junctionID) == null ? false : ctx.getAttribute("requestForActivity" + junctionID));
                    this.requestForClearance = (Boolean) (ctx.getAttribute("requestForClearance" + junctionID) == null ? false : ctx.getAttribute("requestForClearance" + junctionID));
                    this.activityNo = (Integer) (ctx.getAttribute("activityNo" + junctionID) == null ? 0 : ctx.getAttribute("activityNo" + junctionID));
                    this.activitySide = (Integer) (ctx.getAttribute("activitySide" + junctionID) == null ? 0 : ctx.getAttribute("activitySide" + junctionID));
                    this.clearanceSide = (Integer) (ctx.getAttribute("clearanceSide" + junctionID) == null ? 0 : ctx.getAttribute("clearanceSide" + junctionID));

                    activity = receivedActivity == 4 || receivedActivity == 6 ? 1 : this.requestForActivity ? this.activityNo : this.requestForClearance ? 2 : 1;
                    activity1 = receivedActivity == 4 || receivedActivity == 6 ? 1
                            : this.requestForActivity ? this.activityNo == 4 || this.activityNo == 6 ? this.activitySide : 1
                                    : this.requestForClearance ? this.clearanceSide : 1;

                    ctx.setAttribute("requestForActivity" + junctionID, false);
                    //           ctx.setAttribute("requestForClearance" + junctionID, false);
                    ctx.setAttribute("activityNo" + junctionID, 0);
                    ctx.setAttribute("activitySide" + junctionID, 0);
                    //             ctx.setAttribute("clearanceSide" + junctionID, 0);

                    planInfo.setActivity(activity);
                    planInfo.setSide_no(activity1);

                    if (receivedActivity == 4 || receivedActivity == 6) {
                        this.requestForActivity = false;
                    }

                    planInfo.setResponseFromModemForRefresh(true);

                    this.planInfoRefreshList = planInfo;
                    this.ctx.setAttribute("planInfolist", this.planInfoRefreshList);
                    if (junctionID == 2) {
                        sideinfolist = clientResponderModel.getsidedatalist(2);
                        BeanUtils.copyProperties(damohNakaInfo, planInfo);
                        damohNakaInfoRefreshList = damohNakaInfo;
                        this.ctx.setAttribute("damohNakaInfoList", this.damohNakaInfoRefreshList);
                    }
                    if (junctionID == 13) {
                        sideinfolist = clientResponderModel.getsidedatalist(13);
                        BeanUtils.copyProperties(teenPattiInfo, planInfo);
                        teenPattiInfoRefreshList = teenPattiInfo;
                        this.ctx.setAttribute("teenPattiInfoList", this.teenPattiInfoRefreshList);
                    }
                    if (junctionID == 6) {
                        sideinfolist = clientResponderModel.getsidedatalist(6);
                        BeanUtils.copyProperties(gohalPurInfo, planInfo);
                        gohalPurInfoRefreshList = gohalPurInfo;
                        this.ctx.setAttribute("gohalPurInfoList", this.gohalPurInfoRefreshList);
                    }

                    if (junctionID == 11) {
                        sideinfolist = clientResponderModel.getsidedatalist(11);
                        BeanUtils.copyProperties(ranitalInfo, planInfo);
                        ranitalInfoRefreshList = ranitalInfo;
                        this.ctx.setAttribute("ranitalInfoList", this.ranitalInfoRefreshList);
                    }
                    if (junctionID == 1) {
                        sideinfolist = clientResponderModel.getsidedatalist(1);
                        BeanUtils.copyProperties(yatayatThanaInfo, planInfo);
                        yatayatThanaInfoRefreshList = yatayatThanaInfo;
                        this.ctx.setAttribute("yatayatThanaInfoList", this.yatayatThanaInfoRefreshList);
                    }
                    if (junctionID == 3) {
                        sideinfolist = clientResponderModel.getsidedatalist(3);
                        BeanUtils.copyProperties(katangaInfo, planInfo);
                        katangaInfoRefreshList = katangaInfo;
                        this.ctx.setAttribute("katangaInfoList", this.katangaInfoRefreshList);
                    }
                    if (junctionID == 5) {
                        sideinfolist = clientResponderModel.getsidedatalist(5);
                        BeanUtils.copyProperties(baldeobagInfo, planInfo);
                        baldeobagInfoRefreshList = baldeobagInfo;
                        this.ctx.setAttribute("baldeobagInfoList", this.baldeobagInfoRefreshList);
                    }
                    if (junctionID == 14) {
                        sideinfolist = clientResponderModel.getsidedatalist(14);
                        BeanUtils.copyProperties(deendayalChowkInfo, planInfo);
                        deendayalChowkInfoRefreshList = deendayalChowkInfo;
                        this.ctx.setAttribute("deendayalChowkInfoList", this.deendayalChowkInfoRefreshList);
                    }
                    if (junctionID == 7) {
                        sideinfolist = clientResponderModel.getsidedatalist(7);
                        BeanUtils.copyProperties(highCourtInfo, planInfo);
                        highCourtInfoRefreshList = highCourtInfo;
                        this.ctx.setAttribute("highCourtInfoList", this.highCourtInfoRefreshList);
                    }
                    if (junctionID == 8) {
                        sideinfolist = clientResponderModel.getsidedatalist(8);
                        BeanUtils.copyProperties(bloomChowkInfo, planInfo);
                        bloomChowkInfoRefreshList = bloomChowkInfo;
                        this.ctx.setAttribute("bloomChowkInfoList", this.bloomChowkInfoRefreshList);
                    }
                    if (junctionID == 12) {
                        sideinfolist = clientResponderModel.getsidedatalist(12);
                        BeanUtils.copyProperties(madanMahalInfo, planInfo);
                        madanMahalInfoRefreshList = madanMahalInfo;
                        this.ctx.setAttribute("madanMahalInfoList", this.madanMahalInfoRefreshList);
                    }
                    if (junctionID == 15) {
                        sideinfolist = clientResponderModel.getsidedatalist(15);
                        BeanUtils.copyProperties(labourChowkInfo, planInfo);

                        labourChowkInfoRefreshList = labourChowkInfo;
                        sidelistdata = new ArrayList<>();
                        clientResponderModel.getsidedatalist(junctionID);
                        this.ctx.setAttribute("labourChowkInfoList", this.labourChowkInfoRefreshList);
                    }
                    this.ctx.setAttribute("junction", this.junction);
                    this.modemResReadSaveCont.setCtx(this.ctx);
                    //this.planInfoRefreshList = planInfo;
                } catch (Exception e) {
                    System.out.println("ClientResponder junctionRefresh PlanInfoListContext error :" + e);
                }
            }

            sideinfolist.size();
            int dbside1primary_h_no = 0;
            int dbside1Primary_v_aspect_no = 0;
            int dbside1Secondary_h_aspect_no = 0;
            int dbside1Secondary_v_aspect_no = 0;
            int dbside2primary_h_no = 0;
            int dbside2Primary_v_aspect_no = 0;
            int dbside2Secondary_h_aspect_no = 0;
            int dbside2Secondary_v_aspect_no = 0;
            int dbside3primary_h_no = 0;
            int dbside3Primary_v_aspect_no = 0;
            int dbside3Secondary_h_aspect_no = 0;
            int dbside3Secondary_v_aspect_no = 0;
            int dbside4primary_h_no = 0;
            int dbside4Primary_v_aspect_no = 0;
            int dbside4Secondary_h_aspect_no = 0;
            int dbside4Secondary_v_aspect_no = 0;
            int dbside5primary_h_no = 0;
            int dbside5Primary_v_aspect_no = 0;
            int dbside5Secondary_h_aspect_no = 0;
            int dbside5Secondary_v_aspect_no = 0;
            // arrdata= new int[100];
            for (int i = 0; i < sideinfolist.size(); i++) {

                // SlaveInfo
                //    String[] arr = sideinfolist.toArray(new String[0]); 
                int a0 = 0;
                int a1 = 0;
                int a2 = 0;
                int a3 = 0;

                a0 = sideinfolist.get(i).getPrimary_h_aspect_no();
                a1 = sideinfolist.get(i).getPrimary_v_aspect_no();
                a2 = sideinfolist.get(i).getSecondary_h_aspect_no();
                a3 = sideinfolist.get(i).getSecondary_v_aspect_no();
                hmap.put("dbside" + i + "primary_h_no", a0);
                hmap.put("dbside" + i + "Primary_v_aspect_no", a1);
                hmap.put("dbside" + i + "Secondary_h_aspect_no", a2);
                hmap.put("dbside" + i + "Secondary_v_aspect_no", a3);

                System.out.println("sss " + hmap);

            }
            //  System.out.println("sss "+hmap);
            //sideinfolist.toArray();
            String[] side3Arr = new String[5];
            String[] side2Arr = new String[5];
            String[] side1Arr = new String[5];
            String[] side4Arr = new String[5];

            if (noOfSides == 3) {
                dbside1primary_h_no = hmap.get("dbside0primary_h_no");
                dbside1Primary_v_aspect_no = hmap.get("dbside0Primary_v_aspect_no");
                dbside1Secondary_h_aspect_no = hmap.get("dbside0Secondary_h_aspect_no");
                dbside1Secondary_v_aspect_no = hmap.get("dbside0Secondary_v_aspect_no");
                dbside2primary_h_no = hmap.get("dbside1primary_h_no");
                dbside2Primary_v_aspect_no = hmap.get("dbside1Primary_v_aspect_no");
                dbside2Secondary_h_aspect_no = hmap.get("dbside1Secondary_h_aspect_no");
                dbside2Secondary_v_aspect_no = hmap.get("dbside1Secondary_v_aspect_no");
                dbside3primary_h_no = hmap.get("dbside2primary_h_no");
                dbside3Primary_v_aspect_no = hmap.get("dbside2Primary_v_aspect_no");
                dbside3Secondary_h_aspect_no = hmap.get("dbside2Secondary_h_aspect_no");
                dbside3Secondary_v_aspect_no = hmap.get("dbside2Secondary_v_aspect_no");

                if (dbside1primary_h_no > 0) {
                    side1Arr[0] = Integer.toBinaryString(primary_horizontal_for_side1);
                } else {
                    side1Arr[0] = "0";
                }
                if (dbside1Primary_v_aspect_no > 0) {
                    side1Arr[1] = Integer.toBinaryString(primary_Vertical_for_side1);
                } else {
                    side1Arr[1] = "0";
                }
                if (dbside3Secondary_h_aspect_no > 0) {
                    side1Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side3);
                } else {
                    side1Arr[2] = "0";
                }
                if (dbside3Secondary_v_aspect_no > 0) {
                    side1Arr[3] = Integer.toBinaryString(secondry_verticalfor_side3);
                } else {
                    side1Arr[3] = "0";
                }

                if (dbside2primary_h_no > 0) {
                    side2Arr[0] = Integer.toBinaryString(primary_horizontal_for_side2);
                } else {
                    side2Arr[0] = "0";
                }
                if (dbside2Primary_v_aspect_no > 0) {
                    side2Arr[1] = Integer.toBinaryString(primary_Vertical_for_side2);
                } else {
                    side2Arr[1] = "0";
                }

                if (dbside4Secondary_h_aspect_no > 0) {
                    side2Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side4);
                } else {
                    side2Arr[2] = "0";
                }
                if (dbside4Secondary_v_aspect_no > 0) {
                    side2Arr[3] = Integer.toBinaryString(secondry_verticalfor_side4);
                } else {
                    side2Arr[3] = "0";
                }

                if (dbside3primary_h_no > 0) {
                    side3Arr[0] = Integer.toBinaryString(primary_horizontal_for_side3);
                } else {
                    side3Arr[0] = "0";
                }
                if (dbside3Primary_v_aspect_no > 0) {
                    side3Arr[1] = Integer.toBinaryString(primary_Vertical_for_side3);
                } else {
                    side3Arr[1] = "0";
                }
                if (dbside1Secondary_h_aspect_no > 0) {
                    side3Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side1);
                } else {
                    side3Arr[2] = "0";
                }
                if (dbside1Secondary_v_aspect_no > 0) {
                    side3Arr[3] = Integer.toBinaryString(secondry_verticalfor_side1);
                } else {
                    side3Arr[3] = "0";
                }

                side4Arr[0] = "0";
                side4Arr[1] = "0";
                side4Arr[2] = "0";
                side4Arr[3] = "0";

            } else if (noOfSides == 4) {
                dbside1primary_h_no = hmap.get("dbside0primary_h_no");
                dbside1Primary_v_aspect_no = hmap.get("dbside0Primary_v_aspect_no");
                dbside1Secondary_h_aspect_no = hmap.get("dbside0Secondary_h_aspect_no");
                dbside1Secondary_v_aspect_no = hmap.get("dbside0Secondary_v_aspect_no");
                dbside2primary_h_no = hmap.get("dbside1primary_h_no");
                dbside2Primary_v_aspect_no = hmap.get("dbside1Primary_v_aspect_no");
                dbside2Secondary_h_aspect_no = hmap.get("dbside1Secondary_h_aspect_no");
                dbside2Secondary_v_aspect_no = hmap.get("dbside1Secondary_v_aspect_no");
                dbside3primary_h_no = hmap.get("dbside2primary_h_no");
                dbside3Primary_v_aspect_no = hmap.get("dbside2Primary_v_aspect_no");
                dbside3Secondary_h_aspect_no = hmap.get("dbside2Secondary_h_aspect_no");
                dbside3Secondary_v_aspect_no = hmap.get("dbside2Secondary_v_aspect_no");
                dbside4primary_h_no = hmap.get("dbside3primary_h_no");
                dbside4Primary_v_aspect_no = hmap.get("dbside3Primary_v_aspect_no");
                dbside4Secondary_h_aspect_no = hmap.get("dbside3Secondary_h_aspect_no");
                dbside4Secondary_v_aspect_no = hmap.get("dbside3Secondary_v_aspect_no");
                if (dbside1primary_h_no > 0) {
                    side1Arr[0] = Integer.toBinaryString(primary_horizontal_for_side1);
                } else {
                    side1Arr[0] = "0";
                }
                if (dbside1Primary_v_aspect_no > 0) {
                    side1Arr[1] = Integer.toBinaryString(primary_Vertical_for_side1);
                } else {
                    side1Arr[1] = "0";
                }
                if (dbside3Secondary_h_aspect_no > 0) {
                    side1Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side3);
                } else {
                    side1Arr[2] = "0";
                }
                if (dbside3Secondary_v_aspect_no > 0) {
                    side1Arr[3] = Integer.toBinaryString(secondry_verticalfor_side3);
                } else {
                    side1Arr[3] = "0";
                }

                if (dbside2primary_h_no > 0) {
                    side2Arr[0] = Integer.toBinaryString(primary_horizontal_for_side2);
                } else {
                    side2Arr[0] = "0";
                }
                if (dbside2Primary_v_aspect_no > 0) {
                    side2Arr[1] = Integer.toBinaryString(primary_Vertical_for_side2);
                } else {
                    side2Arr[1] = "0";
                }

                if (dbside4Secondary_h_aspect_no > 0) {
                    side2Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side4);
                } else {
                    side2Arr[2] = "0";
                }
                if (dbside4Secondary_v_aspect_no > 0) {
                    side2Arr[3] = Integer.toBinaryString(secondry_verticalfor_side4);
                } else {
                    side2Arr[3] = "0";
                }

                if (dbside3primary_h_no > 0) {
                    side3Arr[0] = Integer.toBinaryString(primary_horizontal_for_side3);
                } else {
                    side3Arr[0] = "0";
                }
                if (dbside3Primary_v_aspect_no > 0) {
                    side3Arr[1] = Integer.toBinaryString(primary_Vertical_for_side3);
                } else {
                    side3Arr[1] = "0";
                }
                if (dbside1Secondary_h_aspect_no > 0) {
                    side3Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side1);
                } else {
                    side3Arr[2] = "0";
                }
                if (dbside1Secondary_v_aspect_no > 0) {
                    side3Arr[3] = Integer.toBinaryString(secondry_verticalfor_side1);
                } else {
                    side3Arr[3] = "0";
                }

                if (dbside4primary_h_no > 0) {
                    side4Arr[0] = Integer.toBinaryString(primary_horizontal_for_side4);
                } else {
                    side4Arr[0] = "0";
                }
                if (dbside4Primary_v_aspect_no > 0) {
                    side4Arr[1] = Integer.toBinaryString(primary_Vertical_for_side4);
                } else {
                    side4Arr[1] = "0";
                }
                if (dbside2Secondary_h_aspect_no > 0) {
                    side4Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side2);
                } else {
                    side4Arr[2] = "0";
                }
                if (dbside2Secondary_v_aspect_no > 0) {
                    side4Arr[3] = Integer.toBinaryString(secondry_verticalfor_side2);
                } else {
                    side4Arr[3] = "0";
                }

            } else {
                dbside1primary_h_no = hmap.get("dbside0primary_h_no");
                dbside1Primary_v_aspect_no = hmap.get("dbside0Primary_v_aspect_no");
                dbside1Secondary_h_aspect_no = hmap.get("dbside0Secondary_h_aspect_no");
                dbside1Secondary_v_aspect_no = hmap.get("dbside0Secondary_v_aspect_no");
                dbside2primary_h_no = hmap.get("dbside1primary_h_no");
                dbside2Primary_v_aspect_no = hmap.get("dbside1Primary_v_aspect_no");
                dbside2Secondary_h_aspect_no = hmap.get("dbside1Secondary_h_aspect_no");
                dbside2Secondary_v_aspect_no = hmap.get("dbside1Secondary_v_aspect_no");
                dbside3primary_h_no = hmap.get("dbside2primary_h_no");
                dbside3Primary_v_aspect_no = hmap.get("dbside2Primary_v_aspect_no");
                dbside3Secondary_h_aspect_no = hmap.get("dbside2Secondary_h_aspect_no");
                dbside3Secondary_v_aspect_no = hmap.get("dbside2Secondary_v_aspect_no");
                dbside4primary_h_no = hmap.get("dbside3primary_h_no");
                dbside4Primary_v_aspect_no = hmap.get("dbside3Primary_v_aspect_no");
                dbside4Secondary_h_aspect_no = hmap.get("dbside3Secondary_h_aspect_no");
                dbside4Secondary_v_aspect_no = hmap.get("dbside3Secondary_v_aspect_no");
                dbside5primary_h_no = hmap.get("dbside4primary_h_no");
                dbside5Primary_v_aspect_no = hmap.get("dbside4Primary_v_aspect_no");
                dbside5Secondary_h_aspect_no = hmap.get("dbside4Secondary_h_aspect_no");
                dbside5Secondary_v_aspect_no = hmap.get("dbside4Secondary_v_aspect_no");

                if (dbside1primary_h_no > 0) {
                    side1Arr[0] = Integer.toBinaryString(primary_horizontal_for_side1);
                } else {
                    side1Arr[0] = "0";
                }
                if (dbside1Primary_v_aspect_no > 0) {
                    side1Arr[1] = Integer.toBinaryString(primary_Vertical_for_side1);
                } else {
                    side1Arr[1] = "0";
                }
                if (dbside3Secondary_h_aspect_no > 0) {
                    side1Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side3);
                } else {
                    side1Arr[2] = "0";
                }
                if (dbside3Secondary_v_aspect_no > 0) {
                    side1Arr[3] = Integer.toBinaryString(secondry_verticalfor_side3);
                } else {
                    side1Arr[3] = "0";
                }

                if (dbside2primary_h_no > 0) {
                    side2Arr[0] = Integer.toBinaryString(primary_horizontal_for_side2);
                } else {
                    side2Arr[0] = "0";
                }
                if (dbside2Primary_v_aspect_no > 0) {
                    side2Arr[1] = Integer.toBinaryString(primary_Vertical_for_side2);
                } else {
                    side2Arr[1] = "0";
                }

                if (dbside4Secondary_h_aspect_no > 0) {
                    side2Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side4);
                } else {
                    side2Arr[2] = "0";
                }
                if (dbside4Secondary_v_aspect_no > 0) {
                    side2Arr[3] = Integer.toBinaryString(secondry_verticalfor_side4);
                } else {
                    side2Arr[3] = "0";
                }

                if (dbside3primary_h_no > 0) {
                    side3Arr[0] = Integer.toBinaryString(primary_horizontal_for_side3);
                } else {
                    side3Arr[0] = "0";
                }
                if (dbside3Primary_v_aspect_no > 0) {
                    side3Arr[1] = Integer.toBinaryString(primary_Vertical_for_side3);
                } else {
                    side3Arr[1] = "0";
                }
                if (dbside1Secondary_h_aspect_no > 0) {
                    side3Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side1);
                } else {
                    side3Arr[2] = "0";
                }
                if (dbside1Secondary_v_aspect_no > 0) {
                    side3Arr[3] = Integer.toBinaryString(secondry_verticalfor_side1);
                } else {
                    side3Arr[3] = "0";
                }

                if (dbside4primary_h_no > 0) {
                    side4Arr[0] = Integer.toBinaryString(primary_horizontal_for_side4);
                } else {
                    side4Arr[0] = "0";
                }
                if (dbside4Primary_v_aspect_no > 0) {
                    side4Arr[1] = Integer.toBinaryString(primary_Vertical_for_side4);
                } else {
                    side4Arr[1] = "0";
                }
                if (dbside2Secondary_h_aspect_no > 0) {
                    side4Arr[2] = Integer.toBinaryString(secondry_horizontal_for_side2);
                } else {
                    side4Arr[2] = "0";
                }
                if (dbside2Secondary_v_aspect_no > 0) {
                    side4Arr[3] = Integer.toBinaryString(secondry_verticalfor_side2);
                } else {
                    side4Arr[3] = "0";
                }
            }
            
            
            String side1 = this.clientResponderModel.decToBinaryAndSplitFirst(unsignedSide13DB);
            String side2 = this.clientResponderModel.decToBinaryAndSplitFirst(unsignedSide24DB);
            String side3 = this.clientResponderModel.decToBinaryAndSplitLater(unsignedSide13DB);
            String side4 = this.clientResponderModel.decToBinaryAndSplitLater(unsignedSide24DB);
            String side5 = "00000000";
            //List of Side Details         

//Severity Case Work Start
//  sideinfolist=clientResponderModel.getseveritydatalist(14);
            side1 = side1.concat("0000");
            side2 = side2.concat("0000");
            side3 = side3.concat("0000");
            side4 = side4.concat("0000");

            fault = matchSignalNew(side1, side2, side3, side4, side5, side1Arr[0], side1Arr[1], side1Arr[2], side1Arr[3], side2Arr[0], side2Arr[1], side2Arr[2], side2Arr[3],
                     side3Arr[0], side3Arr[1], side3Arr[2], side3Arr[3],
                     side4Arr[0], side4Arr[1], side4Arr[2], side4Arr[3],
                    "0", "0",
                    "0", "0", junctionID);

            // List<SeverityCase> caselist1=clientResponderModel.getseveritydatalist(side1);
            //List<SeverityCase> caselist2=clientResponderModel.getseveritydatalist(side2);
            //List<SeverityCase> caselist3=clientResponderModel.getseveritydatalist(side3);
            //List<SeverityCase> caselist4=clientResponderModel.getseveritydatalist(side4);
            
            int primary_Vertical_for_side11 = primary_Vertical_for_side1;
            activity = program_version_no == programVersionNoFromDB ? fileNo == fileNoFromDB ? currentTimeSynchronizationStatus.equals("Y") ? activity : testRequestFromJunction ? 1 : 9 : 8 : 7;
            activity1 = activity == 7 || activity == 8 || activity == 9 ? 1 : activity1;

            String pureBytes = null;
            int junction_id = 0;
            // pureBytes = tsws.arr2;
            //String activeReq = clientResponderModel.getActiveRequest(junctionID);
            if (mapData() != null) {
                if (!mapData().equals("normal")) {
                    String data = mapData();
                    junction_id = Integer.parseInt(data.split("_")[0]);
                    if (junctionID == junction_id) {
                        activity = 2;
                        activity1 = Integer.parseInt(data.split("_")[1]);
                    }
                }

            }
//            

            System.out.println("activity bytes: " + activity + " " + activity1 + " " + activity2 + " " + activity3);
            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + activity + " " + activity1 + " " + activity2 + " " + activity3 + " " + fault + " " + firstLastDelimiter + " " + firstLastDelimiter;
            System.out.println("response ::: " + response);

        } catch (Exception e) {
            System.out.println("PlanInfoListContext error :" + e);
        }
        return response;
    }
 
    public int matchSignalNew(String side1, String side2, String side3, String side4, String side5,
            String primary_horizontal_for_side1, String primary_Vertical_for_side1, String secondry_horizontal_for_side3, String secondry_verticalfor_side3,
             String primary_horizontal_for_side2, String primary_Vertical_for_side2, String secondry_horizontal_for_side4, String secondry_verticalfor_side4,
            String primary_horizontal_for_side3, String primary_Vertical_for_side3, String secondry_horizontal_for_side1, String secondry_verticalfor_side1,
            String primary_horizontal_for_side4, String primary_Vertical_for_side4, String secondry_horizontal_for_side2, String secondry_verticalfor_side2,
            String primary_horizontal_for_side5, String primary_vertical_for_side5, String secondry_horizontal_for_side5, String secondry_vertical_for_side5, int junctionID) {

        int fault = 0;
        int programVersionNumber = 0;
        //primary_horizontal_for_side1 color match 
        if(!"0".equals(primary_horizontal_for_side1)){
        primary_horizontal_for_side1 = settingColorBit(primary_horizontal_for_side1);
        // primary_side_1 = settingColorBit(primary_side_1);
        Map<String, Object> side1Match = this.clientResponderModel.matchColor(side1, primary_horizontal_for_side1);
        if (side1Match.size() > 0) {
            if(side1Match.get("remark").equals("high")){
            fault=1;
            }else if(side1Match.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
           int  faults = this.clientResponderModel.insertIntoLogTable(side1Match.get("severity_case").toString(), Integer.parseInt(side1Match.get("severity_case_id").toString()), sideDetailId);
              System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side1Match.get("severity_case").toString());
          if (faults > 0) {
               // sendSmsToAssignedFor("", side1Match.get("severity_case").toString());
            }
        }
}
        if(!"0".equals(primary_Vertical_for_side1)){
        primary_Vertical_for_side1 = settingColorBit(primary_Vertical_for_side1);
        //  primary_side_2 = settingColorBit(primary_side_2);
        Map<String, Object> side2Match = this.clientResponderModel.matchColor(side1, primary_Vertical_for_side1);
        if (side2Match.size() > 0) {
            if(side2Match.get("remark").equals("high")){
            fault=1;
            }else if(side2Match.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side2Match.get("severity_case").toString(), Integer.parseInt(side2Match.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side2Match.get("severity_case").toString());
            if (faults > 0) {
               // sendSmsToAssignedFor("", side2Match.get("severity_case").toString());
            }
        }}
//        
if(!"0".equals(secondry_horizontal_for_side3)){
        secondry_horizontal_for_side3 = settingColorBit(secondry_horizontal_for_side3);
        Map<String, Object> side3Match = this.clientResponderModel.matchColor(side1, secondry_horizontal_for_side3);
        if (side3Match.size() > 0) {
            if(side3Match.get("remark").equals("high")){
            fault=1;
            }else if(side3Match.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side3Match.get("severity_case").toString(), Integer.parseInt(side3Match.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side3Match.get("severity_case").toString());
          
            if (faults > 0) {
                //sendSmsToAssignedFor("", side3Match.get("severity_case").toString());
            }
        }}
//        
if(!"0".equals(secondry_verticalfor_side3)){        
secondry_verticalfor_side3 = settingColorBit(secondry_verticalfor_side3);
        //  primary_side_4 = settingColorBit(primary_side_4);
        Map<String, Object> side4Match = this.clientResponderModel.matchColor(side1, secondry_verticalfor_side3);
        if (side4Match.size() > 0) {
             if(side4Match.get("remark").equals("high")){
            fault=1;
            }else if(side4Match.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side4Match.get("severity_case").toString(), Integer.parseInt(side4Match.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match.get("severity_case").toString());
            }
        }
}if(!"0".equals(primary_horizontal_for_side2)){
        primary_horizontal_for_side2 = settingColorBit(primary_horizontal_for_side2);
        // primary_side_1 = settingColorBit(primary_side_1);
        Map<String, Object> side1Matchs = this.clientResponderModel.matchColor(side2, primary_horizontal_for_side2);
        if (side1Matchs.size() > 0) {
             if(side1Matchs.get("remark").equals("high")){
            fault=1;
            }else if(side1Matchs.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side1Matchs.get("severity_case").toString(), Integer.parseInt(side1Matchs.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side1Matchs.get("severity_case").toString());
          
            if (faults > 0) {
              //  sendSmsToAssignedFor("", side1Matchs.get("severity_case").toString());
            }
        }
}if(!"0".equals(primary_Vertical_for_side2)){
        primary_Vertical_for_side2 = settingColorBit(primary_Vertical_for_side2);
        //  primary_side_2 = settingColorBit(primary_side_2);
        Map<String, Object> side2Match1 = this.clientResponderModel.matchColor(side2, primary_Vertical_for_side2);
        if (side2Match1.size() > 0) {
             if(side2Match1.get("remark").equals("high")){
            fault=1;
            }else if(side2Match1.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side2Match1.get("severity_case").toString(), Integer.parseInt(side2Match1.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side2Match1.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side2Match1.get("severity_case").toString());
            }
        }}
//        
if(!"0".equals(secondry_horizontal_for_side4)){
        secondry_horizontal_for_side4 = settingColorBit(secondry_horizontal_for_side4);
        Map<String, Object> side3Match3 = this.clientResponderModel.matchColor(side2, secondry_horizontal_for_side4);
        if (side3Match3.size() > 0) {
             if(side3Match3.get("remark").equals("high")){
            fault=1;
            }else if(side3Match3.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side3Match3.get("severity_case").toString(), Integer.parseInt(side3Match3.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side3Match3.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side3Match3.get("severity_case").toString());
            }
        }}
//        
        if(!"0".equals(secondry_verticalfor_side4)){
            secondry_verticalfor_side4 = settingColorBit(secondry_verticalfor_side4);
         
        //  primary_side_4 = settingColorBit(primary_side_4);
        Map<String, Object> side4Match12 = this.clientResponderModel.matchColor(side2, secondry_verticalfor_side4);
        if (side4Match12.size() > 0) {
             if(side4Match12.get("remark").equals("high")){
            fault=1;
            }else if(side4Match12.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side4Match12.get("severity_case").toString(), Integer.parseInt(side4Match12.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match12.get("severity_case").toString());
          
            if (faults > 0) {
             //   sendSmsToAssignedFor("", side4Match12.get("severity_case").toString());
            }
        }}
if(!"0".equals(primary_horizontal_for_side3)){
        primary_horizontal_for_side3 = settingColorBit(primary_horizontal_for_side3);
        // primary_side_1 = settingColorBit(primary_side_1);
        Map<String, Object> side1Matchs21 = this.clientResponderModel.matchColor(side3, primary_horizontal_for_side3);
        if (side1Matchs21.size() > 0) {
             if(side1Matchs21.get("remark").equals("high")){
            fault=1;
            }else if(side1Matchs21.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
           int  faults = this.clientResponderModel.insertIntoLogTable(side1Matchs21.get("severity_case").toString(), Integer.parseInt(side1Matchs21.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side1Matchs21.get("severity_case").toString());
          
           if (faults > 0) {
               // sendSmsToAssignedFor("", side1Matchs21.get("severity_case").toString());
            }
        }}
if(!"0".equals(primary_Vertical_for_side3)){
        primary_Vertical_for_side3 = settingColorBit(primary_Vertical_for_side3);
        //  primary_side_2 = settingColorBit(primary_side_2);
        Map<String, Object> side2Match122 = this.clientResponderModel.matchColor(side3, primary_Vertical_for_side3);
        if (side2Match122.size() > 0) {
             if(side2Match122.get("remark").equals("high")){
            fault=1;
            }else if(side2Match122.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side2Match122.get("severity_case").toString(), Integer.parseInt(side2Match122.get("severity_case_id").toString()), sideDetailId);
           System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side2Match122.get("severity_case").toString());
          
            if (faults > 0) {
             //   sendSmsToAssignedFor("", side2Match122.get("severity_case").toString());
            }
        }}
//        
if(!"0".equals(secondry_horizontal_for_side1)){
        secondry_horizontal_for_side1 = settingColorBit(secondry_horizontal_for_side1);
        Map<String, Object> side3Match23 = this.clientResponderModel.matchColor(side3, secondry_horizontal_for_side1);
        if (side3Match23.size() > 0) {
             if(side3Match23.get("remark").equals("high")){
            fault=1;
            }else if(side3Match23.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side3Match23.get("severity_case").toString(), Integer.parseInt(side3Match23.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side3Match23.get("severity_case").toString());
          
            if (faults > 0) {
              //  sendSmsToAssignedFor("", side3Match23.get("severity_case").toString());
            }
        }}
//        
if(!"0".equals(secondry_verticalfor_side1)){       
secondry_verticalfor_side1 = settingColorBit(secondry_verticalfor_side1);
        //  primary_side_4 = settingColorBit(primary_side_4);
        Map<String, Object> side4Match124 = this.clientResponderModel.matchColor(side3, secondry_verticalfor_side1);
        if (side4Match124.size() > 0) {
             if(side4Match124.get("remark").equals("high")){
            fault=1;
            }else if(side4Match124.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side4Match124.get("severity_case").toString(), Integer.parseInt(side4Match124.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match124.get("severity_case").toString());
          
            if (faults > 0) {
              //  sendSmsToAssignedFor("", side4Match124.get("severity_case").toString());
            }
        }}
if(!"0".equals(primary_horizontal_for_side2)){
        primary_horizontal_for_side2 = settingColorBit(primary_horizontal_for_side4);
        // primary_side_1 = settingColorBit(primary_side_1);
        Map<String, Object> side1Matchs214 = this.clientResponderModel.matchColor(side4, primary_horizontal_for_side4);
        if (side1Matchs214.size() > 0) {
             if(side1Matchs214.get("remark").equals("high")){
            fault=1;
            }else if(side1Matchs214.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side1Matchs214.get("severity_case").toString(), Integer.parseInt(side1Matchs214.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side1Matchs214.get("severity_case").toString());
          
            if (faults > 0) {
             //   sendSmsToAssignedFor("", side1Matchs214.get("severity_case").toString());
            }
        }}
if(!"0".equals(primary_Vertical_for_side4)){
        primary_Vertical_for_side4 = settingColorBit(primary_Vertical_for_side4);
        //  primary_side_2 = settingColorBit(primary_side_2);
        Map<String, Object> side2Match1224 = this.clientResponderModel.matchColor(side4, primary_Vertical_for_side4);
        if (side2Match1224.size() > 0) {
             if(side2Match1224.get("remark").equals("high")){
            fault=1;
            }else if(side2Match1224.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side2Match1224.get("severity_case").toString(), Integer.parseInt(side2Match1224.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side2Match1224.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side2Match1224.get("severity_case").toString());
            }
        }}
//        
if(!"0".equals(secondry_horizontal_for_side2)){
        secondry_horizontal_for_side2 = settingColorBit(secondry_horizontal_for_side2);
        Map<String, Object> side3Match232 = this.clientResponderModel.matchColor(side4, secondry_horizontal_for_side2);
        if (side3Match232.size() > 0) {
             if(side3Match232.get("remark").equals("high")){
            fault=1;
            }else if(side3Match232.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
           int faults = this.clientResponderModel.insertIntoLogTable(side3Match232.get("severity_case").toString(), Integer.parseInt(side3Match232.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side3Match232.get("severity_case").toString());
          
           if (faults > 0) {
               // sendSmsToAssignedFor("", side3Match232.get("severity_case").toString());
            }}
        }
//        
if(!"0".equals(secondry_verticalfor_side2)){
        secondry_verticalfor_side2 = settingColorBit(secondry_verticalfor_side2);
        //  primary_side_4 = settingColorBit(primary_side_4);
        Map<String, Object> side4Match1242 = this.clientResponderModel.matchColor(side4, secondry_verticalfor_side2);
        if (side4Match1242.size() > 0) {
             if(side4Match1242.get("remark").equals("high")){
            fault=1;
            }else if(side4Match1242.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
           int faults = this.clientResponderModel.insertIntoLogTable(side4Match1242.get("severity_case").toString(), Integer.parseInt(side4Match1242.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match1242.get("severity_case").toString());
          
           if (faults > 0) {
             //   sendSmsToAssignedFor("", side4Match1242.get("severity_case").toString());
            }
        }}
if(!"0".equals(primary_horizontal_for_side5)){
        primary_horizontal_for_side5 = settingColorBit(primary_horizontal_for_side5);
        // for side5
        Map<String, Object> side4Match5 = this.clientResponderModel.matchColor(side5, primary_horizontal_for_side5);
        if (side4Match5.size() > 0) {
             if(side4Match5.get("remark").equals("high")){
            fault=1;
            }else if(side4Match5.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(5, junctionID, programVersionNumber);
             int faults = this.clientResponderModel.insertIntoLogTable(side4Match5.get("severity_case").toString(), Integer.parseInt(side4Match5.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match5.get("severity_case").toString());
          
             if (faults > 0) {
              //  sendSmsToAssignedFor("", side4Match5.get("severity_case").toString());
            }
        }}
if(!"0".equals(primary_vertical_for_side5)){
        primary_vertical_for_side5 = settingColorBit(primary_vertical_for_side5);
        // for side5
        Map<String, Object> side4Match52 = this.clientResponderModel.matchColor(side5, primary_vertical_for_side5);
        if (side4Match52.size() > 0) {
             if(side4Match52.get("remark").equals("high")){
            fault=1;
            }else if(side4Match52.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(5, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side4Match52.get("severity_case").toString(), Integer.parseInt(side4Match52.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match52.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match52.get("severity_case").toString());
            }
        }}
if(!"0".equals(secondry_horizontal_for_side5)){
        secondry_horizontal_for_side5 = settingColorBit(secondry_horizontal_for_side5);
        // for side5
        Map<String, Object> side4Match53 = this.clientResponderModel.matchColor(side5, secondry_horizontal_for_side5);
        if (side4Match53.size() > 0) {
             if(side4Match53.get("remark").equals("high")){
            fault=1;
            }else if(side4Match53.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(5, junctionID, programVersionNumber);
           int faults= this.clientResponderModel.insertIntoLogTable(side4Match53.get("severity_case").toString(), Integer.parseInt(side4Match53.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match53.get("severity_case").toString());
          
           if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match53.get("severity_case").toString());
            }
        }}
if(!"0".equals(secondry_vertical_for_side5)){
        secondry_vertical_for_side5 = settingColorBit(secondry_vertical_for_side5);
        // for side5
        Map<String, Object> side4Match54 = this.clientResponderModel.matchColor(side5, secondry_vertical_for_side5);
        if (side4Match54.size() > 0) {
             if(side4Match54.get("remark").equals("high")){
            fault=1;
            }else if(side4Match54.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(5, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(side4Match54.get("severity_case").toString(), Integer.parseInt(side4Match54.get("severity_case_id").toString()), sideDetailId);
           System.out.println("ssssssssssssssssssssssssssssssssssssss"  +side4Match54.get("severity_case").toString());
           
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match54.get("severity_case").toString());
            }
        }
}
if(primary_horizontal_for_side1.equals("0") && primary_horizontal_for_side2.equals("0") && primary_horizontal_for_side3.equals("0") && primary_horizontal_for_side4.equals("0") && primary_horizontal_for_side5.equals("0") && primary_Vertical_for_side1.equals("0") && primary_Vertical_for_side2.equals("0") && primary_Vertical_for_side3.equals("0") && primary_Vertical_for_side4.equals("0") && primary_vertical_for_side5.equals("0") && secondry_horizontal_for_side1.equals("0") && secondry_horizontal_for_side2.equals("0") && secondry_horizontal_for_side3.equals("0") && secondry_horizontal_for_side4.equals("0") && secondry_horizontal_for_side5.equals("0") && secondry_verticalfor_side1.equals("0") && secondry_verticalfor_side2.equals("0") && secondry_verticalfor_side3.equals("0") && secondry_verticalfor_side4.equals("0") && secondry_vertical_for_side5.equals("0")){
       // secondry_vertical_for_side5 = settingColorBit(secondry_vertical_for_side5);
        // for side5
        Map<String, Object> sideoff1 = this.clientResponderModel.matchColor(side1, "00000000");
        if (sideoff1.size() > 0) {
             if(sideoff1.get("remark").equals("high")){
            fault=1;
            }else if(sideoff1.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(sideoff1.get("severity_case").toString(), Integer.parseInt(sideoff1.get("severity_case_id").toString()), sideDetailId);
           System.out.println("ssssssssssssssssssssssssssssssssssssss"  +sideoff1.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match54.get("severity_case").toString());
            }
        }
}
if(primary_horizontal_for_side1.equals("0") && primary_horizontal_for_side2.equals("0") && primary_horizontal_for_side3.equals("0") && primary_horizontal_for_side4.equals("0") && primary_horizontal_for_side5.equals("0") && primary_Vertical_for_side1.equals("0") && primary_Vertical_for_side2.equals("0") && primary_Vertical_for_side3.equals("0") && primary_Vertical_for_side4.equals("0") && primary_vertical_for_side5.equals("0") && secondry_horizontal_for_side1.equals("0") && secondry_horizontal_for_side2.equals("0") && secondry_horizontal_for_side3.equals("0") && secondry_horizontal_for_side4.equals("0") && secondry_horizontal_for_side5.equals("0") && secondry_verticalfor_side1.equals("0") && secondry_verticalfor_side2.equals("0") && secondry_verticalfor_side3.equals("0") && secondry_verticalfor_side4.equals("0") && secondry_vertical_for_side5.equals("0")){
       // secondry_vertical_for_side5 = settingColorBit(secondry_vertical_for_side5);
        // for side5
        Map<String, Object> sideoff2 = this.clientResponderModel.matchColor(side2, "00000000");
        if (sideoff2.size() > 0) {
             if(sideoff2.get("remark").equals("high")){
            fault=1;
            }else if(sideoff2.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(sideoff2.get("severity_case").toString(), Integer.parseInt(sideoff2.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +sideoff2.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match54.get("severity_case").toString());
            }
        }
}
if(primary_horizontal_for_side1.equals("0") && primary_horizontal_for_side2.equals("0") && primary_horizontal_for_side3.equals("0") && primary_horizontal_for_side4.equals("0") && primary_horizontal_for_side5.equals("0") && primary_Vertical_for_side1.equals("0") && primary_Vertical_for_side2.equals("0") && primary_Vertical_for_side3.equals("0") && primary_Vertical_for_side4.equals("0") && primary_vertical_for_side5.equals("0") && secondry_horizontal_for_side1.equals("0") && secondry_horizontal_for_side2.equals("0") && secondry_horizontal_for_side3.equals("0") && secondry_horizontal_for_side4.equals("0") && secondry_horizontal_for_side5.equals("0") && secondry_verticalfor_side1.equals("0") && secondry_verticalfor_side2.equals("0") && secondry_verticalfor_side3.equals("0") && secondry_verticalfor_side4.equals("0") && secondry_vertical_for_side5.equals("0")){
       // secondry_vertical_for_side5 = settingColorBit(secondry_vertical_for_side5);
        // for side5
        Map<String, Object> sideoff3 = this.clientResponderModel.matchColor(side3, "00000000");
        if (sideoff3.size() > 0) {
             if(sideoff3.get("remark").equals("high")){
            fault=1;
            }else if(sideoff3.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(sideoff3.get("severity_case").toString(), Integer.parseInt(sideoff3.get("severity_case_id").toString()), sideDetailId);
             System.out.println("ssssssssssssssssssssssssssssssssssssss"  +sideoff3.get("severity_case").toString());
          
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match54.get("severity_case").toString());
            }
        }
}
if(primary_horizontal_for_side1.equals("0") && primary_horizontal_for_side2.equals("0") && primary_horizontal_for_side3.equals("0") && primary_horizontal_for_side4.equals("0") && primary_horizontal_for_side5.equals("0") && primary_Vertical_for_side1.equals("0") && primary_Vertical_for_side2.equals("0") && primary_Vertical_for_side3.equals("0") && primary_Vertical_for_side4.equals("0") && primary_vertical_for_side5.equals("0") && secondry_horizontal_for_side1.equals("0") && secondry_horizontal_for_side2.equals("0") && secondry_horizontal_for_side3.equals("0") && secondry_horizontal_for_side4.equals("0") && secondry_horizontal_for_side5.equals("0") && secondry_verticalfor_side1.equals("0") && secondry_verticalfor_side2.equals("0") && secondry_verticalfor_side3.equals("0") && secondry_verticalfor_side4.equals("0") && secondry_vertical_for_side5.equals("0")){
       // secondry_vertical_for_side5 = settingColorBit(secondry_vertical_for_side5);
        // for side5
        Map<String, Object> sideoff4 = this.clientResponderModel.matchColor(side4, "00000000");
        if (sideoff4.size() > 0) {
             if(sideoff4.get("remark").equals("high")){
            fault=1;
            }else if(sideoff4.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
            int faults = this.clientResponderModel.insertIntoLogTable(sideoff4.get("severity_case").toString(), Integer.parseInt(sideoff4.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +sideoff4.get("severity_case").toString());
           if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match54.get("severity_case").toString());
            }
        }
}
if(primary_horizontal_for_side1.equals("0") && primary_horizontal_for_side2.equals("0") && primary_horizontal_for_side3.equals("0") && primary_horizontal_for_side4.equals("0") && primary_horizontal_for_side5.equals("0") && primary_Vertical_for_side1.equals("0") && primary_Vertical_for_side2.equals("0") && primary_Vertical_for_side3.equals("0") && primary_Vertical_for_side4.equals("0") && primary_vertical_for_side5.equals("0") && secondry_horizontal_for_side1.equals("0") && secondry_horizontal_for_side2.equals("0") && secondry_horizontal_for_side3.equals("0") && secondry_horizontal_for_side4.equals("0") && secondry_horizontal_for_side5.equals("0") && secondry_verticalfor_side1.equals("0") && secondry_verticalfor_side2.equals("0") && secondry_verticalfor_side3.equals("0") && secondry_verticalfor_side4.equals("0") && secondry_vertical_for_side5.equals("0")){
       // secondry_vertical_for_side5 = settingColorBit(secondry_vertical_for_side5);
        // for side5
        Map<String, Object> sideoff5 = this.clientResponderModel.matchColor(side5, "00000000");
        if (sideoff5.size() > 0) {
             if(sideoff5.get("remark").equals("high")){
            fault=1;
            }else if(sideoff5.get("remark").equals("middle")){
            fault=2;
            }
            int sideDetailId = this.clientResponderModel.getSideId(5, junctionID, programVersionNumber);
            
            int faults = this.clientResponderModel.insertIntoLogTable(sideoff5.get("severity_case").toString(), Integer.parseInt(sideoff5.get("severity_case_id").toString()), sideDetailId);
            System.out.println("ssssssssssssssssssssssssssssssssssssss"  +sideoff5.get("severity_case").toString());
            if (faults > 0) {
               // sendSmsToAssignedFor("", side4Match54.get("severity_case").toString());
            }
        }
}
        return fault;
    }

    public String mapData() {
        if (TrafficSignalWebServices.flag == 0 || TrafficSignalWebServices.mapData.equals("normal")) {
            TrafficSignalWebServices.flag = 0;
            return null;
        }
        return TrafficSignalWebServices.mapData;

    }

    public String settingColorBit(String primary) {
        if (primary.length() != 8) {
            int no_of_zero = 8 - primary.length();
            String zero = "";
            for (int i = 0; i < no_of_zero; i++) {
                zero = zero + "0";
            }
            primary = zero + primary;
        }
        return primary;
    }

    public int matchSignal(String primary_side_1, String primary_side_2, String primary_side_3, String primary_side_4,
            String secondary_side_1, String secondary_side_2, String secondary_side_3, String secondary_side_4,
            String primary1, String primary2, String primary3, String primary4,
            String secondary1, String secondary2, String secondary3, String secondary4, int junctionID) {

        int fault = 0;
        int programVersionNumber = 0;
        //Primary color match 
        primary1 = settingColorBit(primary1);
        primary_side_1 = settingColorBit(primary_side_1);
        Map<String, Object> side1Match = this.clientResponderModel.matchColor(primary1, primary_side_1);
        if (side1Match.size() > 0) {
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
            fault = this.clientResponderModel.insertIntoLogTable(side1Match.get("severity_case").toString(), Integer.parseInt(side1Match.get("severity_case_id").toString()), sideDetailId);
            if (fault > 0) {
                //sendSmsToAssignedFor("", side1Match.get("severity_case").toString());
            }
        }

        primary2 = settingColorBit(primary2);
        primary_side_2 = settingColorBit(primary_side_2);
        Map<String, Object> side2Match = this.clientResponderModel.matchColor(primary2, primary_side_2);
        if (side2Match.size() > 0) {
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            fault = this.clientResponderModel.insertIntoLogTable(side2Match.get("severity_case").toString(), Integer.parseInt(side2Match.get("severity_case_id").toString()), sideDetailId);
            if (fault > 0) {
               // sendSmsToAssignedFor("", side2Match.get("severity_case").toString());
            }
        }
//        
        primary3 = settingColorBit(primary3);
        primary_side_1 = settingColorBit(primary_side_3);
        Map<String, Object> side3Match = this.clientResponderModel.matchColor(primary3, primary_side_3);
        if (side3Match.size() > 0) {
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
            fault = this.clientResponderModel.insertIntoLogTable(side3Match.get("severity_case").toString(), Integer.parseInt(side3Match.get("severity_case_id").toString()), sideDetailId);
            if (fault > 0) {
              //  sendSmsToAssignedFor("8318275529", side3Match.get("severity_case").toString());
            }
        }
//        
        primary4 = settingColorBit(primary4);
        primary_side_4 = settingColorBit(primary_side_4);
        Map<String, Object> side4Match = this.clientResponderModel.matchColor(primary4, primary_side_4);
        if (side1Match.isEmpty()) {
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
            int side4 = this.clientResponderModel.insertIntoLogTable(side4Match.get("severity_case").toString(), Integer.parseInt(side4Match.get("severity_case_id").toString()), sideDetailId);
            if (side4 > 0) {
             //   sendSmsToAssignedFor("", side4Match.get("severity_case").toString());
            }
        }

        secondary1 = settingColorBit(secondary1);
        secondary_side_1 = settingColorBit(secondary_side_1);
        Map<String, Object> side1SecondaryMatch = this.clientResponderModel.matchColor(secondary1, secondary_side_1);
        if (side1SecondaryMatch.size() > 0) {
            int sideDetailId = this.clientResponderModel.getSideId(1, junctionID, programVersionNumber);
            fault = this.clientResponderModel.insertIntoLogTable(side1SecondaryMatch.get("severity_case").toString(), Integer.parseInt(side1SecondaryMatch.get("severity_case_id").toString()), sideDetailId);
            if (fault > 0) {
           //     sendSmsToAssignedFor("", side1SecondaryMatch.get("severity_case").toString());
            }
        }

        secondary2 = settingColorBit(secondary2);
        secondary_side_2 = settingColorBit(secondary_side_2);
        Map<String, Object> side2SecondaryMatch = this.clientResponderModel.matchColor(secondary2, secondary_side_2);
        if (side2SecondaryMatch.size() > 0) {
            int sideDetailId = this.clientResponderModel.getSideId(2, junctionID, programVersionNumber);
            fault = this.clientResponderModel.insertIntoLogTable(side2SecondaryMatch.get("severity_case").toString(), Integer.parseInt(side2SecondaryMatch.get("severity_case_id").toString()), sideDetailId);
            if (fault > 0) {
              //  sendSmsToAssignedFor("", side2SecondaryMatch.get("severity_case").toString());
            }
        }
//        
        secondary3 = settingColorBit(secondary3);
        secondary_side_3 = settingColorBit(secondary_side_3);
        Map<String, Object> side3SecondaryMatch = this.clientResponderModel.matchColor(secondary3, secondary_side_3);
        if (side3SecondaryMatch.size() > 0) {
            int sideDetailId = this.clientResponderModel.getSideId(3, junctionID, programVersionNumber);
            fault = this.clientResponderModel.insertIntoLogTable(side3SecondaryMatch.get("severity_case").toString(), Integer.parseInt(side3SecondaryMatch.get("severity_case_id").toString()), sideDetailId);
            if (fault > 0) {
             //   sendSmsToAssignedFor("", side3SecondaryMatch.get("severity_case").toString());
            }
        }
//        
        secondary4 = settingColorBit(secondary4);
        secondary_side_4 = settingColorBit(secondary_side_4);
        Map<String, Object> side4SecondaryMatch = this.clientResponderModel.matchColor(secondary4, secondary_side_4);
        if (side1Match.isEmpty()) {
            int sideDetailId = this.clientResponderModel.getSideId(4, junctionID, programVersionNumber);
            int side4 = this.clientResponderModel.insertIntoLogTable(side4SecondaryMatch.get("severity_case").toString(), Integer.parseInt(side4SecondaryMatch.get("severity_case_id").toString()), sideDetailId);
            if (side4 > 0) {
               // sendSmsToAssignedFor("", side4SecondaryMatch.get("severity_case").toString());
            }
        }
        return fault;
    }

    public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
        String result = "";
        try {
            String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";//"http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
            String tempMessage = messageStr1;
            String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
            System.out.println("messageStr1 is = " + messageStr1);
            messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
            String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
            String url = host_url + queryString;
            result = call1URL(url);
            System.out.println("SMS URL: " + url);
        } catch (Exception e) {
            result = e.toString();
            System.out.println("SMSModel sendSMS() Error: " + e);
        }
        return result;
    }

    private String call1URL(String strURL) {
        String status = "";
        try {
            java.net.URL obj = new java.net.URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            status = httpReq.getResponseMessage();
        } catch (MalformedURLException me) {
            status = me.toString();
        } catch (IOException ioe) {
            status = ioe.toString();
        } catch (Exception e) {
            status = e.toString();
        }
        return status;
    }

    public String SunriseSunsetDetails(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int month = dataToProcess[firstStartDataPosition + 4];
        int crc = dataToProcess[firstStartDataPosition + 5] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 6];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
        int cityID = this.clientResponderModel.getCityName(junctionID);
        System.out.println("sunrise sunset  request--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + month + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
        List riseSetTime = this.clientResponderModel.getCitySunriseSunset(month, cityID);

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        response = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + month;
        Iterator iter = riseSetTime.iterator();
        while (iter.hasNext()) {
            response = response + " " + (Integer) iter.next();
        }
        response = response + " " + firstLastDelimiter + " " + secLastDelimiter;
        //System.out.println("Response of sunrisr sunset :" + response);
        return response;
    }

    public String JunctionSideNames(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int crc = dataToProcess[firstStartDataPosition + 4] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 5];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 6];

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

//        Junction juncID = (Junction) this.junctionList.get(Integer.valueOf(junctionID));
//        boolean regisStatus = juncID.isRegistration_status();
        System.out.println("junction side names request--- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + firstLastDelimiter + " " + secLastDelimiter);
        int noOfSides = this.clientResponderModel.getNoOfSides(junctionID, program_version_no);
        // response = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + functionNo ;
        response = junctionID + " " + program_version_no + " " + fileNoFromDB + " " + functionNo;
        List sideNameList = new ArrayList();
        sideNameList.add(this.clientResponderModel.getJunctionName(junctionID, program_version_no));
        for (int j = 1; j <= noOfSides; j++) {
            String sideName = this.clientResponderModel.getSideName(j, junctionID, program_version_no);
            sideNameList.add(sideName);
        }
        Iterator itr = sideNameList.iterator();
        while (itr.hasNext()) {
            response = response + " " + (String) itr.next();
        }
        response = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + response + " " + firstLastDelimiter + " " + secLastDelimiter;
        this.totalNoOfSides = noOfSides;

        return response;
    }

    public boolean sendResponse(String response)
            throws IOException {
        boolean status = false;
        byte[] finalBytes = null;
        if (response != null) {
            String[] b1 = response.split(" ");
            // byte[] bytes = new byte[b1.length];
            finalBytes = new byte[b1.length + 3];
            int[] k = new int[b1.length];
            for (int j = 0; j < b1.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                //   bytes[j] = ((byte) k[j]);
            }
            finalBytes = getFinalBytes(k);
            //this.outputStream.write(finalBytes);
            System.out.print("Server Response: ");
            for (int j = 0; j < finalBytes.length; j++) {
                System.out.print(finalBytes[j] + " ");
            }
            System.out.println(" ");
        }
        status = true;
        return status;
    }

    public byte[] sendResponseData(String response)
            throws IOException {
        boolean status = false;
        byte[] finalBytes = null;
        if (response != null) {
            String[] b1 = response.split(" ");
            // byte[] bytes = new byte[b1.length];
            finalBytes = new byte[b1.length + 3];
            int[] k = new int[b1.length];
            for (int j = 0; j < b1.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                //   bytes[j] = ((byte) k[j]);
            }
            finalBytes = getFinalBytes(k);
            //this.outputStream.write(finalBytes);
            System.out.print("Server Response: ");
            for (int j = 0; j < finalBytes.length; j++) {
                System.out.print(finalBytes[j] + " ");
            }
            System.out.println(" ");
        }
        status = true;
        return finalBytes;
    }

    public void sendJunctionResponse(String response) throws IOException {
        if (response != null) {
            String[] b1 = response.split(" ");
//            byte[] bytes = new byte[b1.length - (this.totalNoOfSides + 2)];
//            byte[] bytes1 = null;
//            byte[] bytes2 = new byte[2];
//            int[] k = new int[b1.length];
//            int j = 0;
//            for (j = 0; j <= 3; j++) {
//                k[j] = Integer.parseInt(b1[j]);
//                bytes[j] = ((byte) k[j]);
//            }
//
//            this.outputStream.write(bytes);
//            for (; j < b1.length - 2; j++) {
//                this.outputStream.write(32);
//
//                bytes1 = b1[j].getBytes();
//                this.outputStream.write(bytes1);
//                bytes1 = null;
//            }
//            this.outputStream.write(32);
//            for (int i = 0; j < b1.length; i++) {
//                k[j] = Integer.parseInt(b1[j]);
//                bytes2[i] = ((byte) k[j]);
//
//                j++;
//            }
//            this.outputStream.write(bytes2);

            byte[] startbytes = new byte[6];
            byte[] midbytes = null;
            byte[] endbytes = new byte[2];
            int[] k = new int[b1.length];
            int j = 0;
            for (j = 0; j < startbytes.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                startbytes[j] = ((byte) k[j]);
            }
            this.outputStream.write(startbytes);

            for (; j < b1.length - 2; j++) {
                this.outputStream.write(32);
                midbytes = b1[j].getBytes();
                this.outputStream.write(midbytes);
                midbytes = null;
            }

            this.outputStream.write(32);

            for (int i = 0; j < b1.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                endbytes[i] = ((byte) k[j]);
                i++;
            }
            this.outputStream.write(endbytes);
            System.out.println("Done!!");
        }
    }

    public boolean sendErrorResponse(int error) throws IOException {
        String errorResponse = "126 126 126 126 0 0 0 6 " + error + " 0 0 0 125 125";
        return sendResponse(errorResponse);
    }

    public boolean sendErrorResponse(String response)
            throws IOException {
        boolean status = false;
        if (response != null) {
            byte[] bytes = response.getBytes();
            this.outputStream.write(bytes);
        }
        status = true;
        return status;
    }

    public byte[] getFinalBytes(int[] k) {
        int length = k.length;

        int dSize = 0;
        int dSum = 0;

        byte dataSize[] = new byte[2];
        byte[] finalBytes = new byte[length + 3];
        try {

            for (int i = 4; i < (length - 2); i++) {
                dSum += k[i];
                dSize++;
            }

            dataSize[0] = (byte) ((dSize >> 8) & 0xFF); //>> 8 discards the lowest 8 bits by moving all bits 8 places to the right.
            dataSize[1] = (byte) (dSize & 0xFF); //& 0xFF masks the lowest eight bits.

            byte lowByte = (byte) (dSum & 0xFF); //get lowest 8-bit
            byte crcByte = (byte) (0xFF - lowByte); // subtract lowest 8-bit from 255

            for (int i = 0, j = 0; j < finalBytes.length; j++) {
                if (j == 4) {
                    finalBytes[j] = dataSize[0];
                } else if (j == 5) {
                    finalBytes[j] = dataSize[1];
                } else if (j == finalBytes.length - 3) {
                    finalBytes[j] = crcByte;
                } else {
                    finalBytes[j] = ((byte) k[i]);
                    i++;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in ClientResponder getFinalBytes: " + e);
        }
        return finalBytes;
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

    public int getNoOfDays(int month_no, int year) {
        int noOfDays = 0;
        try {
            if (month_no == 2) { // february
                if (year % 4 == 0) {
                    noOfDays = 29; //leap year
                } else {
                    noOfDays = 28;
                }
            } else if (month_no == 4 || month_no == 6 || month_no == 9 || month_no == 11) {
                noOfDays = 30;
            } else {
                noOfDays = 31;
            }
        } catch (Exception e) {
            System.out.println("ClientResponder getNoOfDays error: " + e);
        }
        return noOfDays;
    }

    private String callURL(String strURL) {
        String responseMsg;
        try {
            URL urlObj = new URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) urlObj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            responseMsg = readWebServerResponse(httpReq.getInputStream());
        } catch (MalformedURLException mfUrlEx) {
            responseMsg = "Invalid URL or unknown protocol.";
        } catch (IOException ioEx) {
            responseMsg = "Unable to connect to the WebServer.";
        } catch (Exception ex) {
            responseMsg = ex.toString();
        }
        return responseMsg;
    }

    private String readWebServerResponse(InputStream inputStream) {
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String response = "";
        try {
            String nextLine = rd.readLine();
            while (nextLine != null) {
                response = response + nextLine;
                nextLine = rd.readLine();
            }
        } catch (IOException ioEx) {
            response = "Unable to read response from the WebServer.";
            System.out.println("ClientResponseReader readWebServerResponse() Error: " + ioEx);
        }
        return response;
    }

    public void setClientResponse(String message) {
        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException uEx) {
            System.out.println("ClientResponseReader setClientResponse() Error: " + uEx);
        }

        String url = "http://localhost/ts/modemResReadSaveCont?task=setClientResponse&message=" + message;
        callURL(url);
    }

    public String updateModemTime(String[] inputs) {
        String response;
        try {
            String hours = inputs[1];
            String minutes = inputs[2];
            String seconds = inputs[3];

            String url = "http://localhost/ts/?req=modem&h=" + hours + "&m=" + minutes + "&s=" + seconds;
            response = callURL(url);
        } catch (Exception ex) {
            response = "Invalid update arguments.";
            System.out.println("ClientResponseReader updateModemTime() Error: " + ex);
        }
        return response;
    }

    public void setConnectedIpList() {
        List<ClientResponderWS> connectedIpList = (List<ClientResponderWS>) this.ctx.getAttribute("connectedIp");
        if (this.clientSocket.isClosed()) {
            if (connectedIpList != null && connectedIpList.contains(this)) {
                connectedIpList.remove(this);
                this.ctx.setAttribute("connectedIp", connectedIpList);
            }
        }
    }

    public void setMap(Map<String, ClientResponderWS> map) {
        this.map = map;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return this.clientSocket;
    }

    public void setTcpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setJunction(Junction junction) {
        this.junction = junction;
    }

    public void setAsync(Async async) {
        this.async = async;
    }

    public void setModemResReadSaveCont(ModemResReadSaveController modemResReadSaveCont) {
        this.modemResReadSaveCont = modemResReadSaveCont;
    }

    public void setContext(ServletContext ctx) {
        this.ctx = ctx;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setJunctionList(Map<Integer, Junction> junctionList) {
        this.junctionList = junctionList;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPassword = db_userPasswrod;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public void setPlanInfoRefreshList(PlanInfo planInfoRefreshList) {
        this.planInfoRefreshList = planInfoRefreshList;
    }

    public static PlanInfo getPlanInfoRefreshList() {
        return planInfoRefreshList;
    }

    public int getClearanceSide() {
        return clearanceSide;
    }

    public void setClearanceSide(int clearanceSide) {
        this.clearanceSide = clearanceSide;
    }

    public boolean isRequestToUpdateProgramVersionNo() {
        return requestToUpdateProgramVersionNo;
    }

    public void setRequestToUpdateProgramVersionNo(boolean requestToUpdateProgramVersionNo) {
        this.requestToUpdateProgramVersionNo = requestToUpdateProgramVersionNo;
    }

    public boolean isRequestForClearance() {
        return requestForClearance;
    }

    public void setRequestForClearance(boolean requestForClearance) {
        this.requestForClearance = requestForClearance;
    }

    public int getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(int activityNo) {
        this.activityNo = activityNo;
    }

    public int getActivitySide() {
        return activitySide;
    }

    public void setActivitySide(int activitySide) {
        this.activitySide = activitySide;
    }

    public boolean isRequestForActivity() {
        return requestForActivity;
    }

    public void setRequestForActivity(boolean requestForActivity) {
        this.requestForActivity = requestForActivity;
    }

    public boolean isIsJunctionLive() {
        return isJunctionLive;
    }

    public void setIsJunctionLive(boolean isJunctionLive) {
        this.isJunctionLive = isJunctionLive;
    }

    public String getIpLoginTimstamp() {
        return ipLoginTimstamp;
    }

    public void setIpLoginTimstamp(String ipLoginTimstamp) {
        this.ipLoginTimstamp = ipLoginTimstamp;
    }

    public Boolean getIpStatus() {
        return ipStatus;
    }

    public void setIpStatus(Boolean ipStatus) {
        this.ipStatus = ipStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public int getNoOfRequestReceived() {
        return noOfRequestReceived;
    }

    public void setNoOfRequestReceived(int noOfRequestReceived) {
        this.noOfRequestReceived = noOfRequestReceived;
    }

    /*private String phaseFunctionOld(byte[] dataToProcess, int firstStartDataPosition) {
        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        int planNo = 0;
        int phaseNo = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);
        if ((dataToProcess[firstStartDataPosition + 19] == 125) && (dataToProcess[firstStartDataPosition + 4] != 127)) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            phaseNo = dataToProcess[firstStartDataPosition + 5];
            for (int i = (firstStartDataPosition + 6); i <= (firstStartDataPosition + 17); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
            crc = dataToProcess[firstStartDataPosition + 18] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 19];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("phase function first request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + planNo + " " + phaseNo + " " + extraByte + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            try {

                int phaseTime = this.clientResponderModel.getPhaseTime(junctionID, planNo, phaseNo);

                List<Integer> greenTimings = this.clientResponderModel.getGreenTiming(junctionID, planNo, phaseNo);
                int green1 = greenTimings.get(0);
                int green2 = greenTimings.get(1);
                int green3 = greenTimings.get(2);
                int green4 = greenTimings.get(3);
                int green5 = greenTimings.get(4);

                List<Integer> sidesValue = this.clientResponderModel.getSideValue(junctionID, planNo, phaseNo);
                int side13 = sidesValue.get(0);
                int side24 = sidesValue.get(1);
                int side5 = sidesValue.get(2);

                int leftGreen = this.clientResponderModel.getLeftGreen(junctionID, planNo, phaseNo);
                int padestrianTime = this.clientResponderModel.getPadestrianTime(junctionID, planNo, phaseNo);
                int GPIO = this.clientResponderModel.getGPIO(junctionID, planNo, phaseNo);

                responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + phaseNo + " " + phaseTime + " " + green1 + " " + green2 + " " + green3 + " " + green4 + " " + green5 + " " + side13 + " " + side24 + " " + side5 + " " + leftGreen + " " + padestrianTime + " " + GPIO;

                responseVal = responseVal + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        } else if (dataToProcess[firstStartDataPosition + 19] != 125) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            phaseNo = dataToProcess[firstStartDataPosition + 5];
            int phaseTime = dataToProcess[firstStartDataPosition + 6];
            int green1 = dataToProcess[firstStartDataPosition + 7];
            int green2 = dataToProcess[firstStartDataPosition + 8];
            int green3 = dataToProcess[firstStartDataPosition + 9];
            int green4 = dataToProcess[firstStartDataPosition + 10];
            int green5 = dataToProcess[firstStartDataPosition + 11];

            int side13 = dataToProcess[firstStartDataPosition + 12];
            int side24 = dataToProcess[firstStartDataPosition + 13];
            int side5 = dataToProcess[firstStartDataPosition + 14];

            int leftGreen = dataToProcess[firstStartDataPosition + 15];
            int padestrianTime = dataToProcess[firstStartDataPosition + 16];
            int gpio = dataToProcess[firstStartDataPosition + 17];

            for (int i = (firstStartDataPosition + 18); i <= (firstStartDataPosition + 29); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
            crc = dataToProcess[firstStartDataPosition + 30] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 31];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 22];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("phase function second request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + planNo + " " + phaseNo + " " + phaseTime + " " + green1 + " " + green2 + " " + green3 + " " + green4 + " " + green5 + " " + side13 + " " + side24 + " " + side5 + " " + leftGreen + " " + padestrianTime + " " + gpio + " " + extraByte + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            try {
                int phaseStatus = 0;
                int phaseTimeR = this.clientResponderModel.getPhaseTime(junctionID, planNo, phaseNo);

                List<Integer> greenTimings = this.clientResponderModel.getGreenTiming(junctionID, planNo, phaseNo);
                int green1R = greenTimings.get(0);
                int green2R = greenTimings.get(1);
                int green3R = greenTimings.get(2);
                int green4R = greenTimings.get(3);
                int green5R = greenTimings.get(4);

                List<Integer> sidesValue = this.clientResponderModel.getSideValue(junctionID, planNo, phaseNo);
                int side13R = sidesValue.get(0);
                int side24R = sidesValue.get(1);
                int side5R = sidesValue.get(2);

                int leftGreenR = this.clientResponderModel.getLeftGreen(junctionID, planNo, phaseNo);
                int padestrianTimeR = this.clientResponderModel.getPadestrianTime(junctionID, planNo, phaseNo);
                int GPIOR = this.clientResponderModel.getGPIO(junctionID, planNo, phaseNo);

                if (phaseTime == phaseTimeR && green1 == green1R && green2 == green2R && green3 == green3R
                        && green4 == green4R && green5 == green5R && side13 == side13R && side24 == side24R && side5 == side5R
                        && leftGreen == leftGreenR && padestrianTime == padestrianTimeR && gpio == GPIOR) {
                    phaseStatus = 1;
                }

                responseVal = firstStartDelimiter + " " + firstStartDelimiter + " " + firstStartDelimiter + " " + firstStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + phaseNo + " " + phaseTime;
                responseVal = responseVal + " " + green1 + " " + green2 + " " + green3 + " " + green4 + " " + green5 + " " + side13 + " " + side24 + " " + side5 + " " + leftGreen + " " + padestrianTime + " " + gpio + " " + phaseStatus;
                responseVal = responseVal + " " + extraByte + " " + firstLastDelimiter + " " + secLastDelimiter;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        }

//        Junction juncID = (Junction) this.junctionList.get(Integer.valueOf(junctionID));
//        boolean regisStatus = juncID.isRegistration_status();
        return responseVal;
    }*/
    private String phaseFunction(byte[] dataToProcess, int firstStartDataPosition) {
        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        int planNo = 0;
        int phaseNo = 0;
        int planId = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);
        if ((dataToProcess[firstStartDataPosition + 19] == 125) && (dataToProcess[firstStartDataPosition + 4] != 127)) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            planId = dataToProcess[firstStartDataPosition + 5];
            phaseNo = dataToProcess[firstStartDataPosition + 6];
            for (int i = (firstStartDataPosition + 7); i <= (firstStartDataPosition + 17); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
            crc = dataToProcess[firstStartDataPosition + 18] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 19];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("phase function first request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + planNo + " " + phaseNo + " " + extraByte + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            try {
                Map<String, String> phase_detail = this.clientResponderModel.getPhaseDetail(junctionID, phaseNo);

                int phase_time = Integer.parseInt(phase_detail.get("phase_time"));
                int phase_id = Integer.parseInt(phase_detail.get("phase_info_id"));
                int phase_no = Integer.parseInt(phase_detail.get("phase_no"));
                int green1 = Integer.parseInt(phase_detail.get("green1"));
                int green2 = Integer.parseInt(phase_detail.get("green2"));
                int green3 = Integer.parseInt(phase_detail.get("green3"));
                int green4 = Integer.parseInt(phase_detail.get("green4"));
                int green5 = Integer.parseInt(phase_detail.get("green5"));

                int side13 = Integer.parseInt(phase_detail.get("side13"));
                int side24 = Integer.parseInt(phase_detail.get("side24"));
                int side5 = Integer.parseInt(phase_detail.get("side5"));

                int leftGreen = Integer.parseInt(phase_detail.get("left_green"));
                int padestrianTime = Integer.parseInt(phase_detail.get("padestrian_info"));

                int GPIO = Integer.parseInt(phase_detail.get("GPIO"));
                int map_id = Integer.parseInt(phase_detail.get("map_id"));

                responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + planId + " " + map_id + " " + phase_id + " " + phase_time + " " + green1 + " " + green2 + " " + green3 + " " + green4 + " " + green5 + " " + side13 + " " + side24 + " " + side5 + " " + leftGreen + " " + padestrianTime + " " + GPIO;

                responseVal = responseVal + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        } else if (dataToProcess[firstStartDataPosition + 19] != 125) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            planNo = dataToProcess[firstStartDataPosition + 5];
            phaseNo = dataToProcess[firstStartDataPosition + 6];
            int phase_id = dataToProcess[firstStartDataPosition + 7];
            int phaseTime = dataToProcess[firstStartDataPosition + 8];
            int green1 = dataToProcess[firstStartDataPosition + 9];
            int green2 = dataToProcess[firstStartDataPosition + 10];
            int green3 = dataToProcess[firstStartDataPosition + 11];
            int green4 = dataToProcess[firstStartDataPosition + 12];
            int green5 = dataToProcess[firstStartDataPosition + 13];

            int side13 = dataToProcess[firstStartDataPosition + 14];
            int side24 = dataToProcess[firstStartDataPosition + 15];
            int side5 = dataToProcess[firstStartDataPosition + 16];

            int leftGreen = dataToProcess[firstStartDataPosition + 17];
            int padestrianTime = dataToProcess[firstStartDataPosition + 18];
            int gpio = dataToProcess[firstStartDataPosition + 19];

            for (int i = (firstStartDataPosition + 20); i <= (firstStartDataPosition + 29); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
            crc = dataToProcess[firstStartDataPosition + 30] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 31];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 22];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("phase function second request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + planNo + " " + phaseNo + " " + phaseTime + " " + green1 + " " + green2 + " " + green3 + " " + green4 + " " + green5 + " " + side13 + " " + side24 + " " + side5 + " " + leftGreen + " " + padestrianTime + " " + gpio + " " + extraByte + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            try {
                int phaseStatus = 0;
                Map<String, String> phase_detail = this.clientResponderModel.getPhaseDetailCheck(junctionID, planNo, planId, phaseNo);
                int phase_timeR = Integer.parseInt(phase_detail.get("phase_time"));
                int phase_idR = Integer.parseInt(phase_detail.get("phase_info_id"));
                int phase_noR = Integer.parseInt(phase_detail.get("phase_no"));
                int green1R = Integer.parseInt(phase_detail.get("green1"));
                int green2R = Integer.parseInt(phase_detail.get("green2"));
                int green3R = Integer.parseInt(phase_detail.get("green3"));
                int green4R = Integer.parseInt(phase_detail.get("green4"));
                int green5R = Integer.parseInt(phase_detail.get("green5"));

                int side13R = Integer.parseInt(phase_detail.get("side13"));
                int side24R = Integer.parseInt(phase_detail.get("side24"));
                int side5R = Integer.parseInt(phase_detail.get("side5"));

                int leftGreenR = Integer.parseInt(phase_detail.get("left_green"));
                int padestrianTimeR = Integer.parseInt(phase_detail.get("padestrian_info"));

                int GPIOR = Integer.parseInt(phase_detail.get("GPIO"));
                int map_idR = Integer.parseInt(phase_detail.get("map_id"));
                if (phaseTime == phase_timeR && phase_id == phase_idR && phaseNo == phase_noR && green1 == green1R && green2 == green2R && green3 == green3R
                        && green4 == green4R && green5 == green5R && side13 == side13R && side24 == side24R && side5 == side5R
                        && leftGreen == leftGreenR && padestrianTime == padestrianTimeR && gpio == GPIOR) {
                    phaseStatus = 1;
                }

                responseVal = firstStartDelimiter + " " + firstStartDelimiter + " " + firstStartDelimiter + " " + firstStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + planNo + " " + planId + " " + map_idR + " " + phase_id + " " + phaseTime;
                responseVal = responseVal + " " + green1 + " " + green2 + " " + green3 + " " + green4 + " " + green5 + " " + side13 + " " + side24 + " " + side5 + " " + leftGreen + " " + padestrianTime + " " + gpio + " " + phaseStatus;
                responseVal = responseVal + " " + extraByte + " " + firstLastDelimiter + " " + secLastDelimiter;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        }

//        Junction juncID = (Junction) this.junctionList.get(Integer.valueOf(junctionID));
//        boolean regisStatus = juncID.isRegistration_status();
        return responseVal;
    }

    public String junctionFunction(byte[] dataToProcess, int firstStartDataPosition) {
        String imeiNo = "";
        String response = "";
        String extraBytes = "";
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];

            for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 18); i++) {
                char ch = (char) dataToProcess[i];
                imeiNo = imeiNo + ch;
            }

            //    for (int i = (firstStartDataPosition + 20); i <= (firstStartDataPosition + 30); i++) {
            for (int i = (firstStartDataPosition + 25); i <= (firstStartDataPosition + 30); i++) {
                //char ch = (char) dataToProcess[i];
                if (extraBytes == "") {
                    extraBytes = extraBytes + dataToProcess[i];
                } else {
                    extraBytes = extraBytes + " " + dataToProcess[i];
                }

            }

            //int crc = dataToProcess[firstStartDataPosition + 19] & 0xFF;
            int crc = dataToProcess[firstStartDataPosition + 31] & 0xFF;
            //int firstLastDelimiter = dataToProcess[firstStartDataPosition + 20];
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 32];
            //int secLastDelimiter = dataToProcess[firstStartDataPosition + 21];
            int secLastDelimiter = firstLastDelimiter;

            int junctIDFromDB = this.clientResponderModel.getJunctionID(imeiNo);
            int programVersionNoFromDB = junctIDFromDB == 0 ? 0 : this.clientResponderModel.getProgramVersionNo(junctIDFromDB);

            int registrationStatus = junctIDFromDB == 0 ? 0 : this.clientResponderModel.updateJunctionTransferredStatus(junctionID, program_version_no, "NO") ? 0 : 0;

            int noOfDateSlot = this.clientResponderModel.getNoOfDateSlot(junctIDFromDB, programVersionNoFromDB);
            int noOfDaySlot = this.clientResponderModel.getNoOfDaySlot(junctIDFromDB, programVersionNoFromDB) + 1;
            int totalNoOfPlan = this.clientResponderModel.getTotalNoOfPlan(junctIDFromDB, programVersionNoFromDB);
            int noOfSides = this.clientResponderModel.getNoOfSides(junctIDFromDB, programVersionNoFromDB);
            int pedestrianTime = this.clientResponderModel.getPedestrianTime(junctIDFromDB, programVersionNoFromDB);
            int fileNoFromDB = this.clientResponderModel.getFileNo(junctIDFromDB, programVersionNoFromDB);
            int totalNoOfPhase = this.clientResponderModel.getTotalNoOfPhase(junctIDFromDB, programVersionNoFromDB);
            ////// adding sides pole type
            int Side1poleType = this.clientResponderModel.getPoleType(junctIDFromDB, programVersionNoFromDB, 1);
            int Side2poleType = this.clientResponderModel.getPoleType(junctIDFromDB, programVersionNoFromDB, 2);
            int Side3poleType = this.clientResponderModel.getPoleType(junctIDFromDB, programVersionNoFromDB, 3);
            int Side4poleType = this.clientResponderModel.getPoleType(junctIDFromDB, programVersionNoFromDB, 4);
            int Side5poleType = this.clientResponderModel.getPoleType(junctIDFromDB, programVersionNoFromDB, 5);

            //////////
            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctIDFromDB, fileNo, programVersionNoFromDB);
            }
            fileNoFromDB = this.clientResponderModel.getFileNo(junctIDFromDB, programVersionNoFromDB);

//            System.out.println("register modem request--- FirstStartDelimliter=" + firstStartDelimiter + " SecondStartDelimliter=" + secStartDelimiter + " dataSize1=" + dataSize1 + " dataSize2=" + dataSize2 + " junctionID=" + junctionID + " program_version_no=" + program_version_no + " file_no=" + fileNo + " functionNo=" + functionNo + " imeiNo= " + imeiNo + " crc=" + crc + " firstLastDelimiter= " + firstLastDelimiter + " secLastDelimiter= " + secLastDelimiter);
//            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctIDFromDB + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + registrationStatus + " " + noOfSides + " " + noOfDateSlot + " " + noOfDaySlot + " " + totalNoOfPlan + " " + pedestrianTime + " " + totalNoOfPhase+ " " + extraBytes + " " + firstLastDelimiter + " " + firstLastDelimiter;
            System.out.println("register modem request--- FirstStartDelimliter=" + firstStartDelimiter + " SecondStartDelimliter=" + secStartDelimiter + " dataSize1=" + dataSize1 + " dataSize2=" + dataSize2 + " junctionID=" + junctionID + " program_version_no=" + program_version_no + " file_no=" + fileNo + " functionNo=" + functionNo + " imeiNo= " + imeiNo + " crc=" + crc + " firstLastDelimiter= " + firstLastDelimiter + " secLastDelimiter= " + secLastDelimiter);
            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctIDFromDB + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + registrationStatus + " " + noOfSides + " " + noOfDateSlot + " " + noOfDaySlot + " " + totalNoOfPlan + " " + pedestrianTime + " " + totalNoOfPhase + " " + Side1poleType + " " + Side2poleType + " " + Side3poleType + " " + Side4poleType + " " + Side5poleType + " " + extraBytes + " " + firstLastDelimiter + " " + firstLastDelimiter;

            System.out.println(response);
        } catch (Exception e) {
            System.out.println("ClientResponder checkRegistration exception: " + e);
        }
        return response;
    }

    public String DateFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String response = "";
        String extraBytes = "";
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];

            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];
            int dateSlot = dataToProcess[firstStartDataPosition + 4];
            int orderNoOfDateSlot = dataToProcess[firstStartDataPosition + 5];

            for (int i = (firstStartDataPosition + 6); i <= (firstStartDataPosition + 13); i++) {
                //char ch = (char) dataToProcess[i];
                if (extraBytes == "") {
                    extraBytes = extraBytes + dataToProcess[i];
                } else {
                    extraBytes = extraBytes + " " + dataToProcess[i];
                }

            }

            int crc = dataToProcess[firstStartDataPosition + 14] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 15];
            int secLastDelimiter = firstLastDelimiter;

            int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

            int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);

            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctionID, fileNo, programVersionNoFromDB);
            }
            List<String> dateDetail = this.clientResponderModel.getDateDetail(junctionID, orderNoOfDateSlot);
            fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);
            String fromDateFromDB = dateDetail.get(0);
            String toDateFromDB = dateDetail.get(1);
            int date_detail_id = Integer.parseInt(dateDetail.get(2));
            int planNoFromDB = this.clientResponderModel.getNoOfPlanInDate(junctionID, orderNoOfDateSlot, date_detail_id);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fromDate = LocalDate.parse(fromDateFromDB, df);
            int fromYear = fromDate.getYear() - 2000;
            int fromMonth = fromDate.getMonthValue();
            int fromDay = fromDate.getDayOfMonth();

            LocalDate toDate = LocalDate.parse(toDateFromDB, df);
            int toYear = toDate.getYear() - 2000;
            int toMonth = toDate.getMonthValue();
            int toDay = toDate.getDayOfMonth();

            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + orderNoOfDateSlot + " " + date_detail_id + " " + fromDay + " " + fromMonth + " " + fromYear + " " + toDay + " " + toMonth + " " + toYear + " " + planNoFromDB + " " + extraBytes + " " + firstLastDelimiter + " " + firstLastDelimiter;
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("ClientResponder checkRegistration exception: " + e);
        }
        return response;
    }

    public String DayFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String response = "";
        String extraBytes = "";
        int planNoFromDB = 0;
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
            int dataSize1 = dataToProcess[firstStartDataPosition - 2];
            int dataSize2 = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];
            int day = dataToProcess[firstStartDataPosition + 4];
            int orderNoOfDaySlot = dataToProcess[firstStartDataPosition + 5];

            for (int i = (firstStartDataPosition + 6); i <= (firstStartDataPosition + 13); i++) {
                //char ch = (char) dataToProcess[i];
                if (extraBytes == "") {
                    extraBytes = extraBytes + dataToProcess[i];
                } else {
                    extraBytes = extraBytes + " " + dataToProcess[i];
                }

            }

            int crc = dataToProcess[firstStartDataPosition + 14] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 15];
            int secLastDelimiter = firstLastDelimiter;

            int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

            int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);

            if (fileNo != fileNoFromDB) {
                this.clientResponderModel.updateFileNo(junctionID, fileNo, programVersionNoFromDB);
            }
            fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);
            String dayFromDB = "";
            int day_id = this.clientResponderModel.getDayId(junctionID, orderNoOfDaySlot);
            int totalNoOfDaySlot = this.clientResponderModel.getNoOfDaySlot(junctionID, programVersionNoFromDB);
            if (orderNoOfDaySlot > totalNoOfDaySlot) {
                //dayFromDB = this.clientResponderModel.getWeekDay(junctionID); 
                dayFromDB = "weekday";
                planNoFromDB = this.clientResponderModel.getWeekDayNoOfPlanInDay(junctionID);
            } else {
                dayFromDB = this.clientResponderModel.getDay(junctionID, orderNoOfDaySlot, day_id);
                planNoFromDB = this.clientResponderModel.getNoOfPlanInDay(junctionID, orderNoOfDaySlot, day_id);
            }
            int dayNo = 0;
            switch (dayFromDB.toLowerCase()) {
                case "monday":
                    dayNo = 1;
                    break;
                case "tuesday":
                    dayNo = 2;
                    break;
                case "wednesday":
                    dayNo = 3;
                    break;
                case "thursday":
                    dayNo = 4;
                    break;
                case "friday":
                    dayNo = 5;
                    break;
                case "saturday":
                    dayNo = 6;
                    break;
                case "sunday":
                    dayNo = 7;
                    break;
                default:
                    dayNo = 8;
            }

            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + orderNoOfDaySlot + " " + day_id + " " + dayNo + " " + planNoFromDB + " " + extraBytes + " " + firstLastDelimiter + " " + firstLastDelimiter;
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("ClientResponder checkRegistration exception: " + e);
        }
        return response;
    }

    public String planDetailFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int planNo = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        if ((dataToProcess[firstStartDataPosition + 18] == 125) && (dataToProcess[firstStartDataPosition + 4] != 127)) {
            planNo = dataToProcess[firstStartDataPosition + 4];
            crc = dataToProcess[firstStartDataPosition + 17] & 0xFF;
            firstLastDelimiter = dataToProcess[firstStartDataPosition + 18];
            //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
            secLastDelimiter = firstLastDelimiter;
            System.out.println("Junction_id" + junctionID);
            System.out.println("plan function first request -- " + firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + planNo + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter);
            responseVal = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo;
            try {
                Map<String, String> planDetail = this.clientResponderModel.getPlanDetail(junctionID, planNo);
                int plan_id = Integer.parseInt(planDetail.get("plan_id"));
                int planNoDB = Integer.parseInt(planDetail.get("plan_no"));
                String strPlanMode = planDetail.get("mode");
                String planMode = "0";
                if (strPlanMode.equals("Blinker")) {
                    planMode = "1";
                } else if (strPlanMode.equals("Signal")) {
                    if (!this.clientResponderModel.isPedestrian(junctionID, program_version_no)) {
                        planMode = "2";
                    } else {
                        planMode = "3";
                    }
                }
//                

                int phaseNo = this.clientResponderModel.getPhaseNumberNew(junctionID, plan_id);

                int onTimeHrFromDB = Integer.parseInt(planDetail.get("on_time_hour"));
                int onTimeMinFromDB = Integer.parseInt(planDetail.get("on_time_min"));
                int offTimeHrFromDB = Integer.parseInt(planDetail.get("off_time_hour"));
                int offTimeMinFromDB = Integer.parseInt(planDetail.get("off_time_min"));

                int noOfSides = this.clientResponderModel.getNoOfSides(junctionID, program_version_no);
                int side1_green_time = Integer.parseInt(planDetail.get("side1_green_time"));
                int side2_green_time = Integer.parseInt(planDetail.get("side2_green_time"));
                int side3_green_time = Integer.parseInt(planDetail.get("side3_green_time"));
                int side4_green_time = Integer.parseInt(planDetail.get("side4_green_time"));
                int side5_green_time = Integer.parseInt(planDetail.get("side5_green_time"));
                int side1_amber_time = Integer.parseInt(planDetail.get("side1_amber_time"));
                int side2_amber_time = Integer.parseInt(planDetail.get("side2_amber_time"));
                int side3_amber_time = Integer.parseInt(planDetail.get("side3_amber_time"));
                int side4_amber_time = Integer.parseInt(planDetail.get("side4_amber_time"));
                int side5_amber_time = Integer.parseInt(planDetail.get("side5_amber_time"));

                for (int i = (firstStartDataPosition + 5); i <= (firstStartDataPosition + 16); i++) {
                    if (extraByte == "") {
                        extraByte = extraByte + dataToProcess[i];
                    } else {
                        extraByte = extraByte + " " + dataToProcess[i];
                    }
                }
                responseVal = responseVal + " " + plan_id + " " + planNoDB + " " + onTimeHrFromDB + " " + onTimeMinFromDB + " " + offTimeHrFromDB + " " + offTimeMinFromDB;
                responseVal = responseVal + " " + side1_green_time + " " + side2_green_time + " " + side3_green_time + " " + side4_green_time + " " + side5_green_time;
                responseVal = responseVal + " " + side1_amber_time + " " + side2_amber_time + " " + side3_amber_time + " " + side4_amber_time + " " + side5_amber_time;
                responseVal = responseVal + " " + planMode + " " + phaseNo + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
        }

        return responseVal;
    }

    public String junctionPlanMapDateFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int date_id = 0;
        int day_id = 0;
        int plan_no = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        date_id = dataToProcess[firstStartDataPosition + 4];
        plan_no = dataToProcess[firstStartDataPosition + 5];
        crc = dataToProcess[firstStartDataPosition + 18] & 0xFF;
        firstLastDelimiter = dataToProcess[firstStartDataPosition + 19];
        //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
        secLastDelimiter = firstLastDelimiter;
        System.out.println("Junction_id" + junctionID);

        responseVal = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNo + " " + functionNo;
        try {
            Map<String, Integer> junctionPlanDetail = this.clientResponderModel.getJunctionPlanMapDate(junctionID, date_id, plan_no);

            int plan_id = junctionPlanDetail.get("plan_id");
            int order_no = junctionPlanDetail.get("order_no");
            int map_id = junctionPlanDetail.get("map_id");
            int noOfPhase = this.clientResponderModel.getPhaseNoMapDate(junctionID, date_id, plan_id);
            for (int i = (firstStartDataPosition + 6) + 2; i <= (firstStartDataPosition + 18); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }

            responseVal = responseVal + " " + date_id + " " + day_id + " " + plan_id + " " + order_no + " " + map_id + " " + noOfPhase + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;

        } catch (Exception e) {
            System.out.println("Error - clientResponder - " + e);
        }

        return responseVal;
    }

    public String junctionPlanMapDayFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int date_id = 0;
        int day_id = 0;
        int plan_no = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        day_id = dataToProcess[firstStartDataPosition + 4];
        plan_no = dataToProcess[firstStartDataPosition + 5];
        crc = dataToProcess[firstStartDataPosition + 18] & 0xFF;
        firstLastDelimiter = dataToProcess[firstStartDataPosition + 19];
        //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
        secLastDelimiter = firstLastDelimiter;
        System.out.println("Junction_id" + junctionID);

        responseVal = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNo + " " + functionNo;
        try {
            Map<String, Integer> junctionPlanDetail = this.clientResponderModel.getJunctionPlanMapDay(junctionID, day_id, plan_no);

            int plan_id = junctionPlanDetail.get("plan_id");
            int order_no = junctionPlanDetail.get("order_no");
            int map_id = junctionPlanDetail.get("map_id");
            int noOfPhase = this.clientResponderModel.getPhaseNoMapDay(junctionID, day_id, plan_id,map_id);
            for (int i = (firstStartDataPosition + 6) + 2; i <= (firstStartDataPosition + 18); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }

            responseVal = responseVal + " " + date_id + " " + day_id + " " + plan_id + " " + order_no + " " + map_id + " " + noOfPhase + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;

        } catch (Exception e) {
            System.out.println("Error - clientResponder - " + e);
        }

        return responseVal;
    }

    public String phaseMapFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        int map_id = 0;
        int phase_no = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        map_id = dataToProcess[firstStartDataPosition + 4];
        phase_no = dataToProcess[firstStartDataPosition + 5];
        crc = dataToProcess[firstStartDataPosition + 18] & 0xFF;
        firstLastDelimiter = dataToProcess[firstStartDataPosition + 19];
        //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
        secLastDelimiter = firstLastDelimiter;
        System.out.println("Junction_id" + junctionID);

        responseVal = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNo + " " + functionNo;
        try {
            Map<String, Integer> junctionPlanDetail = this.clientResponderModel.getPhaseMapDay(junctionID, map_id, phase_no);

            int phase_map_id = junctionPlanDetail.get("phase_map_id");
            int phase_id = junctionPlanDetail.get("phase_id");
            int order_no = junctionPlanDetail.get("order_no");
            for (int i = (firstStartDataPosition + 6) + 1; i <= (firstStartDataPosition + 17); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }
//  for (int i = (firstStartDataPosition + 6)+1; i <= (firstStartDataPosition + 17); i++) 
//            {
//                if (extraByte == "") {
//                    extraByte = extraByte + dataToProcess[i];
//                } else {
//                    extraByte = extraByte + " " + dataToProcess[i];
//                }
//            }

            responseVal = responseVal + " " + map_id + " " + phase_id + " " + phase_map_id + " " + order_no + " " + extraByte + " " + firstLastDelimiter + " " + firstLastDelimiter;

        } catch (Exception e) {
            System.out.println("Error - clientResponder - " + e);
        }

        return responseVal;
    }

    public String SideDataFunction(byte[] dataToProcess, int firstStartDataPosition) {

        String responseVal = null;
        String extraByte = "";
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 3];
        int dataSize1 = dataToProcess[firstStartDataPosition - 2];
        int dataSize2 = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        int map_id = 0;
        int phase_no = 0;
        int firstLastDelimiter = 0;
        int secLastDelimiter = 0;
        int crc = 0;
        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        int noofside = dataToProcess[firstStartDataPosition + 4];
        //phase_no = dataToProcess[firstStartDataPosition + 5];
        crc = dataToProcess[firstStartDataPosition + 14] & 0xFF;
        firstLastDelimiter = dataToProcess[firstStartDataPosition + 15];
        //secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
        secLastDelimiter = firstLastDelimiter;
        System.out.println("Junction_id" + junctionID);

        responseVal = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + junctionID + " " + programVersionNoFromDB + " " + fileNo + " " + functionNo;
        int side1poletype = 0, side1primary_h_aspect_no = 0, side1primary_v_aspect_no = 0, side1secondary_h_aspect_no = 0, side1secondary_v_aspect_no = 0, side1sideno = 0;
        int side2poletype = 0, side2primary_h_aspect_no = 0, side2primary_v_aspect_no = 0, side2secondary_h_aspect_no = 0, side2secondary_v_aspect_no = 0, side2sideno = 0;
        int side3poletype = 0, side3primary_h_aspect_no = 0, side3primary_v_aspect_no = 0, side3secondary_h_aspect_no = 0, side3secondary_v_aspect_no = 0, side3sideno = 0;
        int side4poletype = 0, side4primary_h_aspect_no = 0, side4primary_v_aspect_no = 0, side4secondary_h_aspect_no = 0, side4secondary_v_aspect_no = 0, side4sideno = 0;
        int side5poletype = 0, side5primary_h_aspect_no = 0, side5primary_v_aspect_no = 0, side5secondary_h_aspect_no = 0, side5secondary_v_aspect_no = 0, side5sideno = 0;
        try {
            if (noofside == 3) {
                List<SlaveInfo> jp = this.clientResponderModel.getsidedata(junctionID, map_id, phase_no);
                Map<String, Integer> mp = new HashMap<>();
                for (int a = 1; a <= 3; a++) {

                    mp.put("side" + a + "poletypeid", jp.get(a).getPole_type_id());
                    mp.put("side" + a + "primary_h_aspect_no", jp.get(a).getPrimary_h_aspect_no());
                    mp.put("side" + a + "primary_v_aspect_no", jp.get(a).getPrimary_v_aspect_no());
                    mp.put("side" + a + "secondary_h_aspect_no", jp.get(a).getSecondary_h_aspect_no());
                    mp.put("side" + a + "secondary_v_aspect_no", jp.get(a).getSecondary_v_aspect_no());

                }
                side1poletype = mp.get("side1poletypeid");
                side1primary_h_aspect_no = mp.get("side1primary_h_aspect_no");
                side1primary_v_aspect_no = mp.get("side1primary_v_aspect_no");
                side1secondary_h_aspect_no = mp.get("side1secondary_h_aspect_no");
                side1secondary_v_aspect_no = mp.get("side1secondary_v_aspect_no");

                side2poletype = mp.get("side2poletypeid");
                side2primary_h_aspect_no = mp.get("side2primary_h_aspect_no");
                side2primary_v_aspect_no = mp.get("side2primary_v_aspect_no");
                side2secondary_h_aspect_no = mp.get("side2secondary_h_aspect_no");
                side2secondary_v_aspect_no = mp.get("side2secondary_v_aspect_no");

                side3poletype = mp.get("side3poletypeid");
                side3primary_h_aspect_no = mp.get("side3primary_h_aspect_no");
                side3primary_v_aspect_no = mp.get("side3primary_v_aspect_no");
                side3secondary_h_aspect_no = mp.get("side3secondary_h_aspect_no");
                side3secondary_v_aspect_no = mp.get("side3secondary_v_aspect_no");

                side4poletype = 0;
                side4primary_h_aspect_no = 0;
                side4primary_v_aspect_no = 0;
                side4secondary_h_aspect_no = 0;
                side4secondary_v_aspect_no = 0;
                side4sideno = 0;

                side5poletype = 0;
                side5primary_h_aspect_no = 0;
                side5primary_v_aspect_no = 0;
                side5secondary_h_aspect_no = 0;
                side5secondary_v_aspect_no = 0;

            }
            if (noofside == 4) {
                List<SlaveInfo> jp = this.clientResponderModel.getsidedata(junctionID, map_id, phase_no);
                Map<String, Integer> mp = new HashMap<>();
                for (int a = 0; a < 4; a++) {

                    mp.put("side" + a + "poletypeid", jp.get(a).getPole_type_id());
                    mp.put("side" + a + "primary_h_aspect_no", jp.get(a).getPrimary_h_aspect_no());
                    mp.put("side" + a + "primary_v_aspect_no", jp.get(a).getPrimary_v_aspect_no());
                    mp.put("side" + a + "secondary_h_aspect_no", jp.get(a).getSecondary_h_aspect_no());
                    mp.put("side" + a + "secondary_v_aspect_no", jp.get(a).getSecondary_v_aspect_no());
                    mp.put("side" + a + "sideno", jp.get(a).getSide_no());
                    // side1poletype=mp.get("side1poletypeid");
                }
                side1poletype = mp.get("side0poletypeid");
                side1primary_h_aspect_no = mp.get("side0primary_h_aspect_no");
                side1primary_v_aspect_no = mp.get("side0primary_v_aspect_no");
                side1secondary_h_aspect_no = mp.get("side0secondary_h_aspect_no");
                side1secondary_v_aspect_no = mp.get("side0secondary_v_aspect_no");

                side2poletype = mp.get("side1poletypeid");
                side2primary_h_aspect_no = mp.get("side1primary_h_aspect_no");
                side2primary_v_aspect_no = mp.get("side1primary_v_aspect_no");
                side2secondary_h_aspect_no = mp.get("side1secondary_h_aspect_no");
                side2secondary_v_aspect_no = mp.get("side1secondary_v_aspect_no");

                side3poletype = mp.get("side3poletypeid");
                side3primary_h_aspect_no = mp.get("side2primary_h_aspect_no");
                side3primary_v_aspect_no = mp.get("side2primary_v_aspect_no");
                side3secondary_h_aspect_no = mp.get("side2secondary_h_aspect_no");
                side3secondary_v_aspect_no = mp.get("side2secondary_v_aspect_no");

                side4poletype = mp.get("side3poletypeid");
                side4primary_h_aspect_no = mp.get("side3primary_h_aspect_no");
                side4primary_v_aspect_no = mp.get("side3primary_v_aspect_no");
                side4secondary_h_aspect_no = mp.get("side3secondary_h_aspect_no");
                side4secondary_v_aspect_no = mp.get("side3secondary_v_aspect_no");

                side5poletype = 0;
                side5primary_h_aspect_no = 0;
                side5primary_v_aspect_no = 0;
                side5secondary_h_aspect_no = 0;
                side5secondary_v_aspect_no = 0;

            }

            if (noofside == 5) {
                List<SlaveInfo> jp = this.clientResponderModel.getsidedata(junctionID, map_id, phase_no);
                Map<String, Integer> mp = new HashMap<>();
                for (int a = 1; a <= 5; a++) {

                    mp.put("side" + a + "poletypeid", jp.get(a).getPole_type_id());
                    mp.put("side" + a + "primary_h_aspect_no", jp.get(a).getPrimary_h_aspect_no());
                    mp.put("side" + a + "primary_v_aspect_no", jp.get(a).getPrimary_v_aspect_no());
                    mp.put("side" + a + "secondary_h_aspect_no", jp.get(a).getSecondary_h_aspect_no());
                    mp.put("side" + a + "secondary_v_aspect_no", jp.get(a).getSecondary_v_aspect_no());
                    mp.put("side" + a + "sideno", jp.get(a).getSide_no());

                }
                side1poletype = mp.get("side1poletypeid");
                side1primary_h_aspect_no = mp.get("side1primary_h_aspect_no");
                side1primary_v_aspect_no = mp.get("side1primary_v_aspect_no");
                side1secondary_h_aspect_no = mp.get("side1secondary_h_aspect_no");
                side1secondary_v_aspect_no = mp.get("side1secondary_v_aspect_no");

                side2poletype = mp.get("side2poletypeid");
                side2primary_h_aspect_no = mp.get("side2primary_h_aspect_no");
                side2primary_v_aspect_no = mp.get("side2primary_v_aspect_no");
                side2secondary_h_aspect_no = mp.get("side2secondary_h_aspect_no");
                side2secondary_v_aspect_no = mp.get("side2secondary_v_aspect_no");

                side3poletype = mp.get("side3poletypeid");
                side3primary_h_aspect_no = mp.get("side3primary_h_aspect_no");
                side3primary_v_aspect_no = mp.get("side3primary_v_aspect_no");
                side3secondary_h_aspect_no = mp.get("side3secondary_h_aspect_no");
                side3secondary_v_aspect_no = mp.get("side3secondary_v_aspect_no");

                side4poletype = mp.get("side4poletypeid");
                side4primary_h_aspect_no = mp.get("side4primary_h_aspect_no");
                side4primary_v_aspect_no = mp.get("side4primary_v_aspect_no");
                side4secondary_h_aspect_no = mp.get("side4secondary_h_aspect_no");
                side4secondary_v_aspect_no = mp.get("side4secondary_v_aspect_no");

                side5poletype = mp.get("side5poletypeid");
                side5primary_h_aspect_no = mp.get("side5primary_h_aspect_no");
                side5primary_v_aspect_no = mp.get("side5primary_v_aspect_no");
                side5secondary_h_aspect_no = mp.get("side5secondary_h_aspect_no");
                side5secondary_v_aspect_no = mp.get("side5secondary_v_aspect_no");

            }
//            Map<String, Integer> junctionPlanDetail = this.clientResponderModel.getsidedata(junctionID, map_id, phase_no);
//            
//            int phase_map_id = junctionPlanDetail.get("phase_map_id");
//            int phase_id = junctionPlanDetail.get("phase_id");
//            int order_no = junctionPlanDetail.get("order_no");
//            for (int i = (firstStartDataPosition + 6)+1; i <= (firstStartDataPosition + 18); i++) 
//            {
//                if (extraByte == "") {
//                    extraByte = extraByte + dataToProcess[i];
//                } else {
//                    extraByte = extraByte + " " + dataToProcess[i];
//                }
//            }
            for (int i = (firstStartDataPosition + 4) + 1; i <= (firstStartDataPosition + 13); i++) {
                if (extraByte == "") {
                    extraByte = extraByte + dataToProcess[i];
                } else {
                    extraByte = extraByte + " " + dataToProcess[i];
                }
            }

            responseVal = responseVal + " " + side1poletype + " " + side1primary_h_aspect_no + " " + side1primary_v_aspect_no + " " + side1secondary_h_aspect_no + " " + side1secondary_v_aspect_no + " " + side2poletype + " " + side2primary_h_aspect_no + " " + side2primary_v_aspect_no + " " + side2secondary_h_aspect_no + " " + side2secondary_v_aspect_no + " " + side3poletype + " " + side3primary_h_aspect_no + " " + side3primary_v_aspect_no + " " + side3secondary_h_aspect_no + " " + side3secondary_v_aspect_no + " " + side4poletype + " " + side4primary_h_aspect_no + " " + side4primary_v_aspect_no + " " + side4secondary_h_aspect_no + " " + side4secondary_v_aspect_no + " " + side5poletype + " " + side5primary_h_aspect_no + " " + side5primary_v_aspect_no + " " + side5secondary_h_aspect_no + " " + side5secondary_v_aspect_no + " " + extraByte + " " + crc + " " + firstLastDelimiter + " " + firstLastDelimiter;

        } catch (Exception e) {
            System.out.println("Error - clientResponder - " + e);
        }

        return responseVal;
    }

}
