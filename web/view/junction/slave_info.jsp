<%-- 
    Document   : slave_info
    Created on : Aug 14, 2012, 11:25:00 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="style/style.css" />
<link rel="stylesheet" type="text/css" href="style/Table_content.css" />
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" src="JS/jquery-ui.min.js"></script>
<script type="text/javascript" language="javascript">

//    function verify() {
//        var no_of_sides = document.getElementById("no_of_sides").value;
//       // alert(no_of_sides);
//        if(document.getElementById("clickedButton").value == 'SAVE') {
//            for(var i = 1; i<= parseInt(no_of_sides); i++) {
//                var slave_id= $("#slave_id"+i).val();
//               // alert(slave_id);
//                if($.trim(slave_id).length == 0) {
//                    var message = "<td colspan='6' bgcolor='coral'><b>Slave Id is required...</b></td>";
//                    $("#message").html(message);
//                    document.getElementById("slave_id"+i).focus();
//                    return false; // code to stop from submitting the form2.
//                }
//            }
//           // alert(result);
//           return confirm("Do you want to save slave ids ?");
//        }
//    }

  function verify() {
      var result;
       // alert(no_of_sides);
        if(document.getElementById("clickedButton").value == 'SAVE') {
              var no_of_sides = document.getElementById("no_of_sides").value;
            for(var i = 1; i<= parseInt(no_of_sides); i++) {
                var slave_id= $("#no_of_sides"+i).val();
               // alert(slave_id);
                if($.trim(slave_id).length == 0) {
                    var message = "<td colspan='6' bgcolor='coral'><b>Slave Id is required...</b></td>";
                    $("#message").html(message);
                    document.getElementById("slave_id"+i).focus();
                    return false; // code to stop from submitting the form2.
                }
            }
           // alert(result);
           return confirm("Do you want to save slave ids ?");
        }
         return result;
    }
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Slave Detail</title>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
       <script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<!--        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>-->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
          
                     

      <script type="text/javascript" language="javascript">
          function setStatus(id) {
        if (id == 'SAVE') 
            document.getElementById("clickedButton").value = "SAVE";
        else (id === 'DELETE')
            document.getElementById("clickedButton").value = "DELETE";
        }
        function myFuntion(){
        alert(" ");
        }
        
          </script>
        <style>
            .modal-div {
                    height: 200px;
                    width: 50%;
                }
        </style>
    </head>
    <body>
        <table cellspacing="0" border="0" id="table0"  align="center" width="75%">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr><td><%@include file="/layout/menu.jsp" %></td></tr>
            <tr style="font-size:larger ;font-weight: 700;" align="center">
                <td class="heading">
                    ${slave_info_name} slave info details
                </td>
            </tr>
            <tr align="center">
                <td>
                    <input value="Add Side" id="add_plan" onclick="addRow('dataTable')" type="button">

<!--           <input value="Delete Side" onclick="deleteRow('dataTable')" type="button">-->
                </td>
                
            </tr>
            <tr id="message">
                <c:if test="${not empty message}">
                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                </c:if>
            </tr>
            <tr>
                <td align="center">
                    <form name="form1" action="slaveInfoCont" method="post" enctype='multipart/form-data' onsubmit="return verify()">
                        <table id="dataTable" style="border-collapse: collapse;" border="1" width="100%" align="center">
                            <tbody>
                                <tr>
                                    <th class="heading" >Select.</th>
                                    <th  class="heading">S.No</th>
                                    <th  class="heading">Side No</th>
                                    <th  class="heading">Side Name</th>
                                    <th  class="heading">Pole Type</th>
                                    <th  class="heading">Position</th>
                                    <th  class="heading">Primary Horizontal Aspect Number</th>
                                    <th  class="heading">Primary Vertical Aspect Number</th>
                                    <th  class="heading">Secondary Horizontal Aspect Number</th>
                                    <th  class="heading">Secondary Vertical Aspect Number</th>
                                    <th class="heading">Image</th>                              
                                    <th  class="heading">Vehicle Detection</th>
                                    <th  class="heading">Count Down</th>
                                    <th  class="heading">Number of Lane</th>
                                    <th  class="heading">RLVD</th>
                                    <th  class="heading">ANPR</th>
                                    <th  class="heading">PA_System</th>
                                    <th  class="heading">Lattitude</th>
                                    <th  class="heading">Longitude</th>
                                    <th  class="heading">Get Coordinate</th>
                                    <th  class="heading">Camera</th>

                                </tr>
                                <c:forEach var="list" items="${requestScope['slaveinfo']}" varStatus="loopCounter">
                                    <tr>
                                        
                                        <td><input class="input" type="checkbox"  name="chk" id="checkbox${loopCounter.count}"  onclick="myFunction()"></td>
                                        <td><input class="input" type="text" name="s_no${loopCounter.count}" id="s_no${loopCounter.count}" size="3" value="${loopCounter.count}" readonly style="background-color: #c3c3c3">
                                        </td>
                                        <td><input class="input" type="text" name="side_no${loopCounter.count}" id="side_no${loopCounter.count}" size="3" value="${list.side_no}"  >
                                            <input type="hidden" name="side_detail_id${loopCounter.count}" id="side_detail_id${loopCounter.count}" value="${list.side_detail_id}">
                                            <input type="hidden" id="junction_id" name="junction_id" value="${junction_id}">
                                        <input type="hidden" name="revision_no${loopCounter.count}" id="revision_no${loopCounter.count}" value="${list.revision_no}">
                                        <input type="hidden" name="s_no_count" id="s_no_count" value="${loopCounter.count}"></td>
                                        <td><input class="input" type="text" name="side_name${loopCounter.count}" id="side_name${loopCounter.count}" value="${list.sideName}" onclick="getSideName(id,${junction_id},${list.side_no})" ></td>
                                        <td><select name="pole_type${loopCounter.count}" id="pole_type${loopCounter.count}">
                                          <option value="Standard" ${list.pole_type=="Standard"?"selected":""}>Standard</option>
                                                <option value="Cantilever T" ${list.pole_type=="Cantilever T"?"selected":""}>Cantilever T</option>
                                                <option value="Cantilever L" ${list.pole_type=="Cantilever L"?"selected":""}>Cantilever L</option>
                                              
                                            </select> 
                                        <td><select name="position${loopCounter.count}" id="position${loopCounter.count}">
                                                <option value="center" ${list.position=="center"?"selected":""}>center</option>
                                                <option value="Left side" ${list.position=="Left side"?"selected":""}>Left side</option>
                                            </select> 
                                        <td><input class="input" type="text" name="primary_h_aspect_no${loopCounter.count}" id="primary_h_aspect_no${loopCounter.count}" value="${list.primary_h_aspect_no}"></td>
                                        <td><input class="input" type="text" name="primary_v_aspect_no${loopCounter.count}" id="primary_v_aspect_no${loopCounter.count}" value="${list.primary_v_aspect_no}"></td>
                                        <td><input class="input" type="text" name="secondary_v_aspect_no${loopCounter.count}" id="secondary_v_aspect_no${loopCounter.count}" value="${list.secondary_v_aspect_no}"></td>
                                        <td><input class="input" type="text" name="secondary_h_aspect_no${loopCounter.count}" id="secondary_h_aspect_no${loopCounter.count}" value="${list.secondary_h_aspect_no}"></td>
<!--                                        <td><input class="input" type="file"  name="image_name" id="image_name${loopCounter.count}" value="${slave_info.image_name}" value="" size="35" multiple="muliple"></td>-->
                                       
                                        
                                        
<!--                                        <td><input type="file" id="image_name" name="image_name" value="" size="35" multiple="muliple"></td>-->
<!--                                       <td><input type="file" id="image_name" name="image_name${loopCounter.count}" value="" size="35" multiple="muliple" >
                                         </td>-->
                                          
                                              

                                        
                                   
                                        
                                       <td><a href="#" onclick="ViewPlanInfo('${list.side_detail_id}', '${list.sideName}', '${junction_id}');">View image</a></td>
                                          
                                       
<!-- <td><button type="button"  onclick="addRow()" style="height:200px;width:200px" >Upload</button></td>-->
                                        <td><input class="input" type="text" name="vehicle_detection${loopCounter.count}" id="vehicle_detection${loopCounter.count}" value="${list.vehicle_detection}"></td>
                                        <td><input class="input" type="text" name="count_down${loopCounter.count}" id="count_down${loopCounter.count}" value="${list.count_down}"></td>
                                        <td><input class="input" type="text" name="no_of_lane${loopCounter.count}" id="no_of_lane${loopCounter.count}" value="${list.no_of_lane}"></td>
                                        <td><select name="rlpd${loopCounter.count}" id="rlpd${loopCounter.count}">
                                                <option value="Yes" ${list.rlpd=="Yes"?"selected":""}>Yes</option>
                                                <option value="No" ${list.rlpd=="No"?"selected":""}>No</option>
                                            </select></td>
                                        <td><input class="input" type="text" name="anpr${loopCounter.count}" id="anpr${loopCounter.count}" value="${list.anpr}"></td>
                                        <td><select name="pa_system${loopCounter.count}" id="pa_system${loopCounter.count}">
                                                <option value="Yes" ${list.pa_system=="Yes"?"selected":""}>Yes</option>
                                                <option value="No" ${list.pa_system=="No"?"selected":""}>No</option>
                                            </select></td>
                                            <td><input type="text" id="latitude${loopCounter.count}" name="latitude${loopCounter.count}" value="${list.latitude}" ></td>
                                            <td><input type="text" id="longitude${loopCounter.count}" name="longitude${loopCounter.count}" value="${list.longitude}" ></td>
                                            <td><input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord(${loopCounter.count})"></td>
                                            <td><a href="#" onclick="ViewCameraInfo('${list.junction_id}','${list.side_no}');">View Camera Details</a></td>
                                            
                                    </tr>
                                              
                                                
                                </c:forEach>
                            </tbody>
                        </table>
                        <input class="button" type="submit" id="SAVE" name="task" value="SAVE" onclick="setStatus(id)">
                        <input type="hidden" id="junction_id" name="junction_id" value="${junction_id}">
                        <input type="hidden" id="program_version_no" name="program_version_no" value="${program_version_no}">
                        <input type="hidden" id="no_of_sides" name="no_of_sides" value="${no_of_sides}">
                    </form>
                </td>
            </tr>
        </table>
<!--<div class="modal fade" id="myModal" role="dialog" tabindex="-1" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-cente">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="dynamic-content">
                    <table>
                        <tr id="get_image">
                        </tr>
                    </table>
                    
                </div>
            </div>
       </div>
</div> -->
        <script type="text/javascript">


            function addRow(tableID) {
            debugger;
                var table = document.getElementById(tableID);
                var rowCount = table.rows.length;
                var previousRow = rowCount - 1;
                var junction_id = document.getElementById("junction_id").value;
                var side_num = rowCount;
                $("#message").html("");
                var side_no = document.getElementById("no_of_sides").value;

                if (rowCount > side_no) {
                    var message = "<td colspan='6' bgcolor='coral'>Sorry , You can not add more row .You have completed  side..</td>";
                    $("#message").html(message);
                } else {
                    var row = table.insertRow(rowCount);

                    var cell1 = row.insertCell(0);
                    var element1 = document.createElement("input");
                    element1.type = "checkbox";
                    element1.disabled = false;
                    //element1.style.display = "none";

                    cell1.appendChild(element1);
                    
        
        var cell2 = row.insertCell(1);
                    var element1 = document.createElement("input");
                    element1.type = "text";
                    element1.id = "s_no" + rowCount;
                    element1.name = "s_no" + rowCount;
                    element1.value = rowCount;
                    element1.readOnly = true;
                    element1.size = 2;
                    cell2.appendChild(element1);

                    var cell3 = row.insertCell(2);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "side_no" + rowCount;
                    element2.id = "side_no" + rowCount;
                    element2.size = 3;
                    element2.maxLength = 2;
                    element2.value = rowCount;
                    //element2.readonly =true;
                    element2.setAttribute("onchange", 'validateSide(id,' + rowCount + ',' + side_no + ')');
                    element2.setAttribute("onkeyup", 'validateSide(id,' + rowCount + ',' + side_no + ')');
                    cell3.appendChild(element2);
//                    
//                    var element1 = document.createElement("input");
//                    element1.type = "hidden";
//                    element1.name = "side_detail_id" + rowCount;
//                    element1.id = "side_detail_id" + rowCount;
//                    element1.size = 3;
//                    element1.maxLength = 2;
//                    element1.value = ;
//                    //element2.readonly =true;                    
//                    cell3.appendChild(element1);
//                    
//                    var element3 = document.createElement("input");
//                    element3.type = "hidden";
//                    element3.name = "revision_no" + rowCount;
//                    element3.id = "revision_no" + rowCount;
//                    element3.size = 3;
//                    element3.maxLength = 2;
//                    element3.value = ;
//                    //element2.readonly =true;                    
//                    cell3.appendChild(element3);
//                    
                     var element4 = document.createElement("input");
                    element4.type = "hidden";
                    element4.name = "s_no_count";
                    element4.id = "s_no_count";
                    element4.size = 3;
                    element4.maxLength = 2;
                    element4.value = rowCount;
                    //element2.readonly =true;                    
                    cell3.appendChild(element4);
                    
                    
                    var cell4 = row.insertCell(3);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "side_name" + rowCount;
                    element2.id = "side_name" + rowCount;
                    element2.size = 5;
                    element2.setAttribute("onclick", 'getSideName(id,'+junction_id+','+side_num+')');
                    //            element2.setAttribute("onkeyup", 'validateOnTime(id,'+rowCount+')');
                    cell4.appendChild(element2);

                    var cell5 = row.insertCell(4);
                   var modeElement = document.createElement("select");
                   var poleTypeArray = ["Standard", "Cantilever T", "Cantilever L"];
                       
                   $.ajax({url: "slaveInfoCont?task=poleType",
                   //type: 'POST',
                   dataType: 'json',
                   //contentType: 'application/json',
                   //context: document.body,
                   success: function(response_data)
                   {
                     
                       data=response_data.data;
                       
                       for (var i = 0; i < data.length; i++) {
                       var opt = document.createElement("option");
                       opt.text = data[i].pole_type1;
                       opt.value = data[i].pole_type1;
                     
                       modeElement.options.add(opt);
                     
                       }
                                     
                   }
               });
                   
                   modeElement.name = "pole_type1" + rowCount;
                   modeElement.id = "pole_type1" + rowCount;
                   modeElement.value = "";
                   cell5.appendChild(modeElement);
                   
                   
                   var cell6 = row.insertCell(5);
                    var modeElement1 = document.createElement("select");
                    var positionArray = ["Center", "Left side"];
                    $.ajax({url: "slaveInfoCont?task=position",
                   //type: 'POST',
                   dataType: 'json',
                   //contentType: 'application/json',
                   //context: document.body,
//                   success: function(response_data)
//                   {                  
//                       data=response_data.data;
//                       for (var i = 0; i < data.length; i++) {
//                       var opt = document.createElement("option");
//                       opt.text = data[i].position1;
//                       opt.value = data[i].position1;
//                       alert("text ---"+opt.text);
//                       alert("value ---"+opt.value);
//                       modeElement1.options.add(opt);
//                       }
                                     
                   
                   
                   success: function(response_data){
                   
                   data=response_data.data;
                   for(var i = 0; i < data.length; i++){
                     var opt = document.createElement("option");
                       opt.text = data[i].position1;
                       opt.value = data[i].position1;
                      
                       modeElement1.options.add(opt);  
                   
                    
                    }
                   modeElement1.name = "position1" + rowCount;
                    modeElement1.id = "position1" + rowCount;
                    modeElement1.value = "";
                    cell6.appendChild(modeElement1); 
                   
            }
                   
                   
                   
               });
                     
                    
                    

                    var cell7 = row.insertCell(6);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "primary_h_aspect_no" + rowCount;
                    element2.id = "primary_h_aspect_no" + rowCount;
                    element2.size = 2;
                    element2.value = "";
                    cell7.appendChild(element2);

                    var cell8 = row.insertCell(7);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "primary_v_aspect_no" + rowCount;
                    element2.id = "primary_v_aspect_no" + rowCount;
                    element2.size = 2;
                    element2.value = "";
                    cell8.appendChild(element2);

                    var cell9 = row.insertCell(8);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "secondary_h_aspect_no" + rowCount;
                    element2.id = "secondary_h_aspect_no" + rowCount;
                    element2.size = 2;
                    element2.value = "";
                    cell9.appendChild(element2);

                    var cell10 = row.insertCell(9);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "secondary_v_aspect_no" + rowCount;
                    element2.id = "secondary_v_aspect_no" + rowCount;
                    element2.size = 2;
                    element2.value = "";
                    cell10.appendChild(element2);

//                    var cell10 = row.insertCell(10);
//                     var x = document.createElement("BUTTON");
//                    element2.type = "text";
//                    element2.name = "image" + rowCount;
//                    element2.id = "image" + rowCount;
//                    element2.size = 5;
//                    element2.value = "";
//                    cell10.appendChild(x);
                     var cell10 = row.insertCell(10);
                     var element2 = document.createElement("input");
                    element2.type = "file";
                    element2.name = "image_name" + rowCount;
                    element2.id = "image_name" + rowCount;
                    element2.size = 5;
                    element2.value = "";
                    element2.multiple= "multiple";
                    cell10.appendChild(element2);

                    var cell11 = row.insertCell(11);
                    var modeElement2 = document.createElement("select");
                    var vehicleDetectionArray = ["Yes", "No"];
                    for (var i = 0; i < vehicleDetectionArray.length; i++) {
                        var opt = document.createElement("option");
                        opt.text = vehicleDetectionArray[i];
                        opt.value = vehicleDetectionArray[i];
                        modeElement2.options.add(opt);
                    }
                    modeElement2.name = "vehicle_detection" + rowCount;
                    modeElement2.id = "vehicle_detection" + rowCount;
                    modeElement2.value = "";
                    cell11.appendChild(modeElement2);
                   
                    var cell12 = row.insertCell(12);
                    var modeElement2 = document.createElement("select");
                    var countDownArray = ["Yes", "No"];
                    for (var i = 0; i < countDownArray.length; i++) {
                        var opt = document.createElement("option");
                        opt.text = countDownArray[i];
                        opt.value = countDownArray[i];
                        modeElement2.options.add(opt);
                    }
                    modeElement2.name = "count_down" + rowCount;
                    modeElement2.id = "count_down" + rowCount;
                    modeElement2.value = "";
                    cell12.appendChild(modeElement2);

                    var cell13 = row.insertCell(13);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "no_of_lane" + rowCount;
                    element2.id = "no_of_lane" + rowCount;
                    element2.size = 2;
                    element2.value = "";
                    cell13.appendChild(element2);
                    
                    var cell14 = row.insertCell(14);
                    var modeElement2 = document.createElement("select");
                    var rlpdArray = ["Yes", "No"];
                    for (var i = 0; i < rlpdArray.length; i++) {
                        var opt = document.createElement("option");
                        opt.text = rlpdArray[i];
                        opt.value = rlpdArray[i];
                        modeElement2.options.add(opt);
                    }
                    modeElement2.name = "rlpd" + rowCount;
                    modeElement2.id = "rlpd" + rowCount;
                    modeElement2.value = "";
                    cell14.appendChild(modeElement2);
                    
                    var cell15 = row.insertCell(15);
                    var element2 = document.createElement("input");
                    element2.type = "number";
                    element2.name = "anpr" + rowCount;
                    element2.id = "anpr" + rowCount;
                    element2.size = 2;
                    element2.value = "";
                    cell15.appendChild(element2);
                    
                    var cell16 = row.insertCell(16);
                    var modeElement2 = document.createElement("select");
                    var paSystemArray = ["Yes", "No"];
                    for (var i = 0; i < paSystemArray.length; i++) {
                        var opt = document.createElement("option");
                        opt.text = paSystemArray[i];
                        opt.value = paSystemArray[i];
                        modeElement2.options.add(opt);
                    }
                    modeElement2.name = "pa_system" + rowCount;
                    modeElement2.id = "pa_system" + rowCount;
                    modeElement2.value = "";
                    cell16.appendChild(modeElement2);
                    
                    var cell17 = row.insertCell(17);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "latitude" + rowCount;
                    element2.id = "latitude" + rowCount;
                    element2.value = "";
                    cell17.appendChild(element2);
                    
                    var cell18 = row.insertCell(18);
                    var element2 = document.createElement("input");
                    element2.type = "text";
                    element2.name = "longitude" + rowCount;
                    element2.id = "longitude" + rowCount;
                    element2.value = "";
                    cell18.appendChild(element2);
                    
                    var cell19 = row.insertCell(19);
                    var element2 = document.createElement("input");
                    element2.class = "button";
                    element2.type = "button";
                    element2.name = "get_cordinate" + rowCount;
                    element2.id = "get_cordinate" + rowCount;
                    element2.value = "Get Cordinate";
                    element2.setAttribute("onclick", 'openMapForCord('+rowCount+')');
                    cell19.appendChild(element2);
              
                    
                    var cell20 = row.insertCell(20);
                    var element2 = document.createElement("a");
                    debugger;
                    element2.setAttribute("href","#");
                    element2.setAttribute("onclick", 'ViewCameraInfo('+junction_id+','+side_num+')');
                    element2.innerHTML = "View Camera Details";
                    cell20.appendChild(element2);
                    
                    
                }



            }
            
            function getSideName(id,junction_id,side_no) {
                debugger;
                var poleTypeArray;
                $.ajax({
                    url: 'slaveInfoCont',
                    type: 'GET',
                    dataType : 'json',
                    async: true,
                    contentType: 'application/json',
                    data: {task: 'getSideName',junctionID:junction_id,side_no:side_no},
                    success: function (response_data) {
                        debugger;
                        console.log(response_data);
                        document.getElementById(id).value = response_data.side_name;
                    }, error: function (error) {
                        debugger;
                        console.log(error.responseJSON.side_name);
                        
                    }

                });
            }

            function getPoleType() {
                debugger;
                var poleTypeArray;
                $.ajax({
                    url: "slaveInfoCont",
                    type: "GET",
                    dataType: "text",
                    contentType: "text/plain; charset=utf-8",
                    data: {task: "poleType"},
                    success: function (response_data) {
                        console.log(response_data.pole_type);
                        poleTypeArray = response_data.pole_type;
                        debugger;
                        console.log(response_data.pole_type);
                    }, error: function (error) {
                        console.log(error);
                    }

                });
                // return poleTypeArray;
            }

            function getPosition() {
                debugger;
                var positionArray;
                $.ajax({
                    url: "slaveInfoCont",
                    type: "GET",
                    dataType: "text",
                    contentType: "text/plain; charset=utf-8",
                    data: {task: "position"},
                    success: function (response_data) {
                        debugger;
                        positionArray = response_data.position;
                        console.log(response_data.position);
                    }

                });
                return positionArray;
            }

            function validateSide(id, rowCount, side_no) {
                var current_side_no = document.getElementById(id + rowCount);
                if (current_side_no > side_no) {
                    var message = "<td>Sorry , You can not add more row .You have completed  side..</td>";
                }
                $("#message").html(message);
            }
            
    function deleteRow(tableID) {
                try {
                    // alert(tableID);
                    var table = document.getElementById(tableID);
//                    var junction_id = document.getElementById("junction_id").value;
//                var side_num = rowCount;
                    debugger;
                    var rowCount = table.rows.length;        
                    var delete_palan_row=0;
                    for(var i=1; i<rowCount; i++) {
                        var row = table.rows[i];
                        //  alert(row);
                        var chkbox = row.cells[0].childNodes[0];
                        //     alert(chkbox);
                        if(chkbox !== null  && chkbox.checked === true || k<rowCount) {
                            // alert(i);
                            var  k=i;
                            for(k=i;k<rowCount;k++){
                                table.deleteRow(i);
                                delete_palan_row++;
                                document.getElementById("side_no").value=  document.getElementById("side_no").value-1;
                            }
                        }
                    }
                }catch(e) {
                   alert("Do you wish to delete this side!!!");
                }
    }
      var popupwin = null;
       debugger;
 
                function ViewPlanInfo(side_detail_id, sideName, junction_id) {
                 alert(side_detail_id,sideName);
        var queryString = "task=View_Image&imageid="+ side_detail_id + "&sideName=" + sideName + "&junction_id=" + junction_id;
        var url = "slaveInfoCont?" + queryString;
        popupwin = openPopUp(url, "View PlanInfo", 580, 900);
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
   function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
    debugger;
                return window.open(url, window_name, window_features);
            }
    function ViewCameraInfo(junction_id, side_no) {
//        var queryString = "junction_id=" + junction_id + "&program_version_no=" + program_version_no + "&no_of_sides=" + no_of_sides;
//        var url = "slaveInfoCont?" + queryString;
//        popupwin = openPopUp(url, "View Slave Info ", 580, 900);
        debugger;
        document.getElementById("junction_id1").value = junction_id;
        $("#side_no_details").val(side_no);
        document.forms['redirectCamera'].submit();

    }

    
    function openMapForCord(count) {
        var url="slaveInfoCont?task=GetCordinates1&count="+count;//"getCordinate";
        popupwin = openPopUp(url, "",  600, 630);
        
    }
 
          




        </script>
        <form name="redirectCamera" method="get" action="CameraCont" target="_blank">
            <input type="hidden" id="junction_id1" name="junction_id1" value="">
            <input type="hidden" id="side_no_details" name="side_no_details" value="">
        </form>
    </body>
</html>

