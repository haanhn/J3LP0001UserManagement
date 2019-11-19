<%-- 
    Document   : list-promotions
    Created on : 17-Nov-2019, 15:59:42
    Author     : HaAnh
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>List Promotion</h2>

        <c:if test="${not empty promos}">
            <table border="0" class="table-records">
                <thead>
                <th>No.</th>
                <th>Name</th>
                <th>From Date</th>
                <th>To Date</th>
                <th>Active</th>
                <th>Delete</th>
                <th>View Detail</th>
                <th>Add User</th>
                <th>Get Users</th>
            </thead>
            <form action="ServletCenter" method="POST">
                <c:forEach items="${promos}" var="promo" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>
                           <fmt:formatDate value="${promo.fromDate}"  pattern="dd/MM/yyyy" var="from" />
                           <fmt:formatDate value="${promo.toDate}"  pattern="dd/MM/yyyy" var="to" />
                            ${promo.name}
                            <input type="hidden" name="id" value="${promo.id}" />
                            <input type="hidden" name="name" value="${promo.name}" />
                            <input type="hidden" name="description" value="${promo.description}" />
                            <input type="hidden" name="fromDate" value="${from}" />
                            <input type="hidden" name="toDate" value="${to}" />
                            <input type="hidden" name="active" value="${promo.active}" />
                        </td>
                        <td>${promo.fromDate}</td>
                        <td>${promo.toDate}</td>
                        <td>
                            <c:if test="${promo.active}">
                                <span class="active">
                                    Active
                                </span>
                            </c:if>
                            <c:if test="${promo.active eq false}">
                                <span class="inactive">
                                    Inactive
                                </span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${promo.active}">
                                <c:url var="deleteLink" value="ServletCenter">
                                    <c:param name="action" value="DeletePromo"/>
                                    <c:param name="id" value="${promo.id}"/>
                                </c:url>
                                <a href="${deleteLink}">Delete</a>
                            </c:if>
                        </td>
                        <td>
                            <input type="submit" value="View Promotion Detail" name="action" />
                        </td>
                        <td>
                            <c:if test="${promo.active}">    
                            <c:url var="getUserNotInPromoLink" value="ServletCenter">
                                <c:param name="action" value="GetUsersNotInPromo"/>
                                <c:param name="promoId" value="${promo.id}"/>
                                <c:param name="promoName" value="${promo.name}"/>
                            </c:url>
                            <a href="${getUserNotInPromoLink}">Add User</a>
                            </c:if>
                        </td>
                        <td>
                            <c:url var="getUserInPromoLink" value="ServletCenter">
                                <c:param name="action" value="GetUsersInPromo"/>
                                <c:param name="promoId" value="${promo.id}"/>
                                <c:param name="promoName" value="${promo.name}"/>
                            </c:url>
                            <a href="${getUserInPromoLink}">Get Users</a>
                        </td>
                    </tr>
                </c:forEach>
            </form>
        </table>
    </c:if>
    <c:if test="${empty promos}">
        <p class="message">No Promotions found!</p>
    </c:if>
    <p class="message">${message}</p>
</body>
</html>
