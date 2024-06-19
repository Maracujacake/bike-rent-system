<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Cliente</title>
</head>
<body>
							<!-- MOSTRA O RESULTADO DO CLIENTE A PARTIR DE UMA BUSCA POR ID  -->
    <h2>Detalhes do Cliente</h2>
    <c:if test="${not empty clienteEspecifico}">
        <p>ID: ${clienteEspecifico.id}</p>
        <p>Nome: ${clienteEspecifico.nome}</p>
        <p>Data de Nascimento: ${clienteEspecifico.dataNascimento}</p>
        <!-- Adicione outros campos conforme necessário -->
    </c:if>
    <c:if test="${empty clienteEspecifico}">
        <p>Cliente não encontrado.</p>
    </c:if>
</body>
</html>
