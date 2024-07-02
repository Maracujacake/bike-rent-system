<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <script src="https://cdn.tailwindcss.com"></script>
            <script src="${pageContext.request.contextPath}/scripts/clientNotFound.js"></script>
            <!-- Atualize o caminho -->
            <title>Lista de locacaos</title>
        </head>

        <body class="bg-gray-100">
            <!-- MOSTRA A LISTA DE TODOAS AS LOCAÇÕES DO BANCO DE DADOS  -->

            <div class="flex justify-center items-center h-screen">
                <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Lista de Locações</h2>
                    <c:if test="${not empty listaLocacao}">
                        <table class="w-full mx-auto">
                            <thead>
                                <tr class="bg-gray-100">
                                    <th class="border border-gray-300 px-4 py-2">ID</th>
                                    <th class="border border-gray-300 px-4 py-2">CNPJ locacao</th>
                                    <th class="border border-gray-300 px-4 py-2">Data Locação</th>
                                    <th class="border border-gray-300 px-4 py-2">Opções</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="locacao" items="${listaLocacao}">
                                    <tr class="border-b border-gray-300">
                                        <td class="border border-gray-300 px-4 py-2">${locacao.id}</td>
                                        <td class="border border-gray-300 px-4 py-2">${locacao.cnpjLocadora}</td>
                                        <td class="border border-gray-300 px-4 py-2">${locacao.registro}</td>
                                        <td class="border border-gray-300 px-4 py-2">
                                            <a href="${pageContext.request.contextPath}/locacao/editar?id=${locacao.id}"
                                                class="text-blue-500 hover:underline">Update</a>
                                            <a href="${pageContext.request.contextPath}/locacao/deletar?id=${locacao.id}"
                                                class="text-red-500 hover:underline ml-2 delete-link">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${empty listaLocacao}">
                        <p>Nenhum locacao encontrado.</p>
                    </c:if>

                    <hr class="my-4">

                    <div class="flex justify-around">

                        <a href="${pageContext.request.contextPath}"
                        class="text-gray-500 hover:underline">Voltar à página inicial</a>

                        <a href="${pageContext.request.contextPath}/locacao/buscarLocacao"
                            class="text-blue-500 hover:underline">Buscar Locação por ID</a>

                        <a href="${pageContext.request.contextPath}/locacao/novo"
                            class="text-green-500 hover:underline">Adicionar locacao</a>
                    </div>
                </div>
            </div>
        </body>

        </html>