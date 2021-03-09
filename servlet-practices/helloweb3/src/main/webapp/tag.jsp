<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String no = request.getParameter("no");
int number = -1;
if (no != null && no.matches("\\d*"))
number = Integer.parseInt(no);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
		전달 받은 값 :
		<%= number %></h1>
	<h1>hello world</h1>
	<h2>hello world</h2>
	<h3>hello world</h3>
	<h4>hello world</h4>
	<h5>hello world</h5>
	<h6>hello world</h6>

	<table border="1" cellspacing="0" cellpadding="10">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
		</tr>
		<tr>
			<td>01</td>
			<td>test</td>
			<td>tester01</td>
		</tr>
	</table>

	<br>
	<img src="/helloweb3/images/images.jpeg" />
	<span>절대 경로</span>
	<br>
	<img src='./images/images.jpeg' />
	<span>현재 위치를 기준으로 하는 상대 경로(images/images.jpeg)</span>
	<p>ol을 사용을 권장</p>
	<a href="index.html">메인으로 이동</a>
	<a href="form.jsp">폼으로 가기</a>
	<ul>
		<li>bbbb</li>
		<li>aaaa</li>
	</ul>

	<ol>
		<li>bbbb</li>
		<li>aaaa</li>
	</ol>
</body>
</html>