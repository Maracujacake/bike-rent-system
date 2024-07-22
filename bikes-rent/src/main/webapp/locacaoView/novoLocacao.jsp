<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="${pageContext.request.contextPath}/scripts/clientNotFound.js"></script>
    <title>Nova Locação</title>
</head>
<body class="bg-gray-100">
    <nav class="bg-white border-gray-200 dark:bg-gray-500">
        <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
            <a href="/bikes-rent" class="flex items-center space-x-3 rtl:space-x-reverse">
                <img src="../imagemLogoSemFundo3.png"
                 class="h-8" alt="System Logo" />
                <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white font-elegant">
                    Bike Rent System</span>
            </a>
            <button data-collapse-toggle="navbar-default" type="button" class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-default" aria-expanded="false">
                <span class="sr-only">Open main menu</span>
                <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 1h15M1 7h15M1 13h15" />
                </svg>
            </button>
            <div class="hidden w-full md:block md:w-auto" id="navbar-default">
                <ul class="font-medium flex flex-col p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 rtl:space-x-reverse md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-500 dark:border-gray-700">
                    <li>
                        <a href="/bikes-rent/cliente/" class="block py-2 px-3 text-gray-100 bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 dark:text-white
                         hover:text-blue-300 transition duration-300"
                         aria-current="page">Cliente</a>
                    </li>
                    <li>
                        <a href="/bikes-rent/locadoraLogged/" class="block py-2 px-3 text-white rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0
                         md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-200 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent
                         transition duration-300">
                            Locadora</a>
                    </li>
                    <li>
                        <a href="/bikes-rent/admin/" class="block py-2 px-3 text-white rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white
                         md:dark:hover:text-blue-200 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent transition duration-300">
                            Admin</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="flex justify-center items-center h-screen">
        <div class="max-w-lg w-full p-8 bg-white rounded-lg shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-6 my-8 text-center">Nova Locação</h2>
            <form action="${pageContext.request.contextPath}/cliente/inserirLocacao" method="post" class="space-y-4">

                <div>
                    <label for="cnpj" class="block text-sm font-medium text-gray-700">Nome Locadora:</label>
                    <select name="cnpj" required>
                        <c:forEach var="locadora" items="${listaLocadora}">
                            <option value="${locadora.cnpj}">${locadora.nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label for="dataHorario" class="block text-sm font-medium text-gray-700">Data:</label>
                    <input type="datetime-local" name="dataHorario" min="${dataAtual}" max="2124-06-30T16:30" required class="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                </div>

                <div class="text-center">
                    <button type="submit"
                        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">Salvar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
