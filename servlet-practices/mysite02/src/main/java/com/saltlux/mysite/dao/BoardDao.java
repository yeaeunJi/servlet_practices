package com.saltlux.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.saltlux.mysite.vo.BoardVo;

public class BoardDao {

	// insert 
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "insert"	+ 
					"	into board"	+
					"   values (null, ?, ?,?, ?, now());";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3,  vo.getWriter());
			pstmt.setLong(4, vo.getCount());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<BoardVo> findAll(int curpage, int shownum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();
			String sql = "select no, title, writer, count, reg_date "+
					" from board order by no desc ";
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();

			while(result.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(result.getLong(1));
				vo.setTitle(result.getString(2));
				vo.setWriter(result.getString(3));
				vo.setCount(result.getLong(4));
				vo.setRegDate(result.getString(5));

				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null)	pstmt.close();
				if(conn!=null) conn.close();
				if(result!=null) result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<BoardVo> paging(int curpage, int shownum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet result = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();
//			String sql = "select count(*) from board;"; 
//			pstmt2 = conn.prepareStatement(sql);
//			result = pstmt2.executeQuery();
//			int total = result.getInt(1);
			
			String sql = "select no, title, writer, count, reg_date "+
					" from board order by no desc limit ?, ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (curpage - 1)*shownum);
			pstmt.setInt(2, shownum);
			System.out.println(pstmt.toString());
			result = pstmt.executeQuery();

			while(result.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(result.getLong(1));
				vo.setTitle(result.getString(2));
				vo.setWriter(result.getString(3));
				vo.setCount(result.getLong(4));
				vo.setRegDate(result.getString(5));

				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null)	pstmt.close();
				if(conn!=null) conn.close();
				if(result!=null) result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<BoardVo> search(String kwd){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();
			String sql =  "select no, title, writer,  count, reg_date from board "+
					" where contents like ? or title like ?; ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			result = pstmt.executeQuery();

			while(result.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(result.getLong(1));
				vo.setTitle(result.getString(2));
				vo.setWriter(result.getString(3));
				vo.setCount(result.getLong(4));
				vo.setRegDate(result.getString(5));

				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null)	pstmt.close();
				if(conn!=null) conn.close();
				if(result!=null) result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// findOne
	public BoardVo findOne(Long no){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		BoardVo vo = new BoardVo();

		try {
			conn = getConnection();
			String sql = "select no, title, contents, writer, count, reg_date from board " 
					+" where no = ? ;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			result = pstmt.executeQuery();
			
			
			result.next();
			vo.setNo(result.getLong(1));
			vo.setTitle(result.getString(2));
			vo.setContents(result.getString(3));
			vo.setWriter(result.getString(4));
			vo.setCount(result.getLong(5));
			vo.setRegDate(result.getString(6));
		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null)	pstmt.close();
				if(conn!=null) conn.close();
				if(result!=null) result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}

	// update
	public boolean update(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			conn = getConnection();
			
			sql = "update board set title=?, contents=? where no = ?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3,  vo.getNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	// update count
	public boolean updateCount(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			conn = getConnection();
			
			sql = "update board set count=? where no = ?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,  vo.getCount());
			pstmt.setLong(2,  vo.getNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public boolean delete(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "delete from board where no = ? and writer=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,  vo.getNo());
			pstmt.setString(2, vo.getWriter());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	// db connection method
	public Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("error-"+e);
		}
		return conn;
	}
}
