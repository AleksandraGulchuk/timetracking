<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <c:forEach var="i" begin="1" end="${pagination.amountOfPages}">
            <li class="page-item">
                <a class="page-link"
                   href="controller?command=goToActivity&activityId=${activity.id}&page=${i}">
                    <c:out value="${i}"/>
                </a></li>
        </c:forEach>
    </ul>
</nav>