<%-- 
    Document   : menu
    Created on : Oct 31, 2012, 6:15:00 PM
    Author     : Prachi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <title>Traffic Signal</title>
        <link rel="stylesheet" href="style/style.css" media="screen">
        <link rel="stylesheet" href="style/menu.css" media="screen">
    </head>

    <body>
        <ul class="menu">

            <li><a href="#" class="home">Home</a></li>
            <li><a href="loggedInJunctionCont" class="signal">View Signal Status</a></li>
            <li><a href="junctionCont" class="junction">Junction Detail</a></li>
             <li><a href="JunctionDetailsUpdate" class="junction">Junction Detail Update</a></li>
            <li><a href="#" class="log">Log Detail</a>
                <ul>
                    <li><a href="connectedIpCont" class="log">Connected Ip Log</a></li>
                    <li><a href="LoggerHistoryCont" class="log">Junction Log</a></li>
                    <li><a href="SeverityLevelCont" class="log">Severity Level</a></li>
                    <li><a href="SeverityCaseCont" class="log">Severity Case</a></li>
                    <li><a href="LogTableCont" class="log">Log Table</a></li>
                      
                    
                </ul>
            </li>
            <li><a href="#"  class="documents">Data Entry</a>
                <ul>
                    <li><a href="CityCont" class="documents">City Detail</a></li>
                    <li><a href="districtCont" class="documents">District Detail</a></li>
                    <li><a href="stateCont" class="documents">State Detail</a></li>
                    <li><a href="planModeCont" class="documents">Plan Mode</a></li>
                    <li><a href="CameraCont" class="documents">Camera</a></li>
                    <li><a href="CameraTypeCont" class="documents">Camera Type</a></li>
                    <li><a href="CameraMakeCont" class="documents">Camera Make</a></li>
                    <li><a href="PoleTypeCont" class="documents">Pole Type</a></li>
                    <li><a href="positionCont" class="documents">Position</a></li>
                    <li><a href="JunSunriseSunsetCont" class="documents">Sunrise Details</a></li>
                    <li><a href="ts_statusShowerCont" class="documents">View Refresh</a></li>
                    <li><a href="riseSetCont" class="documents">List of riseset time</a></li>
                    <li><a href="PlanDetailsCont" class="documents">Plan Details</a></li>
                    <li><a href="dayDetailsCont" class="documents">Day Details</a></li>
                    <li><a href="DateDetailsCont" class="documents">Date Details</a></li>
                    <li><a href="JunctionPlanMapCont" class="documents">Junction Plan Map</a></li>
                    <li><a href="PhaseDataCont" class="documents">Phase Data</a></li>
                    
                </ul>
            </li>

            <li><a href="beforeHomeCont" class="signout">Signout</a></li>
        </ul> <!-- end .menu -->

    </body>

</html>