 <%-- 
    Document   : log_table
    Created on : 21 Oct, 2019, 12:24:28 PM
    Author     : DELL
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<!--<script type="text/javascript" src="JS/jquery-ui.min.js"></script>-->
<script type="text/javascript" language="javascript">
    
    jQuery(function () {
       
            $("#searchjunction").autocomplete("LogTableCont", {
                    extraParams: {
                        action1: function () {
                            return "getJunctionName";
                        }
                    }
                });
                });
                
                  jQuery(function () {
            $("#searchside").autocomplete("LogTableCont", {
                    extraParams: {
                        action1: function () {
                            return "getSideName";
                          },
                action2: function () {
                    return document.getElementById("searchjunction").value;
                }
                    }
                });
                });
                  jQuery(function () {
            $("#searchdate").autocomplete("LogTableCont", {
                    extraParams: {
                        action1: function () {
                            return "getdate";
                        }},
                action2: function () {
                    return document.getElementById("searchjunction").value;
                    }
                });
                });
    
        
    function displayMapList(id) {
                var queryString;
            var searchjunction=document.getElementById("searchjunction").value;
            var searchside=document.getElementById("searchside").value;
            var searchdate=document.getElementById("searchdate").value;
           
                if (id === 'viewPdf')
                    queryString = "requester=PRINT"+"&searchjunction=" + searchjunction +"&searchside="+searchside + "&searchdate="+searchdate;
                else
                    queryString = "requester=PRINTXls"+"&searchjunction=" + searchjunction +"&searchside="+searchside + "&searchdate="+searchdate;
                var url = "LogTableCont?" + queryString;
                popupwin = openPopUp(url, "log", 600, 900);
            }  
            
             function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
    

   
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/style.css" media="screen">
        <link rel="stylesheet" href="style/Table_content.css" media="screen">
        <title>Log Table</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table cellspacing="0" id="table0"  align="center" width="100%">
                            <tr><td><table class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Log Table
                                            </td>
                                        </tr>
                                    </table> </td> </tr>
<tr><td>
                                    <form action="LogTableCont" method="post" class="form-group container-fluid">
                   
                                    <table align="center">
                                        <tr >
                                             <td>
                                              Junction<input type="text" name="searchjunction" id="searchjunction" value="${searchjunction}">
                                            </td>
                                            <td>
                                              SideName<input type="text" name="searchside" id="searchside" value="${searchside}">
                                            </td>
                                            <td>
                                              Date&Time<input type="text" name="searchdate" id="searchdate" value="${searchdate}">
                                            </td>
                                             
                                       
                                         <td>
                                              <input type="submit" name="search" id="search" value="Search"/>  
                                             <input type="submit" name="task" value="SearchAllRecords"/>
                                          <input type="button" name="viewPdf" id="viewPdf" value="pdf" onclick="displayMapList(id)">
                                          
                                              <input type="button" name="viewXls" id="viewXls" value="excel"  onclick="displayMapList(id)">
                                             </tr>
                                    </table></form> </td></tr>
                            <tr>
                                <td>
                                    <div style="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px">
                                        <form name="form1" action="LogTableCont" method="post">
                                            <table name="table1" border="1" width="100%" align="center" class="reference70">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Sent Data</th>
                                                    <th class="heading">Recieved Data</th>
                                                    <th class="heading">Severity Case</th>
                                                    <th class="heading">SMS sent Status</th>
                                                    <th class="heading">Key Person</th>
                                                    <th class="heading">Junction Name</th>
                                                    <th class="heading">Side Name</th>
                                                    <th class="heading">Date Time</th>
                                                    <th class="heading">Remark</th> 
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['log_table']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="log_table_id${loopCounter.count}" value="${list.log_table_id}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.send_data}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.r_data}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.severity_case}
                                                            <input type="hidden" id="severity_case_id${loopCounter.count}" value="${list.severity_case_id}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sms_sent_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.key_person_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.date_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.remark}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="22">
                                                        <c:choose>
                                                            <c:when test="${showFirst eq 'false'}">
                                                                <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input  class="button" type='submit' name='buttonAction' value='First'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showPrevious == 'false'}">
                                                                <input  class="button" type='submit' name='buttonAction' value='Previous' disabled>
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
                                            </table>
                                            <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                           <input type="hidden" name="manname" value="${manname}">
                           <input type="hidden" name="pname" value="${pname}">
                           <input type="hidden" name="dname" value="${dname}">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form></div>
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
