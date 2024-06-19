<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buscar Cliente</title>
</head>
<body>

							<!--  FORMULARIO QUE PERMITE A BUSCA DO CLIENTE PELO ID  -->
    <h2>Buscar Cliente</h2>
    <form action="${pageContext.request.contextPath}/cliente/procurar" method="get">
        <label for="id">ID do Cliente:</label>
        <input type="text" id="id" name="id">
        <input type="submit" value="Buscar">
    </form>
</body>
</html>
