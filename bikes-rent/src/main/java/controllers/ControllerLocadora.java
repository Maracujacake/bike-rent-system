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

import dao.LocadoraDAO;
import domain.Locadora;

@WebServlet(urlPatterns = "/locadora/*")
public class ControllerLocadora extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LocadoraDAO dao;

    @Override
    public void init() {
        dao = new LocadoraDAO();
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
        if (action == null)
            action = " ";

        try {
            switch (action) {

                // CREATE
                case "/novo":
                    novoLocadoraForm(request, response);
                    break;
                case "/inserir":
                    inserirLocadora(request, response);
                    break;

                // READ
                case "/list":
                    listarLocadoras(request, response);
                    break;
                case "/buscarLocadora":
                    paginaBuscarLocadora(request, response);
                    break;
                case "/procurar":
                    procuraLocadora(request, response);
                    break;
                case "/buscaLocadoraByCidade":
                    buscaLocadoraByCidade(request, response);
                    break;
                case "/procurarByCidade":
                    procuraLocadoraByCidade(request, response);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/opcoesLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // *** Funções de CREATE ***

    // Apresenta formulário de criação de Locadora
    private void novoLocadoraForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/novoLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // Insere Locadora no banco de dados
    private void inserirLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String cidade = request.getParameter("cidade");
        String cnpj = request.getParameter("cnpj");

        Locadora novoLocadora = new Locadora(senha, nome, cidade, cnpj, email);

        Boolean funcionou = dao.insert(novoLocadora);
        if (funcionou)
            response.sendRedirect("list");
        else {
            String errorMessage = URLEncoder.encode("Locadora ja existe!",
                    StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/locadoraView/locadora/list?error=" + errorMessage);
        }

    }

    // *** Funções de READ ***

    // Apresenta todas as Locadoras
    private void listarLocadoras(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Locadora> listaLocadora = dao.getAll();
        request.setAttribute("listaLocadora", listaLocadora);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/locadoraLista.jsp");
        dispatcher.forward(request, response);

    }

    // Apresenta formulário de busca de Locadora
    private void paginaBuscarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/buscaLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // Procura Locadora pelo seu ID no banco
    private void procuraLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idLocadora = request.getParameter("id");
        if (idLocadora != null) {
            try {
                long id = Long.parseLong(idLocadora);
                Locadora Locadora = dao.get(id);

                if (Locadora != null) {
                    request.setAttribute("LocadoraEspecifico", Locadora);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/locadoraEspecifico.jsp");
                    dispatcher.forward(request, response);
                }

                else {
                    String errorMessage = URLEncoder.encode("Locadora não encontrada. ID não reconhecido",
                            StandardCharsets.UTF_8.toString());
                    response.sendRedirect(
                            request.getContextPath() + "/locadoraView/buscaLocadora.jsp?error=" + errorMessage);
                }
            }

            catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // Apresenta formulário de busca de Locadora por cidade
    private void buscaLocadoraByCidade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/buscaLocadoraByCidade.jsp");
        dispatcher.forward(request, response);
    }

    // Busca locadora por cidade DE FATO no banco
    private void procuraLocadoraByCidade(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Cidade = request.getParameter("cidade");
        try {
            List<Locadora> Locadoras = dao.getByCidade(Cidade);
            request.setAttribute("listaLocadorasCidade", Locadoras);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraView/locadoraCidadeLista.jsp");
            dispatcher.forward(request, response);
        }

        catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }
}
