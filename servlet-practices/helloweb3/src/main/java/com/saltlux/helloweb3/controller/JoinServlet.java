package com.saltlux.helloweb3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post 방식으로 넘어오는 데이터의 인코딩
		request.setCharacterEncoding("UTF-8");
		
		String email = request.getParameter("email");
		String pw = request.getParameter("password");
		String birthYear = request.getParameter("birthYear");
		String gender = request.getParameter("gender");
		String[] hobbies = request.getParameterValues("hobbies");
		String desc = request.getParameter("desc");
		
		System.out.println("email="+email+", pw="+pw);
		System.out.println("birthYear="+birthYear+", gender="+gender);
		
		System.out.println("취미 : ");
		for(String hobby : hobbies) {
			System.out.print(hobby);
		}
		System.out.println(desc);
		
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print("가입이 완료되었습니다.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
