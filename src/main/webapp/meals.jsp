<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Meal list</title>

    <!-- <link rel="stylesheet" type="text/css" href="/static/pure-min.css" /> -->
    <!-- https://xdsoft.net/jqplugins/datetimepicker/ -->
    <link rel="stylesheet" type="text/css" href="/static/jquery.datetimepicker.min.css" />
    <script src="/static/jquery.js"></script>
    <script src="/static/jquery.datetimepicker.full.min.js"></script>

    <script>
        (function ($) {
            $(document).ready(function() {
                $('input[name=dateFrom]').datetimepicker({
                    timepicker:false,
                    format:'Y-m-d'
                });

                $('input[name=dateTo]').datetimepicker({
                    timepicker:false,
                    format:'Y-m-d'
                });

                $('input[name=timeFrom]').datetimepicker({
                    datepicker:false,
                    format:'H:i'
                });

                $('input[name=timeTo]').datetimepicker({
                    datepicker:false,
                    format:'H:i'
                });
            });
        })(jQuery);
    </script>

    <style>
        .time-filter-wrapper {
            margin: 16px 0;
        }
    </style>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        .auth-user-id {
            margin: 20px 0 10px 0;
        }

    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr />

    <div class="time-filter-wrapper">
        <form id="date-time-filter" method="get">
            <table>
                <tr>
                    <td>Date</td>
                    <td><input name="dateFrom" type="text" value="${dateFrom}" /></td>
                    <td><input name="dateTo" type="text" value="${dateTo}" /></td>
                    <td></td>
                </tr>
                <tr>
                    <td>Time</td>
                    <td><input name="timeFrom" type="text" value="${timeFrom}" /></td>
                    <td><input name="timeTo" type="text" value="${timeTo}" /></td>
                    <td><input type="submit" value="Apply" /></td>
                </tr>
            </table>
        </form>
    </div>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&amp;id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&amp;id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<div class="auth-user-id">
    <div>authUserId: ${authUserId}</div>
</div>
<div class="user-id-form">
    <form id="form-new-user-id" method="post" action="meals<c:if test="${not empty queryParams}">?${queryParams}</c:if>">
        <label for="new-user-id">Set user Id to:</label>
        <select id="new-user-id" name="new-user-id"><c:forEach items="${userIds}" var="userId">
            <option value="${userId}" <c:if test="${authUserId eq userId}">selected="selected"</c:if> >${userId}</option>
        </c:forEach></select>
        <input type="hidden" name="op" value="change-auth-user-id" />
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>