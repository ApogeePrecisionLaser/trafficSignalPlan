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
    jQuery(function () {
        $("#junction_name").autocomplete("phaseinfoCont", {
            extraParams: {
                action1: function () {
                    return "getJunctionName";
                }
            }
        });
    });

    var master = 0;
    function makeEditable(id) {
        debugger;
        document.getElementById("phase_info_id").disabled = false;
        document.getElementById("junction_name").disabled = false;
        document.getElementById("phase_no").disabled = false;
        document.getElementById("plan_no").disabled = false;
        document.getElementById("phase_time").disabled = false;
//        document.getElementById("green_one").disabled = false;
//        document.getElementById("green_two").disabled = false;
//        document.getElementById("green_three").disabled = false;
//        document.getElementById("green_four").disabled = false;
//        document.getElementById("green_five").disabled = false;
        document.getElementById("opn").disabled = false;
        document.getElementById("side_no").disabled = false;
        document.getElementById("G1").disabled = false;
        document.getElementById("G2").disabled = false;
        document.getElementById("G12").disabled = false;
        document.getElementById("pair1").disabled = false;
        document.getElementById("pair2").disabled = false;
        document.getElementById("side1R").disabled = false;
        document.getElementById("side1A").disabled = false;
        document.getElementById("side1G1").disabled = false;
        document.getElementById("side1G2").disabled = false;
        document.getElementById("side1G3").disabled = false;

        document.getElementById("side2R").disabled = false;
        document.getElementById("side2A").disabled = false;
        document.getElementById("side2G1").disabled = false;
        document.getElementById("side2G2").disabled = false;
        document.getElementById("side2G3").disabled = false;

        document.getElementById("side3R").disabled = false;
        document.getElementById("side3A").disabled = false;
        document.getElementById("side3G1").disabled = false;
        document.getElementById("side3G2").disabled = false;
        document.getElementById("side3G3").disabled = false;

        document.getElementById("side4R").disabled = false;
        document.getElementById("side4A").disabled = false;
        document.getElementById("side4G1").disabled = false;
        document.getElementById("side4G2").disabled = false;
        document.getElementById("side4G3").disabled = false;

        document.getElementById("side5R").disabled = false;
        document.getElementById("side5A").disabled = false;
        document.getElementById("side5G1").disabled = false;
        document.getElementById("side5G2").disabled = false;
        document.getElementById("side5G3").disabled = false;

        document.getElementById("left_green").disabled = false;
        //document.getElementById("pedestrian_info").disabled = false;
        document.getElementById("gpio").disabled = false;
        document.getElementById("remark").disabled = false;
        //        document.getElementById("side_5_name").disabled = false;
        if (id === 'NEW') {
            $("#message").html('');
            document.getElementById("CONTINUE").disabled = false;
            document.getElementById("SUBMIT").disabled = false;
            document.getElementById("SAVE").disabled = true;
            //document.getElementById("submit_coloumn").style.display = "block";
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 18);
            document.getElementById("junction_name").focus();
        }
        if (id === 'EDIT') {
            document.getElementById("Save").disabled = false;
            document.getElementById("DELETE").disabled = false;
            document.getElementById("junction_name").focus();
        }
        document.getElementById("SAVE").disabled = false;
    }

    function setStatus(id) {
        debugger;
        if (id === 'SAVE') {
            document.getElementById("clickedButton").value = "SAVE";
        } else if (id === 'DELETE') {
            document.getElementById("clickedButton").value = "DELETE";
        } else if (id === 'CONTINUE') {
            document.getElementById("clickedButton").value = "Save And Continue";
        } else if (id === 'SUBMIT') {
            document.getElementById("clickedButton").value = "Save And Submit";
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
        var noOfColumns = 18;
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
        document.getElementById("phase_info_id").value = document.getElementById("phase_info_id" + rowNo).value;
        document.getElementById("junction_id").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("junction_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("phase_no").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;
        document.getElementById("plan_no").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("phase_time").value = document.getElementById(t1id + (lowerLimit + 5)).innerHTML;
        document.getElementById("green_one").value = document.getElementById(t1id + (lowerLimit + 6)).innerHTML;
        document.getElementById("green_two").value = document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("green_three").value = document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
        document.getElementById("green_four").value = document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
        document.getElementById("green_five").value = document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
//        document.getElementById("side_one_three").value = document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
//        document.getElementById("side_two_four").value = document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
//        document.getElementById("side_five").value = document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
        document.getElementById("left_green").value = document.getElementById(t1id + (lowerLimit + 14)).innerHTML;
        document.getElementById("padestrian_info").value = document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
        document.getElementById("gpio").value = document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
        side_one_three = splitValue(hex2bin(document.getElementById(t1id + (lowerLimit + 11)).innerHTML), 4);
        side_two_four = splitValue(hex2bin(document.getElementById(t1id + (lowerLimit + 12)).innerHTML), 4);
        side_one = side_one_three.split(" ")[0];
        side_three = side_one_three.split(" ")[1];
        side_two = side_two_four.split(" ")[0];
        side_four = side_two_four.split(" ")[1];
        side_five = hex2bin(document.getElementById(t1id + (lowerLimit + 13)).innerHTML);
        if (side_one === "1000") {
            document.getElementById("side1R").checked = true;
        } else if (side_one === "0100") {
            document.getElementById("side1A").checked = true;
        } else if (side_one === "0011") {
            document.getElementById("side1G1").checked = true;
            document.getElementById("side1G2").checked = true;
            document.getElementById("side1G3").checked = true;
        }

        if (side_two === "1000") {
            document.getElementById("side2R").checked = true;
        } else if (side_two === "0100") {
            document.getElementById("side2A").checked = true;
        } else if (side_two === "0011") {
            document.getElementById("side2G1").checked = true;
            document.getElementById("side2G2").checked = true;
            document.getElementById("side2G3").checked = true;
        }

        if (side_three === "1000") {
            document.getElementById("side3R").checked = true;
        } else if (side_three === "0100") {
            document.getElementById("side3A").checked = true;
        } else if (side_three === "0011") {
            document.getElementById("side3G1").checked = true;
            document.getElementById("side3G2").checked = true;
            document.getElementById("side3G3").checked = true;
        }

        if (side_four === "1000") {
            document.getElementById("side4R").checked = true;
        } else if (side_four === "0100") {
            document.getElementById("side4A").checked = true;
        } else if (side_four === "0011") {
            document.getElementById("side4G1").checked = true;
            document.getElementById("side4G2").checked = true;
            document.getElementById("side4G3").checked = true;
        }

        if (side_five === "00001000") {
            document.getElementById("side5R").checked = true;
        } else if (side_five === "00000100") {
            document.getElementById("side5A").checked = true;
        } else if (side_five === "00000011") {
            document.getElementById("side5G1").checked = true;
            document.getElementById("side5G2").checked = true;
            document.getElementById("side5G3").checked = true;
        }


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
        document.getElementById("phase_info_id").value = "";
    }



//    function setDropdownVisibility(){
//        //alert(document.getElementById("no_of_sides").value);
//        var no_of_sides = document.getElementById("no_of_sides").value;
//        if(no_of_sides === '2'){
//            document.getElementById("side_3_name").value = "";
//            document.getElementById("side_4_name").value = "";
//            document.getElementById("side_5_name").value = "";
//            document.getElementById("side_3_name").disabled = true;
//            document.getElementById("side_4_name").disabled = true;
//            document.getElementById("side_5_name").disabled = true;
//            document.getElementById("side_3_name").style.backgroundColor = "lightgrey";
//            document.getElementById("side_4_name").style.backgroundColor = "lightgrey";
//            document.getElementById("side_5_name").style.backgroundColor = "lightgrey";
//        }else if(no_of_sides === '3'){
//            document.getElementById("side_4_name").value = "";
//            document.getElementById("side_5_name").value = "";
//            document.getElementById("side_3_name").disabled = false;
//            document.getElementById("side_4_name").disabled = true;
//            document.getElementById("side_5_name").disabled = true;
//            document.getElementById("side_3_name").style.backgroundColor = "";
//            document.getElementById("side_4_name").style.backgroundColor = "lightgrey";
//            document.getElementById("side_5_name").style.backgroundColor = "lightgrey";
//        }else if(no_of_sides === '4'){
//            document.getElementById("side_5_name").value = "";
//            document.getElementById("side_3_name").disabled = false;
//            document.getElementById("side_4_name").disabled = false;
//            document.getElementById("side_5_name").disabled = true;
//            document.getElementById("side_3_name").style.backgroundColor = "";
//            document.getElementById("side_4_name").style.backgroundColor = "";
//            document.getElementById("side_5_name").style.backgroundColor = "lightgrey";
//        }else{
//            document.getElementById("side_3_name").disabled = false;
//            document.getElementById("side_4_name").disabled = false;
//            document.getElementById("side_5_name").disabled = false;
//            document.getElementById("side_3_name").style.backgroundColor = "";
//            document.getElementById("side_4_name").style.backgroundColor = "";
//            document.getElementById("side_5_name").style.backgroundColor = "";
//        }
//    }

    function verify() {
        var result;
        debugger;
        if (document.getElementById("clickedButton").value === 'SAVE' || document.getElementById("clickedButton").value === 'Save AS New' || document.getElementById("clickedButton").value === 'Save And Continue' || document.getElementById("clickedButton").value === 'Save And Submit') {
            var junction_name = document.getElementById("junction_name").value;
            if ($.trim(junction_name).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>Junction Name is required...</b></td>";
                $("#message").html(message);
                document.getElementById("junction_name").focus();
                return false; // code to stop from submitting the form2.
            }
            var plan_no = document.getElementById("plan_no").value;
            if ($.trim(plan_no).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>plan_no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("plan_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var phase_no = document.getElementById("phase_no").value;
            if ($.trim(phase_no).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>phase_no is required...</b></td>";
                $("#message").html(message);
                document.getElementById("phase_no").focus();
                return false; // code to stop from submitting the form2.
            }
//            var green_one = document.getElementById("green_one").value;
//            if ($.trim(green_one).length === 0) {
//                var message = "<td colspan='6' bgcolor='coral'><b>green_one is required...</b></td>";
//                $("#message").html(message);
//                document.getElementById("green_one").focus();
//                return false; // code to stop from submitting the form2.
//            }
//            var green_two = document.getElementById("green_two").value;
//            if ($.trim(green_two).length === 0) {
//                var message = "<td colspan='6' bgcolor='coral'><b>green_two is required...</b></td>";
//                $("#message").html(message);
//                document.getElementById("green_two").focus();
//                return false; // code to stop from submitting the form2.
//            }
//            var green_three = document.getElementById("green_three").value;
//            if ($.trim(green_three).length === 0) {
//                var message = "<td colspan='6' bgcolor='coral'><b>green_three model is required...</b></td>";
//                $("#message").html(message);
//                document.getElementById("green_three").focus();
//                return false; // code to stop from submitting the form2.
//            }
//            var green_four = document.getElementById("green_four").value;
//            if ($.trim(green_four).length === 0) {
//                var message = "<td colspan='6' bgcolor='coral'><b>green_four is required...</b></td>";
//                $("#message").html(message);
//                document.getElementById("green_four").focus();
//                return false; // code to stop from submitting the form2.
//            }
//            var green_five = document.getElementById("green_five").value;
//            if ($.trim(green_five).length === 0) {
//                var message = "<td colspan='6' bgcolor='coral'><b>green_five is required...</b></td>";
//                $("#message").html(message);
//                document.getElementById("green_five").focus();
//                return false; // code to stop from submitting the form2.
//            }
            var side1R = document.getElementById("side1R").checked;
            var side1A = document.getElementById("side1A").checked;
            var side1G1 = document.getElementById("side1G1").checked;
            var side1G2 = document.getElementById("side1G2").checked;
            var side1G3 = document.getElementById("side1G3").checked;

            var side2R = document.getElementById("side2R").checked;
            var side2A = document.getElementById("side2A").checked;
            var side2G1 = document.getElementById("side2G1").checked;
            var side2G2 = document.getElementById("side2G2").checked;
            var side2G3 = document.getElementById("side2G3").checked;

            var side3R = document.getElementById("side3R").checked;
            var side3A = document.getElementById("side3A").checked;
            var side3G1 = document.getElementById("side3G1").checked;
            var side3G2 = document.getElementById("side3G2").checked;
            var side3G3 = document.getElementById("side3G3").checked;

            var side4R = document.getElementById("side4R").checked;
            var side4A = document.getElementById("side4A").checked;
            var side4G1 = document.getElementById("side4G1").checked;
            var side4G2 = document.getElementById("side4G2").checked;
            var side4G3 = document.getElementById("side4G3").checked;

            var side5R = document.getElementById("side5R").checked;
            var side5A = document.getElementById("side5A").checked;
            var side5G1 = document.getElementById("side5G1").checked;
            var side5G2 = document.getElementById("side5G2").checked;
            var side5G3 = document.getElementById("side5G3").checked;

            if (side1R === true) {
                if (side1A === true || side1G1 === true || side1G2 === true || side1G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 1 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side1R").focus();
                    document.getElementById("side1A").focus();
                    document.getElementById("side1G1").focus();
                    document.getElementById("side1G2").focus();
                    document.getElementById("side1G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (side1A === true) {
                if (side1R === true || side1G1 === true || side1G2 === true || side1G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 1 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side1R").focus();
                    document.getElementById("side1A").focus();
                    document.getElementById("side1G1").focus();
                    document.getElementById("side1G2").focus();
                    document.getElementById("side1G3").focus();
                    return false; // code to stop from submitting the form2.
                }

            } else if (side1G1 === true || side1G2 === true || side1G3 === true) {
                if (side1R === true || side1A === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 1 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side1R").focus();
                    document.getElementById("side1A").focus();
                    document.getElementById("side1G1").focus();
                    document.getElementById("side1G2").focus();
                    document.getElementById("side1G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            }

            if (side2R === true) {
                if (side2A === true || side2G1 === true || side2G2 === true || side2G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 2 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side2R").focus();
                    document.getElementById("side2A").focus();
                    document.getElementById("side2G1").focus();
                    document.getElementById("side2G2").focus();
                    document.getElementById("side2G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (side2A === true) {
                if (side2R === true || side2G1 === true || side2G2 === true || side2G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 2 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side2R").focus();
                    document.getElementById("side2A").focus();
                    document.getElementById("side2G1").focus();
                    document.getElementById("side2G2").focus();
                    document.getElementById("side2G3").focus();
                    return false; // code to stop from submitting the form2.
                }

            } else if (side2G1 === true || side2G2 === true || side2G3 === true) {
                if (side2R === true || side2A === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 2 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side2R").focus();
                    document.getElementById("side2A").focus();
                    document.getElementById("side2G1").focus();
                    document.getElementById("side2G2").focus();
                    document.getElementById("side2G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            }

            if (side3R === true) {
                if (side3A === true || side3G1 === true || side3G2 === true || side3G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 3 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side3R").focus();
                    document.getElementById("side3A").focus();
                    document.getElementById("side3G1").focus();
                    document.getElementById("side3G2").focus();
                    document.getElementById("side3G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (side3A === true) {
                if (side3R === true || side3G1 === true || side3G2 === true || side3G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 3 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side3R").focus();
                    document.getElementById("side3A").focus();
                    document.getElementById("side3G1").focus();
                    document.getElementById("side3G2").focus();
                    document.getElementById("side3G3").focus();
                    return false; // code to stop from submitting the form2.
                }

            } else if (side3G1 === true || side3G2 === true || side3G3 === true) {
                if (side3R === true || side3A === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 3 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side3R").focus();
                    document.getElementById("side3A").focus();
                    document.getElementById("side3G1").focus();
                    document.getElementById("side3G2").focus();
                    document.getElementById("side3G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            }

            if (side4R === true) {
                if (side4A === true || side4G1 === true || side4G2 === true || side4G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 4 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side4R").focus();
                    document.getElementById("side4A").focus();
                    document.getElementById("side4G1").focus();
                    document.getElementById("side4G2").focus();
                    document.getElementById("side4G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (side4A === true) {
                if (side4R === true || side4G1 === true || side4G2 === true || side4G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 4 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side4R").focus();
                    document.getElementById("side4A").focus();
                    document.getElementById("side4G1").focus();
                    document.getElementById("side4G2").focus();
                    document.getElementById("side4G3").focus();
                    return false; // code to stop from submitting the form2.
                }

            } else if (side4G1 === true || side4G2 === true || side4G3 === true) {
                if (side4R === true || side4A === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 4 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side4R").focus();
                    document.getElementById("side4A").focus();
                    document.getElementById("side4G1").focus();
                    document.getElementById("side4G2").focus();
                    document.getElementById("side4G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            }

            if (side5R === true) {
                if (side5A === true || side5G1 === true || side5G2 === true || side5G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 5 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side5R").focus();
                    document.getElementById("side5A").focus();
                    document.getElementById("side5G1").focus();
                    document.getElementById("side5G2").focus();
                    document.getElementById("side5G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            } else if (side5A === true) {
                if (side5R === true || side5G1 === true || side5G2 === true || side5G3 === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 5 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side5R").focus();
                    document.getElementById("side5A").focus();
                    document.getElementById("side5G1").focus();
                    document.getElementById("side5G2").focus();
                    document.getElementById("side5G3").focus();
                    return false; // code to stop from submitting the form2.
                }

            } else if (side5G1 === true || side5G2 === true || side5G3 === true) {
                if (side5R === true || side5A === true) {
                    var message = "<td colspan='6' bgcolor='coral'><b>More than one light of Side 5 is checked...</b></td>";
                    $("#message").html(message);
                    document.getElementById("side5R").focus();
                    document.getElementById("side5A").focus();
                    document.getElementById("side5G1").focus();
                    document.getElementById("side5G2").focus();
                    document.getElementById("side5G3").focus();
                    return false; // code to stop from submitting the form2.
                }
            }


            var left_green = document.getElementById("left_green").value;
            if ($.trim(left_green).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>left_green is required...</b></td>";
                $("#message").html(message);
                document.getElementById("left_green").focus();
                return false; // code to stop from submitting the form2.
            }
//            var padestrian_info = document.getElementById("padestrian_info").value;
//            if ($.trim(padestrian_info).length === 0) {
//                var message = "<td colspan='6' bgcolor='coral'><b>padestrian_info is required...</b></td>";
//                $("#message").html(message);
//                document.getElementById("padestrian_info").focus();
//                return false; // code to stop from submitting the form2.
//            }
            var gpio = document.getElementById("gpio").value;
            if ($.trim(gpio).length === 0) {
                var message = "<td colspan='6' bgcolor='coral'><b>GPIO is required...</b></td>";
                $("#message").html(message);
                document.getElementById("gpio").focus();
                return false; // code to stop from submitting the form2.
            }
            if (document.getElementById("clickedButton").value === 'Save AS New') {
                result = confirm("Are you sure you want to save it as New record?");
                return result;
            }
            if (document.getElementById("clickedButton").value === 'Save And Continue') {
                debugger;
                if (plan_no_master === 0) {
                    plan_no_master = document.getElementById("plan_no").value;
                    master = document.getElementById("phase_no").value;
                    phase_time_master = document.getElementById("phase_time").value;
                } else {
                    if (plan_no_master === document.getElementById("plan_no").value) {
                        master = document.getElementById("phase_no").value;
                        phase_time_master = document.getElementById("phase_time").value;
                    }
                }
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

    function check(color, side_no) {
        debugger;
        var sideValue = document.getElementById("side" + side_no + color).checked;
        if (sideValue === true) {
            if (color === "R") {
                document.getElementById("side" + side_no + "A").disabled = true;
                document.getElementById("side" + side_no + "G1").disabled = true;
                document.getElementById("side" + side_no + "G2").disabled = true;
                document.getElementById("side" + side_no + "G3").disabled = true;
            }
            if (color === "A") {
                document.getElementById("side" + side_no + "R").disabled = true;
                document.getElementById("side" + side_no + "G1").disabled = true;
                document.getElementById("side" + side_no + "G2").disabled = true;
                document.getElementById("side" + side_no + "G3").disabled = true;
            }
            if (color === "G1") {
                if (side_no === 1) {
                    if (document.getElementById("side" + 3 + "G1").checked === true) {
                        document.getElementById("side" + side_no + "G2").disabled = true;
                        document.getElementById("side" + 3 + "G2").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G2").checked === true) {
                        document.getElementById("side" + 3 + "G1").disabled = true;
                        document.getElementById("side" + 3 + "G2").disabled = true;
                    }
                }
                if (side_no === 3) {
                    if (document.getElementById("side" + 1 + "G1").checked === true) {
                        document.getElementById("side" + side_no + "G2").disabled = true;
                        document.getElementById("side" + 1 + "G2").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G2").checked === true) {
                        document.getElementById("side" + 3 + "G1").disabled = true;
                        document.getElementById("side" + 3 + "G2").disabled = true;
                    }
                }
                if (side_no === 2) {
                    if (document.getElementById("side" + 4 + "G1").checked === true) {
                        document.getElementById("side" + side_no + "G2").disabled = true;
                        document.getElementById("side" + 4 + "G2").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G2").checked === true) {
                        document.getElementById("side" + 4 + "G1").disabled = true;
                        document.getElementById("side" + 4 + "G2").disabled = true;
                    }
                }
                if (side_no === 4) {
                    if (document.getElementById("side" + 2 + "G1").checked === true) {
                        document.getElementById("side" + side_no + "G2").disabled = true;
                        document.getElementById("side" + 2 + "G2").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G2").checked === true) {
                        document.getElementById("side" + 2 + "G1").disabled = true;
                        document.getElementById("side" + 2 + "G2").disabled = true;
                    }
                }
                document.getElementById("side" + side_no + "R").disabled = true;
                document.getElementById("side" + side_no + "A").disabled = true;
                document.getElementById("side" + side_no + "G3").disabled = true;

            }
            if (color === "G2") {
                if (side_no === 1) {
                    if (document.getElementById("side" + 3 + "G2").checked === true) {
                        document.getElementById("side" + side_no + "G1").disabled = true;
                        document.getElementById("side" + 3 + "G1").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G1").checked === true) {
                        document.getElementById("side" + 3 + "G1").disabled = true;
                        document.getElementById("side" + 3 + "G2").disabled = true;
                    }
                }
                if (side_no === 3) {
                    if (document.getElementById("side" + 1 + "G2").checked === true) {
                        document.getElementById("side" + side_no + "G1").disabled = true;
                        document.getElementById("side" + 1 + "G1").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G1").checked === true) {
                        document.getElementById("side" + 1 + "G1").disabled = true;
                        document.getElementById("side" + 1 + "G2").disabled = true;
                    }
                }
                if (side_no === 2) {
                    if (document.getElementById("side" + 4 + "G2").checked === true) {
                        document.getElementById("side" + side_no + "G1").disabled = true;
                        document.getElementById("side" + 4 + "G1").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G1").checked === true) {
                        document.getElementById("side" + 4 + "G1").disabled = true;
                        document.getElementById("side" + 4 + "G2").disabled = true;
                    }
                }
                if (side_no === 4) {
                    if (document.getElementById("side" + 2 + "G2").checked === true) {
                        document.getElementById("side" + side_no + "G1").disabled = true;
                        document.getElementById("side" + 2 + "G1").disabled = true;
                    } else if (document.getElementById("side" + side_no + "G1").checked === true) {
                        document.getElementById("side" + 2 + "G1").disabled = true;
                        document.getElementById("side" + 2 + "G2").disabled = true;
                    }
                }
                document.getElementById("side" + side_no + "A").disabled = true;
                document.getElementById("side" + side_no + "R").disabled = true;
                document.getElementById("side" + side_no + "G3").disabled = true;
            }
        } else {
            if (color === "R") {
                document.getElementById("side" + side_no + "A").disabled = false;
                document.getElementById("side" + side_no + "G1").disabled = false;
                document.getElementById("side" + side_no + "G2").disabled = false;
                document.getElementById("side" + side_no + "G3").disabled = false;
            }
            if (color === "A") {
                document.getElementById("side" + side_no + "R").disabled = false;
                document.getElementById("side" + side_no + "G1").disabled = false;
                document.getElementById("side" + side_no + "G2").disabled = false;
                document.getElementById("side" + side_no + "G3").disabled = false;
            }
            if (color === "G1") {
                document.getElementById("side" + side_no + "R").disabled = false;
                document.getElementById("side" + side_no + "A").disabled = false;
                document.getElementById("side" + side_no + "G2").disabled = false;
                document.getElementById("side" + side_no + "G3").disabled = false;

            }
            if (color === "G2") {
                document.getElementById("side" + side_no + "R").disabled = false;
                document.getElementById("side" + side_no + "G1").disabled = false;
                document.getElementById("side" + side_no + "A").disabled = false;
                document.getElementById("side" + side_no + "G3").disabled = false;
            }
        }
    }

    function operationSelect(value) {
        debugger;
        uncheckRed('R', 1);
        uncheckRed('R', 2);
        uncheckRed('R', 3);
        uncheckRed('R', 4);
        uncheckRed('R', 5);
        uncheckRed('A', 1);
        uncheckRed('A', 2);
        uncheckRed('A', 3);
        uncheckRed('A', 4);
        uncheckRed('A', 5);
        uncheckRed('G1', 1);
        uncheckRed('G1', 2);
        uncheckRed('G1', 3);
        uncheckRed('G1', 4);
        uncheckRed('G1', 5);
        uncheckRed('G2', 1);
        uncheckRed('G2', 2);
        uncheckRed('G2', 3);
        uncheckRed('G2', 4);
        uncheckRed('G2', 5);
        if (value == 3) {
            checkRed('R', 1);
            checkRed('R', 2);
            checkRed('R', 3);
            checkRed('R', 4);
            checkRed('R', 5);
            ajaxCall("pedestrian", 0, "G0", 0);
            document.getElementById("side_no").disabled = true;
            document.getElementById("G1").disabled = true;
            document.getElementById("G2").disabled = true;
            document.getElementById("G12").disabled = true;
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
        } else if (value == 2) {
            uncheckRed('R', 1);
            uncheckRed('R', 2);
            uncheckRed('R', 3);
            uncheckRed('R', 4);
            uncheckRed('R', 5);
            document.getElementById("side_no").disabled = false;
            document.getElementById("G1").disabled = true;
            document.getElementById("G2").disabled = true;
            document.getElementById("G12").disabled = true;
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
        } else if (value == 1) {
            uncheckRed('R', 1);
            uncheckRed('R', 2);
            uncheckRed('R', 3);
            uncheckRed('R', 4);
            uncheckRed('R', 5);
            document.getElementById("side_no").disabled = false;
            document.getElementById("G1").disabled = false;
            document.getElementById("G2").disabled = false;
            document.getElementById("G12").disabled = false;
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
        } else if (value == 4){
            ajaxCall("amber", 0, "G0", 0);
            document.getElementById("side_no").disabled = true;
            document.getElementById("G1").disabled = true;
            document.getElementById("G2").disabled = true;
            document.getElementById("G12").disabled = true;
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
            sideSelect(0)
             checkRed('A', 1);
            checkRed('A', 2);
            checkRed('A', 3);
            checkRed('A', 4);
            checkRed('A', 5);
        } else if(value == 5) {
            sideSelect(0);
            
             uncheckRed('A', 1);
            uncheckRed('A', 2);
            uncheckRed('A', 3);
            uncheckRed('A', 4);
            uncheckRed('A', 5);
            ajaxCall("amber", 0, "G0", 0);
            document.getElementById("side_no").disabled = true;
            document.getElementById("G1").disabled = true;
            document.getElementById("G2").disabled = true;
            document.getElementById("G12").disabled = true;
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
        }

    }

    function sideSelect(side_no) {
        uncheckRed('R', 1);
        uncheckRed('R', 2);
        uncheckRed('R', 3);
        uncheckRed('R', 4);
        uncheckRed('R', 5);
        uncheckRed('A', 1);
        uncheckRed('A', 2);
        uncheckRed('A', 3);
        uncheckRed('A', 4);
        uncheckRed('A', 5);
        uncheckRed('G1', 1);
        uncheckRed('G1', 2);
        uncheckRed('G1', 3);
        uncheckRed('G1', 4);
        uncheckRed('G1', 5);
        uncheckRed('G2', 1);
        uncheckRed('G2', 2);
        uncheckRed('G2', 3);
        uncheckRed('G2', 4);
        uncheckRed('G2', 5);
        if (side_no == 1) {
            if (document.getElementById("opn").value == 2) {
                document.getElementById("side1A").checked = true;
                checkRed('R', 2);
                checkRed('R', 3);
                checkRed('R', 4);
                checkRed('R', 5);
                ajaxCall("amber", side_no, "G0", 0);
            }
            if (document.getElementById("opn").value == 1) {
                checkRed('R', 2);
                checkRed('R', 4);
                checkRed('R', 5);
            }
        }
        if (side_no == 2) {
            if (document.getElementById("opn").value == 2) {
                document.getElementById("side2A").checked = true;
                checkRed('R', 1);
                checkRed('R', 3);
                checkRed('R', 4);
                checkRed('R', 5);
                ajaxCall("amber", side_no, "G0", 0);
            }
            if (document.getElementById("opn").value == 1) {
                checkRed('R', 1);
                checkRed('R', 3);
                checkRed('R', 5);
            }
        }
        if (side_no == 3) {
            if (document.getElementById("opn").value == 2) {
                document.getElementById("side3A").checked = true;
                checkRed('R', 2);
                checkRed('R', 1);
                checkRed('R', 4);
                checkRed('R', 5);
                ajaxCall("amber", side_no, "G0", 0);
            }
            if (document.getElementById("opn").value == 1) {
                checkRed('R', 2);
                checkRed('R', 4);
                checkRed('R', 5);
            }
        }
        if (side_no == 4) {
            if (document.getElementById("opn").value == 2) {
                document.getElementById("side4A").checked = true;
                checkRed('R', 2);
                checkRed('R', 3);
                checkRed('R', 1);
                checkRed('R', 5);
                ajaxCall("amber", side_no, "G0", 0);
            }
            if (document.getElementById("opn").value == 1) {
                checkRed('R', 1);
                checkRed('R', 3);
                checkRed('R', 5);
            }
        }
        if (side_no == 5) {
            if (document.getElementById("opn").value == 2) {
                document.getElementById("side5A").checked = true;
                checkRed('R', 2);
                checkRed('R', 3);
                checkRed('R', 4);
                checkRed('R', 1);
                ajaxCall("amber", side_no, "G0", 0);
            }
            if (document.getElementById("opn").value == 1) {
                checkRed('R', 2);
                checkRed('R', 4);
                checkRed('R', 1);
                checkRed('R', 3);
            }
        }
    }

    function greenSelect(value) {
        debugger;
        var side_no = document.getElementById("side_no").value;
        document.getElementById("pair1").checked = false;
        document.getElementById("pair2").checked = false;
        if (value == "G12") {
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
            ajaxCall("green", side_no, value, 0);
            if (side_no == 1) {
                document.getElementById("side" + 3 + "G1").checked = false;
                document.getElementById("side" + 3 + "G2").checked = false;
                document.getElementById("side" + 3 + "R").checked = true;
                document.getElementById("side" + 1 + "G1").checked = true;
                document.getElementById("side" + 1 + "G2").checked = true;
            }
            if (side_no == 2) {
                document.getElementById("side" + 4 + "G1").checked = false;
                document.getElementById("side" + 4 + "G2").checked = false;
                 document.getElementById("side" + 4 + "R").checked = true;
                document.getElementById("side" + 2 + "G1").checked = true;
                document.getElementById("side" + 2 + "G2").checked = true;
            }
            if (side_no == 3) {
                document.getElementById("side" + 1 + "G1").checked = false;
                document.getElementById("side" + 1 + "G2").checked = false;
                 document.getElementById("side" + 1 + "R").checked = true;
                document.getElementById("side" + 3 + "G1").checked = true;
                document.getElementById("side" + 3 + "G2").checked = true;
            }
            if (side_no == 4) {
                document.getElementById("side" + 2 + "G1").checked = false;
                document.getElementById("side" + 2 + "G2").checked = false;
                 document.getElementById("side" + 2 + "R").checked = true;
                document.getElementById("side" + 4 + "G1").checked = true;
                document.getElementById("side" + 4 + "G2").checked = true;
            }
            if (side_no == 5) {
                document.getElementById("side" + 5 + "G1").checked = false;
                document.getElementById("side" + 5 + "G2").checked = false;
                document.getElementById("side" + 5 + "G1").checked = true;
                document.getElementById("side" + 5 + "G2").checked = true;
                ajaxCall("green", side_no, value, 0);
            }



        } else if (value === "G1" && side_no == 5) {
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
            document.getElementById("side" + 5 + "G2").checked = false;
            document.getElementById("side" + 5 + "G1").checked = true;
            ajaxCall("green", side_no, value, 0);
        } else if (value === "G2" && side_no == 5) {
            document.getElementById("pair1").disabled = true;
            document.getElementById("pair2").disabled = true;
            document.getElementById("side" + 5 + "G1").checked = false;
            document.getElementById("side" + 5 + "G2").checked = true;
            ajaxCall("green", side_no, value, 0);
        } else {
            document.getElementById("pair1").disabled = false;
            document.getElementById("pair2").disabled = false;
        }

    }

    function pairSelect(pair) {
        if (document.getElementById("side_no").value == 1) {
            var green = document.querySelector('input[name="green"]:checked').value;
            if (green === "G1") {
                document.getElementById("side" + 3 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 1 + "G2").checked = false;
                    document.getElementById("side" + 3 + "G2").checked = false;
                    document.getElementById("side" + 1 + "G1").checked = true;
                    document.getElementById("side" + 3 + "G1").checked = true;
                } else {
                    document.getElementById("side" + 3 + "R").checked = true;
                    document.getElementById("side" + 1 + "G2").checked = false;
                    document.getElementById("side" + 3 + "G2").checked = false;
                    document.getElementById("side" + 1 + "G1").checked = true;
                    document.getElementById("side" + 3 + "G1").checked = false;
                }
            } else if (green === "G2") {
                document.getElementById("side" + 3 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 1 + "G1").checked = false;
                    document.getElementById("side" + 3 + "G1").checked = false;
                    document.getElementById("side" + 1 + "G2").checked = true;
                    document.getElementById("side" + 3 + "G2").checked = true;
                } else {
                    document.getElementById("side" + 3 + "R").checked = true;
                    document.getElementById("side" + 1 + "G1").checked = false;
                    document.getElementById("side" + 3 + "G1").checked = false;
                    document.getElementById("side" + 1 + "G2").checked = true;
                    document.getElementById("side" + 3 + "G2").checked = false;
                }
            }
        }

        if (document.getElementById("side_no").value == 2) {
            var green = document.querySelector('input[name="green"]:checked').value;
            if (green === "G1") {
                document.getElementById("side" + 4 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 2 + "G2").checked = false;
                    document.getElementById("side" + 4 + "G2").checked = false;
                    document.getElementById("side" + 2 + "G1").checked = true;
                    document.getElementById("side" + 4 + "G1").checked = true;
                } else {
                    document.getElementById("side" + 4 + "R").checked = true;
                    document.getElementById("side" + 2 + "G2").checked = false;
                    document.getElementById("side" + 4 + "G2").checked = false;
                    document.getElementById("side" + 2 + "G1").checked = true;
                    document.getElementById("side" + 4 + "G1").checked = false;
                }
            } else if (green === "G2") {
                document.getElementById("side" + 4 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 2 + "G1").checked = false;
                    document.getElementById("side" + 4 + "G1").checked = false;
                    document.getElementById("side" + 2 + "G2").checked = true;
                    document.getElementById("side" + 4 + "G2").checked = true;
                } else {
                    document.getElementById("side" + 4 + "R").checked = true;
                    document.getElementById("side" + 2 + "G1").checked = false;
                    document.getElementById("side" + 4 + "G1").checked = false;
                    document.getElementById("side" + 2 + "G2").checked = true;
                    document.getElementById("side" + 4 + "G2").checked = false;
                }
            }
        }

        if (document.getElementById("side_no").value == 3) {
            var green = document.querySelector('input[name="green"]:checked').value;
            if (green === "G1") {
                document.getElementById("side" + 1 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 3 + "G2").checked = false;
                    document.getElementById("side" + 1 + "G2").checked = false;
                    document.getElementById("side" + 3 + "G1").checked = true;
                    document.getElementById("side" + 1 + "G1").checked = true;
                } else {
                    document.getElementById("side" + 1 + "R").checked = true;
                    document.getElementById("side" + 3 + "G2").checked = false;
                    document.getElementById("side" + 1 + "G2").checked = false;
                    document.getElementById("side" + 3 + "G1").checked = true;
                    document.getElementById("side" + 1 + "G1").checked = false;
                }
            } else if (green === "G2") {
                document.getElementById("side" + 1 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 3 + "G1").checked = false;
                    document.getElementById("side" + 1 + "G1").checked = false;
                    document.getElementById("side" + 3 + "G2").checked = true;
                    document.getElementById("side" + 1 + "G2").checked = true;
                } else {
                    document.getElementById("side" + 1 + "R").checked = true;
                    document.getElementById("side" + 3 + "G1").checked = false;
                    document.getElementById("side" + 1 + "G1").checked = false;
                    document.getElementById("side" + 3 + "G2").checked = true;
                    document.getElementById("side" + 1 + "G2").checked = false;
                }
            }
        }

        if (document.getElementById("side_no").value == 4) {
            var green = document.querySelector('input[name="green"]:checked').value;
            if (green === "G1") {
                document.getElementById("side" + 2 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 4 + "G2").checked = false;
                    document.getElementById("side" + 2 + "G2").checked = false;
                    document.getElementById("side" + 4 + "G1").checked = true;
                    document.getElementById("side" + 2 + "G1").checked = true;
                } else {
                    document.getElementById("side" + 2 + "R").checked = true;
                    document.getElementById("side" + 4 + "G2").checked = false;
                    document.getElementById("side" + 2 + "G2").checked = false;
                    document.getElementById("side" + 4 + "G1").checked = true;
                    document.getElementById("side" + 2 + "G1").checked = false;
                }
            } else if (green === "G2") {
                document.getElementById("side" + 2 + "R").checked = false;
                if (pair == 1) {
                    document.getElementById("side" + 4 + "G1").checked = false;
                    document.getElementById("side" + 2 + "G1").checked = false;
                    document.getElementById("side" + 4 + "G2").checked = true;
                    document.getElementById("side" + 2 + "G2").checked = true;
                } else {
                    document.getElementById("side" + 2 + "R").checked = true;
                    document.getElementById("side" + 4 + "G1").checked = false;
                    document.getElementById("side" + 2 + "G1").checked = false;
                    document.getElementById("side" + 4 + "G2").checked = true;
                    document.getElementById("side" + 2 + "G2").checked = false;
                }
            }
        }

        ajaxCall("green", document.getElementById("side_no").value, document.querySelector('input[name="green"]:checked').value, pair);


    }

    function ajaxCall(operation, side_no, green_type, pairing) {
        debugger;
        var plan_no = document.getElementById("plan_no").value;
        $.ajax({url: "phaseinfoCont",
            async: true,
            data: {task: "ajaxCall", operation: operation, side_no: side_no, green_type: green_type, pairing: pairing, plan_no: plan_no},
            dataType: 'json',
            success: function (response_data) {
                debugger;
                document.getElementById("phase_time").value = response_data.phase_time;
            }

        });            //alert(response_data);
    }

//    

    var plan_no_master = 0;
    var phase_time_master = 0;

    function saveCont() {
        debugger;
        if (plan_no_master === 0) {
            plan_no_master = document.getElementById("plan_no").value;
            master = document.getElementById("phase_no").value;
            phase_time_master = document.getElementById("phase_time").value;
        } else {
            if (plan_no_master === document.getElementById("plan_no").value) {
                master = document.getElementById("phase_no").value;
                phase_time_master = document.getElementById("phase_time").value;
            }
        }

        document.forms['form'].submit();

    }

    function summitButton() {
        alert();
        $.ajax({url: "phaseinfoCont",
            async: true,
            data: {task: "summitPhase", phase_no: master, phase_time: phase_time_master},
            dataType: 'json',
            success: function (response_data) {
                //document.getElementById("phase_time").value = response_data.phase_time;
            }

        });
    }

    function matchPlan(val) {
        debugger;
        if (plan_no > 0) {
            if (val !== plan_no) {
                alert("Please submit previous Phase information related to Plan No" + plan_no);
            }
        }

    }

    function checkRed(color, side_no) {
        document.getElementById("side" + side_no + color).checked = true;
    }

    function uncheckRed(color, side_no) {
        document.getElementById("side" + side_no + color).checked = false;
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
                                                Phase Info Details
                                            </td>
                                        </tr>
                                    </table> </td> </tr>

                            <tr>
                                <td>
                                    <div class="table-responsive" style="width: 990px;max-height: 340px;overflow: auto;margin-bottom: 20px">
                                        <form name="form1" action="phaseinfoCont" method="post">
                                            <table class="reference" border="1" align="center">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Junction ID</th>
                                                    <th class="heading">Junction Name</th>
                                                    <th class="heading">Plan Number</th>
                                                    <th class="heading">Phase Number</th>
                                                    <th class="heading">Phase Time</th>
                                                    <th class="heading">Green Time Side 1</th>
                                                    <th class="heading">Green Time Side 2</th>
                                                    <th class="heading">Green Time Side 3</th>
                                                    <th class="heading">Green Time Side 4</th>
                                                    <th class="heading">Green Time Side 5</th>
                                                    <th class="heading">Side Time 13</th>
                                                    <th class="heading">Side Time 24</th>
                                                    <th class="heading">Side Time 5</th>
                                                    <th class="heading">Left Green</th>
                                                    <th class="heading">Pedestrian Info</th>
                                                    <th class="heading">GPIO</th>
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['junction']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor = '#E3ECF3' onmouseout=this.style.backgroundColor = 'white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" name="phase_info_id${loopCounter.count}" id="phase_info_id${loopCounter.count}" value="${list.phaseInfoId}">
                                                            <input type="hidden" name="junction_id${loopCounter.count}" value="${list.junction_id}">
                                                            <input type="hidden" name="junction_name${loopCounter.count}" value="${list.junction_name}">
                                                            <input type="hidden" name="loopCounter" value="${loopCounter.count}">
<!--                                                            <input type="hidden" id="revision_no${loopCounter.count}" value="${list.revision_no}">-->
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.junction_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.planNo}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.phaseNo}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.phaseTime}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.green1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.green2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.green3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.green4}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.green5}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side13}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side24}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.side5}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.left_green}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.padestrian_info}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.GPIO}</td>
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
                                    <div style="
                                         overflow: scroll;
                                         overflow-x: hidden;
                                         height: 450px;
                                         ">
                                        <form name="form" id="form2"  action="phaseinfoCont" method="post" onsubmit="return verify()">
                                            <table name="table" class="reference"  border="1" align="center">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="22" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="10" class="incHeight">
                                                        <!--<input class="button" type="submit" id="SAVE" name="task" value="Save" onclick="setStatus(id)" disabled />-->
                                                        <input class="button" type="reset" id="NEW" name="task" value="New" onclick="makeEditable(id);
                                                                setDefaullts();"/>
                                                        <!--<input class="button" type="button" id="EDIT" name="task" value="Edit" onclick="makeEditable(id)"/>-->
                                                        <input class="button" type="submit" id="DELETE" name="task" value="Delete" onclick="setStatus(id);makeEditable(id)" />
                                                        <input class="button" type="submit" name="task" id="SAVE" value="SAVE" onclick="setStatus(id)" disabled/>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td align='center' colspan="10" class="incHeight">
                                                        <input class="button" type="submit" id="CONTINUE" name="task" value="Save And Continue" onclick="setStatus(id); setDefaullts();" disabled/>
                                                        <input class="button" type="submit" id="SUBMIT" name="task" value="Save And Submit" onclick="setStatus(id);setDefaullts();" disabled/>
                                                    </td>

                                                </tr>
                                                <tr align="center" class="incHeight">
                                                    <th class="heading" align="center">Junction Name</th>
                                                    <td>
                                                        <input class="input form-control" type="hidden" id="junction_id" name="junction_id" value="${junctionId}" readonly>
                                                        <input class="input form-control" type="hidden" id="phase_info_id" name="phase_info_id" value="" readonly>
                                                        <c:if test="${not empty junctionName}">
                                                            <input class="input form-control"  type="text" id="junction_name" name="junction_name" size="15" value="${junctionName}" disabled readonly>
                                                        </c:if>
                                                        <c:if test="${empty junctionName}">
                                                            <input class="input form-control"  type="text" id="junction_name" name="junction_name" size="15" value="JAYA" disabled readonly>
                                                        </c:if>
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Plan Number</th>
                                                    <td>
                                                        <input class="input form-control"  type="text" id="plan_no" name="plan_no" value="" size="20"disabled onkeyup="matchPlan(this.value)">
                                                    </td>
                                                </tr>

                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Phase Number</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="phase_no" name="phase_no" value="" disabled>
                                                    </td>

                                                </tr>



                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Operation</th>
                                                    <td>
                                                        <select id="opn" name="opn" onchange="operationSelect(this.value)" disabled>
                                                            <option value="0">--Select--</option>
                                                            <option value="1">GREEN</option>
                                                            <option value="2">AMBER</option>
                                                            <option value="3">PEDESTRIAN</option>
                                                            <option value="4">BLINKER ON</option>
                                                            <option value="5">BLINKER OFF</option>
                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Side Number</th>
                                                    <td>
                                                        <select id="side_no" name="side_no" disabled onchange="sideSelect(this.value)">
                                                            <option value="0">--Select--</option>
                                                            <option value="1">1</option>
                                                            <option value="2">2</option>
                                                            <option value="3">3</option>
                                                            <option value="4">4</option>
                                                            <option value="5">5</option>
                                                        </select>
                                                    </td>

                                                </tr>

                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Green Side</th>
                                                    <td>
                                                        <input type="radio" class="green" id="G1" name="green" value="G1"  disabled onclick="greenSelect(this.value)">G1
                                                        <input type="radio" class="green" id="G2" name="green" value="G2" disabled onclick="greenSelect(this.value)">G2
                                                        <input type="radio" class="green" id="G12" name="green" value="G12" disabled onclick="greenSelect(this.value)">G12

                                                    </td>

                                                </tr>
                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Is Paired</th>
                                                    <td>
                                                        <input type="radio" id="pair1" name="pair" value="1" disabled onclick="pairSelect(this.value)">Yes
                                                        <input type="radio" id="pair2" name="pair" value="0" disabled onclick="pairSelect(this.value)">No

                                                    </td>

                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading" colspan="2">Value of Side 13</th>
                                                </tr>

                                                <tr align="center">
                                                    <td>
                                                        <table class="table table-borderless">
                                                            <tr>
                                                                <th class="heading" colspan="5">Side 1</th>
                                                            </tr>  
                                                            <tr>
                                                                <td>R</td>
                                                                <td>A</td>
                                                                <td>G1</td>
                                                                <td>G2</td>
                                                                <td>G3</td>
                                                            </tr> 
                                                            <tr>
                                                                <td><input class="myCheck" onclick="check('R', 1)" type="checkbox" name="side1R" id="side1R" value="0" disabled></td>
                                                                <td><input class="myCheck" onclick="check('A', 1)" value="0" type="checkbox" name="side1A" id="side1A" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G1', 1)" value="0" type="checkbox" name="side1G1" id="side1G1" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G2', 1)" value="0" type="checkbox" name="side1G2" id="side1G2" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G3', 1)" value="0" type="checkbox" name="side1G3" id="side1G3" disabled></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td>
                                                        <table class="table table-borderless">
                                                            <tr>
                                                                <th class="heading" colspan="5">Side 3</th>
                                                            </tr>  
                                                            <tr>
                                                                <td>R</td>
                                                                <td>A</td>
                                                                <td>G1</td>
                                                                <td>G2</td>
                                                                <td>G3</td>
                                                            </tr> 
                                                            <tr>
                                                                <td><input class="myCheck" onclick="check('R', 3)" value="0" type="checkbox"  name="side3R" id="side3R" disabled></td>
                                                                <td><input class="myCheck" onclick="check('A', 3)" value="0" type="checkbox" name="side3A" id="side3A" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G1', 3)" value="0" type="checkbox" name="side3G1" id="side3G1" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G2', 3)" value="0" type="checkbox" name="side3G2" id="side3G2" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G3', 3)" value="0" type="checkbox" name="side3G3" id="side3G3" disabled></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading" colspan="2">Value of Side 24</th>
                                                </tr>
                                                <tr align="center" class="incHeight">
                                                    <td>
                                                        <table class="table table-borderless">
                                                            <tr>
                                                                <th class="heading" align="center" colspan="5">Side 2</th>
                                                            </tr>  
                                                            <tr>
                                                                <td>R</td>
                                                                <td>A</td>
                                                                <td>G1</td>
                                                                <td>G2</td>
                                                                <td>G3</td>
                                                            </tr> 
                                                            <tr>
                                                                <td><input class="myCheck" onclick="check('R', 2)" value="0" type="checkbox" name="side2R" id="side2R" disabled></td>
                                                                <td><input class="myCheck" onclick="check('A', 2)" value="0" type="checkbox" name="side2A" id="side2A" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G1', 2)" value="0" type="checkbox" name="side2G1" id="side2G1" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G2', 2)" value="0" type="checkbox" name="side2G2" id="side2G2" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G3', 2)" value="0" type="checkbox" name="side2G3" id="side2G3" disabled></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td>
                                                        <table class="table table-borderless">
                                                            <tr>
                                                                <th class="heading" colspan="5">Side 4</th>
                                                            </tr>  
                                                            <tr>
                                                                <td>R</td>
                                                                <td>A</td>
                                                                <td>G1</td>
                                                                <td>G2</td>
                                                                <td>G3</td>
                                                            </tr> 
                                                            <tr>
                                                                <td><input class="myCheck" onclick="check('R', 4)" value="0" type="checkbox" name="side4R" id="side4R" disabled></td>
                                                                <td><input class="myCheck" onclick="check('A', 4)" value="0" type="checkbox" name="side4A" id="side4A" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G1', 4)" value="0" type="checkbox" name="side4G1" id="side4G1" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G2', 4)" value="0" type="checkbox" name="side4G2" id="side4G2" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G3', 4)" value="0" type="checkbox" name="side4G3" id="side4G3" disabled></td>
                                                            </tr>
                                                        </table>
                                                    </td>

                                                </tr>
                                                <tr align="center">
                                                    <th  class="heading" colspan="2">Value of Side 5</th>
                                                </tr>
                                                <tr align="center" class="incHeight">
                                                    <td colspan="2">
                                                        <table class="table table-borderless">
                                                            <tr>
                                                                <th class="heading" colspan="5">Side 5</th>
                                                            </tr>  
                                                            <tr>
                                                                <td>R</td>
                                                                <td>A</td>
                                                                <td>G1</td>
                                                                <td>G2</td>
                                                                <td>G3</td>
                                                            </tr> 
                                                            <tr>
                                                                <td><input class="myCheck" onclick="check('R', 5)" value="0" type="checkbox" name="side5R" id="side5R" disabled></td>
                                                                <td><input class="myCheck" onclick="check('A', 5)" value="0" type="checkbox" name="side5A" id="side5A" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G1', 5)" value="0" type="checkbox" name="side5G1" id="side5G1" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G2', 5)" value="0" type="checkbox" name="side5G2" id="side5G2" disabled></td>
                                                                <td><input class="myCheck" onclick="check('G3', 5)" value="0" type="checkbox" name="side5G3" id="side5G3" disabled></td>
                                                            </tr>
                                                        </table>
                                                    </td>

                                                </tr>

                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">Phase Time</th>
                                                    <td colspan="3">
                                                        <input class="input form-control"  size="15" type="text" id="phase_time" name="phase_time" value="" disabled><br>
                                                    </td>
                                                </tr>
                                                <!--                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center" colspan="2">Green Time</th>
                                                                                                </tr>
                                                                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center">Side 1</th>
                                                                                                    <td>
                                                                                                        <input class="input form-control"  size="15" type="text" id="green_one" name="green_one" value="" disabled><br>
                                                                                                    </td>
                                                                                                </tr>
                                                
                                                                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center">Side 2</th>
                                                                                                    <td>
                                                                                                        <input class="input form-control"  size="15" type="text" id="green_two" name="green_two" value="" disabled><br>
                                                                                                    </td>
                                                                                                </tr>
                                                
                                                                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center">Side 3</th>
                                                                                                    <td>
                                                                                                        <input class="input form-control"  size="15" type="text" id="green_three" name="green_three" value="" disabled><br>
                                                                                                    </td>
                                                
                                                                                                </tr>
                                                                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center">Side 4</th>
                                                                                                    <td>
                                                                                                        <input class="input form-control"  size="15" type="text" id="green_four" name="green_four" value="" disabled><br>
                                                                                                    </td>
                                                                                                </tr>
                                                
                                                                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center">Side 5</th>
                                                                                                    <td>
                                                                                                        <input class="input form-control"  size="15" type="text" id="green_five" name="green_five" value="" disabled><br>
                                                                                                    </td>
                                                
                                                                                                </tr>-->
                                                <!--                                                <tr align="center" class="incHeight">
                                                                                                    <th  class="heading"  align="center">Pedestrian Info</th>
                                                                                                    <td>
                                                                                                        <input class="input form-control"  type="text" id="padestrian_info" name="padestrian_info" value="" maxlength="2" disabled><br>
                                                                                                    </td>
                                                                                                </tr>-->
                                                <tr align="center" class="incHeight">
                                                    <th  class="heading" align="center">Left Green</th>
                                                    <td>
                                                        <input class="input form-control" size="15" type="text" id="left_green" name="left_green" value="" maxlength="16" disabled><br>
                                                    </td>
                                                </tr>



                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center">GPIO</th>
                                                    <td>
                                                        <input class="input form-control"  type="text" id="gpio" name="gpio" maxlength="2" disabled><br>
                                                    </td>

                                                </tr>
                                                <tr align="center" class="incHeight">
                                                    <th  class="heading"  align="center" >Remark</th>
                                                    <td colspan="3">
                                                        <input class="input form-control"  type="text" id="remark" name="remark" maxlength="30" disabled><br>
                                                    </td>
                                                </tr>

                                               
                                                <!--                                                <tr id="submit_coloumn" style="display: none" align="center" class="incHeight">
                                                                                                    <td>
                                                                                                        <input class="button" type="button" id="CONTINUE" name="task" value="Continue" onclick="makeEditable(id);
                                                                                                            setDefaullts();" style="display: none"/>
                                                                                                        <input class="button" type="submit" id="SUBMIT" name="task" value="submit" onclick="setStatus(id);
                                                                                                            setDefaullts();" style="display: none"/>
                                                                                                </tr>-->

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
