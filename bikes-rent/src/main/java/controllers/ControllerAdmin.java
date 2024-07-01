package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.LocadoraDAO;
import domain.Cliente;
import domain.Locadora;

@WebServlet(urlPatterns = "/admin/*")
public class ControllerAdmin extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO dao;
    private LocadoraDAO dao2;

    @Override
    public void init() {
        dao = new ClienteDAO();
        dao2 = new LocadoraDAO();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) {
            action = " ";
        }

        try {
            switch (action) {

                //CLIENTE
                // read
                case "/listCliente":
                    listarClientes(request, response);
                    break;
                case "/buscarCliente":
                    paginaBuscarCliente(request, response);
                    break;
                case "/procurarCliente":
                    procuraCliente(request, response);
                    break;

                // update
                case "/editarCliente":
                    editarClienteForm(request, response);
                    break;
                case "/atualizarCliente":
                    atualizarCliente(request, response);
                    break;

                // deletar
                case "/deletarCliente":
                    deletarCliente(request, response);
                    break;

                // LOCADORA
                // update
                case "/editarLocadora":
                    editarLocadoraForm(request, response);
                    break;
                case "/atualizarLocadora":
                    atualizarLocadora(request, response);
                    break;

                // deletar
                case "/deletarLocadora":
                    deletarLocadora(request, response);
                    break;

                default:
                    paginaInicial(request, response);
                    break;
            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }

    // Página inicial
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/opcoesAdmin.jsp");
        dispatcher.forward(request, response);
    }

    // CLIENTE
    // Funções de READ
    // Apresenta todos os clientes
    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> listaCliente = dao.getAll();

        /*
         * TESTE
         * for(Cliente pessoa : listaCliente){
         * System.out.println(pessoa.getDataNascimento());
         * }
         */
        request.setAttribute("listaCliente", listaCliente);
        // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta
        // webapp é a ''raiz''
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/clienteLista.jsp");
        dispatcher.forward(request, response);

    }

    // apresenta formulário de busca de cliente
    private void paginaBuscarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Teste de conexão: System.out.println("cade a pagina fi");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/buscaCliente.jsp");
        dispatcher.forward(request, response);
    }

    // procura cliente pelo seu ID no banco
    private void procuraCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCliente = request.getParameter("id");
        if (idCliente != null) {
            try {
                long id = Long.parseLong(idCliente);
                Cliente cliente = dao.get(id);

                if (cliente != null) {
                    request.setAttribute("clienteEspecifico", cliente);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/clienteEspecifico.jsp");
                    dispatcher.forward(request, response);
                } else {
                    String errorMessage = URLEncoder.encode("Cliente não encontrado. ID não reconhecido",
                            StandardCharsets.UTF_8.toString());
                    response.sendRedirect(request.getContextPath() + "/buscaCliente.jsp?error=" + errorMessage);
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // Funções de UPDATE
    // apresenta formulário de edição de cliente com informações referentes ao seu
    // id
    private void editarClienteForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Cliente clienteExistente = dao.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/editarCliente.jsp");
        request.setAttribute("cliente", clienteExistente);
        dispatcher.forward(request, response);
    }

    // atualiza informações de cliente no banco de dados
    private void atualizarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String cpf = request.getParameter("cpf");
        String dataNascimento = request.getParameter("dataNascimento");

        Cliente clienteAtualizado = new Cliente(id, email, senha, nome, telefone, sexo, cpf,
                java.sql.Date.valueOf(dataNascimento));
        dao.update(clienteAtualizado);
        response.sendRedirect("list");
    }

    // Funções de DELETE
    // deleta cliente do banco de dados
    private void deletarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.delete(id);
        // redireciona para a pagina em cliente/list
        response.sendRedirect("list");
    }

    // LOCADORA
    // Funções de UPDATE
    // apresenta formulário de edição de Locadora com informações referentes ao seu
    // id
    private void editarLocadoraForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Locadora LocadoraExistente = dao2.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/editarLocadora.jsp");
        request.setAttribute("Locadora", LocadoraExistente);
        dispatcher.forward(request, response);
    }

    // atualiza informações de Locadora no banco de dados
    private void atualizarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cidade = request.getParameter("cidade");
        String cnpj = request.getParameter("cnpj");

        Locadora LocadoraAtualizado = new Locadora(id, senha, nome, cidade, cnpj, email);
        dao2.update(LocadoraAtualizado);
        response.sendRedirect("list");
    }

    // Funções de DELETE
    // deleta Locadora do banco de dados
    private void deletarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao2.delete(id);
        // redireciona para a pagina em Locadora/list
        response.sendRedirect("");
    }
}
