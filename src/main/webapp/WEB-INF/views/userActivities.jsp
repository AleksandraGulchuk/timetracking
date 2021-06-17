<%@include file="components/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
    <c:set var="pageTitle" value="User activities" scope="page"/>
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

<h5 align="center"><fmt:message key="user_activities_jsp"/></h5>
<table>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <td><b>ID</b></td>
            <td><b><fmt:message key="category"/></b></td>
            <td><b><fmt:message key="title"/></b></td>
            <td><b><fmt:message key="description"/></b></td>
            <td><b><fmt:message key="creationDateTime"/></b></td>
            <td><b><fmt:message key="lastUpdateDateTime"/></b></td>
            <td><b><fmt:message key="totalTime"/></b></td>
            <td><b><fmt:message key="status"/></b></td>
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
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="deleteActivity">
                        <input type="hidden" name="activityId" value="${activity.id}">
                        <input type="hidden" name="userId" value="${userId}">
                        <input type="submit" class="btn btn-outline-danger" value="<fmt:message key="button.delete"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="addActivity">
                <input type="hidden" name="userId" value="${userId}">
                <td><b><fmt:message key="client_jsp.new_activity"/> </b></td>
                <td><select name="categoryId" class="form-select" required>
                    <option selected disabled value=""><fmt:message key="option.select"/></option>
                    <c:forEach var="category" items="${categories}">
                        <option type="hidden" name="categoryId" value=${category.id}>${category.category}</option>
                    </c:forEach>
                </select>
                </td>
                <td><input type="text" name="title" placeholder="<fmt:message key="title"/>" class="form-control"
                           required/></td>
                <td><input type="text" name="description" placeholder="<fmt:message key="description"/>"
                           class="form-control" required/></td>
                <td>-</td>
                <td>-</td>
                <td><input type="time" name="totalTime" value="00:00" class="form-control"/></td>
                <td>-</td>
                <td>
                    <input type="submit" class="btn btn-secondary my-2 my-sm-0"
                           value="<fmt:message key="button.create"/>" class="form-control" required/>
                </td>
            </form>
        </tr>

        </tbody>
    </table>
</table>

<%@include file="components/js.jspf" %>

</body>
</html>