<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<h1>Formulario</h1>
		<form action="login" method="post">
			<fieldset>
				<legend>Formulario de login</legend>
				<label for="nom">Nombre:</label> 
				<input type="text" name="nombre" id="nom"><br/> 
				<label for="clave">Password:</label> 
				<input type="password" name="clave" id="clave"><br/>
	
			</fieldset>
			<input type="submit" value="Enviar">
	
		</form>
	</body>
</html>