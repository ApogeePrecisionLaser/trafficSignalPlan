<%-- 
    Document   : plan_detail_id
    Created on : 30 Jan, 2020, 12:30:26 PM
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
<script>
    function myFunction(t1,t2,t3,t4){
        var time =t1+":"+t2+"-"+t3+":"+t4;    
        document.getElementById("test1").value = time;
          if (window.opener != null && !window.opener.closed) {
            var txtName = window.opener.document.getElementById("start_time");
            txtName.value = document.getElementById("test1").value;
        }
        window.close();      
    }
    
    function SetName() {
       
    }
    
    function setStatus(id) {
                if (id == 'test') {
                    document.getElementById("clickedButton").value = "test";
                } else if (id == 'save_As') {
                    document.getElementById("clickedButton").value = "Save AS New";
                } else {
                    document.getElementById("clickedButton").value = "Delete";
                    ;
                }
                
                
            }
    
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <div id="body" class="maindiv">
                        <table cellspacing="0" id="table0"  align="center" width="100%">
                            <tr><td><table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Plan Details
                                            </td>
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td>
                                    <div class="table-responsive" style="width: 990px;max-height: 340px;overflow: auto;margin-bottom: 20px">
                                        <form name="form1" action="PlanDetailsCont" method="post">
                                            <table class="reference" border="1" align="center">
                                                <tr>
                                                    <th class="heading"></th>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Plan No</th>
                                                    <th class="heading">On Time Hour</th>
                                                    <th class="heading">On Time Min</th>
                                                    <th class="heading">Off Time Hour</th>
                                                    <th class="heading">Off Time Min</th>
                                                    <th class="heading">Mode</th>

                                                    <th class="heading">Green Time Side 1</th>
                                                    <th class="heading">Green Time Side 2</th>
                                                    <th class="heading">Green Time Side 3</th>
                                                    <th class="heading">Green Time Side 4</th>
                                                    <th class="heading">Green Time Side 5</th>
                                                    <th class="heading">Amber Time Side 1</th>
                                                    <th class="heading">Amber Time Side 2</th>
                                                    <th class="heading">Amber Time Side 3</th>
                                                    <th class="heading">Amber Time Side 4</th>
                                                    <th class="heading">Amber Time Side 5</th>
                                                    <th class="heading">Transferred Status</th>

                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['plandetails']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                        <td><input type="radio" name="gender" value="${list.plan_no}" onchange="myFunction('${list.on_time_hour}','${list.on_time_min}','${list.off_time_hour}','${list.off_time_min}')"><br></td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}

                                                            <input type="hidden" id="plan_id${loopCounter.count}" name="plan_id${loopCounter.count}" value="${list.plan_id}">

                                                            <input type="hidden" name="loopCounter" value="${loopCounter.count}">
                                                        </td>


                                                        <td id="t1c${IDGenerator.uniqueID}">${list.plan_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.on_time_hour}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.on_time_min}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.off_time_hour}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.off_time_min}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.mode}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side1_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side2_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side3_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side4_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side5_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side1_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side2_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side3_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side4_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.side5_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}">${list.transferred_status}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}">${list.remark}</td>
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
                                            <input type="hidden" id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form></div>
                                </td>
                            </tr> 
                            
                            <tr>
                                            <td>
<!--                                                <form name="form2" method="POST" action="JunctionPlanMapCont" onsubmit="return verify()">-->

<!--                                                    <table border="1"  id="table2" align="center"  class="reference">                                                                                                                                                                                                                                                                                                                                                                                                -->
                                                       <tr>
                                                            <td align='center' colspan="6">                                                            
<!--                                                                <input class="button" type="submit" name="task" id="test" value="test" onclick="setStatus(id)">&nbsp;&nbsp;&nbsp;&nbsp;-->
                                                                <input type="hidden" name="test1" id="test1">                                                                
                                                            </td>
                                                        </tr>
                                                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
<!--                                                        <input type="hidden" id="junction_plan_map_id" name="junction_plan_map_id" value="" disabled>
                                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                        <input type="hidden" id="clickedButton" value="">
                                                    </table>

                                                </form>-->
                                            </td>
                                        </tr>                                                                                                                                                                                                                                                          
                        </table>
                    </div>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

        
        
    </body>
</html>
