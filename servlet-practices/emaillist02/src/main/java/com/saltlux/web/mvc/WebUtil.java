package com.saltlux.web.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {

	public static void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(path); // 해당 목적지로 분기할 dispatcher 객체를 얻음
		rd.forward(request, response); // request, response를 위의 목적지로 전달
	}
}
