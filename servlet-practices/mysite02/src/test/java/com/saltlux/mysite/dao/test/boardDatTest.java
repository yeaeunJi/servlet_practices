package com.saltlux.mysite.dao.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.saltlux.mysite.dao.BoardDao2;
import com.saltlux.mysite.vo.BoardVo2;
import com.saltlux.mysite.vo.PageVo;

public class boardDatTest {
	private static BoardDao2 dao = new BoardDao2();
	public static void main(String[] args) {
		//insert();
		//getNewGNo();
		PageVo pageVo = new PageVo();
		pageVo.setStart(0L);
		pageVo.setShowNum(10L);
		findAll(pageVo);
		
//		PageVo page = dao.paging(2);
//		System.out.println("게시글 수 : "+page.getTotalCount()+", 총 페이지 수 : "+page.getTotal());
//		findOne("6065cf5a51b6b92f7cfbe597");
//		BoardVo2 vo = new BoardVo2();
//		vo.setDepth(1);
//		vo.setgNo(2);
//		vo.setoNo(3);
//		System.out.println("해당 게시물은 답글이 있는 게시물입니까? "+	isGetChild(vo));
	
		//delete("6065cf3151b6b96a842d18f0");
		//findOne("6065cf3151b6b96a842d18f0");
		
//		BoardVo2 vo = new BoardVo2();
//		vo.setTitle("updateupate1111");
//		vo.setNo("6065cf5a51b6b92f7cfbe597");
//		vo.setContents(" updateupate" );
//		update(vo);
//		findOne("6065cf5a51b6b92f7cfbe597");
//		getParentInfo("6065cf5a51b6b92f7cfbe597");
//		getMaxONo(2);
//		updateOrderNo(2, 1); // 부모의 order = 1 , 기존 oNo 2가 3으로 변경되어야 함
//		getMaxONo(2);
//		findOne("6065cf5a51b6b92f7cfbe597");
		
		
	}
	public static void getNewGNo() {
		dao.getNewGNo();
	}
	
	public static void insert() {
		BoardVo2 vo = new BoardVo2();
		vo.setTitle("0000");
		vo.setUserNo(5);
		vo.setContents(" 40000044" );
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		vo.setRegDate(df.format(new Date()));
		vo.setgNo(2);
		vo.setoNo(4);
		vo.setDepth(2);
		dao.insert(vo);
	}
	
	public static void findAll(PageVo vo) {
		dao.findAll(vo);
	}
	
	public static void findOne(String no) {
		dao.findOne(no);
	}
	
	public static void delete(String no) {
		dao.delete(no);
	}
	
	public static void update(BoardVo2 vo) {
		dao.update(vo);
	}
	
	public static BoardVo2 getParentInfo(String no) {
		return dao.getParentInfo(no);
	}
	
	public static void getMaxONo(int gNo) {
		dao.getMaxONo(gNo);
	}
	public static void updateOrderNo(int gNo, int oNo) {
		dao.updateOrderNo(gNo, oNo, 1);
	}
	
	public static boolean  isGetChild(BoardVo2 vo) {
		return dao.isGetChild(vo);
	}
}
