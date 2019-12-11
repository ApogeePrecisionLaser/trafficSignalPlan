<%--
    Document   : junction plan map
    Created on : Nov 19, 2019, 9:48:05 AM
    Author     : prachi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: junction Map Table</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
         <link rel="stylesheet" type="text/css" href="style/style.css" />
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />

        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            jQuery(function () {
                $("#date").autocomplete("JunctionPlanMapCont", {
                    
                    extraParams: {
                        action1: function () {
                            return "getDate";
                        }
                    }
                });
                
                $("#start_time").autocomplete("JunctionPlanMapCont", {
                    
                    extraParams: {
                        action1: function () {
                            return "getOnOffTime";
                        }
                    }
                });

            });

            function makeEditable(id) {
                document.getElementById("junction_plan_map_id").disabled = false;
                document.getElementById("junction_name").disabled = false;
                document.getElementById("date").disabled = false;
                document.getElementById("day").disabled = false;
                document.getElementById("start_time").disabled = false;
                document.getElementById("order_no").disabled = false;
                //document.getElementById("plan_no").disabled = false;
                if (id == 'new') {
                    $("#message").html('');
                    //                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 8);
                    document.getElementById("junction_name").focus();
                }
                if (id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("junction_name").focus();
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
                var noOfColumns = 8;
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
                document.getElementById("junction_plan_map_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("junction_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("date").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                document.getElementById("day").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                document.getElementById("start_time").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                document.getElementById("order_no").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
//                document.getElementById("plan_no").value = document.getElementById(tlid + (lowerLimit + 8)).innerHTML;
                // Now enable/disable various buttons.
                document.getElementById("edit").disabled = false;
                if (!document.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                $("#message").html('');      // Remove message
            }
            function setStatus(id) {
                if (id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else if (id == 'save_As') {
                    document.getElementById("clickedButton").value = "Save AS New";
                } else {
                    document.getElementById("clickedButton").value = "Delete";
                    ;
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for (var i = 0; i < str.length; i++) {
                    if (str.charAt(i) == ' ') {
                        beginIndex++;
                    } else {
                        break;
                    }
                }
                return str.substring(beginIndex, str.length);
            }
            
            function getPlanNo() {
                debugger;
            }
            
            function verify() {
                var result;
                debugger;
                if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var junction_name = document.getElementById("junction_name").value;
                    if (myLeftTrim(junction_name).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Junction Name is required...</b></td>";
                        document.getElementById("junction_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    var junction_id = document.getElementById("junction_id").value;
//                    var date = document.getElementById("date").value;
//                    var day = document.getElementById("day").value;
//                    if (myLeftTrim(date).length == 0 && myLeftTrim(day).length == 0) {
//                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Date or  is required...</b></td>";
//                        document.getElementById("date").focus();
//                        return false; // code to stop from submitting the form2.
//                        
//                    }
//                    
//                    if (myLeftTrim(day).length == 0) {
//                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Day  is required...</b></td>";
//                        document.getElementById("day").focus();
//                        return false; // code to stop from submitting the form2.
//                    }
                    
                    var start_time = document.getElementById("start_time").value;
                    if (myLeftTrim(day).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Start Time is required...</b></td>";
                        document.getElementById("start_time").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    
                    var order_no = document.getElementById("order_no").value;
                    if (myLeftTrim(day).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Order No is required...</b></td>";
                        document.getElementById("order_no").focus();
                        return false; // code to stop from submitting the form2.
                    }
//                    var plan_no = document.getElementById("plan_no").value;
//                    if (myLeftTrim(day).length == 0) {
//                        document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>End Time is required...</b></td>";
//                        document.getElementById("plan_no").focus();
//                        return false; // code to stop from submitting the form2.
//                    }
                    if (result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }
                    if (document.getElementById("clickedButton").value == 'Save AS New') {
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
            }
            
            function disableDate(val) {
                if(val != 0) {
                    document.getElementById("date").disabled = true;
                } else if(val==0) {
                    document.getElementById("date").disabled = false;
                }
                
            }
        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table align="center" width="1000" border="0" cellpadding="0" cellspacing="0" >
                            <tr><td><table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Junction Plan Map Table
                                            </td>
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td>
                                    <table id="table0"  align="center" width="500">
                                        <tr>
                                            <td>
                                                <form name="form1" method="POST" action="JunctionPlanMapCont">
                                                    <DIV STYLE="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px">
                                                        <table border="1" id="table1" align="center" class="reference">
                                                            <tr>
                                                                <th class="heading" style="visibility: hidden"><!-- City ID --></th>
                                                                <th class="heading" >S.No.</th>
                                                                <th class="heading" >Junction Name</th>
                                                                <th class="heading" >To&From Date</th>
                                                                <th class="heading" >Day</th>
                                                                <th class="heading" >Start Time</th>
                                                                <th class="heading" >Order No</th>
                                                                <th class="heading" >Plan No</th>
                                                            </tr>
                                                            <c:forEach var="planMap" items="${requestScope['junctionPlanMapList']}" varStatus="loopCounter">
                                                                <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                                    <td id="t1c${IDGenerator.uniqueID}" style="visibility: hidden" onclick="fillColumns(id)">${planMap.junction_plan_map_id}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.junction_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.from_date}//${planMap.to_date}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.day}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.on_time_hr}:${planMap.on_time_min}-${planMap.off_time_hr}:${planMap.off_time_min}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.order_no}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.plan_no}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr>
                                                                <td align='center' colspan="8">
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
                                                                </td>
                                                            </tr>
                                                            <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                            
                                                        </table>
                                                    </DIV>
                                                </form>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <form name="form2" method="POST" action="JunctionPlanMapCont" onsubmit="return verify()">

                                                    <table border="1"  id="table2" align="center"  class="reference">
                                                        <tr id="message">
                                                            <c:if test="${not empty message}">
                                                                <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                            </c:if>
                                                        </tr>

                                                        <tr>
                                                            <th class="heading" >Junction Name</th>
                                                            <td><input class="input" type="text" id="junction_name" name="junction_name" value="${junction_name}" size="50" disabled>
                                                                <input class="input" type="hidden" id="junction_id" name="junction_id" value="${junction_id}" size="50" disabled>
                                                            <input class="input" type="hidden" id="junction_plan_map_id" name="junction_plan_map_id" value="" size="50" disabled></td>
                                                        </tr>
                                                        <tr>
                                                            <th class="heading" >On Time -  Off Time</th>
                                                            <td><input class="input" type="text" id="start_time" name="start_time" value="" maxlength="6" size="50" disabled ></td>  
                                                        </tr>
                                    
                                                        

                                                        <tr><th class="heading" >Order No</th>
                                                            <td><input class="input" type="text" id="order_no" name="order_no" value="" maxlength="6" size="50" disabled></td></tr>


                                                        <tr> <th class="heading" >Day</th>
                                                            <td><select name="day" id="day" style="width: 88%; align-content: center;" onchange="disableDate(this.value)">
                                                                    <option value="0">--select--</option>
                                                                    <option value="Monday">Monday</option>
                                                                    <option value="Tuesday">Tuesday</option>
                                                                    <option value="Wednesday">Wednesday</option>
                                                                    <option value="Thursday">Thursday</option>
                                                                    <option value="Friday">Friday</option>
                                                                    <option value="Saturday">Saturday</option>
                                                                    <option value="Sunday">Sunday</option>

                                                                </select></td>

                                                        </tr> 
                                                        <tr> <tr> <th class="heading" >To&From Date</th>
                                                            <td colspan="3"><input class="input" type="text" id="date" name="date" value="" maxlength="5" size="50" disabled></td>
                                                        </tr>
                                                        <tr>
                                                            <td align='center' colspan="6">
                                                                <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled> &nbsp;&nbsp;&nbsp;&nbsp;
                                                                <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>&nbsp;&nbsp;&nbsp;&nbsp;
                                                                <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>  &nbsp;&nbsp;&nbsp;&nbsp;
                                                                <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">&nbsp;&nbsp;&nbsp;&nbsp;
                                                                <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                            </td>
                                                        </tr>
                                                        <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                        <input type="hidden" id="junction_plan_map_id" name="junction_plan_map_id" value="" disabled>
                                                        <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                        <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                        <input type="hidden" id="clickedButton" value="">
                                                    </table>

                                                </form>
                                            </td>
                                        </tr>
                                    </table>
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

