<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/style.css">
    <title>Перечень потребления еды:</title>
</head>
<body>
    <section>
        <a href="."><h3>Home</h3></a>
        <hr>
        <h1>Meals</h1>
        <td><a href="meals?uuid=''&action=add">Add Meal</a></td>
        <table class="table">
            <tr>
                <td>Date</td>
                <td>Description</td>
                <td>Calories</td>
                <td colspan="2">Actions</td>
            </tr>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr class="${meal.excess ? "red" : "green"}">
                    <td><%=TimeUtil.getDataTimeForHtml(meal.getDateTime())%></td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals?uuid=${meal.id}&action=edit">edit</a></td>
                    <td><a href="meals?uuid=${meal.id}&action=delete">delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
