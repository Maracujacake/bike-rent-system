package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import domain.Cliente;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
		if(action == null) action = " ";

        try {
            switch (action) {
                //create
                case "/novo":
                    novoClienteForm(request, response);
                    break;
                case "/inserir":
                    inserirCliente(request, response);
                    break;

                //read
                case "/list":
                    listarClientes(request, response);
                    break;
                case "/buscarCliente":
                    paginaBuscarCliente(request, response);
                    break;
                case "/procurar":
                    procuraCliente(request, response);
                    break;

                //update
                case "/editar":
                    editarClienteForm(request, response);
                    break;
                case "/atualizar":
                    atualizarCliente(request, response);
                    break;

                //deletar
                case "/deletar":
                    deletarCliente(request, response);
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
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/opcoesCliente.jsp");
        dispatcher.forward(request, response);
    }
    

    
    // Funções de CREATE

    // apresenta formulário de criação de cliente
    private void novoClienteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/novoCliente.jsp");
        dispatcher.forward(request, response);
    }

    // insere cliente no banco de dados
    private void inserirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String cpf = request.getParameter("cpf");
        Date dataNascimento = Date.valueOf(request.getParameter("dataNascimento"));

        Cliente novoCliente = new Cliente(nome, email, senha, telefone, sexo, cpf, dataNascimento);
        dao.insert(novoCliente);
        response.sendRedirect("list");
    }



    // Funções de READ

    // Apresenta todos os clientes
    private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> listaCliente = dao.getAll();
        
        /* TESTE 
		for(Cliente pessoa : listaCliente){
			System.out.println(pessoa.getDataNascimento());
		}
		*/
		
        request.setAttribute("listaCliente", listaCliente);
        // o loop infinito era causado por erro no caminho do arquivo jsp. a pasta webapp é a ''raiz''
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteLista.jsp");
		dispatcher.forward(request, response);
		
    }
    
    // apresenta formulário de busca de cliente
    private void paginaBuscarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	// Teste de conexão: System.out.println("cade a pagina fi");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/buscaCliente.jsp");
        dispatcher.forward(request, response);
    }
    
    
    // procura cliente pelo seu ID no banco
    private void procuraCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	String idCliente = request.getParameter("id");
    	if(idCliente != null) {
    		try {
                long id = Long.parseLong(idCliente); 
        		Cliente cliente = dao.get(id);
        		
        		if(cliente != null) {
        			request.setAttribute("clienteEspecifico", cliente);
        			RequestDispatcher dispatcher = request.getRequestDispatcher("/clienteEspecifico.jsp");
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



    // Funções de UPDATE

    // apresenta formulário de edição de cliente com informações referentes ao seu id
    private void editarClienteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Cliente clienteExistente = dao.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editarCliente.jsp");
        request.setAttribute("cliente", clienteExistente);
        dispatcher.forward(request, response);
    }

    // atualiza informações de cliente no banco de dados
    private void atualizarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String telefone = request.getParameter("telefone");
        String sexo = request.getParameter("sexo");
        String cpf = request.getParameter("cpf");
        String dataNascimento = request.getParameter("dataNascimento");

        Cliente clienteAtualizado = new Cliente(id, email, senha, nome, telefone, sexo, cpf, java.sql.Date.valueOf(dataNascimento));
        dao.update(clienteAtualizado);
        response.sendRedirect("list");
    }


    


    // Funções de DELETE

    // deleta cliente do banco de dados
    private void deletarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        dao.delete(id);
        response.sendRedirect("list");
    }
    
    
}
