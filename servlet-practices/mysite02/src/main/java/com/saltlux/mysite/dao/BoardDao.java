package com.saltlux.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.saltlux.mysite.vo.BoardVo;
import com.saltlux.mysite.vo.PageVo;

public class BoardDao {

	private  Long  getNewGNo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		Long max = 0L;
		try {
			conn = getConnection();
			//conn.setReadOnly(true);
			String sql = "select ifnull(max(group_no),0)+1 as max from board;  ";
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();

			result.next();
			max = result.getLong(1); 

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
		return max;
	}

	// insert 
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			//conn.setReadOnly(false);
			String sql = "insert"	+ 
					"	into board (no, title, contents, user_no, count, reg_date, group_no, order_no, depth, del_flag) "	+
					"   values (null, ?, ?,?, 0, now(), ?, 1 , 0, 'F');";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3,  vo.getUserNo());
			pstmt.setLong(4, getNewGNo());

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

	public boolean replyInsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			//conn.setReadOnly(false);
			String sql = "insert"	+ 
					"	into board (no, title, contents, user_no, count, reg_date, group_no, order_no, depth, del_flag) "	+
					"   values (null, ?, ?,?, 0, now(), ?, ? , ?, 'F');";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3,  vo.getUserNo());
			pstmt.setLong(4, vo.getgNo());
			pstmt.setLong(5,  vo.getoNo());
			pstmt.setLong(6, vo.getDepth());
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


	public boolean updateOrderNo(Long gNo, Long oNo, Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "";
		try {
			conn = getConnection();
			//conn.setReadOnly(false);
			sql = "update board set order_no= order_no+1 where group_no = ? and order_no > ? and no > 0;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, oNo);
			int count = pstmt.executeUpdate();
			result = count >= 1;

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

	public List<BoardVo> findAll(PageVo pageVo, String keyword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();
			//conn.setReadOnly(true);
			String sql = "select no, title, (select name from user where no=board.user_no) as writer, user_no, count, reg_date, group_no, order_no, depth, del_flag  "
					+ "from board where contents like ? or title like ? order by group_no desc, order_no limit ?,?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,"%"+keyword+"%" );
			pstmt.setString(2,"%"+keyword+"%" );
			pstmt.setLong(3, pageVo.getStart());
			pstmt.setLong(4, pageVo.getShowNum());
			result = pstmt.executeQuery();
			while(result.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(result.getLong(1));
				vo.setTitle(result.getString(2));
				vo.setWriter(result.getString(3));
				vo.setUserNo(result.getLong(4));
				vo.setCount(result.getLong(5));
				vo.setRegDate(result.getString(6));
				vo.setgNo(result.getLong(7));
				vo.setoNo(result.getLong(8));
				vo.setDepth(result.getLong(9));
				vo.setDelFlag(result.getString(10).charAt(0));
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

	public PageVo paging(Long shownum, String keyword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		PageVo page = null;
		try {
			conn = getConnection();

			//conn.setReadOnly(true);
			String sql =  "SELECT count(no) as total, CASE WHEN ceiling(count(no)/?)  = 0 THEN 1 ELSE ceiling(count(no)/?)  END AS totalpage FROM board where contents like ? or title like ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,shownum );
			pstmt.setLong(2,shownum );
			pstmt.setString(3,"%"+keyword+"%" );
			pstmt.setString(4,"%"+keyword+"%" );
			//System.out.println(pstmt.toString());
			result = pstmt.executeQuery();

			Long totalPage = 1L;
			Long totalCount = 0L;
			page = new PageVo();

			while(result.next()) {
				totalCount = result.getLong(1);
				totalPage = result.getLong(2);
			}

			page.setTotalCount(totalCount);
			page.setTotal(totalPage);
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
		return page;
	}

	public List<BoardVo> search(String keyword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();
			//conn.setReadOnly(true);
			String sql =  "select no, title, (select name from user where no=board.user_no) as writer, user_no, count, reg_date, group_no, order_no, depth,  del_flag  from board "+
					" where contents like ? or title like ?; ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setString(2, "%"+keyword+"%");
			result = pstmt.executeQuery();

			while(result.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(result.getLong(1));
				vo.setTitle(result.getString(2));
				vo.setWriter(result.getString(3));
				vo.setUserNo(result.getLong(4));
				vo.setCount(result.getLong(5));
				vo.setRegDate(result.getString(6));
				vo.setgNo(result.getLong(7));
				vo.setoNo(result.getLong(8));
				vo.setDepth(result.getLong(9));
				vo.setDelFlag(result.getString(10).charAt(0));

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
			//conn.setReadOnly(true);
			String sql = "select no, title, contents, user_no, (select name from user where no=board.user_no) as writer, count, reg_date, depth, group_no, order_no from board " 
					+" where no = ? and del_flag = 'F' ;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			result = pstmt.executeQuery();


			result.next();
			vo.setNo(result.getLong(1));
			vo.setTitle(result.getString(2));
			vo.setContents(result.getString(3));
			vo.setUserNo(result.getLong(4));
			vo.setWriter(result.getString(5));
			vo.setCount(result.getLong(6));
			vo.setRegDate(result.getString(7));
			vo.setDepth(result.getLong(8));
			vo.setgNo(result.getLong(9));
			vo.setoNo(result.getLong(10));
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
			//conn.setReadOnly(false);
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
			//conn.setReadOnly(false);
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
			//conn.setReadOnly(false);
			String sql = "delete from board where no = ? ;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1,  vo.getNo());

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

	public boolean getChildCount(BoardVo vo) {
		boolean result = true;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			//conn.setReadOnly(true);
			String sql = "select count(*)  from board where depth > ? and order_no = ?+1 and group_no=?;";
			pstmt = conn.prepareStatement(sql);
			//pstmt.setLong(1,  vo.getNo());
			pstmt.setLong(1, vo.getDepth());
			pstmt.setLong(2, vo.getoNo());
			pstmt.setLong(3, vo.getgNo());
			rs = pstmt.executeQuery();

			rs.next();
			int count = 0;
			count = rs.getInt(1);
			result = count>0?true:false;

		} catch (SQLException e) {
			System.out.println("error-"+e);
		} finally {
			try {
				if(pstmt!=null)	pstmt.close();
				if(conn!=null) conn.close();
				if(rs!=null) rs.close();
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

	// Mysql DB 이중화 사용한 connection
//	public Connection getConnection()  throws SQLException {
//		Connection conn = null;
//		try {
//			System.out.println("+++++++ DB 연결 시작 +++++++");
////			Class.forName("com.mysql.jdbc.ReplicationDriver");
//		    Properties props = new Properties();
//
//		    // We want this for failover on the replicas
//		    props.put("autoReconnect", "true");
//
//		    // We want to load balance between the replicas
//		    props.put("roundRobinLoadBalance", "true");
//
//		    props.put("user", "repluser");
//		    props.put("password", "replpw");
//
//			Class.forName("com.mysql.jdbc.ReplicationDriver");
//			System.out.println("- 드라이브 로딩 완료 ");
//			conn = DriverManager.getConnection("jdbc:mysql:replication://172.17.0.2:3306, 172.17.0.3:3306/webdb?characterEncoding=utf8&serverTimezone=UTC", props);
//			System.out.println("+++++++ DB 연결 완료 +++++++");
//	} catch (ClassNotFoundException e) {
//		System.out.println("+++++++ DB 연결 실패 +++++++"); 
//		e.printStackTrace();
//	}
//	return conn;
//}




public BoardVo getParentInfo(Long no) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet result = null;
	BoardVo vo = new BoardVo();

	try {
		conn = getConnection();
		//conn.setReadOnly(true);
		String sql = "select group_no, order_no, depth from board where no=?;";
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, no);
		result = pstmt.executeQuery();

		result.next();
		vo.setgNo(result.getLong(1));
		vo.setoNo(result.getLong(2));
		vo.setDepth(result.getLong(3));

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

public Long getMaxONo(Long gNo) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet result = null;
	Long max = 0L;
	try {
		conn = getConnection();
		//conn.setReadOnly(true);
		String sql = "select ifnull(max(order_no),0)+1 as max from board where group_no = ?;  ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, gNo);
		result = pstmt.executeQuery();

		result.next();
		max = result.getLong(1); 

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
	return max;
}
}