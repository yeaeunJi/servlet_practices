<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String color = request.getParameter("c");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--  http://localhost:8080/jstlel/color.jsp?c=red  -->
	<%
	if ("red".equals(color)) {
	%>
	<h1 style="color: red">Hello JTSL</h1>
	<%
	} else if ("blue".equals(color)) {
	%>
	<h1 style="color: blue">Hello JTSL</h1>
	<%
	} else if ("green".equals(color)) {
	%>
	<h1 style="color: green">Hello JTSL</h1>
	<%
	} else {
	%>
	<h1 style="color: black">Hello JTSL</h1>
	<%
	}
	%>
</body>
</html>