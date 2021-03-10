package com.saltlux.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saltlux.emaillist.vo.EmaillistVo;

public class EmaillistDao {

	public List<EmaillistVo> findAll(){
		// ArrayList를 사용한 이유 : 모든 이메일 리스트를 조회하는 것이므로 데이터가 연속된 메모리주소에 위치하여
		// 순회기능을 가장 잘 사용할 수 있는 ArrayList를 사용
		List<EmaillistVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null; 
		
		//jdbc 연결
		try { // spring 사용 시 해당 구문은 런타임 에러를 발생시킴
			// 이때 해당 에러는 dao -> jsp -> 서블릿 -> tomcat -> java로 올라가 뱉어냄
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			// 3. 쿼리 객체 생성
			String sql = "select no, first_name, last_name, email from emaillist order by no desc;";
			pstmt = conn.prepareStatement(sql);

			// 4. 바인딩

			// 5. SQL문 실행
			result = pstmt.executeQuery();

			// 6. 데이터 가져오기
			while(result.next()) {
				Long no = result.getLong(1); // 1부터 시작
				String first_name = result.getString(2);
				String last_name = result.getString(3);
				String email = result.getString(4);

				EmaillistVo vo = new EmaillistVo();
				vo.setNo(no);
				vo.setFirst_name(first_name);
				vo.setLast_name(last_name);
				vo.setEmail(email);

				list.add(vo);
			}

		} catch (ClassNotFoundException e) { // 현재는 컴파일 에러
			// 1. 사과
			// 2. 디버깅 로그
			System.out.println("error:"+e);
			// 3. 안전하게 종료
			return list;
		} catch (SQLException e) { // 현재는 컴파일 에러
			// 1. 사과
			// 2. 디버깅 로그
			System.out.println("error:"+e);
			// 3. 안전하게 종료
		} finally { // 자원 정리
			try {
				if(result != null) result.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
