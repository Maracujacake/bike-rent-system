<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <script src="https://cdn.tailwindcss.com"></script>
        <script src="${pageContext.request.contextPath}/scripts/clientNotFound.js"></script>
        <meta charset="UTF-8">
        <title>Buscar locadora</title>
    </head>

    <body class="bg-gray-100">

        <!-- FORMULÁRIO QUE PERMITE A BUSCA DO locadora PELO ID -->
        <div class="flex justify-center items-center h-screen">
            <div class="max-w-md w-full p-8 bg-white rounded-lg shadow-lg text-center">
                <h2 class="text-2xl font-bold text-gray-800 mb-4">Buscar Locadoras por cidade:</h2>
                <form action="${pageContext.request.contextPath}/locadora/procurarByCidade" method="get"
                    class="mx-auto max-w-sm">
                    <div class="flex items-center border-b border-b-2 border-blue-500 py-2">
                        <input
                            class="appearance-none bg-transparent border-none w-full text-gray-700 mr-3 py-1 px-2 leading-tight focus:outline-none"
                            type="text" id="cidade" name="cidade" placeholder="Cidade da locadora">
                        <button type="submit"
                            class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                            Buscar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>

    </html>