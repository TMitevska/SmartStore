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
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
    <script>
        window.onload = function () {

            var dps = []; // dataPoints
            var chart = new CanvasJS.Chart("chartContainer", {
                title :{
                    text: "${clientsInShop}"
                },
                axisY: {
                    title: "${clients}",
                    includeZero: true, gridThickness: 2,
                    interval:2
                },
                axisX:{
                    title: "${time}",
                    gridThickness: 2,
                    interval:2,
                    valueFormatString: "hh:mm:ss TT ",
                    labelAngle: 50
                },
                data: [{
                    type: "line",
                    dataPoints: dps
                }]
            });

            var xVal = formatDate();
            var yVal = 5;
            var updateInterval = 10000;
            var dataLength = 20; // number of dataPoints visible at any point

            var updateChart = function () {
                xVal = formatDate();
                yVal = yVal +  Math.round(5 + Math.random() *(-5-5));
                if(yVal<0)
                    yVal *= -1;
                dps.push({
                    x: formatDate(),
                    y: yVal
                });

                if (dps.length > dataLength) {
                    dps.shift();
                }

                chart.render();
            };

            updateChart();
            setInterval(function(){updateChart()}, updateInterval);

        }

        function formatDate() {
            var date = new Date();
            var now_utc =  Date.UTC(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(),
                date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());

            return new Date(now_utc);
        }
    </script>
</head>
<body class="homepage">
<header id="header">
    <nav class="navbar navbar-fixed-top" role="banner">
        <div class="container">
            <div class="collapse navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
                    <li ><a href="http://localhost:9090/home?userId=${userid}&roleId=${roleId}"> <i class="fa fa-home" aria-hidden="true"></i> ${home}</a></li>
                    <li  ><a href="http://localhost:9090/remoteControl?userId=${userid}&roleId=${roleId}"><i class="fa fa-laptop" aria-hidden="true"></i> ${remoteControl} </a></li>
                    <%if(request.getParameter("roleId").equals(Translator.acountantid) || request.getParameter("roleId").equals(Translator.ownerid)){%>
                    <li><a href='http://localhost:9090/createDocument?userId=${userid}&roleId=${roleId}'><i class="fa fa-plus-square-o" aria-hidden="true"></i> ${createinvoice}</a></li>
                    <%}%>
                    <li><a href='http://localhost:9090/invoicePdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${invoiceList}</a></li>
                    <li><a href='http://localhost:9090/payslipPdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${payslipList}</a></li>
                    <li class="active"><a href='http://localhost:9090/business?userId=${userid}&roleId=${roleId}'><i class="fa fa-briefcase" aria-hidden="true"></i> ${statistics}</a></li>
                    <li><a href='http://localhost:9090/'> <i class="fa fa-sign-out" aria-hidden="true"></i> ${logout}</a></li>
                </ul>
            </div>
        </div>
        <!--/.container-->
    </nav>
    <!--/nav-->
</header>
<div class="slider" style="margin-top: 200px;text-align: center;alignment: center;height: 550px">
    <div class="container">
        <div id="chartContainer" style="height: 500px; width: 100%;"></div>
    </div>
</div>

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
            <div class="col-sm-6">
            </div>
        </div>
    </div>
</footer>
</body>
</html>