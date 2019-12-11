<%--
    Document   : junction
    Created on : Aug 10, 2012, 9:33:33 AM
    Author     : Shruti
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
    
    function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 4;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for(var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "yellowgreen";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("phase_info_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("junction_id").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("junction_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;               
                // Now enable/disable various buttons.
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save_As").disabled = true;
                }
                $("#message").html('');      // Remove message
            }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) === ' ') {
                beginIndex++;
            } else {
                break;
            }
        }
        return str.substring(beginIndex, str.length);
    }

    function setDefaullts(){
        document.getElementById("junction_id").value = "";
    }

    function changecolor(){
        //alert(document.getElementById("pedestrian1").checked);
        if(document.getElementById("pedestrian1").checked){
            document.getElementById("pedestrian_time").value = "";
            document.getElementById("pedestrian_time").disabled = true;
            document.getElementById("pedestrian_time").style.backgroundColor = "lightgrey";
        }else{

            document.getElementById("pedestrian_time").disabled = false;
            document.getElementById("pedestrian_time").style.backgroundColor = "";
        }
    }

      
    function makeEditable(id) {
        document.getElementById("phase_info_id").disabled = false;
        document.getElementById("junction_name").disabled = false;
        document.getElementById("phase_no").disabled = false;
        document.getElementById("plan_no").disabled = false;
        document.getElementById("phase_time").disabled = false;
        document.getElementById("green_one").disabled = false;
        document.getElementById("green_two").disabled = false;
        document.getElementById("green_three").disabled = false;
        document.getElementById("green_four").disabled = false;
        document.getElementById("green_five").disabled = false;
        document.getElementById("side_one_three").disabled = false;
        document.getElementById("side_two_four").disabled = false;
        document.getElementById("side_five").disabled = false;
        document.getElementById("left_green").disabled = false;
        document.getElementById("pedestrian_info").disabled = false;
        document.getElementById("gpio").disabled = false;
        document.getElementById("remark").disabled = false;
        //        document.getElementById("side_5_name").disabled = false;
        if(id === 'NEW') {
            $("#message").html('');
            document.getElementById("EDIT").disabled = true;
            document.getElementById("DELETE").disabled = true;
            document.getElementById("Save AS New").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 22);
            document.getElementById("junction_name").focus();
        }
        if(id === 'EDIT') {
            document.getElementById("Save AS New").disabled = false;
            document.getElementById("DELETE").disabled = false;
            document.getElementById("junction_name").focus();
        }
        document.getElementById("SAVE").disabled = false;
    }

    function setStatus(id) {
        if(id === 'SAVE') {
            document.getElementById("clickedButton").value = "SAVE";
        }
        else if(id === 'DELETE'){
            document.getElementById("clickedButton").value = "DELETE";
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
  
    function verify() {
        var result;
        if(document.getElementById("clickedButton").value === 'SAVE' || document.getElementById("clickedButton").value === 'Save AS New') {
            var junction_name = document.getElementById("junction_name").value;
            if($.trim(junction_name).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Junction Name is required...</b></td>";
                $("#message").html(message);
                document.getElementById("junction_name").focus();
                return false; // code to stop from submitting the form2.
            }
            var plan_no = document.getElementById("plan_no").value;
            if($.trim(plan_no).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>plan_no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("plan_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var phase_no = document.getElementById("phase_no").value;
            if($.trim(phase_no).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>phase_no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("phase_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var green_one = document.getElementById("green_one").value;
            if($.trim(green_one).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>green_one is required...</b></td>";
                $("#message").html(message);
                document.getElementById("green_one").focus();
                return false; // code to stop from submitting the form2.
            }
            var green_two = document.getElementById("green_two").value;
            if($.trim(green_two).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>green_two is required...</b></td>";
                $("#message").html(message);
                document.getElementById("green_two").focus();
                return false; // code to stop from submitting the form2.
            }
            var green_three = document.getElementById("green_three").value;
            if($.trim(green_three).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>green_three model is required...</b></td>";
                $("#message").html(message);
                document.getElementById("green_three").focus();
                return false; // code to stop from submitting the form2.
            }
            var green_four = document.getElementById("green_four").value;
            if($.trim(green_four).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>green_four is required...</b></td>";
                $("#message").html(message);
                document.getElementById("green_four").focus();
                return false; // code to stop from submitting the form2.
            }
            var green_five = document.getElementById("green_five").value;
            if($.trim(green_five).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>green_five is required...</b></td>";
                $("#message").html(message);
                document.getElementById("green_five").focus();
                return false; // code to stop from submitting the form2.
            }
            var side_one_three = document.getElementById("side_one_three").value;
            if($.trim(side_one_three).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>side_one_three is required...</b></td>";
                $("#message").html(message);
                document.getElementById("side_one_three").focus();
                return false; // code to stop from submitting the form2.
            }
            var side_two_four = document.getElementById("side_two_four").value;
            if($.trim(side_two_four).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>side_two_four is required...</b></td>";
                $("#message").html(message);
                document.getElementById("no_of_plans").focus();
                return false; 
            }
            var side_five = document.getElementById("side_five").value;
            if($.trim(side_five).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>side_five is required...</b></td>";
                $("#message").html(message);
                document.getElementById("side_five").focus();
                return false; // code to stop from submitting the form2.
            }
            var left_green = document.getElementById("left_green").value;
            if($.trim(left_green).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>left_green is required...</b></td>";
                $("#message").html(message);
                document.getElementById("left_green").focus();
                return false; // code to stop from submitting the form2.
            }
            var padestrian_info = document.getElementById("padestrian_info").value;
            if($.trim(padestrian_info).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>padestrian_info is required...</b></td>";
                $("#message").html(message);
                document.getElementById("padestrian_info").focus();
                return false; // code to stop from submitting the form2.
            }
            var gpio = document.getElementById("gpio").value;
            if($.trim(gpio).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>GPIO is required...</b></td>";
                $("#message").html(message);
                document.getElementById("gpio").focus();
                return false; // code to stop from submitting the form2.
            }
            if(document.getElementById("clickedButton").value === 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?");
                return result;
            }
        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }    
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/style.css" media="screen">
        <link rel="stylesheet" href="style/Table_content.css" media="screen">
        <title>Junction Page</title>
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
                            <tr><td><table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Junction Details
                                            </td>
                                        </tr>
                                    </table> </td> </tr>                            

<!--                            <tr>
                                <td>
                                    <div style="width: 990px;max-height: 340px;overflow: auto;margin-bottom: 20px">
                                        <form name="form1" action="phaseinfoCont" method="post">
                                            <table name="table1" border="1" width="100%" align="center" class="reference">
                                                <tr>
                                                    <th class="heading" style="visibility: hidden">
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Junction ID</th>
                                                    <th class="heading">Junction Name</th>
                                                    
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['junction']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" style="visibility: hidden" onclick="fillColumns(id)">${list.phase_info_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_name}</td>                                                       
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
                            </tr>-->
                            
                            
                                       <tr>
                                            <td>
                                                <form name="form1" method="POST" action="phaseinfoCont">
                                                    <DIV STYLE="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px">
                                                        <table border="1" id="table1" align="center" class="reference">
                                                            <tr>
                                                    <th class="heading" style="visibility: hidden">
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Junction ID</th>
                                                    <th class="heading">Junction Name</th>
                                                    
                                                </tr>
                                                            <c:forEach var="list" items="${requestScope['junction']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" style="visibility: hidden" onclick="fillColumns(id)">${list.phase_info_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_name}</td>                                                       
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
                                    <div style="width: 990px;overflow: auto ">
                                        <form name="form"  action="phaseinfoCont" method="post" onsubmit="return verify()">
                                            <table name="table" class="reference"  border="1" align="center">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="22" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr align="center">
                                                    <th class="heading" >Junction Name</th>
                                                    <td>
                                                        <input class="input" type="hidden" id="junction_id" name="junction_id" value="" readonly>
                                                        <input class="input" type="hidden" id="phase_info_id" name="phase_info_id" value="" readonly>
                                                        <input class="input"  type="text" id="junction_name" name="junction_name" size="15" value="" disabled>
                                                    </td>
                                                    <th  class="heading">Plan Number</th>
                                                    <td>
                                                        <input class="input"  type="number" id="plan_no" name="plan_no" value="" size="20"disabled>
                                                    </td>
                                                    <th  class="heading">Phase Number</th>
                                                    <td>
                                                        <input class="input" size="15" type="number" id="phase_no" name="phase_no" value="" disabled>
                                                    </td>

                                                </tr>

                                                <tr align="center">
                                                    <th  class="heading" colspan="3">Phase Time</th>
                                                    <td colspan="3">
                                                        <input class="input"  size="15" type="number" id="phase_time" name="phase_time" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading" colspan="6">Green Time</th>
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">Side 1</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="green_one" name="green_one" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 2</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="green_two" name="green_two" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 3</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="green_three" name="green_three" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th  class="heading" colspan="2">Side 4</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="green_four" name="green_four" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    <th  class="heading" colspan="2">Side 5</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="green_five" name="green_five" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading" colspan="6" >Side Time</th>
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">Side 13</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="side_one_three" name="side_one_three" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 24</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="side_two_four" name="side_two_four" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 5</th>
                                                    <td>
                                                        <input class="input"  size="15" type="number" id="side_five" name="side_five" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">Left Green</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="left_green" name="left_green" value="" maxlength="16" disabled><br>
                                                    </td>
                                                    <th  class="heading">Pedestrian Info</th>
                                                    <td>
                                                        <input class="input"  type="text" id="pedestrian_info" name="pedestrian_info" value="30" maxlength="2" disabled><br>
                                                    </td>
                                                   <th  class="heading">GPIO</th>
                                                    <td>
                                                        <input class="input"  type="text" id="gpio" name="gpio" value="30" maxlength="2" disabled><br>
                                                    </td>

                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading" colspan="3" >Remark</th>
                                                    <td colspan="3">
                                                        <input class="input"  type="text" id="remark" name="remark" value="30" maxlength="2" disabled><br>
                                                    </td>
                                                </tr>
                                               
                                                <tr>
                                                    <td align='center' colspan="8">
                                                        <input class="button" type="submit" id="SAVE" name="task" value="Save" onclick="setStatus(id)" />
                                                        <input class="button" type="reset" id="NEW" name="task" value="New" onclick="makeEditable(id);setDefaullts();"/>
                                                        <input class="button" type="button" id="EDIT" name="task" value="Edit" onclick="makeEditable(id)" disabled/>
                                                        <input class="button" type="submit" id="DELETE" name="task" value="Delete" onclick="setStatus(id)" disabled>
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
