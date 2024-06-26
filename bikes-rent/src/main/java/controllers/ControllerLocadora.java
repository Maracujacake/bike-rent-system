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
                // create
                case "/novo":
                    novoLocadoraForm(request, response);
                    break;
                case "/inserir":
                    inserirLocadora(request, response);
                    break;

                // read
                case "/list":
                    listarLocadoras(request, response);
                    break;
                case "/buscarLocadora":
                    paginaBuscarLocadora(request, response);
                    break;
                case "/procurar":
                    procuraLocadora(request, response);
                    break;

                // update
                case "/editar":
                    editarLocadoraForm(request, response);
                    break;
                case "/atualizar":
                    atualizarLocadora(request, response);
                    break;

                // deletar
                case "/deletar":
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/opcoesLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // Funções de CREATE

    // apresenta formulário de criação de Locadora
    private void novoLocadoraForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/novoLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // insere Locadora no banco de dados
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
            response.sendRedirect(request.getContextPath() + "/locadora/list?error=" + errorMessage);
        }

    }

    // Funções de READ

    // Apresenta todos os Locadoras
    private void listarLocadoras(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Locadora> listaLocadora = dao.getAll();

        /*
         * TESTE
         * for(Locadora pessoa : listaLocadora){
         * System.out.println(pessoa.getDataNascimento());
         * }
         */

        request.setAttribute("listaLocadora", listaLocadora);
        // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta
        // webapp é a ''raiz''
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraLista.jsp");
        dispatcher.forward(request, response);

    }

    // apresenta formulário de busca de Locadora
    private void paginaBuscarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Teste de conexão: System.out.println("cade a pagina fi");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/buscaLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // procura Locadora pelo seu ID no banco
    private void procuraLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idLocadora = request.getParameter("id");
        if (idLocadora != null) {
            try {
                long id = Long.parseLong(idLocadora);
                Locadora Locadora = dao.get(id);

                if (Locadora != null) {
                    request.setAttribute("LocadoraEspecifico", Locadora);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraEspecifico.jsp");
                    dispatcher.forward(request, response);
                }

                else {
                    String errorMessage = URLEncoder.encode("Locadora não encontrada. ID não reconhecido",
                            StandardCharsets.UTF_8.toString());
                    response.sendRedirect(request.getContextPath() + "/buscaLocadora.jsp?error=" + errorMessage);
                }
            }

            catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // Funções de UPDATE

    // apresenta formulário de edição de Locadora com informações referentes ao seu
    // id
    private void editarLocadoraForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Locadora LocadoraExistente = dao.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editarLocadora.jsp");
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
        dao.update(LocadoraAtualizado);
        response.sendRedirect("list");
    }

    // Funções de DELETE

    // deleta Locadora do banco de dados
    private void deletarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.delete(id);
        // redireciona para a pagina em Locadora/list
        response.sendRedirect("list");
    }

}
