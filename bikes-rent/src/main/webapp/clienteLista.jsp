<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Clientes</title>
</head>
<body>
    <h2>Lista de Clientes</h2>
    <c:if test="${not empty listaCliente}"> <!--  comunicacao com o arquivo .jsp  -->
        <ul>
            <c:forEach var="cliente" items="${listaCliente}">
                <li>${cliente.nome}</li>
                <li>${cliente.dataNascimento}</li>
                <li>${cliente.sexo}</li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty listaCliente}">
        <p>Nenhum cliente encontrado.</p>
    </c:if>
</body>
</html>
