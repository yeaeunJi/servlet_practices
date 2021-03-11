package com.saltlux.guestbook.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.saltlux.guestbook.dao.GuestbookDao;
import com.saltlux.guestbook.vo.GuestbookVo;
import com.saltlux.web.mvc.WebUtil;

public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		
		if("delete".equals(action)) { 
			String password = request.getParameter("password");
			Long no = Long.parseLong(request.getParameter("no"));
			
			GuestbookVo vo = new GuestbookVo();
			vo.setPassword(password);
			vo.setNo(no);
			
			GuestbookDao dao = new GuestbookDao();
			dao.delete(vo);
			WebUtil.redirect(request.getContextPath()+"/gb", request, response);
			
		} else if("deleteform".equals(action)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			request.setAttribute("no", no); // request안에 보내줄 데이터를 담음
			request.setAttribute("password", password);
			WebUtil.forward("/WEB-INF/views/deleteform.jsp", request, response);
		}  
		else if("add".equals(action)) {
			
			GuestbookDao dao = new GuestbookDao();
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String contents = request.getParameter("message"). replaceAll("\n", "<br>");
			
			if(!name.isBlank() && !password.isBlank() && !contents.isBlank() ) {
				GuestbookVo vo = new GuestbookVo(); 
				vo.setName(name);
				vo.setPassword(password);
				vo.setContents(contents);
				dao.insert(vo);
			}
			WebUtil.redirect(request.getContextPath()+"/gb", request, response);
		
		} else {
			List<GuestbookVo> list = new GuestbookDao().findAll();
			request.setAttribute("list", list); // request안에 보내줄 데이터를 담음
			WebUtil.forward("/WEB-INF/views/index.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
