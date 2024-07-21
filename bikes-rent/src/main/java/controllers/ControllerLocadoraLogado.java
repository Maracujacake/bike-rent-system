package controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LocacaoDAO;
import dao.LocadoraDAO;
import domain.Locacao;
import domain.Locadora;

// Funções referentes à Locadora quando logada
@WebServlet(urlPatterns = "/locadoraLogged/*")
public class ControllerLocadoraLogado extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LocadoraDAO daoLocadora;
    private LocacaoDAO daoLocacao;

    @Override
    public void init() {
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

                // READ
                case "/locacoesCNPJ":
                    buscaLocacoesCNPJ(request, response);
                    break;

                // UPDATE
                case "/editarLocacao":
                    editarLocacao(request, response);
                    break;

                case "/atualizarLocacao":
                    atualizarLocacao(request, response);
                    break;
                
                case "/editarLocadora":
                    editarLocadora(request, response);
                    break;

                case "/atualizarLocadora":
                    atualizarLocadora(request, response);
                    break;

                // DELETE
                case "/deletarLocacao":
                    deletarLocacao(request, response);
                    break;

                default:
                    paginaInicial(request, response);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Página inicial de opções de Locadora 
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraLogado/opcoesLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // *** Funções de READ ***

    // Busca todas as locações de uma locadora pelo CNPJ
    private void buscaLocacoesCNPJ(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        String cnpj = (String) session.getAttribute("cnpj");
        List<Locacao> listaLocadora = daoLocadora.getLocacaoByCNPJ(cnpj);
      
        request.setAttribute("listaLocadora", listaLocadora);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraLogado/locacoesLocadora.jsp");
        dispatcher.forward(request, response);
    }

    // *** Funções de UPDATE ***

    // Apresenta formulário de edição de Locação
    private void editarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idLocacao = request.getParameter("id");
        if (idLocacao != null) {
            try {
                long id = Long.parseLong(idLocacao);
                Locacao locacao = daoLocacao.getByID(id);

                if (locacao != null) {
                    request.setAttribute("locacao", locacao);
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("/locadoraLogado/editarLocacao.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("Locacao não encontrada. ID não reconhecido");
                }
            } catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    // Atualiza Locação no banco
    private void atualizarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String cpf = request.getParameter("cpfCliente");
        String cnpj = request.getParameter("cnpjLocadora");
        String dataHorario = request.getParameter("dataHorario");
        LocalDateTime dtDiaHora = LocalDateTime.parse(dataHorario);
        Locacao locacaoAtualizado = new Locacao(id, cpf, cnpj, dtDiaHora);
        daoLocacao.update(locacaoAtualizado);
        response.sendRedirect("");
    }

    private void editarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                //System.out.println("chegou aqui");
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                HttpSession session = httpRequest.getSession(false);
                String cnpj = (String) session.getAttribute("cnpj");
                Locadora locadora = daoLocadora.getByCnpj(cnpj);
              
                request.setAttribute("locadora", locadora);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/locadoraLogado/editarLocadora.jsp");
                dispatcher.forward(request, response);
    }

    private void atualizarLocadora(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String cidade = request.getParameter("cidade");
        String cnpj = request.getParameter("cnpj");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        daoLocadora.update(new Locadora(id, senha, nome, cidade, cnpj, email));
        response.sendRedirect("");
    }

    // *** Funções de DELETE ***

    // Deleta Locação do banco
    private void deletarLocacao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        daoLocacao.delete(id);
        // redireciona para a pagina de locacoes de locadora
        response.sendRedirect("locacoesCNPJ");
    }

}
