<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>제목입니다.</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								내용 1입니다.<br>
								내용 2입니다.<br>
								내용 3입니다.
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="">글목록</a>
					<a href="">글수정</a>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>