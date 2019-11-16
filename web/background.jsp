<%-- 
    Document   : background
    Created on : 16-Nov-2019, 23:30:39
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <ul class="list-top-navigation">
            <li>
                <c:url var="profileLink" value="ServletCenter">
                    <c:param name="action" value="ViewProfile"/>
                </c:url>
                <a href="${profileLink}">View My Profile</a>
            </li>
            <li>
                <c:url var="logOutLink" value="ServletCenter">
                    <c:param name="action" value="Log Out"/>
                </c:url>
                <a href="${logOutLink}">Log Out</a>
            </li>

        </ul>

        <h1>User Management</h1>
        <div class="box-welcome">
            Welcome, ${currentUser.fullname}
        </div>

        <ul class="list-function">
            <li>
                <c:url var="viewUsersLink" value="ServletCenter">
                </c:url>
                <a href="${viewUsersLink}">View Users</a>
            </li>
            <!--For Admin-->
            <c:if test="${currentUser.roleId eq 'AD001'}">
                <li>
                    <c:url var="pageInsertLink" value="ServletCenter">
                        <c:param name="action" value="PageInsert"/>
                    </c:url>
                    <a href="${pageInsertLink}">Insert new User</a>
                </li>
            </c:if>
        </ul>

        <jsp:include page="${includedPage}"/>

    </body>
</html>
