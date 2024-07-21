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
import java.time.LocalDateTime;

import dao.ClienteDAO;
import dao.LocacaoDAO;
import dao.LocadoraDAO;
import domain.Cliente;
import domain.Locacao;
import domain.Locadora;
import utils.DataUtils;

// Todas as funções de administração do sistema
@WebServlet(urlPatterns = "/admin/*")
public class ControllerAdmin extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDao;
    private LocadoraDAO locadoraDao;
    private LocacaoDAO locacaoDao;
    @Override
    public void init() {
        clienteDao = new ClienteDAO();
        locadoraDao = new LocadoraDAO();
        locacaoDao = new LocacaoDAO();
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

                // CLIENTE
                // read
                case "/listLocadora":
                    listLocadora(request, response);
                    break;
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

                //Locacoes
                case "/listLocacoes":
                    listLocacoes(request, response);
                    break;

                case "/editarLocacao":
                    editarLocacao(request, response);
                    break;
                
                case "/atualizarLocacao":
                    atualizarLocacao(request, response);
                    break;

                case "/deletarLocacao":
                    deletarLocacao(request, response);
                    break;

                default:
                    paginaInicial(request, response);
                    break;
            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }


    private void listLocadora(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Locadora> listaLocadoras = locadoraDao.getAll();
        request.setAttribute("listaLocadora", listaLocadoras);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/locadoraLista.jsp");
        dispatcher.forward(request, response);
    }

    private void listLocacoes(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        List<Locacao> listaLocacoes = locacaoDao.getAll();
        listaLocacoes.forEach(locacao -> {
            System.out.println("CPF LOCACAO: " + locacao.getCpfCliente());
        });
        request.setAttribute("listaLoc", listaLocacoes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/locacaoLista.jsp");
        dispatcher.forward(request, response);
    }

    private void editarLocacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idLocacao = request.getParameter("id");
        if (idLocacao != null) {
            try {
                long id = Long.parseLong(idLocacao);
                Locacao locacao = locacaoDao.getByID(id);
                if (locacao != null) {
                    request.setAttribute("locacao", locacao);
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("/adminView/editarLocacao.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("Locacao não encontrada. ID não reconhecido");
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    private void atualizarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String cpf = request.getParameter("cpfCliente");
        String cnpj = request.getParameter("cnpjLocadora");
        String dataHorario = request.getParameter("dataHorario");
        LocalDateTime dtDiaHora = LocalDateTime.parse(dataHorario);
        if (!DataUtils.checkFullHour(dtDiaHora)) {
            String errorMessage = URLEncoder.encode("O registro deve estar na hora cheia (ex: 13:00, 15:00).",
                    StandardCharsets.UTF_8.toString());
            response.sendRedirect("editarLocacao?id=" + id+ "&error=" + errorMessage);
            return;
        }
        Locacao locacaoAtualizado = new Locacao(id, cpf, cnpj, dtDiaHora);
        locacaoDao.update(locacaoAtualizado);
        response.sendRedirect("");
    }

    private void deletarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        locacaoDao.delete(id);
        // Redireciona para a pagina de locacoes de cliente
        response.sendRedirect("clienteCPF");
    }


    // Página inicial de opções
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/opcoesAdmin.jsp");
        dispatcher.forward(request, response);
    }

    // ***** CLIENTE *****

    // *** Funções de READ ***

    // Apresenta todos os clientes
    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> listaCliente = clienteDao.getAll();
        
         
        request.setAttribute("listaCliente", listaCliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/clienteLista.jsp");
        dispatcher.forward(request, response);
    }


    // Apresenta formulário de busca de cliente por ID
    private void paginaBuscarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Teste de conexão: System.out.println("cade a pagina fi");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/buscaCliente.jsp");
        dispatcher.forward(request, response);
    }

    // Procura cliente pelo seu ID no banco
    private void procuraCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCliente = request.getParameter("id");
        if (idCliente != null) {
            try {
                long id = Long.parseLong(idCliente);
                Cliente cliente = clienteDao.get(id);
                if (cliente != null) {
                    request.setAttribute("clienteEspecifico", cliente);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/clienteEspecifico.jsp");
                    dispatcher.forward(request, response);
                } else {
                    String errorMessage = URLEncoder.encode("Cliente não encontrado. ID não reconhecido",
                            StandardCharsets.UTF_8.toString());
                    response.sendRedirect(
                            request.getContextPath() + "/adminView/buscaCliente.jsp?error=" + errorMessage);
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // *** Funções de UPDATE ***


    // Apresenta formulário de edição de cliente com informações referentes ao seu id
    private void editarClienteForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Cliente clienteExistente = clienteDao.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/editarCliente.jsp");
        request.setAttribute("cliente", clienteExistente);
        dispatcher.forward(request, response);
    }


    // Atualiza informações de cliente no banco de dados
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
        clienteDao.update(clienteAtualizado);
        response.sendRedirect("listCliente");
    }

    // *** Funções de DELETE ***


    // Deleta cliente do banco de dados
    private void deletarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        clienteDao.delete(id);
        // redireciona para a pagina em cliente/list
        response.sendRedirect("listCliente");
    }


    // ***** LOCADORA *****


    // *** Funções de UPDATE ***

    // Apresenta formulário de edição de Locadora com informações referentes ao seu id
    private void editarLocadoraForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Locadora LocadoraExistente = locadoraDao.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/adminView/editarLocadora.jsp");
        request.setAttribute("Locadora", LocadoraExistente);
        dispatcher.forward(request, response);
    }

    // Atualiza informações de Locadora no banco de dados
    private void atualizarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cidade = request.getParameter("cidade");
        String cnpj = request.getParameter("cnpj");

        Locadora LocadoraAtualizado = new Locadora(id, senha, nome, cidade, cnpj, email);
        locadoraDao.update(LocadoraAtualizado);
        response.sendRedirect("listCliente");
    }

    // *** Funções de DELETE ***

    // Deleta Locadora do banco de dados
    private void deletarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        locadoraDao.delete(id);
        // redireciona para a pagina em Locadora/list
        response.sendRedirect("");
    }

}
