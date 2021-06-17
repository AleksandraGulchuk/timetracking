<%@include file="components/taglib.jspf" %>
<html>
<head>
    <c:set var="pageTitle" value="Denied requests" scope="page"/>
    <%@include file="components/head.jspf" %>
    <fmt:setBundle basename="resources"/>
</head>
<body>
<comp:navBar role="${sessionScope.user.role}"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4"></div>
        <div class="col-md-4"><comp:locale/></div>
    </div>
</div>

<h5 align="center"><fmt:message key="deniedRequests_jsp"/></h5>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <td><b><fmt:message key="requestDateTime"/></b></td>
        <td><b><fmt:message key="requestType"/> </b></td>
        <td><b><fmt:message key="category"/></b></td>
        <td><b><fmt:message key="title"/> </b></td>
        <td><b><fmt:message key="comment"/> </b></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="request" items="${requests}">
        <tr>
            <td><t:formatDateTime dateTime="${request.requestDateTime}"/></td>
            <td>${request.requestType}</td>
            <td>${request.category}</td>
            <td>${request.title}</td>
            <td>${request.comment}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@include file="components/js.jspf" %>
</body>
</html>
