<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
        <fmt:setBundle basename="message" />
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <script src="https://cdn.tailwindcss.com"></script>
            <title>
                <fmt:message key="opcoes" />
            </title>
        </head>
        <!-- MOSTRA TODAS AS OPÇÕES REFERENTES A LOCACAO-->

        <body class="bg-gray-100">

            <div class="flex justify-center items-center h-screen">
                <div class="max-w-md p-8 bg-white rounded shadow-lg">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">
                        Bem-vindo ao sistema de locações!
                    </h2>
                    <ul class="space-y-4">
                        <li>
                            <a href="${pageContext.request.contextPath}/locacao/list"
                                class="block px-4 py-2 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">
                                Listar todas as locações
                            </a>
                        </li>

                        <li>
                            <a href="${pageContext.request.contextPath}/locacao/buscarLocacao"
                                class="block px-4 py-2 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">
                                Buscar locação por ID
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

        </body>

        </html>
