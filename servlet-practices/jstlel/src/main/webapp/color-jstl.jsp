<!-- 라이브러리 지시자 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--  http://localhost:8080/jstlel/color.jsp?c=red  -->
	<c:choose>
		<c:when test="${'red' == param.c }">
			<h1 style="color: red">Hello JTSL</h1>
		</c:when>
		<c:when test="${'blue' == param.c }">
			<h1 style="color: blue">Hello JTSL</h1>
		</c:when>
		<c:when test="${'green' == param.c }">
			<h1 style="color: green">Hello JTSL</h1>
		</c:when>
		<c:otherwise>
			<h1 style="color: black">Hello JTSL</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>