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
        <h2>List Account</h2>

        <c:if test="${currentUser.roleId eq 'AD001'}">

            <form action="ServletCenter">
                <input type="text" name="searchValue" value="${param.searchValue}" />
                <input type="hidden" name="roleSearched" value="${param.roleSearched}" />
                <input type="submit" name="action" value="Search" />
            </form>
        </c:if>

        <ul class="list-tab-roles">
            <li>
                <c:if test="${not empty param.roleSearched}">
                    <a href="ServletCenter">All</a>
                </c:if>
                <c:if test="${empty param.roleSearched}">
                    <b><a href="ServletCenter">All</a></b>
                </c:if>
            </li>

            <c:if test="${currentUser.roleId eq 'AD001'}">
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
            </c:if>
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
                <c:if test="${currentUser.roleId eq 'AD001'}">
                    <th>Delete</th>
                    <th>View Detail</th>
                </c:if>
            </thead>
            <c:forEach items="${users}" var="user" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>
                        <c:if test="${empty user.photo}">
                            (No Photo)
                        </c:if>
                        <c:if test="${not empty user.photo}">
                            <img style="width: 120px;" src="${user.photo}"/>
                        </c:if>
                    </td>
                    <td>${user.userId}</td>
                    <td>${user.fullname}</td>
                    <td>
                        <c:if test="${user.active}">
                            <span class="active-user">
                                Active
                            </span>
                        </c:if>
                        <c:if test="${user.active eq false}">
                            <span class="inactive-user">
                                Inactive
                            </span>
                        </c:if>
                    </td>
                    <td>${roles[user.roleId]}</td>
                    <c:if test="${currentUser.roleId eq 'AD001'}">
                        <td>
                            <c:if test="${user.active}">
                                <c:url var="deleteLink" value="ServletCenter">
                                    <c:param name="action" value="Delete"/>
                                    <c:param name="userId" value="${user.userId}"/>
                                    <c:param name="roleSearched" value="${param.roleSearched}"/>
                                </c:url>
                                <a href="${deleteLink}">Delete</a>
                            </c:if>
                        </td>
                        <td>
                            <c:url var="viewDetailLink" value="ServletCenter">
                                <c:param name="action" value="View User Detail"/>
                                <c:param name="userId" value="${user.userId}"/>
                            </c:url>
                            <a href="${viewDetailLink}">View Detail</a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${empty users}">
        <p class="message">No User found!</p>
    </c:if>

    <p class="message">${message}</p>
</body>
</html>
