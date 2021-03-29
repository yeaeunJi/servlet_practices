package com.saltlux.mysite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class JsonTest {

	public static void main(String[] args) throws ParseException, SQLException {
		// connection 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		JSONArray list = new JSONArray();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("jdbc 드라이버 로딩 완료");
			
			String url = "jdbc:mysql://localhost:3307/employees?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "bit");
			System.out.println("db connection 완료");
			String sql ="select first_name, last_name from employees limit 0, 10;"; // db에서 데이터를 받아와서 
			pstmt = conn.prepareStatement(sql);		
			result = pstmt.executeQuery();
			String firstName = "";
			String lastName = "";
			while(result.next()) {
				firstName = result.getString(1);
				lastName = result.getString(2);
				System.out.println("firstName_lastName:"+firstName+"_"+lastName);
				JSONObject obj = new JSONObject();		
				obj.put("firstName", firstName);
				obj.put("lastName", lastName);
				list.add(obj);
			}
			//String strjSON = obj.toJSONString(); // JSON String 
			//System.out.println("strJSON : "+strjSON);
			JSONObject resData = new JSONObject();		
			resData.put("employees", list);
			System.out.println("전송할 데이터 = "+resData.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("error - "+e);
		}finally {
			try {
				if(result!=null) result.close();
				if(conn!=null) conn.close();
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
				System.out.println("error-"+e);
			}
		}
	}
}
