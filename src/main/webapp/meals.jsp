<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <title>Meals</title>

    <base href="${pageContext.request.contextPath}/" />

    <link rel="stylesheet" type="text/css" href="static/pure-min.css" />
    <link rel="stylesheet" type="text/css" href="static/jquery.datetimepicker.min.css" />

    <script src="static/jquery.js"></script>
    <script src="static/jquery.datetimepicker.full.min.js"></script>

    <script>
        (function ($) {
            $(document).ready(function() {
                $('.datetimepicker').datetimepicker();
            });
        })(jQuery);
    </script>

    <style>
        body {
            margin:20px;
        }
        .exceeded * {
            color: #e50000;
        }
        .normal * {
            color: darkgreen;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<div>

    <p>
        <a href="meals/add">Add</a>
    </p>

    <table class="pure-table pure-table-horizontal">
        <tr>
            <th class="id">Id</th>
            <th class="description">Описание</th>
            <th class="calories">Калории</th>
            <th class="time">Время</th>
            <th class=""></th>
        </tr>
        <c:forEach items="${mealList}" var="element">
        <jsp:useBean id="element" scope="page" type="ru.javawebinar.topjava.model.WithIdWithExceed"/>
        <tr class="${element.exceed ? 'exceeded' : 'normal'}">
            <td class="id">${element.id}</td>
            <td class="description">${element.description}</td>
            <td class="calories">${element.calories}</td>
            <td class="time"><%=TimeUtil.toString(element.getDateTime())%></td>
            <td class="edit"><a href="meals/edit?id=${element.id}">edit</a></td>
        </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>