<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <title>Meals</title>

    <base href="${pageContext.request.contextPath}/" />

    <link rel="stylesheet" type="text/css" href="static/pure-min.css" />
    <!-- https://xdsoft.net/jqplugins/datetimepicker/ -->
    <link rel="stylesheet" type="text/css" href="static/jquery.datetimepicker.min.css" />

    <script src="static/jquery.js"></script>
    <script src="static/jquery.datetimepicker.full.min.js"></script>

    <script>
        (function ($) {
            $(document).ready(function() {
                $('.datetimepicker').datetimepicker({
                    format:'Y-m-d H:i',
                    lang:'ru'
                });
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
<h2>${meal.description}</h2>
<div>

    <form method="POST" action="${pageContext.request.contextPath}/meals/edit" accept-charset="UTF-8">
    <%-- https://stackoverflow.com/questions/270444/javax-servlet-servletexception-bean-name-not-found-within-scope --%>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <table>
        <tr class="description">
            <td><label for="description">Описание</label></td>
            <td>
                <input id="description" name="description" type="text" size="60" value="${meal.description}"/>
            </td>
        </tr>
        <tr class="calories">
            <td><label for="calories">Калории</label></td>
            <td>
                <input id="calories" name="calories" type="text" size="10" value="${meal.calories}" />
            </td>
        </tr>
        <!-- datetime datetime-local -->
        <tr class="datetime">
            <td><label for="datetime">Время</label></td>
            <td>
                <input id="datetime" name="datetime" class="datetimepicker" value="<%=TimeUtil.toString(meal.getDateTime())%>" />
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <input name="mealid" type="hidden" value="${mealId}" />
                <input type="submit" value="Submit" />
            </td>
        </tr>

    </table>
    </form>

</div>
</body>
</html>