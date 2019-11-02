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
        function addDocument(){
            $.ajax({
                url: "http://localhost:5208/addDocument",
                type: "POST",
                data: $("#document").serialize(),
                success: function (data) {
                    if(data){
                        $("#documentid").val(data);
                        $.ajax({
                            url: "http://localhost:5208/addProducts",
                            type: "POST",
                            data: $("#productForm").serialize(),
                            success: function (result) {
                                location.href="http://localhost:9090/invoicePdfList?userId=${userId}&roleId=${roleId}";
                            }
                        });
                    }
                }
            });
        };
    </script>
    <style type="text/css">
        .form input {
            font-family: "Roboto", sans-serif;
            outline: 0;
            background: #f2f2f2;
            width: 95%;
            border: 0;
            margin: 0 0 3px;
            padding: 5px;
            box-sizing: border-box;
            font-size: 12px;
        }
    </style>
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
            <div class="form ">
                <form id="productForm" class="login-form">
                    <input type="hidden" name="numberOfProducts" value="${numberOfProducts}"/>
                    <input type="hidden" id="documentid" name="documentid" value=""/>
                    <div style="overflow-y:scroll;height:350px;margin:10px">
                        <table class="table table-striped">
                            <thead>
                                <th>${productOrderNumber}</th>
                                <th>${productName}</th>
                                <th>${productType}</th>
                                <th>${productNumber}</th>
                                <th>${productValue}</th>
                            </thead>
                            <tbody>
                            <%for(int i=0;i<Integer.parseInt( request.getParameter("numberOfProducts"));i++){%>
                                <tr>
                                    <td><b><%=(i+1)%>.</b></td>
                                    <td><input name="productName<%=(i+1)%>" type="text"/></td>
                                    <td><input name="productType<%=(i+1)%>" type="text"/></td>
                                    <td><input name="productNumber<%=(i+1)%>" type="number"/></td>
                                    <td><input name="productValue<%=(i+1)%>" step="0.01" type="number"/></td>
                                </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                    <input id="submit" type="button" onclick="addDocument()" value="${next}"/>
                </form>
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
    <form id="document">
        <input type="hidden" name="invoiceNumber" value="${INVOICE_NUMBER}"/>
        <input type="hidden" name="companyName" value="${customerCompanyName}"/>
        <input type="hidden" name="description" value="${customerCompanyDescription}"/>
        <input type="hidden" name="userId" value="${userId}"/>
    </form>
</body>

</html>


