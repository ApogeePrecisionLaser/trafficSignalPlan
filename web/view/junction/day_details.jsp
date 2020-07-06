<%-- 
    Document   : district
    Created on : Aug 14, 2012, 11:01:10 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: District Table</title>
          <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

            jQuery(function () {
                $("#junction_name").autocomplete("dayDetailsCont", {
                    extraParams: {
                        action1: function () {
                            return "getJunctionName"
                        }
                    }
                });

            });

            function makeEditable(id) {
                document.getElementById("day_detail_id").disabled = false;
                document.getElementById("day_name").disabled = false;
                document.getElementById("day").disabled = false;
            
                document.getElementById("remark").disabled = false;
                if (id === 'new') {
                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 4);
                    document.getElementById("junction_name").focus();
                }
                if (id === 'edit') {
                    document.getElementById("save_As").disabled = false;
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
                debugger;
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 5;
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

                document.getElementById("day_detail_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("day_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("day").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
              
                document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                // Now enable/disable various buttons.
                document.getElementById("edit").disabled = false;
                if (!document.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                document.getElementById("message").innerHTML = "";      // Remove message
            }
            function setStatus(id) {
                if (id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else if (id == 'save_As') {
                    document.getElementById("clickedButton").value = "Save AS New";
                } else {
                    document.getElementById("clickedButton").value = "Delete";

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
                if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var junction_name = document.getElementById("junction_name").value;
                    if (myLeftTrim(state_name).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>State Name is required...</b></td>";
                        document.getElementById("junction_name").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    var day_name = document.getElementById("day_name").value;
                    if (myLeftTrim(district_name).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>District Name is required...</b></td>";
                        document.getElementById("day_name").focus();
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
            
             function displayMapList(id) {
                var queryString;
            var searchstate=document.getElementById("searchstate").value;
            var searchday=document.getElementById("searchday").value;
          
                if (id === 'viewPdf')
                    queryString = "requester=PRINT"+"&searchstate=" + searchstate + "&searchday="+searchday;
                else
                  queryString = "requester=PRINT"+"&searchstate=" + searchstate + "&searchday="+searchday;
                var url = "dayDetailsCont?" + queryString;
                popupwin = openPopUp(url, "PoleTypeCont", 600, 900);
            }  
            
             function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
               jQuery(function () {
            $("#searchstate").autocomplete("dayDetailsCont", {
                    extraParams: {
                        action1: function () {
                            return "getState";
                        }
                    }
                });
                });
                  jQuery(function () {
            $("#searchday").autocomplete("dayDetailsCont", {
                    extraParams: {
                        action1: function () {
                            return "getDay";
                        }
                    }
                });
                });
        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table align="center" width="100%"border="0" cellpadding="0" cellspacing="0" >
                            <tr><td>
                                    <table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Day Table
                                            </td>
                                        </tr>
                                    </table>
                                </td> </tr>
<tr><td>
                                    <form action="dayDetailsCont" method="post" class="form-group container-fluid">
                   
                                        <table align="center" border="1px">
                                        <tr >
<!--                                             <td>
                                            Junction<input type="text" name="searchstate" id="searchstate" value="${searchstate}">
                                            </td>-->
                                             <td>
                                              Day<input type="text" name="searchday" id="searchday" value="${searchday}">
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
                                    <form name="form1" method="POST" action="dayDetailsCont">
                                        <DIV STYLE="overflow: auto; width: 500px; max-height: 410px; padding:0px; margin-bottom: 20px" >
                                            <table  border="1" id="table1" align="center" class="reference">
                                                <tr>

                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Day Name</th>
                                                    <th class="heading">Day</th>
<!--                                                    <th class="heading">Junction Name</th>-->
                                                    <th class="heading">Remark</th>

                                                </tr>
                                                <c:forEach var="dayDetail" items="${requestScope['dayDetailList']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${dayDetail.day_detail_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}

                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${dayDetail.day_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${dayDetail.day}</td>
                                                      
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${dayDetail.remark}</td>
                                                    <input style="display: none" onclick="fillColumns(id)" value="${dayDetail.junction_id}">


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
                                                    </td> </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                    <input type="hidden" name="manname" value="${manname}">
                                                    <input type="hidden" name="pname" value="${manname}">
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="dayDetailsCont" onsubmit="return verify()">
                                        <DIV STYLE="overflow: auto; width: 380px; padding:0px; margin-bottom: 20px" >
                                            <table  border="1" id="table2" align="center" class="reference" >
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>

                                                <tr>
                                                    <th class="heading">Day Name</th>
                                                    <td><input class="input" type="text" id="day_name" name="day_name" size="30" value="" disabled>
                                                     <input type="hidden" id="day_detail_id" name="day_detail_id" disabled></td>
                                                </tr>
                                                <tr> <th class="heading" >Day</th><td><select name="day" id="day" style="width: 83%; align-content: center;">
                                                            <option>--select--</option>
                                                            <option value="Monday">Monday</option>
                                                            <option value="Tuesday">Tuesday</option>
                                                            <option value="Wednesday">Wednesday</option>
                                                            <option value="Thursday">Thursday</option>
                                                            <option value="Friday">Friday</option>
                                                            <option value="Saturday">Saturday</option>
                                                            <option value="Sunday">Sunday</option>

                                                        </select></td>

                                                </tr> 
                                              
                                                <tr>
                                                    <th class="heading">Remark</th>
                                                    <td><input class="input" type="text" id="remark" name="remark" size="30" value="" disabled></td>
                                                </tr>


                                                <tr>
                                                    <td align='center' colspan="2">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)">
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>  
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> 
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>

                                                <input type="hidden" id="day_detail_id" name="day_detail_id" value="" disabled>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </DIV>
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

