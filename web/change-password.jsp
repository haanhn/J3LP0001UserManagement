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
        <title>Change my Password</title>
        <link href="css/myStyle.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <a href="ServletCenter">Back to Home</a>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <th>Old Password</th>
                    <td>
                        <input type="password" name="oldPassword" value="" />
                        <span class="show-input-err">${error.oldPasswordErr}</span>
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
                        <input type="submit" value="Change Password" name="action" />
                    </td>
                </tr>
            </table>
        </form>
        <p class="message">${message}</p>
    </body>
</html>
