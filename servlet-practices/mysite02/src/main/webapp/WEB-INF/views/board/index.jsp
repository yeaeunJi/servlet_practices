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
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
<style>
.tbl-ex tr:nth-child(odd) {
background-color : gray;
}

.tbl-ex tr:nth-child(even) {
background-color: #777799;;
}
</style>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form"
					action="${pageContext.request.contextPath }/board" method="post">
					<input type='hidden' name='a' value='search' /> <input type="text"
						id="kwd" name="keyword" value="${keyword}"> <input type="submit"
						value="찾기">
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
					<c:set var="count" value="${fn:length(list)}" />
					<c:forEach items="${list}" begin="0" step="1" varStatus="status"
						var="vo">
						<tr class="board">
							<!-- padding-left:\${(vo.depth-1)*20}px -->
							<td>${page.totalCount - (page.curPage-1)*page.showNum-status.index}</td>
							<td style="text-align: left; padding-left: 0px;"><a
								style="text-align: left; padding-left: 0px;"
								href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}&keyword=${keyword}" onclick="setHistory(this)">
									<c:if test="${vo.depth >= 1 }">
										<c:forEach begin="1" end="${vo.depth}" step="1">
											<span>&nbsp;</span>
										</c:forEach>
										<img
											src="${pageContext.request.contextPath}/assets/images/reply.png" />
									</c:if> <c:choose>
										<c:when test="${fn:length(vo.title) <= 10}">
									 		${vo.title}
									 	</c:when>
										<c:otherwise>
									 		${fn:substring(vo.title,0,10)}...
									 	</c:otherwise>
									</c:choose>
							</a></td>
							<td>${vo.writer }</td>
							<td>${vo.count }</td>
							<td>${vo.regDate }</td>
							<c:if test="${!empty authUser  && authUser.no == vo.userNo}">
								<td><a
									href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}&keyword=${keyword}"
									class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>

				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:choose>
							<c:when test="${page.startPage!=1}">
								<li><a
									href="${pageContext.request.contextPath }/board?a=mulPageBefore&startPage=${page.startPage}&totalPage=${page.total}&keyword=${keyword}">
										◀◀ </a></li>
							</c:when>
							<c:otherwise>
									<li><a href="#">◀◀ </a></li>
							</c:otherwise>
						</c:choose>
					
					
						<c:choose>
							<c:when test="${page.curPage!=1}">
								<li><a
									href="${pageContext.request.contextPath }/board?a=onePageBefore&curPage=${page.curPage}&startPage=${page.startPage}&endPage=${page.endPage}&totalPage=${page.total}&keyword=${keyword}">◀</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="#">◀</a></li>
							</c:otherwise>
						</c:choose>
						
						<c:forEach step="1" begin="${page.startPage}" end="${page.endPage}"  var="pageNum"  varStatus="status">
					<li><a
								href="${pageContext.request.contextPath }/board?a=movePage&movePage=${pageNum}&keyword=${keyword}">${pageNum}</a></li>
					 
						</c:forEach>
						
						<c:choose>
							<c:when test="${page.curPage!= page.total}">
								<li><a
									href="${pageContext.request.contextPath }/board?a=onePageNext&curPage=${page.curPage}&startPage=${page.startPage}&endPage=${page.endPage}&totalPage=${page.total}&keyword=${keyword}">▶</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="#">▶</a></li>
							</c:otherwise>
						</c:choose>


						<c:choose>
							<c:when test="${page.endPage != page.total}">
								<li><a
									href="${pageContext.request.contextPath }/board?a=mulPageNext&endPage=${page.endPage}&totalPage=${page.total}&keyword=${keyword}">
										▶▶</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="#">
										▶▶</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>

				<div class="bottom">
					<c:if test="${!empty authUser }">
						<a href='${pageContext.request.contextPath }/board?a=writeform'
							id="new-book">글쓰기</a>
					</c:if>
				</div>

				<div class="history">
				<h3 style="display:inline-block;"> ** 최근 방문한 게시글 ** </h3>
				<button onclick="removeHistory()">히스토리 지우기</button> 
				<ul  id="historyList" >			
				</ul>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>

<script>
// 전역변수 history 배열에  localStorage에서 가져온 값을 담아놓음
var history = [];

window.onload = function() {  
	history =JSON.parse(localStorage.getItem("history"))||[];
	getHistory();
}

// 게시글 제목 url 클릭 시 발생하는 이벤트함수 : localStorage에 접근 url 추가
function setHistory(aTag)
{
	let url = aTag.href; 
	history.push(url);
	localStorage.setItem("history", JSON.stringify(history));
	getHistory();
}

// history에 저장된 값을 화면에 표시 --> dom 사용
function getHistory(){
	// historyList 태그에 추가할 노드 생성
	let parent = document.getElementById("historyList");
	for (var i = history.length-1; i >= 0 ; i--){
		
		let li = document.createElement("li");
		let liNode = document.createTextNode("["+(i+1)+"] "+history[i]);
		var att=document.createAttribute("style");
		att.value="border:1px solid purple;";
		li.setAttributeNode(att);
		li.appendChild(liNode);
		parent.appendChild(li);
	}
}

// 히스토리 삭제
function removeHistory(){	
	localStorage.removeItem('history');
	//window.location.reload();
}
</script>
</html>