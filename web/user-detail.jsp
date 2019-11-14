<%-- 
    Document   : user-detail
    Created on : 14-Nov-2019, 21:45:26
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
        <h1>User Detail</h1>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>User Id</th>
                    <td>${user.userId}</td>
                </tr>
                <tr>
                    <th>Fullname</th>
                    <td>
                        <input type="text" name="fullname" value="${user.fullname}" />
                        <span class="show-input-err">${e.fullnameErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>
                        <input type="text" name="email" value="${user.email}" />
                        <span class="show-input-err">${e.emailErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>
                        <input type="text" name="phone" value="${user.phone}" />
                        <span class="show-input-err">${e.phoneErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Photo</th>
                    <td>
                        <input type="text" name="photo" value="${user.photo}" />
                    </td>
                </tr>
                <tr>
                    <th>Active</th>
                    <td>
                        <c:if test="${user.active eq true}">
                            Active
                        </c:if>
                        <c:if test="${user.active eq false}">
                            Inactive
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>Role</th>
                    <td>
                        <select name="role">
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.key}">
                                    ${role.value}
                                </option>
                            </c:forEach> 
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Update User" name="action" />
                    </td>
                </tr>
            </table>
        </form>


    </body>
</html>
