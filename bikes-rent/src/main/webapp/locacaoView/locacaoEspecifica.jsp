<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
            <fmt:setBundle basename="message" />
            <!DOCTYPE html>
            <html>

        <head>
            <meta charset="UTF-8">
            <title>Detalhes do Locação</title>
            <script src="https://cdn.tailwindcss.com"></script>
        </head>

        <body>
            <!-- MOSTRA O RESULTADO DO locacao A PARTIR DE UMA BUSCA POR ID  -->
            <div class="flex justify-center items-center h-screen">
                <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Informações da Locação:</h2>
                    <c:if test="${not empty locacaoEspecifico}">
                        <p>CPF Cliente: ${locacaoEspecifico.cpfCliente}</p>
                        <p>CNPJ Locadora: ${locacaoEspecifico.cnpjLocadora}</p>
                        <p>Data: ${locacaoEspecifico.registro}</p>
                    </c:if>
                    <c:if test="${empty locacaoEspecifico}">
                        <p>Locação não encontrada.</p>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/locacao/list"
                        class="block px-4 py-2 my-4 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">Voltar
                        para lista de locações</a>
                </div>

            </body>

            </html>