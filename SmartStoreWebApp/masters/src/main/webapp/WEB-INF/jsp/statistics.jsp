<%@ page import="com.tamara.masters.smartshop.strings.Translator" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="bootstrap.min.css" >
    <link rel="stylesheet" type="text/css" href="font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="animate.min.css" >
    <link rel="stylesheet" type="text/css" href="prettyPhoto.css" >
    <link rel="stylesheet" type="text/css" href="style.css" >
    <link rel="stylesheet" type="text/css" href="responsive.css" >
</head>
<body class="homepage">
<header id="header">
    <nav class="navbar navbar-fixed-top" role="banner">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="http://localhost:9090/home?userId=${userid}&roleId=${roleId}">${title}</a>
            </div>

            <div class="collapse navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
                    <li ><a href="http://localhost:9090/home?userId=${userid}&roleId=${roleId}"> <i class="fa fa-home" aria-hidden="true"></i> ${home}</a></li>
                    <li><a href="http://localhost:9090/remoteControl?userId=${userid}&roleId=${roleId}"><i class="fa fa-laptop" aria-hidden="true"></i> ${remoteControl} </a></li>
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

<section id="feature" style="margin-top: 200px;height: 550px">
    <div class="container">
        <div class="row">
            <div class="features">
                <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="feature-wrap">
                        <a href='http://localhost:9090/products?userId=${userid}&roleId=${roleId}'>
                            <i class="fa fa-star" aria-hidden="true"></i>
                            <h2>${products}</h2>
                        </a>
                    </div>
                </div>
                <!--/.col-md-4-->

                <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="feature-wrap">
                        <a href='http://localhost:9090/soldProducts?userId=${userid}&roleId=${roleId}'>
                            <i class="fa fa-magic" aria-hidden="true"></i>
                            <h2> ${predict}</h2>
                        </a>
                    </div>
                </div>
                <!--/.col-md-4-->

                <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="feature-wrap">
                        <a href='http://localhost:9090/soldProductsPr?userId=${userid}&roleId=${roleId}'>
                            <i class="fa fa-cogs" aria-hidden="true"></i>
                            <h2>${resources}</h2>
                        </a>
                    </div>
                </div>
                <!--/.col-md-4-->

                <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="feature-wrap">
                        <a href='http://localhost:9090/clients?userId=${userid}&roleId=${roleId}'>
                            <i class="fa fa-users" aria-hidden="true"></i>
                            <h2>${clients}</h2>
                        </a>
                    </div>
                </div>
                <!--/.col-md-4-->

                <%--<div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="feature-wrap">
                        <i class="fa fa-cogs"></i>
                        <h2>${management}</h2>
                    </div>
                </div>
                <!--/.col-md-4-->

                <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="feature-wrap">
                        <i class="fa fa-heart"></i>
                        <h2>${performance}</h2>
                    </div>
                </div>--%>
                <!--/.col-md-4-->
            </div>
            <!--/.services-->
        </div>
        <!--/.row-->
    </div>
    <!--/.container-->
</section>
<!--/#feature-->


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
<!--/#footer-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="jquery.js"></script>
<script src="bootstrap.min.js"></script>
<script src="jquery.prettyPhoto.js"></script>
<script src="jquery.isotope.min.js"></script>
<script src="wow.min.js"></script>
<script src="main.js"></script>
</body>
</html>