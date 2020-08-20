<%--
    Document   : timeShower
    Created on : Jun 5, 2012, 12:28:18 PM
    Author     : Tarun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modem Current Time</title>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            var doc = document;
            $( document ).ready(function() {
                setActivity();  
            });
            var globalVar = 0;
            var refreshSide1GlobalVar = 0;
            var refreshSide2GlobalVar = 0;
            var refreshSide3GlobalVar = 0;
            var refreshSide4GlobalVar = 0;
            var temGreenTime =0;
            var delay = 0;
            var sys_time = '';
            var time = 0;
            var side1Time = 0, side2Time = 0, side3Time = 0, side4Time = 0;
            var current_min = 0;
            function loadContents() {

                //                greenTime = doc.getElementById("side1_green_time").value;// in minutes
                //                if(greenTime != 0 ){
                //                    time = (+(hours*60*60)) + (+(mins*60)) + (+secs); //time is 3:50
                //                    current_min = (+time) + (+(greenTime*60));
                //                    doc.getElementById("timeToDisplay").value = (greenTime*60);
                //                    //                     alert("time+currentTime ==" + time + " , " + current_min +"sys_time " +sys_time);
                //                }else{
                //                    time = (+(onTimeHr%12)*60*60) + (+(onTimeMin*60)) + (+onTimeSec);
                //                    current_min = (+(OffTimeHr%12)*60*60) + (+(OffTimeMin*60)) + (+offTimeSec);
                //                    var calculatedTime = current_min - time;
                //                    doc.getElementById("timeToDisplay").value = calculatedTime;
                //                    //                    alert("time" +time + "cuttent_min" +current_min + "timetodisplay" + doc.getElementById("timeToDisplay").value);
                //                }

                //                alert("current min" +current_min + "time to display" +timeToDisplay);
                //                alert("systime" +sys_time + "current min" +current_min +"clearance time" +clearanceTime);

                //                 alert("real hour:" + real_hour + " , " + "Hour:" +hour + " , " + sys_time +" , "+ current_min);
                //                alert(doc.getElementById("side1_green_time").value);
                //                alert(delay);

                //$.ajax({url: "ts_statusShowerCont", async: true, data: "task=jQueryRequest", success: function(response_data) {
                //        $("#currentTime").html(response_data);
                //    }
                //});
                var current_junction_id = doc.getElementById("junctionId").value;               
          
                $.ajax({url: "ts_statusShowerCont", async: true, data: "task=getLatestStatus&current_junction_id="+current_junction_id, dataType:'json', success: function(response_data) {
                        //alert(response_data);
                        if(response_data == ''){
                        }else{                            
                            $("#junction_id").val(response_data.junction_id);
                            $("#program_version_no").val(response_data.program_version_no);
                            $("#fileNo").val(response_data.fileNo);
                            $("#functionNo").val(response_data.functionNo);
                            $("#selectedActivity").val(response_data.activity);
                            $("#side_no").val(response_data.side_no);
                            $("#plan_no").val(response_data.plan_no);
                            $("#junctionName").val(response_data.junctionName);
                            $("#sideName").val(response_data.sideName);
                            $("#onTime").val(response_data.onTime);
                            $("#offTime").val(response_data.offTime);
                            $("#mode").val(response_data.mode);
                            $("#side1Name").val(response_data.side1Name);
                            $("#side2Name").val(response_data.side2Name);
                            $("#side3Name").val(response_data.side3Name);
                            $("#side4Name").val(response_data.side4Name);
                            $("#side5Name").val(response_data.side5Name);
                            $("#side1Time").val(response_data.side1Time);
                            $("#side2Time").val(response_data.side2Time);
                            $("#side3Time").val(response_data.side3Time);
                            $("#side4Time").val(response_data.side4Time);
                            $("#side1LeftStatus").val(response_data.side1LeftStatus);
                            $("#side1RightStatus").val(response_data.side1RightStatus);
                            $("#side1UpStatus").val(response_data.side1UpStatus);
                            $("#side1DownStatus").val(response_data.side1DownStatus);
                            $("#side2LeftStatus").val(response_data.side2LeftStatus);
                            $("#side2RightStatus").val(response_data.side2RightStatus);
                            $("#side2UpStatus").val(response_data.side2UpStatus);
                            $("#side2DownStatus").val(response_data.side2DownStatus);
                            $("#side3LeftStatus").val(response_data.side3LeftStatus);
                            $("#side3RightStatus").val(response_data.side3RightStatus);
                            $("#side3UpStatus").val(response_data.side3UpStatus);
                            $("#side3DownStatus").val(response_data.side3DownStatus);
                            $("#side4LeftStatus").val(response_data.side4LeftStatus);
                            $("#side4RightStatus").val(response_data.side4RightStatus);
                            $("#side4UpStatus").val(response_data.side4UpStatus);
                            $("#side4DownStatus").val(response_data.side4DownStatus);
                            $("#juncHr").val(response_data.juncHr);
                            $("#juncMin").val(response_data.juncMin);
                            $("#juncDat").val(response_data.juncDat);
                            $("#juncMonth").val(response_data.juncMonth);
                            $("#juncYear").val(response_data.juncYear);
                        }
                    }
                });
                ChangeStatus();
                setActivity();
               setTimeout("loadContents()", 500);//1 * 50);
//                setInterval("changeActivityFn()",500);
            }

            function ChangeStatus(){
                var d = new Date();
                var hour = d.getHours();
                var min = d.getMinutes();
                var  sec = d.getSeconds();
                var real_hour = hour%12;
                sys_time = (real_hour*60*60) + (min*60) + sec;
                //var onTimeHr = doc.getElementById("onTimeHr").value;
                //var onTimeMin = doc.getElementById("onTimeMin").value;
                //var onTimeSec = doc.getElementById("onTimeSec").value;
                //var OffTimeHr = doc.getElementById("OffTimeHr").value;
                //var OffTimeMin = doc.getElementById("OffTimeMin").value;
                //var offTimeSec = doc.getElementById("offTimeSec").value;
                //                var hours =doc.getElementById("hours").value;
                //                var mins =doc.getElementById("mins").value;
                //                var secs =doc.getElementById("secs").value;
                var functionNo = doc.getElementById("functionNo").value;
                //                alert("functionNo -" +functionNo + " " );
                //                alert("functionNo" +functionNo);
                $("#plan_no_td").html(doc.getElementById("plan_no").value);
                $("#mode_td").html(doc.getElementById("mode").value);
                $("#onTime_td").html(doc.getElementById("onTime").value);
                $("#offTime_td").html(doc.getElementById("offTime").value);
                $("#date_time_td").html(doc.getElementById("juncHr").value+":"+doc.getElementById("juncMin").value+"  "+doc.getElementById("juncDat").value+"-"+doc.getElementById("juncMonth").value);
                if(functionNo == 8){
                    var side1LeftStatus = doc.getElementById("side1LeftStatus").value;
                    var side1RightStatus = doc.getElementById("side1RightStatus").value;
                    var side1UpStatus = doc.getElementById("side1UpStatus").value;
                    var side1DownStatus = doc.getElementById("side1DownStatus").value;
                    var side2LeftStatus = doc.getElementById("side2LeftStatus").value;
                    var side2RightStatus = doc.getElementById("side2RightStatus").value;
                    var side2UpStatus = doc.getElementById("side2UpStatus").value;
                    var side2DownStatus = doc.getElementById("side2DownStatus").value;
                    var side3LeftStatus = doc.getElementById("side3LeftStatus").value;
                    var side3RightStatus = doc.getElementById("side3RightStatus").value;
                    var side3UpStatus = doc.getElementById("side3UpStatus").value;
                    var side3DownStatus = doc.getElementById("side3DownStatus").value;
                    var side4LeftStatus = doc.getElementById("side4LeftStatus").value;
                    var side4RightStatus = doc.getElementById("side4RightStatus").value;
                    var side4UpStatus = doc.getElementById("side4UpStatus").value;
                    var side4DownStatus = doc.getElementById("side4DownStatus").value;
                    if(side1LeftStatus == 1){
                        $("#signal_upper_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else{
                        $("#signal_upper_down").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side1RightStatus == 1){
                        $("#signal_upper_left").html(doc.getElementById("div_yellowSignal").innerHTML);
                    }else{
                        $("#signal_upper_left").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side1UpStatus == 1){
                        $("#signal_upper_up").html(doc.getElementById("green_up").innerHTML);
                    }else{
                        $("#signal_upper_up").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side1DownStatus == 1){
                        $("#signal_upper_right").html(doc.getElementById("green_right").innerHTML);
                    }else{
                        $("#signal_upper_right").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side1LeftStatus == 1  || side1UpStatus == 1 || side1DownStatus == 1){
                        //                    refreshCounterUpper();
                        //                    refreshSide1GlobalVar = 1;
                        $("#side1").html(doc.getElementById("side1Name").value);
                        $("#side1_span").html(doc.getElementById("side1Name").value);
                        $("#label_upper").html(doc.getElementById("side1Time").value);
                    }else if(side1RightStatus == 1){
                        $("#side1").html(doc.getElementById("side1Name").value);
                        $("#side1_span").html(doc.getElementById("side1Name").value);
                        //                    refreshSide1GlobalVar = 0;
                        //                    side1Time = 0;
                    }
                    if(side2LeftStatus == 1){
                        $("#signal_left_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else{
                        $("#signal_left_down").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side2RightStatus == 1){
                        $("#signal_left_left").html(doc.getElementById("div_yellowSignal").innerHTML);
                    }else{
                        $("#signal_left_left").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side2UpStatus == 1){
                        $("#signal_left_up").html(doc.getElementById("green_up").innerHTML);
                    }else{
                        $("#signal_left_up").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side2DownStatus == 1){
                        $("#signal_left_right").html(doc.getElementById("green_right").innerHTML);
                    }else{
                        $("#signal_left_right").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side2LeftStatus == 1  || side2UpStatus == 1 || side2DownStatus == 1){
                        //                    refreshCounterLeft();
                        //                    refreshSide2GlobalVar = 1;
                        $("#side2").html(doc.getElementById("side2Name").value);
                        $("#side2_span").html(doc.getElementById("side2Name").value);
                        $("#label_left").html(doc.getElementById("side2Time").value);
                    }else if(side2RightStatus == 1){
                        //                    refreshSide2GlobalVar = 0;
                        //                    side2Time = 0;
                        $("#side2").html(doc.getElementById("side2Name").value);
                        $("#side2_span").html(doc.getElementById("side2Name").value);
                    }
                    if(side3LeftStatus == 1){
                        $("#signal_right_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else{
                        $("#signal_right_down").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side3RightStatus == 1){
                        $("#signal_right_left").html(doc.getElementById("div_yellowSignal").innerHTML);
                    }else{
                        $("#signal_right_left").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side3UpStatus == 1){
                        $("#signal_right_up").html(doc.getElementById("green_up").innerHTML);
                    }else{
                        $("#signal_right_up").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side3DownStatus == 1){
                        $("#signal_right_right").html(doc.getElementById("green_right").innerHTML);
                    }else{
                        $("#signal_right_right").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side3LeftStatus == 1  || side3UpStatus == 1 || side3DownStatus == 1){
                        //                    refreshCounterRight();
                        //                    refreshSide3GlobalVar = 1;
                        $("#side3").html(doc.getElementById("side3Name").value);
                        $("#side3_span").html(doc.getElementById("side3Name").value);
                        $("#label_right").html(doc.getElementById("side3Time").value);
                    }else if(side3RightStatus == 1){
                        //  alert("globalVar -ELSE--"+globalVar);
                        //                    side3Time = 0;
                        //                    refreshSide3GlobalVar = 0;
                        $("#side3").html(doc.getElementById("side3Name").value);
                        $("#side3_span").html(doc.getElementById("side3Name").value);
                    }
                    if(side4LeftStatus == 1){
                        $("#signal_lower_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else{
                        $("#signal_lower_down").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side4RightStatus == 1){
                        $("#signal_lower_left").html(doc.getElementById("div_yellowSignal").innerHTML);
                    }else{
                        $("#signal_lower_left").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side4UpStatus == 1){
                        $("#signal_lower_up").html(doc.getElementById("green_up").innerHTML);
                    }else{
                        $("#signal_lower_up").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side4DownStatus == 1){
                        $("#signal_lower_right").html(doc.getElementById("green_right").innerHTML);
                    }else{
                        $("#signal_lower_right").html(doc.getElementById("div_greySignal").innerHTML);
                    }if(side4LeftStatus == 1  || side4UpStatus == 1 || side4DownStatus == 1){
                        //                    refreshCounterDown();
                        //                    refreshSide4GlobalVar = 1;
                        $("#side4").html(doc.getElementById("side4Name").value);
                        $("#side4_span").html(doc.getElementById("side4Name").value);
                        $("#label_lower").html(doc.getElementById("side4Time").value);
                    }else if(side4RightStatus == 1){
                        //   alert("globalVar -2nd ELSE--"+globalVar);
                        //                    refreshSide4GlobalVar = 0;
                        //                    side4Time = 0;
                        $("#side4").html(doc.getElementById("side4Name").value);
                        $("#side4_span").html(doc.getElementById("side4Name").value);
                    }
                }
                if(functionNo == 6){
                    var sideNo = doc.getElementById("sideNo").value;
                    //                    alert(sideNo);
                    if(sideNo == 1){
                        $("#signal_upper_right").html(doc.getElementById("green_left").innerHTML);
                        $("#signal_upper_left").html(doc.getElementById("green_right").innerHTML);
                        $("#signal_upper_up").html(doc.getElementById("green_down").innerHTML);
                        $("#signal_upper_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else{
                        $("#signal_upper_right").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_upper_left").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_upper_up").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_upper_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }
                    if(sideNo == 2){
                        $("#signal_left_left").html(doc.getElementById("green_up").innerHTML);
                        $("#signal_left_right").html(doc.getElementById("green_down").innerHTML);
                        $("#signal_left_up").html(doc.getElementById("green_right").innerHTML);
                        $("#signal_left_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else {
                        $("#signal_left_left").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_left_right").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_left_up").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_left_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }
                    if(sideNo == 3){
                        $("#signal_right_left").html(doc.getElementById("green_down").innerHTML);
                        $("#signal_right_right").html(doc.getElementById("green_up").innerHTML);
                        $("#signal_right_up").html(doc.getElementById("green_left").innerHTML);
                        $("#signal_right_down").html(doc.getElementById("div_redSignal").innerHTML);
                    } else{
                        $("#signal_right_left").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_right_right").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_right_up").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_right_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }
                    if(sideNo == 4){
                        $("#signal_lower_left").html(doc.getElementById("green_left").innerHTML);
                        $("#signal_lower_right").html(doc.getElementById("green_right").innerHTML);
                        $("#signal_lower_up").html(doc.getElementById("green_up").innerHTML);
                        $("#signal_lower_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }else{
                        $("#signal_lower_left").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_lower_right").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_lower_up").html(doc.getElementById("div_greySignal").innerHTML);
                        $("#signal_lower_down").html(doc.getElementById("div_redSignal").innerHTML);
                    }
                    $("#side1").html(doc.getElementById("side1Name").value);
                    $("#side1_span").html(doc.getElementById("side1Name").value);
                    $("#side2").html(doc.getElementById("side2Name").value);
                    $("#side2_span").html(doc.getElementById("side2Name").value);
                    $("#side3").html(doc.getElementById("side3Name").value);
                    $("#side3_span").html(doc.getElementById("side3Name").value);
                    $("#side4").html(doc.getElementById("side4Name").value);
                    $("#side4_span").html(doc.getElementById("side4Name").value);
                }
            }

            function refreshCounterUpper(){
                if(refreshSide1GlobalVar == 0){
                    side1Time = doc.getElementById("side1Time").value;
                }
                if(side1Time != 0){
                    side1Time = side1Time - 1;
                    doc.getElementById("refreshCounterTime").value = side1Time;
                }
            }
            function refreshCounterLeft(){
                if(refreshSide2GlobalVar == 0){
                    side2Time = doc.getElementById("side2Time").value;
                }
                if(side2Time > 0){
                    side2Time = side2Time - 1;
                    doc.getElementById("refreshCounterTime1").value = side2Time;
                }
            }
            function refreshCounterRight(){
                if(refreshSide3GlobalVar == 0){
                    side3Time = doc.getElementById("side3Time").value;
                }
                if(side3Time > 0){
                    side3Time = side3Time - 1;
                    doc.getElementById("refreshCounterTime2").value = side3Time;
                }
            }
            function refreshCounterDown(){
                if(refreshSide4GlobalVar == 0){
                    side4Time = doc.getElementById("side4Time").value;
                }
                if(side4Time > 0){
                    side4Time = side4Time - 1;
                    doc.getElementById("refreshCounterTime3").value = side4Time;
                }
            }

            function changeGreenTime(time, current_min){
                var delay;
                if(globalVar == 0){
                    //                    hour = d.getHours();
                    //                    min = d.getMinutes();
                    //                    sec = d.getSeconds();
                    //                    real_hour = hour%12;
                    //                    sys_time = (real_hour*60*60) + (min*60) + sec;
                    //                    var current_min = 0;
                    //                    var onTimeHr = doc.getElementById("onTimeHr").value;
                    //                    var onTimeMin = doc.getElementById("onTimeMin").value;
                    //                    var onTimeSec = doc.getElementById("onTimeSec").value;
                    //                    var OffTimeHr = doc.getElementById("OffTimeHr").value;
                    //                    var OffTimeMin = doc.getElementById("OffTimeMin").value;
                    //                    var offTimeSec = doc.getElementById("offTimeSec").value;
                    //                    greenTime = doc.getElementById("side1_green_time").value;// in minutes
                    //                    if(greenTime != 0 ){
                    //                        time = 300 + 00; //time is 3:50
                    //                        current_min = (+time) + (+greenTime);
                    //                        doc.getElementById("timeToDisplay").value = greenTime;
                    //
                    //                    }else{
                    //                        time = (+(onTimeHr%12)*60*60) + (+(onTimeMin*60)) + (+onTimeSec);
                    //                        current_min = (+(OffTimeHr%12)*60*60) + (+(OffTimeMin*60)) + (+offTimeSec);
                    //                        var calculatedTime = current_min - time;
                    //                        doc.getElementById("timeToDisplay").value = calculatedTime;
                    //                        alert("sys_time :" + sys_time + "time: " +time + "current_min: " + current_min);
                    //                    }
                    delay = (sys_time - time);
                    var timeToDisplay =  doc.getElementById("timeToDisplay").value// in minutes
                    //                    alert(delay);
                    temGreenTime = (timeToDisplay) - delay;
                    //                    alert("clearance time:" +clearanceTime + "tempGreenTime:" +temGreenTime);

                }

                //                alert("tempgreentime" + tempgreentime + "delay" +delay);
                //                alert("changeGreenTime--"+globalVar + ",greenTime- "+greenTime + " ,temGreenTime- "+temGreenTime);
                //                alert("time+currentTime ==" + time + " , " + current_min);
                if(sys_time == time || sys_time <= current_min){
                    doc.getElementById("current_time").value = current_min;
                    doc.getElementById("sys_time").value = sys_time;
                    temGreenTime = temGreenTime - 1;
                    doc.getElementById("greenTime").value = temGreenTime;
                    // alert(temGreenTime);
                }
            }

            function changeStatus(id) {
                var junctionId = doc.getElementById("junctionId").value;
                var junctionName = doc.getElementById("junctionName").value;
                var program_version_no = doc.getElementById("program_version_no").value;
                var fileNo = doc.getElementById("fileNo").value;
                doc.forms['form1'].action = "ts_statusUpdaterCont?task=Back To Normal&junctionId=" + junctionId + "&junctionName=" +junctionName+ "&program_version_no=" +program_version_no+ "&fileNo=" +fileNo;
                doc.forms['form1'].submit();
            }
            function sendMessage() {
                var customMsg = doc.getElementById("customMsg").value;
                var queryString = "task=sendMsg&customMsg=" + customMsg;
                $.ajax({url: "modemsTaskCont", async: true, data: queryString, success: function(response_data) {
                        $("#td_responseMsg").html(response_data);
                    }
                });
            }

            function onChange(id){
                //                alert(id);

                if(id == 'clearnceSide1'){
                    doc.getElementById("tdNo").value = "color1";
                    doc.getElementById("clickedButton").value = "1";
                    doc.getElementById("selectedClearanceSide").value = "1";
                } else if(id == 'clearnceSide2'){
                    doc.getElementById("tdNo").value = "color2";
                    doc.getElementById("clickedButton").value = "2";
                    doc.getElementById("selectedClearanceSide").value = "2";
                } else if(id == 'clearnceSide3'){
                    doc.getElementById("tdNo").value = "color3";
                    doc.getElementById("clickedButton").value = "3";
                    doc.getElementById("selectedClearanceSide").value = "3";
                } else{
                    doc.getElementById("tdNo").value = "color4";
                    doc.getElementById("clickedButton").value = "4";
                    doc.getElementById("selectedClearanceSide").value = "4";
                }
            }

            function readClientResponse() {
                var junctionId = doc.getElementById("junctionId").value;
                var junctionName = doc.getElementById("junctionName").value;
                var program_version_no = doc.getElementById("program_version_no").value;
                var fileNo = doc.getElementById("fileNo").value;
                var radioBtnValue = doc.getElementById("clickedButton").value;
                //alert(junctionId);
                var queryString = "task=Change To Green&junctionId=" + junctionId + "&junctionName=" +junctionName +"&radioBtnValue=" +radioBtnValue+ "&program_version_no=" +program_version_no+ "&fileNo=" +fileNo;
                doc.getElementById("message").innerHTML = "Request for Changing <b>"  + junctionName +  "</b> signal status sent Successfully";
                var tdID = doc.getElementById("tdNo").value;
                doc.getElementById(tdID).style.backgroundColor = "lightblue";
                doc.getElementById("message").style.backgroundColor = "lightyellow";
                //$('#image_loading').html(doc.getElementById("div_animationLoading").innerHTML);
                var queryString = "task=Change To Green&junctionId=" + junctionId + "&junctionName=" +junctionName +"&radioBtnValue=" +radioBtnValue+ "&program_version_no=" +program_version_no+ "&fileNo=" +fileNo;
                $.ajax({
                    url:"ts_statusUpdaterCont",
                    method:"POST",
                    data:queryString,
                    success:function(response){
                        var res = response;
                        //$('#image_loading').html("");
                    }
                });
//                doc.forms['form1'].action = "ts_statusUpdaterCont?task=Change To Green&junctionId=" + junctionId + "&junctionName=" +junctionName +"&radioBtnValue=" +radioBtnValue+ "&program_version_no=" +program_version_no+ "&fileNo=" +fileNo;
//                doc.forms['form1'].submit();
            }

            function onSelect(id){
                //                alert(id);

                if(id == 'activity1'){
                    doc.getElementById("selectedActivity").value = "1";
                    doc.getElementById("activity_no").value = "1";
                    doc.getElementById("div_middle").style.display = "none";
                    doc.getElementById("changeActivity").style.display = "";
                    doc.getElementById("div_middle_pedestrian").style.display = "";
                } else if(id == 'activity2'){
                    doc.getElementById("div_middle").style.display = "";
                    doc.getElementById("activity_no").value = "2";
                    doc.getElementById("changeActivity").style.display = "none";
                    doc.getElementById("div_middle_pedestrian").style.display = "none";

                } else if(id == 'activity3'){
                    doc.getElementById("selectedActivity").value = "3";
                    doc.getElementById("activity_no").value = "3";
                    doc.getElementById("div_middle").style.display = "none";
                    doc.getElementById("changeActivity").style.display = "";
                    doc.getElementById("div_middle_pedestrian").style.display = "";
                } else if(id == 'activity4'){
                    doc.getElementById("selectedActivity").value = "4";
                    doc.getElementById("activity_no").value = "4";
                    doc.getElementById("div_middle").style.display = "none";
                    doc.getElementById("changeActivity").style.display = "";
                    doc.getElementById("div_middle_pedestrian").style.display = "";
                } else if(id == 'activity5'){
                    doc.getElementById("selectedActivity").value = "5";
                    doc.getElementById("activity_no").value = "5";
                    doc.getElementById("div_middle").style.display = "none";
                    doc.getElementById("changeActivity").style.display = "";
                    doc.getElementById("div_middle_pedestrian").style.display = "";
                } else{
                    doc.getElementById("selectedActivity").value = "6";
                    doc.getElementById("activity_no").value = "6";
                    doc.getElementById("div_middle").style.display = "none";
                    doc.getElementById("changeActivity").style.display = "";
                    doc.getElementById("div_middle_pedestrian").style.display = "";
                }
                
            }

            function changeActivityFn() {
                //var radioBtnValue = doc.getElementById("activityBtn").value;
               // if(radioBtnValue===2){
              //  readClientResponse();
               // }
             //  alert();
               debugger;
                var pwmval = document.getElementById('pwm');
               // alert(pwmval);
                if(pwmval!==null){
                pwmval=pwmval.options[pwmval.options.selectedIndex].value;
                // alert(pwmval);
                }else{
                    pwmval="3";
                }
                var junctionId = doc.getElementById("junctionId").value;
                var junctionName = doc.getElementById("junctionName").value;
                var program_version_no = doc.getElementById("program_version_no").value;
                var fileNo = doc.getElementById("fileNo").value;
                var activity_no = doc.getElementById("activity_no").value;

                var side1UpStatus = doc.getElementById("side1UpStatus").value;
                var side1DownStatus = doc.getElementById("side1DownStatus").value;

                var side2UpStatus = doc.getElementById("side2UpStatus").value;
                var side2DownStatus = doc.getElementById("side2DownStatus").value;

                var side3UpStatus = doc.getElementById("side3UpStatus").value;
                var side3DownStatus = doc.getElementById("side3DownStatus").value;

                var side4UpStatus = doc.getElementById("side4UpStatus").value;
                var side4DownStatus = doc.getElementById("side4DownStatus").value;

                var activitySide = side1UpStatus == '1' && side1DownStatus == '1' ? '1' :
                    side2UpStatus == '1' && side2DownStatus == '1' ? '2' :
                    side3UpStatus == '1' && side3DownStatus == '1' ? '3' :
                    side4UpStatus == '1' && side4DownStatus == '1' ? '4' : '1';
                //   alert(activitySide);
                //doc.getElementById("activity_message").innerHTML = "Request has been sent successfully";
                // doc.getElementById("activity_message").style.backgroundColor = "lightyellow";
                // $('#activity_image_loading').html(doc.getElementById("div_animationLoading").innerHTML);

                var queryString = "task=changeActivity&activitySide="+activitySide+"&activity_no="+activity_no+"&junctionId=" + junctionId + "&junctionName=" +junctionName +"&program_version_no=" +program_version_no+ "&fileNo=" +fileNo+ "&pwmval=" +pwmval;
                $.ajax({
                    url:"ts_statusUpdaterCont",
                    method:"POST",
                    data:queryString,
                    success:function(response){
                        var res = response;
                    }
                });
                  var property = document.getElementById("changeActivity");
     
        property.style.backgroundColor = "#228B22"
     
                //doc.forms['form3'].action = "ts_statusUpdaterCont?task=changeActivity&activitySide="+activitySide;
                //doc.forms['form3'].submit();
            }

            function setActivity() {
                var selectedActivity = $("#selectedActivity").val();
                var activity_no = $("#activity_no").val();

                var val= activity_no=="" ? selectedActivity : activity_no;
                // alert("selectedActivity:"+selectedActivity+" activity_no: "+activity_no+" val: "+val);
                if(val != 2) {
                    //  alert("pedestrian");
                    doc.getElementById("div_middle").style.display = "none";
                    doc.getElementById("changeActivity").style.display = "";
                    doc.getElementById("div_middle_pedestrian").style.display = "";
                    $("#activity"+val).attr("checked","checked");
                }else{
                    $("#activity2").attr("checked","checked");
                    var side_no = $("#side_no").val();
                    var selectedSide = $("#selectedClearanceSide").val();
                    var sideVal = selectedSide=="" ? side_no : selectedSide;
                    //alert(side_no);
                    doc.getElementById("div_middle").style.display = "";
                    doc.getElementById("changeActivity").style.display = "none";
                    doc.getElementById("div_middle_pedestrian").style.display = "none";
                    $("#clearnceSide"+sideVal).attr("checked","checked");
                }
            }

        </script>
    </head>
    <body onload="loadContents();changeGreenTime();">
        <table align="center" cellpadding="0" cellspacing="0" class="main" >
            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <!--        <img src="./images/close-button.jpg" align="right"/>-->
                        <h1 id="curr_jun_name" align="center">${junctionName} Signal Status</h1>
                        <!-- <h1 align="center">${val}</h1>
                           <h3 align="center">Through EL: ${applicationScope['currentTime']}</h3>
                        <h3 id="currentTime" align="center"></h3>
                        <div id="div_signalStatus" style="margin: 5px auto 0 auto">-->
                        <table align="center">
                            <tr>
                                <td>
                                    <table align="center" border="0">
                                        <tr>
                                            <td bgcolor="lightsteelblue" colspan="4"><div id="change_activity_div" style="position: static;  height: 50px;" >
                                                    <form name="form3" method="post">
                                                        <table  width="100%" border="0">
                                                            <tr>
                                                                <td><input type="radio" name="activityBtn" value="1" id="activity1" onclick="onSelect(id);">Normal&nbsp;&nbsp;&nbsp;</td>
                                                                <td><input type="radio" name="activityBtn" value="2" id="activity2" onclick="onSelect(id);">Clearance&nbsp;&nbsp;&nbsp;</td>
                                                                <td><input type="radio" name="activityBtn" value="3" id="activity3" onclick="onSelect(id);">Change Mode&nbsp;&nbsp;&nbsp;</td>
                                                                <td><input type="radio" name="activityBtn" value="4" id="activity4" onclick="onSelect(id);">Jump&nbsp;&nbsp;&nbsp;</td>
                                                                <td><input type="radio" name="activityBtn" value="5" id="activity5" onclick="onSelect(id);">Shutdown&nbsp;&nbsp;&nbsp;</td>
                                                                <td><input type="radio" name="activityBtn" value="6" id="activity6" onclick="onSelect(id);">Extend</td>
                                                                <td><label> <h5>PWM</h5> </label></td>  <td><select name="pwm" id="pwm">
  <option value="3">Select</option>
  <option value="1">Low</option>
  <option value="2">Middle</option>
  <option value="3">High</option>
   
</select></td>
                                                                <td>
                                                                    <input type="button" id="changeActivity" value="Submit" onclick="changeActivityFn()">
                                                                    <input type="hidden" name="junctionId" value="${junctionId}">
                                                                    <input type="hidden" name="program_version_no" value="${program_version_no}">
                                                                    <input type="hidden" name="fileNo" value="${fileNo}">
                                                                    <input type="hidden" name="junctionName" value="${junctionName}">
                                                                    <input type="hidden" id="selectedActivity" name="selectedActivity" value="${activity}">
                                                                    <input type="hidden" id="activity_no" name="activity_no" value="">
                                                                    <input type="hidden" id="side_no" name="side_no" value="${side_no}">
                                                                </td>
                                                                <td>
                                                                    <table width="100%" border="0px">
                                                                        <tr>
                                                                            <td id="activity_message" style="color: darkred"></td>
                                                                            <td id="activity_image_loading" style="color: #fef2d8"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                        <!--                                            <caption>Signal Type: Chauraha</caption>-->
                                        <tr>
                                            <td><div style="width: 150px; height: 100px;"></div></td>
                                            <td colspan="2">
                                                <div id="div_upper" style="width: 150px; height: 150px; border-left-style: ridge; border-right-style: ridge; background-color: grey;">
                                                    <table align="center" border="0" width="100%">
                                                        <tr align="center">
                                                            <td id="side1"></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <table border="0" width="50%" align="center">
                                                                    <tr>
                                                                        <td colspan="" align="center" id="signal_upper_down"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="" id="signal_upper_left"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="" id="signal_upper_up"></td>
                                                                        <td align="right" id="signal_upper_right"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="2"><label id="label_upper" style="color: white"></label></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                            <td rowspan="3">
                                                <div id="plan_info_div" style="position: static; width: 200px;height: 390px">
                                                    <table align="center" style="background-color: lightsteelblue;" border="1" width="100%" height="100%">
                                                        <caption align="top"><b>Plan Details</b></caption>
                                                        <tr>
                                                            <th>Date Time</th>
                                                            <td id="date_time_td"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Plan No</th>
                                                            <td id="plan_no_td"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Mode</th>
                                                            <td id="mode_td"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>On Time</th>
                                                            <td id="onTime_td"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Off Time</th>
                                                            <td id="offTime_td"></td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div id="div_left" style="width: 150px; height: 150px; border-top-style: ridge; border-bottom-style: ridge; background-color: grey;">
                                                    <table align="left" border="0" width="100%">

                                                        <tr>
                                                            <td align="center" id="side2"></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <table width="50%" align="center">
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="signal_left_down"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right" id="signal_left_left"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="signal_left_up"></td>
                                                                        <td align="left" id="signal_left_right"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="2"><label id="label_left" style="color: white"></label></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>

                                            <td bgcolor="lightgrey">
                                                <div id="div_middle" style="position: static; width: 150px; height: 150px;display:none">
                                                    <form name="form1" method="post">
                                                        <table  width="100%" border="0">
                                                            <tr id="color1">
                                                                <td width="45%">
                                                                    <input type="radio" name="radiobtn" value="1" id="clearnceSide1" onclick="onChange(id);">${side1Name}
                                                                </td>
                                                            </tr>
                                                            <tr id="color2">
                                                                <td>
                                                                    <input type="radio" name="radiobtn" value="2" id="clearnceSide2" onclick="onChange(id);">${side2Name}
                                                                </td>
                                                            </tr>
                                                            <tr id="color3">
                                                                <td>
                                                                    <input type="radio" name="radiobtn" value="3" id="clearnceSide3" onclick="onChange(id);">${side3Name}
                                                                </td>
                                                            </tr>
                                                            <tr id="color4">
                                                                <td>
                                                                    <input type="radio" name="radiobtn" value="4" id="clearnceSide4" onclick="onChange(id);">${side4Name}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="2">
                                                                    <input type="button" name="task" id="submitbtn" value="Change To Green" onclick="readClientResponse();">
                                                                    <input type="hidden" id="junctionId" name="junctionId" value="${junctionId}">
                                                                    <input type="hidden" id="program_version_no" name="program_version_no" value="${program_version_no}">
                                                                    <input type="hidden" id="fileNo" name="fileNo" value="${fileNo}">
                                                                    <input type="hidden" id="junctionName" name="junctionName" value="${junctionName}">
                                                                    <input type="hidden" id="clickedButton" value="">
                                                                    <input type="hidden" id="tdNo" value="">
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table width="100%" border="0px">
                                                            <tr>
                                                                <td id="message" style="color: darkred"></td>
                                                                <td id="image_loading" style="color: #fef2d8"></td>
                                                            </tr>
                                                        </table>
                                                    </form>
                                                </div>
                                                <div id="div_middle_pedestrian" style="position: static; width: 150px; height: 150px;">
                                                    <table  width="100%" border="0">
                                                        <tr>
                                                            <td>
                                                                <table width="50%" align="center">
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="pedestrian_up"><div style="position: static;width: 50px; height: 50px"><img src="./img/red_pedestrian.jpg" width="30" height="30"></div></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="left" id="pedestrian_left"><div style="position: static;width: 50px; height: 50px"><img src="./img/red_pedestrian.jpg" width="30" height="30"></div></td>
                                                                        <td align="right" id="pedestrian_down"><div style="position: static;width: 50px; height: 50px"><img src="./img/red_pedestrian.jpg" width="30" height="30"></div></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="pedestrian_right"><div style="position: static;width: 50px; height: 50px"><img src="./img/red_pedestrian.jpg" width="30" height="30"></div></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>

                                            <td>
                                                <div id="div_right" style="position: static; width: 150px; height: 150px; border-top-style: ridge; border-bottom-style: ridge; background-color: grey;">
                                                    <table align="right" border="0" width="100%">
                                                        <tr>
                                                            <td align="center" id="side4"></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <table width="50%" align="center">
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="signal_lower_down"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right" id="signal_lower_left"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="signal_lower_up"></td>
                                                                        <td align="left" id="signal_lower_right"></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td align="center"><label id="label_lower" style="color: white"></label></td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><div style="width: 150px; height: 120px;"></div></td>
                                            <td>
                                                <div id="div_lower" style="width: 150px; height: 150px; border-left-style: ridge; border-right-style: ridge; background-color: grey;">
                                                    <table align="center" border="0" style="vertical-align: bottom" width="100%">
                                                        <tr>
                                                            <td align="center" colspan="2"><label id="label_right" style="color: white"></label></td>
                                                        </tr>
                                                        <tr>
                                                            <td align="center" id="side3"></td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <table width="50%" align="center">
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="signal_right_down"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="right" id="signal_right_left"></td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td align="center" colspan="2" id="signal_right_up"></td>
                                                                        <td align="left" id="signal_right_right"></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <%-- <td>
                                          <table align="center" style="background-color: lightsteelblue">
                                              <caption align="top">Send Message To The Client</caption>
                                              <tr>
                                                  <th>Message</th>
                                                  <td><input type="text" id="customMsg" name="customMsg" value="Hello!" size="30"></td>
                                              </tr>
                                              <tr>
                                                  <td colspan="2" align="right">
                                                      <input type="button" id="sendMsgBtn" value="Send Message" onclick="sendMessage();">
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td id="td_responseMsg" colspan="2"></td>
                                              </tr>
                                          </table>
                                      </td>
                                      <td>
                                          <table align="center" style="background-color: lightsteelblue">
                                              <caption align="top">Clients Latest Response</caption>
                                              <tr>
                                                  <td id="client">
                                                      <textarea id="clientResponse" rows="10" cols="20" readonly></textarea>
                                                  </td>
                                              </tr>
                                          </table>
                                      </td>  --%>
                            </tr>
                        </table>
                        <input type="hidden" id="functionNo" name="functionNo" value="${functionNo}">
                        <input type="hidden" id="sideNo" name="sideNo" value="${sideNo}">
                        <input type="hidden" id="sideName" name="sideName" value="${sideName}">
                        <input type="hidden" id="side1Name" name="side1Name" value="${side1Name}">
                        <input type="hidden" id="side2Name" name="side2Name" value="${side2Name}">
                        <input type="hidden" id="side3Name" name="side3Name" value="${side3Name}">
                        <input type="hidden" id="side4Name" name="side4Name" value="${side4Name}">
                        <input type="hidden" id="side5Name" name="side5Name" value="${side5Name}">
                        <input type="hidden" id="side1_green_time" name="side1_green_time" value="${side1_green_time}">
                        <%--<input type="hidden" id="onTimeHr" name="onTimeHr" value="${onTimeHr}">
                            <input type="hidden" id="onTimeMin" name="onTimeMin" value="${onTimeMin}">
                            <input type="hidden" id="onTimeSec" name="onTimeSec" value="${onTimeSec}">
                            <input type="hidden" id="OffTimeHr" name="OffTimeHr" value="${OffTimeHr}">
                            <input type="hidden" id="OffTimeMin" name="OffTimeMin" value="${OffTimeMin}">
                            <input type="hidden" id="offTimeSec" name="offTimeSec" value="${offTimeSec}">  --%>
                        <input type="hidden" id="juncHr" name="juncHr" value="${juncHr}">
                        <input type="hidden" id="juncMin" name="juncMin" value="${juncMin}">
                        <input type="hidden" id="juncDat" name="juncDat" value="${juncDat}">
                        <input type="hidden" id="juncMonth" name="juncMonth" value="${juncMonth}">
                        <input type="hidden" id="juncYear" name="juncYear" value="${juncYear}">
                        <input type="hidden" id="onTime" name="onTime" value="${onTime}">
                        <input type="hidden" id="offTime" name="offTime" value="${offTime}">
                        <input type="hidden" id="plan_no" name="plan_no" value="${plan_no}">
                        <input type="hidden" id="mode" name="mode" value="${mode}">
                        <input type="hidden" id="timeToDisplay" id="timeToDisplay" value="">
                        <input type="hidden" id="greenTime" name="greenTime" value="">
                        <input type="hidden" id="current_time" name="current_time" value="">
                        <input type="hidden" id="sys_time" name="sys_time" value="">
                        <input type="hidden" id="hours" name="hours" value="${hours}">
                        <input type="hidden" id="mins" name="mins" value="${mins}">
                        <input type="hidden" id="secs" name="secs" value="${secs}">
                        <input type="hidden" id="side1Time" name="side1Time" value="${side1Time}">
                        <input type="hidden" id="side2Time" name="side2Time" value="${side2Time}">
                        <input type="hidden" id="side3Time" name="side3Time" value="${side3Time}">
                        <input type="hidden" id="side4Time" name="side4Time" value="${side4Time}">
                        <div style="visibility: hidden"> side1</div>
                        <input type="hidden" id="side1LeftStatus" name="side1LeftStatus" value="${side1LeftStatus}">
                        <input type="hidden" id="side1RightStatus" name="side1RightStatus" value="${side1RightStatus}">
                        <input type="hidden" id="side1UpStatus" name="side1UpStatus" value="${side1UpStatus}">
                        <input type="hidden" id="side1DownStatus" name="side1DownStatus" value="${side1DownStatus}">
                        <div style="visibility: hidden"> side2</div>
                        <input type="hidden" id="side2LeftStatus" name="side2LeftStatus" value="${side2LeftStatus}">
                        <input type="hidden" id="side2RightStatus" name="side2RightStatus" value="${side2RightStatus}">
                        <input type="hidden" id="side2UpStatus" name="side2UpStatus" value="${side2UpStatus}">
                        <input type="hidden" id="side2DownStatus" name="side2DownStatus" value="${side2DownStatus}">
                        <div style="visibility: hidden">side3 </div>
                        <input type="hidden" id="side3LeftStatus" name="side3LeftStatus" value="${side3LeftStatus}">
                        <input type="hidden" id="side3RightStatus" name="side3RightStatus" value="${side3RightStatus}">
                        <input type="hidden" id="side3UpStatus" name="side3UpStatus" value="${side3UpStatus}">
                        <input type="hidden" id="side3DownStatus" name="side3DownStatus" value="${side3DownStatus}">
                        <div style="visibility: hidden">side4</div>
                        <input type="hidden" id="side4LeftStatus" name="side4LeftStatus" value="${side4LeftStatus}">
                        <input type="hidden" id="side4RightStatus" name="side4RightStatus" value="${side4RightStatus}">
                        <input type="hidden" id="side4UpStatus" name="side4UpStatus" value="${side4UpStatus}">
                        <input type="hidden" id="side4DownStatus" name="side4DownStatus" value="${side4DownStatus}">
                        <div style="visibility: hidden">Counter: </div>
                        <input type="hidden" id="refreshCounterTime" name="refreshCounterTime" value="">
                        <input type="hidden" id="refreshCounterTime1" name="refreshCounterTime1" value="">
                        <input type="hidden" id="refreshCounterTime2" name="refreshCounterTime2" value="">
                        <input type="hidden" id="refreshCounterTime3" name="refreshCounterTime3" value="">
                        <div id="div_signals" style="visibility: hidden;border: 1px solid red">
                            <div id="div_redSignal" style="position: absolute;"> <img src="./images/red_light.png" width="30" height="30"></div>
                            <div id="div_yellowSignal" style="position: absolute;"><img src="./images/yellow_light.png" width="30" height="30"></div>
                            <div id="green_down" style="position: absolute;"><img src="./images/down.png" width="30" height="30"></div>
                            <div id="green_right" style="position: absolute;"><img src="./images/right.png" width="30" height="30"></div>
                            <div id="green_left" style="position: absolute;"><img src="./images/left.png" width="30" height="30"></div>
                            <div id="green_up" style="position: absolute;"><img src="./images/up.png" width="30" height="30"></div>
                            <div id="div_greySignal" style="position: absolute;"><img src="./images/grey.png" width="30" height="25"></div>
                            <div id="div_animationLoading" style="position: absolute;"><img src="./images/animated.gif" width="30" height="30"></div>
                        </div>
                        <input type="hidden" id="selectedClearanceSide" value="">
                    </DIV>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
<!--        KARMETA to KHAMARIYA
MOTHER TERESA TO GHANA
DHANWANTRI  NAGAR to KHAMARIYA
TILWARA to RAILWAY PLAT FORM 4
DEVRI to DEVRI Via GHAMAPUR
DEVRI to DEVRI Via GOHALPUR
RAILWAY STATION to DHUANDHAR
DAMOH NAKA to SHARDA MANDIR
DAMOH NAKA to DHUANDHAR
CIVIK CENTRE to DHAWANTRI NAGAR
GLOBAL COLLEGE to RAILWAY STATION
SHIV NAGAR to GWARIGHAT R
RAILWAY STATION to DHUANDHAR
GWARIGHAT to RAILWAY PLAT FORM 4
MEDICAL to MAHARAJPUR
DAMOH NAKA to MEDICAL
RDVV to DAMOH NAKA
TEENPATTI BUS STAND to ADHARTAAL
कर्मेटा-खमरिया
मदर टेरेसा-घना
धन्वन्तरी नगर-खमरिया
तिलवारा-रेलवे प्लेटफार्म 4
देवरी-देवरी वाया घमापुर
देवरी-देवरी वाया गोहालपुर
रेलवे स्टेशन-धुंधर
दमोह नाका-शारदा मंदिर
दमोह नाका-धुंधर
सिविक सेन्टर-धन्वन्तरी नगर
ग्लोबल कॉलेज-रेलवे स्टेशन
शिव नगर-ग्वारीघाट आर
ग्वारीघाट-रेलवे प्लेटफार्म 4
मेडिकल-महाराजपुर
दमोह नाका-मेडिकल
आर डी वी वी-दमोह नाका
तीनपत्ती बस स्टैंड-अधारताल




आई एस बी टी
एस बी आई चौक
अशोक होटल-->

    </body>
</html>
