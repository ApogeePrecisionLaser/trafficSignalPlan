 <%--
    Document   : junction
    Created on : Aug 10, 2012, 9:33:33 AM
    Author     : prachi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">  
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
  
<!--<script src="../../JS/jquery.dataTables.js"></script>
	<script src="../../JS/dataTables.bootstrap.js"></script>-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.13/css/jquery.dataTables.css">
 
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css"> 
<script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/1.10.13/js/jquery.dataTables.js"></script>

 

<!--<script type="text/javascript" src="../../JS/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="../../JS/jquery.autocomplete.js"></script>
 -->
 
<!--<script type="text/javascript" src="JS/jquery-ui.min.js"></script>-->
<script>
		$(document).ready(function() {
			$('#tab6').dataTable();
		});
	</script>
<script type="text/javascript" language="javascript">
    
    function showdiv() {
        
       //gh();
      // form1.submit();
        var plus = document.getElementById("plus");
        var divbody = document.getElementById("body");
        var div = document.getElementById("jdt");
        var div1 = document.getElementById("t1");
         var divplan = document.getElementById("pform");
        //var div2=document.getElementById("jundetails");
        div1.style.display = 'block';
        div.style.display = 'block';
        divbody.style.display = 'block';
        plus.style.display = 'none';
        divplan.style.display = 'block';
        // div2.style.display='none';
 
    }
    function hidediv() {

        var div = document.getElementById("t1");

        div.style.display = 'none';
        var plus = document.getElementById("plus");
        plus.style.display = 'block';
        var divbody = document.getElementById("body");
        divbody.style.display = 'none';
    }
    function showdiv1() {
        debugger;

        var plus = document.getElementById("plus1");


        var div1 = document.getElementById("rbutton");
        div1.style.display = 'block';

        var divbody1 = document.getElementById("formPlan");
        divbody1.style.display = 'block';

        plus.style.display = 'none';

    }
    function hidediv1() {

        var div = document.getElementById("t1");
        var divbody1 = document.getElementById("formPlan");
        divbody1.style.display = 'none';
        div.style.display = 'none';
        var plus = document.getElementById("plus1");
        plus.style.display = 'block';
    }

    function showdiv2() {

        var plus = document.getElementById("plus2");

        var div = document.getElementById("bodyphase");
        //  var div1=document.getElementById("t1");
        //   div1.style.display = 'block';

        //    var divbody1=document.getElementById("formPlan");
        //      divbody1.style.display = 'block';
        div.style.display = 'block';
        plus.style.display = 'none';

    }
    function hidediv2() {

        var div = document.getElementById("bodyphase");
        //  var divbody1=document.getElementById("formPlan");
        //     divbody1.style.display = 'none';
        div.style.display = 'none';
        var plus = document.getElementById("plus2");
        plus.style.display = 'block';
    }

    function showdiv3() {

        var plus = document.getElementById("plus3");

        var div = document.getElementById("formPhasedetail1");
        //  var div1=document.getElementById("t1");
        //   div1.style.display = 'block';

        //    var divbody1=document.getElementById("formPlan");
        //      divbody1.style.display = 'block';
        div.style.display = 'block';
        plus.style.display = 'none';

    }
    function hidediv3() {

        var div = document.getElementById("formPhasedetail1");
        //  var divbody1=document.getElementById("formPlan");
        //     divbody1.style.display = 'none';
        div.style.display = 'none';
        var plus = document.getElementById("plus3");
        plus.style.display = 'block';
    }





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

            document.getElementById("junction_name").focus();
        }
        document.getElementById("SAVE").disabled = false;
    }

    function setStatus(id) {
        if (id == 'SAVE') {
            document.getElementById("clickedButton").value = "SAVE";
        } else if (id == 'junctionsave') {
            document.getElementById("clickedButton").value = "junctionsave";
        } else if (id == 'plandet') {
            document.getElementById("clickedButton").value = "plandet";
        } else if (id == 'finalsubmit') {
            document.getElementById("clickedButton").value = "finalsubmit";
        } else if (id == 'junctionplanmap') {
            document.getElementById("clickedButton").value = "junctionplanmap";
        } else if (id == 'DELETE') {
            document.getElementById("clickedButton").value = "DELETE";
        } else {
            document.getElementById("clickedButton").value = "Save AS New";
            ;
        }
    }

//    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
//        for (var i = 0; i < noOfRowsTraversed; i++) {
//            for (var j = 1; j <= noOfColumns; j++) {
//                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
//            }
//        }
//    }
    function fillColumns(id) {
        openjdetailsdiv();
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;

        var noOfColumns = 23;
        var columnId = id;
        debugger;
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
        debugger;
        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        //alert(lowerLimit);
        var t1id = "t1c";
        debugger;

        document.getElementById("junction_id").value = document.getElementById("junction_id" + rowNo).value;
        document.getElementById("remark").value = document.getElementById("remark" + rowNo).value;
        document.getElementById("no_of_sides").value = document.getElementById("no_of_sides" + rowNo).value;
        document.getElementById("junction_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("address_1").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("address_2").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("state_name").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        document.getElementById("city_name").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
        document.getElementById("controller_model").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("amber_time").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
        document.getElementById("flash_rate").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
        document.getElementById("no_of_plans").value = document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
        document.getElementById("mobile_no").value = document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
        document.getElementById("sim_no").value = document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
        document.getElementById("imei_no").value = document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
        document.getElementById("instant_green_time").value = document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
        var pedestrian = document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
        if (pedestrian == "YES") {
            document.getElementsByName('pedestrian')[1].checked = true;
        }
        document.getElementById("pedestrian_time").value = document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
        document.getElementById("side_1_name").value = document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
        document.getElementById("side_2_name").value = document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
        document.getElementById("side_3_name").value = document.getElementById(t1id + (lowerLimit + 19)).innerHTML;
        document.getElementById("side_4_name").value = document.getElementById(t1id + (lowerLimit + 20)).innerHTML;
        document.getElementById("side_5_name").value = document.getElementById(t1id + (lowerLimit + 21)).innerHTML;
        document.getElementById("file_no").value = document.getElementById(t1id + (lowerLimit + 22)).innerHTML;
        // Now enable/disable various buttons.

        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "yellowgreen";        // set the background color of clicked row to yellow.
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

    }
function insertTempData(id){
   // alert();
    // ajax call to insert data in temp tables
  $.ajax({url: "JunctionDetailsUpdate?task=inserttempdata",
            
            dataType: 'json',
           
             data: {id:id},

            success: function (response_data)
            {
          var status = response_data.status;
          var data = response_data.data;
          var listsize = response_data.listsize;
    alert("status"+status);
    
         
            }
        });

}
            success: function (response_data)
            {
          var status = response_data.status;
          var data = response_data.data;
          var listsize = response_data.listsize;
    alert("status"+status);
    
         
            }
        });

}
    function Openform(junction_id, program_version_no, no_of_sides) {
        debugger;
     //   alert(junction_id);
         insertTempData(junction_id);        
        var queryString = "task=SelectedJunctionPlans&junction_id_selected=" + junction_id + "&program_version_no=" + program_version_no + "&no_of_sides=" + no_of_sides;

        var url = "JunctionDetailsUpdate?" + queryString;

        window.location.href = url;
        var x = document.getElementById('formPlan');
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }
    function Openformphase(junction_plan_map_id, plan_no, junction_name, from_date, to_date, on_time_hr, off_time_hr, on_time_min, off_time_min, day, junction_id) {
        debugger;
        var queryString = "task=SelectedJunctionPhase&junction_plan_map_id_selected=" + junction_plan_map_id + "&plan_no=" + plan_no + "&junction_name=" + junction_name + "&from_date=" + from_date + "&to_date=" + to_date + "&on_time_hr=" + on_time_hr + "&off_time_hr=" + off_time_hr + "&on_time_min=" + on_time_min + "&off_time_min=" + off_time_min + "&day=" + day + "&junction_id=" + junction_id;

        var url = "JunctionDetailsUpdate?" + queryString;

        window.location.href = url;
        var x1 = document.getElementById('formPhase');
        var x2 = document.getElementById('formPhasedetail');
        if (x1.style.display === "block") {
            x1.style.display = "none";
        } else {
            x1.style.display = "block";
        }

        if (x2.style.display === "block") {
            x2.style.display = "none";
        } else {
            x2.style.display = "block";
        }
//           document.getElementById('form2').style.display = '';formPhasedetail


    }



    function Openformphase11(fdata1, tdata1, j_id,jpm_id) {
       
           hidediv1();
        $(".row2").remove();
        $(".row3").remove();
        debugger;
      
        var moduleHtml;
        // var queryString = "task=SelectedJunctionPhase&from_date=" + from_date + "&to_date=" + to_date + "&junction_id=" + j_id;
        $.ajax({url: "JunctionDetailsUpdate?task=plandetails",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: j_id, from_date: fdata1, to_date: tdata1, jun_plan_map_id:jpm_id},

            success: function (response_data)
            {
                debugger;
      var data = response_data.data;
                // alert("data js -" + data);
                // alert("data js -" + data[7]);
                // var jpm=response_data.jpm;
                // var j_id=response_data.j_id;
                // alert(jpm);
                // alert(j_id);
                var data_len = data.length / 19;
                var i = 0;
                // alert("data lemn --"+data_len);

                moduleHtml += '<tr class="row2"  >';
                moduleHtml += '<th class="heading">Edit</th>';
                moduleHtml += '<th class="heading">Plan No</th>';
                moduleHtml += '<th class="heading">On Time </th>';
                // moduleHtml += '<th class="heading">On Time Min</th>';
                moduleHtml += '<th class="heading">Off Time </th>';
                //    moduleHtml += '<th class="heading">Off Time Min</th>';
                moduleHtml += '<th class="heading">Mode</th>';
                moduleHtml += '<th class="heading">Green Time Side 1</th>';
                moduleHtml += '<th class="heading">Green Time Side 2</th>';
                moduleHtml += '<th class="heading">Green Time Side 3</th>';
                moduleHtml += '<th class="heading">Green Time Side 4</th>';
                moduleHtml += '<th class="heading">Green Time Side 5</th>';
                moduleHtml += ' <th class="heading">Amber Time Side 1</th>';
                moduleHtml += '<th class="heading">Amber Time Side 2</th>';
                moduleHtml += ' <th class="heading">Amber Time Side 3</th>';
                moduleHtml += '   <th class="heading">Amber Time Side 4</th>';
                moduleHtml += '<th class="heading">Amber Time Side 5</th>';
                moduleHtml += ' <th class="heading">Transferred Status</th>';
                moduleHtml += ' <th class="heading">Remark</th>';
                moduleHtml += ' <th class="heading">TotalPhase</th>';


                moduleHtml += '</tr>'

                for (var j = 0; j < data_len; j++) {
                    debugger;
                    moduleHtml += '<tr class="row3"  >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs11' + j + '" name="rb2" onclick="fillColumns2(id)"  value=""></td>';
                    moduleHtml += '<td id="abc" class="dateviewdata" onclick="fillColumns2(id)">' + data[i] + '</td>';
                    //  alert(i);   
                    var fdata = "'" + data[i] + "'";
                    // alert(faaaaaaaaaaaaaaaaaaaaaaaaaaaaaromdata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + ':' + data[++i] + '</td>';
                    var tdata = "'" + data[i] + "'";

                    //  alert(i);
                    //  alert(todata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + ':' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    //moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';

                    moduleHtml += '<td><input type="button" value="View Phases" id="pdet" onclick="OpenformphasedataView(' + fdata + ',' + j_id + ')"></td>';

  //  moduleHtml += '<td><input type="button" value="PlanDetails" id="pdet" onclick="Openformphase11(' + fdata + ',' + tdata + ',' + j_id + ')"></td>';


                    moduleHtml += '</tr>'
                    //  var r=document.getElementById("tlcs1").value;
                    //   alert(r);
                    i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                }

                $("#tab4").append(moduleHtml);
                //$(".DateDayClass").append(moduleHtml);
                var a = document.getElementById("foot");
                $("#tab4").append(a);
            }
        });
    }


    function Openformphase12(fdata1, j_id,jpm_id) {
        
           hidediv1();
        $(".row2").remove();
        $(".row3").remove();
        debugger;
        var moduleHtml;
        // var queryString = "task=SelectedJunctionPhase&from_date=" + from_date + "&to_date=" + to_date + "&junction_id=" + j_id;
        $.ajax({url: "JunctionDetailsUpdate?task=plandetailsday",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: j_id, day: fdata1, jun_plan_map_id:jpm_id},

            success: function (response_data)
            {
                debugger;
      var data = response_data.data;
                // alert("data js -" + data);
                // alert("data js -" + data[7]);
                // var jpm=response_data.jpm;
                // var j_id=response_data.j_id;
                // alert(jpm);
                // alert(j_id);
                var data_len = data.length / 19;
                var i = 0;
                // alert("data lemn --"+data_len);

                moduleHtml += '<tr class="row2"  >';
                moduleHtml += '<th class="heading">Edit</th>';
                moduleHtml += '<th class="heading">Plan No</th>';
                moduleHtml += '<th class="heading">On Time </th>';
                // moduleHtml += '<th class="heading">On Time Min</th>';
                moduleHtml += '<th class="heading">Off Time </th>';
                //    moduleHtml += '<th class="heading">Off Time Min</th>';
                moduleHtml += '<th class="heading">Mode</th>';
                moduleHtml += '<th class="heading">Green Time Side 1</th>';
                moduleHtml += '<th class="heading">Green Time Side 2</th>';
                moduleHtml += '<th class="heading">Green Time Side 3</th>';
                moduleHtml += '<th class="heading">Green Time Side 4</th>';
                moduleHtml += '<th class="heading">Green Time Side 5</th>';
                moduleHtml += ' <th class="heading">Amber Time Side 1</th>';
                moduleHtml += '<th class="heading">Amber Time Side 2</th>';
                moduleHtml += ' <th class="heading">Amber Time Side 3</th>';
                moduleHtml += '   <th class="heading">Amber Time Side 4</th>';
                moduleHtml += '<th class="heading">Amber Time Side 5</th>';
                moduleHtml += ' <th class="heading">Transferred Status</th>';
                moduleHtml += ' <th class="heading">Remark</th>';
                moduleHtml += ' <th class="heading">TotalPhase</th>';


                moduleHtml += '</tr>'

                for (var j = 0; j < data_len; j++) {
                    debugger;
                    moduleHtml += '<tr class="row3"  >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs11' + j + '" name="rb2" onclick="fillColumns2(id)"  value=""></td>';
                    moduleHtml += '<td id="abc" class="dateviewdata" onclick="fillColumns2(id)">' + data[i] + '</td>';
                    //  alert(i);   
                    var fdata = "'" + data[i] + "'";
                    // alert(fromdata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + ':' + data[++i] + '</td>';
                    var tdata = "'" + data[i] + "'";

                    //  alert(i);
                    //  alert(todata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + ':' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    //moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';

                    moduleHtml += '<td><input type="button" value="View Phases" id="pdet" onclick="OpenformphasedataView(' + fdata + ',' + j_id + ')"></td>';


                    //  moduleHtml += '<td><input type="button" value="PlanDetails" id="pdet" onclick="Openformphase11(' + fdata + ',' + tdata + ',' + j_id + ')"></td>';


                    moduleHtml += '</tr>'
                    //  var r=document.getElementById("tlcs1").value;
                    //   alert(r);
                    i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                }

                $("#tab4").append(moduleHtml);
                //$(".DateDayClass").append(moduleHtml);
                var a = document.getElementById("foot");
                $("#tab4").append(a);
            }
        });
    }


    function Openformphase13(j_id, p_id,jpm_id) {
       debugger;
       // alert(j_id, p_id);
      //  alert(jpm_id);
        hidediv1();
        $(".row2").remove();
        $(".row3").remove();
        debugger;
        var moduleHtml;
        // var queryString = "task=SelectedJunctionPhase&from_date=" + from_date + "&to_date=" + to_date + "&junction_id=" + j_id;
        $.ajax({url: "JunctionDetailsUpdate?task=plandetailsnormal",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: j_id, plan_id: p_id, jun_plan_map_id:jpm_id},

            success: function (response_data)
            {
                debugger;

                var data = response_data.data;
                // alert("data js -" + data);
                // alert("data js -" + data[7]);
                // var jpm=response_data.jpm;
                // var j_id=response_data.j_id;
                // alert(jpm);
                // alert(j_id);
                var data_len = data.length / 19;
                var i = 0;
                // alert("data lemn --"+data_len);

                moduleHtml += '<tr class="row2"  >';
                moduleHtml += '<th class="heading">Edit</th>';
                moduleHtml += '<th class="heading">Plan No</th>';
                moduleHtml += '<th class="heading">On Time </th>';
                // moduleHtml += '<th class="heading">On Time Min</th>';
                moduleHtml += '<th class="heading">Off Time </th>';
                //    moduleHtml += '<th class="heading">Off Time Min</th>';
                moduleHtml += '<th class="heading">Mode</th>';
                moduleHtml += '<th class="heading">Green Time Side 1</th>';
                moduleHtml += '<th class="heading">Green Time Side 2</th>';
                moduleHtml += '<th class="heading">Green Time Side 3</th>';
                moduleHtml += '<th class="heading">Green Time Side 4</th>';
                moduleHtml += '<th class="heading">Green Time Side 5</th>';
                moduleHtml += ' <th class="heading">Amber Time Side 1</th>';
                moduleHtml += '<th class="heading">Amber Time Side 2</th>';
                moduleHtml += ' <th class="heading">Amber Time Side 3</th>';
                moduleHtml += '   <th class="heading">Amber Time Side 4</th>';
                moduleHtml += '<th class="heading">Amber Time Side 5</th>';
                moduleHtml += ' <th class="heading">Transferred Status</th>';
                moduleHtml += ' <th class="heading">Remark</th>';
                moduleHtml += ' <th class="heading">TotalPhase</th>';


                moduleHtml += '</tr>'

                for (var j = 0; j < data_len; j++) {
                    debugger;
                    moduleHtml += '<tr class="row3"  >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs11' + j + '" name="rb2" onclick="fillColumns2(id)"  value=""></td>';
                    moduleHtml += '<td id="abc" class="dateviewdata" onclick="fillColumns2(id)">' + data[i] + '</td>';
                    //  alert(i);   
                    var fdata = "'" + data[i] + "'";
                    // alert(fromdata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + ':' + data[++i] + '</td>';
                    var tdata = "'" + data[i] + "'";

                    //  alert(i);
                    //  alert(todata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + ':' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    //moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';

                    moduleHtml += '<td><input type="button" value="View Phases" id="pdet" onclick="OpenformphasedataView(' + fdata + ',' + j_id + ')"></td>';


                    moduleHtml += '</tr>'
                    //  var r=document.getElementById("tlcs1").value;
                    //   alert(r);
                    i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                }

                $("#tab4").append(moduleHtml);
                //$(".DateDayClass").append(moduleHtml);
                var a = document.getElementById("foot");
                $("#tab4").append(a);
            }
        });
    }



    function OpenformphasedataView(p_id, j_id) {
       hidediv2();
        $(".row5").remove();
        $(".row6").remove();
         $(".row7").remove();
        debugger;
        var moduleHtml;
        // var queryString = "task=SelectedJunctionPhase&from_date=" + from_date + "&to_date=" + to_date + "&junction_id=" + j_id;
        $.ajax({url: "JunctionDetailsUpdate?task=phasedataviewdetails",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: j_id, plan_id: p_id},

            success: function (response_data)
            {
                debugger;
                var no = response_data.no_of_sides;
                var plan_id = response_data.plan_id;
               // alert(no);
                if (no === "4") {
                    // alert("hi");
                    var data = response_data.data;
                    // alert("data js -" + data);
                    // alert("data js -" + data[7]);
                    // var jpm=response_data.jpm;
                    // var j_id=response_data.j_id;
                    // alert(jpm);
                    // alert(j_id);
                    //  alert(response_data.no_of_sides);
                    var data_len = data.length / 37;
                    var i = 0;
                    // alert("data lemn --"+data_len);

                    moduleHtml += '<tr class="row5"  >';
                  //  moduleHtml += '<th class="heading">Edit</th>';
                  //  moduleHtml += '<th class="heading">phase_info_id</th>';
                    moduleHtml += '<th class="heading">JunctionName </th>';


                   // moduleHtml += '<th class="heading">phase_no</th>';
                    moduleHtml += '<th class="heading">Order_no</th>';

                    moduleHtml += ' <th class="heading">Side1</th>';
                    moduleHtml += ' <th class="heading">Side2</th>';
                    moduleHtml += ' <th class="heading">Side3</th>';
                    moduleHtml += ' <th class="heading">Side4</th>';


                    moduleHtml += '</tr>'
                    moduleHtml += '<tr class="row7" id="'+j+'"  >';
                         moduleHtml += '<td width="35%"><input type="button" class="" id="1" name="rb11" value="Edit" onclick="myFunction2(id)"></td>';
                        moduleHtml += '</tr>'
                    for (var j = 0; j < data_len; j++) {
                     //   alert(j + "    jj");
                    //    alert(i + "    1");
                        debugger;
                       
                
                        moduleHtml += '<tr class="row6" id="'+j+'"  >';
                         var phaseinfoid = "'" + data[i] + "'";
                        var tdata = "'" + data[i] + "'";

                        moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                        var jun_name="'" + data[i] + "'";
                        moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)" style="display:none">' + data[++i] + '</td>';
                        var phase_no="'" + data[i] + "'";
                         moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                        var Order_no="'" + data[i] + "'";
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side1R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side1Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side1G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side1G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side1G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side2R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side2Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side2G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side2G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side2G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side3R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side3Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side3G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side3G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side3G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side4R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side4Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side4G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side4G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side4G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side1R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)" ><label>Y</label><input type="checkbox" class="preference" id="side1Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>G1</label><input type="checkbox" class="preference" id="side1G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side1G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side1G3' + j + '" name="rb1"   value="' + data[++i] + '"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side2R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>Y</label><input type="checkbox" class="preference" id="side2Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>G1</label><input type="checkbox" class="preference" id="side2G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side2G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side2G3' + j + '" name="rb1"   value="' + data[++i] + '"  > </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side3R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>Y</label><input type="checkbox" class="preference" id="side3Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>G1</label><input type="checkbox" class="preference" id="side3G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side3G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side3G3' + j + '" name="rb1"   value="' + data[++i] + '" > </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side4R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)" ><label>Y</label><input type="checkbox" class="preference" id="side4Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>G1</label><input type="checkbox" class="preference" id="side4G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side4G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side4G3' + j + '" name="rb1"   value="' + data[++i] + '"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        
                      //  moduleHtml += '<td width="35%"><input type="button" id="Button1" class="button1" value="Demo" onclick="reset()"></td>';
                      //   moduleHtml += '<td width="35%"><input type="button" class="" id="1" name="rb11" value="Edit" onclick="myFunction2(id)"></td>';
                       moduleHtml += '<td width="35%"><input type="button" class="" id="t1cs" name="rb1" value="SavePhase" onclick="CheckPhase('+jun_name+',id,'+phase_no+','+plan_id+','+Order_no+','+phaseinfoid+')"></td>';
                        moduleHtml += '</tr>'
                       
                        i++;
                     
                    }

                    $("#tab5").append(moduleHtml);
              

  
                  
                        $('input[type=checkbox]').each(function () {
                            var val = $(this).val();
                       //  alert("check box val -" + val);
                       //  alert("Id: " + $(this).attr("id") );
                            if (val == 0) {

                            } else {
                                $(this).attr('checked', true);
                            }
                        });
                         
                    
                } else if (no === "3") {
                   // alert("hi");
                    var data = response_data.data;
                    // alert("data js -" + data);
                    // alert("data js -" + data[7]);
                    // var jpm=response_data.jpm;
                    // var j_id=response_data.j_id;
                    // alert(jpm);
                    // alert(j_id);
                    //  alert(response_data.no_of_sides);
                    var data_len = data.length / 37;
                    var i = 0;
                    // alert("data lemn --"+data_len);

                    moduleHtml += '<tr class="row5"  >';
                  //  moduleHtml += '<th class="heading">Edit</th>';
                  //  moduleHtml += '<th class="heading">phase_info_id</th>';
                    moduleHtml += '<th class="heading">JunctionName </th>';


                   // moduleHtml += '<th class="heading">phase_no</th>';
                    moduleHtml += '<th class="heading">Order_no</th>';

                    moduleHtml += ' <th class="heading">Side1</th>';
                    moduleHtml += ' <th class="heading">Side2</th>';
                    moduleHtml += ' <th class="heading">Side3</th>';
                  //  moduleHtml += ' <th class="heading">Side4</th>';


                    moduleHtml += '</tr>'
                    moduleHtml += '<tr class="row7" id="'+j+'"  >';
                         moduleHtml += '<td width="35%"><input type="button" class="" id="1" name="rb11" value="Edit" onclick="myFunction2(id)"></td>';
                        moduleHtml += '</tr>'
                    for (var j = 0; j < data_len; j++) {
                     //   alert(j + "    jj");
                    //    alert(i + "    1");
                        debugger;
                       
                
                        moduleHtml += '<tr class="row6" id="'+j+'"  >';
                         var phaseinfoid = "'" + data[i] + "'";
                        var tdata = "'" + data[i] + "'";

                        moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                        var jun_name="'" + data[i] + "'";
                        moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)" style="display:none">' + data[++i] + '</td>';
                        var phase_no="'" + data[i] + "'";
                         moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                        var Order_no="'" + data[i] + "'";
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side1R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side1Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side1G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side1G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side1G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side2R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side2Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side2G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side2G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side2G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side3R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side3Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side3G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side3G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side3G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side4R' + j + '" name="rb1" value="' + data[++i] + '" onclick="myFunction1(id)" ><label>Y</label><input type="checkbox" class="preference" id="side4Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G1</label><input type="checkbox" class="preference" id="side4G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction1(id)"><label>G2</lable><input type="checkbox" class="preference" id="side4G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction1(id)"><label>G3</lable><input type="checkbox" class="preference" id="side4G3' + j + '" name="rb1"   value="' + data[++i] + '"  onclick="myFunction1(id)"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
//                        
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side1R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>Y</label><input type="checkbox" class="preference" id="side1Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)" ><label>G1</label><input type="checkbox" class="preference" id="side1G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side1G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side1G3' + j + '" name="rb1"   value="' + data[++i] + '"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side2R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>Y</label><input type="checkbox" class="preference" id="side2Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)" ><label>G1</label><input type="checkbox" class="preference" id="side2G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side2G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side2G3' + j + '" name="rb1"   value="' + data[++i] + '"  > </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        moduleHtml += '<td width="35%"><label>R</label><input type="checkbox" class="preference" id="side3R' + j + '" name="rb1" value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>Y</label><input type="checkbox" class="preference" id="side3Y' + j + '" name="rb1"    value="' + data[++i] + '" onclick="checkboxvalueonclick(id)"><label>G1</label><input type="checkbox" class="preference" id="side3G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side3G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side3G3' + j + '" name="rb1"   value="' + data[++i] + '" > </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        moduleHtml += '<td style="display:none" width="35%"><label>R</label><input type="checkbox" class="preference" id="side4R' + j + '" name="rb1" value="' + data[++i] + '"  ><label>Y</label><input type="checkbox" class="preference" id="side4Y' + j + '" name="rb1"    value="' + data[++i] + '" ><label>G1</label><input type="checkbox" class="preference" id="side4G1' + j + '" name="rb1"    value="' + data[++i] + '" onclick="myFunction3(id)"><label>G2</lable><input type="checkbox" class="preference" id="side4G2' + j + '" name="rb1"   value="' + data[++i] + '" onclick="myFunction3(id)"><label>G3</lable><input type="checkbox" class="preference" id="side4G3' + j + '" name="rb1"   value="' + data[++i] + '"> </br><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '"  ><input type="hidden" class="preference" id="t1cs" name="rb1" onclick="fillColumns1(id)"  value="' + data[++i] + '" disabled=""><input type="hidden" class="preference" id="t1cs" name="rb1"   value="' + data[++i] + '" disabled=""></td>'; 
                        
                      //  moduleHtml += '<td width="35%"><input type="button" id="Button1" class="button1" value="Demo" onclick="reset()"></td>';
                      //   moduleHtml += '<td width="35%"><input type="button" class="" id="1" name="rb11" value="Edit" onclick="myFunction2(id)"></td>';
                       moduleHtml += '<td width="35%"><input type="button" class="" id="t1cs" name="rb1" value="SavePhase" onclick="CheckPhase('+jun_name+',id,'+phase_no+','+plan_id+','+Order_no+','+phaseinfoid+')"></td>';
                        moduleHtml += '</tr>'
                       
                        i++;
                     
                    }

                    $("#tab5").append(moduleHtml);
              

  
                  
                        $('input[type=checkbox]').each(function () {
                            var val = $(this).val();
                       //  alert("check box val -" + val);
                       //  alert("Id: " + $(this).attr("id") );
                            if (val == 0) {

                            } else {
                                $(this).attr('checked', true);
                            }
                        });
                         
                    
                }
                //else{
//    
//}
            }
        });
    }
    
    function myFunction3(id) {
        debugger;
         
        $.ajax({url: "JunctionDetailsUpdate?task=validatephasedata",
            
            dataType: 'json',
           
            data: {id:id},

            success: function (response_data)
            {
          var data = response_data.data;
            var laterid = response_data.id;
            var finalid = response_data.finalid;
          var Sa = response_data.Sa;
          var Sb = response_data.Sb;
          var Sc = response_data.Sc;
          var Sa1 = response_data.Sa1;
          var Sb1 = response_data.Sb1;
          var Sc1 = response_data.Sc1;
        
       // alert( finalid);
//          alert(Sa);
//          alert(Sa);
//          alert(Sb);
//          alert(Sc);
//          alert(Sa1);
//          alert(Sb1);
//          alert(Sc1);
         document.getElementById(laterid).value="1";
          if(document.getElementById(finalid)!==null){
          document.getElementById(finalid).value="1";
     }
         
         document.getElementById(Sa).value="1";
         document.getElementById(Sb).value="1";
         document.getElementById(Sc).value="1";
         if(document.getElementById(Sa1)!==null){
         document.getElementById(Sa1).value="1";
     }
        
         if(document.getElementById(Sb1)!==null){
         document.getElementById(Sb1).value="1";
     }
      if(document.getElementById(Sc1)!==null){
          document.getElementById(Sc1).value="1";
     }
        
        
          $('input[type=checkbox]').each(function () {
                            var val = $(this).val();
                           // alert("check box val -" + val);
                            if (val == 0) {

                            } else {
                                $(this).attr('checked', true);
                              //  $(this).parent().addClass("redBackground"); 
                                //$(this).css("background-color", "#808080");
                            }
                        });
         
         
//                    if(data===0){
//                        alert("No Match Found!!!!Are You Want To Insert New Record");
//                    }else{
//                          alert("Match Found!!!!Are You Want To Update  Record");
//                    }
            }
        });
        
        }
    function CheckPhase(j_name,id,phase_no,plan_id,Order_no,phaseinfoid) {
        debugger;
        //alert("phase_no"+phase_no);
    // alert("plan_id"+plan_id);
    // alert("j_name"+j_name);
     // alert("id"+id);
     // alert(document.getElementById("side1R0").value);
      var s1data;
      var s2data;
      var s3data;
      var s4data;
         
        var rowid;
         
       $('#tab5 tr').click(function() {
        rowid = $(this).attr('id'); // table row ID 
      //  alert("row===="+rowid);
     
    if(rowid===rowid){
        var i=rowid;
       // alert("iiiiiiiiiiii"+i);
        var s1r=document.getElementById("side1R"+i).value;
        var s1y=document.getElementById("side1Y"+i).value;
        var s1g1=document.getElementById("side1G1"+i).value;
        var s1g2=document.getElementById("side1G2"+i).value;
        var s2r=document.getElementById("side2R"+i).value;
        var s2y=document.getElementById("side2Y"+i).value;
        var s2g1=document.getElementById("side2G1"+i).value;
        var s2g2=document.getElementById("side2G2"+i).value;
        var s3r=document.getElementById("side3R"+i).value;
        var s3y=document.getElementById("side3Y"+i).value;
        var s3g1=document.getElementById("side3G1"+i).value;
        var s3g2=document.getElementById("side3G2"+i).value;
        var s4r=document.getElementById("side4R"+i).value;
        var s4y=document.getElementById("side4Y"+i).value;
        var s4g1=document.getElementById("side4G1"+i).value;
        var s4g2=document.getElementById("side4G2"+i).value;
       // alert("-------"+s1r+"--- ----"+s1y+"--- ----"+s1g1+"--- ----"+s1g2);
         s1data=s1r.concat(s1y).concat(s1g1).concat(s1g2);
          s2data=s2r.concat(s2y).concat(s2g1).concat(s2g2);
          s3data=s3r.concat(s3y).concat(s3g1).concat(s3g2);
          s4data=s4r.concat(s4y).concat(s4g1).concat(s4g2);
      //  alert("s1data"+s1data);
     // alert("s2data"+s2data);
    //     alert("s3data"+s3data);
    //   alert("s4data"+s4data);
    }
  //  alert(looplength+"  looplength");
  //  alert(finallooplength+"  looplength");
  var count=0;
      
        $('input[type=checkbox]').each(function () {
            count++;
            debugger;
          
            if($(this).is(':checked')){
               
            }else{
              
            }
          
          // } 
        });
         
 
        

        
        $.ajax({url: "JunctionDetailsUpdate?task=checkphasedata",
            
            dataType: 'json',
           
            data: {side1: s1data, side2: s2data,side3:s3data,side4:s4data,junction_names:j_name,phase_no:phase_no,plan_id:plan_id,Order_no:Order_no,phaseinfoid:phaseinfoid},

            success: function (response_data)
            {
          var data = response_data.data;
                    if(data===0){
                        alert("No Match Found!!!!Are You Want To Insert New Record");
                        alert("---Please Save Next Phase if Available--- ");
                    }else{
                          alert("Match Found!!!!Are You Want To Update  Record");
                          alert("---Please Save Next Phase if Available--- ");
                    }
            }
        });
         });
        }
 
function checkboxvalueonclick(id){
      //  alert("hi");
        $('input[id='+id+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                               this.value = this.checked ? 1 : 0;
                                
                        });
    //
    }
 function myFunction1(id) {
      debugger;
     var rowid;
        // alert(id);
        // this.value = this.checked ? 1 : 0;
           $('#tab5 tr').click(function() {
          rowid = $(this).attr('id'); // table row ID 
       
        var side=id.substring(0, 5);
         // alert(side);
        rowid=rowid+1;
         for(var i=0; i<rowid;i++){
             
         if(side==="side1"){
             var ckbox=id.substring(5, 7);
             // alert("ckbox     "+ckbox);
            //
             if(ckbox==="R"+i){
             
                  $('input[id=side2Y'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                               this.value = this.checked ? 1 : 0;
                                
                        });
             }
               if(ckbox==="Y"+i){
                     
                  $('input[id=side2G1'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(id.substring(5, 8)==="G1"+i){
                //   alert("ckbox hhhhhhhhhhhhhh    "+ckbox);
            
                  $('input[id=side2G2'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(id.substring(5, 8)==="G2"+i){
                  
                  $('input[id=side2R'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
              
         }else if(side==="side2"){
              var ckbox=id.substring(5, 7);
             if(ckbox==="R"+i){
                  
                  $('input[id=side3Y'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(ckbox==="Y"+i){
                  $('input[id=side3G1'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(id.substring(5, 8)==="G1"+i){
                  $('input[id=side3G2'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(id.substring(5, 8)==="G2"+i){
                  $('input[id=side3R'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
            
         }else if(side==="side3"){
              var ckbox=id.substring(5, 7);
             if(ckbox==="R"+i){
                  $('input[id=side4Y'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(ckbox==="Y"+i){
                  $('input[id=side4G1'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(id.substring(5, 8)==="G1"+i){
                  $('input[id=side4G2'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
               if(id.substring(5, 8)==="G2"+i){
                  $('input[id=side4R'+i+']').each(function () {
                            
                            
                                $(this).attr('checked', true);
                             this.value = this.checked ? 1 : 0;
                        });
             }
             
         }else if(side==="side4"){
             
         }else {
             
         }
     }
      });
//       if( $( this ).attr( 'type' ) === 'checkbox' ) {
//    value = +$(this).is( ':checked' );
//}


   $('input[type=checkbox]').each(function () {
                            var val = $(this).val();
                       //  alert("check box val -" + val);
                       //  alert("Id: " + $(this).attr("id") );
                            if ( $(this).is(':checked')) {
                                $(this).attr('value', this.checked ? 1 : 0)
                            }
                        });
       $('input[type="checkbox"]').on('change', function(){
          // onclick="$(this).attr('value', this.checked ? 1 : 0)";
  
    this.value ^= 1;
});
    }
   
 function myFunction2(id) {
    // var rowid;
     
      // alert("hi");
       // $('input:checkbox').removeAttr('checked');
         var rowcount = $('#tab5 tr').length;
         rowcount=rowcount-4;
      //  alert("No. Of Rows ::" +rowcount);
     
           for(var i=0;i<rowcount;i++){
              // alert(i);
           document.getElementById("side1R"+i).value="0";
         document.getElementById("side1Y"+i).value="0";
         document.getElementById("side1G1"+i).value="0";
        
         document.getElementById("side1G2"+i).value="0";
         document.getElementById("side2R"+i).value="0";
         document.getElementById("side2Y"+i).value="0";
        document.getElementById("side2G1"+i).value="0";
         document.getElementById("side2G2"+i).value="0";
         document.getElementById("side3R"+i).value="0";
         document.getElementById("side3Y"+i).value="0";
         document.getElementById("side3G1"+i).value="0";
        document.getElementById("side3G2"+i).value="0";
         document.getElementById("side4R"+i).value="0";
         document.getElementById("side4Y"+i).value="0";
        document.getElementById("side4G1"+i).value="0";
         document.getElementById("side4G2"+i).value="0";
               
           }
          
         
         $('input:checkbox').removeAttr('checked');
        document.getElementById("1").style.display='none';
         
        // onclick="$(this).attr('value', this.checked ? 1 : 0)"
    }
    
  function editable(id){
      debugger;
       var ids= "t1cs"+id ; 
       
       document.getElementById("ids").disabled=false;
        alert("okk");
  }
    
    function fillColumns1(id) {
        openjunctionplandetails();
        debugger;
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;

        // debugger;
        var noOfColumns = 10;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(4, id.length);
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

        // debugger;
        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        //alert(lowerLimit);
        var t1id1 = "t1cs";

        document.getElementById("junction_name1").value = document.getElementById(t1id1 + (lowerLimit + 3)).innerHTML;
        document.getElementById("start_time").value = document.getElementById(t1id1 + (lowerLimit + 6)).innerHTML;
        document.getElementById("order_no").value = document.getElementById(t1id1 + (lowerLimit + 7)).innerHTML;
        document.getElementById("day").value = document.getElementById(t1id1 + (lowerLimit + 6)).innerHTML;
        document.getElementById("date").value = document.getElementById(t1id1 + (lowerLimit + 5)).innerHTML;
        document.getElementById("junction_plan_map_id").value = document.getElementById(t1id1 + (lowerLimit + 0)).innerHTML;

        for (var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id1 + (lower + i)).bgColor = "yellowgreen";
            // set the background color of clicked row to yellow.
        }
        document.getElementById("EDIT").disabled = false;
        if (!document.getElementById("SAVE").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("DELETE").disabled = false;
            document.getElementById("NEW").disabled = false;
        }
        $("#message").html('');
    }


    function makeEditable1(id) {

        document.getElementById("junction_name1").disabled = false;
        document.getElementById("start_time").disabled = false;
        document.getElementById("order_no").disabled = false;
        // document.getElementById("day").disabled = false;
        document.getElementById("date").disabled = false;

//        if (id == 'new') {
//            $("#message").html('');
//            document.getElementById("junctionplanmap").disabled = false;
//            document.getElementById("edit").disabled = true;
//            document.getElementById("delete").disabled = true;
//            document.getElementById("save_As").disabled = true;
//           
//            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 22);
//            document.getElementById("junction_name").focus();
//        }
        if (id == 'edit') {
            document.getElementById("junctionplanmap").disabled = false;
            document.getElementById("DELETE").disabled = false;
            document.getElementById("junctionplanmap").disabled = false;
            document.getElementById("new").disabled = true;
            document.getElementById("junction_name").focus();
        }
        document.getElementById("junctionplanmap").disabled = false;
    }




    function fillColumns2(id) {
        debugger;
        openplandet();
      
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
               noOfRowsTraversed=1;
        debugger;
        var noOfColumns = 19;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(4, id.length);
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

        // debugger;
       // setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        //alert(lowerLimit);
        var t1id12 = "t1cs11";
        document.getElementById("plan_no").value = document.getElementById(t1id12 + (lower + 2)).innerHTML;
        //  document.getElementById("plan_id").value = document.getElementById(t1id12 + (lower + 3)).innerHTML;
        document.getElementById("on_time_hour").value = document.getElementById(t1id12 + (lower + 3)).innerHTML;
        document.getElementById("on_time_min").value = document.getElementById(t1id12 + (lower + 4)).innerHTML;
        document.getElementById("off_time_hour").value = document.getElementById(t1id12 + (lower + 5)).innerHTML;
        document.getElementById("off_time_min").value = document.getElementById(t1id12 + (lower + 6)).innerHTML;
        document.getElementById("mode").value = document.getElementById(t1id12 + (lower + 7)).innerHTML;
        document.getElementById("side1_green_time").value = document.getElementById(t1id12 + (lower + 8)).innerHTML;
        document.getElementById("side2_green_time").value = document.getElementById(t1id12 + (lower + 9)).innerHTML;
        document.getElementById("side3_green_time").value = document.getElementById(t1id12 + (lower + 10)).innerHTML;
        document.getElementById("side4_green_time").value = document.getElementById(t1id12 + (lower + 11)).innerHTML;
        document.getElementById("side5_green_time").value = document.getElementById(t1id12 + (lower + 12)).innerHTML;
        document.getElementById("side1_amber_time").value = document.getElementById(t1id12 + (lower + 13)).innerHTML;
        document.getElementById("side2_amber_time").value = document.getElementById(t1id12 + (lower + 14)).innerHTML;
        document.getElementById("side3_amber_time").value = document.getElementById(t1id12 + (lower + 15)).innerHTML;
        document.getElementById("side4_amber_time").value = document.getElementById(t1id12 + (lower + 16)).innerHTML;
        document.getElementById("side5_amber_time").value = document.getElementById(t1id12 + (lower + 17)).innerHTML;
        document.getElementById("transferred_status").value = document.getElementById(t1id12 + (lower + 18)).innerHTML;
        document.getElementById("remark11").value = document.getElementById(t1id12 + (lower + 19)).innerHTML;

        for (var i = 0; i < noOfColumns; i++) {
            //   document.getElementById(t1id1 + (lower + i)).bgColor = "yellowgreen";   
            // set the background color of clicked row to yellow.
        }
        document.getElementById("EDIT").disabled = false;
        if (!document.getElementById("SAVE").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("DELETE").disabled = false;
            document.getElementById("NEW").disabled = false;
        }
        $("#message").html('');
    }
    function makeEditable2(id) {

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
        document.getElementById("remark11").disabled = false;

        if (id == 'NEW1') {
            //alert(id);
            $("#message").html('');
            document.getElementById("EDIT1").disabled = true;
            document.getElementById("DELETE1").disabled = true;
            document.getElementById("plandet").disabled = false;
            document.getElementById("SAVENEW1").disabled = false;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 22);
            document.getElementById("plan_id").focus();
        }
        if (id == 'EDIT1') {
            // alert(id);
            document.getElementById("SAVENEW1").disabled = false;
            document.getElementById("DELETE1").disabled = true;
            document.getElementById("plandet").disabled = false;
            document.getElementById("NEW1").disabled = true;
            document.getElementById("plan_id").focus();
        }
        document.getElementById("NEW1").disabled = false;
    }
    function openjdetailsdiv() {

        var div = document.getElementById("jundetails");
        div.style.display = 'block';


    }

    function openjunctionplandetails() {

        var div = document.getElementById("table4");
        div.style.display = 'block';


    }
    function openplandet() {

        var div = document.getElementById("pform");
        div.style.display = 'block';


    }

    function viewdate() {
        debugger;
        var moduleHtml = "";

        $(".row1").remove();
        $(".row").remove();

        // $('#head').remove();

        var A;
        // var bydates = [];
        // var todates = [];
        // var planno = [];
        var bydate = "date";
        var jid = document.getElementById("j_idd").value;
        // alert("aaaaa");
        $.ajax({url: "JunctionDetailsUpdate?task=testing",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: jid, filterdata: bydate},

            success: function (response_data)
            {
                debugger;
                //   alert("data js -" + response_data);
                var data = response_data.data;

                var jpm = response_data.jpm;
                var j_id = response_data.j_id;
                alert(jpm);
                // alert(j_id);
                var data_len = data.length / 2;
                var i = 0;
                // alert("data lemn --"+data_len);

                moduleHtml += '<tr class="row1"  >';

                moduleHtml += '   <th class="heading" >Edit</th>';
                moduleHtml += '   <th class="heading" >FromDate</th>';
                moduleHtml += '   <th class="heading" >ToDate</th>';
                moduleHtml += '   <th class="heading" >TotalPlans</th>';
                moduleHtml += '</tr>'

                for (var j = 0; j < data_len; j++) {
                    debugger;
                    moduleHtml += '<tr class="row"  >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs11' + j + '" name="rb2" onclick="fillColumns2(id)"  value=""></td>';
                    moduleHtml += '<td id="abc" class="dateviewdata" onclick="fillColumns2(id)">' + data[i] + '</td>';
                    //  alert(i);   
                    var fdata = "'" + data[i] + "'";
                    // alert(fromdata);
                    moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    var tdata = "'" + data[i] + "'";

                    //  alert(i);
                    //  alert(todata);
                    
                   
            moduleHtml += '<td id="t1cs11' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                   
              if(j===0)
                    {
                  var check_test_plan=2;
                     var jpm_id = "'" + data[check_test_plan] + "'";
                     
                }
                    if(j>0)
                    {
                
                   var h=3;
                     var jpm_id = "'" + data[check_test_plan+h] + "'";
                     
                    check_test_plan=check_test_plan+h;
                     moduleHtml += '<td id="t1cs11' + j + '" style="display:none" class="dateviewdata" onclick="fillColumns2(id)">' + data[check_test_plan+h] + '</td>';
                    
                }
            moduleHtml += '<td><input type="button" value="PlanDetails" id="pdet" onclick="Openformphase11(' + fdata + ',' + tdata + ',' + j_id + ','+ jpm_id +')"></td>';


                    moduleHtml += '</tr>'
                    //  var r=document.getElementById("tlcs1").value;
                    //   alert(r);
                    i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                }

                $("#table3").append(moduleHtml);
                //$(".DateDayClass").append(moduleHtml);
                var a = document.getElementById("after");
                $("#table3").append(a);
            }
        });
        var jid = document.getElementById("junction_plan_map_id").value;

        document.getElementById("junction_plan_map_id1").value = jid;
        var a = document.getElementsByClassName("a");

        var div = document.getElementById("p1");
        div.style.display = 'block';

        var datecol = document.getElementById("day1");
        datecol.style.display = 'none';

        var datecol = document.getElementById("date1");
        datecol.style.display = 'block';
        var datecol = document.getElementById("todate");
        datecol.style.display = 'block';



        var dateviewdata = document.getElementsByClassName("dateviewdata");
        if (dateviewdata.length > 0) {
            for (var i = 0; i < dateviewdata.length; i++) {

                dateviewdata[i].style.display = 'block';

            }
        }

    }




    function viewday() {

        $(".row1").remove();
        $(".row").remove();


        var bydate = "day";
        var jid = document.getElementById("j_idd").value;

        // alert("aaaaa");
        $.ajax({url: "JunctionDetailsUpdate?task=testing",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: jid, filterdata: bydate},

            success: function (response_data)
            {
                debugger;
                var data = response_data.data;
                //alert("data js -" + data[7]);

                var data_len = data.length / 2;


                var i = 0;
                // alert("data lemn --"+data_len);
                var moduleHtml = "";

                moduleHtml += '<tr class="row1" id="r1" >';

                moduleHtml += '   <th class="heading" >Edit</th>';
                moduleHtml += '   <th class="heading" >Day</th>';

                moduleHtml += '   <th class="heading" >TotalPlans</th>';
                moduleHtml += '</tr>'

                for (var j = 0; j < data_len; j++) {

                    moduleHtml += '<tr class="row" id="r2" >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs' + j + '" name="rb1" onclick="fillColumns2(id)"  value=""></td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[i] + '</td>';
                    var fdata = "'" + data[i] + "'";
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    // moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns1(id)">' + data[++i] + '</td>';
                    if(j===0)
                    {
                  var check_test_plan=2;
                     var jpm_id = "'" + data[check_test_plan] + "'";
                     
                }
                    if(j>0)
                    {
                
                   var h=3;
                     var jpm_id = "'" + data[check_test_plan+h] + "'";
                     
                    check_test_plan=check_test_plan+h;
                     moduleHtml += '<td id="t1cs' + j + '" style="display:none" class="dateviewdata" onclick="fillColumns2(id)">' + data[check_test_plan+h] + '</td>';
                    
                }
                 
            moduleHtml += '<td><input type="button" value="PlanDetails" id="pdet" onclick="Openformphase12(' + fdata + ',' + jid + ','+ jpm_id +')"></td>';

                    moduleHtml += '</tr>'
                    i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                }

                $("#table3").append(moduleHtml);
                //$(".DateDayClass").append(moduleHtml);
                var a = document.getElementById("after");
                $("#table3").append(a);





            }




        });

        var div = document.getElementById("p1");
        div.style.display = 'block';
        var datecol = document.getElementById("day1");
        datecol.style.display = 'table-cell';
        var datecol = document.getElementById("date1");
        datecol.style.display = 'none';
//        var datecoldata=document.getElementById("datedisplay");
//        datecoldata.style.display='none';
//          var datecoldata=document.getElementById("daydisplay");
//        datecoldata.style.display='block';
//          
//        
        var dayviewdata = document.getElementsByClassName("dayviewdata");

        if (dayviewdata.length > 0) {
            for (var i = 0; i < dayviewdata.length; i++) {

                dayviewdata[i].style.display = 'table-cell';

            }
        }

        var dateviewdata = document.getElementsByClassName("dateviewdata");

//        if (dateviewdata.length > 0) {
//            for (var i = 0; i < dateviewdata.length; i++) {
//
//                dateviewdata[i].style.display = 'none';
//
//            }
//        }

//          var dateviewdata=document.getElementsByClassName("dateviewdata");
//         if(dateviewdata.length>0){
//       for (var i = 0; i < dateviewdata.length; i++) {
//         
//    dateviewdata[i].style.display = 'none';
//     
//  }
//         }
    }
    function viewnormal() {

        $(".row1").remove();
        $(".row").remove();


        var bydate = "normal";
        var jid = document.getElementById("j_idd").value;

        //var pid = document.getElementById("j_idd").value;

        // alert("aaaaa");
        $.ajax({url: "JunctionDetailsUpdate?task=testing",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: jid, filterdata: bydate},

            success: function (response_data)
            {
                debugger;
                var data = response_data.data;
                     var data_jpm = response_data.jpm;
                     //alert(data_jpm);
                var data_len = data.length /3;


                var i = 0;
               
                var moduleHtml = "";

                moduleHtml += '<tr class="row1" id="r1" >';

                moduleHtml += '   <th class="heading" >Edit</th>';
                moduleHtml += '   <th class="heading" >Time</th>';

                //moduleHtml += '   <th class="heading" >TotalPlans</th>';
                moduleHtml += '</tr>'

                for (var j = 0; j < data_len; j++) {

                    moduleHtml += '<tr class="row" id="r2" >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs' + i + '" name="rb1" onclick="fillColumns2(id)"  value=""></td>';

                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata"  name="fromdate"onclick="fillColumns2(id)">' + data[i] + '</td>';
                    
                    
        // moduleHtml += '<td id="t1cs' + j + '" style="display:none" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>'; 
            moduleHtml += '<td id="t1cs' + j + '" style="display:none" class="dateviewdata" onclick="fillColumns2(id)">' + data[++i] + '</td>';
                    var pid = "'" + data[i] + "'";
                    
                    var jpm_id = "'" + data[++i] + "'";
//                    if(j===0)
//                    {
//                  var check_test_plan=2;
//                     var jpm_id = "'" + data[check_test_plan] + "'";
//                     
//                }
//                    if(j>0)
//                    {
//                
//                   var h=3;
//                     var jpm_id = "'" + data[check_test_plan+h] + "'";
//                     
//                    check_test_plan=check_test_plan+h;
//                    
//                    
//                }
//                
                    moduleHtml += '<td><input type="button" value="PlanDetails" id="pdet" onclick="Openformphase13(' + jid + ',' + pid + ','+ jpm_id +')"></td>';
                   
                    moduleHtml += '</tr>'
                     i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                } moduleHtml  += '</tr>'
                 
                  
                
             
               
                $("#table3").append(moduleHtml);
               // $("#table3").append(moduleHtml1);
                //$(".DateDayClass").append(moduleHtml);
                var a = document.getElementById("after");
                $("#table3").append(a);





            }




        });

        var div = document.getElementById("p1");
        div.style.display = 'block';


        var datecol = document.getElementById("date1");
        datecol.style.display = 'table-cell';
        var daycol = document.getElementById("day1");
        daycol.style.display = 'table-cell';
        var dateviewdata = document.getElementsByClassName("dateviewdata");
        if (dateviewdata.length > 0) {
            for (var i = 0; i < dateviewdata.length; i++) {

                dateviewdata[i].style.display = 'table-cell';

            }

        }



        var dayviewdata = document.getElementsByClassName("dayviewdata");

        if (dayviewdata.length > 0) {
            for (var i = 0; i < dayviewdata.length; i++) {

                dayviewdata[i].style.display = 'table-cell';

            }
        }
//        var datecoldata=document.getElementById("datedisplay");
//        datecoldata.style.display='block';

//       var datecoldata=document.getElementById("daydisplay");
//        datecoldata.style.display='block';


    }
    function nextbuttontask(){
    var lowerLimit=document.getElementById("lowerLimit").value;  
    alert(lowerLimit);
         $.ajax({url: "JunctionDetailsUpdate?task=testing",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: { lowerLimit: lowerLimit},

            success: function (response_data)
            {
              
            }


        });
    }



    function openlastdiv() {

        var div = document.getElementById("lastdiv");
        div.style.display = 'block';
        alert("aa");

    }

    function myFunction() {
        var url = "PlanDetailIdController?";
        popupwin = openPopUp(url, "plan_detail_id", 580, 900);


    }
   
    
     function Openformphasenewfirst(fdata1, tdata1, j_id) {
        $(".row21").remove();
         $(".row31").remove();
        debugger;
        var moduleHtml;
        // var queryString = "task=SelectedJunctionPhase&from_date=" + from_date + "&to_date=" + to_date + "&junction_id=" + j_id;
        $.ajax({url: "JunctionDetailsUpdate?task=jdetails",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {junction_id: j_id, from_date: fdata1, to_date: tdata1},

            success: function (response_data)
            {
                debugger;
      var data = response_data.data;
//      var jun_id = response_data.jun_id;
//      var p_no = response_data.p_no;
//      var no_sides = response_data.sides;
//                alert("jun_id   -" + jun_id);
//                alert("p_no   -" + p_no);
 //              alert("no_sides   -" + no_sides);
              //  alert("data js -" + data);
                // var jpm=response_data.jpm;
                // var j_id=response_data.j_id;
                // alert(jpm);
                // alert(j_id);
                
                var data_len = data.length / 25;
                var i = 0;
                // alert("data lemn --"+data_len);

                moduleHtml += '<tr class="row21"  >';
                moduleHtml += '<th class="heading">Editssss</th>';
                moduleHtml += '<th class="heading">Junction ID</th>';
                moduleHtml += '<th class="heading">Junction Name </th>';
                moduleHtml += '<th class="heading">Address1 </th>';
                moduleHtml += '<th class="heading">Address2 </th>';
                moduleHtml += '<th class="heading">State Name</th>';
                moduleHtml += '<th class="heading">City Name</th>';
                moduleHtml += '<th class="heading">Controller Model </th>';
                moduleHtml += '<th class="heading">No of Sides</th>';
                moduleHtml += '<th class="heading">Amber Time</th>';
                moduleHtml += '<th class="heading">Flash Rate</th>';
                moduleHtml += '<th class="heading">No of Plans</th>';
                moduleHtml += '<th class="heading">Mobile No</th>';
                moduleHtml += '<th class="heading">SIM NO</th>';
                moduleHtml += '<th class="heading">IMEI NO</th>';
                moduleHtml += '<th class="heading">Instant Green Time</th>';
                moduleHtml += '<th class="heading">Pedestrian</th>';
                moduleHtml += '<th class="heading"> Pedestrian Time</th>';
                moduleHtml += '<th class="heading"> Side1 Name</th>';
                moduleHtml += '<th class="heading">Side2 Name </th>';
                moduleHtml += '<th class="heading">Side3 Name </th>';
                moduleHtml += '<th class="heading">Side4 Name </th>';
                moduleHtml += '<th class="heading">Side5 Name </th>';
                moduleHtml += '<th class="heading">File No </th>';
                moduleHtml += '<th class="heading">Program Version </th>';
                moduleHtml += '<th class="heading">Plan Details </th>';
                
 
                moduleHtml += '</tr>'
 
                for (var j = 0; j <data_len; j++) {
                    debugger;
                    moduleHtml += '<tr class="row31"  >';
                    moduleHtml += '   <td width="25%"><input type="radio" id="t1cs' + j + '" name="rb1" onclick="fillColumns(id)"  value=""></td>';
                    moduleHtml += '<td id="abc" class="dateviewdata" onclick="fillColumns(id)">' + data[i] + '</td>';
                    var jun_id=data[i];
                  //  alert(jun_id+"j_id");
                   moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    var no_sides=data[i];
                  // alert(no_sides+"sides");
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    moduleHtml += '<td id="t1cs' + j + '" class="dateviewdata" onclick="fillColumns(id)">' + data[++i] + '</td>';
                    var p_no=data[i];
                   //  alert(p_no+"pno");
                    moduleHtml += '<td><input type="button" value="Plan Details" id="pdet" onclick="Openform(' + jun_id + ',' + p_no + ',' + no_sides + ')"></td>';

  //  moduleHtml += '<td><input type="button" value="PlanDetails" id="pdet" onclick="Openformphase11(' + fdata + ',' + tdata + ',' + j_id + ')"></td>';


                    moduleHtml += '</tr>'
                    //  var r=document.getElementById("tlcs1").value;
                      
                    i++;
                    //$(".DateDayClass").html(moduleHtml + '</tr>');
                    //$("#table3").html(moduleHtml + '</tr>');
                }

                $("#tab6").append(moduleHtml);
               // $('#tab6').dataTable();
                //$(".DateDayClass").append(moduleHtml);
//                var a = document.getElementById("foot12");
//                $("#tab6").append(a);
            }
        });
    }
 
 
 
 
 
 function submitForms() {
     debugger;
//      getConfirmation();
  document.getElementById("formplandetailss").submit();
 
 }
 function search(ele) {
     debugger;
    if(event.key === 'Enter') {
        alert(ele.value);        
    }
}

function myFunctiontest(id) {
    
    debugger;
   var tr = document.createElement('tr');
    var x = document.getElementById(id);
    var x1_test = document.getElementById("on_time_hour");
    var x2_test = document.getElementById("on_time_min");
    var x3_test = document.getElementById("off_time_hour");
    var x4_test = document.getElementById("off_time_min");
    
    var x1=x.value;
    var on1='on_time_hour';
    var on2='on_time_min';
    var on3='off_time_hour';
      var on4='off_time_min';
     var value_all_on_off_time =   x1_test.value+","+x2_test.value+","+x3_test.value+","+x4_test.value;
    if(id == on1 || id == on2 || id == on3 ||  id == on4 )
    {
        
        if(id == on4){
       
        $.ajax({url: "JunctionDetailsUpdate?task=testingCheck",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {id_value:value_all_on_off_time,id:id},
                     
            success: function (response_data)
            {
             
           
                var data1 = response_data.data;
                var pp="Plan not Exist";
                 var pp1="Plan Exist";
                if(data1==pp)
                {
                   
                       var xp = document.getElementById(id);
                      xp.style.backgroundColor = "red";
                    
                }
                if(data1 == pp1){
                 myFunction_on_off_popup(value_all_on_off_time);
             }
                var conform_test="on_time_hour";
                if(id == conform_test){
                   
               
            }
                 // $("#pkc").append(data1[0]);
//         alert(data1[0]);
//          alert("hi");
          
            }
           
        });
    }
        
     
    }else{
    
    //alert(x1);
   // x.value = x.value.toUpperCase();
         $.ajax({url: "JunctionDetailsUpdate?task=testingCheck",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {id_value:x1,id:id},
                     
            success: function (response_data)
            {
             
           
                var data1 = response_data.data;
                //alert(data1);
                var pp="Plan not Exist";
                if(data1 == pp)
                {
                  // alert("hi2");
                       var xp = document.getElementById(id);
                      xp.style.backgroundColor = "red";
                    
                }
              

                 // $("#pkc").append(data1[0]);
//         alert(data1[0]);
//          alert("hi");
          
            }
           
        });
        
}
}
<<<<<<< HEAD
function makeAllEditable(id){
     alert();
    // junction details
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
        document.getElementById("junctionsave").disabled = false;
        

        //plan map
         document.getElementById("junction_name1").disabled = false;
        document.getElementById("start_time").disabled = false;
        document.getElementById("order_no").disabled = false;
        // document.getElementById("day").disabled = false;
        document.getElementById("date").disabled = false;
         document.getElementById("junctionplanmap").disabled = false;
       
        //plan details
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
        document.getElementById("remark11").disabled = false;
        document.getElementById("SAVENEW1").disabled = false;
        

// ajax call to insert data in temp tables
//  $.ajax({url: "JunctionDetailsUpdate?task=inserttempdata",
//            
//            dataType: 'json',
//           
//           // data: {id:id},
//
//            success: function (response_data)
//            {
//          var status = response_data.status;
//          var data = response_data.data;
//          var listsize = response_data.listsize;
//   alert("status"+status);
//   alert("data "+data);
//   alert("listsize "+listsize);
//         
//            }
//        });

}




=======
>>>>>>> 85371e7894cd74d582af0c237ce82aaf63cfee31

        function myFunction_on_off_popup(value_all_on_off_time) {
          debugger;
                var url = "PlanDetailIdController1?&check_task="+value_all_on_off_time;
                popupwin = openPopUp(url, "plan_detail_id", 580, 900);


            }
//  function getConfirmation() {
//               var retVal = confirm("Do you want to change next plan on time ?");
//               if( retVal == true ) {
//                  //document.write ("User wants to change next plan on time!");
//                    $.ajax({url: "JunctionDetailsUpdate?task=testingCheckonofftime",
//            //type: 'POST',
//            dataType: 'json',
//            //contentType: 'application/json',
//            //context: document.body,
//
//            data: {id_value:"x1,id:id"},
//                     
//            success: function (response_data)
//            {
//             
//           
//                var data1 = response_data.data;
//                var pp="Plan not Exsist";
//              
//              
//                 // $("#pkc").append(data1[0]);
////         alert(data1[0]);
////          alert("hi");
//          
//            }
//           
//        });
//                  return true;
//               } else {
//                 // document.write ("User does not want to change next plan on time!");
//                  return false;
//               }
//            }

function finalSave(id) {
    
    debugger;
  
   // x.value = x.value.toUpperCase();
         $.ajax({url: "JunctionDetailsUpdate?task=save_final",
            //type: 'POST',
            dataType: 'json',
            //contentType: 'application/json',
            //context: document.body,

            data: {id:id},
                     
            success: function (response_data)
            {
             
           
                var data1 = response_data.data;
                //alert(data1);
                var pp="Plan not Exist";
                if(data1 == pp)
                {
                  // alert("hi2");
                       var xp = document.getElementById(id);
                      xp.style.backgroundColor = "red";
                    
                }
              

                 // $("#pkc").append(data1[0]);
//         alert(data1[0]);
//          alert("hi");
          
            }
           
        });
        
}
}
</script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/style.css" media="screen">
        <link rel="stylesheet" href="style/Table_content.css" media="screen">
        <title>Junction Page</title>
        <style>#DdataTables-example_filter {
	margin-left: 90px;
}</style>
            

    </head>
    <body onload="Openformphasenewfirst()">
        

        <table align="center" cellpadding="0" cellspacing="0" class="main" border="1" width="1500px">

            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr><td><center><input type="button" id="editrecord" value="Edit Records" onclick="makeAllEditable()"><input type="button" id="viewrecord" value="View Records" onclick="makeAllEditable()"></center></td> 
           </tr>
            <tr>
                <td>
                    <div><table class="header_table" width="100%">
                            <tr style="font-size:larger ;font-weight: 700;" align="center" >
                                <td style="margin-left: 1px; width: 90%">
                                    Junction Details
                                </td>
                                <td>
                                    <button id="plus" bgcolor="#FF0000" style="margin-left:85%;" onclick="showdiv()">+</button>
                                </td><td> <button id="minus" bgcolor="#FF0000" onclick="hidediv()">-</button>
                                </td>
                        </table></div>
                    <DIV id="body" class="maindiv" style="display:none">
                        <table cellspacing="0" id="table0"  align="center" width="100%">
                            <tr>  <td><div><table class="header_table" width="100%">
                                            <tr style="font-size:larger ;font-weight: 700; margin-left: 100%;" align="center" >


                                                <td style="margin-bottom:100px;">
                                                    Junction Detail
                                                </td>
                                                <!--                                            <td>
                                                                                                <button id="plus" bgcolor="#FF0000" style="margin-left:85%;" onclick="showdiv()">+</button>
                                                                                            </td><td> <button id="minus" bgcolor="#FF0000" onclick="hidediv()">-</button>
                                                                                            </td>-->
                                        </table> </div></td></tr>

                        </table> 

                        <table id="t1">
                            <tr>
                                <td>
                                    <div style="width: 990px;max-height: 340px;overflow: auto;margin-bottom: 20px; margin-top: 20px; display: none" id="jdt">
                                        <form name="form1" action="JunctionDetailsUpdate" method="post">
                                            <div class="table-responsive">
                                            <table class="table table-striped table-bordered table-hover" name="table1" border="1" width="100%" align="center" id="tab6" >
                                               
                                                
                                               
                                                
                                             
                                            </table></div>
                                            <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form> 
                                    </div> </td>
                            </tr>

                            <tr>
                                <td>
                                    <div style="width: 990px;overflow: auto; display: none"  id="jundetails">
                                        <form name="form2"  action="JunctionDetailsUpdate" method="post">
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
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="8">
                                                        <input class="button" type="submit" id="junctionsave" name="task" value="junctionsave" onclick="setStatus(id)" />
<!--                                                        <input class="button" type="reset" id="NEW" name="task" value="New" onclick="makeEditable(id)"/>
                                                        <input class="button" type="button" id="EDIT" name="task" value="Edit" onclick="makeEditable(id)" disabled/>
                                                        <input class="button" type="submit" id="DELETE" name="task" value="Delete" onclick="setStatus(id)" disabled>
                                                        <input class="button1" type="submit" name="task" id="Save AS New" value="Save AS New" onclick="setStatus(id)" disabled>
                                            -->    </tr>

                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form of table. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="j" value="${junction}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                    </div></div>  </td>
                            </tr>

                        </table>
                    </DIV>
                </td>
            </tr>
            <tr>
                <td>
                    <div><table class="header_table" width="100%">
                            <tr style="font-size:larger ;font-weight: 700;" align="center">
                                <td style=" width: 90%">
                                    Junction Plan Details
                                </td>
                                <td>
                                    <button id="plus1" bgcolor="#FF0000" style="margin-left:85%;" onclick="showdiv1()">+</button>
                                </td><td> <button id="minus1" bgcolor="#FF0000" onclick="hidediv1()">-</button>
                                </td>
                            </tr>
                            <tr style="font-size:larger ;font-weight: 300;" align="center">
                                <td>
                                    <div style="display: none" id="rbutton">
                                        <input type="radio" name=" " value="" id="bydateplan" onclick="viewdate()">Date 
                                        <input type="radio" name=" " value="" calss="bydayplan" id="bydayplan" onclick="viewday()">Day
                                        <input type="radio" name=" " value="" id="normalplan" onclick="viewnormal()">Normal
                                    </div> 

                                </td>

                            </tr>
                        </table></div>
                    <input type="hidden" value="${j_id}" id="j_idd" name="j_idd">
                    <br>
                    <DIV  class="maindiv" id ="formPlan" style="display: none">
                        <table align="center" width="1000" border="0" cellpadding="0" cellspacing="0" >
                            <tr><td><table border="7" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">
                                            <!--                                            <td>
                                                                                            Junction Plan Map Table
                                                                                            
                                                                                             </td>
                                                                                        <td>
                                                                                            <button id="plus1" bgcolor="#FF0000" style="margin-left:85%;" onclick="showdiv1()">+</button>
                                                                                        </td><td> <button id="minus1" bgcolor="#FF0000" onclick="hidediv1()">-</button>
                                            -->
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td>
                                    <table id="table1"  align="center" width="500">
                                        <tr>
                                            <td>
                                                <form name="form3" method="POST" action="JunctionDetailsUpdate">
                                                    <DIV STYLE="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px; display: none" id="p1">
                                                        <table border="1" id="table3" align="center" class="reference">
                                                            <tr id="head">
                                                                <!--                                                                <th class="heading" >Edit</th>
                                                                                                                                <th class="heading" >Junction Name</th>
                                                                                                                                <th class="heading" id="date1" style="display: none;"  >FromDate</th>
                                                                                                                                 <th class="heading" id="todate" style="display: none;"  >ToDate</th>
                                                                                                                                <th class="heading" id="day1" style="display: none;" >Day</th>
                                                                                                                                <th class="heading" style="display: none;" >Time</th>
                                                                                                                                <th class="heading" style="display: none;">Order No</th>
                                                                                                                                <th class="heading" >Total Plans</th>-->
                                                                <!--                                                                <th class="heading" >Phase Details</th>-->
                                                            </tr>

                                                            <!--                                                                                                                       <div id="myDateId" class="DateDayClass">
                                                                                                                        
                                                                                                                                                                                    </div>-->


                                                            <!--                                                            <tr class="DateDayClass">
                                                            
                                                                                                                        </tr>-->
                                                            
                                                            <tr id="after">

<!--                                                                <td align='center' colspan="8">
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
                                                                </td>-->
                                                            </tr>
                                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                            <input class="input" type="hidden" id="junction_id" name="junction_id" value="${junction_id}" size="50" >

<!--                                                            <input class="input" type="hidden" id="junction_id" name="junction_id" value="${plan_id}" size="50" >-->
                                                            <!--                                                            <tr><td class="a" >
                                                            
                                                            
                                                                                                                            </td></tr>-->
                                                        </table>
                                                    </DIV>
                                                </form>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <form name="form4" method="POST" action="JunctionDetailsUpdate" >

                                                    <table border="1"  id="table4" align="center"  class="reference" style="display: none;">
                                                        <tr id="message">
                                                            <c:if test="${not empty message}">
                                                                <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                            </c:if>
                                                        </tr>

                                                        <tr>
                                                            <th class="heading" >Junction Name</th>
                                                            <td><input class="input" type="text" id="junction_name1" name="junction_name1" value="${junction_name}" size="50" disabled>
                                                                <input class="input" type="hidden" id="junction_id" name="junction_id" value="${junction_id}" size="50" >
                                                                <input class="input" type="hidden" id="junction_plan_map_id1p" name="junction_plan_map_id1p" value="${p_id14_junction_plan_map}" size="50" ></td>
                                                       <td>${p_id14_junction_plan_map}</td> </tr>
                                                        <tr>
                                                            <th class="heading" >On Time -  Off Time</th>
                                                            <td><input class="input" type="text" id="start_time" name="start_time" value="" maxlength="6" size="40" onkeyup="myFunction()"  >
                                                                <input type="hidden" id="selected_plan_id">
                                                                <!--                                                               <input class="button" type="button" name="ok" id="ok" value="ok" onclick="testSelect(name)"></td>-->
                                                        </tr>



                                                        <tr><th class="heading" >Order No</th>
                                                            <td><input class="input" type="text" id="order_no" name="order_no" value="" maxlength="6" size="50" disabled>
                                                            </td></tr>


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
<!--                                                                <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable1(id)"> &nbsp;&nbsp;&nbsp;&nbsp;
                                                                <input class="button" type="submit" name="task" id="junctionplanmap" value="junctionplanmap" onclick="setStatus(id)" disabled>&nbsp;&nbsp;&nbsp;&nbsp;
                                                               -->
                                                                <input class="button" type="submit" name="task" id="junctionplanmap" value="Save" onclick="setStatus(id)" disabled>  &nbsp;&nbsp;&nbsp;&nbsp;
                                                                <!--                                                                <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable1(id)">&nbsp;&nbsp;&nbsp;&nbsp;-->
<!--                                                                <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                            -->
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

            <tr><td>



                    <div><table class="header_table" width="100%">
                            <tr style="font-size:larger ;font-weight: 700;" align="center">
                                <td style=" width: 90%">
                                    Plan Details
                                </td>
                                <td>
                                    <button id="plus2" bgcolor="#FF0000" style="margin-left:85%;" onclick="showdiv2()">+</button>
                                </td><td> <button id="minus2" bgcolor="#FF0000" onclick="hidediv2()">-</button>
                                </td>
                            </tr>

                        </table></div>
                    <DIV id="bodyphase" class="maindiv" id="formPhase" style="display: none">
                        <table cellspacing="0" id="table9"  align="center" width="100%">
                            <tr><td><table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">

                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td>
                                    <div class="table-responsive" style="width: 990px;max-height: 340px;overflow: auto;margin-bottom: 20px" id="">
                                        <form name="form1" action="JunctionDetailsUpdate" method="post">
                                            <table class="reference" border="1" align="center" id="tab4">
                                                <tr id="head1">


                                                </tr>

                                                <tr id="foot">
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
                                    <div style="overflow: scroll; height: 450px; display: none;" id="pform">
                                        <form name="form" id="formplandetailss"  action="JunctionDetailsUpdate" method="post" >
                                            <table name="table8" class="reference"  border="1" align="center" style="width: 80% !important;">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="22" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                        
                                                </tr>

                                                <input class="input form-control"  size="15" type="hidden" id="plan_id" name="plan_id" value="" >
                                                               <input type="hidden" id="junction_plan_map_id_test_update" name="junction_plan_map_id" value="" disabled>
                                                                   <input type="hidden"  name="task" value="SaveupdateDetails" disabled>
                                                                   <tr><div id="pkc">testing:</div></tr>
                                                                   <tr align="center" class="incHeight" >
                                                    <th  class="heading"  align="center" colspan="2">Plan No</th>
                                                    
                                                    <td colspan="2">
                                                        <input class="input form-control"  size="15" type="text" id="plan_no" name="plan_no" value="" size="30" disabled><br>
                                                    </td>

                                                </tr>
                                                
                                                <tr>
                                                     <th  class="heading"  align="center">On Time Hr</th>

                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="on_time_hour" name="on_time_hour" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>

                                                    <th  class="heading" align="center">On Time Min</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="on_time_min" name="on_time_min" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>
                                                
                                                <tr align="center" class="incHeight">




                                                    <th  class="heading" align="center">Off Time Hour</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="off_time_hour" name="off_time_hour" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">off Time Min</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="off_time_min" name="off_time_min" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight" >
                                                    <th  class="heading" align="center" colspan="2">MOde</th>
                                                    <td colspan="2">
                                                        <select class="select form-control" id="mode" name="mode" style="height: 20px;width: 80%;margin: 8px 0;display: inline-block;border: 1px solid #ccc;
                                                                border-radius: 4px;box-sizing: border-box;" onfocusout="myFunctiontest(id)" disabled>
                                                            <option value="Signal" >Signal</option>
                                                            <option value="Blinker" >Blinker</option>
                                                            <option value="Off" >Off</option>
                                                        </select><BR>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">
                                                    <th  class="heading" align="center">Side1 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side1_green_time" name="side1_green_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">Side2 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side2_green_time" name="side2_green_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>
                                                <tr align="center" class="incHeight">

                                                    <th  class="heading" align="center">Side3 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side3_green_time" name="side3_green_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">Side4 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side4_green_time" name="side4_green_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">

                                                    <th  class="heading" align="center">Side5 Green Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side5_green_time" name="side5_green_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">Side1 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side1_amber_time" name="side1_amber_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">

                                                    <th  class="heading" align="center">Side2 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side2_amber_time" name="side2_amber_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">Side3 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side3_amber_time" name="side3_amber_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">

                                                    <th  class="heading" align="center">Side4 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side4_amber_time" name="side4_amber_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                    <th  class="heading" align="center">Side5 Amber Time</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="side5_amber_time" name="side5_amber_time" value="" maxlength="16" onfocusout="myFunctiontest(id)" disabled><br>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">

                                                    <th  class="heading"  align="center">Transferred Status</th>
                                                    <td>
                                                        <input class="input form-control"  type="text" id="transferred_status" name="transferred_status" maxlength="2" disabled><br>
                                                    </td>
                                                    <th  class="heading"  align="center" >Remark</th>
                                                    <td colspan="3">
                                                        <input class="input form-control"  type="text" id="remark11" name="remark" maxlength="30" disabled><br>
                                                    </td>
                                                </tr>

                                         
                                                <input type="hidden" id="clickedButton" name="j_id" value="${j_id}">
                                                 <input type="hidden" id="SaveupdateDetails" name="task" value="SaveupdateDetails">
                                                <tr>
                                                    <td align='center' colspan="10">
                                                        <!--<input class="button" type="submit" id="plandet" name="task" value="plandet" onclick="setStatus(id)" />-->
<!--                                                        <input class="button" type="reset" id="NEW1" name="task" value="New" onclick="makeEditable2(id)"/>
                                                        <input class="button" type="button" id="EDIT1" name="task" value="Edit"  onclick="makeEditable2(id)" />
                                                        <input class="button" type="submit" id="DELETE1" name="task" value="Delete" disabled>
                                                       -->
                                                        <input class="button" type="submit" name="task" id="SAVENEW1" value="Save" onclick="setStatus(id)" disabled>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form of table. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">   
                                                <input type="hidden" id="clickedButton" name="listdata" value="${j_id}">
                                            </table>
                                        </form>
                                    </div>  </td>
                            </tr>
                        </table>
                    </DIV>
                </td></tr>


            <tr><td>
                    <div><table class="header_table" width="100%">
                            <tr style="font-size:larger ;font-weight: 700;" align="center">
                                <td style=" width: 90%">
                                    Junction Phase Data
                                </td>
                                <td>
                                    <button id="plus3" bgcolor="#FF0000" style="margin-left:85%;" onclick="showdiv3()">+</button>
                                </td><td> <button id="minus3" bgcolor="#FF0000" onclick="hidediv3()">-</button>
                                </td>
                        </table></div>
                    <DIV  class="maindiv" id="formPhasedetail1" style="display: none">
                        <table align="center" width="1000" border="0" cellpadding="0" cellspacing="0" >
                            <tr><td><table border="4" class="header_table" width="100%">
                                        <tr style="font-size:larger ;font-weight: 700;" align="center">

                                        </tr>
                                    </table> 
                                </td> 
                            </tr>
                            <tr>
                                <td>

                                    <table id="table11"  align="center" width="500">
                                        <tr>
                                            <td>
                                                <form name="form11" method="POST" action="JunctionDetailsUpdate">
                                                    <DIV STYLE="overflow: auto;  max-height: 410px; padding:0px; margin-bottom: 20px">
                                                        <!--                                                        <table border="1" id="table12" align="center" class="reference">
                                                                                                                    <tr>
                                                                                                                        <th class="heading" >Edit</th>
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
                                                        <c:forEach var="list" items="${requestScope['phasedetailsSelected']}" varStatus="loopCounter">
                                                            <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                                <td id="t1c${IDGenerator.uniqueID3}" style="display: none" onclick="fillColumns3(id)">${list.junction_plan_map_id}</td>
                                                                <td width="25%"><input type="radio" id="t1c${IDGenerator.uniqueID3}" name="" value="${list.junction_plan_map_id}" onclick="openlastdiv()"></td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)" style="display: none"  align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.junction_name}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.from_date}//${planMap.to_date}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.day}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.on_time_hr}:${list.on_time_min}-${list.off_time_hr}:${list.off_time_min}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.order_no}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.plan_no}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.phase_no}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.side13}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.side24}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.padestrian_info}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.day_name}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" onclick="fillColumns3(id)">${list.remark}</td>
                                                                <td id="t1c${IDGenerator.uniqueID3}" style="display: none" onclick="fillColumns3(id)">${list.junction_id}</td>
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
                                                    </table>-->
                                                        <table class="reference" border="1" width="100%" align="center" id="tab5">
                                                            <tr id="head1">
                                                            
                                                            </tr>

                                                            <tr id="foot1" style="display: none">
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
                                                    </DIV>
                                                </form>
                                            </td>
                                        </tr>
                                        <tr> 
                                            <td> <div align="center" id="lastdiv" style="display: none;">
                                                    <form name="form12" method="POST" action="PhaseDataCont">
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

                                                                <td>ON&OFF Time<input class="input" type="text" id="start_time" name="start_time" value="" maxlength="6" size="20"></td>


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

                                </td>
                            </tr>
                        </table>
                    </div>
                </td></tr>



            <tr><td><form action="" id="mainform" method="post">
<!--                        <center><input class="button" type="submit" id="finalsubmit" name="task" value="Final" onclick="setStatus(id)" /></center>                                                -->
<!--<center><input class="button" type="submit" id="finalsubmit" name="task" value="Final" onclick="submitForms()" /></center>-->
<!--                   
                    <input type="button" id="SaveupdateDetails" name="task" value="SaveupdateDetails" onclick="submitForms()">-->
                      
                    <input type="button" id="SaveFinal" name="task" value="Final" onclick="submitForms()">
                    </form></td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>


        </table>


    </body>
</html>
