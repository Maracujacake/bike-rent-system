<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
<fmt:setBundle basename="message" /> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="${pageContext.request.contextPath}/scripts/confirmDelete.js"></script> <!-- Atualize o caminho -->
    <title><fmt:message key="cliente.lista" /></title>
</head>
<body class="bg-gray-100">
    							<!-- MOSTRA A LISTA DE TODOS OS CLIENTES DO BANCO DE DADOS  -->

    <div class="flex justify-center items-center h-screen">
        <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
            <h2 class="text-2xl font-bold text-gray-800 mb-4"><fmt:message key="cliente.lista" /></h2>
            <c:if test="${not empty listaCliente}">
                <table class="w-full mx-auto">
                    <thead>
                        <tr class="bg-gray-100">
                            <th class="border border-gray-300 px-4 py-2"><fmt:message key="nome" /></th>
                            <th class="border border-gray-300 px-4 py-2"><fmt:message key="birthdate" /></th>
                            <th class="border border-gray-300 px-4 py-2"><fmt:message key="sexo" /></th>
                            <th class="border border-gray-300 px-4 py-2">ID</th>
                            <th class="border border-gray-300 px-4 py-2"><fmt:message key="opcoes" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cliente" items="${listaCliente}">
                            <tr class="border-b border-gray-300">
                                <td class="border border-gray-300 px-4 py-2">${cliente.nome}</td>
                                <td class="border border-gray-300 px-4 py-2">${cliente.dataNascimento}</td>
                                <td class="border border-gray-300 px-4 py-2">${cliente.sexo}</td>
                                <td class="border border-gray-300 px-4 py-2">${cliente.id}</td>
                                <td class="border border-gray-300 px-4 py-2">
                                    <a href="${pageContext.request.contextPath}/admin/editarCliente?id=${cliente.id}" class="text-blue-500 hover:underline">Update</a>
                                    <a href="${pageContext.request.contextPath}/admin/deletarCliente?id=${cliente.id}" class="text-red-500 hover:underline ml-2 delete-link">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty listaCliente}">
                <p><fmt:message key="cliente.notFound" /></p>
            </c:if>

            <hr class="my-4">

            <div class="flex justify-around">
                <a href="${pageContext.request.contextPath}/cliente/buscarCliente" class="text-blue-500 hover:underline"><fmt:message key="cliente.getById" /></a>
                <a href="${pageContext.request.contextPath}/cliente/novo" class="text-green-500 hover:underline"><fmt:message key="cliente.add" /></a>
            </div>
        </div>
    </div>
</body>
</html>
