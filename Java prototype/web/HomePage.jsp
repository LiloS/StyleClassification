<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <title>Classification</title>
    <link rel="stylesheet" type="text/css" href="resources/templatemo_476_conquer/css/style.css"/>
    <style>
        .myButton {
            -moz-box-shadow: inset 0px 1px 3px 0px #91b8b3;
            -webkit-box-shadow: inset 0px 1px 3px 0px #91b8b3;
            box-shadow: inset 0px 1px 3px 0px #91b8b3;
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #b4b8b7), color-stop(1, #6c7c7c));
            background: -moz-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: -webkit-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: -o-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: -ms-linear-gradient(top, #b4b8b7 5%, #6c7c7c 100%);
            background: linear-gradient(to bottom, #b4b8b7 5%, #6c7c7c 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#b4b8b7', endColorstr='#6c7c7c', GradientType=0);
            background-color: #b4b8b7;
            -moz-border-radius: 15px;
            -webkit-border-radius: 15px;
            border-radius: 15px;
            display: inline-block;
            cursor: pointer;
            color: #ffffff;
            font-family: Arial;
            font-size: 15px;
            font-weight: bold;
            padding: 11px 76px;
            text-decoration: none;
            text-shadow: 1px 2px 5px #2b665e;
        }

        .myButton:hover {
            background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #6c7c7c), color-stop(1, #b4b8b7));
            background: -moz-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: -webkit-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: -o-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: -ms-linear-gradient(top, #6c7c7c 5%, #b4b8b7 100%);
            background: linear-gradient(to bottom, #6c7c7c 5%, #b4b8b7 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#6c7c7c', endColorstr='#b4b8b7', GradientType=0);
            background-color: #6c7c7c;
        }

        .myButton:active {
            position: relative;
            top: 1px;
        }

        progress {
            background-color: #d1ddd1;
            border: 0;
            height: 18px;
            border-radius: 9px;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            border: 1px solid #e7e7e7;
            background-color: #f3f3f3;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: #666;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a:hover:not(.active) {
            background-color: #ddd;
        }

        li a.active {
            color: white;
            background-color: #585e59;
        }
    </style>
</head>
<body>
<div align="center">
    <div style="background-image: url(resources/images/bg1.jpg); height: 200px; text-align: center;" ;>
        <p align="right">Login as </p>
        <p align="right"><c:out value="${usernameDisplay}" default="guest"/></p>
        <h1>Classification</h1></div>
    <ul>
        <li><a class="active" href="HomePage">Home</a></li>
        <li><a id="history_link_to_change" href="#" onclick="ChangeHistoryLink()">History</a></li>
        <li><a id="settings_Link" href="SettingsPage">Settings</a></li>
    </ul>
    <form method="post" action="HomePageLogin" name="login form" align="center">
        <table border="0" cellpadding="1" cellspacing="1" style="width:100%;">
            <tbody>
            <tr>
                <td style="text-align: center;" align="top">
                    <h2>Login</h2>

                    <p style="text-align: left;">Username&nbsp;<input name="username" type="text" value="${username}"/>
                    </p>

                    <p style="text-align: left;">Password&nbsp;<input name="password" type="password"
                                                                      value="${password}"/></p>

                    <p style="text-align: left;"><input name="Login" type="submit" value="Login"
                                                        onclick="form.action='HomePageLogin';"/>&nbsp;
                        <input name="Register" type="submit" value="Register"
                               onclick="form.action='HomePageRegister';"/>
                        <input name="Exit" type="submit" value="Exit" id="buttonExit"
                               onclick="form.action='HomePageExit';"/>
                        <input name="RegisterNewAdmin" type="submit" id="button" value="Register new Admin"
                               onclick="form.action='HomePageRegisterNewAdmin';"/></p>
                    <p>${requestScope.message}</p>

                </td>
                <td style="width: 80%;">
                    <h2 style="text-align: center;">Image selection</h2>
                </td>
            </tr>
            <tr>
                <td>
                </td>
                <td style="width: 80%;">
                    <p><img id="blah" src="#" alt="Please select image for classification"
                            style="width: 50%; height: 50%; border-width: 2px; border-style: solid; margin: 2px;"/></p>
                <td>
            </tr>
            </tbody>
        </table>
    </form>
    <form method="post" action="UploadServlet" enctype="multipart/form-data">
        <table border="0" cellpadding="1" cellspacing="1" style="width:100%;">
            <tbody>
            <tr>
                <td style="width: 40%;">
                </td>
                <td style="width: 60%;">
                    <p>Select image for classification:</p>
                    <input type="file" name="file" size="60"
                           onchange="readURL(this);"/><br/>
                    <br/> <input type="submit" value="Classify" onclick="ProcessingPainting()" class="myButton"/>
                    <progress id="progressbar" value="0" max="5000"></progress>
                    <p>${requestScope.message2}</p>
                <td>
            </tr>
            </tbody>
        </table>
    </form>
</div>

<script type='text/javascript'>

    // Progress while processing the painting.
    function ProcessingPainting() {
        var progressbar = $('#progressbar'),
                max = progressbar.attr('max'),
                time = (5000 / max) * 5,
                value = progressbar.val();

        var loading = function () {
            value += 1;
            addValue = progressbar.val(value);

            $('.progress-value').html(value + '%');

            if (value == max) {
                clearInterval(animate);
            }
        };

        var animate = setInterval(function () {
            loading();
        }, time);
    }
    ;

    // Display Register New Admin button only for admin user.
    window.onload = function () {
        if (${IsAdmin == "1"}) {
            document.getElementById("button").style.display = 'block';
            document.getElementById("buttonExit").style.display = 'block';
            document.getElementById("history_link_to_change").style.display = 'block';
            document.getElementById("settings_Link").style.display = 'block';
        }
        else if (${usernameDisplay == null}|| ${usernameDisplay == "guest"}) {
            document.getElementById("button").style.display = 'none';
            document.getElementById("buttonExit").style.display = 'none';
            document.getElementById("history_link_to_change").style.display = 'none';
            document.getElementById("settings_Link").style.display = 'none';
        }
        else {
            document.getElementById("buttonExit").style.display = 'block';
            document.getElementById("button").style.display = 'none';
            document.getElementById("history_link_to_change").style.display = 'block';
            document.getElementById("settings_Link").style.display = 'none';
        }

    }

    // Change Histoy link depending on logged user.
    function ChangeHistoryLink() {
        if (${IsAdmin == "1"}) {
            document.getElementById("history_link_to_change").href = "HistoryForAdminServlet";
        }
        else if (${usernameDisplay == null}) {
            document.getElementById("history_link_to_change").href = "HistoryForGuest";
        }
        else {
            document.getElementById("history_link_to_change").href = "HistoryForUserServlet";
        }
    }

    // Display image immediately after selection.
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                        .attr('src', e.target.result);
                //.width(150)
                // .height(200);
            };

            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

</body>
</html>
