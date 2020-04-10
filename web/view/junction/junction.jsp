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
<!--<script type="text/javascript" src="JS/jquery-ui.min.js"></script>-->
<script type="text/javascript" language="javascript">
    jQuery(function () {
        $("#state_name").autocomplete("junctionCont", {
            extraParams: {
                action1: function () {
                    return "getStateName"
                }
            }
        });
        $("#city_name").autocomplete("junctionCont", {
            extraParams: {
                action1: function () {
                    return "getCityName"
                },
                action2: function () {
                    return document.getElementById("state_name").value;
                }
            }
        });
    });

    function makeEditable(id) {
        document.getElementById("junction_id").disabled = false;
        document.getElementById("junction_name").disabled = false;
        document.getElementById("address_1").disabled = false;
        document.getElementById("address_2").disabled = false;
        document.getElementById("state_name").disabled = false;
        document.getElementById("city_name").disabled = false;
        document.getElementById("controller_model").disabled = false;
        document.getElementById("no_of_sides").disabled = false;
        document.getElementById("amber_time").disabled = false;
        document.getElementById("flash_rate").disabled = false;
        document.getElementById("no_of_plans").disabled = false;
        document.getElementById("mobile_no").disabled = false;
        document.getElementById("sim_no").disabled = false;
        document.getElementById("imei_no").disabled = false;
        document.getElementById("instant_green_time").disabled = false;
        document.getElementById("pedestrian1").disabled = false;
        document.getElementById("pedestrian2").disabled = false;
        document.getElementById("pedestrian_time").disabled = false;
        document.getElementById("side_1_name").disabled = false;
        document.getElementById("side_2_name").disabled = false;
        document.getElementById("side_3_name").disabled = false;
        document.getElementById("side_4_name").disabled = false;
        document.getElementById("file_no").disabled = false;
        document.getElementById("remark").disabled = false;
          document.getElementById("bluetooth_address").disabled = false;
       
        //        document.getElementById("side_5_name").disabled = false;
        if (id == 'NEW') {
            $("#message").html('');
            document.getElementById("EDIT").disabled = true;
            document.getElementById("DELETE").disabled = true;
            document.getElementById("Save AS New").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 22);
            document.getElementById("junction_name").focus();
        }
        if (id == 'EDIT') {
            document.getElementById("Save AS New").disabled = false;
            document.getElementById("DELETE").disabled = false;
            document.getElementById("junction_name").focus();
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
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        //alert(noOfRowsTraversed);
        var noOfColumns = 24;
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
        document.getElementById("junction_id").value = document.getElementById("junction_id" + rowNo).value;
        document.getElementById("remark").value = document.getElementById("remark" + rowNo).value;
        document.getElementById("no_of_sides").value = document.getElementById("no_of_sides" + rowNo).value;
        document.getElementById("junction_name").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("address_1").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("address_2").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("state_name").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("city_name").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        document.getElementById("controller_model").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
        document.getElementById("amber_time").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("flash_rate").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
        document.getElementById("no_of_plans").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
        document.getElementById("mobile_no").value = document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
        document.getElementById("sim_no").value = document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
        document.getElementById("imei_no").value = document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
        document.getElementById("instant_green_time").value = document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
        var pedestrian = document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
        if (pedestrian == "YES") {
            document.getElementsByName('pedestrian')[1].checked = true;
        }
        document.getElementById("pedestrian_time").value = document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
        document.getElementById("side_1_name").value = document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
        document.getElementById("side_2_name").value = document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
        document.getElementById("side_3_name").value = document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
        document.getElementById("side_4_name").value = document.getElementById(t1id + (lowerLimit + 19)).innerHTML;
        document.getElementById("side_5_name").value = document.getElementById(t1id + (lowerLimit + 20)).innerHTML;
        document.getElementById("file_no").value = document.getElementById(t1id + (lowerLimit + 21)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 22)).innerHTML;
        document.getElementById("bluetooth_address").value = document.getElementById(t1id + (lowerLimit + 23)).innerHTML;
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

    var popupwin = null;
    function popup(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        popupwin = window.showModalDialog(url, window_name, "dialogWidth:" + popup_width + "px;" + " dialogHeight:" + popup_height + "px;" + " dialogLeft:" + popup_left_pos + "px;" + " dialogTop:" + popup_top_pos + "px;" + " resizable:no; center:yes");
        popupwin.focus()
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

    function ViewPlanInfo(junction_id, program_version_no, no_of_sides) {
        var queryString = "junction_id=" + junction_id + "&program_version_no=" + program_version_no + "&no_of_sides=" + no_of_sides;
        var url = "JunctionPlanMapCont?" + queryString;
        popupwin = openPopUp(url, "View PlanInfo ", 580, 900);
        //                var bodyElementRef = null;
        //                    var currentColor = '';
        //                    var elementArray = document.getElementsByTagName('BODY');
        //
        //                    if ( elementArray.length > 0 )
        //                    {
        //                        bodyElementRef = elementArray[0];
        //                        currentColor = bodyElementRef.style.backgroundColor;
        //                        bodyElementRef.style.backgroundColor = 'Yellow';
        //                    }

    }

    function ViewSlaveInfo(junction_id, program_version_no, no_of_sides) {
//        var queryString = "junction_id=" + junction_id + "&program_version_no=" + program_version_no + "&no_of_sides=" + no_of_sides;
//        var url = "slaveInfoCont?" + queryString;
//        popupwin = openPopUp(url, "View Slave Info ", 580, 900);
        debugger;
        document.getElementById("junction_id122").value = junction_id;
        document.getElementById("program_version_no122").value = program_version_no;
        document.getElementById("no_of_sides122").value = no_of_sides;
        document.forms['redirectSlave'].submit();

    }
    
    function ViewPhaseInfo(junction_id,junction_name) {
        debugger;
        document.getElementById("junction_id121").value = junction_id;
        document.getElementById("junction_name121").value = junction_name;
        
        document.forms['redirectPhase'].submit();
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
                            <tr><td><table class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <td>
                                                Junction Details
                                            </td>
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td>
                                    <div style="width: 990px;max-height: 340px;overflow: auto;margin-bottom: 20px; margin-top: 20px">
                                        <form name="form1" action="junctionCont" method="post">
                                            <table name="table1" border="1" width="100%" align="center" class="reference">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Junction ID</th>
                                                    <th class="heading"></th>
                                                    <th class="heading"></th>
                                                    <th class="heading">Junction Name</th>
                                                    <th class="heading">Address 1</th>
                                                    <th class="heading">Address 2</th>
                                                    <th class="heading">State Name</th>
                                                    <th class="heading">City Name</th>
                                                    <th class="heading">Controller Model</th>
                                                    <th class="heading">No of Sides</th>
                                                    <th class="heading">Amber Time</th>
                                                    <th class="heading">Flash Rate</th>
                                                    <th class="heading">No of Plans</th>
                                                    <th class="heading">Mobile No</th>
                                                    <th class="heading">SIM NO</th>
                                                    <th class="heading">IMEI NO</th>
                                                    <th class="heading">Instant Green Time</th>
                                                    <th class="heading">Pedestrian</th>
                                                    <th class="heading">Pedestrian Time</th>
                                                    <th class="heading">Side1 Name</th>
                                                    <th class="heading">Side2 Name</th>
                                                    <th class="heading">Side3 Name</th>
                                                    <th class="heading">Side4 Name</th>
                                                    <th class="heading">Side5 Name</th>
                                                    <th class="heading">File No</th>
                                                     <th class="heading">Remark</th>
                                                    
                                                      <th class="heading">Bluetooth Address</th>
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['junction']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="junction_id${loopCounter.count}" value="${list.junction_id}">
                                                            <input type="hidden" id="program_version_no${loopCounter.count}" value="${list.program_version_no}">
                                                            <input type="hidden" id="remark${loopCounter.count}" value="${list.remark}">
                                                            <input type="hidden" id="no_of_sides${loopCounter.count}" value="${list.no_of_sides}">
                                                            
                                                        </td>
                                                        <td>${list.junction_id}</td>
                                                        <td><a href="#" onclick="ViewPlanInfo('${list.junction_id}', '${list.program_version_no}', '${list.no_of_sides}');">View Plan Info</a></td>

                                                        <td><a href="#" onclick="ViewPhaseInfo('${list.junction_id}', '${list.junction_name}');" class="arrow">View Phase Info</a></td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.address1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.address2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.state_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.city_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.controller_model}</td>
                                                        <td>${list.no_of_sides}&nbsp;<a href="#" onclick="ViewSlaveInfo('${list.junction_id}', '${list.program_version_no}', '${list.no_of_sides}');">(View Slave Info)</a></td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.amber_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.flash_rate}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.no_of_plans}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.mobile_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.sim_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.imei_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.instant_green_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.pedestrian}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.pedestrian_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side1_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side2_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side3_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side4_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side5_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.file_no}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.remark}</td>
                                                          <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.bluetooth_address}</td>
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
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form></div>
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <div style="width: 990px;overflow: auto ">
                                        <form name="form"  action="junctionCont" method="post" onsubmit="return verify()">
                                            <table name="table" class="reference"  border="1" align="center">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="22" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr align="center">
                                                    <th class="heading">Junction Name</th>
                                                    <td>
                                                        <input class="input" type="hidden" id="junction_id" name="junction_id" value="" readonly>
                                                        <input class="input"  type="text" id="junction_name" name="junction_name" size="15" value="" disabled>
                                                    </td>
                                                    <th  class="heading">Address 1</th>
                                                    <td>
                                                        <input class="input"  type="text" id="address_1" name="address_1" value="" size="20"disabled><br>
                                                    </td>
                                                    <th  class="heading">Address 2</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="address_2" name="address_2" value="" disabled><br>
                                                    </td>

                                                </tr>

                                                <tr align="center">
                                                    <th  class="heading">State</th>
                                                    <td>
                                                        <input class="input"  size="15" type="text" id="state_name" name="state_name" value="Madhya Pradesh" disabled><br>
                                                    </td>
                                                    <th  class="heading">City Name</th>
                                                    <td>
                                                        <input class="input"  type="text" id="city_name" name="city_name" value="Jabalpur" disabled><br>
                                                    </td>
                                                    <th  class="heading">Controller Model</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="controller_model" name="controller_model" value="ATS 1" disabled><br>
                                                    </td>

                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">No of Sides</th>
                                                    <td>
                                                        <select class="select" id="no_of_sides" name="no_of_sides" onchange="setDropdownVisibility();" disabled>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4" selected>4</option>
                                                            <option value="5">5</option>
                                                        </select>
                                                    </td>
                                                    <th  class="heading">Amber Time(per seconds)</th>
                                                    <td>
                                                        <input class="input"  type="text" id="amber_time" name="amber_time" value="5" maxlength="1" disabled><br>
                                                    </td>
                                                    <th  class="heading">Flash Rate(per seconds)</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="flash_rate" name="flash_rate" value="1" maxlength="1" disabled><br>
                                                    </td>
                                                </tr><tr>
                                                    <th  class="heading">No of Plans</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="no_of_plans" name="no_of_plans" value="0" style="background-color: lightgray" maxlength="0" disabled readonly><br>
                                                    </td>
                                                    <th  class="heading">Mobile No</th>
                                                    <td>
                                                        <input class="input"  type="text" id="mobile_no" name="mobile_no" value="" maxlength="10" disabled><br>
                                                    </td>
                                                    <th  class="heading">SIM No</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="sim_no" name="sim_no" value="" disabled><br>
                                                    </td>
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">IMEI No</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="imei_no" name="imei_no" value="" maxlength="16" disabled><br>
                                                    </td>
                                                    <th  class="heading">Instant Green Time(per seconds)</th>
                                                    <td>
                                                        <input class="input"  type="text" id="instant_green_time" name="instant_green_time" value="30" maxlength="2" disabled><br>
                                                    </td>
                                                    <th  class="heading">Pedestrian</th>
                                                    <td id="pedestrian">
                                                        <input type="radio" id="pedestrian1" name="pedestrian" value="NO" checked disabled>NO
                                                        <input type="radio" id="pedestrian2" name="pedestrian" value="YES" disabled>YES
                                                        <!--                                    <input type="text" id="pedestrian" name="pedestrian" value="N" maxlength="1" disabled><br>-->
                                                    </td>

                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading">Pedestrian Time(per seconds)</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="pedestrian_time" name="pedestrian_time" value=""  maxlength="2" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 1 Name</th>
                                                    <td>
                                                        <input class="input"  type="text" id="side_1_name" name="side_1_name" value="" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 2 Name</th>
                                                    <td>
                                                        <input class="input" size="15"  type="text" id="side_2_name" name="side_2_name" value="" disabled><br>
                                                    </td>
                                                </tr><tr>
                                                    <th  class="heading">Side 3 Name</th>
                                                    <td>
                                                        <input class="input" size="15"  type="text" id="side_3_name" name="side_3_name" value="" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 4 Name</th>
                                                    <td>
                                                        <input class="input"  type="text" id="side_4_name" name="side_4_name" value="" disabled><br>
                                                    </td>
                                                    <th  class="heading">Side 5 Name</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="side_5_name" name="side_5_name" value="" style="background-color: lightgray" disabled><br>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th  class="heading">File No</th>
                                                    <td>
                                                        <input class="input" size="15" type="text" id="file_no" name="file_no" value="" disabled><br>
                                                    </td>
                                                    <th  class="heading">Remark</th>
                                                    <td>
                                                        <input class="input" size="20" type="text" id="remark" name="remark"  value="" disabled><br>
                                                    </td>
                                                     <th  class="heading">Bluetooth Address</th>
                                                    <td>
                                                        <input class="input" size="20" type="text" id="bluetooth_address" name="bluetooth_address"  value="" disabled><br>
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
        <form name="redirectPhase" method="post" action="phaseinfoCont" target="_blank">
            <input type="hidden" id="junction_id121" name="junction_id2" value="">
            <input type="hidden" id="junction_name121" name="junction_name212" value="">
        </form>
        <form name="redirectSlave" method="get" action="slaveInfoCont" target="_blank">
            <input type="hidden" id="junction_id122" name="junction_id122" value="">
            <input type="hidden" id="program_version_no122" name="program_version_no122" value="">
            <input type="hidden" id="no_of_sides122" name="no_of_sides122" value="">
        </form>

    </body>
</html>
