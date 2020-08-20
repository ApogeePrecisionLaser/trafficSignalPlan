 <%--
    Document   : junction
    Created on : Aug 10, 2012, 9:33:33 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
         <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" type="text/css" href="style/style.css" />
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
        <script type="text/javascript" src="JS/calendar.js"></script>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/jquery-ui.min.js"></script>

        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#city_name").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function() { return "getCityName"}
                    }
                });
            });

            function makeEditable(id) {
                document.getElementById("city_name").disabled = false;
                document.getElementById("sunrise_hr").disabled = false;
                document.getElementById("sunrise_min").disabled = false;
                document.getElementById("sunset_hr").disabled = false;
                document.getElementById("sunset_min").disabled = false;
                document.getElementById("date").disabled = false;
                if(id === 'NEW') {
                    $("#message").html('');
                    //                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("EDIT").disabled = true;
                    document.getElementById("DELETE").disabled = true;
                    document.getElementById("SAVE").disabled = false;
                    document.getElementById("Save AS New").disabled = true
                    ;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 7);
                    document.getElementById("city_name").focus();
                }
                if(id === 'EDIT') {
                   // document.getElementById("date").readOnly = true;
                    document.getElementById("NEW").disabled = false;
                    document.getElementById("Save AS New").disabled = false;
                    document.getElementById("SAVE").disabled = true;
                    document.getElementById("DELETE").disabled = false;
                    document.getElementById("city_name").focus();
                }
            }
            function setStatus(id) {
                if(id == 'SAVE') {
                    document.getElementById("clickedButton").value = "SAVE";
                }
                else if(id == 'DELETE'){
                    document.getElementById("clickedButton").value = "DELETE";
                }
                else if(id == 'NEW'){
                    document.getElementById("form1").reset();
                    document.getElementById("subTasks").value = "NEW";
                }
                else if(id == 'EDIT'){
                    document.getElementById("subTasks").value = "EDIT";
                }
                else {
                    document.getElementById("clickedButton").value = "Save AS New";;
                }
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                //alert(noOfRowsTraversed);
                var noOfColumns = 7;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNum = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNum++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                //alert(lowerLimit);
                var t1id = "t1c";
                document.getElementById("city_name").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                document.getElementById("sunrise_hr").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("sunrise_min").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                document.getElementById("sunset_hr").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                document.getElementById("sunset_min").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                document.getElementById("date1").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                // Now enable/disable various buttons.

                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "yellowgreen";        // set the background color of clicked row to yellow.
                }

                document.getElementById("EDIT").disabled = false;
                if(!document.getElementById("SAVE").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("DELETE").disabled = false;
                    //            document.getElementById("NEW").disabled = true;
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ') {
                        beginIndex++;
                    } else {
                        break;
                    }
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'SAVE' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var city_name = document.getElementById("city_name").value;
                    if(myLeftTrim(city_name).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>City Name is required...</b></td>";
                        document.getElementById("city_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
            }
            function setYrs(sy, ey)
            {
                // current Date
                var curDate = new Date();
                var curYear = getRealYear(curDate);
                if (sy)
                    startYear = curYear;
                if (ey)
                    endYear = curYear;
                document.getElementById('year').options.length = 0;
                var j = 0;
                for (y=ey; y>=sy; y--)
                {
                    document.getElementById('year')[j++] = new Option(y, y);
                }
            }

            function setVisibility(id){
                if(document.getElementById("city_name_cb").checked) {
                    document.getElementById("city_name_filter").disabled=false;
                    document.getElementById("city_name_filter").focus();
                }else{
                    document.getElementById("city_name_filter").disabled=true;}
                if(document.getElementById("year_cb").checked){
                    document.getElementById("year").disabled=false;
                }else{
                    document.getElementById("year").disabled=true;}
                if(document.getElementById("month_cb").checked){
                    document.getElementById("month").disabled=false;
                }else{
                    document.getElementById("month").disabled=true;}
                if(document.getElementById("date_cb").checked){
                    document.getElementById("date_filter").disabled=false;
                }else{
                    document.getElementById("date_filter").disabled=true;}
            }

            function setDIVVisibility(){
                if(document.getElementById("city_name_cb").checked) {
                    document.getElementById("city_name_filter").disabled=false;
                    document.getElementById("city_name_filter").focus();
                }else{
                    document.getElementById("city_name_filter").disabled=true;}
                if(document.getElementById("year_cb").checked){
                    document.getElementById("year").disabled=false;
                }else{
                    document.getElementById("year").disabled=true;}
                if(document.getElementById("month_cb").checked){
                    document.getElementById("month").disabled=false;
                }else{
                    
                    document.getElementById("month").disabled=true;}
                if(document.getElementById("date_cb").checked){
                    document.getElementById("date_filter").disabled=false;
                }else{
                    document.getElementById("date_filter").disabled=true;}
            }

     jQuery(function () {
                
                $("#searchCity").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchCity";
                        }
                    }
                });
                $("#searchdate").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchdate";
                        }
                        
                    }
                });
                $("#searchsunrisehr").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchsunrisehr";
                        } 
                        
                    }
                });
                 $("#searchsunrisemin").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchsunrisemin";
                        }
                    }
                });
                
                 $("#searchsunsethr").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchsunsethr";
                        }
                    }
                });
                 $("#searchsunsetmin").autocomplete("JunSunriseSunsetCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchsunsetmin";
                        }
                    }
                });
                
                 
             

            });
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body onload="setDIVVisibility();">
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table width="100%">
                            <tr>
                                <td align="center">
                                    <table class="header_table" border="4" align="center" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Junction SunRise SunSet Details
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                 <form action="JunSunriseSunsetCont" method="post">
                                <td align="center">
                                    <table id="table0"  align="center" width="500" >

                                       
                                                        <tr>
                                                             <td>
                                              City<input type="text" name="searchCity" id="searchCity" value="${searchCity}">
                                            </td>
                                            <td>
                                              Date<input type="text"  name="searchdate" id="searchdate" value="${searchdate}">
                                            </td>
                                            
                                            <td>
                                                SunriseHr
                                                 <input type="text" name="searchsunrisehr" id="searchsunrisehr" value="${searchsunrisehr}">
                                            </td>
                                             <td>
                                                SunriseMin
                                                 <input type="text" name="searchsunrisemin" id="searchsunrisemin" value="${searchsunrisemin}">
                                            </td>
                                             <td>
                                                SunsetHr
                                                 <input type="text" name="searchsunsethr" id="searchsunsethr" value="${searchsunsethr}">
                                            </td>
                                             <td>
                                              SunsetMin<input type="text" name="searchsunsetmin" id="searchsunsetmin" value="${searchJun}">
                                            </td>
                                              </tr>
                                              <tr>
                                                  <td></td>
                                                  <td></td>
                                            <td>
                                                <input type="submit" name="search" id="search" value="Search"/></td>  
                                            <td>  <input type="submit" name="task" value="SearchAllRecords"/></td>
                                                          
                                                            
                                                        </tr>
                                                    </table>
                                               
                                            </td>
                                             </form>
                                        </tr>
                                        <tr>
                                            <td>
                                                <form name="form1" action="JunSunriseSunsetCont" method="post">
                                                    <table name="table1" border="1" width="100%" align="center" class="reference">
                                                        <tr>
                                                            <th class="heading">S.No.</th>
                                                            <th class="heading">City Name</th>
                                                            <th class="heading">Sunrise Hrs</th>
                                                            <th class="heading">Sunrise Min</th>
                                                            <th class="heading">Sunset Hrs</th>
                                                            <th class="heading">Sunset Min</th>
                                                            <th class="heading">Date</th>
                                                        </tr>
                                                        <c:forEach var="list" items="${requestScope['RiseSet']}" varStatus="loopCounter">
                                                            <tr>
                                                                <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                                    ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                                </td>
                                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.city_name}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sunrise_time_hrs}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sunrise_time_min}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sunset_time_hrs}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sunset_time_min}</td>
                                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.date}</td>
                                                            </tr>
                                                        </c:forEach>
                                                        <tr>
                                                            <td align='center' colspan="7">
                                                                <c:choose>
                                                                    <c:when test="${showFirst eq 'false'}">
                                                                        <input type='submit' class="button" name='buttonAction' value='First' disabled>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type='submit' class="button" name='buttonAction' value='First'>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <c:choose>
                                                                    <c:when test="${showPrevious == 'false'}">
                                                                        <input type='submit' class="button" name='buttonAction' value='Previous' disabled>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type='submit' class="button" name='buttonAction' value='Previous'>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <c:choose>
                                                                    <c:when test="${showNext eq 'false'}">
                                                                        <input type='submit' class="button" name='buttonAction' value='Next' disabled>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type='submit' class="button" name='buttonAction' value='Next'>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <c:choose>
                                                                    <c:when test="${showLast == 'false'}">
                                                                        <input type='submit' class="button" name='buttonAction' value='Last' disabled>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type='submit' class="button" name='buttonAction' value='Last'>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td> </tr>
                                                    </table>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                    <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                    <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                    <input type="hidden" name="city_name_filter" id="city_name_filter" value="${city_name_filter}">
                                                    <input type="hidden" name="year" id="year" value="${year}">
                                                    <input type="hidden" name="month" id="month" value="${month}">
                                                    <input type="hidden" name="date_filter" id="date_filter" value="${date_filter}">
                                               <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                 <input type="hidden" name="a" value="${a}">
                                             <input type="hidden" name="b" value="${b}">
                                             <input type="hidden" name="c" value="${c}">
                         
                                             <input type="hidden" name="d" value="${d}">
                                             <input type="hidden" name="e" value="${e}">
                                             <input type="hidden" name="f" value="${f}">
                                                
                                                
                                                </form>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <form name="form" id="form1" action="JunSunriseSunsetCont" method="get" onsubmit="return verify()">
                                                    <table name="table" class="reference" width="100%" border="1" align="center">
                                                        <tr align="center">
                                                            <th class="heading">City Name</th>
                                                            <td>
                                                                <input type="text" size="35" id="city_name" name="city_name" value="" disabled><br>
                                                            </td> </tr>
                                                         <tr align="center">
                                                              <th class="heading">SunriseHr</th>
                                                            <td>
                                                                <input type="number" size="35" id="sunrise_hr" name="sunrise_hr" value="" min="0" max="23" disabled><br>
                                                            </td></tr>
                                                          <tr align="center">
                                                              <th class="heading">SunriseMin</th>
                                                            <td>
                                                                <input type="number"  size="35" id="sunrise_min" name="sunrise_min" value="" min="0" max="59" disabled><br>
                                                            </td></tr>
                                                           <tr align="center">
                                                              <th class="heading">SunsetHr</th>
                                                            <td>
                                                                <input type="number" size="35" id="sunset_hr" name="sunset_hr" value="" min="0" max="23" disabled><br>
                                                            </td></tr>
                                                            <tr align="center">
                                                              <th class="heading">SunsetMin</th>
                                                            <td>
                                                                <input type="number" size="35" id="sunset_min" name="sunset_min" value="" min="0" max="59" disabled><br>
                                                            </td>
                                                            
                                                        </tr>
                                                          <tr align="center">
                                                              <th class="heading">Date</th>
                                                            <td>
                                                                <input type="date" size="35" id="date" name="date" value="" disabled><br>
                                                                <input type="hidden" size="35" id="date1" name="date1" value="" ><br>
                                                            </td>
                                                            
                                                        </tr>
                                                        <tr>
                                                            <td align='center' colspan="2">
                                                                <input type="submit" id="SAVE" name="task" value="SAVE" onclick="setStatus(id)" disabled/>
                                                                <input type="button" id="NEW" name="task" value="NEW" onclick="makeEditable(id);setStatus(id);"/>
                                                                <input type="button" id="EDIT" name="task" value="EDIT" onclick="makeEditable(id);setStatus(id);" disabled/>
                                                                <input type="submit" id="DELETE" name="task" value="DELETE" onclick="setStatus(id)" disabled>
                                                                <input type="submit" name="task" id="Save AS New" value="Save AS New" onclick="setStatus(id)" disabled>
                                                        </tr>
                                                        <tr id="message">
                                                            <c:if test="${not empty message}">
                                                                <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                            </c:if>
                                                        </tr>
                                                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form of table. --%>
                                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                        <input type="hidden" id="clickedButton" value="">
                                                        <input type="hidden" id="subTasks" name="subTasks" value="">
                                                    </table>
                                                </form>
                                            </td>
                                        </tr>
                                    </table>
                                    <jsp:include page="calendarView"/>
                                </td>
                            </tr>
                        </table>

                    </DIV>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

    </body>
</html>
