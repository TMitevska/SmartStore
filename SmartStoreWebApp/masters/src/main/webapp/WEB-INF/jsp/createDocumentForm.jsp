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
    <link rel="stylesheet" type="text/css" href="form.css" >
    <link rel="stylesheet" type="text/css" href="responsive.css" >
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script type="text/javascript">
        $( document ).ready(function() {
            $.ajax({
                url: "http://localhost:5208/getdocumentNumber",
                type: "GET",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    if(data){
                        $("#INVOICE_NUMBER").val(data);
                    }
                }
            });
        });
        function addproducts(){
            var url = "http://localhost:9090/addproductsinDocument";
            url += "?userId="+$("#userId").val()+"&roleId=${roleId}&numberOfProducts="+$("#numberOfProducts").val()+"&customerCompanyDescription="+$("#customerCompanyDescription").val();
            url += "&customerCompanyName="+$("#customerCompanyName").val()+"&INVOICE_NUMBER="+$("#INVOICE_NUMBER").val();
            location.href = url;
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
                        <li class="active"><a href='http://localhost:9090/createDocument?userId=${userid}&roleId=${roleId}'><i class="fa fa-plus-square-o" aria-hidden="true"></i> ${createinvoice}</a></li>
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
                    <form id="documentInfo" class="login-form">
                        <input type="hidden" id="INVOICE_NUMBER" name="INVOICE_NUMBER" />
                        <input type="text" id="customerCompanyName" name="customerCompanyName" placeholder="${customerCompanyName}"/>
                        <input type="text" id="customerCompanyDescription" name="customerCompanyDescription" placeholder="${customerCompanyDescription}"/>
                        <input type="number" min="1" id="numberOfProducts" name="numberOfProducts" placeholder="${numberOfProducts}"/>
                        <input type="hidden" id="userId" name="userId" value="${userId}"/>

                        <input id="submit" type="button" onclick="addproducts()" value="${next}"/>
                    </form>
                </div>
            </div>
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

            </div>
        </div>
    </footer>
</body>

</html>


