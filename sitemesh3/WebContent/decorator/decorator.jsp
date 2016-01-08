<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp的装饰页面</title>
<style type="text/css">
	body{font-size: 12px;}
</style>
<sitemesh:write property="head"/>
</head>
<body>
header </br>
下面是被装饰的页面：</br>
==========分割线==========</br>
	 <sitemesh:write property="body"/>  
</br>==========分割线==========
	 </br>
footer
</body>
</html>