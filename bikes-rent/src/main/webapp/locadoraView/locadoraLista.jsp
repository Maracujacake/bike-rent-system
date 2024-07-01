<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <script src="https://cdn.tailwindcss.com"></script>
            <script src="${pageContext.request.contextPath}/scripts/clientNotFound.js"></script>
            <!-- Atualize o caminho -->
            <title>Lista de Locadoras</title>
        </head>

        <body class="bg-gray-100">
            <!-- MOSTRA A LISTA DE TODOS OS locadoraS DO BANCO DE DADOS  -->

            <div class="flex justify-center items-center h-screen">
                <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Lista de Locadoras</h2>
                    <c:if test="${not empty listaLocadora}">
                        <table class="w-full mx-auto">
                            <thead>
                                <tr class="bg-gray-100">
                                    <th class="border border-gray-300 px-4 py-2">Nome</th>
                                    <th class="border border-gray-300 px-4 py-2">Cidade</th>
                                    <th class="border border-gray-300 px-4 py-2">CNPJ</th>
                                    <th class="border border-gray-300 px-4 py-2">ID</th>
                                    <th class="border border-gray-300 px-4 py-2">Email</th>
                                    <th class="border border-gray-300 px-4 py-2">Opções</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="locadora" items="${listaLocadora}">
                                    <tr class="border-b border-gray-300">
                                        <td class="border border-gray-300 px-4 py-2">${locadora.nome}</td>
                                        <td class="border border-gray-300 px-4 py-2">${locadora.cidade}</td>
                                        <td class="border border-gray-300 px-4 py-2">${locadora.cnpj}</td>
                                        <td class="border border-gray-300 px-4 py-2">${locadora.id}</td>
                                        <td class="border border-gray-300 px-4 py-2">${locadora.email}</td>
                                        <td class="border border-gray-300 px-4 py-2">
                                            <a href="${pageContext.request.contextPath}/admin/editarLocadora?id=${locadora.id}"
                                                class="text-blue-500 hover:underline">Update</a>
                                            <a href="${pageContext.request.contextPath}/admin/deletarLocadora?id=${locadora.id}"
                                                class="text-red-500 hover:underline ml-2 delete-link">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty listaLocadora}">
                        <p>Nenhum Locadora encontrado.</p>
                    </c:if>

                    <hr class="my-4">

                    <div class="flex justify-around">
                        <a href="${pageContext.request.contextPath}/locadora/buscarLocadora"
                            class="text-blue-500 hover:underline">Buscar Locadora por ID</a>
                        <a href="${pageContext.request.contextPath}/locadora/novo"
                            class="text-green-500 hover:underline">Adicionar Locadora</a>
                    </div>
                </div>
            </div>
        </body>

        </html>