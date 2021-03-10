<%@page import="com.saltlux.guestbook.dao.GuestbookDao"%>
<%@page import="com.saltlux.guestbook.vo.GuestbookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String password = request.getParameter("password");
	String contents = request.getParameter("message"). replaceAll("\n", "<br>");
	
	if(!name.isBlank() && !password.isBlank() && !contents.isBlank() ) {
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setContents(contents);
		GuestbookDao dao = new GuestbookDao();
		boolean result = dao.insert(vo);
	}
	
	response.sendRedirect("/guestbook01/index.jsp"); 
	
%>