<%-- 
    Document   : multipleImage
    Created on : Jan 29, 2019, 4:10:37 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<!--<script src="js4LightBox/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js4LightBox/lightbox-2.6.min.js"></script>
<link href="css4LightBox/lightbox.css" rel="stylesheet" />-->
<link rel="stylesheet" type="text/css" href="style/style.css" />
<link rel="stylesheet" type="text/css" href="style/Table_content.css" />
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Images</title>
    </head>
    <body>
        <div>
            <table>
                <tr>
                    <td>
                        <form name="form1"  method="post" action="slaveInfoCont" enctype= "multipart/form-data">
                            <DIV align="center" style="display: list-item" >
                                <table id="table1" align="center" width="100%">
                                    <tr><td align="center">
                                    <c:forEach var="imageList" items="${requestScope['imageList']}" varStatus="loopCounter">      
                                     
                                         <img  src="slaveInfoCont?task2=getImageThumb&image_name=${imageList.image_name}&imageid1=${imageid1}&side_name1=${side_name1}&junctionName=${junctionName}"  width="380px" height="250px" alt=""/>
                                    
                                    </c:forEach>
                                    </td></tr>
                                    
                                </table>
                            </DIV>
                        </form>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
