<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="${pageContext.request.contextPath}/scripts/confirmDelete.js"></script> <!-- Atualize o caminho -->
    <title>Lista de Clientes</title>
</head>
<body class="bg-gray-100">
    							<!-- MOSTRA A LISTA DE TODOS OS CLIENTES DO BANCO DE DADOS  -->

    <div class="flex justify-center items-center h-screen">
        <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">Lista de Locações</h2>
            <c:if test="${not empty listaCliente}">
                <table class="w-full mx-auto">
                    <thead>
                        <tr class="bg-gray-100">
                            <th class="border border-gray-300 px-4 py-2">CNPJ Locadora</th>
                            <th class="border border-gray-300 px-4 py-2">Data de Locação</th>
                            <th class="border border-gray-300 px-4 py-2">Opções</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="locacao" items="${listaCliente}">
                            <tr class="border-b border-gray-300">
                                <td class="border border-gray-300 px-4 py-2">${locacao.cnpjLocadora}</td>
                                <td class="border border-gray-300 px-4 py-2">${locacao.registro}</td>
                                <td class="border border-gray-300 px-4 py-2">
                                    <a href="${pageContext.request.contextPath}/cliente/editarLocacao?id=${locacao.id}" class="text-blue-500 hover:underline">Update</a>
                                    <a href="${pageContext.request.contextPath}/cliente/deletarLocacao?id=${locacao.id}" class="text-red-500 hover:underline ml-2 delete-link">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty listaCliente}">
                <p>Nenhuma locação encontrada.</p>
            </c:if>

            <hr class="my-4">

            <div class="flex justify-around">
                <a href="${pageContext.request.contextPath}/locadora/list" class="text-blue-500 hover:underline">Ver locadoras</a>
                <a href="${pageContext.request.contextPath}/cliente/novoLocacao" class="text-green-500 hover:underline">Adicionar Locação</a>
            </div>
        </div>
    </div>
</body>
</html>
