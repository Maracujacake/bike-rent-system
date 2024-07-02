package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LocacaoDAO;
import domain.Locacao;


@WebServlet(urlPatterns = "/locacao/*")
public class ControllerLocacao extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LocacaoDAO dao;

    @Override
    public void init() {
        dao = new LocacaoDAO();
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
                // criação está em cliente e em admin

                // read
                case "/list":
                    listarLocacoes(request, response);
                    break;
                /*
                 * case "/buscaLocacaoByCPF":
                 * listarLocacoesByCPF(request, response);
                 * break;
                 */

                /*
                 * case "/buscaLocacaoByCNPJ":
                 * listarLocacoesByCNPJ(request, response);
                 * break;
                 */
                case "/buscarLocacao":
                    paginaBuscarLocacao(request, response);
                    break;
                case "/procurar":
                    procuraLocacao(request, response);
                    break;

                // update
                case "/editar":
                    editarLocacaoForm(request, response);
                    break;
                case "/atualizar":
                    atualizarLocacao(request, response);
                    break;

                // deletar
                case "/deletar":
                    deletarLocacao(request, response);
                    break;

                default:
                    paginaInicial(request, response);
                    break;
            }
        } 
        
        catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e); 
        }
    }

    // Página inicial
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/opcoesLocacao.jsp");
        dispatcher.forward(request, response);
    }

    // Funções de READ
    // Apresenta todas as Locações
    private void listarLocacoes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Locacao> listaLocacao = dao.getAll();

        request.setAttribute("listaLocacao", listaLocacao);
        // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta
        // webapp é a ''raiz''
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/locacaoLista.jsp");
        dispatcher.forward(request, response);

    }

    /*
     * falta
     * // Apresenta todas as Locações por CPF
     * private void listarLocacoesByCPF(HttpServletRequest request,
     * HttpServletResponse response)
     * throws ServletException, IOException {
     * List<Locacao> listaLocacao = dao.getAll();
     * 
     * request.setAttribute("listaLocacao", listaLocacao);
     * // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta
     * // webapp é a ''raiz''
     * RequestDispatcher dispatcher =
     * request.getRequestDispatcher("/locacaoLista.jsp");
     * dispatcher.forward(request, response);
     * 
     * }
     */

    /*
     * falta
     * // Apresenta todas as Locações por CNPJ
     * private void listarLocacoesByCNPJ(HttpServletRequest request,
     * HttpServletResponse response)
     * throws ServletException, IOException {
     * List<Locacao> listaLocacao = dao.getAll();
     * 
     * request.setAttribute("listaLocacao", listaLocacao);
     * // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta
     * // webapp é a ''raiz''
     * RequestDispatcher dispatcher =
     * request.getRequestDispatcher("/locacaoLista.jsp");
     * dispatcher.forward(request, response);
     * 
     * }
     */

    // apresenta formulário de busca de Locadora
    private void paginaBuscarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Teste de conexão: System.out.println("cade a pagina fi");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/buscaLocacao.jsp");
        dispatcher.forward(request, response);
    }

    // procura Locadora pelo seu ID no banco
    private void procuraLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idLocadora = request.getParameter("id");
        if (idLocadora != null) {
            try {
                long id = Long.parseLong(idLocadora);
                Locacao locacao = dao.getByID(id);

                if (locacao != null) {
                    request.setAttribute("locacaoEspecifico", locacao);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/locacaoEspecifica.jsp");
                    dispatcher.forward(request, response);
                } else {
                    String errorMessage = URLEncoder.encode("Locadora não encontrada. ID não reconhecido",
                            StandardCharsets.UTF_8.toString());
                    response.sendRedirect(
                            request.getContextPath() + "/locacaoView/buscaLocacao.jsp?error=" + errorMessage);
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // Funções de UPDATE
    // apresenta formulário de edição de Locadora com informações referentes ao seu
    // id
    private void editarLocacaoForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Locacao locacaoExistente = dao.getByID(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacaoView/editarLocacao.jsp");
        request.setAttribute("Locadora", locacaoExistente);
        dispatcher.forward(request, response);
    }

    // atualiza informações de Locadora no banco de dados
    private void atualizarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String cpfCliente = request.getParameter("cpfCliente");
        String cnpjLocadora = request.getParameter("cnpjLocadora");
        String dataHora = request.getParameter("dataHora");
        LocalDateTime dtDiaHora = LocalDateTime.parse(dataHora);

        Locacao locacaoAtualizado = new Locacao(id, cpfCliente, cnpjLocadora, dtDiaHora);
        dao.update(locacaoAtualizado);
        response.sendRedirect("list");
    }

    // Funções de DELETE
    // deleta Locadora do banco de dados
    private void deletarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.delete(id);
        // redireciona para a pagina em Locadora/list
        response.sendRedirect("list");
    }

}
