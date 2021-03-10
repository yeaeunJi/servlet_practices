<%@page import="com.saltlux.emaillist.vo.EmaillistVo"%>
<%@page import="com.saltlux.emaillist.dao.EmaillistDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	request.setCharacterEncoding("utf-8");
	String firstName = request.getParameter("firstName");
	String lastName = request.getParameter("lastName");
	String email = request.getParameter("email");
	EmaillistVo vo = new EmaillistVo();
	vo.setFirst_name(firstName);
	vo.setLast_name(lastName);
	vo.setEmail(email);
	
	EmaillistDao dao = new EmaillistDao();
	boolean result = dao.insert(vo);
	
	// 지금과 같이 jsp 에서 insert가 일어난 경우에는 새로고침 시 계속해서 데이터가 중복되어 들어가므로 현재 페이지에 
	// 머무르지 않고 다른 페이지로 이동해야 함
	response.sendRedirect("/emaillist01/index.jsp"); // 응답으로 브라우저에게 이동할 url을 알려주면 브라우즈는 다시 이 url로 이동해달라고 요청
	
%>
<!-- 이렇게 코드만 들어있는 jsp가 생기므로 서블릿을 사용하여 제어하는 것이 좋음 -->