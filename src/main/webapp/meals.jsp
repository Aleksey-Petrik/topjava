<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/style.css">
    <title>Перечень резюме:</title>
</head>
<body>
    <section>
        <table class="table">
            <tr>
                <td>Date</td>
                <td>Description</td>
                <td>Calories</td>
                <td colspan="2">Actions</td>
            </tr>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
                <c:if test="${meal.excess}">
                    <tr class="red">
                </c:if>
                <c:if test="${!meal.excess}">
                    <tr class="green">
                </c:if>
                    <td>${meal.dateTime}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals?uuid=${meal.dateTime}&action=edit">edit</a></td>
                    <td><a href="meals?uuid=${meal.dateTime}&action=delete">delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
