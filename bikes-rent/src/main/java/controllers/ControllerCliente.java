package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClienteDAO;
import dao.LocacaoDAO;
import dao.LocadoraDAO;
import domain.Cliente;
import domain.Locacao;
import domain.Locadora;
import service.EmailService;
import utils.DataUtils;

// Todas as funções relacionadas ao cliente
@WebServlet(urlPatterns = "/cliente/*")
public class ControllerCliente extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO dao;
    private LocacaoDAO daoLocacao;
    private LocadoraDAO daoLocadora;

    // Inicialização
    @Override
    public void init() {
        dao = new ClienteDAO();
        daoLocacao = new LocacaoDAO();
        daoLocadora = new LocadoraDAO();
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

                // Create
                case "/novo":
                    novoClienteForm(request, response);
                    break;
                case "/inserir":
                    inserirCliente(request, response);
                    break;

                // Read
                case "/buscaCPF":
                    clienteCPFSearch(request, response);
                    break;

                case "/clienteCPF":
                    listarClienteLocacaoByCPF(request, response);
                    break;

                // Update
                case "/editarCliente":
                    editarDados(request, response);
                    break;
                case "/atualizarCliente":
                    atualizarCliente(request, response);
                    break;

                // LOCACAO - relacionado a cliente

                // Create
                case "/novoLocacao":
                    novoLocacaoForm(request, response);
                    break;

                case "/inserirLocacao":
                    inserirLocacao(request, response);
                    break;

                // Update
                case "/editarLocacao":
                    editarLocacao(request, response);
                    break;

                case "/atualizarLocacao":
                    atualizarLocacao(request, response);
                    break;

                // Delete
                case "/deletarLocacao":
                    deletarLocacao(request, response);
                    break;
                default:
                    paginaInicial(request, response);
                    break;

            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ***** LOCAÇÃO *****

    // *** Funções de CREATE ***

    // Apresenta formulário de criação de Locação
    private void novoLocacaoForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Locadora> loc = daoLocadora.getAll();
        request.setAttribute("listaLocadora", loc);
        LocalDateTime dataAtual = LocalDateTime.now();
        request.setAttribute("dataAtual", DataUtils.parseLocalDate(dataAtual));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/novoLocacao.jsp");
        dispatcher.forward(request, response);
    }

    // Insere nova locação no banco de dados
    private void inserirLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        String cpfCliente = (String) session.getAttribute("cpf");
        String email = (String) session.getAttribute("email");

        String cnpjLocadora = request.getParameter("cnpj");
        String dataHora = request.getParameter("dataHorario");
        LocalDateTime dtDiaHora = LocalDateTime.parse(dataHora);

        Locadora locadora = daoLocadora.getByCnpj(cnpjLocadora);
        String emailLocadora = locadora.getEmail();
        if (!DataUtils.checkFullHour(dtDiaHora)) {
            String errorMessage = URLEncoder.encode("O registro deve estar na hora cheia (ex: 13:00, 15:00).",
                    StandardCharsets.UTF_8.toString());
            response.sendRedirect("novoLocacao?error=" + errorMessage);
            return;
        }
        Locacao novaLocacao = new Locacao(cpfCliente, cnpjLocadora, dtDiaHora);

        Boolean funcionou = daoLocacao.insert(novaLocacao);
        if (funcionou) {
            String formatedDate = DataUtils.parseEmailData(dataHora);
            EmailService.sendEmail(email, "Locacao Feita (" + formatedDate + ")", "Sua locacao foi feita com sucesso!");
            EmailService.sendEmail(emailLocadora, "Locacao Feita (" + formatedDate + ")",
                    "Um cliente realizou uma locacao com sucesso!");
            response.sendRedirect("clienteCPF");
        } else {
            String errorMessage = URLEncoder.encode("Locacao ja existe!",
                    StandardCharsets.UTF_8.toString());
            response.sendRedirect("novoLocacao?error=" + errorMessage);
        }

    }

    // Busca de locações com base no CPF do cliente
    private void clienteCPFSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/formCPFCliente.jsp");
        dispatcher.forward(request, response);
    }

    // Apresenta todas as locações de um cliente dado o cpf
    private void listarClienteLocacaoByCPF(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        String cpf = session.getAttribute("cpf").toString();

        List<Locacao> listaCliente = dao.getLocacaoByCPF(cpf);
        request.setAttribute("listaCliente", listaCliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/locacoesCliente.jsp");
        dispatcher.forward(request, response);
    }

    // *** Funções de Update ***

    // Formulário de edição de locação
    private void editarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idLocacao = request.getParameter("id");
        if (idLocacao != null) {
            try {
                long id = Long.parseLong(idLocacao);
                Locacao locacao = dao.getLocacaoByID(id);

                if (locacao != null) {
                    request.setAttribute("locacao", locacao);
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("/clienteLogado/clienteView/editarLocacao.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("Locacao não encontrada. ID não reconhecido");
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // Atualiza informações da locacao no banco de dados
    private void atualizarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String cpf = request.getParameter("cpfCliente");
        String cnpj = request.getParameter("cnpjLocadora");
        String dataHorario = request.getParameter("dataHorario");
        LocalDateTime dtDiaHora = LocalDateTime.parse(dataHorario);
        Locacao locacaoAtualizado = new Locacao(id, cpf, cnpj, dtDiaHora);
        dao.updateLocacao(locacaoAtualizado);
        response.sendRedirect("");
    }

    // *** Funções de DELETE ***

    // Deleta locação do banco de dados
    private void deletarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        daoLocacao.delete(id);
        // Redireciona para a pagina de locacoes de cliente
        response.sendRedirect("clienteCPF");
    }

    // ***** CLIENTE *****

    // Página inicial de opções do cliente
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/opcoesCliente.jsp");
        dispatcher.forward(request, response);
    }

    // *** Funções de CREATE ***

    // Apresenta formulário de criação de cliente
    private void novoClienteForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/novoCliente.jsp");
        dispatcher.forward(request, response);
    }

    // Insere cliente no banco de dados
    private void inserirCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String cpf = request.getParameter("cpf");
        Date dataNascimento = Date.valueOf(request.getParameter("dataNascimento"));

        Cliente novoCliente = new Cliente(email, senha, nome, telefone, sexo, cpf, dataNascimento);
        dao.insert(novoCliente);
        response.sendRedirect("");
    }

    /// *** Funções de UPDATE ***

    private void editarDados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        Cliente cliente = dao.get((session.getAttribute("email")).toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/editarCliente.jsp");
        request.setAttribute("cliente", cliente);
        dispatcher.forward(request, response);
    }

    private void atualizarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String cpf = request.getParameter("cpf");
        Date dataNascimento = Date.valueOf(request.getParameter("dataNascimento"));

        Cliente cliente = new Cliente(id, email, senha, nome, telefone, sexo, cpf, dataNascimento);
        dao.update(cliente);
        response.sendRedirect("");
    }

}
