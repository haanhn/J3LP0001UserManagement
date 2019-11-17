<%-- 
    Document   : list-user-not-in-promo
    Created on : 17-Nov-2019, 20:29:26
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
        <h2>Add User to Promotion</h2>
        <table border="0">
            <tr>
                <td>Promotion Id:</td>
                <td>${param.promoId}</td>
            </tr>
            <tr>
                <td>Name:</td>
                <td>${param.promoName}</td>
            </tr>
        </table>

        <c:if test="${not empty users}">
            <table class="table-records" border="0">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Photo</th>
                        <th>User Id</th>
                        <th>Fullname</th>
                        <th>Add To Promotion</th>
                    </tr>
                </thead>
                <tbody>
                <form action="ServletCenter" method="POST">
                    <c:forEach var="user" items="${users}" varStatus="counter">
                        <input type="hidden" name="promoId" value="${param.promoId}" />
                        <input type="hidden" name="promoName" value="${param.promoName}" />
                        <input type="hidden" name="userId" value="${user.userId}" />
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
                            <td>
                                ${user.userId}
                            </td>
                            <td>
                                ${user.fullname}
                            </td>
                            <td>
                                <input type="submit" value="Add User To Promotion" name="action" />
                            </td>
                        </tr>
                    </c:forEach>
                </form>

            </tbody>
        </table>
    </c:if>
    <c:if test="${empty users}">
        <div class="message">All Users added to this Promotion</div>
    </c:if>

    <div class="message">${message}</div>
</body>
</html>
