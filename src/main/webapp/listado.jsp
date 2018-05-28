<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>
			Hola
			<%=request.getParameter("nombre")%>
		</h1>
		<p>
			Tu clave es:
			<%=request.getParameter("clave")%>
		</p>
	
	</body>
</html>