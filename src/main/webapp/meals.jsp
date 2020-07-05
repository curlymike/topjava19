<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>

    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css"
          integrity="sha384-nn4HPE8lTHyVtfCBi5yW9d20FjT8BJwUXyWZT9InLYax14RDjBj46LmSztkmNP9w"
          crossorigin="anonymous">

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

    <table class="pure-table pure-table-horizontal">
        <tr>
            <th class="description">Описание</th>
            <th class="year">Калории</th>
            <th class="author">Время</th>
        </tr>
        <c:forEach items="${mealList}" var="element">
        <jsp:useBean id="element" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${element.excess ? 'exceeded' : 'normal'}">
            <td class="id">${element.description}</td>
            <td class="title">${element.calories}</td>
            <td class="time"><%=TimeUtil.toString(element.getDateTime())%></td>
        </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>