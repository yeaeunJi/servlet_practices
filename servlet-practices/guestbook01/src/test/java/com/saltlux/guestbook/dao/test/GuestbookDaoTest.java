package com.saltlux.guestbook.dao.test;

import java.util.List;

import com.saltlux.guestbook.dao.GuestbookDao;
import com.saltlux.guestbook.vo.GuestbookVo;


public class GuestbookDaoTest {

	public static void main(String[] args) {
		// insertTest();
//		testInsert();
		
		// findall test
		testFindAll();
		
//		testDelete();
//		testFindAll();
	}

	public static void testFindAll() {
		List<GuestbookVo> list = new GuestbookDao().findAll();
		for(GuestbookVo vo : list) {
			System.out.println(vo);
		}
	}
	
	public static void testInsert() {
		GuestbookVo vo  = new GuestbookVo();
		vo.setName("마이클");
		vo.setPassword("1234");
		vo.setContents("내용이 이렇게 \n 많다니");
		vo.setRegDate("2021-03-10 17:05:00");
		new GuestbookDao().insert(vo);
	}
	
	public static void testDelete() {
		GuestbookVo vo  = new GuestbookVo();
		vo.setNo(Long.parseLong("1"));
		vo.setPassword("1234");
		new GuestbookDao().delete(vo);
	}

}
