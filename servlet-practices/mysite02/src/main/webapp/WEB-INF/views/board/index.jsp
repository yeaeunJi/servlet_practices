<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
						<td style="text-align:left; padding-left:0px;">
							<a style="text-align:left; padding-left:0px;"   href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}">
							<c:if test="${vo.depth >= 1 }">
								<c:forEach begin="1" end="${vo.depth}" step="1">
									<span>&nbsp;</span>
								</c:forEach>
								<img src="${pageContext.request.contextPath}/assets/images/reply.png" />
							</c:if>
								${vo.title }
							</a>
						</td>
						<td>${vo.writer }</td>
						<td>${vo.count }</td>
						<td>${vo.regDate }</td>
						<c:if test="${!empty authUser  && authUser.no == vo.userNo}">
						<td><a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul> <!-- 일단 페이지 전체 출력하기 -->
						<li><a href="">◀</a></li>
						<li><a href="${pageContext.request.contextPath }/board?curpage=1" class="selected">1</a>
						<c:forEach  step="1"   begin="2"  end="${page.total}" varStatus="status">
						<a href="${pageContext.request.contextPath }/board?curpage=${status.index}" >${status.index}</a>
						</c:forEach>
						</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>					

				<div class="bottom">
				 <c:if test="${!empty authUser }">
				<a href='${pageContext.request.contextPath }/board?a=writeform' id="new-book">글쓰기</a>
				</c:if> 
				</div>
				
			</div>
		</div>
		<c:import url ="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url ="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>