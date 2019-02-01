<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<font color="red">${message}</font>
<body>
<form action="addStudent" method="post">
Student Id : <input type="text" name="stdId"/></br>
Name : <input type="text" name="name"/></br>
Collegeg : <input type="text" name="collegeName"/></br>
<input type="submit" value="Add Student"/>
</form>

<form action="showScore">
	<input type="submit" value="Show Score"/>
</form>
</body>
</html>