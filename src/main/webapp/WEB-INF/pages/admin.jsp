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
<div align="center">
    <h1>Secret page for admins only!</h1>
    <c:url value="/logout" var="logoutUrl"/>
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>
</div>
<div align="left">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul id="moduleList" class="nav navbar-nav">
                    <li>
                        <button type="button" id="add_question" class="btn btn-default navbar-btn">Add Question</button>
                    </li>
                    <li>
                        <button type="button" id="add_module" class="btn btn-default navbar-btn">Add Module</button>
                    </li>
                    <li>
                        <button type="button" id="delete_question" class="btn btn-default navbar-btn">Delete Question
                        </button>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">Modules <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/admin">All questions</a></li>
                            <c:forEach items="${modules}" var="module">
                                <li><a href="/module/${module.id}">${module.name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
                <form class="navbar-form navbar-left" role="search" action="/search" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="pattern" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
        </div>
    </nav>
    <table align="left" class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>Question</b></td>
            <td><b>Answer</b></td>
            <td><b>Full Answer</b></td>
            <td><b>Module</b></td>
        </tr>
        </thead>
        <c:forEach items="${questions}" var="question">
            <tr>
                <td><input type="checkbox" name="toDelete[]" value="${question.id}" id="checkbox_${question.id}"/></td>
                <td>
                    <pre>${question.question}</pre>
                </td>
                <td>${question.answer}</td>
                <td>
                    <pre>${question.fullAnswer}</pre>
                </td>
                <c:choose>
                    <c:when test="${question.module ne null}">
                        <td>${question.module.name}</td>
                    </c:when>
                    <c:otherwise>
                        <td>Default</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:if test="${allPages ne null}">
                <c:forEach var="i" begin="1" end="${allPages}">
                    <li><a href="/admin/?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                </c:forEach>
            </c:if>
            <c:if test="${byGroupPages ne null}">
                <c:forEach var="i" begin="1" end="${byGroupPages}">
                    <li><a href="/module/${moduleId}?page=<c:out value="${i - 1}"/>"><c:out value="${i}"/></a></li>
                </c:forEach>
            </c:if>
        </ul>
    </nav>
</div>
<script>
    $('.dropdown-toggle').dropdown();
    $('#add_question').click(function () {
        window.location.href = '/question_add_page';
    });
    $('#add_module').click(function () {
        window.location.href = '/module_add_page';
    });
    $('#delete_question').click(function () {
        var data = {'toDelete[]': []};
        $(":checked").each(function () {
            data['toDelete[]'].push($(this).val());
        });
        $.post("/question/delete", data, function (data, status) {
            window.location.reload();
        });
    });
</script>
</body>
</html>