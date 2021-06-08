<%@include file="components/taglib.jspf" %>

<html>
<head>
    <c:set var="pageTitle" value="Requests" scope="page"/>
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

<h5 align="center"><fmt:message key="admin_jsp.user_requests"/></h5>

<table class="table table-striped table-hover">
    <thead>
    <tr>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=id" class="link-dark"><b>ID</b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=requestDateTime"
               class="link-dark"><b><fmt:message key="requestDateTime"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=userName"
               class="link-dark"><b><fmt:message key="userName"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=activityId"
               class="link-dark"><b><fmt:message key="activityID"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=category"
               class="link-dark"><b><fmt:message key="category"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=title" class="link-dark"><b><fmt:message
                key="title"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=description"
               class="link-dark"><b><fmt:message key="description"/></b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=totalTime"
               class="link-dark"><b><fmt:message key="totalTime"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=status" class="link-dark"><b><fmt:message
                key="status"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=comment"
               class="link-dark"><b><fmt:message key="comment"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=requestType"
               class="link-dark"><b><fmt:message key="requestType"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=userRequests&condition=appendTime"
               class="link-dark"><b><fmt:message key="activity_jsp.append_time"/> </b></a></td>
        <td><b>choose</b></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="userRequest" items="${userRequests}">
        <tr>
            <td>${userRequest.id}</td>
            <td><t:formatDateTime dateTime="${userRequest.requestDateTime}"/></td>
            <td>${userRequest.userName}</td>
            <td>${userRequest.activityId}</td>
            <td>${userRequest.category}</td>
            <td>${userRequest.title}</td>
            <td>${userRequest.description}</td>
            <td>${userRequest.totalTime}</td>
            <td>${userRequest.status}</td>
            <td>${userRequest.comment}</td>
            <td>${userRequest.requestType}</td>
            <td>${userRequest.appendTime}</td>
            <td>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="processUserRequest">
                    <input type="hidden" name="requestType" value="${userRequest.requestType}">
                    <input type="hidden" name="requestId" value="${userRequest.id}">


                    <button type="submit" class="btn btn-outline-success" name="choose" value="confirm">
                        <fmt:message key="button.confirm"/>
                    </button>

<%--                    <input type="submit" class="btn btn-outline-success" name="choose"--%>
<%--                           value="confirm"><fmt:message key="button.confirm"/>--%>

                    <div class="input-group mb-3">
                        <input type="text" name="comment" class="form-control"
                               placeholder="<fmt:message key="comment"/>" aria-label="Comment"
                               aria-describedby="button-addon2">

                        <button type="submit" class="btn btn-outline-dark" name="choose" value="deny">
                            <fmt:message key="button.deny"/>
                        </button>

<%--                        <input type="submit" class="btn btn-outline-dark" name="choose"--%>
<%--                               value="deny"><fmt:message key="button.deny"/>--%>


                    </div>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@include file="components/js.jspf" %>
</body>
</html>
