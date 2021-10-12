<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="CSS/style.css">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <title></title>
    </head>
</head>
<body>
    <section>
        <a href="."><h3>Home</h3></a>
        <hr>
        <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
            <input type="hidden" name="uuid" value="${meal.id}">
            <div style="margin-left: 30px">
                <bl>
                    <dt><h1>Edit meal</h1></dt>
                </bl>
                <dl>
                    <dt><b>DateTime:</b></dt>
                    <dd><input type="datetime-local" name="dateTime" size=10 value="${meal.dateTime}"></dd>
                </dl>
                <dl>
                    <dt><b>Description:</b></dt>
                    <dd><input type="text" name="descriptions" size=50 value="${meal.description}"></dd>
                </dl>
                <dl>
                    <dt><b>Calories:</b></dt>
                    <dd><input type="text" name="calories" size=25 value="${meal.calories}"></dd>
                </dl>
                <button type="submit">Сохранить</button>
                <button type="reset" onclick="window.history.back()">Отменить</button>
            </div>
        </form>
        <hr>
    </section>
</body>
</html>
