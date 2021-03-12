<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.saltlux.mysite.vo.BoardVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type='hidden' name='a' value = 'search'/>
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
					<h6>* 제목과 내용에서 해당 키워드를 검색합니다.</h6>
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
					<c:set var="count" value="${fn:length(list)}"/>	
					<c:forEach items="${list}" begin="0" step="1" varStatus="status" var="vo" >
					<tr>
					<!-- padding-left:\${(vo.depth-1)*20}px -->
						<td>${count-status.index }</td>
						<td><a style="text-align:left; padding-left:0px;"   href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a></td>
						<td>${vo.writer }</td>
						<td>${vo.count }</td>
						<td>${vo.regDate }</td>
						<td><a href="${pageContext.request.contextPath }/board?a=delete&writer=지예은&no=${vo.no}" class="del">삭제</a></td>

					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>					

				<div class="bottom">
					<a href='${pageContext.request.contextPath }/board?a=writeform&name=지예은' id="new-book">글쓰기</a>
				</div>
				
			</div>
		</div>
		<c:import url ="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url ="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>