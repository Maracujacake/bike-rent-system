<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Opcoes</title>
</head>
					<!--  MOSTRA AS OPÇÕES POSSÍVEIS DE SEREM REALIZADAS A PARTIR DE CLIENTE -->


<body>
	<h2>Bem-vindo ao Sistema de Clientes</h2>
	<ul>
		<li><a href="${pageContext.request.contextPath}/cliente/list">Listar
				Todos os Clientes</a></li>
		<li><a
			href="${pageContext.request.contextPath}/cliente/buscarCliente">Buscar
				Cliente por ID</a></li>
	</ul>
</body>
</html>