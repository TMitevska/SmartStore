<%@ page import="com.tamara.masters.smartshop.strings.Translator" %>
<!DOCTYPE HTML>
<html>
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
        function refreshChart(productId){
            $("#dropdownName").text(productId + " >>");
            var sales = [];
            $.ajax({
                url: "http://localhost:5208/getSoldProducts?itemId="+productId,
                type: "GET",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    if(data){
                        $.each(JSON.parse(data), function (index, value) {

                            var element = {x: new Date(value.date), y: value.unit_sales};
                            sales.push(element);

                        });
                        var chart = new CanvasJS.Chart("chartContainer", {
                            animationEnabled: true,
                            exportEnabled: true,
                            title:{
                                text: "${soldQuantityByProduct} " + productId
                            },
                            axisY:{
                                title: "${soldQuantity}",
                                includeZero: false,
                                interval: 1,
                                suffix: "",
                                valueFormatString: "#"
                            },
                            axisX:{
                                title: "${date}",
                            },
                            data: [{
                                type: "stepLine",
                                yValueFormatString: "#",
                                xValueFormatString: "DD MMM YYYY",
                                markerSize: 5,
                                dataPoints: sales
                            }]
                        });
                        chart.render();
                    }
                }
            });
        };

        window.onload = function() {
            $.ajax({
                url: "http://localhost:5208/getAllProducts",
                type: "GET",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    if(data){

                        $.each(JSON.parse(data), function (index, value) {
                            $("#productDropdown").append("<li><a onclick='refreshChart(" + value.item_nbr + ")'>" + value.item_nbr + " - " + value.name + "</a></li>");
                        });
                    }
                }
            });
            refreshChart("1501508");
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
                        <li  ><a href="http://localhost:9090/remoteControl?userId=${userid}&roleId=${roleId}"><i class="fa fa-laptop" aria-hidden="true"></i> ${remoteControl} </a></li>
                        <%if(request.getParameter("roleId").equals(Translator.acountantid) || request.getParameter("roleId").equals(Translator.ownerid)){%>
                        <li><a href='http://localhost:9090/createDocument?userId=${userid}&roleId=${roleId}'><i class="fa fa-plus-square-o" aria-hidden="true"></i> ${createinvoice}</a></li>
                        <%}%>
                        <li><a href='http://localhost:9090/invoicePdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${invoiceList}</a></li>
                        <li><a href='http://localhost:9090/payslipPdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${payslipList}</a></li>
                        <li><a href='http://localhost:9090/products?userId=${userid}&roleId=${roleId}'><i class="fa fa-lemon-o" aria-hidden="true"></i> ${products}</a></li>
                        <li class="active" ><a href='http://localhost:9090/soldProducts?userId=${userid}&roleId=${roleId}'><i class="fa fa-briefcase" aria-hidden="true"></i> ${predict}</a></li>
                        <li ><a href='http://localhost:9090/soldProductsPr?userId=${userid}&roleId=${roleId}'><i class="fa fa-briefcase" aria-hidden="true"></i> ${resources}</a></li>
                        <li><a href='http://localhost:9090/'> <i class="fa fa-sign-out" aria-hidden="true"></i> ${logout}</a></li>
                    </ul>
                </div>
            </div>
            <!--/.container-->
        </nav>
        <!--/nav-->
    </header>

    <div class="container" style="margin-top: 150px">
        <div class="dropdown" style="alignment: left">
            <button id="dropdownName" class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                <span class="caret"></span></button>
            <ul id="productDropdown" class="dropdown-menu">
            </ul>
        </div>
    </div>

    <div class="slider" style="margin-top: 30px;text-align: center;alignment: center;height: 500px">
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