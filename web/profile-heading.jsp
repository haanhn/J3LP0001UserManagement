<%-- 
    Document   : profile-heading
    Created on : 15-Nov-2019, 15:29:55
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
        <h1>My Profile</h1>
        <form action="ServletCenter" method="POST">
            <input type="submit" value="Change My Password" name="action" />
        </form>
    </body>
</html>
