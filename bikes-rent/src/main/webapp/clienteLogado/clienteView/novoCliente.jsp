<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Novo Cliente</title>
</head>
<body class="bg-gray-100">
    <div class="flex justify-center items-center h-screen">
        <div class="max-w-lg w-full p-8 bg-white rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-6 my-8 text-center">Novo Cliente</h2>
            <form action="${pageContext.request.contextPath}/cliente/inserir" method="post" class="space-y-4">
                <div>
                    <label for="nome" class="block text-sm font-medium text-gray-700">Nome:</label>
                    <input type="text" name="nome" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div>
                    <label for="email" class="block text-sm font-medium text-gray-700">Email:</label>
                    <input type="email" name="email" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div>
                    <label for="senha" class="block text-sm font-medium text-gray-700">Senha:</label>
                    <input type="password" name="senha" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div>
                    <label for="telefone" class="block text-sm font-medium text-gray-700">Telefone:</label>
                    <input type="text" name="telefone" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div>
                    <label for="sexo" class="block text-sm font-medium text-gray-700">Sexo:</label>
                    <input type="text" name="sexo" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div>
                    <label for="cpf" class="block text-sm font-medium text-gray-700">CPF:</label>
                    <input type="text" name="cpf" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div>
                    <label for="dataNascimento" class="block text-sm font-medium text-gray-700">Data de Nascimento:</label>
                    <input type="date" name="dataNascimento" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div class="text-center">
                    <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">Salvar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
