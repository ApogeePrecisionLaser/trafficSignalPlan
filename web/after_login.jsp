<%-- 
    Document   : index
    Created on : Jun 6, 2012, 2:34:35 PM
    Author     : Tarun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <link rel="stylesheet" href="style/style.css" media="screen">
        <link rel="stylesheet" href="style/Table_content.css" media="screen">
        <title>Traffic Signals Monitoring</title>
         <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        
        <script type="text/javascript" language="javascript">
       
function myFunction(id) {
    debugger;
  var x = document.getElementById("stopWebService");
   var x1 = document.getElementById("startWebService");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
  if(id==="stopWebService"){
if(x.value === "stopWebService")
{
      var queryString = "task=StopWebServiceResponse";

        var url = "loginCont?" + queryString;

        window.location.href = url; 
}
else{
 if(x1.value === "startWebService")
 {
    var queryString = "task=StartWebServiceResponse";

        var url = "loginCont?" + queryString;

        window.location.href = url; 
}
}
}
}

    function Openform(id) {
        debugger;
        var queryString = "task=StopWebServiceResponse";

        var url = "loginCont?" + queryString;

        window.location.href = url;
//        var x = document.getElementById('formPlan');
//        if (x.style.display === "none") {
//            x.style.display = "block";
//        } else {
//            x.style.display = "none";
//        }
document.getElementById("stopWebService").style.display = "none";
document.getElementById("startWebService").style.display = "block";
    }
      function Openform1(id) {
        debugger;
        var queryString = "task=StartWebServiceResponse";

        var url = "loginCont?" + queryString;

        window.location.href = url;
//        var x = document.getElementById('formPlan');
//        if (x.style.display === "none") {
//            x.style.display = "block";
//        } else {
//            x.style.display = "none";
//        }
document.getElementById("startWebService").style.display = "none";
document.getElementById("stopWebService").style.display = "block";
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
                    <DIV id="body" class="maindiv">
                        <form  method="post" action="loginCont">
<!--                        <input type="button" id="stopWebService" name="stopWebService" value="Stop WebService Response" onclick="web(id)">
                         <input type="button" id="startWebService" name="startWebService" value="Start WebService Response" onclick="web(id)">-->
                  
                         <input type="button" name="stopWebService" value="stopWebService" id="stopWebService" onclick="Openform(id)">
                         <input type="button" style="display: none" name="startWebService" value="startWebService" id="startWebService" onclick="Openform1(id)">


                        </form>
                    </DIV>
                </td>
            </tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
<!--                         <h2 align="center">Welcome To Traffic Signals Monitoring</h2>
        <table align="center">
            <tr><td><a href="loggedInJunctionCont">View Signal Status</a></td></tr><br>
            <tr><td><a href="junctionCont">Junction Details</a></td></tr><br>
            <tr><td><a href="CityCont">City Details</a></td></tr><br>
            <tr><td><a href="stateCont">State Details</a></td></tr><br>
            <tr><td><a href="districtCont">District Details</a></td></tr>
            <tr><td><a href="JunSunriseSunsetCont">Sunrise Sunset Details</a></td></tr>
            <tr><td><a href="LoggerHistoryCont">Logger History Details</a></td></tr>
            <tr><td><a href="ts_statusShowerCont">view refresh</a></td></tr>
            <tr><td><a href="riseSetCont">List of riseset time</a></td></tr>
        </table>-->
       
    </body>
</html>
