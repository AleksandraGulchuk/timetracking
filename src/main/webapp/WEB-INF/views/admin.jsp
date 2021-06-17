<%@include file="components/taglib.jspf" %>

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

<div class="card">
    <div class="card-body">
        <div class="p-3 mb-2 bg-secondary text-white">
            <h5 class="card-title"><fmt:message key="admin_jsp.user_requests"/></h5>
            <p class="card-text">
                <fmt:message key="admin_jsp.user_requests_description_row1"/><br>
                <fmt:message key="admin_jsp.user_requests_description_row2"/>
            </p>
            <a href="${app}/controller?command=goToUsersRequests" class="btn btn-secondary">
                <fmt:message key="admin_jsp.button_go_to_user_requests"/> </a>
        </div>
    </div>
</div>

<div class="card text-center">
    <div class="card-body">
        <div class="p-3 mb-2 bg-secondary text-white">
            <h5 class="card-title"><fmt:message key="admin_jsp.management"/></h5>
            <p class="card-text">
                <fmt:message key="admin_jsp.management_description_row1"/>
            </p>
            <a href="${app}/controller?command=goToManagement" class="btn btn-secondary">
                <fmt:message key="admin_jsp.button_go_to_management"/> </a>
        </div>
    </div>
</div>

<div class="card text-end">
    <div class="card-body">
        <div class="p-3 mb-2 bg-secondary text-white">
            <h5 class="card-title"><fmt:message key="admin_jsp.reports"/></h5>
            <p class="card-text">
                <fmt:message key="admin_jsp.reports_description_row1"/>
            </p>
            <a href="${app}/controller?command=goToReports" class="btn btn-secondary">
                <fmt:message key="admin_jsp.button_go_to_reports"/>
            </a>
        </div>
    </div>
</div>
<%@include file="components/js.jspf" %>
</body>
</html>