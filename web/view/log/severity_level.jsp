<%-- 
    Document   : severity_level
    Created on : 21 Oct, 2019, 12:24:46 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
    function makeEditable(id) {
        document.getElementById("severity_level_id").disabled = false;
        document.getElementById("severity_number").disabled = false;
        document.getElementById("remark").disabled = false;
        //        document.getElementById("side_5_name").disabled = false;
        if (id == 'NEW') {
            $("#message").html('');
            document.getElementById("EDIT").disabled = true;
            document.getElementById("DELETE").disabled = true;
            document.getElementById("Save AS New").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 22);
            document.getElementById("severity_number").focus();
        }
        if (id == 'EDIT') {
            document.getElementById("Save AS New").disabled = false;
            document.getElementById("DELETE").disabled = false;
            document.getElementById("severity_number").focus();
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
        //alert(noOfRowsTraversed);
        var noOfColumns = 3;
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
        //alert(lowerLimit);
        var t1id = "t1c";
        //        alert(rowNo);
        document.getElementById("severity_level_id").value = document.getElementById("severity_level_id" + rowNo).value;
        document.getElementById("revision_no").value = document.getElementById("revision_no" + rowNo).value;
        document.getElementById("severity_number").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        // Now enable/disable various buttons.

        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lower + i)).bgColor = "yellowgreen";        // set the background color of clicked row to yellow.
        }

        document.getElementById("EDIT").disabled = false;
        if (!document.getElementById("SAVE").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("DELETE").disabled = false;
            document.getElementById("NEW").disabled = false;
        }
        $("#message").html('');
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

    function setDefaullts() {
        document.getElementById("junction_id").value = "";
    }

    function changecolor() {
        //alert(document.getElementById("pedestrian1").checked);
        if (document.getElementById("pedestrian1").checked) {
            document.getElementById("pedestrian_time").value = "";
            document.getElementById("pedestrian_time").disabled = true;
            document.getElementById("pedestrian_time").style.backgroundColor = "lightgrey";
        } else {

            document.getElementById("pedestrian_time").disabled = false;
            document.getElementById("pedestrian_time").style.backgroundColor = "";
        }
    }

    function setDropdownVisibility() {
        //alert(document.getElementById("no_of_sides").value);
        var no_of_sides = document.getElementById("no_of_sides").value;
        if (no_of_sides == '2') {
            document.getElementById("side_3_name").value = "";
            document.getElementById("side_4_name").value = "";
            document.getElementById("side_5_name").value = "";
            document.getElementById("side_3_name").disabled = true;
            document.getElementById("side_4_name").disabled = true;
            document.getElementById("side_5_name").disabled = true;
            document.getElementById("side_3_name").style.backgroundColor = "lightgrey";
            document.getElementById("side_4_name").style.backgroundColor = "lightgrey";
            document.getElementById("side_5_name").style.backgroundColor = "lightgrey";
        } else if (no_of_sides == '3') {
            document.getElementById("side_4_name").value = "";
            document.getElementById("side_5_name").value = "";
            document.getElementById("side_3_name").disabled = false;
            document.getElementById("side_4_name").disabled = true;
            document.getElementById("side_5_name").disabled = true;
            document.getElementById("side_3_name").style.backgroundColor = "";
            document.getElementById("side_4_name").style.backgroundColor = "lightgrey";
            document.getElementById("side_5_name").style.backgroundColor = "lightgrey";
        } else if (no_of_sides == '4') {
            document.getElementById("side_5_name").value = "";
            document.getElementById("side_3_name").disabled = false;
            document.getElementById("side_4_name").disabled = false;
            document.getElementById("side_5_name").disabled = true;
            document.getElementById("side_3_name").style.backgroundColor = "";
            document.getElementById("side_4_name").style.backgroundColor = "";
            document.getElementById("side_5_name").style.backgroundColor = "lightgrey";
        } else {
            document.getElementById("side_3_name").disabled = false;
            document.getElementById("side_4_name").disabled = false;
            document.getElementById("side_5_name").disabled = false;
            document.getElementById("side_3_name").style.backgroundColor = "";
            document.getElementById("side_4_name").style.backgroundColor = "";
            document.getElementById("side_5_name").style.backgroundColor = "";
        }
    }

    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'SAVE' || document.getElementById("clickedButton").value == 'Save AS New') {
            var junction_name = document.getElementById("junction_name").value;
            if ($.trim(junction_name).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Junction Name is required...</b></td>";
                $("#message").html(message);
                document.getElementById("junction_name").focus();
                return false; // code to stop from submitting the form2.
            }
            var address_1 = document.getElementById("address_1").value;
            if ($.trim(address_1).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Address1 is required...</b></td>";
                $("#message").html(message);
                document.getElementById("address_1").focus();
                return false; // code to stop from submitting the form2.
            }
            var address_2 = document.getElementById("address_2").value;
            if ($.trim(address_2).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Address2 is required...</b></td>";
                $("#message").html(message);
                document.getElementById("address_2").focus();
                return false; // code to stop from submitting the form2.
            }
            var state_name = document.getElementById("state_name").value;
            if ($.trim(state_name).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>City Name is required...</b></td>";
                $("#message").html(message);
                document.getElementById("state_name").focus();
                return false; // code to stop from submitting the form2.
            }
            var city_name = document.getElementById("city_name").value;
            if ($.trim(city_name).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>City Name is required...</b></td>";
                $("#message").html(message);
                document.getElementById("city_name").focus();
                return false; // code to stop from submitting the form2.
            }
            var controller_model = document.getElementById("controller_model").value;
            if ($.trim(controller_model).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Controller model is required...</b></td>";
                $("#message").html(message);
                document.getElementById("controller_model").focus();
                return false; // code to stop from submitting the form2.
            }
            var no_of_sides = document.getElementById("no_of_sides").value;
            if ($.trim(no_of_sides).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>No of sides is required...</b></td>";
                $("#message").html(message);
                document.getElementById("no_of_sides").focus();
                return false; // code to stop from submitting the form2.
            }
            var amber_time = document.getElementById("amber_time").value;
            if ($.trim(amber_time).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Amber time is required...</b></td>";
                $("#message").html(message);
                document.getElementById("amber_time").focus();
                return false; // code to stop from submitting the form2.
            }
            var flash_rate = document.getElementById("flash_rate").value;
            if ($.trim(flash_rate).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Flash Rate is required...</b></td>";
                $("#message").html(message);
                document.getElementById("flash_rate").focus();
                return false; // code to stop from submitting the form2.
            }
            var no_of_plans = document.getElementById("no_of_plans").value;
            if ($.trim(no_of_plans).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>No of plans is required...</b></td>";
                $("#message").html(message);
                document.getElementById("no_of_plans").focus();
                return false;
            }
            var mobile_no = document.getElementById("mobile_no").value;
            if ($.trim(mobile_no).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Mobile no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("mobile_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var sim_no = document.getElementById("sim_no").value;
            if ($.trim(sim_no).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>SIM NO is required...</b></td>";
                $("#message").html(message);
                document.getElementById("sim_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var imei_no = document.getElementById("imei_no").value;
            if ($.trim(imei_no).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>IMEI NO is required...</b></td>";
                $("#message").html(message);
                document.getElementById("imei_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var instant_green_time = document.getElementById("instant_green_time").value;
            if ($.trim(instant_green_time).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Instant Green Time is required...</b></td>";
                $("#message").html(message);
                document.getElementById("instant_green_time").focus();
                return false; // code to stop from submitting the form2.
            }
            var pedestrian_time = document.getElementById("pedestrian_time").value;
            if (!document.getElementById("pedestrian1").checked) {
                if ($.trim(pedestrian_time).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Pedestrian Time is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("pedestrian_time").focus();
                    return false; // code to stop from submitting the form2.
                }
            }
            var no_of_sides = document.getElementById("no_of_sides").value;
            var side_1_name = document.getElementById("side_1_name").value;
            var side_2_name = document.getElementById("side_2_name").value;
            var side_3_name = document.getElementById("side_3_name").value;
            var side_4_name = document.getElementById("side_4_name").value;
            var side_5_name = document.getElementById("side_5_name").value;
            if (no_of_sides == '2') {
                if ($.trim(side_1_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 1 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_1_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_2_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 2 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_2_name").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (no_of_sides == '3') {
                if ($.trim(side_1_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 1 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_1_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_2_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 2 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_2_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_3_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 3 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_3_name").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (no_of_sides == '4') {
                if ($.trim(side_1_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 1 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_1_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_2_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 2 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_2_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_3_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 3 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_3_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_4_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 4 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_4_name").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else {
                if ($.trim(side_1_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 1 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_1_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_2_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 2 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_2_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_3_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 3 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_3_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_4_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 4 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_4_name").focus();
                    return false; // code to stop from submitting the form2.
                }
                if ($.trim(side_5_name).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Side 5 name is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side_5_name").focus();
                    return false; // code to stop from submitting the form2.
                }
            }
            var file_no = document.getElementById("file_no").value;
            if ($.trim(file_no).length == 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>File no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("file_no").focus();
                return false; // code to stop from submitting the form2.
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
  function displayMapList(id) {
                var queryString;
            var searchstate=document.getElementById("searchstate").value;
          
                if (id === 'viewPdf')
                    queryString = "requester=PRINT"+"&searchstate=" + searchstate;
                else
                    queryString = "requester=PRINTXls"+"&searchstate=" + searchstate;
                var url = "SeverityLevelCont?" + queryString;
                popupwin = openPopUp(url, "SeverityLevelCont", 600, 900);
            }  
            
             function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
               jQuery(function () {
            $("#searchstate").autocomplete("SeverityLevelCont", {
                    extraParams: {
                        action1: function () {
                            return "getState";
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
        <title>Severity Level Page</title>
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
                                                Severity Level Details
                                            </td>
                                        </tr>
                                    </table> </td> </tr>
<tr><td>
                                    <form action="SeverityLevelCont" method="post" class="form-group container-fluid">
                   
                                        <table align="center" border="1px">
                                        <tr >
                                             <td>
                                              Serveirty Level<input type="text" name="searchstate" id="searchstate" value="${searchstate}">
                                            </td>
                                            
                                       
                                         <td>
                                              <input type="submit" name="search" id="search" value="Search"/>  
                                             <input type="submit" name="task" value="SearchAllRecords"/>
                                               </tr>
                                    </table></form> </td></tr>
                            <tr>
                                <td>
                                    <div style="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px">
                                        <form name="form1" action="SeverityLevelCont" method="post">
                                            <table name="table1" border="1" width="100%" align="center" class="reference70">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Severity Number</th>
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['severity_level']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="severity_level_id${loopCounter.count}" value="${list.severity_level_id}">
                                                            <input type="hidden" id="revision_no${loopCounter.count}" value="${list.revision_no}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.severity_number}</td>
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
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form></div>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                  
                                        <form name="form"  action="SeverityLevelCont" method="post" onsubmit="return verify()">
                                            <DIV STYLE="overflow: auto; width: 500px; max-height: 410px; padding:0px; margin-left:200px; align-self:center ">
                                            <table name="table" class="reference"  border="1" align="center">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="22" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr align="center">
                                                    <th class="heading">Severity Level Number</th>
                                                    <td>
                                                        <input class="input" type="hidden" id="severity_level_id" name="severity_level_id" value="" readonly>
                                                        <input class="input" type="hidden" id="revision_no" name="revision_no" value="" readonly>
                                                        <input class="input"  type="text" id="severity_number" name="severity_number" size="15" value="" disabled>
                                                    </td>
                                                    <th  class="heading">Remark</th>
                                                    <td>
                                                        <input class="input"  type="text" id="remark" name="remark" value="" size="20"disabled><br>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="8">
                                                        <input class="button" type="submit" id="SAVE" name="task" value="Save" onclick="setStatus(id)" />
                                                        <input class="button" type="reset" id="NEW" name="task" value="New" onclick="makeEditable(id);
                                                                setDefaullts();"/>
                                                        <input class="button" type="button" id="EDIT" name="task" value="Edit" onclick="makeEditable(id)" disabled/>
                                                        <input class="button" type="submit" id="DELETE" name="task" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input class="button1" type="submit" name="task" id="Save AS New" value="Save AS New" onclick="setStatus(id)" disabled>
                                                </tr>

                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form of table. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                              </div>
                                        </form>
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
