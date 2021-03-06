<!DOCTYPE html>
<html>
<body>

<h2>Make a table based on JSON data.</h2>

<p id="demo"></p>

<script>
var obj, dbParam, xmlhttp, myObj, x, txt = "";

xmlhttp = new XMLHttpRequest();
xmlhttp.onreadystatechange = function() {
  if (this.readyState == 4 && this.status == 200) {
    myObj = JSON.parse(this.responseText);
  
    txt += "<table border='1'>";
    for (x in myObj.name) {
      txt += "<tr><td>" + myObj.name[x] + "</td></tr>";
    }
    txt += "</table>"  ;  
    document.getElementById("demo").innerHTML = txt;
  }
};
xmlhttp.open("GET", "sample.json", true);
xmlhttp.send();
</script>

</body>
</html>