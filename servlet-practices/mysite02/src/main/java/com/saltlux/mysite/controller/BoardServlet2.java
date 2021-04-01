package com.saltlux.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.saltlux.mysite.dao.BoardDao2;
import com.saltlux.mysite.vo.BoardVo2;
import com.saltlux.mysite.vo.PageVo;
import com.saltlux.mysite.vo.UserVo;
import com.saltlux.web.mvc.WebUtil;

public class BoardServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Long showNum = 10L;
	private Long pageShowNum = 5L;
	private BoardDao2 dao = new BoardDao2();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");

		if ("writeform".equals(action)) {
			// 이름
			//String writer = request.getParameter("name");
			//request.setAttribute("writer", writer);
			WebUtil.forward("/WEB-INF/views/board2/write.jsp", request, response);

		} 
		else if ("write".equals(action)) {
			// session 객체에 담겨 있는 name 가져오기
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}
			String writer = authUser.getName();
			int userNo =  Integer.parseInt(authUser.getNo().toString());
			String title = request.getParameter("title");
			String contents = request.getParameter("content");

			if(title.isBlank() || contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			else {
				BoardVo2 vo = new BoardVo2();
				vo.setContents(contents);
				vo.setTitle(title);
				vo.setUserNo(userNo);
				vo.setoNo(1);
				vo.setDepth(0);
				vo.setWriter(writer);
				int gNo = dao.getNewGNo();
				vo.setgNo(gNo);
				dao.insert(vo);
				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			}
		} 
			else if ("replyform".equals(action)) {
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}

			String no = request.getParameter("no");
			request.setAttribute("no", no);
			WebUtil.forward("/WEB-INF/views/board2/reply.jsp", request, response);
		} 
		else if ("reply".equals(action)) {
			// session 객체에 담겨 있는 name 가져오기
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}

			int userNo =  Integer.parseInt(authUser.getNo().toString());
			String contents = request.getParameter("contents");
			String title = request.getParameter("title");
			String no =  request.getParameter("no");
			String name = authUser.getName();
			
			if(contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			else {
				BoardDao2 dao = new BoardDao2();
				BoardVo2 vo = new BoardVo2();
				vo.setContents(contents);
				vo.setUserNo(userNo);

				// reply를 남기려는 게시글에 대한 정보를 가져옴
				BoardVo2 parent = dao.getParentInfo(no); // 최상위 게시글의 gno
				if(parent ==null) {
					WebUtil.redirect(request.getContextPath()+"/board2", request, response);
					return;
				}

				// 조회된 정보를 바탕으로 계층형 구조에 알맞은 값 넣기
				int gNo = parent.getgNo(); 
				int oNo = parent.getoNo();
				int depth = parent.getDepth() + 1;

				// 같은 그룹 내에서 oNo 최댓값 조회
				int maxONo = dao.getMaxONo(gNo);

				// 부모 oNo가 maxONo 보다 작으면 oNo이상 +1
				// parent : 1,  max = 2 ==> maxONo  2 
				if (maxONo > oNo) {  // 3 > 2 ==> 나는 3이 되어야함
					dao.updateOrderNo(gNo, oNo, 1); // 답글에 답글이 달릴 경우에는 해당 답글 뒤의 order가 밀림
				}
				
				oNo += 1; // 3  	

				// 새 게시글에 답글이 달린 경우에는
				vo.setgNo(gNo);
				vo.setDepth(depth);
				vo.setoNo(oNo);
				vo.setTitle(title);
				vo.setWriter(name);
				dao.insert(vo);

				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			}
		} 
		else if ("view".equals(action)) {
			String no = request.getParameter("no");
			BoardVo2 vo = dao.findOne(no);
			if(vo!=null) {
				request.setAttribute("vo", vo);
				WebUtil.forward("/WEB-INF/views/board2/view.jsp", request, response);
			}else {
				WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
				return;
			}
		}
			else if ("updateform".equals(action)) {
			String no = request.getParameter("no");
			BoardVo2 vo = dao.findOne(no);
			if(vo!=null) {
				request.setAttribute("vo", vo);
				WebUtil.forward("/WEB-INF/views/board2/modify.jsp", request, response);
			}else {
				WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
			}

		} 
		else if ("update".equals(action)) {
			String no = request.getParameter("no");
			String title = request.getParameter("title");
			String contents = request.getParameter("content");

			if(title.isBlank() || contents.isBlank())
				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			else {
				BoardVo2 vo = new BoardVo2();
				vo.setContents(contents);
				vo.setTitle(title);
				vo.setNo(no);
				dao.update(vo);
				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			}
		} 
		else if ("delete".equals(action)) {
			String no = request.getParameter("no");
			UserVo authUser = WebUtil.getAuthUser(request, response);

			if (authUser == null) {
				WebUtil.redirect(request.getContextPath()+"/loginform", request, response);
				return;
			}
			
			BoardVo2 vo = dao.findOne(no); 
			if (!dao.isGetChild(vo)	) {
				dao.delete(no);
				dao.updateOrderNo(vo.getgNo(), vo.getoNo(), -1);
				WebUtil.redirect(request.getContextPath()+"/board2", request, response);
			}else {
				WebUtil.redirect(request.getContextPath()+"/board2?msg=false", request, response);
			}

		} 
//		else if ("onePageBefore".equals(action)) {
//			String keyword = request.getParameter("keyword");
//			request.setAttribute("keyword", keyword);
//			Long curPage = request.getParameter("curPage") == null?1L:Long.parseLong(request.getParameter("curPage"));
//			Long endPage = request.getParameter("endPage") == null?1L:Long.parseLong(request.getParameter("endPage"));
//			//Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));
//			Long startPage = request.getParameter("startPage") == null?1L:Long.parseLong(request.getParameter("startPage"));
//			if (curPage % pageShowNum == 1)
//				startPage -= pageShowNum;
//
//			if (curPage % pageShowNum == 1) {
//				endPage =curPage-1;
//			}
//
//			curPage --;
//
//			BoardDao2 dao = new BoardDao2();
//			PageVo page = new PageVo();
//			page= dao.paging(showNum, keyword);
//			page.setShowNum(showNum);
//			page.setCurPage(curPage);
//			page.setStartPage(startPage);
//			page.setEndPage(endPage);
//			page.setStart((curPage-1)*showNum);
//			page.setTotal(page.getTotal());
//			page.setPageShowNum(pageShowNum);
//
//			List<BoardVo> list = dao.findAll(page);
//
//			request.setAttribute("list", list);
//			request.setAttribute("page", page);
//			WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
//		} 	
//		else if ("onePageNext".equals(action)) {
//
//			String keyword = request.getParameter("keyword");
//			request.setAttribute("keyword", keyword);
//			Long curPage = request.getParameter("curPage") == null?1L:Long.parseLong(request.getParameter("curPage"));
//			Long endPage = request.getParameter("endPage") == null?1L:Long.parseLong(request.getParameter("endPage"));
//			Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));
//			Long startPage = request.getParameter("startPage") == null?1L:Long.parseLong(request.getParameter("startPage"));
//			if (curPage % pageShowNum == 0 && totalPage != curPage)
//				startPage += pageShowNum;
//
//			if (totalPage - startPage < pageShowNum)	endPage = totalPage;
//			else if(curPage % pageShowNum == 0) {
//				System.out.println("endPage 증가");
//				endPage += pageShowNum;
//			}
//
//			curPage ++;
//
//			BoardDao2 dao = new BoardDao2();
//			PageVo page = new PageVo();
//			page= dao.paging(showNum, keyword);
//			page.setShowNum(showNum);
//			page.setCurPage(curPage);
//			page.setStartPage(startPage);
//			page.setEndPage(endPage);
//			page.setStart((curPage-1)*showNum);
//			page.setTotal(page.getTotal());
//			page.setPageShowNum(pageShowNum);
//
//			List<BoardVo> list = dao.findAll(page);
//
//			request.setAttribute("list", list);
//			request.setAttribute("page", page);
//			WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
//		} 	else if ("mulPageNext".equals(action)) {
//			Long endPage = request.getParameter("endPage") == null?1L:Long.parseLong(request.getParameter("endPage"));
//			Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));
//
//			String keyword = request.getParameter("keyword") == null ? "":request.getParameter("keyword");
//			request.setAttribute("keyword", keyword);
//			BoardDao2 dao = new BoardDao2();
//			PageVo page = new PageVo();
//			page= dao.paging(showNum, keyword);
//			page.setShowNum(showNum);
//			page.setCurPage(endPage+1);
//			page.setStartPage(endPage+1);
//			page.setStart((endPage)*showNum);
//
//			if (totalPage - (endPage+1) < pageShowNum)	endPage = totalPage;
//			else if(endPage % pageShowNum == 0) {
//				endPage += pageShowNum;
//			}
//
//			page.setEndPage(endPage);
//			page.setTotal(page.getTotal());
//			page.setPageShowNum(pageShowNum);
//			List<BoardVo> list = dao.findAll(page);
//
//			request.setAttribute("list", list);
//			request.setAttribute("page", page);
//			WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
//		} 	else if ("mulPageBefore".equals(action)) {
//			String keyword = request.getParameter("keyword") == null ? "":request.getParameter("keyword");
//			request.setAttribute("keyword", keyword);
//			Long startPage = request.getParameter("startPage") == null?1L:Long.parseLong(request.getParameter("startPage"));
//			//Long totalPage = request.getParameter("totalPage") == null?1L:Long.parseLong(request.getParameter("totalPage"));
//			BoardDao2 dao = new BoardDao2();
//			PageVo page = new PageVo();
//			page= dao.paging(showNum, keyword);
//			page.setShowNum(showNum);
//			page.setCurPage(startPage-1);
//			page.setStart((startPage-2)*showNum);
//			page.setEndPage(startPage-1);
//
//			startPage -= (pageShowNum-1);
//			page.setStartPage(startPage-1);
//
//			page.setTotal(page.getTotal());
//			page.setPageShowNum(pageShowNum);
//			List<BoardVo> list = dao.findAll(page);
//
//			request.setAttribute("list", list);
//			request.setAttribute("page", page);
//			WebUtil.forward("/WEB-INF/views/board2index.jsp", request, response);
//		} else if("movePage".equals(action)) { 			// 전체 게시판 조회
//			String keyword = request.getParameter("keyword") == null ? "":request.getParameter("keyword");
//			request.setAttribute("keyword", keyword);
//			BoardDao2 dao = new BoardDao2();
//			PageVo page = new PageVo();
//			String movePage = (request.getParameter("movePage"));
//			Long curPage = 1L;
//
//			if(!movePage.isBlank()) {
//				curPage =Long.parseLong(movePage); 
//			}
//
//			Long endPage =1L;
//			page = dao.paging(showNum, keyword);
//			Long startPage = 1L;
//
//			if (page.getTotal() - startPage < pageShowNum)	endPage = page.getTotal();
//			else  endPage = pageShowNum;
//
//			page.setShowNum(showNum);
//			page.setCurPage(curPage);
//			page.setStart((curPage-1)*showNum);
//			page.setStartPage(startPage);
//			page.setEndPage(endPage);
//			page.setPageShowNum(pageShowNum);
//			List<BoardVo> list = dao.findAll(page);
//			request.setAttribute("list", list);
//			request.setAttribute("page", page);
//			WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
		//}
	else { 			// 전체 게시판 조회
		
			String msg = request.getParameter("msg") == null ? "":request.getParameter("msg");
			System.out.println("msg = "+msg);
			if(!"".equals(msg)) {
				request.setAttribute("msg", "답글이 있는 게시글은 삭제할 수 없습니다");
			}
			
			PageVo page = new PageVo();
			Long curPage = 1L;
			Long endPage =1L;
			page = dao.paging(showNum);
			System.out.println("page="+page);
			Long startPage = 1L;

			if (page.getTotal() - startPage < pageShowNum)	endPage = page.getTotal();
			else  endPage = (Long)pageShowNum;

			page.setShowNum(showNum);
			page.setCurPage(curPage);
			page.setStartPage(startPage);
			page.setEndPage(endPage);
			page.setStart((curPage-1)*showNum);
			page.setPageShowNum(pageShowNum);
			List<BoardVo2> list = dao.findAll(page);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			WebUtil.forward("/WEB-INF/views/board2/index.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}