<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Let's TEST!</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Работа над ошибками в модуле ${moduleName}!</h1>
<table class="table table-striped">
        <c:forEach items="${listAnswer}" var="answer">
            <tr><td>
                <pre><b>${answer.question}</b></pre></td>
            </tr>
            <tr><td>
                <pre>${answer.fullAnswer}</pre></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="Назад" onclick="window.location='/test';"/>
</body>
</html>