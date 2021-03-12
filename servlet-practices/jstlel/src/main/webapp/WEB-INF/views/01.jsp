<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>값 받아보기</h1>
	- ${iVal } - ${lVal } - ${fVal } - ${bVal } - ${sVal }

	<h1>객체 받아보기</h1>
	- ${vo.no } 
	- ${vo.name }
	- ${obj } <!-- 에러는 안나지만 값이 나오지 않음 -->
	
	<h1>산술연산</h1>
	- ${iVal + 5 }
	
	<h1>관계연산</h1>
	- ${iVal > 11 }
	- ${obj == null }
	- ${empty obj }
	- ${not empty obj }
	
	<h1>논리연산</h1>
	- ${iVal == 10 && lVal < 1000 }
	- ${iVal > 10 || lVal > 1 }
	
	<!-- url에 쿼리스트링으로 넘어오는 값을 가져오는 방법 -->
	<!-- url 에 다음과 같이 접속해보기 	http://localhost:8080/jstlel/01?a=10 -->
	- ${param.a }
	- ${param.email }
	
	<h1>Map으로 값 받아보기</h1>
	- ${map.ival }
	- ${map.fval }

</body>
</html>