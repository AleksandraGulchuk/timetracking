<%@include file="components/taglib.jspf" %>

<html>
<head>
    <c:set var="pageTitle" value="Reports" scope="page"/>
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

<table class="table">
    <thead>
    <tr>
        <td><b><fmt:message key="category"/> </b></td>
        <td><b><fmt:message key="status"/> </b></td>
        <td><b><fmt:message key="userName"/> </b></td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <tr class="align-middle">
        <td>
            <form>
                <input type="hidden" name="command" value="filterBy">
                <input type="hidden" name="condition" value="category">
                <input type="hidden" name="listName" value="activities">
                <fieldset>
                    <c:forEach var="category" items="${categories}">
                        <input type="checkbox" name="value" value="${category.category}">${category.category}<br>
                    </c:forEach>
                    <br>
                    <button type="submit" class="btn btn-outline-secondary"><fmt:message key="button.filter"/></button>
                </fieldset>
            </form>
        </td>

        <td>
            <form>
                <input type="hidden" name="command" value="filterBy">
                <input type="hidden" name="condition" value="status">
                <input type="hidden" name="listName" value="activities">

                <fieldset>
                    <c:forEach var="activityStatus" items="${activityStatuses}">
                        <input type="checkbox" name="value" value="${activityStatus.status}">${activityStatus.status}
                        <br>
                    </c:forEach>
                    <br>
                    <button type="submit" class="btn btn-outline-secondary"><fmt:message key="button.filter"/></button>

                </fieldset>
            </form>
        </td>

        <td>
            <form>
                <input type="hidden" name="command" value="filterBy">
                <input type="hidden" name="condition" value="userName">
                <input type="hidden" name="listName" value="activities">
                <fieldset>
                    <c:forEach var="user" items="${users}">
                        <input type="checkbox" name="value" value="${user.name}">${user.name}<br>
                    </c:forEach>
                    <br>
                    <button type="submit" class="btn btn-outline-secondary"><fmt:message key="button.filter"/></button>
                </fieldset>
            </form>
        </td>
        <td>
            <form>
                <input type="hidden" name="command" value="goToReports">
                <button type="submit" class="btn btn-secondary"><fmt:message key="button.reset_filters"/></button>
            </form>
        </td>
        <td>
            <div class="card text-white bg-secondary mb-3" style="max-width: 25rem;">
                <div class="card-header" align="center"><fmt:message key="reports_jsp_totalTime_text"/></div>
                <div class="card-body">
                    <h5 class="card-title" align="center">
                        <t:sumActivityTime activities="${activities}"/>
                    </h5>
                    <p class="card-text" align="center">
                        <fmt:message key="reports_jsp_totalTime_format"/>
                    </p>
                </div>
            </div>
        </td>
    </tr>
    </tbody>
</table>


<table class="table table-striped table-hover">
    <thead>
    <tr>
        <td><a href="controller?command=sortBy&listName=activities&condition=userName"
               class="link-dark"><b><fmt:message key="userName"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=id"
               class="link-dark"><b>ID</b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=category"
               class="link-dark"><b><fmt:message key="category"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=title"
               class="link-dark"><b><fmt:message key="title"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=description"
               class="link-dark"><b><fmt:message key="description"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=creationDateTime"
               class="link-dark"><b><fmt:message key="creationDateTime"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=lastUpdateDateTime"
               class="link-dark"><b><fmt:message key="lastUpdateDateTime"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=totalTime"
               class="link-dark"><b><fmt:message key="totalTime"/> </b></a></td>
        <td><a href="controller?command=sortBy&listName=activities&condition=status" class="link-dark"><b><fmt:message
                key="status"/></b></a>
        </td>
        <td class="w-25 p-3"><b><fmt:message key="activity_story"/></b>
        </td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${activities}">
        <tr>
            <td>${activity.userName}</td>
            <td>${activity.id}</td>
            <td>${activity.category}</td>
            <td>${activity.title}</td>
            <td>${activity.description}</td>
            <td><t:formatDateTime dateTime="${activity.creationDateTime}"/></td>
            <td><t:formatDateTime dateTime="${activity.lastUpdateDateTime}"/></td>
            <td>${activity.totalTime}</td>
            <td>${activity.status}</td>
            <td>
                <p>
                    <button class="btn btn-outline-secondary" type="button"
                            data-bs-toggle="collapse" data-bs-target="#collapseExample"
                            aria-expanded="false" aria-controls="collapseExample">
                        <fmt:message key="updateDateTime"/> <fmt:message key="timeSpent"/> <fmt:message key="comment"/>
                    </button>
                </p>
                <div class="collapse" id="collapseExample">
                    <div class="card card-body">
                        <table class="table table-bordered">
                            <c:forEach var="story" items="${activity.stories}">
                                <tr>
                                    <td><t:formatDateTime dateTime="${story.updateDateTime}"/></td>
                                    <td>${story.timeSpent}</td>
                                    <td>${story.comment}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@include file="components/js.jspf" %>

</body>
</html>
