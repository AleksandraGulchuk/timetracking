<%@include file="components/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
    <c:set var="pageTitle" value="Management" scope="page"/>
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

<div class="accordion" id="accordionExample">
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne"
                    aria-expanded="true" aria-controls="collapseOne">
                <b><fmt:message key="management_jsp.users"/></b>
            </button>
        </h2>
        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne"
             data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <td><b>ID</b></td>
                        <td><b><fmt:message key="userName"/></b></td>
                        <td><b><fmt:message key="login"/></b></td>
                        <td><b><fmt:message key="role"/> </b></td>
                        <td><b><fmt:message key="activities"/> </b></td>
                        <td><b><fmt:message key="action"/> </b></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${user.role}</td>
                            <td><a href="controller?command=showUserActivities&userId=${user.id}" class="link-dark">
                                <fmt:message key="activities"/>
                            </a>
                            </td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="deleteUser">
                                    <input type="submit" class="btn btn-outline-danger"
                                           value="<fmt:message key="button.delete"/>">
                                    <input type="hidden" name="userId" value="${user.id}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="addUser">
                            <td><b><fmt:message key="management_jsp.create_user"/> </b></td>
                            <td><input type="text" name="name" placeholder="<fmt:message key="userName"/>"
                                       class="form-control" required/></td>
                            <td><input type="text" name="login" placeholder="<fmt:message key="login"/>"
                                       class="form-control" required/></td>
                            <td><select name="roleId" class="form-select" required>
                                <option selected disabled value=""><fmt:message key="option.select"/> </option>
                                <c:forEach var="role" items="${roles}">
                                    <option type="hidden" name="roleId" value="${role.id}">${role.role}</option>
                                </c:forEach>
                            </select></td>
                            <td><input type="password" name="password"
                                       placeholder="<fmt:message key="index_jsp.password"/>" class="form-control"
                                       required/></td>
                            <td><input type="submit" class="btn btn-secondary my-2 my-sm-0"
                                       value="<fmt:message key="button.create"/>"/>
                        </form>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingTwo">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                <b><fmt:message key="management_jsp.categories"/></b>
            </button>
        </h2>
        <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
             data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <td><b>Id</b></td>
                        <td><b><fmt:message key="category"/> </b></td>
                        <td><b><fmt:message key="action"/> </b></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.id}</td>
                            <td>${category.category}</td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="deleteCategory">
                                    <input type="submit" class="btn btn-outline-danger"
                                           value="<fmt:message key="button.delete"/>">
                                    <input type="hidden" name="categoryId" value="${category.id}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="addCategory">
                            <td><b><fmt:message key="management_jsp.create_category"/> </b></td>
                            <td><input type="text" name="category" placeholder="<fmt:message key="category"/>"
                                       class="form-control" required/></td>
                            <td><input type="submit" class="btn btn-secondary my-2 my-sm-0"
                                       value="<fmt:message key="button.create"/>"/></td>
                        </form>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="components/js.jspf" %>
</body>
</html>