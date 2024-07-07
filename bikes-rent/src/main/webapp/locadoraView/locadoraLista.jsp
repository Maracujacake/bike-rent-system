<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
            <fmt:setBundle basename="message" />
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <script src="https://cdn.tailwindcss.com"></script>
                <script src="${pageContext.request.contextPath}/scripts/clientNotFound.js"></script>

                <!-- Atualize o caminho -->
                <title>
                    <fmt:message key="locadora.lista" />
                </title>
            </head>

            <body class="bg-gray-100">
                <!-- MOSTRA A LISTA DE TODOS OS locadoraS DO BANCO DE DADOS  -->

                <div class="flex justify-center items-center h-screen">
                    <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
                        <h2 class="text-2xl font-bold text-gray-800 mb-4">
                            <fmt:message key="locadora.lista" />
                        </h2>
                        <c:if test="${not empty listaLocadora}">
                            <table class="w-full mx-auto">
                                <thead>
                                    <tr class="bg-gray-100">
                                        <th class="border border-gray-300 px-4 py-2">
                                            <fmt:message key="nome" />
                                        </th>
                                        <th class="border border-gray-300 px-4 py-2">
                                            <fmt:message key="cidade" />
                                        </th>
                                        <th class="border border-gray-300 px-4 py-2">CNPJ</th>
                                        <th class="border border-gray-300 px-4 py-2">ID</th>
                                        <th class="border border-gray-300 px-4 py-2">
                                            <fmt:message key="email" />
                                        </th>
                                        <th class="border border-gray-300 px-4 py-2">
                                            <fmt:message key="opcao" />
                                        </th>
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
                                                    class="text-blue-500 hover:underline">
                                                    <fmt:message key="atualizar" />
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/deletarLocadora?id=${locadora.id}"
                                                    class="text-red-500 hover:underline ml-2 delete-link">
                                                    <fmt:message key="deletar" />
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <c:if test="${empty listaLocadora}">
                            <p>
                                <fmt:message key="locadora.notFound" />
                            </p>
                        </c:if>

                        <hr class="my-4">

                        <div class="flex justify-around">

                            <a href="${pageContext.request.contextPath}"
                                class="text-gray-500 hover:underline">
                                Voltar à página inicial
                            </a>

                            <a href="${pageContext.request.contextPath}/locadora/buscarLocadora"
                                class="text-blue-500 hover:underline">
                                <fmt:message key="locadora.getById" />
                            </a>

                            <a href="${pageContext.request.contextPath}/locadora/buscaLocadoraByCidade"
                                class="text-blue-500 hover:underline">
                                <fmt:message key="locadora.getByCidade" />
                            </a>

                            <a href="${pageContext.request.contextPath}/locadora/novo"
                                class="text-green-500 hover:underline">
                                <fmt:message key="locadora.add" />
                            </a>
                        </div>
                    </div>
                </div>
            </body>

            </html>