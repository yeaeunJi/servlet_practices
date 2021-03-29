<html>
<body>

<h2>The XMLHttpRequest Object</h2>

<p id="demo"></p>
 
<script>
// [] 로 th이 될 내용 가져와서 dom 객체 생성
function makeTh(XML, table, lists){
 let parent = document.body;
 let tr = document.createElement("tr");
 
 for(var i=0; i<lists.length; i++){
 	let th = document.createElement("th");
 	let txt = document.createTextNode(lists[i]);
    
 	th.appendChild(txt);  
 	tr.appendChild(th);
 }
 table.appendChild(tr);
}

var xhttp, xmlDoc, txt, x, i;
xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
  if (this.readyState == 4 && this.status == 200) {
    xmlDoc = this.responseXML;
    var parent = document.body;
    var table = document.createElement("table");
    
    var lists = ["TITLE", "ARTIST", "COUNTRY", "COMPANY", "PRICE", "YEAR"] ;
    makeTh(xmlDoc, table, lists);
   

   x = xmlDoc.getElementsByTagName("CD");
   
    for (i = 0; i < x.length; i++) {
    	let tr = document.createElement("tr");
    
   		for(k = 0; k < lists.length; k++){
    		let td = document.createElement("td");
    		let txt = document.createTextNode(x[i].getElementsByTagName(lists[k])[0].childNodes[0].nodeValue );
    		td.appendChild(txt);
  			tr.appendChild(td);
  	  	} // inner for
      	table.appendChild(tr);
    } // outer for
    parent.appendChild(table);
  } // end if
};
xhttp.open("GET", "cd_catalog.xml", true);
xhttp.send();
</script>
</body>
</html>