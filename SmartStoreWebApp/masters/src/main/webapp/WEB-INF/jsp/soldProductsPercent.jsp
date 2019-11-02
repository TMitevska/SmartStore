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
        function refreshChart(){
            var sales = [];
            $.ajax({
                url: "http://localhost:5208/getAllProductsPercent",
                type: "GET",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    var sum = 0;
                    if(data){
                        $.each(JSON.parse(data), function (index, value) {
                            var element = {y: value.unit_sales , label: value.item_nbr};
                            sales.push(element);
                            sum += parseInt(value.unit_sales);
                        });
                    }

                    $.each(sales, function (index, value) {
                       sales[index].y = (parseInt(sales[index].y)/sum)*100;
                    });

                    var chart = new CanvasJS.Chart("chartContainer", {
                        theme: "dark1", // "light1", "light2", "dark1", "dark2"
                        exportEnabled: true,
                        animationEnabled: true,
                        title: {
                            text: "${soldQuantityByProduct}"
                        },
                        data: [{
                            type: "pie",
                            startAngle: 25,
                            toolTipContent: "<b>{label}</b>: {y}%",
                            showInLegend: "true",
                            legendText: "{label}",
                            indexLabelFontSize: 16,
                            indexLabel: "{label} - {y}%",
                            dataPoints: sales
                        }]
                    });
                    chart.render();
                }
            });
        };

        window.onload = function() {
            refreshChart();

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
                        <li><a href='http://localhost:9090/products?userId=${userid}&roleId=${roleId}'><i class="fa fa-lemon-o" aria-hidden="true"></i> ${products}</a></li>
                        <li><a href='http://localhost:9090/soldProducts?userId=${userid}&roleId=${roleId}'><i class="fa fa-briefcase" aria-hidden="true"></i> ${predict}</a></li>
                        <li class="active" ><a href='http://localhost:9090/soldProductsPr?userId=${userid}&roleId=${roleId}'><i class="fa fa-briefcase" aria-hidden="true"></i> ${resources}</a></li>
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