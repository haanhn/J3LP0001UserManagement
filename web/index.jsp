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
    </head>
    <body>
        Welcome, ${currentUser.fullname}
        <h1>List Account</h1>

        <form action="ServletCenter">
            <input type="text" name="searchValue" value="${param.searchValue}" />
            <input type="submit" name="action" value="Search" />
        </form>

        <c:if test="${not empty roles}">
            <ul>
                <c:forEach items="${roles}" var="role">
                    <li>${role.value}</li>
                    </c:forEach>
            </ul>
        </c:if>

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
                            Active
                        </c:if>
                        <c:if test="${user.active eq false}">
                            Inactive
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
