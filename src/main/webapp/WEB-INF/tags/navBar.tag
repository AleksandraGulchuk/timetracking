<%@ attribute name="role" type="java.lang.String" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">TimeTracking</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="controller?command=startPageCommand"><fmt:message key="navbar.home"/></a>
                </li>

                <c:if test="${role eq 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${app}/controller?command=goToUsersRequests"><fmt:message key="admin_jsp.user_requests"/> </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${app}/controller?command=goToManagement"><fmt:message key="admin_jsp.management"/> </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${app}/controller?command=goToReports"><fmt:message key="admin_jsp.reports"/> </a>
                    </li>
                </c:if>

                <c:if test="${role eq 'client'}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=showDeniedRequests"><fmt:message key="deniedRequests_jsp"/> </a>
                    </li>
                </c:if>

                <c:if test="${role ne 'visitor'}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=logOut"><fmt:message key="navbar.logout"/> </a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="about.jsp"><fmt:message key="navbar.about_project"/> </a>
                </li>
            </ul>

            <c:if test="${role ne 'visitor'}">
            <span class="navbar-text">
        <fmt:message key="navbar.hello"/> ${sessionScope.user.name}!
      </span>
            </c:if>
        </div>
    </div>
</nav>