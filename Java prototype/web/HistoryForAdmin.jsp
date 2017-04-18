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
        <h1>History</h1></div>
    <ul>
        <li><a class="active" href="HomePage">Home</a></li>
        <li><a id="history_link_to_change" href="#" onclick="ChangeHistoryLink()">History</a></li>
        <li><a id="settings_Link" href="SettingsPage">Settings</a></li>
    </ul>
</div>

<div id="gamediv" align="center">
    <table border="0" cellpadding="1" cellspacing="1" style="width:100%;">
        <tbody>
        <tr>
            <script type="text/javascript">
                clear();
            </script>
            <c:forEach items="${requestScope.PathToAllImages}" var="word" varStatus="status">
            <td style="width:20%; padding-right:10px">
                <a id="Link_divIDNo${status.index}" href="#" onclick="ChangeImageLink('divIDNo${status.index}');">
                    <img src="${word}" id="Image_divIDNo${status.index}" alt="Issues displaying classified image"
                         style="border-width: 2px; border-style: solid; margin: 2px;"/>
                </a>
            </td>
            <c:if test="${status.count % 5 == 0}">
        </tr>
        <tr>
            </c:if>

            </c:forEach>
        </tr>
        </tbody>
    </table>
</div>

<script type='text/javascript'>

    window.onload = function () {
        if (${IsAdmin == "1"}) {
            document.getElementById("history_link_to_change").style.display = 'block';
            document.getElementById("settings_Link").style.display = 'block';
        }
        else if (${usernameDisplay == null}|| ${usernameDisplay == "guest"}) {
            document.getElementById("history_link_to_change").style.display = 'none';
            document.getElementById("settings_Link").style.display = 'none';
        }
        else {
            document.getElementById("history_link_to_change").style.display = 'block';
            document.getElementById("settings_Link").style.display = 'none';
        }

    }
    // Display all images for specific user
    function clear() {
        document.getElementById("gamediv").innerHTML = "";
    }

    // Change Histoy link depending on logged user.
    function ChangeHistoryLink() {
        if (${IsAdmin == "1"}) {
            document.getElementById("history_link_to_change").href = "HistoryForAdminServlet";
        }
        else if (${usernameDisplay == null} || ${usernameDisplay == "guest"}) {
            document.getElementById("history_link_to_change").href = "HistoryForGuest";
        }
        else {
            document.getElementById("history_link_to_change").href = "HistoryForUserServlet";
        }
    }

    // Change Histoy link depending on logged user.
    function ChangeImageLink(idee) {
        if (${IsAdmin == "1"}) {
            document.getElementById("Link_" + idee).href = "PictureDetailForAdminServlet?" + "path=" + document.getElementById("Image_" + idee).src;
        }
        else {
            document.getElementById("Link_" + idee).href = "PictureDetailForUserServlet?" + "path=" + document.getElementById("Image_" + idee).src;
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
