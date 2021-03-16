package com.saltlux.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saltlux.mysite.dao.BoardDao;
import com.saltlux.mysite.vo.BoardVo;
import com.saltlux.mysite.vo.PageVo;
import com.saltlux.mysite.vo.UserVo;
import com.saltlux.web.mvc.WebUtil;

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");

		if ("writeform".equals(action)) {
			// 이름
			String writer = request.getParameter("name");
			request.setAttribute("writer", writer);
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);

		} else if ("write".equals(action)) {
			// session 객체에 담겨 있는 name 가져오기
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}

			Long userNo = authUser.getNo();
			String title = request.getParameter("title");
			String contents = request.getParameter("content");

			if(title.isBlank() || contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			else {
				BoardVo vo = new BoardVo();
				vo.setContents(contents);
				vo.setTitle(title);
				vo.setUserNo(userNo);

				new BoardDao().insert(vo);
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			}
		} else if ("replyform".equals(action)) {
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}

			Long no = Long.parseLong(request.getParameter("no"));
			request.setAttribute("no", no);
			WebUtil.forward("/WEB-INF/views/board/reply.jsp", request, response);

		} 
		else if ("reply".equals(action)) {
			// session 객체에 담겨 있는 name 가져오기
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}

			Long userNo = authUser.getNo();
			String contents = request.getParameter("contents");
			String title = request.getParameter("title");
			Long no =  Long.parseLong(request.getParameter("no"));

			if(contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			else {
				BoardDao dao = new BoardDao();
				BoardVo vo = new BoardVo();
				vo.setContents(contents);
				vo.setUserNo(userNo);

				// reply를 남기려는 게시글에 대한 정보를 가져옴
				BoardVo parent = dao.getParentInfo(no); // 최상위 게시글의 gno
				if(parent ==null) {
					WebUtil.redirect(request.getContextPath()+"/board", request, response);
					return;
				}

				// 조회된 정보를 바탕으로 계층형 구조에 알맞은 값 넣기
				Long gNo = parent.getgNo(); 
				Long oNo = parent.getoNo();
				Long depth = parent.getDepth() + 1;

				// 같은 그룹 내에서 oNo 최댓값 조회
				Long maxONo = dao.getMaxONo(gNo);

				// 부모 oNo가 maxONo 보다 작으면 oNo이상 +1
				if (maxONo > oNo) {
					// 답글에 답글이 달릴 경우에는 해당 답글 뒤의 order가 밀림
					dao.updateOrderNo(gNo, oNo, no);
				}

				// 나는 oNo가 됨
				oNo += 1;

				// 새 게시글에 답글이 달린 경우에는
				vo.setgNo(gNo);
				vo.setDepth(depth);
				vo.setoNo(oNo);
				vo.setTitle(title);
				dao. replyInsert(vo);

				WebUtil.redirect(request.getContextPath()+"/board", request, response);
			}
		}else if ("search".equals(action)) {
			String kwd = request.getParameter("kwd");
			List<BoardVo> list = new BoardDao().search(kwd);
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		} else if ("view".equals(action)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = new BoardDao().findOne(no);
			if(vo!=null) {
				request.setAttribute("vo", vo);
				WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
			}else {
				WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
				return;
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
				WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
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
			//			String writer = request.getParameter("writer");

			BoardVo vo = new BoardVo();
			vo.setNo(no);
			//			vo.setWriter(writer);

			new BoardDao().delete(vo);
			WebUtil.redirect(request.getContextPath()+"/board", request, response);

		} else { 			// 전체 게시판 조회
			Long curpage = 1L;
			
			if(request.getParameter("curpage") != null)
				curpage = Long.parseLong(request.getParameter("curpage"));
			PageVo page = new PageVo();
			
			page.setShowNum(5L);
			page.setCur(curpage);
			Long totalpage = new BoardDao().paging(page.getShowNum());
			page.setTotal(totalpage);
			page.setStart((curpage - 1)*page.getShowNum());
			List<BoardVo> list = new BoardDao().findAll(page);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
