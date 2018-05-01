<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Hello World!</h2>
<form action="/api/get/login">
    用户名<input type="text" name="username">
    用户密码<input type="password" name="userpassword">
    <input type="submit" value="提交">
</form>
<form action="/upLoadFile" method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="file"/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
