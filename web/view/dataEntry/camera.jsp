 <%-- 
    Document   : camera_make
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
        <title>Data Entry: Camera Table</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

            function makeEditable(id) {
                document.getElementById("camera_id").disabled = false;
                document.getElementById("camera_ip").disabled = false;
                document.getElementById("camera_make").disabled = false;
                document.getElementById("camera_mod").disabled = false;
                document.getElementById("camera_type").disabled = false;
                document.getElementById("junction_id").disabled = false;
                document.getElementById("junction_name").disabled = false;
                document.getElementById("side_no").disabled = false;
                 document.getElementById("latitude").disabled = false;
                  document.getElementById("longitude").disabled = false;
 document.getElementById("camera_facing").disabled = false;
 document.getElementById("lane_no").disabled = false;
                document.getElementById("remark").disabled = false;
                if (id == 'new') {
                    document.getElementById("save").disabled = false;
                    document.getElementById("message").innerHTML = "";      // Remove message
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
                    document.getElementById("camera_ip").focus();
                    document.getElementById("save").disabled = false;
  document.getElementById("latitude").disabled = false;
                  document.getElementById("longitude").disabled = false;
 document.getElementById("camera_facing").disabled = false;
 document.getElementById("camera_mod").disabled = false;
 document.getElementById("lane_no").disabled = false;
                }
                if (id == 'edit') {
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
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 13;
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
                document.getElementById("camera_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
                document.getElementById("camera_ip").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                 document.getElementById("camera_make").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
                 document.getElementById("camera_mod").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
                 document.getElementById("camera_type").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                 document.getElementById("junction_name").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
                 document.getElementById("side_no").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
                document.getElementById("latitude").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
                document.getElementById("longitude").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
                document.getElementById("camera_facing").value = document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
                document.getElementById("lane_no").value = document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
                document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
                // Now enable/disable various buttons.
                  document.getElementById("edit").disabled = false;
                if (!document.getElementById("save").disabled) {
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("message").innerHTML = "";      // Remove message
            }
            function setStatus(id) {
                if (id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
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
                    var camera_ip = document.getElementById("camera_ip").value;
                    if (myLeftTrim(camera_ip).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>camera ip is required...</b></td>";
                        document.getElementById("camera_ip").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    var remark = document.getElementById("remark").value;
                    if (myLeftTrim(remark).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>remark is required...</b></td>";
                        document.getElementById("remark").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    var file = document.getElementById("file").value;
                    if (myLeftTrim(file).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>file is required...</b></td>";
                        document.getElementById("file").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    var lat = document.getElementById("latitude").value;
                     if (myLeftTrim(lat).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>latitude is required...</b></td>";
                        document.getElementById("latitude").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    var camera_mod = document.getElementById("camera_mod").value;
                     if (myLeftTrim(camera_mod).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>latitude is required...</b></td>";
                        document.getElementById("camera_mod").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    
                     var longitude = document.getElementById("longitude").value;
                     if (myLeftTrim(longitude).length == 0) {
                        document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>longitude is required...</b></td>";
                        document.getElementById("longitude").focus();
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
            
            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
    debugger;
                return window.open(url, window_name, window_features);
            }
            
 function openMapForCord() {
        var url="CameraCont?task=GetCordinates1";
        popupwin = openPopUp(url, "",  600, 630);
       
    }
            jQuery(function () {
                $("#camera_make").autocomplete("CameraCont", {
                    extraParams: {
                        action1: function () {
                            return "getCameraMake";
                        }
                    }
                });
                $("#camera_type").autocomplete("CameraCont", {
                    extraParams: {
                        action1: function () {
                            return "getCameraType";
                        }
                        
                    }
                });
                $("#camera_mod").autocomplete("CameraCont", {
                    extraParams: {
                        action1: function () {
                            return "getCameraModel";
                        }, action2: function () {
                            return $("#camera_make").val();
                        }
                        
                    }
                });
                 $("#junction_name").autocomplete("CameraCont", {
                    extraParams: {
                        action1: function () {
                            return "getsearchJunctionName";
                        }
                    }
                });

            });
        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <div id="body" class="maindiv" >
                        <table align="center" width="1000" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>
                                    <table  class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Camera Table
                                            </td>
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="CameraCont">
                                        <div STYLE="overflow: auto; width: 900px; max-height: 410px; padding:0px; margin-bottom: 20px; margin-top:20px">
                                            <table id="rcorners3" align="center" class="reference" border="1">
                                                <tr>
                                                    <th class="heading" style="display: none"> Camera ID</th>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading" >Camera_IP</th>
                                                    <th class="heading" >Camera Type</th>
                                                    <th class="heading" >Camera Make</th>
                                                     <th class="heading" >Camera Model</th> 
                                                    <th class="heading" >Junction Name</th>
                                                    <th class="heading" >Side No</th>
                                                    <th class="heading" >Latitude</th>
                                                    <th class="heading" >Longitude</th>
                                                    <th class="heading" >CameraFacing</th>
                                                    <th class="heading" >LaneNo</th>
                                                    <th class="heading" >Remark</th>
                                                </tr>
                                                <c:forEach var="camera" items="${requestScope['cameraList']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${camera.camera_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.camera_ip}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.camera_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.camera_make}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.camera_model}</td>
                                                     
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.junction_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.side_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.latitude}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.longitude}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.camerafacing}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.lane_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${camera.remark}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="10">
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
                                        </div>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <form name="form3" method="POST" action="CameraCont" onsubmit="return verify()" enctype = "multipart/form-data" >
                                        <div STYLE="overflow: auto; width: 800px;padding:0px; margin: 0px">
                                            <table class="divv" border="1" border-color="blue">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>

                                                <tr>
                                                    <td style="display: none"></td>
                                                    <th class="heading">Camera_IP</th><td><input class="input" type="text" id="camera_ip" name="camera_ip" size="38" value="" disabled></td>
                                                    <th class="heading">Camera_Make</th><td><input class="input" type="text" id="camera_make" name="camera_make" size="38" value="" autocomplete="on" disabled></td>

                                                </tr>
                                                <tr>
                                                    <th class="heading">Camera_Model</th><td><input class="input" type="text" id="camera_mod" name="camera_mod" size="38" value="" autocomplete="on" disabled></td>
                                                    <th class="heading">Camera_Type</th><td><input class="input" type="text" id="camera_type" name="camera_type" size="38" value="" autocomplete="on" disabled></td>
                                                   
                                                    
                                                   
                                                </tr>
                                                 <tr>
                                                    <th class="heading">Junction Name</th><td><input class="input" type="text" id="junction_name" name="junction_name" size="38" value="${junction_name}" disabled></td>

                                                     <th class="heading">LaneNo</th><td><input class="input" type="number" id="lane_no" name="lane_no" size="38"  value=""  disabled></td>
                                                    
                                                </tr>
                                                <tr>
                                                    <th class="heading">Side_No</th><td><input class="input" type="text" id="side_no" name="side_no" size="38" value="${side_no}" disabled></td>
                                                     <th class="heading">Remark</th><td><input class="input" type="text" id="remark" name="remark" size="38" value="" disabled></td>

                                                </tr>
                                                <tr>
                                                    <th class="heading">Lattitude</th><td><input class="input" type="text" id="latitude" name="latitude" size="38" value="" disabled></td>
                                                     <th class="heading">Longitude</th><td><input class="input" type="text" id="longitude" name="longitude" size="38"  value="" disabled></td>

                                                </tr>
                                                <tr>
                                                    <th class="heading">Camera-Facing</th><td><input class="input" type="text" id="camera_facing" name="camera_facing" size="38"  value=""  disabled></td>
                                                    <th class="heading">Select Image</th><td><input class="input" type="file" id="file" name="file" size="38" value=""   style="height: 20px;" multiple></td>

                                                </tr>
                                                
                                                 
                                                <tr>
                                                    
                                                </tr>


                                                <tr id="rcorners4" align="center" class="reference" border-radius='15'>
                                                    <td align='center' colspan="6">
                                                        <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="button" name="getCoordinate" id="cordinate" value="GetCo-ordinate" onclick="openMapForCord()" >
                                                    </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" id="junction_id" name="junction_id"  value="${junction_id}">
                                                 <input type="hidden" id="side_detail_id" name="side_detail_id" value="" disabled>
                                                <input type="hidden" id="camera_id" name="camera_id" value="" disabled>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </div>
                                    </form>
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

