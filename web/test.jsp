<%-- 
    Document   : test
    Created on : 14-Nov-2019, 17:29:18
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
        <form action="ServletTest" method="POST" enctype="multipart/form-data">
            <input type="file" name="img" />
            <input type="submit" value="upload" />
        </form>
    </body>
</html>
