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
        $("#state_name").autocomplete("junctionCont", {
            extraParams: {
                action1: function () {
                    return "getStateName"
                }
            }
        });
        $("#city_name").autocomplete("junctionCont", {
            extraParams: {
                action1: function () {
                    return "getCityName"
                },
                action2: function () {
                    return document.getElementById("state_name").value;
                }
            }
        });
    });

    

    

   
    

    

    
  

    
    

   
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
