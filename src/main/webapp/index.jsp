<%@include file="WEB-INF/views/components/taglib.jspf" %>

<!DOCTYPE html>

<html>
<head>
    <c:set var="pageTitle" value="Login Page" scope="page"/>
    <%@include file="WEB-INF/views/components/head.jspf" %>
    <fmt:setBundle basename="resources"/>
</head>

<body>
<c:set var="sessionScope.user.role" value="visitor" scope="session"/>
<comp:navBar role='visitor'/>

<table class="table table-striped">
    <thead>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>
            <comp:locale/>
        </td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td></td>
        <td></td>
        <td>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="logIn">
                <h5 class="navbar-brand">
                    <fmt:message key="index_jsp.text"/>
                </h5>
                <input type="text" name="login" class="form-control" required placeholder="<fmt:message key="login"/>"/>
                <input type="password" name="password" class="form-control" required
                       placeholder="<fmt:message key="index_jsp.password"/>"/>
                <button type="submit" class="btn btn-secondary">
                    <fmt:message key="index_jsp.button.login"/>
            </form>
        </td>
        <td></td>
        <td></td>
    </tr>
    </tbody>
</table>
</body>
</html>