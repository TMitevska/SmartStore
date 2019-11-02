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
        <link rel="stylesheet" type="text/css" href="form.css">
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script type="text/javascript">
            $('.message a').click(function(){
                $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
            });
            function createAccount(){
                $.ajax({
                    url: "http://localhost:5208/addUserInDatabase",
                    type: "POST",
                    data: $("#registerForm").serialize(),
                    success: function (data) {
                        if(data == false){
                            swal ( "${error}" ,  "${emailerrormessage}" ,  "error" )
                        }
                        else{
                            location.href = "http://localhost:9090/";
                        }
                    }
                });

            }
        </script>
    </head>
    <body class="homepage" style="text-align: center">
        <header id="header">
            <nav class="navbar navbar-fixed-top" role="banner">
                <div class="container">

                </div>
                <!--/.container-->
            </nav>
            <!--/nav-->
        </header>
        <div class="slider" style="margin-top: 150px;text-align: center;alignment: center;height: 600px">
            <div class="container">
                <div class="login-page">
                    <div class="form">
                        <div style="height:500px;margin:10px">
                            <form id="registerForm" class="register-form" >
                                <input type="text" name="name" placeholder="${name}"/>
                                <input type="text" name="address" placeholder="${address}"/>
                                <input type="text" name="phone" placeholder="${phone}"/>
                                <input type="email" name="email" placeholder="${email}"/>
                                <input type="password" name="password" placeholder="${password}"/>

                                <select name="role" style="width: 50%;margin-bottom: 20px;background: #f2f2f2;">
                                    <option value="DsCXZdx7VRsW0VAzC0PT">${owner}</option>
                                    <option value="s1sze9GAHFXf4gMZ1X3K">${employee}</option>
                                    <option value="56mF7iFQnU28oqwtFYdQ">${acountant}</option>
                                </select>


                                <input id="submit" type="button" onclick="createAccount()" value="${register}"/>
                                <p class="message"> <a href="/">${login}</a></p>
                            </form>
                        </div>
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
                    <div class="col-sm-6">
                    </div>
                </div>
            </div>
        </footer>
    </body>
</html>