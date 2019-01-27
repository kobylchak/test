<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Let's TEST!</title>
</head>
<body>
<div align="center">
    <h1>Your login is: ${login}, your roles are:</h1>
    <c:if test="${admin ne null}">
        <input type="submit" value="Secret admin page" onclick="window.location='/admin'; "/>
    </c:if>
    <c:if test="${user ne null}">
        <input type="submit" value="Let's TEST" onclick="window.location='/test'; "/>
    </c:if>
    <c:forEach var="s" items="${roles}">
        <h3><c:out value="${s}"/></h3>
    </c:forEach>
    <c:url value="/update" var="updateUrl"/>
    <form action="${updateUrl}" method="POST">
        E-mail:<br/><input type="text" name="email" value="${email}"/><br/>
        Phone:<br/><input type="text" name="phone" value="${phone}"/><br/>
        <input type="submit" value="Update"/>
    </form>
    <c:url value="/logout" var="logoutUrl"/>
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>
</div>
</body>
</html>
