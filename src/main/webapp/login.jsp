<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	.error { color:red;}
</style>
</head>
<body>
	<!-- comprobar y pintar errores -->

	<c:if test="${!empty errores }">
		<c:forEach items="${errores }" var="error">

			<c:if test="${error.key =='login'}">
				<p class="error">El nombre no puede quedar en blanco</p>

			</c:if>
			<c:if test="${error.key =='password'}">
				<p class="error">La clave no puede quedar en blanco</p>

			</c:if>
			<c:if test="${error.key =='loginFailed'}">
				<p class="error">Usuario y/o contraseña invalidos</p>

			</c:if>
		</c:forEach>
	</c:if>
	<h1>Formulario</h1>
	<form action="login" method="post">
		<fieldset>
			<legend>Formulario de login</legend>
			<label for="nom">Nombre:</label> 
			<input type="text" name="login" id="nom"><br/> 
			<label for="clave">Password:</label> 
			<input type="password" name="password" id="clave"><br/>

		</fieldset>
		<input type="submit" value="Enviar">

	</form>
</body>
</html>