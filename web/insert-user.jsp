<%-- 
    Document   : user-detail
    Created on : 13-Nov-2019, 09:31:24
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
        <h1>Hello World!</h1>
        <c:set var="e" value="${error}"/>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>User Id</th>
                    <td>
                        <input type="text" name="userId" value="${param.userId}" />
                        <div class="show-input-err">${e.userIdErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Password</th>
                    <td>
                        <input type="password" name="password" value="${param.password}"/>
                        <div class="show-input-err">${e.passwordErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Confirm</th>
                    <td>
                        <input type="password" name="confirm" value="${param.confirm}" />
                        <div class="show-input-err">${e.confirmErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Fullname</th>
                    <td>
                        <input type="text" name="fullname" value="${param.fullname}" />
                        <div class="show-input-err">${e.fullnameErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>
                        <input type="text" name="email" value="${param.email}" />
                        <div class="show-input-err">${e.emailErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>
                        <input type="text" name="phone" value="${param.phone}" />
                        <div class="show-input-err">${e.phoneErr}</div>
                    </td>
                </tr>
                <tr>
                    <th>Photo</th>
                    <td>
                        <input type="text" name="photo" value="${param.photo}" />
                    </td>
                </tr>
                <tr>
                    <th>Active</th>
                    <td>
                        <input type="checkbox" name="active" value="" checked="checked" />
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
                        <input type="submit" name="action" value="Insert" />
                    </td>
                </tr>
            </table>
        </form>
        ${message}
    </body>
</html>
