<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Cliente</title>
</head>
<body>
    <h2>Editar Cliente</h2>
    <form action="${pageContext.request.contextPath}/cliente/atualizar" method="post">
        <input type="hidden" name="id" value="${cliente.id}">

        <label for="nome">Nome:</label>
        <input type="text" name="nome" value="${cliente.nome}" required><br>

        <label for="email">Email:</label>
        <input type="email" name="email" value="${cliente.email}" required><br>

        <label for="senha">Senha:</label>
        <input type="password" name="senha" value="${cliente.senha}" required><br>

        <label for="telefone">Telefone:</label>
        <input type="text" name="telefone" value="${cliente.telefone}" required><br>

        <label for="sexo">Sexo:</label>
        <input type="text" name="sexo" value="${cliente.sexo}" required><br>

        <label for="cpf">CPF:</label>
        <input type="text" name="cpf" value="${cliente.cpf}" required><br>

        <label for="dataNascimento">Data de Nascimento:</label>
        <input type="date" name="dataNascimento" value="${cliente.dataNascimento}" required><br>

        <button type="submit">Atualizar</button>
    </form>
</body>
</html>
