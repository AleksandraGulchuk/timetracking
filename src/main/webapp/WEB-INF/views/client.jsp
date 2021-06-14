<%@include file="components/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
    <c:set var="pageTitle" value="Cabinet" scope="page"/>
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

<h5><fmt:message key="client_jsp.new_activity"/></h5>
<form action="controller" method="post">
    <input type="hidden" name="command" value="requestCreateActivity">
    <input type="hidden" name="userId" value="${sessionScope.user.id}">
    <table>
        <tr>
            <td><fmt:message key="client_jsp.select_category"/> </td>
            <td><select name="category" class="form-select" required>
                <option selected disabled value=""><fmt:message key="option.select"/> </option>
                <c:forEach var="category" items="${categories}">
                    <option type="hidden">${category.category}</option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td><fmt:message key="title"/> </td>
            <td>
                <input type="text" name="title" class="form-control" required />
            </td>
        </tr>
        <tr>
            <td><fmt:message key="description"/> </td>
            <td><input type="text" name="description" class="form-control" required/></td>
        </tr>
        <tr>
            <td><fmt:message key="totalTime"/> </td>
            <td><input type="time" name="totalTime" value="00:00" class="form-control" required/></td>
        </tr>
        <tr>
            <td><fmt:message key="comment"/> </td>
            <td><input type="text" name="comment" class="form-control" required/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" class="btn btn-secondary my-2 my-sm-0" value="<fmt:message key="button.create"/>"/></td>
        </tr>
    </table>
</form>

<hr>

<h5 align="center"><fmt:message key="client_jsp.activities"/> </h5>
<table class="table table-striped table-hover">
    <thead>
    <tr>
        <td><a href="controller?command=sortBy&listName=activities&condition=id" class="link-dark"><b>ID</b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=category" class="link-dark"><b><fmt:message key="category"/></b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=title" class="link-dark"><b><fmt:message key="title"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=description" class="link-dark"><b><fmt:message key="description"/></b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=creationDateTime" class="link-dark"><b><fmt:message key="creationDateTime"/></b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=lastUpdateDateTime" class="link-dark"><b><fmt:message key="lastUpdateDateTime"/></b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=totalTime" class="link-dark"><b><fmt:message key="totalTime"/></b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=status" class="link-dark"><b><fmt:message key="status"/></b></a></td>
        <td><b></b></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}">
        <tr>
            <td>${activity.id}</td>
            <td>${activity.category}</td>
            <td>${activity.title}</td>
            <td>${activity.description}</td>
            <td><t:formatDateTime dateTime="${activity.creationDateTime}"/></td>
            <td><t:formatDateTime dateTime="${activity.lastUpdateDateTime}"/></td>
            <td>${activity.totalTime}</td>
            <td>${activity.status}</td>
            <td>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="goToActivity">
                    <input type="submit" class="btn btn-secondary my-2 my-sm-0" value="<fmt:message key="button.go"/>">
                    <input type="hidden" name="activityId" value="${activity.id}">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@include file="components/js.jspf"%>
</body>
</html>