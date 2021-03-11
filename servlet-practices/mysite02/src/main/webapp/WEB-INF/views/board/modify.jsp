<%@page import="com.saltlux.mysite.vo.BoardVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	BoardVo vo = (BoardVo) request.getAttribute("vo");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="<%=request.getContextPath()%>/board">
				<input type='hidden' name="a" value="update"/>
				<input type='hidden' name="no" value="<%=vo.getNo() %>"/>
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="<%=vo.getTitle() %>"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"><%=vo.getContents() %></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="<%=request.getContextPath()%>/board?a=view&no=<%=vo.getNo()%>">취소</a>
						<input type="submit" value="수정">
					</div>
				</form>				
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>