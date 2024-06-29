package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import domain.Cliente;
import domain.Locacao;

@WebServlet(urlPatterns = "/cliente/*")
public class ControllerCliente extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO dao;

    @Override
    public void init() {
        dao = new ClienteDAO();
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

                // read
                case "/list":
                    listarClientes(request, response);
                    break;
                case "/buscarCliente":
                    paginaBuscarCliente(request, response);
                    break;
                case "/procurar":
                    procuraCliente(request, response);
                    break;

                // update
                case "/editar":
                    editarClienteForm(request, response);
                    break;
                case "/atualizar":
                    atualizarCliente(request, response);
                    break;

                // deletar
                case "/deletar":
                    deletarCliente(request, response);
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

    // Cliente passa o CPF para usar na busca de locações
    private void clienteCPFSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/formCPFCliente.jsp");
        dispatcher.forward(request, response);
    }

    // Apresenta todas as locações de um cliente dado o cpf
    private void listarClienteLocacaoByCPF(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");
        List<Locacao> listaCliente = dao.getLocacaoByCPF(cpf);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/novoCliente.jsp");
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
        response.sendRedirect("list");
    }

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/clienteLista.jsp");
        dispatcher.forward(request, response);

    }

    // apresenta formulário de busca de cliente
    private void paginaBuscarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // System.out.println("cade a pagina fi"); Teste de conexão
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLogado/clienteView/buscaCliente.jsp");
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
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("/clienteLogado/clienteView/clienteEspecifico.jsp");
                    dispatcher.forward(request, response);
                }

                else {
                    response.sendRedirect("Cliente não encontrado. ID não reconhecido");
                }
            }

            catch (RuntimeException | IOException | ServletException e) {
                throw new ServletException(e);
            }
        }
    }

    private void paginaEditarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCliente = request.getParameter("id");
        if (idCliente != null) {
            try {
                long id = Long.parseLong(idCliente);
                Cliente cliente = dao.get(id);

                if (cliente != null) {
                    request.setAttribute("clienteEspecifico", cliente);
                    RequestDispatcher dispatcher = request
                            .getRequestDispatcher("/clienteLogado/clienteView/editarCliente.jsp");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editarCliente.jsp");
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

}
