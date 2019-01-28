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
<h1>Let's start!</h1>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul id="moduleList" class="nav navbar-nav">
                <li class="dropdown">
                    <p>Choose any module:</p>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">All questions <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <c:forEach items="${modules}" var="module">
                            <li><a href="/module_user/${module.id}">${module.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
<c:if test="${questions ne null}">
    <form action="/check_answer" method="post">
        <input type="hidden" name="modulok" value="${modulok}">
        <table class="table table-striped">
            <thead>
            <tr>
                <td><b>Question</b></td>
                <td><b>Choose your answer</b></td>
            </tr>
            </thead>
            <c:forEach items="${questions}" var="question">
                <tr>
                    <td>
                        <pre>${question.question}</pre>
                    </td>
                    <td>
                        <input type="text" name="answer[]" requeired placeholder="Відповідати '+' або '-'" >
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="Check!">
    </form>
</c:if>
</div>
</body>
</html>