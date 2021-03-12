<%@page import="com.saltlux.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	UserVo authUser = (UserVo)session.getAttribute("authUser"); // jsp에서 session 객체를 내장하고 있음
	// getSession(true)로 가져오기 때문에 HttpSession 객체를 무조건 가지고 있음
	
%>
<div id="header">
	<h1>MySite</h1>
	<ul>
		<% if(authUser == null){ %>
		<li><a href="<%=request.getContextPath()%>/user?a=loginform">로그인</a>
		</li>
		<li><a href="<%=request.getContextPath()%>/user?a=joinform">회원가입</a>
		</li>
		<% } else { %>
		<li><a href="<%=request.getContextPath()%>/user?a=updateform">회원정보수정</a>
		</li>
		<li><a href="<%=request.getContextPath()%>/user?a=logout">로그아웃</a>
		</li>
		<li><%=authUser.getName() %>님 안녕하세요 ^^;</li>
		<% } %>
	</ul>
</div>