<%-- 
    Document   : index
    Created on : Sep 6, 2018, 10:52:19 AM
    Author     : david
--%>

<%@page import="com.el.humen.modules.utils.CookieUtils"%>
<%@page import="com.el.humen.modules.iam.entities.User"%>
<%
    User u = CookieUtils.getCurrentUser(request, response);
    if (u != null) {
        response.sendRedirect("/index.rsc");
        return;
    }
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="width: 100%; padding: 0px; margin: 0px;">

    <head>
        <!-- Google Analytics -->
        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-41266411-5', 'auto');
            ga('send', 'pageview');
        </script>
        <!-- End Google Analytics -->


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="title" content ="Free Online Staff Holiday Planner">
        <meta name="keywords" content="Online Team Holiday Planner,Team Holiday Planner,Free Online Team Holiday Planner,Humen,Free Online Staff Holiday Planner by Human">
        <meta name="description" content="Human is a Simple Free Online Team Holiday Planner That's Smarter Than Spreadsheets and Faster Than Papers.">
        <meta name="viewport" content="width=640, user-scalable=no">
        <title>Humen - Free Online Staff Holiday Planner</title>

        <meta property="og:url"                content="https://humen.io" />
        <meta property="og:type"               content="website" />
        <meta property="og:title"              content="Free Online Staff Holiday Planner" />
        <meta property="og:description"        content="Human is a simple an free Online Team Holiday Planner That is Smarter Than Spreadsheets and Faster Than Papers." />
        <meta property="og:image"              content="https://humen.io/images/screenshots/ss_calendar_full.png" />
        <link rel="canonical" href="https://humen.io" />
        <link rel="shortcut icon" type="image/png" href="/favicon.png"/>
        <link rel="shortcut icon" type="image/png" href="https://humen.io/favicon.png"/>

        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800" rel="stylesheet">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" crossorigin="anonymous">

        <script src="https://howu.io/js/howu-widget.js" key="KLMVDyvwzYZORq9OoJnpxX"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="/files/style.css">
        
    </head>
    <body style="width: 100%; padding-top: 60px; margin: 0px;">
 
        <div style="display: flex; align-items: center; justify-content: center; position: fixed; left: 0px; top: 0px; right: 0px; height: 60px; background-color: #fff; box-shadow: 0px 0px 10px rgba(0,0,0, 0.3); z-index: 10000;">
            <div class="container">
                <div class="row align-items-center justify-content-center">
                    <div class="col-6 align-items-center" style="line-height: 60px;">
                      <a style="text-decoration: none;" alt="Free Online Staff Holiday Planner" title="Free Online Staff Holiday Planner" href="https://humen.io">  <i class="material-icons" style="font-size: 36px; color: #009155; vertical-align: middle; ">account_circle</i>
                        <span style="font-size: 24px; font-weight: 100; color: #004126; vertical-align: middle;">Humen</span></a>
                    </div>
                    <div class="col-6" style="text-align: right; vertical-align: middle;">
                        <a href="/login.wpi" style=" vertical-align: middle;" >Login</a>
                        <a href="/register.wpi" class="btn btn-success" style="font-size: 14px; font-weight: 600; margin-left: 10px; vertical-align: middle;">Sign Up Free</a>
                    </div>
                </div>
            </div>
        </div>



        <div class="section jumbotron-color">
            <div class="section-title white"></div>
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-md-6" style="text-align: center">
                        <img title="Free Online Staff Holiday Planner" alt="Free Online Staff Holiday Planner" src="./images/screenshots/ss_calendar_full.png" class="feature-image" style="width: 90%; height: auto"/>
                    </div>
                    <div class="col-12 col-md-6 desc-padding">
                        <h1 class="slogen-title white">
                            Staff Holiday Planning Made Easy By Human
                        </h1>
                        <h2 class="slogen-content white">
                            There are lots of administrative stuff to get stuck with. Staff Holiday Planning is not one of them anymore!
                        </h2>
                    </div>
                </div>
            </div>
        </div>

        <div class="section" style="background-color: #f5f8f4;">
            <div class="section-title black">Why Humen?</div>
            <div class="container">
                <div class="row align-items-center justify-content-center">
                    <div class="section-desc black">
                        Why Humen is better than spreadsheets or holiday forms?
                    </div>
                </div>
                <div class="row align-items-start justify-content-center" style="padding-bottom: 80px;">
                    <div class="col-12 col-md-4">
                        <div style="text-align: center;">
                            <i class="material-icons box-icon">mood</i>
                        </div>
                        <div class="box-title">Easier to use</div>
                        <div class="box-desc">You don't need to search for the actual spreadsheet and juggling with cell coloring and format data. Just hit the plus button, fill the form and you are ready to go!</div>
                    </div>

                    <div class="col-12 col-md-4">
                        <div  style="text-align: center;">
                            <i class="material-icons box-icon">bar_chart</i>
                        </div>
                        <div class="box-title">Easier to report</div>
                        <div class="box-desc">You don't need to track manually the used up and remaining vacation count of each team member. Humen export will show you every month the used up and remaining vacation count for each employee.</div>
                    </div>

                    <div class="col-12 col-md-4">
                        <div  style="text-align: center;">
                            <i class="material-icons box-icon">visibility</i>
                        </div>
                        <div class="box-title">Easier to overview</div>
                        <div class="box-desc">Aligning your holiday to other team members' day offs can be a nightmare if there isn't a place where you can see the big picture. In the team calendar, you can find any information you need.</div>
                    </div>
                </div>
            </div>
        </div>


        <div class="section section-color">
            <div class="section-title white">Free Features</div>
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-md-6" style="text-align: center">
                        <img title="Free Online Staff Holiday Planner" alt="Free Online Staff Holiday Planner" src="./images/screenshots/ss_addvacation_dialog.png" class="feature-image" style="width: 90%; height: auto"/>
                    </div>
                    <div class="col-12 col-md-6 desc-padding">
                        <h3 class="desc-title white">
                            Easy day off registration
                        </h3>
                        <div class="desc-content white">
                            You don't need to looking for spreadsheets and holiday forms in shared folders. All you need to do is to hit the plus button, fill the form and you are ready to go.
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="section" style="background-color: #f5f8f4;">
            <div class="section-title black"></div>
            <div class="container">
                <div class="row align-items-center justify-content-center" style="padding-bottom: 80px;">
                    <div class="col-12 col-md-6">
                        <h3 class="desc-title black">
                            Personal vacation statistics
                        </h3>
                        <div class="desc-content black">
                            Do you know how many vacations do you have per year? How many do you used up? How many are remaining? Yes, you do! Just check it on the dashboard!
                        </div>
                    </div>
                </div>
                <div class="row align-items-center">
                    <div class="col-12" style="text-align: center">
                        <img title="Free Online Staff Holiday Planner" alt="Free Online Staff Holiday Planner" src="./images/screenshots/ss_vacation_statistics.png" class="feature-image" style="width: 90%; height: auto"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="section section-color">
            <div class="section-title white"></div>
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-md-6" style="padding-bottom: 80px;">
                        <h3 class="desc-title white">
                            Upcoming events
                        </h3>
                        <div class="desc-content white">
                            Do you always know when an extra workday or a national holiday is coming? The dashboard will tell you.
                        </div>
                    </div>
                    <div class="col-12 col-md-6" style="text-align: center">
                        <img title="Free Online Staff Holiday Planner" alt="Free Online Staff Holiday Planner" src="./images/screenshots/ss_upcomingevents.png" class="feature-image" style="width: 90%; height: auto"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="section" style="background-color: #f5f8f4;">
            <div class="section-title"></div>
            <div class="container">
                <div class="row align-items-center justify-content-center" style="padding-bottom: 80px;">
                    <div class="col-12 col-md-6">
                        <h3 class="desc-title black">
                            Team calendar
                        </h3>
                        <div class="desc-content black">
                            There are situations when you need to align your holiday with other team members' leaves, or you just want to know who is in and who is out. That's what the team calendar is designed for!
                        </div>
                    </div>
                </div>
                <div class="row align-items-center">
                    <div class="col-12" style="text-align: center">
                        <img title="Free Online Staff Holiday Planner" alt="Free Online Staff Holiday Planner" src="./images/screenshots/ss_calendar.png" class="feature-image" style="width: 90%; height: auto"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="section section-color">
            <div class="section-title white"></div>
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-12 col-md-6" style="padding-bottom: 80px;">
                        <h3 class="desc-title white">
                            Half day vacations
                        </h3>
                        <div class="desc-content white">
                            Managing half day vacations can be a nightmare! Not anymore! Humen can track half day vacations too!
                        </div>
                    </div>
                    <div class="col-12 col-md-6" style="text-align: center">
                        <img title="Free Online Staff Holiday Planner" alt="Free Online Staff Holiday Planner" src="./images/screenshots/ss_add_halfday_vacation.png" class="feature-image" style="width: 90%; height: auto"/>
                    </div>
                    
                </div>
            </div>
        </div>





        <div class="section" style="background-color: #f5f8f4;">
            <div class="section-title black">Pricing</div>
            <div class="container">
                <div class="row align-items-center justify-content-center">
                    <div class="section-desc black">
                        Our pricing is very simple! We have only two packages!
                    </div>
                </div>
                <div class="row align-items-center justify-content-center" style="padding-bottom: 80px;">
                    <div class="col-12 col-md-6">
                        <div style="text-align: center;">
                            <i class="material-icons box-icon">money_off</i>
                        </div>
                        <div class="box-title">Free package</div>
                        <div class="box-desc">
                            All the free features can be used for free. No limitations, no credit card is needed.
                        </div>
                    </div>

                    <div class="col-12 col-md-6">
                        <div style="text-align: center;">
                            <i class="material-icons box-icon">star</i>
                        </div>
                        <div class="box-title">Pro package</div>
                        <div class="box-desc">In the beta period, you can use the Pro features for free. Prices are coming soon, after the beta period.</div>
                    </div>
                </div>
            </div>
        </div>






        <div class="section jumbotron-color">
            <div class="section-title white">Contact Us</div>
            <div class="container">
                <div class="row align-items-center justify-content-center">
                    <div class="section-desc white">
                        Contact us on any of the following channels if you have any question or anything that you want to tell us about our Staff Holiday Planner!
                    </div>
                </div>
                <div class="row align-items-center">
                    <div class="col-12 col-md-6">
                        <div style="text-align: center; font-weight: 400; font-size: 22px; color: #fff; padding-top: 80px">
                             info(at)humen.io
                        </div>
                    </div>
                    <div class="col-12 col-md-6">
                        <div style="text-align: center; font-weight: 400; font-size: 22px; color: #fff; padding-top: 80px">
                            <a href="https://www.facebook.com/humenio" target="_blank" class="white-link">Facebook</a> | <a href="https://twitter.com/humenapp" target="_blank" class="white-link">Twitter</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        
        
        <div id="wim-dialog" style="display: none; position: fixed; left: 0px; top: 0px; right: 0px; bottom: 0px; background-color: rgba(0,0,0, 0.9); align-items: center; justify-content: center; z-index: 200000;">
            <div style="box-shadow: 1px 1px 10px rgba(0,0,0, 0.7); width: 600px; padding: 32px; background-color: #fff;">
                <div style="font-size: 32px; font-weight: 900; color: #222; text-align: center; margin-bottom: 32px;">What are you missing?</div>
                <div style="font-size: 18px; font-weight: 400; color: #888; text-align: center; margin-bottom: 32px;">
                    Help us improve our service by telling us what you miss
                </div>
                <form method="post" action="/submitFeedback.jsp">
                    <b>Name (optional)</b><br/>
                    <input type="text" name="name" style="width: 100%;" /><br/>
                    <br/>
                    
                    <b>Email (optional)</b><br/>
                    <input type="email" name="email" style="width: 100%;" /><br/>
                    <br/>
                    
                    <b>Message</b><br/>
                    <textarea name="message" style="width: 100%; height: 200px; margin-bottom: 32px;"></textarea>
                    <div style="text-align: center;">
                        <button onclick="document.getElementById('wim-dialog').style.display = 'none'; event.preventDefault();"
                                style="background-color: transparent; border: 0px solid transparent; text-decoration: underline; cursor: pointer;">
                            No thanks
                        </button>
                        <button type="submit" style="cursor: pointer;">Send</button>
                    </div>
                </form>
            </div>
        </div>
        
        <script>
            //document.addEventListener("mouseleave", function () {
            //    document.getElementById('wim-dialog').style.display = 'flex';
            //});
        </script>

    </body>
</html>
