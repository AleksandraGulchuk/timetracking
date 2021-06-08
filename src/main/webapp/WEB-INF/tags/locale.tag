<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="resources"/>

<form class="d-flex" action="controller" method="get">
    <select name="locale" class="form-select">
        <c:forEach var="locale" items="${applicationScope.locales}">
            <option value="${locale}"
                    <c:if test="${sessionScope.currentLocale == locale}">selected</c:if>
            >
                    ${locale.toUpperCase()}
            </option>
        </c:forEach>
    </select>
    <input type="hidden" name="command" value="setLocale">
    <button class="btn btn-outline-secondary" type="submit">
        <fmt:message key="button.select"/>
    </button>
</form>
