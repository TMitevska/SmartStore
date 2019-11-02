<%@ page import="com.tamara.masters.smartshop.strings.Translator" %>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <title>${title}</title>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="jquery.js"></script>
    <script src="bootstrap.min.js"></script>
    <script src="jquery.prettyPhoto.js"></script>
    <script src="jquery.isotope.min.js"></script>
    <script src="/js/wow.min.js"></script>
    <script src="/js/main.js"></script>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="togglebutton.css">
    <link rel="stylesheet" type="text/css" href="bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="animate.min.css" >
    <link rel="stylesheet" type="text/css" href="prettyPhoto.css" >
    <link rel="stylesheet" type="text/css" href="style.css" >
    <link rel="stylesheet" type="text/css" href="responsive.css" >

    <script type="text/javascript">
        function changeDeviceStatus(){
            var result = "";
            if(document.getElementById("mytoggle").checked)
                result += "true,";
            else
                result += "false,";
            if(document.getElementById("mytoggle2").checked)
                result += "true,";
            else
                result += "false,";
            if(document.getElementById("mytoggle3").checked) {
                result += "true,";
                $("#livestream").show();
            }
            else {
                result += "false,";
                $("#livestream").hide();
            }
            $("#status").val(result);
            $.ajax({
                url: "http://localhost:5208/changeRemoteDevicesStatus",
                type: "GET",
                data: $("#update").serialize(),
                success: function (data) {
                }
            });
        }

        $(document).ready(function()  {
            checkStatus();
            setInterval(function(){
                checkStatus();
            }, 2000);

        });
        function checkStatus(){
            $.ajax({
                url: "http://localhost:5208/getRemoteDevicesStatus",
                type: "GET",
                data: $("#loginForm").serialize(),
                success: function (data) {
                    var result = data.split(":");
                    $("#controlId").val(result[0]);
                    var result = data.split(":");
                    if(result[4]=="true")
                        $("#mytoggle").prop("checked",true);
                    else
                        $("#mytoggle").prop("checked",false);
                    if(result[8]=="true")
                        $("#mytoggle2").prop("checked",true);
                    else
                        $("#mytoggle2").prop("checked",false);
                    if(result[10]=="true") {
                        $("#mytoggle3").prop("checked", true);
                        $("#livestream").show();
                    }
                    else {
                        $("#mytoggle3").prop("checked", false);
                        $("#livestream").hide();
                    }

                }
            });
        };
        function redirectLivestream(){
            window.open('http://192.168.43.254:8081', '', '_blank');
        };
    </script>
</head>
<body class="homepage">
<header id="header">
    <nav class="navbar navbar-fixed-top" role="banner">
        <div class="container">
            <div class="collapse navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
                    <li ><a href="http://localhost:9090/home?userId=${userid}&roleId=${roleId}"> <i class="fa fa-home" aria-hidden="true"></i> ${home}</a></li>
                    <li class="active" ><a href="http://localhost:9090/remoteControl?userId=${userid}&roleId=${roleId}"><i class="fa fa-laptop" aria-hidden="true"></i> ${remoteControl} </a></li>
                    <%if(request.getParameter("roleId").equals(Translator.acountantid) || request.getParameter("roleId").equals(Translator.ownerid)){%>
                    <li><a href='http://localhost:9090/createDocument?userId=${userid}&roleId=${roleId}'><i class="fa fa-plus-square-o" aria-hidden="true"></i> ${createinvoice}</a></li>
                    <%}%>
                    <li><a href='http://localhost:9090/invoicePdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${invoiceList}</a></li>
                    <li><a href='http://localhost:9090/payslipPdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${payslipList}</a></li>
                    <li><a href='http://localhost:9090/business?userId=${userid}&roleId=${roleId}'><i class="fa fa-briefcase" aria-hidden="true"></i> ${statistics}</a></li>
                    <li><a href='http://localhost:9090/'> <i class="fa fa-sign-out" aria-hidden="true"></i> ${logout}</a></li>
                </ul>
            </div>
        </div>
        <!--/.container-->
    </nav>
    <!--/nav-->
</header>
    <div class="slider" style="margin-top: 200px;height: 550px">
        <div class="container">
            <div class="login-page">
                <div class="form ">
                    <form id="loginForm" class="login-form">
                        <div class="newRow margin_bottom100">&nbsp;</div>
                        <table align="center">
                            <tbody>
                            <tr>
                                <td align="left"><div><h1 style="margin-left:20px;margin-top: 10px;color: #0f0f0f">${music}</h1></div></td>
                                <td align="left" style="margin-left: 20px">
                                    <div class="toggle margin_left100 " style="margin-left:100px;">
                                        <input type="checkbox" name="toggle" class="check-checkbox" id="mytoggle" onclick="changeDeviceStatus()">
                                        <label class="check-label" for="mytoggle">
                                            <div id="background"></div>
                                            <span class="face">
                                                    <span class="face-container">
                                                      <span class="eye left"></span>
                                                      <span class="eye right"></span>
                                                      <span class="mouth"></span>
                                                    </span>
                                                 </span>
                                        </label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left"><div><h1 style="margin-left:20px;margin-top: 10px;color: #0f0f0f">${light}</h1></div></td>
                                <td align="left" style="margin-left: 20px">
                                    <div class="toggle margin_left100" style="margin-left:100px;">
                                        <input type="checkbox" name="toggle" class="check-checkbox" id="mytoggle2" onclick="changeDeviceStatus()">
                                        <label class="check-label" for="mytoggle2">
                                            <div id="background2"></div>
                                            <span class="face">
                                                    <span class="face-container">
                                                      <span class="eye left"></span>
                                                      <span class="eye right"></span>
                                                      <span class="mouth"></span>
                                                    </span>
                                                  </span>
                                        </label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left"><div><h1 style="margin-left:20px;margin-top: 10px;color: #0f0f0f">${camera}</h1></div></td>
                                <td align="left" style="margin-left: 20px" >
                                    <div class="toggle margin_left100" style="margin-left:100px;">
                                        <input type="checkbox" name="toggle" class="check-checkbox" id="mytoggle3" onclick="changeDeviceStatus()">
                                        <label class="check-label" for="mytoggle3">
                                            <div id="background3"></div>
                                            <span class="face">
                                                    <span class="face-container">
                                                      <span class="eye left"></span>
                                                      <span class="eye right"></span>
                                                      <span class="mouth"></span>
                                                    </span>
                                                  </span>
                                        </label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left">
                                    <input id="livestream" style="margin: 20px" type="button" onclick="redirectLivestream()" value="Livestream"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="newRow margin_bottom100">&nbsp;</div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <form id="update">
        <input type="hidden" name="status" id="status" value="" />
        <input type="hidden" id="controlId" name="controlId" value="" />
    </form>

    <!--/#recent-works-->
    <footer id="footer" class="midnight-blue">
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    &copy; ${title}. All Rights Reserved.
                    <div class="credits">
                        Designed by ${creator}
                    </div>
                </div>

            </div>
        </div>
    </footer>

</body>

</html>


