<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>New Question</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <form role="form" class="form-horizontal" action="/question/add" method="post">
        <h3>New question</h3>
        <select class="selectpicker form-control form-group" name="module">
            <option value="-1">Default</option>
            <c:forEach items="${modules}" var="module">
                <option value="${module.id}">${module.name}</option>
            </c:forEach>
        </select>
        <textarea class="form-control form-group" name="question" placeholder="question"></textarea>
        <input class="form-control form-group" type="text" name="answer" placeholder="answer">
        <textarea class="form-control form-group" name="fullAnswer" placeholder="fullAnswer"></textarea>
        <input type="submit" class="btn btn-primary" value="Add">
    </form>
</div>
<script>
    $('.selectpicker').selectpicker();
</script>
</body>
</html>