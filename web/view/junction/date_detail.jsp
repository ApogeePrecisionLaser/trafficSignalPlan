<%-- 
    Document   : state
    Created on : Aug 10, 2012, 12:20:21 PM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: Date Table</title>
            <link rel="stylesheet" type="text/css" href="style/style.css" />
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
         <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
           <script type="text/javascript" src="JS/jquery-ui.min.js"></script>        
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/calendar.js"></script>
        <script type="text/javascript" language="javascript">
            


            function makeEditable(id) {
          
                document.getElementById("date_detail_id").disabled = false;
                document.getElementById("name").disabled = false;
                 document.getElementById("from_date").disabled = false;
                  document.getElementById("to_date").disabled = false;
                   document.getElementById("remark").disabled = false;
                if (id == 'new') {
                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 6);
                    document.getElementById("name").focus();
                }
                if (id == 'edit') {
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for (var i = 0; i < noOfRowsTraversed; i++) {
                    for (var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 6;
                var columnId = id;
            <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);
            <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit;
                for (var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for (var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "yellowgreen";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("date_detail_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                   document.getElementById("from_date").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                      document.getElementById("to_date").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                         document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                // Now enable/disable various buttons.
                document.getElementById("edit").disabled = false;
                if (!document.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("message").innerHTML = "";      // Remove message
            }
            function setStatus(id) {
                if (id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else {
                    document.getElementById("clickedButton").value = "Delete";
                    
                }
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
            function myLeftTrim(str) {
                var beginIndex = 0;
                for (var i = 0; i < str.length; i++) {
                    if (str.charAt(i) == ' ')
                        beginIndex++;
                    else
                        break;
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if (document.getElementById("clickedButton").value == 'Save') {
                    var name = document.getElementById("name").value;
                    if (myLeftTrim(state_name).length == 0) {
                        alert("Name is required");
                        document.getElementById("name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if (result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
            }
            function setDIVVisibility(){
             debugger;
                    document.getElementById("date_filter").disabled=false;
                
            }
            
                function displayMapList(id) {
                var queryString;
            var searchstate=document.getElementById("searchstate").value;
          
                if (id === 'viewPdf')
                    queryString = "requester=PRINT"+"&searchstate=" + searchstate;
                else
                    queryString = "requester=PRINTXls"+"&searchstate=" + searchstate;
                var url = "DateDetailsCont?" + queryString;
                popupwin = openPopUp(url, "DateDetailsCont", 600, 900);
            }  
            
             function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
               jQuery(function () {
            $("#searchstate").autocomplete("DateDetailsCont", {
                    extraParams: {
                        action1: function () {
                            return "getState";
                        }
                    }
                });
                });
        </script>
    </head>
    <body >
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
          
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table align="center" width="1000" border="0" cellpadding="0" cellspacing="0" >
                            <tr><td>
                                    <table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Date Table
                                            </td>
                                        </tr>
                                    </table> </td> </tr>
<tr><td>
                                    <form action="DateDetailsCont" method="post" class="form-group container-fluid">
                   
                                        <table align="center" border="1px">
                                        <tr >
                                             <td>
                                              Name<input type="text" name="searchstate" id="searchstate" value="${searchstate}">
                                            </td>
                                            
                                       
                                         <td>
                                              <input type="submit" name="search" id="search" value="Search"/>  
                                             <input type="submit" name="task" value="SearchAllRecords"/>
                                          <input type="button" name="viewPdf" id="viewPdf" value="pdf" onclick="displayMapList(id)">
                                          
                                              <input type="button" name="viewXls" id="viewXls" value="excel"  onclick="displayMapList(id)">
                                             </tr>
                                    </table></form> </td></tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="DateDetailsCont">
                                        <DIV STYLE="overflow: auto; width: 400px; max-height: 410px; padding:0px; margin-bottom: 20px" >
                                            <table border="1" id="table1" align="center" class="reference">
                                                <tr>
                                                    <th class="heading" style="display: none"><!-- State ID--></th>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Name</th>
                                                     <th class="heading">From Date</th>
                                                    <th class="heading">To Date</th>
                                                   
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="datedetail" items="${requestScope['dateDetailList']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor ="#E3ECF3" onmouseout=this.style.backgroundColor ="white">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${datedetail.date_detail_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${datedetail.name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${datedetail.from_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${datedetail.to_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${datedetail.remark}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="6">
                                                        <c:choose>
                                                            <c:when test="${showFirst eq 'false'}">
                                                                <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="button" type='submit' name='buttonAction' value='First'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showPrevious == 'false'}">
                                                                <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showNext eq 'false'}">
                                                                <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="button" type='submit' name='buttonAction' value='Next'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showLast == 'false'}">
                                                                <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input class="button" type='submit' name='buttonAction' value='Last'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>  </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                            <input type="hidden" name="manname" value="${manname}">

                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="DateDetailsCont" onsubmit="return verify()">
                                        <DIV STYLE="overflow: auto; width: 340px;padding:0px; margin: 0px">
                                            <table border="1" id="table2" align="center" class="reference">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>

                                                <tr>

                                                    <th class="heading"> Name</th><td><input class="input" type="text" id="name" name="name" size="30" value="" disabled></td>
                                                </tr>
                                              
                                                <tr>
                                                    <th class="heading">From Date</th>
                                                    <td nowrap>
                                                        <input class="input" type="text" id="from_date" name="from_date" value="${from_date eq '' ? 'dd-mm-yyyy' : from_date}" style="text-align: center" size="30" readonly disabled>
                                                                <a href="#" onclick="setYears(1947,2022);showCalender(this, 'from_date', true);">
                                                                    <img src="images/calender.png" alt="calender.png">
                                                                </a>
                                                                </td>
                                                            </tr>
                                                              <tr>
                                                    <th class="heading">To Date</th>
                                                    <td nowrap>
                                                        <input class="input" type="text" id="to_date" name="to_date" value="${to_date eq '' ? 'dd-mm-yyyy' : to_date}" style="text-align: center" size="30" readonly disabled>
                                                                <a href="#" onclick="setYears(1947,2022);showCalender(this, 'to_date', true);">
                                                                    <img src="images/calender.png" alt="calender.png">
                                                                </a>
                                                                </td>
                                                            </tr>
                                                <tr>
                                                    <th class="heading">Remark</th>
                                                    <td> <input class="input" type="text" id="remark" name="remark"  size="30" ><td>
                                                </tr> 
                                                <tr>
                                                    <td align='center' colspan="3">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" id="date_detail_id" name="date_detail_id" value="" disabled>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>
                        </table>
                                                <jsp:include page="calendarView"/>
                    </DIV>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

    </body>
</html>
