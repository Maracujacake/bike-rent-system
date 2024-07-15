package controllers;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import dao.ClienteDAO;
import domain.Cliente;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/public/*")
public class ControllerPublic extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = " ";
        }
        try {
            switch (action) {
                case "/novoClienteForm":
                    novoClienteForm(request, response);
                    break;
                case "/criarCliente":
                    criarCliente(request, response);
                    break;
                default:
                    novoClienteForm(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void novoClienteForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/novoCliente.jsp");
        dispatcher.forward(request, response);

    }

    private void criarCliente(HttpServletRequest request, HttpServletResponse response) {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String cpf = request.getParameter("cpf");
        Date dataNascimento = Date.valueOf(request.getParameter("dataNascimento"));

        try {
            Cliente novoCliente = new Cliente(email, senha, nome, telefone, sexo, cpf, dataNascimento);
            clienteDAO.insert(novoCliente);
            response.sendRedirect("/bikes-rent/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
