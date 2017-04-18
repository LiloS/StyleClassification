<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
    <title>Yellow Pages</title>
    <link rel="stylesheet" type="text/css" href="resources/templatemo_476_conquer/css/style.css"/>
</head>
<body>
<div align="center">
        <h1 >Yellow Pages</h1>
    <form  method="post" action="Task3JDBCServlet" name="search form" align="center">
        <p style="text-align: center;">Part of the last name&nbsp;<input name="lastName" type="text" value="${lastName}"/></p>
        <p style="text-align: center;"><input name="SearchByLastName" type="submit" value="Search"/></p>
        <p style="text-align: center;">Part of the phone&nbsp;<input name="phone" type="text" value="${phone}"/></p>
        <p style="text-align: center;"><input name="SearchByPhone" type="submit" value="Search"/></p>
        <p>${requestScope.message}</p>
    </form>
</div>

</body>
</html>
