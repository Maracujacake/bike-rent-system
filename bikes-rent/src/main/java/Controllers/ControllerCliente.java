package Controllers;

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
	// define rotas e chama funções com base nessas rotas relativas a cliente

	private ClienteDAO dao;

	@Override
	public void init() {
		dao = new ClienteDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String action = request.getPathInfo();
		if (action == null) {
			action = "";
		}

		try {
			// Ta pegando as informaçoes do DAO porem nao renderiza no jsp. Fica em loop
			// infinito e crasha.
			algumacoisa(request, response);

		} catch (RuntimeException | IOException | ServletException e) {
			throw new ServletException(e);
		}
	}

	public void algumacoisa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("algumacoisssa");
		List<Cliente> listaCliente = dao.getAll();
		listaCliente.forEach(cliente -> {
			System.out.println(cliente.getDataNascimento() + " " + cliente.getNome());
		});
		// request.setAttribute("listaCliente", listaCliente);
		// // request.setAttribute("contextPath", request.getContextPath().replace("/",
		// // ""));
		// RequestDispatcher dispatcher =
		// request.getRequestDispatcher("/cliente/lista.jsp");
		// dispatcher.forward(request, response);

	}
}
