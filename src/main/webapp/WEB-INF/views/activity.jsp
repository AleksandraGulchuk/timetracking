<%@include file="components/taglib.jspf" %>
<html>
<head>
    <c:set var="pageTitle" value="Activity" scope="page"/>
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

<table class="table table-borderless">
    <tr>
        <td class="w-25 p-3">
            <h5><fmt:message key="activity_jsp.info"/></h5>
            <table class="table table-hover">
                <tr>
                    <td>ID</td>
                    <td>${activity.id}</td>
                </tr>
                <tr>
                    <td><fmt:message key="category"/></td>
                    <td>${activity.category}</td>
                </tr>
                <tr>
                    <td><fmt:message key="title"/></td>
                    <td>${activity.title}</td>
                </tr>
                <tr>
                    <td><fmt:message key="description"/></td>
                    <td>${activity.description}</td>
                </tr>
                <tr>
                    <td><fmt:message key="creationDateTime"/></td>
                    <td><t:formatDateTime dateTime="${activity.creationDateTime}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="lastUpdateDateTime"/></td>
                    <td><t:formatDateTime dateTime="${activity.lastUpdateDateTime}"/></td>
                </tr>
                <tr>
                    <td><fmt:message key="totalTime"/></td>
                    <td>${activity.totalTime}</td>
                </tr>
                <tr>
                    <td><fmt:message key="status"/></td>
                    <td>${activity.status}</td>
                </tr>
            </table>

            <h5><fmt:message key="activity_jsp.append_time_of_activity"/></h5>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="appendTimeActivity">
                <input type="hidden" name="id" value="${activity.id}">
                <div class="input-group mb-3">
                    <input type="time" name="appendTime" class="form-control" required/>
                    <input type="text" class="form-control" name="comment" placeholder="<fmt:message key="comment"/>"
                           aria-label="comment" aria-describedby="button-addon2">
                    <input type="submit" class="btn btn-secondary" value="<fmt:message key="button.append"/>"/>
                </div>
            </form>
        </td>

        <td>
            <h5 align="center"><fmt:message key="activity_story"/></h5>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <td><b><fmt:message key="updateDateTime"/> </b></td>
                    <td><b><fmt:message key="timeSpent"/> </b></td>
                    <td class="w-50 p-3"><b><fmt:message key="comment"/> </b></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach begin="${pagination.beginIndex}" end="${pagination.endIndex}" var="story"
                           items="${activity.stories}">
                    <tr>
                        <td><t:formatDateTime dateTime="${story.updateDateTime}"/></td>
                        <td>${story.timeSpent}</td>
                        <td>${story.comment}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>

    <tr>
        <td>
            <h5><fmt:message key="activity_jsp.delete_activity"/></h5>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="requestDeleteActivity">
                <input type="hidden" name="id" value="${activity.id}">
                <input type="hidden" name="status" value="${activity.status}">
                <input type="hidden" name="userId" value="${sessionScope.user.id}">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" name="comment" placeholder="<fmt:message key="comment"/>"
                           aria-label="comment" aria-describedby="button-addon2">
                    <input type="submit" class="btn btn-outline-danger" value="<fmt:message key="button.delete"/>"/>
                </div>
            </form>
        </td>
        <td class="align-bottom">
            <comp:pagination/>
        </td>
    </tr>
</table>

<%@include file="components/js.jspf" %>

</body>
</html>