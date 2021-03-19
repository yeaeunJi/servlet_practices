package com.saltlux.mysite.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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
	private Long showNum = 2L;
	private Long pageShowNum = 3L;
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

		} else if ("onePageBefore".equals(action)) {
			System.out.println(" ===== 한 페이지 앞으로 이동 =====");
			
			Long curPage = request.getParameter("curPage") == null?1L:Long.parseLong(request.getParameter("curPage"));
			Long endPage = request.getParameter("endPage") == null?1L:Long.parseLong(request.getParameter("endPage"));
			Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));
			Long startPage = request.getParameter("startPage") == null?1L:Long.parseLong(request.getParameter("startPage"));
			System.out.println("endPage="+endPage);
			if (curPage % pageShowNum == 1)
				startPage -= pageShowNum;
			
			if (curPage % pageShowNum == 1) {
				endPage =curPage-1;
			}
			
			curPage --;
			
			BoardDao dao = new BoardDao();
			PageVo page = new PageVo();
			page= dao.paging(showNum);
			page.setShowNum(showNum);
			page.setCurPage(curPage);
			page.setStartPage(startPage);
			page.setEndPage(endPage);
			page.setStart((curPage-1)*showNum);
			page.setTotal(page.getTotal());
			page.setPageShowNum(pageShowNum);
			System.out.println("=== paging ====");
			System.out.println(page);
			
			
			List<BoardVo> list = dao.findAll(page);
			
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		} 	else if ("onePageNext".equals(action)) {
			System.out.println(" ===== 한 페이지 옆으로 이동 =====");
			
			Long curPage = request.getParameter("curPage") == null?1L:Long.parseLong(request.getParameter("curPage"));
			Long endPage = request.getParameter("endPage") == null?1L:Long.parseLong(request.getParameter("endPage"));
			Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));
			Long startPage = request.getParameter("startPage") == null?1L:Long.parseLong(request.getParameter("startPage"));
			System.out.println("endPage="+endPage);
			if (curPage % pageShowNum == 0 && totalPage != curPage)
				startPage += pageShowNum;
			
			if (totalPage - startPage < pageShowNum)	endPage = totalPage;
			else if(curPage % pageShowNum == 0) {
				System.out.println("endPage 증가");
				endPage += pageShowNum;
			}
			
			curPage ++;
			
			BoardDao dao = new BoardDao();
			PageVo page = new PageVo();
			page= dao.paging(showNum);
			page.setShowNum(showNum);
			page.setCurPage(curPage);
			page.setStartPage(startPage);
			page.setEndPage(endPage);
			page.setStart((curPage-1)*showNum);
			page.setTotal(page.getTotal());
			page.setPageShowNum(pageShowNum);
			System.out.println("=== paging ====");
			System.out.println(page);
			
			
			List<BoardVo> list = dao.findAll(page);
			
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		} 	 else if ("mulPageNext".equals(action)) {
				System.out.println("====== mulPageNext ====");
				Long endPage = request.getParameter("endPage") == null?1L:Long.parseLong(request.getParameter("endPage"));
				Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));

				BoardDao dao = new BoardDao();
				PageVo page = new PageVo();
				page= dao.paging(showNum);
				page.setShowNum(showNum);
				page.setCurPage(endPage+1);
				page.setStartPage(endPage+1);
				page.setStart((endPage)*showNum);

				if (totalPage - (endPage+1) < pageShowNum)	endPage = totalPage;
				else if(endPage % pageShowNum == 0) {
					endPage += pageShowNum;
				}
				
				page.setEndPage(endPage);
				page.setTotal(page.getTotal());
				page.setPageShowNum(pageShowNum);
				System.out.println("+++++ paging +++++");
				System.out.println(page);
				List<BoardVo> list = dao.findAll(page);
				
				request.setAttribute("list", list);
				request.setAttribute("page", page);
				WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
			} 
		else if("onePageBefore".equals(action)){ 			// 전체 게시판 조회
			String strCur =  request.getParameter("curPage");
			Long curPage = 0L;
			Long showNum = 2L;
			PageVo page = new PageVo();
			
			if (Pattern.matches("[1-9]*$", strCur))
				curPage = Long.parseLong(strCur);
			
			if(curPage % showNum == 0) {
				page.setStartPage(curPage+1);
			}
//			BoardDao dao = new BoardDao();
//			page= dao.paging(showNum);
//			page.setShowNum(2L);
//			page.setCur(curpage);
//page.setPageShowNum(pageShowNum);
//			page.setStart((curpage - 1)*showNum);
//			page.setTotal(page.getTotalCount());
//			List<BoardVo> list = dao.findAll(page);
//			page.setStartPage(1L);
//			
//			
//			if (page.getTotal() >= 5L) 	page.setEndPage(5L);
//			else page.setEndPage(page.getTotal());
//			System.out.println("startPage:"+page.getStartPage()+", endPage : "+page.getEndPage());
//			request.setAttribute("list", list);
//			request.setAttribute("page", page);
//			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		}else if("movePage".equals(action)) { 			// 전체 게시판 조회
			BoardDao dao = new BoardDao();
			PageVo page = new PageVo();
			String movePage = (request.getParameter("movePage"));
			Long curPage = 1L;
			
			if(!movePage.isBlank()) {
				curPage =Long.parseLong(movePage); 
			}
			
			Long endPage =1L;
			page = dao.paging(showNum);
			Long startPage = 1L;
		
			if (page.getTotal() - startPage < pageShowNum)	endPage = page.getTotal();
			else  endPage = pageShowNum;
			
			page= dao.paging(showNum);
			page.setShowNum(showNum);
			page.setCurPage(curPage);
			page.setStart((curPage-1)*showNum);
			page.setStartPage(startPage);
			page.setEndPage(endPage);
			page.setPageShowNum(pageShowNum);
			System.out.println("movePage result:"+page);
			List<BoardVo> list = dao.findAll(page);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		}else { 			// 전체 게시판 조회
			BoardDao dao = new BoardDao();
			PageVo page = new PageVo();
			Long curPage = 1L;
			Long endPage =1L;
			page = dao.paging(showNum);
			Long startPage = 1L;
		
			if (page.getTotal() - startPage < pageShowNum)	endPage = page.getTotal();
			else  endPage = (Long)pageShowNum;
			
			page= dao.paging(showNum);
			page.setShowNum(showNum);
			page.setCurPage(curPage);
			page.setStartPage(startPage);
			page.setEndPage(endPage);
			page.setStart((curPage-1)*showNum);
			page.setPageShowNum(pageShowNum);
			System.out.println("paging:"+page);
			List<BoardVo> list = dao.findAll(page);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
