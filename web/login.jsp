<%-- 
    Document   : login
    Created on : 11-Nov-2019, 09:39:12
    Author     : HaAnh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Management</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="ServletCenter" method="POST">
            <table border="0">
                <tr>
                    <td>User Id</td>
                    <td>
                        <input type="text" name="userId" value="" />
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td> <input type="password" name="password" value="" /> </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Login" name="action" />
                    </td>
                </tr>
            </table>
        </form>
        ${requestScope.error}
    </body>
</html>
