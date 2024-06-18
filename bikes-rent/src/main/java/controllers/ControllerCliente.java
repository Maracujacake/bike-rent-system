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
            	/* chamar outras funçoes com base na requisiçao seguindo o exemplo abaixo
            	 
                case "/list":
                    listarClientes(request, response);
                    break;
                */ 
                default:
                    listarClientes(request, response);
                    break;
            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }

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
}
