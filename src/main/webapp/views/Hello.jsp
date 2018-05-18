<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
<h2>Hello World!</h2>

<form action="/upLoadFile" method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="file"/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
