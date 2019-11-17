<%-- 
    Document   : insert-promo
    Created on : 17-Nov-2019, 02:26:05
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
        <h2>Insert Promotion</h2>
        <c:set var="e" value="${error}"/>
        <form action="ServletCenter" method="POST">
            <table border="0">
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
                    <td colspan="2">
                        <input type="submit" name="action" value="Insert Promotion" />
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
