package com.saltlux.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saltlux.mysite.dao.BoardDao;
import com.saltlux.mysite.vo.BoardVo;
import com.saltlux.web.mvc.WebUtil;

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");

		if ("writeform".equals(action)) {
			// 이름
			String writer = request.getParameter("name");
			request.setAttribute("writer", writer);
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);

		} else if ("write".equals(action)) {
			// 이름
			String writer = request.getParameter("writer");
			String title = request.getParameter("title");
			String contents = request.getParameter("content");

			if(writer.isBlank() || title.isBlank() || contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			else {
				Long count = 0L;

				BoardVo vo = new BoardVo();
				vo.setContents(contents);
				vo.setCount(count);
				vo.setTitle(title);
				vo.setWriter(writer);

				new BoardDao().insert(vo);
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			}
		} else if ("search".equals(action)) {
			String kwd = request.getParameter("kwd");
			List<BoardVo> list = new BoardDao().search(kwd);
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
		} else if ("view".equals(action)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = new BoardDao().findOne(no);
			if(vo!=null) {
				request.setAttribute("vo", vo);
				WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
			}else {
				WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
			}

			Long count = vo.getCount();
			vo.setCount(++count);
			new BoardDao().updateCount(vo);


		} else if ("updateform".equals(action)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = new BoardDao().findOne(no);
			if(vo!=null) {
				request.setAttribute("vo", vo);
				WebUtil.forward("/WEB-INF/views/board/modify.jsp", request, response);
			}else {
				WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
			}

		} else if ("update".equals(action)) {
			Long no = Long.parseLong(request.getParameter("no"));
			String title = request.getParameter("title");
			String contents = request.getParameter("content");

			if(title.isBlank() || contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			else {
				BoardVo vo = new BoardVo();
				vo.setContents(contents);
				vo.setTitle(title);
				vo.setNo(no);

				new BoardDao().update(vo);
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			}


		} else if ("delete".equals(action)) {
			Long no = Long.parseLong(request.getParameter("no"));
			String writer = request.getParameter("writer");

			BoardVo vo = new BoardVo();
			vo.setNo(no);
			vo.setWriter(writer);

			new BoardDao().delete(vo);
			WebUtil.redirect(request.getContextPath()+"/board", request, response);

		} else {
			// 전체 게시판 조회
			int curpage = 1;
			if(request.getParameter("curpage") != null)
				curpage = Integer.parseInt(request.getParameter("curpage"));
			List<BoardVo> list = new BoardDao().findAll(curpage, 5);	
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
