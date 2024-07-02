<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Opções</title>
</head>
									<!-- MOSTRA TODAS AS OPÇÕES REFERENTES A CLIENTE-->
<body class="bg-gray-100">

    <div class="flex justify-center items-center h-screen">
        <div class="max-w-md p-8 bg-white rounded shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">Bem-vindo(a) ${sessionScope.nome}!</h2>
            <ul class="space-y-4">
                <li>
                    <!-- manda para formulário de CPF para busca de locações -->
                    <a href="${pageContext.request.contextPath}/cliente/clienteCPF"
                    class="block px-4 py-2 rounded bg-blue-500 text-white font-bold hover:bg-blue-600"
                    >Listar suas locações</a>
                </li>

                <li>
                    <!-- manda para formulário de criação de locação -->
                    <a href="${pageContext.request.contextPath}/cliente/novoLocacao"
                    class="block px-4 py-2 rounded bg-blue-500 text-white font-bold hover:bg-blue-600"
                    >Adicionar Locação</a>
                </li>

                <li>
                    <!-- manda para formulário de criação de locação -->
                    <a href="${pageContext.request.contextPath}/"
                    class="block px-4 py-2 rounded bg-gray-500 text-white font-bold hover:bg-blue-600"
                    >Voltar à página inicial</a>
                </li>
            </ul>
        </div>
    </div>

</body>

</html>