<%-- 
    Document   : index
    Created on : 11-Nov-2019, 08:59:17
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
        <link href="css/myStyle.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        Welcome, ${currentUser.fullname}
        <h1>List Account</h1>

        <form action="ServletCenter">
            <input type="text" name="searchValue" value="${param.searchValue}" />
            <input type="hidden" name="roleSearched" value="${param.roleSearched}" />
            <input type="submit" name="action" value="Search" />
        </form>

        <ul class="list-tab-roles">
            <li>
                <c:if test="${not empty param.roleSearched}">
                    <a href="ServletCenter">All</a>
                </c:if>
                <c:if test="${empty param.roleSearched}">
                    <b><a href="ServletCenter">All</a></b>
                </c:if>
            </li>
            <c:forEach items="${roles}" var="role">
                <li>
                    <c:url var="linkGetUsersByRole" value="ServletCenter">
                        <c:param name="action" value="GetUsersByRole"/>
                        <c:param name="roleSearched" value="${role.key}"/>
                    </c:url>
                    <c:choose>
                        <c:when test="${not empty param.roleSearched && param.roleSearched eq role.key}">
                            <b><a href="${linkGetUsersByRole}">${role.value}</a></b>
                            </c:when>
                            <c:otherwise>
                            <a href="${linkGetUsersByRole}">${role.value}</a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>

        <c:if test="${not empty users}">
            <table border="0">
                <thead>
                <th>No.</th>
                <th>Image</th>
                <th>User Id</th>
                <th>Fullname</th>
                <th>Active</th>
                <th>Role</th>
                <th>Delete</th>
            </thead>
            <c:forEach items="${users}" var="user" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>
                        <c:if test="${empty user.photo}">
                            No Photo
                        </c:if>
                        <c:if test="${not empty user.photo}">
                            ${user.photo}
                        </c:if>
                    </td>
                    <td>${user.userId}</td>
                    <td>${user.fullname}</td>
                    <td>
                        <c:if test="${user.active}">
                            <p class="active-user">
                                Active
                            </p>
                        </c:if>
                        <c:if test="${user.active eq false}">
                            <p class="inactive-user">
                                Inactive
                            </p>
                        </c:if>
                    </td>
                    <td>${roles[user.roleId]}</td>
                    <td>
                        <c:if test="${user.active}">
                            <c:url var="deleteLink" value="ServletCenter">
                                <c:param name="action" value="Delete"/>
                                <c:param name="userId" value="${user.userId}"/>
                                <c:param name="searchValue" value="${param.searchValue}"/>
                            </c:url>
                            <a href="${deleteLink}">Delete</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty users}">
        No User found!
    </c:if>

    ${message}
</body>
</html>
