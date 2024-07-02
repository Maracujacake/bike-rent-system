<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty param.lang ? param.lang : 'pt'}" />
<fmt:setBundle basename="message" /> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="cliente.info" /></title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <!-- MOSTRA O RESULTADO DO CLIENTE A PARTIR DE UMA BUSCA POR ID  -->
    <div class="flex justify-center items-center h-screen">
        <div class="max-w-4xl w-full p-8 bg-white rounded shadow-lg text-center">
            <h2 class="text-2xl font-bold text-gray-800 mb-4"><fmt:message key="cliente.info" />:</h2>
                <c:if test="${not empty clienteEspecifico}">
                    <p>ID: ${clienteEspecifico.id}</p>
                    <p><fmt:message key="nome" />: ${clienteEspecifico.nome}</p>
                    <p>Email: ${clienteEspecifico.email}</p>
                    <p><fmt:message key="birthdate" />: ${clienteEspecifico.dataNascimento}</p>
                </c:if>
                <c:if test="${empty clienteEspecifico}">
                    <p><fmt:message key="cliente.NotFound" /></p>
                </c:if>
                <a href="${pageContext.request.contextPath}/admin/listCliente" class="block px-4 py-2 my-4 rounded bg-blue-500 text-white font-bold hover:bg-blue-600"><fmt:message key="cliente.returnToList" /></a>
        </div>
    </div>
    
</body>
</html>
