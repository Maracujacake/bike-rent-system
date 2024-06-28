<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Opções</title>
</head>
									<!-- MOSTRA TODAS AS OPÇÕES REFERENTES A LOCAÇÃO-->
<body class="bg-gray-100">

    <div class="flex justify-center items-center h-screen">
        <div class="max-w-md p-8 bg-white rounded shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-4">Bem-vindo ao Sistema de Locações</h2>
            <ul class="space-y-4">
                <li>
                    <a href="${pageContext.request.contextPath}/locacao/list"
                        class="block px-4 py-2 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">Listar Todas
                        as Locações</a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/locacao/buscarLocacao"
                        class="block px-4 py-2 rounded bg-blue-500 text-white font-bold hover:bg-blue-600">Buscar
                        Locação por ID</a>
                </li>
            </ul>
        </div>
    </div>

</body>

</html>
