package com.saltlux.helloweb3.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 보내는 텍스트 내용에 한글이 포함된 문자셋을 사용하라고 알려줘야 함
		response.setContentType("text/html; charset=utf-8"); // reseponse 헤더에 인코딩 정보를 담음
		PrintWriter pw = response.getWriter();  // getWrtier() : \0000 빈 개행 문자 뒤에 body가 생성
		pw.print("<h1>안녕 :) !!!</h1>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
