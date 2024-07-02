<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
        <fmt:setBundle basename="message" />
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <link rel="icon" href=""> <!-- caminho para o ícone -->
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Bike Rent System</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap');

        .font-elegant {
            font-family: 'Playfair Display', serif;
        }
    </style>
</head>

<body class="bg-cover bg-center h-screen" style="background-image: url('https://c.stocksy.com/a/JQIH00/z9/4122399.jpg');">
    <div class="flex justify-center items-center h-full bg-black bg-opacity-40">
        <div class="text-center text-white">
            <h1 class="text-6xl font-elegant mb-16">Bike Rent System</h1>
            <div class="space-x-4 mb-8">
                <a href="cliente/" class="text-2xl hover:text-blue-300 transition duration-300"><fmt:message key="clientes" /></a>
                <a href="locadora/" class="text-2xl hover:text-blue-300 transition duration-300"><fmt:message key="locadoras" /></a>
                <a href="locacao/" class="text-2xl hover:text-blue-300 transition duration-300"><fmt:message key="locacoes" /></a>
            </div>
        </div>
    </div>
</body>

</html>
