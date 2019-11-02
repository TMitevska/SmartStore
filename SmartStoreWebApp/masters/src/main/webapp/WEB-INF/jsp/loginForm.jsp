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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link rel="stylesheet" type="text/css" href="form.css">
        <script type="text/javascript">
            $('.message a').click(function(){
                $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
            });
            function login(){
                $.ajax({
                    url: "http://localhost:5208/loginUser",
                    type: "POST",
                    data: $("#loginForm").serialize(),
                    success: function (data) {
                        if(data == ""){
                            swal ( "${error}" ,  "${loginerrormessage}" ,  "error" )
                        }
                        else{
                            var result = data.split(":");
                            location.href = "http://localhost:9090/home?userId="+result[0]+"&roleId="+result[1];
                        }
                    }
                });
            }
        </script>
    </head>
    <body class="homepage">
        <header id="header">
            <nav class="navbar navbar-fixed-top" role="banner">
                <div class="container">

                </div>
                <!--/.container-->
            </nav>
            <!--/nav-->
        </header>
        <div class="slider" style="margin-top: 200px;height: 550px">
            <div class="container">
                <div class="login-page" width="1200">
                    <div class="form">
                        <form id="loginForm" class="login-form">
                            <input type="text" name="email" placeholder="${name}"/>
                            <input type="password" name="password" placeholder="${password}"/>

                            <input id="submit" type="button" onclick="login()" value="${login}"/>
                            <p class="message"> <a href="/register">${register}</a></p>
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