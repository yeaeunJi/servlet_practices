<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	<form method="post" action="/guestbook01/delete.jsp">
	<input type='hidden' name="no" value="<%=request.getParameter("no")%>"> <!-- 사용자가 지우고 싶은 방명록의 번호를 받아옴 -->
	<table>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="password"></td>
			<td><input type="submit" value="확인"></td>
			<td><a href="/guestbook01/index.jsp">메인으로 돌아가기</a></td>
		</tr>
	</table>
	</form>
</body>
</html>