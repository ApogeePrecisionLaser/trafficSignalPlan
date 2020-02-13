<%--
    Document   : phase Data
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
        <title>Data Entry: Phase Data</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" type="text/css" href="style/style.css" />
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />

        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js">
      
        </script>

        <style>
            .divtab{
                border-color: black;
                margin-top: 6%;
                margin-left: 13%;

                width: 70%;
                height: 70%;
            }
            .red{
                background-color: red;
                color: red;
            }
        </style>
    </head>
    <body onload="generate_table('${side_no}', '${no_of_phase}', '${day_select}', '${date_select}', '${junction_name}', '${on_off_time_select}','${selected_plan_id1}')" id="mainBody">



        <table align="center" cellpadding="0" cellspacing="0" class="main" id="mainTable">
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
                                                Junction Phase Data
                                            </td>
                                        </tr>
                                    </table> 
                                </td> 
                            </tr>
                            <tr>
                                <td>
                                    <table id="table1"  align="center" width="500">
                                        <tr>
                                            <td>
                                                <form name="form3" method="POST" action="PhaseDataCont">
                                                    <DIV STYLE="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px">
                                                        <table border="1" id="table2" align="center" class="reference">
                                                            <tr>
                                                                <th class="heading" >S.No.</th>
                                                                <th class="heading" >Junction Name</th>
                                                                <th class="heading" >To&From Date</th>
                                                                <th class="heading" >Day</th>
                                                                <th class="heading" >Time</th>
                                                                <th class="heading" >Order No</th>
                                                                <th class="heading" >Plan No</th>
                                                                <th class="heading" >Phase No</th>
                                                                <th class="heading" >Side13</th>
                                                                <th class="heading" >Side24</th>
                                                                <th class="heading" >Padestrian Info</th>
                                                                <th class="heading" >Day Name</th>
                                                                <th class="heading" >Remark</th>

                                                            </tr>
                                                            <c:forEach var="planMap" items="${requestScope['junctionPlanMapPhaseList']}" varStatus="loopCounter">
                                                                <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none" onclick="fillColumns(id)">${planMap.junction_plan_map_id}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.junction_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.from_date}//${planMap.to_date}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.day}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.on_time_hr}:${planMap.on_time_min}-${planMap.off_time_hr}:${planMap.off_time_min}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.order_no}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.plan_no}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.phase_no}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.side13}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.side24}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.padestrian_info}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.day_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${planMap.remark}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" style="display: none" onclick="fillColumns(id)">${planMap.junction_id}</td>
                                                                    <td>${selected_plan_id1}</td>


                                                                </tr>
                                                            </c:forEach>
                                                            <tr>
                                                                <td align='center' colspan="15">
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
                                                            <input class="input" type="hidden" id="junction_id" name="junction_id" value="${junction_id}" size="50" >
                                                        </table>
                                                    </DIV>
                                                </form>
                                            </td>
                                        </tr>
                                        <tr> 
                                            <td> <div align="center">
                                                    <form name="form0" method="POST" action="PhaseDataCont">
                                                        <table align="center" class="heading1" width="600">
                                                            <tr>
                                                                <td>Junction<input class="input" type="text" id="searchJunctionName" name="searchJunctionName" value="" size="20" ></td>
                                                                <td>Day<select name="day" id="day" onchange="disableDate(this.value)">
                                                                        <option value="0">--select--</option>
                                                                        <option value="Monday">Monday</option>
                                                                        <option value="Tuesday">Tuesday</option>
                                                                        <option value="Wednesday">Wednesday</option>
                                                                        <option value="Thursday">Thursday</option>
                                                                        <option value="Friday">Friday</option>
                                                                        <option value="Saturday">Saturday</option>
                                                                        <option value="Sunday">Sunday</option></select></td>

                                                                <td>Date<input class="input" type="text" id="date" name="date" value="" maxlength="5" size="20"></td>
                                                            <td><input class="input" type="hidden" id="selected_plan_id" name="selected_plan_id" value="" size="50" >
                                                                 <input type="hidden" id="selected_plan_id1" name="selected_plan_id1" value="${selected_plan_id1}">
                                                            ON&OFF Time<input class="input" type="text" id="start_time" name="start_time" value="" onkeyup="myFunction()" maxlength="6" size="20"></td>


                                                            <!--                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                                                                            <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                                                                            <td><input class="button" type="button" name="task" id="nextPage" value="Next Page" onclick="goToDevice()"></td>
                                                                                                            <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>-->
                                                            <td>Save<input class="button" type="submit" name="task" id="ok" value="OK"></td>
                                                            </tr>
                                                            <input type="hidden" name="side_no" value="${side_no}">
                                                            <input type="hidden" name="no_of_phase" value="${no_of_phase}">
                                                            <input type="hidden" name="junction_name" value="${junction_name}">
                                                            <input type="hidden" name="day_select" value="${day_select}">
                                                            <input type="hidden" name="date_select" value="${date_select}">
                                                            <input type="hidden" name="on_off_time_select" value="${on_off_time_select}">
                                                           
                                                        </table>
                                                    </form>
                                                </div>

                                            </td>
                                        </tr>
                                        <tr id="mainTable">
                                            <td>
                                                <div id="tableDiv" class="divtab"></div>
                                            </td>
                                        </tr>

                                    </table>
                                    </DIV>
                                </td>
                            </tr>
                            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
                        </table>
                        <script type="text/javascript" language="javascript">
                            jQuery(function () {
                                $("#searchJunctionName").autocomplete("PhaseDataCont", {

                                    extraParams: {
                                        action1: function () {
                                            return "getsearchJunctionName";
                                        }
                                    }
                                });
                                $("#date").autocomplete("PhaseDataCont", {
                                    extraParams: {
                                        action1: function () {
                                            return "getDate"
                                        },
                                        action2: function () {
                                            return  $("#searchJunctionName").val();
                                        }
                                    }
                                });


                                //        $("#day").autocomplete("PhaseDataCont",{
                                //            extraParams: {
                                //                action1 : function () {
                                //                    return "getDay"
                                //               },
                                //                action2: function () { return $("#searchJunctionName").val();},
                                //                action3: function () { return $("#date").val();}
                                //                
                                //            }
                                //            
                                //        });

//                                $("#start_time").autocomplete("PhaseDataCont", {
//
//                                    extraParams: {
//                                        action1: function () {
//                                            return "getOnOffTime";
//                                        },
//                                        action2: function () {
//                                            return $("#searchJunctionName").val();
//                                        },
//                                        action3: function () {
//                                            return $("#day").val();
//                                        },
//                                        action4: function () {
//                                            return $("#date").val();
//                                        }
//
//                                    }
//                                });



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
                                debugger;
                                if (val != 0) {
                                    document.getElementById("date").disabled = true;
                                } else if (val == 0) {
                                    document.getElementById("date").disabled = false;
                                }

                            }
                            debugger;
                            function generate_table(side_no, no_of_phase, day_select, date_select, junction_name, on_off_time_select,selected_plan_id1) {
                                // get the reference for the body
                                debugger;
                                var body = document.getElementsByTagName("body")[0];
                                var div1 = document.createElement("myModel");
                                var div2 = document.getElementById("tableDiv");
                                var form = document.createElement("form");
                                var button1 = document.createElement("input");
                                button1.name = "task";
                                button1.value = "submit";
                                button1.type = "submit";

                                // creates a <table> element and a <tbody> element
                                var tbl = document.createElement("table");

                                var tblBody = document.createElement("tbody");
                                var row1 = document.createElement("tr");
                                var elementHiddenl = document.createElement("input");
                                elementHiddenl.name = "columns";
                                elementHiddenl.value = side_no;
                                elementHiddenl.type = "hidden";
                                var elementHidden2 = document.createElement("input");
                                elementHidden2.name = "rows";
                                elementHidden2.value = no_of_phase;
                                elementHidden2.type = "hidden";
                                var elementHidden3 = "";
                                elementHidden3 = document.createElement("input");
                                elementHidden3.name = "day_select";
                                elementHidden3.value = day_select;
                                elementHidden3.type = "hidden";
                                var elementHidden4 = "";
                                elementHidden4 = document.createElement("input");
                                elementHidden4.name = "date_select";
                                elementHidden4.value = date_select;
                                elementHidden4.type = "hidden";
                                var elementHidden5 = "";
                                elementHidden5 = document.createElement("input");
                                elementHidden5.name = "junction_name";
                                elementHidden5.value = junction_name;
                                elementHidden5.type = "hidden";
                                var elementHidden6 = "";
                                elementHidden6 = document.createElement("input");
                                elementHidden6.name = "on_off_time_select";
                                elementHidden6.value = on_off_time_select;
                                elementHidden6.type = "hidden";
                                 var elementHidden7 = "";
                                elementHidden7 = document.createElement("input");
                                elementHidden7.name = "selected_plan_id1";
                                elementHidden7.value = selected_plan_id1;
                                elementHidden7.type = "hidden";
                                form.method = "POST";
                                form.action = "PhaseDataCont";
                                for (var i = 0; i < 1; i++) {
                                    // creates a table row
                                    var cellTextIndex = document.createTextNode("sideNo");

                                    for (var j = 0; j <= side_no; j++) {

                                        // Create a <td> element and a text node, make the text
                                        // node the contents of the <td>, and put the <td> at
                                        // the end of the table row
                                        var cellText = "";
                                        var cell = document.createElement("td");
                                        var cell1 = document.createElement("td");
                                        var row2 = document.createElement("tr");
                                        if (j === 0)
                                        {
                                            cellText = document.createTextNode("S.NO.");
                                        } else {
                                            cellText = document.createTextNode("side " + j);
                                            cell1.appendChild(cellText);
                                            row1.appendChild(cell1);
                                        }

                                    }

                                    tblBody.appendChild(row1);
                                }

                                // creating all cells
                                var cellText1 = "";
                                var cellText2 = "";
                                var cellText3 = "";
                                var cellText4 = "";
                                var cellText5 = "";
                                for (var i = 0; i < no_of_phase; i++) {
                                    // creates a table row
                                    var row = document.createElement("tr");
                                    var rowText = document.createElement("tr");
                                    for (var j = 0; j < side_no; j++) {
                                        // Create a <td> element and a text node, make the text
                                        // node the contents of the <td>, and put the <td> at
                                        // the end of the table row
                                        var cell = document.createElement("td");
                                        var cellTextcolor = document.createElement("td");


                                        cellText1 = document.createTextNode("R");

                                        cell.appendChild(cellText1);
                                        rowText.appendChild(cell);



                                        var element1 = document.createElement("input");
                                        element1.type = "radio";
                                        element1.id = "hp" + i;
                                        element1.name = "hp" + i + j;
                                        element1.value = "red" + i + j;
                                        element1.size = 3;
                                        cell.appendChild(element1);
                                        row.appendChild(cell);
                                        cellText2 = document.createTextNode("Y");

                                        cell.appendChild(cellText2);
                                        rowText.appendChild(cell);
                                        var element2 = document.createElement("input");
                                        element2.type = "radio";
                                        element2.id = "hp1" + i;
                                        element2.name = "hp" + i + j;
                                        element2.value = "amber" + i + j;
                                        element2.size = 3;
                                        cell.appendChild(element2);
                                        row.appendChild(cell);
                                        cellText3 = document.createTextNode("G1");

                                        cell.appendChild(cellText3);
                                        rowText.appendChild(cell);
                                        var element3 = document.createElement("input");
                                        element3.type = "radio";
                                        element3.id = "hp2" + i;
                                        element3.name = "hp" + i + j;
                                        element3.value = "green1" + i + j;
                                        element3.size = 3;
                                        cell.appendChild(element3);
                                        row.appendChild(cell);

                                        cellText4 = document.createTextNode("G2");

                                        cell.appendChild(cellText4);
                                        rowText.appendChild(cell);
                                        var element4 = document.createElement("input");
                                        element4.type = "radio";
                                        element4.id = "hp3" + i;
                                        element4.name = "hp" + i + j;
                                        element4.value = "green2" + i + j;
                                        element4.size = 3;
                                        cell.appendChild(element4);
                                        row.appendChild(cell);

                                        cellText5 = document.createTextNode("G1G2");

                                        cell.appendChild(cellText5);
                                        rowText.appendChild(cell);
                                        var element5 = document.createElement("input");
                                        element5.type = "radio";
                                        element5.id = "hp4" + i;
                                        element5.name = "hp" + i + j;
                                        element5.value = "green12" + i + j;
                                        element5.size = 3;
                                        cell.appendChild(element5);
                                        row.appendChild(cell);

                                    }

                                    // add the row to the end of the table body

                                    tblBody.appendChild(row);
                                    tblBody.appendChild(rowText);
                                }

                                tblBody.appendChild(row);
                                row2.appendChild(button1);
                                tblBody.appendChild(row2);

                                // put the <tbody> in the <table>
                                tbl.appendChild(tblBody);
                                //                  form.appendChild(element1);
                                //                   form.appendChild(element2);
                                //                   form.appendChild(element3);
                                //                      form.appendChild(element4);
                                //                         form.appendChild(tblBody);
                                form.appendChild(elementHiddenl);
                                form.appendChild(elementHidden2);
                                form.appendChild(elementHidden3);
                                form.appendChild(elementHidden4);
                                form.appendChild(elementHidden5);
                                form.appendChild(elementHidden6);
                                form.appendChild(elementHidden7);
                                form.appendChild(tbl);
                                //                          form.appendChild(button1);




                                debugger;
                                var p = document.getElementById("tableDiv");
                                p.appendChild(form);
                                //               
                                tbl.setAttribute("border", "1");
                                tbl.setAttribute("align", "center");
                                tbl.setAttribute("margin", "10");
                                element3.setAttribute("class", "red");

                                //   divl.setAttribute("class", "center");
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
      function myFunction() {
          debugger;
                var url = "PlanDetailIdController?";
                popupwin = openPopUp(url, "plan_detail_id", 580, 900);


            }
                        </script>
                        </body>
                        </html>

