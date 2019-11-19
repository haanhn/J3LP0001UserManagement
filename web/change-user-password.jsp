<%-- 
    Document   : change-user-password
    Created on : 16-Nov-2019, 22:01:45
    Author     : HaAnh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/myStyle.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <h1>Change User Password</h1>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>User Id</th>
                    <td>
                        ${param.userId}
                    </td>
                </tr>
                <tr>
                    <th>New Password</th>
                    <td>
                        <input type="password" name="newPassword" value="" />
                        <span class="show-input-err">${error.newPasswordErr}</span>
                    </td>
                </tr>
                <tr>
                    <th>Confirm</th>
                    <td>
                        <input type="password" name="confirm" value="" />
                        <span class="show-input-err">${error.confirmErr}</span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="hidden" value="${param.userId}" name="userId" />
                        <input type="submit" value="Change User Password" name="action" />
                    </td>
                </tr>
            </table>
        </form>
        <p class="message">${message}</p>
        <a href="ServletCenter">Back to Home</a>
    </body>
</html>
