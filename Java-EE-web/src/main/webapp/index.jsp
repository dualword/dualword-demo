<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.dualword.javaeeweb.ContextListener" %>   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Java-EE-web</title>
</head>
<body>

<h2>Hello World!</h2>

<br>
<a href="imageservlet">Image Servlet</a>&nbsp;
<img src="imageservlet"/>
<br>

<p>
Current time:<%= String.valueOf(System.currentTimeMillis()) %>&nbsp;
<%
StringBuilder sb = new StringBuilder();
sb.append(ContextListener.UPTIME.substring(0,1).toUpperCase());
sb.append(ContextListener.UPTIME.substring(1).toLowerCase());
sb.append(":");
sb.append(getServletContext().getAttribute(ContextListener.UPTIME));
out.write(sb.toString());
%>
</p>

</body>
</html>

