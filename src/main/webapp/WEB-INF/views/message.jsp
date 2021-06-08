<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <c:set var="pageTitle" value="Message" scope="page"/>
    <%@include file="components/head.jspf" %>
    <fmt:setBundle basename="resources"/>
</head>
<body>

<div class="card text-center">
    <div class="card-header">
        <fmt:message key="message_jsp.result"/>
    </div>
    <div class="card-body">
        <h3 class="card-title">${message}</h3>
        <p class="card-text"></p>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="showPageCommand">

        <%--            <input type="hidden" name="command" value="startPageCommand">--%>
            <input type="submit" class="btn btn-outline-secondary" value="<fmt:message key="button.back"/>"/>
<%--             to start page">--%>
        </form>
    </div>
</div>
</body>
</html>
