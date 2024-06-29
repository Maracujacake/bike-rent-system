package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClienteLoginDAO;

// confere as informações de login do cliente provenientes do loginCliente.jsp
@WebServlet("/clienteLogin")
public class ControllerClienteLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClienteLoginDAO dao; 


    @Override
    public void init() {
        dao = new ClienteLoginDAO();
		// System.out.println("ALO"); teste de inicialização
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
            try {
                if (dao.authCliente(email, password) != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);

                    String redirectTo = (String) session.getAttribute("redirectTo");
                    if (redirectTo != null) {
                        session.removeAttribute("redirectTo");
                        response.sendRedirect(request.getContextPath() + redirectTo);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/cliente/list");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/login/loginCliente.jsp");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }
}
