package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;

// Confere as informações de login do cliente provenientes do loginCliente.jsp
@WebServlet("/adminLogin")
public class ControllerAdminLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO dao;

    // Inicialização
    @Override
    public void init() {
        dao = new AdminDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            if (dao.authAdmin(email, password) != null) {
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                String nomeAdmin = email.split("@")[0];
                session.setAttribute("role", "admin");
                session.setAttribute("nome", nomeAdmin);

                String redirectTo = (String) session.getAttribute("redirectTo");
                if (redirectTo != null) {
                    session.removeAttribute("redirectTo");
                    response.sendRedirect(redirectTo);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/");
                }
            } else {
                String errorMessage = URLEncoder.encode("Senha ou email invalidos!", StandardCharsets.UTF_8.toString());

                response.sendRedirect(request.getContextPath() + "/login/loginAdmin.jsp?error="+errorMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
