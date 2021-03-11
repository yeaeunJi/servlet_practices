package com.saltlux.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.saltlux.mysite.vo.UserVo;

public class UserDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("error - "+e);
		}
		return conn;
	}


	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "insert " +
					"into user " +
					"values (null, ?, ?, ?, ?,now());";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			int count = pstmt.executeUpdate();
			result = count == 1;
			return result;
		} catch(SQLException e) {
			System.out.println("error-"+e);
			return result;
		} finally {
			try {
				if(conn!=null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				System.out.println("error-"+e);
			}
			
		}
	}
}
