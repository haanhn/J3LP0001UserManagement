<%-- 
    Document   : promo-detail
    Created on : 17-Nov-2019, 17:01:33
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
        <h2>Promotion</h2>
        <c:set var="e" value="${error}"/>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>Id</th>
                    <td>
                        ${param.id}
                        <input type="hidden" name="id" value="${param.id}"/>
                    </td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td>
                        <input type="text" name="name" value="${param.name}"/>
                        <span class="show-input-err">${e.nameErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td>
                        <textarea name="description">${param.description}</textarea>
                        <span class="show-input-err">${e.descriptionErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>From Date</th>
                    <td>
                        <input type="text" name="fromDate" value="${param.fromDate}" />
                        <span class="show-input-err">${e.fromDateErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>To Date</th>
                    <td>
                        <input type="text" name="toDate" value="${param.toDate}" />
                        <span class="show-input-err">${e.toDateErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Active</th>
                    <td>
                        <input type="hidden" name="active" value="${param.active}"/>
                        <c:if test="${param.active}">
                            <span class="active">
                                Active
                            </span>
                        </c:if>
                        <c:if test="${param.active eq false}">
                            <span class="inactive">
                                Inactive
                            </span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Update Promotion" />
                    </td>
                </tr>
            </table>
        </form>
        <p class="message">${message}</p>
    </body>
</html>
