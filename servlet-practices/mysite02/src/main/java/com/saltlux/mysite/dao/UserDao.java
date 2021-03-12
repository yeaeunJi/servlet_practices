package com.saltlux.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public UserVo findByNo(Long no ) {
		UserVo userVo = null ;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			conn = getConnection();
			String sql ="select no, name, email, gender from user where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			result = pstmt.executeQuery();
			
			if(result.next()) {
				String name = result.getString(2);
				String email = result.getString(3);
				String gender = result.getString(4);
				userVo = new UserVo();
				userVo.setName(name);
				userVo.setNo(no);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			
		} catch(SQLException e) {
			System.out.println("error-"+e);
			
		} finally {
			try {
				if(result!=null) result.close();
				if(conn!=null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				System.out.println("error-"+e);
			}
			
		}
		return userVo;
	}
	
	public UserVo findByEmailAndPassword(UserVo vo ) {
		UserVo userVo = null ;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		try {
			conn = getConnection();
			String sql ="select no, name from user where email=? and password=?;";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());

			result = pstmt.executeQuery();
			
			if(result.next()) {
				Long no = result.getLong(1);
				String name = result.getString(2);
				
				userVo = new UserVo();
				userVo.setName(name);
				userVo.setNo(no);
			}
			
		} catch(SQLException e) {
			System.out.println("error-"+e);
			
		} finally {
			try {
				if(result!=null) result.close();
				if(conn!=null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				System.out.println("error-"+e);
			}
			
		}
		return userVo;
	}
	
	public boolean updateNameAndGender(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update user set name = ?, gender=? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
//			pstmt.setString(3, vo.getPassword());
			pstmt.setString(2, vo.getGender());
			pstmt.setLong(3, vo.getNo());

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
	
	public boolean updateAll(UserVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update user set name = ?, gender=?, password=? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setString(3, vo.getPassword());
			pstmt.setLong(4, vo.getNo());

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
