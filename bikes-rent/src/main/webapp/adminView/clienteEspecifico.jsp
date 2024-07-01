<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Cliente</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <!-- MOSTRA O RESULTADO DO CLIENTE A PARTIR DE UMA BUSCA POR ID  -->
    <div class="flex justify-center items-center h-screen">
        <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">Informações do cliente:</h2>
                <c:if test="${not empty clienteEspecifico}">
                    <p>ID: ${clienteEspecifico.id}</p>
                    <p>Nome: ${clienteEspecifico.nome}</p>
                    <p>Email: ${clienteEspecifico.email}</p>
                    <p>Data de Nascimento: ${clienteEspecifico.dataNascimento}</p>
                </c:if>
                <c:if test="${empty clienteEspecifico}">
                    <p>Cliente não encontrado.</p>
                </c:if>
                <a href="${pageContext.request.contextPath}/admin/listCliente" class="block px-4 py-2 my-4 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">Voltar para lista de clientes</a>
        </div>
    </div>
    
</body>
</html>
