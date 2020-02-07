<%--
    Document   : junction
    Created on : Feb 11, 2019, 9:33:33 AM
    Author     : Jaya Kumari
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">
   
function makeEditable1()

{
    debugger;
    alert("Hi");
}

    function makeEditable(id) {
        debugger;
             
        document.getElementById("plan_id").disabled = false;
        document.getElementById("plan_no").disabled = false;
        document.getElementById("on_time_hour").disabled = false;
        document.getElementById("on_time_min").disabled = false;
        document.getElementById("off_time_hour").disabled = false;
        document.getElementById("off_time_min").disabled = false;
        document.getElementById("mode").disabled = false;
        document.getElementById("side1_green_time").disabled = false;
        document.getElementById("side2_green_time").disabled = false;
        document.getElementById("side3_green_time").disabled = false;
        document.getElementById("side4_green_time").disabled = false;
        document.getElementById("side5_green_time").disabled = false;
        document.getElementById("side1_amber_time").disabled = false;
        document.getElementById("side2_amber_time").disabled = false;
        document.getElementById("side3_amber_time").disabled = false;
        document.getElementById("side4_amber_time").disabled = false;
        document.getElementById("side5_amber_time").disabled = false;

        document.getElementById("transferred_status").disabled = false;
       
        document.getElementById("remark").disabled = false;
        //        document.getElementById("side_5_name").disabled = false;
        if (id === 'NEW') {
            $("#message").html('');           
            document.getElementById("SAVE").disabled = true;
            //document.getElementById("submit_coloumn").style.display = "block";
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 18);
            ajaxCall();
            //document.getElementById("plan_no").focus();
        }
        if (id === 'EDIT') {
            document.getElementById("SAVE").disabled = false;
            document.getElementById("DELETE").disabled = false;
            document.getElementById("Save AS New").disabled = false;
            document.getElementById("plan_no").focus();
        }
        document.getElementById("SAVE").disabled = false;
    }

   
    function setStatus(id) {
        if (id == 'SAVE') {
            document.getElementById("clickedButton").value = "SAVE";
        } else if (id == 'DELETE') {
            document.getElementById("clickedButton").value = "DELETE";
        } else {
            document.getElementById("clickedButton").value = "Save AS New";
            ;
        }
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        debugger;
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function fillColumns(id) {
        debugger;
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 19;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;
        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            rowNo++;
            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }
        var lower = lowerLimit;
        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";
        //        alert(rowNo);
//        document.getElementById("plan_id").value = document.getElementById("phase_info_id" + rowNo).value;
        document.getElementById("plan_id").value = document.getElementById("plan_id" + rowNo).value;
        document.getElementById("plan_no").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("on_time_hour").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("on_time_min").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("off_time_hour").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("off_time_min").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        document.getElementById("mode").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
        document.getElementById("side1_green_time").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("side2_green_time").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
        document.getElementById("side3_green_time").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
        document.getElementById("side4_green_time").value = document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
        document.getElementById("side5_green_time").value = document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
        document.getElementById("side1_amber_time").value = document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
        document.getElementById("side2_amber_time").value = document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
        document.getElementById("side3_amber_time").value = document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
        document.getElementById("side4_amber_time").value = document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
        document.getElementById("side5_amber_time").value = document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
        document.getElementById("transferred_status").value = document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 18)).innerHTML;



        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lower + i)).bgColor = "yellowgreen";        // set the background color of clicked row to yellow.
        }

        document.getElementById("DELETE").disabled = false;
        if (!document.getElementById("SAVE").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("DELETE").disabled = false;
            document.getElementById("NEW").disabled = false;
        }
        $("#message").html('');
    }

    function splitValue(value, index) {
        return value.substring(0, index) + " " + value.substring(index);
    }

    function hex2bin(hex) {
        return (parseInt(hex, 16).toString(2)).padStart(8, '0');
    }


    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) === ' ') {
                beginIndex++;
            } else {
                break;
            }
        }
        return str.substring(beginIndex, str.length);
    }

    function setDefaullts() {
        document.getElementById("plan_id").value = "";
    }


    function verify() {
        var result;
        debugger;
        if (document.getElementById("clickedButton").value === 'SAVE' || document.getElementById("clickedButton").value === 'Save AS New' || document.getElementById("clickedButton").value === 'Save And Continue' || document.getElementById("clickedButton").value === 'Save And Submit') {
           
            var plan_no = document.getElementById("plan_no").value;
            if ($.trim(plan_no).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>plan_no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("plan_no").focus();
                return false; // code to stop from submitting the form2.
            }
          
        }
          return result;
    } 
            


           
  

    var popupwin = null;
    function popup(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        popupwin = window.showModalDialog(url, window_name, "dialogWidth:" + popup_width + "px;" + " dialogHeight:" + popup_height + "px;" + " dialogLeft:" + popup_left_pos + "px;" + " dialogTop:" + popup_top_pos + "px;" + " resizable:no; center:yes");
        popupwin.focus();
    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
        popupwin = window.open(url, window_name, window_features);
        //popupwin = window.showModalDialog(url,window_name,"dialogWidth:" +popup_width+"px;" + " dialogHeight:" +popup_height+ "px;"+" dialogLeft:" +popup_left_pos+ "px;"+" dialogTop:" +popup_top_pos+ "px;"+" resizable:no; center:yes");
        window.focus();
        return popupwin;
    }

   

    


    function ajaxCall() {
        debugger;
        var plan_no = document.getElementById("plan_no").value;
        $.ajax({url: "PlanDetailsCont",
            async: true,
            data: {task: "getPlanNo"},
            dataType: 'json',
            success: function (response_data) {
                debugger;
                document.getElementById("plan_no").value = response_data.plan_no + 1;
            }

        });            //alert(response_data);
    }





</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/style.css" media="screen">
        <link rel="stylesheet" href="style/Table_content.css" media="screen">
        <title>Phase Info Page</title>
        <style>
            table.reference {
                background-color: white;
                border-bottom-style: inset;
                border-collapse: collapse;
                white-space: nowrap;
                width: 60%;
                border: black;
            }

            table.showTable {
                background-color: white;
                border-bottom-style: inset;
                border-collapse: collapse;
                white-space: nowrap;
                width: 60%;
                border: black;
            }


            .input {
                width: 80%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            th {
                text-align: center !important;
            }

            td {
                text-align: center !important;
            }

            .table-borderless > tbody > tr > td,
            .table-borderless > tbody > tr > th,
            .table-borderless > tfoot > tr > td,
            .table-borderless > tfoot > tr > th,
            .table-borderless > thead > tr > td,
            .table-borderless > thead > tr > th {
                border: none;
            }

            .table {
                width: 100%;
                max-width: 100%;
                margin-bottom: 0px;
            }
        </style>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
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
                                                    <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}

                                                            <input type="hidden" id="plan_id${loopCounter.count}" name="plan_id${loopCounter.count}" value="${list.plan_id}">

                                                            <input type="hidden" name="loopCounter" value="${loopCounter.count}">
                                                        </td>


                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.plan_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.on_time_hour}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.on_time_min}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.off_time_hour}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.off_time_min}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.mode}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side1_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side2_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side3_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side4_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side5_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side1_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side2_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side3_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side4_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side5_amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.transferred_status}</td>

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
                                            <input type="hidden" id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form></div>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <div style="overflow: scroll; height: 450px;">
                                        <form name="form" id="form2"  action="PlanDetailsCont" method="post" onsubmit="return verify()">
                                            <table name="table" class="reference"  border="1" align="center" style="width: 80% !important;">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="22" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                               
                                                            <input class="input form-control"  size="15" type="hidden" id="plan_id" name="plan_id" value="" >
                                         
                                                <tr align="center" class="incHeight" >
                                                    <th  class="heading"  align="center" colspan="2">Plan No</th>
                                                    <td colspan="2">
                                                        <input class="input form-control"  size="15" type="text" id="plan_no" name="plan_no" value="" size="30" disabled><br>
                                                    </td>
                                                    
                                                </tr>
                                                <tr>
                                                     <th  class="heading" align="center">On Time Hour</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="on_time_hour" name="on_time_hour" value="" maxlength="16" disabled><br>
                                                    </td>
                                                    
                                                    <th  class="heading" align="center">On Time Min</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="on_time_min" name="on_time_min" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>
                                                
                                                <tr align="center" class="incHeight">
                                                    
                                                    
                                               
                                                    
                                                     <th  class="heading" align="center">Off Time Hour</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="off_time_hour" name="off_time_hour" value="" maxlength="16" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">off Time Min</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="off_time_min" name="off_time_min" value="" maxlength="16" disabled><br>
                                                    </td>
                                                 </tr>
                                              
                                                <tr align="center" class="incHeight" >
                                                      <th  class="heading" align="center" colspan="2">MOde</th>
                                                    <td colspan="2">
                                                        <select class="select form-control" id="mode" name="mode" style="height: 20px;width: 80%;margin: 8px 0;display: inline-block;border: 1px solid #ccc;
                                                                border-radius: 4px;box-sizing: border-box;" disabled>
                                                            <option value="Signal" >Signal</option>
                                                            <option value="Blinker" >Blinker</option>
                                                            <option value="Off" >Off</option>
                                                        </select><BR>
                                                    </td>
                                                </tr>
                                              
                                                <tr align="center" class="incHeight">
                                                    <th  class="heading" align="center">Side1 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side1_green_time" name="side1_green_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                      <th  class="heading" align="center">Side2 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side2_green_time" name="side2_green_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>
                                                <tr align="center" class="incHeight">
                                                  
                                                      <th  class="heading" align="center">Side3 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side3_green_time" name="side3_green_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                     <th  class="heading" align="center">Side4 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side4_green_time" name="side4_green_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>
                                             
                                                <tr align="center" class="incHeight">
                                                   
                                                    <th  class="heading" align="center">Side5 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side5_green_time" name="side5_green_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                     <th  class="heading" align="center">Side1 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side1_amber_time" name="side1_amber_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>
                                                
                                                <tr align="center" class="incHeight">
                                                   
                                                     <th  class="heading" align="center">Side2 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side2_amber_time" name="side2_amber_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                     <th  class="heading" align="center">Side3 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side3_amber_time" name="side3_amber_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>
                                                
                                                <tr align="center" class="incHeight">
                                                   
                                                    <th  class="heading" align="center">Side4 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side4_amber_time" name="side4_amber_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                      <th  class="heading" align="center">Side5 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side5_amber_time" name="side5_amber_time" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>
                                           
                                                <tr align="center" class="incHeight">
                                                  
                                                        <th  class="heading"  align="center">Transferred Status</th>
                                                    <td>
                                                        <input class="input form-control"  type="text" id="transferred_status" name="transferred_status" maxlength="2" disabled><br>
                                                    </td>
                                                     <th  class="heading"  align="center" >Remark</th>
                                                    <td colspan="3">
                                                        <input class="input form-control"  type="text" id="remark" name="remark" maxlength="30" disabled><br>
                                                    </td>
                                                </tr>


 <tr>
                                                                <td align='center' colspan="10">
                                                                    <input class="button" type="submit" id="SAVE" name="task" value="Save" onclick="setStatus(id)" />
                                                                    <input class="button" type="reset" id="NEW" name="task" value="New" onclick="makeEditable(id)"/>
                                                                    <input class="button" type="button" id="EDIT" name="task" value="Edit"  disabled/>
                                                                    <input class="button" type="submit" id="DELETE" name="task" value="Delete" onclick="makeEditable(id)" disabled>
                                                                    <input class="button" type="submit" name="task" id="Save AS New" value="Save AS New" onclick="setStatus(id)" disabled>
                                                            </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form of table. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                    </div>  </td>
                            </tr>
                        </table>
                    </DIV>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>

    </body>
</html>
