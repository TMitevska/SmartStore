<%@ page import="com.tamara.masters.smartshop.strings.Translator" %>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <title>${title}</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="jquery.js"></script>
    <script src="bootstrap.min.js"></script>
    <script src="jquery.prettyPhoto.js"></script>
    <script src="jquery.isotope.min.js"></script>
    <script src="/js/wow.min.js"></script>
    <script src="/js/main.js"></script>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="togglebutton.css">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="animate.min.css" >
    <link rel="stylesheet" type="text/css" href="prettyPhoto.css" >
    <link rel="stylesheet" type="text/css" href="style.css" >
    <link rel="stylesheet" type="text/css" href="responsive.css" >
    <script src="//mozilla.github.io/pdf.js/build/pdf.js"></script>
    <script src="//mozilla.github.io/pdf.js/build/pdf.js"></script>
    <script type="text/javascript" charset="utf-8" src="https://github.com/PlatformSupport/pdf-sample/blob/master/PDF%20Sample/scripts/pdf.js/pdf.worker.js"></script>
    <script type="text/javascript">
        $( document ).ready(function() {
            loaddata();
            setInterval(function(){
                $('#invoiceTable tbody tr').not(':first').remove();
                loaddata();
            }, 60000);

        });
        function loaddata(){
            $.ajax({
                url: "http://localhost:5208/getDocuments?userId="+"${userid}",
                type: "POST",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    if(data){
                        $.each(JSON.parse(data), function (index, value) {
                            var res = value.split(":");
                            $('#invoiceTable tbody tr:last').after("<tr id=\""+res[0]+"\"><td width='100' align=\"left\"><b>"+(index+1)+". </b></td><td align=\"left\" width='200'>"+res[1]+"</td><td width='250' align=\"left\">"+res[2]+"</td><td width='150' align=\"left\">"+res[3]+" </td><td width='150' align=\"left\"><a onclick=\"previewInvoice('"+res[0]+"')\" title=\"${preview}\"><img style=\"cursor:pointer\" src='preview.png' width='30' height='30' alt='img'></a><a onclick=\"printInvoice('"+res[0]+"')\" style=\"margin:0px 20px\" title=\"${download}\"><img style=\"cursor:pointer\" src='download.png' width='30' height='30' alt='img'></a><a onclick=\"deleteInvoice('"+res[0]+"')\" class=\"delete\" title=\"${delete}\"><img style=\"cursor:pointer\" src='delete.png' width='30' height='30' alt='img'></a></td></tr>");
                        });

                        <%if(!request.getParameter("roleId").equals(Translator.acountantid) && !request.getParameter("roleId").equals(Translator.ownerid)){%>
                        $(".delete").hide();
                        <%}%>
                    }
                }
            });
        };
        function deleteInvoice(id){
            $.ajax({
                url: "http://localhost:5208/deleteDocument?id="+id,
                type: "POST",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                        swal ( "" ,  "${successDelete}" ,  "success" )
                        location.reload();
                }
            });
        };
        function printInvoice(id){
            $.ajax({
                url: "http://localhost:5208/getDocument?id="+id,
                type: "POST",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    if(data){
                        var res = data.split(",");
                        for(i=0;i<res.length;i++) {
                            attr = res[i].split("=");
                            if (attr[0] == "document_number")
                                $("#document_number").val(attr[1]);
                            else if (attr[0] == "company_name")
                                $("#company_name").val(attr[1]);
                            else if (attr[0] == "company_info")
                                $("#company_info").val(attr[1]);
                            else if (attr[0] == "creation_date")
                                $("#creation_date").val(attr[1]);
                        }
                        $.ajax({
                            url: "http://localhost:5208/getDocumentProducts?id="+id,
                            type: "POST",
                            error: function () {
                                swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                            },
                            success: function (productdata) {
                                    var products = productdata.split(",");
                                    $("#num").val(products.length-1);
                                    $("#products").val(productdata);
                                    $.ajax({
                                        url: "http://localhost:9090/downloadInvoice",
                                        type: "POST",
                                        data: $("#documentInfo").serialize(),
                                        error: function () {
                                            swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                                        },
                                        success: function (result) {
                                            if(result){
                                                swal ( "" ,  "${destination}" ,  "success" )
                                            }

                                        }
                                    });
                            }
                        });
                    }

                }
            });
        };
        function previewInvoice(id){
            $.ajax({
                url: "http://localhost:5208/getDocument?id="+id,
                type: "POST",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    if(data){
                        var res = data.split(",");
                        for(i=0;i<res.length;i++) {
                            attr = res[i].split("=");
                            if (attr[0] == "document_number")
                                $("#document_number").val(attr[1]);
                            else if (attr[0] == "company_name")
                                $("#company_name").val(attr[1]);
                            else if (attr[0] == "company_info")
                                $("#company_info").val(attr[1]);
                            else if (attr[0] == "creation_date")
                                $("#creation_date").val(attr[1]);
                        }
                        $.ajax({
                            url: "http://localhost:5208/getDocumentProducts?id="+id,
                            type: "POST",
                            error: function () {
                                swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                            },
                            success: function (productdata) {
                                var products = productdata.split(",");
                                $("#num").val(products.length-1);
                                $("#products").val(productdata);
                                $.ajax({
                                    url: "http://localhost:9090/downloadInvoice",
                                    type: "POST",
                                    data: $("#documentInfo").serialize(),
                                    error: function () {
                                        swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                                    },
                                    success: function (result) {
                                        if(result){
                                            $.ajax({
                                                url: "http://localhost:9090/previewFile?path="+result,
                                                type: "POST",
                                                error: function () {
                                                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                                                },
                                                success: function (result) {
                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        });
                    }

                }
            });
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
                        <li class="active"><a href='http://localhost:9090/invoicePdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${invoiceList}</a></li>
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
    <div class="slider" style="margin-top: 200px;text-align: center;alignment: center;height: 550px">
        <div class="container">
            <div class="login-page">
                <div class="form ">
                    <form id="loginForm" class="login-form">
                        <img src="invoicelist.png" class="margin_bottom30" width="400" height="80" alt="img">
                        <table id="invoiceTable" class="table table-striped">
                            <thead>
                            <th>${productOrderNumber}</th>
                            <th>${INVOICE_NUMBER}</th>
                            <th>${customerCompanyName}</th>
                            <th>${amount}</th>
                            <th></th>
                            </thead>
                            <tbody>
                            <tr></tr>
                            </tbody>

                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>


        <form id="documentInfo">
            <input id="document_number" name="document_number" type="hidden" value=""/>
            <input id="company_name"  name="company_name" type="hidden" value=""/>
            <input id="company_info" name="company_info" type="hidden" value=""/>
            <input id="creation_date" name="creation_date" type="hidden" value=""/>
            <input id="num" name="num" type="hidden" value=""/>
            <input id="products" name="products" type="hidden" value=""/>
            <input id="language" name="language" type="hidden" value="false"/>
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
                    <div class="col-sm-6">
                    </div>
                </div>
            </div>
        </footer>
    </body>
</html>


