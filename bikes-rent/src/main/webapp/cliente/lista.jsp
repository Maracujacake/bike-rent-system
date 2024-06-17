<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page isELIgnored="false" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <html>

            <head>
                <title>Livraria Virtual</title>
            </head>

            <body>
                <div align="center">
                    <h1>Gerenciamento de Livros</h1>
                    <h2>
                        <a href="/${requestScope.contextPath}">Menu Principal</a> &nbsp;&nbsp;&nbsp;
                    </h2>
                </div>
                <h1>Alguma coisa!</h1>

                <!-- <div align="center">
                    <table border="1">
                        <caption>Lista de Livros</caption>
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th>Editora</th>
                            <th>Autor</th>
                            <th>Ano</th>
                            <th>Preço</th>
                            <th>Acões</th>
                        </tr>
                        <c:forEach var="cli" items="${requestScope.listaCliente}">
                            <tr>
                                <td>${cli.nome}</td>
                                <td>${cli.cpf}</td>
                                <td>${cli.data}</td>
                                <td>${cli.email}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div> -->
            </body>

            </html>