package com.saltlux.mysite.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Mysql {
	private static Mysql mysql ;
	private static Connection normalConn = null;
	private static Connection replicatedConn = null;
	public static boolean useReplicated = true;

	static {
		try {
			mysql =  new Mysql();
			
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

	} // static initializer(자바 프로그램 실행 시 단한번 호출됨)

	// private 접근제한자로 막아놓은 생성자로, 이 클래스 외부에서는 객체를 생성할 수 없음
	// 하지만 내부에서는 호출  가능
	private Mysql() throws SQLException {	
		System.out.println("***************** Mysql class 생성자 *****************");
		if (! useReplicated) normalConnect();
		else replicatedConnect();
	} ; // Constructor

	public void normalConnect() throws SQLException {
		normalConn = null;
		try {
			//System.out.println("***************** normalDriver 로딩 시작 *****************");
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			//System.out.println("***************** normalDriver 로딩 완료 *****************");
			normalConn = DriverManager.getConnection(url, "repluser", "replpw");
			System.out.println("***************** normal connection 완료 *****************");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("error-"+e);
		}
	}

	public void replicatedConnect() throws SQLException {
		replicatedConn = null;
		try {
			//System.out.println("***************** ReplicationDriver 로딩 시작 *****************");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties props = new Properties();
			//System.out.println("***************** ReplicationDriver 로딩 완료 *****************");
			props.put("autoReconnect", "true");
			props.put("user", "repluser");
			props.put("password", "replpw");
		    props.put("roundRobinLoadBalance", "true");
			replicatedConn  = DriverManager.getConnection("jdbc:mysql:replication://localhost:3306, localhost:3307/webdb?characterEncoding=utf8&serverTimezone=UTC", props);
			System.out.println("***************** Replication DB 연결 완료 *****************");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("replicatedConnect ClassNotFoundException-"+e);
		}
		catch (Exception e) {
			System.out.println("error ::::::::::::::::::: "+e.getLocalizedMessage());
			System.out.println("replicatedConnect error-"+e);
		}
	}

	public static Mysql getInstance() {
		return mysql;
	}

	private static Connection getNormalConnection() {		
		return normalConn;
	}

	private  static Connection getReplicatedConnection() {
		return replicatedConn;
	}
	
	// db connection method
	public static Connection getConnection() throws SQLException {
		if (useReplicated) {
			return getReplicatedConnection();
		}else {
			return getNormalConnection();
		}
	}
}
