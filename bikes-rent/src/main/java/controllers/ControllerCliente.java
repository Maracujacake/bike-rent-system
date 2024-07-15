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

@WebServlet(urlPatterns = "/cliente/*")
public class ControllerCliente extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO dao;
    private LocacaoDAO daoLocacao;
    private LocadoraDAO daoLocadora;

    @Override
    public void init() {
        dao = new ClienteDAO();
        daoLocacao = new LocacaoDAO();
        daoLocadora = new LocadoraDAO();
        // System.out.println("ALO"); teste de inicialização
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

                /* Especifico para cliente e não ADMIN: */
                case "/buscaCPF":
                    clienteCPFSearch(request, response);
                    break;

                case "/clienteCPF":
                    listarClienteLocacaoByCPF(request, response);
                    break;

                case "/editarLocacao":
                    editarLocacao(request, response);
                    break;

                case "/atualizarLocacao":
                    atualizarLocacao(request, response);
                    break;
                // create
                case "/novo":
                    novoClienteForm(request, response);
                    break;
                case "/inserir":
                    inserirCliente(request, response);
                    break;

                // LOCACAO
                case "/novoLocacao":
                    novoLocacaoForm(request, response);
                    break;
                case "/inserirLocacao":
                    inserirLocacao(request, response);
                    break;

                case "/deletarLocacao":
                    deletarLocacao(request, response);
                    break;
                default:
                    paginaInicial(request, response);
                    break;

            }
        }

        catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Funções de CREATE
    // apresenta formulário de criação de Locadora
    private void novoLocacaoForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Locadora> loc = daoLocadora.getAll();
        request.setAttribute("listaLocadora", loc);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/novoLocacao.jsp");
        dispatcher.forward(request, response);
    }

    // insere Locadora no banco de dados
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

        Locacao novaLocacao = new Locacao(cpfCliente, cnpjLocadora, dtDiaHora);
        // System.out.println(novaLocacao.getCnpjLocadora());
        // System.out.println(novaLocacao.getCpfCliente());
        // System.out.println(novaLocacao.getRegistro());
        Boolean funcionou = daoLocacao.insert(novaLocacao);
        if (funcionou) {

            EmailService.sendEmail(email, "Locacao Feita ("+dataHora+")", "Sua locacao foi feita com sucesso!");
            EmailService.sendEmail(emailLocadora, "Locacao Feita ("+dataHora+")", "Um cliente realizou uma locacao com sucesso!");
            response.sendRedirect("clienteCPF");
        } else {
            String errorMessage = URLEncoder.encode("Locacao ja existe!",
                    StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/locacaoView/locacao/list?error=" + errorMessage);
        }

    }

    // Cliente passa o CPF para usar na busca de locações
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
        for (Locacao x : listaCliente) {
            System.out.println(x.getCnpjLocadora());
        }
        request.setAttribute("listaCliente", listaCliente);
        // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta
        // webapp é a ''raiz''
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/locacoesCliente.jsp");
        dispatcher.forward(request, response);

    }

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

    // atualiza informações da locacao no banco de dados
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

    // Página inicial
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/opcoesCliente.jsp");
        dispatcher.forward(request, response);
    }

    // Funções de CREATE
    // apresenta formulário de criação de cliente
    private void novoClienteForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/novoCliente.jsp");
        dispatcher.forward(request, response);
    }

    // insere cliente no banco de dados
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

    // Funções de DELETE
    // deleta Locadora do banco de dados
    private void deletarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        daoLocacao.delete(id);
        // redireciona para a pagina de locacoes de cliente
        response.sendRedirect("clienteCPF");
    }

}
