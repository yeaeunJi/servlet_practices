package com.saltlux.emaillist.dao.test;

import java.util.List;
import com.saltlux.emaillist.dao.EmaillistDao;
import com.saltlux.emaillist.vo.EmaillistVo;

// EmaillistDao를 테스트하는 클래스
// 실제로 테스트 프레임 워크 사용시에는 사용법이 다름 
public class EmaillistDaoTest {

	public static void main(String[] args) {
		testFindAll();
	}


	public static void testFindAll() {
		List<EmaillistVo> list = new EmaillistDao().findAll();
		for(EmaillistVo vo : list) {
			System.out.println(vo);
		}
	}

}
