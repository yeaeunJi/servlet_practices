<%@page import="com.saltlux.guestbook.dao.GuestbookDao"%>
<%@page import="com.saltlux.guestbook.vo.GuestbookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	request.setCharacterEncoding("utf-8");
	String password = request.getParameter("password");

	Long no = Long.parseLong(request.getParameter("no"));
	GuestbookVo vo = new GuestbookVo();
	vo.setPassword(password);
	vo.setNo(no);
	
	GuestbookDao dao = new GuestbookDao();
	boolean result = dao.delete(vo);
	response.sendRedirect("/guestbook01/index.jsp"); 
	
%>