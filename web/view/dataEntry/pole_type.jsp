<%-- 
    Document   : pole_type
    Created on : 23 Jul, 2019, 12:08:32 PM
    Author     : DELL
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Entry: Pole Type Table</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

            function makeEditable(id) {
                document.getElementById("pole_type_id").disabled = false;
                document.getElementById("pole_type").disabled = false;
                if(id == 'new') {
                    document.getElementById("save").disabled = false;
                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                    document.getElementById("pole_type").focus();
//                     document.getElementById("save").disabled = false;
                    
                }
                if(id == 'edit') {
                    document.getElementById("delete").disabled = false;
                }
//                document.getElementById("save").disabled = false;
            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 3;
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
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "lightgray";
                }
                // Now get clicked row data, and set these into the below edit table.
                document.getElementById("pole_type_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("pole_type").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                // Now enable/disable various buttons.
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("message").innerHTML = "";      // Remove message
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                } else {
                    document.getElementById("clickedButton").value = "Delete";;
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save') {
                    var pole_type = document.getElementById("pole_type").value;
                    if(myLeftTrim(pole_type).length == 0) {
                        alert("Pole_Type is required");
                        document.getElementById("pole_type").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    if(result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
                return result;
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
                    <DIV id="body" class="maindiv" >
                        <table align="center" width="1000" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>
                                    <table  class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Pole Type Table
                                            </td>
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="PoleTypeCont">
                                        <DIV STYLE="overflow: auto; width: 400px; max-height: 410px; padding:0px; margin-bottom: 20px; margin-top:20px">
                                            <table id="rcorners3" align="center" class="reference">
                                                <tr>
                                                    <th class="heading" style="display: none"> State ID</th>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading" colspan="3">Pole Type</th>
                                                </tr>
                                                <c:forEach var="pole_type" items="${requestScope['pole_typeList']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${pole_type.pole_type_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" colspan="3">${pole_type.pole_type}</td>
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
                                                    </td></tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form3" method="POST" action="PoleTypeCont" onsubmit="return verify()">
                                        <DIV STYLE="overflow: auto; width: 600px;padding:0px; margin: 0px">
                                            <table class="divv" border="1" border-color="blue">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>

                                                <tr>
                                                    <td style="display: none"></td>
                                                    <th class="heading">Pole_Type</th><td><input class="input" type="text" id="pole_type" name="pole_type" size="38" value="" disabled></td>
                                                </tr>


                                                <tr id="rcorners4" align="center" class="reference" border-radius='15'>
                                                    <td align='center' colspan="3">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" id="pole_type_id" name="pole_type_id" value="" disabled>
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

