<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="WEB-INF/views/components/taglib.jspf" %>


<!DOCTYPE html>
<html>
<head>
    <c:set var="pageTitle" value="About project" scope="page"/>
    <%@include file="WEB-INF/views/components/head.jspf" %>
    <fmt:setBundle basename="resources"/>
</head>
<body>

<comp:navBar role="${sessionScope.user.role}"/>

<div class="card">
    <h5 class="card-header" align="center">Облік часу</h5>
    <div class="card-body">
        <h6 class="card-title">Існують ролі: адміністратор і користувач системи.</h6>
        <h6 class="card-title">Адміністратор</h6>
        <p class="card-text">
            Адміністратор керує:<br>
            - користувачами;<br>
            - категоріями активностей;<br>
            - активностями;<br>
            - підтверджує (закріпляє) за користувачами певну активність.<br>
            Адміністратор також може переглядати список всіх наявних активностей, здійснювати сортування:<br>
            - за назвою;<br>
            - за категорією;<br>
            - за кількістю користувачів.<br>
            Здійснювати фільтрацію за категорією активностей.<br>
            Переглядати звіт по всіх користувачах, кількості активностей і часу, відміченого користувачем.
        </p>
        <h6 class="card-title">Користувач</h6>
        <p class="card-text">
            Користувач повинен мати кабінет.<br>
            У користувача може бути одна або кілька активностей.<br>
            Користувач відмічає кількість витраченого часу на кожну активність.<br>
            Користувач може відправити запит на додавання/видалення активності.
        </p>
        <a href="index.jsp" class="btn btn-outline-secondary"><fmt:message key="button.go_to_start_page"/> </a>
    </div>
</div>
</body>
</html>