<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
        <fmt:setBundle basename="message" />
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <script src="https://cdn.tailwindcss.com"></script>
            <script src="${pageContext.request.contextPath}/scripts/confirmDelete.js"></script>
            <title>
                <fmt:message key="locadora.new" />
            </title>
        </head>

        <body class="bg-gray-100">
            <div class="flex justify-center items-center h-screen">
                <div class="max-w-lg w-full p-8 bg-white rounded-lg shadow-lg">
                    <h2 class="text-2xl font-bold text-gray-800 mb-6 my-8 text-center">
                        <fmt:message key="locadora.new" />
                    </h2>
                    <form action="${pageContext.request.contextPath}/locadora/inserir" method="post" class="space-y-4">
                        <div>
                            <label for="nome" class="block text-sm font-medium text-gray-700">
                                <fmt:message key="nome" />:
                            </label>
                            <input type="text" name="nome" required
                                class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>

                        <div>
                            <label for="email" class="block text-sm font-medium text-gray-700">
                                <fmt:message key="email" />:
                            </label>
                            <input type="email" name="email" required
                                class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>

                        <div>
                            <label for="senha" class="block text-sm font-medium text-gray-700">
                                <fmt:message key="senha" />:
                            </label>
                            <input type="password" name="senha" required
                                class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>

                        <div>
                            <label for="cidade" class="block text-sm font-medium text-gray-700">
                                <fmt:message key="cidade" />:
                            </label>
                            <input type="text" name="cidade" required
                                class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>

                        <div>
                            <label for="cnpj" class="block text-sm font-medium text-gray-700">CNPJ:</label>
                            <input type="text" name="cnpj" required
                                class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>


                        <div class="text-center">
                            <button type="submit"
                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                                <fmt:message key="salvar" />:
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </body>

        </html>