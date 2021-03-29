<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, org.json.simple.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<%
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet result = null;
	JSONArray list = new JSONArray();
	JSONObject resData = new JSONObject();
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3307/employees?characterEncoding=utf8&serverTimezone=UTC";
		conn = DriverManager.getConnection(url, "root", "bit");

		String sql = "select first_name, last_name from employees limit 0, 10;"; // db에서 데이터를 받아와서 
		pstmt = conn.prepareStatement(sql);
		result = pstmt.executeQuery();
		String firstName = "";
		String lastName = "";

		while (result.next()) {
			firstName = result.getString(1);
			lastName = result.getString(2);

			JSONObject obj = new JSONObject();
			obj.put("firstName", firstName);
			obj.put("lastName", lastName);
			list.add(obj);
		}
		resData.put("employees", list); // db에서 조회한 firstName, lastName 데이터를 json 형식으로 가져옴

	} catch (ClassNotFoundException e) {
		System.out.println("error - " + e);
	} finally {
		try {
			if (result != null)	result.close();
			if (conn != null)	conn.close();
			if (pstmt != null)	pstmt.close();
		} catch (SQLException e) {
			System.out.println("error-" + e);
		}
	
	}
	%>
<script>
//  json 형식으로 가져온 데이터를 화면에 뿌리기
// [] 로 th이 될 내용 가져와서 dom 객체 생성
function makeTh(table, lists){
 let parent = document.body;
 let tr = document.createElement("tr");
 
 for(let i=0; i<lists.length; i++){
 	let th = document.createElement("th");
 	let txt = document.createTextNode(lists[i]);
    
 	th.appendChild(txt);  
 	tr.appendChild(th);
 }
 table.appendChild(tr);
}

window.onload = function(){
	var resData = JSON.parse('<%=resData%>');
    var parent = document.body;
    var table = document.createElement("table");
    
    var lists = ["firstName", "lastName"] ;
    makeTh(table, lists);

    for (x in resData.employees) {
    	let tr = document.createElement("tr");
    		let td = document.createElement("td");
    		let txt = document.createTextNode(resData.employees[x].firstName);
    		td.appendChild(txt);
  			tr.appendChild(td);
  			table.appendChild(tr);
  			
    		td = document.createElement("td");
    		txt = document.createTextNode(resData.employees[x].lastName);
    		td.appendChild(txt);
  			tr.appendChild(td);
    		table.appendChild(tr);
  	  	} // inner for
    parent.appendChild(table);
}
</script>
</head>
<body>

</body>
