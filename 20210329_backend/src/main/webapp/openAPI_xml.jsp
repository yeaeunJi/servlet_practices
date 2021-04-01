<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"	src="${pageContext.request.contextPath }/js/jquery/jquery-1.9.0.js"></script>
<meta charset="UTF-8">
<title>OpenAPI</title>
<script>
	var xmlDoc, txt, xhttp;
	var encodingKey = 'xYOCwju4IsIBn%2Flaq9iMhQBy1RY78fDssJFZW1Nq%2BGATGrcSuS1e3TY%2Br47meFsvYCRJgn7NSZrpDVKM%2B99xNw%3D%3D';
	var decodingkey = 'xYOCwju4IsIBn/laq9iMhQBy1RY78fDssJFZW1Nq+GATGrcSuS1e3TY+r47meFsvYCRJgn7NSZrpDVKM+99xNw==';

	var url = 'http://apis.data.go.kr/B551182/codeInfoService/getSpclMdlrtCodeList'; /*URL*/
	var queryParams = '?' + encodeURIComponent('ServiceKey') + '='
			+ encodeURIComponent(encodingKey);
	queryParams += '&' + encodeURIComponent('srchTpCd') + '='
			+ encodeURIComponent('01');
	$.ajax({
		//dataType : "jsonp",
		url : url,
		type : "GET",
		data : '',
		dataType : "xml",
		success : function(data) {
					callback(data.responseText);
		}
	});
	
	function makeTh(XML, table, lists) {
		let parent = document.body;
		let tr = document.createElement("tr");

		for (var i = 0; i < lists.length; i++) {
			let th = document.createElement("th");
			let txt = document.createTextNode(lists[i]);

			th.appendChild(txt);
			tr.appendChild(th);
		}
		table.appendChild(tr);
	}
	
	function callback(data) {
		var parser = new DOMParser();
		xmlDoc = parser.parseFromString(data, "text/xml");
			var parent = document.body;
		    var table = document.createElement("table");		   
			var lists = [ "srchCd", "srchCdNm", "srchCdCmmt" ];
			console.log(xmlDoc);
			makeTh(xmlDoc, table, lists);

			x = xmlDoc.getElementsByTagName("item");
			console.log(x);
			for (i = 0; i < x.length; i++) {
				let tr = document.createElement("tr");

				for (k = 0; k < lists.length; k++) {
					let td = document.createElement("td");
					let txt = document.createTextNode(x[i].getElementsByTagName(lists[k])[0].childNodes[0].nodeValue);
					td.appendChild(txt);
					tr.appendChild(td);
				} // inner for
				table.appendChild(tr);
			} // outer for
			parent.appendChild(table);
	}
</script>
</head>
<body>
	<p></p>
</body>
</html>

