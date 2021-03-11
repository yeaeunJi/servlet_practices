<%@page import="com.saltlux.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<BoardVo> list = (List<BoardVo>) request.getAttribute("list");
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
				<form id="search_form" action="<%=request.getContextPath()%>/board" method="post">
					<input type='hidden' name='a' value = 'search'/>
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>			
					<%
						for(int i=list.size()-1; i >= 0; i--){
							BoardVo vo = list.get(i);
					%>	
					<tr>
						<td><%=i+1%></td>
						<td><a href="<%=request.getContextPath()%>/board?a=view&no=<%=vo.getNo()%>"><%=vo.getTitle()%></a></td>
						<td><%=vo.getWriter() %></td>
						<td><%=vo.getCount() %></td>
						<td><%=vo.getRegDate() %></td>
						<td><a href="<%=request.getContextPath()%>/board?a=delete&writer=지예은&no=<%=vo.getNo() %>" class="del">삭제</a></td>

					</tr>
					<%
						}
					%>
				</table>
				<!-- 
				<div>			
					<ol id="paging">
						<li><a href= "<%=request.getContextPath()%>/board?curpage=1">1</a></li>
					</ol>
				</div>	 -->
				<div class="bottom">
					<a href='<%=request.getContextPath()%>/board?a=writeform&name=지예은' id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>