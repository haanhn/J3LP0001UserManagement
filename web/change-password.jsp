<%-- 
    Document   : change-password
    Created on : 15-Nov-2019, 15:33:03
    Author     : HaAnh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>Old Password</th>
                    <td>
                        <input type="password" name="oldPassword" value="" />
                        ${error.oldPasswordErr}
                    </td>
                </tr>
                <tr>
                    <th>New Password</th>
                    <td>
                        <input type="password" name="newPassword" value="" />
                        ${error.newPasswordErr}
                    </td>
                </tr>
                <tr>
                    <th>Confirm</th>
                    <td>
                        <input type="password" name="confirm" value="" />
                        ${error.confirmErr}
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Change Password" name="action" />
                    </td>
                </tr>
            </table>
        </form>
        ${message}
    </body>
</html>
