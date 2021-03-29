package com.saltlux.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saltlux.mysite.vo.GuestbookVo;


public class GuestbookDao {

	public boolean insert(GuestbookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			//conn.setReadOnly(false);
			String sql = "insert into guestbook values(null, ? , ?, ?, date_format(now(), '%Y-%m-%d %H:%i:%s'));";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			int count = pstmt.executeUpdate() ; 
			result = count == 1 ? true:false;

		} catch (SQLException e) { 
			System.out.println("error:"+e);
		} finally { // 자원 정리
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<GuestbookVo> findAll(){
		List<GuestbookVo> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null; 

		try {
			conn = getConnection();
			//conn.setReadOnly(true);
			String sql = "select no, name, password, contents, date_format(reg_date, '%Y-%m-%d %H:%i:%s') as reg_date from guestbook order by no desc ;";
			pstmt = conn.prepareStatement(sql);

			result = pstmt.executeQuery();

			while(result.next()) {
				Long no = result.getLong(1); // 1부터 시작
				String name = result.getString(2);
				String password = result.getString(3);
				String contents = result.getString(4);
				String regDate = result.getString(5);
				GuestbookVo vo = new GuestbookVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setPassword(password);
				vo.setContents(contents);
				vo.setRegDate(regDate);
				list.add(vo);
			}

		} catch (SQLException e) { 
			System.out.println("error:"+e);
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

	public boolean delete(GuestbookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			//conn.setReadOnly(false);

			String sql = "delete from guestbook where no=? and password=?;";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());

			int count = pstmt.executeUpdate() ;

			result = count == 1 ? true:false;

		} catch (SQLException e) { 
			System.out.println("error:"+e);
		} finally { // 자원 정리
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("error - "+e);
		}
		return conn;
	}
	
	// Mysql DB 이중화 사용한 connection
//	public Connection getConnection()  throws SQLException {
//		Connection conn = null;
//		try {
//			System.out.println("+++++++ DB 연결 시작 +++++++");
////			Class.forName("com.mysql.jdbc.ReplicationDriver");
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("- 드라이브 로딩 완료 ");
//			conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306, 172.17.0.3:3306/webdb", "repluser", "replpw");
//			System.out.println("+++++++ DB 연결 완료 +++++++");
//	} catch (ClassNotFoundException e) {
//		// TODO Auto-generated catch block
//		System.out.println("+++++++ DB 연결 실패 +++++++");
//		e.printStackTrace();
//	}
//	return conn;
//}

}
