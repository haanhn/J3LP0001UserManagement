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
        <link href="css/myStyle.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2>User Detail</h2>
        
        <c:if test="${empty user.photo}">
            (No Photo)
        </c:if>
        <c:if test="${not empty user.photo}">
            <img style="width: 150px;" src="${user.photo}"/>
        </c:if>
        
        <c:set var="e" value="${error}"/>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>User Id</th>
                    <td>
                        ${user.userId}
                        <input type="hidden" name="userId" value="${user.userId}" />
                    </td>
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
                                <c:if test="${role.key == user.roleId}">
                                    <option value="${role.key}" selected="selected" >
                                        ${role.value}
                                    </option>
                                </c:if>
                                <c:if test="${role.key !=  user.roleId}">

                                    <option value="${role.key}" >
                                        ${role.value}
                                    </option>
                                </c:if>
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
        <p class="message">${message}</p>
        
        <form action="ServletCenter" method="POST">
            <input type="hidden" value="${user.userId}" name="userId" />
            <input type="submit" value="PageChangeUserPassword" name="action" />
        </form>
        
        <form action="ServletCenter" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="userId" value="${user.userId}" />
            <input type="file" name="photo" />
            <input type="submit" value="ChangeUserPhoto" name="action" />
        </form>
        
    </body>
</html>
