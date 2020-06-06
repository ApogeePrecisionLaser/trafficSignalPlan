<%-- 
    Document   : severity_case
    Created on : 21 Oct, 2019, 12:25:08 PM
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
        $("#severity_number").autocomplete("SeverityCaseCont", {
            extraParams: {
                action1: function () {
                    return "getLevel"
                }
            }
        });
       
    });

    function makeEditable(id) {
        document.getElementById("severity_level_id").disabled = false;
        document.getElementById("severity_number").disabled = false;
        document.getElementById("case").disabled = false;
        document.getElementById("send_data").disabled = false;
        document.getElementById("recieved_data").disabled = false;
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
        var noOfColumns = 6;
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
        document.getElementById("severity_case_id").value = document.getElementById("severity_case_id" + rowNo).value;
        document.getElementById("revision_no").value = document.getElementById("revision_no" + rowNo).value;
        document.getElementById("case").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("severity_number").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("send_data").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("recieved_data").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
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

   

    function setDefaullts() {
        document.getElementById("junction_id").value = "";
    }

   
function displayMapList(id) {
                var queryString;
            var searchstate=document.getElementById("searchstate").value;
            var searchcase=document.getElementById("searchcase").value;
          
                if (id === 'viewPdf')
                    queryString = "requester=PRINT"+"&searchstate=" + searchstate+"&searchcase="+searchcase;
                else
                    queryString = "requester=PRINT"+"&searchstate=" + searchstate+"&searchcase="+searchcase;
                var url = "SeverityCaseCont?" + queryString;
                popupwin = openPopUp(url, "SeverityCaseCont", 600, 900);
            }  
            
             function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
               jQuery(function () {
            $("#searchstate").autocomplete("SeverityCaseCont", {
                    extraParams: {
                        action1: function () {
                            return "getState";
                        }
                    }
                });
                });
               jQuery(function () {
            $("#searchcase").autocomplete("SeverityCaseCont", {
                    extraParams: {
                        action1: function () {
                            return "getCase";
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
        <title>Severity Case Page</title>
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
                                                Severity Case Details
                                            </td>
                                        </tr>
                                    </table> </td> </tr>
<tr><td>
                                    <form action="SeverityCaseCont" method="post" class="form-group container-fluid">
                   
                                        <table align="center" border="1px">
                                        <tr >
                                             <td>
                                              Serveirty Number<input type="text" name="searchstate" id="searchstate" value="${searchstate}">
                                            </td>
                                            
                                             <td>
                                              Serveirty Case<input type="text" name="searchcase" id="searchcase" value="${searchcase}">
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
                                        <form name="form1" action="SeverityCaseCont" method="post">
                                            <table name="table1" border="1" width="100%" align="center" class="reference70">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Case</th>
                                                    <th class="heading">Severity Number</th>
                                                    <th class="heading">Send Data</th>
                                                    <th class="heading">Received data</th>
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                
                                                <c:forEach var="list" items="${requestScope['severity_case']}" varStatus="loopCounter">
                                                   <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'> 
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="severity_level_id${loopCounter.count}" value="${list.severity_level_id}">
                                                            <input type="hidden" id="severity_case_id${loopCounter.count}" value="${list.severity_case_id}">
                                                            <input type="hidden" id="revision_no${loopCounter.count}" value="${list.revision_no}">
                                                    </td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.severity_case}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.severity_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sent_data}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.received_data}</td>
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
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form></div>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <div style="width: 698px;padding-left: 148px;">
                                        <form name="form"  action="SeverityCaseCont" method="post" >
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
                                                        <input class="input" type="hidden" id="severity_case_id" name="severity_case_id" value="" readonly>
                                                        <input class="input" type="hidden" id="revision_no" name="revision_no" value="" readonly>
                                                        <input class="input"  type="text" id="severity_number" name="severity_number" size="15" value="" disabled>
                                                    </td>
                                                </tr>
                                                <tr align="center">                                                    
                                                    <th  class="heading">Severity Case</th>
                                                    <td>
                                                        <input class="input"  type="text" id="case" name="case" value="" size="20" disabled><br>
                                                    </td>                                                   
                                                </tr>
                                                <tr align="center">                                                    
                                                    <th  class="heading">Send Data</th>
                                                    <td>
                                                        <input class="input"  type="text" id="send_data" name="send_data" value="" size="20" disabled><br>
                                                    </td>                                                   
                                                </tr>
                                                <tr align="center">                                                    
                                                    <th  class="heading">Received Data</th>
                                                    <td>
                                                        <input class="input"  type="text" id="recieved_data" name="recieved_data" value="" size="20" disabled><br>
                                                    </td>                                                   
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">Remark</th>
                                                    <td>
                                                        <input class="input"  type="text" id="remark" name="remark" value="" size="20" disabled><br>
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
