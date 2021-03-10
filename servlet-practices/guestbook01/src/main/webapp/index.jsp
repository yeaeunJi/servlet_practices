<%@page import="com.saltlux.guestbook.dao.GuestbookDao"%>
<%@page import="java.util.List"%>
<%@page import="com.saltlux.guestbook.vo.GuestbookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	GuestbookDao dao = new GuestbookDao();
	List<GuestbookVo> list = dao.findAll();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	<form action="/guestbook01/add.jsp" method="post">
		<table border=1 width=500>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan=4><textarea name="message" cols=60 rows=5></textarea></td>
			</tr>
			<tr>
				<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
			</tr>
		</table>
	</form>
	<br>
	<%
	for (int i = list.size() - 1; i >= 0; i--) {
		GuestbookVo vo = list.get(i);
	%>
	<table width=510 border=1>
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>등록일</th>
		</tr>
		<tr>
			<td align=center>[<%=i + 1%>]
			</td>
			<!-- no을 그대로 뿌리는 것이 아니라 조회된 코멘트 수 기준 -->
			<td><%=vo.getName()%></td>
			<td><%=vo.getRegDate()%></td>
			<td align=center><a
				href="/guestbook01/deleteform.jsp?no=<%=vo.getNo()%>">삭제</a></td>
			<!--  과제 : 비밀번호를 받은 후 삭제하는 기능 -->
		</tr>
		<tr>
			<th>내용</th>
			<td colspan=4><%=vo.getContents()%></td>
			<!-- 개행\n이 유지된 채로 나오도록 -->
		</tr>
	</table>
	<%
		}
	%>

</body>
</html>