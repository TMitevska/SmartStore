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

        function bs_input_file() {
            $("#sales_form1").append(
                function() {
                    var e = $("#input-file");
                    console.log($("#input-file").length)
                    if ( ! e.prev().hasClass('input-ghost') ) {
                        var element = $("<input name='file' id='file' accept='.csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel' type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                        element.attr("name",e.attr("name"));
                        element.change(function(){
                            $("#file_name").val((element.val()).split('\\').pop());
                            $("#submit").show();
                        });
                        e.find("button.btn-choose").click(function(){
                            element.click();
                        });
                        e.find("button.btn-reset").click(function(){
                            element.val(null);
                            $("#file_name").val('');
                            $("#submit").hide();
                        });
                        e.find('input').css("cursor","pointer");
                        e.find('input').mousedown(function() {
                            e.parents('#input-file').prev().click();
                            return false;
                        });
                        return element;
                    }
                }
            );

            $("#sales_form2").append(
                function() {
                    var e = $("#input-file2");
                    console.log($("#input-file").length)
                    if ( ! e.prev().hasClass('input-ghost') ) {
                        var element = $("<input name='file' id='file' accept='.csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel' type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                        element.attr("name",e.attr("name"));
                        element.change(function(){
                            $("#file_name2").val((element.val()).split('\\').pop());
                            $("#submit2").show();
                        });
                        e.find("button.btn-choose").click(function(){
                            element.click();
                        });
                        e.find("button.btn-reset").click(function(){
                            element.val(null);
                            $("#file_name2").val('');
                            $("#submit2").hide();
                        });
                        e.find('input').css("cursor","pointer");
                        e.find('input').mousedown(function() {
                            e.parents('#input-file2').prev().click();
                            return false;
                        });
                        return element;
                    }
                }
            );

        }
        $(function() {
            $("#submit").hide();
            $("#submit2").hide();
            bs_input_file();
        });

        function addSales(){
            var form = $("#sales_form1");
            var formdata = false;
            if (window.FormData){
                formdata = new FormData(form[0]);
            }
            $.ajax({
                url: "http://localhost:5208/storage/addPredictedSales",
                type: "POST",
                enctype: 'multipart/form-data',
                data: formdata ? formdata : form.serialize(),
                processData: false,
                contentType: false,
                cache: false,
                success: function (data) {
                    $("#file_name").val('');
                    $("#submit").hide();
                },
                error: function (e) {
                    console.log("error");
                    location.href="http://localhost:9090/products?userId=${userid}&roleId=${roleId}";
                }
            });
            console.log("done");
            location.href="http://localhost:9090/products?userId=${userid}&roleId=${roleId}";
        };

        function addProducts(){
            var form = $("#sales_form2");
            var formdata = false;
            if (window.FormData){
                formdata = new FormData(form[0]);
            }
            $.ajax({
                url: "http://localhost:5208/storage/addProducts",
                type: "POST",
                enctype: 'multipart/form-data',
                data: formdata ? formdata : form.serialize(),
                processData: false,
                contentType: false,
                cache: false,
                success: function (data) {
                    $("#file_name2").val('');
                    $("#submit2").hide();
                },
                error: function (e) {
                    console.log("error");
                    location.href="http://localhost:9090/products?userId=${userid}&roleId=${roleId}";
                 }
            });
            console.log("done");
            location.href="http://localhost:9090/products?userId=${userid}&roleId=${roleId}";
        };
        function deleteProducts(){
            $.ajax({
                url: "http://localhost:5208/storage/deleteProducts",
                type: "POST",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    swal ( "" ,  "${successDelete}" ,  "success" )
                }
            });
        };
        function deletePredictions(){
            $.ajax({
                url: "http://localhost:5208/storage/deletePredictions",
                type: "POST",
                error: function () {
                    swal ( "${error}" ,  "${errorDatabaseConnection}" ,  "error" )
                },
                success: function (data) {
                    swal ( "" ,  "${successDelete}" ,  "success" )
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
                        <li ><a href='http://localhost:9090/invoicePdfList?userId=${userid}&roleId=${roleId}'><i class="fa fa-list" aria-hidden="true"></i> ${invoiceList}</a></li>
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
            <table id="importTable">
                <tr>
                    <div class="container">
                        <div class="col-md-8 col-md-offset-2">
                            <h3>${newPredictions}</h3>
                            <form method="POST" action="http://localhost:5208/storage/addPredictedSales" target="hidden_iframe" enctype="multipart/form-data" id="sales_form1">
                                <input type="hidden" value="${userid}" name="userId">
                                <input type="hidden" value="${roleId}" name="roleId">
                                <!-- COMPONENT START -->
                                <div class="form-group">
                                    <div class="input-group input-file" name="file" id="input-file">
                                        <span class="input-group-btn">
                                            <button class="btn btn-default btn-choose" type="button">${choose}</button>
                                        </span>
                                        <input id="file_name" type="text" class="form-control"  placeholder="${newFile}"/>
                                        <span class="input-group-btn">
                                             <button class="btn btn-warning btn-reset" type="button">${delete}</button>
                                        </span>
                                    </div>
                                </div>
                                <!-- COMPONENT END -->
                                <div class="form-group">
                                    <button id="submit" type="submit" class="btn btn-primary pull-right">${submit}</button>
                                </div>
                            </form>
                            <iframe name="hidden_iframe" hidden></iframe>
                            <h5 style="color: cornflowerblue; text-decoration-style: wavy">${message}</h5>
                        </div>
                    </div>
                </tr>
            </table>
            <table id="table2">
                <tr>
                    <div class="container">
                        <div class="col-md-8 col-md-offset-2">
                            <h3>${newProducts}</h3>
                            <form method="POST" action="#" enctype="multipart/form-data" id="sales_form2">
                                <input type="hidden" value="${userid}" name="userId">
                                <input type="hidden" value="${roleId}" name="roleId">
                                <!-- COMPONENT START -->
                                <div class="form-group">
                                    <div class="input-group input-file" name="file" id="input-file2">
                                        <span class="input-group-btn">
                                            <button class="btn btn-default btn-choose" type="button">${choose}</button>
                                        </span>
                                        <input id="file_name2" type="text" class="form-control"  placeholder="${newFile}"/>
                                        <span class="input-group-btn">
                                             <button class="btn btn-warning btn-reset" type="button">${delete}</button>
                                        </span>
                                    </div>
                                </div>
                                <!-- COMPONENT END -->
                                <div class="form-group">
                                    <button id="submit2" type="submit" class="btn btn-primary pull-right" onclick="addProducts()">${submit}</button>
                                </div>
                            </form>
                            <h5 style="color: cornflowerblue; text-decoration-style: wavy">${message}</h5>
                        </div>
                    </div>
                </tr>
            </table>

            <div class="container" style="margin-top: 100px;">
                <button  class="btn btn-warning btn-block" onclick="deletePredictions()"> ${deletePredictions}</button>
                <button  class="btn btn-default btn-block" onclick="deleteProducts()"> ${deleteProducts}</button>
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
                <div class="col-sm-6">
                </div>
            </div>
        </div>
    </footer>

</body>
</html>


