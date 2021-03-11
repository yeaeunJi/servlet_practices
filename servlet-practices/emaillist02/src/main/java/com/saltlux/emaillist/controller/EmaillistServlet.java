package com.saltlux.emaillist.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.saltlux.emaillist.dao.EmaillistDao;
import com.saltlux.emaillist.vo.EmaillistVo;
import com.saltlux.web.mvc.WebUtil;

public class EmaillistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");
		// 만약 url에 emaillist02/el/list 형식으로 보낼 경우에는 다음과 같이 처리해야 함
		//  web.xml에 추가
		//	String uri = request.getRequestURI();
		//	String[] paths = uri.split("/");
		//	String action = paths[paths.length-1];
		// ==> Spring 사용 시 더 간단해져서 자주 사용됨
		
		if("form".equals(action)) { // action.equals("list")로 사용할 경우 action이 null이 되면 문제가 될 수 있음
									// nullPointerException ==> 500 페이지 표시됨(내부 서버 오류)
			WebUtil.forward("/WEB-INF/views/form.jsp", request, response);
		} else if("add".equals(action)) {
			
		} else {
			List<EmaillistVo> list = new EmaillistDao().findAll();
			// 포워딩(forwarding = request dispatch = request extension)을 통해 JSP로 제어권을 넘겨줘야함
			request.setAttribute("list", list); // request안에 보내줄 데이터를 담음
			WebUtil.forward("/WEB-INF/views/index.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
