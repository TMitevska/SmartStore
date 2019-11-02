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
            <li class="active"><a href="http://localhost:9090/home?userId=${userid}&roleId=${roleId}"> <i class="fa fa-home" aria-hidden="true"></i> ${home}</a></li>
            <li><a href="http://localhost:9090/remoteControl?userId=${userid}&roleId=${roleId}"><i class="fa fa-laptop" aria-hidden="true"></i> ${remoteControl} </a></li>
            <%if(request.getParameter("roleId").equals(Translator.acountantid) || request.getParameter("roleId").equals(Translator.ownerid)){%>
            <li><a href='http://localhost:9090/createDocument?userId=${userid}&roleId=${roleId}'><i class="fa fa-plus-square-o" aria-hidden="true"></i> ${createinvoice}</a></li>
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
  <!--/header-->
  <div class="slider" style="margin-top: 200px">
    <div class="container">
      <div id="about-slider">
        <div id="carousel-slider" class="carousel slide" data-ride="carousel">
          <!-- Indicators -->
          <ol class="carousel-indicators visible-xs">
            <li data-target="#carousel-slider" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-slider" data-slide-to="1"></li>
            <li data-target="#carousel-slider" data-slide-to="2"></li>
          </ol>

          <div class="carousel-inner">
            <div class="item active">
              <img src="slider_one.jpg" class="img-responsive" alt="">
            </div>
            <div class="item">
              <img src="slider_one.jpg" class="img-responsive" alt="">
            </div>
            <div class="item">
              <img src="slider_one.jpg" class="img-responsive" alt="">
            </div>
          </div>
          <a class="left carousel-control hidden-xs" href="#carousel-slider" data-slide="prev">
						<i class="fa fa-angle-left"></i>
					</a>
          <a class=" right carousel-control hidden-xs" href="#carousel-slider" data-slide="next">
						<i class="fa fa-angle-right"></i>
					</a>
        </div>
        <!--/#carousel-slider-->
      </div>
      <!--/#about-slider-->
    </div>
  </div>
  <section id="feature">
    <div class="container">
      <div class="row">
        <div class="features">
          <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
            <div class="feature-wrap">
              <i class="fa fa-laptop"></i>
              <h2>${remoteControl}</h2>
            </div>
          </div>
          <!--/.col-md-4-->

          <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
            <div class="feature-wrap">
              <i class="fa fa-comments"></i>
              <h2>${predict}</h2>
            </div>
          </div>
          <!--/.col-md-4-->

          <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
            <div class="feature-wrap">
              <i class="fa fa-cloud-download"></i>
              <h2>${createinvoice}</h2>
            </div>
          </div>
          <!--/.col-md-4-->

          <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
            <div class="feature-wrap">
              <i class="fa fa-leaf"></i>
              <h2>${efficient}</h2>
            </div>
          </div>
          <!--/.col-md-4-->

          <div class="col-md-4 col-sm-6 wow fadeInDown" data-wow-duration="1000ms" data-wow-delay="600ms">
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
          </div>
          <!--/.col-md-4-->
        </div>
        <!--/.services-->
      </div>
      <!--/.row-->
    </div>
    <!--/.container-->
  </section>
  <!--/#feature-->

  <section id="recent-works">
    <div class="container">
      <div class="center wow fadeInDown">
        <h2>${photos}</h2>
      </div>

      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item1.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item1.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item2.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item2.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item3.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item3.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item4.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item4.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item5.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item5.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item6.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item6.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item7.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item7.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xs-12 col-sm-4 col-md-3">
          <div class="recent-work-wrap">
            <img class="img-responsive" src="/portfolio/recent/item8.png" alt="">
            <div class="overlay">
              <div class="recent-work-inner">
                <a class="preview" href="/portfolio/full/item8.png" rel="prettyPhoto"><i class="fa fa-eye"></i> View</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--/.row-->
    </div>
    <!--/.container-->
  </section>
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