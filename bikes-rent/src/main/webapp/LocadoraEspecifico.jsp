<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>Detalhes do Locadora</title>
            <script src="https://cdn.tailwindcss.com"></script>
        </head>

        <body>
            <!-- MOSTRA O RESULTADO DO Locadora A PARTIR DE UMA BUSCA POR ID  -->
            <div class="flex justify-center items-center h-screen">
                <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
                    <h2 class="text-2xl font-bold text-gray-800 mb-4">Informações do Locadora:</h2>
                    <c:if test="${not empty LocadoraEspecifico}">
                        <p>ID: ${LocadoraEspecifico.id}</p>
                        <p>Nome: ${LocadoraEspecifico.nome}</p>
                        <p>Email: ${LocadoraEspecifico.email}</p>
                        <p>CNPJ: ${LocadoraEspecifico.cnpj}</p>
                        <p>Cidade: ${LocadoraEspecifico.cidade}</p>
                    </c:if>
                    <c:if test="${empty LocadoraEspecifico}">
                        <p>Locadora não encontrado.</p>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/locadora/list"
                        class="block px-4 py-2 my-4 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">Voltar
                        para lista de Locadoras</a>
                </div>
            </div>

        </body>

        </html>