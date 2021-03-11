package com.saltlux.emaillist.dao.test;

import java.util.List;
import com.saltlux.emaillist.dao.EmaillistDao;
import com.saltlux.emaillist.vo.EmaillistVo;

// EmaillistDao를 테스트하는 클래스
// 실제로 테스트 프레임 워크 사용시에는 사용법이 다름 
public class EmaillistDaoTest {

	public static void main(String[] args) {
		// insertTest();
		testInsert();
		
		// findall test
		testFindAll();
	}

	public static void testFindAll() {
		List<EmaillistVo> list = new EmaillistDao().findAll();
		for(EmaillistVo vo : list) {
			System.out.println(vo);
		}
	}
	
	public static void testInsert() {
		EmaillistVo vo  = new EmaillistVo();
		vo.setFirst_name("마");
		vo.setLast_name("이클");
		vo.setEmail("2m@naver.com");
		
		new EmaillistDao().insert(vo);
	}

}
