<%-- 
    Document   : profile
    Created on : 17-Nov-2019, 00:17:03
    Author     : HaAnh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>My Profile</h2>
        
        <c:if test="${empty currentUser.photo}">
            (No Photo)
        </c:if>
        <c:if test="${not empty currentUser.photo}">
            <img style="width: 150px;" src="${currentUser.photo}"/>
        </c:if>
        
        <c:set var="e" value="${error}"/>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>User Id</th>
                    <td>
                        ${currentUser.userId}
                        <input type="hidden" name="userId" value="${currentUser.userId}" />
                    </td>
                </tr>
                <tr>
                    <th>Fullname</th>
                    <td>
                        <input type="text" name="fullname" value="${currentUser.fullname}" />
                        <span class="show-input-err">${e.fullnameErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>
                        <input type="text" name="email" value="${currentUser.email}" />
                        <span class="show-input-err">${e.emailErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Phone</th>
                    <td>
                        <input type="text" name="phone" value="${currentUser.phone}" />
                        <span class="show-input-err">${e.phoneErr}</span>
                    </td>
                </tr>
                
                <tr>
                    <th>Role</th>
                    <td>
                        ${roles[currentUser.roleId]}
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
            <input type="submit" value="Change My Password" name="action" />
        </form>
        
        <form action="ServletCenter" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="userId" value="${user.userId}" />
            <input type="file" name="photo" />
            <input type="submit" value="ChangeUserPhoto" name="action" />
        </form>
    </body>
</html>
