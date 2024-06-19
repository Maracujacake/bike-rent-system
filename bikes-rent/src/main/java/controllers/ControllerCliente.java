package controllers;

import java.io.IOException;
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
            	case "/list": // apresentação de todos os clientes
            		listarClientes(request, response);
            		break;
            	case "/buscarCliente": // apresentação da pagina de busca por id
            		paginaBuscarCliente(request, response);
            		break;
            	case "/procurar": // busca no banco de dados
            		procuraCliente(request, response);
            		break;
            	case "/": // menu inicial
                    paginaInicial(request, response);
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
    // apresenta formulário de busca de cliente
    private void paginaInicial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/opcoesCliente.jsp");
        dispatcher.forward(request, response);
    }
    
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
    	System.out.println("cade a pagina fi");
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
    
    
}
